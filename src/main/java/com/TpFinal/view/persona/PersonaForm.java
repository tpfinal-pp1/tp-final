package com.TpFinal.view.persona;

import com.TpFinal.dto.contrato.ContratoVenta;
import com.TpFinal.dto.persona.Calificacion;
import com.TpFinal.dto.persona.Inquilino;
import com.TpFinal.dto.persona.Persona;
import com.TpFinal.dto.publicacion.PublicacionAlquiler;
import com.TpFinal.dto.publicacion.PublicacionVenta;
import com.TpFinal.services.PersonaService;
import com.TpFinal.utils.DummyDataGenerator;
import com.TpFinal.utils.Utils;
import com.TpFinal.view.component.BlueLabel;
import com.TpFinal.view.component.DeleteButton;
import com.TpFinal.view.component.PreferenciasBusqueda;
import com.TpFinal.view.component.TinyButton;
import com.vaadin.data.Binder;
import com.vaadin.data.BindingValidationStatus;
import com.vaadin.data.HasValue;
import com.vaadin.data.ValidationException;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.ArrayList;
import java.util.List;

public class PersonaForm extends FormLayout {
    private Persona persona;
    private Button save = new Button("Guardar");
    // Button test = new Button("Test");
    private DeleteButton delete = new DeleteButton("Eliminar",
	    VaadinIcons.WARNING, "Eliminar", "20%", new Button.ClickListener() {
		@Override
		public void buttonClick(Button.ClickEvent clickEvent) {
		    delete();
		}
	    });
    private Button cargarPrefsBusqueda = new Button("Pref. Busqueda");

    private CheckBox cbEsInmobiliaria = new CheckBox(null);
    private TextField nombre = new TextField("Nombre");
    private TextField apellido = new TextField("Apellido");
    private TextField DNI = new TextField("DNI");
    private TextField telefono = new TextField("Telefono");
    private TextField telefono2 = new TextField("Celular");
    private TextField mail = new TextField("Mail");
    private TextArea infoAdicional = new TextArea("Info");
    private ContratoVenta aSeleccionar;
    private TextField calificacion = new TextField("Calificacion Inquilino");

    // private NativeSelect<Persona.Sexo> sexo = new NativeSelect<>("Sexo");

    PersonaService personaService = new PersonaService();
    private PersonaABMView addressbookView;
    private Binder<Persona> binderPersona = new Binder<>(Persona.class);

    // TabSheet
    private FormLayout principal;
    private FormLayout adicional;
    private TabSheet personaFormTabSheet;

    // Easily binding forms to beans and manage validation and buffering

    public PersonaForm(PersonaABMView addressbook) {
	// setSizeUndefined();

	addressbookView = addressbook;
	configureComponents();
	binding();
	buildLayout();

    }

