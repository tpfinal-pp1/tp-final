package com.TpFinal.view.contrato;

import com.TpFinal.dto.contrato.Contrato;
import com.TpFinal.dto.contrato.ContratoAlquiler;
import com.TpFinal.dto.contrato.ContratoVenta;
import com.TpFinal.dto.persona.Persona;
import com.TpFinal.services.ContratoService;
import com.TpFinal.services.DashboardEvent;
import com.TpFinal.view.component.DefaultLayout;
import com.google.common.eventbus.Subscribe;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Widgetset;
import com.vaadin.data.ValueProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.LocalDateRenderer;
import com.vaadin.ui.themes.ValoTheme;

import java.util.List;

/* User Interface written in Java.
 *
 * Define the user interface shown on the Vaadin generated web page by extending the UI class.
 * By default, a new UI instance is automatically created when the page is loaded. To reuse
 * the same instance, add @PreserveOnRefresh.
 */

@Title("Addressbook")
@Theme("valo")
@Widgetset("com.vaadin.v7.Vaadin7WidgetSet")
public class ContratoABMView extends DefaultLayout implements View {

    
    private TextField filter = new TextField();
    private Grid<Contrato> grid = new Grid<>();
    private Button nuevoAlquiler = new Button("Nuevo Alquiler");
    private Button nuevaVenta = new Button("Nueva Venta");
    private Button clearFilterTextBtn = new Button(VaadinIcons.CLOSE);
    private HorizontalLayout mainLayout;

    // Forms
    private ContratoVentaForm ContratoVentaForm = new ContratoVentaForm(this);
    private ContratoAlquilerForm ContratoAlquilerForm = new ContratoAlquilerForm(this);

    private boolean isonMobile = false;

   
    ContratoService service = new ContratoService();
    private List<Contrato> contratos;

    public ContratoABMView() {
	super();
	buildLayout();
	configureComponents();

    }

    private void configureComponents() {
	
	configureFilter();

	nuevaVenta.addClickListener(e -> {
	    grid.asSingleSelect().clear();
	    ContratoVentaForm.clearFields();
	    ContratoVentaForm.setContratoVenta(null);
	});

	nuevoAlquiler.addClickListener(e -> {
	    grid.asSingleSelect().clear();
	    ContratoAlquilerForm.clearFields();
	    ContratoAlquilerForm.setContratoAlquiler(null);

	});
	nuevaVenta.setStyleName(ValoTheme.BUTTON_PRIMARY);

	setearColumnas();

	
	grid.asSingleSelect().addValueChangeListener(event -> {
	    if (event.getValue() == null) {
		if (ContratoVentaForm.isVisible() || ContratoAlquilerForm.isVisible())
		    setComponentsVisible(true);
		ContratoVentaForm.setVisible(false);
		ContratoAlquilerForm.setVisible(false);
	    } else {

		if (event.getValue() instanceof ContratoAlquiler) {
		    ContratoVentaForm.setVisible(false);
		    ContratoAlquilerForm.setContratoAlquiler((ContratoAlquiler) event.getValue());

		}

		else if (event.getValue() instanceof ContratoVenta) {
		    ContratoAlquilerForm.setVisible(false);
		    ContratoVentaForm.setContratoVenta((ContratoVenta) event.getValue());

		}
	    }
	});

	
	
	updateList();
    }

    private void configureFilter() {
	filter.addValueChangeListener(e -> updateList());
	filter.setValueChangeMode(ValueChangeMode.LAZY);
	filter.setPlaceholder("Filtrar");
	filter.addValueChangeListener(e -> updateList());
	filter.setIcon(VaadinIcons.SEARCH);
	filter.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
	clearFilterTextBtn.setDescription("Limpiar filtro");
	clearFilterTextBtn.addClickListener(e -> ClearFilterBtnAction());
    }

