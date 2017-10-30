package com.TpFinal.view.empleados;

import java.util.Arrays;

import org.apache.log4j.Logger;

import com.TpFinal.dto.persona.CategoriaEmpleado;
import com.TpFinal.dto.persona.Credencial;
import com.TpFinal.dto.persona.Empleado;
import com.TpFinal.dto.persona.Persona;
import com.TpFinal.services.CredencialService;
import com.TpFinal.services.PersonaService;
import com.TpFinal.utils.Utils;
import com.TpFinal.view.component.BlueLabel;
import com.TpFinal.view.component.DeleteButton;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

public class EmpleadoForm extends FormLayout {

    private Empleado empleado;
    private Credencial credencial;
    private final static Logger logger = Logger.getLogger(EmpleadoForm.class);

    Button save = new Button("Guardar");
    DeleteButton delete = new DeleteButton("Eliminar",
	    VaadinIcons.WARNING, "Eliminar", "20%", new Button.ClickListener() {
		@Override
		public void buttonClick(Button.ClickEvent clickEvent) {
		    delete();
		}
	    });

    private TextField nombre = new TextField("Nombre");
    private TextField apellido = new TextField("Apellido");
    private TextField DNI = new TextField("DNI");
    private TextField telefono = new TextField("Telefono");
    private TextField telefono2 = new TextField("Celular");
    private TextField mail = new TextField("Mail");
    private TextArea infoAdicional = new TextArea("Info Adicional");

    // DatosAdministrativos
    private ComboBox<CategoriaEmpleado> cbCategoria = new ComboBox<>("Categoría", Arrays.asList(CategoriaEmpleado
	    .values()));
    private BlueLabel blCredenciales = new BlueLabel("Credenciales");
    private TextField tfNombreUsuario = new TextField("Nombre Usuario");
    private PasswordField pfPassIngreso = new PasswordField("Password");
    private PasswordField pfPassConfirmacion = new PasswordField("Confirmacion Password");
    // XXX
    PersonaService service = new PersonaService();
    CredencialService credencialService = new CredencialService();

    private EmpleadoABMView addressbookView;
    private Binder<Empleado> binderEmpleado = new Binder<>(Empleado.class);
    private Binder<Credencial> binderCredencial = new Binder<>(Credencial.class);
    TabSheet tabSheet;
    // TabSheet
    private FormLayout datosPersonales;
    private FormLayout datosAdministativos;

    // Easily binding forms to beans and manage validation and buffering

    public EmpleadoForm(EmpleadoABMView addressbook) {
	// setSizeUndefined();

	addressbookView = addressbook;
	configureComponents();
	binding();
	buildLayout();

    }

    private void configureComponents() {
	/*
	 * Highlight primary actions.
	 *
	 * With Vaadin built-in styles you can highlight the primary save button
	 *
	 * and give it a keyoard shortcut for a better UX.
	 */

	// tfNombreUsuario.addValueChangeListener( e -> {
	//
	// })
	cbCategoria.setEmptySelectionAllowed(false);
	
	cbCategoria.addValueChangeListener(e ->{
	    if (e.getValue() == CategoriaEmpleado.sinCategoria) {
		pfPassConfirmacion.setEnabled(false);
		pfPassIngreso.setEnabled(false);
		tfNombreUsuario.setEnabled(false);
		pfPassConfirmacion.clear();
		pfPassIngreso.clear();
		tfNombreUsuario.clear();
	    }
	    else {
		pfPassConfirmacion.setEnabled(true);
		pfPassIngreso.setEnabled(true);
		tfNombreUsuario.setEnabled(true);
		if (empleado.getCredencial() != null)
		    binderCredencial.readBean(empleado.getCredencial());
	    }
	});

	pfPassConfirmacion.addValueChangeListener(e -> {
	    binderCredencial.validate();
	});
	pfPassIngreso.addValueChangeListener(e -> {
	    binderCredencial.validate();
	});
	tfNombreUsuario.addValueChangeListener(e -> {
	    binderCredencial.validate();
	});

	delete.setStyleName(ValoTheme.BUTTON_DANGER);
	save.addClickListener(e -> this.save());

	save.setStyleName(ValoTheme.BUTTON_PRIMARY);
	save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
	setVisible(false);
    }

    private void binding() {
	bindearDatosPersonales();
	binderEmpleado.forField(cbCategoria)
		.asRequired("Seleccione una categoría")
		.bind(Empleado::getCategoriaEmpleado, Empleado::setCategoriaEmpleado);
	bindearCredencial();
    }

