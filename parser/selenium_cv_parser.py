import concurrent.futures
import re
import signal
import sys
from threading import Lock

import pandas as pd
from selenium import webdriver
from selenium.common.exceptions import NoSuchElementException
from selenium.webdriver.common.by import By


def get_job_search_status(driver):
    try:
        # Поиск элемента по атрибуту data-qa
        status_element = driver.find_element(By.CSS_SELECTOR, '[data-qa="job-search-status"]')
        return status_element.text
    except NoSuchElementException:
        return None


def get_personal_info(driver):
    info = {}

    # Извлекаем пол
    try:
        gender = driver.find_element(By.CSS_SELECTOR, '[data-qa="resume-personal-gender"]').text
        info['gender'] = gender
    except NoSuchElementException:
        info['gender'] = None

    # Извлекаем возраст
    try:
        age = driver.find_element(By.CSS_SELECTOR, '[data-qa="resume-personal-age"]').text
        # Убираем "лет" и другие лишние символы
        age = int(age.split()[0])
        info['age'] = age
    except NoSuchElementException:
        info['age'] = None

    # Извлекаем город
    try:
        city = driver.find_element(By.CSS_SELECTOR, '[data-qa="resume-personal-address"]').text
        info['city'] = city
    except NoSuchElementException:
        info['city'] = None

    # Извлекаем готовность к переезду
    try:
        general_info = driver.find_element(By.CSS_SELECTOR, '.bloko-translate-guard p').text

        info[
            'relocation_ready'] = 'не готов к переезду' not in general_info and 'не готова к переезду' not in general_info
        info[
            'business_trip_ready'] = 'не готов к командировкам' not in general_info and 'не готова к командировкам' not in general_info
    except NoSuchElementException:
        info['relocation_ready'] = False
        info['business_trip_ready'] = False

    return info


def extract_position_info(driver):
    info = {}

    # Извлекаем позицию
    try:
        position = driver.find_element(By.CSS_SELECTOR, '[data-qa="resume-block-title-position"]').text
        info['position'] = position
    except NoSuchElementException:
        info['position'] = None

    # Извлекаем специализации
    try:
        specializations = driver.find_elements(By.CSS_SELECTOR, '[data-qa="resume-block-position-specialization"]')
        info['specializations'] = [spec.text for spec in specializations]
    except NoSuchElementException:
        info['specializations'] = None

    # Извлекаем занятость
    try:
        employment = driver.find_element(By.XPATH, "//p[contains(text(), 'Занятость:')]").text.split(":")[1].strip()
        info['employment'] = employment
    except NoSuchElementException:
        info['employment'] = None

    # Извлекаем график работы
    try:
        work_schedule = driver.find_element(By.XPATH, "//p[contains(text(), 'График работы:')]").text.split(":")[
            1].strip()
        info['work_schedule'] = work_schedule
    except NoSuchElementException:
        info['work_schedule'] = None

    # Извлекаем желаемую зарплату
    try:
        salary_text = driver.find_element(By.CSS_SELECTOR, '[data-qa="resume-block-salary"]').text
        salary_amount = int("".join(salary_text.split()[0:2]).replace('\u202f', ''))
        # Удаляем неразрывный пробел и конвертируем в int

        if '₸' in salary_text:
            conversion_rate = 0.2
            salary_rub = salary_amount * conversion_rate
        elif '$' in salary_text:
            conversion_rate = 100
            salary_rub = salary_amount * conversion_rate
        elif 'Br' in salary_text:
            conversion_rate = 28.11
            salary_rub = salary_amount * conversion_rate

        elif '₽' in salary_text:
            salary_rub = salary_amount
        else:
            salary_rub = None  # Если валюта неизвестна

        info['desired_salary'] = salary_rub
    except NoSuchElementException:
        info['desired_salary'] = None

    return info


def extract_work_experience(driver):
    info = {}

    # Извлекаем общий опыт работы
    try:
        years_text = driver.find_element(By.XPATH, "//span[contains(text(), 'Опыт работы')]/span[1]").text
        years = int(years_text.split()[0])

        months_text = driver.find_element(By.XPATH, "//span[contains(text(), 'Опыт работы')]/span[2]").text
        months = int(months_text.split()[0])

        total_experience = years + months / 12.0
        info['total_experience'] = round(total_experience, 2)
    except NoSuchElementException:
        info['total_experience'] = None

    # Извлекаем информацию о предыдущих позициях
    previous_positions = []
    positions_elements = driver.find_elements(By.CSS_SELECTOR, '[data-qa="resume-block-experience-position"]')

    for pos_el in positions_elements:
        previous_positions.append(pos_el.text)

    info['previous_positions'] = previous_positions

    return info


