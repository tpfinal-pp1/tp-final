package com.TpFinal.view.persona;

import com.TpFinal.dto.persona.Persona;
import com.TpFinal.dto.publicacion.Rol;
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

/* User Interface written in Java.
 *
 * Define the user interface shown on the Vaadin generated web page by extending the UI class.
 * By default, a new UI instance is automatically created when the page is loaded. To reuse
 * the same instance, add @PreserveOnRefresh.
 */

@Title("Addressbook")
@Theme("valo")
public class PersonaABMView extends DefaultLayout implements View {

    TextField filter = new TextField();
    private Grid<Persona> grid = new Grid<>(Persona.class);
    Button newItem = new Button("Nuevo");
    Button clearFilterTextBtn = new Button(VaadinIcons.CLOSE);
    RadioButtonGroup<String> filtroRoles = new RadioButtonGroup<>();
    Button seleccionFiltro = new Button(VaadinIcons.SEARCH_MINUS);
    Window sw = new Window("Filtrar");

    HorizontalLayout mainLayout;
    PersonaForm personaForm = new PersonaForm(this);
    private boolean isonMobile = false;
    PersonaService personaService = new PersonaService();

    // Para identificar los layout de acciones
    private int acciones = 0;

    public PersonaABMView() {
	super();
	buildLayout();
	configureComponents();
    }

    private void configureComponents() {
	Responsive.makeResponsive(this);
	configureFilter();
	configurarNewItem();
	configurarGrid();
	updateList();
    }

    private void configurarNewItem() {
	newItem.addClickListener(e -> {
	    grid.asSingleSelect().clear();
	    personaForm.setPersona(new Persona());
	});
	newItem.setStyleName(ValoTheme.BUTTON_PRIMARY);
    }

    private void configurarGrid() {
	grid.setColumns("nombre", "apellido", "DNI");
	grid.getColumn("DNI").setCaption("DNI");
	grid.getColumn("nombre").setCaption("Nombre");
	grid.getColumn("apellido").setCaption("Apellido ");
	grid.addComponentColumn(configurarAcciones()).setCaption("Acciones");
	grid.getColumns().forEach(col -> col.setResizable(false));
    }

    private ValueProvider<Persona, HorizontalLayout> configurarAcciones() {

	return persona -> {

	    Button edit = new Button(VaadinIcons.EDIT);
	    edit.addStyleNames(ValoTheme.BUTTON_QUIET, ValoTheme.BUTTON_SMALL);
	    edit.addClickListener(e -> {
		personaForm.setPersona(persona);
	    });
	    edit.setDescription("Editar");

	    Button del = new Button(VaadinIcons.TRASH);
	    del.addClickListener(click -> {
		DialogConfirmacion dialog = new DialogConfirmacion("Eliminar",
			VaadinIcons.WARNING,
			"¿Esta seguro que desea Eliminar?",
			"100px",
			confirmacion -> {
			    personaService.delete(persona);
			    showSuccessNotification("Persona borrada (" + persona.getNombre() + " " + persona
				    .getApellido() + ")");
			    updateList();
			});
	    });
	    del.addStyleNames(ValoTheme.BUTTON_QUIET, ValoTheme.BUTTON_SMALL);
	    del.setDescription("Borrar");

	    Button addIntereses = new Button(VaadinIcons.THUMBS_UP_O);
	    addIntereses.addClickListener(click -> {
		new PreferenciasBusqueda(persona.getPrefBusqueda()) {

		    @Override
		    public boolean onSave() {
			persona.setPrefBusqueda(getCriterio());
			if (personaService.saveOrUpdate(persona)) {
			    showSuccessNotification("Preferencias Guardadas");
			    return true;
			} else {
			    showErrorNotification("No se han realizado modificaciones");
			    return false;
			}

		    }

			@Override
			public boolean onClean() {
				return true;
			}

			@Override
			public boolean searchVisible() {
				return true;
			}

		};
	    });
	    addIntereses.addStyleNames(ValoTheme.BUTTON_QUIET, ValoTheme.BUTTON_SMALL);
	    addIntereses.setDescription("Añadir intereses de búsqueda");
	    HorizontalLayout hl = new HorizontalLayout(edit, del, addIntereses);
	    hl.setSpacing(false);
	    hl.setCaption("Accion " + acciones);
	    acciones++;
	    return hl;
	};
    }

    private void abriVentanaSelectoraFiltros() {
	HorizontalLayout hl = new HorizontalLayout(filtroRoles);
	hl.setMargin(true);
	hl.setSpacing(true);
	sw.setContent(hl);
	filtroRoles.setItems("Todos", "Inquilinos", "Propietarios");
	filtroRoles.addValueChangeListener(l -> {
	    System.out.println(l.getValue());
	    String valor = l.getValue();
	    filter(valor);
	});
	Responsive.makeResponsive(sw);
	sw.setModal(true);
	sw.setResizable(false);
	sw.setClosable(true);
	sw.setVisible(true);
	sw.center();
	UI.getCurrent().addWindow(sw);
	sw.focus();
    }

    private void configureFilter() {
	filter.addValueChangeListener(e -> updateList());
	filter.setValueChangeMode(ValueChangeMode.LAZY);
	filter.setPlaceholder("Filtrar");
	filter.addValueChangeListener(e -> updateList());
	clearFilterTextBtn.addClickListener(e -> ClearFilterBtnAction());
	if (isonMobile) {
	    filter.setWidth("100%");
	}
	seleccionFiltro.addClickListener(event -> {
	    abriVentanaSelectoraFiltros();
	});
    }

    public void setComponentsVisible(boolean b) {
	newItem.setVisible(b);
	filter.setVisible(b);
	seleccionFiltro.setVisible(b);
	// clearFilterTextBtn.setVisible(b);
	if (isonMobile)
	    grid.setVisible(b);

    }

    private void buildLayout() {

	CssLayout filtering = new CssLayout();
	HorizontalLayout hl = new HorizontalLayout();
	filtering.addComponents(seleccionFiltro, filter, clearFilterTextBtn, newItem);
	filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
	hl.addComponent(filtering);

	buildToolbar("Personas", hl);
	grid.setSizeFull();
	mainLayout = new HorizontalLayout(grid, personaForm);
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
	List<Persona> customers = personaService.findAll(filter.getValue());
	grid.setItems(customers);
    }

    public void filter(String valor) {
	List<Persona> customers = null;
	if (valor.equals("Todos"))
	    customers = personaService.findAll(filter.getValue());
	else if (valor.equals("Inquilinos"))
	    customers = personaService.findForRole(Rol.Inquilino.toString());
	else if (valor.equals("Propietarios"))
	    customers = personaService.findForRole(Rol.Propietario.toString());
	grid.setItems(customers);
    }

    public boolean isIsonMobile() {
	return isonMobile;
    }

    public void ClearFilterBtnAction() {
	if (this.personaForm.isVisible()) {
	    newItem.focus();
	    personaForm.cancel();

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
