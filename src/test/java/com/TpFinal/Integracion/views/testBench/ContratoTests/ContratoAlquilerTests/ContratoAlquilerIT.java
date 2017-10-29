package com.TpFinal.Integracion.views.testBench.ContratoTests.ContratoAlquilerTests;

import com.TpFinal.Integracion.views.pageobjects.TBContratosView.TBContratoAlquilerForm;
import com.TpFinal.Integracion.views.pageobjects.TBContratosView.TBContratoView;
import com.TpFinal.Integracion.views.pageobjects.TBLoginView;
import com.TpFinal.Integracion.views.pageobjects.TBMainView;
import com.TpFinal.Integracion.views.testBench.TBUtils;
import com.vaadin.testbench.Parameters;
import com.vaadin.testbench.ScreenshotOnFailureRule;
import com.vaadin.testbench.TestBenchTestCase;
import org.junit.*;

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
       // TBUtils.sleep(3000);
        Assert.assertTrue(contratoView.isDisplayed());


        //New Aqluiler
        contratoView.getNuevoAlquilerButton().first().click();

        TBContratoAlquilerForm  contratoAlquilerForm = new TBContratoAlquilerForm(getDriver());

        //Tab "Datos principales"
        List<String> tabsheet1Options = contratoAlquilerForm.getTabSheet1().first().getTabCaptions();
        Assert.assertEquals(tabsheet1Options.get(0),"Alquiler");
        Assert.assertEquals(tabsheet1Options.get(1),"Condiciones");
        contratoAlquilerForm.getTabSheet1().first().openTab("Alquiler");

        //ComboboxInmueble
        List<String> inmuebleList = contratoAlquilerForm.getInmuebleComboBox().first().getPopupSuggestions();
        String selectedInmueble = inmuebleList.get(indexCombo);
        contratoAlquilerForm.getInmuebleComboBox().first().selectByText("calle 149 n°5300, Antiguo, Jujuy");

        //ComboboxInquilino
        List<String> inquilinoList = contratoAlquilerForm.getInquilinoComboBox().first().getPopupSuggestions();
        String selectedInquilino = inquilinoList.get(indexCombo);
        contratoAlquilerForm.getInquilinoComboBox().first().selectByText("Rosalina Marilou");

        //DateField Celebracion
        contratoAlquilerForm.getFechadeCelebracionDateField().first().setValue("22/10/2017");

        //TODO Doc contrato
        contratoAlquilerForm.getEstadodocumentoTextField().first();
        contratoAlquilerForm.getUploadButton().first();
        contratoAlquilerForm.getDownlooadButton().first();


        //Tab "condiciones"
        contratoAlquilerForm.getTabSheet1().first().openTab("Condiciones");

        //ComboDuracion
        List<String> durationList = contratoAlquilerForm.getDuracinComboBox().first().getPopupSuggestions();
        String selectedDuration = durationList.get(0);
        contratoAlquilerForm.getDuracinComboBox().first().selectByText("14 Meses");

        //textfield punitario
        contratoAlquilerForm.getFrecuenciadeIncrementomesesTextField().first().setValue("2");

        
        //ComboInteres
        List<String> intereList = contratoAlquilerForm.getTipoInteres().getPopupSuggestions();
        String selectedIntere = intereList.get(1);
        contratoAlquilerForm.getTipoInteres().selectByText(selectedIntere);

        contratoAlquilerForm.getDadePagoTextField().first().setValue("2");

        contratoAlquilerForm.getRecargoPunitorioTextField().first().setValue("2");
        contratoAlquilerForm.getAumentoporActualizacinTextField().first().setValue("4");

        //ComboInteresMonto
        List<String> interesList = contratoAlquilerForm.getTipoInteresMonto().getPopupSuggestions();
        String selectedInteres = interesList.get(1);
        contratoAlquilerForm.getTipoInteresMonto().selectByText(selectedInteres);

        //Seteando valor inicial
        contratoAlquilerForm.getValorInicialTextField().first().setValue("20000");

        contratoAlquilerForm.getTipoMonedaRadioButtonGroup().first().selectByText("Pesos");

        contratoAlquilerForm.getGuardarButton().first().click();
        
        TBUtils.sleep(3000);

        Assert.assertFalse(contratoAlquilerForm.isDisplayed());



    }
}
