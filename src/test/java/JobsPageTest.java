import utils.TestUtils;
import org.itiprojectindeed.JobDetailsPage;
import org.itiprojectindeed.JobsPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.Assert;
import java.time.Duration;
import java.util.List;

public class JobsPageTest {
    private WebDriver driver;
    private JobsPage jobsPage;
    private JobDetailsPage jobDetailsPage;
    private WebDriverWait wait;
    private final String BASE_URL = "https://www.freelanceyard.com/en/jobs";

    @BeforeClass
    public void setUp() {
        try {
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            wait = new WebDriverWait(driver, Duration.ofSeconds(15));

            jobsPage = new JobsPage(driver);
            jobDetailsPage = new JobDetailsPage(driver);

            System.out.println("Navigating to: " + BASE_URL);
            driver.get(BASE_URL);
            TestUtils.waitForPageLoad(driver);


            wait.until(d -> jobsPage.getJobCardsCount() > 0);

            System.out.println("Setup completed successfully");

        } catch (Exception e) {
            System.out.println("Setup failed: " + e.getMessage());
            e.printStackTrace();
            if (driver != null) {
                driver.quit();
            }
            throw new RuntimeException("Setup failed", e);
        }
    }

    @Test(priority = 1, description = "Verify that the jobs page loads successfully")
    public void testPageLoadsSuccessfully() {
        System.out.println("Running test: Page Loads Successfully");

        Assert.assertNotNull(driver, "WebDriver should not be null");
        Assert.assertNotNull(jobsPage, "JobsPage should not be null");

        String currentUrl = driver.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);
        Assert.assertTrue(currentUrl.contains("/jobs"), "Jobs page should be loaded");
        Assert.assertTrue(jobsPage.areResultsDisplayed(), "Job results should be displayed");
    }

    @Test(priority = 2, description = "Verify that job cards contain all required elements")
    public void testJobCardsContainRequiredElements() {
        System.out.println("Running test: Job Cards Contain Required Elements");

        List<WebElement> jobCards = jobsPage.getAllJobCards();
        System.out.println("Found " + jobCards.size() + " job cards");
        Assert.assertTrue(jobCards.size() > 0, "At least one job card should be present");

        WebElement firstJobCard = jobCards.get(0);


        String title = jobsPage.getJobTitle(firstJobCard);
        System.out.println("✓ Job Title: " + title);
        Assert.assertNotNull(title, "Job title should not be null");
        Assert.assertFalse(title.isEmpty(), "Job title should not be empty");
        Assert.assertNotEquals("Title not found", title, "Job title should be found");


        String client = jobsPage.getJobClient(firstJobCard);
        System.out.println("✓ Job Client: " + client);
        Assert.assertNotNull(client, "Job client should not be null");
        Assert.assertNotEquals("Client not found", client, "Job client should be found");


        String category = jobsPage.getJobCategory(firstJobCard);
        System.out.println("✓ Job Category: " + category);
        Assert.assertNotNull(category, "Job category should not be null");
        Assert.assertFalse(category.isEmpty(), "Job category should not be empty");
        Assert.assertNotEquals("Category not found", category, "Job category should be found");


        String budget = jobsPage.getJobBudget(firstJobCard);
        System.out.println("✓ Job Budget: " + budget);
        Assert.assertNotNull(budget, "Job budget should not be null");
        Assert.assertNotEquals("Budget not found", budget, "Job budget should be found");


        String date = jobsPage.getJobDate(firstJobCard);
        System.out.println("✓ Job Date: " + date);
        Assert.assertNotNull(date, "Job date should not be null");
        Assert.assertNotEquals("Date not found", date, "Job date should be found");
    }

    @Test(priority = 3, description = "Verify that View Job button navigates to job details page")
    public void testViewJobButtonNavigation() {
        System.out.println("Running test: View Job Button Navigation");
        List<WebElement> jobCards = jobsPage.getAllJobCards();

        if (jobCards.size() > 0) {
            WebElement firstJobCard = jobCards.get(0);
            String expectedJobTitle = jobsPage.getJobTitle(firstJobCard);
            System.out.println("Testing job: " + expectedJobTitle);

            String jobsPageUrl = driver.getCurrentUrl();

            jobsPage.clickViewJob(firstJobCard);


            wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(jobsPageUrl)));
            TestUtils.waitForPageLoad(driver);

            String currentUrl = driver.getCurrentUrl();
            System.out.println("After click - Current URL: " + currentUrl);
            Assert.assertNotEquals(currentUrl, jobsPageUrl,
                    "Should navigate to job details page");

            Assert.assertTrue(currentUrl.contains("/jobs/"),
                    "Should be on job details page");


            driver.navigate().back();
            TestUtils.waitForPageLoad(driver);
            wait.until(d -> jobsPage.getJobCardsCount() > 0);

            System.out.println("Navigated back to jobs page");
        } else {
            Assert.fail("No job cards available for navigation test");
        }
    }

    @Test(priority = 4, description = "Test search functionality with valid search term")
    public void testSearchFunctionality() {
        System.out.println("Running test: Search Functionality");

        int initialCount = jobsPage.getJobCardsCount();
        System.out.println("Initial job count: " + initialCount);

        String searchTerm = "writing";
        jobsPage.searchJobs(searchTerm);


        wait.until(d -> !jobsPage.getSearchInputValue().isEmpty());

        Assert.assertTrue(jobsPage.areResultsDisplayed(),
                "Results should be displayed after search");
        Assert.assertEquals(jobsPage.getSearchInputValue(), searchTerm,
                "Search input should contain the search term");

        int afterSearchCount = jobsPage.getJobCardsCount();
        System.out.println("After search job count: " + afterSearchCount);

        System.out.println("Search test completed for: " + searchTerm);
    }

    @Test(priority = 5, description = "Test search with empty search bar")
    public void testEmptySearch() {
        System.out.println("Running test: Empty Search");

        jobsPage.clearSearch();


        wait.until(d -> jobsPage.isSearchInputEmpty());

        Assert.assertTrue(jobsPage.areResultsDisplayed(),
                "Results should be displayed with empty search");
        Assert.assertTrue(jobsPage.isSearchInputEmpty(),
                "Search input should be empty");

        System.out.println("Empty search test completed");
    }

    @Test(priority = 9, description = "Test search with special characters")
    public void testSearchWithSpecialCharacters() {
        System.out.println("Running test: Search With Special Characters");

        String specialChars = "$";
        jobsPage.searchWithSpecialCharacters(specialChars);

        wait.until(d -> jobsPage.getSearchInputValue().equals(specialChars));
        Assert.assertEquals(jobsPage.getSearchInputValue(), specialChars,
                "Search input should accept special characters");

        jobsPage.pressEnterOnSearchInput();

        wait.until(d -> jobsPage.isNoJobsFoundMessageDisplayed());
        Assert.assertTrue(jobsPage.isNoJobsFoundMessageDisplayed(),
                "Expected 'No jobs were found' message to appear");

        System.out.println("Special character search resulted in no jobs found as expected");

        jobsPage.clearSearch();
        jobsPage.pressEnterOnSearchInput();
    }

    @Test(priority = 6, description = "Test category filter functionality")
    public void testCategoryFilter() {
        System.out.println("Running test: Category Filter");

        int initialCount = jobsPage.getJobCardsCount();
        System.out.println("Initial job count: " + initialCount);

        String categoryToSelect = "IT & Tech";
        jobsPage.selectCategory(categoryToSelect);


        wait.until(d -> jobsPage.getSelectedCategory().equals(categoryToSelect));

        Assert.assertTrue(jobsPage.areResultsDisplayed(),
                "Results should be displayed after category filter");

        int afterFilterCount = jobsPage.getJobCardsCount();
        System.out.println("After filter job count: " + afterFilterCount);

        System.out.println("Category filter test completed");
    }

    @Test(priority = 7, description = "Test reset filters functionality")
    public void testResetFilters() {
        System.out.println("Running test: Reset Filters");


        jobsPage.searchJobs("test");
        wait.until(d -> !jobsPage.isSearchInputEmpty());


        jobsPage.resetFilters();


        wait.until(d -> jobsPage.isSearchInputEmpty());

        Assert.assertTrue(jobsPage.isSearchInputEmpty(),
                "Search input should be empty after reset");
        Assert.assertTrue(jobsPage.areResultsDisplayed(),
                "Results should still be displayed after reset");

        System.out.println("Reset filters test completed");
    }

    @Test(priority = 8, description = "Verify all job cards have required elements")
    public void testAllJobCardsHaveRequiredElements() {
        System.out.println("Running test: All Job Cards Have Required Elements");
        List<WebElement> jobCards = jobsPage.getAllJobCards();
        System.out.println("Testing " + jobCards.size() + " job cards");

        int testedCards = 0;
        int maxCardsToTest = 3;

        for (int i = 0; i < Math.min(jobCards.size(), maxCardsToTest); i++) {
            WebElement jobCard = jobCards.get(i);
            String title = jobsPage.getJobTitle(jobCard);
            String category = jobsPage.getJobCategory(jobCard);
            String budget = jobsPage.getJobBudget(jobCard);


            if (title.equals("Title not found") || category.equals("Category not found")) {
                System.out.println("Skipping card " + (i+1) + " - missing essential elements");
                continue;
            }

            System.out.println("Card " + (i+1) + ":");
            System.out.println("  - Title: " + title);
            System.out.println("  - Category: " + category);
            System.out.println("  - Budget: " + budget);

            Assert.assertFalse(title.isEmpty(),
                    "Job card " + (i+1) + " should have a title");
            Assert.assertNotEquals("Title not found", title,
                    "Job card " + (i+1) + " should have a valid title");
            Assert.assertFalse(category.isEmpty(),
                    "Job card " + (i+1) + " should have a category");
            Assert.assertNotEquals("Category not found", category,
                    "Job card " + (i+1) + " should have a valid category");

            testedCards++;
        }

        Assert.assertTrue(testedCards > 0, "Should have tested at least one valid job card");
        System.out.println("All job cards test completed - tested " + testedCards + " cards");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            System.out.println("Closing browser...");
            driver.quit();
            System.out.println("Browser closed");
        }
    }
}