    private void bindearCredencial() {
	binderCredencial.forField(tfNombreUsuario)
		.withValidator(texto -> {
		    boolean ret = false;
		    // Devolver True solo si hay un password ingresado
		    if (!this.pfPassIngreso.isEmpty())
			ret = true;
		    // Tambien devolver true si todos los campos son empty(no se ingreso una
		    // credencial)
		    if (tfNombreUsuario.isEmpty() && pfPassConfirmacion.isEmpty() && pfPassIngreso.isEmpty())
			ret = true;
		    return ret;
		}, "Debe Ingresar un password")
		.withValidator(texto -> {
		    boolean ret = false;
		    if (!credencialService.existeUsuario(texto))
			ret = true;
		    // si la credencial existe pero es la de este empleado, devolver true
		    if (empleado.getCredencial() != null &&
			    empleado.getCredencial().getUsuario() != null &&
			    empleado.getCredencial().getUsuario().equals(texto))
			ret = true;
		    if (tfNombreUsuario.isEmpty() && pfPassConfirmacion.isEmpty() && pfPassIngreso.isEmpty())
			ret = true;
		    return ret;
		}, "El nombre de usuario ya esta registrado en el sistema")
		.withValidator(texto -> {
		    boolean ret = true;
		    if (tfNombreUsuario.isEmpty() && (!pfPassConfirmacion.isEmpty() || !pfPassIngreso.isEmpty()))
			ret = false;
		    if (tfNombreUsuario.isEmpty() && pfPassConfirmacion.isEmpty() && pfPassIngreso.isEmpty())
			ret = true;
		    return ret;
		}, "Debe Ingresar un nombre de usuario")
		.bind(credencial -> {
		    return credencial != null ? credencial.getUsuario() : null;
		}, (credencial, input) -> {
		    if (credencial != null)
			credencial.setUsuario(input);
		});

	binderCredencial.forField(pfPassIngreso)
		.withValidator(texto -> {
		    boolean ret = false;
		    // Devolver True solo si la confirmación coincide
		    if (!this.pfPassIngreso.isEmpty() && pfPassIngreso.getValue().equals(pfPassConfirmacion.getValue()))
			ret = true;
		    // Tambien devolver true si todos los campos son empty(no se ingreso una
		    // credencial)
		    if (tfNombreUsuario.isEmpty() && pfPassConfirmacion.isEmpty() && pfPassIngreso.isEmpty())
			ret = true;
		    return ret;
		}, "Los passwords ingresados no coinciden")
		.bind(credencial -> {
		    return credencial != null ? credencial.getContrasenia() : null;
		}, (credencial, input) -> {
		    if (credencial != null)
			credencial.setContrasenia(input);
		});

	binderCredencial.forField(pfPassConfirmacion)
		.withValidator(texto -> {
		    boolean ret = false;
		    // Devolver True solo si la confirmación coincide
		    if (!this.pfPassConfirmacion.isEmpty() && pfPassConfirmacion.getValue().equals(pfPassIngreso
			    .getValue()))
			ret = true;
		    // Tambien devolver true si todos los campos son empty(no se ingreso una
		    // credencial)
		    if (tfNombreUsuario.isEmpty() && pfPassConfirmacion.isEmpty() && pfPassIngreso.isEmpty())
			ret = true;
		    return ret;
		}, "Los passwords ingresados no coinciden")
		.bind(credencial -> {
		    return credencial != null ? credencial.getContrasenia() : null;
		}, (credencial, input) -> {
		    if (credencial != null)
			credencial.setContrasenia(input);
		});
    }

