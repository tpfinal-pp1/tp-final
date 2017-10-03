package com.TpFinal.selenium.tests;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Created by Max on 10/2/2017.
 */
public class SeleniumTest {

    @Test
    public void seleniumTest(){
        System.setProperty("webdriver.gecko.driver", "C:\\Drivers\\geckodriver.exe");
        WebDriver webDriver = new FirefoxDriver();
        webDriver.get("http://inmobi.ddns.net/");
        webDriver.close();
        //webDriver.quit();
    }
}
