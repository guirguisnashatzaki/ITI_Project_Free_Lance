package Guirguis;

import org.itiprojectindeed.Guirguis.Home_Navigation_Page;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Home_Navigation_Test extends BaseTest{
    Home_Navigation_Page homeNavigationPage;


    @BeforeClass
    public void setupPage(){
        homeNavigationPage = new Home_Navigation_Page(driver);
    }

    @BeforeMethod
    public void navigateToHomePage() {
        driver.get("https://www.freelanceyard.com/en/home");
    }

    @Test
    public void verifyHomePageTitle() {
        assert homeNavigationPage.verifyHomePageTitle() : "Title mismatch! Expected: " + homeNavigationPage.getExpectedTitle() + ", but got: " + homeNavigationPage.getActualTitle();
    }

    @Test
    public void verifyNavigateToAbout(){
        boolean check = homeNavigationPage.navigateToAbout();
        assert check : "Navigation to About page failed!";
    }

    @Test
    public void verifyNavigateToJobs(){
        boolean check = homeNavigationPage.navigateToJobs();
        assert check : "Navigation to Jobs page failed!";
    }

    @Test
    public void verifyNavigateToCourses(){
        boolean check = homeNavigationPage.navigateToCourses();
        assert check : "Navigation to Courses page failed!";
    }

}
