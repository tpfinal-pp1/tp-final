package com.TpFinal.view.calendario;

import com.TpFinal.dto.cita.Cita;
import com.TpFinal.dto.contrato.Contrato;
import com.TpFinal.dto.persona.Persona;
import com.TpFinal.services.CitaService;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;
import javafx.scene.control.DatePicker;
import org.apache.lucene.document.DateTools;

import java.time.ZoneId;
import java.util.Date;

@SuppressWarnings("serial")
public abstract class CitaFormWindow extends Window {

    public static final String ID = "profilepreferenceswindow";


    /*
     * Fields for editing the User object are defined here as class members.
     * They are later bound to a FieldGroup by calling
     * fieldGroup.bindMemberFields(this). The Fields' values don't need to be
     * explicitly set, calling fieldGroup.setItemDataSource(user) synchronizes
     * the fields with the user object.
     */
    protected HorizontalLayout root = new HorizontalLayout();
    protected  Cita cita;
    protected  Binder<Cita> binderCita = new Binder<>(Cita.class);
    protected CitaService service = new CitaService();
    protected DateTimeField fechaInicio= new DateTimeField();
    protected DateTimeField fechaFin= new DateTimeField();

    protected TextField citado = new TextField("Citado");
    protected TextField direccionLugar = new TextField("Dirección");
    protected TextArea observaciones = new TextArea("Info");

    public CitaFormWindow(Cita p) {
        this.cita =p;
        configureComponents();
        binding();
        setCita(cita);
    }
    public abstract void onSave();
    private void configureComponents(){


        addStyleName("profile-window");
        setId(ID);
        Responsive.makeResponsive(this);

       // setModal(true);
        setCloseShortcut(KeyCode.ESCAPE, null);
     //   setResizable(false);
     //   setClosable(true);
       // setHeight(90.0f, Unit.PERCENTAGE);

        VerticalLayout content = new VerticalLayout();
      //  content.setSizeFull();
      //  content.setMargin(new MarginInfo(true, false, false, false));
     //   content.setSpacing(false);
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
        citado.setRequiredIndicatorVisible(true);

        direccionLugar.setRequiredIndicatorVisible(true);

        binderCita.forField(citado).asRequired("Ingrese un citado").
                bind(Cita::getCitado,Cita::setCitado);

        binderCita.forField(direccionLugar).asRequired("Ingrese un Lugar/Direccion").
                bind(Cita::getDireccionLugar,Cita::setDireccionLugar);


        binderCita.forField(observaciones).
                bind(Cita::getObservaciones,Cita::setObservaciones);
        binderCita.forField(this.fechaInicio).asRequired("Seleccione una fecha de inicio")
                .bind(Cita::getFechaInicio,Cita::setFechaInicio);
        binderCita.forField(this.fechaFin).asRequired("Seleccione una fecha de fin")
                .bind(Cita::getFechaFin,Cita::setFechaFin);

    }

    private void setCita(Cita cita) {

        this.cita = cita;
        binderCita.readBean(cita);
        // Show delete button for only Persons already in the database

        setVisible(true);
        citado.selectAll();


    }
    private void save(){
        boolean success=false;
        try {
            binderCita.writeBean(cita);
            service.addCita(cita);
            onSave();
            success=true;


        } catch (ValidationException e) {
            Notification.show("Error al guardar, por favor revise los campos e intente de nuevo");
            e.printStackTrace();
            System.out.println( e.getValidationErrors()+" "+e.getFieldValidationErrors());

            return;
        }


        if(success) {
            Notification exito = new Notification(
                    "Guardado: " + cita.getDetails());
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

        root.setCaption("Cita");
        root.setIcon(VaadinIcons.USER);
        root.setWidth(100.0f, Unit.PERCENTAGE);
        root.setMargin(true);
        root.addStyleName("profile-form");

        FormLayout details = new FormLayout();
        details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        root.addComponent(details);
        root.setExpandRatio(details, 1);

        details.addComponent(citado);
        details.addComponent(direccionLugar);
        details.addComponent(fechaInicio);
        details.addComponent(fechaFin);
        fechaInicio.setCaption("Inicio");
        fechaFin.setCaption("Fin");



        Label section = new Label("Información Adicional");
        section.addStyleName(ValoTheme.LABEL_H4);
        section.addStyleName(ValoTheme.LABEL_COLORED);
        details.addComponent(section);
        observaciones.setWidth("100%");
        observaciones.setRows(4);
        details.addComponent(observaciones);

        return root;
    }

}
