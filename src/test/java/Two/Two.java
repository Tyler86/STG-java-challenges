package Two;

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


public class Two {
    public WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void startClass() throws Exception{
        System.setProperty("webdriver.chrome.driver", "./bin/chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 20);
    }
    @AfterClass
    public void stopClass(){
        driver.quit();
    }

    @Test
    public void searchForExotic(){
        String url = "https://copart.com";
        driver.get(url);

        String searchTerm = "exotic";

        By searchInput = By.id("input-search");

        By searchBtn = By.xpath("(//button[@ng-click=\"search()\"])[2]");

        //wait for input to be visible and enter the searchterm
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
        WebElement inputField = driver.findElement(searchInput);
        inputField.sendKeys(searchTerm);

        // click on the search btn
        driver.findElement(searchBtn).click();

        By searchResults = By.id("serverSideDataTable");

        //  this waits for results to load and then get the page src and confirms that the word PORSCHE is found if not it will fail the test
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchResults));
        String pageSource = driver.getPageSource();
        Assert.assertEquals(true,pageSource.contains("PORSCHE"));

    }
}
