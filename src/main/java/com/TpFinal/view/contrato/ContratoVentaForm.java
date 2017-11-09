package com.TpFinal.view.contrato;

import com.TpFinal.dto.contrato.Contrato;
import com.TpFinal.dto.contrato.ContratoVenta;
import com.TpFinal.dto.contrato.EstadoContrato;
import com.TpFinal.dto.inmueble.EstadoInmueble;
import com.TpFinal.dto.inmueble.Inmueble;
import com.TpFinal.dto.inmueble.TipoMoneda;
import com.TpFinal.dto.persona.Persona;
import com.TpFinal.dto.publicacion.PublicacionVenta;
import com.TpFinal.services.ContratoService;
import com.TpFinal.services.InmuebleService;
import com.TpFinal.services.PersonaService;
import com.TpFinal.utils.Utils;
import com.TpFinal.view.component.*;
import com.vaadin.data.*;
import com.vaadin.data.converter.StringToBigDecimalConverter;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.io.File;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* Create custom UI Components.
 *
 * Create your own Vaadin components by inheritance and composition.
 * This is a form component inherited from VerticalLayout. Use
 * Use BeanFieldGroup to binding data fields from DTO to UI fields.
 * Similarly named field by naming convention or customized
 * with @PropertyId annotation.
 */
public class ContratoVentaForm extends FormLayout {
    private ContratoVenta contratoVenta;
    private InmuebleService inmuebleService = new InmuebleService();

    // Actions
    EstadoCargaDocumento estadoCargaDocumento = EstadoCargaDocumento.NoCargado;
    Button save = new Button("Guardar");
    DeleteButton delete = new DeleteButton("Eliminar",
	    VaadinIcons.WARNING, "Eliminar", "20%", e -> delete());
    Button finalizarCarga = new Button("Finalizar Carga");
    Button renovarContrato = new Button("Renovar");

    // TabPrincipal
    ComboBox<Inmueble> cbInmuebles = new ComboBox<>("Inmueble");

    Label lblNombreVendedor = new Label("No seleccionado");
    ComboBox<Persona> cbComprador = new ComboBox<>("Comprador");
    DateField fechaIngreso = new DateField("Fecha de Ingreso");
    DateField fechaCelebracion = new DateField("Fecha de Celebracion");

    // Documento
    public String nombreArchivo = "";
    TextField tfDocumento = new TextField();
    File archivo;

    DownloadButton btDescargar = new DownloadButton();
    UploadReceiver uploadReceiver = new UploadReceiver();
    UploadButton btCargar = new UploadButton(uploadReceiver);
    
//    UploadButton btCargar = new UploadButton(new AbstractUploadReceiver() {
//
//	@Override
//	public void onSuccessfullUpload(String filename) {
//	    nombreArchivo = filename;
//	    tfDocumento.setValue("Documento Cargado");
//	   // btDescargar.setFile(filename);
//	    archivo = new File(this.getPathAndName());
//	}
//    });

    TextField tfPrecioDeVenta = new TextField("Valor de venta $");
    RadioButtonGroup<TipoMoneda> rbgTipoMoneda = new RadioButtonGroup<>("Tipo Moneda", TipoMoneda.toList());

    ContratoService service = new ContratoService();
    private ContratoABMView contratoABMView;
    private Binder<ContratoVenta> binderContratoVenta = new Binder<>(ContratoVenta.class);

    TabSheet tabSheet;

    Persona person; // TODO ver que hacer con persona

    // Easily binding forms to beans and manage validation and buffering

    public ContratoVentaForm(ContratoABMView addressbook) {
	// setSizeUndefined();
	contratoABMView = addressbook;
	configureComponents();
	binding();
	buildLayout();
	updateComboInmuebles();
	updateComboCompradores();
	addStyleName("v-scrollable");
    }

