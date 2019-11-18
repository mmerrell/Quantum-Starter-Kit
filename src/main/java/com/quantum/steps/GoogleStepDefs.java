package com.quantum.steps;

import com.qmetry.qaf.automation.step.QAFTestStepProvider;
import com.qmetry.qaf.automation.ui.WebDriverTestBase;
import com.qmetry.qaf.automation.ui.webdriver.QAFExtendedWebDriver;
import com.qmetry.qaf.automation.ui.webdriver.QAFExtendedWebElement;
import com.quantum.utils.DeviceUtils;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.JavascriptExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@QAFTestStepProvider
public class GoogleStepDefs {
    private Logger log = LoggerFactory.getLogger("GoogleStepDefs");
    @Given("^I am on Google Search Page, looking for (\\w+:\\w+)$")
    public void I_am_on_Google_Search_Page(String testName) {
        WebDriverTestBase base = new WebDriverTestBase();
        QAFExtendedWebDriver driver = base.getDriver();
        driver.executeScript("sauce:job-name=" + testName);
        driver.get("http://www.google.com/");
        log.info("WebDriver SessionID: " + driver.toString());
    }

    @When("^I search for \"([^\"]*)\"$")
    public void I_search_for(String searchKey) {
        QAFExtendedWebElement searchBoxElement = new QAFExtendedWebElement("search.text.box");
        QAFExtendedWebElement searchBtnElement = new QAFExtendedWebElement("search.button");

        searchBoxElement.clear();
        searchBoxElement.sendKeys(searchKey);
        // Web and mobile elements are sometimes different so we have done two things:
        // We used multiple/alternate locator strategy for finding the element.
        // We also used Javascript click because the element was getting hidden in
        // Desktop Web due to suggestions and was not clickable. This java script click
        // will work for both desktop and mobile in this case.
        JavascriptExecutor js = DeviceUtils.getQAFDriver();
        js.executeScript("arguments[0].click();", searchBtnElement);

    }

    @Then("^it should have \"([^\"]*)\" in search results$")
    public void it_should_have_in_search_results(String result) {
        compareSearchResults(result);
    }

    private void compareSearchResults(String result) {
        QAFExtendedWebElement searchResultElement = new QAFExtendedWebElement("partialLink=" + result);
        String isPassed = searchResultElement.verifyPresent(result) ? "passed" : "failed";

        QAFExtendedWebDriver driver = new WebDriverTestBase().getDriver();
        log.info("Logging test result to Sauce: " + isPassed);
        if (driver != null) {
            ((JavascriptExecutor) driver).executeScript("sauce:job-result=" + isPassed);
        }
    }

    @Then("^it should have following search results:$")
    public void it_should_have_all_in_search_results(List<String> results) {
        for (String result : results) {
            compareSearchResults(result);
        }
    }
}
