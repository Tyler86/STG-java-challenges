package Three;

        import org.openqa.selenium.By;
        import org.openqa.selenium.WebDriver;
        import org.openqa.selenium.WebElement;
        import org.openqa.selenium.chrome.ChromeDriver;
        import org.openqa.selenium.support.ui.ExpectedConditions;
        import org.openqa.selenium.support.ui.WebDriverWait;
        import org.testng.Assert;
        import org.testng.annotations.AfterClass;
        import org.testng.annotations.BeforeClass;
        import org.testng.annotations.Test;

        import java.util.List;
        import java.util.TreeMap;


public class Three {
    public WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void startClass() throws Exception {
        System.setProperty("webdriver.chrome.driver", "./bin/chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 20);
    }

    @AfterClass
    public void stopClass() {
        driver.quit();
    }

    @Test
    public void getListOfPopularCars() {
        // go to https://copart.com
        String url = "https://copart.com";
        driver.get(url);


        By popularSearchesContainer = By.xpath("//div[@ng-if=\"popularSearches\"]");
        By popularSearchesList__By = By.xpath("//div[@ng-if=\"popularSearches\"]//a");

        wait.until(ExpectedConditions.visibilityOfElementLocated(popularSearchesContainer));

        List<WebElement> popularSearchesList = driver.findElements(popularSearchesList__By);
        Assert.assertEquals(20, popularSearchesList.size(),"unable to find 20 results for popular products");

        for (WebElement element:popularSearchesList) {
            System.out.println(element.getText() + " -- " + element.getAttribute("href"));
        }



    }
}


