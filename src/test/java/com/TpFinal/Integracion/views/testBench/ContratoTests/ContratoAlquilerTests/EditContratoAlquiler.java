package com.TpFinal.Integracion.views.testBench.ContratoTests.ContratoAlquilerTests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import com.TpFinal.Integracion.views.pageobjects.TBContratoAlquilerForm;
import com.TpFinal.Integracion.views.pageobjects.TBContratoView;
import com.TpFinal.Integracion.views.pageobjects.TBLoginView;
import com.TpFinal.Integracion.views.pageobjects.TBMainView;
import com.TpFinal.Integracion.views.testBench.TBUtils;
import com.vaadin.testbench.Parameters;
import com.vaadin.testbench.ScreenshotOnFailureRule;
import com.vaadin.testbench.TestBenchTestCase;

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

    @Ignore
    public void addContratoAlquilerTest(){
        int indexCombo = 1;
        getDriver().get(TBUtils.getUrl("contratos"));
        
        Assert.assertTrue(contratoView.isDisplayed());


        TBUtils.sleep(4000);
        contratoView.getFilterTextField().first().setValue("eun zachariah");
        TBUtils.sleep(4000);
        
        //La del conflicto
        contratoView.getEditButton("Accion 2").click();
        
        TBContratoAlquilerForm  contratoAlquilerForm = new TBContratoAlquilerForm(getDriver());
        
        //Tab "Datos principales"
        List<String> tabsheet1Options = contratoAlquilerForm.getTabSheet1().first().getTabCaptions();
        Assert.assertEquals(tabsheet1Options.get(0),"Alquiler");
        Assert.assertEquals(tabsheet1Options.get(1),"Condiciones");
        contratoAlquilerForm.getTabSheet1().first().openTab("Alquiler");

        //ComboboxInmueble
        List<String> inmuebleList = contratoAlquilerForm.getInmuebleComboBox().first().getPopupSuggestions();
        String selectedInmueble = inmuebleList.get(indexCombo);
        contratoAlquilerForm.getInmuebleComboBox().first().selectByText("calle 194 n°3700, Calderilla, Salta");

        //ComboboxInquilino
        List<String> inquilinoList = contratoAlquilerForm.getInquilinoComboBox().first().getPopupSuggestions();
        String selectedInquilino = inquilinoList.get(indexCombo);
        contratoAlquilerForm.getInquilinoComboBox().first().selectByText("Raeann Morris");

        //DateField Celebracion
        contratoAlquilerForm.getFechadeCelebracionDateField().first().setValue("5/11/2017");

        //TODO Doc contrato
        contratoAlquilerForm.getEstadodocumentoTextField().first();
        contratoAlquilerForm.getUploadButton().first();
        contratoAlquilerForm.getDownlooadButton().first();


        //Tab "condiciones"
        contratoAlquilerForm.getTabSheet1().first().openTab("Condiciones");

        //ComboDuracion
        List<String> durationList = contratoAlquilerForm.getDuracinComboBox().first().getPopupSuggestions();
        String selectedDuration = durationList.get(0);
        contratoAlquilerForm.getDuracinComboBox().first().selectByText("64 Meses");

        //textfield punitario
        contratoAlquilerForm.getFrecuenciadeIncrementomesesTextField().first().setValue("32");

        
        //ComboInteres
        List<String> intereList = contratoAlquilerForm.getTipoInteres().getPopupSuggestions();
        String selectedIntere = intereList.get(1);
        contratoAlquilerForm.getTipoInteres().selectByText(selectedIntere);

        contratoAlquilerForm.getDadePagoTextField().first().setValue("3");

        contratoAlquilerForm.getRecargoPunitorioTextField().first().setValue("3");
        contratoAlquilerForm.getAumentoporActualizacinTextField().first().setValue("5");

        //ComboInteresMonto
        List<String> interesList = contratoAlquilerForm.getTipoInteresMonto().getPopupSuggestions();
        String selectedInteres = interesList.get(1);
        contratoAlquilerForm.getTipoInteresMonto().selectByText(selectedInteres);

        contratoAlquilerForm.getValorInicialTextField().first().setValue("77777");

        contratoAlquilerForm.getTipoMonedaRadioButtonGroup().first().selectByText("Dolares");

        contratoAlquilerForm.getGuardarButton().first().click();
        
        TBUtils.sleep(3000);

        Assert.assertFalse(contratoAlquilerForm.isDisplayed());



    }
}

