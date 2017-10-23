package com.TpFinal.Integracion.views.pageobjects.TBContratosView;

import com.vaadin.testbench.ElementQuery;
import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.*;
import org.openqa.selenium.WebDriver;

/**
 * Created by Max on 10/17/2017.
 */
public class TBContratoView extends TestBenchTestCase {

    private ElementQuery<GridElement> grid1;
    private ElementQuery<TextFieldElement> filterTextField;
    private ElementQuery<ButtonElement> cancelFilterButton;
    private ElementQuery<ButtonElement> nuevaVentaButton;
    private ElementQuery<ButtonElement> nuevoAlquilerButton ;
    private ElementQuery<HorizontalLayoutElement> accion0HorizontalLayout;
    private ElementQuery<ButtonElement> editButton;
    private ElementQuery<ButtonElement> finalizarContratoButton;
    private ElementQuery<ButtonElement> renovarContratoButton;
    private ElementQuery<ButtonElement> eliminarContratoButton;
    private ElementQuery<WindowElement> eliminarWindow ;
    private ElementQuery<ButtonElement> siButton ;
    private ElementQuery<ButtonElement> noButton ;

    private ElementQuery<ComboBoxElement> comboTipoContrato ;
    private ElementQuery<DateFieldElement> dateFieldDesde ;
    private ElementQuery<DateFieldElement> dateFieldHasta;
    private ElementQuery<ComboBoxElement> comboEstadoContrato ;
    private ElementQuery<TextFieldElement> filterDireccion ;
    private ElementQuery<TextFieldElement> filterIntervinientes ;
    private ElementQuery<TextFieldElement> textFieldAnio ;
    private ElementQuery<TextFieldElement> textFieldMes ;
    private ElementQuery<ButtonElement> botondesdeButton;



    public TBContratoView(WebDriver driver){
        setDriver(driver);

        grid1 = $(GridElement.class);
        filterTextField = $(TextFieldElement.class);
        cancelFilterButton = $(VerticalLayoutElement.class).$(ButtonElement.class);
        nuevaVentaButton = $(ButtonElement.class).caption("Nueva Venta");
        nuevoAlquilerButton = $(ButtonElement.class).caption("Nuevo Alquiler");
        accion0HorizontalLayout = $(HorizontalLayoutElement.class).caption("Accion 0");
        siButton = $(ButtonElement.class).caption("Si");
        noButton = $(ButtonElement.class).caption("No");

        textFieldAnio = $(TextFieldElement.class);
        filterDireccion= $(GridElement.class).$$(TextFieldElement.class);
        filterIntervinientes = $(GridElement.class).$$(TextFieldElement.class);
        textFieldMes = $(TextFieldElement.class);
        dateFieldDesde = $(DateFieldElement.class);
        dateFieldHasta = $(DateFieldElement.class);
        comboTipoContrato = $(ComboBoxElement.class);
        comboEstadoContrato = $(ComboBoxElement.class);
        botondesdeButton = $(ButtonElement.class).caption("Boton desde");
    }


    private ElementQuery<HorizontalLayoutElement> getHorizontalLayoutAcciones(String caption) { return accion0HorizontalLayout.caption(caption); }

    private ElementQuery<ButtonElement> getButtonEdit(String caption) { return editButton = getHorizontalLayoutAcciones(caption).$$(ButtonElement.class); }

    private ElementQuery<ButtonElement> getButtonFinalizar(String caption) { return  finalizarContratoButton = getHorizontalLayoutAcciones(caption).$$(ButtonElement.class); }

    private ElementQuery<ButtonElement> getButtonRenovar(String caption) { return  renovarContratoButton = getHorizontalLayoutAcciones(caption).$$(ButtonElement.class); }

    private ElementQuery<ButtonElement> getButtonEliminar(String caption) { return  eliminarContratoButton = getHorizontalLayoutAcciones(caption).$$(ButtonElement.class); }

    public ButtonElement getEditButton(String caption){ return getButtonEdit(caption).first(); }

    public ButtonElement getFinalizareButton(String caption){ return getButtonFinalizar(caption).get(1); }

    public ButtonElement getRenovarButton(String caption){ return getButtonRenovar(caption).get(2); }

    public ButtonElement getRemoveButton(String caption){ return getButtonEliminar(caption).get(3); }

    public TextFieldElement getAnioField(){ return  textFieldAnio.first(); }

    public TextFieldElement getFilterDireccion(){ return filterDireccion.first();}

    public TextFieldElement getFilterIntervinientes(){ return  filterIntervinientes.get(1);}

    public TextFieldElement getMesField(){ return textFieldMes.get(1);}

    public DateFieldElement getDateFieldDesde(){return dateFieldDesde.first();}

    public DateFieldElement getDateFieldHasta(){ return dateFieldHasta.get(1);}

    public ComboBoxElement getTipoCombo(){ return comboTipoContrato.first();}

    public ComboBoxElement getEstadoCombo(){return comboEstadoContrato.get(1);}

    public ElementQuery<GridElement> getGrid1() { return grid1; }

    public ElementQuery<TextFieldElement> getFilterTextField() { return filterTextField; }

    public ElementQuery<ButtonElement> getCancelFilterButton() { return cancelFilterButton; }

    public ElementQuery<ButtonElement> getNuevaVentaButton() { return nuevaVentaButton; }

    public ElementQuery<ButtonElement> getNuevoAlquilerButton(){ return nuevoAlquilerButton;}

    public ElementQuery<WindowElement> getEliminarWindow() { return eliminarWindow; }

    public ElementQuery<ButtonElement> getSiButton() { return siButton; }

    public ElementQuery<ButtonElement> getNoButton() { return noButton; }

    public boolean isDisplayed(){ return this.grid1.exists();}

    public boolean isEliminarWindowDisplayed(){ return eliminarWindow.exists();}
}
