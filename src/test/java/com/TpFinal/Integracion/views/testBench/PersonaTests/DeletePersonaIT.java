package com.TpFinal.Integracion.views.testBench.PersonaTests;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.TpFinal.Integracion.views.pageobjects.TBLoginView;
import com.TpFinal.Integracion.views.pageobjects.TBMainView;
import com.TpFinal.Integracion.views.pageobjects.TBPersonaView;
import com.TpFinal.Integracion.views.testBench.TBUtils;
import com.vaadin.testbench.Parameters;
import com.vaadin.testbench.ScreenshotOnFailureRule;
import com.vaadin.testbench.TestBenchTestCase;

public class DeletePersonaIT extends TestBenchTestCase{
	
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
    
    //TODO
	@Test
	public void test() {
		getDriver().get(TBUtils.getUrl("personas"));
        TBUtils.sleep(3000);
        Assert.assertTrue(personaView.isDisplayed());

        TBUtils.sleep(3000);
        personaView.getRemoveButton("Accion 0").click();
        
        //TODO
	}

}
