package com.TpFinal.selenium.tb.testBench.InmuebleTests;

import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.*;
import org.apache.commons.lang3.SystemUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Max on 10/12/2017.
 */
public class AgregadoInmuebleTest extends TestBenchTestCase {
    FirefoxDriver driver;

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

//TODO completar TEST (MAX)
    @Test
    public void agregadoInmuebleTest() {
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

        //Main View
        //Inmuebles button view
        ButtonElement inmueblesViewButton = $(ButtonElement.class).caption("Inmuebles").first();
        inmueblesViewButton.click();

        //Inmuebles View
        //getDriver().get("http://inmobi.ddns.net/#!inmuebles");
        getDriver().get("http://localhost:8080/#!inmuebles");
        GridElement inmuebleGrid = $(GridElement.class).first();

        //Agregar inmueble
        //Tab "Datos Principales"
        ButtonElement nuevoInmuebleButton = $(ButtonElement.class).caption("Nuevo").first();
        nuevoInmuebleButton.click();
        //Tab "Datos principales"
        TabSheetElement tabSheet1 = $(TabSheetElement.class).first();
        List<String> tabsheet1Options = $(TabSheetElement.class).first().getTabCaptions();
        Assert.assertEquals(tabsheet1Options.get(0),"Datos Principales");
        Assert.assertEquals(tabsheet1Options.get(1),"Características");
        tabSheet1.openTab("Datos Principales");
//TODO comment
        //ComboPersonas
        ComboBoxElement personasComboBox = $(ComboBoxElement.class).first();


        //ComboClase Inmueble
        ComboBoxElement claseComboBox = $(ComboBoxElement.class).caption("Clase").first();

        RadioButtonGroupElement tipoRadioButtonGroup = $(RadioButtonGroupElement.class).caption("Tipo").first();
        tipoRadioButtonGroup.selectByText("Comercial");
        //Adress data
        TextFieldElement calleTextField = $(TextFieldElement.class).caption("Calle").first();
        calleTextField.setValue("Evergreen Terrace");
        TextFieldElement nmeroTextField = $(TextFieldElement.class).caption("Número").first();
        nmeroTextField.setValue("742");

        //Combobox provincias
        ComboBoxElement provinciaComboBox = $(ComboBoxElement.class).caption("Provincia").first();


        //Tab "Caracteristicas"
        tabSheet1.openTab("Características");
        TextFieldElement ambientesTextField = $(TextFieldElement.class).caption("Ambientes").first();
        ambientesTextField.setValue("2");
        TextFieldElement cocherasTextField = $(TextFieldElement.class).caption("Cocheras").first();
        cocherasTextField.setValue("3");
        TextFieldElement dormitoriosTextField = $(TextFieldElement.class).caption("Dormitorios").first();
        dormitoriosTextField.setValue("5");
        TextFieldElement supTotalTextField = $(TextFieldElement.class).caption("Sup. Total").first();
        supTotalTextField.setValue("100");
        TextFieldElement supCubiertaTextField = $(TextFieldElement.class).caption("Sup. Cubierta").first();
        supCubiertaTextField.setValue("300");

        //CheckBoxes
        WebElement element ;
        Actions actions = new Actions(getDriver()); //used to locate checkboxes and click them

        //Using actions to locate all visible checkbox elements and click them.
        List<CheckBoxElement> elements = $(CheckBoxElement.class).all();
        for(CheckBoxElement check : elements){
            element = check;
            actions.moveToElement(element).click().perform();
        }

        CheckBoxElement aestrenarCheckBox = $(CheckBoxElement.class).caption("A estrenar").first();
        CheckBoxElement aireAcondicionadoCheckBox = $(CheckBoxElement.class).caption("Aire Acondicionado").first();
        //If checkbox is checked, clear it (uncheck it)
        if(aireAcondicionadoCheckBox.getValue().equals("checked"))
            actions.moveToElement(aireAcondicionadoCheckBox).click().perform();
        CheckBoxElement jardnCheckBox = $(CheckBoxElement.class).caption("Jardín").first();
        if(jardnCheckBox.getValue().equals("checked"))
            actions.moveToElement(jardnCheckBox).click().perform();
        CheckBoxElement parrillaCheckBox = $(CheckBoxElement.class).caption("Parrilla").first();
        CheckBoxElement piletaCheckBox = $(CheckBoxElement.class).caption("Pileta").first();
        //Checking checkboxes selection
        Assert.assertEquals(aestrenarCheckBox.getValue(),"checked");
        Assert.assertEquals(aireAcondicionadoCheckBox.getValue(),"unchecked");
        Assert.assertEquals(jardnCheckBox.getValue(),"unchecked");
        Assert.assertEquals(parrillaCheckBox.getValue(),"checked");
        Assert.assertEquals(piletaCheckBox.getValue(),"checked");

        //Sve inmueble.
        ButtonElement cancelButton = $(VerticalLayoutElement.class).$(ButtonElement.class).first();
        ButtonElement guardarButton = $(ButtonElement.class).caption("Guardar").first();
        ButtonElement llenarcomboButton = $(ButtonElement.class).caption("llenar combo").first();
        llenarcomboButton.click();
        guardarButton.click();

        //Verificar que la grid tiene un elemen to, verfificando el propietario del inmueble
        //Assert.assertEquals(inmuebleGrid.getCell(0,1).getText(),"Brandy Wilburn");
    }

    @After
    public void tearDown() throws Exception {
        getDriver().quit();
    }

}
