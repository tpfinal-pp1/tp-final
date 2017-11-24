package com.TpFinal.view.publicacion;

import com.TpFinal.dto.contrato.ContratoAlquiler;
import com.TpFinal.dto.contrato.ContratoDuracion;
import com.TpFinal.dto.contrato.TipoInteres;
import com.TpFinal.dto.inmueble.Inmueble;
import com.TpFinal.dto.inmueble.TipoMoneda;
import com.TpFinal.dto.publicacion.*;
import com.TpFinal.services.ContratoDuracionService;
import com.TpFinal.services.InmuebleService;
import com.TpFinal.services.ParametrosSistemaService;
import com.TpFinal.services.ProvinciaService;
import com.TpFinal.services.PublicacionService;
import com.TpFinal.utils.Utils;
import com.TpFinal.view.component.BlueLabel;
import com.TpFinal.view.component.DeleteButton;
import com.vaadin.data.Binder;
import com.vaadin.data.BindingValidationStatus;
import com.vaadin.data.HasValue;
import com.vaadin.data.ValidationException;
import com.vaadin.data.ValueProvider;
import com.vaadin.data.converter.StringToBigDecimalConverter;
import com.vaadin.data.converter.StringToDoubleConverter;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.data.validator.DateRangeValidator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.SerializablePredicate;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.apache.log4j.Logger;

public class PublicacionForm extends FormLayout {

    private static final Logger logger = Logger.getLogger(PublicacionForm.class);

    Button save = new Button("Guardar");
    DateField fechaPublicacion = new DateField("Fecha publicacion");
    RadioButtonGroup<EstadoPublicacion> estadoPublicacion = new RadioButtonGroup<>("Estado de la publicacion",
	    EstadoPublicacion.toList());

    TextField nombrePropietario = new TextField();
    TextField monto = new TextField("Monto");
    ComboBox<TipoMoneda> moneda = new ComboBox<>("", TipoMoneda.toList());
    RadioButtonGroup<TipoPublicacion> tipoPublicacion = new RadioButtonGroup<>(" ", TipoPublicacion.toList());
    private Publicacion publicacion;
    PublicacionService service = new PublicacionService();
    private InmuebleService inmuebleService = new InmuebleService();
    private PublicacionABMView addressbookView;
    private Binder<Publicacion> binderPublicacion = new Binder<>();
    ComboBox<Inmueble> comboInmueble = new ComboBox<Inmueble>("Inmueble", new InmuebleService().readAll());

    // XXX refinamiento requerimientos
    private BlueLabel condiciones = new BlueLabel("Condiciones");
    private Binder<PublicacionAlquiler> binderAlquiler = new Binder<>();
    private ComboBox<ContratoDuracion> comboDuracion = new ComboBox<ContratoDuracion>("Duración",
	    new ContratoDuracionService().readAll());
    private TextField frecuenciaIncrementoCuota = new TextField("Frecuencia de incremento (meses)");
    private TextField aumentoPorActualizacion = new TextField("Aumento por actualización (%)");
    private ComboBox<TipoInteres> tipoInteres = new ComboBox<>("Tipo Interes", TipoInteres.toList());
    private TextField cantCertificados = new TextField("Cantidad de certificados");
    private TextField sellado = new TextField("Sellado (%)");
    // XXX

    DeleteButton delete = new DeleteButton("Eliminar",
	    VaadinIcons.WARNING, "Eliminar", "20%", new Button.ClickListener() {
		@Override
		public void buttonClick(Button.ClickEvent clickEvent) {
		    delete();
		}
	    });
    TabSheet tabSheet;

    public PublicacionForm(PublicacionABMView addressbook) {

	addressbookView = addressbook;
	configureComponents();
	binding();
	buildLayout();
    }

