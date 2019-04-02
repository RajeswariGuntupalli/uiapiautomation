package com.test.pageobjects;

import com.test.dataset.DataField;
import com.test.hooks.Hooks;
import com.test.logger.ScenarioLogger;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class LocateUsPage extends CommonPageObjects {
    public static WebDriver edriver;
    public static DataField datafield;

    @FindBy(how = How.LINK_TEXT, using = "Locate us")
    public WebElement locateUs;

    @FindBy(how = How.ID, using = "locatorTextSearch")
    public WebElement locatorTextSearch;
    @FindBy(how = How.XPATH, using = "//span[text()='Search']/parent::button")
    public WebElement locatorSearchBtn;

    public String serviceLocation = "//div[contains(@class, 'locator__results-list')]/descendant::a[contains(text(),'<service_location>')]";

    public LocateUsPage(WebDriver driver) throws Exception {
        super(driver);
        edriver = driver;
        PageFactory.initElements(driver, this);
        setDriver(edriver);
        datafield = Hooks.dataField;
    }

    public void locateService() throws Exception {
        click(locateUs);
        enterNonEmptyValue(locatorTextSearch, datafield.getData("Service Location Search Text"));
        click(locatorSearchBtn);
    }

    public void verifyServiceLocation() throws Exception {
        Assert.assertEquals(getText(StringUtils.replace(serviceLocation, "<service_location>", datafield.getData("Service Centre Name"))),
                datafield.getData("Service Centre Name"));
        ScenarioLogger.addStepLog("Service center result: " + datafield.getData("Service Centre Name") +" is validated");
    }

}
