package com.TpFinal;



import com.TpFinal.dto.persona.Empleado;
import com.TpFinal.dto.persona.ViewAccess;
import com.TpFinal.services.DashboardEvent;
import com.TpFinal.services.DashboardEventBus;
import com.TpFinal.utils.DataProviderImpl;

import com.TpFinal.utils.DataProvider;
import com.TpFinal.utils.GeneradorDeDatosSinAsociaciones;
import com.TpFinal.view.LoginView;
import com.TpFinal.view.MainView;
import com.google.common.eventbus.Subscribe;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.Page;
import com.vaadin.server.Page.BrowserWindowResizeEvent;
import com.vaadin.server.Page.BrowserWindowResizeListener;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import java.util.Locale;

@Theme("dashboard")
@Widgetset("com.TpFinal.DashboardWidgetSet")
@Title("Inmobi")
@SuppressWarnings("serial")
public final class DashboardUI extends UI {

    /*
     * This field stores an access to the dummy backend layer. In real
     * applications you most likely gain access to your beans trough lookup or
     * injection; and not in the UI but somewhere closer to where they're
     * actually accessed.
     */
    private final DataProviderImpl dataProvider = new DataProviderImpl();
    private final DashboardEventBus dashboardEventbus = new DashboardEventBus();



        @Override
        protected void init(final VaadinRequest request) {
         
            GeneradorDeDatosSinAsociaciones.generarDatos(4);
            setLocale(Locale.forLanguageTag("es-AR"));

            DashboardEventBus.register(this);
            Responsive.makeResponsive(this);
            addStyleName(ValoTheme.UI_WITH_MENU);
            
            updateContent();
            


            // Some views need to be aware of browser resize events so a
            // BrowserResizeEvent gets fired to the event bus on every occasion.
        Page.getCurrent().addBrowserWindowResizeListener(
                new BrowserWindowResizeListener() {
                    @Override
                    public void browserWindowResized(
                            final BrowserWindowResizeEvent event) {
                        DashboardEventBus.post(new DashboardEvent.BrowserResizeEvent());
                    }
                });
    }

    /**
     * Updates the correct content for this UI based on the current user status.
     * If the user is logged in with appropriate privileges, main view is shown.
     * Otherwise login view is shown.
     */
    private void updateContent() {
        Empleado empleado = (Empleado) VaadinSession.getCurrent()
                .getAttribute(Empleado.class.getName());
        if(empleado==null){
            setContent(new LoginView());
            //Cuando recien inicia entra con empleado=null
        }
        else if(empleado.getCredencial()==null){
            setContent(new LoginView());
            Notification.show("Usuario o Contraseña Incorrectos");
        }
        else if(empleado.getCredencial().getViewAccess()==null){
                setContent(new LoginView());
                Notification.show("Credenciales sin acceso al sistema");
            }
        else{
            setContent(new MainView());
            removeStyleName("loginview");
            getNavigator().navigateTo(getNavigator().getState());
        }
      }


    @Subscribe
    public void userLoginRequested(final DashboardEvent.UserLoginRequestedEvent event) {
        Empleado empleado = getDataProvider().authenticate(event.getUserName(),
                event.getPassword());
        VaadinSession.getCurrent().setAttribute(Empleado.class.getName(), empleado);
        updateContent();
    }

    @Subscribe
    public void userLoggedOut(final DashboardEvent.UserLoggedOutEvent event) {
        // When the user logs out, current VaadinSession gets closed and the
        // page gets reloaded on the login screen. Do notice the this doesn't
        // invalidate the current HttpSession.
        VaadinSession.getCurrent().close();
        Page.getCurrent().reload();
    }

    @Subscribe
    public void closeOpenWindows(final DashboardEvent.CloseOpenWindowsEvent event) {
        for (Window window : getWindows()) {
            window.close();
        }
    }

    /**
     * @return An instance for accessing the (dummy) services layer.
     */
    public static DataProvider getDataProvider() {
        return ((DashboardUI) getCurrent()).dataProvider;
    }

    public static DashboardEventBus getDashboardEventbus() {
        return ((DashboardUI) getCurrent()).dashboardEventbus;
    }
}
