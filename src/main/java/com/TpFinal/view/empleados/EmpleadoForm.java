package com.TpFinal.view.empleados;

import com.TpFinal.dto.persona.Empleado;
import com.TpFinal.dto.persona.Persona;
import com.TpFinal.view.component.DeleteButton;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

public class EmpleadoForm extends FormLayout {
	 
		
		private Empleado empleado;
		
	
	    Button save = new Button("Guardar");
	    DeleteButton delete = new DeleteButton("Eliminar",
	            VaadinIcons.WARNING,"Eliminar","20%", new Button.ClickListener() {
	        @Override
	        public void buttonClick(Button.ClickEvent clickEvent) {
	            delete();
	        }
	    });

	    private TextField nombre = new TextField("Nombre");
	    private TextField apellido = new TextField("Apellido");
	    private TextField DNI = new TextField("DNI");
	    private TextField telefono = new TextField("Telefono");
	    private TextField telefono2 = new TextField("Celular");
	    private TextField mail = new TextField("Mail");
	    private TextArea infoAdicional = new TextArea("Info");
	    
	    //TODO REPLACE
	    //ContratoDuracionService service = new ContratoDuracionService();
	    
	    private EmpleadoABMView addressbookView;
	    private Binder<Empleado> binderEmpleado= new Binder<>(Empleado.class);
	    TabSheet tabSheet;
	    //TabSheet
	    private FormLayout principal;
	    
	    // Easily binding forms to beans and manage validation and buffering


	    public EmpleadoForm(EmpleadoABMView addressbook) {
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
	    	
	    	 nombre.setRequiredIndicatorVisible(true);
	         apellido.setRequiredIndicatorVisible(true);
	         mail.setRequiredIndicatorVisible(true);
	         telefono.setRequiredIndicatorVisible(true);
	         DNI.setRequiredIndicatorVisible(false);
	         binderEmpleado.forField(nombre).asRequired("Ingrese un nombre").bind(Persona::getNombre,Persona::setNombre);

	         binderEmpleado.forField(apellido).asRequired("Ingrese un apellido").bind(Persona::getApellido,Persona::setApellido);

	         binderEmpleado.forField(DNI).bind(Persona::getDNI,Persona::setDNI);


	         binderEmpleado.forField(telefono).asRequired("Ingrese un teléfono").bind(Persona::getTelefono,Persona::setTelefono);

	         binderEmpleado.forField(telefono2).bind(Persona::getTelefono2,Persona::setTelefono2);


	         binderEmpleado.forField(mail).withValidator(new EmailValidator(
	                 "Introduzca un email valido!" )).bind(Persona::getMail,Persona::setMail);
	         
	         binderEmpleado.forField(infoAdicional).bind(Persona::getInfoAdicional,Persona::setInfoAdicional);

	       
	    }

	    private void buildLayout() {
	        setSizeFull();
	        setMargin(true);

	        tabSheet=new TabSheet();

	        principal=new FormLayout(nombre, apellido,DNI,mail,telefono,telefono2);
	       
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


	    public void setContratoDuracion(Empleado empleado) {
	   
	    //TODO REPLACE
	    	
	    	/*	if(duracionContrato != null) {
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
	    		    this.focus();	*/
	  
	    }

	    private void delete() {
	        
	    	//TODO REPLACE
	    	//service.delete(empleado);
	        addressbookView.updateList();
	        setVisible(false);
	        getAddressbookView().setComponentsVisible(true);
	        getAddressbookView().showSuccessNotification("Borrado: "+ empleado.toString());

	    }

	    
	    private void save() {

	        boolean success=false;
	        try {
	        	binderEmpleado.writeBean(empleado);
	            
	        	//TODO
	        	//service.saveOrUpdate(empleado);
	            success=true;


	        } catch (ValidationException e) {
	            e.printStackTrace();
	            Notification.show("Error al guardar, por favor revise los campos e intente de nuevo");

	            return;
	        }
	        catch (Exception e){
	            e.printStackTrace();
	            Notification.show("Error: "+e.toString());
	        }

	        addressbookView.updateList();
	      
	        setVisible(false);
	        getAddressbookView().setComponentsVisible(true);


	        if(success)
	            getAddressbookView().showSuccessNotification("Guardado: "+ empleado.toString());


	    }

	    public void cancel() {
	        addressbookView.updateList();
	        setVisible(false);
	        getAddressbookView().setComponentsVisible(true);
	    }
	    
	    public void clearFields() {
	    	nombre.clear();
	    	apellido.clear();
	    	mail.clear();
	    	DNI.clear();
	    	telefono.clear();
	    	telefono2.clear();
	    	infoAdicional.clear();
	    }
	    
	    public EmpleadoABMView getAddressbookView() {
	        return addressbookView;
	    }

	}