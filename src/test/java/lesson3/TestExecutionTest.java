package lesson3;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import testbase.BaseTestSuite;

import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class TestExecutionTest extends BaseTestSuite {

    private void toSite() {
        driver.navigate().to("http://localhost/login.php");
    }

    private void login(String login, String password) {
        $("input#tl_login").sendKeys(login);
        $("input#tl_password").sendKeys(password);
        $("input[type='submit']").click();
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

    private void clickOnFirstTestOnPage() {
        toTreeFrame();
        $("input[value='Expand tree']").click();
        WebElement element = getFirstTestOnPage();
        element.click();
        element.sendKeys(Keys.ENTER);
    }

    private WebElement getFirstTestOnPage(){
        toTreeFrame();
        return $$("div.x-tree-node-el.x-tree-node-leaf.x-unselectable>a").get(0);
    }

    private void setRandomExecutionDuration() {
        toWorkFrame();
        Random random = new Random(System.currentTimeMillis());
        $("input#execution_duration").sendKeys("" + random.nextInt(50) + 1);
    }

    private void setExecutionStatusForAll(String status) {
        toWorkFrame();
        List<WebElement> elms = $$("select.step_status");
        for (WebElement elm : elms) {
            new Select(elm).selectByValue(status);
        }
    }

    private void setTestPassed() {
        toWorkFrame();
        $("img[title='Click to set to passed']").click();
    }
    private void setTestFailed(){
        toWorkFrame();
        $("img[title='Click to set to failed']").click();
    }

    private void assertTestsColorIsGreen() {
        toWorkFrame();
        List<WebElement> passeds = $$("td.passed");
        String color = passeds.get(passeds.size() - 1).getCssValue("background-color");
        assertThat(color).isEqualTo("rgba(0, 100, 0, 1)");
        toTreeFrame();
        assertThat($$("div.x-tree-node-el.x-tree-node-leaf.x-unselectable>a>span>span")
                .get(0).getCssValue("background-color"))
                .isEqualTo("rgba(213, 238, 213, 1)");
    }

    private void assertTestsColorIsRed() {
        toWorkFrame();
        List<WebElement> passeds = $$("td.failed");
        String color = passeds.get(passeds.size() - 1).getCssValue("background-color");
        assertThat(color).isEqualTo("rgba(178, 34, 34, 1)");
        toTreeFrame();
        assertThat($$("div.x-tree-node-el.x-tree-node-leaf.x-unselectable>a>span>span")
                .get(0).getCssValue("background-color"))
                .isEqualTo("rgba(238, 213, 213, 1)");
    }

    @Test
    public void testExecutionTest() {
        toSite();
        login("user", "bitnami");
        toTestExecution();
        clickOnFirstTestOnPage();
        setRandomExecutionDuration();
        setExecutionStatusForAll("p");
        setTestPassed();
        assertTestsColorIsGreen();
        setExecutionStatusForAll("f");
        setTestFailed();
        assertTestsColorIsRed();
    }

    public void toTPM(){
        toMainFrame();
        $("a[href='lib/project/projectView.php']").click();
    }

    public Project createProject(){
        Project project = new Project();
        toMainFrame();
        $("input#create").click();
        $("input[name='tprojectName']")
                .sendKeys(project.name);
        $("input[name='tcasePrefix']")
                .sendKeys(project.prefix);
        driver.switchTo().frame($("iframe"));
        $("body").sendKeys(project.desc);
        toMainFrame();
        $("input[name='tprojectName']").sendKeys(Keys.ENTER);
        return project;
    }

    public void assertProject(Project project){
        toMainFrame();
        assertThat($("tr:last-child>td>a").getText()).isEqualTo(project.name);
        assertThat($("tr:last-child>td:nth-child(2)>p")).isEqualTo(project.desc);
        assertThat($("tr:last-child>td:nth-child(3)")).isEqualTo(project.prefix);
        assertThat($("tr:last-child>td:nth-child(8)>img")
                .getCssValue("src")).contains("accept");
    }

    @Test
    public void testProjectCreate(){
        toSite();
        login("user", "bitnami");
        toTPM();
        assertProject(createProject());


    }

    class Project{
        String name = RandomStringUtils.randomAlphabetic(10);
        String prefix = RandomStringUtils.randomAlphabetic(2).toUpperCase();
        String desc = RandomStringUtils.randomAlphabetic(10);
    }


}
