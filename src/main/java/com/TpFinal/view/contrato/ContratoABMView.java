package com.TpFinal.view.contrato;

import com.TpFinal.dto.contrato.Contrato;
import com.TpFinal.dto.contrato.ContratoAlquiler;
import com.TpFinal.dto.contrato.ContratoVenta;
import com.TpFinal.dto.contrato.EstadoContrato;
import com.TpFinal.dto.persona.Persona;
import com.TpFinal.services.ContratoService;
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
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.HeaderRow;
import com.vaadin.ui.renderers.LocalDateRenderer;
import com.vaadin.ui.themes.ValoTheme;

import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/* User Interface written in Java.
 *
 * Define the user interface shown on the Vaadin generated web page by extending the UI class.
 * By default, a new UI instance is automatically created when the page is loaded. To reuse
 * the same instance, add @PreserveOnRefresh.
 */

@Title("Addressbook")
@Theme("valo")
@Widgetset("com.vaadin.v7.Vaadin7WidgetSet")
public class ContratoABMView extends DefaultLayout implements View {

	private Grid<Contrato> grid = new Grid<>();
	private Button nuevoAlquiler = new Button("Nuevo Alquiler");
	private Button nuevaVenta = new Button("Nueva Venta");
	private Button clearFilterTextBtn = new Button(VaadinIcons.CLOSE);
	private HorizontalLayout mainLayout;

	// Forms
	private ContratoVentaForm ContratoVentaForm = new ContratoVentaForm(this);
	private ContratoAlquilerForm ContratoAlquilerForm = new ContratoAlquilerForm(this);

	private boolean isonMobile = false;

	ContratoService service = new ContratoService();
	private List<Contrato> contratos;
	private FiltroContrato filtro = new FiltroContrato();

	// acciones segun numero de fila
	private int acciones = 0;

	public ContratoABMView() {
		super();
		buildLayout();
		configureComponents();

	}

	private void configureComponents() {
		configureFilter();
		configureNuevaVenta();
		configureNuevoAlquiler();
		configureGrid();
		updateList();
	}

	private void configureNuevoAlquiler() {
		nuevoAlquiler.addClickListener(e -> {
			grid.asSingleSelect().clear();
			ContratoAlquilerForm.clearFields();
			ContratoAlquilerForm.setContratoAlquiler(null);

		});
	}

	private void configureNuevaVenta() {
		nuevaVenta.addClickListener(e -> {
			grid.asSingleSelect().clear();
			ContratoVentaForm.clearFields();
			ContratoVentaForm.setContratoVenta(null);
		});
		nuevaVenta.setStyleName(ValoTheme.BUTTON_PRIMARY);
	}

	private void configureFilter() {
		clearFilterTextBtn.setVisible(false);
		clearFilterTextBtn.addClickListener(e -> ClearFilterBtnAction());
	}

	private void configureGrid() {

		grid.addColumn(getTipoContrato()).setCaption("Tipo").setId("tipo");
		grid.addColumn(Contrato::getFechaCelebracion, new LocalDateRenderer("dd/MM/yyyy")).setCaption(
				"Fecha de celebración").setId("fecha celebracion");
		grid.addColumn(Contrato::getEstadoContrato).setCaption("Estado").setId("estado");
		grid.addColumn(contrato -> contrato.getInmueble().getDireccion()).setCaption("Dirección").setId("direccion");
		grid.addColumn(getIntervinientes()).setCaption("Intervinientes").setId("intervinientes");
		grid.addComponentColumn(configurarAcciones()).setCaption("Acciones").setId("acciones");
		grid.getColumns().forEach(col -> col.setResizable(false));

		HeaderRow filterRow = grid.appendHeaderRow();
		filterRow.getCell("tipo").setComponent(filtroTipo());
		filterRow.getCell("fecha celebracion").setComponent(filtroFecha());
		filterRow.getCell("estado").setComponent(filtroEstado());
		filterRow.getCell("direccion").setComponent(filtroDireccion());
		filterRow.getCell("intervinientes").setComponent(filtroIntervinientes());

	}

	private Component filtroIntervinientes() {
		TextField filtroIntervinientes = new TextField();
		filtroIntervinientes.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
		filtroIntervinientes.setPlaceholder("Sin Filtro");
		filtroIntervinientes.addValueChangeListener(e -> {
			if (e.getValue() != null) {
				filtro.setIntervinientes(contrato -> {
					String intervinientes = getIntervinientes().apply(contrato);
					return intervinientes.toLowerCase().contains(filtroIntervinientes.getValue().toLowerCase());
				});
			} else {
				filtro.setIntervinientes(contrato -> true);
			}
			updateList();
		});
		return filtroIntervinientes;
	}

