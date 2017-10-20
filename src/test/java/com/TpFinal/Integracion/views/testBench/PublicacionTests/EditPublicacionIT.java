package com.TpFinal.Integracion.views.testBench.PublicacionTests;

import com.TpFinal.Integracion.views.pageobjects.TBLoginView;
import com.TpFinal.Integracion.views.pageobjects.TBMainView;
import com.TpFinal.Integracion.views.pageobjects.TBPublicacionView;
import com.TpFinal.Integracion.views.testBench.TBUtils;
import com.vaadin.testbench.Parameters;
import com.vaadin.testbench.ScreenshotOnFailureRule;
import com.vaadin.testbench.TestBenchTestCase;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Created by Max on 10/18/2017.
 */
public class EditPublicacionIT extends TestBenchTestCase{

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

    @Test
    public void addPublicacionTest(){
    	
    	//Accedemos a la pestaña de publicaciones
        getDriver().get(TBUtils.getUrl("publicaciones"));
        TBUtils.sleep(3000);
        Assert.assertTrue(publicacionView.isDisplayed());


        //Editamos la primera publicacion
        TBUtils.sleep(3000);
        publicacionView.getEditButton("Accion 0").click();

        TBUtils.sleep(3000);

        //Elegimos la opcion "terminada" del radiobutton
        publicacionView.getEstadodelapublicacionRadioButtonGroup().first().selectByText("Terminada");
        
        //Edit monto
        publicacionView.getMontoTextField().first().setValue("2000000000000");
        
        //Obtenemos los tipos de monedas del combo
        List<String> monedas = publicacionView.getMonedaComboBox().first().getPopupSuggestions();
        String monedaSelected = monedas.get(1);
        publicacionView.getMonedaComboBox().first().selectByText(monedaSelected);
        
        //Clickeamos guardar
        publicacionView.getGuardarButton().first().click();
        TBUtils.sleep(3000);
        
        //Verificamos que el form ya no se muestre
        Assert.assertFalse(publicacionView.isFormDisplayed());
    }


}
