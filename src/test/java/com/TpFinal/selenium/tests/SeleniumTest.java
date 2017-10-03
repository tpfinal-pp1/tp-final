package com.TpFinal.selenium.tests;

import org.apache.commons.lang3.SystemUtils;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;

/**
 * Created by Max on 10/2/2017.
 */
public class SeleniumTest {

    @Test
    public void seleniumTest(){
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
        WebDriver webDriver = new FirefoxDriver();
        webDriver.get("http://google.com");
        webDriver.close();
        //webDriver.quit();
    }
}
