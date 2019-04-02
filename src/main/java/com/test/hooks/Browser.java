package com.test.hooks;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;


public class Browser {

    private static DesiredCapabilities dr = null;
    private static String IE_BROWSER_NAME = "ie";
    private static String FIREFOX_BROWSER_NAME = "firefox";
    private static String CHROME_BROWSER_NAME = "chrome";
    private static String EDGE_BROWSER_NAME = "";

    private String CurrentDirectory;

    public WebDriver setLocalDriver(String browserName) throws Exception {
        CurrentDirectory = System.getProperty("user.dir");
        if (browserName.equalsIgnoreCase(FIREFOX_BROWSER_NAME)) {
            System.setProperty("webdriver.gecko.driver", CurrentDirectory + "\\drivers\\geckodriver.exe");
            FirefoxBinary firefoxBinary = new FirefoxBinary();
            if (Hooks.environmentConfigurations.isHeadlessMode()) firefoxBinary.addCommandLineOptions("--headless");
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.setBinary(firefoxBinary);
            return new FirefoxDriver(firefoxOptions);
        } else if (browserName.equalsIgnoreCase(EDGE_BROWSER_NAME)) {
            System.setProperty("webdriver.chrome.driver", CurrentDirectory + "\\drivers\\MicrosoftWebDriver.exe");
            return new ChromeDriver();
        } else if (browserName.equalsIgnoreCase(IE_BROWSER_NAME)) {
            System.setProperty("webdriver.ie.driver", CurrentDirectory + "\\drivers\\IEDriverServer.exe");
            InternetExplorerOptions options = new InternetExplorerOptions()
                    .introduceFlakinessByIgnoringSecurityDomains()
                    .requireWindowFocus()
                    .destructivelyEnsureCleanSession();
            return new InternetExplorerDriver(options);
        } else {
            if (System.getProperty("webdriver.chrome.driver") == null) {
                System.setProperty("webdriver.chrome.driver", CurrentDirectory + "\\drivers\\chromedriver.exe");
            }
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--disable-web-security");
            if (Hooks.environmentConfigurations.isHeadlessMode()) {
                chromeOptions.addArguments("--headless", "window-size=1920,1080", "--no-sandbox", "--disable-infobars", "--disable-dev-shm-usage", "--disable-browser-side-navigation", "--disable-gpu");
                chromeOptions.setPageLoadStrategy(PageLoadStrategy.NONE);
            }
            return new ChromeDriver(chromeOptions);
        }
    }

}
