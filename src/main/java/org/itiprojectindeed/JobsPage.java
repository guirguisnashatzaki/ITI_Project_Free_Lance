package org.itiprojectindeed;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import java.util.List;

public class JobsPage extends BasePage {


    private By searchInput = By.cssSelector("input[placeholder='Job title']");
    private By categoryFilter = By.xpath("//h6[text()='Filter by category']/following-sibling::select");
    private By resetFilterBtn = By.xpath("//button[contains(text(), 'Reset filters')]");
    private By jobCards = By.cssSelector("div[class='h-full p-4 mb-4 bg-white border rounded-lg']");
    private By jobTitle = By.xpath(".//h3/a");
    private By jobClientInfo = By.cssSelector("i[class='uil uil-user']");
    private By jobCategory = By.cssSelector("span[class='text-xs']");
    private By jobBudget = By.cssSelector("span.text-green-700");
    private By jobDate = By.cssSelector("div.text-sm.text-gray-400");
    private By viewJobBtn = By.cssSelector("a[type='button']");

    public JobsPage(WebDriver driver) {
        super(driver);
    }

    public void searchJobs(String searchTerm) {
        try {
            WebElement searchBox = wait.until(ExpectedConditions.presenceOfElementLocated(searchInput));
            searchBox.clear();
            searchBox.sendKeys(searchTerm);

            waitForResultsToLoad();
        } catch (Exception e) {
            System.out.println("Error in searchJobs: " + e.getMessage());
            throw e;
        }
    }

    public void selectCategory(String categoryName) {
        try {
            WebElement categoryDropdown = wait.until(ExpectedConditions.presenceOfElementLocated(categoryFilter));
            Select select = new Select(categoryDropdown);
            select.selectByVisibleText(categoryName);
            waitForResultsToLoad();
        } catch (Exception e) {
            System.out.println("Error in selectCategory: " + e.getMessage());
            throw e;
        }
    }

    public void resetFilters() {
        try {
            WebElement resetBtn = wait.until(ExpectedConditions.elementToBeClickable(resetFilterBtn));
            resetBtn.click();
            waitForResultsToLoad();
        } catch (Exception e) {
            System.out.println("Error in resetFilters: " + e.getMessage());
            throw e;
        }
    }

