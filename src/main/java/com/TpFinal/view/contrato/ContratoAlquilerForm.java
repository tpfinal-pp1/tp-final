package com.TpFinal.view.contrato;

import com.TpFinal.dto.contrato.Contrato;
import com.TpFinal.dto.contrato.ContratoAlquiler;
import com.TpFinal.dto.contrato.ContratoDuracion;
import com.TpFinal.dto.contrato.EstadoContrato;
import com.TpFinal.dto.contrato.TipoInteres;
import com.TpFinal.dto.inmueble.EstadoInmueble;
import com.TpFinal.dto.inmueble.Inmueble;
import com.TpFinal.dto.inmueble.TipoMoneda;
import com.TpFinal.dto.persona.Calificacion;
import com.TpFinal.dto.persona.Inquilino;
import com.TpFinal.dto.persona.Persona;
import com.TpFinal.dto.persona.Rol;
import com.TpFinal.dto.publicacion.PublicacionAlquiler;
import com.TpFinal.exceptions.services.ContratoServiceException;
import com.TpFinal.services.ContratoDuracionService;
import com.TpFinal.services.ContratoService;
import com.TpFinal.services.InmuebleService;
import com.TpFinal.services.PersonaService;
import com.TpFinal.utils.Utils;
import com.TpFinal.view.component.*;
import com.vaadin.data.Binder;
import com.vaadin.data.BindingValidationStatus;
import com.vaadin.data.HasValue;
import com.vaadin.data.ValidationException;
import com.vaadin.data.converter.StringToBigDecimalConverter;
import com.vaadin.data.converter.StringToDoubleConverter;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;
import org.vaadin.risto.stepper.IntStepper;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
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
public class ContratoAlquilerForm extends FormLayout {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private ContratoAlquiler contratoAlquiler;

    // Actions
    Button save = new Button("Guardar");
    DeleteButton delete = new DeleteButton("Eliminar",
	    VaadinIcons.WARNING, "Eliminar", "20%", e -> delete());
    Button finalizarCarga = new Button("Finalizar Carga");
    Button renovarContrato = new Button("Renovar");

    // TabPrincipal
    ComboBox<Inmueble> cbInmuebles = new ComboBox<>("Inmueble");
    TextField tfPropietario = new TextField("Propietario");
    ComboBox<Persona> cbInquilino = new ComboBox<>("Inquilino");
    DateField fechaCelebracion = new DateField("Fecha de Celebracion");
    DateField fechaVencimiento = new DateField("Fecha de Vencimiento");

    public String nombreArchivo = "";
    File archivo;

    // Documento
    TextField tfDocumento = new TextField();
    UploadButton btCargar = new UploadButton(new UploadReceiver() {

	@Override
	public void onSuccessfullUpload(String filename) {
	    nombreArchivo = filename;
	    tfDocumento.setValue("Documento Cargado");
	    btDescargar.setFile(filename);
	    archivo = new File(this.getPathAndName());
	}
    });

    DownloadButton btDescargar = new DownloadButton();

    // Condiciones
    TextField tfDiaDePago = new TextField("Día de Pago");
    TextField tfPagoFueraDeTermino = new TextField("Recargo Punitorio( % )");
    ComboBox<TipoInteres> cbInteresFueraDeTermino = new ComboBox<>("Tipo Interes");
    ComboBox<ContratoDuracion> cbDuracionContrato = new ComboBox<>("Duración");
    TextField stIncremento = new TextField("Frecuencia de Incremento");

    TextField tfPActualizacion = new TextField("(%)");
    ComboBox<TipoInteres> cbtipointeres = new ComboBox<>("Tipo Interes");
    TextField tfValorInicial = new TextField("Valor Inicial $");
    RadioButtonGroup<TipoMoneda> rbgTipoMoneda = new RadioButtonGroup<>("Tipo Moneda", TipoMoneda.toList());

    ContratoService service = new ContratoService();
    InmuebleService inmuebleService = new InmuebleService();
    PersonaService personaService = new PersonaService();
    ContratoDuracionService contratoDuracionService = new ContratoDuracionService();
    private ContratoABMView contratoABMView;
    private Binder<ContratoAlquiler> binderContratoAlquiler = new Binder<>(ContratoAlquiler.class);
    Persona person = new Persona(); // TODO ver donde se usa persona p.

    // Tabsheet
    FormLayout principal;
    FormLayout condiciones;
    TabSheet tabSheet;

