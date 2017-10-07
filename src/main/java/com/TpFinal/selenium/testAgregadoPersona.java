package com.TpFinal.selenium;

import org.apache.commons.lang3.SystemUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class testAgregadoPersona {
    FirefoxDriver driver;

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

        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    }



    @After
    public void tearDown() {
        driver.quit();
    }
    
    @Test
    public void testAgregadoPersona() {
        driver.get("http://inmobi.ddns.net/");
        driver.findElement(By.id("gwt-uid-9")).click();
        driver.findElement(By.id("gwt-uid-9")).clear();
        driver.findElement(By.id("gwt-uid-9")).sendKeys("max");
        driver.findElement(By.id("gwt-uid-11")).click();
        driver.findElement(By.id("gwt-uid-11")).clear();
        driver.findElement(By.id("gwt-uid-11")).sendKeys("1234");
        driver.findElement(By.xpath("//div[@class='v-expand']/div/div/div[3]/div/div[5]/div")).click();
        driver.get("http://inmobi.ddns.net/#!personas");
        driver.findElement(By.xpath("//div[@id='ROOT-2521314']//div[.='Nuevo']")).click();
        driver.findElement(By.id("gwt-uid-17")).click();
        driver.findElement(By.id("gwt-uid-17")).clear();
        driver.findElement(By.id("gwt-uid-17")).sendKeys("Maxi");
        driver.findElement(By.id("gwt-uid-19")).click();
        driver.findElement(By.id("gwt-uid-19")).clear();
        driver.findElement(By.id("gwt-uid-19")).sendKeys("Lencina");
        driver.findElement(By.id("gwt-uid-21")).click();
        driver.findElement(By.id("gwt-uid-21")).clear();
        driver.findElement(By.id("gwt-uid-21")).sendKeys("12345678");
        driver.findElement(By.id("gwt-uid-23")).click();
        driver.findElement(By.id("gwt-uid-23")).clear();
        driver.findElement(By.id("gwt-uid-23")).sendKeys("max@max.com");
        driver.findElement(By.id("gwt-uid-25")).click();
        driver.findElement(By.id("gwt-uid-25")).clear();
        driver.findElement(By.id("gwt-uid-25")).sendKeys("12345678");
        driver.findElement(By.id("gwt-uid-27")).click();
        driver.findElement(By.id("gwt-uid-27")).clear();
        driver.findElement(By.id("gwt-uid-27")).sendKeys("1234567890");
        driver.findElement(By.xpath("//div[@id='gwt-uid-29']//div[.='Adicional']")).click();
        driver.findElement(By.id("gwt-uid-32")).click();
        driver.findElement(By.xpath("//div[@id='ROOT-2521314']/div/div[2]/div/div[2]/div/div/div/div[2]/div/div/div[3]/div/table/tbody/tr[2]/td[3]/div/div[1]/div")).click();
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