def extract_skills(driver):
    # Ищем все элементы с ключевыми навыками
    skills_elements = driver.find_elements(By.CSS_SELECTOR, '[data-qa="bloko-tag__text"] span')

    # Извлекаем текст из каждого элемента и добавляем его в список
    skills = [skill.text for skill in skills_elements]

    return skills


def extract_languages(driver):
    # Ищем все элементы с языками и их уровнями
    language_elements = driver.find_elements(By.CSS_SELECTOR, '[data-qa="resume-block-language-item"]')

    # Извлекаем язык и уровень из каждого элемента и добавляем их в словарь
    languages = {}
    for lang_el in language_elements:
        lang_info = lang_el.text.split(' — ')
        # print("Lang info", lang_info)
        if len(lang_info) >= 2:
            lang = lang_info[0].strip()
            level = lang_info[1].strip()  # taking the last part as level
            languages[lang] = level

    # print("Что в конечном счёте записано", languages)

    return languages



def extract_higher_education(driver):
    # Ищем элементы с названием учебного заведения и специализацией
    education_elements = driver.find_elements(By.CSS_SELECTOR,
                                              '[data-qa="resume-block-education"] [data-qa="resume-block-education-item"]')

    return _extract_education_data(education_elements)


def extract_courses(driver):
    # Ищем элементы с названием учебного заведения и специализацией для курсов
    courses_elements = driver.find_elements(By.CSS_SELECTOR,
                                            '[data-qa="resume-block-additional-education"] [data-qa="resume-block-education-item"]')

    return _extract_education_data(courses_elements)


def _extract_education_data(elements):
    # Общая функция для извлечения информации об образовании
    education_list = []
    for edu_el in elements:
        name = edu_el.find_element(By.CSS_SELECTOR, '[data-qa="resume-block-education-name"]').text
        organization = edu_el.find_element(By.CSS_SELECTOR, '[data-qa="resume-block-education-organization"]').text

        education_info = {
            "name": name,
            "organization": organization
        }
        education_list.append(education_info)

    return education_list


def extract_driving_info(driver):
    # Проверяем наличие собственного автомобиля
    has_car_elements = driver.find_elements(By.XPATH, '//p[contains(text(), "Имеется собственный автомобиль")]')
    has_car = len(has_car_elements) > 0

    # Извлекаем категории вождения
    try:
        driving_categories_element = driver.find_element(By.XPATH, '//div[contains(text(), "Права категории")]')
        driving_categories_text = driving_categories_element.text.split("Права категории")[1].strip()
    except NoSuchElementException:
        driving_categories_text = ''

    # Ищем категории с помощью регулярного выражения
    categories = re.findall(r'\b[A-DM]|(?:[ABCD]1?E?)\b', driving_categories_text)

    return {
        "has_car": has_car,
        "driving_categories": categories
    }


def extract_about_me(driver):
    try:
        about_me_element = driver.find_element(By.XPATH, '//div[@data-qa="resume-block-skills-content"]/span')
        return about_me_element.text
    except NoSuchElementException:
        return ''



