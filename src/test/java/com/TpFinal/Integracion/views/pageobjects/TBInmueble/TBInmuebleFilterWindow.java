package com.TpFinal.Integracion.views.pageobjects.TBInmueble;

import com.vaadin.testbench.ElementQuery;
import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.*;
import org.openqa.selenium.WebDriver;

/**
 * Created by Max on 10/20/2017.
 */
public class TBInmuebleFilterWindow extends TestBenchTestCase {

    private ElementQuery<WindowElement> profilepreferenceswindow ;
    private ElementQuery<TabSheetElement> tabSheet1 ;
    private ElementQuery<RadioButtonGroupElement> tipoRadioButtonGroup ;
    private ElementQuery<RadioButtonGroupElement> estadoRadioButtonGroup ;
    private ElementQuery<ComboBoxElement> provinciaComboBox ;
    private ElementQuery<ComboBoxElement> localidadComboBox ;
    private ElementQuery<TextFieldElement> precioTextFieldMinimo ;
    private ElementQuery<TextFieldElement> precioTextFieldMaximo ;
    private ElementQuery<RadioButtonGroupElement> tipoMonedaRadioButtonGroup ;

    private ElementQuery<TextFieldElement> ambientesTextFieldMinimo ;
    private ElementQuery<TextFieldElement> ambientesTextFieldMaximo  ;
    private ElementQuery<TextFieldElement> cocherasTextFieldMinimo ;
    private ElementQuery<TextFieldElement> cocherasTextFieldMaximo ;
    private ElementQuery<TextFieldElement> dormitoriosTextFieldMinimo ;
    private ElementQuery<TextFieldElement> dormitoriosTextFieldMaximo ;
    private ElementQuery<TextFieldElement> supTotalTextFieldMinimo ;
    private ElementQuery<TextFieldElement> supTotalTextFieldMaximo ;
    private ElementQuery<TextFieldElement> supCubiertaTextFieldMinimo;
    private ElementQuery<TextFieldElement> supCubiertaTextFieldMaximo ;
    private ElementQuery<CheckBoxElement> aestrenarCheckBox ;
    private ElementQuery<CheckBoxElement> aireAcondicionadoCheckBox;
    private ElementQuery<CheckBoxElement> jardnCheckBox ;
    private ElementQuery<CheckBoxElement> parrilaCheckBox;
    private ElementQuery<CheckBoxElement> piletaCheckBox ;

    private ElementQuery<CheckBoxGroupElement> checkBoxGroup1 ;
    private ElementQuery<ButtonElement> guardarButton ;
    private ElementQuery<ButtonElement> limpiarButton;

    public TBInmuebleFilterWindow (WebDriver driver){
        setDriver(driver);

         profilepreferenceswindow = $$(WindowElement.class);
         tabSheet1 = $(TabSheetElement.class);
         tipoRadioButtonGroup = $(RadioButtonGroupElement.class).caption("Tipo");
         estadoRadioButtonGroup = $(RadioButtonGroupElement.class).caption("Estado");
         provinciaComboBox = $(ComboBoxElement.class).caption("Provincia");
         localidadComboBox = $(ComboBoxElement.class).caption("Localidad");
         precioTextFieldMinimo = $$(WindowElement.class).id("profilepreferenceswindow").$(TextFieldElement.class);
         precioTextFieldMaximo = $$(WindowElement.class).id("profilepreferenceswindow").$(TextFieldElement.class);
         tipoMonedaRadioButtonGroup = $(RadioButtonGroupElement.class).caption("Tipo Moneda");
         ambientesTextFieldMinimo = $$(WindowElement.class).id("profilepreferenceswindow").$(TextFieldElement.class);
         ambientesTextFieldMaximo = $$(WindowElement.class).id("profilepreferenceswindow").$(TextFieldElement.class);
         cocherasTextFieldMinimo = $(HorizontalLayoutElement.class).caption("Cocheras").$$(TextFieldElement.class);
         cocherasTextFieldMaximo = $(HorizontalLayoutElement.class).caption("Cocheras").$$(TextFieldElement.class);
         dormitoriosTextFieldMinimo = $(HorizontalLayoutElement.class).caption("Dormitorios").$$(TextFieldElement.class);
         dormitoriosTextFieldMaximo = $(HorizontalLayoutElement.class).caption("Dormitorios").$$(TextFieldElement.class);
         supTotalTextFieldMinimo = $(HorizontalLayoutElement.class).caption("Sup. Total").$$(TextFieldElement.class);
         supTotalTextFieldMaximo = $(HorizontalLayoutElement.class).caption("Sup. Total").$$(TextFieldElement.class);
         supCubiertaTextFieldMinimo = $(HorizontalLayoutElement.class).caption("Sup. Cubierta").$$(TextFieldElement.class);
         supCubiertaTextFieldMaximo = $(HorizontalLayoutElement.class).caption("Sup. Cubierta").$$(TextFieldElement.class);
         aestrenarCheckBox = $(CheckBoxElement.class).caption("A estrenar");
         aireAcondicionadoCheckBox = $(CheckBoxElement.class).caption("Aire Acondicionado");
         jardnCheckBox = $(CheckBoxElement.class).caption("Jardín");
         parrilaCheckBox = $(CheckBoxElement.class).caption("Parrila");
         piletaCheckBox = $(CheckBoxElement.class).caption("Pileta");
         checkBoxGroup1 = $(CheckBoxGroupElement.class).caption("");
         guardarButton = $(ButtonElement.class).caption("Guardar");
         limpiarButton = $(ButtonElement.class).caption("Limpiar");
    }

