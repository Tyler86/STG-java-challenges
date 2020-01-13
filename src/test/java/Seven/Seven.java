package Seven;

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

public class Seven {
    public WebDriver driver;
    WebDriverWait wait;

    String url = "https://copart.com";


    @BeforeClass
    public void startClass() throws Exception {
        System.setProperty("webdriver.chrome.driver", "./bin/chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 20);
        driver.get(url);
        driver.manage().window().maximize();
    }

    @AfterClass
    public void stopClass() {
        driver.quit();
    }

    @Test
    public void getListOfPopularCars() {
        // go to https://copart.com


        By popularSearchesContainer = By.xpath("//div[@ng-if=\"popularSearches\"]");
        By popularSearchesList__By = By.xpath("//div[@ng-if=\"popularSearches\"]//a");

        wait.until(ExpectedConditions.visibilityOfElementLocated(popularSearchesContainer));

        List<WebElement> popularSearchesList = driver.findElements(popularSearchesList__By);
        Assert.assertEquals(20, popularSearchesList.size(),"unable to find 20 results for popular products");
        TreeMap<String,String> popularModels = new TreeMap<>();
        for (WebElement element:popularSearchesList) {
            String model = element.getText() ;
            String href = element.getAttribute("href");
            popularModels.put(model,href);
            System.out.println( model+ " -- " + url);
        }

        for (String key: popularModels.keySet()) {
            driver.get(popularModels.get(key));
            try {
                wait.until(ExpectedConditions.titleContains(key.toLowerCase()));
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("link for " + key + " does not go to a relevent page");
            }
        }
    }
}
