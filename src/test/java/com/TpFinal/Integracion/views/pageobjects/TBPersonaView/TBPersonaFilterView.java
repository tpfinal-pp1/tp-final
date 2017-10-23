package com.TpFinal.Integracion.views.pageobjects.TBPersonaView;

import com.vaadin.testbench.ElementQuery;
import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.RadioButtonGroupElement;
import com.vaadin.testbench.elements.WindowElement;
import org.openqa.selenium.WebDriver;

/**
 * Created by Max on 10/20/2017.
 */
public class TBPersonaFilterView extends TestBenchTestCase{

    private ElementQuery<WindowElement> filtrarWindow ;
    private ElementQuery<RadioButtonGroupElement> radioButtonGroup1 ;

    public TBPersonaFilterView(WebDriver driver){
        setDriver(driver);

        filtrarWindow = $$(WindowElement.class).caption("Filtrar");
        radioButtonGroup1 = $(RadioButtonGroupElement.class);
    }

    public ElementQuery<WindowElement> getFiltrarWindow() { return filtrarWindow; }

    public ElementQuery<RadioButtonGroupElement> getRadioButtonGroup1() { return radioButtonGroup1; }
}
