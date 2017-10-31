package com.TpFinal.view.empleados;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.TpFinal.dto.inmueble.TipoInmueble;
import com.TpFinal.dto.persona.CategoriaEmpleado;
import com.TpFinal.dto.persona.Credencial;
import com.TpFinal.dto.persona.Empleado;
import com.TpFinal.dto.persona.EstadoEmpleado;
import com.TpFinal.services.DashboardEvent;
import com.TpFinal.services.PersonaService;
import com.TpFinal.view.component.DefaultLayout;
import com.TpFinal.view.component.DialogConfirmacion;
import com.TpFinal.view.persona.FiltroEmpleados;
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
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import com.vaadin.ui.components.grid.HeaderRow;
import com.vaadin.ui.themes.ValoTheme;

@Title("Addressbook")
@Theme("valo")
public class EmpleadoABMView extends DefaultLayout implements View {

    private static final Logger logger = Logger.getLogger(EmpleadoABMView.class);

    // Para identificar los layout de acciones
    private int acciones = 0;
    private Grid<Empleado> grid = new Grid<>();
    Button newItem = new Button("Nuevo");
    Button clearFilterTextBtn = new Button(VaadinIcons.CLOSE);
    // Button seleccionFiltro=new Button(VaadinIcons.SEARCH);
    HorizontalLayout mainLayout;
    EmpleadoForm empleadoForm = new EmpleadoForm(this);
    private boolean isonMobile = false;
    // XXX
    PersonaService service = new PersonaService();
    FiltroEmpleados filtro = new FiltroEmpleados();

    public EmpleadoABMView() {

	super();
	buildLayout();
	configureComponents();

    }

    private void configureComponents() {

	clearFilterTextBtn.addClickListener(e -> ClearFilterBtnAction());

	newItem.addClickListener(e -> {
	    grid.asSingleSelect().clear();
	    empleadoForm.setEmpleado(null);
	});

	configureGrid();
	Responsive.makeResponsive(this);
	newItem.setStyleName(ValoTheme.BUTTON_PRIMARY);
	updateList();
    }

    private void configureGrid() {
	grid.addColumn(empleado -> {
	    return empleado.getPersona().getNombre();
	}).setCaption("Nombre").setId("nombre");
	grid.addColumn(empleado -> {
	    return empleado.getPersona().getApellido();
	}).setCaption("Apellido").setId("apellido");
	grid.addColumn(empleado -> {
	    return empleado.getPersona().getMail();
	}).setCaption("E-Mail").setId("mail");
	grid.addColumn(empleado -> {
	    return empleado.getPersona().getTelefono();
	}).setCaption("Teléfono").setId("telefono");
	grid.addColumn(empleado -> {
	    return empleado.getCategoriaEmpleado();
	}).setCaption("Categoría").setId("categoria");
	grid.addColumn(empleado -> {
	    String ret = "No";
	    if (empleado.getCredencial() != null) {
		Credencial c = empleado.getCredencial();
		if (c.getContrasenia() != null && c.getUsuario() != null && c.getViewAccess() != null)
		    ret = "Sí";
	    }
	    return ret;
	}).setCaption("Posee Acceso").setId("acceso");
	grid.addColumn(empleado -> {
	    String ret = "";
	    logger.debug("Empleado : " + empleado);
	    logger.debug("Estado: " + empleado.getEstadoEmpleado());
	    if (empleado.getEstadoEmpleado() != null) {
		ret = empleado.getEstadoEmpleado().toString();
	    }
	    return ret;
	}).setCaption("Estado Empleado").setId("estadoEmp");
	grid.addComponentColumn(configurarAcciones()).setCaption("Acciones").setId("acciones");
	grid.setColumnOrder("acciones", "nombre", "apellido", "mail", "telefono", "categoria", "acceso", "estadoEmp");
	grid.getColumns().forEach(col -> col.setResizable(false));

	HeaderRow filterRow = grid.appendHeaderRow();
	filterRow.getCell("nombre").setComponent(filtroNombre());
	filterRow.getCell("apellido").setComponent(filtroApellido());
	filterRow.getCell("mail").setComponent(filtroEmail());
	filterRow.getCell("telefono").setComponent(filtroTelefono());
	filterRow.getCell("categoria").setComponent(filtroCategoria());
	filterRow.getCell("acceso").setComponent(filtroAcceso());
	filterRow.getCell("estadoEmp").setComponent(filtroEstadoEmpleado());
    }