	private Component filtroDireccion() {
		TextField filtroDireccion = new TextField();
		filtroDireccion.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
		filtroDireccion.setPlaceholder("Sin Filtro");
		filtroDireccion.addValueChangeListener(e -> {
			if (e.getValue() != null) {
				filtro.setDireccion(contrato -> contrato.getInmueble().getDireccion()
						.toString().toLowerCase()
						.contains(filtroDireccion.getValue().toLowerCase()));
			} else {
				filtro.setDireccion(contrato -> true);
			}
			updateList();
		});
		return filtroDireccion;
	}

	private Component filtroEstado() {
		ComboBox<EstadoContrato> filtroEstado = new ComboBox<>();
		filtroEstado.setStyleName(ValoTheme.COMBOBOX_BORDERLESS);
		filtroEstado.setPlaceholder("Sin Filtro");
		filtroEstado.setItems(EstadoContrato.toList());
		filtroEstado.addValueChangeListener(e -> {
			if (e.getValue() != null) {
				if (!filtroEstado.isEmpty())
					filtro.setEstado(contrato -> contrato.getEstadoContrato().equals(e.getValue()));
				else
					filtro.setEstado(contrato -> true);
			} else {
				filtro.setEstado(contrato -> true);
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
				filtro.setMes(contrato -> contrato.getFechaCelebracion()
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
					filtro.setAnio(contrato -> contrato.getFechaCelebracion().getYear() == anioInt);
				} catch (Exception ex) {
				    System.err.println(ex.getMessage());
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
							: contrato.getFechaCelebracion().compareTo(fDesde.getValue()) >= 0;
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
							: contrato.getFechaCelebracion().compareTo(fHasta.getValue()) <= 0;
				});
			} else {
				filtro.setFechaHasta(f -> true);
			}
			updateList();
		});
		return fHasta;
	}

	private Component filtroTipo() {
		ComboBox<FiltroContrato.tipo> filtroTipo = new ComboBox<>();
		filtroTipo.setStyleName(ValoTheme.COMBOBOX_BORDERLESS);
		filtroTipo.setPlaceholder("Sin Filtro");
		filtroTipo.setItems(Arrays.asList(FiltroContrato.tipo.Alquiler, FiltroContrato.tipo.Venta));
		filtroTipo.addValueChangeListener(e -> {
			if (e.getValue() != null) {
				if (e.getValue().equals(FiltroContrato.tipo.Alquiler)) {
					filtro.setTipo(contrato -> contrato instanceof ContratoAlquiler);
				} else {
					filtro.setTipo(contrato -> contrato instanceof ContratoVenta);
				}
			} else {
				filtro.setTipo(t -> true);
				System.out.println("Entre al vacio");
			}
			updateList();
		});
		return filtroTipo;
	}

	private ValueProvider<Contrato, String> getIntervinientes() {
		return contrato -> {
			String ret = "";
			if (contrato instanceof ContratoVenta) {
				ContratoVenta c = (ContratoVenta) contrato;
				Persona propietario = c.getInmueble().getPropietario().getPersona();
				Persona comprador = c.getComprador();
				ret = propietario.getNombre() + " " + propietario.getApellido() + ", " + comprador.getNombre() + " "
						+ comprador.getApellido();
			} else {
				ContratoAlquiler c = (ContratoAlquiler) contrato;
				Persona propietario = c.getInmueble().getPropietario().getPersona();
				Persona inquilino = c.getInquilinoContrato().getPersona();
				ret = propietario.getNombre() + " " + propietario.getApellido() + ", " + inquilino.getNombre() + " "
						+ inquilino.getApellido();

			}
			return ret;
		};
	}

	private ValueProvider<Contrato, String> getTipoContrato() {
		return contrato -> {
			String ret = "";
			if (contrato instanceof ContratoVenta) {
				ret = "Venta";
			} else {
				ret = "Alquiler";
			}
			return ret;
		};
	}

	private ValueProvider<Contrato, HorizontalLayout> configurarAcciones() {
		return contrato -> {
			Button edit = new Button(VaadinIcons.EDIT);
			edit.addClickListener(e -> {
				if (contrato instanceof ContratoAlquiler) {
					ContratoAlquilerForm.setContratoAlquiler((ContratoAlquiler) contrato);
				} else {
					ContratoVentaForm.setContratoVenta((ContratoVenta) contrato);
				}

			});
			edit.setDescription("Editar");

			Button del = new Button(VaadinIcons.TRASH);
			del.addClickListener(click -> {
				DialogConfirmacion dialog = new DialogConfirmacion("Eliminar",
						VaadinIcons.WARNING,
						"¿Esta seguro que desea Eliminar?",
						"100px",
						confirmacion -> {
							if (service.delete(contrato)) {
								showSuccessNotification("Contrato Borrado");
							} else {
								showErrorNotification("No se realizaron cambios");
							}
							updateList();
						});

			});
			del.setDescription("Borrar");

			Button renovarContrato = new Button(VaadinIcons.REFRESH);
			renovarContrato.addClickListener(click -> {
				ContratoAlquilerForm.setContratoAlquiler((ContratoAlquiler) contrato);
				ContratoAlquilerForm.renovarContrato.click();
			});
			renovarContrato.setDescription("Renovar Contrato");

			Button finalizarCarga = new Button(VaadinIcons.CHECK_SQUARE);
			finalizarCarga.addClickListener(click -> {
				if (contrato instanceof ContratoAlquiler) {
					ContratoAlquilerForm.setContratoAlquiler((ContratoAlquiler) contrato);
					ContratoAlquilerForm.finalizarCarga.click();
				} else {
					ContratoVentaForm.setContratoVenta((ContratoVenta) contrato);
					ContratoVentaForm.finalizarCarga.click();
				}
			});
			finalizarCarga.setDescription("Finalizar Carga");

			HorizontalLayout hl = new HorizontalLayout(edit, finalizarCarga, renovarContrato, del);
			hl.forEach(button -> button.addStyleNames(ValoTheme.BUTTON_QUIET, ValoTheme.BUTTON_SMALL));
			hl.setSpacing(false);

			EstadoContrato estado = contrato.getEstadoContrato();
			if (estado == EstadoContrato.EnProcesoDeCarga) {
				renovarContrato.setEnabled(false);
			} else if (estado == EstadoContrato.Vigente || estado == EstadoContrato.ProximoAVencer) {
				del.setEnabled(false);
				finalizarCarga.setEnabled(false);
				renovarContrato.setEnabled(false);
			} else { // Estado.Vencido
				del.setEnabled(false);
				finalizarCarga.setEnabled(false);
				if (contrato instanceof ContratoVenta)
					renovarContrato.setEnabled(false);
			}

			hl.forEach(button -> {
				button.addStyleNames(ValoTheme.BUTTON_QUIET, ValoTheme.BUTTON_SMALL);
				if (!button.isEnabled()) {
					button.addStyleName(ValoTheme.BUTTON_BORDERLESS);
					((Button) button).setDescription("No disponible");
				}
			});
			hl.setSpacing(false);
			hl.setCaption("Accion " + acciones);
			acciones++;
			return hl;
		};
	}

	public void setComponentsVisible(boolean b) {
		nuevaVenta.setVisible(b);
		nuevoAlquiler.setVisible(b);


			clearFilterTextBtn.setVisible(!b);

		// clearFilterTextBtn.setVisible(b);
		if (isonMobile)
			grid.setVisible(b);

	}

	private void buildLayout() {
		Responsive.makeResponsive(this);
		CssLayout filtering = new CssLayout();

		nuevaVenta.setStyleName(ValoTheme.BUTTON_PRIMARY);

		if (checkIfOnMobile()) {

			// layout.setSpacing(false);
			// layout.setResponsive(true);

			// Responsive.makeResponsive(layout);
			// filter.setWidth("58%");
			nuevaVenta.setCaption("Venta");
			nuevoAlquiler.setCaption("Alquiler");
			// nuevaVenta.addStyleName(ValoTheme.BUTTON_TINY);
			// nuevoAlquiler.addStyleName(ValoTheme.BUTTON_TINY);

			// layout.setMargin(false);
			// layout.setSizeUndefined();
			filtering.addComponents(clearFilterTextBtn, nuevaVenta, nuevoAlquiler);
			clearFilterTextBtn.setVisible(false);

		} else {
			HorizontalLayout layout = new HorizontalLayout(nuevaVenta, nuevoAlquiler);
			filtering.addComponents(clearFilterTextBtn, layout);

		}

		filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		buildToolbar("Contratos", filtering);
		grid.setSizeFull();
		mainLayout = new HorizontalLayout(grid, ContratoVentaForm, ContratoAlquilerForm);
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
		contratos = service.findAll(filtro);
		grid.setItems(contratos);

	}

	public boolean isIsonMobile() {
		return isonMobile;
	}

	public void ClearFilterBtnAction() {
		if (this.ContratoVentaForm.isVisible()) {
			nuevaVenta.focus();
			ContratoVentaForm.cancel();

		} else if (this.ContratoAlquilerForm.isVisible()) {
			nuevoAlquiler.focus();
			ContratoAlquilerForm.cancel();
		}

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

}