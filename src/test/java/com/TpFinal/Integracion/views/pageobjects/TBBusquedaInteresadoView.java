package com.TpFinal.Integracion.views.pageobjects;

import com.vaadin.testbench.ElementQuery;
import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.*;
import org.openqa.selenium.WebDriver;

/**
 * Created by Max on 10/19/2017.
 */
public class TBBusquedaInteresadoView extends TestBenchTestCase{

    private ElementQuery<RadioButtonGroupElement> tipoRadioButtonGroup ;
    private ElementQuery<RadioButtonGroupElement> estadoRadioButtonGroup ;
    private ElementQuery<ComboBoxElement> provinciaComboBox ;
    private ElementQuery<ComboBoxElement> localidadComboBox ;
    private ElementQuery<TextFieldElement> precioMinimoTextField;
    private ElementQuery<TextFieldElement> precioMaximoTextField;
    private ElementQuery<RadioButtonGroupElement> tipoMonedaRadioButtonGroup ;
    private ElementQuery<TabSheetElement> tabSheet1 ;

    private ElementQuery<TextFieldElement> ambientesMinimoTextField;
    private ElementQuery<TextFieldElement> ambientesMaximoTextField;
    private ElementQuery<TextFieldElement> cocherasMinimoTextField;
    private ElementQuery<TextFieldElement> cocherasMaximoTextField;
    private ElementQuery<TextFieldElement> domirtoriosMinimoTextField;
    private ElementQuery<TextFieldElement> dormitoriosMaximoTextField;
    private ElementQuery<TextFieldElement> supTotalMinimoTextField;
    private ElementQuery<TextFieldElement> supTotalMaximoTextField;
    private ElementQuery<TextFieldElement> supCubiertaMinimoTextField;
    private ElementQuery<TextFieldElement> supCubiertaMaximoTextField;
    private ElementQuery<CheckBoxElement> aestrenarCheckBox ;
    private ElementQuery<CheckBoxElement> aireAcondicionadoCheckBox ;
    private ElementQuery<CheckBoxElement> jardnCheckBox ;
    private ElementQuery<CheckBoxElement> parrilaCheckBox ;
    private ElementQuery<CheckBoxElement> piletaCheckBox ;

    private ElementQuery<CheckBoxGroupElement> checkBoxGroupClaseInmueble;
    private ElementQuery<ButtonElement> guardarButton ;
    private ElementQuery<ButtonElement> buscarButton ;
    private ElementQuery<ButtonElement> limpiarButton ;

    public TBBusquedaInteresadoView(WebDriver driver){
         setDriver(driver);

         tipoRadioButtonGroup = $(RadioButtonGroupElement.class).caption("Tipo");
         estadoRadioButtonGroup = $(RadioButtonGroupElement.class).caption("Estado");
         provinciaComboBox = $(ComboBoxElement.class).caption("Provincia");
         localidadComboBox = $(ComboBoxElement.class).caption("Localidad");
         precioMinimoTextField = $$(WindowElement.class).id("profilepreferenceswindow").$(TextFieldElement.class);
         precioMaximoTextField = $$(WindowElement.class).id("profilepreferenceswindow").$(TextFieldElement.class);
         tipoMonedaRadioButtonGroup = $(RadioButtonGroupElement.class).caption("Tipo Moneda");
         tabSheet1 = $(TabSheetElement.class);

         ambientesMinimoTextField = $$(WindowElement.class).id("profilepreferenceswindow").$(TextFieldElement.class);
         ambientesMaximoTextField = $$(WindowElement.class).id("profilepreferenceswindow").$(TextFieldElement.class);
         cocherasMinimoTextField = $(HorizontalLayoutElement.class).caption("Cocheras").$$(TextFieldElement.class);
         cocherasMaximoTextField = $(HorizontalLayoutElement.class).caption("Cocheras").$$(TextFieldElement.class);
         domirtoriosMinimoTextField = $(HorizontalLayoutElement.class).caption("Dormitorios").$$(TextFieldElement.class);
         dormitoriosMaximoTextField = $(HorizontalLayoutElement.class).caption("Dormitorios").$$(TextFieldElement.class);
         supTotalMinimoTextField = $(HorizontalLayoutElement.class).caption("Sup. Total").$$(TextFieldElement.class);
         supTotalMaximoTextField = $(HorizontalLayoutElement.class).caption("Sup. Total").$$(TextFieldElement.class);
         supCubiertaMinimoTextField = $(HorizontalLayoutElement.class).caption("Sup. Cubierta").$$(TextFieldElement.class);
         supCubiertaMaximoTextField = $(HorizontalLayoutElement.class).caption("Sup. Cubierta").$$(TextFieldElement.class);
         aestrenarCheckBox = $(CheckBoxElement.class).caption("A estrenar");
         aireAcondicionadoCheckBox = $(CheckBoxElement.class).caption("Aire Acondicionado");
         jardnCheckBox = $(CheckBoxElement.class).caption("Jardín");
         parrilaCheckBox = $(CheckBoxElement.class).caption("Parrila");
         piletaCheckBox = $(CheckBoxElement.class).caption("Pileta");

         checkBoxGroupClaseInmueble = $(CheckBoxGroupElement.class).caption("");
         guardarButton = $(ButtonElement.class).caption("Guardar");
         buscarButton = $(ButtonElement.class).caption("Buscar");
         limpiarButton = $(ButtonElement.class).caption("Limpiar");


    }

