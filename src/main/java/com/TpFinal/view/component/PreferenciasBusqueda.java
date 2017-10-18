package com.TpFinal.view.component;

import com.TpFinal.dto.Localidad;
import com.TpFinal.dto.Provincia;
import com.TpFinal.dto.inmueble.ClaseInmueble;
import com.TpFinal.dto.inmueble.CriterioBusquedaInmuebleDTO;
import com.TpFinal.dto.inmueble.TipoInmueble;
import com.TpFinal.dto.persona.Persona;
import com.TpFinal.services.InmuebleService;
import com.TpFinal.services.PersonaService;
import com.TpFinal.services.ProvinciaService;
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
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public abstract class PreferenciasBusqueda extends Window {

    public static final String ID = "profilepreferenceswindow";

    private CriterioBusquedaInmuebleDTO criterio;
    private Binder<CriterioBusquedaInmuebleDTO> binderPersona = new Binder<>(CriterioBusquedaInmuebleDTO.class);
    PersonaService personaService = new PersonaService();
    InmuebleService inmuebleService = new InmuebleService();
    ProvinciaService provinciaService = new ProvinciaService();
    // Componentes
    ComboBox<ClaseInmueble> cbClaseInmueble = new ComboBox<>("Clase", ClaseInmueble.toList());
    ComboBox<Provincia> cbProvincia = new ComboBox<>("Provincia", provinciaService.getProvincias());
    ComboBox<Localidad> cbLocalidad = new ComboBox<>("Localidad", provinciaService.getLocalidades());
    RadioButtonGroup<TipoInmueble> rbgTipoInmueble = new RadioButtonGroup<>("Tipo", TipoInmueble.toList());
    MinMaxTextField minMaxPrecio = new MinMaxTextField("Precio");
    MinMaxTextField minMaxAmbientes = new MinMaxTextField("Ambientes");
    MinMaxTextField minMaxCocheras = new MinMaxTextField("Cocheras");
    MinMaxTextField minMaxDormitorios = new MinMaxTextField("Dormitorios");
    MinMaxTextField minMaxSupTotal = new MinMaxTextField("Sup. Total");
    MinMaxTextField minMaxSupCubierta = new MinMaxTextField("Sup. Cubierta");
    CheckBox aEstrenar = new CheckBox("A estrenar", false);
    CheckBox conAireAcond = new CheckBox("Aire Acondicionado", false);
    CheckBox conJardin = new CheckBox("Jardín", false);
    CheckBox conParrila = new CheckBox("Parrila", false);
    CheckBox conPileta = new CheckBox("Pileta", false);

    // Acciones
    Button guardar = new Button("Guardar", e -> save());
    Button buscar = new Button("Buscar", e -> search());
    Button borrar = new Button("Limpiar", e -> cleanPreferences());

    public PreferenciasBusqueda(CriterioBusquedaInmuebleDTO criterio) {
	this.criterio = criterio;
	buildLayout();
	configureComponents();
	binding();
	setCriterio(criterio);
	UI.getCurrent().addWindow(this);
	this.focus();
    }

    private void buildLayout() {
	// TODO Auto-generated method stub

    }

    private void cleanPreferences() {
	// TODO Auto-generated method stub

    }

    private void search() {
	// TODO Auto-generated method stub

    }

    public abstract void onSave();

    private void configureComponents() {
	this.setWidthUndefined();
	setId(ID);
	Responsive.makeResponsive(this);

	setModal(true);
	setCloseShortcut(KeyCode.ESCAPE, null);
	setResizable(false);
	setClosable(true);
	setHeight(90.0f, Unit.PERCENTAGE);
        setWidth(45.0f, Unit.PERCENTAGE);
        center();

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

	detailsWrapper.addComponent(buildSearchPreferencesTab());
	// detailsWrapper.addComponent(buildPreferencesTab());

	content.addComponent(buildFooter());

    }

    private void binding() {
    }

    private void setCriterio(CriterioBusquedaInmuebleDTO criterio2) {
	//
	// this.persona = persona;
	// binderPersona.readBean(persona);
	// // Show delete button for only Persons already in the database

	setVisible(true);
	// nombre.selectAll();

    }

    private void save() {
	boolean success = false;
	// try {
	// //a binderPersona.writeBean(persona);
	// service.saveOrUpdate(persona);
	// onSave();
	// success=true;
	//
	//
	// } catch (ValidationException e) {
	// Notification.show("Error al guardar, por favor revise los campos e intente de
	// nuevo");
	// e.printStackTrace();
	// System.out.println( e.getValidationErrors()+"
	// "+e.getFieldValidationErrors());
	//
	// return;
	// }

	if (success) {
	    // Notification exito = new Notification(
	    // "Guardado: " + criterio.getNombre() + " " +
	    // criterio.getApellido());
	    // exito.setDelayMsec(2000);
	    // exito.setStyleName("bar success small");
	    // exito.setPosition(Position.BOTTOM_CENTER);
	    // exito.show(Page.getCurrent());
	    
	}
	close();

    }

    private Component buildFooter() {	
	HorizontalLayout actions = new HorizontalLayout(guardar, buscar, borrar);
	actions.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
	actions.setWidth(100.0f, Unit.PERCENTAGE);
	actions.setSpacing(false);
	return actions;
    }

    private Component buildSearchPreferencesTab() {
	HorizontalLayout root = new HorizontalLayout();
	root.setCaption("Preferencias de Búsqueda");
	root.setIcon(VaadinIcons.SEARCH);
	root.setWidth(100.0f, Unit.PERCENTAGE);
	root.setMargin(true);
	root.addStyleName("profile-form");

	FormLayout details = new FormLayout();
	details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
	root.addComponent(details);
	root.setExpandRatio(details, 1);

	rbgTipoInmueble.addStyleName(ValoTheme.OPTIONGROUP_HORIZONTAL);
	
	details.addComponents(
		this.cbClaseInmueble,
		this.rbgTipoInmueble,
		this.cbProvincia,
		this.cbLocalidad,
		this.minMaxPrecio,
		this.minMaxAmbientes,
		this.minMaxCocheras,
		this.minMaxDormitorios,
		this.minMaxSupTotal,
		this.minMaxSupCubierta,
		this.aEstrenar,
		this.conAireAcond,
		this.conJardin,
		this.conParrila,
		this.conPileta
		);
	details.forEach(component -> component.setWidth("100%"));
	return root;
    }

}
