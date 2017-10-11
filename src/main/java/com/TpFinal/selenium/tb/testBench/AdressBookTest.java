package com.TpFinal.selenium.tb.testBench;

import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.ButtonElement;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

/**
 * Created by Max on 10/11/2017.
 */
public class AdressBookTest extends TestBenchTestCase{

    @Before
    public void setUp() throws Exception {

        setDriver( new FirefoxDriver());
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

    }


        @Test
        public void testFormShowsCorrectData() {
            getDriver().get("http://inmobi.ddns.net/");
            ButtonElement iniciarSesinButton = $(ButtonElement.class).caption("Iniciar Sesión").first();
            //iniciarSesinButton.click();
            String buttonName = iniciarSesinButton.getText();
            // 4. Assert that the values in the first name and
            // last name fields are the same as in the grid
            Assert.assertEquals(buttonName, "Iniciar Sesión");

        }




    @After
    public void tearDown() throws Exception {
        getDriver().quit();
    }
}
