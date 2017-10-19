package com.TpFinal.Integracion.views.testBench.ContratoTests.ContratoAlquilerTests;

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

/**
 * Created by Max on 10/12/2017.
 */
public class ContratoAlquilerIT extends TestBenchTestCase{

    private TBLoginView loginView;
    private TBMainView mainView;
    private TBContratoView contratoView;
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

        contratoView = mainView.getContratoView();
    }

    @Test
    public void addContratoAlquilerTest(){
        getDriver().get(TBUtils.getUrl("contratos"));
       // TBUtils.sleep(3000);
        Assert.assertTrue(contratoView.isDisplayed());


        //New Aqluiler
        contratoView.getNuevoAlquilerButton().first().click();

        //Tab "Datos principales"
        List<String> tabsheet1Options = contratoView.getTabSheet1().first().getTabCaptions();
        Assert.assertEquals(tabsheet1Options.get(0),"Alquiler");
        Assert.assertEquals(tabsheet1Options.get(1),"Condiciones");
        contratoView.getTabSheet1().first().openTab("Alquiler");

        //ComboboxInmueble
        List<String> inmuebleList = contratoView.getInmuebleComboBoxAlquiler().first().getPopupSuggestions();
        String selectedInmueble = inmuebleList.get(2);
        contratoView.getInmuebleComboBoxAlquiler().first().selectByText(selectedInmueble);

        //ComboboxInquilino
        List<String> inquilinoList = contratoView.getInquilinoComboBoxAlquiler().first().getPopupSuggestions();
        String selectedInquilino = inquilinoList.get(1);
        contratoView.getInquilinoComboBoxAlquiler().first().selectByText(selectedInquilino);

        //DateField Celebracion
        contratoView.getFechadeCelebracionDateFieldAlquiler().first().setValue("22/10/2017");

        //TODO Doc contrato
        contratoView.getEstadodocumentoTextFieldAlquiler().first();
        contratoView.getUploadDocAlquiler().first();
        contratoView.getDownloadContratoAlquiler().first();

        //Tab "condiciones"
        contratoView.getTabSheet1().first().openTab("Condiciones");

        //ComboDuracion
        List<String> durationList = contratoView.getDuracinComboBox().first().getPopupSuggestions();
        String selectedDuration = durationList.get(1);
        contratoView.getDuracinComboBox().first().selectByText(selectedDuration);

        //ComboInteres
        List<String> intereList = contratoView.getTipoInteresComboBox().first().getPopupSuggestions();
        String selectedIntere = intereList.get(1);
        contratoView.getTipoInteresComboBox().first().selectByText(selectedIntere);

        contratoView.getDiadePagoTextField().first().setValue("2");
        contratoView.getRecargoPunitorioTextField().first().setValue("2");
        contratoView.getAumentoporActualizacinTextField().first().setValue("4");

        //ComboInteresMonto IMPORTANTE USAR GET(1)
        List<String> interesList = contratoView.getTipoInteresComboBoxMonto().get(1).getPopupSuggestions();
        String selectedInteres = interesList.get(1);
        contratoView.getTipoInteresComboBoxMonto().get(1).selectByText(selectedInteres);

        contratoView.getValorInicialTextField().first().setValue("20000");

        contratoView.getTipoMonedaRadioButtonGroupAlquiler().first().selectByText("Pesos");

        contratoView.getGuardarButtonAlquiler().first().click();
        //TBUtils.sleep(3000);
        Assert.assertFalse(contratoView.isAlquilerFormDisplayed());



    }
}
