package com.test.stepdefinitions;

import com.test.hooks.Hooks;
import com.test.pageobjects.LocateUsPage;
import com.test.pageobjects.SearchPage;
import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;

public class UILocateServiceStepDefinition {

    public WebDriver driver;
    public Scenario scenario;
    SearchPage searchPage;
    LocateUsPage locateUsPage;

    public UILocateServiceStepDefinition() {
        driver = Hooks.driver;
        this.scenario = Hooks.scenario;
    }

    @When("^User searches for specific service$")
    public void userSearchesForSpecificServiceAndSelectAServiceByClickingOnItInSearchResultsPage() throws Throwable {
        searchPage = searchPage == null ? new SearchPage(driver) : searchPage;
        searchPage.searchService();
    }

    @Then("^Validates the navigation to appropriate page$")
    public void validatesTheNavigationToAppropriatePage() throws Throwable {
        searchPage = searchPage == null ? new SearchPage(driver) : searchPage;
        searchPage.validateNavigationToSearchResultsPage();
    }

    @And("^User locates service by searching with specific location criteria$")
    public void userLocatesServiceUsingSpecificLocationCriteria() throws Throwable {
        locateUsPage = locateUsPage == null ? new LocateUsPage(driver) : locateUsPage;
        locateUsPage.locateService();
    }

    @Then("^Verifies expected service center is displayed on results page$")
    public void verifiesExpectedServiceCenterIsDisplayedOnResultsPage() throws Throwable {
        locateUsPage = locateUsPage == null ? new LocateUsPage(driver) : locateUsPage;
        locateUsPage.verifyServiceLocation();
    }
}