    private void configureComponents() {
	configurarAcciones();

	btCargar.addStartedListener(e -> {
		tfDocumento.setIcon(VaadinIcons.UPLOAD);
		tfDocumento.setValue("Cargando documento...");
		estadoCargaDocumento = EstadoCargaDocumento.Cargando;
	});
	btCargar.addFailedListener(e -> {
		tfDocumento.setIcon(VaadinIcons.WARNING);
		tfDocumento.setValue("Error al Cargar el documento");
		estadoCargaDocumento = EstadoCargaDocumento.FalloLaCarga;
	});
	btCargar.addSucceededListener(e -> {
		tfDocumento.setIcon(VaadinIcons.CHECK_CIRCLE);
		tfDocumento.setValue("Documento Cargado");
		estadoCargaDocumento = EstadoCargaDocumento.Cargado;
		contratoVenta.getArchivo().setExtension(uploadReceiver.getFileName());
		contratoVenta.getArchivo().setNombre(uploadReceiver.getFileName());
		archivo = new File(uploadReceiver.getFullPath());
		try {
		    uploadReceiver.getOutputFile().flush();
		    uploadReceiver.getOutputFile().close();
			btDescargar.setArchivoFromPath(archivo.getPath(),contratoVenta.getArchivo().getNombre()+contratoVenta.getArchivo().getExtension());
		} catch (Exception ex) {
		}
	});

	cbInmuebles.addValueChangeListener(new HasValue.ValueChangeListener<Inmueble>() {
	    @Override
	    public void valueChange(HasValue.ValueChangeEvent<Inmueble> valueChangeEvent) {
		if (valueChangeEvent != null) {
		    Inmueble inmueble = (Inmueble) valueChangeEvent.getValue();
		    if (inmueble == null) {
			contratoVenta.setInmueble(null);
			lblNombreVendedor.setValue("No seleccionado");
		    } else {
			Persona vendedor = inmueble.getPropietario().getPersona();
			lblNombreVendedor.setValue(vendedor.getNombre() + " " + vendedor.getApellido());
			contratoVenta.setInmueble(inmueble);
			contratoVenta.setVendedor(vendedor);
			PublicacionVenta asociado = service.getPublicacionVentaActiva(inmueble);
			if (asociado != null) {
			    contratoVenta.setPrecioVenta(asociado.getPrecio());
			    contratoVenta.setMoneda(asociado.getMoneda());
			    binderContratoVenta.readBean(contratoVenta);
			}
		    }
		}
	    }
	});

	cbComprador.addValueChangeListener(new HasValue.ValueChangeListener<Persona>() {
	    @Override
	    public void valueChange(HasValue.ValueChangeEvent<Persona> valueChangeEvent) {
		if (valueChangeEvent != null) {
		    Persona comprador = (Persona) valueChangeEvent.getValue();
		    if (comprador != null) {
			contratoVenta.setComprador(comprador);
		    } else
			contratoVenta.setComprador(null);
		}
	    }
	});

	btDescargar.addClickListener(event -> {
	    btDescargar.descargar(contratoVenta.getArchivo());
	});

	setVisible(false);

    }

    private void configurarAcciones() {
	delete.setStyleName(ValoTheme.BUTTON_DANGER);
	save.setStyleName(ValoTheme.BUTTON_PRIMARY);
	save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

	finalizarCarga.setStyleName(ValoTheme.BUTTON_FRIENDLY);
	renovarContrato.setStyleName(ValoTheme.BUTTON_FRIENDLY);
	save.addClickListener(e -> {
	    this.binderContratoVenta = getBinderParaEdicion();
	    this.save();
	    binderContratoVenta.validate();
	});
	finalizarCarga.addClickListener(e -> {
	    this.binderContratoVenta = getBinderParaFinalizacionDeCarga();
	    if (estadoCargaDocumento.equals(EstadoCargaDocumento.Cargado) && binderContratoVenta.isValid()) {
		contratoVenta.setEstadoContrato(EstadoContrato.Vigente);
	    } else {
		tfDocumento.setValue("Cargue un documento.");
	    }
	    this.save();
	    binderContratoVenta.validate();
	});
    }

    private void binding() {
	binderContratoVenta = getBinderParaEdicion();
    }

