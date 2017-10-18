package com.TpFinal.Integracion.views.pageobjects;

import com.vaadin.testbench.ElementQuery;
import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.ButtonElement;
import org.openqa.selenium.WebDriver;

public class TBLoginView extends TestBenchTestCase {


    public TBLoginView(WebDriver driver) {
        setDriver(driver);
    }

    public TBMainView login() {
        if(isDisplayed()) {
            getLoginButton().first().click();
        }
        return new TBMainView(getDriver());
    }
    private ElementQuery<ButtonElement> getLoginButton() {
        return $(ButtonElement.class).caption("Iniciar Sesión");
    }


    public boolean isDisplayed() {
        return getLoginButton().exists();
    }


}
