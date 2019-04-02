package com.test.stepdefinitions;

import com.test.hooks.Hooks;
import com.test.pageobjects.LocateUsPage;
import com.test.pageobjects.SearchPage;
import cucumber.api.Scenario;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;

public class APIStepDefinition {

    public WebDriver driver;
    public Scenario scenario;

    public APIStepDefinition() {
        driver = Hooks.driver;
        this.scenario = Hooks.scenario;
    }
}