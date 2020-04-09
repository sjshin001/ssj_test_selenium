package common;

import org.apache.commons.exec.OS;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

public class LaunchWebDriver {
    private static final Logger logger = LoggerFactory.getLogger(LaunchWebDriver.class);

    private WebDriver driver;

    public LaunchWebDriver(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public LaunchWebDriver() {
    }

    public WebDriver launchWebDriverMode(String headlessOnOff) {

        Path path = Paths.get("");
        String pathStr = path.toAbsolutePath().toString(); //Jenkins 서버와 Local 의 workspace 가 다르기에, path를 따로 가져온다.
        logger.debug("pathStr={}", pathStr);

		/* MAC 과 WINDOWS chromedriver 분기 */
        if (isWindows()) {
            System.setProperty("webdriver.chrome.driver", pathStr + "//driver//chromedriver.exe");
        } else if (isMac()) {
            System.setProperty("webdriver.chrome.driver", pathStr + "//driver//chromedriver");
        } else {
            logger.error("지원하지 않는 OS 입니다.");
            return null;
        }

        ChromeOptions co = new ChromeOptions();
        logger.debug("computername={}", System.getenv("computername"));
        /* 공통 ChromeOptions 값. */
        co.addArguments("start-maximized");
        co.addArguments("enable-automation");
        co.addArguments("--disable-gpu");
        co.addArguments("--no-sandbox");
        co.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
//        co.setCapability(CapabilityType.UNHANDLED_PROMPT_BEHAVIOUR, UnhandledAlertException.DRIVER_INFO);

        if (isWindows() && "NG-BF".equals(System.getenv("computername"))) {
            System.out.println("===>>> isWindows ");
            /* Jenkins 서버(NG-BF)는 항상 headless 모드이다 (java.awt.headless = true) */
            logger.debug("headless Mode : NG-BF is Always headlessMode");
            /* jenkins 에만 추가되는 ChromeOptions 값. */
//            co.addArguments("--no-sandbox"); // Jenkins 장비에서, 19/3/22에 업데이트 된 Chrome 버전 73 이후 부터 해당 값 추가해야만 정상적으로 실행 됨. 추후 Chrome 버전 업데이트 이후 제거 가능여부 확인 필요.
//            co.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
//            co.addArguments("--disable-extensions");
//            co.addArguments("--dns-prefetch-disable");
//            co.addArguments("--disable-dev-shm-usage");
//            co.addArguments("--disable-browser-side-navigation");

        } else {
            System.out.println("===>>> isWindows1 ");
            if ("On".equals(headlessOnOff)) { // On / Off
                /* headless chrome */
                co.addArguments("--headless");
//                co.addArguments("--no-sandbox");
//                co.addArguments("--disable-extensions");
//                co.addArguments("--dns-prefetch-disable");
//                co.addArguments("--disable-dev-shm-usage");
//                co.addArguments("--disable-browser-side-navigation");
//                co.addArguments("--proxy-server='direct://'");
//                co.addArguments("--proxy-bypass-list=*");
			}else{
                System.out.println("===>>> isWindows2 ");
//                co.addArguments("--disable-dev-shm-usage");
//                co.addArguments("--proxy-server='direct://'");
//                co.addArguments("--proxy-bypass-list=*");
            }

        }

        driver = new ChromeDriver(co);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); // 기본 대기 시간
        driver.manage().window().maximize();
        return driver;
    }

    public static boolean isWindows() {
        return (OS.isFamilyWindows());
    }

    public static boolean isMac() {
        return (OS.isFamilyMac());
    }
}
