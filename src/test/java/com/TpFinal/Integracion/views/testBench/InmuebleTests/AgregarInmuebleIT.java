package com.TpFinal.Integracion.views.testBench.InmuebleTests;

import com.TpFinal.Integracion.views.pageobjects.TBInmuebleView;
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

public class AgregarInmuebleIT extends TestBenchTestCase{

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

    @Ignore
    public void agregarPersonaTest(){
        getDriver().get(TBUtils.getUrl("inmuebles"));
        Assert.assertTrue(inmuebleView.getGrid().exists());

        //New inmueble Button
        inmuebleView.getNuevoInmuebleButton().first().click();

        //Tab "Datos Principales"
        //Adding new owner to the property
        /*inmuebleView.getNuevpPropietarioButton().first().click();
        TBPersonaInmueblePopupView personaInmueblePopupView = inmuebleView.getPersonaInmueblePopupView();
        personaInmueblePopupView.getNombreTextField().first().setValue("Aiko");
        personaInmueblePopupView.getApellidoTextField().first().setValue("Fulanito");
        personaInmueblePopupView.getdNITextField().first().setValue("33443432");
        personaInmueblePopupView.getEmailTextField().first().setValue("aikofulanito@gmail.com");
        personaInmueblePopupView.getTelfonoTextField().first().setValue("324432334");
        personaInmueblePopupView.getCelularTextField().first().setValue("32532523");
        personaInmueblePopupView.getInfoTextArea().first().setValue("Hola, mi nombre es aiko fulanito");
        personaInmueblePopupView.getGuardarPersonaButton().first().click();
        */
        //Rest of the data
        //address
        inmuebleView.getCalleTextField().first().setValue("Almagro");
        inmuebleView.getNumeroTextField().first().setValue("7777");
        inmuebleView.getProvinciaComboBox().first().selectByText("La Rioja");
        inmuebleView.getGuardarButton().first().click();


        //Tab "Caracteristicas"
        //With the validation failing, the form is automatically moved into the second tab
        inmuebleView.getTabSheet().first().openTab("Características");
        inmuebleView.getAmbientesTextField().first().setValue("6");
        inmuebleView.getCocherasTextField().first().setValue("90");
        inmuebleView.getDormitoriosTextField().first().setValue("5");
        inmuebleView.getSupTotalTextField().first().setValue("100000");
        inmuebleView.getSupCubiertaTextField().first().setValue("300000");


        //CheckBoxes
        inmuebleView.getAestrenarCheckBox().first().click();
        inmuebleView.getJardnCheckBox().first().click();
        inmuebleView.getParrillaCheckBox().first().click();
        inmuebleView.getAireAcondicionadoCheckBox().first().click();
        inmuebleView.getPiletaCheckBox().first().click();

        inmuebleView.getJardnCheckBox().first().click();
        inmuebleView.getParrillaCheckBox().first().click();

        inmuebleView.getGuardarButton().first().click();

        Assert.assertFalse(inmuebleView.isFormDisplayed());
   
    }


}