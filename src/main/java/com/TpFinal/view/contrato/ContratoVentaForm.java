package com.TpFinal.view.contrato;

import com.TpFinal.data.dto.contrato.Contrato;
import com.TpFinal.data.dto.contrato.ContratoVenta;
import com.TpFinal.data.dto.persona.Persona;
import com.TpFinal.services.ContratoService;
import com.TpFinal.services.PersonaService;
import com.TpFinal.view.component.BlueLabel;
import com.TpFinal.view.component.TinyButton;
import com.TpFinal.view.component.VentanaSelectora;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.validator.DateRangeValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.time.LocalDate;
import java.util.List;

/* Create custom UI Components.
 *
 * Create your own Vaadin components by inheritance and composition.
 * This is a form component inherited from VerticalLayout. Use
 * Use BeanFieldGroup to binding data fields from DTO to UI fields.
 * Similarly named field by naming convention or customized
 * with @PropertyId annotation.
 */
public class ContratoVentaForm extends FormLayout {
    private ContratoVenta ContratoVenta;

    Button save = new Button("Guardar");
  //  Button test = new Button("Test");
    Button delete = new Button("Eliminar");
    DateField fechaCelebracion = new DateField("Fecha de Celebracion");


    // private NativeSelect<ContratoVenta.Sexo> sexo = new NativeSelect<>("Sexo");

    ContratoService service = new ContratoService();
    private ContratoABMView addressbookView;
    private Binder<ContratoVenta> binderContratoVenta = new Binder<>(ContratoVenta.class);




    TabSheet tabSheet;






    // Easily binding forms to beans and manage validation and buffering


    public ContratoVentaForm(ContratoABMView addressbook) {
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
        //  sexo.setItems(ContratoVenta.Sexo.values());
        delete.setStyleName(ValoTheme.BUTTON_DANGER);
        save.addClickListener(e -> this.save());
        delete.addClickListener(e -> this.delete());
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);





        setVisible(false);
    }


    private void binding(){
       //binder.bindInstanceFields(this); //Binding automatico
        binderContratoVenta.forField(fechaCelebracion).withValidator(new DateRangeValidator(
                "Debe celebrarse desde mañana en adelante", LocalDate.now(),LocalDate.now().plusDays(365))
        ).bind(Contrato::getFechaCelebracion,Contrato::setFechaCelebracion);







    }

    private void buildLayout() {
        setSizeFull();
        setMargin(true);

        tabSheet=new TabSheet();


        BlueLabel otro = new  BlueLabel("Venta");
        BlueLabel info = new  BlueLabel("Información Adicional");

        TinyButton personas=new TinyButton("Ver Personas");

        personas.addClickListener(e -> new VentanaSelectora<Persona>() {
            @Override
            public void updateList() {
                PersonaService PersonaService=
                        new PersonaService();
                List<Persona> Personas = PersonaService.readAll();
                grid.setItems(Personas);
            }

            @Override
            public void setGrid() {
                grid=new Grid<>(Persona.class);
            }

            @Override
            protected void recieveSelection(Persona objeto) {
            }
        });

        VerticalLayout Roles=new VerticalLayout(personas);



        FormLayout principal=new FormLayout(otro, Roles);


        principal.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);



        tabSheet.addTab(principal,"Principal");

        addComponent(tabSheet);
        HorizontalLayout actions = new HorizontalLayout(save,delete);

        addComponent(actions);
        this.setSpacing(false);
        actions.setSpacing(true);

        //  addStyleName("v-scrollable");

    }

    public void setContratoVenta(ContratoVenta ContratoVenta) {

        this.ContratoVenta = ContratoVenta;
        binderContratoVenta.readBean(ContratoVenta);

        // Show delete button for only Persons already in the database
        delete.setVisible(ContratoVenta.getId()!=null);

        setVisible(true);
        getAddressbookView().setComponentsVisible(false);

        if(getAddressbookView().isIsonMobile())
            tabSheet.focus();

    }



    private void delete() {
        service.delete(ContratoVenta);
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
            binderContratoVenta.writeBean(ContratoVenta);
            service.save(ContratoVenta);
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
       /* String msg = String.format("Guardado '%s %s'.", ContratoVenta.getNombre(),
                ContratoVenta.getApellido());*
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



    public ContratoABMView getAddressbookView() {
        return addressbookView;
    }



}