package com.TpFinal.view.adressbook;
import com.TpFinal.data.dto.Person;
import com.TpFinal.services.ContactService;
import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.Notification.Type;
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
    public class PersonForm extends FormLayout {
    private Person person;
    Button save = new Button("Guardar");
    Button delete = new Button("Eliminar");
    TextField firstName = new TextField("Nombre");
    TextField lastName = new TextField("Apellido");
    TextField dni = new TextField("DNI");
    TextField phone = new TextField("Celular");
    TextField email = new TextField("Mail");
    DateField birthDate = new DateField("F.de Nac");
    private NativeSelect<Person.Sex> sex = new NativeSelect<>("Sexo");

    ContactService service = ContactService.getService();
    private ABMPersonView addressbookView;
    private Binder<Person> binder = new Binder<>(Person.class);
    VerticalLayout principal;
    TabSheet tabSheet;






        // Easily bind forms to beans and manage validation and buffering


    public PersonForm(ABMPersonView addressbook) {
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
        sex.setItems(Person.Sex.values());
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

        VerticalLayout principal=new VerticalLayout( firstName, lastName, dni,sex, birthDate);
        VerticalLayout contacto=new VerticalLayout( email,phone);

        tabSheet.addTab(principal,"Principal");
        tabSheet.addTab(contacto,"Contacto");

        addComponent(tabSheet);
        HorizontalLayout actions = new HorizontalLayout(save,delete);
        addComponent(actions);
        actions.setSpacing(true);


    }


    public void setPerson(Person person) {
        this.person = person;
        binder.setBean(person);

        // Show delete button for only Persons already in the database
        delete.setVisible(person.getId()!=null);
        setVisible(true);
        getAddressbookView().setComponentsVisible(false);
        firstName.selectAll();
        if(getAddressbookView().isIsonMobile())
            tabSheet.focus();

    }

    private void delete() {
        service.delete(person);
        addressbookView.updateList();
        setVisible(false);
        getAddressbookView().setComponentsVisible(true);
    }

    private void save() {
        service.save(person);
        addressbookView.updateList();
        String msg = String.format("Guardado '%s %s'.", person.getFirstName(),
                person.getLastName());
        Notification.show(msg, Type.TRAY_NOTIFICATION);
        setVisible(false);
        getAddressbookView().setComponentsVisible(true);



    }

   public void cancel() {

        addressbookView.updateList();
        setVisible(false);
        getAddressbookView().setComponentsVisible(true);
    }
    
    

    public ABMPersonView getAddressbookView() {
        return addressbookView;
    }



}
