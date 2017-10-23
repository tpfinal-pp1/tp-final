package com.TpFinal.Integracion.views.testBench.CobroTests;

import com.TpFinal.Integracion.views.pageobjects.TBCobrosView.TBCobroView;
import com.TpFinal.Integracion.views.pageobjects.TBLoginView;
import com.TpFinal.Integracion.views.pageobjects.TBMainView;
import com.TpFinal.Integracion.views.testBench.TBUtils;
import com.vaadin.testbench.Parameters;
import com.vaadin.testbench.ScreenshotOnFailureRule;
import com.vaadin.testbench.TestBenchTestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Created by Max on 10/21/2017.
 */
public class CobroFilterIT extends TestBenchTestCase {

    private TBLoginView loginView;
    private TBMainView mainView;
    private TBCobroView cobroView;

    @Rule
    public ScreenshotOnFailureRule screenshotOnFailureRule =
            new ScreenshotOnFailureRule(this, true);

    @Before
    public void setUp() throws Exception {
        Parameters.setScreenshotErrorDirectory(
                "Files/errors");
        Parameters.setMaxScreenshotRetries(2);
        Parameters.setScreenshotComparisonTolerance(1.0);
        Parameters.setScreenshotRetryDelay(10);
        Parameters.setScreenshotComparisonCursorDetection(true);
        setDriver(TBUtils.initializeDriver());
        loginView = TBUtils.loginView(this.getDriver());
        mainView=loginView.login();

        cobroView = mainView.getCobroView();
    }

    @Test
    public void filterTest(){
        getDriver().get(TBUtils.getUrl("cobros"));
        TBUtils.sleep(2000);
        Assert.assertTrue(cobroView.isDisplayed());

        cobroView.getLupaButton().click();
        Assert.assertTrue(cobroView.isFilterWindowDisplayed());
        cobroView.getRadioButtonGroup1().first().selectByText("Cobrados");
        cobroView.getFiltrarWindow().first().close();

        Assert.assertFalse(cobroView.isFilterWindowDisplayed());

        Assert.assertEquals(cobroView.getGrid1().first().getRowCount(),0);

        cobroView.getLupaButton().click();
        Assert.assertTrue(cobroView.isFilterWindowDisplayed());
        cobroView.getRadioButtonGroup1().first().selectByText("Todos");
        cobroView.getFiltrarWindow().first().close();

        Assert.assertNotEquals(cobroView.getGrid1().first().getRowCount(),0);
        Assert.assertFalse(cobroView.isFilterWindowDisplayed());



    }
}
