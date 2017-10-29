package com.TpFinal.Integracion.views.testBench.PersonaTests;

import com.TpFinal.Integracion.views.pageobjects.TBLoginView;
import com.TpFinal.Integracion.views.pageobjects.TBMainView;
import com.TpFinal.Integracion.views.pageobjects.TBPersonaView.TBBusquedaInteresadoView;
import com.TpFinal.Integracion.views.pageobjects.TBPersonaView.TBPersonaView;
import com.TpFinal.Integracion.views.testBench.TBUtils;
import com.vaadin.testbench.Parameters;
import com.vaadin.testbench.ScreenshotOnFailureRule;
import com.vaadin.testbench.TestBenchTestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;

import java.util.List;

public class AddCriterioDeBusquedaSimpleIT extends TestBenchTestCase{

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

	@Ignore
	public void agregarCriteriosDeBusqueda() {
		getDriver().get(TBUtils.getUrl("personas"));

		TBUtils.sleep(3000);
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
		
		//Click boton guardar
		busquedaInteresadoView.getGuardarButton().first().click();

	}



}