    public ContratoAlquilerForm(ContratoABMView addressbook) {
	contratoABMView = addressbook;
	configureComponents();
	binding();
	buildLayout();
	addStyleName("v-scrollable");
    }

    private void configureComponents() {
	tfPropietario.setEnabled(false);
	cbDuracionContrato.setItems(contratoDuracionService.readAll());
	cbInmuebles.setItems(inmuebleService.readAll());
	cbInquilino.setItems(personaService.readAll());
	cbInteresFueraDeTermino.setItems(TipoInteres.toList());
	cbtipointeres.setItems(TipoInteres.toList());

	configurarAcciones();

	cbInmuebles.addValueChangeListener(new HasValue.ValueChangeListener<Inmueble>() {
	    @Override
	    public void valueChange(HasValue.ValueChangeEvent<Inmueble> valueChangeEvent) {
		if (valueChangeEvent != null) {
		    Inmueble inmueble = (Inmueble) valueChangeEvent.getValue();
		    if (inmueble == null) {
			contratoAlquiler.setInmueble(null);
			tfPropietario.setValue("No seleccionado");
		    } else {
			Persona propietario = inmueble.getPropietario().getPersona();
			tfPropietario.setValue(propietario.getNombre() + " " + propietario.getApellido());
			contratoAlquiler.setInmueble(inmueble);
			contratoAlquiler.setPropietario(propietario);
			PublicacionAlquiler asociado = service.getPublicacionAlquilerActiva(inmueble);
			if (asociado != null) {
			    contratoAlquiler.setValorInicial(asociado.getValorCuota());
			    contratoAlquiler.setMoneda(asociado.getMoneda());
			    binderContratoAlquiler.readBean(contratoAlquiler);
			}
		    }
		}
	    }
	});

	cbInquilino.addValueChangeListener(new HasValue.ValueChangeListener<Persona>() {
	    @Override
	    public void valueChange(HasValue.ValueChangeEvent<Persona> valueChangeEvent) {
		if (valueChangeEvent != null) {
		    Persona inquilino = (Persona) valueChangeEvent.getValue();
		    if (inquilino != null) {
			if (inquilino.getRol(Rol.Inquilino) != null) {
			    contratoAlquiler.setInquilinoContrato((Inquilino) inquilino.getRol(Rol.Inquilino));
			}
			else {
			    Inquilino i = new Inquilino.Builder().setCalificacion(Calificacion.C)
				    .setPersona(inquilino).build();
			    i.getContratos().add(contratoAlquiler);
			    inquilino.addRol(i);
			}
		    } else
			contratoAlquiler.setInquilinoContrato(null);
		}
	    }
	});
	
	fechaCelebracion.addValueChangeListener(event ->{
		if(event.isUserOriginated()) {
			if(fechaCelebracion.getValue()!=null && cbDuracionContrato.getValue() !=null) {
				fechaVencimiento.setValue(fechaCelebracion.getValue().plusMonths(cbDuracionContrato.getValue().getDuracion()));
			}
		}
	});
	
	cbDuracionContrato.addValueChangeListener(event ->{
		if(event.isUserOriginated()) {
			if(fechaCelebracion.getValue()!=null && cbDuracionContrato.getValue() !=null) {
				fechaVencimiento.setValue(fechaCelebracion.getValue().plusMonths(cbDuracionContrato.getValue().getDuracion()));
			}
		}
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
	    this.binderContratoAlquiler = getBinderParaEdicion();
	    this.save();
	    binderContratoAlquiler.validate();
	});
	finalizarCarga.addClickListener(renovarContrato());
	renovarContrato.addClickListener(e -> {
	    this.binderContratoAlquiler = getBinderParaEdicion();
	    this.setContratoAlquiler(contratoAlquiler.clone());

	});
    }

    private ClickListener renovarContrato() {
	return e -> {
	    this.binderContratoAlquiler = getBinderParaFinalizacionDeCarga();
	    if (binderContratoAlquiler.isValid()) {
		binderContratoAlquiler.writeBeanIfValid(contratoAlquiler);
		contratoAlquiler.setEstadoContrato(EstadoContrato.Vigente);
		service.addCobros(contratoAlquiler);
		this.save();
		
	    } else {
		tfDocumento.setValue("Cargue un documento.");
		binderContratoAlquiler.validate();
	    }

	};
    }

