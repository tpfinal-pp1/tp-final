package com.TpFinal.view.operacion;
import com.TpFinal.data.dto.publicacion.PublicacionAlquiler;
import com.TpFinal.services.PublicacionService;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

/* Create custom UI Components.
 *
 * Create your own Vaadin components by inheritance and composition.
 * This is a form component inherited from VerticalLayout. Use
 * Use BeanFieldGroup to binding data fields from DTO to UI fields.
 * Similarly named field by naming convention or customized
 * with @PropertyId annotation.
 */
public class PublicacionAlquilerForm extends FormLayout {
    private PublicacionAlquiler PublicacionAlquiler;

    Button save = new Button("Guardar");
  //  Button test = new Button("Test");
    Button delete = new Button("Eliminar");


    // private NativeSelect<PublicacionAlquiler.Sexo> sexo = new NativeSelect<>("Sexo");

    PublicacionService service = new PublicacionService();
    private PublicacionABMView addressbookView;
    private Binder<PublicacionAlquiler> binderPublicacionAlquiler = new Binder<>(PublicacionAlquiler.class);




    TabSheet tabSheet;






    // Easily binding forms to beans and manage validation and buffering


    public PublicacionAlquilerForm(PublicacionABMView addressbook) {
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
        //  sexo.setItems(PublicacionAlquiler.Sexo.values());
        delete.setStyleName(ValoTheme.BUTTON_DANGER);
        save.addClickListener(e -> this.save());
        delete.addClickListener(e -> this.delete());
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);





        setVisible(false);
    }


    private void binding(){
       //binder.bindInstanceFields(this); //Binding automatico







    }

    private void buildLayout() {
        setSizeFull();
        setMargin(true);

        tabSheet=new TabSheet();

        VerticalLayout principal=new VerticalLayout();
        VerticalLayout adicional=new VerticalLayout();




        tabSheet.addTab(principal,"Alquiler");
        tabSheet.addTab(adicional,"Contrato");


        addComponent(tabSheet);
        HorizontalLayout actions = new HorizontalLayout(save,delete);
        addComponent(actions);
        actions.setSpacing(true);

    }

    public void setPublicacionAlquiler(PublicacionAlquiler PublicacionAlquiler) {

        this.PublicacionAlquiler = PublicacionAlquiler;
      //  binderPublicacionAlquiler.readBean(PublicacionAlquiler);

        // Show delete button for only Persons already in the database
        delete.setVisible(PublicacionAlquiler.getId()!=null);

        setVisible(true);

        getAddressbookView().setComponentsVisible(false);
        if(getAddressbookView().isIsonMobile())
            tabSheet.focus();

    }



    private void delete() {
        service.delete(PublicacionAlquiler);
        addressbookView.updateList();
        setVisible(false);
        getAddressbookView().setComponentsVisible(true);
        getAddressbookView().showSuccessNotification("Borrado");


    }

   /* private void test() {
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

    }*/

    private void save() {

        boolean success=false;
        try {
            binderPublicacionAlquiler.writeBean(PublicacionAlquiler);
            service.save(PublicacionAlquiler);
            success=true;


        } catch (ValidationException e) {
            e.printStackTrace();
            Notification.show("Error al guardar, porfavor revise los campos e intente de nuevo");
            // Notification.show("Error: "+e.getCause());
            return;
        }
        catch (Exception e){
            e.printStackTrace();
            Notification.show("Error: "+e.toString());
        }

        addressbookView.updateList();
       /* String msg = String.format("Guardado '%s %s'.", PublicacionAlquiler.getNombre(),
                PublicacionAlquiler.getApellido());*
        Notification.show(msg, Type.TRAY_NOTIFICATION);*/
        setVisible(false);
        getAddressbookView().setComponentsVisible(true);


        if(success)
            getAddressbookView().showSuccessNotification("Guardado");




    }

    public void cancel() {
        addressbookView.updateList();
        setVisible(false);
        getAddressbookView().setComponentsVisible(true);
    }



    public PublicacionABMView getAddressbookView() {
        return addressbookView;
    }



}