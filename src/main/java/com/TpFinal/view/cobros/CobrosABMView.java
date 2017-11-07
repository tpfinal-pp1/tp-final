package com.TpFinal.view.cobros;

import com.TpFinal.dto.cobro.Cobro;
import com.TpFinal.dto.inmueble.TipoMoneda;
import com.TpFinal.services.CobroService;
import com.TpFinal.services.DashboardEvent;
import com.TpFinal.view.component.DefaultLayout;
import com.google.common.eventbus.Subscribe;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.HeaderRow;
import com.vaadin.ui.renderers.LocalDateRenderer;
import com.vaadin.ui.themes.ValoTheme;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;

/**
 * Created by Max on 10/14/2017.
 */
@Title("Cobros")
@Theme("valo")
// @Widgetset("com.vaadin.v7.Vaadin7WidgetSet")
public class CobrosABMView extends DefaultLayout implements View {

    /**
     * 
     */
    private static final long serialVersionUID = -3937821370623152063L;
    private Grid<Cobro> grid = new Grid<>();
    private Button clearFilterTextBtn = new Button(VaadinIcons.CLOSE);
    private HorizontalLayout mainLayout;
    private CobrosForm cobrosForm = new CobrosForm(this);
    private boolean isonMobile = false;
    private Controller controller = new Controller();
    private int acciones = 0;

    public CobrosABMView() {
	super();
	buildLayout();
	controller.configureComponents();
	UI.getCurrent().getPage().getStyles().add(".v-grid-row.vencido {color: darkred;}");

    }

    public Controller getController() {
	return controller;
    }

