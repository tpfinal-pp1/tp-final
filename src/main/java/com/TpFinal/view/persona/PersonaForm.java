package com.TpFinal.view.persona;
import com.TpFinal.data.dto.persona.Persona;
import com.TpFinal.services.PersonaService;
import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.ui.DateField;
import com.vaadin.ui.TextField;

/* Create custom UI Components.
 *
 * Create your own Vaadin components by inheritance and composition.
 * This is a form component inherited from VerticalLayout. Use
 * Use BeanFieldGroup to bind data fields from DTO to UI fields.
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
    TextField telefono = new TextField("Celular");
    TextField mail = new TextField("Mail");
    DateField FdeNac = new DateField("F.de Nac");
   // private NativeSelect<Persona.Sexo> sexo = new NativeSelect<>("Sexo");

    PersonaService service = PersonaService.getService();
    private PersonaABMView addressbookView;
    private Binder<Persona> binder = new Binder<>(Persona.class);
    TabSheet tabSheet;






        // Easily bind forms to beans and manage validation and buffering


    public PersonaForm(PersonaABMView addressbook) {
       // setSizeUndefined();
        addressbookView=addressbook;
        configureComponents();
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
        binder.bindInstanceFields(this);
        setVisible(false);
    }

    private void buildLayout() {
        setSizeFull();
        setMargin(true);

        tabSheet=new TabSheet();

        VerticalLayout principal=new VerticalLayout(nombre, apellido, DNI, FdeNac);
        VerticalLayout personao=new VerticalLayout(mail, telefono);

        tabSheet.addTab(principal,"Principal");
        tabSheet.addTab(personao,"Contacto");

        addComponent(tabSheet);
        HorizontalLayout actions = new HorizontalLayout(save,delete);
        addComponent(actions);
        actions.setSpacing(true);


    }


    public void setPersona(Persona persona) {
        this.persona = persona;
        binder.setBean(persona);

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
        service.save(persona);
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
