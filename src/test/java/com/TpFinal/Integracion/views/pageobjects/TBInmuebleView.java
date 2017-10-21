package com.TpFinal.Integracion.views.pageobjects;

import com.vaadin.testbench.ElementQuery;
import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.*;
import org.openqa.selenium.WebDriver;

/**
 * Created by Max on 10/17/2017.
 */
public class TBInmuebleView extends TestBenchTestCase{


    private ElementQuery<GridElement> grid    ;
    private ElementQuery<TextFieldElement> filterText                ;
    private ElementQuery<ButtonElement> inmuebleLupa;
    private ElementQuery<ButtonElement> quitFilter                    ;
    private ElementQuery<ButtonElement> nuevoInmuebleButton          ;
    private ElementQuery<TabSheetElement> tabSheet                    ;
    private ElementQuery<ComboBoxElement> comboBoxPropietario         ;
    private ElementQuery<ButtonElement> nuevpPropietarioButton         ;
    private ElementQuery<ComboBoxElement> claseComboBox               ;
    private ElementQuery<RadioButtonGroupElement> tipoRadioButtonGroup ;
    private ElementQuery<TextFieldElement> calleTextField             ;
    private ElementQuery<TextFieldElement> numeroTextField            ;
    private ElementQuery<ComboBoxElement> provinciaComboBox            ;
    private ElementQuery<ComboBoxElement> localidadComboBox           ;
    private ElementQuery<TextFieldElement> codigopostalTextField     ;
    private ElementQuery<ButtonElement> buscarUbicacionButton        ;
    private ElementQuery<ButtonElement> guardarButton                ;
    private ElementQuery<ButtonElement> llenarcomboButton             ;
    private ElementQuery<TextFieldElement> ambientesTextField         ;
    private ElementQuery<TextFieldElement> cocherasTextField       ;
    private ElementQuery<TextFieldElement> dormitoriosTextField        ;
    private ElementQuery<TextFieldElement> supTotalTextField          ;
    private ElementQuery<TextFieldElement> supCubiertaTextField      ;
    private ElementQuery<CheckBoxElement> aestrenarCheckBox            ;
    private ElementQuery<CheckBoxElement> aireAcondicionadoCheckBox    ;
    private ElementQuery<CheckBoxElement> jardnCheckBox               ;
    private ElementQuery<CheckBoxElement> parrillaCheckBox            ;
    private ElementQuery<CheckBoxElement> piletaCheckBox               ;
    private ElementQuery<HorizontalLayoutElement> horizontalLayoutAcciones;
    private ElementQuery<ButtonElement> buttonEdit ;
    private ElementQuery<ButtonElement> buttonRemove ;
    private ElementQuery<ButtonElement> buttonImage;

    private TBPersonaInmueblePopupView personaInmueblePopupView;


    public TBInmuebleView(WebDriver driver){
        setDriver(driver);
        grid                        = $(GridElement.class);
        filterText                  = $(TextFieldElement.class);
        inmuebleLupa                = $(VerticalLayoutElement.class).$(ButtonElement.class);
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
        horizontalLayoutAcciones =  $(HorizontalLayoutElement.class);
    }


    public ElementQuery<GridElement> getGrid() { return grid; }

    public ElementQuery<TextFieldElement> getFilterText() { return filterText; }

    public ElementQuery<ButtonElement> getQuitFilter() { return quitFilter; }

    public ElementQuery<ButtonElement> getInmuebleLupa() { return inmuebleLupa; }

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

    private ElementQuery<HorizontalLayoutElement> getHorizontalLayoutAcciones(String caption) { return horizontalLayoutAcciones.caption(caption); }

    private ElementQuery<ButtonElement> getButtonEdit(String caption) { return buttonEdit = getHorizontalLayoutAcciones(caption).$$(ButtonElement.class); }

    private ElementQuery<ButtonElement> getButtonRemove(String caption) { return  buttonRemove = getHorizontalLayoutAcciones(caption).$$(ButtonElement.class); }

    private ElementQuery<ButtonElement> getButtonImage(String caption) { return  buttonImage = getHorizontalLayoutAcciones(caption).$$(ButtonElement.class); }

    public ButtonElement getEditButton(String caption){ return getButtonEdit(caption).first(); }

    public ButtonElement getRemoveButton(String caption){ return getButtonRemove(caption).get(1); }

    public ButtonElement getImageButton(String caption){ return getButtonImage(caption).get(2); }

    public boolean isDisplayed(){ return this.grid.exists(); }

    public boolean isFormDisplayed(){ return this.guardarButton.exists(); }

    public TBPersonaInmueblePopupView getPersonaInmueblePopupView() { return personaInmueblePopupView = new TBPersonaInmueblePopupView(getDriver()); }
}
