package Five;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.TreeMap;

public class Five {
    public WebDriver driver;
    WebDriverWait wait;

    String url = "https://copart.com";
    String searchTerm = "porsche";

    // -------------- locators -----------------------
    By searchInput = By.id("input-search");

    By searchBtn = By.xpath("(//button[@ng-click=\"search()\"])[2]");
    By searchResults = By.id("serverSideDataTable");
    By selectEntries__By = By.xpath("(//select[@name=\"serverSideDataTable_length\"])[1]");
    By loadingIcon = By.id("serverSideDataTable_processing");
    By models_list__By = By.xpath("//span[@data-uname=\"lotsearchLotmodel\"]");
    By damages__By = By.xpath("//span[@data-uname=\"lotsearchLotdamagedescription\"]");


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
    public void searchForExotic(){


        //wait for  search input to be visible and enter the search term and click on search
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
        WebElement inputField = driver.findElement(searchInput);
        inputField.sendKeys(searchTerm);
        driver.findElement(searchBtn).click();

        // waits for search results table to load
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchResults));


        // wait for qty dropdown to be visible an changes the value to be 100
        wait.until(ExpectedConditions.visibilityOfElementLocated(selectEntries__By));
        Select dropDown = new Select(driver.findElement(selectEntries__By));
        dropDown.selectByValue("100");

        // wait for the loading icon to disappear
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingIcon));
        System.out.println("Success");

        countModels();
        countDamages();




    }

    // prints any TreeMap to the console with the props of <String, Integer>
    public void printTreeMap(TreeMap<String,Integer> map){

        for (String key:map.keySet()){
            System.out.println( key +" - "+map.get(key));
        }
    }

    // gathers a list of all the models and prints  a count of each unique model
    public void countModels(){
        List<WebElement> modelsElementList = driver.findElements(models_list__By);

        TreeMap<String, Integer> modelsCount = new TreeMap<String,Integer>();

        // this  for each loop will go thru list of elements and grab the model text out and count how many times each model appears.

        for (WebElement element : modelsElementList) {
            String key = element.getText();
            if (modelsCount.containsKey(key)) {
                modelsCount.put(key,modelsCount.get(key)+1);
            } else {
                modelsCount.put(key, 1);
            }
        }
        // prints out list of models and the count
        System.out.println("--------------- models list -----------------");
        printTreeMap(modelsCount);
    }



    //gathers all the damages and from the different types of vehicles and counts how many of each of the 4 types and damages and the count of all the misc... ones as well
    public void countDamages(){

        List<WebElement> damages_List;
        damages_List = driver.findElements(damages__By);
        TreeMap<String, Integer> damages_count = new TreeMap<String,Integer>();

        // sets default values in for the different types of damages
        damages_count.put("REAR END",0);
        damages_count.put("FRONT END",0);
        damages_count.put("MINOR DENT/SCRATCHES",0);
        damages_count.put("UNDERCARRIAGE",0);
        damages_count.put("MISC",0);


        // categories the different types of damages and counts how many of each
        for ( WebElement element: damages_List) {
            String typeOfDamage = element.getText();


            switch (typeOfDamage){
                case "REAR END":
                    damages_count.put("REAR END",damages_count.get("REAR END")+1);
                    break;
                case "FRONT END":
                    damages_count.put("FRONT END",damages_count.get("FRONT END")+1);
                    break;
                case "MINOR DENT/SCRATCHES":
                    damages_count.put("MINOR DENT/SCRATCHES",damages_count.get("MINOR DENT/SCRATCHES")+1);
                    break;
                case "UNDERCARRIAGE":
                    damages_count.put("UNDERCARRIAGE",damages_count.get("UNDERCARRIAGE")+1);
                    break;
                default:
                    damages_count.put("MISC",damages_count.get("MISC")+1);
                    break;
            }
        }
        System.out.println("--------------- damages list -----------------");
        printTreeMap(damages_count);
    }
}
