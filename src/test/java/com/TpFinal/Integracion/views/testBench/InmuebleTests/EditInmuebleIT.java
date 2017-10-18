package com.TpFinal.Integracion.views.testBench.InmuebleTests;

import com.TpFinal.Integracion.views.pageobjects.TBInmuebleView;
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
 * Created by Max on 10/18/2017.
 */
public class EditInmuebleIT  extends TestBenchTestCase{


    private TBLoginView loginView;
    private TBMainView mainView;
    private TBInmuebleView inmuebleView;
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

        inmuebleView = mainView.getInmuebleView();

    }

    @Test
    public void editPersonaTest(){
        getDriver().get(TBUtils.getUrl("inmuebles"));
        Assert.assertTrue(inmuebleView.isDisplayed());

        //Edit persona
        TBUtils.sleep(5000);
        inmuebleView.getEditButton("Accion 1").click();
        TBUtils.sleep(5000);

        //Edit name
        inmuebleView.getCalleTextField().first().setValue("Falsa");
        inmuebleView.getNumeroTextField().first().setValue("123");
        inmuebleView.getGuardarButton().first().click();
        TBUtils.sleep(5000);

        Assert.assertFalse(inmuebleView.isFormDisplayed());
    }


}
