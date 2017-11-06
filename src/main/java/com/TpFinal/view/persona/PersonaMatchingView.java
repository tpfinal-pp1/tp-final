package com.TpFinal.view.persona;

import com.TpFinal.dto.inmueble.Inmueble;
import com.TpFinal.dto.persona.Inquilino;
import com.TpFinal.dto.persona.Persona;
import com.TpFinal.dto.persona.Rol;
import com.TpFinal.services.DashboardEvent;
import com.TpFinal.services.PersonaService;
import com.TpFinal.view.component.DefaultLayout;
import com.TpFinal.view.component.DialogConfirmacion;
import com.TpFinal.view.component.PreferenciasBusqueda;
import com.google.common.eventbus.Subscribe;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.data.ValueProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.themes.ValoTheme;

import java.util.List;
import java.util.function.Supplier;

@Title("Clientes")
@Theme("valo")
public class PersonaMatchingView extends DefaultLayout implements View {

    private Grid<Persona> grid = new Grid<>(Persona.class);
    HorizontalLayout mainLayout;
    private boolean isonMobile = false;
    PersonaService personaService = new PersonaService();
    FiltroInteresados filtroBase = new FiltroInteresados();

    // Para identificar los layout de acciones
    private int acciones = 0;

    public PersonaMatchingView() {
	super();
	buildLayout();
	configureComponents();
    }

    public PersonaMatchingView(FiltroInteresados filtroBase) {
	super();
	this.filtroBase = filtroBase;
	buildLayout();
	configureComponents();
    }

    private void configureComponents() {
	Responsive.makeResponsive(this);
	configurarGrid();
	updateList();
    }

    private void configurarGrid() {
	grid.setColumns("nombre", "apellido", "DNI");
	grid.getColumn("DNI").setCaption("DNI");
	grid.getColumn("nombre").setCaption("Nombre");
	grid.getColumn("apellido").setCaption("Apellido ");
	grid.addColumn(persona -> {
	    String ret = "";
	    if (persona.getPrefBusqueda() != null)
		ret = "Interesado, ";
	    for (Rol rol : persona.giveMeYourRoles()) {
		if (!ret.contains(rol.toString()))
		    ret += rol + ", ";
	    }
	    return ret.length() >= 2 ? ret.substring(0, ret.length() - 2) : "Sin Rol";
	}).setCaption("Rol").setId("rol");
	grid.addColumn(persona -> {
	    String ret = "N/A";
	    if (persona.giveMeYourRoles().contains(Rol.Inquilino)) {
		Inquilino inquilino = (Inquilino) persona.getRol(Rol.Inquilino);
		ret = inquilino.getCalificacion().toString();
	    }
	    return ret;
	}).setCaption("Califación").setId("calificacion");
	grid.setColumnOrder("nombre", "apellido", "DNI", "calificacion", "rol");
    }

    public void setComponentsVisible(boolean b) {

	if (isonMobile)
	    grid.setVisible(b);

    }

    private void buildLayout() {

	HorizontalLayout hl = new HorizontalLayout();
	buildToolbar("Clientes", hl);
	grid.setSizeFull();
	mainLayout = new HorizontalLayout(grid);
	mainLayout.setSizeFull();
	addComponent(mainLayout);
	this.setExpandRatio(mainLayout, 1);

    }
    public void showErrorNotification(String notification) {
	Notification success = new Notification(
		notification);
	success.setDelayMsec(4000);
	success.setStyleName("bar error small");
	success.setPosition(Position.BOTTOM_CENTER);
	success.show(Page.getCurrent());
    }

    public void showSuccessNotification(String notification) {
	Notification success = new Notification(
		notification);
	success.setDelayMsec(2000);
	success.setStyleName("bar success small");
	success.setPosition(Position.BOTTOM_CENTER);
	success.show(Page.getCurrent());
    }

    public void updateList() {
	List<Persona> customers = personaService.findAllClientes(filtroBase);
	grid.setItems(customers);
    }
    public boolean isIsonMobile() {
	return isonMobile;
    }
    @Override
    public void detach() {
	super.detach();
	// A new instance of TransactionsView is created every time it's
	// navigated to so we'll need to clean up references to it on detach.
	com.TpFinal.services.DashboardEventBus.unregister(this);
    }

    @Subscribe
    public void browserWindowResized(final DashboardEvent.BrowserResizeEvent event) {
	if (Page.getCurrent().getBrowserWindowWidth() < 800) {
	    isonMobile = true;
	} else {
	    isonMobile = false;
	}

    }

    @Override
    public void enter(final ViewChangeListener.ViewChangeEvent event) {
    }

}
