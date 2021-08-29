package lesson10.taskOne;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

public class SearchTest {

    private WebDriver driver;

    @Before
    public void openChrome() {
        System.setProperty("webdriver.chrome.driver", "C:\\Tools\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @After
    public void closeChrome() {
        driver.quit();
    }

    @Test
    public void simpleTest() {

        driver.get("https://store.steampowered.com/");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class=\"home_page_content\"]")));
        Assert.assertEquals(driver.getTitle(), "Добро пожаловать в Steam");  //check open page

        WebElement searchElement = searchElement
                ("//div[@class=\"store_nav\"]//div[@class =\"searchbox\"]//input[@id=\"store_nav_search_term\"]");
        searchElement.click();

        WebElement searchElementForText = searchElement
                ("//div[@class=\"home_tabs_content\"]//a[@href][1]//div[@class =\"tab_item_name\"][1]");
        String keyForSearch = searchElementForText.getText();
        searchElement.sendKeys(keyForSearch, Keys.ENTER);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class=\"searchbar\"]")));
        Assert.assertEquals(driver.getTitle(), "Поиск Steam");

        List<WebElement> listElements = searchElements
                ("//div[@class=\"page_content_ctn\"]//div[@id=\"search_results\"]//span[@class=\"title\"]");
        for (int i = 0; i < listElements.size(); i++) {
            Assert.assertTrue(listElements.get(1).getText().contains(keyForSearch));
        }
    }

    public WebElement searchElement(String str) {
        WebElement searchElement = null;
        try {
            searchElement = driver.findElement(By.xpath(str));
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        } finally {
            return searchElement;
        }
    }

    public List<WebElement> searchElements(String str) {

        List<WebElement> listElements = null;
        try {
            listElements = driver.findElements(By.xpath(str));
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
        return listElements;
    }
}