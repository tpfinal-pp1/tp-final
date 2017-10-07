package com.TpFinal.view.contrato;

import com.TpFinal.data.dto.contrato.Contrato;
import com.TpFinal.data.dto.contrato.ContratoAlquiler;
import com.TpFinal.data.dto.contrato.DuracionContrato;
import com.TpFinal.data.dto.contrato.TipoInteres;
import com.TpFinal.data.dto.inmueble.Inmueble;
import com.TpFinal.data.dto.inmueble.TipoMoneda;
import com.TpFinal.data.dto.persona.Persona;
import com.TpFinal.services.ContratoService;
import com.TpFinal.services.PersonaService;
import com.TpFinal.view.component.BlueLabel;
import com.TpFinal.view.component.TinyButton;
import com.TpFinal.view.component.VentanaSelectora;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.validator.DateRangeValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.Orientation;
import com.vaadin.shared.ui.slider.SliderOrientation;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.vaadin.risto.stepper.IntStepper;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.vaadin.risto.stepper.IntStepper;




/* Create custom UI Components.
 *
 * Create your own Vaadin components by inheritance and composition.
 * This is a form component inherited from VerticalLayout. Use
 * Use BeanFieldGroup to binding data fields from DTO to UI fields.
 * Similarly named field by naming convention or customized
 * with @PropertyId annotation.
 */
public class ContratoAlquilerForm extends FormLayout {
    private ContratoAlquiler ContratoAlquiler;

    // Actions
    Button save = new Button("Guardar");
    Button delete = new Button("Eliminar");

    // TabPrincipal
    ComboBox<Inmueble> cbInmuebles = new ComboBox<>("Inmueble");
    TextField tfPropietario = new TextField("Propietario");
    ComboBox<Persona> cbInquilino = new ComboBox<>("Inquilino");
    DateField fechaCelebracion = new DateField("Fecha de Celebracion");
    
    //Documento
    TextField tfDocumento = new TextField();
    Button btCargar = new Button(VaadinIcons.UPLOAD);
    Button btDescargar = new Button(VaadinIcons.DOWNLOAD);
    
    //Condiciones
    Slider slDiaDePago = crearSliderInteger(1,28,"Día de Pago" , "dias");
    Slider slPagoFueraDeTermino = crearSliderDouble(0,100,"", "%");
    ComboBox<TipoInteres> cbInteresFueraDeTermino = new ComboBox<>();
    ComboBox<DuracionContrato> cbDuracionContrato = new ComboBox<>("Duración");
    IntStepper stIncremento = new IntStepper();
    
    Slider slPActualizacion = crearSliderDouble(0,100,"", "%");
    ComboBox<TipoInteres> cbPActualizacion = new ComboBox<>();
    TextField tfValorInicial = new TextField("Valor Inicial $");
    RadioButtonGroup<TipoMoneda> rbgTipoMoneda = new RadioButtonGroup<>("Tipo Moneda", TipoMoneda.toList());
    
    

    // private NativeSelect<ContratoAlquiler.Sexo> sexo = new
    // NativeSelect<>("Sexo");

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
    
    private Slider crearSliderInteger(int limiteInferior, int limiteSuperior, String descripcion, String unidad) {
		Slider slider = new Slider(limiteInferior, limiteSuperior);
		slider.setOrientation(SliderOrientation.HORIZONTAL);
		slider.setCaption(descripcion + " " + slider.getValue().intValue() + " " + unidad);
		slider.addValueChangeListener(e -> {
			slider.setCaption(descripcion + " " + slider.getValue().intValue() + " " + unidad);
		});
		return slider;
	}

