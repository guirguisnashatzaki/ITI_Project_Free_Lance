package org.itiprojectindeed;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ClientRegisterPage {
    private WebDriver driver;
    private WebDriverWait wait;


    private By companyNameField = By.name("company_name");
    private By firstNameField = By.name("first_name");
    private By lastNameField = By.name("last_name");
    private By emailField = By.cssSelector("input[type='email']");
    private By passwordField = By.cssSelector("input[type='password']");
    private By countryDropdownButton = By.xpath("//button[@type='button' and @aria-expanded='false' and .//span[@data-title]]");
    private By phoneCodeDropdownButton = By.xpath("//label[contains(text(),'Cell Phone')]/following-sibling::div//select[@data-hs-select]/following-sibling::button");
    private By localPhoneField = By.xpath("//input[@type='tel' and contains(@class,'form-control') and contains(@class,'w-2/3') and @placeholder='Enter number without country code']");
    private By companyTypeDropdownButton = By.cssSelector("select[wire\\:model='company_type'] + button");
    private By companyTypeDropdownMenu = By.cssSelector("div[data-hs-select-dropdown].hs-dropdown-menu");
    private By registerButton = By.cssSelector("button[type='submit']");
    private By pageTitle = By.xpath("//div[contains(text(),'Client Registration')]");
    private By errorMessages = By.cssSelector("span.text-red-600");
    By searchBoxLocator = By.xpath("//form//div[6]//input");

    public ClientRegisterPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }


    public void navigateToPage() {
        driver.get("https://www.freelanceyard.com/en/register/client");
    }

    public boolean isPageLoaded() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitle));
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public void enterCompanyName(String companyName) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(companyNameField));
        element.clear();
        element.sendKeys(companyName);
    }

    public void enterFirstName(String firstName) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameField));
        element.clear();
        element.sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(lastNameField));
        element.clear();
        element.sendKeys(lastName);
    }

    public void enterEmail(String email) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));
        element.clear();
        element.sendKeys(email);
    }

    public void enterPassword(String password) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        element.clear();
        element.sendKeys(password);
    }


    public void selectCountry(String countryName) {
        try {
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(countryDropdownButton));
            button.click();

            By dropdownLocator = By.xpath("//div[@data-hs-select-dropdown]");
            wait.until(ExpectedConditions.visibilityOfElementLocated(dropdownLocator));

            By optionLocator = By.xpath("//div[@data-hs-select-dropdown]//div[@data-title-value='" + countryName + "']");
            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", option);
            option.click();
        } catch (Exception e) {
            throw new RuntimeException("Failed to select country: " + countryName, e);
        }
    }

    public void selectCountryByValue(String value) {
        try {
            String script = "var select = document.querySelector('select[wire\\\\:model=\"country_id\"]');" +
                    "if(select) { select.value = '" + value + "';" +
                    " select.dispatchEvent(new Event('change', { bubbles: true }));" +
                    " select.dispatchEvent(new Event('input', { bubbles: true })); }";
            ((JavascriptExecutor) driver).executeScript(script);
            wait.until(ExpectedConditions.attributeToBeNotEmpty(driver.findElement(By.cssSelector("select[wire\\:model='country_id']")), "value"));
        } catch (Exception e) {
            throw new RuntimeException("Failed to select country with value: " + value, e);
        }
    }


    public void selectPhoneCode(String phoneCode) {
        try {
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(phoneCodeDropdownButton));
            button.click();

            WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(searchBoxLocator));
            searchBox.clear();
            searchBox.sendKeys(phoneCode);

            By optionLocator = By.xpath("//div[@data-value='" + phoneCode + "']");
            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", option);
            option.click();
        } catch (Exception e) {
            throw new RuntimeException("Failed to select phone code: " + phoneCode, e);
        }
    }

    public void selectPhoneCodeByCountryName(String countryName) {
        try {
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(phoneCodeDropdownButton));
            button.click();

            WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(searchBoxLocator));
            searchBox.clear();
            searchBox.sendKeys(countryName);

            By optionLocator = By.xpath("//div[contains(normalize-space(), '(" + countryName + ")')]");
            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
            option.click();
        } catch (Exception e) {
            throw new RuntimeException("Failed to select phone code for country: " + countryName, e);
        }
    }

    public void enterLocalPhone(String phone) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(localPhoneField));
        element.clear();
        element.sendKeys(phone);
    }


    public void selectCompanyType(String companyType) {
        try {
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(companyTypeDropdownButton));
            button.click();

            By optionLocator = By.xpath("//div[@data-hs-select-dropdown]//div[normalize-space(text())='" + companyType + "']");
            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
            option.click();
        } catch (Exception e) {
            throw new RuntimeException("Failed to select company type: " + companyType, e);
        }
    }

    public void clickRegisterButton() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(registerButton));
        button.click();
    }


    public void waitForErrorMessages() {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(errorMessages));
        } catch (Exception e) {

        }
    }

    public boolean isErrorMessageDisplayed() {
        try {
            return !driver.findElements(errorMessages).isEmpty() &&
                    driver.findElements(errorMessages).get(0).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public List<String> getAllErrorMessages() {
        List<String> errors = new ArrayList<>();
        try {
            List<WebElement> errorElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(errorMessages));
            for (WebElement error : errorElements) {
                if (!error.getText().trim().isEmpty()) errors.add(error.getText());
            }
        } catch (Exception e) {

        }
        return errors;
    }

    public boolean isSpecificErrorDisplayed(String errorText) {
        return getAllErrorMessages().stream().anyMatch(error -> error.toLowerCase().contains(errorText.toLowerCase()));
    }

    public boolean isCompanyNameErrorDisplayed() { return isSpecificErrorDisplayed("company name"); }
    public boolean isFirstNameErrorDisplayed() { return isSpecificErrorDisplayed("first name"); }
    public boolean isLastNameErrorDisplayed() { return isSpecificErrorDisplayed("last name"); }
    public boolean isEmailErrorDisplayed() { return isSpecificErrorDisplayed("email"); }
    public boolean isPasswordErrorDisplayed() { return isSpecificErrorDisplayed("password"); }
    public boolean isCountryErrorDisplayed() { return isSpecificErrorDisplayed("country"); }
    public boolean isPhoneErrorDisplayed() { return isSpecificErrorDisplayed("phone") || isSpecificErrorDisplayed("cell phone"); }
    public boolean isCompanyTypeErrorDisplayed() { return isSpecificErrorDisplayed("account type") || isSpecificErrorDisplayed("company type"); }
}