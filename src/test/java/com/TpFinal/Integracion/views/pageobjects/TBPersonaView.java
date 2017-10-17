package com.TpFinal.Integracion.views.pageobjects;

import com.vaadin.testbench.ElementQuery;
import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.ButtonElement;
import com.vaadin.testbench.elements.GridElement;
import com.vaadin.testbench.elements.TextFieldElement;
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


    public TBPersonaView (WebDriver driver){
        setDriver(driver);
        peopleGrid             = $(GridElement.class);
        nuevaPersonaButton     = $(ButtonElement.class).caption("Nuevo");
        nameTextField          = $(TextFieldElement.class).caption("Nombre");
        surnameTextField       = $(TextFieldElement.class).caption("Apellido");
        DNITextField           = $(TextFieldElement.class).caption("DNI");
        mailTextField          = $(TextFieldElement.class).caption("Mail");
        telefonoTextField      = $(TextFieldElement.class).caption("Telefono");
        celularTextField       = $(TextFieldElement.class).caption("Celular");
        guardarButton          = $(ButtonElement.class).caption("Guardar");
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

    public boolean isDisplayed(){ return this.peopleGrid.exists(); }

    public boolean isFormDisplayed() { return this.guardarButton.exists(); }
}
