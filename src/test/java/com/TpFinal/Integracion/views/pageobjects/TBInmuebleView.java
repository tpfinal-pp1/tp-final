package com.TpFinal.Integracion.views.pageobjects;

import com.vaadin.testbench.ElementQuery;
import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.*;
import org.openqa.selenium.WebDriver;

/**
 * Created by Max on 10/17/2017.
 */
public class TBInmuebleView extends TestBenchTestCase{


    private ElementQuery<GridElement> grid                             = $(GridElement.class);
    private ElementQuery<TextFieldElement> filterText                  = $(TextFieldElement.class);
    private ElementQuery<ButtonElement> quitFilter                     = $(VerticalLayoutElement.class).$(ButtonElement.class);
    private ElementQuery<ButtonElement> nuevoInmuebleButton            = $(ButtonElement.class).caption("Nuevo");
    private ElementQuery<TabSheetElement> tabSheet                     = $(TabSheetElement.class);
    private ElementQuery<ComboBoxElement> comboBoxPropietario          = $(ComboBoxElement.class);
    private ElementQuery<ButtonElement> nuevpPropietarioButton         = $(FormLayoutElement.class).$(ButtonElement.class);
    private ElementQuery<ComboBoxElement> claseComboBox                = $(ComboBoxElement.class).caption("Clase");
    private ElementQuery<RadioButtonGroupElement> tipoRadioButtonGroup = $(RadioButtonGroupElement.class).caption("Tipo");
    private ElementQuery<TextFieldElement> calleTextField              = $(TextFieldElement.class).caption("Calle");
    private ElementQuery<TextFieldElement> numeroTextField             = $(TextFieldElement.class).caption("Número");
    private ElementQuery<ComboBoxElement> provinciaComboBox            = $(ComboBoxElement.class).caption("Provincia");
    private ElementQuery<ComboBoxElement> localidadComboBox            = $(ComboBoxElement.class).caption("Localidad");
    private ElementQuery<TextFieldElement> codigopostalTextField       = $(TextFieldElement.class).caption("Código postal");
    private ElementQuery<ButtonElement> buscarUbicacionButton          = $(ButtonElement.class).caption("Buscar Ubicación");
    private ElementQuery<ButtonElement> guardarButton                  = $(ButtonElement.class).caption("Guardar");
    private ElementQuery<ButtonElement> llenarcomboButton              = $(ButtonElement.class).caption("llenar combo");
    private ElementQuery<TextFieldElement> ambientesTextField          = $(TextFieldElement.class).caption("Ambientes");
    private ElementQuery<TextFieldElement> cocherasTextField           = $(TextFieldElement.class).caption("Cocheras");
    private ElementQuery<TextFieldElement> dormitoriosTextField        = $(TextFieldElement.class).caption("Dormitorios");
    private ElementQuery<TextFieldElement> supTotalTextField           = $(TextFieldElement.class).caption("Sup. Total");
    private ElementQuery<TextFieldElement> supCubiertaTextField        = $(TextFieldElement.class).caption("Sup. Cubierta");
    private ElementQuery<CheckBoxElement> aestrenarCheckBox            = $(CheckBoxElement.class).caption("A estrenar");
    private ElementQuery<CheckBoxElement> aireAcondicionadoCheckBox    = $(CheckBoxElement.class).caption("Aire Acondicionado");
    private ElementQuery<CheckBoxElement> jardnCheckBox                = $(CheckBoxElement.class).caption("Jardín");
    private ElementQuery<CheckBoxElement> parrillaCheckBox             = $(CheckBoxElement.class).caption("Parrilla");
    private ElementQuery<CheckBoxElement> piletaCheckBox               = $(CheckBoxElement.class).caption("Pileta");


