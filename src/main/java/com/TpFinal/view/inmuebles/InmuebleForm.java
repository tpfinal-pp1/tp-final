package com.TpFinal.view.inmuebles;

import com.TpFinal.data.dto.inmueble.ClaseInmueble;
import com.TpFinal.data.dto.inmueble.Inmueble;
import com.TpFinal.data.dto.inmueble.TipoInmueble;
import com.TpFinal.data.dto.persona.Propietario;
import com.TpFinal.services.InmuebleService;
import com.TpFinal.services.PersonaService;
import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.ui.TextField;

/* Create custom UI Components.
 *
 * Create your own Vaadin components by inheritance and composition.
 * This is a form component inherited from VerticalLayout. Use
 * Use BeanFieldGroup to binding data fields from DTO to UI fields.
 * Similarly named field by naming convention or customized
 * with @PropertyId annotation.
 */
public class InmuebleForm extends FormLayout {
    private InmuebleService inmbService = new InmuebleService();
    private Inmueble inmueble;
    private Button save = new Button("Guardar");
    private Button test = new Button("Test");
    private Button delete = new Button("Eliminar");
    
    private ComboBox<Propietario> propietario = new ComboBox<>();
    private TextField direccion = new TextField("Direccion");
    private ComboBox<TipoInmueble> tipoInmueble = new ComboBox<>();
    private ComboBox<ClaseInmueble> claseInmueble = new ComboBox<>();

    PersonaService service = new PersonaService();
    private InmuebleABMView inmuebleABMView;
    private Binder<Inmueble> binderInmueble = new Binder<>(Inmueble.class);
    
    TabSheet tabSheet;

    // Easily binding forms to beans and manage validation and buffering

    public InmuebleForm(InmuebleABMView abmView) {
	// setSizeUndefined();
	inmuebleABMView = abmView;
	configureComponents();
	binding();
	buildLayout();

    }

    private void configureComponents() {
	delete.setStyleName(ValoTheme.BUTTON_DANGER);
	save.addClickListener(e -> this.save());
	test.addClickListener(e -> this.test());
	delete.addClickListener(e -> this.delete());
	save.setStyleName(ValoTheme.BUTTON_PRIMARY);
	save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

	setVisible(false);
    }

    private void binding() {
//	// binder.bindInstanceFields(this); //Binding automatico
//	nombre.setRequiredIndicatorVisible(true);
//	apellido.setRequiredIndicatorVisible(true);
//	mail.setRequiredIndicatorVisible(true);
//	binderPersona.forField(nombre).withValidator(new StringLengthValidator(
//		"El nombre debe estar entre 2 y 20 caracteres",
//		2, 20)).bind(Persona::getNombre, Persona::setNombre);
//
//	binderPersona.forField(apellido).withValidator(new StringLengthValidator(
//		"El nombre debe estar entre 2 y 20 caracteres",
//		2, 20)).bind(Persona::getApellido, Persona::setApellido);
//
//	binderPersona.forField(DNI)./*
//				     * withValidator(new StringLengthValidator(
//				     * "El DNI de estar entre 2 y 20 caracteres", 2, 20)).
//				     */bind(Persona::getDNI, Persona::setDNI);
//	binderPersona.forField(telefono).bind(Persona::getTelefono, Persona::setTelefono);
//	binderPersona.forField(telefono2).bind(Persona::getTelefono2, Persona::setTelefono2);
//	binderPersona.forField(mail).withValidator(new EmailValidator(
//		"Introduzca un email valido!")).bind(Persona::getMail, Persona::setMail);
//	binderPersona.forField(infoAdicional).withValidator(new StringLengthValidator(
//		"El nombre debe estar entre 2 y 20 caracteres",
//		0, 255)).bind(Persona::getInfoAdicional, Persona::setInfoAdicional);

    }

    private void buildLayout() {
	addStyleName("v-scrollable");
	//Creando el formulario
	
	
	addComponents(this.direccion,this.propietario,this.claseInmueble,this.tipoInmueble);
	
	HorizontalLayout actions = new HorizontalLayout(save, test, delete);
	addComponent(actions);
	actions.setSpacing(true);
		
    }

    public void setInmueble(Inmueble inmueble) {

	this.inmueble = inmueble;
	binderInmueble.readBean(inmueble);

	// Show delete button for only Persons already in the database
	delete.setVisible(inmueble.getId() != null);

	setVisible(true);
	getABMView().setComponentsVisible(false);
	if (getABMView().isIsonMobile())
	    this.focus();

    }

    private void delete() {
//	service.delete(persona);
//	inmuebleABMView.getController().updateList();
//	setVisible(false);
//	getAddressbookView().setComponentsVisible(true);
//	getAddressbookView().showSuccessNotification("Borrado: " + persona.getNombre() + " " +
//		persona.getApellido());

    }

    private void test() {
//	nombre.setValue(DummyDataGenerator.randomFirstName());
//	apellido.setValue(DummyDataGenerator.randomLastName());
//	mail.setValue(nombre.getValue() + "@" + apellido.getValue() + ".com");
//	DNI.setValue(DummyDataGenerator.randomNumber(8));
//	telefono.setValue(DummyDataGenerator.randomPhoneNumber());
//	telefono2.setValue(DummyDataGenerator.randomPhoneNumber());
//	String info = DummyDataGenerator.randomText(80);
//	if (info.length() > 255) {
//	    info = info.substring(0, 255);
//
//	}
//	infoAdicional.setValue(info);
//
//	save();
//
    }

    private void save() {
//
//	boolean success = false;
//	try {
//	    binderPersona.writeBean(persona);
//	    service.save(persona);
//	    success = true;
//
//	} catch (ValidationException e) {
//	    e.printStackTrace();
//	    Notification.show("Error al guardar, porfavor revise los campos e intente de nuevo");
//	    // Notification.show("Error: "+e.getCause());
//	    return;
//	} catch (Exception e) {
//	    e.printStackTrace();
//	    Notification.show("Error: " + e.toString());
//	}
//
//	inmuebleABMView.getController().updateList();
//	/*
//	 * String msg = String.format("Guardado '%s %s'.", persona.getNombre(),
//	 * persona.getApellido());* Notification.show(msg, Type.TRAY_NOTIFICATION);
//	 */
//	setVisible(false);
//	getAddressbookView().setComponentsVisible(true);
//
//	if (success)
//	    getAddressbookView().showSuccessNotification("Guardado: " + persona.getNombre() + " " +
//		    persona.getApellido());
//
    }

    public void cancel() {
	inmuebleABMView.getController().updateList();
	setVisible(false);
	getABMView().setComponentsVisible(true);
    }

    public InmuebleABMView getABMView() {
	return inmuebleABMView;
    }

}
