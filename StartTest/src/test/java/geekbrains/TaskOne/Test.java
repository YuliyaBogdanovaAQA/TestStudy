package geekbrains.TaskOne;

import geekbrainsTest.GeekbrainsTest;
import org.junit.After;
import org.junit.Before;

public class Test {
    GeekbrainsTest geekbrainsTest = new GeekbrainsTest();

    @Before
    public void openBrowser() {
        geekbrainsTest.openChrome();
    }

    @After
    public void closeBrowser() {
        geekbrainsTest.closeChrome();
    }

    @org.junit.Test
    public void test() {

        geekbrainsTest.loginPage();
        geekbrainsTest.testCaseOne();
    }

    @org.junit.Test
    public void test2() {

        geekbrainsTest.loginPage();
        geekbrainsTest.testCaseTwo();
    }
}