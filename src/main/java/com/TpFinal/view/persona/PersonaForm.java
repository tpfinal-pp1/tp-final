package com.TpFinal.view.persona;
import com.TpFinal.data.dto.persona.Persona;
import com.TpFinal.services.PersonaService;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.StringLengthValidator;
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
    public class PersonaForm extends FormLayout {
    private Persona persona;
    Button save = new Button("Guardar");
    Button delete = new Button("Eliminar");
    TextField nombre = new TextField("Nombre");
    TextField apellido = new TextField("Apellido");
    TextField DNI = new TextField("DNI");
    TextField telefono = new TextField("Telefono");
    TextField telefono2 = new TextField("Celular");
    TextField mail = new TextField("Mail");
    TextArea infoAdicional = new TextArea("Información Adicional");

   // private NativeSelect<Persona.Sexo> sexo = new NativeSelect<>("Sexo");

    PersonaService service = PersonaService.getService();
    private PersonaABMView addressbookView;
    private Binder<Persona> binder = new Binder<>(Persona.class);
    TabSheet tabSheet;






        // Easily binding forms to beans and manage validation and buffering


    public PersonaForm(PersonaABMView addressbook) {
       // setSizeUndefined();
        addressbookView=addressbook;
        configureComponents();
        binding();
        buildLayout();
        addStyleName("v-scrollable");
    }

    private void configureComponents() {
        /*
         * Highlight primary actions.
         *
         * With Vaadin built-in styles you can highlight the primary save button
         *
         * and give it a keyoard shortcut for a better UX.
         */

     //   sexo.setEmptySelectionAllowed(false);
      //  sexo.setItems(Persona.Sexo.values());
        delete.setStyleName(ValoTheme.BUTTON_DANGER);

        save.addClickListener(e -> this.save());
        delete.addClickListener(e -> this.delete());
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);





        setVisible(false);
    }

    
    private void binding(){
        //binder.bindInstanceFields(this); //Binding automatico
        binder.forField(nombre).withValidator(new StringLengthValidator(
                "El nombre debe estar entre 2 y 20 caracteres",
                2, 20)).bind(Persona::getNombre,Persona::setNombre);

        binder.forField(apellido).withValidator(new StringLengthValidator(
                "El nombre debe estar entre 2 y 20 caracteres",
                2, 20)).bind(Persona::getApellido,Persona::setApellido);

        binder.forField(DNI).withValidator(new StringLengthValidator(
                "El DNI de estar entre 2 y 20 caracteres",
                2, 20)).bind(Persona::getDNI,Persona::setDNI);
        binder.forField(telefono).bind(Persona::getTelefono,Persona::setTelefono);
        binder.forField(telefono2).bind(Persona::getTelefono2,Persona::setTelefono2);
        binder.forField(mail)./*withValidator(new EmailValidator(  //por ahora suspendo validator Mail, si escribis algo depsuea al borrar sigue tirando error
                "Introduzca un email valido!"
                )).*/bind(Persona::getMail,Persona::setMail);
        binder.forField(infoAdicional).bind(Persona::getInfoAdicional,Persona::setInfoAdicional);







    }
    
    private void buildLayout() {
        setSizeFull();
        setMargin(true);

        tabSheet=new TabSheet();

        VerticalLayout principal=new VerticalLayout(nombre, apellido,telefono,mail,DNI);
        VerticalLayout adicional=new VerticalLayout(telefono,telefono2,infoAdicional);

        tabSheet.addTab(principal,"Principal");
        tabSheet.addTab(adicional,"Contacto");

        addComponent(tabSheet);
        HorizontalLayout actions = new HorizontalLayout(save,delete);
        addComponent(actions);
        actions.setSpacing(true);


    }


    public void setPersona(Persona persona) {
        System.out.println("ID Inicial: "+persona.getId());
        this.persona = persona;
        binder.readBean(persona);

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

    private void save() {
        System.out.println("ID Final: "+persona.getId());
        try {
            binder.writeBean(persona);
            service.save(persona);
        } catch (ValidationException e) {
            e.printStackTrace();
            Notification.show("Error al guardar, porfavor revise los campos e intente de nuevo");
            Notification.show("Error: "+e.getCause());
            return;
        }
        catch (Exception e){
            e.printStackTrace();
            Notification.show("Error: "+e.getCause());
        }

        addressbookView.updateList();
       /* String msg = String.format("Guardado '%s %s'.", persona.getNombre(),
                persona.getApellido());*
        Notification.show(msg, Type.TRAY_NOTIFICATION);*/
        setVisible(false);
        getAddressbookView().setComponentsVisible(true);
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
