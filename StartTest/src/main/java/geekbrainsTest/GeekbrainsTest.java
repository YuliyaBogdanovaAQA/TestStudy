package geekbrainsTest;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.NoSuchElementException;

public class GeekbrainsTest {

    protected WebDriver driver;

    public void openChrome() {
        String path = System.getProperty("user.dir");
        System.setProperty("webdriver.chrome.driver", path + "\\src\\main\\resources\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    public void closeChrome() {
        driver.quit();
    }

    public void loginPage() {
        driver.get("https://crm.geekbrains.space/user/login");

        checkOpenPage("//h2[@class=\"title\"]", "Логин");
        enterData("//form[@id='login-form']//input[@name='_username']", "Applanatest1");

        WebElement pass = webElement
                ("//form[@id='login-form']//input[@name='_password']");
        Assert.assertNotNull("Element \"password\" not found", pass);
        pass.sendKeys("Student2020!", Keys.ENTER);                     //  end of authorization

        checkOpenPage("//h1[@class=\"logo logo-text\"]",
                "Панель инструментов");                                      // check of authorization
    }

    public void testCaseOne() {

        choiceFromDropdownMenu("//ul[@class=\"nav nav-multilevel main-menu\"]//li[@class=\"dropdown\"]//a[@href=\"#\"]//span[text()=\"Проекты\"]",
                "//ul[@class=\"nav nav-multilevel main-menu\"]//li[@class=\"dropdown\"]//li[@data-route=\"crm_project_my\"]");

        checkOpenPage("//div[@class=\"filter-box oro-clearfix-width\"]",
                "Все - Мои проекты - Все проекты - Проекты");               // check of Go to project

        webElement("//div[@class=\"btn-group\"]//a[@title=\"Создать проект\"]")
                .click();                                                       //Go to Create The project

        checkOpenPage("//div[@class=\"company-container\"]//span[@class=\"select2-chosen\"]",
                "Создать проект - Все проекты - Проекты");                  // check of Go to Create The project

        enterData2("My Project One", "//div/div/div[2]/input");

        webElement("//span[contains(text(),'Укажите организацию')]").click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//div[@id=\"select2-drop\"]//ul[@class=\"select2-results\"]//li[2]")));
        webElement("//div[@id=\"select2-drop\"]//ul[@class=\"select2-results\"]//li[2]").click();

        choiceFromDropdownMenu("//select[@name=\"crm_project[businessUnit]\"]",
                "//select[@name=\"crm_project[businessUnit]\"]//option[2]");
        choiceFromDropdownMenu("//select[@name=\"crm_project[curator]\"]",
                "//select[@name=\"crm_project[curator]\"]//option[7]");
        choiceFromDropdownMenu("//select[@name=\"crm_project[rp]\"]",
                "//select[@name=\"crm_project[rp]\"]//option[7]");
        choiceFromDropdownMenu("//select[@name=\"crm_project[administrator]\"]",
                "//select[@name=\"crm_project[administrator]\"]//option[7]");
        choiceFromDropdownMenu("//select[@name=\"crm_project[manager]\"]",
                "//select[@name=\"crm_project[manager]\"]//option[7]");
        choiceFromDropdownMenu("//div[@class=\"control-group control-group-choice\"]//a",
                "//div[@id=\"select2-drop\"]//ul[@class=\"select2-results\"]//li[7]");

        webElement("//div[@class=\"container-fluid page-title\"]//button[@class=\"btn btn-success action-button\"]")
                .click();

        controlPage("//div[@class=\"btn-group\"]//a[@title=\"Создать проект\"]"
                , "Все - Проекты - Все проекты - Проекты"
                , "Проект сохранен");
    }

    public void testCaseTwo() {

        loginPage();

        choiceFromDropdownMenu("//ul[@class=\"nav nav-multilevel main-menu\"]//a[@href=\"#\"]//span[text()=\"Контрагенты\"]",
                "//ul[@class=\"nav nav-multilevel main-menu\"]//li[@data-route=\"crm_contact_index\"]//span");

        checkOpenPage("//a[@title=\"Создать контактное лицо\"]",
                "Контактные лица - Контактные лица - Контрагенты");               // check of Create to Contact person

        webElement("//div[@class=\"row\"]//div[@class=\"btn-group\"]//a")
                .click();                                                                   //Go to Create to Contact person

        checkOpenPage("//div[@class=\"responsive-cell responsive-cell-no-blocks\"][1]//div[@class=\"control-group control-group-text\"][1]//label",
                "Создать контактное лицо - Контактные лица - Контрагенты");             // check of Go to Create to Contact person

        enterData2("Chudakov", "//div[@class = \"controls\"]//input[@name=\"crm_contact[lastName]\"]");
        enterData2("Ivan", "//div[@class = \"controls\"]//input[@name=\"crm_contact[firstName]\"]");
        enterData2("Engineer", "//input[@name=\"crm_contact[jobTitle]\"]");

        choiceFromDropdownMenu("//div/a/span[@class=\"select2-chosen\"]",
                "//div[@id=\"select2-drop\"]//li[5]");

        webElement("//div[@class=\"container-fluid page-title\"]//button[@class=\"btn btn-success action-button\"]")
                .click();

        controlPage("//a[@title=\"Создать контактное лицо\"]"
                , "Все - Контактные лица - Контактные лица - Контрагенты"
                , "Контактное лицо сохранено");
    }

    public WebElement webElement(String str) {
        WebElement searchElement = null;
        try {
            searchElement = driver.findElement(By.xpath(str));
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        } finally {
            return searchElement;
        }
    }

    public void checkOpenPage(String locatorXPath, String title) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorXPath)));
        Assert.assertEquals(driver.getTitle(), title);
    }

    public void enterData(String locatorXPath, String data) {
        WebElement element = webElement(locatorXPath);
        try {
            Assert.assertNotNull("Element not found", element);
            element.sendKeys(data);
        } catch (IllegalArgumentException ex) {
            ex.getMessage();
        } finally {
            System.out.println(element.getText());
        }
    }

    public void enterData2(String data, String locatorXPath) {
        StringBuilder sb = new StringBuilder();
        data = sb.append(data + " " + (int) (Math.random() * 100)).toString();
        enterData(locatorXPath, data);
    }

    public void choiceFromDropdownMenu(String locatorXPathSee, String locatorXPathChoice) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        new Actions(driver)
                .click(webElement(locatorXPathSee))
                .perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorXPathChoice)));
        webElement(locatorXPathChoice).click();
    }

    public void controlPage(String locator, String title, String exspectedMessage) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
        Assert.assertEquals(driver.getTitle(), title);

        Assert.assertEquals(exspectedMessage, driver.findElement(By.cssSelector(".message")).getText());
    }
}