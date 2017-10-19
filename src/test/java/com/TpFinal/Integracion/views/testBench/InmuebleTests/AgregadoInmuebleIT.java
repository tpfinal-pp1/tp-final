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
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

/**
 * Created by Max on 10/12/2017.
 */
public class AgregadoInmuebleIT extends TestBenchTestCase {
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
        mainView =loginView.login();

        inmuebleView = mainView.getInmuebleView();

    }


    @Test
    public void agregadoInmuebleTest() {
       // initializeDriver().get("http://inmobi.ddns.net/");
        getDriver().get(TBUtils.getUrl("inmuebles"));
        //TBUtils.sleep(3000);


        //Agregar inmueble
        //Tab "Datos Principales"
        Assert.assertTrue(inmuebleView.getGrid().exists());
        inmuebleView.getNuevoInmuebleButton().first().click();
        //TBUtils.sleep(3000);

        //Tab "Datos principales"
        List<String> tabsheet1Options = inmuebleView.getTabSheet().first().getTabCaptions();
        Assert.assertEquals(tabsheet1Options.get(0),"Datos Principales");
        Assert.assertEquals(tabsheet1Options.get(1),"Características");
        inmuebleView.getTabSheet().first().openTab("Datos Principales");

        //ComboPersonas
        List<String> owners = inmuebleView.getComboBoxPropietario().first().getPopupSuggestions();
        String selectedOwner = owners.get(1);
        inmuebleView.getComboBoxPropietario().first().selectByText(selectedOwner);


        //ComboClase Inmueble
        List<String> inmuebleClasess = inmuebleView.getClaseComboBox().first().getPopupSuggestions();
        String selectedInmuebleClass = inmuebleClasess.get(1);
        inmuebleView.getClaseComboBox().first().selectByText(selectedInmuebleClass);

        //RadioButton
        inmuebleView.getTipoRadioButtonGroup().first().selectByText("Comercial");

        //Adress data
        inmuebleView.getCalleTextField().first().setValue("Evergreen Terrace");
        inmuebleView.getNumeroTextField().first().setValue("742");

        //Combobox provincias
        List<String> provinces = inmuebleView.getProvinciaComboBox().first().getPopupSuggestions();
        String selectedProvince = provinces.get(1);
        inmuebleView.getProvinciaComboBox().first().selectByText(selectedProvince);

        //TBUtils.sleep(3000);
        //Tab "Caracteristicas"
        inmuebleView.getTabSheet().first().openTab("Características");
        inmuebleView.getAmbientesTextField().first().setValue("2");
        inmuebleView.getCocherasTextField().first().setValue("3");
        inmuebleView.getDormitoriosTextField().first().setValue("5");
        inmuebleView.getSupTotalTextField().first().setValue("100");
        inmuebleView.getSupCubiertaTextField().first().setValue("300");

        //TBUtils.sleep(3000);
        //CheckBoxes
        inmuebleView.getAestrenarCheckBox().first().click();
        inmuebleView.getJardnCheckBox().first().click();
        inmuebleView.getParrillaCheckBox().first().click();
        inmuebleView.getAireAcondicionadoCheckBox().first().click();
        inmuebleView.getPiletaCheckBox().first().click();

        /* TODO dejarlo de prueba
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
*/
        //Save inmueble.
        inmuebleView.getGuardarButton().first().click();
        //TBUtils.sleep(3000);
        //ButtonElement llenarcomboButton = $(ButtonElement.class).caption("llenar combo").first();
       // llenarcomboButton.click();


        //Verificar que la grid tiene un elemen to, verfificando el propietario del inmueble
        //Assert.assertEquals(inmuebleGrid.getCell(0,1).getText(),"Brandy Wilburn");
        Assert.assertFalse(inmuebleView.isFormDisplayed());
    }



}
