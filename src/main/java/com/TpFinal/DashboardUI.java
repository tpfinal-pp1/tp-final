package com.TpFinal;



import com.TpFinal.dto.cita.Cita;
import com.TpFinal.dto.cita.TipoCita;
import com.TpFinal.dto.persona.Empleado;
import com.TpFinal.services.*;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

    private final DashboardEventBus dashboardEventbus = new DashboardEventBus();
    private CredencialService credServ=new CredencialService();



    @Override
    protected void init(final VaadinRequest request) {

        GeneradorDeDatosSinAsociaciones.generarDatos(4);
        try {
            Planificador planificador=new Planificador();
            planificador.encender();
            planificador.setNotificacion(new NotificadorBus());
            List<Cita> citas= new ArrayList<>();

            for(int i=0; i< 10 ; i++) {
                LocalDateTime fInicio = LocalDateTime.now();
                fInicio=fInicio.plusMinutes(i+2);
                fInicio=fInicio.plusHours(24);

                Cita c = new Cita.Builder()
                        .setCitado("Señor "+String.valueOf(i))
                        .setDireccionLugar("sarasa: "+String.valueOf(i))
                        .setFechahora(fInicio)
                        .setObservaciones("obs"+String.valueOf(i))
                        .setTipoDeCita(TipoCita.Otros)
                        .build();
                c.setId(Long.valueOf(i));

                citas.add(c);
            }
            planificador.agregarNotificaciones(citas);}
            catch (Exception e){
            e.printStackTrace();
            }




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
        Empleado empleado = credServ.logIn(event.getUserName(),
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


    public static DashboardEventBus getDashboardEventbus() {
        return ((DashboardUI) getCurrent()).dashboardEventbus;
    }
}