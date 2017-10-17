package com.TpFinal.views;

import com.TpFinal.utils.Utils;
import com.TpFinal.views.pageobjects.TBLoginView;
import com.vaadin.testbench.Parameters;
import com.vaadin.testbench.ScreenshotOnFailureRule;
import com.vaadin.testbench.TestBench;
import com.vaadin.testbench.TestBenchTestCase;
import io.github.bonigarcia.wdm.PhantomJsDriverManager;
import org.junit.Rule;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;


public class TBUtils {

    private static final String TARGET_URL ="http://localhost:8080/?restartApplication=true";





    public static WebDriver initializeDriver() {

        PhantomJsDriverManager.getInstance().setup();
        WebDriver phantomJsDriver=new PhantomJSDriver();
        phantomJsDriver.manage().window().setSize(
                new Dimension(1920, 1080));
        WebDriver driver = TestBench.createDriver(phantomJsDriver);
        driver.get(TARGET_URL);

        return driver;
    }



    public static void closeDriver(WebDriver webDriver) throws Exception{
        if (webDriver == null) {
            return;
        }
        webDriver.quit();
        webDriver = null;
    }
    public static void sleep(int seconds){
        try {
            Thread.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static String getUrl(String ventana){

        return TARGET_URL+"#!"+ventana.toLowerCase();
    }


    public static TBLoginView loginView(WebDriver driver){
        driver.get(TARGET_URL);
        TBLoginView initialView = new TBLoginView(driver);
        return initialView;
    }



}
