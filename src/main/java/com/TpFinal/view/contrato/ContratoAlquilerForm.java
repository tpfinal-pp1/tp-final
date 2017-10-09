package com.TpFinal.view.contrato;

import com.TpFinal.data.dto.contrato.Contrato;
import com.TpFinal.data.dto.contrato.ContratoAlquiler;
import com.TpFinal.data.dto.contrato.DuracionContrato;
import com.TpFinal.data.dto.contrato.TipoInteres;
import com.TpFinal.data.dto.inmueble.EstadoInmueble;
import com.TpFinal.data.dto.inmueble.Inmueble;
import com.TpFinal.data.dto.inmueble.TipoMoneda;
import com.TpFinal.data.dto.persona.Calificacion;
import com.TpFinal.data.dto.persona.Inquilino;
import com.TpFinal.data.dto.persona.Persona;
import com.TpFinal.data.dto.publicacion.Rol;
import com.TpFinal.services.ContratoService;
import com.TpFinal.services.InmuebleService;
import com.TpFinal.services.PersonaService;
import com.TpFinal.view.component.*;
import com.vaadin.data.Binder;
import com.vaadin.data.HasValue;
import com.vaadin.data.ValidationException;
import com.vaadin.data.converter.StringToBigDecimalConverter;
import com.vaadin.data.converter.StringToDoubleConverter;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import org.vaadin.risto.stepper.IntStepper;

