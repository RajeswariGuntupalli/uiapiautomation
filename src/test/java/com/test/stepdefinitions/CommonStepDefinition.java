package com.test.stepdefinitions;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import com.test.dataset.DataField;
import com.test.dataset.ExcelUtils;
import com.test.hooks.Hooks;
import com.test.logger.ScenarioLogger;
import com.test.utilities.FileSystem;
import com.test.utilities.JSONFactory;
import com.test.utilities.RestClientFactory;
import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.java.it.Ma;
import gherkin.JSONParser;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;

import java.awt.print.Book;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

public class CommonStepDefinition {

    public WebDriver driver;
    public Scenario scenario;
    ExcelUtils dataPool;
    DataField df;
    HttpResponse response;

    public CommonStepDefinition() {
        driver = Hooks.driver;
        this.scenario = Hooks.scenario;
    }

    @Given("^scenario executes TC(\\d+) to verify the journey$")
    public void scenarioExecutesTestCaseToVerifyTheJourney(String testcase) throws Throwable {
        String filename = FileSystem.getFilePrefix().replaceFirst("/", "") + ".xlsx";
        dataPool = new ExcelUtils(getClass().getClassLoader().getResource(filename).toURI().getPath(), "Sheet1");
        df = new DataField(dataPool, "TC" + testcase);
        Hooks.dataField = df;
    }

    @Given("^User opens URL$")
    public void userOpensBrowserWithURL() throws Throwable {
        Hooks.initializeDriver();
    }

    @When("^API is called$")
    public void apiIsCalled() throws Throwable {
        String apiURL = Hooks.environmentConfigurations.getApiUrl() + df.getData("API Name");
        apiURL = StringUtils.replace(apiURL, "{key}", Hooks.environmentConfigurations.getApiKey());
        Pattern p = Pattern.compile("\\{.*?\\}");
        Matcher matcher = p.matcher(apiURL);
        while(matcher.find()) {
            apiURL = StringUtils.replace(apiURL, matcher.group(0),df.getData(StringUtils.substringBetween(matcher.group(0), "{", "}")));
        }
        response = RestClientFactory.performHttpGet(apiURL);
    }

    @Then("^Verify response$")
    public void verifyResponse() throws Throwable {
        InputStreamReader inputStream= new InputStreamReader(response.getEntity().getContent());
        char[] readBuffer = new char[100000];
        int read = inputStream.read(readBuffer,0,readBuffer.length);
        inputStream.close();
        String message = new String(Arrays.copyOf(readBuffer, read));
        String[] verifyResponseEles = df.getData("verify_response").split(",");
        String jsonPathExpression = "";
        for(int i=0; i<((Integer)JsonPath.parse(message).read("$.data.length()")).intValue(); i++){
            jsonPathExpression = "$.data[" + i + "]";
            for(int j=0; j<verifyResponseEles.length; j++ ){
                Object obj = ((Map)JsonPath.parse(message).read(jsonPathExpression)).get(verifyResponseEles[j]);
                if(obj instanceof String){
                    ScenarioLogger.addStepLog("Value for " + verifyResponseEles[j] + " : " + obj + "\n");
                }else if(obj instanceof Map){
                    Map<String, Object> map = (Map) obj;
                    for(Map.Entry<String, Object> entry : map.entrySet()){
                        ScenarioLogger.addStepLog("Value for " + verifyResponseEles[j] + "." + entry.getKey() + " : "
                                + (entry.getValue() instanceof String? entry.getValue().toString() :
                                (entry.getValue() instanceof Integer ? String.valueOf(entry.getValue()) : entry.getValue().toString())) + "\n");
                    }
                }
            }
        }
    }
}