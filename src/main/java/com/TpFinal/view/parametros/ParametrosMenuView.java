package com.TpFinal.view.parametros;

import com.TpFinal.services.DashboardEvent;
import com.TpFinal.view.component.DefaultLayout;
import com.google.common.eventbus.Subscribe;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

/* User Interface written in Java.
 *
 * Define the user interface shown on the Vaadin generated web page by extending the UI class.
 * By default, a new UI instance is automatically created when the page is loaded. To reuse
 * the same instance, add @PreserveOnRefresh.
 */

@Title("Addressbook")
@Theme("valo")
public class ParametrosMenuView extends DefaultLayout implements View {

    // Para identificar los layout de acciones
    private int acciones = 0;
    Button newItem = new Button("Nuevo");
    Button clearFilterTextBtn = new Button(VaadinIcons.CLOSE);
    RadioButtonGroup<String> filtroRoles = new RadioButtonGroup<>();
    VerticalLayout mainLayout;
    private boolean isonMobile = false;

    // XXX ParametrosService services = new ParametrosService()
    // ContratoDuracionService service = new ContratoDuracionService();

    public ParametrosMenuView() {

	super();
	buildLayout();
	configureComponents();

    }

    private void configureComponents() {

	newItem.addClickListener(e -> {
	});
	Responsive.makeResponsive(this);
	newItem.setStyleName(ValoTheme.BUTTON_PRIMARY);

    }

    public void setComponentsVisible(boolean b) {
	newItem.setVisible(b);
    }

    private void buildLayout() {

	CssLayout filtering = new CssLayout();
	HorizontalLayout hl = new HorizontalLayout();
	filtering.addComponents(clearFilterTextBtn, newItem);
	filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
	hl.addComponent(filtering);

	buildToolbar("Parámetros del Sistema", hl);
	// XXX Cargar botones aca.
	Button recordatorios = new Button("Periodicidad de Recordatorios", VaadinIcons.CLOCK);
	Button duracionesContratos = new Button("Duraciones de Contratos", VaadinIcons.CALENDAR_CLOCK);
	Button comisiones = new Button("Comisiones", VaadinIcons.COIN_PILES);
	Button sellados = new Button("Sellado de bancos", VaadinIcons.INSTITUTION);

	mainLayout = new VerticalLayout(recordatorios, duracionesContratos, comisiones, sellados);
	mainLayout.setSpacing(true);
	mainLayout.forEach(component -> {
	    component.setWidth("500px");
	    mainLayout.setExpandRatio(component, 1);
	    component.addStyleName(ValoTheme.BUTTON_HUGE);
	});
	mainLayout.setDefaultComponentAlignment(Alignment.TOP_CENTER);
	mainLayout.setWidthUndefined();
	addComponent(mainLayout);
	this.setComponentAlignment(mainLayout, Alignment.TOP_CENTER);
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
