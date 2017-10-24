package com.TpFinal.view.inmuebles;

import com.TpFinal.dto.inmueble.*;
import com.TpFinal.services.DashboardEvent;
import com.TpFinal.services.InmuebleService;
import com.TpFinal.view.component.DefaultLayout;
import com.TpFinal.view.component.DialogConfirmacion;
import com.TpFinal.view.component.ImageVisualizer;
import com.TpFinal.view.component.PreferenciasBusqueda;
import com.TpFinal.view.persona.FiltroInteresados;
import com.TpFinal.view.persona.PersonaABMViewWindow;
import com.google.common.eventbus.Subscribe;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.data.ValueProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.HeaderRow;
import com.vaadin.ui.themes.ValoTheme;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

@Title("Inmuebles")
@Theme("valo")
public class InmuebleABMView extends DefaultLayout implements View {

    /**
     * 
     */
    private static final long serialVersionUID = 1749224574589852377L;
    private TextField filter = new TextField();
    private Grid<Inmueble> grid = new Grid<>();
    private Button newItem = new Button("Nuevo");
    private Button clearFilterTextBtn = new Button(VaadinIcons.CLOSE);
    private HorizontalLayout mainLayout;
    private InmuebleForm inmuebleForm = new InmuebleForm(this);
    private boolean isonMobile = false;
    private Controller controller = new Controller();
    private Supplier<List<Inmueble>> inmuebleSupplier;
    private Button btnSearch = new Button(VaadinIcons.SEARCH_MINUS);
    private FiltroInmueble filtro = new FiltroInmueble();
    private Predicate<Inmueble> filtroCustom = i -> true;

    // acciones segun numero de fila
    int acciones = 0;

    public InmuebleABMView() {
	super();
	buildLayout();
	controller.configureComponents();

    }

    public InmuebleABMView(Supplier<List<Inmueble>> supplier) {
	super();
	inmuebleSupplier = supplier;
	buildLayout();
	controller.configureComponents();
    }

    public InmuebleABMView(Supplier<List<Inmueble>> supplier, Predicate<Inmueble> filtroCustom) {
	super();
	inmuebleSupplier = supplier;
	if (filtroCustom != null) {
	    this.filtroCustom = filtroCustom;
	    filtro.setFiltroCustom(filtroCustom);
	}
	buildLayout();
	controller.configureComponents();
    }

    public Controller getController() {
	return controller;
    }

    private void buildLayout() {
	CssLayout filtering = new CssLayout();

	filtering.addComponents(btnSearch, filter, clearFilterTextBtn, newItem);

	filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
	HorizontalLayout hlf = new HorizontalLayout(filtering);

	buildToolbar("Inmuebles", hlf);
	grid.setSizeFull();
	mainLayout = new HorizontalLayout(grid, inmuebleForm);
	mainLayout.setSizeFull();
	addComponent(mainLayout);
	this.setExpandRatio(mainLayout, 1);
    }

    /**
     * Oculta o muestra los componentes de la grilla y su toolbar.
     * 
     * @param b
     *            true para mostrar, false para ocultar
     */
    public void setComponentsVisible(boolean b) {
	newItem.setVisible(b);
	filter.setVisible(b);
	btnSearch.setVisible(b);
	// clearFilterTextBtn.setVisible(b);
	if (isonMobile)
	    grid.setVisible(b);

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

    public void setSupplier(Supplier<List<Inmueble>> supplier) {
	this.inmuebleSupplier = supplier;

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
	    System.out.println("Mobile!");
	    isonMobile = true;
	} else {
	    isonMobile = false;

	}

    }

    @Override
    public void enter(final ViewChangeListener.ViewChangeEvent event) {
    }

    public class Controller {

	private InmuebleService inmuebleService = new InmuebleService();

	public void configureComponents() {
	    if (inmuebleSupplier == null)
		inmuebleSupplier = () -> inmuebleService.readAll();
	    inmuebleService.setSupplier(inmuebleSupplier);
	    configureFilter();
	    configureNewItem();
	    configureGrid();
	    configureSearch();
	    updateList();
	}

	private void configureNewItem() {
	    newItem.addClickListener(e -> {
		grid.asSingleSelect().clear();
		inmuebleForm.clearFields();
		inmuebleForm.edicion=false;
		inmuebleForm.setInmueble(null);
	    });
	    newItem.setStyleName(ValoTheme.BUTTON_PRIMARY);
	}

