package com.test.pageobjects;

import com.test.dataset.DataField;
import com.test.hooks.Hooks;
import com.test.logger.ScenarioLogger;
import com.test.pageobject.util.PageObjectBase;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class SearchPage extends CommonPageObjects {
    public static WebDriver edriver;
    public static DataField datafield;

    @FindBy(how = How.XPATH, using = "//input[contains(@placeholder,'Start your search here')]")
    public WebElement searchInput;
    @FindBy(how = How.ID, using = "edit-submit-site-search")
    public WebElement searchSubmitBtn;
    @FindBy(how = How.XPATH, using = "//h1[text()='Search Results']")
    public WebElement searchResultsHeader;

    public SearchPage(WebDriver driver) throws Exception {
        super(driver);
        edriver = driver;
        PageFactory.initElements(driver, this);
        setDriver(edriver);
        datafield = Hooks.dataField;
    }

    public void searchService() throws Exception {
        enterNonEmptyValue(searchInput, datafield.getData("Service Name"));
        click(searchSubmitBtn);
        waitUntilElementIsVisible(searchResultsHeader);
    }

    public void validateNavigationToSearchResultsPage() throws Exception{
        Assert.assertTrue(StringUtils.contains(edriver.getCurrentUrl(), Hooks.environmentConfigurations.getApplicationUIUrl()+"search-results?contains="
                + StringUtils.replace(datafield.getData("Service Name"), " ", "+")), "Page is not navigated to search results page");
        ScenarioLogger.addStepLog("Navigation to Search result page is validated");
    }

}
