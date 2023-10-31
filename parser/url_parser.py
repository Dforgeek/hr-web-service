from selenium import webdriver
from selenium.webdriver.common.by import By
import csv
import time

BASE_URL = "https://spb.hh.ru/search/resume?no_magic=true&order_by=relevance&ored_clusters=true&search_period=0&items_on_page=100"
TOTAL_PAGES = 50
cv_urls = []

# Set up the Selenium webdriver (assuming you're using Chrome)
driver = webdriver.Chrome()


def save_to_csv(urls):
    """Save the given list of URLs to a CSV file."""
    with open("cv_urls.csv", "w", newline="") as file:
        writer = csv.writer(file)
        writer.writerow(["URL"])  # Header
        for url in urls:
            writer.writerow([url])


try:
    for page in range(TOTAL_PAGES):
        # Navigate to the URL with the current page number
        driver.get(f"{BASE_URL}&page={page}")

        # Find all CV URLs within the specified container
        cv_elements = driver.find_elements(By.CSS_SELECTOR, "#a11y-main-content .serp-item a.serp-item__title")

        # Extract the href attribute (the URL) from each element and add to the list
        cv_urls.extend([cv_element.get_attribute("href") for cv_element in cv_elements])

        # Wait for a short duration before moving to the next page to avoid being rate-limited
        time.sleep(2)

except Exception as e:
    print(f"An error occurred: {e}")
finally:
    # Save the URLs to the CSV file
    save_to_csv(cv_urls)

    # Close the browser
    driver.quit()

    print("CV URLs have been saved to cv_urls.csv!")


