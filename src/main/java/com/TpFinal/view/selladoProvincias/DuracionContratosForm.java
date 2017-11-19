package com.TpFinal.view.selladoProvincias;

import com.TpFinal.dto.contrato.ContratoAlquiler;
import com.TpFinal.dto.contrato.ContratoDuracion;
import com.TpFinal.services.ContratoDuracionService;
import com.TpFinal.view.component.BlueLabel;
import com.TpFinal.view.component.DeleteButton;
import com.TpFinal.view.component.TinyButton;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class DuracionContratosForm extends FormLayout {
 
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
    FiltroSellado filtro = new FiltroSellado();

    ContratoDuracionService service = new ContratoDuracionService();
    private SelladoProvinciasABMView addressbookView;
    private Binder<ContratoDuracion> binderContratoDuracion= new Binder<>(ContratoDuracion.class);
    TabSheet tabSheet;

    // Easily binding forms to beans and manage validation and buffering


    public DuracionContratosForm(SelladoProvinciasABMView addressbook) {
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
        
        binderContratoDuracion.forField(descripcion).asRequired("Ingrese una descripcion").bind(ContratoDuracion::getDescripcion,ContratoDuracion::setDescripcion);
                
        binderContratoDuracion.forField(this.cantidad).withNullRepresentation("")
		.withConverter(new StringToIntegerConverter("Debe ingresar un número"))
		.withValidator(n -> n > 0 && n<=1200, "Ingrese una cantidad de meses entre 1 y 1200 meses")
		.bind(ContratoDuracion::getDuracion, ContratoDuracion::setDuracion);
        
       
    }

    private void buildLayout() {
        setSizeFull();
        setMargin(true);

        tabSheet=new TabSheet();

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
   
    	if(duracionContrato != null) {
    		this.duracionContrato = duracionContrato;
    		binderContratoDuracion.readBean(this.duracionContrato);
    		delete.setVisible(true);
    		}else {
    		    this.duracionContrato = ContratoDuracionService.getInstancia();
    			delete.setVisible(false);
    		}
    		setVisible(true);
    		getAddressbookView().setComponentsVisible(false);
    		if (getAddressbookView().isIsonMobile())
    		    this.focus();
  
    }

    private void delete() {
        
    	service.delete(duracionContrato);
        addressbookView.updateList();
        setVisible(false);
        getAddressbookView().setComponentsVisible(true);
        getAddressbookView().showSuccessNotification("Borrado: "+ duracionContrato.getDescripcion());

    }

    
    private void save() {

        boolean success=false;
        try {
        	binderContratoDuracion.writeBean(duracionContrato);
            
        	service.saveOrUpdate(duracionContrato);
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
    
    public void clearFields() {
    	descripcion.clear();
    	cantidad.clear();
    }
    
    public SelladoProvinciasABMView getAddressbookView() {
        return addressbookView;
    }

}