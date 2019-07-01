package lesson2;

import driversmanager.DriversEnum;
import driversmanager.WebDriverFactory;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;

import java.util.concurrent.TimeUnit;

public class CreateTestCaseTest {
    private WebDriver driver;

    private void login(String login, String password, boolean xpath) {
        LoginPage page = new LoginPage().toSite();
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
        driver.navigate().to("http://localhost/index.php");
        String currentHandle = driver.getWindowHandle();
        driver.switchTo().defaultContent();
        driver.switchTo().frame("mainframe");
        MainPage page = new MainPage();
        if (xpath) {
            driver.findElement(By.xpath("//a[contains(text(),'Test Specification')]")).click();
        } else {
            driver.findElement(page.cssLink).click();
        }
        driver.switchTo().window(currentHandle);
    }

    private void javascriptClick(WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", element);
    }

    private void createNewTestSuite() {
        TestSpecificationsPage page = new TestSpecificationsPage();
        driver.switchTo().frame("mainframe");
        driver.switchTo().frame("workframe");
        driver.findElement(page.actionsXpath).click();
        driver.findElement(page.newTestSuiteXpath).click();
        driver.findElement(page.caseNameCss).sendKeys(RandomStringUtils.randomAlphabetic(10));
        WebElement frame = driver.findElement(By.cssSelector("div#cke_1_contents iframe"));
        driver.switchTo().frame(frame);
        driver.findElement(By.cssSelector("body")).sendKeys(RandomStringUtils.randomAlphabetic(10));
        driver.switchTo().defaultContent();
        driver.switchTo().frame("mainframe");
        driver.switchTo().frame("workframe");
        driver.findElement(page.saveCaseXpath).click();
    }

    private void createNewTestCase(boolean xpath) {
        TestSpecificationsPage page = new TestSpecificationsPage();
        driver.switchTo().defaultContent();
        driver.switchTo().frame("mainframe");
        driver.switchTo().frame("treeframe");
        driver.findElement(page.suiteXpath).click();
        driver.findElement(By.cssSelector("body")).sendKeys(Keys.ENTER);
        driver.switchTo().defaultContent();
        driver.switchTo().frame("mainframe");
        driver.switchTo().frame("workframe");
        driver.findElement(page.actionsXpath).click();
        driver.findElement(By.cssSelector("[name='create_tc']")).click();
        driver.findElement(By.cssSelector("[name='testcase_name']")).sendKeys(RandomStringUtils.randomAlphabetic(10));
        WebElement frame = driver.findElement(By.cssSelector("div#cke_1_contents iframe"));
        driver.switchTo().frame(frame);
        driver.findElement(By.cssSelector("body")).sendKeys(RandomStringUtils.randomAlphabetic(10));
        driver.switchTo().defaultContent();
        driver.switchTo().frame("mainframe");
        driver.switchTo().frame("workframe");
        driver.findElement(By.cssSelector("#do_create_button")).click();
    }

    @Before
    public void setupWebDriver() {
        String driverName = System.getProperty("browser") == null ? "chrome" : System.getProperty("browser");
        driver = WebDriverFactory.create(DriversEnum.getDriverValueByAlias(driverName), null);
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    public void test() {
        login("user", "bitnami", true);
        toTestSpecifications(true);
        createNewTestSuite();
        driver.navigate().to("http://localhost/index.php");
        toTestSpecifications(true);
        createNewTestCase(true);
        toTestSpecifications(true);
        createNewTestCase(true);
    }

    class LoginPage {

        By cssLogin = By.cssSelector("#tl_login");
        By xpathLogin = By.xpath("//input[@id='tl_login']");
        By cssPassword = By.cssSelector("#tl_password");
        By xpathPassword = By.xpath("//input[@id='tl_password']");
        By cssButton = By.cssSelector("input[type='submit']");
        By xpathButton = By.xpath("//input[@type='submit']");

        public LoginPage toSite() {
            driver.navigate().to("http://localhost/login.php");
            return this;
        }
    }

    class MainPage {
        By cssLink = By.cssSelector("a[href='lib/general/frmWorkArea.php?feature=editTc']");
        By xpathLink = By.xpath("//a[contains(text(),'Test Specification')]");
    }

    class TestSpecificationsPage {
        By suiteCss = By.cssSelector("li.x-tree-node li img:nth-child(2)");
        By suiteXpath = By.xpath("(//li[contains(@class,'x-tree-node')]//li//a)[last()]");

        By actionsCss = By.cssSelector("img.clickable[title='Actions']");
        By actionsXpath = By.xpath("//img[contains(@class,'clickable')][@title='Actions']");

        By newTestSuiteCss = By.cssSelector("input#new_testsuite");
        By newTestSuiteXpath = By.xpath("//input[@id='new_testsuite']");

        By caseNameCss = By.cssSelector("#name");
        By caseXpath = By.xpath("//*[@id='name']");

        By saveCaseCss = By.cssSelector("[name='add_testsuite_button']");
        By saveCaseXpath = By.xpath("//*[@name='add_testsuite_button']");

    }

    @After
    public void quit() {
        if (driver != null) {
            driver.quit();
        }
    }
}
