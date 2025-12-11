package org.itiprojectindeed.Guirguis;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class LoginFree {
    WebDriver driver;
    WebDriverWait wait;

    // Locators
    @FindBy(css = "a[href='https://www.freelanceyard.com/en/login']")
    WebElement loginButtonHome;

    @FindBy(css = "input[type='email']")
    WebElement emailInput;

    @FindBy(css = "input[type='password']")
    WebElement passwordInput;

    @FindBy(css = "button[type='submit']")
    WebElement loginButtonSubmit;

    @FindBy(xpath = "//span[contains(@class,'user-name') or contains(text(),'Dashboard')]")
    WebElement profileName;

    public LoginFree(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void goToLoginPage() {
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
        try {
            driver.get("https://www.freelanceyard.com/en/home");
        } catch (TimeoutException e) {
            System.out.println("⚠️ Page load timeout reached. Forcing stop...");

            ((JavascriptExecutor) driver).executeScript("window.stop();");
        }


        loginButtonHome.click();
        //wait.until(ExpectedConditions.elementToBeClickable(loginButtonHome)).click();
    }

    public void login(String email, String password) {
        wait.until(ExpectedConditions.visibilityOf(emailInput)).sendKeys(email);
        passwordInput.sendKeys(password);
        loginButtonSubmit.click();
    }

    public boolean isLoginSuccessful() {
        try {
            wait.until(ExpectedConditions.visibilityOf(profileName));
            return profileName.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }
}