    private void configureComponents() {
	fechaPublicacion.setRangeStart(LocalDate.now());
	delete.setStyleName(ValoTheme.BUTTON_DANGER);
	save.addClickListener(e -> this.save());
	save.setStyleName(ValoTheme.BUTTON_PRIMARY);
	save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
	setVisible(false);
	nombrePropietario.setEnabled(false);

	comboInmueble.addValueChangeListener(e ->{	 
		if (e.getValue() != null) {
		    Inmueble i = e.getValue();
		    nombrePropietario.setValue(i.getPropietario().toString());
		    binderPublicacion.validate();
		    BigDecimal val = BigDecimal.ZERO;
		    if (i.getDireccion() != null && i.getDireccion().getProvincia() != null) {
			ProvinciaService ps = new ProvinciaService();
			val = ps.getSelladoFromProvincia(ps.getProvinciaFromString(i.getDireccion().getProvincia()));
			
		    }
		    sellado.setValue(val.toString());
		} else
		    nombrePropietario.setValue("");
	    }
	);
	tipoPublicacion.addValueChangeListener(e -> {
	    TipoPublicacion tipo = e.getValue();
	    if (tipo.equals(TipoPublicacion.Venta)) {
		publicacion = PublicacionService.InstanciaPublicacionVenta();
		publicacion.setInmueble(new Inmueble());
		cambiarVisibilidadCamposAlquiler(false);

	    } else if (tipo.equals(TipoPublicacion.Alquiler)) {
		publicacion = PublicacionService.InstanciaPublicacionAlquiler();
		publicacion.setInmueble(new Inmueble());
		cambiarVisibilidadCamposAlquiler(true);

	    }
	});
    }

    private void cambiarVisibilidadCamposAlquiler(boolean b) {
	this.condiciones.setVisible(b);
	this.comboDuracion.setVisible(b);
	this.aumentoPorActualizacion.setVisible(b);
	this.tipoInteres.setVisible(b);
	this.cantCertificados.setVisible(b);
	this.sellado.setVisible(b);
	this.frecuenciaIncrementoCuota.setVisible(b);
    }

    private void binding() {

	binderPublicacion.forField(estadoPublicacion)
		.asRequired("Seleccione el estado de la publicación")
		.withValidator(noMasDeUnaPublicacionPorTipo(this.publicacion),
			"No puede haber más de una publicación activa del mismo tipo asociada al inmueble!")
		.bind(Publicacion::getEstadoPublicacion,
			Publicacion::setEstadoPublicacion);

	binderPublicacion.forField(fechaPublicacion)
		.asRequired("Debe introducirse una fecha")
		.withValidator(new DateRangeValidator(
			"Debe celebrarse desde hoy en adelante", LocalDate.now(), LocalDate.now().plusDays(365)))
		.bind(Publicacion::getFechaPublicacion, Publicacion::setFechaPublicacion);

	binderPublicacion.forField(moneda).asRequired("Seleccione un tipo de moneda").bind(publicacion -> {

	    if (publicacion instanceof PublicacionAlquiler) {
		PublicacionAlquiler publiAl = (PublicacionAlquiler) publicacion;

		return publiAl.getMoneda();
	    } else if (publicacion instanceof PublicacionVenta) {
		PublicacionVenta publiAl = (PublicacionVenta) publicacion;

		return publiAl.getMoneda();
	    } else {

		throw new IllegalArgumentException("no se definió" +
			" el tipo de publicacion");
	    }

	}, (publicacion, tipo) -> {

	    if (publicacion instanceof PublicacionAlquiler) {
		PublicacionAlquiler publiAl = (PublicacionAlquiler) publicacion;

		publiAl.setMoneda(tipo);

	    } else if (publicacion instanceof PublicacionVenta) {
		PublicacionVenta publiAl = (PublicacionVenta) publicacion;

		publiAl.setMoneda(tipo);
	    }

	    else {
		throw new IllegalArgumentException("no se definió" +
			" el tipo de publicacion");
	    }
	});

	binderPublicacion.forField(monto).asRequired("Ingrese un valor mayor a 0").withValidator(new RegexpValidator(
		"Ingrese un valor mayor a 0",
		"^[1-9][0-9]|.*$")).withConverter(new StringToBigDecimalConverter("Ingrese un valor mayor a 0"))
		.withValidator(n -> {
		    return n.compareTo(BigDecimal.ZERO) > 0;
		}, "Debe ingresar un precio positivo")
		.bind(publicacion -> {

		    if (publicacion instanceof PublicacionAlquiler) {
			PublicacionAlquiler publiAl = (PublicacionAlquiler) publicacion;
			return publiAl.getValorCuota();
		    }

	    else if (publicacion instanceof PublicacionVenta) {
			PublicacionVenta publiAl = (PublicacionVenta) publicacion;

			return publiAl.getPrecio();
		    } else {
			throw new IllegalArgumentException("no se definió" +
				" el tipo de publicacion");
		    }

		}, (publicacion, cantidad) -> {

		    if (publicacion instanceof PublicacionAlquiler) {
			PublicacionAlquiler publiAl = (PublicacionAlquiler) publicacion;

			publiAl.setValorCuota(cantidad);
		    } else if (publicacion instanceof PublicacionVenta) {
			PublicacionVenta publiAl = (PublicacionVenta) publicacion;

			publiAl.setPrecio(cantidad);
		    }

	    else {

			throw new IllegalArgumentException("no se definió" +
				" el tipo de publicacion");
		    }
		});

	binderPublicacion.forField(this.comboInmueble).asRequired(
		"Debe seleccionar o cargar un inmueble de la publicación!")
		.bind(publicacion -> publicacion.getInmueble(), (publicacion, inmueble) -> publicacion.setInmueble(
			inmueble));

	binderAlquiler.forField(this.cantCertificados).asRequired(
		"Debe ingresar una cantidad de garantes para el contrato")
		.withConverter(new StringToIntegerConverter("Debe ingresar un número"))
		.withValidator(n -> n >= ParametrosSistemaService.getParametros().getCantMinimaCertificados(),
			"Debe ingresar al menos " + ParametrosSistemaService.getParametros().getCantMinimaCertificados()
				+ " certificados!")
		.bind(PublicacionAlquiler::getCantCertificadosGarantes,
			PublicacionAlquiler::setCantCertificadosGarantes);

	binderAlquiler.forField(this.comboDuracion).asRequired("Seleccione una Duración")
		.bind(PublicacionAlquiler::getDuracionContrato, PublicacionAlquiler::setDuracionContrato);

	binderAlquiler.forField(this.tipoInteres).asRequired("Seleccione un Tipo de Interes")
		.bind(PublicacionAlquiler::getTipoIncrementoCuota, PublicacionAlquiler::setTipoIncrementoCuota);

	binderAlquiler.forField(this.frecuenciaIncrementoCuota)
		.withConverter(new StringToIntegerConverter("Debe ingresar un número"))
		.asRequired(
			"Ingrese una frecuencia de incremento de la cuota")
		.withValidator(v -> {
		    ContratoDuracion d = this.comboDuracion.getValue();
		    if (d != null) {
			if (d.getDuracion() % v == 0)
			    return true;
		    }
		    return false;
		}, "El intervalo de actualización debe dividir a la duración del contrato!")
		.bind(PublicacionAlquiler::getIntervaloActualizacion, PublicacionAlquiler::setIntervaloActualizacion);

	binderAlquiler.forField(this.aumentoPorActualizacion).withNullRepresentation("")
		.withConverter(new StringToDoubleConverter("Debe ingresar un número"))
		.withValidator(n -> (n >= 0 && n <= 100), "Ingrese un porcentaje entre 0 y 100")
		.bind(PublicacionAlquiler::getPorcentajeIncrementoCuota,
			PublicacionAlquiler::setPorcentajeIncrementoCuota);

    }