    private void bindearDatosPersonales() {
	binderEmpleado.forField(nombre).asRequired("Ingrese un nombre")
		.bind(empleado -> {
		    return empleado.getPersona().getNombre();
		}, (empleado, nombre) -> {
		    empleado.getPersona().setNombre(nombre);
		});

	binderEmpleado.forField(apellido).asRequired("Ingrese un apellido").bind(empleado -> {
	    return empleado.getPersona().getApellido();
	}, (empleado, apellido) -> {
	    empleado.getPersona().setApellido(apellido);
	});

	binderEmpleado.forField(DNI).bind(empleado -> {
	    return empleado.getPersona().getDNI();
	}, (empleado, dni) -> {
	    empleado.getPersona().setDNI(dni);
	});

	binderEmpleado.forField(telefono).asRequired("Ingrese un teléfono")
		.bind(empleado -> {
		    return empleado.getPersona().getTelefono();
		}, (empleado, tel) -> {
		    empleado.getPersona().setTelefono(tel);
		});

	binderEmpleado.forField(telefono2)
		.bind(empleado -> {
		    return empleado.getPersona().getTelefono2();
		}, (empleado, tel) -> {
		    empleado.getPersona().setTelefono2(tel);
		});

	binderEmpleado.forField(mail)
		.withValidator(new EmailValidator("Introduzca un email valido!"))
		.bind(empleado -> {
		    return empleado.getPersona().getMail();
		}, (empleado, mail) -> {
		    empleado.getPersona().setMail(mail);
		});

	binderEmpleado.forField(infoAdicional)
		.bind(empleado -> {
		    return empleado.getPersona().getInfoAdicional();
		}, (empleado, info) -> {
		    empleado.getPersona().setInfoAdicional(info);
		});
    }

    private void buildLayout() {
	setSizeFull();
	setMargin(true);

	tabSheet = new TabSheet();

	datosPersonales = new FormLayout(nombre, apellido, DNI, mail, telefono, telefono2);
	datosPersonales.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
	datosAdministativos = new FormLayout(cbCategoria, blCredenciales, tfNombreUsuario, pfPassIngreso,
		pfPassConfirmacion, infoAdicional);
	datosAdministativos.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);

	tabSheet.addTab(datosPersonales, "Datos Personales");
	tabSheet.addTab(datosAdministativos, "Datos Administrativos");

	addComponent(tabSheet);
	HorizontalLayout actions = new HorizontalLayout(save, delete);
	addComponent(actions);
	this.setSpacing(false);
	actions.setSpacing(true);

	// addStyleName("v-scrollable");

    }

    public void setEmpleado(Empleado empleado) {
	clearFields();
	if (empleado != null) {
	    this.empleado = empleado;
	    binderEmpleado.readBean(this.empleado);
	    if (empleado.getCredencial() != null) {
		this.credencial = empleado.getCredencial();
		binderCredencial.readBean(credencial);
	    } else {
		credencial = new Credencial.Builder()
			.setEmpleado(empleado)
			.build();
		empleado.setCredencial(credencial);
	    }
	    delete.setVisible(true);
	} else {
	    this.empleado = PersonaService.getEmpleadoInstancia();
	    this.credencial = this.empleado.getCredencial();
	    delete.setVisible(false);
	}
	setVisible(true);
	getAddressbookView().setComponentsVisible(false);
	if (getAddressbookView().isIsonMobile())
	    this.focus();

    }

    private void delete() {

	// XXX
	service.delete(empleado.getPersona());
	addressbookView.updateList();
	setVisible(false);
	getAddressbookView().setComponentsVisible(true);
	getAddressbookView().showSuccessNotification("Borrado: " + empleado.toString());

    }

    private void save() {

	boolean success = false;
	try {
	    binderEmpleado.writeBean(empleado);
	    binderCredencial.writeBean(credencial);
	    if(binderCredencial.getFields().allMatch(p -> p.isEmpty())) {
		if (empleado.getCredencial() !=  null)
		credencialService.deepDelete(empleado.getCredencial());
		empleado.setCredencial(null);
	    }

	    // XXX
	    success = service.saveOrUpdate(empleado.getPersona());
	    
	} catch (ValidationException e) {
	    Utils.mostarErroresValidator(e);
	    Notification.show("Errores de validación, por favor revise los campos e intente de nuevo",
		    Notification.Type.WARNING_MESSAGE);

	    return;
	} catch (Exception e) {
	    e.printStackTrace();
	    Notification.show("Error: " + e.toString());
	}

	addressbookView.updateList();

	setVisible(false);
	getAddressbookView().setComponentsVisible(true);

	if (success)
	    getAddressbookView().showSuccessNotification("Guardado: " + empleado.toString());

    }

    public void cancel() {
	addressbookView.updateList();
	setVisible(false);
	getAddressbookView().setComponentsVisible(true);
    }

    public void clearFields() {
	binderCredencial.getFields().forEach(field -> field.clear());
	binderEmpleado.getFields().forEach(field -> field.clear());
    }

    public EmpleadoABMView getAddressbookView() {
	return addressbookView;
    }

}