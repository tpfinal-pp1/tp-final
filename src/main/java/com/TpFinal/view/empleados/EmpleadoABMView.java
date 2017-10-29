package com.TpFinal.view.empleados;

import java.util.List;

import com.TpFinal.dto.persona.Empleado;
import com.TpFinal.services.DashboardEvent;
import com.TpFinal.services.PersonaService;
import com.TpFinal.view.component.DefaultLayout;
import com.TpFinal.view.component.DialogConfirmacion;
import com.TpFinal.view.persona.FiltroEmpleados;
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
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.RadioButtonGroup;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

@Title("Addressbook")
@Theme("valo")
public class EmpleadoABMView extends DefaultLayout implements View {

    // Para identificar los layout de acciones
    private int acciones = 0;

    TextField filter = new TextField();
    private Grid<Empleado> grid = new Grid<>(Empleado.class);
    Button newItem = new Button("Nuevo");
    Button clearFilterTextBtn = new Button(VaadinIcons.CLOSE);
    
    // Button seleccionFiltro=new Button(VaadinIcons.SEARCH);
    Window sw = new Window("Filtrar");

    HorizontalLayout mainLayout;

    EmpleadoForm empleadoForm = new EmpleadoForm(this);
    private boolean isonMobile = false;

    //XXX
    PersonaService service = new PersonaService();

    public EmpleadoABMView() {

	super();
	buildLayout();
	configureComponents();

    }

    private void configureComponents() {
	
	filter.addValueChangeListener(e -> updateList());
	filter.setIcon(VaadinIcons.SEARCH);
	filter.setStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
	filter.setValueChangeMode(ValueChangeMode.LAZY);
	filter.setPlaceholder("Filtrar");
	filter.addValueChangeListener(e -> updateList());

	clearFilterTextBtn.addClickListener(e -> ClearFilterBtnAction());

	newItem.addClickListener(e -> {

	    empleadoForm.clearFields();
	    grid.asSingleSelect().clear();
	    empleadoForm.setEmpleado(null);
	});

	grid.setColumns("nombre", "apellido", "DNI");
	grid.getColumn("DNI").setCaption("DNI");
	grid.getColumn("nombre").setCaption("Nombre");
	grid.getColumn("apellido").setCaption("Apellido ");
	
	grid.addComponentColumn(configurarAcciones()).setCaption("Acciones");
	grid.getColumns().forEach(col -> col.setResizable(false));

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

    private ValueProvider<Empleado, HorizontalLayout> configurarAcciones() {

	return empleado -> {

	    // Button edit = new Button(VaadinIcons.EDIT);
	    // edit.addStyleNames(ValoTheme.BUTTON_QUIET, ValoTheme.BUTTON_SMALL);
	    // edit.addClickListener(e -> {
	    // DuracionContratosForm.setContratoDuracion(contratoduracion);
	    // });
	    // edit.setDescription("Editar");

	    Button del = new Button(VaadinIcons.TRASH);
	    
	    //TODO REPLACE
	    del.addClickListener(click -> {
		DialogConfirmacion dialog = new DialogConfirmacion("Eliminar",
			VaadinIcons.WARNING,
			"¿Esta seguro que desea Eliminar?",
			"100px",
			confirmacion -> {
			    service.delete(empleado);
			    showSuccessNotification("Empleado: " + empleado.getNombre() + " " + empleado.getApellido());
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
   
	List<Empleado> customers = service.findAllEmpleados(new FiltroEmpleados());
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

