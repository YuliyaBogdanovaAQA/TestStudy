package lesson10.taskOne;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.swing.*;
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

   /* @After
    public void closeChrome() {
        driver.quit();
    }*/

    @Test
    public void simpleTest() {

        driver.get("https://store.steampowered.com/");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class=\"home_page_content\"]")));
        Assert.assertEquals(driver.getTitle(), "Добро пожаловать в Steam");  //check open page

        WebElement searchElementDroDown = searchElement
                ("//div[@id =\"genre_tab\"]//a[@class=\"pulldown_desktop\"]");

        WebElement searchElementDroDownClick = searchElement
                ("//div[@id =\"genre_flyout\"]//div[@class=\"popup_menu popup_menu_browse\"]//a[1]");

        JavascriptExecutor executor = (JavascriptExecutor) driver;
        Object beforeClick=executor.executeScript
                ("var items = {}; for (index = 0; index < arguments[0].attributes.length; ++index) { items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value }; return items;",
                        searchElementDroDownClick);
        System.out.println("before click:\n" + beforeClick.toString());

        new Actions(driver)
                .moveToElement(searchElementDroDown)
                .pause(100)
                .perform();

        searchElementDroDownClick.click();

        WebElement searchElementDroDownClick2 = searchElement
                ("//div[@id =\"genre_flyout\"]//div[@class=\"popup_menu popup_menu_browse\"]//a[2]");
        Object afterClick=executor.executeScript
                ("var items = {}; for (index = 0; index < arguments[0].attributes.length; ++index) { items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value }; return items;",
                        searchElementDroDownClick2);
        System.out.println("after click:\n" + afterClick.toString());




      /*  WebElement searchElement = searchElement
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
        }*/
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