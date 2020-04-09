package pagefactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserLogin {
    private final Logger logger = LoggerFactory.getLogger(UserLogin.class);

    @FindBy(how = How.ID, using = "mem_id")
    private WebElement inputID;

    @FindBy(how = How.ID, using = "mem_pw")
    private WebElement inputPW;

    @FindBy(how = How.CLASS_NAME, using = "cmem_btn")
    private WebElement loginBtn;

    private static String USERID = "shise000";
    private static String PWD = "qlalfqjsgh1";

    public UserLogin(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    public void userLoginAction(String id, String pw){
        inputID.sendKeys(id);
        inputPW.sendKeys(pw);
        loginBtn.click();
    }


    public Boolean login(WebDriver driver) {
        this.userLoginAction(USERID, PWD);

        return true;
    }
}
