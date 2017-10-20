package com.TpFinal.Integracion.views.pageobjects;

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




    public TBContratoView(WebDriver driver){
        setDriver(driver);

        grid1 = $(GridElement.class);
        filterTextField = $(TextFieldElement.class);
        cancelFilterButton = $(VerticalLayoutElement.class).$(ButtonElement.class);
        nuevaVentaButton = $(ButtonElement.class).caption("Nueva Venta");
        nuevoAlquilerButton = $(ButtonElement.class).caption("Nuevo Alquiler");
        accion0HorizontalLayout = $(HorizontalLayoutElement.class).caption("Accion 0");
        editButton = $(HorizontalLayoutElement.class).caption("Accion 0").$$(ButtonElement.class);
        finalizarContratoButton = $(HorizontalLayoutElement.class).caption("Accion 0").$$(ButtonElement.class);
        renovarContratoButton = $(HorizontalLayoutElement.class).caption("Accion 0").$$(ButtonElement.class);
        eliminarContratoButton = $(HorizontalLayoutElement.class).caption("Accion 0").$$(ButtonElement.class);
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

    public ElementQuery<GridElement> getGrid1() { return grid1; }

    public ElementQuery<TextFieldElement> getFilterTextField() { return filterTextField; }

    public ElementQuery<ButtonElement> getCancelFilterButton() { return cancelFilterButton; }

    public ElementQuery<ButtonElement> getNuevaVentaButton() { return nuevaVentaButton; }

    public ElementQuery<ButtonElement> getNuevoAlquilerButton(){ return nuevoAlquilerButton;}

    public boolean isDisplayed(){ return this.grid1.exists();}
}
