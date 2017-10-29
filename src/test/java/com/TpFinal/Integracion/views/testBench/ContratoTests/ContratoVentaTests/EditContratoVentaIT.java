package com.TpFinal.Integracion.views.testBench.ContratoTests.ContratoVentaTests;

import com.TpFinal.Integracion.views.pageobjects.TBContratosView.TBContratoView;
import com.TpFinal.Integracion.views.pageobjects.TBLoginView;
import com.TpFinal.Integracion.views.pageobjects.TBMainView;
import com.TpFinal.Integracion.views.testBench.TBUtils;
import com.vaadin.testbench.Parameters;
import com.vaadin.testbench.ScreenshotOnFailureRule;
import com.vaadin.testbench.TestBenchTestCase;
import org.junit.*;

public class EditContratoVentaIT extends TestBenchTestCase{
	
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

	@Ignore
	public void test() {
		 getDriver().get(TBUtils.getUrl("contratos"));
         TBUtils.sleep(3000);
	     Assert.assertTrue(contratoView.isDisplayed());
	        
	      //Edit contrato
	      TBUtils.sleep(3000);
	      //contratoView.getEditButton("Accion 1").click();
	      TBUtils.sleep(3000);
	        
	  

		
		
		
	}

}