def process_cv(url):
    try:
        driver = webdriver.Chrome()
        driver.get(url)
        job_search_status = get_job_search_status(driver)
        personal_info_dict = get_personal_info(driver)
        position_dict = extract_position_info(driver)
        experience_dict = extract_work_experience(driver)
        skills_list = extract_skills(driver)
        lang_dict = extract_languages(driver)

        edu_dict = extract_higher_education(driver)
        courses_dict = extract_courses(driver)
        car_dict = extract_driving_info(driver)
        about_me_str = extract_about_me(driver)

        data_row = {
            "Position": position_dict['position'],
            "Sex": personal_info_dict['gender'],
            "Age": personal_info_dict['age'],
            "City": personal_info_dict['city'],
            "Is_Relocation_Ready": personal_info_dict['relocation_ready'],
            "Is_Business_Trip_Ready": personal_info_dict['business_trip_ready'],

            "Specializations": ", ".join(position_dict.get('specializations', [])),
            "Employment": position_dict.get('employment', ''),
            "Work_Schedule": position_dict.get('work_schedule', ''),
            "Desired_Salary": position_dict.get('desired_salary', ''),
            "Total_Experience": experience_dict.get('total_experience', ''),
            "Previous_Positions": ", ".join(experience_dict.get('previous_positions', [])),
            "Skills": ", ".join(skills_list),
            "Languages": ", ".join([f"{k} - {v}" for k, v in lang_dict.items()]),
            "Education": ", ".join([f"{item['name']} - {item['organization']}" for item in edu_dict]),
            "Courses": ", ".join([f"{item['name']} - {item['organization']}" for item in courses_dict]),
            "Has_Car": car_dict.get('has_car', False),
            "Driving_Categories": ", ".join(car_dict.get('driving_categories', [])),
            "About_Me": about_me_str,
            "Job_Search_Status": job_search_status
        }

        driver.close()
        return data_row
    except Exception as e:
        print(f"Error processing resume at {url}: {str(e)}")
        return None


if __name__ == '__main__':
    NUM_THREADS = 6
    data_rows = []
    processed_count = 0
    lock = Lock()  # <-- Lock for thread-safe counter increment


    def save_data():
        df = pd.DataFrame(data_rows)
        df.to_csv("resume_data.csv", index=False)
        print("Data saved to resume_data.csv")


    def signal_handler(sig, frame):
        print('You pressed Ctrl+C! Saving data before exiting...')
        save_data()
        sys.exit(0)


    def process_cv(url):
        global processed_count
        try:
            driver = webdriver.Chrome()
            driver.get(url)
            job_search_status = get_job_search_status(driver)
            personal_info_dict = get_personal_info(driver)
            position_dict = extract_position_info(driver)
            experience_dict = extract_work_experience(driver)
            skills_list = extract_skills(driver)
            lang_dict = extract_languages(driver)
            edu_dict = extract_higher_education(driver)
            courses_dict = extract_courses(driver)
            car_dict = extract_driving_info(driver)
            about_me_str = extract_about_me(driver)

            data_row = {
                "Position": position_dict['position'],
                "Sex": personal_info_dict['gender'],
                "Age": personal_info_dict['age'],
                "City": personal_info_dict['city'],
                "Is_Relocation_Ready": personal_info_dict['relocation_ready'],
                "Is_Business_Trip_Ready": personal_info_dict['business_trip_ready'],

                "Specializations": ", ".join(position_dict.get('specializations', [])),
                "Employment": position_dict.get('employment', ''),
                "Work_Schedule": position_dict.get('work_schedule', ''),
                "Desired_Salary": position_dict.get('desired_salary', ''),
                "Total_Experience": experience_dict.get('total_experience', ''),
                "Previous_Positions": ", ".join(experience_dict.get('previous_positions', [])),
                "Skills": ", ".join(skills_list),
                "Languages": ", ".join([f"{k} - {v}" for k, v in lang_dict.items()]),
                "Education": ", ".join([f"{item['name']} - {item['organization']}" for item in edu_dict]),
                "Courses": ", ".join([f"{item['name']} - {item['organization']}" for item in courses_dict]),
                "Has_Car": car_dict.get('has_car', False),
                "Driving_Categories": ", ".join(car_dict.get('driving_categories', [])),
                "About_Me": about_me_str,
                "Job_Search_Status": job_search_status,
                "URL": url
            }

            driver.close()
            data_rows.append(data_row)

            with lock:  # Acquire the lock to ensure thread safety
                processed_count += 1
                print(f"Processed {processed_count}/{len(url_list)} CVs")

        except Exception as e:
            print(f"Error processing resume at {url}: {str(e)}")
            return None


    signal.signal(signal.SIGINT, signal_handler)
    signal.signal(signal.SIGTERM, signal_handler)

    url_list = pd.read_csv('cv_urls.csv')
    url_list = url_list['URL'].tolist()

    with concurrent.futures.ThreadPoolExecutor(max_workers=NUM_THREADS) as executor:
        executor.map(process_cv, url_list)

    save_data()
