package com.TpFinal.Integracion.views.testBench.ContratoTests.ContratoVentaTests;

import com.TpFinal.Integracion.views.pageobjects.TBContratosView.TBContratoView;
import com.TpFinal.Integracion.views.pageobjects.TBLoginView;
import com.TpFinal.Integracion.views.pageobjects.TBMainView;
import com.TpFinal.Integracion.views.testBench.TBUtils;
import com.vaadin.testbench.Parameters;
import com.vaadin.testbench.ScreenshotOnFailureRule;
import com.vaadin.testbench.TestBenchTestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;

/**
 * Created by Max on 10/12/2017.
 */
public class ContratoVentaIT extends TestBenchTestCase{
	
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

    //TODO rearmar test
    @Ignore
    public void addContratoAlquilerTest(){
        getDriver().get(TBUtils.getUrl("contratos"));
       // TBUtils.sleep(3000);
        Assert.assertTrue(contratoView.isDisplayed());
        
        contratoView.getNuevaVentaButton().first().click();
        /*
      //ComboboxInmueble
        List<String> inmuebleList = contratoView.getInmuebleComboBoxVenta().first().getPopupSuggestions();
        String selectedInmueble = inmuebleList.get(2);
        contratoView.getInmuebleComboBoxVenta().first().selectByText(selectedInmueble);

        //ComboboxComprador
        List<String> compradorList = contratoView.getCompradorComboBox().first().getPopupSuggestions();
        String selectedComprador = compradorList.get(1);
        contratoView.getCompradorComboBox().first().selectByText(selectedComprador);

        //DateField Celebracion
        contratoView.getFechadeCelebracionDateFieldAlquiler().first().setValue("22/10/2017");
        
        //Valor de venta textfield
        contratoView.getValordeventaTextField().first().setValue("344");
        
        //Radio button de tipo de moneda
        contratoView.getTipoMonedaRadioButtonGroupVenta().first().selectByText("Dolares");
        
        //Boton de guardar
        contratoView.getGuardarButtonVenta().first().click();
        */

    }	

}
