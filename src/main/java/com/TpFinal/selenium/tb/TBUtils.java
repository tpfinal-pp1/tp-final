package com.TpFinal.selenium.tb;

import com.TpFinal.selenium.tb.pageobjects.TBLoginView;
import com.vaadin.testbench.TestBench;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;




public class TBUtils {

    private static final String TARGET_URL = "http://localhost:8080/?restartApplication=true";

    public static TBLoginView openInitialView() {
        WebDriver driver = TestBench.createDriver(new FirefoxDriver());
        driver.get(TARGET_URL);
        TBLoginView initialView = new TBLoginView(driver);
        return initialView;
    }

}
