package org.itiprojectindeed;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Random;

public class Register_Free_Lance_Page extends BasePage {

    private By firstNameField = By.name("first_name");
    private By lastNameField = By.name("last_name");
    private By emailField = By.cssSelector("input[type='email']");
    private By passwordField = By.cssSelector("input[type='password']");
    private By mobileNumberField = By.cssSelector("input[type='tel']");
    private By registerButton = By.xpath("(//button[contains(text(),'Register')])[2]");
    private By genderDropdown = By.xpath("(//button[contains(@class,'hs-select')])[1]");
    private By countryDropdown = By.xpath("(//button[contains(@class,'hs-select')])[2]");
    private By mobileCode = By.xpath("(//button[contains(@class,'hs-select')])[3]");
    public Register_Free_Lance_Page(WebDriver driver) {
        super(driver);
    }

    public void selectDropdownOption(By dropdownLocator, String optionText) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(dropdownLocator));
        dropdown.click();

        By optionLocator = By.xpath("//div[@data-title-value='" + optionText + "']");
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
        option.click();
    }

    public void test(String optionText) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(mobileCode));
        dropdown.click();

        By optionLocator = By.xpath("//div[@data-title-value='" + optionText + "']");
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
        option.click();
    }

    public Boolean isElementDisplayed(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean verifyPageElements(){
        return isElementDisplayed(firstNameField) &&
                isElementDisplayed(lastNameField) &&
                isElementDisplayed(emailField) &&
                isElementDisplayed(passwordField) &&
                isElementDisplayed(mobileNumberField) &&
                isElementDisplayed(registerButton) &&
                isElementDisplayed(genderDropdown) &&
                isElementDisplayed(countryDropdown) &&
                isElementDisplayed(mobileCode);
    }

    public Boolean registerWithEmptyFields(){
        driver.findElement(By.xpath("//button[contains(text(),'Register')]")).click();
        return isElementDisplayed(By.xpath("//div[contains(text(),'First name is required')]")) &&
                isElementDisplayed(By.xpath("//div[contains(text(),'Last name is required')]")) &&
                isElementDisplayed(By.xpath("//div[contains(text(),'Email is required')]")) &&
                isElementDisplayed(By.xpath("//div[contains(text(),'Password is required')]")) &&
                isElementDisplayed(By.xpath("//div[contains(text(),'Mobile number is required')]"));
    }

    public Boolean registerWithInvalidEmail(String firstName, String lastName, String email, String password, String mobileNumber,String gender, String country){
        driver.findElement(firstNameField).sendKeys(firstName);
        driver.findElement(lastNameField).sendKeys(lastName);
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(mobileNumberField).sendKeys(mobileNumber);
        selectDropdownOption(genderDropdown,gender);
        selectDropdownOption(countryDropdown,country);
        driver.findElement(registerButton).click();
        return isElementDisplayed(By.xpath("//div[contains(text(),'must be a valid email')]"));
    }

    public Boolean registerWithValidData(){
        driver.findElement(firstNameField).sendKeys("John");
        driver.findElement(lastNameField).sendKeys("Smith");
        driver.findElement(emailField).sendKeys("johnsmith" + System.currentTimeMillis() + "@mail.com");
        driver.findElement(passwordField).sendKeys("Gogo_5401519");
        Random rand = new Random();
        long number = (long) (rand.nextDouble() * 9000000000L) + 1000000000L;
        driver.findElement(mobileNumberField).sendKeys(number + "");
        selectDropdownOption(genderDropdown,"Male");
        selectDropdownOption(countryDropdown,"Egypt");

        driver.findElement(registerButton).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("verify"));
        return driver.getCurrentUrl().contains("register");
    }

    public Boolean registerWithMissingFirstNameData(){
        driver.findElement(lastNameField).sendKeys("Smith");
        driver.findElement(emailField).sendKeys("johnsmith" + System.currentTimeMillis() + "@mail.com");
        driver.findElement(passwordField).sendKeys("Gogo_5401519");
        Random rand = new Random();
        long number = (long) (rand.nextDouble() * 9000000000L) + 1000000000L;
        driver.findElement(mobileNumberField).sendKeys(number + "");
        selectDropdownOption(genderDropdown,"Male");
        selectDropdownOption(countryDropdown,"Egypt");

        driver.findElement(registerButton).click();
        return driver.getPageSource().toLowerCase().contains("required")
                || driver.getCurrentUrl().contains("register");
    }

    public Boolean registerWithMissingLastNameData(){
        driver.findElement(firstNameField).sendKeys("Smith");
        driver.findElement(emailField).sendKeys("johnsmith" + System.currentTimeMillis() + "@mail.com");
        driver.findElement(passwordField).sendKeys("Gogo_5401519");
        Random rand = new Random();
        long number = (long) (rand.nextDouble() * 9000000000L) + 1000000000L;
        driver.findElement(mobileNumberField).sendKeys(number + "");
        selectDropdownOption(genderDropdown,"Male");
        selectDropdownOption(countryDropdown,"Egypt");

        driver.findElement(registerButton).click();
        return driver.getPageSource().toLowerCase().contains("required")
                || driver.getCurrentUrl().contains("register");
    }

    public Boolean registerWithMissingMobileNumberData(){
        driver.findElement(lastNameField).sendKeys("Smith");
        driver.findElement(firstNameField).sendKeys("Smith");
        driver.findElement(emailField).sendKeys("johnsmith" + System.currentTimeMillis() + "@mail.com");
        driver.findElement(passwordField).sendKeys("Gogo_5401519");
        selectDropdownOption(genderDropdown,"Male");
        selectDropdownOption(countryDropdown,"Egypt");

        driver.findElement(registerButton).click();
        return driver.getPageSource().toLowerCase().contains("required")
                || driver.getCurrentUrl().contains("register");
    }

    public Boolean registerWithMissingPasswordData(){
        driver.findElement(firstNameField).sendKeys("John");
        driver.findElement(lastNameField).sendKeys("Smith");
        driver.findElement(emailField).sendKeys("johnsmith" + System.currentTimeMillis() + "@mail.com");

        Random rand = new Random();
        long number = (long) (rand.nextDouble() * 9000000000L) + 1000000000L;
        driver.findElement(mobileNumberField).sendKeys(number + "");
        selectDropdownOption(genderDropdown,"Male");
        selectDropdownOption(countryDropdown,"Egypt");

        driver.findElement(registerButton).click();
        return driver.getPageSource().toLowerCase().contains("required")
                || driver.getCurrentUrl().contains("register");
    }

    public Boolean registerWithWeakPasswordData(){
        driver.findElement(firstNameField).sendKeys("John");
        driver.findElement(lastNameField).sendKeys("Smith");
        driver.findElement(emailField).sendKeys("johnsmith" + System.currentTimeMillis() + "@mail.com");
        driver.findElement(passwordField).sendKeys("12345");
        Random rand = new Random();
        long number = (long) (rand.nextDouble() * 9000000000L) + 1000000000L;
        driver.findElement(mobileNumberField).sendKeys(number + "");
        selectDropdownOption(genderDropdown,"Male");
        selectDropdownOption(countryDropdown,"Egypt");

        driver.findElement(registerButton).click();
        return driver.getPageSource().toLowerCase().contains("8 symbols")
                || driver.getCurrentUrl().contains("register");
    }

    public Boolean registerWithInvalidPhoneData(){
        driver.findElement(firstNameField).sendKeys("John");
        driver.findElement(lastNameField).sendKeys("Smith");
        driver.findElement(emailField).sendKeys("johnsmith" + System.currentTimeMillis() + "@mail.com");
        driver.findElement(passwordField).sendKeys("Gogo_5401519");
        Random rand = new Random();
        long number = (long) (rand.nextDouble() * 9000000000L) + 1000000000L;
        driver.findElement(mobileNumberField).sendKeys("asda");
        selectDropdownOption(genderDropdown,"Male");
        selectDropdownOption(countryDropdown,"Egypt");

        driver.findElement(registerButton).click();
        return driver.getPageSource().toLowerCase().contains("invalid")
                || driver.getCurrentUrl().contains("register");
    }

    public Boolean registerWithGenderEmptyData(){
        driver.findElement(firstNameField).sendKeys("John");
        driver.findElement(lastNameField).sendKeys("Smith");
        driver.findElement(emailField).sendKeys("johnsmith" + System.currentTimeMillis() + "@mail.com");
        driver.findElement(passwordField).sendKeys("Gogo_5401519");
        Random rand = new Random();
        long number = (long) (rand.nextDouble() * 9000000000L) + 1000000000L;
        driver.findElement(mobileNumberField).sendKeys(number+"");

        selectDropdownOption(countryDropdown,"Egypt");

        driver.findElement(registerButton).click();

        return driver.getPageSource().toLowerCase().contains("invalid")
                || driver.getCurrentUrl().contains("register");
    }

    public Boolean registerWithCountryEmptyData(){
        driver.findElement(firstNameField).sendKeys("John");
        driver.findElement(lastNameField).sendKeys("Smith");
        driver.findElement(emailField).sendKeys("johnsmith" + System.currentTimeMillis() + "@mail.com");
        driver.findElement(passwordField).sendKeys("Gogo_5401519");
        Random rand = new Random();
        long number = (long) (rand.nextDouble() * 9000000000L) + 1000000000L;
        driver.findElement(mobileNumberField).sendKeys(number+"");

        selectDropdownOption(genderDropdown,"Male");

        driver.findElement(registerButton).click();
        return driver.getPageSource().toLowerCase().contains("invalid")
                || driver.getCurrentUrl().contains("register");
    }

    public Boolean registerWithRegisteredEmailData(){
        driver.findElement(firstNameField).sendKeys("John");
        driver.findElement(lastNameField).sendKeys("Smith");
        driver.findElement(emailField).sendKeys("gogonashatzaki@gmail.com");
        driver.findElement(passwordField).sendKeys("Gogo_5401519");
        Random rand = new Random();
        long number = (long) (rand.nextDouble() * 9000000000L) + 1000000000L;
        driver.findElement(mobileNumberField).sendKeys(number+"");
        selectDropdownOption(countryDropdown,"Egypt");
        selectDropdownOption(genderDropdown,"Male");

        driver.findElement(registerButton).click();
        return driver.getPageSource().toLowerCase().contains("invalid")
                || driver.getCurrentUrl().contains("register");
    }


}