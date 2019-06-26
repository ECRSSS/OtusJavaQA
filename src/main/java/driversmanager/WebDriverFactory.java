package driversmanager;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;


public class WebDriverFactory {

    public static WebDriver create(DriversEnum driverName, MutableCapabilities options) {
        WebDriver driver;
        switch (driverName) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                if (options != null) {
                    driver = new ChromeDriver((ChromeOptions) options);
                } else {
                    driver = new ChromeDriver();
                }
                break;
            case OPERA:
                WebDriverManager.operadriver().setup();
                if (options != null) {
                    driver = new OperaDriver((OperaOptions) options);
                } else {
                    driver = new OperaDriver();
                }
                break;
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                if (options != null) {
                    driver = new FirefoxDriver((FirefoxOptions) options);
                } else {
                    driver = new FirefoxDriver();
                }
                break;
            case IE:
                WebDriverManager.iedriver().setup();
                if (options != null) {
                    driver = new InternetExplorerDriver((InternetExplorerOptions) options);
                } else {
                    driver = new InternetExplorerDriver();
                }
                break;
            case EDGE:
                WebDriverManager.edgedriver().setup();
                if (options != null) {
                    driver = new EdgeDriver((EdgeOptions) options);
                } else {
                    driver = new EdgeDriver();
                }
                break;
            default:
                throw new RuntimeException("driver not found");
        }
        return driver;
    }
}