    public TextFieldElement getPrecioMinimoTextField(){ return this.precioMinimoTextField.first() ;}

    public TextFieldElement getPrecioMaximoTextField() { return this.precioMaximoTextField.get(1) ; }

    public ElementQuery<RadioButtonGroupElement> getTipoRadioButtonGroup() { return tipoRadioButtonGroup; }

    public ElementQuery<RadioButtonGroupElement> getEstadoRadioButtonGroup() { return estadoRadioButtonGroup; }

    public ElementQuery<ComboBoxElement> getProvinciaComboBox() { return provinciaComboBox; }

    public ElementQuery<ComboBoxElement> getLocalidadComboBox() { return localidadComboBox; }

    public ElementQuery<RadioButtonGroupElement> getTipoMonedaRadioButtonGroup() { return tipoMonedaRadioButtonGroup; }

    public ElementQuery<TabSheetElement> getTabSheet1() { return tabSheet1; }

    public TextFieldElement getAmbientesMinimoTextField() { return ambientesMinimoTextField.first(); }

    public TextFieldElement getAmbientesMaximoTextField() { return ambientesMaximoTextField.get(1); }

    public TextFieldElement getCocherasMinimoTextField() { return cocherasMinimoTextField.first(); }

    public TextFieldElement getCocherasMaximoTextField() { return cocherasMaximoTextField.get(1); }

    public TextFieldElement getDomirtoriosMinimoTextField() { return domirtoriosMinimoTextField.first(); }

    public TextFieldElement getDormitoriosMaximoTextField() { return dormitoriosMaximoTextField.get(1); }

    public TextFieldElement getSupTotalMinimoTextField() { return supTotalMinimoTextField.first(); }

    public TextFieldElement getSupTotalMaximoTextField() { return supTotalMaximoTextField.get(1); }

    public TextFieldElement getSupCubiertaMinimoTextField() { return supCubiertaMinimoTextField.first(); }

    public TextFieldElement getSupCubiertaMaximoTextField() { return supCubiertaMaximoTextField.get(1); }

    public ElementQuery<CheckBoxElement> getAestrenarCheckBox() { return aestrenarCheckBox;}

    public ElementQuery<CheckBoxElement> getAireAcondicionadoCheckBox() { return aireAcondicionadoCheckBox; }

    public ElementQuery<CheckBoxElement> getJardnCheckBox() { return jardnCheckBox; }

    public ElementQuery<CheckBoxElement> getParrilaCheckBox() { return parrilaCheckBox; }

    public ElementQuery<CheckBoxElement> getPiletaCheckBox() { return piletaCheckBox; }

    public ElementQuery<CheckBoxGroupElement> getCheckBoxGroupClaseInmueble() { return checkBoxGroupClaseInmueble; }

    public ElementQuery<ButtonElement> getGuardarButton() { return guardarButton; }

    public ElementQuery<ButtonElement> getBuscarButton() { return buscarButton; }

    public ElementQuery<ButtonElement> getLimpiarButton() { return limpiarButton; }

    public boolean isDisplayed(){return this.guardarButton.exists();}
}
