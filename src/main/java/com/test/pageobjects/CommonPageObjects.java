package com.test.pageobjects;

import com.test.hooks.Hooks;
import com.test.pageobject.util.PageObjectBase;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class CommonPageObjects extends PageObjectBase {

    public static WebDriver edriver;

    @FindBy(how = How.XPATH, using = "//a[contains(text(),'Allow all cookies')]")
    public WebElement allowCookiesLink;
    @FindBy(how = How.XPATH, using = "//a[contains(text(),'Accept cookies')]")
    public WebElement acceptCookiesLink;

    public CommonPageObjects(WebDriver driver) {
        edriver = driver;
        PageFactory.initElements(driver, this);
        setDriver(edriver);
    }

    public void allowCookies() throws Exception {
        click(allowCookiesLink);
    }

    public void acceptCookies() throws Exception {
        click(acceptCookiesLink);
    }

}
