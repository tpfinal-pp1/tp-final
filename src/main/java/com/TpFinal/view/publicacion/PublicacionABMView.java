package com.TpFinal.view.publicacion;

import com.TpFinal.dto.publicacion.EstadoPublicacion;
import com.TpFinal.dto.publicacion.Publicacion;
import com.TpFinal.dto.publicacion.TipoPublicacion;
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
import com.vaadin.ui.components.grid.HeaderRow;
import com.vaadin.ui.themes.ValoTheme;

import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

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
    private FiltroPublicacion filtro = new FiltroPublicacion();

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
	grid.addColumn(Publicacion -> Publicacion.getInmueble().getPropietario()).setCaption("Propietario").setId("propietario");
	grid.addComponentColumn(configurarAcciones()).setCaption("Acciones");
	grid.getColumns().forEach(c -> c.setResizable(false));
	
	  HeaderRow filterRow = grid.appendHeaderRow();
			filterRow.getCell("inmueble").setComponent(filtroInmueble());
			filterRow.getCell("tipoPublicacion").setComponent(filtroOperacion());
			filterRow.getCell("fechaPublicacion").setComponent(filtroFecha());
			filterRow.getCell("propietario").setComponent(filtroPropietario());
			filterRow.getCell("estadoPublicacion").setComponent(filtroEstado());
    }
    
    private Component filtroInmueble() {
    	TextField filtroInmueble = new TextField();
		filtroInmueble.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
		filtroInmueble.setPlaceholder("Sin Filtro");
		filtroInmueble.addValueChangeListener(e -> {
			if (e.getValue() != null) {
				if(!filtroInmueble.isEmpty()) {
					filtro.setInmueble(Publicacion -> Publicacion.getInmueble().getDireccion()
							.toString().toLowerCase()
							.contains(filtroInmueble.getValue().toLowerCase()));
				}else
					filtro.setInmueble(Publicacion -> true);
				
			} else {
				filtro.setInmueble(Publicacion -> true);
			}
			updateList();
		});
		return filtroInmueble;
    }
    
    private Component filtroOperacion() {
    	ComboBox<TipoPublicacion> filtroOperacion = new ComboBox<>();
		filtroOperacion.setStyleName(ValoTheme.COMBOBOX_BORDERLESS);
		filtroOperacion.setPlaceholder("Sin Filtro");
		filtroOperacion.setItems(TipoPublicacion.toList());
		filtroOperacion.addValueChangeListener(e -> {
			Notification.show("Valor evento: "+e.getValue() + "\nValor Combo: "+filtroOperacion.getValue());
			if (e.getValue() != null) {
				if (!filtroOperacion.isEmpty())
					filtro.setOperacion(contrato -> contrato.getTipoPublicacion().equals(e.getValue()));
				else
					filtro.setOperacion(contrato -> true);
			} else {
				filtro.setOperacion(contrato -> true);
			}
			updateList();
		});
		return filtroOperacion;
    }
    
    private Component filtroPropietario() {
    	TextField filtroPropietario = new TextField();
		filtroPropietario.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
		filtroPropietario.setPlaceholder("Sin Filtro");
		filtroPropietario.addValueChangeListener(e -> {
			if (e.getValue() != null) {
				if(!filtroPropietario.isEmpty()) {
					filtro.setPropietario(Publicacion -> Publicacion.getInmueble().getPropietario()
							.toString().toLowerCase()
							.contains(filtroPropietario.getValue().toLowerCase()));
				}else
					filtro.setPropietario(Publicacion -> true);
			} else {
				filtro.setPropietario(Publicacion -> true);
			}
			updateList();
		});
		return filtroPropietario;
    }
    
    private Component filtroEstado() {
    	ComboBox<EstadoPublicacion> filtroEstado = new ComboBox<>();
		filtroEstado.setStyleName(ValoTheme.COMBOBOX_BORDERLESS);
		filtroEstado.setPlaceholder("Sin Filtro");
		filtroEstado.setItems(EstadoPublicacion.toList());
		filtroEstado.addValueChangeListener(e -> {
			Notification.show("Valor evento: "+e.getValue() + "\nValor Combo: "+filtroEstado.getValue());
			if (e.getValue() != null) {
				if (!filtroEstado.isEmpty())
					filtro.setEstado(publicacion -> publicacion.getEstadoPublicacion().equals(e.getValue()));
				else
					filtro.setEstado(publicacion -> true);
			} else {
				filtro.setEstado(publicacion -> true);
			}
			updateList();
		});
		return filtroEstado;
    }
    
    private Component filtroFecha() {
		HorizontalLayout hl = new HorizontalLayout();
		DateField fDesde = filtroFDesde();
		DateField fHasta = filtroFHasta();
		TextField anio = filtroAnio();
		TextField mes = filtroMes();

		HorizontalLayout hlFechas = hlFechas(fDesde, fHasta);
		HorizontalLayout hlAnioMes = hlAnioMes(anio, mes);

		Button adicionales = new Button(VaadinIcons.BOOKMARK);
		adicionales.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_TINY, ValoTheme.BUTTON_ICON_ONLY);
		adicionales.setWidth("90%");
		adicionales.setDescription("Búsqueda por Año y Mes");
		adicionales.setCaption("Boton desde");
		adicionales.addClickListener(e -> {
			if (adicionales.getIcon().equals(VaadinIcons.BOOKMARK)) {
				adicionales.setIcon(VaadinIcons.BOOKMARK_O);
				adicionales.setDescription("Búsqueda por fechas");
				fDesde.clear();
				fHasta.clear();
				filtro.setAnio(c -> true);
				filtro.setMes(f -> true);
				mostrarAnioMes(hl, adicionales, hlAnioMes);
			} else {
				adicionales.setIcon(VaadinIcons.BOOKMARK);
				adicionales.setDescription("Búsqueda por Año y Mes");
				anio.clear();
				mes.clear();
				filtro.setAnio(c -> true);
				filtro.setMes(f -> true);
				mostrarFechas(hl, adicionales, hlFechas);
			}
			updateList();
		});
		mostrarFechas(hl, adicionales, hlFechas);
		return hl;
	}

	private void mostrarFechas(HorizontalLayout hl, Button adicionales, HorizontalLayout hlFechas) {
		hl.removeAllComponents();
		hl.addComponents(adicionales, hlFechas);
		hl.setWidth("175px");
		hl.setSpacing(false);
		hl.setExpandRatio(adicionales, 0.10f);
		hl.setExpandRatio(hlFechas, 1);
	}

	private void mostrarAnioMes(HorizontalLayout hl, Button adicionales, HorizontalLayout hlAnioMes) {
		hl.removeAllComponents();
		hl.addComponents(adicionales, hlAnioMes);
		hl.setWidth("175px");
		hl.setSpacing(false);
		hl.setExpandRatio(adicionales, 0.10f);
		hl.setExpandRatio(hlAnioMes, 1);
	}

	private HorizontalLayout hlFechas(DateField fDesde, DateField fHasta) {

		HorizontalLayout hlFechas = new HorizontalLayout(fDesde, fHasta);
		hlFechas.setSpacing(false);
		hlFechas.forEach(component -> component.addStyleNames(ValoTheme.DATEFIELD_TINY,
				ValoTheme.DATEFIELD_BORDERLESS));
		hlFechas.setWidth("160px");
		return hlFechas;
	}

	private HorizontalLayout hlAnioMes(TextField anio, TextField mes) {

		anio.setWidth("100%");
		mes.setWidth("100%");
		HorizontalLayout hl = new HorizontalLayout(anio, mes);
		hl.setSpacing(true);
		hl.forEach(component -> component.addStyleNames(ValoTheme.TEXTFIELD_ALIGN_CENTER, ValoTheme.TEXTFIELD_TINY,
				ValoTheme.TEXTFIELD_BORDERLESS));
		hl.setWidth("160px");
		hl.setExpandRatio(anio, 0.5f);
		hl.setExpandRatio(mes, 0.5f);
		return hl;
	}

	private TextField filtroMes() {
		TextField mes = new TextField();
		mes.setPlaceholder("Mes");
		mes.addBlurListener(e -> {
			if (!mes.isVisible())
				mes.clear();
		});
		mes.addValueChangeListener(e -> {
			if (e.getValue() != null) {
				filtro.setMes(contrato -> contrato.getFechaPublicacion()
						.getMonth()
						.getDisplayName(TextStyle.FULL, Locale.forLanguageTag("es-AR"))
						.toLowerCase().contains(mes.getValue().toLowerCase()));
				;
			} else {
				filtro.setMes(contrato -> true);
			}
			updateList();
		});
		return mes;
	}

	private TextField filtroAnio() {

		TextField anio = new TextField();
		anio.setPlaceholder("Año");
		anio.addBlurListener(e -> {
			if (!anio.isVisible())
				anio.setValue("");
		});
		anio.addValueChangeListener(e -> {
			if (e.getValue() != null) {
				Integer anioInt;
				try {
					anioInt = Integer.parseInt(anio.getValue());
					filtro.setAnio(contrato -> contrato.getFechaPublicacion().getYear() == anioInt);
				} catch (Exception ex) {
					filtro.setAnio(contrato -> true);
				}
			} else {
				filtro.setAnio(contrato -> true);
			}
			updateList();
		});

		return anio;
	}

	private DateField filtroFDesde() {
		DateField fDesde = new DateField();
		fDesde.setPlaceholder("Desde");
		fDesde.setParseErrorMessage("Formato de fecha no reconocido");
		fDesde.addValueChangeListener(e -> {
			if (fDesde.getValue() != null) {
				filtro.setFechaDesde(contrato -> {
					return fDesde.getValue() == null ? true
							: contrato.getFechaPublicacion().compareTo(fDesde.getValue()) >= 0;
				});
			} else {
				filtro.setFechaDesde(f -> true);
			}
			updateList();
		});
		return fDesde;
	}

	private DateField filtroFHasta() {
		DateField fHasta = new DateField();
		fHasta.setPlaceholder("Hasta");
		fHasta.setParseErrorMessage("Formato de fecha no reconocido");
		fHasta.addValueChangeListener(e -> {
			if (fHasta.getValue() != null) {
				filtro.setFechaHasta(contrato -> {
					return fHasta.getValue() == null ? true
							: contrato.getFechaPublicacion().compareTo(fHasta.getValue()) <= 0;
				});
			} else {
				filtro.setFechaHasta(f -> true);
			}
			updateList();
		});
		return fHasta;
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
	List<Publicacion> customers = publicacionService.findAll(filtro);
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
