package com.TpFinal.view.inmuebles;

import com.TpFinal.data.dto.inmueble.Inmueble;
import com.TpFinal.data.dto.persona.Persona;

import com.TpFinal.services.DashboardEvent;
import com.TpFinal.services.InmuebleService;
import com.TpFinal.services.PersonaService;
import com.TpFinal.utils.GeneradorDeDatos;
import com.TpFinal.view.component.DefaultLayout;
import com.google.common.eventbus.Subscribe;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Widgetset;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import java.util.List;

@Title("Inmuebles")
@Theme("valo")
// @Widgetset("com.vaadin.v7.Vaadin7WidgetSet")
public class InmuebleABMView extends DefaultLayout implements View {

    private TextField filter = new TextField();
    private Grid<Inmueble> grid = new Grid<>(Inmueble.class);
    private Button newItem = new Button("Nuevo");
    private Button clearFilterTextBtn = new Button(VaadinIcons.CLOSE);
    private HorizontalLayout mainLayout;
    private InmuebleForm inmuebleForm = new InmuebleForm(this);
    private boolean isonMobile = false;
    private Controller controller = new Controller();

    public InmuebleABMView() {
	super();
	buildLayout();
	GeneradorDeDatos.generarDatos(10);
	controller.configureComponents();
	
    }
    
    public Controller getController() {
	return controller;
    }

    public void setComponentsVisible(boolean b) {
	newItem.setVisible(b);
	filter.setVisible(b);
	// clearFilterTextBtn.setVisible(b);
	if (isonMobile)
	    grid.setVisible(b);

    }

    private void buildLayout() {
	CssLayout filtering = new CssLayout();
	filtering.addComponents(filter, clearFilterTextBtn);
	filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

	addComponent(buildToolbar("Inmuebles", filtering, newItem));
	grid.setSizeFull();
	mainLayout = new HorizontalLayout(grid, inmuebleForm);
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

    

    public boolean isIsonMobile() {
	return isonMobile;
    }

    public void ClearFilterBtnAction() {
	if (this.inmuebleForm.isVisible()) {
	    newItem.focus();
	    inmuebleForm.cancel();

	}
	filter.clear();
    }

    /*
     * 
     * Deployed as a Servlet or Portlet.
     *
     * You can specify additional servlet parameters like the URI and UI class name
     * and turn on production mode when you have finished developing the
     * application.
     */
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

    public class Controller {

	private InmuebleService imuebleService = new InmuebleService();

	public void configureComponents() {

	    filter.addValueChangeListener(e -> updateList());
	    filter.setValueChangeMode(ValueChangeMode.LAZY);
	    filter.setPlaceholder("Filtrar");
	    filter.setIcon(VaadinIcons.SEARCH);
	    filter.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

	    clearFilterTextBtn.setDescription("Limpiar filtro");
	    clearFilterTextBtn.addClickListener(e -> ClearFilterBtnAction());

	    newItem.addClickListener(e -> {
		grid.asSingleSelect().clear();
		inmuebleForm.setInmueble(new Inmueble());
	    });
	    newItem.setStyleName(ValoTheme.BUTTON_PRIMARY);

	    grid.setColumns(Inmueble.pDireccion, Inmueble.pPropietario, Inmueble.pEstadoInmueble, Inmueble.pClaseInmb,
		    Inmueble.pTipoInmb);
	    updateList();
	}
	
	public void updateList() {
		List<Inmueble> inmuebles = imuebleService.readAll();
		grid.setItems(inmuebles);

	    }

    }

}
