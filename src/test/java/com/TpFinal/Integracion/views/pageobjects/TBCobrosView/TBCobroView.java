package com.TpFinal.Integracion.views.pageobjects.TBCobrosView;

import com.vaadin.testbench.ElementQuery;
import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.*;
import org.openqa.selenium.WebDriver;

/**
 * Created by Max on 10/20/2017.
 */
public class TBCobroView extends TestBenchTestCase{

    private ElementQuery<GridElement> grid1 ;
    private ElementQuery<ButtonElement> botonLupa;
    private ElementQuery<TextFieldElement> textField1 ;
    private ElementQuery<ButtonElement> botonClearFilter;
    private ElementQuery<ButtonElement> button3 ;
    private ElementQuery<ButtonElement> button4 ;

    private ElementQuery<WindowElement> cobraralquilerWindow ;
    private ElementQuery<ButtonElement> noButton ;
    private ElementQuery<ButtonElement> siButton;
    private ElementQuery<ButtonElement> quitForm;

    private ElementQuery<WindowElement> filtrarWindow;
    private ElementQuery<RadioButtonGroupElement> radioButtonGroup1 ;

    public TBCobroView(WebDriver driver){
         setDriver(driver);

         grid1 = $(GridElement.class);
         botonLupa = $(VerticalLayoutElement.class).$(ButtonElement.class);
         textField1 = $(TextFieldElement.class);
         botonClearFilter = $(VerticalLayoutElement.class).$(ButtonElement.class);

         cobraralquilerWindow = $$(WindowElement.class).caption("Cobrar alquiler");
         noButton = $(ButtonElement.class).caption("No");
         siButton = $(ButtonElement.class).caption("Si");
         quitForm = $(VerticalLayoutElement.class).$(ButtonElement.class);

         filtrarWindow = $$(WindowElement.class).caption("Filtrar");
         radioButtonGroup1 = $(RadioButtonGroupElement.class);
    }

    private ElementQuery<ButtonElement> getEdit(String caption){ return  button3 = $(CssLayoutElement.class).caption(caption).$$(ButtonElement.class);}

    private ElementQuery<ButtonElement> getRemove(String caption){ return  button4 = $(CssLayoutElement.class).caption(caption).$$(ButtonElement.class);}

    public ButtonElement getEditButton(String caption){ return getEdit(caption).first();}

    public ButtonElement getRemoveButton(String caption){ return getEdit(caption).get(1);}

    public ButtonElement getLupaButton(){ return botonLupa.first();}

    public ButtonElement getClearFilter(String caption){ return botonClearFilter.get(1);}

    public ElementQuery<GridElement> getGrid1() { return grid1; }

    public ElementQuery<TextFieldElement> getTextField1() { return textField1; }

    public ElementQuery<WindowElement> getCobraralquilerWindow() { return cobraralquilerWindow; }

    public ElementQuery<ButtonElement> getNoButton() { return noButton; }

    public ElementQuery<ButtonElement> getSiButton() { return siButton; }

    public ElementQuery<ButtonElement> getQuitForm() { return quitForm; }

    public boolean isDisplayed(){return this.grid1.exists();}

    public boolean isFormDisplayed(){ return quitForm.exists();}

    public boolean isCobrarWindowDisplayed(){return cobraralquilerWindow.exists();}

    public ElementQuery<WindowElement> getFiltrarWindow() { return filtrarWindow; }

    public ElementQuery<RadioButtonGroupElement> getRadioButtonGroup1() { return radioButtonGroup1; }

    public boolean isFilterWindowDisplayed(){ return this.filtrarWindow.exists();}
}
