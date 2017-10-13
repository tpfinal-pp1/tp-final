package com.TpFinal.selenium.tb.testBench.InmuebleTests;

import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Max on 10/12/2017.
 */
public class AgregadoInmuebleTest extends TestBenchTestCase {


    @Before
    public void setUp() throws Exception {

        setDriver( new FirefoxDriver());
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

    }

//TODO completar TEST (MAX)
    @Test
    public void agregadoInmuebleTest(){
        getDriver().get("http://inmobi.ddns.net/");

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

        //Main View
        ButtonElement inmueblesButton = $(ButtonElement.class).caption("Inmuebles").first();

        //Inmuebles View
        getDriver().get("http://inmobi.ddns.net/#!inmuebles");
        //Tab "Datos principales"
        TabSheetElement tabSheet1 = $(TabSheetElement.class).first();
        List<String> tabsheet1Options = $(TabSheetElement.class).first().getTabCaptions();
        Assert.assertEquals(tabsheet1Options.get(0),"Datos Personales");
        Assert.assertEquals(tabsheet1Options.get(1),"Características");
        tabSheet1.openTab("Datos Personales");

        //Agregar inmueble
        ButtonElement nuevoButton = $(ButtonElement.class).caption("Nuevo").first();
        nuevoButton.click();
        //ComboPersonas
        ComboBoxElement personasComboBox = $(ComboBoxElement.class).first();
        List<String> comboValues = personasComboBox.getPopupSuggestions();
        comboValues.forEach(System.out::println);
        //ComboClase Inmueble
        ComboBoxElement claseComboBox = $(ComboBoxElement.class).caption("Clase").first();
        List<String> comboClaseValues = claseComboBox.getPopupSuggestions();
        comboClaseValues.forEach(System.out::println);

        TextFieldElement calleTextField = $(TextFieldElement.class).caption("Calle").first();
        TextFieldElement nmeroTextField = $(TextFieldElement.class).caption("Número").first();
        ComboBoxElement provinciaComboBox = $(ComboBoxElement.class).caption("Provincia").first();
        ComboBoxElement localidadComboBox = $(ComboBoxElement.class).caption("Localidad").first();
        TextFieldElement cdigopostalTextField = $(TextFieldElement.class).caption("Código postal").first();
        ButtonElement guardarButton = $(ButtonElement.class).caption("Guardar").first();
        ButtonElement eliminarButton = $(ButtonElement.class).caption("Eliminar").first();

        TextFieldElement ambientesTextField = $(TextFieldElement.class).caption("Ambientes").first();
        TextFieldElement cocherasTextField = $(TextFieldElement.class).caption("Cocheras").first();
        TextFieldElement dormitoriosTextField = $(TextFieldElement.class).caption("Dormitorios").first();
        TextFieldElement supTotalTextField = $(TextFieldElement.class).caption("Sup. Total").first();
        TextFieldElement supCubiertaTextField = $(TextFieldElement.class).caption("Sup. Cubierta").first();
        CheckBoxElement aestrenarCheckBox = $(CheckBoxElement.class).caption("A estrenar").first();
        CheckBoxElement aireAcondicionadoCheckBox = $(CheckBoxElement.class).caption("Aire Acondicionado").first();
        CheckBoxElement jardnCheckBox = $(CheckBoxElement.class).caption("Jardín").first();
        CheckBoxElement piletaCheckBox = $(CheckBoxElement.class).caption("Pileta").first();
        ButtonElement button1 = $(VerticalLayoutElement.class).$(ButtonElement.class).first();
    }

    @After
    public void tearDown() throws Exception {
        getDriver().quit();
    }

}
