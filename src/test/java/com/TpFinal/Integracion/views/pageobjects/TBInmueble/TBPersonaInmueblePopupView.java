package com.TpFinal.Integracion.views.pageobjects.TBInmueble;

import com.vaadin.testbench.ElementQuery;
import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.ButtonElement;
import com.vaadin.testbench.elements.TextAreaElement;
import com.vaadin.testbench.elements.TextFieldElement;
import com.vaadin.testbench.elements.WindowElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Max on 10/17/2017.
 */
public class TBPersonaInmueblePopupView  extends TestBenchTestCase{


    private ElementQuery<TextFieldElement> nombreTextField  ;
    private ElementQuery<TextFieldElement> apellidoTextField ;
    private ElementQuery<TextFieldElement> dNITextField ;
    private ElementQuery<TextFieldElement> emailTextField ;
    private ElementQuery<TextFieldElement> telfonoTextField ;
    private ElementQuery<TextFieldElement> celularTextField ;
    private ElementQuery<TextAreaElement> infoTextArea ;
    private ElementQuery<ButtonElement> guardarPersonaButton ;
    @FindBy(id = "profilepreferenceswindow")
    private ElementQuery<WindowElement> profilepreferenceswindow ;

    public TBPersonaInmueblePopupView(WebDriver driver){
        setDriver(driver);

        nombreTextField = $(TextFieldElement.class).caption("Nombre");
        apellidoTextField = $(TextFieldElement.class).caption("Apellido");
        dNITextField = $(TextFieldElement.class).caption("DNI");
        emailTextField = $(TextFieldElement.class).caption("Email");
        telfonoTextField = $(TextFieldElement.class).caption("Teléfono");
        celularTextField = $(TextFieldElement.class).caption("Celular");
        infoTextArea = $(TextAreaElement.class).caption("Info");
        guardarPersonaButton = $(ButtonElement.class).caption("Guardar Persona");
        profilepreferenceswindow = $$(WindowElement.class);

    }

    public ElementQuery<TextFieldElement> getNombreTextField() { return nombreTextField; }

    public ElementQuery<TextFieldElement> getApellidoTextField() { return apellidoTextField; }

    public ElementQuery<TextFieldElement> getdNITextField() { return dNITextField; }

    public ElementQuery<TextFieldElement> getEmailTextField() { return emailTextField; }

    public ElementQuery<TextFieldElement> getTelfonoTextField() { return telfonoTextField; }

    public ElementQuery<TextFieldElement> getCelularTextField() { return celularTextField; }

    public ElementQuery<TextAreaElement> getInfoTextArea() { return infoTextArea; }

    public ElementQuery<ButtonElement> getGuardarPersonaButton() { return guardarPersonaButton; }

    public ElementQuery<WindowElement> getProfilepreferenceswindow() { return profilepreferenceswindow; }
}
