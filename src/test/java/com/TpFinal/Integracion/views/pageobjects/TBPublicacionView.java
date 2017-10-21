package com.TpFinal.Integracion.views.pageobjects;

import com.vaadin.testbench.ElementQuery;
import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.*;
import org.openqa.selenium.WebDriver;

/**
 * Created by Max on 10/17/2017.
 */
public class TBPublicacionView extends TestBenchTestCase {

    private ElementQuery<GridElement> grid1;
    private ElementQuery<ButtonElement> nuevaButton ;
    private ElementQuery<TextFieldElement> filter;
    private ElementQuery<ButtonElement> filterBrowser;
    private ElementQuery<RadioButtonGroupElement> radioButtonGroup;
    private ElementQuery<DateFieldElement> fechapublicacionDateField;
    private ElementQuery<RadioButtonGroupElement> estadodelapublicacionRadioButtonGroup;
    private ElementQuery<ComboBoxElement> inmuebleComboBox;
    private ElementQuery<TextFieldElement> propietarioTextField ;
    private ElementQuery<TextFieldElement> montoTextField;
    private ElementQuery<ComboBoxElement> monedaComboBox;
    private ElementQuery<ButtonElement> guardarButton;
    private ElementQuery<HorizontalLayoutElement> horizontalLayoutAcciones;
    private ElementQuery<ButtonElement> buttonEdit ;
    private ElementQuery<ButtonElement> buttonRemove ;

    private ElementQuery<WindowElement> eliminarWindow ;
    private ElementQuery<ButtonElement> siButton ;
    private ElementQuery<ButtonElement> noButton ;

    public TBPublicacionView(WebDriver driver){
        setDriver(driver);
        grid1 = $(GridElement.class);
        nuevaButton = $(ButtonElement.class).caption("Nueva");
        filter = $(TextFieldElement.class);
        filterBrowser = $(VerticalLayoutElement.class).$(ButtonElement.class);
        radioButtonGroup = $(RadioButtonGroupElement.class).caption(" ");
        fechapublicacionDateField = $(DateFieldElement.class).caption("Fecha publicacion");
        estadodelapublicacionRadioButtonGroup = $(RadioButtonGroupElement.class).caption("Estado de la publicacion");
        inmuebleComboBox = $(ComboBoxElement.class).caption("Inmueble");
        propietarioTextField = $(TextFieldElement.class).caption("Propietario: ");
        montoTextField = $(TextFieldElement.class).caption("Monto");
        monedaComboBox = $(ComboBoxElement.class).caption("Moneda");
        guardarButton = $(ButtonElement.class).caption("Guardar");
        horizontalLayoutAcciones = $(GridElement.class).$$(HorizontalLayoutElement.class);

        eliminarWindow = $$(WindowElement.class).caption("Eliminar");
        siButton = $(ButtonElement.class).caption("Si");
        noButton = $(ButtonElement.class).caption("No");
    }

    public ElementQuery<GridElement> getGrid1() { return grid1; }

    public ElementQuery<ButtonElement> getNuevaButton() { return nuevaButton; }

    public ElementQuery<TextFieldElement> getFilter() { return filter; }

    public ElementQuery<ButtonElement> getFilterBrowser() { return filterBrowser; }

    public ElementQuery<RadioButtonGroupElement> getRadioButtonGroup() { return radioButtonGroup; }

    public ElementQuery<DateFieldElement> getFechapublicacionDateField() { return fechapublicacionDateField; }

    public ElementQuery<RadioButtonGroupElement> getEstadodelapublicacionRadioButtonGroup() { return estadodelapublicacionRadioButtonGroup; }

    public ElementQuery<ComboBoxElement> getInmuebleComboBox() { return inmuebleComboBox; }

    public ElementQuery<TextFieldElement> getPropietarioTextField() { return propietarioTextField; }

    public ElementQuery<TextFieldElement> getMontoTextField() { return montoTextField; }

    public ElementQuery<ComboBoxElement> getMonedaComboBox() { return monedaComboBox; }

    public ElementQuery<ButtonElement> getGuardarButton() { return guardarButton; }

    private ElementQuery<HorizontalLayoutElement> getHorizontalLayoutAcciones(String caption) { return horizontalLayoutAcciones.caption(caption); }

    private ElementQuery<ButtonElement> getButtonEdit(String caption) { return buttonEdit =  getHorizontalLayoutAcciones(caption).$$(ButtonElement.class); }

    private ElementQuery<ButtonElement> getButtonRemove(String caption) { return  buttonRemove =  getHorizontalLayoutAcciones(caption).$$(ButtonElement.class); }

    public ButtonElement getEditButton(String caption){ return getButtonEdit(caption).first(); }

    public ButtonElement getRemoveButton(String caption){ return getButtonRemove(caption).get(1); }

    public boolean isDisplayed(){ return this.grid1.exists();}

    public boolean isFormDisplayed(){ return this.guardarButton.exists();}

    public ElementQuery<WindowElement> getEliminarWindow() { return eliminarWindow; }

    public ElementQuery<ButtonElement> getSiButton() { return siButton; }

    public ElementQuery<ButtonElement> getNoButton() { return noButton; }

    public boolean isEliminarWindowDisplayed(){return this.eliminarWindow.exists();}
}
