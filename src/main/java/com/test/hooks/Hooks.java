package com.test.hooks;

import com.test.dataset.DataField;
import com.test.logger.ScenarioLogger;
import com.test.utilities.Constant;
import com.test.utilities.FileSystem;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


public class Hooks {

    public static WebDriver driver;
    public static EnvironmentConfigurations environmentConfigurations;
    public static Properties configProperties;
    public static Scenario scenario;
    public static DataField dataField;
    public static Browser browser = new Browser();

    @Before(order = 1)
    public void openBrowser(Scenario scenario) throws Exception {
        loadProperties();
        this.scenario = scenario;
    }

    public static void initializeDriver() throws Exception{
        try{
            String browserName = environmentConfigurations.getExecutionBrowser();
            driver = browser.setLocalDriver(browserName);
            driver.manage().deleteAllCookies();
            driver.manage().timeouts().implicitlyWait(Constant.IMPLICIT_WAIT_SECONDS, TimeUnit.SECONDS);
            driver.manage().timeouts().pageLoadTimeout(Constant.IMPLICIT_WAIT_SECONDS, TimeUnit.SECONDS);
            driver.manage().timeouts().setScriptTimeout(Constant.IMPLICIT_WAIT_SECONDS, TimeUnit.SECONDS);
            driver.manage().window().maximize();
            driver.get(environmentConfigurations.getApplicationUIUrl());
            driver.manage().deleteAllCookies();
        }catch (TimeoutException te) {
            initializeDriver();
        }
    }

    private String getPropertyValue(String propertyName) throws Exception {
        return StringUtils.isNotEmpty(System.getProperty(propertyName)) ?
                System.getProperty(propertyName) : configProperties.getProperty(propertyName);
    }

    private void loadProperties() throws Exception {
        if (configProperties == null) {
            configProperties = new Properties();
            configProperties.load(new FileInputStream(FileSystem.getResourcePath("config.properties")));
        }
        if (environmentConfigurations == null) {
            environmentConfigurations = new EnvironmentConfigurations();
            environmentConfigurations.setApplicationUIUrl(getPropertyValue("env.application.url"));
            environmentConfigurations.setApiUrl(getPropertyValue("env.weather.api.url"));
            environmentConfigurations.setApiKey(getPropertyValue("env.weather.api.key"));
            environmentConfigurations.setExecutionBrowser(getPropertyValue("test.execution.browser"));
            environmentConfigurations.setHeadlessMode(StringUtils.isEmpty(getPropertyValue("test.execution.isHeadlessMode")) ?
                    false : BooleanUtils.toBoolean(getPropertyValue("test.execution.isHeadlessMode")));
            environmentConfigurations.setScreenshotPath(getPropertyValue("test.failures.screenshot.path"));
        }
    }

    @After(order = 1)
    public void embedScreenshot() throws Exception {
        if (scenario.isFailed()) {
            ScenarioLogger.addStepLog("Current Page URL is " + driver.getCurrentUrl());
            ScenarioLogger.takeFullPageScreenShot(driver);
        }
        try {
            if(driver != null)
                driver.quit();
        } catch (UnreachableBrowserException ube) {
            ScenarioLogger.addScenarioLog("UnreachableBrowserException..." + ube.getMessage());
        }
        catch (Exception e) {
            ScenarioLogger.addScenarioLog("UnreachableBrowserException..." + e.getMessage());
        }
    }

}
