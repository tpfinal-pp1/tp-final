package com.TpFinal.Integracion.views.pageobjects.TBContratosView;

import com.vaadin.testbench.ElementQuery;
import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.*;
import org.openqa.selenium.WebDriver;

/**
 * Created by Max on 10/20/2017.
 */
public class TBDuracionContratosView extends TestBenchTestCase {

    private ElementQuery<GridElement> grid1 ;
    private ElementQuery<TextFieldElement> filterDuracion;
    private ElementQuery<ButtonElement> clearFilter;
    private ElementQuery<ButtonElement> nuevoButton ;
    private ElementQuery<HorizontalLayoutElement> accion0HorizontalLayout ;
    private ElementQuery<ButtonElement> editButton;
    private ElementQuery<ButtonElement> removeButton ;

    private ElementQuery<TextFieldElement> descripcionTextField ;
    private ElementQuery<TextFieldElement> cantidadTextField ;
    private ElementQuery<ButtonElement> guardarButton ;
    private ElementQuery<ButtonElement> cancelNuevoDuracion;

    private ElementQuery<WindowElement> eliminarWindow ;
    private ElementQuery<ButtonElement> noButton ;
    private ElementQuery<ButtonElement> siButton ;

    public TBDuracionContratosView(WebDriver driver){
         setDriver(driver);

         grid1 = $(GridElement.class);
         filterDuracion = $(TextFieldElement.class);
         clearFilter = $(VerticalLayoutElement.class).$(ButtonElement.class);
         nuevoButton = $(ButtonElement.class).caption("Nuevo");
         accion0HorizontalLayout = $(HorizontalLayoutElement.class);
         descripcionTextField = $(TextFieldElement.class).caption("Descripcion");
         cantidadTextField = $(TextFieldElement.class).caption("Cantidad");
         guardarButton = $(ButtonElement.class).caption("Guardar");
         cancelNuevoDuracion = $(VerticalLayoutElement.class).$(ButtonElement.class);

         eliminarWindow = $$(WindowElement.class).caption("Eliminar");
         noButton = $(ButtonElement.class).caption("No");
         siButton = $(ButtonElement.class).caption("Si");
    }

    private ElementQuery<HorizontalLayoutElement> getHorizontalLayoutAcciones(String caption) { return accion0HorizontalLayout.caption(caption); }

    private ElementQuery<ButtonElement> getButtonEdit(String caption) { return editButton = getHorizontalLayoutAcciones(caption).$$(ButtonElement.class); }

    private ElementQuery<ButtonElement> getButtonRemove(String caption) { return  removeButton = getHorizontalLayoutAcciones(caption).$$(ButtonElement.class); }

    public ButtonElement getEditButton(String caption){ return getButtonEdit(caption).first(); }

    public ButtonElement getRemoveButton(String caption){ return getButtonRemove(caption).get(1); }

    public ElementQuery<GridElement> getGrid1() { return grid1; }

    public ElementQuery<TextFieldElement> getFilterDuracion() { return filterDuracion; }

    public ElementQuery<ButtonElement> getClearFilter() { return clearFilter; }

    public ElementQuery<ButtonElement> getNuevoButton() { return nuevoButton; }

    public ElementQuery<TextFieldElement> getDescripcionTextField() { return descripcionTextField; }

    public ElementQuery<TextFieldElement> getCantidadTextField() { return cantidadTextField; }

    public ElementQuery<ButtonElement> getGuardarButton() { return guardarButton; }

    public ElementQuery<ButtonElement> getCancelNuevoDuracion() { return cancelNuevoDuracion; }

    public ElementQuery<WindowElement> getEliminarWindow() { return eliminarWindow; }

    public ElementQuery<ButtonElement> getNoButton() { return noButton; }

    public ElementQuery<ButtonElement> getSiButton() { return siButton; }

    public boolean isDisplayed(){ return this.grid1.exists();}

    public boolean isFormDisplayed(){return this.guardarButton.exists();}

    public boolean isEliminarWindowDisplayed(){return this.eliminarWindow.exists();}
}
