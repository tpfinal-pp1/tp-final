package com.TpFinal.view.inmuebles;

import com.TpFinal.data.dto.LocalidadRAW;
import com.TpFinal.data.dto.Provincia;
import com.TpFinal.data.dto.inmueble.ClaseInmueble;
import com.TpFinal.data.dto.inmueble.Coordenada;
import com.TpFinal.data.dto.inmueble.Direccion;
import com.TpFinal.data.dto.inmueble.EstadoInmueble;
import com.TpFinal.data.dto.inmueble.Inmueble;
import com.TpFinal.data.dto.inmueble.TipoInmueble;
import com.TpFinal.data.dto.persona.Persona;
import com.TpFinal.data.dto.persona.Propietario;
import com.TpFinal.data.dto.publicacion.Rol;
import com.TpFinal.services.InmuebleService;
import com.TpFinal.services.PersonaService;
import com.TpFinal.services.ProvinciaService;
import com.TpFinal.utils.GeneradorDeDatos;
import com.TpFinal.view.component.BlueLabel;
import com.TpFinal.view.component.TinyButton;
import com.TpFinal.view.persona.PersonaFormWindow;
import com.vaadin.data.Binder;
import com.vaadin.data.Binder.BindingBuilder;
import com.vaadin.data.HasValue;
import com.vaadin.data.ValidationException;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Setter;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.io.File;
import java.time.LocalDate;

/* Create custom UI Components.
 *
 * Create your own Vaadin components by inheritance and composition.
 * This is a form component inherited from VerticalLayout. Use
 * Use BeanFieldGroup to binding data fields from DTO to UI fields.
 * Similarly named field by naming convention or customized
 * with @PropertyId annotation.
 */
@SuppressWarnings("serial")
public class InmuebleForm extends FormLayout {
    private InmuebleService inmbService = new InmuebleService();
    private PersonaService personaService = new PersonaService();
    private Inmueble inmueble;

    // Acciones
    private Button save = new Button("Guardar");
    private Button test = new Button("Test");
    private Button delete = new Button("Eliminar");

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
    private ComboBox<LocalidadRAW> localidades = new ComboBox<>("Localidad");
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
    private InmuebleABMView inmuebleABMView;
    private Binder<Inmueble> binderInmueble = new Binder<>(Inmueble.class);
    private ProvinciaService serviceProvincia= new ProvinciaService("src"+File.separator+"main"+File.separator+"webapp"+File.separator+"Localidades.json");

    TabSheet tabSheet;

    // Easily binding forms to beans and manage validation and buffering

    public InmuebleForm(InmuebleABMView abmView) {
	// setSizeUndefined();
	inmuebleABMView = abmView;
	configureComponents();
	binding();
	buildLayout();
	updateComboPersonas();

    }

    private void configureComponents() {
	delete.setStyleName(ValoTheme.BUTTON_DANGER);
	save.addClickListener(e -> this.save());
	test.addClickListener(e -> this.test());
	delete.addClickListener(e -> this.delete());

	btnNuevoPropietario.addClickListener(e -> this.setNewPropietario());
	save.setStyleName(ValoTheme.BUTTON_PRIMARY);
	save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
	setVisible(false);

	localidades.setItems(GeneradorDeDatos.getLocalidades());
	provincias.setItems(GeneradorDeDatos.getProvincias());

	provincias.addValueChangeListener(new HasValue.ValueChangeListener<Provincia>() {
	    @Override
	    public void valueChange(HasValue.ValueChangeEvent<Provincia> valueChangeEvent) {
		Provincia provincia = valueChangeEvent.getValue();
		if (provincia != null && !provincia.equals(provincias.getSelectedItem())) {
		    if (valueChangeEvent.getOldValue() != null) {
			//FIXME
//			localidades.setItems(provincia.getLocalidades());
//			localidades.setSelectedItem(provincia.getLocalidades().get(0));
		    }
		}

	    }
	});

	localidades.addValueChangeListener(new HasValue.ValueChangeListener<LocalidadRAW>() {
	    @Override
	    public void valueChange(HasValue.ValueChangeEvent<LocalidadRAW> valueChangeEvent) {

		if (valueChangeEvent.getValue() != null) {
		    // provincias.setSelectedItem(valueChangeEvent.getValue().getProvincia());
		    // //FIXME
		    codPostal.setValue(valueChangeEvent.getValue().getCodPosta());
		}

	    }

	});
	codPostal.setEnabled(false);

    }