    public TBInmuebleView(WebDriver driver){
        setDriver(driver);
        grid                        = $(GridElement.class);
        filterText                  = $(TextFieldElement.class);
        quitFilter                  = $(VerticalLayoutElement.class).$(ButtonElement.class);
        nuevoInmuebleButton         = $(ButtonElement.class).caption("Nuevo");
        tabSheet                    = $(TabSheetElement.class);
        comboBoxPropietario         = $(ComboBoxElement.class);
        nuevpPropietarioButton      = $(FormLayoutElement.class).$(ButtonElement.class);
        claseComboBox               = $(ComboBoxElement.class).caption("Clase");
        tipoRadioButtonGroup        = $(RadioButtonGroupElement.class).caption("Tipo");
        calleTextField              = $(TextFieldElement.class).caption("Calle");
        numeroTextField             = $(TextFieldElement.class).caption("Número");
        provinciaComboBox           = $(ComboBoxElement.class).caption("Provincia");
        localidadComboBox           = $(ComboBoxElement.class).caption("Localidad");
        codigopostalTextField       = $(TextFieldElement.class).caption("Código postal");
        buscarUbicacionButton       = $(ButtonElement.class).caption("Buscar Ubicación");
        guardarButton               = $(ButtonElement.class).caption("Guardar");
        llenarcomboButton           = $(ButtonElement.class).caption("llenar combo");
        ambientesTextField          = $(TextFieldElement.class).caption("Ambientes");
        cocherasTextField           = $(TextFieldElement.class).caption("Cocheras");
        dormitoriosTextField        = $(TextFieldElement.class).caption("Dormitorios");
        supTotalTextField           = $(TextFieldElement.class).caption("Sup. Total");
        supCubiertaTextField        = $(TextFieldElement.class).caption("Sup. Cubierta");
        aestrenarCheckBox           = $(CheckBoxElement.class).caption("A estrenar");
        aireAcondicionadoCheckBox   = $(CheckBoxElement.class).caption("Aire Acondicionado");
        jardnCheckBox               = $(CheckBoxElement.class).caption("Jardín");
        parrillaCheckBox            = $(CheckBoxElement.class).caption("Parrilla");
        piletaCheckBox              = $(CheckBoxElement.class).caption("Pileta");
    }


    public ElementQuery<GridElement> getGrid() { return grid; }

    public ElementQuery<TextFieldElement> getFilterText() { return filterText; }

    public ElementQuery<ButtonElement> getQuitFilter() { return quitFilter; }

    public ElementQuery<ButtonElement> getNuevoInmuebleButton() { return nuevoInmuebleButton; }

    public ElementQuery<TabSheetElement> getTabSheet() { return tabSheet; }

    public ElementQuery<ComboBoxElement> getComboBoxPropietario() { return comboBoxPropietario; }

    public ElementQuery<ButtonElement> getNuevpPropietarioButton() { return nuevpPropietarioButton; }

    public ElementQuery<ComboBoxElement> getClaseComboBox() { return claseComboBox; }

    public ElementQuery<RadioButtonGroupElement> getTipoRadioButtonGroup() { return tipoRadioButtonGroup; }

    public ElementQuery<TextFieldElement> getCalleTextField() { return calleTextField; }

    public ElementQuery<TextFieldElement> getNumeroTextField() { return numeroTextField; }

    public ElementQuery<ComboBoxElement> getProvinciaComboBox() { return provinciaComboBox; }

    public ElementQuery<ComboBoxElement> getLocalidadComboBox() { return localidadComboBox; }

    public ElementQuery<TextFieldElement> getCodigopostalTextField() { return codigopostalTextField; }

    public ElementQuery<ButtonElement> getBuscarUbicacionButton() { return buscarUbicacionButton; }

    public ElementQuery<ButtonElement> getGuardarButton() { return guardarButton; }

    public ElementQuery<ButtonElement> getLlenarcomboButton() { return llenarcomboButton; }

    public ElementQuery<TextFieldElement> getAmbientesTextField() { return ambientesTextField; }

    public ElementQuery<TextFieldElement> getCocherasTextField() { return cocherasTextField; }

    public ElementQuery<TextFieldElement> getDormitoriosTextField() { return dormitoriosTextField; }

    public ElementQuery<TextFieldElement> getSupTotalTextField() { return supTotalTextField; }

    public ElementQuery<TextFieldElement> getSupCubiertaTextField() { return supCubiertaTextField; }

    public ElementQuery<CheckBoxElement> getAestrenarCheckBox() { return aestrenarCheckBox; }

    public ElementQuery<CheckBoxElement> getAireAcondicionadoCheckBox() { return aireAcondicionadoCheckBox; }

    public ElementQuery<CheckBoxElement> getJardnCheckBox() { return jardnCheckBox; }

    public ElementQuery<CheckBoxElement> getParrillaCheckBox() { return parrillaCheckBox; }

    public ElementQuery<CheckBoxElement> getPiletaCheckBox() { return piletaCheckBox; }

    public boolean isDisplayed(){ return this.grid.exists(); }

    public boolean isFormDisplayed(){ return this.guardarButton.exists(); }
}