	@SuppressWarnings("serial")
	private void configureSearch() {
	    btnSearch.addClickListener(click -> {
		CriterioBusqInmueble criterio = new CriterioBusqInmueble();
		new PreferenciasBusqueda(criterio) {

		    @Override
		    public boolean onSave() {
			inmuebleSupplier = () -> inmuebleService.findByCaracteristicas(criterio);
			inmuebleService.setSupplier(inmuebleSupplier);
			updateList();
			return false;
		    }

		    @Override
		    public boolean onClean() {
			boolean success = true;
			try {
			    inmuebleSupplier = () -> inmuebleService.readAll();
			    inmuebleService.setSupplier(inmuebleSupplier);
			    updateList();
			} catch (Exception e) {
			    success = false;
			    e.printStackTrace();
			}
			return success;
		    }

		    @Override
		    public boolean searchVisible() {
			return false;
		    }
		};
	    });
	}

	private void configureFilter() {
	    filter.addValueChangeListener(e -> filtrarPorCalle(filter.getValue()));
	    filter.setValueChangeMode(ValueChangeMode.LAZY);
	    filter.setPlaceholder("Filtrar");
	    clearFilterTextBtn.setDescription("Limpiar filtro");
	    clearFilterTextBtn.addClickListener(e -> ClearFilterBtnAction());
	}

	private void configureGrid() {
	    grid.asSingleSelect().addValueChangeListener(event -> {
		if (event.getValue() == null) {
		    if (inmuebleForm.isVisible())
			setComponentsVisible(true);
		    inmuebleForm.setVisible(false);
		    inmuebleForm.clearFields();
		}
	    });

	    grid.addColumn(inmueble -> {
		String ret = "";
		if (inmueble.getDireccion() != null) {
		    Direccion d = inmueble.getDireccion();
		    ret = d.getCalle() + " " + d.getNro() + ", " + d.getLocalidad() + ", " + d.getProvincia();

		}
		return ret;
	    }).setCaption("Dirección").setId("direccion");

	    grid.addColumn(Inmueble::getPropietario).setCaption("Propietario").setId("propietario");
	    grid.addColumn(Inmueble::getTipoInmueble).setCaption("TipoInmueble").setId("tipo inmueble");
	    grid.addColumn(Inmueble::getEstadoInmueble).setCaption("Estado Inmueble").setId("estado inmueble");
	    grid.addComponentColumn(configurarAcciones()).setCaption("Acciones");
	    grid.getColumns().forEach(c -> c.setResizable(false));

	    HeaderRow filterRow = grid.appendHeaderRow();
	    filterRow.getCell("direccion").setComponent(filtroDireccion());
	    filterRow.getCell("propietario").setComponent(filtroPropietario());
	    filterRow.getCell("tipo inmueble").setComponent(filtroTipo());
	    filterRow.getCell("estado inmueble").setComponent(filtroEstado());
	}

	private Component filtroDireccion() {
	    TextField filtroDireccion = new TextField();
	    filtroDireccion.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
	    filtroDireccion.setPlaceholder("Sin Filtro");
	    filtroDireccion.addValueChangeListener(e -> {
		if (e.getValue() != null) {
		    if (!filtroDireccion.isEmpty()) {
			filtro.setDireccion(inmueble -> inmueble.getDireccion() != null &&
					inmueble.getDireccion()
					.toString().toLowerCase()
					.contains(filtroDireccion.getValue().toLowerCase()));
		    } else
			filtro.setDireccion(inmueble -> true);

		} else {
		    filtro.setDireccion(inmueble -> true);
		}
		updateList();
	    });
	    return filtroDireccion;
	}

	private Component filtroPropietario() {
	    TextField filtroPropietario = new TextField();
	    filtroPropietario.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
	    filtroPropietario.setPlaceholder("Sin Filtro");
	    filtroPropietario.addValueChangeListener(e -> {
		if (e.getValue() != null) {
		    if (!filtroPropietario.isEmpty()) {
			filtro.setPropietario(inmueble -> inmueble.getPropietario()
				.toString().toLowerCase()
				.contains(filtroPropietario.getValue().toLowerCase()));
		    } else
			filtro.setPropietario(inmueble -> true);
		} else {
		    filtro.setPropietario(inmueble -> true);
		}
		updateList();
	    });
	    return filtroPropietario;
	}

