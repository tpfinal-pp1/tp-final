package com.TpFinal.selenium;

import org.apache.commons.lang3.SystemUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class testPublicacionVenta {
    FirefoxDriver wd;
    WebDriverWait wait;

    @Before
    public void setUp() throws Exception {
        if(SystemUtils.IS_OS_LINUX){
            System.setProperty("webdriver.gecko.wd","GeckoDriver"+ File.separator+"geckodriver");
            System.out.println("OS: LINUX");
        }
        if(SystemUtils.IS_OS_WINDOWS) {
            String systemArchitecture = System.getProperty("sun.arch.data.model");
            if(systemArchitecture.equals("64")) {
                System.setProperty("webdriver.gecko.driver", "GeckoDriver" + File.separator + "geckodriver.exe");



                System.out.println("OS: Windows 64-bit");
            }
            else {
                System.setProperty("webdriver.gecko.driver", "GeckoDriver-32-bits" + File.separator + "geckodriver.exe");
                System.out.println("OS: Windows 32-bit");
            }
        }

        wd = new FirefoxDriver();
        wd.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);


    }

    @Test
    public void testPublicacionVenta() {
        wd.get("http://inmobi.ddns.net/");
        wd.findElement(By.id("gwt-uid-4")).click();
        wd.findElement(By.id("gwt-uid-4")).clear();
        wd.findElement(By.id("gwt-uid-4")).sendKeys("max");
        wd.findElement(By.id("gwt-uid-6")).click();
        wd.findElement(By.id("gwt-uid-6")).clear();
        wd.findElement(By.id("gwt-uid-6")).sendKeys("1234");
        wd.findElement(By.xpath("//div[@class='v-expand']/div/div/div[3]/div/div[5]/div")).click();
        wd.get("http://inmobi.ddns.net/#!publicaciones");
        wd.findElement(By.xpath("//div[@id='ROOT-2521314']/div/div[2]/div/div[2]/div/div/div/div[1]/div/div[3]/div/div[3]/div/div[1]/div")).click();
        wd.findElement(By.xpath("//label[@for='gwt-uid-27']")).click();
        if (!wd.findElement(By.id("gwt-uid-27")).isSelected()) {
            wd.findElement(By.id("gwt-uid-27")).click();
        }
        wd.findElement(By.cssSelector("button.v-datefield-button")).click();
        wd.findElement(By.xpath("//td[@class='v-datefield-calendarpanel-body']//span[.='15']")).click();
        wd.findElement(By.xpath("//div[@id='gwt-uid-19']/div[3]/div")).click();
        wd.findElement(By.xpath("//div[@class='v-window-contents']//td[.='Margarita Hang']")).click();
        wd.findElement(By.xpath("//div[@class='v-window-contents']/div/div/div[3]/div/div/div/div")).click();
        wd.findElement(By.id("gwt-uid-23")).click();
        wd.findElement(By.xpath("//div[@id='combo']/div")).click();
        wd.findElement(By.xpath("//div[@class='v-filterselect-suggestmenu']//span[.='Dolares']")).click();
        wd.findElement(By.xpath("//div[@id='ROOT-2521314']/div/div[2]/div/div[2]/div/div/div/div[2]/div/div/div[3]/div/table/tbody/tr[3]/td[3]/div/div/div")).click();

    }


    @After
    public void tearDown() {
        wd.quit();
    }
    
    public static boolean isAlertPresent(FirefoxDriver wd) {
        try {
            wd.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }
}
