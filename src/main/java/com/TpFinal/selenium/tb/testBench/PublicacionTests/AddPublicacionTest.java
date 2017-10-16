package com.TpFinal.selenium.tb.testBench.PublicacionTests;

import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.*;
import org.apache.commons.lang3.SystemUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * Created by Max on 10/12/2017.
 */
public class AddPublicacionTest extends TestBenchTestCase {


    @Before
    public void setUp() throws Exception {
        if(SystemUtils.IS_OS_LINUX){
            System.setProperty("webdriver.gecko.driver","GeckoDriver"+ File.separator+"geckodriver");
            System.out.println("OS: LINUX");
        }
        if(SystemUtils.IS_OS_WINDOWS) {
            String systemArchitecture = System.getProperty("sun.arch.data.model");
            if(systemArchitecture.equals("64")) {
                System.setProperty("webdriver.gecko.driver", "GeckoDriver" + File.separator + "geckodriver.exe");

                System.out.println("OS: Windows 64-bit");
            }
            else {
                System.setProperty("webdriver.gecko.driver", "GeckoDriver-32-bits" + File.separator + "geckodriver.exe");
                System.out.println("OS: Windows 32-bit");
            }
        }
        String userHomeDirectory = System.getenv("ProgramFiles");
        FirefoxBinary binary = new FirefoxBinary(new File(userHomeDirectory + File.separator + "Mozilla Firefox" +File.separator + "firefox.exe"));
        FirefoxProfile firefoxProfile = new FirefoxProfile();
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        // Enable legacy mode
        capabilities.setCapability(FirefoxDriver.MARIONETTE, false);

        driver = new FirefoxDriver(binary,firefoxProfile,capabilities);
        setDriver(driver);
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

    }

    @Test
    public void addPublicacionTest(){
        //getDriver().get("http://inmobi.ddns.net/");
        getDriver().get("http://localhost:8080/");

        //Login screen (TextFields and Login Button)
        TextFieldElement usuarioTextField = $(TextFieldElement.class).caption("Usuario").first();
        usuarioTextField.setValue("Max");
        PasswordFieldElement contraseaPasswordField = $(PasswordFieldElement.class).caption("Contraseña").first();
        contraseaPasswordField.setValue("1234");
        ButtonElement iniciarSesinButton = $(ButtonElement.class).caption("Iniciar Sesión").first();
        //Check if the Login fields are filled with credentials.
        Assert.assertEquals($(TextFieldElement.class).caption("Usuario").first().getValue(),"Max");
        Assert.assertEquals($(PasswordFieldElement.class).caption("Contraseña").first().getValue(),"1234");
        Assert.assertEquals(usuarioTextField.getValue(),"Max");
        Assert.assertEquals(contraseaPasswordField.getValue(),"1234");
        iniciarSesinButton.click();

        ButtonElement publicacionesButton = $(ButtonElement.class).caption("Publicaciones").first();
        publicacionesButton.click();
        getDriver().get("http://localhost:8080/#!publicaciones");
        GridElement inmuebleGrid = $(GridElement.class).first();



        ButtonElement nuevaButton = $(ButtonElement.class).caption("Nueva").first();
        nuevaButton.click();
        FormLayoutElement formLayout1 = $(FormLayoutElement.class).$(FormLayoutElement.class).first();

        RadioButtonGroupElement radioButtonGroup = $(RadioButtonGroupElement.class).caption(" ").first();
        radioButtonGroup.selectByText("Venta");
        DateFieldElement fechapublicacionDateField = $(DateFieldElement.class).caption("Fecha publicacion").first();
        fechapublicacionDateField.setValue("21/10/2017");
        RadioButtonGroupElement estadodelapublicacionRadioButtonGroup = $(RadioButtonGroupElement.class).caption("Estado de la publicacion").first();
        estadodelapublicacionRadioButtonGroup.selectByText("Terminada");

        ComboBoxElement inmuebleComboBox = $(ComboBoxElement.class).caption("Inmueble").first();

        TextFieldElement montoTextField = $(TextFieldElement.class).caption("Monto").first();
        montoTextField.setValue("200000");
        ComboBoxElement monedaComboBox = $(ComboBoxElement.class).caption("Moneda").first();

        ButtonElement guardarButton = $(ButtonElement.class).caption("Guardar").first();
        ButtonElement cancelButton = $(VerticalLayoutElement.class).$(ButtonElement.class).first();
        ButtonElement llenarcomboButton = $(ButtonElement.class).caption("llenar combo").first();
        llenarcomboButton.click();

        guardarButton.click();
    }

    @After
    public void tearDown() throws Exception {
        getDriver().quit();
    }
}
