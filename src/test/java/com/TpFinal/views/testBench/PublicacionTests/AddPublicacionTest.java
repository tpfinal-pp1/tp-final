package com.TpFinal.views.testBench.PublicacionTests;

import com.TpFinal.views.TBUtils;
import com.TpFinal.views.pageobjects.TBLoginView;
import com.TpFinal.views.pageobjects.TBMainView;
import com.vaadin.testbench.Parameters;
import com.vaadin.testbench.ScreenshotOnFailureRule;
import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.*;
import org.junit.*;

/**
 * Created by Max on 10/12/2017.
 */
public class AddPublicacionTest extends TestBenchTestCase {


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
    public void addPublicacionTest(){
        getDriver().get(TBUtils.getUrl("publicaciones"));
        Assert.assertTrue($(GridElement.class).exists());
        GridElement inmuebleGrid = $(GridElement.class).first();

//TODO comment
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


}
