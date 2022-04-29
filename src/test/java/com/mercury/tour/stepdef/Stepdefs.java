package com.mercury.tour.stepdef;

import com.mercury.tour.excel.ExcelFile;
import com.mercury.tour.pages.HomePage;
import com.mercury.tour.pages.SignInPage;
import io.cucumber.java.AfterAll;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.mercury.tour.stepdef.CloseHook.shouldClose;


public class Stepdefs {

    final private static Logger LOG = LoggerFactory.getLogger(Stepdefs.class);
    public static WebDriver webDriver;
    public static ExcelFile excel;
    private static Wait<WebDriver> waitWorker;
    private int currentRowIndex;

    @AfterAll
    public static void afterAll() throws Exception {
        if (excel != null) excel.close();
        if (shouldClose && webDriver != null) webDriver.close();
    }

    @Given("I navigate to Mercury Tours Home page")
    public void iNavigateToMercuryToursHomePage() {

        WebDriverManager.chromedriver().setup();

        if (webDriver == null) webDriver = new ChromeDriver(); // Open browser if not
        if (waitWorker == null) waitWorker = new WebDriverWait(webDriver, 5); // Create waiter if not

        webDriver.get("https://demo.guru99.com/test/newtours/index.php"); // Open Home page
    }

    @And("I select Sign On at the top menu")
    public void iSelectSignOnAtTheTopMenu() {
        HomePage homePage = new HomePage();
        PageFactory.initElements(webDriver, homePage);

        waitWorker.until(ExpectedConditions.visibilityOf(homePage.signOnButton));
        homePage.signOnButton.click();
    }

    /**
     * Working with Excel file
     */
    @And("I enter credentials from Excel row {int} and click Submit")
    public void iEnterCredentialsAndClickSubmit(int rowIndex) {
        if (excel == null) {
            excel = new ExcelFile("/TestData.xlsx");
        }
        currentRowIndex = rowIndex;

        // reads username and password from Excel file
        String username = excel.getData(0, rowIndex, 1);
        String password = excel.getData(0, rowIndex, 2);

        SignInPage signInPage = new SignInPage();
        PageFactory.initElements(webDriver, signInPage);
        waitWorker.until(ExpectedConditions.visibilityOf(signInPage.submitButton));
        signInPage.userNameField.sendKeys(username);
        signInPage.passwordField.sendKeys(password);
        signInPage.submitButton.click();
    }

    @Then("I see the message {string}")
    public void iSeeTheMessageLoginSuccessfully(String expectedMessage) {
        SignInPage signInPage = new SignInPage();
        PageFactory.initElements(webDriver, signInPage);

        boolean hasError = false; // if there is no error
        try {
            waitWorker.until(ExpectedConditions.visibilityOf(signInPage.successLoginMessage));
            String actualMessage = signInPage.successLoginMessage.getText();
            Assertions.assertThat(actualMessage)
                    .as("actual message should be equal to expected message")
                    .isEqualTo(expectedMessage);
        } catch (Throwable error) {
            LOG.error(error.getLocalizedMessage());
            hasError = true;
        }

        if (hasError) {
            LOG.error("Test failed");
            /* save to excel FAILED */
            excel.setData(0, currentRowIndex, 3, "FAILED");
        } else {
            LOG.info("Test passed");
            /* save to excel PASSED */
            excel.setData(0, currentRowIndex, 3, "PASSED");
        }
    }

    @Given("I save excel results")
    public void iSaveExcelResults() throws Exception {  // why throws Exception?
        excel.save();
    }
}
