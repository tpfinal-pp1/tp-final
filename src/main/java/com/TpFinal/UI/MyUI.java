package com.TpFinal.UI;

import com.TpFinal.Services.Tasker;
import com.TpFinal.UI.Vistas.MainScreen;
import com.TpFinal.UI.authentication.AccessControl;
import com.TpFinal.UI.authentication.LoginScreen;
import com.TpFinal.UI.authentication.StrictAccessControl;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Viewport;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

import javax.servlet.annotation.WebServlet;

/**
 * Main UI class of the application that shows either the login screen or the
 * main view of the application depending on whether a user is signed in.
 *
 * The @Viewport annotation configures the viewport meta tags appropriately on
 * mobile devices. Instead of device based scaling (default), using responsive
 * layouts.
 */
@Viewport("user-scalable=no,initial-scale=1.0")
@Theme("newtheme")
public class MyUI extends UI {
//
    private AccessControl accessControl = new StrictAccessControl();
    private LoginScreen login;

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        Tasker tasker=Tasker.getTasker();
        if(!tasker.isAlive())
            tasker.start();

        Responsive.makeResponsive(this);
        setLocale(vaadinRequest.getLocale());
        getPage().setTitle("TpFinal");
        if (!accessControl.isUserSignedIn()) {
            login = new LoginScreen(accessControl, new LoginScreen.LoginListener() {
                @Override
                public void loginSuccessful() {
                    showMainView();
                }
            });
            setContent(login);
        } else {

            showMainView();
        }
    }

    protected void showMainView() {
        addStyleName(ValoTheme.UI_WITH_MENU);
        setContent(new MainScreen(MyUI.this));
        getNavigator().navigateTo(getNavigator().getState());
    }

    public static MyUI get() {
        return (MyUI) UI.getCurrent();
    }

    public AccessControl getAccessControl() {
        return this.accessControl;
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = true)
    public static class MyUIServlet extends VaadinServlet {
    }
}
