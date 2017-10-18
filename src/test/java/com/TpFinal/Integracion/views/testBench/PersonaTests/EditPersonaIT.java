package com.TpFinal.Integracion.views.testBench.PersonaTests;

import com.TpFinal.Integracion.views.pageobjects.TBLoginView;
import com.TpFinal.Integracion.views.pageobjects.TBMainView;
import com.TpFinal.Integracion.views.pageobjects.TBPersonaView;
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
public class EditPersonaIT extends TestBenchTestCase {

    private TBLoginView loginView;
    private TBMainView mainView;
    private TBPersonaView personaView;

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

        personaView = mainView.getPersonaView();

    }

    @Test
    public void editPersonaTest(){
        getDriver().get(TBUtils.getUrl("personas"));
        TBUtils.sleep(1000);
        Assert.assertTrue(personaView.isDisplayed());

        //Edit persona
        personaView.getEditButton("Accion 2").click();
        TBUtils.sleep(5000);

        //Edit name
        personaView.getNameTextField().first().setValue("Cosme");
        personaView.getGuardarButton().first().click();
        TBUtils.sleep(5000);

        Assert.assertFalse(personaView.isFormDisplayed());
    }
}
