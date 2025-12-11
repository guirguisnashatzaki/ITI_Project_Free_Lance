import org.itiprojectindeed.GmailOtpFetcher;
import org.itiprojectindeed.LoginFree;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class Send_Proposal_Test extends BaseTest {

    By proposalTextArea = By.cssSelector("textarea.form-control");
    By priceInput = By.cssSelector("input[type='number']");
    By attachFileInput = By.xpath("//p[contains(normalize-space(.), 'Drop here or') and contains(normalize-space(.), 'Browse files')]");
    By submitProposalButton = By.cssSelector("button[type='button']");
    By currencyLabel =  By.xpath("//span[contains(text(), 'US Dollar')]");
    By uploadedFileName = By.xpath("//div[normalize-space(text())='testingExample.txt']");

    LoginFree loginPage;

    By view_Jobs_Button = By.xpath("//a[contains(@href, 'mtlob-to-do-list-bsyt')]");


    public void waitSeconds(WebDriver driver, int seconds) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(seconds))
                    .until(d -> false);
        } catch (TimeoutException ignored) {}
    }

    @BeforeClass
    public void setupPageObjects() {
        loginPage = new LoginFree(driver);
    }

    public void loginWithOtp() throws Exception {
        loginPage.goToLoginPage();
        loginPage.login("gogonashatzaki@gmail.com", "Gogo_5401519");

        GmailOtpFetcher f = new GmailOtpFetcher();
        String otp = f.waitForOtp("from:(no-reply@Freelance Yard.com) OR subject:(verification OR code)", 120000, 3000);
        System.out.println("Retrieved OTP: " + otp);

        driver.findElement(By.cssSelector("input[type='tel']")).sendKeys(otp);
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        waitSeconds(driver, 5);
    }

    @BeforeMethod
    public void navigateToSendProposalPage() throws Exception {
        loginWithOtp();
        driver.findElement(By.linkText("Jobs")).click();
        driver.findElement(view_Jobs_Button).click();
        driver.findElement(By.xpath("//a[normalize-space()='Send proposal']")).click();
    }

    @Test
    public void ProposalIsRequired() {
        driver.findElement(proposalTextArea).clear();
        driver.findElement(priceInput).sendKeys("50");
        driver.findElement(submitProposalButton).click();

        WebElement error = driver.findElement(By.cssSelector(".proposal-error")); // UPDATE LOCATOR
        Assert.assertTrue(error.isDisplayed());
    }

    @Test
    public void VerifyCurrencyLabel() {
        WebElement currency = driver.findElement(currencyLabel);
        Assert.assertTrue(currency.isDisplayed());
    }

    @Test
    public void UploadValidFile() {
        WebElement fileUpload = driver.findElement(attachFileInput);
        fileUpload.sendKeys("C:\\Users\\KimoStore\\Desktop\\spring-react\\gogo (1)\\ITI_Project_Indeed\\testExample.txt");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement fileName = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        uploadedFileName
                )
        );

        Assert.assertTrue(fileName.isDisplayed());
    }



}