import java.time.LocalDate;
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
    Button delete = new Button("Eliminar");

    // TabPrincipal
    ComboBox<Inmueble> cbInmuebles = new ComboBox<>("Inmueble");
    TextField tfPropietario = new TextField("Propietario");
    ComboBox<Persona> cbInquilino = new ComboBox<>("Inquilino");
    DateField fechaCelebracion = new DateField("Fecha de Celebracion");
    public String nombreArchivo="";

    // Documento
    TextField tfDocumento = new TextField();
    UploadButton btCargar = new UploadButton(new UploadReceiver() {
		@Override
		public void onSuccessfullUpload(String filename) {
			nombreArchivo=filename;
			tfDocumento.setValue("Documento cargado");
			btDescargar.setFile(filename);
		}
	});
    DownloadButton btDescargar = new DownloadButton();

    // Condiciones
    TextField tfDiaDePago = new TextField("Día de Pago");
    TextField tfPagoFueraDeTermino = new TextField("Recargo Punitorio");
    ComboBox<TipoInteres> cbInteresFueraDeTermino = new ComboBox<>("Tipo Interes");
    ComboBox<DuracionContrato> cbDuracionContrato = new ComboBox<>("Duración");
    IntStepper stIncremento = new IntStepper("");

    TextField tfPActualizacion = new TextField("");
    ComboBox<TipoInteres> cbtipointeres = new ComboBox<>("Tipo Interes");
    TextField tfValorInicial = new TextField("Valor Inicial $");
    RadioButtonGroup<TipoMoneda> rbgTipoMoneda = new RadioButtonGroup<>("Tipo Moneda", TipoMoneda.toList());

    ContratoService service = new ContratoService();
    InmuebleService inmuebleService = new InmuebleService();
    PersonaService personaService = new PersonaService();
    private ContratoABMView addressbookView;
    private Binder<ContratoAlquiler> binderContratoAlquiler = new Binder<>(ContratoAlquiler.class);
    Persona person = new Persona(); // TODO ver donde se usa persona p.

    TabSheet tabSheet;

    public ContratoAlquilerForm(ContratoABMView addressbook) {
	// setSizeUndefined();
	addressbookView = addressbook;
	configureComponents();
	binding();
	buildLayout();
	addStyleName("v-scrollable");
    }

    private void configureComponents() {
	tfPropietario.setEnabled(false);
	cbDuracionContrato.setItems(DuracionContrato.toList());
	cbInmuebles.setItems(inmuebleService.readAll());
	cbInquilino.setItems(personaService.readAll());
	cbInteresFueraDeTermino.setItems(TipoInteres.toList());
	cbtipointeres.setItems(TipoInteres.toList());

	delete.setStyleName(ValoTheme.BUTTON_DANGER);
	save.addClickListener(e -> this.save());
	delete.addClickListener(e -> this.delete());
	save.setStyleName(ValoTheme.BUTTON_PRIMARY);
	save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

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
			;
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
			    contratoAlquiler.setInquilinoContrato(inquilino.getInquilino());
			} else {
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

	setVisible(false);
    }

    private void binding() {

	binderContratoAlquiler.forField(this.fechaCelebracion)
		.bind(Contrato::getFechaCelebracion, Contrato::setFechaCelebracion);

	binderContratoAlquiler.forField(this.cbDuracionContrato)
		.bind(ContratoAlquiler::getDuracionContrato, ContratoAlquiler::setDuracionContrato);

	binderContratoAlquiler.forField(this.cbInmuebles).withNullRepresentation(new Inmueble())
		.bind(ContratoAlquiler::getInmueble, ContratoAlquiler::setInmueble);

	binderContratoAlquiler.forField(this.cbInquilino).withNullRepresentation(new Persona())
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
	binderContratoAlquiler.forField(this.cbInteresFueraDeTermino).withNullRepresentation(null)
		.bind(ContratoAlquiler::getTipoInteresPunitorio, ContratoAlquiler::setTipoInteresPunitorio);

	binderContratoAlquiler.forField(this.cbtipointeres).withNullRepresentation(null)
		.bind(ContratoAlquiler::getTipoIncrementoCuota, ContratoAlquiler::setTipoIncrementoCuota);

	binderContratoAlquiler.forField(rbgTipoMoneda).bind("moneda");

	binderContratoAlquiler.forField(this.stIncremento).withNullRepresentation(null)
		.bind(ContratoAlquiler::getIntervaloActualizacion, ContratoAlquiler::setIntervaloActualizacion);

	binderContratoAlquiler.forField(this.tfDiaDePago).withNullRepresentation("")
		.withConverter(new StringToIntegerConverter("Debe ingresar un número"))
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
		.withConverter(new StringToDoubleConverter("Debe ingresar un coeficiente"))
		.bind(ContratoAlquiler::getPorcentajeIncrementoCuota, ContratoAlquiler::setPorcentajeIncrementoCuota);

	binderContratoAlquiler.forField(this.tfPagoFueraDeTermino).withNullRepresentation("")
		.withConverter(new StringToDoubleConverter("Debe ingresar un coeficiente"))
		.bind(ContratoAlquiler::getPorcentajeIncrementoCuota, ContratoAlquiler::setPorcentajeIncrementoCuota);

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
		.bind(ContratoAlquiler::getValorInicial, ContratoAlquiler::setValorInicial);

    }

    private void buildLayout() {

	//setSizeFull();
//	setMargin(true);
		stIncremento.setStyleName(ValoTheme.COMBOBOX_BORDERLESS);


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

	stIncremento.setValue(1);
	// TODO setear el step como divisor de la duracion.
	stIncremento.setStepAmount(1);
	stIncremento.setWidth("77%");
	if (!this.addressbookView.checkIfOnMobile()) {
		rbgTipoMoneda.addStyleName(ValoTheme.OPTIONGROUP_HORIZONTAL);
		stIncremento.setCaption("Frecuencia de Incremento(meses)");
		tfPActualizacion.setCaption("Aumento por Actualización");
	}
	else{
		fechaCelebracion.setCaption("Fecha");
		stIncremento.setCaption("Frecuencia");
		tfPActualizacion.setCaption("Monto");
		rbgTipoMoneda.addStyleName(ValoTheme.OPTIONGROUP_SMALL);
	}

	HorizontalLayout documentoButtonsRow = new HorizontalLayout();
	documentoButtonsRow.addComponents(btCargar, btDescargar);
	documentoButtonsRow.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
	tfDocumento.setCaption("Nombre");
	btCargar.setStyleName(ValoTheme.BUTTON_BORDERLESS);
	btDescargar.setStyleName(ValoTheme.BUTTON_BORDERLESS);

	FormLayout principal = new FormLayout(cbInmuebles, tfPropietario, cbInquilino, fechaCelebracion, seccionDoc,
		tfDocumento,
		documentoButtonsRow);

	FormLayout condiciones = new FormLayout(cbDuracionContrato,tfDiaDePago, tfPagoFueraDeTermino, cbtipointeres ,new BlueLabel("Monto e Incremento"),
		stIncremento, tfPActualizacion, tfValorInicial, rbgTipoMoneda);
	condiciones.setMargin(true);
	condiciones.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
	principal.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
	condiciones.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);

	tabSheet.addTab(principal, "Alquiler");
	tabSheet.addTab(condiciones, "Condiciones");

	addComponent(tabSheet);
	HorizontalLayout actions = new HorizontalLayout(save, delete);

	addComponent(actions);
	this.setSpacing(false);
	actions.setSpacing(true);
	
	btDescargar.addClickListener(event ->{
		btDescargar.descargar(contratoAlquiler, "contrato.doc");
	});

	// addStyleName("v-scrollable");

    }

    public void setContratoAlquiler(ContratoAlquiler ContratoAlquiler) {

	if (ContratoAlquiler != null) {
	    this.contratoAlquiler = ContratoAlquiler;
	    binderContratoAlquiler.readBean(ContratoAlquiler);
	    delete.setVisible(ContratoAlquiler.getId() != null);
	} else 
	{
	    this.contratoAlquiler = ContratoService.getInstanciaAlquiler();
	    delete.setVisible(false);
	}

	setVisible(true);

	getAddressbookView().setComponentsVisible(false);
	if (getAddressbookView().isIsonMobile())
	    tabSheet.focus();

    }

    private void delete() {
	service.delete(contratoAlquiler);
	addressbookView.updateList();
	setVisible(false);
	getAddressbookView().setComponentsVisible(true);
	getAddressbookView().showSuccessNotification("Borrado");

    }

    private void save() {

	boolean success = false;
	try {
		 if(contratoAlquiler.getInmueble() != null && contratoAlquiler.getInquilinoContrato() != null) {
             if (contratoAlquiler.getInmueble().getId() != null && contratoAlquiler.getInquilinoContrato().getId() != null && contratoAlquiler.getPropietario() != null) {
                 binderContratoAlquiler.writeBean(contratoAlquiler);
                 //if(!archivo.exists()) TODO
                 if(!contratoAlquiler.getInmueble().getEstadoInmueble().equals(EstadoInmueble.Alquilado)
                		 &&!contratoAlquiler.getInmueble().getEstadoInmueble().equals(EstadoInmueble.Vendido)) {
                	 
                	 contratoAlquiler.getInmueble().setEstadoInmueble(EstadoInmueble.Alquilado);
                	 service.saveOrUpdate(contratoAlquiler, null);
             	    	success = true;
                 }
	    
             }
		 }

	} catch (ValidationException e) {
	    e.printStackTrace();
	    Notification.show("Error al guardar, porfavor revise los campos e intente de nuevo");
	    // Notification.show("Error: "+e.getCause());
	    return;
	} catch (Exception e) {
	    e.printStackTrace();
	    Notification.show("Error: " + e.toString());
	}

	addressbookView.updateList();
	setVisible(false);
	getAddressbookView().setComponentsVisible(true);

	if (success)
	    getAddressbookView().showSuccessNotification("Guardado");

    }

    public void cancel() {
	addressbookView.updateList();
	setVisible(false);
	getAddressbookView().setComponentsVisible(true);
    }

    public ContratoABMView getAddressbookView() {
	return addressbookView;
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
	this.rbgTipoMoneda.clear();
	this.stIncremento.clear();	
	this.tfDiaDePago.clear();
	this.tfDocumento.clear();
	this.tfPActualizacion.clear();
	this.tfPagoFueraDeTermino.clear();
	this.tfPropietario.clear();
	this.tfValorInicial.clear();
    }
}