package com.TpFinal.view.inmuebles;

import com.TpFinal.data.dto.Localidad;
import com.TpFinal.data.dto.Provincia;
import com.TpFinal.data.dto.inmueble.ClaseInmueble;
import com.TpFinal.data.dto.inmueble.Direccion;
import com.TpFinal.data.dto.inmueble.Inmueble;
import com.TpFinal.data.dto.inmueble.TipoInmueble;
import com.TpFinal.data.dto.persona.Persona;
import com.TpFinal.data.dto.persona.Propietario;
import com.TpFinal.data.dto.publicacion.Rol;
import com.TpFinal.services.InmuebleService;
import com.TpFinal.services.PersonaService;
import com.TpFinal.services.ProvinciaService;
import com.TpFinal.view.component.BlueLabel;
import com.TpFinal.view.component.DeleteButton;
import com.TpFinal.view.component.TinyButton;
import com.TpFinal.view.persona.PersonaFormWindow;
import com.vaadin.data.Binder;
import com.vaadin.data.HasValue;
import com.vaadin.data.ValidationException;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Setter;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class InmuebleForm extends FormLayout {
    private InmuebleService inmbService = new InmuebleService();
    private PersonaService personaService = new PersonaService();
    private Inmueble inmueble;

    // Acciones
    private Button save = new Button("Guardar");
	DeleteButton delete = new DeleteButton("Eliminar",
			VaadinIcons.WARNING,"¿Esta seguro que desea eliminar?","20%", new Button.ClickListener() {
		@Override
		public void buttonClick(Button.ClickEvent clickEvent) {
			delete();
		}
	});

    // TabPrincipal
    private final ComboBox<Persona> comboPropietario = new ComboBox<>();
    private Persona persona = new Persona();
    private Button btnNuevoPropietario = new Button();
    private ComboBox<ClaseInmueble> clasesInmueble = new ComboBox<>("Clase", ClaseInmueble.toList());
    private RadioButtonGroup<TipoInmueble> tiposInmueble = new RadioButtonGroup<>("Tipo", TipoInmueble.toList());

    // TabDireccion
    private TextField calle = new TextField("Calle");
    private TextField nro = new TextField("Número");
    private TextField codPostal = new TextField("Código postal");
    private ComboBox<Localidad> localidades = new ComboBox<>("Localidad");
    private ComboBox<Provincia> provincias = new ComboBox<>("Provincia");
    private TinyButton buscarUbicacion = new TinyButton("Buscar Ubicación", VaadinIcons.MAP_MARKER);

    // TabCaracteristicas 1
    private TextField ambientes = new TextField("Ambientes");
    private TextField cocheras = new TextField("Cocheras");
    private TextField dormitorios = new TextField("Dormitorios");
    private TextField supTotal = new TextField("Sup. Total");
    private TextField supCubierta = new TextField("Sup. Cubierta");

    // TabCaracteristicas 2
    private CheckBox aEstrenar = new CheckBox("A estrenar");
    private CheckBox aireAcond = new CheckBox("Aire Acondicionado");
    private CheckBox cJardin = new CheckBox("Jardín");
    private CheckBox cParrilla = new CheckBox("Parrilla");
    private CheckBox cPpileta = new CheckBox("Pileta");

    PersonaService service = new PersonaService();
    private InmuebleABMView abmView;
    private Binder<Inmueble> binderInmueble = new Binder<>(Inmueble.class);
    private ProvinciaService provinciaService = new ProvinciaService();

    TabSheet tabSheet;

    public InmuebleForm(InmuebleABMView abmView) {
	this.abmView = abmView;
	configureComponents();
	binding();
	buildLayout();
	updateComboPersonas();

    }

    private void configureComponents() {
	delete.setStyleName(ValoTheme.BUTTON_DANGER);
	save.addClickListener(e -> this.save());



	btnNuevoPropietario.addClickListener(e -> this.setNewPropietario());
	save.setStyleName(ValoTheme.BUTTON_PRIMARY);
	save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
	setVisible(false);
	localidades.setItems(provinciaService.getLocalidades());
	provincias.setItems(provinciaService.getProvincias());
	//provincias.setTextInputAllowed(false);
	provincias.addValueChangeListener(new HasValue.ValueChangeListener<Provincia>() {
	    @Override
	    public void valueChange(HasValue.ValueChangeEvent<Provincia> valueChangeEvent) {
		Provincia provincia = valueChangeEvent.getValue();


		if (provincia != null) {
		    localidades.setItems(provincia.getLocalidades());
		    localidades.setSelectedItem(provincia.getLocalidades().get(0));

		}
		
	    }

	});

	localidades.addValueChangeListener(new HasValue.ValueChangeListener<Localidad>() {
	    @Override
	    public void valueChange(HasValue.ValueChangeEvent<Localidad> valueChangeEvent) {

		if (valueChangeEvent.getValue() != null) {
		    codPostal.setValue(valueChangeEvent.getValue().getCodigoPostal());
		}

	    }

	});
	codPostal.setEnabled(false);
	comboPropietario.setTextInputAllowed(true);
	clasesInmueble.setTextInputAllowed(true);
	localidades.setTextInputAllowed(true);
	provincias.setTextInputAllowed(true);

    }

    private void setNewPropietario() {
	this.persona = new Persona();
	persona.addRol(new Propietario());
	Propietario propietario = (Propietario)persona.getRol(Rol.Propietario);
	propietario.addInmueble(this.inmueble);

	new PersonaFormWindow(this.persona) {
	    @Override
	    public void onSave() {
		personaService.saveOrUpdate(persona);
		updateComboPersonas();
		comboPropietario.setSelectedItem(persona);
	    }
	};

    }

    private void binding() {

	binderInmueble.forField(this.aEstrenar)
		.bind(Inmueble::getaEstrenar, Inmueble::setaEstrenar);

	binderInmueble.forField(this.aireAcond)
		.bind(Inmueble::getConAireAcondicionado, Inmueble::setConAireAcondicionado);

	binderInmueble.forField(this.ambientes).withNullRepresentation("")
		.withConverter(new StringToIntegerConverter("Debe ingresar un número"))
		.withValidator(n -> n >= 0, "Debe ingresar un número no negativo")
		.bind(Inmueble::getCantidadAmbientes, Inmueble::setCantidadAmbientes);

	binderInmueble.forField(this.cJardin)
		.bind(Inmueble::getConJardin, Inmueble::setConJardin);

	binderInmueble.forField(this.clasesInmueble)
		.bind(Inmueble::getClaseInmueble, Inmueble::setClaseInmueble);

	binderInmueble.forField(this.cocheras)
		.withNullRepresentation("")
		.withConverter(new StringToIntegerConverter("Debe ingresar un número"))
		.withValidator(n -> n >= 0, "Debe ingresar un número no negativo")
		.bind(Inmueble::getCantidadCocheras, Inmueble::setCantidadCocheras);

	binderInmueble.forField(this.cParrilla)
		.bind(Inmueble::getConParilla, Inmueble::setConParilla);

	binderInmueble.forField(this.cPpileta)
		.bind(Inmueble::getConPileta, Inmueble::setConPileta);

	binderInmueble.forField(this.dormitorios)
		.withNullRepresentation("")
		.withConverter(new StringToIntegerConverter("Debe ingresar un número"))
		.withValidator(n -> n >= 0, "Debe ingresar un número no negativo")
		.bind(Inmueble::getCantidadDormitorios, Inmueble::setCantidadDormitorios);

	binderInmueble.forField(this.codPostal)
		.withNullRepresentation("")
		.bind(inmueble -> inmueble.getDireccion().getCodPostal(),
			(inmueble, cod) -> inmueble.getDireccion().setCodPostal(cod));

	binderInmueble.forField(this.nro).asRequired("Ingrese la altura")
		.withNullRepresentation("")
		.withConverter(new StringToIntegerConverter("Debe ingresar un número"))
		.withValidator(n -> n >= 0, "Debe ingresar una altura no negativa!")
		.bind(inmueble -> inmueble.getDireccion().getNro(),
			(inmueble, nro) -> inmueble.getDireccion().setNro(nro));

	binderInmueble.forField(this.calle).asRequired("Ingrese el nombre de la calle")
		.withNullRepresentation("")
		.bind(inmueble -> inmueble.getDireccion().getCalle(),
			(inmueble, calle) -> inmueble.getDireccion().setCalle(calle));

	binderInmueble.forField(this.localidades).asRequired("Seleccione una localidad").bind(inmueble -> {
	    Direccion dir = inmueble.getDireccion();
	    return dir != null ? provinciaService.getLocalidadFromNombreAndProvincia(dir.getLocalidad(), dir
		    .getProvincia()) : null;
	},
		(inmueble, localidad) -> {
		    if (inmueble.getDireccion() == null)
			inmueble.setDireccion(new Direccion());
		    if (localidad != null) {
			inmueble.getDireccion().setLocalidad(localidad.getNombre());
			inmueble.getDireccion().setCodPostal(localidad.getCodigoPostal());
			inmueble.getDireccion().setProvincia(localidad.getProvincia().getNombre());
		    }
		});

	binderInmueble.forField(this.provincias).asRequired("Seleccione una provincia")
		.bind(inmueble -> {
		    Direccion dir = inmueble.getDireccion();
		    return dir != null ? provinciaService.getProvinciaFromString(dir.getProvincia()) : null;
		},
			(inmueble, provincia) -> {
			    if (inmueble.getDireccion() == null)
				inmueble.setDireccion(new Direccion());
			    if (provincia != null) {
				inmueble.getDireccion().setProvincia(provincia.getNombre());
			    }
			});

	binderInmueble.forField(this.comboPropietario).asRequired(
		"Debe seleccionar o cargar un propietario del inmueble!")
		.withNullRepresentation(new Persona())
		.bind(inmueble -> inmueble.getPropietario().getPersona(), setPropietario());

	binderInmueble.forField(this.supCubierta)
		.withNullRepresentation("")
		.withConverter(new StringToIntegerConverter("Debe ingresar un número"))
		.withValidator(n -> n >= 0, "Debe ingresar un número no negativo")
		.bind(Inmueble::getSuperficieCubierta, Inmueble::setSuperficieCubierta);

	binderInmueble.forField(this.supTotal)
		.withNullRepresentation("")
		.withConverter(new StringToIntegerConverter("Debe ingresar un número"))
		.withValidator(n -> n >= 0, "Debe ingresar un número no negativo")
		.bind(Inmueble::getSuperficieTotal, Inmueble::setSuperficieTotal);

	binderInmueble.forField(this.tiposInmueble)
		.bind(Inmueble::getTipoInmueble, Inmueble::setTipoInmueble);

    }

    private Setter<Inmueble, Persona> setPropietario() {
	return (inmueble, persona) -> {
	    if (persona != null) {
		Propietario rolP;
		if (persona.contiene(Rol.Propietario) == false) {
		    persona.addRol(new Propietario());
		}
		rolP = (Propietario) persona.getRol(Rol.Propietario);
		rolP.addInmueble(inmueble);
		rolP.setPersona(persona);
		inmueble.setPropietario(rolP);
	    }
	};

    }

    private void buildLayout() {
	// addStyleName("v-scrollable");
	buscarUbicacion.setEnabled(false);
	btnNuevoPropietario.setIcon(VaadinIcons.PLUS);
	comboPropietario.addStyleName(ValoTheme.COMBOBOX_BORDERLESS);
	btnNuevoPropietario.addStyleName(ValoTheme.BUTTON_BORDERLESS);
	btnNuevoPropietario.addStyleName(ValoTheme.BUTTON_FRIENDLY);

	if (this.abmView.isIsonMobile()) {

	    localidades.setWidth("55%");
	    provincias.setWidth("55%");
	    clasesInmueble.setWidth("58%");
	} else {
	    tiposInmueble.addStyleName(ValoTheme.OPTIONGROUP_HORIZONTAL);
	    localidades.setWidth("72%");
	    provincias.setWidth("72%");
	    clasesInmueble.setWidth("72%");

	}

	HorizontalLayout propietarioCombo = new HorizontalLayout();
	propietarioCombo.addComponents(comboPropietario, btnNuevoPropietario);
	propietarioCombo.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
	propietarioCombo.setCaption("Propietario");
	propietarioCombo.setExpandRatio(comboPropietario, 1f);

	FormLayout principal = new FormLayout(propietarioCombo, clasesInmueble, tiposInmueble,
		new BlueLabel("Direccion"), calle, nro, provincias, localidades, codPostal, buscarUbicacion);

	FormLayout caracteristicas1 = new FormLayout(ambientes, cocheras, dormitorios, supTotal,
		supCubierta, new BlueLabel("Adiconales"), aEstrenar, aireAcond, cJardin, cParrilla, cPpileta);

	this.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);

	principal.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);

	caracteristicas1.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);

	TabSheet tabsheet = new TabSheet();
	tabsheet.addTab(principal, "Datos Principales");
	tabsheet.addTab(caracteristicas1, "Características");

	HorizontalLayout actions = new HorizontalLayout(save, delete);
	addComponents(tabsheet, actions);
	actions.setSpacing(true);

    }

    public void setInmueble(Inmueble inmueble) {

	if(inmueble != null) {
	this.inmueble = inmueble;
	binderInmueble.readBean(this.inmueble);
	delete.setVisible(true);
	}else {
	    this.inmueble = InmuebleService.getInstancia();
	    delete.setVisible(false);
	}
	setVisible(true);
	getABMView().setComponentsVisible(false);
	if (getABMView().isIsonMobile())
	    this.focus();

    }

    private void updateComboPersonas() {
	PersonaService ps = new PersonaService();
	comboPropietario.setItems(ps.findAll(""));
    }

    private void delete() {
		inmbService.delete(inmueble);
		abmView.getController().updateList();
		setVisible(false);
		getABMView().setComponentsVisible(true);
		getABMView().showSuccessNotification("Borrado: " + inmueble.getDireccion().toString() + " de " +
				inmueble.getPropietario().toString());

	}


    private void save() {

	boolean success = false;
	try {
	    binderInmueble.writeBean(inmueble);
	    if (inmueble.getPropietario().getPersona() != null)
		success = inmbService.save(inmueble);
	    if (success)
		getABMView().showSuccessNotification("Inmuble Guardado");
	    else
		getABMView().showSuccessNotification("No se han realizado modificaciones");

	} catch (ValidationException e) {
	    e.printStackTrace();
	    Notification.show("Error al guardar, porfavor revise los campos e intente de nuevo", Notification.Type.WARNING_MESSAGE);
	    return;
	} catch (Exception e) {
	    e.printStackTrace();
	    Notification.show("Error: " + e.toString(), Notification.Type.WARNING_MESSAGE);
	}

	abmView.getController().updateList();
	setVisible(false);
	getABMView().setComponentsVisible(true);

    }

    public void cancel() {
	abmView.getController().updateList();
	setVisible(false);
	getABMView().setComponentsVisible(true);
    }

    public InmuebleABMView getABMView() {
	return abmView;
    }

    public void clearFields() {
	this.aEstrenar.clear();
	this.aireAcond.clear();
	this.aireAcond.clear();
	this.ambientes.clear();
	this.calle.clear();
	this.cJardin.clear();
	this.clasesInmueble.clear();
	this.cocheras.clear();
	this.codPostal.clear();
	this.comboPropietario.clear();
	this.cParrilla.clear();
	this.cPpileta.clear();
	this.dormitorios.clear();
	this.localidades.clear();
	this.nro.clear();
	this.provincias.clear();
	this.supCubierta.clear();
	this.supTotal.clear();
	this.tiposInmueble.clear();	
    }

}
