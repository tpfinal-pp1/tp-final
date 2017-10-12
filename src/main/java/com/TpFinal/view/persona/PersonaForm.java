package com.TpFinal.view.persona;

import com.TpFinal.data.dto.contrato.ContratoVenta;
import com.TpFinal.data.dto.persona.Calificacion;
import com.TpFinal.data.dto.persona.Persona;
import com.TpFinal.services.PersonaService;
import com.TpFinal.utils.DummyDataGenerator;
import com.TpFinal.view.component.BlueLabel;
import com.TpFinal.view.component.DeleteButton;
import com.TpFinal.view.component.TinyButton;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;


public class PersonaForm extends FormLayout {
    private Persona persona;
    Button save = new Button("Guardar");
    //Button test = new Button("Test");
    DeleteButton delete = new DeleteButton("Eliminar",
            VaadinIcons.WARNING,"Eliminar","20%", new Button.ClickListener() {
        @Override
        public void buttonClick(Button.ClickEvent clickEvent) {
            delete();
        }
    });


    TextField nombre = new TextField("Nombre");
    TextField apellido = new TextField("Apellido");
    TextField DNI = new TextField("DNI");
    TextField telefono = new TextField("Telefono");
    TextField telefono2 = new TextField("Celular");
    TextField mail = new TextField("Mail");
    TextArea infoAdicional = new TextArea("Info");
    ContratoVenta aSeleccionar;
    private NativeSelect<Calificacion> calificacion =
            new NativeSelect<>("Calificacion Inquilino");

    // private NativeSelect<Persona.Sexo> sexo = new NativeSelect<>("Sexo");

    PersonaService service = new PersonaService();
    private PersonaABMView addressbookView;
    private Binder<Persona> binderPersona = new Binder<>(Persona.class);
    TabSheet tabSheet;

    // Easily binding forms to beans and manage validation and buffering


    public PersonaForm(PersonaABMView addressbook) {
        // setSizeUndefined();

        addressbookView=addressbook;
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

        calificacion.setItems(Calificacion.values());
        calificacion.setEmptySelectionAllowed(true);
        calificacion.setSelectedItem(Calificacion.A);
        delete.setStyleName(ValoTheme.BUTTON_DANGER);
        save.addClickListener(e -> this.save());
        //test.addClickListener(e -> this.test());

        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        setVisible(false);
    }


    private void binding(){
        //binder.bindInstanceFields(this); //Binding automatico
        nombre.setRequiredIndicatorVisible(true);
        apellido.setRequiredIndicatorVisible(true);
        mail.setRequiredIndicatorVisible(true);
        telefono.setRequiredIndicatorVisible(true);
        binderPersona.forField(nombre).asRequired("Ingrese un nombre").bind(Persona::getNombre,Persona::setNombre);

        binderPersona.forField(apellido).asRequired("Ingrese un apellido").bind(Persona::getApellido,Persona::setApellido);

        binderPersona.forField(DNI).asRequired("Ingrese un dni").withValidator(new RegexpValidator("No se pueden ingresar letras","[0-9]+")
        ).bind(Persona::getDNI,Persona::setDNI);


        binderPersona.forField(telefono).asRequired("Ingrese un teléfono").bind(Persona::getTelefono,Persona::setTelefono);

        binderPersona.forField(telefono2).bind(Persona::getTelefono2,Persona::setTelefono2);


        binderPersona.forField(mail).withValidator(new EmailValidator(
                "Introduzca un email valido!" )).bind(Persona::getMail,Persona::setMail);
        
        binderPersona.forField(infoAdicional).bind(Persona::getInfoAdicional,Persona::setInfoAdicional);

    }