    private void setearColumnas() {
	contratos = service.findAll(filter.getValue());
	grid.addColumn(getTipoContrato()).setCaption("Tipo");
	grid.addColumn(Contrato::getFechaCelebracion, new LocalDateRenderer("dd/MM/yyyy")).setCaption(
		"Fecha de celebración");
	grid.addColumn(Contrato::getEstadoContrato).setCaption("Estado");
	grid.addColumn(contrato -> contrato.getInmueble().getDireccion()).setCaption("Dirección");
	grid.addColumn(getIntervinientes()).setCaption("Intervinientes");
	grid.getColumns().forEach(col -> col.setResizable(false));
    }

    private ValueProvider<Contrato, String> getIntervinientes() {
	return contrato -> {
	    String ret = "";
	    if (contrato instanceof ContratoVenta) {
		ContratoVenta c = (ContratoVenta) contrato;
		Persona propietario = c.getInmueble().getPropietario().getPersona();
		Persona comprador = c.getComprador();
		ret = propietario.getNombre() + " " + propietario.getApellido() + ", " + comprador.getNombre() + " "
			+ comprador.getApellido();
	    } else {
		ContratoAlquiler c = (ContratoAlquiler) contrato;
		Persona propietario = c.getInmueble().getPropietario().getPersona();
		Persona inquilino = c.getInquilinoContrato().getPersona();
		ret = propietario.getNombre() + " " + propietario.getApellido() + ", " + inquilino.getNombre() + " "
			+ inquilino.getApellido();

	    }
	    return ret;
	};
    }

    private ValueProvider<Contrato, String> getTipoContrato() {
	return contrato -> {
	    String ret = "";
	    if (contrato instanceof ContratoVenta) {
		ret = "Venta";
	    } else {
		ret = "Alquiler";
	    }
	    return ret;
	};
    }

    public void setComponentsVisible(boolean b) {
	nuevaVenta.setVisible(b);
	nuevoAlquiler.setVisible(b);
	filter.setVisible(b);
	if (checkIfOnMobile()) {
	    clearFilterTextBtn.setVisible(!b);
	}
	// clearFilterTextBtn.setVisible(b);
	if (isonMobile)
	    grid.setVisible(b);

    }

    private void buildLayout() {
	Responsive.makeResponsive(this);
	CssLayout filtering = new CssLayout();

	nuevaVenta.setStyleName(ValoTheme.BUTTON_PRIMARY);

	if (checkIfOnMobile()) {

	    // layout.setSpacing(false);
	    // layout.setResponsive(true);
	    filter.setSizeUndefined();
	    // Responsive.makeResponsive(layout);
	    // filter.setWidth("58%");
	    nuevaVenta.setCaption("Venta");
	    nuevoAlquiler.setCaption("Alquiler");
	    // nuevaVenta.addStyleName(ValoTheme.BUTTON_TINY);
	    // nuevoAlquiler.addStyleName(ValoTheme.BUTTON_TINY);

	    // layout.setMargin(false);
	    // layout.setSizeUndefined();
	    filtering.addComponents(filter, clearFilterTextBtn, nuevaVenta, nuevoAlquiler);
	    clearFilterTextBtn.setVisible(false);

	} else {
	    HorizontalLayout layout = new HorizontalLayout(nuevaVenta, nuevoAlquiler);
	    filtering.addComponents(filter, clearFilterTextBtn, layout);

	}

	filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
	buildToolbar("Contratos", filtering);
	grid.setSizeFull();
	mainLayout = new HorizontalLayout(grid, ContratoVentaForm, ContratoAlquilerForm);
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
	contratos = service.findAll(filter.getValue());
	grid.setItems(contratos);

    }

    public boolean isIsonMobile() {
	return isonMobile;
    }

    public void ClearFilterBtnAction() {
	if (this.ContratoVentaForm.isVisible()) {
	    nuevaVenta.focus();
	    ContratoVentaForm.cancel();

	} else if (this.ContratoAlquilerForm.isVisible()) {
	    nuevoAlquiler.focus();
	    ContratoAlquilerForm.cancel();
	}

	filter.clear();
    }

    public boolean checkIfOnMobile() {
	if (Page.getCurrent().getBrowserWindowWidth() < 800) {
	    isonMobile = true;
	    return true;
	} else {
	    isonMobile = false;
	    return false;

	}
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

}
