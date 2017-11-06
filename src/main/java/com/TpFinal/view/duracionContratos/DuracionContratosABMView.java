package com.TpFinal.view.duracionContratos;

import com.TpFinal.dto.contrato.ContratoDuracion;
import com.TpFinal.dto.persona.Persona;
import com.TpFinal.services.ContratoDuracionService;
import com.TpFinal.services.DashboardEvent;

import com.TpFinal.view.component.DefaultLayout;
import com.TpFinal.view.component.DialogConfirmacion;
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
public class DuracionContratosABMView extends DefaultLayout implements View {

    /*
     * Hundreds of widgets. Vaadin's user interface components are just Java objects
     * that encapsulate and handle cross-browser support and client-server
     * communication. The default Vaadin components are in the com.vaadin.ui package
     * and there are over 500 more in vaadin.com/directory.
     */

    // Para identificar los layout de acciones
    private int acciones = 0;

    TextField filter = new TextField();
    private Grid<ContratoDuracion> grid = new Grid<>(ContratoDuracion.class);
    Button newItem = new Button("Nuevo");
    Button clearFilterTextBtn = new Button(VaadinIcons.CLOSE);
    RadioButtonGroup<String> filtroRoles = new RadioButtonGroup<>();
    // Button seleccionFiltro=new Button(VaadinIcons.SEARCH);
    Window sw = new Window("Filtrar");

    HorizontalLayout mainLayout;
    // DuracionContratosForm is an example of a custom component class
    DuracionContratosForm DuracionContratosForm = new DuracionContratosForm(this);
    private boolean isonMobile = false;

    // DuracionContratosService is a in-memory mock DAO that mimics
    // a real-world datasource. Typically implemented for
    // example as EJB or Spring Data based service.
    ContratoDuracionService service = new ContratoDuracionService();

    public DuracionContratosABMView() {

	super();
	buildLayout();
	configureComponents();

    }

    private void configureComponents() {
	/*
	 * Synchronous event handling.
	 *
	 * Receive user interaction events on the server-side. This allows you to
	 * synchronously handle those events. Vaadin automatically sends only the needed
	 * changes to the web page without loading a new page.
	 */

	filter.addValueChangeListener(e -> updateList());
	filter.setIcon(VaadinIcons.SEARCH);
	filter.setStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
	filter.setValueChangeMode(ValueChangeMode.LAZY);
	filter.setPlaceholder("Filtrar");
	filter.addValueChangeListener(e -> updateList());

	clearFilterTextBtn.addClickListener(e -> ClearFilterBtnAction());

	newItem.addClickListener(e -> {

	    DuracionContratosForm.clearFields();
	    grid.asSingleSelect().clear();
	    DuracionContratosForm.setContratoDuracion(null);
	});

	grid.setColumns("descripcion", "duracion");
	grid.getColumn("descripcion").setCaption("Descripcion");
	grid.getColumn("duracion").setCaption("Duracion");
	grid.addComponentColumn(configurarAcciones()).setCaption("Acciones").setId("acciones");
		grid.setColumns("acciones","descripcion", "duracion");
	grid.getColumns().forEach(col -> {
	    col.setResizable(false);
	    col.setHidable(true);
	});

	Responsive.makeResponsive(this);

	// grid.setSelectionMode(Grid.SelectionMod
	//
	// e.SINGLE);

	if (isonMobile) {
	    filter.setWidth("100%");
	}
	newItem.setStyleName(ValoTheme.BUTTON_PRIMARY);

	// filter.setIcon(VaadinIcons.SEARCH);
	// filter.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

	updateList();
    }

    private ValueProvider<ContratoDuracion, HorizontalLayout> configurarAcciones() {

	return contratoduracion -> {

	    // Button edit = new Button(VaadinIcons.EDIT);
	    // edit.addStyleNames(ValoTheme.BUTTON_QUIET, ValoTheme.BUTTON_SMALL);
	    // edit.addClickListener(e -> {
	    // DuracionContratosForm.setContratoDuracion(contratoduracion);
	    // });
	    // edit.setDescription("Editar");

	    Button del = new Button(VaadinIcons.TRASH);
	    del.addClickListener(click -> {
		DialogConfirmacion dialog = new DialogConfirmacion("Eliminar",
			VaadinIcons.WARNING,
			"¿Esta seguro que desea Eliminar?",
			"100px",
			confirmacion -> {
			    service.delete(contratoduracion);
			    showSuccessNotification("Duración de contrato borrada: " + contratoduracion
				    .getDescripcion());
			    updateList();
			});
	    });

	    del.addStyleNames(ValoTheme.BUTTON_QUIET, ValoTheme.BUTTON_SMALL);
	    del.setDescription("Borrar");

	    HorizontalLayout hl = new HorizontalLayout(del);
	    hl.setSpacing(false);
	    hl.setCaption("Accion " + acciones);
	    acciones++;
	    return hl;
	};
    }
    /*
     * Robust layouts.
     *
     * Layouts are components that contain other components. HorizontalLayout
     * contains TextField and Button. It is wrapped with a Grid into VerticalLayout
     * for the left side of the screen. Allow user to resize the components with a
     * SplitPanel.
     *
     * In addition to programmatically building layout in Java, you may also choose
     * to setup layout declaratively with Vaadin Designer, CSS and HTML.
     */

    public void setComponentsVisible(boolean b) {
	newItem.setVisible(b);
	filter.setVisible(b);
	// seleccionFiltro.setVisible(b);
	// clearFilterTextBtn.setVisible(b);
	if (isonMobile)
	    grid.setVisible(b);

    }

    private void buildLayout() {

	CssLayout filtering = new CssLayout();
	HorizontalLayout hl = new HorizontalLayout();
	filtering.addComponents(filter, clearFilterTextBtn, newItem);
	filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
	hl.addComponent(filtering);

	buildToolbar("Duraciones de Contratos", hl);
	grid.setSizeFull();
	mainLayout = new HorizontalLayout(grid, DuracionContratosForm);
	mainLayout.setSizeFull();
	addComponent(mainLayout);
	this.setExpandRatio(mainLayout, 1);

    }

    /*
     * Choose the design patterns you like.
     *
     * It is good practice to have separate data access methods that handle the
     * back-end access and/or the user interface updates. You can further split your
     * code into classes to easier maintenance. With Vaadin you can follow MVC, MVP
     * or any other design pattern you choose.
     */

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

	List<ContratoDuracion> customers = service.findAll(filter.getValue());
	grid.setItems(customers);
    }

    public boolean isIsonMobile() {
	return isonMobile;
    }

    public void ClearFilterBtnAction() {
	if (this.DuracionContratosForm.isVisible()) {
	    newItem.focus();
	    DuracionContratosForm.cancel();

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

}
