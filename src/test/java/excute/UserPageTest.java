package excute;

import common.BaseTest;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pagefactory.UserLogin;

public class UserPageTest extends BaseTest {
    private static String LONIN_PAGE_URL = "https://member.ssg.com/member/login.ssg";

    private WebDriver driver;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        driver = super.openBrowser();
    }

//    @AfterTest(alwaysRun = true)
//    public void closeDriver() {
//        super.taskKill();
//    }

//    @AfterClass(alwaysRun = true)
//    public void closeBrowser() {
//        super.quitBrowser(driver);
////        super.taskKill();
//    }

    @Test
    public void test_01_user_longin() {
        driver.get(LONIN_PAGE_URL);

        UserLogin userLogin = new UserLogin(driver);
        boolean isVisibleUserLogOutBtn = userLogin.login(driver);

        Assert.assertTrue(isVisibleUserLogOutBtn);
    }
}
