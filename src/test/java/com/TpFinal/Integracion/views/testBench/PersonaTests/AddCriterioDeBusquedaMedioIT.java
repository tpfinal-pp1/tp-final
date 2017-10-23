package com.TpFinal.Integracion.views.testBench.PersonaTests;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.TpFinal.Integracion.views.pageobjects.TBPersonaView.TBBusquedaInteresadoView;
import com.TpFinal.Integracion.views.pageobjects.TBLoginView;
import com.TpFinal.Integracion.views.pageobjects.TBMainView;
import com.TpFinal.Integracion.views.pageobjects.TBPersonaView.TBPersonaView;
import com.TpFinal.Integracion.views.testBench.TBUtils;
import com.vaadin.testbench.Parameters;
import com.vaadin.testbench.ScreenshotOnFailureRule;
import com.vaadin.testbench.TestBenchTestCase;

public class AddCriterioDeBusquedaMedioIT extends TestBenchTestCase{

	private TBLoginView loginView;
	private TBMainView mainView;
	private TBPersonaView personaView;
	private TBBusquedaInteresadoView busquedaInteresadoView;

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

		personaView = mainView.getPersonaView();


	}

	@Test
	public void agregarCriteriosDeBusqueda() {
		getDriver().get(TBUtils.getUrl("personas"));

		TBUtils.sleep(4000);
		Assert.assertTrue(personaView.isDisplayed());

		personaView.getCriterioButton("Accion 2").click();

		busquedaInteresadoView = new TBBusquedaInteresadoView(driver);

		//Eligiendo el tipo
		busquedaInteresadoView.getTipoRadioButtonGroup().first().selectByText("Vivienda");

		//Eligiendo estado
		busquedaInteresadoView.getEstadoRadioButtonGroup().first().selectByText("En Alquiler");

		//Eligiendo provincia del combobox
		List<String> provinciasList = busquedaInteresadoView.getProvinciaComboBox().first().getPopupSuggestions();
		String selectedProvincia = provinciasList.get(1);
		busquedaInteresadoView.getProvinciaComboBox().first().selectByText(selectedProvincia);
		
		//Eligiendo localidad del combobox
		List<String> localidadList = busquedaInteresadoView.getLocalidadComboBox().first().getPopupSuggestions();
		String selectedLocalidad = localidadList.get(1);
		busquedaInteresadoView.getLocalidadComboBox().first().selectByText(selectedLocalidad);
		
		//Seteando precion minimo
		busquedaInteresadoView.getPrecioMinimoTextField().setValue("224");
		
		//Seteando precion maximo
		busquedaInteresadoView.getPrecioMaximoTextField().setValue("772778");
		
		//Eligiendo tipo de moneda
		busquedaInteresadoView.getTipoMonedaRadioButtonGroup().first().selectByText("Dolares");
		
		//Obtengo las pestañas posibles dentro del form de criterios de busqueda
		List<String> tabsheet1Options = busquedaInteresadoView.getTabSheet1().first().getTabCaptions();
		Assert.assertEquals(tabsheet1Options.get(0),"Características Principales");
        Assert.assertEquals(tabsheet1Options.get(1),"Características Adicionales");
        Assert.assertEquals(tabsheet1Options.get(2),"Clases de Inmueble");
        busquedaInteresadoView.getTabSheet1().first().openTab("Características Adicionales");
        
        //Seteando cantidad ambiente minimo
        busquedaInteresadoView.getAmbientesMinimoTextField().setValue("2");
        
        busquedaInteresadoView.getAmbientesMaximoTextField().setValue("6");
        
        busquedaInteresadoView.getCocherasMinimoTextField().setValue("3");
        
        busquedaInteresadoView.getCocherasMaximoTextField().setValue("7");
        
        busquedaInteresadoView.getDomirtoriosMinimoTextField().setValue("3");
        
        busquedaInteresadoView.getDormitoriosMaximoTextField().setValue("7");
        
        busquedaInteresadoView.getSupTotalMinimoTextField().setValue("3");
        
        busquedaInteresadoView.getSupTotalMaximoTextField().setValue("9");
        
        busquedaInteresadoView.getSupCubiertaMinimoTextField().setValue("7");
        
        busquedaInteresadoView.getSupCubiertaMaximoTextField().setValue("778");
        
        busquedaInteresadoView.getAireAcondicionadoCheckBox().first().click();
        
        busquedaInteresadoView.getJardnCheckBox().first().click();
        
        //Selecciono la opcion de parrilla
        busquedaInteresadoView.getParrilaCheckBox().first().click();
		
		//Click boton guardar
		busquedaInteresadoView.getGuardarButton().first().click();
		
		

	}



}