    public ElementQuery<WindowElement> getProfilepreferenceswindow() { return profilepreferenceswindow; }

    public ElementQuery<TabSheetElement> getTabSheet1() { return tabSheet1; }

    public ElementQuery<RadioButtonGroupElement> getTipoRadioButtonGroup() { return tipoRadioButtonGroup; }

    public ElementQuery<RadioButtonGroupElement> getEstadoRadioButtonGroup() { return estadoRadioButtonGroup; }

    public ElementQuery<ComboBoxElement> getProvinciaComboBox() { return provinciaComboBox; }

    public ElementQuery<ComboBoxElement> getLocalidadComboBox() { return localidadComboBox; }

    public TextFieldElement getPrecioTextFieldMinimo() { return precioTextFieldMinimo.first(); }

    public TextFieldElement getPrecioTextFieldMaximo() { return precioTextFieldMaximo.get(1); }

    public ElementQuery<RadioButtonGroupElement> getTipoMonedaRadioButtonGroup() { return tipoMonedaRadioButtonGroup; }

    public TextFieldElement getAmbientesTextFieldMinimo() { return ambientesTextFieldMinimo.first(); }

    public TextFieldElement getAmbientesTextFieldMaximo() { return ambientesTextFieldMaximo.get(1); }

    public TextFieldElement getCocherasTextFieldMinimo() { return cocherasTextFieldMinimo.first(); }

    public TextFieldElement getCocherasTextFieldMaximo() { return cocherasTextFieldMaximo.get(1); }

    public TextFieldElement getDormitoriosTextFieldMinimo() { return dormitoriosTextFieldMinimo.first(); }

    public TextFieldElement getDormitoriosTextFieldMaximo() { return dormitoriosTextFieldMaximo.get(1); }

    public TextFieldElement getSupTotalTextFieldMinimo() { return supTotalTextFieldMinimo.first(); }

    public TextFieldElement getSupTotalTextFieldMaximo() { return supTotalTextFieldMaximo.get(1); }

    public TextFieldElement getSupCubiertaTextFieldMinimo() { return supCubiertaTextFieldMinimo.first(); }

    public TextFieldElement getSupCubiertaTextFieldMaximo() { return supCubiertaTextFieldMaximo.get(1); }

    public ElementQuery<CheckBoxElement> getAestrenarCheckBox() { return aestrenarCheckBox; }

    public ElementQuery<CheckBoxElement> getAireAcondicionadoCheckBox() { return aireAcondicionadoCheckBox; }

    public ElementQuery<CheckBoxElement> getJardnCheckBox() { return jardnCheckBox; }

    public ElementQuery<CheckBoxElement> getParrilaCheckBox() { return parrilaCheckBox; }

    public ElementQuery<CheckBoxElement> getPiletaCheckBox() { return piletaCheckBox; }

    public ElementQuery<CheckBoxGroupElement> getCheckBoxGroup1() { return checkBoxGroup1; }

    public ElementQuery<ButtonElement> getGuardarButton() { return guardarButton; }

    public ElementQuery<ButtonElement> getLimpiarButton() { return limpiarButton; }
}
