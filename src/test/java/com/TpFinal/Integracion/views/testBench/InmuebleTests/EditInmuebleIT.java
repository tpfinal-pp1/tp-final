package com.TpFinal.Integracion.views.testBench.InmuebleTests;

import com.TpFinal.Integracion.views.pageobjects.TBInmuebleView;
import com.TpFinal.Integracion.views.pageobjects.TBLoginView;
import com.TpFinal.Integracion.views.pageobjects.TBMainView;
import com.TpFinal.Integracion.views.testBench.TBUtils;
import com.vaadin.testbench.Parameters;
import com.vaadin.testbench.ScreenshotOnFailureRule;
import com.vaadin.testbench.TestBenchTestCase;

import java.util.List;

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
                "Files/errors");
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
        TBUtils.sleep(8000);
        Assert.assertTrue(inmuebleView.isDisplayed());

        //Edit inmueble
        TBUtils.sleep(6000);
        inmuebleView.getEditButton("Accion 1").click();
        TBUtils.sleep(6000);

        //Edit name
        inmuebleView.getCalleTextField().first().setValue("Falsa");
        inmuebleView.getNumeroTextField().first().setValue("123");
        
        //Combobox provincias
        List<String> provinces = inmuebleView.getProvinciaComboBox().first().getPopupSuggestions();
        String selectedProvince = provinces.get(1);
        inmuebleView.getProvinciaComboBox().first().selectByText(selectedProvince);
        
        List<String> localidades = inmuebleView.getLocalidadComboBox().first().getPopupSuggestions();
        String localidadSelected = localidades.get(1);
        inmuebleView.getLocalidadComboBox().first().selectByText(localidadSelected);
        
        //Seteando codigo postal
        inmuebleView.getCodigopostalTextField().first().setValue("32433334");
        
        List<String> clases = inmuebleView.getClaseComboBox().first().getPopupSuggestions();
        String claseSelected = clases.get(3);
        inmuebleView.getClaseComboBox().first().selectByText(claseSelected);
        
      //Tab "Caracteristicas"
        inmuebleView.getTabSheet().first().openTab("Características");
        inmuebleView.getAmbientesTextField().first().setValue("222");
        inmuebleView.getCocherasTextField().first().setValue("333");
        inmuebleView.getDormitoriosTextField().first().setValue("225");
        inmuebleView.getSupTotalTextField().first().setValue("1002");
        inmuebleView.getSupCubiertaTextField().first().setValue("30222220");

        inmuebleView.getAireAcondicionadoCheckBox().first().click();
        inmuebleView.getAestrenarCheckBox().first().click();
        
        inmuebleView.getGuardarButton().first().click();
        TBUtils.sleep(3000);
        
        

        Assert.assertFalse(inmuebleView.isFormDisplayed());
    }


}
