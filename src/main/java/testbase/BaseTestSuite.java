package testbase;

import driversmanager.DriversEnum;
import driversmanager.WebDriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

public abstract class BaseTestSuite {

    protected WebDriver driver;
    protected final Logger log4j = LogManager.getLogger(this.getClass());

    @Before
    public void before() {
        driver = WebDriverFactory.create(DriversEnum.CHROME, new ChromeOptions());
        log4j.info("Запускаем браузер:",driver);
    }

    @After
    public void after() {
        driver.quit();
        log4j.info("Закрываем драйвер");
    }

    protected WebElement $(String cssSelector){
        return driver.findElement(By.cssSelector(cssSelector));
    }

    protected List<WebElement> $$(String cssSelector){
        return driver.findElements(By.cssSelector(cssSelector));
    }
}