    private void configureComponents() {
	delete.setStyleName(ValoTheme.BUTTON_DANGER);
	save.addClickListener(e -> this.save());
	save.setStyleName(ValoTheme.BUTTON_PRIMARY);
	save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
	cargarPrefsBusqueda.setIcon(VaadinIcons.SEARCH_MINUS);
	cargarPrefsBusqueda.addClickListener(click -> {
	    new PreferenciasBusqueda(persona.getPrefBusqueda()) {
		@Override
		public boolean onSave() {
		    persona.setPrefBusqueda(getCriterio());
		    addressbookView.showSuccessNotification("Preferencias de búsqueda establecidas");
		    close();
		    return false;		    
		}

		@Override
		public boolean onClean() {
		    return true;
		}

		@Override
		public boolean searchVisible() {
		    return true;
		}

	    };
	});
	cbEsInmobiliaria.addValueChangeListener(new HasValue.ValueChangeListener<Boolean>() {
	    @Override
	    public void valueChange(HasValue.ValueChangeEvent<Boolean> valueChangeEvent) {

		if (valueChangeEvent.getValue()) {
		    nombre.setValue("Inmobiliaria");
		    nombre.setVisible(false);
		    nombre.setRequiredIndicatorVisible(false);
		    apellido.setCaption("Nombre");
		    DNI.setVisible(false);
		    DNI.setValue("");
		}

		else {
		    if (valueChangeEvent.isUserOriginated())
			nombre.setValue("");
		    nombre.setVisible(true);
		    nombre.setRequiredIndicatorVisible(true);
		    apellido.setCaption("Apellido");
		    DNI.setVisible(true);

		}

	    }
	});
	setVisible(false);
    }
	private Button configurarBtnCalcularCalificaciones() {
		Button btnCalcularCalificaciones = new Button ("Calcular calificaciones");
		btnCalcularCalificaciones.setVisible(true);
		btnCalcularCalificaciones.addClickListener(event -> {
			personaService.readAll().forEach(persona -> {
				personaService.calificarInquilino(persona);
				personaService.saveOrUpdate(persona);
			});
		});
		return btnCalcularCalificaciones;
	}
    private void binding() {
	// binder.bindInstanceFields(this); //Binding automatico
	nombre.setRequiredIndicatorVisible(true);
	apellido.setRequiredIndicatorVisible(true);
	mail.setRequiredIndicatorVisible(true);
	telefono.setRequiredIndicatorVisible(true);
	DNI.setRequiredIndicatorVisible(false);
	binderPersona.forField(nombre).asRequired("Ingrese un nombre").bind(Persona::getNombre, Persona::setNombre);

	binderPersona.forField(apellido).asRequired("Ingrese un apellido").bind(Persona::getApellido,
		Persona::setApellido);

	binderPersona.forField(DNI).bind(Persona::getDNI, Persona::setDNI);

	binderPersona.forField(telefono).asRequired("Ingrese un teléfono").bind(Persona::getTelefono,
		Persona::setTelefono);

	binderPersona.forField(telefono2).bind(Persona::getTelefono2, Persona::setTelefono2);

	binderPersona.forField(mail).withValidator(new EmailValidator(
		"Introduzca un email valido!")).bind(Persona::getMail, Persona::setMail);

	binderPersona.forField(infoAdicional).bind(Persona::getInfoAdicional, Persona::setInfoAdicional);

	binderPersona.forField(cbEsInmobiliaria).bind(Persona::getEsInmobiliaria, Persona::setEsInmobiliaria);
	binderPersona.forField(calificacion).bind(persona -> {
		calificacion.setVisible(false);
		if(persona.getInquilino()!=null){
			Inquilino inquilino=persona.getInquilino();
			if(inquilino.getCalificacion()!=null) {
				calificacion.setVisible(true);
				return inquilino.getCalificacion().toString();

			}

		}
		return "";
		}, null);

    }

    private void buildLayout() {
	setSizeFull();
	setMargin(true);

	personaFormTabSheet = new TabSheet();

	BlueLabel Publicaciones = new BlueLabel("Operaciones");
	BlueLabel info = new BlueLabel("Información Adicional");
	BlueLabel contacto = new BlueLabel("Contacto");

	TinyButton contratos = new TinyButton("Ver Contratos");
	contratos.setEnabled(false);
	TinyButton busquedas = new TinyButton("Ver Busquedas");
	busquedas.setEnabled(false);

	aSeleccionar = new ContratoVenta();
	/*
	 * contratos.addClickListener(e -> new PersonaFormWindow(new Persona()));
	 */
	VerticalLayout Roles = new VerticalLayout(calificacion,configurarBtnCalcularCalificaciones());

	HorizontalLayout checkboxInm = new HorizontalLayout(cbEsInmobiliaria);
	checkboxInm.setCaption("Inmobiliaria");

	principal = new FormLayout(checkboxInm, nombre, apellido, DNI, contacto, mail, telefono, telefono2);
	adicional = new FormLayout(
		Publicaciones, Roles);
	adicional.addComponent(info);
	adicional.addComponent(infoAdicional);

	principal.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
	adicional.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);

	calificacion.setEnabled(false);
	calificacion.setStyleName(ValoTheme.COMBOBOX_BORDERLESS);

	personaFormTabSheet.addTab(principal, "Principal");
	personaFormTabSheet.addTab(adicional, "Adicional");

	addComponent(personaFormTabSheet);
	// HorizontalLayout actions = new HorizontalLayout(save,test,delete);
	HorizontalLayout actions = new HorizontalLayout(save, delete, cargarPrefsBusqueda);
	addComponent(actions);
	this.setSpacing(false);
	actions.setSpacing(true);

