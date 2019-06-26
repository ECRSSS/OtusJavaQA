package task1.suites;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SuiteOne {
    private WebDriver driver;

    class LoginPage {
        By cssLogin = By.cssSelector("#tl_login");
        By xpathLogin = By.xpath("//input[@id='tl_login']");

        By cssPassword = By.cssSelector("#tl_password");
        By xpathPassword = By.xpath("//input[@id='tl_password']");

        By cssButton = By.cssSelector("input[type='submit']");
        By xpathButton = By.xpath("//input[@type='submit']");
    }

    class MainPage {
        By cssLink = By.cssSelector("a[href='lib/general/frmWorkArea.php?feature=editTc']");
        By xpathLink = By.xpath("//a[@href='lib/general/frmWorkArea.php?feature=editTc']");
    }

    private void login(String login, String password, boolean xpath) {
        LoginPage page = new LoginPage();
        if (xpath) {
            driver.findElement(page.cssLogin).sendKeys(login);
            driver.findElement(page.cssPassword).sendKeys(password);
            driver.findElement(page.cssButton).click();
        } else {
            driver.findElement(page.xpathLogin).sendKeys(login);
            driver.findElement(page.xpathPassword).sendKeys(password);
            driver.findElement(page.xpathButton).click();
        }
    }

    private void toTestSpecifications(boolean xpath) {
        MainPage page = new MainPage();
        if (xpath) {
            driver.findElement(page.cssLink).click();
        } else {
            driver.findElement(page.xpathLink).click();
        }
    }

    private void createNewTestCase(boolean xpath){

    }

    @Before
    public void setupWebDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @Test
    public void test() {
        login("ecrs", "root", true);
        toTestSpecifications(true);


    }

    @After
    public void quit() {
        if (driver != null) {
            driver.quit();
        }
    }
}
