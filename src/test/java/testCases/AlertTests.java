package testCases;

import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class AlertTests extends BaseTest {
    public String username = "sbaskaran435";
    public String accesskey = "1KfTKWmoFzUiWD8tqSof0hxilluLNqTRcZ7KTkXg8S8Ug68SwD";
    public static RemoteWebDriver driver = null;
    public String gridURL = "@hub.lambdatest.com/wd/hub";
    @BeforeTest
    public void startReport() {
        initializeReport();
    }

    @BeforeMethod
    public void setUp() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", "chrome");
        capabilities.setCapability("version", "96.0");
        capabilities.setCapability("platform", "win10"); // If this cap isn't specified, it will just get the any available one
        capabilities.setCapability("build", "ExtentReportGeneration");
        capabilities.setCapability("name", "AlertTest");
        try {
            driver = new RemoteWebDriver(new URL("https://" + username + ":" + accesskey + gridURL), capabilities);
        } catch (MalformedURLException e) {
            System.out.println("Invalid grid URL");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void clickSimpleAlertAndGetMessage() {
        String methodName = new Exception().getStackTrace()[0].getMethodName();
        String className = new Exception().getStackTrace()[0].getClassName();

        test = extent.createTest(methodName, "Click alert and verify the text");
        test.log(Status.INFO, "starting");
        test.assignCategory("P0");

        driver.get("https://www.lambdatest.com/selenium-playground/");
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);

        WebElement alertMenu = driver.findElement(By.linkText("Bootstrap Alerts"));
        alertMenu.click();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);

        WebElement alert = driver.findElement(By.xpath("//button[text()='Normal Success Message']"));
        alert.click();

        WebElement message = driver.findElement(By.xpath("//div[contains(text(),'normal success message')]"));
        String actualMessage = message.getText();
        String expectedMessage = "I'm a normal success message. To close use the appropriate button.";
        Assert.assertTrue(actualMessage.contains(expectedMessage));

    }


    @AfterMethod
    public void end(){
        driver.quit();

    }
    @AfterTest
    public void endReport(){
        extent.flush();

    }
}