    private Binder<ContratoVenta> getBinderParaEdicion() {
	Binder<ContratoVenta> binderContratoVenta = new Binder<>(ContratoVenta.class);

	binderContratoVenta.forField(fechaIngreso).asRequired("Seleccione una fecha de ingreso").bind(
		Contrato::getFechaIngreso,
		Contrato::setFechaIngreso);
	binderContratoVenta.forField(fechaCelebracion).asRequired("Seleccione una fecha celebracion").bind(
			Contrato::getFechaCelebracion,
			Contrato::setFechaCelebracion);
	binderContratoVenta.forField(rbgTipoMoneda).asRequired("Seleccione un tipo de moneda").bind("moneda");
	binderContratoVenta.forField(cbInmuebles).asRequired("Debe Ingresar un inmueble")
		.withValidator(inmueble -> {
		    return !inmuebleService.inmueblePoseeContratoVigente(inmueble);
		},
			"El inmueble seleccionado ya posee un contrato vigente.")
		.bind(Contrato::getInmueble, Contrato::setInmueble);

	Validator<Persona> personaValidator = new Validator<Persona>() {
	    @Override
	    public ValidationResult apply(Persona persona, ValueContext valueContext) {
		ValidationResult result = new ValidationResult() {
		    @Override
		    public String getErrorMessage() {
			if (contratoVenta.getInmueble().getId() != null)
			    if (contratoVenta.getInmueble().getPropietario().getPersona().getId().equals(persona
				    .getId()))
				return "Error: No se puede seleccionar al vendedor como comprador";
			return "No se detectaron errores";
		    }

		    @Override
		    public boolean isError() {
			if (contratoVenta.getInmueble().getId() != null)
			    if (persona != null)
				if (contratoVenta.getInmueble().getPropietario().getPersona().getId().equals(persona
					.getId()))
				    return true;
			return false;
		    }
		};
		return result;
	    }
	};
	binderContratoVenta.forField(cbComprador).asRequired("Debe seleccionar un comprador").withValidator(
		personaValidator)
		.withNullRepresentation(new Persona())
		.bind(contratoVenta -> contratoVenta.getComprador(), (contratoVenta, persona) -> contratoVenta
			.setComprador(persona));

	/*
	 * binderContratoVenta.forField(lblVendedor) .withNullRepresentation("") .bind(
	 * (inmueble) -> inmueble.getVendedor().getNombre(), (inmueble,nombre) ->
	 * inmueble.getVendedor().setNombre(nombre));
	 */

	binderContratoVenta.forField(tfPrecioDeVenta)
		.withNullRepresentation("")
		.withConverter(new StringToBigDecimalConverter("Ingrese un numero"))
		.withValidator(n -> {
		    return (n.compareTo(BigDecimal.ZERO) > 0);
		}, "Debe Ingresar un Valor Positivo")
		.asRequired("Debe Ingresar un Precio de Venta")
		.bind("precioVenta");

	binderContratoVenta.forField(this.tfDocumento).withNullRepresentation("")
		.bind(contrato -> {
		    if (contrato.getDocumento() != null) {
			estadoCargaDocumento = EstadoCargaDocumento.Cargado;
			return "Documento Cargado";
		    } else {
			estadoCargaDocumento = EstadoCargaDocumento.NoCargado;
			return "Documento No Cargado";
		    }
		}, (contrato, text) -> {
		});
	return binderContratoVenta;
    }

    private Binder<ContratoVenta> getBinderParaFinalizacionDeCarga() {
	binderContratoVenta = getBinderParaEdicion();
	binderContratoVenta.forField(this.tfDocumento)
		.asRequired("Debe Cargar al menos un documento antes de finalizar la carga.")
		.withValidator(text -> text == "Documento Cargado",
			"Debe Cargar al menos un documento antes de finalizar la carga.")
		.bind(contrato -> {
		    if (contrato.getDocumento() != null)
			return "Documento Cargado";
		    return "Documento No Cargado";
		}, (contrato, text) -> {
		});
	return binderContratoVenta;

    }

    private void updateComboInmuebles() {
	InmuebleService is = new InmuebleService();
	cbInmuebles.setItems(is.readAll());
    }

    private void updateComboCompradores() {
	PersonaService ps = new PersonaService();
	cbComprador.setItems(ps.readAll());
    }