	private Component filtroTipo() {
	    ComboBox<TipoInmueble> filtroTipo = new ComboBox<>();
	    filtroTipo.setStyleName(ValoTheme.COMBOBOX_BORDERLESS);
	    filtroTipo.setPlaceholder("Sin Filtro");
	    filtroTipo.setItems(TipoInmueble.toList());
	    filtroTipo.addValueChangeListener(e -> {
		if (e.getValue() != null) {
		    if (!filtroTipo.isEmpty())
			filtro.setTipo(contrato -> contrato.getTipoInmueble().equals(e.getValue()));
		    else
			filtro.setTipo(contrato -> true);
		} else {
		    filtro.setTipo(contrato -> true);
		}
		updateList();
	    });
	    return filtroTipo;
	}

	private Component filtroEstado() {
	    ComboBox<EstadoInmueble> filtroEstado = new ComboBox<>();
	    filtroEstado.setStyleName(ValoTheme.COMBOBOX_BORDERLESS);
	    filtroEstado.setPlaceholder("Sin Filtro");
	    filtroEstado.setItems(EstadoInmueble.toList());
	    filtroEstado.addValueChangeListener(e -> {
		if (e.getValue() != null) {
		    if (!filtroEstado.isEmpty())
			filtro.setEstado(contrato -> contrato.getEstadoInmueble().equals(e.getValue()));
		    else
			filtro.setEstado(contrato -> true);
		} else {
		    filtro.setEstado(contrato -> true);
		}
		updateList();
	    });
	    return filtroEstado;
	}

	private ValueProvider<Inmueble, HorizontalLayout> configurarAcciones() {
	    return inmueble -> {

		Button edit = new Button(VaadinIcons.EDIT);
		edit.addStyleNames(ValoTheme.BUTTON_QUIET, ValoTheme.BUTTON_SMALL);
		edit.addClickListener(e -> {
		    inmuebleForm.setInmueble(inmueble);

		});
		edit.setDescription("Editar");

		Button del = new Button(VaadinIcons.TRASH);
		del.addClickListener(click -> {
		    new DialogConfirmacion("Eliminar",
			    VaadinIcons.WARNING,
			    "¿Esta seguro que desea Eliminar?",
			    "100px",
			    confirmacion -> {
				if (inmuebleService.delete(inmueble)) {
				    showSuccessNotification("Borrado: " + inmueble.getDireccion().toString() + " de " +
					    inmueble.getPropietario().toString());
				} else {
				    showErrorNotification("No se realizaron cambios");
				}
				updateList();
			    });
		});
		del.addStyleNames(ValoTheme.BUTTON_QUIET, ValoTheme.BUTTON_SMALL);
		del.setDescription("Borrar");

		Button verFotos = new Button(VaadinIcons.PICTURE);

		verFotos.addClickListener(click -> {
		    Resource resource = inmuebleService.getPortada(inmueble);
		    if (resource != null) {
			ImageVisualizer imgv = new ImageVisualizer();
			imgv.singleImage(resource);
		    } else {
			Notification.show("Este imueble no posee portada");

		    }
		});
		verFotos.addStyleNames(ValoTheme.BUTTON_QUIET, ValoTheme.BUTTON_SMALL);
		verFotos.setDescription("Ver Fotos");
		String archivoPortada = inmueble.getNombreArchivoPortada();
		if (archivoPortada == null || archivoPortada == "")
		    verFotos.setEnabled(false);

		Button verIntesados = new Button(VaadinIcons.SEARCH);
		verIntesados.addClickListener(click -> {
		    new PersonaABMViewWindow("Posibles interesados en este inmueble", new FiltroInteresados(inmueble));
		});
		verIntesados.addStyleNames(ValoTheme.BUTTON_QUIET, ValoTheme.BUTTON_SMALL);
		verIntesados.setDescription("Buscar interesados.");

		HorizontalLayout hl = new HorizontalLayout(edit, del, verFotos, verIntesados);
		hl.setCaption("Accion " + acciones);
		hl.setSpacing(false);
		acciones++;
		return hl;
	    };
	}

	public void updateList() {
	    List<Inmueble> inmuebles = inmuebleService.findAll(filtro);
	    inmuebles.forEach(i ->{
		//System.out.println("---------------------------------------------------------");
		//System.out.println("---------------------------------------------------------");
		//System.out.println(i);
		//System.out.println("Cantidad de publicaciones: " + i.getPublicaciones()!= null? i.getPublicaciones().size() : "0");
		if (i.getPublicaciones()!= null) {
		   // i.getPublicaciones().forEach(p ->// System.out.println("Publicacion:" + p));
		}
	    });
	    grid.setItems(inmuebles);
	}

	public void filtrarPorCalle(String filtro) {
		List<Inmueble> inmuebles = inmuebleService.filtrarPorCalle(filtro);
		grid.setItems(inmuebles);
	}

    }

}
