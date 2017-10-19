package com.TpFinal.Integracion.views.testBench.PublicacionTests;

import com.TpFinal.Integracion.views.pageobjects.TBLoginView;
import com.TpFinal.Integracion.views.pageobjects.TBMainView;
import com.TpFinal.Integracion.views.pageobjects.TBPublicacionView;
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
public class EditPublicacionIT extends TestBenchTestCase{

    private TBLoginView loginView;
    private TBMainView mainView;
    private TBPublicacionView publicacionView;
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

        publicacionView = mainView.getPublicacionView();
    }

    @Test
    public void addPublicacionTest(){
        getDriver().get(TBUtils.getUrl("publicaciones"));
        TBUtils.sleep(3000);
        Assert.assertTrue(publicacionView.isDisplayed());


        //Edit Publicacion
        TBUtils.sleep(3000);
        publicacionView.getEditButton("Accion 0").click();

        TBUtils.sleep(3000);

        //Edit name
        publicacionView.getMontoTextField().first().setValue("2000000000000");
        publicacionView.getGuardarButton().first().click();
        TBUtils.sleep(3000);

        Assert.assertFalse(publicacionView.isFormDisplayed());
    }


}