	// addStyleName("v-scrollable");

    }

    public void setPersona(Persona persona) {


	this.persona = persona;
	binderPersona.readBean(persona);

	// Show delete button for only Persons already in the database
	delete.setVisible(persona.getId() != null);

	setVisible(true);
	getAddressbookView().setComponentsVisible(false);
	nombre.selectAll();
	if (getAddressbookView().isIsonMobile())
	    personaFormTabSheet.focus();

    }

    private void delete() {
	personaService.delete(persona);
	addressbookView.updateList();
	setVisible(false);
	getAddressbookView().setComponentsVisible(true);
	getAddressbookView().showSuccessNotification("Borrado: " + persona.getNombre() + " " +
		persona.getApellido());

    }

    private void test() {
	nombre.setValue(DummyDataGenerator.randomFirstName());
	apellido.setValue(DummyDataGenerator.randomLastName());
	mail.setValue(nombre.getValue() + "@" + apellido.getValue() + ".com");
	DNI.setValue(DummyDataGenerator.randomNumber(8));
	telefono.setValue(DummyDataGenerator.randomPhoneNumber());
	telefono2.setValue(DummyDataGenerator.randomPhoneNumber());
	String info = DummyDataGenerator.randomText(80);
	if (info.length() > 255) {
	    info = info.substring(0, 255);

	}
	infoAdicional.setValue(info);

	save();

    }

    private void save() {

	boolean success = false;
	try {
	    binderPersona.writeBean(persona);
	    personaService.saveOrUpdate(persona);
	    success = true;

	} catch (ValidationException e) {
	    Utils.mostarErroresValidator(e);
	    checkFieldsPerTab(e.getFieldValidationErrors());

	    return;
	} catch (Exception e) {
	    e.printStackTrace();
	    Notification.show("Error: " + e.toString());
	}

	addressbookView.updateList();
	/*
	 * String msg = String.format("Guardado '%s %s'.", persona.getNombre(),
	 * persona.getApellido());* Notification.show(msg, Type.TRAY_NOTIFICATION);
	 */
	setVisible(false);
	getAddressbookView().setComponentsVisible(true);

	if (success)
	    getAddressbookView().showSuccessNotification("Guardado: " + persona.getNombre() + " " +
		    persona.getApellido());

    }

    public void cancel() {
	addressbookView.updateList();
	setVisible(false);
	getAddressbookView().setComponentsVisible(true);
    }

    public PersonaABMView getAddressbookView() {
	return addressbookView;
    }

    private void checkFieldsPerTab(List<BindingValidationStatus<?>> invalidComponents) {
	boolean tabPrincipalInvalidFields = false;
	boolean tabConditionsInvalidFields = false;
	// TabElements for tab principal
	List<Component> tabPrincipalComponents = new ArrayList<Component>();
	tabPrincipalComponents.add(nombre);
	tabPrincipalComponents.add(apellido);
	tabPrincipalComponents.add(DNI);
	tabPrincipalComponents.add(mail);
	tabPrincipalComponents.add(telefono);
	tabPrincipalComponents.add(telefono2);

	for (BindingValidationStatus invalidField : invalidComponents) {
	    tabPrincipalInvalidFields = tabPrincipalComponents.contains(invalidField.getField());
	    if (tabPrincipalInvalidFields)
		break;
	}
	System.out.println(tabPrincipalInvalidFields);

	// Tab elements for tab caracteristicas
	List<Component> tabConditionsComponents = new ArrayList<Component>();
	tabConditionsComponents.add(calificacion);

	for (BindingValidationStatus invalidField : invalidComponents) {
	    tabConditionsInvalidFields = tabConditionsComponents.contains(invalidField.getField());
	    if (tabConditionsInvalidFields)
		break;
	}
	System.out.println(tabConditionsInvalidFields);

	// Take user to the invalid components tag (in case there's only one)
	if (tabPrincipalInvalidFields && !tabConditionsInvalidFields) {
	    Notification.show("Error al guardar, por favor revise los campos principales",
		    Notification.Type.WARNING_MESSAGE);
	    personaFormTabSheet.setSelectedTab(principal);
	} else if (!tabPrincipalInvalidFields && tabConditionsInvalidFields) {
	    Notification.show("Error al guardar, por favor revise las condiciones del contrato e intente de nuevo",
		    Notification.Type.WARNING_MESSAGE);
	    personaFormTabSheet.setSelectedTab(adicional);
	} else {
	    Notification.show("Error al guardar, por favor revise los campos e intente de nuevo",
		    Notification.Type.WARNING_MESSAGE);
	}
    }

}