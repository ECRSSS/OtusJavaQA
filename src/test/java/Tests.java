import driversmanager.DriversEnum;
import driversmanager.WebDriverFactory;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Tests {

    private WebDriver driver;

    @Before
    public void setupWebDriver(){
        //WebDriverManager.chromedriver().setup();
    }

    @Test
    public void test(){
            driver = WebDriverFactory.create(DriversEnum.CHROME);
            driver.get("https://otus.ru/");
    }

    @After
    public void quit(){
        if(driver!=null) {
        driver.quit();
        }
    }
}
