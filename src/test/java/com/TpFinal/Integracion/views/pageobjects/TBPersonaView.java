package com.TpFinal.Integracion.views.pageobjects;

import com.vaadin.testbench.ElementQuery;
import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.*;
import org.openqa.selenium.WebDriver;

/**
 * Created by Max on 10/17/2017.
 */
public class TBPersonaView extends TestBenchTestCase{


    private ElementQuery<GridElement> peopleGrid            ;
    private ElementQuery<ButtonElement> nuevaPersonaButton   ;
    private ElementQuery<TextFieldElement> nameTextField     ;
    private ElementQuery<TextFieldElement> surnameTextField  ;
    private ElementQuery<TextFieldElement> DNITextField      ;
    private ElementQuery<TextFieldElement> mailTextField    ;
    private ElementQuery<TextFieldElement> telefonoTextField ;
    private ElementQuery<TextFieldElement> celularTextField ;
    private ElementQuery<ButtonElement> guardarButton        ;
    private ElementQuery<HorizontalLayoutElement> horizontalLayoutAcciones;
    private ElementQuery<ButtonElement> buttonEdit ;
    private ElementQuery<ButtonElement> buttonRemove ;
    private ElementQuery<ButtonElement> buttonCriterio ;
    private ElementQuery<ButtonElement> siButton ;
    private ElementQuery<ButtonElement> noButton ;
    private ElementQuery<WindowElement> eliminarWindow ;
    private ElementQuery<TextFieldElement> filterField;
    private ElementQuery<ButtonElement> botonLupa;
    private ElementQuery<ButtonElement> botonCerrarFilter;


    public TBPersonaView (WebDriver driver){
        setDriver(driver);
        peopleGrid               = $(GridElement.class);
        nuevaPersonaButton       = $(ButtonElement.class).caption("Nuevo");
        nameTextField            = $(TextFieldElement.class).caption("Nombre");
        surnameTextField         = $(TextFieldElement.class).caption("Apellido");
        DNITextField             = $(TextFieldElement.class).caption("DNI");
        mailTextField            = $(TextFieldElement.class).caption("Mail");
        telefonoTextField        = $(TextFieldElement.class).caption("Telefono");
        celularTextField         = $(TextFieldElement.class).caption("Celular");
        guardarButton            = $(ButtonElement.class).caption("Guardar");
        horizontalLayoutAcciones = $(GridElement.class).$$(HorizontalLayoutElement.class);
        siButton                 = $(ButtonElement.class).caption("Si");
        noButton                 = $(ButtonElement.class).caption("No");
        eliminarWindow           = $$(WindowElement.class).caption("Eliminar");
        filterField = $(TextFieldElement.class);
        botonLupa = $(VerticalLayoutElement.class).$(ButtonElement.class);
        botonCerrarFilter = $(VerticalLayoutElement.class).$(ButtonElement.class);
    }


    public ElementQuery <GridElement> getPeopleGrid() { return peopleGrid ; }

    public ElementQuery <ButtonElement> getNuevaPersonaButton() { return nuevaPersonaButton ; }

    public ElementQuery<TextFieldElement> getNameTextField() { return nameTextField;  }

    public ElementQuery<TextFieldElement> getSurnameTextField() { return surnameTextField; }

    public ElementQuery<TextFieldElement> getDNITextField() { return DNITextField;  }

    public ElementQuery<TextFieldElement> getMailTextField() { return mailTextField; }

    public ElementQuery<TextFieldElement> getTelefonoTextField() { return telefonoTextField;  }

    public ElementQuery<TextFieldElement> getCelularTextField() { return celularTextField;  }

    public ElementQuery<ButtonElement> getGuardarButton() { return guardarButton; }

    private ElementQuery<HorizontalLayoutElement> getHorizontalLayoutAcciones(String caption) { return horizontalLayoutAcciones.caption(caption); }

    private ElementQuery<ButtonElement> getButtonEdit(String caption) { return buttonEdit = getHorizontalLayoutAcciones(caption).$$(ButtonElement.class); }

    private ElementQuery<ButtonElement> getButtonRemove(String caption) { return  buttonRemove = getHorizontalLayoutAcciones(caption).$$(ButtonElement.class); }

    private ElementQuery<ButtonElement> getButtonCriterio(String caption) { return  buttonCriterio = getHorizontalLayoutAcciones(caption).$$(ButtonElement.class); }

    public ButtonElement getEditButton(String caption){ return getButtonEdit(caption).first(); }

    public ButtonElement getRemoveButton(String caption){ return getButtonRemove(caption).get(1); }

    public ButtonElement getCriterioButton(String caption){ return getButtonCriterio(caption).get(2); }

    public ElementQuery<TextFieldElement> getFilterField(){return filterField;}

    public ButtonElement getButtonLupa(){return botonLupa.first();}

    public ButtonElement getButtonCerrarFilter(){return botonCerrarFilter.get(1);}

    public boolean isDisplayed(){ return this.peopleGrid.exists(); }

    public boolean isFormDisplayed() { return this.guardarButton.exists(); }

    public ElementQuery<ButtonElement> getSiButton() { return siButton; }

    public ElementQuery<ButtonElement> getNoButton() { return noButton; }

    public ElementQuery<WindowElement> getEliminarWindow() { return eliminarWindow; }

    public boolean isEliminarWindowDisplayed(){return this.eliminarWindow.exists();}
}
