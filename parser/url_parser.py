from selenium import webdriver
from selenium.webdriver.common.by import By
import csv
import time

BASE_URL = "https://spb.hh.ru/search/resume?no_magic=true&order_by=relevance&ored_clusters=true&search_period=0&items_on_page=100"
TOTAL_PAGES = 50
cv_urls = []

driver = webdriver.Chrome()


def save_to_csv(urls):
    with open("cv_urls.csv", "w", newline="") as file:
        writer = csv.writer(file)
        writer.writerow(["URL"])
        for url in urls:
            writer.writerow([url])


try:
    for page in range(TOTAL_PAGES):
        driver.get(f"{BASE_URL}&page={page}")

        cv_elements = driver.find_elements(By.CSS_SELECTOR, "#a11y-main-content .serp-item a.serp-item__title")

        cv_urls.extend([cv_element.get_attribute("href") for cv_element in cv_elements])

        time.sleep(2)

except Exception as e:
    print(f"An error occurred: {e}")
finally:
    save_to_csv(cv_urls)

    driver.quit()

    print("CV URLs have been saved to cv_urls.csv!")