    private void binding() {
	binderContratoAlquiler = getBinderParaEdicion();
    }

    private Binder<ContratoAlquiler> getBinderParaEdicion() {
	Binder<ContratoAlquiler> binderContratoAlquiler = new Binder<>(ContratoAlquiler.class);
	binderContratoAlquiler.forField(this.fechaCelebracion).asRequired("Seleccione una fecha de celebración")
		.bind(Contrato::getFechaCelebracion, Contrato::setFechaCelebracion);
	
	binderContratoAlquiler.forField(this.fechaVencimiento)
		.bind(c -> {
			return c.getFechaCelebracion().plusMonths(c.getDuracionContrato().getDuracion());
		}, (c,ca) -> {});

	binderContratoAlquiler.forField(this.cbDuracionContrato).asRequired("Seleccione una Duración")
		.bind(ContratoAlquiler::getDuracionContrato, ContratoAlquiler::setDuracionContrato);

	binderContratoAlquiler.forField(this.cbInmuebles).asRequired("Seleccione un Inmueble")
		.withValidator(inmueble -> {
		    return !inmuebleService.inmueblePoseeContratoVigente(inmueble);
		},
			"El inmueble seleccionado ya posee un contrato vigente.")
		.bind(ContratoAlquiler::getInmueble, ContratoAlquiler::setInmueble);

	binderContratoAlquiler.forField(this.cbInquilino).asRequired("Seleccione una persona")
		.withNullRepresentation(new Persona())
		.withValidator(p -> p.equals(contratoAlquiler.getPropietario()) == false,
			"No pueden ser la misma persona")
		.bind(contrato -> contrato.getInquilinoContrato().getPersona(),
			(contrato, persona) -> {
			    Inquilino i;
			    if (persona.contiene(Rol.Inquilino)) {
				i = (Inquilino) persona.getRol(Rol.Inquilino);
				i.getContratos().add(contrato);
			    } else {
				i = new Inquilino.Builder()
					.setCalificacion(Calificacion.C)
					.setPersona(persona)
					.build();
			    }
			    i.getContratos().add(contrato);
			    contrato.setInquilinoContrato(i);
			});
	binderContratoAlquiler.forField(this.cbInteresFueraDeTermino).asRequired("Seleccione un Tipo de Interes")
		.withNullRepresentation(null)
		.bind(ContratoAlquiler::getTipoInteresPunitorio, ContratoAlquiler::setTipoInteresPunitorio);

	binderContratoAlquiler.forField(this.cbtipointeres).asRequired("Seleccione un Tipo de Interes")
		.bind(ContratoAlquiler::getTipoIncrementoCuota, ContratoAlquiler::setTipoIncrementoCuota);

	binderContratoAlquiler.forField(rbgTipoMoneda).asRequired("Seleccione un Tipo de Moneda")
		.bind("moneda");

	binderContratoAlquiler.forField(this.stIncremento)
		.withConverter(new StringToIntegerConverter("Debe ingresar un número"))
		.asRequired(
			"Ingrese una frecuencia de incremento de la cuota")
		.withValidator(v -> {
		    ContratoDuracion d = this.cbDuracionContrato.getValue();
		    if (d != null) {
			if (d.getDuracion() % v == 0)
			    return true;
		    }
		    return false;
		}, "El intervalo de actualización debe dividir a la duración del contrato!")
		.bind(ContratoAlquiler::getIntervaloActualizacion, ContratoAlquiler::setIntervaloActualizacion);

	binderContratoAlquiler.forField(this.tfDiaDePago).withNullRepresentation("")
		.withConverter(new StringToIntegerConverter("Debe ingresar un número"))
		.withValidator(n -> (n > 0 && n < 29), "Ingrese un dia entre 1 y 28")
		.bind(ContratoAlquiler::getDiaDePago, ContratoAlquiler::setDiaDePago);

	binderContratoAlquiler.forField(this.tfDocumento).withNullRepresentation("")
		.bind(contrato -> {
		    if (contrato.getDocumento() != null)
			return "Documento Cargado";
		    else
			return "Documento No Cargado";
		}, (contrato, text) -> {
		});

	binderContratoAlquiler.forField(this.tfPActualizacion).withNullRepresentation("")
		.withConverter(new StringToDoubleConverter("Debe ingresar un número"))
		.withValidator(n -> (n >= 0 && n <= 100), "Ingrese un porcentaje entre 0 y 100")
		.bind(ContratoAlquiler::getPorcentajeIncrementoCuota, ContratoAlquiler::setPorcentajeIncrementoCuota);

	binderContratoAlquiler.forField(this.tfPagoFueraDeTermino).withNullRepresentation("")
		.withConverter(new StringToDoubleConverter("Debe ingresar un número"))
		.withValidator(n -> (n >= 0 && n <= 100), "Ingrese un porcentaje entre 0 y 100")
		.bind(ContratoAlquiler::getInteresPunitorio, ContratoAlquiler::setInteresPunitorio);

	binderContratoAlquiler.forField(this.tfPropietario).withNullRepresentation("")
		.bind(contrato -> {
		    String ret = "";
		    if (contrato.getPropietario() != null) {
			ret = contrato.getPropietario().getNombre() + " " + contrato.getPropietario().getApellido();
		    }
		    return ret;
		}, (contrato, persona) -> {
		});

	binderContratoAlquiler.forField(this.tfValorInicial).withNullRepresentation("")
		.withConverter(new StringToBigDecimalConverter("Debe ingresar un número"))
		.withValidator(n -> {
		    return (n.compareTo(BigDecimal.ZERO) > 0);
		}, "Debe Ingresar un Valor Positivo")
		.bind(ContratoAlquiler::getValorInicial, ContratoAlquiler::setValorInicial);
	return binderContratoAlquiler;
    }

