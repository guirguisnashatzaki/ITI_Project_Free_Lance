package org.itiprojectindeed.Guirguis;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Login_Page {


    //Variables
    private final WebDriver driver;


    //Locators
    By emailField = By.cssSelector("input[type='email']");
    By passwordField = By.cssSelector("input[type='password']");
    By loginButton = By.cssSelector("button[type='submit']");
    By forgotPasswordLink = By.cssSelector("a[href*=\"https://www.freelanceyard.com/en/forgot-password\"]");
    //Constructor
    public Login_Page(WebDriver driver){
        this.driver = driver;
    }


    //Actions (Test Cases)
    public String LoginWithValidCredentials(String user, String pass){
        driver.findElement(emailField).clear();
        driver.findElement(emailField).sendKeys(user);
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(pass);
        driver.findElement(loginButton).click();
        return driver.findElement(loginButton).getText();
    }

    public boolean isLoggedIn(String expectedUrl){
        return driver.getCurrentUrl().equals(expectedUrl);
    }

    public boolean checkDisplaying(){
        return driver.findElement(emailField).isDisplayed() &&
               driver.findElement(passwordField).isDisplayed() &&
               driver.findElement(loginButton).isDisplayed();
    }

    public boolean loginWithEmptyFields(){
        driver.findElement(loginButton).click();
        return driver.getPageSource().contains("required") || driver.getCurrentUrl().contains("login");
    }

    public boolean loginWithInvalidCredentials(String invalidUser, String invalidPass){
        driver.findElement(emailField).sendKeys(invalidUser);
        driver.findElement(passwordField).sendKeys(invalidPass);
        driver.findElement(loginButton).click();
        return driver.getPageSource().toLowerCase().contains("invalid")
                || driver.getCurrentUrl().contains("login");
    }

    public Boolean verifyForgotPasswordLink(){
        driver.findElement(forgotPasswordLink).click();
        return driver.getCurrentUrl().contains("forgot");
    }

    public void clearFields(){
        driver.findElement(emailField).clear();
        driver.findElement(passwordField).clear();
    }





}
