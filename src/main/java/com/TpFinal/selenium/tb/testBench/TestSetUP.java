package com.TpFinal.selenium.tb.testBench;

import com.vaadin.testbench.TestBenchTestCase;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

/**
 * Created by Max on 10/11/2017.
 */
public class TestSetUP extends TestBenchTestCase{

    @Before
    public void setUp() throws Exception {

        setDriver( new FirefoxDriver());
        getDriver().manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

    }

    @After
    public void tearDown() throws Exception {
        getDriver().quit();
    }
}
