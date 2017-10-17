package com.TpFinal.Integracion.views.testBench.InmuebleTests;

import com.TpFinal.Integracion.views.testBench.TBUtils;
import com.TpFinal.Integracion.views.pageobjects.TBLoginView;
import com.TpFinal.Integracion.views.pageobjects.TBMainView;
import com.vaadin.testbench.Parameters;
import com.vaadin.testbench.ScreenshotOnFailureRule;
import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.*;
import org.junit.*;

public class AgregarInmuebleIT extends TestBenchTestCase{

    private TBLoginView loginView;
    private TBMainView mainView;
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

    }

    @Test
    public void agregarPersonaTest(){
        getDriver().get(TBUtils.getUrl("inmuebles"));
        Assert.assertTrue($(GridElement.class).exists());
        ButtonElement nuevoButton = $(ButtonElement.class).caption("Nuevo").first();
        nuevoButton.click();
        
        ButtonElement button1 = $(FormLayoutElement.class).$(ButtonElement.class).first();
        button1.click();
        
        TextFieldElement nombreTextField = $(TextFieldElement.class).caption("Nombre").first();
        nombreTextField.setValue("Aiko");
        
        TextFieldElement apellidoTextField = $(TextFieldElement.class).caption("Apellido").first();
        apellidoTextField.setValue("Fulanito");
        
        TextFieldElement dNITextField = $(TextFieldElement.class).caption("DNI").first();
        dNITextField.setValue("33443432");
                
        TextFieldElement emailTextField = $(TextFieldElement.class).caption("Email").first();
        emailTextField.setValue("aikofulanito@gmail.com"); 
        
        TextFieldElement telfonoTextField = $(TextFieldElement.class).caption("Teléfono").first();
        telfonoTextField.setValue("324432334");
                
        TextFieldElement celularTextField = $(TextFieldElement.class).caption("Celular").first();
        celularTextField.setValue("32532523"); 
        
        TextAreaElement infoTextArea = $(TextAreaElement.class).caption("Info").first();
        infoTextArea.setValue("soy infor adicional siendo testeada con testbench");
        
        ButtonElement guardarButton = $$(WindowElement.class).id("profilepreferenceswindow").$(ButtonElement.class).caption("Guardar Persona").first();
        guardarButton.click();
        
        //WindowElement profilepreferenceswindow = $$(WindowElement.class).id("profilepreferenceswindow");
        //profilepreferenceswindow.click();
        
        TextFieldElement calleTextField = $(TextFieldElement.class).caption("Calle").first();
        calleTextField.setValue("Almagro");
        
        TextFieldElement nmeroTextField = $(TextFieldElement.class).caption("Número").first();
        nmeroTextField.setValue("7777");
        
        //FIXME
        ComboBoxElement provinciaComboBox = $(ComboBoxElement.class).caption("Provincia").first();
        provinciaComboBox.selectByText("Buenos Aires");
        
        //FIXME
        ComboBoxElement localidadComboBox = $(ComboBoxElement.class).caption("Localidad").first();
        localidadComboBox.selectByText("Los Polvorines");
        
        ButtonElement guardarButton2 = $(ButtonElement.class).caption("Guardar").first();
        guardarButton2.click();     
   
    }


}