    private Binder<ContratoAlquiler> getBinderParaFinalizacionDeCarga() {
	binderContratoAlquiler = getBinderParaEdicion();
	binderContratoAlquiler.forField(this.tfDocumento)
		.asRequired("Debe Cargar al menos un documento antes de finalizar la carga.")
		.withValidator(text -> text == "Documento Cargado",
			"Debe Cargar al menos un documento antes de finalizar la carga.")
		.bind(contrato -> {
		    if (contrato.getDocumento() != null)
			return "Documento Cargado";
		    return "Documento No Cargado";
		}, (contrato, text) -> {
		});
	return binderContratoAlquiler;
    }

    private void buildLayout() {
	setSizeFull();
	stIncremento.setStyleName(ValoTheme.COMBOBOX_BORDERLESS);

	tabSheet = new TabSheet();

	BlueLabel seccionDoc = new BlueLabel("Documento Word");

	stIncremento.setWidth("77%");
	if (!this.contratoABMView.checkIfOnMobile()) {
	    rbgTipoMoneda.addStyleName(ValoTheme.OPTIONGROUP_HORIZONTAL);
	    stIncremento.setCaption("Frecuencia de Incremento(meses)");
	    tfPActualizacion.setCaption("Aumento por Actualización( % )");
	} else {
	    fechaCelebracion.setCaption("Fecha");
	    fechaVencimiento.setCaption("Vencimiento");
	    stIncremento.setCaption("Frecuencia");
	    tfPActualizacion.setCaption("Monto");
	    rbgTipoMoneda.addStyleName(ValoTheme.OPTIONGROUP_SMALL);
	}

	HorizontalLayout documentoButtonsRow = new HorizontalLayout();
	documentoButtonsRow.addComponents(btCargar, btDescargar);
	documentoButtonsRow.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
	tfDocumento.setCaption("Estado documento");
	btCargar.setStyleName(ValoTheme.BUTTON_BORDERLESS);
	btDescargar.setStyleName(ValoTheme.BUTTON_BORDERLESS);
	btDescargar.addClickListener(event -> {
	    btDescargar.descargar(contratoAlquiler, "Contrato.doc");
	});

	principal = new FormLayout(cbInmuebles, tfPropietario, cbInquilino, fechaCelebracion,fechaVencimiento, seccionDoc,
		tfDocumento,
		documentoButtonsRow);

	condiciones = new FormLayout(cbDuracionContrato, tfDiaDePago, tfPagoFueraDeTermino, cbInteresFueraDeTermino,
		new BlueLabel("Monto e Incremento"),
		stIncremento, tfPActualizacion, cbtipointeres, tfValorInicial, rbgTipoMoneda);
	condiciones.setMargin(true);
	condiciones.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
	principal.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
	condiciones.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);

	tabSheet.addTab(principal, "Alquiler");
	tabSheet.addTab(condiciones, "Condiciones");

