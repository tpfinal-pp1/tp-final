package com.TpFinal.Integracion.views.pageobjects;

import com.vaadin.testbench.ElementQuery;
import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.ButtonElement;
import com.vaadin.testbench.elements.PasswordFieldElement;
import com.vaadin.testbench.elements.TextFieldElement;
import org.openqa.selenium.WebDriver;

public class TBLoginView extends TestBenchTestCase {


    public TBLoginView(WebDriver driver) {
        setDriver(driver);
    }

    public TBMainView login() {
        if(isDisplayed()) {
            getUserNameTextfield().first().setValue("Max");
            getUserPassTextField().first().setValue("1234");
            getLoginButton().first().click();
        }
        return new TBMainView(getDriver());
    }
    private ElementQuery<ButtonElement> getLoginButton() {
        return $(ButtonElement.class).caption("Iniciar Sesión");
    }

    private ElementQuery<TextFieldElement>  getUserNameTextfield(){ return $(TextFieldElement.class).caption("Usuario");}

    private ElementQuery<PasswordFieldElement>  getUserPassTextField(){ return $(PasswordFieldElement.class).caption("Contraseña");}

    public boolean isDisplayed() {
        return getLoginButton().exists();
    }


}
