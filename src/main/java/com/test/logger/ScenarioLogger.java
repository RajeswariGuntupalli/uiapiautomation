package com.test.logger;

import com.cucumber.listener.Reporter;
import com.test.hooks.Hooks;
import com.test.utilities.FileSystem;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

public class ScenarioLogger {

    public static void addStepLog(String logMsg) throws Exception {
        try {
            Reporter.addStepLog(logMsg);
        } catch (NullPointerException e) {
            Hooks.scenario.write(logMsg);
        }
    }

    public static void addScenarioLog(String logMsg) throws Exception {
        try {
            Reporter.addScenarioLog(logMsg);
        } catch (NullPointerException e) {
            Hooks.scenario.write(logMsg);
        }
    }

    public static void addScreenShot() throws Exception {
        try {
            File sourcePath = ((TakesScreenshot) Hooks.driver).getScreenshotAs(OutputType.FILE);
            File destinationPath;
            if(StringUtils.isNotEmpty(Hooks.environmentConfigurations.getScreenshotPath())){
                destinationPath = new File(Hooks.environmentConfigurations.getScreenshotPath() + sourcePath.getName());
            }else {
                destinationPath = new File(System.getProperty("user.dir") + "/target/cucumber-reports/" + sourcePath.getName());
            }
            FileUtils.copyFile(sourcePath, destinationPath);
            Reporter.addScreenCaptureFromPath(destinationPath.toString());
        } catch (NullPointerException e) {
            byte[] screenshot = ((TakesScreenshot) Hooks.driver).getScreenshotAs(OutputType.BYTES);
            Hooks.scenario.embed(screenshot, "image/png");
        }
    }

    public static void takeFullPageScreenShot (WebDriver driver) throws Exception {
        String screenShotPath = capture(driver);
        try {
            Reporter.addScreenCaptureFromPath(screenShotPath);
        }catch (NullPointerException e) {
            Hooks.scenario.embed(Files.readAllBytes(Paths.get(screenShotPath)), "image/png");
        }
    }

    public static String capture(WebDriver driver) throws Exception
    {
        String destinationPath;
        String screenShotName = new Date().toString().replace(":","-")+".png";
        Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
        if(StringUtils.isNotEmpty(Hooks.environmentConfigurations.getScreenshotPath())){
            if(!FileSystem.isFileExist(Hooks.environmentConfigurations.getScreenshotPath())){
                new File(Hooks.environmentConfigurations.getScreenshotPath()).mkdirs();
            }
            destinationPath = String.valueOf(new File(Hooks.environmentConfigurations.getScreenshotPath()+ screenShotName));
        }else {
            destinationPath  = System.getProperty("user.dir") + "\\target\\cucumber-reports\\" + screenShotName ;
        }

        ImageIO.write(screenshot.getImage(),"PNG",new File(destinationPath));
        return destinationPath;
    }


}