    private void buildLayout() {
	setSizeFull();

	tabSheet = new TabSheet();

	BlueLabel seccionDoc = new BlueLabel("Documento Word");
	//
	// TinyButton personas = new TinyButton("Ver Personas");
	//
	// personas.addClickListener(e -> getPersonaSelector());
	//
	// VerticalLayout Roles = new VerticalLayout(personas);
	//
	// fechaCelebracion.setWidth("100");

	if (this.contratoABMView.checkIfOnMobile())
	    rbgTipoMoneda.addStyleName(ValoTheme.OPTIONGROUP_SMALL);

	else
	    rbgTipoMoneda.addStyleName(ValoTheme.OPTIONGROUP_HORIZONTAL);

	HorizontalLayout documentoButtonsRow = new HorizontalLayout();
	documentoButtonsRow.addComponents(btCargar, btDescargar);
	documentoButtonsRow.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
	tfDocumento.setCaption("Estado Documento");
	btCargar.setStyleName(ValoTheme.BUTTON_BORDERLESS);
	btDescargar.setStyleName(ValoTheme.BUTTON_BORDERLESS);

	BlueLabel info = new BlueLabel("Información Adicional");

	HorizontalLayout hl = new HorizontalLayout(lblNombreVendedor);
	hl.setCaption("Vendedor");
	FormLayout principal = new FormLayout(cbInmuebles, cbComprador,fechaCelebracion, fechaIngreso, hl, tfPrecioDeVenta,
		rbgTipoMoneda, seccionDoc,
		tfDocumento,
		documentoButtonsRow);

	principal.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);

	fechaIngreso.setWidth("100");
	fechaCelebracion.setWidth("100");

	tabSheet.addTab(principal, "Venta");

	addComponent(tabSheet);
	HorizontalLayout actions = new HorizontalLayout(save, delete, finalizarCarga, renovarContrato);

