package com.TpFinal.selenium.tb;

import com.TpFinal.selenium.tb.pageobjects.TBLoginView;
import com.TpFinal.selenium.tb.pageobjects.TBMainView;
import com.TpFinal.selenium.tb.pageobjects.TBProfileWindow;
import com.vaadin.testbench.elements.ButtonElement;
import com.vaadin.testbench.elements.LabelElement;
import com.vaadin.testbench.elements.TextFieldElement;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import com.vaadin.testbench.TestBenchTestCase;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

public class MainViewIT extends TestBenchTestCase {

    private TBLoginView loginView;
    private TBMainView mainView;




    @Test
    public void simpleTest() {
        setDriver(new PhantomJSDriver());
        getDriver().get("http://localhost:8080/");

        TextFieldElement nameInput = $(TextFieldElement.class).first();
        nameInput.setValue("Jeff");
        ButtonElement helloButton = $(ButtonElement.class).caption("Click Me")
                .first();
        helloButton.click();

        LabelElement outputLabel = $(LabelElement.class).first();
        Assert.assertEquals("Thanks Jeff, it works!", outputLabel.getText());
    }

    @Test
    public void testEditProfile() {
        TBProfileWindow profile = mainView.openProfileWindow();
        profile.setName("Test", "User");
        profile.commit();
        Assert.assertEquals("Test User", mainView.getUserFullName());
    }


}
