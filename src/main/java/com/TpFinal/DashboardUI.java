package com.TpFinal;

import com.TpFinal.data.conexion.ConexionHibernate;
import com.TpFinal.dto.persona.Empleado;
import com.TpFinal.services.*;
import com.TpFinal.view.LoginView;
import com.TpFinal.view.MainView;
import com.TpFinal.view.component.BackupWindow;
import com.google.common.eventbus.Subscribe;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Widgetset;
import com.vaadin.event.UIEvents;
import com.vaadin.server.Page;
import com.vaadin.server.Page.BrowserWindowResizeEvent;
import com.vaadin.server.Page.BrowserWindowResizeListener;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.Position;
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
     * This field stores an access to the dummy backend layer. In real applications
     * you most likely gain access to your beans trough lookup or injection; and not
     * in the UI but somewhere closer to where they're actually accessed.
     */

    private final DashboardEventBus dashboardEventbus = new DashboardEventBus();
    private CredencialService credServ = new CredencialService();
    private boolean alreadyWatingForBackupToEnd =false;

    @Override
    protected void init(final VaadinRequest request) {

	getUI().getCurrent().setPollInterval(3000);
	getUI().getCurrent().addPollListener(new UIEvents.PollListener() {
	    @Override
	    public void poll(UIEvents.PollEvent event) {
			if (!VaadinSession.getCurrent().equals(BackupWindow.getVaadinSession())) {

				if (ConexionHibernate.isBackupmode()) {
					if(!alreadyWatingForBackupToEnd){
						alreadyWatingForBackupToEnd = true;
						for (int i = 0; i < 999; i++) {
							showWaitNotification();
						}
						Page page=Page.getCurrent();
						VaadinSession session=VaadinSession.getCurrent();
						new Thread(() -> {
							while (ConexionHibernate.isBackupmode())
								try {
									Thread.sleep(1000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							alreadyWatingForBackupToEnd =false;
							session.close();
							page.reload();
						}).start();
					}



				} else {
					DashboardEventBus.post(new DashboardEvent.NotificationsCountUpdatedEvent());
				}

			}


	    }
	});

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
     * Updates the correct content for this UI based on the current user status. If
     * the user is logged in with appropriate privileges, main view is shown.
     * Otherwise login view is shown.
     */
    private void updateContent() {
	Empleado empleado = (Empleado) VaadinSession.getCurrent()
		.getAttribute(Empleado.class.getName());
	if (empleado == null) {
		if(credServ.isfirstRun())
			setContent(new LoginView(true));
		else{
			setContent(new LoginView());
		}

	    // Cuando recien inicia entra con empleado=null
	} else if (empleado.getCredencial() == null) {
	    setContent(new LoginView());
	    showErrorNotification("Usuario o Contraseña Incorrectos");
	} else if (empleado.getCredencial().getViewAccess() == null) {
	    setContent(new LoginView());
	    showErrorNotification("Credenciales sin acceso al sistema");
	} else {
	    setContent(new MainView());
	    removeStyleName("loginview");
	    getNavigator().navigateTo(getNavigator().getState());
	}
    }
	public static void showWaitNotification() {
		Notification success = new Notification(
				"Sistema Inmobi se encuentra en mantenimiento, \n" +
						"porfavor espere a que el administrador concluya el proceso de mantenimiento");
		success.setDelayMsec(999999999);
		success.setStyleName("bar error small");
		success.setPosition(Position.MIDDLE_CENTER);
		success.show(Page.getCurrent());
	}
	public static void showErrorNotification(String notification) {
		Notification success = new Notification(
				notification);
		success.setDelayMsec(4000);
		success.setStyleName("bar error small");
		success.setPosition(Position.BOTTOM_CENTER);
		success.show(Page.getCurrent());
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

    public static Empleado getEmpleadoLogueado() {
	Empleado empleado = null;
	if(VaadinSession.getCurrent()
                .getAttribute(Empleado.class.getName()) != null)
	 empleado= (Empleado) VaadinSession.getCurrent()
                .getAttribute(Empleado.class.getName());
	return empleado;
    }

    public static DashboardEventBus getDashboardEventbus() {
	if (getCurrent() == null) {
	    DashboardUI dashboardUI = new DashboardUI();
	    setCurrent(dashboardUI);
	}
	return ((DashboardUI) getCurrent()).dashboardEventbus;
    }
}