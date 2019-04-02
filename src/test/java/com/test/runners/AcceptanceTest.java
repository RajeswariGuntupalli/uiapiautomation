package com.test.runners;

import com.cucumber.listener.Reporter;
import com.test.utilities.FileSystem;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.AfterClass;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)

@CucumberOptions(plugin = "com.cucumber.listener.ExtentCucumberFormatter:target/cucumber-reports/report.html",
        features = {"src/test/resources"},
        glue = {"com.test"})
public class AcceptanceTest {
    @AfterClass
    public static void setup() throws Exception{
        Reporter.loadXMLConfig(FileSystem.getResourcePath("extent-config.xml"));
    }
}

