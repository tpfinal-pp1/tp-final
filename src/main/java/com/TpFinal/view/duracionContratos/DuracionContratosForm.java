package com.TpFinal.view.duracionContratos;

import com.TpFinal.data.dto.contrato.ContratoDuracion;
import com.TpFinal.data.dto.contrato.ContratoVenta;
import com.TpFinal.data.dto.contrato.DuracionContrato;
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


public class DuracionContratosForm extends FormLayout {
    
	//necesita clase nueva
	private ContratoDuracion duracionContrato;
    Button save = new Button("Guardar");
    DeleteButton delete = new DeleteButton("Eliminar",
            VaadinIcons.WARNING,"Eliminar","20%", new Button.ClickListener() {
        @Override
        public void buttonClick(Button.ClickEvent clickEvent) {
            delete();
        }
    });


    TextField descripcion = new TextField("Descripcion");  
    TextField cantidad = new TextField("Cantidad");

    //PersonaService service = new PersonaService();
    private DuracionContratosABMView addressbookView;
    private Binder<ContratoDuracion> binderContratoDuracion= new Binder<>(ContratoDuracion.class);
    TabSheet tabSheet;

    // Easily binding forms to beans and manage validation and buffering


    public DuracionContratosForm(DuracionContratosABMView addressbook) {
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
        //test.addClickListener(e -> this.test());

        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        setVisible(false);
    }


    private void binding(){
        //binder.bindInstanceFields(this); //Binding automatico
        descripcion.setRequiredIndicatorVisible(true);
        cantidad.setRequiredIndicatorVisible(true);
        binderContratoDuracion.forField(descripcion).asRequired("Ingrese un nombre").bind(ContratoDuracion::getDescripcion,ContratoDuracion::setDescripcion);

        binderContratoDuracion.forField(cantidad).asRequired("Ingrese un teléfono").bind(ContratoDuracion::getDuracionString,ContratoDuracion::setDuracionString);

        
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


        FormLayout principal=new FormLayout(descripcion, cantidad);
        
       
        principal.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        
        tabSheet.addTab(principal,"Principal");
        
        addComponent(tabSheet);
        //HorizontalLayout actions = new HorizontalLayout(save,test,delete);
        HorizontalLayout actions = new HorizontalLayout(save,delete);
        addComponent(actions);
        this.setSpacing(false);
        actions.setSpacing(true);

      //  addStyleName("v-scrollable");

    }


    public void setContratoDuracion(ContratoDuracion duracionContrato) {
   
        this.duracionContrato = duracionContrato;
        binderContratoDuracion.readBean(duracionContrato);

        // Show delete button for only Persons already in the database
        //TODO
        // delete.setVisible(duracionContrato.getId()!=null);

        setVisible(true);
        getAddressbookView().setComponentsVisible(false);
        descripcion.selectAll();
        if(getAddressbookView().isIsonMobile())
            tabSheet.focus();

    }

    private void delete() {
        
    	//TODO
    	//service.delete(duracionContrato);
        addressbookView.updateList();
        setVisible(false);
        getAddressbookView().setComponentsVisible(true);
        getAddressbookView().showSuccessNotification("Borrado: "+ duracionContrato.getDescripcion());

    }

    
    private void save() {

        boolean success=false;
        try {
        	binderContratoDuracion.writeBean(duracionContrato);
            //TODO
        	//service.saveOrUpdate(duracionContrato);
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
            getAddressbookView().showSuccessNotification("Guardado: "+ duracionContrato.getDescripcion());


    }

    public void cancel() {
        addressbookView.updateList();
        setVisible(false);
        getAddressbookView().setComponentsVisible(true);
    }



    public DuracionContratosABMView getAddressbookView() {
        return addressbookView;
    }

}