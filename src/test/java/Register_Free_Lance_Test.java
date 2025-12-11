import org.itiprojectindeed.Register_Free_Lance_Page;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Register_Free_Lance_Test extends BaseTest{

    Register_Free_Lance_Page registerFreeLancePage;

    @BeforeClass
    public void setupPageObjects() {
        registerFreeLancePage = new Register_Free_Lance_Page(driver);
    }
    @BeforeMethod
    public void navigateToRegisterPage() {
        driver.get("https://www.freelanceyard.com/en/register/freelancer");
    }

    @Test(priority = 1)
    public void verifyPageElements() {
        Assert.assertTrue(registerFreeLancePage.verifyPageElements());
    }

    @Test(priority = 2)
    public void registerWithEmptyFields() {
        driver.findElement(By.xpath("//button[contains(text(),'Register')]")).click();
        Assert.assertTrue(registerFreeLancePage.registerWithEmptyFields());
    }

    @Test
    public void registerWithInvalidEmail(){
        Assert.assertTrue(registerFreeLancePage.registerWithInvalidEmail("Gogo","Nashatzaki","email","Gogo_5401519","0123456789","Male","Egypt"));
    }

    @Test(priority = 4)
    public void registerWithValidData() {
        Assert.assertFalse(registerFreeLancePage.registerWithValidData());
    }

    @Test
    public void registerWithMissingFirstNameData() {
        Assert.assertTrue(registerFreeLancePage.registerWithMissingFirstNameData());
    }

    @Test
    public void registerWithMissingLastNameData() {
        Assert.assertTrue(registerFreeLancePage.registerWithMissingLastNameData());
    }

    @Test
    public void registerWithMissingMobileNumberData() {
        Assert.assertTrue(registerFreeLancePage.registerWithMissingMobileNumberData());
    }

    @Test
    public void registerWithMissingPasswordData() {
        Assert.assertTrue(registerFreeLancePage.registerWithMissingPasswordData());
    }

    @Test
    public void registerWithWeakPasswordData() {
        Assert.assertTrue(registerFreeLancePage.registerWithWeakPasswordData());
    }

    @Test
    public void registerWithInvalidPhoneData() {
        Assert.assertTrue(registerFreeLancePage.registerWithInvalidPhoneData());
    }

    @Test
    public void registerWithGenderEmptyData() {
        Assert.assertTrue(registerFreeLancePage.registerWithGenderEmptyData());
    }

    @Test
    public void registerWithCountryEmptyData() {
        Assert.assertTrue(registerFreeLancePage.registerWithCountryEmptyData());
    }

    @Test
    public void registerWithRegisteredEmailData() {
        Assert.assertTrue(registerFreeLancePage.registerWithRegisteredEmailData());
    }

}
