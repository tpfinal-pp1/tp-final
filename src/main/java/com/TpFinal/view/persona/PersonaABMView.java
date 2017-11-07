package com.TpFinal.view.persona;


import com.TpFinal.dto.inmueble.Inmueble;
import com.TpFinal.dto.persona.Calificacion;
import com.TpFinal.dto.persona.CategoriaEmpleado;
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
import com.vaadin.ui.*;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.components.grid.HeaderRow;
import com.vaadin.ui.themes.ValoTheme;
import java.util.List;


@SuppressWarnings("serial")
@Title("Clientes")
@Theme("valo")
public class PersonaABMView extends DefaultLayout implements View {

    private Grid<Persona> grid = new Grid<>(Persona.class);
    Button newItem = new Button("Nuevo");
    Button cerrarForm = new Button(VaadinIcons.CLOSE);
    RadioButtonGroup<String> filtroRoles = new RadioButtonGroup<>();
    HorizontalLayout mainLayout;
    PersonaForm personaForm = new PersonaForm(this);
    private boolean isonMobile = false;
    PersonaService personaService = new PersonaService();
    FiltroInteresados filtroBase = new FiltroInteresados();
    FiltroClientes filtroClientes = new FiltroClientes();

    // Para identificar los layout de acciones
    private int acciones = 0;

    public PersonaABMView() {
	super();
	buildLayout();
	configureComponents();
    }

    public PersonaABMView(FiltroInteresados filtroBase) {
	super();
	this.filtroBase = filtroBase;
	filtroBase.setFiltroCustom(filtroClientes.getFiltroCompuesto());
	buildLayout();
	configureComponents();
    }

    private void configureComponents() {
	Responsive.makeResponsive(this);
	configurarNewItem();
	configurarGrid();
	updateList();
	cerrarForm.addClickListener(click -> {
	   personaForm.setVisible(false); 
	});
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
	grid.addComponentColumn(configurarAcciones()).setCaption("Acciones").setId("acciones");
	grid.getColumns().forEach(col -> {
	    col.setResizable(false);
	    col.setHidable(true);
	});
	grid.setColumnOrder("acciones", "nombre", "apellido", "DNI", "calificacion", "rol");
	HeaderRow filterRow = grid.appendHeaderRow();
	filterRow.getCell("nombre").setComponent(filtroNombre());
	filterRow.getCell("apellido").setComponent(filtroApellido());
	filterRow.getCell("DNI").setComponent(filtroDNI());
	filterRow.getCell("calificacion").setComponent(filtroCalificacion());
	filterRow.getCell("rol").setComponent(filtroRol());
    }

    private Component filtroNombre() {
	TextField filtroNombre = new TextField();
	filtroNombre.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
	filtroNombre.setPlaceholder("Sin Filtro");
	filtroNombre.addValueChangeListener(e -> {
	    if (e.getValue() != null) {
		if (!filtroNombre.isEmpty()) {
		    filtroClientes.setFiltroNombre(persona -> {
			if (persona.getNombre() != null)
			    return persona.getNombre().toLowerCase().contains(e.getValue().toLowerCase());
			return true;
		    });
		} else
		    filtroClientes.setFiltroNombre(persona -> true);

	    } else {
		filtroClientes.setFiltroNombre(persona -> true);
	    }
	    updateList();
	});
	return filtroNombre;
    }

    private Component filtroApellido() {
	TextField filtroApellido = new TextField();
	filtroApellido.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
	filtroApellido.setPlaceholder("Sin Filtro");
	filtroApellido.addValueChangeListener(e -> {
	    if (e.getValue() != null) {
		if (!filtroApellido.isEmpty()) {
		    filtroClientes.setFiltroApellido(persona -> {
			if (persona.getApellido() != null)
			    return persona.getApellido().toLowerCase().contains(e.getValue().toLowerCase());
			return true;
		    });
		} else
		    filtroClientes.setFiltroApellido(persona -> true);

	    } else {
		filtroClientes.setFiltroApellido(persona -> true);
	    }
	    updateList();
	});
	return filtroApellido;
    }

