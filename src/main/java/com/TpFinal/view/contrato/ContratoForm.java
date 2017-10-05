package com.TpFinal.view.contrato;

import com.TpFinal.data.dto.contrato.Contrato;
import com.TpFinal.services.ContratoService;
import com.TpFinal.view.component.BlueLabel;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.validator.DateRangeValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.time.LocalDate;
import java.util.Random;

/**
 * Created by Max on 10/5/2017.
 */
public class ContratoForm extends FormLayout {
    private Contrato contrato;
    Button save = new Button("Guardar");
    Button test = new Button("Test");
    Button delete = new Button("Eliminar");
    DateField fechaCelebracion = new DateField("Fecha de Celebracion");


    ContratoService service = new ContratoService();
    private ContratoABMView addressbookView;
    private Binder<Contrato> binderContrato = new Binder<>(Contrato.class);
    TabSheet tabSheet;






    // Easily binding forms to beans and manage validation and buffering


    public ContratoForm(ContratoABMView addressbook) {
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



        delete.setStyleName(ValoTheme.BUTTON_DANGER);
        save.addClickListener(e -> this.save());
        test.addClickListener(e -> this.test());
        delete.addClickListener(e -> this.delete());
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        setVisible(false);
    }


    private void binding(){
        //binder.bindInstanceFields(this); //Binding automatico

        binderContrato.forField(fechaCelebracion).withValidator(new DateRangeValidator(
                "Debe celebrarse desde mañana en adelante",LocalDate.now(),LocalDate.now().plusDays(365))
        ).bind(Contrato::getFechaCelebracion,Contrato::setFechaCelebracion);



    }

    private void buildLayout() {
        setSizeFull();
        setMargin(true);

        tabSheet=new TabSheet();


        BlueLabel Publicaciones = new  BlueLabel("Publicaciones");
        BlueLabel info = new  BlueLabel("Información Adicional");
       // BlueLabel contacto = new  BlueLabel("Contacto");
        
        FormLayout principal=new FormLayout(fechaCelebracion);
        FormLayout adicional=new FormLayout(Publicaciones);
        adicional.addComponent(info);
        //adicional.addComponent(infoAdicional);

        principal.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        adicional.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);



        tabSheet.addTab(principal,"Principal");
        tabSheet.addTab(adicional,"Adicional");

        addComponent(tabSheet);
        HorizontalLayout actions = new HorizontalLayout(save,test,delete);

        addComponent(actions);
        this.setSpacing(false);
        actions.setSpacing(true);

        //  addStyleName("v-scrollable");

    }


    public void setContrato(Contrato contrato) {

        this.contrato = contrato;
        binderContrato.readBean(contrato);

        // Show delete button for only Persons already in the database
        delete.setVisible(contrato.getId()!=null);

        setVisible(true);
        getAddressbookView().setComponentsVisible(false);
        //nombre.selectAll();
        if(getAddressbookView().isIsonMobile())
            tabSheet.focus();

    }

    private void delete() {
        service.delete(contrato);
        addressbookView.updateList();
        setVisible(false);
        getAddressbookView().setComponentsVisible(true);
        getAddressbookView().showSuccessNotification("Borrado: "+ contrato.getId()+" "+
               contrato.getEstadoRegistro());


    }

    private void test() {
        fechaCelebracion.setValue( LocalDate.now().plusDays( (new Random().nextInt(10)) ) );
        save();

    }

    private void save() {

        boolean success=false;
        try {
             binderContrato.writeBean(contrato);
             service.save(contrato);
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
       /* String msg = String.format("Guardado '%s %s'.", persona.getNombre(),
                persona.getApellido());*
        Notification.show(msg, Type.TRAY_NOTIFICATION);*/
        setVisible(false);
        getAddressbookView().setComponentsVisible(true);


        if(success)
            getAddressbookView().showSuccessNotification("Guardado: "+ contrato.getId()+" "+
                    contrato.getEstadoRegistro());




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
