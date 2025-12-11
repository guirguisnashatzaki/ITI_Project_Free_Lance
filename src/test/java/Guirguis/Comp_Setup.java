package Guirguis;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.itiprojectindeed.Guirguis.GmailOtpFetcher;
import org.itiprojectindeed.Guirguis.Home_Navigation_Page;
import org.itiprojectindeed.Guirguis.LoginFree;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class Comp_Setup extends BaseTest {

    WebDriver driver;
    LoginFree loginPage;

    Home_Navigation_Page homeNavigationPage;

    By view_Jobs_Button = By.xpath("//a[contains(@href, 'mtlob-to-do-list-bsyt')]");



    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        loginPage = new LoginFree(driver);
        homeNavigationPage = new Home_Navigation_Page(driver);

    }

    public void waitSeconds(WebDriver driver, int seconds) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(seconds))
                    .until(d -> false);
        } catch (TimeoutException ignored) {}
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

    @Test
    public void verifyingOpeningJobDetails() throws Exception {
        loginWithOtp();
        driver.findElement(By.linkText("Jobs")).click();
        driver.findElement(view_Jobs_Button).click();
        Assert.assertTrue(driver.findElement(By.xpath("//a[normalize-space()='Send proposal']")).isDisplayed());
    }

    @Test
    public void verifyingOpeningProposalPage() throws Exception {
        loginWithOtp();
        driver.findElement(By.linkText("Jobs")).click();
        driver.findElement(view_Jobs_Button).click();
        driver.findElement(By.xpath("//a[normalize-space()='Send proposal']")).click();
        Assert.assertTrue(driver.findElement(By.tagName("h1")).isDisplayed());
    }
}
