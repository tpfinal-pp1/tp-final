package com.TpFinal.view.calendario;

import com.TpFinal.dto.cita.Cita;
import com.TpFinal.dto.cita.TipoCita;

import com.TpFinal.dto.persona.Credencial;
import com.TpFinal.dto.persona.Empleado;
import com.TpFinal.services.CitaService;
import com.TpFinal.services.CredencialService;
import com.TpFinal.services.PersonaService;
import com.TpFinal.view.component.BlueLabel;
import com.TpFinal.view.component.DeleteButton;
import com.vaadin.data.*;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

import java.time.LocalDateTime;
import java.util.Arrays;

@SuppressWarnings("serial")
public abstract class PreferenciasCitaFormWindow extends Window {

    public static final String ID = "profilepreferenceswindow";

    /*
     * Fields for editing the User object are defined here as class members. They
     * are later bound to a FieldGroup by calling fieldGroup.bindMemberFields(this).
     * The Fields' values don't need to be explicitly set, calling
     * fieldGroup.setItemDataSource(user) synchronizes the fields with the user
     * object.
     */
    protected HorizontalLayout root = new HorizontalLayout();
    protected Empleado empleado;
    private Binder<Empleado> binderEmpleado = new Binder<>(Empleado.class);
    protected CitaService citaService = new CitaService();
    protected PersonaService personaService = new PersonaService();
    BlueLabel titulo = new BlueLabel("Cant. de hs antes para recordar");
    private TextField hsrecordatorio1 = new TextField("Recordatorio 1");
    private TextField hsrecordatorio2 = new TextField("Recordatorio 2");

    public PreferenciasCitaFormWindow(Empleado emp) {
	this.empleado = emp;
	configureComponents();
	binding();
	setEmpleado(empleado);
    }

    public abstract void onSave();

    private void configureComponents() {

	addStyleName("profile-window");
	setId(ID);
	Responsive.makeResponsive(this);

	setModal(true);
	setCloseShortcut(KeyCode.ESCAPE, null);
	setResizable(false);
	setClosable(true);
	setHeight(200f, Unit.PERCENTAGE);

	FormLayout content = new FormLayout();
	content.setSizeFull();
	content.setMargin(new MarginInfo(true, false, false, false));
	content.setSpacing(false);
	setContent(content);

	TabSheet detailsWrapper = new TabSheet();
	detailsWrapper.setSizeFull();
	detailsWrapper.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
	detailsWrapper.addStyleName(ValoTheme.TABSHEET_ICONS_ON_TOP);
	detailsWrapper.addStyleName(ValoTheme.TABSHEET_CENTERED_TABS);
	content.addComponent(detailsWrapper);
	content.setExpandRatio(detailsWrapper, 1f);

	detailsWrapper.addComponent(buildProfileTab());
	content.addComponent(buildFooter());
	setWidth(400.0f, Unit.PIXELS);
	center();
	getUI().getCurrent().addWindow(this);
	focus();
	this.addCloseListener(new CloseListener() {
	    @Override
	    public void windowClose(CloseEvent closeEvent) {
		onSave();
	    }
	});

    }

    private void binding() {
	binderEmpleado.forField(this.hsrecordatorio1).asRequired("Debe ingresar una cantidad de horas")
		.withConverter(new StringToIntegerConverter("Debe Ingresar un número entero!"))
		.withValidator(n -> n >= 0, "Ingrese un número no negativo!")
		.bind(Empleado::getHorasAntesRecoradatorio1, Empleado::setHorasAntesRecoradatorio2);
	binderEmpleado.forField(this.hsrecordatorio2).asRequired("Debe ingresar una cantidad de horas")
		.withConverter(new StringToIntegerConverter("Debe Ingresar un número entero!"))
		.withValidator(n -> n >= 0, "Ingrese un número no negativo!")
		.bind(Empleado::getHorasAntesRecoradatorio2, Empleado::setHorasAntesRecoradatorio2);
    }

    private void setEmpleado(Empleado empleado) {
	this.empleado = empleado;
	binderEmpleado.readBean(empleado);
	setVisible(true);
    }

    private void save() {
	boolean success = false;
	try {
	    binderEmpleado.writeBean(empleado);
	    success = personaService.saveOrUpdate(empleado.getPersona());
	    onSave();
	} catch (ValidationException e) {
	    Notification.show("Error al guardar, por favor revise los campos e intente de nuevo");
	    e.printStackTrace();
	    System.out.println(e.getValidationErrors() + " " +
		    e.getFieldValidationErrors());
	    return;
	}

	if (success) {
	    Notification exito = new Notification(
		    "Preferencias guardadas");
	    exito.setDelayMsec(2000);
	    exito.setStyleName("bar success small");
	    exito.setPosition(Position.BOTTOM_CENTER);
	    exito.show(Page.getCurrent());
	    close();
	} else {
	    Notification error = new Notification(
		    "Error al guardar las preferencias");
	    error.setDelayMsec(2000);
	    error.setStyleName("bar error small");
	    error.setPosition(Position.BOTTOM_CENTER);
	    error.show(Page.getCurrent());
	    close();
	}

    }

    private Component buildFooter() {

	HorizontalLayout footer = new HorizontalLayout();
	footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
	footer.setWidth(100.0f, Unit.PERCENTAGE);
	footer.setSpacing(false);

	Button ok = new Button("Guardar");
	ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
	ok.addClickListener(new ClickListener() {
	    @Override
	    public void buttonClick(ClickEvent event) {
		save();

	    }
	});
	ok.focus();
	footer.addComponent(ok);

	footer.setComponentAlignment(ok, Alignment.TOP_RIGHT);
	return footer;
    }

    private Component buildProfileTab() {

	root.setCaption("Parámetros de Citas - Agente Inmobiliario");
	root.setIcon(VaadinIcons.LIST);
	root.setWidth(100.0f, Unit.PERCENTAGE);
	// root.setHeight(500.0f, Unit.PERCENTAGE);
	root.setMargin(true);
	root.addStyleName("profile-form");

	FormLayout details = new FormLayout();
	details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
	root.addComponent(details);
	root.setExpandRatio(details, 1);

	details.addComponent(titulo);
	details.addComponent(hsrecordatorio1);
	details.addComponent(hsrecordatorio2);
	return root;
    }

}