	actions.setSpacing(true);
	addComponent(actions);
	this.setSpacing(false);
    }

    public void setContratoVenta(ContratoVenta contratoVenta) {

	if (contratoVenta != null) {
	    configurarComponentesSegunEstadoContrato(contratoVenta.getEstadoContrato());
	    this.contratoVenta = contratoVenta;
	    binderContratoVenta.readBean(contratoVenta);
	} else {
	    this.contratoVenta = ContratoService.getInstanciaVenta();
	    configurarComponentesSegunEstadoContrato(this.contratoVenta.getEstadoContrato());
	}

	setVisible(true);
	contratoABMView().setComponentsVisible(false);
	if (contratoABMView().isIsonMobile())
	    tabSheet.focus();

    }

    /**
     * Configura el modo de edición de los componentes segun el estado del contrato.
     * <p>
     * <Strong>En proceso de carga: </Strong>Se permite libre edición, borrado y
     * finalización de carga. No se permite renovación.
     * <p>
     * <Strong>Vigente, Próximo a vencer y vencido: </Strong>Se deshabilita la
     * edición, borrado y finalización de carga. Se permite renovación.
     * 
     * @param estadoContrato
     */
    private void configurarComponentesSegunEstadoContrato(EstadoContrato estadoContrato) {
	tfDocumento.setEnabled(false);
	if (estadoContrato == EstadoContrato.EnProcesoDeCarga) {
	    binderContratoVenta = getBinderParaEdicion();
	    this.save.setVisible(true);
	    this.delete.setVisible(true);
	    this.finalizarCarga.setVisible(true);
	    this.renovarContrato.setVisible(false);
	    this.btCargar.setEnabled(true);
	    this.btDescargar.setEnabled(true);
	    this.cbComprador.setEnabled(true);
	    this.cbInmuebles.setEnabled(true);
	    this.fechaIngreso.setEnabled(true);
	    this.fechaCelebracion.setEnabled(true);
	    this.rbgTipoMoneda.setEnabled(true);
	    this.tfDocumento.setEnabled(false);
	    this.tfPrecioDeVenta.setEnabled(true);
	} else if (estadoContrato == EstadoContrato.Vencido) {
	    binderContratoVenta = getBinderParaFinalizacionDeCarga();
	    this.save.setVisible(false);
	    this.delete.setVisible(false);
	    this.finalizarCarga.setVisible(false);
	    this.renovarContrato.setVisible(false);
	    this.btCargar.setEnabled(false);
	    this.btDescargar.setEnabled(true);
	    this.cbComprador.setEnabled(false);
	    this.cbInmuebles.setEnabled(false);
	    this.fechaIngreso.setEnabled(false);
	    this.fechaCelebracion.setEnabled(false);
	    this.rbgTipoMoneda.setEnabled(false);
	    this.tfDocumento.setEnabled(false);
	    this.tfPrecioDeVenta.setEnabled(false);
	} else {
	    binderContratoVenta = getBinderParaFinalizacionDeCarga();
	    this.save.setVisible(false);
	    this.delete.setVisible(false);
	    this.finalizarCarga.setVisible(false);
	    this.renovarContrato.setVisible(false);
	    this.btCargar.setEnabled(false);
	    this.btDescargar.setEnabled(true);
	    this.cbComprador.setEnabled(false);
	    this.cbInmuebles.setEnabled(false);
	    this.fechaIngreso.setEnabled(false);
	    this.fechaCelebracion.setEnabled(false);
	    this.rbgTipoMoneda.setEnabled(false);
	    this.tfDocumento.setEnabled(false);
	    this.tfPrecioDeVenta.setEnabled(false);

	}

    }

    private void delete() {
	boolean success = service.delete(contratoVenta);
	contratoABMView.updateList();
	setVisible(false);
	contratoABMView().setComponentsVisible(true);
	if (success) {
	    contratoABMView().showSuccessNotification("Borrado");
	} else {
	    contratoABMView().showErrorNotification("No se realizaron Cambios");
	}
    }

    private void save() {

	boolean success = false;
	try {
	    binderContratoVenta.writeBean(contratoVenta);
	    if (contratoVenta.getEstadoContrato() == EstadoContrato.Vigente)
		contratoVenta.getInmueble().setEstadoInmueble(EstadoInmueble.Vendido);

	    if (archivo != null && !archivo.exists())
		success = service.saveOrUpdate(contratoVenta, null);
	    else {
		success = service.saveOrUpdate(contratoVenta, archivo);
	    }

	    contratoABMView.updateList();
	    setVisible(false);
	    contratoABMView().setComponentsVisible(true);

	    if (success)
		contratoABMView().showSuccessNotification("Guardado");
	    else {
		contratoABMView().showErrorNotification("Fallo al guardar");
	    }

	} catch (ValidationException e) {
	    Utils.mostarErroresValidator(e);
	    Notification.show("Error al guardar, por favor revise los campos e intente de nuevo.",
		    Notification.Type.WARNING_MESSAGE);
	} catch (Exception e) {
	    e.printStackTrace();
	    Notification.show("Error: " + e.toString());
	}

    }

    public void cancel() {
	contratoABMView.updateList();
	setVisible(false);
	contratoABMView().setComponentsVisible(true);
    }

    public ContratoABMView contratoABMView() {
	return contratoABMView;
    }

    private void getPersonaSelector() {
	VentanaSelectora<Persona> personaSelector = new VentanaSelectora<Persona>(person) {
	    @Override
	    public void updateList() {
		PersonaService PersonaService = new PersonaService();
		List<Persona> Personas = PersonaService.readAll();
		Collections.sort(Personas, new Comparator<Persona>() {

		    @Override
		    public int compare(Persona o1, Persona o2) {
			return (int) (o2.getId() - o1.getId());
		    }
		});
		grid.setItems(Personas);
	    }

	    @Override
	    public void setGrid() {
		grid = new Grid<>(Persona.class);
	    }

	    @Override
	    public void seleccionado(Persona objeto) {
		person = objeto;
	    }

	};
	personaSelector.getSelectionButton().addClickListener(e -> person = personaSelector.getObjeto());
    }

    public void clearFields() {
	this.cbComprador.clear();
	this.cbInmuebles.clear();
	this.fechaIngreso.clear();
	this.fechaCelebracion.clear();
	this.lblNombreVendedor.setCaption("");
	this.rbgTipoMoneda.clear();
	this.tfDocumento.clear();
	this.tfPrecioDeVenta.clear();
    }

}