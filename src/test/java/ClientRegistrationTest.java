import org.itiprojectindeed.ClientRegisterPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ClientRegistrationTest extends BaseTest {
    private ClientRegisterPage registerPage;

    @BeforeMethod
    public void initializePage() {
        registerPage = new ClientRegisterPage(driver);
        registerPage.navigateToPage();
    }



    @Test(priority = 1, description = "TC01: Verify page loads successfully")
    public void testPageLoad() {
        Assert.assertTrue(registerPage.isPageLoaded(),
                "Registration page should load successfully");
    }

    @Test(priority = 2, description = "TC02: Valid registration with Startup type")
    public void testValidRegistrationStartup() {
        registerPage.enterCompanyName("InnovateX");
        registerPage.enterFirstName("Laila");
        registerPage.enterLastName("Hussein");
        registerPage.enterEmail("laila." + System.currentTimeMillis() + "@innovatex.com");
        registerPage.enterPassword("Startup@1234");
        registerPage.selectCountry("Egypt");
        registerPage.selectPhoneCodeByCountryName("Egypt");
        registerPage.enterLocalPhone("01123954455");
        registerPage.selectCompanyType("Startup");
        registerPage.clickRegisterButton();
        registerPage.waitForErrorMessages();

        Assert.assertFalse(registerPage.isErrorMessageDisplayed(),
                "No errors should appear with valid data");
    }

    @Test(priority = 3, description = "TC03: Valid registration with Corporate type")
    public void testValidRegistrationCorporate() {
        registerPage.enterCompanyName("AlphaTech Corp");
        registerPage.enterFirstName("Omar");
        registerPage.enterLastName("Farouk");
        registerPage.enterEmail("omar." + System.currentTimeMillis() + "@alphatech.com");
        registerPage.enterPassword("Corp@5678");
        registerPage.selectCountry("United Arab Emirates");
        registerPage.selectPhoneCode("971");
        registerPage.enterLocalPhone("519786543");
        registerPage.selectCompanyType("Corporate");
        registerPage.clickRegisterButton();
        registerPage.waitForErrorMessages();

        Assert.assertFalse(registerPage.isErrorMessageDisplayed(),
                "No errors should appear for Corporate type");
    }

    @Test(priority = 4, description = "TC04: Valid registration with NGO type")
    public void testValidRegistrationNgo() {
        registerPage.enterCompanyName("Hope Foundation NGO");
        registerPage.enterFirstName("Sara");
        registerPage.enterLastName("Mahmoud");
        registerPage.enterEmail("sara." + System.currentTimeMillis() + "@hopefoundation.org");
        registerPage.enterPassword("Ngo@9876");
        registerPage.selectCountry("Saudi Arabia");
        registerPage.selectPhoneCode("966");
        registerPage.enterLocalPhone("511212233");
        registerPage.selectCompanyType("Ngo");
        registerPage.clickRegisterButton();
        registerPage.waitForErrorMessages();

        Assert.assertFalse(registerPage.isErrorMessageDisplayed(),
                "No errors should appear for NGO type");
    }

    @Test(priority = 5, description = "TC05: Valid registration with Individual type")
    public void testValidRegistrationIndividual() {
        registerPage.enterCompanyName("Freelance World");
        registerPage.enterFirstName("Nour");
        registerPage.enterLastName("Ali");
        registerPage.enterEmail("nour." + System.currentTimeMillis() + "@freelanceworld.com");
        registerPage.enterPassword("Individual@456");
        registerPage.selectCountry("Kuwait");
        registerPage.selectPhoneCode("965");
        registerPage.enterLocalPhone("997269655");
        registerPage.selectCompanyType("Individual");
        registerPage.clickRegisterButton();
        registerPage.waitForErrorMessages();

        Assert.assertFalse(registerPage.isErrorMessageDisplayed(),
                "No errors should appear for Individual type");
    }

    @Test(priority = 6, description = "TC06: Valid registration with Social Enterprise type")
    public void testValidRegistrationSocialEnterprise() {
        registerPage.enterCompanyName("Green Impact Solutions");
        registerPage.enterFirstName("Omar");
        registerPage.enterLastName("Sami");
        registerPage.enterEmail("omar." + System.currentTimeMillis() + "@greenimpact.org");
        registerPage.enterPassword("Social@2025");
        registerPage.selectCountry("Qatar");
        registerPage.selectPhoneCode("974");
        registerPage.enterLocalPhone("44352211");
        registerPage.selectCompanyType("Social Enterprise");
        registerPage.clickRegisterButton();
        registerPage.waitForErrorMessages();

        Assert.assertFalse(registerPage.isErrorMessageDisplayed(),
                "No errors should appear for Social Enterprise type");
    }


    @Test(priority = 10, description = "TC10: Submit empty form - validate all required fields")
    public void testEmptyFormSubmission() {
        registerPage.clickRegisterButton();
        registerPage.waitForErrorMessages();

        Assert.assertTrue(registerPage.isErrorMessageDisplayed(),
                "Error messages should appear for empty form");

        int errorCount = registerPage.getAllErrorMessages().size();
        System.out.println("Empty form validation: " + errorCount + " errors displayed");

        Assert.assertTrue(errorCount > 0,
                "Should display multiple error messages. Found: " + errorCount);
    }

    @Test(priority = 11, description = "TC11: Missing company name field")
    public void testMissingCompanyName() {
        registerPage.enterFirstName("Ali");
        registerPage.enterLastName("Hassan");
        registerPage.enterEmail("ali." + System.currentTimeMillis() + "@example.com");
        registerPage.enterPassword("Pass@1234");
        registerPage.selectCountry("Egypt");
        registerPage.selectPhoneCodeByCountryName("Egypt");
        registerPage.enterLocalPhone("1212188212");
        registerPage.selectCompanyType("Startup");
        registerPage.clickRegisterButton();
        registerPage.waitForErrorMessages();

        Assert.assertTrue(registerPage.isCompanyNameErrorDisplayed(),
                "Company name error should be displayed");
    }

    @Test(priority = 12, description = "TC12: Missing first name field")
    public void testMissingFirstName() {
        registerPage.enterCompanyName("Alpha Solutions");
        registerPage.enterLastName("Sami");
        registerPage.enterEmail("alpha." + System.currentTimeMillis() + "@example.com");
        registerPage.enterPassword("Alpha@1234");
        registerPage.selectCountry("Egypt");
        registerPage.selectPhoneCodeByCountryName("Egypt");
        registerPage.enterLocalPhone("1113531313");
        registerPage.selectCompanyType("Corporate");
        registerPage.clickRegisterButton();
        registerPage.waitForErrorMessages();

        Assert.assertTrue(registerPage.isFirstNameErrorDisplayed(),
                "First name error should be displayed");
    }

    @Test(priority = 13, description = "TC13: Missing last name field")
    public void testMissingLastName() {
        registerPage.enterCompanyName("Beta Tech");
        registerPage.enterFirstName("Mona");
        registerPage.enterEmail("beta." + System.currentTimeMillis() + "@example.com");
        registerPage.enterPassword("Beta@1234");
        registerPage.selectCountry("Egypt");
        registerPage.selectPhoneCodeByCountryName("Egypt");
        registerPage.enterLocalPhone("1414191914");
        registerPage.selectCompanyType("Ngo");
        registerPage.clickRegisterButton();
        registerPage.waitForErrorMessages();

        Assert.assertTrue(registerPage.isLastNameErrorDisplayed(),
                "Last name error should be displayed");
    }

    @Test(priority = 14, description = "TC14: Invalid email format - missing domain")
    public void testInvalidEmailFormat() {
        registerPage.enterCompanyName("Delta Corp");
        registerPage.enterFirstName("Hassan");
        registerPage.enterLastName("Omar");
        registerPage.enterEmail("invalid-email-delta");
        registerPage.enterPassword("Delta@1234");
        registerPage.selectCountry("Egypt");
        registerPage.selectPhoneCodeByCountryName("Egypt");
        registerPage.enterLocalPhone("1515151919");
        registerPage.selectCompanyType("Individual");
        registerPage.clickRegisterButton();
        registerPage.waitForErrorMessages();

        Assert.assertTrue(registerPage.isEmailErrorDisplayed(),
                "Email error should be displayed for invalid format");
    }

    @Test(priority = 15, description = "TC15: Missing email field")
    public void testMissingEmail() {
        registerPage.enterCompanyName("Epsilon Tech");
        registerPage.enterFirstName("Fatima");
        registerPage.enterLastName("Khaled");
        registerPage.enterPassword("Epsilon@123");
        registerPage.selectCountry("Egypt");
        registerPage.selectPhoneCodeByCountryName("Egypt");
        registerPage.enterLocalPhone("1617161716");
        registerPage.selectCompanyType("Startup");
        registerPage.clickRegisterButton();
        registerPage.waitForErrorMessages();

        Assert.assertTrue(registerPage.isEmailErrorDisplayed(),
                "Email error should be displayed when field is empty");
    }

    @Test(priority = 16, description = "TC16: Weak password - too short")
    public void testWeakPasswordTooShort() {
        registerPage.enterCompanyName("Zeta Solutions");
        registerPage.enterFirstName("Omar");
        registerPage.enterLastName("Sami");
        registerPage.enterEmail("zeta." + System.currentTimeMillis() + "@example.com");
        registerPage.enterPassword("12");
        registerPage.selectCountry("Egypt");
        registerPage.selectPhoneCodeByCountryName("Egypt");
        registerPage.enterLocalPhone("1717173727");
        registerPage.selectCompanyType("Corporate");
        registerPage.clickRegisterButton();
        registerPage.waitForErrorMessages();

        Assert.assertTrue(registerPage.isPasswordErrorDisplayed(),
                "Password error should be displayed for weak password");
    }

    @Test(priority = 17, description = "TC17: Missing password field")
    public void testMissingPassword() {
        registerPage.enterCompanyName("Theta Labs");
        registerPage.enterFirstName("Mona");
        registerPage.enterLastName("Ali");
        registerPage.enterEmail("theta." + System.currentTimeMillis() + "@example.com");
        registerPage.selectCountry("Egypt");
        registerPage.selectPhoneCodeByCountryName("Egypt");
        registerPage.enterLocalPhone("1818581819");
        registerPage.selectCompanyType("Ngo");
        registerPage.clickRegisterButton();
        registerPage.waitForErrorMessages();

        Assert.assertTrue(registerPage.isPasswordErrorDisplayed(),
                "Password error should be displayed when field is empty");
    }

    @Test(priority = 18, description = "TC18: Missing phone number field")
    public void testMissingPhoneNumber() {
        registerPage.enterCompanyName("Iota Solutions");
        registerPage.enterFirstName("Sara");
        registerPage.enterLastName("Mahmoud");
        registerPage.enterEmail("iota." + System.currentTimeMillis() + "@example.com");
        registerPage.enterPassword("Iota@1234");
        registerPage.selectCountry("Egypt");
        registerPage.selectPhoneCodeByCountryName("Egypt");
        registerPage.selectCompanyType("Individual");
        registerPage.clickRegisterButton();
        registerPage.waitForErrorMessages();

        Assert.assertTrue(registerPage.isPhoneErrorDisplayed(),
                "Phone error should be displayed when phone number is missing");
    }

    @Test(priority = 19, description = "TC19: Missing company type selection")
    public void testMissingCompanyType() {
        registerPage.enterCompanyName("Kappa Tech");
        registerPage.enterFirstName("John");
        registerPage.enterLastName("Doe");
        registerPage.enterEmail("kappa." + System.currentTimeMillis() + "@example.com");
        registerPage.enterPassword("Kappa@1234");
        registerPage.selectCountry("Egypt");
        registerPage.selectPhoneCodeByCountryName("Egypt");
        registerPage.enterLocalPhone("1919195719");
        registerPage.clickRegisterButton();
        registerPage.waitForErrorMessages();

        Assert.assertTrue(registerPage.isCompanyTypeErrorDisplayed(),
                "Company type error should be displayed when not selected");
    }

    @Test(priority = 20, description = "TC20: Verify all company types are selectable")
    public void testAllCompanyTypesSelectable() {
        String[] companyTypes = {"Startup", "Corporate", "Ngo", "Individual", "Social Enterprise"};

        for (String type : companyTypes) {
            driver.navigate().refresh();
            registerPage.enterCompanyName("Test " + type + " Co");
            registerPage.enterFirstName("Test");
            registerPage.enterLastName("User");
            registerPage.enterEmail("test." + type.toLowerCase().replace(" ", "") +
                    System.currentTimeMillis() + "@example.com");
            registerPage.enterPassword("TestPass@2025");
            registerPage.selectCountry("Egypt");
            registerPage.selectPhoneCodeByCountryName("Egypt");
            registerPage.enterLocalPhone("1231231724");

            try {
                registerPage.selectCompanyType(type);
                System.out.println("Successfully selected company type: " + type);
            } catch (Exception e) {
                Assert.fail("Failed to select company type: " + type + " - " + e.getMessage());
            }
        }
    }


    @Test(priority = 22, description = "TC22: Invalid email - missing @ symbol")
    public void testEmailWithoutAtSymbol() {
        registerPage.enterCompanyName("Lambda Tech");
        registerPage.enterFirstName("Ali");
        registerPage.enterLastName("Hassan");
        registerPage.enterEmail("testlambda.com");
        registerPage.enterPassword("Lambda@1234");
        registerPage.selectCountry("Egypt");
        registerPage.selectPhoneCodeByCountryName("Egypt");
        registerPage.enterLocalPhone("01113631613");
        registerPage.selectCompanyType("Startup");
        registerPage.clickRegisterButton();
        registerPage.waitForErrorMessages();

        Assert.assertTrue(registerPage.isEmailErrorDisplayed(),
                "Email error should be displayed for missing @ symbol");
    }

    @Test(priority = 23, description = "TC23: Invalid email - double @ symbols")
    public void testEmailWithDoubleAtSymbol() {
        registerPage.enterCompanyName("Mu Tech");
        registerPage.enterFirstName("Sara");
        registerPage.enterLastName("Ali");
        registerPage.enterEmail("mu@@example.com");
        registerPage.enterPassword("MuTech@123");
        registerPage.selectCountry("Egypt");
        registerPage.selectPhoneCodeByCountryName("Egypt");
        registerPage.enterLocalPhone("01414641614");
        registerPage.selectCompanyType("Corporate");
        registerPage.clickRegisterButton();
        registerPage.waitForErrorMessages();

        Assert.assertTrue(registerPage.isEmailErrorDisplayed(),
                "Email error should be displayed for double @ symbols");
    }

    @Test(priority = 24, description = "TC24: Weak password - only numbers")
    public void testPasswordOnlyNumbers() {
        registerPage.enterCompanyName("Nu Solutions");
        registerPage.enterFirstName("Omar");
        registerPage.enterLastName("Sami");
        registerPage.enterEmail("nu." + System.currentTimeMillis() + "@example.com");
        registerPage.enterPassword("12345678");
        registerPage.selectCountry("Egypt");
        registerPage.selectPhoneCodeByCountryName("Egypt");
        registerPage.enterLocalPhone("1515651565");
        registerPage.selectCompanyType("Ngo");
        registerPage.clickRegisterButton();
        registerPage.waitForErrorMessages();

        Assert.assertTrue(registerPage.isPasswordErrorDisplayed(),
                "Password error should be displayed for numbers-only password");
    }


    @Test(priority = 26, description = "TC26: Test phone code selection by country name method")
    public void testPhoneCodeSelectionByCountryName() {
        registerPage.enterCompanyName("Omicron Solutions");
        registerPage.enterFirstName("Huda");
        registerPage.enterLastName("Ali");
        registerPage.enterEmail("omicron." + System.currentTimeMillis() + "@example.com");
        registerPage.enterPassword("Omicron@123");
        registerPage.selectCountry("Egypt");

        try {
            registerPage.selectPhoneCodeByCountryName("Egypt");
            registerPage.enterLocalPhone("1717271617");
            registerPage.selectCompanyType("Startup");
            System.out.println("Phone code selected successfully by country name");
        } catch (Exception e) {
            Assert.fail("Failed to select phone code by country name: " + e.getMessage());
        }
    }

    @Test(priority = 27, description = "TC27: Test invalid phone number with letters")
    public void testInvalidPhoneNumberWithLetters() {
        registerPage.enterCompanyName("Pi Tech");
        registerPage.enterFirstName("Amal");
        registerPage.enterLastName("Hassan");
        registerPage.enterEmail("pi." + System.currentTimeMillis() + "@example.com");
        registerPage.enterPassword("Pi@1234");
        registerPage.selectCountry("Egypt");
        registerPage.selectPhoneCodeByCountryName("Egypt");
        registerPage.enterLocalPhone("abc123xyz");
        registerPage.selectCompanyType("Startup");
        registerPage.clickRegisterButton();
        registerPage.waitForErrorMessages();

        Assert.assertTrue(registerPage.isPhoneErrorDisplayed() ||
                        registerPage.isErrorMessageDisplayed(),
                "Phone error should be displayed for invalid phone with letters");
    }

    @Test(priority = 28, description = "TC28: Test invalid phone number with special characters")
    public void testInvalidPhoneNumberWithSpecialChars() {
        registerPage.enterCompanyName("Rho Tech");
        registerPage.enterFirstName("Omar");
        registerPage.enterLastName("Sami");
        registerPage.enterEmail("rho." + System.currentTimeMillis() + "@example.com");
        registerPage.enterPassword("Rho@1234");
        registerPage.selectCountry("Egypt");
        registerPage.selectPhoneCodeByCountryName("Egypt");
        registerPage.enterLocalPhone("12-64-56-58");
        registerPage.selectCompanyType("Corporate");
        registerPage.clickRegisterButton();
        registerPage.waitForErrorMessages();

        Assert.assertTrue(registerPage.isPhoneErrorDisplayed() ||
                        registerPage.isErrorMessageDisplayed(),
                "Phone error should be displayed for phone with special characters");
    }

    @Test(priority = 29, description = "TC29: Test complete error messages display")
    public void testCompleteErrorMessagesDisplay() {
        registerPage.clickRegisterButton();
        registerPage.waitForErrorMessages();

        java.util.List<String> allErrors = registerPage.getAllErrorMessages();
        int errorCount = allErrors.size();

        System.out.println("=== Complete Form Validation Errors ===");
        System.out.println("Total errors displayed: " + errorCount);
        for (int i = 0; i < allErrors.size(); i++) {
            System.out.println((i + 1) + ". " + allErrors.get(i));
        }

        Assert.assertTrue(errorCount >= 7,
                "Should have at least 7 required field errors. Got: " + errorCount);
    }

    @Test(priority = 30, description = "TC30: Test specific error message checks")
    public void testSpecificErrorMessageChecks() {
        registerPage.clickRegisterButton();
        registerPage.waitForErrorMessages();

        System.out.println("=== Checking Specific Error Messages ===");
        System.out.println("Company Name Error: " + registerPage.isCompanyNameErrorDisplayed());
        System.out.println("First Name Error: " + registerPage.isFirstNameErrorDisplayed());
        System.out.println("Last Name Error: " + registerPage.isLastNameErrorDisplayed());
        System.out.println("Email Error: " + registerPage.isEmailErrorDisplayed());
        System.out.println("Password Error: " + registerPage.isPasswordErrorDisplayed());
        System.out.println("Country Error: " + registerPage.isCountryErrorDisplayed());
        System.out.println("Phone Error: " + registerPage.isPhoneErrorDisplayed());
        System.out.println("Company Type Error: " + registerPage.isCompanyTypeErrorDisplayed());

        Assert.assertTrue(registerPage.isCompanyNameErrorDisplayed(),
                "Company name error should be displayed");
        Assert.assertTrue(registerPage.isEmailErrorDisplayed(),
                "Email error should be displayed");
        Assert.assertTrue(registerPage.isPasswordErrorDisplayed(),
                "Password error should be displayed");
    }
}