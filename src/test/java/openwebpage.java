import org.junit.After;
import org.junit.Before;

import org.junit.Test;
import static org.junit.Assert.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.*;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import org.apache.commons.io.FileUtils;

public class openwebpage{
    WebDriver driver;
    WebDriverWait wait;
    String ScreenshotFileName = "grusha_open_webpage";
    String ChomeDriverPath = "lib/chrome/chromedriver.exe";
    String ErrorChomeDriverNotFound = "Error: Incorrect configuration. Please check the path lib/chrome/chromedriver.exe";
    String ErrorIncorrectConfiguration = "Error: Incorrect configuration.";
    String ErrorVarWebDriverIsNull = "Error: Incorrect configuration. WebDriver driver is null";
    String ErrorDependenciesAndJars = "Error: Incorrect configuration. Check dependencies in pom.xml. Check jar-files in configration.";
    String ErrorChomeConfiguration = "Check that there is crome in path ";
    String ChomePath = "C:\\Users\\Reader\\Local Settings\\Application Data\\Google\\Chrome\\Application\\chrome.exe";

    @Test
    public void test(){
        try {
            System.setProperty("webdriver.chrome.driver", ChomeDriverPath);
        }catch(IllegalStateException e)
        {
            System.out.println(ErrorChomeDriverNotFound);
        }
        catch (Exception e)
        {
            System.out.println(ErrorIncorrectConfiguration);
        }
        try
        {
            driver = new ChromeDriver();
            if (driver==null){
                System.out.println(ErrorVarWebDriverIsNull);
                return;
            }
            wait = new WebDriverWait(driver, 10);
            driver.get("http://blazedemo.com/index.php");
            driver.findElement(By.xpath("//input[@value='Find Flights']")).click();
            boolean isTitleCorrect = wait.until(ExpectedConditions.titleIs("BlazeDemo - reserve"));
            assertTrue(isTitleCorrect == true);
            checkPageIsReady(driver);
            takeScreenShot();
        }catch(Exception e)
        {
            System.out.println(ErrorDependenciesAndJars);
            System.out.println(ErrorChomeConfiguration);
            System.out.println(ChomePath);
        }finally {
            driver.quit();
        }
    }

    public void takeScreenShot() throws IOException {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String fileName = UUID.randomUUID().toString();
        File targetFile = new File(ScreenshotFileName + ".png");
        FileUtils.copyFile(scrFile, targetFile);
    }

    public void checkPageIsReady(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor)driver;

        if (js.executeScript("return document.readyState").toString().equals("complete")){
            System.out.println("Page Is loaded.");
            return;
        }

        for (int i=0; i<25; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            //To check page ready state.
            if (js.executeScript("return document.readyState").toString().equals("complete")) {
                break;
            }
        }
    }
}
