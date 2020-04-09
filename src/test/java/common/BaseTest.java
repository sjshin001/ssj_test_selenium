package common;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.io.IOException;

public abstract class BaseTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public WebDriver openBrowser(){
        WebDriver driver;
        logger.info("===*** openBrowser webDriver ***===");
        String headlessOnOff ="Off"; // headless = On or Off
        LaunchWebDriver launchWebDriver = new LaunchWebDriver();
        driver = launchWebDriver.launchWebDriverMode(headlessOnOff);
        driver.manage().window().setSize(new Dimension(1936, 1056));

        if(driver == null){
            Assert.assertTrue(false);
        }

        return driver;
    }

    public void quitBrowser(WebDriver driver){
        System.out.println("===*** quitBrowser webDriver ***===");
        if(driver != null) {
            driver.quit();
        }
    }

    public void taskKill() {
        logger.info("===*** webDriver taskkill ***===");
        if( LaunchWebDriver.isWindows() ){
            try {
                Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe /T");
            } catch (IOException ie) {
                logger.info("IOException e={}", ie);
            } catch (Exception e) {
                logger.info("Exception e={}", e);
            }
        }
    }
}