    private void buildLayout() {
        setSizeFull();
        setMargin(true);

        tabSheet=new TabSheet();


        BlueLabel Publicaciones = new  BlueLabel("Operaciones");
        BlueLabel info = new  BlueLabel("Información Adicional");
        BlueLabel contacto = new  BlueLabel("Contacto");
        
        TinyButton contratos=new TinyButton("Ver Contratos");
        contratos.setEnabled(false);
        TinyButton busquedas= new TinyButton("Ver Busquedas");
        busquedas.setEnabled(false);


        aSeleccionar=new ContratoVenta();
     /*   contratos.addClickListener(e ->
                new PersonaFormWindow(new Persona()));*/
        VerticalLayout Roles=new VerticalLayout(calificacion,contratos
                ,busquedas
                );



        FormLayout principal=new FormLayout(nombre, apellido,DNI,contacto,mail,telefono,telefono2);
        FormLayout adicional=new FormLayout(
                Publicaciones, Roles
                );
        adicional.addComponent(info);
        adicional.addComponent(infoAdicional);

        principal.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        adicional.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);


        calificacion.setEnabled(false);
        calificacion.setStyleName(ValoTheme.COMBOBOX_BORDERLESS);

        tabSheet.addTab(principal,"Principal");
        tabSheet.addTab(adicional,"Adicional");

        addComponent(tabSheet);
        //HorizontalLayout actions = new HorizontalLayout(save,test,delete);
        HorizontalLayout actions = new HorizontalLayout(save,delete);
        addComponent(actions);
        this.setSpacing(false);
        actions.setSpacing(true);

      //  addStyleName("v-scrollable");

    }


    public void setPersona(Persona persona) {
   /*   if(persona.getInquilino()!=null){
            this.calificacion.setVisible(true);
            calificacion.setSelectedItem(Calificacion.A);
            this.calificacion.setSelectedItem(persona.getInquilino().getCalificacion());
        }
        else{
            this.calificacion.setVisible(false);
        }*/
        this.calificacion.setVisible(false);
        this.persona = persona;
        binderPersona.readBean(persona);

        // Show delete button for only Persons already in the database
        delete.setVisible(persona.getId()!=null);

        setVisible(true);
        getAddressbookView().setComponentsVisible(false);
        nombre.selectAll();
        if(getAddressbookView().isIsonMobile())
            tabSheet.focus();

    }

    private void delete() {
        service.delete(persona);
        addressbookView.updateList();
        setVisible(false);
        getAddressbookView().setComponentsVisible(true);
        getAddressbookView().showSuccessNotification("Borrado: "+ persona.getNombre()+" "+
                persona.getApellido());

    }

    private void test() {
        nombre.setValue(DummyDataGenerator.randomFirstName());
        apellido.setValue(DummyDataGenerator.randomLastName());
        mail.setValue(nombre.getValue()+"@"+apellido.getValue()+".com");
        DNI.setValue(DummyDataGenerator.randomNumber(8));
        telefono.setValue(DummyDataGenerator.randomPhoneNumber());
        telefono2.setValue(DummyDataGenerator.randomPhoneNumber());
        String info=DummyDataGenerator.randomText(80);
        if(info.length()>255){
            info=info.substring(0,255);

        }
        infoAdicional.setValue(info);


        save();

    }

    private void save() {

        boolean success=false;
        try {
            binderPersona.writeBean(persona);
            service.saveOrUpdate(persona);
            success=true;


        } catch (ValidationException e) {
            e.printStackTrace();
            Notification.show("Error al guardar, por favor revise los campos e intente de nuevo");
            // Notification.show("Error: "+e.getCause());
            return;
        }
        catch (Exception e){
            e.printStackTrace();
            Notification.show("Error: "+e.toString());
        }

        addressbookView.updateList();
       /* String msg = String.format("Guardado '%s %s'.", persona.getNombre(),
                persona.getApellido());*
        Notification.show(msg, Type.TRAY_NOTIFICATION);*/
        setVisible(false);
        getAddressbookView().setComponentsVisible(true);


        if(success)
            getAddressbookView().showSuccessNotification("Guardado: "+ persona.getNombre()+" "+
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

}