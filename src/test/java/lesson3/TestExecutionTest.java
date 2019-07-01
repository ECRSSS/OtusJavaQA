package lesson3;

import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import testbase.BaseTestSuite;

import java.util.List;
import java.util.Random;

public class TestExecutionTest extends BaseTestSuite {

    private void login(String login, String password) {
        $("#tl_login").sendKeys(login);
        $("#tl_password").sendKeys(password);
        $("input[type='sunmit']").click();
    }

    private void toMainFrame() {
        driver.switchTo().defaultContent();
        driver.switchTo().frame("mainframe");
    }

    private void toTreeFrame() {
        toMainFrame();
        driver.switchTo().frame("treeframe");
    }

    private void toWorkFrame() {
        toMainFrame();
        driver.switchTo().frame("workframe");
    }

    private void toTestExecution() {
        toMainFrame();
        $("a[href='lib/general/frmWorkArea.php?feature=executeTest']").click();
    }

    private String clickOnFirstTestOnPage() {
        toWorkFrame();
        WebElement element = $$("a.x-tree-node-anchor span.light_not_run:last-child").get(0);
        element.click();
        log4j.info("Имя кликнутого теста: " + element.getText());
        return element.getText();
    }

    private void setRandomExecutionDuration() {
        toWorkFrame();
        Random random = new Random(System.currentTimeMillis());
        $("input#execution_duration").sendKeys("" + random.nextInt(50) + 1);
    }

    private void setPassedExecutionStatusForAll() {
        toWorkFrame();
        List<WebElement> elms = $$("select.step_status");
        for (WebElement elm : elms) {
            new Select(elm).selectByValue("p");
        }
    }

    private void setTestPassed() {
        toWorkFrame();
        $("img[title='Click to set to passed']").click();
    }

    private void assertTestsColorIsGreen(){
       String color = $("td.passed").getCssValue("background");
        System.out.println("fdsf");
    }
    @Test
    public void testExecutionTest() {
        login("user", "bitnami");
        toTestExecution();
        String testName = clickOnFirstTestOnPage();
        setRandomExecutionDuration();
        setPassedExecutionStatusForAll();
        setTestPassed();
        assertTestsColorIsGreen();


    }


}
