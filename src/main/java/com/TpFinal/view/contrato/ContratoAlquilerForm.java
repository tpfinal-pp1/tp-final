package com.TpFinal.view.contrato;

import com.TpFinal.data.dto.contrato.Contrato;
import com.TpFinal.data.dto.contrato.ContratoAlquiler;
import com.TpFinal.data.dto.contrato.DuracionContrato;
import com.TpFinal.data.dto.contrato.TipoInteres;
import com.TpFinal.data.dto.inmueble.Inmueble;
import com.TpFinal.data.dto.inmueble.TipoMoneda;
import com.TpFinal.data.dto.persona.Calificacion;
import com.TpFinal.data.dto.persona.Inquilino;
import com.TpFinal.data.dto.persona.Persona;
import com.TpFinal.data.dto.publicacion.Rol;
import com.TpFinal.services.ContratoService;
import com.TpFinal.services.PersonaService;
import com.TpFinal.view.component.BlueLabel;
import com.TpFinal.view.component.VentanaSelectora;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.validator.DateRangeValidator;
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

    // Documento
    TextField tfDocumento = new TextField();
    Button btCargar = new Button(VaadinIcons.UPLOAD);
    Button btDescargar = new Button(VaadinIcons.DOWNLOAD);

    // Condiciones
    TextField tfDiaDePago = new TextField("Día de Pago");
    TextField tfPagoFueraDeTermino = new TextField("Recargo Punitorio");
    ComboBox<TipoInteres> cbInteresFueraDeTermino = new ComboBox<>("Tipo Interes");
    ComboBox<DuracionContrato> cbDuracionContrato = new ComboBox<>("Duración");
    IntStepper stIncremento = new IntStepper("Frecuencia de Incremento (meses)");

  
    TextField tfPActualizacion = new TextField("Aumento por Actualización");
    ComboBox<TipoInteres> cbPActualizacion = new ComboBox<>("Tipo Interes");
    TextField tfValorInicial = new TextField("Valor Inicial $");
    RadioButtonGroup<TipoMoneda> rbgTipoMoneda = new RadioButtonGroup<>("Tipo Moneda", TipoMoneda.toList());


    ContratoService service = new ContratoService();
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
	
	delete.setStyleName(ValoTheme.BUTTON_DANGER);
	save.addClickListener(e -> this.save());
	delete.addClickListener(e -> this.delete());
	save.setStyleName(ValoTheme.BUTTON_PRIMARY);
	save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

	setVisible(false);
    }

    private void binding() {	
	
	binderContratoAlquiler.forField(this.fechaCelebracion)
	.bind(Contrato::getFechaCelebracion, Contrato::setFechaCelebracion);
	
	binderContratoAlquiler.forField(this.cbDuracionContrato)
	.bind(ContratoAlquiler::getDuracionContrato, ContratoAlquiler::setDuracionContrato);
	
	binderContratoAlquiler.forField(this.cbInmuebles)
	.bind(ContratoAlquiler::getInmueble, ContratoAlquiler::setInmueble);
	
	binderContratoAlquiler.forField(this.cbInquilino)
	.withValidator(p -> p.equals(contratoAlquiler.getPropietario()) == false, "No pueden ser la misma persona")
	.bind(contrato -> contrato.getInquilinoContrato().getPersona(), 
		(contrato, persona)->{
		    Inquilino i;
		    if (persona.contiene(Rol.Inquilino)) {
			i = (Inquilino) persona.getRol(Rol.Inquilino);
			i.getContratos().add(contrato);
		    }else {
			i = new Inquilino.Builder()
				.setCalificacion(Calificacion.C)
				.setPersona(persona)
				.build();			
		    }
		    i.getContratos().add(contrato);
		    contrato.setInquilinoContrato(i);
	});
	binderContratoAlquiler.forField(this.cbInteresFueraDeTermino)
	.bind(ContratoAlquiler::getTipoInteresPunitorio,ContratoAlquiler::setTipoInteresPunitorio);
	
	binderContratoAlquiler.forField(this.cbPActualizacion)
	.bind(ContratoAlquiler::getTipoIncrementoCuota,ContratoAlquiler::setTipoIncrementoCuota);
	
	binderContratoAlquiler.forField(this.rbgTipoMoneda)
	.bind(Contrato::getMoneda,Contrato::setMoneda);
	
    }
    private void buildLayout() {
	setSizeFull();
	setMargin(true);

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
	stIncremento.setStepAmount(2);
	stIncremento.setWidth("77%");
	rbgTipoMoneda.addStyleName(ValoTheme.OPTIONGROUP_HORIZONTAL);

	HorizontalLayout documentoButtonsRow = new HorizontalLayout();
	documentoButtonsRow.addComponents(btCargar, btDescargar);
	documentoButtonsRow.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
	tfDocumento.setCaption("Nombre");
	btCargar.setStyleName(ValoTheme.BUTTON_BORDERLESS);
	btDescargar.setStyleName(ValoTheme.BUTTON_BORDERLESS);

	FormLayout principal = new FormLayout(cbInmuebles, tfPropietario, cbInquilino, fechaCelebracion, seccionDoc,
		tfDocumento,
		documentoButtonsRow);

	FormLayout condiciones = new FormLayout(tfDiaDePago, tfPagoFueraDeTermino, cbDuracionContrato,
		stIncremento, tfPActualizacion, cbPActualizacion, tfValorInicial, rbgTipoMoneda);

	principal.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
	condiciones.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);

	tabSheet.addTab(principal, "Principal");
	tabSheet.addTab(condiciones, "Condiciones");

	addComponent(tabSheet);
	HorizontalLayout actions = new HorizontalLayout(save, delete);

	addComponent(actions);
	this.setSpacing(false);
	actions.setSpacing(true);

	// addStyleName("v-scrollable");

    }

    public void setContratoAlquiler(ContratoAlquiler ContratoAlquiler) {

	this.contratoAlquiler = ContratoAlquiler;
	binderContratoAlquiler.readBean(ContratoAlquiler);

	// Show delete button for only Persons already in the database
	delete.setVisible(ContratoAlquiler.getId() != null);

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
	    binderContratoAlquiler.writeBean(contratoAlquiler);
	    service.saveOrUpdate(contratoAlquiler, null);
	    success = true;

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
	/*
	 * String msg = String.format("Guardado '%s %s'.", ContratoAlquiler.getNombre(),
	 * ContratoAlquiler.getApellido());* Notification.show(msg,
	 * Type.TRAY_NOTIFICATION);
	 */
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
}