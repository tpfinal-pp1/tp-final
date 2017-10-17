package com.TpFinal.views;

import com.TpFinal.views.pageobjects.TBLoginView;
import com.TpFinal.views.pageobjects.TBMainView;
import com.vaadin.testbench.Parameters;
import com.vaadin.testbench.ScreenshotOnFailureRule;
import io.github.bonigarcia.wdm.PhantomJsDriverManager;
import org.junit.*;


import com.vaadin.testbench.TestBenchTestCase;
import org.openqa.selenium.WebDriver;

public class MainViewIT extends TestBenchTestCase {

    private TBLoginView loginView;
    private TBMainView mainView;

    @Rule
    public ScreenshotOnFailureRule screenshotOnFailureRule =
            new ScreenshotOnFailureRule(this, true);
    @Before
    public void setUp() throws Exception {
        Parameters.setScreenshotErrorDirectory(
                "File/errors");
        Parameters.setMaxScreenshotRetries(2);
        Parameters.setScreenshotComparisonTolerance(1.0);
        Parameters.setScreenshotRetryDelay(10);
        Parameters.setScreenshotComparisonCursorDetection(true);
        setDriver(TBUtils.initializeDriver());
        loginView = TBUtils.loginView(this.getDriver());
        mainView=loginView.login();

    }

  /*  @After
    public void tearDown() {

        try {
            TBUtils.closeDriver(loginView.getDriver());
        } catch (Exception e) {
            System.out.println("No se pudo cerrar");
        }
    }*/
    @Test
    public void testProfileName() {
        TBUtils.sleep(1000);
        Assert.assertTrue(mainView.getUserFullName().contains("test"));
    }

}
