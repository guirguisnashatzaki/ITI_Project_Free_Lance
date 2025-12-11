package org.itiprojectindeed;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class JobDetailsPage extends BasePage {

    private By jobTitle = By.tagName("h1");
    private By jobDescription = By.xpath("//div[contains(@class, 'job-description')]");

    public JobDetailsPage(WebDriver driver) {
        super(driver);
    }

    public String getJobTitle() {
        WebElement titleElement = wait.until(ExpectedConditions.visibilityOfElementLocated(jobTitle));
        return titleElement.getText();
    }

    public boolean isJobDescriptionDisplayed() {
        try {
            WebElement descElement = wait.until(ExpectedConditions.visibilityOfElementLocated(jobDescription));
            return descElement.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}