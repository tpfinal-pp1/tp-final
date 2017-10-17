package com.TpFinal.Integracion.views.testBench.PersonaTests;

import com.TpFinal.Integracion.views.testBench.TBUtils;
import com.TpFinal.Integracion.views.pageobjects.TBLoginView;
import com.TpFinal.Integracion.views.pageobjects.TBMainView;
import com.vaadin.testbench.Parameters;
import com.vaadin.testbench.ScreenshotOnFailureRule;
import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.ButtonElement;
import com.vaadin.testbench.elements.GridElement;
import com.vaadin.testbench.elements.TextFieldElement;
import org.junit.*;

/**
 * Created by Max on 10/11/2017.
 */
public class AgregarPersonaIT extends TestBenchTestCase{
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
        getDriver().get(TBUtils.getUrl("personas"));

        Assert.assertTrue($(GridElement.class).exists());
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

}
