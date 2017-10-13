package com.TpFinal.selenium.tb.testBench.InmuebleTests;

import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

public class AgregarInmuebleTest extends TestBenchTestCase{

    @Before
    public void setUp() throws Exception {

        setDriver( new FirefoxDriver());
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

    }


    @Test
    public void agregarPersonaTest(){
        getDriver().get("http://inmobi.ddns.net/");
        
        TextFieldElement usuarioTextField = $(TextFieldElement.class).caption("Usuario").first();
        usuarioTextField.setValue("Misa");
        PasswordFieldElement contraseaPasswordField = $(PasswordFieldElement.class).caption("Contraseña").first();
        contraseaPasswordField.setValue("7777");        
        ButtonElement iniciarSesinButton = $(ButtonElement.class).caption("Iniciar Sesión").first();
        iniciarSesinButton.click();
        
        getDriver().get("http://inmobi.ddns.net/#!inmuebles");
        
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

    @After
    public void tearDown() throws Exception {
        getDriver().quit();
    }
}