package com.TpFinal.view.inmobiliaria;

import com.TpFinal.dto.inmobiliaria.Inmobiliaria;
import com.TpFinal.dto.persona.Persona;
import com.TpFinal.services.InmobiliariaService;
import com.TpFinal.services.PersonaService;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
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
	    private Binder<Inmobiliaria> binderInmobiliaria = new Binder<>(Inmobiliaria.class);
	    InmobiliariaService service = new InmobiliariaService();
	    TextField nombre = new TextField("Nombre");
	    TextField cuit = new TextField("Cuit");

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

	        detailsWrapper.addComponent(buildProfileTab());
	        //  detailsWrapper.addComponent(buildPreferencesTab());

	        content.addComponent(buildFooter());

	    }

	    private void binding(){
	        //binder.bindInstanceFields(this); //Binding automatico
	        nombre.setRequiredIndicatorVisible(true);
	       // telefono.setRequiredIndicatorVisible(true);
	        cuit.setRequiredIndicatorVisible(true);
	        
	        binderInmobiliaria.forField(nombre).bind(i -> i.getNombre(), (i, nom)-> i.setNombre(nom));
	        binderInmobiliaria.forField(cuit).bind(i -> i.getCuit(), (i, c)-> i.setCuit(c));
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
	        details.addComponent(cuit);

	        return root;
	    }

}
