package com.TpFinal.view.publicacion;

import com.TpFinal.dto.publicacion.Publicacion;
import com.TpFinal.services.DashboardEvent;
import com.TpFinal.services.PublicacionService;
import com.TpFinal.view.component.DefaultLayout;
import com.TpFinal.view.component.DialogConfirmacion;
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
import com.vaadin.ui.themes.ValoTheme;

import java.util.List;

@Title("Addressbook")
@Theme("valo")
public class PublicacionABMView extends DefaultLayout implements View {

    /**
     * 
     */
    private static final long serialVersionUID = 8290150520198357534L;
    TextField filter = new TextField();
    private Grid<Publicacion> grid = new Grid<>(Publicacion.class);
    Button nuevo = new Button("Nueva");

    Button clearFilterTextBtn = new Button(VaadinIcons.CLOSE);

    HorizontalLayout mainLayout;
    // ContratoVentaForm is an example of a custom component class
    PublicacionForm publicacionForm = new PublicacionForm(this);

    private boolean isonMobile = false;

    // PublicacionService is a in-memory mock DAO that mimics
    // a real-world datasource. Typically implemented for
    // example as EJB or Spring Data based service.
    PublicacionService publicacionService = new PublicacionService();

    //acciones segun numero de fila
	int acciones = 0;

    public PublicacionABMView() {
	super();
	buildLayout();
	configureComponents();
	setSizeFull();

    }

    private void configureComponents() {
	configureFilter();
	configureNewItem();
	configureGrid();
	updateList();
    }

    private void configureGrid() {
	grid.setColumns("inmueble", "tipoPublicacion", "fechaPublicacion", "estadoPublicacion");
	grid.getColumn("tipoPublicacion").setCaption("Operación");
	grid.getColumn("fechaPublicacion").setCaption("Fecha Publicación");
	grid.addColumn(Publicacion -> Publicacion.getInmueble().getPropietario()).setCaption("Propietario");
	grid.addComponentColumn(configurarAcciones()).setCaption("Acciones");
	grid.getColumns().forEach(c -> c.setResizable(false));
    }

    private ValueProvider<Publicacion, HorizontalLayout> configurarAcciones() {
	return publicacion -> {
	    Button edit = new Button(VaadinIcons.EDIT);
	    edit.addStyleNames(ValoTheme.BUTTON_QUIET, ValoTheme.BUTTON_SMALL);
	    edit.addClickListener(e -> {
		publicacionForm.setPublicacion(publicacion);

	    });
	    edit.setDescription("Editar");

	    Button del = new Button(VaadinIcons.TRASH);
	    del.addClickListener(click -> {
		DialogConfirmacion dialog = new DialogConfirmacion("Eliminar",
			VaadinIcons.WARNING,
			"¿Esta seguro que desea Eliminar?",
			"100px",
			confirmacion -> {
			    if (publicacionService.delete(publicacion)) {
				showSuccessNotification("Publicación Borrada");
			    } else {
				showErrorNotification("No se realizaron cambios");
			    }
			    updateList();
			});

	    });
	    del.addStyleNames(ValoTheme.BUTTON_QUIET, ValoTheme.BUTTON_SMALL);
	    del.setDescription("Borrar");
	    HorizontalLayout hl = new HorizontalLayout(edit, del);
	    hl.setCaption("Accion "+acciones);
	    hl.setSpacing(false);
	    acciones++;
	    return hl;
	};
    }

    private void configureNewItem() {
	nuevo.addClickListener(e -> {
	    grid.asSingleSelect().clear();
	    publicacionForm.clearFields();
	    publicacionForm.setPublicacion(null);

	});
    }

    private void configureFilter() {
	filter.addValueChangeListener(e -> updateList());
	filter.setValueChangeMode(ValueChangeMode.LAZY);
	filter.setPlaceholder("Filtrar");
	filter.addValueChangeListener(e -> updateList());

	clearFilterTextBtn.addClickListener(e -> ClearFilterBtnAction());
	filter.setIcon(VaadinIcons.SEARCH);
	filter.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
    }

    public void setComponentsVisible(boolean b) {
	nuevo.setVisible(b);
	filter.setVisible(b);
	if (checkIfOnMobile())
	    clearFilterTextBtn.setVisible(!b);
	// clearFilterTextBtn.setVisible(b);
	if (isonMobile)
	    grid.setVisible(b);

    }

    private void buildLayout() {
	CssLayout filtering = new CssLayout();

	nuevo.setStyleName(ValoTheme.BUTTON_PRIMARY);
	HorizontalLayout layout = new HorizontalLayout(nuevo);
	filtering.addComponents(filter, clearFilterTextBtn, layout);

	filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
	buildToolbar("Publicaciones", filtering/* , layout */);
	grid.setSizeFull();
	mainLayout = new HorizontalLayout(grid, publicacionForm);
	mainLayout.setSizeFull();
	addComponent(mainLayout);
	this.setExpandRatio(mainLayout, 1);
	Responsive.makeResponsive(this);

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
	List<Publicacion> customers = publicacionService.readAll(filter.getValue());
	grid.setItems(customers);

    }

    public boolean isIsonMobile() {
	return isonMobile;
    }

    public void ClearFilterBtnAction() {
	if (this.publicacionForm.isVisible()) {
	    nuevo.focus();
	    publicacionForm.cancel();

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

    public void enableGrid() {
	grid.setEnabled(true);
    }
}
