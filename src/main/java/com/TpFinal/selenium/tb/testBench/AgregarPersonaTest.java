package com.TpFinal.selenium.tb.testBench;

import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.ButtonElement;
import com.vaadin.testbench.elements.PasswordFieldElement;
import com.vaadin.testbench.elements.TextFieldElement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

/**
 * Created by Max on 10/11/2017.
 */
public class AgregarPersonaTest extends TestBenchTestCase{

    @Before
    public void setUp() throws Exception {

        setDriver( new FirefoxDriver());
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

    }


    @Test
    public void agregarPersonaTest(){
        getDriver().get("http://inmobi.ddns.net/");
        TextFieldElement usuarioTextField = $(TextFieldElement.class).caption("Usuario").first();
        usuarioTextField.setValue("Max");
        PasswordFieldElement contraseaPasswordField = $(PasswordFieldElement.class).caption("Contraseña").first();
        contraseaPasswordField.setValue("1234");
        ButtonElement iniciarSesinButton = $(ButtonElement.class).caption("Iniciar Sesión").first();
        iniciarSesinButton.click();
        ButtonElement personasButton = $(ButtonElement.class).caption("Personas").first();
        personasButton.click();
        getDriver().get("http://inmobi.ddns.net/#!personas");
        ButtonElement nuevoButton = $(ButtonElement.class).caption("Nuevo").first();
        nuevoButton.click();
        TextFieldElement nombreTextField = $(TextFieldElement.class).caption("Nombre").first();
        nombreTextField.setValue("Max");
        TextFieldElement apellidoTextField = $(TextFieldElement.class).caption("Apellido").first();
        apellidoTextField.setValue("Lencina");
        TextFieldElement dNITextField = $(TextFieldElement.class).caption("DNI").first();
        dNITextField.setValue("13256789");
        TextFieldElement mailTextField = $(TextFieldElement.class).caption("Mail").first();
        mailTextField.setValue("max@max.com");
        TextFieldElement telefonoTextField = $(TextFieldElement.class).caption("Telefono").first();
        telefonoTextField.setValue("45345678");
        TextFieldElement celularTextField = $(TextFieldElement.class).caption("Celular").first();
        celularTextField.setValue("1234669874");
        ButtonElement guardarButton = $(ButtonElement.class).caption("Guardar").first();
        guardarButton.click();
    }

    @After
    public void tearDown() throws Exception {
        getDriver().quit();
    }
}
