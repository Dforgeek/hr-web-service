import requests
from bs4 import BeautifulSoup
import csv
from selenium import webdriver

import requests
from bs4 import BeautifulSoup
import csv


def get_address_relocation_business_trip_status(soup):
    # Extract city
    city = soup.find('span', {'data-qa': 'resume-personal-address'}).text

    # Extract readiness for relocation and business trips
    text = soup.p.text.replace(city, '').strip()

    relocation_statuses = [
        ("готов к переезду", 1),
        ("готова к переезду", 1),
        ("не готов к переезду", 0),
        ("не готова к переезду", 0)
    ]

    business_trip_statuses = [
        ("готов к командировкам", 1),
        ("готова к командировкам", 1),
        ("не готов к командировкам", 0),
        ("не готова к командировкам", 0)
    ]
    relocation_ready = "Unknown"
    business_trip_ready = "Unknown"
    relocation_ready_binary = -1
    business_trip_ready_binary = -1
    # Check relocation readiness
    for status, value in relocation_statuses:
        if status in text:
            relocation_ready = status
            relocation_ready_binary = value
            break

    # Check business trip readiness
    for status, value in business_trip_statuses:
        if status in text:
            business_trip_ready = status
            business_trip_ready_binary = value
            break

    return city, relocation_ready_binary, business_trip_ready_binary


def parse_resume(url):
    browser = webdriver.Chrome()
    browser.get(url)
    html_content = browser.page_source
    soup = BeautifulSoup(html_content, 'html.parser')

    # Extracting required data
    gender = soup.select_one('[data-qa="resume-personal-gender"]').text if soup.select_one(
        '[data-qa="resume-personal-gender"]') else 'null'
    age = soup.select_one('[data-qa="resume-personal-age"]').text if soup.select_one(
        '[data-qa="resume-personal-age"]') else 'null'

    city, relocation_ready, business_trip_ready = get_address_relocation_business_trip_status(soup)
    print(city, relocation_ready, business_trip_ready)

    desired_salary = soup.select_one('[data-qa="resume-block-salary"]').text if soup.select_one(
        '[data-qa="resume-block-salary"]') else 'null'
    employment_type = soup.text.split('Занятость: ')[1].split('\n')[0] if 'Занятость: ' in soup.text else 'null'
    work_schedule = soup.text.split('График работы: ')[1].split('\n')[0] if 'График работы: ' in soup.text else 'null'
    experience_years = soup.select_one('.resume-block__title-text_sub').text.split(' ')[2] if soup.select_one(
        '.resume-block__title-text_sub') else 'null'
    experience_description = ' '.join(
        [desc.text for desc in soup.select('[data-qa="resume-block-experience-description"]')])
    key_skills = ', '.join([skill.text for skill in soup.select('.bloko-tag__section_text')])
    education = soup.text.split('Образование')[1].split('\n')[1] if 'Образование' in soup.text else 'null'
    languages = soup.text.split('Знание языков')[1].split('\n')[1] if 'Знание языков' in soup.text else 'null'

    # Citizenship, work permit, and desired commute time are not provided in the sample HTML, so they are set to 'null'
    citizenship = 'null'
    work_permit = 'null'
    commute_time = 'null'
    browser.quit()

    # Return the data as a list (row for CSV)
    return [gender, age,  city, relocation_ready, business_trip_ready, experience_years, experience_description,
            None, desired_salary, employment_type, work_schedule, key_skills, education, languages,
            citizenship, work_permit, commute_time]


# Example usage:
# url = "YOUR_RESUME_URL_HERE"
# data_row = parse_resume(url)
#
# # Writing to CSV
# with open('parsed_resumes.csv', 'a', newline='', encoding='utf-8') as file:
#     writer = csv.writer(file)
#     writer.writerow(data_row)

if __name__ == "__main__":
    url = "https://spb.hh.ru/resume/a8121aa70000edb63d0039ed1f6274724c436b?hhtmFrom=resume_search_result"
    data_row = parse_resume(url)
    with open('parsed_resumes.csv', 'a', newline='', encoding='utf-8') as file:
        writer = csv.writer(file)
        writer.writerow(data_row)
