package com.TpFinal.view.persona;

import com.TpFinal.data.dto.dummy.User;
import com.TpFinal.data.dto.persona.Persona;
import com.TpFinal.services.DashboardEvent;
import com.TpFinal.services.DashboardEventBus;
import com.TpFinal.services.PersonaService;
import com.vaadin.annotations.PropertyId;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.UserError;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.v7.data.fieldgroup.BeanFieldGroup;
import com.vaadin.v7.data.fieldgroup.FieldGroup.CommitException;

import java.util.Arrays;

@SuppressWarnings("serial")
public abstract class PersonaFormWindow extends Window {

    public static final String ID = "profilepreferenceswindow";


    /*
     * Fields for editing the User object are defined here as class members.
     * They are later bound to a FieldGroup by calling
     * fieldGroup.bindMemberFields(this). The Fields' values don't need to be
     * explicitly set, calling fieldGroup.setItemDataSource(user) synchronizes
     * the fields with the user object.
     */
    private Persona persona;
    private Binder<Persona> binderPersona = new Binder<>(Persona.class);
    PersonaService service = new PersonaService();
    TextField nombre = new TextField("Nombre");
    TextField apellido = new TextField("Apellido");
    TextField DNI = new TextField("DNI");
    TextField telefono = new TextField("Telefono");
    TextField telefono2 = new TextField("Celular");
    TextField mail = new TextField("Mail");
    TextArea infoAdicional = new TextArea("Info");

    public PersonaFormWindow(Persona p) {
        this.persona=p;
        configureComponents();
        binding();
        setPersona(persona);
        UI.getCurrent().addWindow(this);
        this.focus();
    }
    public abstract void onSave();
    private void configureComponents(){


        addStyleName("profile-window");
        setId(ID);
        Responsive.makeResponsive(this);

        setModal(true);
        setCloseShortcut(KeyCode.ESCAPE, null);
        setResizable(false);
        setClosable(true);
        setHeight(90.0f, Unit.PERCENTAGE);

        VerticalLayout content = new VerticalLayout();
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
        //  detailsWrapper.addComponent(buildPreferencesTab());

        content.addComponent(buildFooter());

    }

    private void binding(){
        //binder.bindInstanceFields(this); //Binding automatico
        nombre.setRequiredIndicatorVisible(true);
        apellido.setRequiredIndicatorVisible(true);
        mail.setRequiredIndicatorVisible(true);
        binderPersona.forField(nombre).withValidator(new StringLengthValidator(
                "El nombre debe estar entre 2 y 20 caracteres",
                2, 20)).bind(Persona::getNombre,Persona::setNombre);

        binderPersona.forField(apellido).withValidator(new StringLengthValidator(
                "El nombre debe estar entre 2 y 20 caracteres",
                2, 20)).bind(Persona::getApellido,Persona::setApellido);

        binderPersona.forField(DNI)./*withValidator(new StringLengthValidator(
                "El DNI de estar entre 2 y 20 caracteres",
                2, 20)).*/bind(Persona::getDNI,Persona::setDNI);
        binderPersona.forField(telefono).bind(Persona::getTelefono,Persona::setTelefono);
        binderPersona.forField(telefono2).bind(Persona::getTelefono2,Persona::setTelefono2);
        binderPersona.forField(mail).withValidator(new EmailValidator(
                "Introduzca un email valido!"
        )).bind(Persona::getMail,Persona::setMail);
        binderPersona.forField(infoAdicional).withValidator(new StringLengthValidator(
                "El nombre debe estar entre 2 y 20 caracteres",
                0, 255)).bind(Persona::getInfoAdicional,Persona::setInfoAdicional);

    }

    private void setPersona(Persona persona) {

        this.persona = persona;
        binderPersona.readBean(persona);
        // Show delete button for only Persons already in the database

        setVisible(true);
        nombre.selectAll();


    }
    private void save(){
        boolean success=false;
        try {
            binderPersona.writeBean(persona);
            service.saveOrUpdate(persona);
            onSave();
            success=true;


        } catch (ValidationException e) {
            Notification.show("Error al guardar, porfavor revise los campos e intente de nuevo");
            // Notification.show("Error: "+e.getCause());
            return;
        }


        if(success) {
            Notification exito = new Notification(
                    "Guardado: " + persona.getNombre() + " " +
                            persona.getApellido());
            exito.setDelayMsec(2000);
            exito.setStyleName("bar success small");
            exito.setPosition(Position.BOTTOM_CENTER);
            exito.show(Page.getCurrent());
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
        HorizontalLayout root = new HorizontalLayout();
        root.setCaption("Persona");
        root.setIcon(VaadinIcons.USER);
        root.setWidth(100.0f, Unit.PERCENTAGE);
        root.setMargin(true);
        root.addStyleName("profile-form");

        FormLayout details = new FormLayout();
        details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        root.addComponent(details);
        root.setExpandRatio(details, 1);

        nombre = new TextField("Nombre");
        details.addComponent(nombre);
        apellido = new TextField("Apellido");
        details.addComponent(apellido);
        DNI = new TextField("DNI");
        details.addComponent(DNI);

        Label section = new Label("Contacto");
        section.addStyleName(ValoTheme.LABEL_H4);
        section.addStyleName(ValoTheme.LABEL_COLORED);
        details.addComponent(section);

        mail = new TextField("Email");
        mail.setWidth("100%");
        mail.setRequiredIndicatorVisible(true);
        // TODO add validation that not empty, use binder
        details.addComponent(mail);

        telefono = new TextField("Teléfono");
        telefono.setWidth("100%");
        details.addComponent(telefono);

        telefono2 = new TextField("Celular");
        telefono2.setWidth("100%");
        details.addComponent(telefono2);

        section = new Label("Información Adicional");
        section.addStyleName(ValoTheme.LABEL_H4);
        section.addStyleName(ValoTheme.LABEL_COLORED);
        details.addComponent(section);

        infoAdicional = new TextArea("Info");
        infoAdicional.setWidth("100%");
        infoAdicional.setRows(4);
        details.addComponent(infoAdicional);

        return root;
    }




}