	addComponent(tabSheet);
	HorizontalLayout actions = new HorizontalLayout(save, delete, finalizarCarga, renovarContrato);

	addComponent(actions);
	this.setSpacing(false);
	actions.setSpacing(true);

    }

    public void setContratoAlquiler(ContratoAlquiler contratoAlquiler) {

	if (contratoAlquiler != null) {
	    configurarComponentesSegunEstadoContrato(contratoAlquiler.getEstadoContrato());
	    this.contratoAlquiler = contratoAlquiler;
	    binderContratoAlquiler.readBean(contratoAlquiler);

	} else {
	    this.contratoAlquiler = ContratoService.getInstanciaAlquiler();
	    configurarComponentesSegunEstadoContrato(this.contratoAlquiler.getEstadoContrato());
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
	// TODO Auto-generated method stub

	tfDocumento.setEnabled(false);
	if (estadoContrato == EstadoContrato.EnProcesoDeCarga) {
	    binderContratoAlquiler = getBinderParaEdicion();
	    this.save.setVisible(true);
	    this.delete.setVisible(true);
	    this.finalizarCarga.setVisible(true);
	    this.renovarContrato.setVisible(false);
	    this.btCargar.setEnabled(true);
	    this.btDescargar.setEnabled(true);
	    this.cbDuracionContrato.setEnabled(true);
	    this.cbInmuebles.setEnabled(true);
	    this.cbInquilino.setEnabled(true);
	    this.cbInteresFueraDeTermino.setEnabled(true);
	    this.cbtipointeres.setEnabled(true);
	    this.fechaCelebracion.setEnabled(true);
	    this.fechaVencimiento.setEnabled(false);
	    this.rbgTipoMoneda.setEnabled(true);
	    this.stIncremento.setEnabled(true);
	    this.tfDiaDePago.setEnabled(true);
	    this.tfDocumento.setEnabled(false);
	    this.tfPActualizacion.setEnabled(true);
	    this.tfPagoFueraDeTermino.setEnabled(true);
	    this.tfValorInicial.setEnabled(true);

	} else if (estadoContrato == EstadoContrato.Vencido) {
	    binderContratoAlquiler = getBinderParaFinalizacionDeCarga();
	    this.save.setVisible(false);
	    this.delete.setVisible(false);
	    this.finalizarCarga.setVisible(false);
	    this.renovarContrato.setVisible(true);
	    this.btCargar.setEnabled(false);
	    this.btDescargar.setEnabled(true);
	    this.cbDuracionContrato.setEnabled(false);
	    this.cbInmuebles.setEnabled(false);
	    this.cbInquilino.setEnabled(false);
	    this.cbInteresFueraDeTermino.setEnabled(false);
	    this.cbtipointeres.setEnabled(false);
	    this.fechaCelebracion.setEnabled(false);
	    this.rbgTipoMoneda.setEnabled(false);
	    this.stIncremento.setEnabled(false);
	    this.tfDiaDePago.setEnabled(false);
	    this.tfDocumento.setEnabled(false);
	    this.tfPActualizacion.setEnabled(false);
	    this.tfPagoFueraDeTermino.setEnabled(false);
	    this.tfValorInicial.setEnabled(false);

	} else {
	    binderContratoAlquiler = getBinderParaFinalizacionDeCarga();
	    this.save.setVisible(false);
	    this.delete.setVisible(false);
	    this.finalizarCarga.setVisible(false);
	    this.renovarContrato.setVisible(false);
	    this.btCargar.setEnabled(false);
	    this.btDescargar.setEnabled(true);
	    this.cbDuracionContrato.setEnabled(false);
	    this.cbInmuebles.setEnabled(false);
	    this.cbInquilino.setEnabled(false);
	    this.cbInteresFueraDeTermino.setEnabled(false);
	    this.cbtipointeres.setEnabled(false);
	    this.fechaCelebracion.setEnabled(false);
	    this.rbgTipoMoneda.setEnabled(false);
	    this.stIncremento.setEnabled(false);
	    this.tfDiaDePago.setEnabled(false);
	    this.tfDocumento.setEnabled(false);
	    this.tfPActualizacion.setEnabled(false);
	    this.tfPagoFueraDeTermino.setEnabled(false);
	    this.tfValorInicial.setEnabled(false);

	}
    }

    private void delete() {
	boolean success = service.delete(contratoAlquiler);
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

	    binderContratoAlquiler.writeBean(contratoAlquiler);
	    if (contratoAlquiler.getEstadoContrato() == EstadoContrato.Vigente)
		contratoAlquiler.getInmueble().setEstadoInmueble(EstadoInmueble.Alquilado);

	    if (archivo != null && archivo.exists())
		success = service.saveOrUpdate(contratoAlquiler, archivo);
	    else
		success = service.saveOrUpdate(contratoAlquiler, null);

	    contratoABMView.updateList();
	    setVisible(false);
	    contratoABMView().setComponentsVisible(true);

	} catch (ValidationException e) {
	    Utils.mostarErroresValidator(e);
	    checkFieldsPerTab(e.getFieldValidationErrors());

	} catch (ContratoServiceException e) {
	    System.err.println("Error al guardar: "+ contratoAlquiler + "\n"+ e.getCause());
	    e.printStackTrace();
	}catch(Exception e) {
	    e.printStackTrace();
	}

	if (success)
	    contratoABMView().showSuccessNotification("Guardado");
	else {
	    contratoABMView().showErrorNotification("No se realizaron cambios");
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

    }

    public void clearFields() {
	this.cbDuracionContrato.clear();
	this.cbInmuebles.clear();
	this.cbInquilino.clear();
	this.cbInteresFueraDeTermino.clear();
	this.cbtipointeres.clear();
	this.fechaCelebracion.clear();
	this.fechaVencimiento.clear();
	this.rbgTipoMoneda.clear();
	this.stIncremento.clear();
	this.tfDiaDePago.clear();
	this.tfDocumento.clear();
	this.tfPActualizacion.clear();
	this.tfPagoFueraDeTermino.clear();
	this.tfPropietario.clear();
	this.tfValorInicial.clear();
    }

    private void checkFieldsPerTab(List<BindingValidationStatus<?>> invalidComponents) {
	boolean tabPrincipalInvalidFields = false;
	boolean tabConditionsInvalidFields = false;
	// TabElements for tab principal
	List<Component> tabPrincipalComponents = new ArrayList<Component>();
	tabPrincipalComponents.add(cbInmuebles);
	tabPrincipalComponents.add(tfPropietario);
	tabPrincipalComponents.add(cbInquilino);
	tabPrincipalComponents.add(fechaCelebracion);
	tabPrincipalComponents.add(fechaVencimiento);
	tabPrincipalComponents.add(tfDocumento);
	for (BindingValidationStatus invalidField : invalidComponents) {
	    tabPrincipalInvalidFields = tabPrincipalComponents.contains(invalidField.getField());
	    if (tabPrincipalInvalidFields)
		break;
	}
	System.out.println(tabPrincipalInvalidFields);

	// Tab elements for tab caracteristicas
	List<Component> tabConditionsComponents = new ArrayList<Component>();
	tabConditionsComponents.add(cbDuracionContrato);
	tabConditionsComponents.add(tfDiaDePago);
	tabConditionsComponents.add(tfPagoFueraDeTermino);
	tabConditionsComponents.add(cbtipointeres);
	tabConditionsComponents.add(new BlueLabel("Monto e Incremento"));
	tabConditionsComponents.add(stIncremento);
	tabConditionsComponents.add(tfPActualizacion);
	tabConditionsComponents.add(tfValorInicial);
	tabConditionsComponents.add(rbgTipoMoneda);
	for (BindingValidationStatus invalidField : invalidComponents) {
	    tabConditionsInvalidFields = tabConditionsComponents.contains(invalidField.getField());
	    if (tabConditionsInvalidFields)
		break;
	}
	System.out.println(tabConditionsInvalidFields);

	// Take user to the invalid components tag (in case there's only one)
	if (tabPrincipalInvalidFields && !tabConditionsInvalidFields) {
	    Notification.show("Error al guardar, porfavor revise los campos principales",
		    Notification.Type.WARNING_MESSAGE);
	    tabSheet.setSelectedTab(principal);
	} else if (!tabPrincipalInvalidFields && tabConditionsInvalidFields) {
	    Notification.show("Error al guardar, porfavor revise las condiciones del contrato e intente de nuevo",
		    Notification.Type.WARNING_MESSAGE);
	    tabSheet.setSelectedTab(condiciones);
	} else {
	    Notification.show("Error al guardar, porfavor revise los campos e intente de nuevo",
		    Notification.Type.WARNING_MESSAGE);
	}
    }
}