    private void setNewPropietario() {
	this.persona = new Persona();
	persona.addRol(Rol.Propietario);
	Propietario propietario = persona.getPropietario();
	propietario.addInmueble(this.inmueble);
	new PersonaFormWindow(this.persona) {
	    @Override
	    public void onSave() {
		updateComboPersonas();
		comboPropietario.setSelectedItem(persona);

	    }
	};

    }

    private void binding() {

	// binderInmueble.forField(calle).bind(Dire,Inmueble::setaEstrenar);

	binderInmueble.forField(this.aEstrenar)

		.bind(Inmueble::getaEstrenar, Inmueble::setaEstrenar);

	binderInmueble.forField(this.aireAcond)

		.bind(Inmueble::getConAireAcondicionado, Inmueble::setConAireAcondicionado);

	binderInmueble.forField(this.ambientes).withNullRepresentation("")
		.withConverter(new StringToIntegerConverter("Debe ingresar un número"))
		.withNullRepresentation(0)
		.withValidator(n -> n >= 0, "Debe ingresar un número no negativo")
		.bind(Inmueble::getCantidadAmbientes, Inmueble::setCantidadAmbientes);

	binderInmueble.forField(this.cJardin)

		.bind(Inmueble::getConJardin, Inmueble::setConJardin);

	binderInmueble.forField(this.clasesInmueble)

		.bind(Inmueble::getClaseInmueble, Inmueble::setClaseInmueble);

	binderInmueble.forField(this.cocheras)
		.withNullRepresentation("")
		.withConverter(new StringToIntegerConverter("Debe ingresar un número"))
		.withNullRepresentation(0)
		.withValidator(n -> n >= 0, "Debe ingresar un número no negativo")
		.bind(Inmueble::getCantidadCocheras, Inmueble::setCantidadCocheras);

	binderInmueble.forField(this.cParrilla)

		.bind(Inmueble::getConParilla, Inmueble::setConParilla);

	binderInmueble.forField(this.cPpileta)

		.bind(Inmueble::getConPileta, Inmueble::setConPileta);

	binderInmueble.forField(this.dormitorios)
		.withNullRepresentation("")
		.withConverter(new StringToIntegerConverter("Debe ingresar un número"))
		.withNullRepresentation(0)
		.withValidator(n -> n >= 0, "Debe ingresar un número no negativo")

		.bind(Inmueble::getCantidadDormitorios, Inmueble::setCantidadDormitorios);

	binderInmueble.forField(this.codPostal)
		.withNullRepresentation("")
		.bind(inmueble -> inmueble.getDireccion().getCodPostal(),
			(inmueble, cod) -> inmueble.getDireccion().setCodPostal(cod));

	binderInmueble.forField(this.nro)
		.withNullRepresentation("")
		.withConverter(new StringToIntegerConverter("Debe ingresar un número"))
		.withNullRepresentation(0)
		.withValidator(n -> n >= 0, "Debe ingresar una altura no negativa!")

		.bind(inmueble -> inmueble.getDireccion().getNro(),
			(inmueble, nro) -> inmueble.getDireccion().setNro(nro));

	binderInmueble.forField(this.calle)
		.withNullRepresentation("")
		.bind(inmueble -> inmueble.getDireccion().getCalle(),
			(inmueble, calle) -> inmueble.getDireccion().setCalle(calle));

	/*
	 * binderInmueble.forField(this.localidades).bind(inmueble ->
	 * inmueble.getDireccion() ,(inmueble,calle) ->
	 * inmueble.getDireccion().setCalle(calle));
	 */
	// TODO*/
	// binderInmueble.forField(this.localidades);
	// binderInmueble.forField(this.provincias);

	binderInmueble.forField(this.comboPropietario)
		.withNullRepresentation(new Persona())
		.bind(inmueble -> inmueble.getPropietario().getPersona(), setPropietario());

	binderInmueble.forField(this.supCubierta)
		.withNullRepresentation("")
		.withConverter(new StringToIntegerConverter("Debe ingresar un número"))
		.withNullRepresentation(0)
		.withValidator(n -> n >= 0, "Debe ingresar un número no negativo")
		.bind(Inmueble::getSuperficieCubierta, Inmueble::setSuperficieCubierta);

	binderInmueble.forField(this.supTotal)
		.withNullRepresentation("")
		.withConverter(new StringToIntegerConverter("Debe ingresar un número"))
		.withNullRepresentation(0)
		.withValidator(n -> n >= 0, "Debe ingresar un número no negativo")
		.bind(Inmueble::getSuperficieTotal, Inmueble::setSuperficieTotal);

	binderInmueble.forField(this.tiposInmueble)
		.bind(Inmueble::getTipoInmueble, Inmueble::setTipoInmueble);

    }

