package com.test.pageobject.util;

import com.test.dataset.DataField;
import com.test.dataset.ExcelUtils;
import com.test.utilities.Constant;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;


public abstract class PageObjectBase {
    private static final Logger logger = Logger.getLogger(PageObjectBase.class);

    public static String MethodName = Thread.currentThread().getStackTrace()[2].getMethodName();
    public static String ClassName = Thread.currentThread().getStackTrace()[2].getClassName();
    public static WebDriver edriver;
    private static WebDriver jsWaitDriver;
    private static WebDriverWait jsWait;
    private static JavascriptExecutor jsExec;
    public ExcelUtils resultPool = null;
    public DataField resultDfInputs = null;
    public DataField resultDfOutputs = null;

    public PageObjectBase() {

    }

    public static void setDriver(WebDriver driver) {
        edriver = driver;
        jsWaitDriver = driver;
        jsWait = new WebDriverWait(jsWaitDriver, Constant.WAIT_TIMEOUT_SECONDS);
        jsExec = (JavascriptExecutor) jsWaitDriver;
    }

    public static void waitUntilElementAttributeContainsValue(WebElement element, String attribute, String value) throws Exception {
        waitJQueryAngular();
        try {
            (new WebDriverWait(edriver, 20L)).until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver d) {
                    return Boolean.valueOf(StringUtils.contains(element.getAttribute(attribute), value));
                }
            });
        }catch (StaleElementReferenceException ex){
            Thread.sleep(2000);
            waitUntilElementAttributeContainsValue(element, attribute, value);
        }

    }

    public static void waitUntilElementIsVisible(WebElement element) throws Exception {
        waitJQueryAngular();
        waitWhileBrowserIsBusy();
        try {
            new WebDriverWait(edriver, Constant.WAIT_TIMEOUT_SECONDS)
                    .until(ExpectedConditions.visibilityOf(element));
        }catch (StaleElementReferenceException ex){
            Thread.sleep(2000);
            waitUntilElementIsVisible(element);
        }
    }

    public static void waitUntilElementIsVisible(WebElement element, long timeout) throws Exception {
        waitWhileBrowserIsBusy();
        try {
            new WebDriverWait(edriver, timeout)
                    .until(ExpectedConditions.visibilityOf(element));
        }catch (StaleElementReferenceException ex){
            Thread.sleep(2000);
            waitUntilElementIsVisible(element, timeout);
        }
    }

    public static void waitUntilElementIsVisible(String path) throws Exception {
        waitJQueryAngular();
        waitWhileBrowserIsBusy();
        try {
            WebElement element = edriver.findElement(By.xpath(path));
            new WebDriverWait(edriver, Constant.WAIT_TIMEOUT_SECONDS)
                    .until(ExpectedConditions.visibilityOf(element));
        }catch (StaleElementReferenceException ex){
            Thread.sleep(2000);
            waitUntilElementIsVisible(path);
        }
    }

    public static void waitUntilElementIsInVisible(WebElement element, long timeout) throws Exception {
        waitWhileBrowserIsBusy();
        try {
            new WebDriverWait(edriver, timeout)
                    .until(ExpectedConditions.invisibilityOf(element));
        }catch (Exception ex){
        }
    }

    public static void waitUntilElementIsInVisible(String xpath, long timeout) throws Exception {
        waitJQueryAngular();
        waitWhileBrowserIsBusy();
        try {
            new WebDriverWait(edriver, timeout)
                    .until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
        }catch (Exception ex){
        }
    }

    public static void waitUntilElementIsInVisible(WebElement element) throws Exception {
        waitWhileBrowserIsBusy();
        try {
            new WebDriverWait(edriver, Constant.WAIT_TIMEOUT_SECONDS)
                    .until(ExpectedConditions.invisibilityOf(element));
        }catch (Exception ex){
        }
    }

    public static void waitUntilElementIsInVisible(String xpath) throws Exception {
        waitJQueryAngular();
        waitWhileBrowserIsBusy();
        try {
            new WebDriverWait(edriver, Constant.WAIT_TIMEOUT_SECONDS)
                    .until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
        }catch (Exception ex){
        }
    }

    public static void waitUntilElementIsClickable(WebElement element) throws Exception {
        waitWhileBrowserIsBusy();
        waitJQueryAngular();
        try {
            new WebDriverWait(edriver, Constant.WAIT_TIMEOUT_SECONDS)
                    .until(ExpectedConditions.elementToBeClickable(element));
        }catch (StaleElementReferenceException ex){
            Thread.sleep(2000);
            waitUntilElementIsClickable(element);
        }
    }

    public static void waitUntilElementContainsValue(WebElement element, String value) throws Exception {
        waitJQueryAngular();
        try {
            (new WebDriverWait(edriver, Constant.WAIT_TIMEOUT_SECONDS)).until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver d) {
                    if (StringUtils.equals(element.getAttribute("value"), value)) {
                        getFocusOut(element);
                    }
                    return StringUtils.equals(element.getAttribute("value"), value);
                }
            });
        }catch (StaleElementReferenceException ex){
            Thread.sleep(2000);
            waitUntilElementContainsValue(element, value);
        }
    }

    public static void waitUntilElementContainsText(final WebElement element, final String text) throws Exception {
        waitJQueryAngular();
        try {
            (new WebDriverWait(edriver, 20L)).until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver d) {
                    if (StringUtils.equals(element.getText(), text)) {
                        PageObjectBase.getFocusOut(element);
                    }

                    return Boolean.valueOf(StringUtils.equals(element.getText(), text));
                }
            });
        }catch (StaleElementReferenceException ex){
            Thread.sleep(2000);
            waitUntilElementContainsText(element, text);
        }
    }

    public static void waitUntilListContainsValueAtIndex(List collection, int index) throws Exception {
        waitJQueryAngular();
        (new WebDriverWait(edriver, Constant.WAIT_TIMEOUT_SECONDS)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return (collection != null && collection.size() > index);
            }
        });
    }

    public static void waitUntilListContainsValueAtIndex(String xpath, int index) throws Exception {
        try {
            waitJQueryAngular();
            (new WebDriverWait(edriver, Constant.WAIT_TIMEOUT_SECONDS)).until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver d) {
                    return (edriver.findElements(By.xpath(xpath)) != null && edriver.findElements(By.xpath(xpath)).size() > index);
                }
            });
        }catch (StaleElementReferenceException ex){
            Thread.sleep(2000);
            waitUntilListContainsValueAtIndex(xpath, index);
        }
    }

    public static void waitUntilValuesAreEqual(WebElement ele, String actualValue, String expectedValue) throws Exception {
        waitJQueryAngular();
        try {
            (new WebDriverWait(edriver, Constant.WAIT_TIMEOUT_SECONDS)).until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver d) {
                    if (StringUtils.equals(actualValue, expectedValue)) {
                        getFocusOut(ele);
                    }
                    return StringUtils.equals(actualValue, expectedValue);
                }
            });
        }catch (StaleElementReferenceException ex){
            Thread.sleep(2000);
            waitUntilValuesAreEqual(ele, actualValue, expectedValue);
        }
    }

    public static void waitUntilElementIsNotEmpty(WebElement element) throws Exception {
        waitJQueryAngular();
        try {
            new WebDriverWait(edriver, Constant.WAIT_TIMEOUT_SECONDS)
                    .until(ExpectedConditions.attributeToBeNotEmpty(element, "value"));
        }catch (StaleElementReferenceException ex){
            Thread.sleep(2000);
            waitUntilElementIsNotEmpty(element);
        }
    }

    public static void waitUntilElementIsEmpty(WebElement element) throws Exception {
        waitJQueryAngular();
        try {
            new WebDriverWait(edriver, Constant.WAIT_TIMEOUT_SECONDS)
                    .until(ExpectedConditions.attributeToBe(element, "value", ""));
        }catch (StaleElementReferenceException ex){
            Thread.sleep(2000);
            waitUntilElementIsEmpty(element);
        }
    }

    public static void waitUntilElementIsDisabled(WebElement element) throws Exception {
        waitJQueryAngular();
        try {
            new WebDriverWait(edriver, Constant.WAIT_TIMEOUT_SECONDS)
                    .until(ExpectedConditions.attributeToBe(element, "disabled", "true"));
        }catch (StaleElementReferenceException ex){
            Thread.sleep(2000);
            waitUntilElementIsDisabled(element);
        }
    }

    public static void waitUntilSelectDropdownHasValueAtIndex(Select element, int index) throws Exception {
        waitJQueryAngular();
        try {
            (new WebDriverWait(edriver, Constant.WAIT_TIMEOUT_SECONDS)).until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver d) {
                    return (element.getOptions() != null && element.getOptions().size() > index);
                }
            });
        }catch (StaleElementReferenceException ex){
            Thread.sleep(2000);
            waitUntilSelectDropdownHasValueAtIndex(element, index);
        }
    }

    public static void waitWhileBrowserIsBusy() throws Exception {
        ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                JavascriptExecutor executor = (JavascriptExecutor) driver;
                return executor.executeScript("return document.readyState").equals("complete");
            }
        };

        Wait<WebDriver> wait = new WebDriverWait(edriver, Constant.WAIT_TIMEOUT_SECONDS);
        try {
            wait.until(expectation);
        } catch (Throwable t) {
            logger.error("Browser did not responded in " + Constant.WAIT_TIMEOUT_SECONDS
                    + " sec(s)");
        }

    }

    public static void waitWhileAngularAjaxCallsAreActive() {
        Wait<WebDriver> wait = new WebDriverWait(edriver, Constant.WAIT_TIMEOUT_SECONDS);
        wait.until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                Boolean ajaxInactive = true;
                JavascriptExecutor executor = (JavascriptExecutor) driver;
                if (Boolean.valueOf(executor.executeScript("return (window.angular != undefined)").toString())) {
                    ajaxInactive = Boolean.valueOf(executor.executeScript(
                            "return angular.element('.ng-scope').injector().get('$http').pendingRequests.length == 0").toString());
                }
                return ajaxInactive;
            }
        });
    }

    public static void getFocusOut(WebElement element) {
        ((JavascriptExecutor) edriver).executeScript("arguments[0].blur();", element);
    }

    public static void focus(WebElement element) throws Exception {
        ((JavascriptExecutor) edriver).executeScript("arguments[0].focus();", element);
    }

    public static void doubleClick(WebElement element) throws Exception {
        waitUntilElementIsClickable(element);
        ((JavascriptExecutor) edriver).executeScript("var evt = document.createEvent('MouseEvents');" +
                "evt.initMouseEvent('dblclick',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);" +
                "arguments[0].dispatchEvent(evt);", element);
    }

    public static void doubleClick(String xpath) throws Exception {
        WebElement element = edriver.findElement(By.xpath(xpath));
        waitUntilElementIsClickable(element);
        ((JavascriptExecutor) edriver).executeScript("var evt = document.createEvent('MouseEvents');" +
                "evt.initMouseEvent('dblclick',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);" +
                "arguments[0].dispatchEvent(evt);", element);
    }

    public static void focusWindowAndClick(WebElement element) throws Exception {
        try {
            new WebDriverWait(edriver, 1)
                    .until(ExpectedConditions.elementToBeClickable(element));
        } catch (StaleElementReferenceException e) {
            Thread.sleep(Constant.WAIT_STALE_ELEMENT_TIMEOUT_SECONDS);
        } catch (Exception e) {
        }
        ((JavascriptExecutor) edriver).executeScript("window.focus();");
        ((JavascriptExecutor) edriver).executeScript("arguments[0].click();", element);
    }

    public static void scroll() {
        ((JavascriptExecutor) edriver).executeScript("window.scrollBy(0,200)");
    }

    public static void scrollDown() {
        ((JavascriptExecutor) edriver).executeScript("window.scrollBy(0,250)");
    }

    public static void scrollToElement(WebElement element) {
        ((JavascriptExecutor) edriver).executeScript("window.scrollBy(0,-100)");
        ((JavascriptExecutor) edriver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public static void waitForJQueryLoad() {
        setDriver(edriver);
        ExpectedCondition<Boolean> jQueryLoad = driver1 -> ((Long) ((JavascriptExecutor) jsWaitDriver)
                .executeScript("return jQuery.active") == 0);
        boolean jqueryReady = (Boolean) jsExec.executeScript("return jQuery.active==0");
        if (!jqueryReady) {
            logger.info("JQuery is NOT Ready!");
            jsWait.until(jQueryLoad);
        }
    }

    public static void waitForAngularLoad() {
        WebDriverWait wait = new WebDriverWait(jsWaitDriver, 15);
        JavascriptExecutor jsExec = (JavascriptExecutor) jsWaitDriver;
        String angularReadyScript = "return angular.element(document).injector().get('$http').pendingRequests.length === 0";
        ExpectedCondition<Boolean> angularLoad = driver1 -> Boolean.valueOf(((JavascriptExecutor) edriver)
                .executeScript(angularReadyScript).toString());
        boolean angularReady = Boolean.valueOf(jsExec.executeScript(angularReadyScript).toString());
        if (!angularReady) {
            logger.info("ANGULAR is NOT Ready!");
            wait.until(angularLoad);
        }
    }

    public static void waitUntilJSReady() {
        WebDriverWait wait = new WebDriverWait(jsWaitDriver, 15);
        JavascriptExecutor jsExec = (JavascriptExecutor) jsWaitDriver;

        ExpectedCondition<Boolean> jsLoad = driver1 -> ((JavascriptExecutor) jsWaitDriver)
                .executeScript("return document.readyState").toString().equals("complete");

        boolean jsReady = (Boolean) jsExec.executeScript("return document.readyState").toString().equals("complete");

        if (!jsReady) {
            logger.info("JS in NOT Ready!");
            wait.until(jsLoad);
        }
    }

    public static void waitUntilJQueryReady() {
        JavascriptExecutor jsExec = (JavascriptExecutor) jsWaitDriver;

        Boolean jQueryDefined = (Boolean) jsExec.executeScript("return typeof jQuery != 'undefined'");
        if (jQueryDefined == true) {
            sleep(20);
            waitForJQueryLoad();
            waitUntilJSReady();
            sleep(20);
        }
    }

    public static void waitUntilAngularReady() {
        JavascriptExecutor jsExec = (JavascriptExecutor) jsWaitDriver;

        Boolean angularUnDefined = (Boolean) jsExec.executeScript("return window.angular === undefined");
        if (!angularUnDefined) {
            Boolean angularInjectorUnDefined = (Boolean) jsExec.executeScript("return angular.element(document).injector() === undefined");
            if (!angularInjectorUnDefined) {
                sleep(20);
                waitForAngularLoad();
                waitUntilJSReady();
                sleep(20);
            }
        }
    }

    public static void waitJQueryAngular() {
        // waitUntilJQueryReady();
        waitUntilAngularReady();
    }

    public static void sleep(Integer seconds) {
        long secondsLong = (long) seconds;
        try {
            Thread.sleep(secondsLong);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void switchToNewWindow(int windowNumber) throws InterruptedException {
        Thread.sleep(12000);
        Set<String> s = edriver.getWindowHandles();
        Iterator<String> ite = s.iterator();
        int i = 1;
        while (ite.hasNext() && i < 10) {
            String popupHandle = ite.next().toString();
            edriver.switchTo().window(popupHandle);
            edriver.manage().window().maximize();
            edriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            if (i == windowNumber) break;
            i++;
        }
    }

    public boolean isElementExist(By locator) throws Exception {
        waitWhileBrowserIsBusy();
        try {
            if (edriver.findElement(locator) != null) {
                return true;
            }
            return false;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean isElementExist(WebElement ele) throws Exception {
        waitWhileBrowserIsBusy();
        try {
            if (ele.isDisplayed()) {
                return true;
            }
            return false;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean isElementExistWithMinimumImplicitWait(WebElement ele, int timeoutInMilliSeconds) throws Exception {
        waitWhileBrowserIsBusy();
        turnOffImplicitWait(timeoutInMilliSeconds);
        try {
            waitUntilElementIsVisible(ele,TimeUnit.MILLISECONDS.toSeconds(timeoutInMilliSeconds));
            if (ele.isDisplayed()) {
                turnOnImplicitWait();
                return true;
            }
            turnOnImplicitWait();
            return false;
        } catch (Exception ex) {
            turnOnImplicitWait();
            return false;
        }
    }

    public boolean isElementExistWithMinimumImplicitWait(String xpath, int timeoutInMilliSeconds) throws Exception {
        waitWhileBrowserIsBusy();
        turnOffImplicitWait(timeoutInMilliSeconds);
        try {
            if (edriver.findElement(By.xpath(xpath)) != null) {
                turnOnImplicitWait();
                return true;
            }
            turnOnImplicitWait();
            return false;
        } catch (Exception ex) {
            turnOnImplicitWait();
            return false;
        }
    }

    public void waitUntilElementIsInVisibleWithMinimumImplicitWait(WebElement ele, int timeoutInMilliSeconds) throws Exception {
        waitWhileBrowserIsBusy();
        turnOffImplicitWait(timeoutInMilliSeconds);
        try {
            waitUntilElementIsInVisible(ele,TimeUnit.MILLISECONDS.toSeconds(timeoutInMilliSeconds));
            turnOnImplicitWait();
        } catch (Exception ex) {
            turnOnImplicitWait();
        }
    }

    public void waitUntilElementIsInVisibleWithMinimumImplicitWait(String xpath, int timeoutInMilliSeconds) throws Exception {
        waitWhileBrowserIsBusy();
        turnOffImplicitWait(timeoutInMilliSeconds);
        try {
            waitUntilElementIsInVisible(xpath,TimeUnit.MILLISECONDS.toSeconds(timeoutInMilliSeconds));
            turnOnImplicitWait();
        } catch (Exception ex) {
            turnOnImplicitWait();
        }
    }

    public boolean isElementEnabled(WebElement ele) throws Exception {
        waitWhileAngularAjaxCallsAreActive();
        waitUntilJQueryReady();
        waitWhileBrowserIsBusy();
        try {
            if (ele.isEnabled()) {
                return true;
            }
            return false;
        } catch (NoSuchElementException ex) {
            return false;
        }
    }

    protected void switchToFrame(By locator) throws Exception {
        try {
            edriver.switchTo().defaultContent();
            WebElement iFrame = (new WebDriverWait(edriver, Constant.WAIT_TIMEOUT_SECONDS))
                    .until(ExpectedConditions.visibilityOfElementLocated(locator));
            edriver.switchTo().frame(iFrame);
        } catch (Exception e) {
            logger.error("frame not found, searched by By [" + locator.toString() + "]", e);
            throw e;
        }
    }

    public void compareInputAndUIValue(String fieldName, WebElement ele, String expectedValue) throws Exception {
        if (StringUtils.isNotEmpty(expectedValue) && isElementExistWithMinimumImplicitWait(ele, Constant.MIN_IMPLICIT_WAIT_MILL_SECONDS)) {
            Assert.assertEquals(fieldName + " mismatched - inputValue: " + expectedValue + " and UI value: " + ele.getAttribute("value"),
                    expectedValue, ele.getAttribute("value"));
        }
    }

    public void compareIfUIElementIsSelected(String fieldName, WebElement ele, String inputValue) throws Exception {
        if (StringUtils.isNotEmpty(inputValue) && isElementExistWithMinimumImplicitWait(ele, Constant.MIN_IMPLICIT_WAIT_MILL_SECONDS)) {
            Assert.assertTrue(fieldName + " is not selected - UI value: " + ele.isSelected(),
                    ele.isSelected());
        }
    }

    public void compareInputAndUISelectedValue(String fieldName, WebElement ele, String inputValue) throws Exception {
        if (StringUtils.isNotEmpty(inputValue) && isElementExistWithMinimumImplicitWait(ele, Constant.MIN_IMPLICIT_WAIT_MILL_SECONDS)) {
            Assert.assertEquals(fieldName + " mismatched - inputValue: " + inputValue + " and UI value: " + new Select(ele).getFirstSelectedOption().getText().trim(),
                    inputValue, new Select(ele).getFirstSelectedOption().getText().trim());
        }
    }

    public void focusClick(WebElement element) throws Exception {
        try {
            new WebDriverWait(edriver, Constant.WAIT_TIMEOUT_SECONDS)
                    .until(ExpectedConditions.elementToBeClickable(element));
        } catch (StaleElementReferenceException e) {
            Thread.sleep(Constant.WAIT_STALE_ELEMENT_TIMEOUT_SECONDS);
        } catch (Exception e) {
        }
        waitUntilElementIsVisible(element);
        scrollToElement(element);
        ((JavascriptExecutor) edriver).executeScript("arguments[0].focus();", element);
        waitUntilElementIsClickable(element);
        waitWhileBrowserIsBusy();
        ((JavascriptExecutor) edriver).executeScript("arguments[0].click();", element);
    }

    public void focusDoubleClick(WebElement element) throws Exception {
        try {
            new WebDriverWait(edriver, Constant.WAIT_TIMEOUT_SECONDS)
                    .until(ExpectedConditions.elementToBeClickable(element));
        } catch (StaleElementReferenceException e) {
            Thread.sleep(Constant.WAIT_STALE_ELEMENT_TIMEOUT_SECONDS);
        } catch (Exception e) {
        }
        waitWhileBrowserIsBusy();
        waitUntilElementIsVisible(element);
        focus(element);
        doubleClick(element);
    }

    public void focusDoubleClick(String xpath) throws Exception {
        WebElement element = edriver.findElement(By.xpath(xpath));
        focusDoubleClick(element);
    }

    public void focusClick(String xpath) throws Exception {
        WebElement element = edriver.findElement(By.xpath(xpath));
        scrollToElement(element);
        focusClick(element);
    }

    public void click(WebElement element) throws Exception {
        waitWhileAngularAjaxCallsAreActive();
        waitJQueryAngular();
        try {
            new WebDriverWait(edriver, Constant.WAIT_TIMEOUT_SECONDS)
                    .until(ExpectedConditions.elementToBeClickable(element));
        } catch (StaleElementReferenceException e) {
            Thread.sleep(Constant.WAIT_STALE_ELEMENT_TIMEOUT_SECONDS);
            PageFactory.initElements(edriver, this);
        } catch (Exception e) {
        }
        waitUntilElementIsVisible(element);
        waitUntilElementIsClickable(element);
        scrollToElement(element);
        element.click();
        waitWhileBrowserIsBusy();
    }

    public void click(String xpath) throws Exception {
        WebElement element = edriver.findElement(By.xpath(xpath));
        click(element);
    }

    public void sendKeys(WebElement element, String text) throws Exception {
        focusClick(element);
        element.clear();
        if(StringUtils.isNotEmpty(element.getAttribute("value"))){
            for(int i=element.getAttribute("value").length(); i>0; i--){
                element.sendKeys(Keys.BACK_SPACE);
            }
        }
        waitUntilElementIsEmpty(element);
        element.sendKeys(text);
    }

    public void sendKeys(String xpath, String text) throws Exception {
        WebElement element = edriver.findElement(By.xpath(xpath));
        sendKeys(element, text);
    }

    public void enterTextUsingJavaScript(WebElement element, String text) throws Exception {
        ((JavascriptExecutor) edriver).executeScript("arguments[0].setAttribute('value',arguments[1]);", element, text);
    }

    public void selectByValue(WebElement element, String value) throws Exception {
        focusClick(element);
        Select select = new Select(element);
        select.selectByValue(value);
    }

    public void selectByValue(String xpath, String value) throws Exception {
        WebElement element = edriver.findElement(By.xpath(xpath));
        selectByValue(element, value);
    }

    public void selectByVisibleText(WebElement element, String text) throws Exception {
        focusClick(element);
        Select select = new Select(element);
        select.selectByVisibleText(text);
    }

    public void selectByVisibleText(String xpath, String text) throws Exception {
        WebElement element = edriver.findElement(By.xpath(xpath));
        selectByVisibleText(element, text);
    }

    public void selectByIndex(WebElement element, int index) throws Exception {
        focusClick(element);
        Select select = new Select(element);
        waitUntilSelectDropdownHasValueAtIndex(select, index);
        select.selectByIndex(index);
    }

    public String getText(WebElement element) throws Exception {
        try {
            return element.getText();
        } catch (Exception e) {
            return "";
        }
    }

    public String getText(String xpath) throws Exception {
        WebElement element = edriver.findElement(By.xpath(xpath));
        return getText(element);
    }

    public List<WebElement> getElements(String xpath) throws Exception {
        try {
            //This line of code is to avoid StaleElementReferenceException
            if(edriver.findElements(By.xpath(xpath)) != null) {
                edriver.findElements(By.xpath(xpath)).get(0).getTagName();
            }
            return edriver.findElements(By.xpath(xpath));
        }catch(StaleElementReferenceException ex){
            Thread.sleep(2000);
            getElements(xpath);
        }
        return null;
    }

    public WebElement getElement(String xpath) throws Exception {
        try {
            //This line of code is to avoid StaleElementReferenceException
            edriver.findElement(By.xpath(xpath)).getTagName();
            return edriver.findElement(By.xpath(xpath));
        }catch(StaleElementReferenceException ex){
            Thread.sleep(2000);
            getElement(xpath);
        }
        return null;
    }

    public void navigateToNextPage() throws Exception {
        focusClick(edriver.findElement(By.xpath("//button[contains(@id,'navigation_button')]")));
    }

    public void turnOffImplicitWait(long timeout) throws Exception {
        edriver.manage().timeouts().implicitlyWait(timeout, TimeUnit.MILLISECONDS);
    }

    public void turnOnImplicitWait() throws Exception {
        edriver.manage().timeouts().implicitlyWait(Constant.IMPLICIT_WAIT_SECONDS, TimeUnit.SECONDS);
    }

    public String retrieveElementValueBasedOnType(WebElement webElement) throws Exception {
        if (StringUtils.contains(webElement.getAttribute("type"), "checkbox")) {
            if (webElement.isSelected()) {
                return "Y";
            } else {
                return "N";
            }
        } else if (StringUtils.contains(webElement.getAttribute("type"), "select")) {
            return new Select(webElement).getFirstSelectedOption().getText().trim();
        } else if (StringUtils.contains(webElement.getAttribute("type"), "text") || StringUtils.contains(webElement.getTagName(), "input")) {
            return webElement.getAttribute("value");
        } else {
            return getText(webElement);
        }
    }

    public String retrieveElementValueBasedOnType(String xpath) throws Exception {
        return retrieveElementValueBasedOnType(edriver.findElement(By.xpath(xpath)));
    }

    public void enterNonEmptyValue(WebElement webElement, String value) throws Exception {
        if (StringUtils.isNotEmpty(value)) {
            sendKeys(webElement, value);
        }
    }

    public void enterNonEmptyValue(String xpath, String value) throws Exception {
        if (StringUtils.isNotEmpty(value)) {
            sendKeys(edriver.findElement(By.xpath(xpath)), value);
        }
    }

    public void enterValue(WebElement webElement, String value) throws Exception {
        if (StringUtils.isNotEmpty(value)) {
            sendKeys(webElement, value);
        }else if (StringUtils.isEmpty(value)) {
            sendKeys(webElement, "");
        }
    }

    public void enterValue(String xpath, String value) throws Exception {
        enterValue(getElement(xpath), value);
    }

    public void selectNonEmptyValueByVisibleText(WebElement webElement, String text) throws Exception {
        if (StringUtils.isNotEmpty(text)) {
            selectByVisibleText(webElement, text);
        }
    }

    public void selectNonEmptyValueByVisibleText(String xpath, String text) throws Exception {
        selectNonEmptyValueByVisibleText(getElement(xpath), text);
    }

    public void selectNonEmptyValueByValue(WebElement webElement, String text) throws Exception {
        if (StringUtils.isNotEmpty(text)) {
            selectByValue(webElement, text);
        }
    }

    public void clickCheckBox(WebElement webElement, String text) throws Exception {
        if ((StringUtils.equalsIgnoreCase(text, "True") ||
                StringUtils.equalsIgnoreCase(text, "Y") ||
                StringUtils.equalsIgnoreCase(text, "Yes")) &&
                !webElement.isSelected()) {
            click(webElement);
        }else if ((StringUtils.equalsIgnoreCase(text, "false") ||
                StringUtils.equalsIgnoreCase(text, "N") ||
                StringUtils.equalsIgnoreCase(text, "No")) &&
                webElement.isSelected()) {
            click(webElement);
        }
    }

    public void clickCheckBox(String xpath, String text) throws Exception {
        clickCheckBox(edriver.findElement(By.xpath(xpath)), text);
    }

    public void clickIfValueIsNotEmpty(WebElement webElement, String text) throws Exception {
        if (StringUtils.isNotEmpty(text)) {
            click(webElement);
        }
    }

    public void clickIfValueIsNotEmpty(String xpath, String text) throws Exception {
        if (StringUtils.isNotEmpty(text)) {
            click(edriver.findElement(By.xpath(xpath)));
        }
    }

    public void clickIfValueEquals(WebElement webElement, String expected, String actual) throws Exception {
        if (StringUtils.equals(expected, actual)) {
            click(webElement);
        }
    }

    public void clickIfValueEquals(String xpath, String expected, String actual) throws Exception {
        if (StringUtils.equals(expected, actual)) {
            click(edriver.findElement(By.xpath(xpath)));
        }
    }
}