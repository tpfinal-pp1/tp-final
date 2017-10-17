package com.TpFinal.Integracion.views.testBench.InmuebleTests;

import com.TpFinal.Integracion.views.testBench.TBUtils;
import com.TpFinal.Integracion.views.pageobjects.TBLoginView;
import com.TpFinal.Integracion.views.pageobjects.TBMainView;
import com.vaadin.testbench.Parameters;
import com.vaadin.testbench.ScreenshotOnFailureRule;
import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.*;
import org.junit.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

/**
 * Created by Max on 10/12/2017.
 */
public class AgregadoInmuebleIT extends TestBenchTestCase {
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

//TODO completar TEST (MAX)
    @Test
    public void agregadoInmuebleTest() {
       // initializeDriver().get("http://inmobi.ddns.net/");
        getDriver().get(TBUtils.getUrl("inmuebles"));

        //Agregar inmueble
        //Tab "Datos Principales"
        Assert.assertTrue($(GridElement.class).exists());
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
      //  ComboBoxElement personasComboBox = $(ComboBoxElement.class).first();


        //ComboClase Inmueble
       // ComboBoxElement claseComboBox = $(ComboBoxElement.class).caption("Clase").first();

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



}
