package com.TpFinal.selenium;

import org.apache.commons.lang3.SystemUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import javax.validation.constraints.AssertTrue;
import java.io.File;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

/**
 * Created by Max on 10/2/2017.
 */
public class SeleniumTest {


    FirefoxDriver wd;

    @Test
    public void testCrearPersona() {
        wd.get("http://inmobi.ddns.net/#!personas");
        assertTrue(wd.getTitle().equals("TpFinal"));
        wd.findElement(By.xpath("//div[@class='v-expand']/div/div/div[3]/div/div[5]/div")).click();
        wd.findElement(By.xpath("//div[@id='ROOT-2521314']/div/div[2]/div/div[2]/div/div/div/div[1]/div/div[3]/div/div[3]/div")).click();
        wd.findElement(By.xpath("//div[@id='ROOT-2521314']/div/div[2]/div/div[2]/div/div/div/div[2]/div/div/div[3]/div/table/tbody/tr[2]/td[3]/div/div[3]/div")).click();

    }


    @Before
    public void setUp() throws Exception {
        if(SystemUtils.IS_OS_LINUX){
            System.setProperty("webdriver.gecko.driver","GeckoDriver"+ File.separator+"geckodriver");
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
