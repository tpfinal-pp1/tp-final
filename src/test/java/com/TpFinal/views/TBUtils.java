package com.TpFinal.views;

import com.TpFinal.views.pageobjects.TBLoginView;
import com.vaadin.testbench.TestBench;
import io.github.bonigarcia.wdm.PhantomJsDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;


public class TBUtils {

    private static final String RESTART_URL ="http://localhost:8080/?restartApplication=true";
    private static final String NORMAL_URL ="http://localhost:8080/";




    public static WebDriver initializeDriver() {

        PhantomJsDriverManager.getInstance().setup();
        WebDriver phantomJsDriver=new PhantomJSDriver();
        phantomJsDriver.manage().window().setSize(
                new Dimension(1920, 1080));
        WebDriver driver = TestBench.createDriver(phantomJsDriver);
        driver.get(RESTART_URL);

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

        return NORMAL_URL +"#!"+ventana.toLowerCase();
    }


    public static TBLoginView loginView(WebDriver driver){
        driver.get(RESTART_URL);
        TBLoginView initialView = new TBLoginView(driver);
        return initialView;
    }



}