    private SerializablePredicate<? super EstadoPublicacion> noMasDeUnaPublicacionPorTipo(Publicacion publicacion2) {
	return estado -> {
	    logger.debug("Publicacion: " + publicacion);
	    if (estado == EstadoPublicacion.Activa && comboInmueble.getValue() != null && publicacion
		    .getTipoPublicacion() != null) {
		if (publicacion != null && publicacion.getId() != null) {
		    if (publicacion.getEstadoPublicacion() == EstadoPublicacion.Activa)
			return true;
		}
		return !inmuebleService.inmueblePoseePubActivaDeTipo(comboInmueble.getValue(), publicacion
			.getTipoPublicacion());
	    } else
		return true;
	};
    }

    private void buildLayout() {
	comboDuracion.setEmptySelectionAllowed(false);
	tipoInteres.setEmptySelectionAllowed(false);
	sellado.setEnabled(false);

	comboInmueble.addStyleName(ValoTheme.COMBOBOX_BORDERLESS);

	moneda.addStyleName(ValoTheme.COMBOBOX_BORDERLESS);
	monto.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);

	moneda.setCaption("Moneda");
	moneda.setEmptySelectionAllowed(false);

	tabSheet = new TabSheet();
	// fechaPublicacion.setWidth("40%");
	// moneda.setWidth("30%");
	nombrePropietario.setCaption("Propietario: ");
	FormLayout principal = new FormLayout(tipoPublicacion, fechaPublicacion, estadoPublicacion,
		new BlueLabel("Inmueble"), comboInmueble, nombrePropietario, monto, moneda, condiciones, comboDuracion,
		frecuenciaIncrementoCuota,
		aumentoPorActualizacion, tipoInteres, cantCertificados, sellado);
	principal.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
	tabSheet.addTab(principal, "Publicacion");