    private Setter<Inmueble, Persona> setPropietario() {
	return (inmueble, persona) -> {

	};
    }

    private void buildLayout() {
	// addStyleName("v-scrollable");

	btnNuevoPropietario.setIcon(VaadinIcons.PLUS);
	comboPropietario.addStyleName(ValoTheme.COMBOBOX_BORDERLESS);
	btnNuevoPropietario.addStyleName(ValoTheme.BUTTON_BORDERLESS);
	btnNuevoPropietario.addStyleName(ValoTheme.BUTTON_FRIENDLY);

	if (this.inmuebleABMView.isIsonMobile()) {

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
		new BlueLabel("Direccion"), calle, nro, codPostal, localidades, provincias, buscarUbicacion);

	FormLayout caracteristicas1 = new FormLayout(ambientes, cocheras, dormitorios, supTotal,
		supCubierta, new BlueLabel("Adiconales"), aEstrenar, aireAcond, cJardin, cParrilla, cPpileta);

	this.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);

	principal.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);

	caracteristicas1.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);

	TabSheet tabsheet = new TabSheet();
	tabsheet.addTab(principal, "Datos Principales");
	tabsheet.addTab(caracteristicas1, "Características");

	HorizontalLayout actions = new HorizontalLayout(save, test, delete);
	addComponents(tabsheet, actions);
	actions.setSpacing(true);

    }

    public void setInmueble(Inmueble inmueble) {

	this.inmueble = inmueble;

	System.out.println(this.inmueble);
	binderInmueble.readBean(this.inmueble);

	// Show delete button for only Persons already in the database
	delete.setVisible(inmueble.getId() != null);

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
	inmuebleABMView.getController().updateList();
	setVisible(false);
	getABMView().setComponentsVisible(true);
	getABMView().showSuccessNotification("Borrado: " + inmueble.getDireccion().toString() + " de " +
		inmueble.getPropietario().toString());

    }

    private void test() {
	// nombre.setValue(DummyDataGenerator.randomFirstName());
	// apellido.setValue(DummyDataGenerator.randomLastName());
	// mail.setValue(nombre.getValue() + "@" + apellido.getValue() + ".com");
	// DNI.setValue(DummyDataGenerator.randomNumber(8));
	// telefono.setValue(DummyDataGenerator.randomPhoneNumber());
	// telefono2.setValue(DummyDataGenerator.randomPhoneNumber());
	// String info = DummyDataGenerator.randomText(80);
	// if (info.length() > 255) {
	// info = info.substring(0, 255);
	//
	// }
	// infoAdicional.setValue(info);
	//
	// save();
	//
    }

    private void save() {

	boolean success = false;
	try {
	    binderInmueble.writeBean(inmueble);
	    inmbService.saveOrUpdate(inmueble);
	    success = true;

	} catch (ValidationException e) {
	    e.printStackTrace();
	    Notification.show("Error al guardar, porfavor revise los campos e intente de nuevo");
	    // Notification.show("Error: "+e.getCause());
	    return;
	} catch (Exception e) {
	    e.printStackTrace();
	    Notification.show("Error: " + e.toString());
	}

	inmuebleABMView.getController().updateList();
	setVisible(false);
	getABMView().setComponentsVisible(true);

	if (success)
	    getABMView().showSuccessNotification("Guardado: " + inmueble.getDireccion().toString() + " de " +
		    inmueble.getPropietario().toString());

    }

    public void cancel() {
	inmuebleABMView.getController().updateList();
	setVisible(false);
	getABMView().setComponentsVisible(true);
    }

    public InmuebleABMView getABMView() {
	return inmuebleABMView;
    }

}
