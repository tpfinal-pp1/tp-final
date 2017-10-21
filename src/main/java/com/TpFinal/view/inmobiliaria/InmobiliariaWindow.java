package com.TpFinal.view.inmobiliaria;

import com.TpFinal.dto.Localidad;
import com.TpFinal.dto.Provincia;
import com.TpFinal.dto.inmobiliaria.Inmobiliaria;
import com.TpFinal.dto.inmueble.Direccion;
import com.TpFinal.dto.persona.Persona;
import com.TpFinal.services.InmobiliariaService;
import com.TpFinal.services.PersonaService;
import com.TpFinal.services.ProvinciaService;
import com.vaadin.data.Binder;
import com.vaadin.data.HasValue;
import com.vaadin.data.ValidationException;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public abstract class InmobiliariaWindow extends Window {

	 public static final String ID = "profilepreferenceswindow";

	 	private Inmobiliaria inmobiliaria;
	 	private ProvinciaService provinciaService= new ProvinciaService();
	    private Binder<Inmobiliaria> binderInmobiliaria = new Binder<>(Inmobiliaria.class);
	    private  InmobiliariaService service = new InmobiliariaService();
	    private TextField nombre = new TextField("Nombre");
	    private TextField cuit = new TextField("Cuit");
	    private TextField telefono = new TextField("Telefono");
	    private TextField mail = new TextField("Mail");
	    private TextField calle = new TextField("Calle");
	    private TextField nro = new TextField("Número");
	    private TextField codPostal = new TextField("Código postal");
	    private ComboBox<Localidad> localidades = new ComboBox<>("Localidad");
	    private ComboBox<Provincia> provincias = new ComboBox<>("Provincia");

	    public InmobiliariaWindow(Inmobiliaria p) {
	        this.inmobiliaria=p;
	        configureComponents();
	        binding();
	        setInmobiliaria(inmobiliaria);
	        UI.getCurrent().addWindow(this);
	        this.focus();
	    }
	    public abstract void onSave();
	    private void configureComponents(){


	        addStyleName("profile-window");
	        setId(ID);
	        Responsive.makeResponsive(this);

	        setModal(true);
	        setCloseShortcut(KeyCode.ESCAPE, null);
	        setResizable(false);
	        setClosable(true);
	        setHeight(90.0f, Unit.PERCENTAGE);

	        VerticalLayout content = new VerticalLayout();
	        content.setSizeFull();
	        content.setMargin(new MarginInfo(true, false, false, false));
	        content.setSpacing(false);
	        setContent(content);

	        TabSheet detailsWrapper = new TabSheet();
	        detailsWrapper.setSizeFull();
	        detailsWrapper.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
	        detailsWrapper.addStyleName(ValoTheme.TABSHEET_ICONS_ON_TOP);
	        detailsWrapper.addStyleName(ValoTheme.TABSHEET_CENTERED_TABS);
	        content.addComponent(detailsWrapper);
	        content.setExpandRatio(detailsWrapper, 1f);
	        
	        localidades.setItems(provinciaService.getLocalidades());
	    	provincias.setItems(provinciaService.getProvincias());
	    	
	    	provincias.addValueChangeListener(new HasValue.ValueChangeListener<Provincia>() {
	    		@Override
	    		public void valueChange(HasValue.ValueChangeEvent<Provincia> valueChangeEvent) {
	    			Provincia provincia = valueChangeEvent.getValue();
	    			if (provincia != null) {
	    				localidades.setEnabled(true);
	    				localidades.setItems(provincia.getLocalidades());
	    				localidades.setSelectedItem(provincia.getLocalidades().get(0));
	    			} else {
	    				localidades.setEnabled(false);
	    				localidades.setSelectedItem(null);
	    			}
	    		}
	    	});

	    	localidades.addValueChangeListener(new HasValue.ValueChangeListener<Localidad>() {
	    	    @Override
	    	    public void valueChange(HasValue.ValueChangeEvent<Localidad> valueChangeEvent) {
	    		if (valueChangeEvent.getValue() != null) {
	    			String CP=valueChangeEvent.getValue().getCodigoPostal();
	    			if(!CP.equals("0"))
	    		    	codPostal.setValue(CP);

	    			else
	    				codPostal.setValue("");
	    		}
	    	    }
	    	});

	        detailsWrapper.addComponent(buildProfileTab());
	        //  detailsWrapper.addComponent(buildPreferencesTab());

	        content.addComponent(buildFooter());

	    }

	    private void binding(){
	        //binder.bindInstanceFields(this); //Binding automatico
	        nombre.setRequiredIndicatorVisible(true);
	       // telefono.setRequiredIndicatorVisible(true);
	        cuit.setRequiredIndicatorVisible(true);
	        telefono.setRequiredIndicatorVisible(true);
	        mail.setRequiredIndicatorVisible(true);
	        localidades.setRequiredIndicatorVisible(true);
	        provincias.setRequiredIndicatorVisible(true);
	        codPostal.setRequiredIndicatorVisible(true);
	        nro.setRequiredIndicatorVisible(true);
	        calle.setRequiredIndicatorVisible(true);
	        
	        binderInmobiliaria.forField(nombre)
	        .asRequired("Ingrese un nombre")
	        .bind(i -> i.getNombre(), (i, nom)-> i.setNombre(nom));
	        
	        binderInmobiliaria.forField(cuit)
	        .asRequired("Ingrese un cuit")
	        .bind(i -> i.getCuit(), (i, c)-> i.setCuit(c));
	        
	        binderInmobiliaria.forField(telefono)
	        .asRequired("Ingrese un telefono")
	        .bind(i -> i.getTelefono(), (i, c)-> i.setTelefono(c));
	        
	        binderInmobiliaria.forField(mail)
	        .withValidator(new EmailValidator("Introduzca un email valido"))
	        .bind(i -> i.getCuit(), (i, c)-> i.setCuit(c));
	        
	        binderInmobiliaria.forField(this.localidades).withValidator(localidad -> localidades.isEnabled(), "Debe seleccionar una provincia primero")
			.asRequired("Seleccione una localidad").bind(inmobiliaria -> {
		    	Direccion dir = inmobiliaria.getDireccion();

					return dir != null ? provinciaService.getLocalidadFromNombreAndProvincia(dir.getLocalidad(), dir
							.getProvincia()) : null;

			},
			(inmobiliaria, localidad) -> {
			    if (inmobiliaria.getDireccion() == null)
					inmobiliaria.setDireccion(new Direccion());
			    if (localidad != null) {
					inmobiliaria.getDireccion().setLocalidad(localidad.getNombre());
					inmobiliaria.getDireccion().setCodPostal(localidad.getCodigoPostal());
					inmobiliaria.getDireccion().setProvincia(localidad.getProvincia().getNombre());
			    }
			});

	        binderInmobiliaria.forField(this.provincias).asRequired("Seleccione una provincia")
			.bind(inmobiliaria -> {
			    Direccion dir = inmobiliaria.getDireccion();
			    return dir != null ? provinciaService.getProvinciaFromString(dir.getProvincia()) : null;
			},
				(inmueble, provincia) -> {
				    if (inmueble.getDireccion() == null)
						inmueble.setDireccion(new Direccion());
				    if (provincia != null) {
						inmueble.getDireccion().setProvincia(provincia.getNombre());
				    }
				});
	        
	        binderInmobiliaria.forField(this.codPostal)
			.withNullRepresentation("")
			.bind(inmobiliaria -> inmobiliaria.getDireccion().getCodPostal(),
				(inmobiliaria, cod) -> inmobiliaria.getDireccion().setCodPostal(cod));

	        binderInmobiliaria.forField(this.nro).asRequired("Ingrese la altura")
			.withNullRepresentation("")
			.withConverter(new StringToIntegerConverter("Debe ingresar un número"))
			.withValidator(n -> n >= 0, "Debe ingresar una altura no negativa!")
			.bind(inmobiliaria -> inmobiliaria.getDireccion().getNro(),
				(inmobiliaria, nro) -> inmobiliaria.getDireccion().setNro(nro));

	        binderInmobiliaria.forField(this.calle).asRequired("Ingrese el nombre de la calle")
			.withNullRepresentation("")
			.bind(inmobiliaria -> inmobiliaria.getDireccion().getCalle(),
				(inmobiliaria, calle) -> inmobiliaria.getDireccion().setCalle(calle));
	    }

	    private void setInmobiliaria(Inmobiliaria inmo) {

	        this.inmobiliaria = inmo;
	        binderInmobiliaria.readBean(inmo);

	        setVisible(true);
	        nombre.selectAll();


	    }
	    private void save(){
	        boolean success=false;
	        try {
	            binderInmobiliaria.writeBean(inmobiliaria);
	            service.saveOrUpdate(inmobiliaria);
	            onSave();
	            success=true;


	        } catch (ValidationException e) {
	            Notification.show("Error al guardar, por favor revise los campos e intente de nuevo");
	            e.printStackTrace();
	            System.out.println( e.getValidationErrors()+" "+e.getFieldValidationErrors());

	            return;
	        }


	        if(success) {
	            Notification exito = new Notification(
	                    "Guardado: " + inmobiliaria.getNombre());
	            exito.setDelayMsec(2000);
	            exito.setStyleName("bar success small");
	            exito.setPosition(Position.BOTTOM_CENTER);
	            exito.show(Page.getCurrent());
	            close();
	        }

	    }
	    private Component buildFooter() {
	        HorizontalLayout footer = new HorizontalLayout();
	        footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
	        footer.setWidth(100.0f, Unit.PERCENTAGE);
	        footer.setSpacing(false);

	        Button ok = new Button("Guardar Inmobiliaria");
	        ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
	        ok.addClickListener(new ClickListener() {
	            @Override
	            public void buttonClick(ClickEvent event) {
	                save();

	            }
	        });
	        ok.focus();
	        footer.addComponent(ok);
	        footer.setComponentAlignment(ok, Alignment.TOP_RIGHT);
	        return footer;
	    }


	    private Component buildProfileTab() {
	        HorizontalLayout root = new HorizontalLayout();
	        root.setCaption("Inmobiliaria");
	        root.setIcon(VaadinIcons.USER);
	        root.setWidth(100.0f, Unit.PERCENTAGE);
	        root.setMargin(true);
	        root.addStyleName("profile-form");

	        FormLayout details = new FormLayout();
	        details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
	        root.addComponent(details);
	        root.setExpandRatio(details, 1);

	        nombre = new TextField("Nombre");
	        details.addComponent(nombre);
	        cuit = new TextField("Cuit");
	        codPostal.setEnabled(false);
	        details.addComponent(cuit);
	        details.addComponent(telefono);
	        details.addComponent(mail);
	        details.addComponent(calle);
	        details.addComponent(nro);
	        details.addComponent(provincias);
	        details.addComponent(localidades);
	        details.addComponent(codPostal);

	        return root;
	    }

}
