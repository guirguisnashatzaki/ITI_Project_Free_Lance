package org.itiprojectindeed.Guirguis;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Home_Navigation_Page extends BasePage{


    private By aboutLink = By.linkText("About");
    private By jobsLink = By.linkText("Jobs");
    private By coursesLink = By.linkText("Courses");
    private String expectedTitle;
    private String actualTitle;

    public String getExpectedTitle() {
        return expectedTitle;
    }

    public String getActualTitle() {
        return actualTitle;
    }

    public Home_Navigation_Page(WebDriver driver) {
        super(driver);
    }

    public boolean verifyHomePageTitle() {
        expectedTitle = "Home | Freelance Yard";
        actualTitle = driver.getTitle();
        if (!actualTitle.equals(expectedTitle)) {
            return false;
        }else{
            return true;
        }
    }

    public boolean navigateToAbout(){
        driver.findElement(aboutLink).click();
        return driver.getCurrentUrl().contains("about");
    }

    public boolean navigateToJobs(){
        driver.findElement(jobsLink).click();
        return driver.getCurrentUrl().contains("jobs");
    }

    public boolean navigateToCourses(){
        driver.findElement(coursesLink).click();
        return driver.getCurrentUrl().contains("courses");
    }
}
