package com.TpFinal.view.inmuebles;

import com.TpFinal.dto.contrato.ContratoAlquiler;
import com.TpFinal.dto.inmueble.*;
import com.TpFinal.dto.publicacion.EstadoPublicacion;
import com.TpFinal.dto.publicacion.Publicacion;
import com.TpFinal.dto.publicacion.PublicacionAlquiler;
import com.TpFinal.dto.publicacion.PublicacionVenta;
import com.TpFinal.services.ContratoService;
import com.TpFinal.services.DashboardEvent;
import com.TpFinal.services.InmuebleService;
import com.TpFinal.services.PublicacionService;
import com.TpFinal.utils.Utils;
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

import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;

@Title("Inmuebles")
@Theme("valo")
public class InmuebleMatchingView extends DefaultLayout implements View {

    /**
     * 
     */
    private static final long serialVersionUID = 1749224574589852377L;
    private Grid<Inmueble> grid = new Grid<>();
    private Button clearFilterTextBtn = new Button(VaadinIcons.CLOSE);
    private HorizontalLayout mainLayout;
    private InmuebleFormMatching inmuebleForm = new InmuebleFormMatching(this);
    private boolean isonMobile = false;
    private Controller controller = new Controller();
    private Supplier<List<Inmueble>> inmuebleSupplier;
    private FiltroInmueble filtro = new FiltroInmueble();
    private Predicate<Inmueble> filtroCustom = i -> true;

    // acciones segun numero de fila
    int acciones = 0;

    public InmuebleMatchingView() {
	super();
	buildLayout();
	controller.configureComponents();

    }

    public InmuebleMatchingView(Supplier<List<Inmueble>> supplier) {
	super();
	inmuebleSupplier = supplier;
	buildLayout();
	controller.configureComponents();
    }