    private Component filtroNombre() {
	TextField filtroNombre = new TextField();
	filtroNombre.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
	filtroNombre.setPlaceholder("Sin Filtro");
	filtroNombre.addValueChangeListener(e -> {
	    if (e.getValue() != null) {
		if (!filtroNombre.isEmpty()) {
		    filtro.setFiltroNombre(empleado -> {
			if (empleado.getPersona() != null && empleado.getPersona().getNombre() != null)
			    return empleado.getPersona().getNombre().toLowerCase().contains(e.getValue().toLowerCase());
			return true;
		    });
		} else
		    filtro.setFiltroNombre(empleado -> true);

	    } else {
		filtro.setFiltroNombre(empleado -> true);
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
		    filtro.setFiltroApellido(empleado -> {
			if (empleado.getPersona() != null && empleado.getPersona().getApellido() != null)
			    return empleado.getPersona().getApellido().toLowerCase().contains(e.getValue()
				    .toLowerCase());
			return true;
		    });
		} else
		    filtro.setFiltroApellido(empleado -> true);

	    } else {
		filtro.setFiltroApellido(empleado -> true);
	    }
	    updateList();
	});
	return filtroApellido;
    }
    
    private Component filtroTelefono() {
	TextField filtroTel = new TextField();
	filtroTel.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
	filtroTel.setPlaceholder("Sin Filtro");
	filtroTel.addValueChangeListener(e -> {
	    if (e.getValue() != null) {
		if (!filtroTel.isEmpty()) {
		    filtro.setFiltroTelefono(empleado -> {
			if (empleado.getPersona() != null && empleado.getPersona().getTelefono() != null)
			    return empleado.getPersona().getTelefono().toLowerCase().contains(e.getValue()
				    .toLowerCase());
			return true;
		    });
		} else
		    filtro.setFiltroTelefono(empleado -> true);

	    } else {
		filtro.setFiltroTelefono(empleado -> true);
	    }
	    updateList();
	});
	return filtroTel;
    }


    private Component filtroEmail() {
	TextField filtroEmail = new TextField();
	filtroEmail.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
	filtroEmail.setPlaceholder("Sin Filtro");
	filtroEmail.addValueChangeListener(e -> {
	    if (e.getValue() != null) {
		if (!filtroEmail.isEmpty()) {
		    filtro.setFiltroEmail(empleado -> {
			if (empleado.getPersona() != null && empleado.getPersona().getMail() != null)
			    return empleado.getPersona().getMail().toLowerCase().contains(e.getValue().toLowerCase());
			return true;
		    });
		} else
		    filtro.setFiltroEmail(empleado -> true);

	    } else {
		filtro.setFiltroEmail(empleado -> true);
	    }
	    updateList();
	});
	return filtroEmail;
    }

    private Component filtroCategoria() {
	ComboBox<CategoriaEmpleado> filtroTipo = new ComboBox<>();
	filtroTipo.setStyleName(ValoTheme.COMBOBOX_BORDERLESS);
	filtroTipo.setPlaceholder("Sin Filtro");
	filtroTipo.setItems(CategoriaEmpleado.values());
	filtroTipo.addValueChangeListener(e -> {
	    if (e.getValue() != null) {
		if (!filtroTipo.isEmpty())
		    filtro.setFiltroCategoria(empleado -> {
			if (empleado.getCategoriaEmpleado() != null)
			    return empleado.getCategoriaEmpleado().equals(e.getValue());
			else
			    return false;
		    });
		else
		    filtro.setFiltroCategoria(empleado -> true);
	    } else {
		filtro.setFiltroCategoria(empleado -> true);
	    }
	    updateList();
	});
	return filtroTipo;
    }
    
    private Component filtroEstadoEmpleado() {
	ComboBox<EstadoEmpleado> filtroTipo = new ComboBox<>();
	filtroTipo.setStyleName(ValoTheme.COMBOBOX_BORDERLESS);
	filtroTipo.setPlaceholder("Sin Filtro");
	filtroTipo.setItems(EstadoEmpleado.values());
	filtroTipo.addValueChangeListener(e -> {
	    if (e.getValue() != null) {
		if (!filtroTipo.isEmpty())
		    filtro.setFiltroEstadoEmpleado(empleado -> {
			if (empleado.getEstadoEmpleado() != null)
			    return empleado.getEstadoEmpleado().equals(e.getValue());
			else
			    return false;
		    });
		else
		    filtro.setFiltroEstadoEmpleado(empleado -> true);
	    } else {
		filtro.setFiltroEstadoEmpleado(empleado -> true);
	    }
	    updateList();
	});
	return filtroTipo;
    }

    private Component filtroAcceso() {
	ComboBox<String> filtroTipo = new ComboBox<String>();
	filtroTipo.setItems("Sí", "No");
	filtroTipo.setStyleName(ValoTheme.COMBOBOX_BORDERLESS);
	filtroTipo.setPlaceholder("Sin Filtro");
	filtroTipo.addValueChangeListener(e -> {
	    if (e.getValue() != null) {
		if (e.getValue().toLowerCase().equals("sí")) {
		    if (!filtroTipo.isEmpty())
			filtro.setFiltroAcceso(empleado -> {
			    if (empleado.getCredencial() != null && empleado.getCredencial().getViewAccess() != null)
				return true;
			    else
				return false;
			});
		    else
			filtro.setFiltroAcceso(empleado -> true);
		} else {
		    if (!filtroTipo.isEmpty())
			filtro.setFiltroAcceso(empleado -> {
			    if (empleado.getCredencial() != null && empleado.getCredencial().getViewAccess() != null)
				return false;
			    else
				return true;
			});
		    else
			filtro.setFiltroAcceso(empleado -> true);

		}
	    } else {
		filtro.setFiltroAcceso(empleado -> true);
	    }
	    updateList();
	});
	return filtroTipo;
    }

    private ValueProvider<Empleado, HorizontalLayout> configurarAcciones() {

	return empleado -> {

	    Button edit = new Button(VaadinIcons.EDIT);
	    edit.addStyleNames(ValoTheme.BUTTON_QUIET, ValoTheme.BUTTON_SMALL);
	    edit.addClickListener(e -> {
		empleadoForm.setEmpleado(empleado);
	    });
	    edit.setDescription("Editar");

	    Button del = new Button(VaadinIcons.TRASH);

	    del.addClickListener(click -> {
		@SuppressWarnings("unused")
		DialogConfirmacion dialog = new DialogConfirmacion("Dar de Baja",
			VaadinIcons.WARNING,
			"¿Esta seguro que desea dar de baja al empleado?",
			"100px",
			confirmacion -> {
			    service.darDeBajaEmpleado(empleado);
			    showSuccessNotification("Dado de Baja: " + empleado.getPersona().getNombre() + " "
				    + empleado.getPersona().getApellido());
			    updateList();
			});
	    });

	    del.addStyleNames(ValoTheme.BUTTON_QUIET, ValoTheme.BUTTON_SMALL);
	    del.setDescription("Dar de baja");

	    HorizontalLayout hl = new HorizontalLayout(edit, del);
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
	filtering.addComponents(clearFilterTextBtn, newItem);
	filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
	hl.addComponent(filtering);

	buildToolbar("Empleados", hl);
	grid.setSizeFull();
	mainLayout = new HorizontalLayout(grid, empleadoForm);
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

	List<Empleado> customers = service.findAllEmpleados(filtro);
	grid.setItems(customers);
    }

    public boolean isIsonMobile() {
	return isonMobile;
    }

    public void ClearFilterBtnAction() {
	if (this.empleadoForm.isVisible()) {
	    newItem.focus();
	    empleadoForm.cancel();
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