    private Component filtroDNI() {
	TextField filtroDNI = new TextField();
	filtroDNI.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
	filtroDNI.setPlaceholder("Sin Filtro");
	filtroDNI.addValueChangeListener(e -> {
	    if (e.getValue() != null) {
		if (!filtroDNI.isEmpty()) {
		    filtroClientes.setFiltroDNI(persona -> {
			if (persona.getDNI() != null)
			    return persona.getDNI().toLowerCase().contains(e.getValue().toLowerCase());
			return true;
		    });
		} else
		    filtroClientes.setFiltroDNI(persona -> true);

	    } else {
		filtroClientes.setFiltroDNI(persona -> true);
	    }
	    updateList();
	});
	return filtroDNI;
    }

    private Component filtroCalificacion() {
	ComboBox<Calificacion> filtroCalificacion = new ComboBox<>();
	filtroCalificacion.setStyleName(ValoTheme.COMBOBOX_BORDERLESS);
	filtroCalificacion.setPlaceholder("Sin Filtro");
	filtroCalificacion.setItems(Calificacion.values());
	filtroCalificacion.addValueChangeListener(e -> {
	    if (e.getValue() != null) {
		if (!filtroCalificacion.isEmpty())
		    filtroClientes.setFiltroCalificacion(persona -> {
			Inquilino inquilino = (Inquilino) persona.getRol(Rol.Inquilino);
			if (inquilino != null)
			    return inquilino.getCalificacion().equals(e.getValue());
			else
			    return false;
		    });
		else
		    filtroClientes.setFiltroCalificacion(persona -> true);
	    } else {
		filtroClientes.setFiltroCalificacion(persona -> true);
	    }
	    updateList();
	});
	return filtroCalificacion;
    }

    private Component filtroRol() {
	ComboBox<Rol> filtroRol = new ComboBox<>();
	filtroRol.setStyleName(ValoTheme.COMBOBOX_BORDERLESS);
	filtroRol.setPlaceholder("Sin Filtro");
	filtroRol.setItems(Rol.values());
	filtroRol.addValueChangeListener(e -> {
	    if (e.getValue() != null) {
		if (!filtroRol.isEmpty())
		    filtroClientes.setFiltroRol(persona -> {
			return persona.contiene(e.getValue());
		    });
		else
		    filtroClientes.setFiltroRol(persona -> true);
	    } else {
		filtroClientes.setFiltroRol(persona -> true);
	    }

	    updateList();
	});
	return filtroRol;
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

	    Button addIntereses = new Button(VaadinIcons.SEARCH_MINUS);
	    addIntereses.addClickListener(click -> {
		new PreferenciasBusqueda(persona.getPrefBusqueda()) {

		    @Override
		    public boolean onSave() {
			persona.setPrefBusqueda(getCriterio());
			if (personaService.saveOrUpdate(persona)) {
			    showSuccessNotification("Preferencias Guardadas");
			    updateList();
			    return true;
			} else {
			    showErrorNotification("No se han realizado modificaciones");
			    updateList();
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

    public void setComponentsVisible(boolean b) {
	newItem.setVisible(b);
	if (isonMobile)
	    grid.setVisible(b);

    }

    private void buildLayout() {	
	
	CssLayout filtering = new CssLayout();
	HorizontalLayout hl = new HorizontalLayout();
	filtering.addComponents(cerrarForm, newItem);
	filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
	hl.addComponent(filtering);

	buildToolbar("Clientes", hl);
	grid.setSizeFull();
	mainLayout = new HorizontalLayout(grid, personaForm);
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
	filtroBase.setFiltroPersona(filtroClientes.getFiltroCompuesto());
	List<Persona> customers = personaService.findAllClientes(filtroBase);
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
