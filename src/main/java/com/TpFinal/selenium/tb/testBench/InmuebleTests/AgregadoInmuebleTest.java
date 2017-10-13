package com.TpFinal.selenium.tb.testBench.InmuebleTests;

import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.firefox.FirefoxDriver;

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


    @Test
    public void agregadoInmuebleTest(){

        TextFieldElement usuarioTextField = $(TextFieldElement.class).caption("Usuario").first();
        PasswordFieldElement contraseaPasswordField = $(PasswordFieldElement.class).caption("Contraseña").first();
        ButtonElement iniciarSesinButton = $(ButtonElement.class).caption("Iniciar Sesión").first();
        ButtonElement inmueblesButton = $(ButtonElement.class).caption("Inmuebles").first();
        ButtonElement nuevoButton = $(ButtonElement.class).caption("Nuevo").first();
        ComboBoxElement comboBox1 = $(ComboBoxElement.class).first();
        ComboBoxElement claseComboBox = $(ComboBoxElement.class).caption("Clase").first();
        //RadioButtonGroupElement tipoRadioButtonGroup = $(RadioButtonGroupElement.class).caption("Tipo").first();
        TextFieldElement calleTextField = $(TextFieldElement.class).caption("Calle").first();
        TextFieldElement nmeroTextField = $(TextFieldElement.class).caption("Número").first();
        ComboBoxElement provinciaComboBox = $(ComboBoxElement.class).caption("Provincia").first();
        ComboBoxElement localidadComboBox = $(ComboBoxElement.class).caption("Localidad").first();
        TextFieldElement cdigopostalTextField = $(TextFieldElement.class).caption("Código postal").first();
        ButtonElement guardarButton = $(ButtonElement.class).caption("Guardar").first();
        ButtonElement eliminarButton = $(ButtonElement.class).caption("Eliminar").first();
        TabSheetElement tabSheet1 = $(TabSheetElement.class).first();
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
