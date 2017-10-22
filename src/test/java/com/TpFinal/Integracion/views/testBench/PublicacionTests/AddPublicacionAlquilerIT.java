package com.TpFinal.Integracion.views.testBench.PublicacionTests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.TpFinal.Integracion.views.pageobjects.TBLoginView;
import com.TpFinal.Integracion.views.pageobjects.TBMainView;
import com.TpFinal.Integracion.views.pageobjects.TBPublicacionView;
import com.TpFinal.Integracion.views.testBench.TBUtils;
import com.vaadin.testbench.Parameters;
import com.vaadin.testbench.ScreenshotOnFailureRule;
import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.GridElement;

public class AddPublicacionAlquilerIT extends TestBenchTestCase {


    private TBLoginView loginView;
    private TBMainView mainView;
    private TBPublicacionView publicacionView;
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
        mainView=loginView.login();

        publicacionView = mainView.getPublicacionView();
    }

    @Ignore
    public void addPublicacionTest(){
        getDriver().get(TBUtils.getUrl("publicaciones"));
        TBUtils.sleep(3000);
        Assert.assertTrue($(GridElement.class).exists());

        //New Publication
        publicacionView.getNuevaButton().first().click();

        TBUtils.sleep(8000);
        //RadioButtons
        publicacionView.getRadioButtonGroup().first().selectByText("Alquiler");
        publicacionView.getEstadodelapublicacionRadioButtonGroup().first().selectByText("Terminada");

        //DateField
        publicacionView.getFechapublicacionDateField().first().setValue("29/10/2017");

        //Inmueble ComboBox
        List<String> inmuebleList = publicacionView.getInmuebleComboBox().first().getPopupSuggestions();
        String selectedInmueble = inmuebleList.get(2);
        publicacionView.getInmuebleComboBox().first().selectByText(selectedInmueble);

        //MontoField
        publicacionView.getMontoTextField().first().setValue("9999999999999999");

        //Seleccionamos el tipo de moneda en el combo
        publicacionView.getMonedaComboBox().first().selectByText("Dolares");

        publicacionView.getGuardarButton().first().click();
        TBUtils.sleep(8000);

        //Verify the form is closed
        Assert.assertFalse(publicacionView.isFormDisplayed());
    }


}