    public InmuebleMatchingView(Supplier<List<Inmueble>> supplier, Predicate<Inmueble> filtroCustom) {
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
	    inmuebleForm.cancel();
	}

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
	    configureGrid();
	    updateList();
	}

	private void configureFilter() {
	    clearFilterTextBtn.setDescription("Cerrar ventana");
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

	    grid.addColumn(inmueble -> {
		List<Publicacion> pubs = inmuebleService.getPublicacionesActivas(inmueble);
		String ret = "";
		for (Publicacion p : pubs) {
		    if (p instanceof PublicacionAlquiler) {
			PublicacionAlquiler pubA = (PublicacionAlquiler) p;
			String entrada = "";
			ContratoAlquiler aux = getContratoTemporalAPartirDePublicacion(pubA);
			entrada = "(E:" + TipoMoneda.getSimbolo(pubA.getMoneda()) + ContratoService.getValorEntrada(aux)
				.toPlainString() + ")";
			ret = ret + " Alquiler: " + TipoMoneda.getSimbolo(pubA.getMoneda()) + pubA.getPrecio()
				.toString() + " " + entrada + " " + " | ";
		    } else {
			PublicacionVenta pubV = (PublicacionVenta) p;

			ret = ret + " Venta: " + TipoMoneda.getSimbolo(pubV.getMoneda()) + pubV.getPrecio().toString()
				+ " | ";
		    }
		}

		if (ret.length() > 0) {
		    ret = ret.substring(0, ret.length() - 3);
		}

		if (ret == "")
		    ret = "No Encontrado";
		return ret;
	    }).setCaption("Precio").setId("precio")
		    .setDescriptionGenerator(i -> {
			String ret = "";
			List<Publicacion> pubs = inmuebleService.getPublicacionesActivas(i);
			PublicacionAlquiler pub = pubs.stream()
				.filter(p -> p instanceof PublicacionAlquiler)
				.map(p -> (PublicacionAlquiler) p)
				.findFirst().orElse(null);
			if (pub != null) {
			    ContratoAlquiler c = getContratoTemporalAPartirDePublicacion(pub);
			    String simbolo = TipoMoneda.getSimbolo(pub.getMoneda());
			    ret = "Valor entrada:"
				    + "\n1ra cuota: " + simbolo + pub.getValorCuota().setScale(2,RoundingMode.HALF_UP)
					    .toPlainString()
				    + "\nMes comisión: " + simbolo + ContratoService.getMontoUltimaCuota(c)
					    .toPlainString()
				    + "\nMes depósito: " + simbolo + ContratoService.getMontoUltimaCuota(c)
					    .toPlainString()
				    + "\nsellado: " + simbolo + ContratoService.getValorSelladoAlquiler(c)
					    .toPlainString()
				    + "\ngarantes("+pub.getCantCertificadosGarantes()+"): " + simbolo + ContratoService.getValorCertificadosGarantes(c)
					    .toPlainString()
				    + "\nTOTAL: " + simbolo + ContratoService.getValorEntrada(c).toPlainString();
			}

			return ret;
		    });
	    grid.addColumn(Inmueble::getPropietario).setCaption("Propietario").setId("propietario");
	    grid.addColumn(Inmueble::getTipoInmueble).setCaption("TipoInmueble").setId("tipo inmueble");
	    grid.addColumn(Inmueble::getEstadoInmueble).setCaption("Estado Inmueble").setId("estado inmueble");
	    grid.addComponentColumn(configurarAcciones()).setCaption("Acciones").setId("acciones");
	    grid.getColumns().forEach(col -> {
		col.setResizable(false);
		col.setHidable(true);
	    });

	    grid.addColumn(inmueble -> {
		String ret = "Disponible";
		LocalDate fecha = PublicacionService.getFechaDisponibilidad(inmueble);
		if (fecha != null && fecha.isAfter(
			LocalDate.now())) {
		    ret = fecha.format(Utils.getFormatoFechaArg());
		}
		return ret;

	    }).setCaption("Fecha de disponibilidad").setId("fechaDisponibilidad");

	    grid.setColumnOrder("acciones", "precio", "fechaDisponibilidad", "estado inmueble", "tipo inmueble",
		    "direccion");

	    HeaderRow filterRow = grid.appendHeaderRow();
	    filterRow.getCell("direccion").setComponent(filtroDireccion());
	    filterRow.getCell("propietario").setComponent(filtroPropietario());
	    filterRow.getCell("tipo inmueble").setComponent(filtroTipo());
	    filterRow.getCell("estado inmueble").setComponent(filtroEstado());
	}

	private ContratoAlquiler getContratoTemporalAPartirDePublicacion(PublicacionAlquiler pubA) {
	    return new ContratoAlquiler.Builder()
		    .setCantCertificadosGarantes(pubA.getCantCertificadosGarantes())
		    .setDuracionContrato(pubA.getDuracionContrato())
		    .setIntervaloActualizacion(pubA.getIntervaloActualizacion())
		    .setPorcentajeIncremento(pubA.getPorcentajeIncrementoCuota())
		    .setTipoIncrementoCuota(pubA.getTipoIncrementoCuota())
		    .setValorIncial(pubA.getValorCuota())
		    .setInmueble(pubA.getInmueble())
		    .build();
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
		Button verFotos = new Button(VaadinIcons.PICTURE);
		Map<String, Resource> fotos = new HashMap<>();
		verFotos.addClickListener(click -> {

		    for (String pathFoto : inmueble.getPathImagenes()) {
			Resource foto = InmuebleService.GenerarStreamResource(pathFoto);
			if (foto != null) {
			    fotos.put(pathFoto, foto);
			}
		    }
		    ImageVisualizer imgv = new ImageVisualizer(fotos, inmueble.getNombreArchivoPortada());
		});
		verFotos.addStyleNames(ValoTheme.BUTTON_QUIET, ValoTheme.BUTTON_SMALL);
		verFotos.setDescription("Ver Fotos");
		String archivoPortada = inmueble.getNombreArchivoPortada();
		if (inmueble.getNombreArchivoPortada() == null)
		    verFotos.setEnabled(false);
		HorizontalLayout hl = new HorizontalLayout(edit, verFotos);
		hl.setCaption("Accion " + acciones);
		hl.setSpacing(false);
		acciones++;
		return hl;
	    };
	}

	public void updateList() {
	    List<Inmueble> inmuebles = inmuebleService.findAll(filtro);
	    inmuebles.forEach(i -> {
		// System.out.println("---------------------------------------------------------");
		// System.out.println("---------------------------------------------------------");
		// System.out.println(i);
		// System.out.println("Cantidad de publicaciones: " + i.getPublicaciones()!=
		// null? i.getPublicaciones().size() : "0");
		if (i.getPublicaciones() != null) {
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
