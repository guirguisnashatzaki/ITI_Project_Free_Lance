import org.itiprojectindeed.Login_Page;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Login_Test extends BaseTest {

    Login_Page loginPage;

    @BeforeClass
    public void setupPageObjects() {
        loginPage = new Login_Page(driver);
    }

    @BeforeMethod
    public void navigateToLoginPage() {
        driver.get("https://www.freelanceyard.com/en/login");
    }

        @Test(priority = 1)
        public void verifyLoginPageElements() {
            Assert.assertTrue(loginPage.checkDisplaying(), "One of the fields not visible");
        }

        @Test(priority = 2)
        public void loginWithEmptyFields() {
            // Expect validation messages (if any client-side)
            Assert.assertTrue(loginPage.loginWithEmptyFields());
        }

        @Test(priority = 3)
        public void loginWithInvalidCredentials() {
            Assert.assertTrue(loginPage.loginWithInvalidCredentials("wrong@example.com", "wrongpass"));
        }

        @Test(priority = 4)
        public void verifyForgotPasswordLink() {
            Assert.assertTrue(loginPage.verifyForgotPasswordLink());
            driver.navigate().back();
        }

        @Test(priority = 5)
        public void loginWithValidCredentials() {
            Assert.assertTrue(loginPage.LoginWithValidCredentials("gogonashatzaki@gmail.com","Gogo_5401519").equals("Processing..."),"Sending the otp");
        }

}