    public List<WebElement> getAllJobCards() {
        try {
            return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(jobCards));
        } catch (Exception e) {
            System.out.println("Error getting job cards: " + e.getMessage());
            return List.of();
        }
    }

    public String getJobTitle(WebElement jobCard) {
        try {
            WebElement titleElement = jobCard.findElement(jobTitle);
            return titleElement.getText().trim();
        } catch (Exception e) {
            System.out.println("Error getting job title: " + e.getMessage());
            return "Title not found";
        }
    }

    public String getJobClient(WebElement jobCard) {
        try {
            WebElement clientElement = jobCard.findElement(jobClientInfo);
            String fullText = clientElement.getText();
            // Extract client name - remove icon text and client type
            String client = fullText.replace("uil-user", "")
                    .replace("Individual", "")
                    .replace("Startup", "")
                    .replace("-", "")
                    .trim();
            return client.isEmpty() ? "Unknown Client" : client;
        } catch (Exception e) {
            System.out.println("Error getting job client: " + e.getMessage());
            return "Client not found";
        }
    }

    public String getJobCategory(WebElement jobCard) {
        try {
            return jobCard.findElement(jobCategory).getText().trim();
        } catch (Exception e) {
            System.out.println("Error getting job category: " + e.getMessage());
            return "Category not found";
        }
    }

    public String getJobBudget(WebElement jobCard) {
        try {
            return jobCard.findElement(jobBudget).getText().trim();
        } catch (Exception e) {
            System.out.println("Error getting job budget: " + e.getMessage());

            try {
                List<WebElement> spans = jobCard.findElements(By.tagName("span"));
                for (WebElement span : spans) {
                    String text = span.getText();
                    if (text.contains("Budget") || text.contains("EGP") || text.contains("USD")) {
                        return text.trim();
                    }
                }
            } catch (Exception ex) {

            }
            return "Budget not found";
        }
    }

    public String getJobDate(WebElement jobCard) {
        try {
            return jobCard.findElement(jobDate).getText().trim();
        } catch (Exception e) {
            System.out.println("Error getting job date: " + e.getMessage());
            return "Date not found";
        }
    }

    public void clickViewJob(WebElement jobCard) {
        try {
            WebElement viewJobButton = jobCard.findElement(viewJobBtn);
            String jobUrl = viewJobButton.getAttribute("href");
            System.out.println("Navigating to: " + jobUrl);
            viewJobButton.click();


            wait.until(ExpectedConditions.urlContains(driver.getCurrentUrl()));
        } catch (Exception e) {
            System.out.println("Error clicking view job: " + e.getMessage());
            throw e;
        }
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public boolean isSearchInputEmpty() {
        try {
            WebElement searchBox = wait.until(ExpectedConditions.presenceOfElementLocated(searchInput));
            return searchBox.getAttribute("value").isEmpty();
        } catch (Exception e) {
            System.out.println("Error checking if search is empty: " + e.getMessage());
            return false;
        }
    }

    public int getJobCardsCount() {
        return getAllJobCards().size();
    }

    public boolean areResultsDisplayed() {
        try {
            return getJobCardsCount() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    private void waitForResultsToLoad() {
        // Wait for potential loading
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        try {
            wait.until(driver -> {
                List<WebElement> cards = driver.findElements(jobCards);
                return !cards.isEmpty() || driver.findElements(By.xpath("//*[contains(text(), 'No jobs') or contains(text(), 'no results')]")).size() > 0;
            });
        } catch (Exception e) {
            System.out.println("Wait for results loading completed with exception: " + e.getMessage());
        }
    }

    public void searchWithSpecialCharacters(String specialChars) {
        try {
            WebElement searchBox = wait.until(ExpectedConditions.presenceOfElementLocated(searchInput));
            searchBox.clear();
            searchBox.sendKeys(specialChars);
            waitForResultsToLoad();
        } catch (Exception e) {
            System.out.println("Error in searchWithSpecialCharacters: " + e.getMessage());
            throw e;
        }
    }

    public String getSearchInputValue() {
        try {
            WebElement searchBox = wait.until(ExpectedConditions.presenceOfElementLocated(searchInput));
            return searchBox.getAttribute("value");
        } catch (Exception e) {
            System.out.println("Error getting search input value: " + e.getMessage());
            return "";
        }
    }

    public String getSelectedCategory() {
        try {
            WebElement categoryDropdown = wait.until(ExpectedConditions.presenceOfElementLocated(categoryFilter));
            Select select = new Select(categoryDropdown);
            WebElement selectedOption = select.getFirstSelectedOption();
            return selectedOption.getText();
        } catch (Exception e) {
            System.out.println("Error getting selected category: " + e.getMessage());
            return "";
        }
    }

    public void clearSearch() {
        try {
            WebElement searchBox = wait.until(ExpectedConditions.presenceOfElementLocated(searchInput));
            searchBox.clear();


            searchBox.sendKeys("");
            waitForResultsToLoad();
        } catch (Exception e) {
            System.out.println("Error in clearSearch: " + e.getMessage());
            throw e;
        }
    }

    public void pressEnterOnSearchInput() {
        WebElement input = driver.findElement(searchInput); // غيري الـ locator حسب حالتك
        input.sendKeys(Keys.ENTER);
    }


    public String getNoJobsMessageText() {
        return driver.findElement(By.cssSelector("p[class='text-gray-500']")).getText();
    }

    public boolean isNoJobsFoundMessageDisplayed() {
        return driver.findElement(By.cssSelector("p[class='text-gray-500']")).isDisplayed();
    }
}
