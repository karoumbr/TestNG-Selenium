package commonLibs;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class CommonDriver {

    private WebDriver driver;
    private int pageloadTimeout;
    private int elementDetectionTimeout;
    private String currentWorkingDirectory = System.getProperty("user.dir");

    public CommonDriver(String browserType) throws Exception {
        browserType = browserType.trim();

        switch (browserType.toLowerCase()) {
            case "chrome":
                System.setProperty("webdriver.chrome.driver", currentWorkingDirectory + "/drivers/chromedriver.exe");
                driver = new ChromeDriver();
                break;

            case "firefox":
                System.setProperty("webdriver.gecko.driver", currentWorkingDirectory + "/drivers/geckodriver.exe");
                driver = new FirefoxDriver();
                break;

            case "edge":
                System.setProperty("webdriver.edge.driver", currentWorkingDirectory + "/drivers/msedgedriver.exe");
                driver = new EdgeDriver();
                break;

            default:
                throw new Exception("Invalid browser type: " + browserType);
        }

        driver.manage().window().maximize();
    }

    public void navigateToFirstUrl(String url) {
        driver.get(url);
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void setPageloadTimeout(long pageloadTimeoutInSeconds) {
        this.pageloadTimeout = (int) pageloadTimeoutInSeconds;
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(pageloadTimeout));
    }

    public void setElementDetectionTimeout(long elementDetectionTimeoutInSeconds) {
        this.elementDetectionTimeout = (int) elementDetectionTimeoutInSeconds;
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(elementDetectionTimeout));
    }

    public void closeAllBrowsers() {
        if (driver != null) {
            driver.quit();
        }
    }
}
