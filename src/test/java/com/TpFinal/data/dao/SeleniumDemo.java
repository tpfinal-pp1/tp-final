package com.TpFinal.data.dao;



    import org.apache.commons.lang3.SystemUtils;
    import org.junit.After;
import org.junit.Before;
    import org.junit.Test;
import static org.junit.Assert.*;

    import java.io.File;
    import java.util.concurrent.TimeUnit;


    import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.*;

public class SeleniumDemo {
        FirefoxDriver driver;

        @Before
        public void setUp() throws Exception {

            if(SystemUtils.IS_OS_LINUX){
                System.setProperty("webdriver.gecko.driver","/usr/bin/geckodriver");
                System.out.println("OS: LINUX");
            }
            if(SystemUtils.IS_OS_WINDOWS) {
                System.setProperty("webdriver.gecko.driver", "GeckoDriver/geckodriver.exe");
                System.out.println("OS: Windows");
            }
            driver = new FirefoxDriver();
           // driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        }

        @Test
        public void test(){
            driver.get("http://inmobi.ddns.net/");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        assertTrue(driver.getTitle().contains("Inmobi"));
    }


    public void test1 () {
            driver.get("http://inmobi.ddns.net/");
            driver.findElement(By.xpath("//div[@class='v-expand']/div/div/div[3]/div/div[5]/div")).click();
            driver.findElement(By.xpath("//div[@id='dashboard-menu']//span[.='Personas']")).click();
            driver.findElement(By.xpath("//div[@id='ROOT-2521314']/div/div[2]/div/div[2]/div/div/div/div[1]/div/div[3]/div/div[3]/div")).click();
            driver.findElement(By.id("gwt-uid-17")).click();
            driver.findElement(By.id("gwt-uid-17")).clear();
            driver.findElement(By.id("gwt-uid-17")).sendKeys("Sel");
            driver.findElement(By.id("gwt-uid-19")).click();
            driver.findElement(By.id("gwt-uid-19")).clear();
            driver.findElement(By.id("gwt-uid-19")).sendKeys("Enium");
            driver.findElement(By.id("gwt-uid-21")).click();
            driver.findElement(By.id("gwt-uid-21")).clear();
            driver.findElement(By.id("gwt-uid-21")).sendKeys("sele@nium.com");
            driver.findElement(By.id("gwt-uid-23")).click();
            driver.findElement(By.id("gwt-uid-23")).clear();
            driver.findElement(By.id("gwt-uid-23")).sendKeys("21234567");
            driver.findElement(By.xpath("//div[@id='gwt-uid-25']//div[.='Contacto']")).click();
            driver.findElement(By.id("gwt-uid-27")).click();
            driver.findElement(By.id("gwt-uid-27")).clear();
            driver.findElement(By.id("gwt-uid-27")).sendKeys("12345435");
            driver.findElement(By.id("gwt-uid-29")).click();
            driver.findElement(By.id("gwt-uid-29")).clear();
            driver.findElement(By.id("gwt-uid-29")).sendKeys("234234324");
            driver.findElement(By.id("gwt-uid-31")).click();
            driver.findElement(By.id("gwt-uid-31")).clear();
            driver.findElement(By.id("gwt-uid-31")).sendKeys("Hola soy Selenium y estoy testeando");
            driver.findElement(By.xpath("//div[@id='ROOT-2521314']/div/div[2]/div/div[2]/div/div/div/div[2]/div/div/div[3]/div/table/tbody/tr[2]/td[3]/div/div[1]/div")).click();
        }

        @After
        public void tearDown() {
            driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
            driver.quit();
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
