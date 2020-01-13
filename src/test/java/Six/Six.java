package Six;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;

public class Six {
    public WebDriver driver;
    WebDriverWait wait;

    String url = "https://copart.com";
    String searchTerm = "nissan";

    // -------------- locators -----------------------
    By searchInput = By.id("input-search");

    By searchBtn = By.xpath("(//button[@ng-click=\"search()\"])[2]");
    By searchResults = By.id("serverSideDataTable");

    // ---------------------------------------- setup locators and test should be good from there
    By model_Filter__By = By.xpath("//a[@data-uname=\"ModelFilter\"]");
    By modelInputField = By.xpath("(//input[@ng-model=\"filter.searchText\"])[4]");
    By skyLineOption__By = By.xpath("//abbr[@value=\"Skyline\"]");



    @BeforeClass
    public void startClass() throws Exception{
        System.setProperty("webdriver.chrome.driver", "./bin/chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 20);
        driver.get(url);
        driver.manage().window().maximize();


    }
    @AfterClass
    public void stopClass(){
        driver.quit();
    }

    @Test
    public void searchForExotic() {


        //wait for  search input to be visible and enter the search term and click on search
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
        WebElement inputField = driver.findElement(searchInput);
        inputField.sendKeys(searchTerm);
        driver.findElement(searchBtn).click();

        // waits for search results table to load
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchResults));

        wait.until(ExpectedConditions.visibilityOfElementLocated(model_Filter__By));
        driver.findElement(model_Filter__By).click();

        // wait for input field to be available and then type in Skyline
        wait.until(ExpectedConditions.visibilityOfElementLocated(modelInputField));
        WebElement modelInput = driver.findElement(modelInputField);
        modelInput.sendKeys("Skyline" + Keys.ENTER);

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(skyLineOption__By));
        } catch (Exception e) {
            e.printStackTrace();
            takeScreenshot("C:/temp/screenshots");
        }
    }
    public void takeScreenshot(String fileWithPath){
        //Convert web driver object to TakeScreenshot

        TakesScreenshot scrShot =((TakesScreenshot)driver);

        //Call getScreenshotAs method to create image file

        File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
        //Move image file to new destination

        File DestFile=new File(fileWithPath);

        // for some reason i am unable to get the fileUtils to work properly will work on this feature in the future.
        //Copy file at destination
       //  FileUtils.copyFile(SrcFile, DestFile);
    }
}

