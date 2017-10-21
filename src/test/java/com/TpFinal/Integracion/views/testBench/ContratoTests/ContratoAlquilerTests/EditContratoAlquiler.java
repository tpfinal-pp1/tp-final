package com.TpFinal.Integracion.views.testBench.ContratoTests.ContratoAlquilerTests;

import com.TpFinal.Integracion.views.pageobjects.TBContratoAlquilerForm;
import com.TpFinal.Integracion.views.pageobjects.TBContratoView;
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

import java.util.List;

public class EditContratoAlquiler extends TestBenchTestCase{

    private TBLoginView loginView;
    private TBMainView mainView;
    private TBContratoView contratoView;
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

        contratoView = mainView.getContratoView();
    }

    @Test
    public void addContratoAlquilerTest(){
        int indexCombo = 1;
        getDriver().get(TBUtils.getUrl("contratos"));
        TBUtils.sleep(3000);
        Assert.assertTrue(contratoView.isDisplayed());


        contratoView.getCancelFilterButton().first().click();
        TBUtils.sleep(4000);

        //La del conflicto
        contratoView.getEditButton("Accion 0").click();
        
        TBContratoAlquilerForm  contratoAlquilerForm = new TBContratoAlquilerForm(getDriver());
        
        //Tab "Datos principales"
        List<String> tabsheet1Options = contratoAlquilerForm.getTabSheet1().first().getTabCaptions();
        Assert.assertEquals(tabsheet1Options.get(0),"Alquiler");
        Assert.assertEquals(tabsheet1Options.get(1),"Condiciones");
        contratoAlquilerForm.getTabSheet1().first().openTab("Alquiler");


        //Tab "condiciones"
        contratoAlquilerForm.getTabSheet1().first().openTab("Condiciones");

        
        TBUtils.sleep(3000);

        contratoAlquilerForm.getCancel().first().click();
        Assert.assertFalse(contratoAlquilerForm.isDisplayed());



    }
}