    private void buildLayout() {
	CssLayout filtering = new CssLayout();
	filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

	filtering.addComponents(clearFilterTextBtn);
	HorizontalLayout hlf = new HorizontalLayout(filtering);
	buildToolbar("Cobros", hlf);
	grid.setSizeFull();
	mainLayout = new HorizontalLayout(grid, cobrosForm);
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
	if (this.cobrosForm.isVisible()) {
	    cobrosForm.cancel();
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
	@SuppressWarnings("unused")
	private final Logger logger = Logger.getLogger(CobrosABMView.Controller.class);

	private CobroService cobroService = new CobroService();

	private FiltroCobros filtro = new FiltroCobros();

	public void configureComponents() {
	    configureFilter();
	    configureGrid();
	    updateList();
	    cobrosForm.cancel();

	}

	private void configureFilter() {
	    clearFilterTextBtn.setDescription("Cerrar Formulario");
	    clearFilterTextBtn.addClickListener(e -> ClearFilterBtnAction());
	}

	private void configureGrid() {

	    grid.setStyleGenerator(cobro -> {
		String ret = null;
		if (cobro.getFechaDeVencimiento() != null) {
		    if (cobro.getFechaDeVencimiento().isBefore(LocalDate.now())) {
			ret = "vencido";
		    }
		}
		return ret;
	    });

	    grid.asSingleSelect().addValueChangeListener(event -> {
		if (event.getValue() == null) {
		    if (cobrosForm.isVisible())
			setComponentsVisible(true);
		    cobrosForm.setVisible(false);
		    cobrosForm.clearFields();
		    cobrosForm.setVisible(false);
		}
	    });

	    grid.addColumn(cobro -> {
		String ret = "";
		ret = cobro.getContrato().getInmueble().toString();
		return ret;
	    }).setCaption("Inmueble").setId("inmuebles");

	    // XXX para futuros cobros de ventas y otros
	    grid.addColumn(cobro -> {
		String ret = "";
		ret = "Alquiler";
		return ret;
	    }).setCaption("Tipo").setId("tipos");

	    grid.addColumn(Cobro::getFechaDeVencimiento, new LocalDateRenderer("dd/MM/yyyy")).setCaption(
		    "Fecha De Vencimiento")
		    .setId("fechasDeVencimiento");

	    grid.addColumn(cobro -> {
		String ret = "";
		if (cobro.getFechaDePago() != null) {
		    DateTimeFormatter formatters = DateTimeFormatter.ofPattern("d/MM/uuuu");
		    ret = cobro.getFechaDePago().format(formatters);
		} else
		    ret = "No ha sido pagado";
		return ret;
	    }).setCaption("Fecha de Pago").setId("fechasDePagos");

	    grid.addColumn(cobro -> {
		String ret = "";
		ret = cobro.getContrato().getInquilinoContrato().toString();
		return ret;
	    }).setCaption("Inquilino").setId("inquilinos");

	    grid.addColumn(cobro -> {
		String ret = "";
		ret = TipoMoneda.getSimbolo(cobro.getContrato().getMoneda()) + " " + cobro.getMontoRecibido()
			.toString();
		return ret;
	    }).setCaption("Monto").setId("montos");

	    grid.addComponentColumn(cobro -> {
		Button ver = new Button(VaadinIcons.EYE);
		ver.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_SMALL, ValoTheme.BUTTON_PRIMARY);
		ver.addClickListener(e -> {
		    cobrosForm.setCobro(cobro);
		});
		HorizontalLayout hl = new HorizontalLayout(ver);
		hl.setCaption("Accion " + acciones);
		hl.setId("acciones");
		acciones++;
		return hl;
	    }).setCaption("Acciones").setId("acciones");
	    grid.setColumnOrder("acciones", "inmuebles", "tipos", "fechasDeVencimiento", "fechasDePagos", "inquilinos",
		    "montos");
	    grid.getColumns().forEach(col -> {
		col.setResizable(false);
		col.setHidable(true);
	    });
	    grid.getColumn("montos").setWidth(250);

	    HeaderRow filterRow = grid.appendHeaderRow();
	    filterRow.getCell("inmuebles").setComponent(filtroInmuebles());
	    // TODO
	    filterRow.getCell("tipos").setComponent(filtroTipos());
	    filterRow.getCell("fechasDeVencimiento").setComponent(filtroFechaVencimiento());
	    filterRow.getCell("fechasDePagos").setComponent(filtroFechaPago());
	    filterRow.getCell("inquilinos").setComponent(filtroInquilinos());
	    filterRow.getCell("montos").setComponent(filtroMontos());
	}

	private Component filtroFechaVencimiento() {
	    HorizontalLayout hl = new HorizontalLayout();
	    DateField fDesde = filtroFVDesde();
	    DateField fHasta = filtroFVHasta();
	    TextField anio = filtroVAnio();
	    TextField mes = filtroVMes();
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
		    filtro.setAnioV(c -> true);
		    filtro.setMesV(f -> true);
		    mostrarAnioMes(hl, adicionales, hlAnioMes);
		} else {
		    adicionales.setIcon(VaadinIcons.BOOKMARK);
		    adicionales.setDescription("Búsqueda por Año y Mes");
		    anio.clear();
		    mes.clear();
		    filtro.setAnioV(c -> true);
		    filtro.setMesV(f -> true);
		    mostrarFechas(hl, adicionales, hlFechas);
		}
		updateList();
	    });
	    mostrarFechas(hl, adicionales, hlFechas);
	    return hl;
	}

	private Component filtroFechaPago() {
	    HorizontalLayout hl = new HorizontalLayout();
	    DateField fDesde = filtroFPDesde();
	    DateField fHasta = filtroFPHasta();
	    TextField anio = filtroPAnio();
	    TextField mes = filtroPMes();
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
		    filtro.setAnioV(c -> true);
		    filtro.setMesV(f -> true);
		    mostrarAnioMes(hl, adicionales, hlAnioMes);
		} else {
		    adicionales.setIcon(VaadinIcons.BOOKMARK);
		    adicionales.setDescription("Búsqueda por Año y Mes");
		    anio.clear();
		    mes.clear();
		    filtro.setAnioV(c -> true);
		    filtro.setMesV(f -> true);
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

	private TextField filtroVMes() {
	    TextField mes = new TextField();
	    mes.setPlaceholder("Mes");
	    mes.addBlurListener(e -> {
		if (!mes.isVisible())
		    mes.clear();
	    });
	    mes.addValueChangeListener(e -> {
		if (e.getValue() != null) {
		    filtro.setMesV(cobro -> cobro.getFechaDeVencimiento()
			    .getMonth()
			    .getDisplayName(TextStyle.FULL, Locale.forLanguageTag("es-AR"))
			    .toLowerCase().contains(mes.getValue().toLowerCase()));
		    ;
		} else {
		    filtro.setMesV(cobro -> true);
		}
		updateList();
	    });
	    return mes;
	}

	private TextField filtroVAnio() {

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
			filtro.setAnioV(c -> c.getFechaDeVencimiento().getYear() == anioInt);
		    } catch (Exception ex) {
			System.err.println(ex.getMessage());
			filtro.setAnioV(cobro -> true);
		    }
		} else {
		    filtro.setAnioV(cobro -> true);
		}
		updateList();
	    });

	    return anio;
	}

	private DateField filtroFVDesde() {
	    DateField fDesde = new DateField();
	    fDesde.setPlaceholder("Desde");
	    fDesde.setParseErrorMessage("Formato de fecha no reconocido");
	    fDesde.addValueChangeListener(e -> {
		if (fDesde.getValue() != null) {
		    filtro.setFiltroFechaVencimientoDesde(cobro -> {
			return fDesde.getValue() == null ? true
				: cobro.getFechaDeVencimiento().compareTo(fDesde.getValue()) >= 0;
		    });
		} else {
		    filtro.setFiltroFechaVencimientoDesde(f -> true);
		}
		updateList();
	    });
	    return fDesde;
	}

	private DateField filtroFVHasta() {
	    DateField fHasta = new DateField();
	    fHasta.setPlaceholder("Hasta");
	    fHasta.setParseErrorMessage("Formato de fecha no reconocido");
	    fHasta.addValueChangeListener(e -> {
		if (fHasta.getValue() != null) {
		    filtro.setFiltroFechaVencimientoHasta(cobro -> {
			return fHasta.getValue() == null ? true
				: cobro.getFechaDeVencimiento().compareTo(fHasta.getValue()) <= 0;
		    });
		} else {
		    filtro.setFiltroFechaVencimientoHasta(f -> true);
		}
		updateList();
	    });
	    return fHasta;
	}

	private DateField filtroFPDesde() {
	    DateField fDesde = new DateField();
	    fDesde.setPlaceholder("Desde");
	    fDesde.setParseErrorMessage("Formato de fecha no reconocido");
	    fDesde.addValueChangeListener(e -> {
		if (fDesde.getValue() != null) {
		    filtro.setFiltroFechaDePagoDesde(cobro -> {
			LocalDate fechaPago = cobro.getFechaDePago();
			if (fechaPago != null) {
			    return fDesde.getValue() == null ? true
				    : fechaPago.compareTo(fDesde.getValue()) >= 0;
			} else {
			    return false;
			}
		    });
		} else {
		    filtro.setFiltroFechaDePagoDesde(f -> true);
		}
		updateList();
	    });
	    return fDesde;
	}

	private DateField filtroFPHasta() {
	    DateField fHasta = new DateField();
	    fHasta.setPlaceholder("Hasta");
	    fHasta.setParseErrorMessage("Formato de fecha no reconocido");
	    fHasta.addValueChangeListener(e -> {
		if (fHasta.getValue() != null) {
		    filtro.setFiltroFechaDePagoHasta(cobro -> {
			LocalDate fechaPago = cobro.getFechaDePago();
			if (fechaPago != null) {
			    return fHasta.getValue() == null ? true
				    : fechaPago.compareTo(fHasta.getValue()) <= 0;
			} else {
			    return false;
			}
		    });
		} else {
		    filtro.setFiltroFechaDePagoHasta(f -> true);
		}
		updateList();
	    });
	    return fHasta;
	}

	private TextField filtroPAnio() {

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

			filtro.setAnioP(c -> {
			    LocalDate fechaPago = c.getFechaDePago();
			    if (fechaPago != null) {
				return c.getFechaDePago().getYear() == anioInt;
			    } else {
				return false;
			    }
			});
		    } catch (Exception ex) {
			System.err.println(ex.getMessage());
			filtro.setAnioP(cobro -> true);
		    }
		} else {
		    filtro.setAnioP(cobro -> true);
		}
		updateList();
	    });

	    return anio;
	}

	private TextField filtroPMes() {
	    TextField mes = new TextField();
	    mes.setPlaceholder("Mes");
	    mes.addBlurListener(e -> {
		if (!mes.isVisible())
		    mes.clear();
	    });
	    mes.addValueChangeListener(e -> {
		if (e.getValue() != null) {
		    filtro.setMesP(cobro -> {
			LocalDate fechaPago = cobro.getFechaDePago();
			if (fechaPago != null) {
			    return fechaPago
				    .getMonth()
				    .getDisplayName(TextStyle.FULL, Locale.forLanguageTag("es-AR"))
				    .toLowerCase().contains(mes.getValue().toLowerCase());
			} else {
			    return false;
			}
		    });
		    ;
		} else {
		    filtro.setMesP(cobro -> true);
		}
		updateList();
	    });
	    return mes;
	}

	private Component filtroTipos() {
	    TextField fitroTipos = new TextField();
	    fitroTipos.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
	    fitroTipos.setPlaceholder("Sin Filtro");
	    fitroTipos.addValueChangeListener(e -> {
		if (e.getValue() != null) {
		    // TODO
		} else {
		    // TODO
		}
		// updateList();
	    });
	    return fitroTipos;
	}

	private Component filtroInmuebles() {
	    TextField filtroInmuebles = new TextField();
	    filtroInmuebles.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
	    filtroInmuebles.setPlaceholder("Sin Filtro");
	    filtroInmuebles.addValueChangeListener(e -> {
		if (e.getValue() != null) {
		    filtro.setFiltroInmueble(cobro -> cobro.getContrato().getInmueble()
			    .toString().toLowerCase()
			    .contains(e.getValue().toLowerCase()));
		} else {
		    filtro.setFiltroInmueble(cobro -> true);
		}
		updateList();
	    });
	    return filtroInmuebles;
	}

	private Component filtroInquilinos() {
	    TextField filtroInquilino = new TextField();
	    filtroInquilino.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
	    filtroInquilino.setPlaceholder("Sin Filtro");
	    filtroInquilino.addValueChangeListener(e -> {
		if (e.getValue() != null) {
		    filtro.setFiltroInquilino(cobro -> cobro.getContrato().getInquilinoContrato()
			    .toString().toLowerCase()
			    .contains(e.getValue().toLowerCase()));
		} else {
		    filtro.setFiltroInquilino(cobro -> true);
		}
		updateList();
	    });
	    return filtroInquilino;
	}

	private Component filtroMontos() {
	    HorizontalLayout hl = new HorizontalLayout();
	    HorizontalLayout hlWrapper = new HorizontalLayout();
	    TextField valor = filtroMonto();
	    Button tipoMoneda = new Button(TipoMoneda.getSimbolo(TipoMoneda.Pesos));
	    tipoMoneda.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_TINY);
	    tipoMoneda.setWidth("10%");
	    tipoMoneda.setDescription("Búsqueda en Pesos");
	    tipoMoneda.addClickListener(e -> {
		if (tipoMoneda.getCaption().equals(TipoMoneda.getSimbolo(TipoMoneda.Pesos))) {
		    tipoMoneda.setCaption(TipoMoneda.getSimbolo(TipoMoneda.Dolares));
		    tipoMoneda.setDescription("Búsqueda en Dolares");
		    filtro.setTipoMoneda(TipoMoneda.Dolares);

		} else {
		    tipoMoneda.setCaption(TipoMoneda.getSimbolo(TipoMoneda.Pesos));
		    tipoMoneda.setDescription("Búsqueda en Pesos");
		    filtro.setTipoMoneda(TipoMoneda.Pesos);
		}
		updateList();
	    });
	    hlWrapper.setSpacing(true);
	    hlWrapper.addComponents(tipoMoneda, valor);
	    hlWrapper.setExpandRatio(valor, 1);
	    hl.addComponent(hlWrapper);
	    hl.setComponentAlignment(hlWrapper, Alignment.MIDDLE_CENTER);
	    return hl;
	}

	private TextField filtroMonto() {
	    TextField monto = new TextField();
	    monto.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
	    monto.setPlaceholder("Monto total");
	    monto.addValueChangeListener(e -> {
		if (e.getValue() != null) {
		    filtro.setFiltroMonto(cobro -> cobro.getMontoRecibido()
			    .toString().toLowerCase()
			    .contains(e.getValue().toLowerCase()));
		} else {
		    filtro.setFiltroMonto(cobro -> true);
		}
		updateList();
	    });
	    return monto;
	}

	public void updateList() {
	    List<Cobro> cobros = cobroService.findAll(filtro);
	    grid.setItems(cobros);
	}

    }

}