    private Slider crearSliderDouble(int limiteInferior, int limiteSuperior, String descripcion, String unidad) {
	Slider slider = new Slider(limiteInferior, limiteSuperior, 1);
	slider.setOrientation(SliderOrientation.HORIZONTAL);
	slider.setCaption(descripcion + " " + slider.getValue().intValue() + " " + unidad);
	slider.addValueChangeListener(e -> {
		slider.setCaption(descripcion + " " + slider.getValue().intValue() + " " + unidad);
	});
	return slider;
}

    
    private void configureComponents() {
	/*
	 * Highlight primary actions.
	 *
	 * With Vaadin built-in styles you can highlight the primary save button
	 *
	 * and give it a keyoard shortcut for a better UX.
	 */

	// sexo.setEmptySelectionAllowed(false);
	// sexo.setItems(ContratoAlquiler.Sexo.values());
	delete.setStyleName(ValoTheme.BUTTON_DANGER);
	save.addClickListener(e -> this.save());
	delete.addClickListener(e -> this.delete());
	save.setStyleName(ValoTheme.BUTTON_PRIMARY);
	save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

	setVisible(false);
    }

    private void binding() {
	// binder.bindInstanceFields(this); //Binding automatico
	binderContratoAlquiler.forField(fechaCelebracion).withValidator(new DateRangeValidator(
		"Debe celebrarse desde mañana en adelante", LocalDate.now(), LocalDate.now().plusDays(365))).bind(
			Contrato::getFechaCelebracion, Contrato::setFechaCelebracion);

    }

    private void buildLayout() {
	setSizeFull();
	setMargin(true);

	tabSheet = new TabSheet();

	BlueLabel blCondiciones = new BlueLabel("Condiciones");
//	BlueLabel info = new BlueLabel("Información Adicional");
//
//	TinyButton personas = new TinyButton("Ver Personas");
//
//	personas.addClickListener(e -> getPersonaSelector());
//	
//	VerticalLayout Roles = new VerticalLayout(personas);
//
//	fechaCelebracion.setWidth("100");
	
	stIncremento.setValue(1);
	stIncremento.setStepAmount(2);
	
	HorizontalLayout documentoRow = new HorizontalLayout();
	documentoRow.addComponents(tfDocumento, btCargar,btDescargar);
	documentoRow.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
	documentoRow.setCaption("Documento");
	documentoRow.setExpandRatio(tfDocumento, 1f);
	FormLayout principal = new FormLayout(cbInmuebles,tfPropietario,cbInquilino,fechaCelebracion,documentoRow);
	
	HorizontalLayout fueraDeTerminoRow = new HorizontalLayout();
	fueraDeTerminoRow.addComponents(slPagoFueraDeTermino,cbInteresFueraDeTermino);
	
	fueraDeTerminoRow.setCaption("Interes Fuera de Termino");
	
	
	HorizontalLayout actualizacionRow = new HorizontalLayout();
	actualizacionRow.addComponents(slPActualizacion,cbPActualizacion);
	
	actualizacionRow.setCaption("Actualizacion Valor");

	
	FormLayout condiciones = new FormLayout(slDiaDePago,fueraDeTerminoRow,cbDuracionContrato,
		stIncremento,actualizacionRow,tfValorInicial,rbgTipoMoneda);

//	FormLayout condiciones = new FormLayout(slDiaDePago,slPagoFueraDeTermino,cbInteresFueraDeTermino,cbDuracionContrato,
//		stIncremento,slPActualizacion,cbPActualizacion,tfValorInicial,rbgTipoMoneda);

	
	principal.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);

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

	this.ContratoAlquiler = ContratoAlquiler;
	// binderContratoAlquiler.readBean(ContratoAlquiler);

	// Show delete button for only Persons already in the database
	delete.setVisible(ContratoAlquiler.getId() != null);

	setVisible(true);

	getAddressbookView().setComponentsVisible(false);
	if (getAddressbookView().isIsonMobile())
	    tabSheet.focus();

    }

    private void delete() {
	service.delete(ContratoAlquiler);
	addressbookView.updateList();
	setVisible(false);
	getAddressbookView().setComponentsVisible(true);
	getAddressbookView().showSuccessNotification("Borrado");

    }

  

    private void save() {

	boolean success = false;
	try {
	    binderContratoAlquiler.writeBean(ContratoAlquiler);
	    service.saveOrUpdate(ContratoAlquiler, null);
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