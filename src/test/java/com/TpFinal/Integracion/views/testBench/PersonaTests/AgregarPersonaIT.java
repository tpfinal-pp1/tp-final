package com.TpFinal.Integracion.views.testBench.PersonaTests;

import com.TpFinal.Integracion.views.pageobjects.TBBusquedaInteresadoView;
import com.TpFinal.Integracion.views.pageobjects.TBLoginView;
import com.TpFinal.Integracion.views.pageobjects.TBMainView;
import com.TpFinal.Integracion.views.pageobjects.TBPersonaView;
import com.TpFinal.Integracion.views.testBench.TBUtils;
import com.vaadin.testbench.Parameters;
import com.vaadin.testbench.ScreenshotOnFailureRule;
import com.vaadin.testbench.TestBenchTestCase;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

/**
 * Created by Max on 10/11/2017.
 */
public class AgregarPersonaIT extends TestBenchTestCase{

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
    public void agregarPersonaTest(){
        getDriver().get(TBUtils.getUrl("personas"));

        TBUtils.sleep(3000);
        Assert.assertTrue(personaView.isDisplayed());
        personaView.getNuevaPersonaButton().first().click();
        personaView.getNameTextField().first().setValue("Max");
        personaView.getSurnameTextField().first().setValue("Lencina");
        personaView.getDNITextField().first().setValue("13256789");
        personaView.getMailTextField().first().setValue("max@max.com");
        personaView.getTelefonoTextField().first().setValue("45345678");
        personaView.getCelularTextField().first().setValue("1234669874");
        personaView.getGuardarButton().first().click();
        TBUtils.sleep(3000);
        Assert.assertFalse(personaView.isFormDisplayed());
        
        
    }
    
  
    	

}
