package com.TpFinal.Integracion.views.testBench;

import com.TpFinal.Integracion.views.pageobjects.TBLoginView;
import com.TpFinal.Integracion.views.pageobjects.TBMainView;
import com.vaadin.testbench.Parameters;
import com.vaadin.testbench.ScreenshotOnFailureRule;
import org.junit.*;


import com.vaadin.testbench.TestBenchTestCase;

public class LoginViewIT extends TestBenchTestCase {

    private TBLoginView loginView;
    @Rule
    public ScreenshotOnFailureRule screenshotOnFailureRule =
            new ScreenshotOnFailureRule(this, true);
    @Before
    public void setUp() {
        Parameters.setScreenshotErrorDirectory(
                "Files/errors");
        Parameters.setMaxScreenshotRetries(2);
        Parameters.setScreenshotComparisonTolerance(1.0);
        Parameters.setScreenshotRetryDelay(10);
        Parameters.setScreenshotComparisonCursorDetection(true);
        loginView = TBUtils.loginView(TBUtils.initializeDriver());
    }

    @Test
    public void testLoginLogout() {
        Assert.assertTrue(loginView.isDisplayed());

        TBMainView mainView = loginView.login();
        Assert.assertTrue(mainView.isDisplayed());

        mainView.logout();
        Assert.assertTrue(loginView.isDisplayed());
    }

    @After
    public void tearDown() {
        loginView.getDriver().quit();
    }
}