	addComponent(tabSheet);
	HorizontalLayout actions = new HorizontalLayout(save, delete);
	addComponent(actions);
	actions.setSpacing(true);

    }

    // TODO comment
    public void setPublicacion(Publicacion Publicacion) {

	this.publicacion = Publicacion;

	if (Publicacion != null) {
	    if (Publicacion instanceof PublicacionVenta) {
		tabSheet.getTab(0).setCaption("Venta");
		binderPublicacion.readBean((PublicacionVenta) Publicacion);
		cambiarVisibilidadCamposAlquiler(false);
	    } else if (Publicacion instanceof PublicacionAlquiler) {
		tabSheet.getTab(0).setCaption("Alquiler");
		binderPublicacion.readBean((PublicacionAlquiler) Publicacion);
		binderAlquiler.readBean((PublicacionAlquiler) Publicacion);
		cambiarVisibilidadCamposAlquiler(true);
		
	    }

	    delete.setVisible(true);
	    tipoPublicacion.setEnabled(false);
	    tipoPublicacion.setVisible(false);

	} else {
	    tabSheet.getTab(0).setCaption("Publicación");
	    tipoPublicacion.setVisible(true);
	    tipoPublicacion.setEnabled(true);
	    delete.setVisible(false);
	    tipoPublicacion.setRequiredIndicatorVisible(true);
	    tipoPublicacion.setValue(TipoPublicacion.Alquiler);
	    publicacion = PublicacionService.InstanciaPublicacionAlquiler();
	    cambiarVisibilidadCamposAlquiler(true);
	    binderPublicacion.getFields().forEach(field -> field.clear());
	    binderAlquiler.getFields().forEach(field -> field.clear());
	    fechaPublicacion.setValue(LocalDate.now());
	    fechaPublicacion.setParseErrorMessage("Fecha no reconocida");
	    fechaPublicacion.setDateOutOfRangeMessage("Debe seleccionar una fecha de hoy en adelante");
	    moneda.setSelectedItem(TipoMoneda.Pesos);
	    estadoPublicacion.setSelectedItem(EstadoPublicacion.Activa);
	    this.cantCertificados.setValue(ParametrosSistemaService.getParametros().getCantMinimaCertificados().toString());
	    // Por defecto en alquiler para evitar problemas
	}

	getAddressbookView().setComponentsVisible(false);

	if (getAddressbookView().isIsonMobile())
	    tabSheet.focus();

	setVisible(true);

    }

    private void delete() {

	service.delete(publicacion);
	addressbookView.updateList();
	setVisible(false);
	getAddressbookView().setComponentsVisible(true);
	getAddressbookView().showSuccessNotification("Borrado");
	getAddressbookView().enableGrid();

    }

    private void save() {

	boolean success = false;
	try {
	    binderPublicacion.validate();
	    binderPublicacion.writeBean(publicacion);

	    success = service.save(publicacion);

	} catch (ValidationException e) {
	    Utils.mostarErroresValidator(e);
	    printInvalidComponents(e.getFieldValidationErrors());
	    Notification.show("Errores de validación, por favor revise los campos.", Notification.Type.WARNING_MESSAGE);
	    return;
	} catch (Exception e) {
	    e.printStackTrace();
	    Notification.show("Error: " + e.toString());
	}

	setVisible(false);
	getAddressbookView().setComponentsVisible(true);

	if (success) {
	    getAddressbookView().showSuccessNotification("Guardado");
	    addressbookView.updateList();
	}

    }

    private void printInvalidComponents(List<BindingValidationStatus<?>> invalidComponents) {
	for (BindingValidationStatus invalidField : invalidComponents) {
	    logger.debug(invalidField.getMessage() + invalidField.getStatus().toString());
	}
    }

    public void cancel() {
	addressbookView.updateList();
	setVisible(false);
	getAddressbookView().setComponentsVisible(true);
	// getAddressbookView().enableGrid();
    }

    public PublicacionABMView getAddressbookView() {
	return addressbookView;
    }

    public void clearFields() {
	this.estadoPublicacion.clear();
	this.fechaPublicacion.clear();
	this.moneda.clear();
	this.nombrePropietario.clear();
	this.monto.clear();
	this.comboInmueble.clear();
	binderAlquiler.getFields().forEach(c -> c.clear());
    }

}
