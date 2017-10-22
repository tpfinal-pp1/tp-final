package com.TpFinal.view.inmuebles;

import com.TpFinal.dto.Localidad;
import com.TpFinal.dto.Provincia;
import com.TpFinal.dto.inmobiliaria.Inmobiliaria;
import com.TpFinal.dto.inmueble.ClaseInmueble;
import com.TpFinal.dto.inmueble.Direccion;
import com.TpFinal.dto.inmueble.Inmueble;
import com.TpFinal.dto.inmueble.TipoInmueble;
import com.TpFinal.dto.persona.Persona;
import com.TpFinal.dto.persona.Propietario;
import com.TpFinal.dto.publicacion.Rol;
import com.TpFinal.services.InmobiliariaService;
import com.TpFinal.services.InmuebleService;
import com.TpFinal.services.PersonaService;
import com.TpFinal.services.ProvinciaService;
import com.TpFinal.utils.Utils;

import com.TpFinal.view.component.BlueLabel;
import com.TpFinal.view.component.DeleteButton;
import com.TpFinal.view.component.TinyButton;
import com.TpFinal.view.component.UploadButton;
import com.TpFinal.view.component.UploadReceiver;
import com.TpFinal.view.inmobiliaria.InmobiliariaWindow;

import com.TpFinal.view.persona.PersonaFormWindow;
import com.google.gwt.dom.client.Style;
import com.vaadin.data.Binder;
import com.vaadin.data.BindingValidationStatus;
import com.vaadin.data.HasValue;
import com.vaadin.data.ValidationException;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Resource;
import com.vaadin.server.Setter;
import com.vaadin.server.StreamResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.easymock.internal.matchers.Not;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("serial")
public class InmuebleForm extends FormLayout {
    private InmuebleService inmbService = new InmuebleService();
    private PersonaService personaService = new PersonaService();
    private InmobiliariaService inmobiliariaService = new InmobiliariaService();
    private Inmueble inmueble;
    private Inmobiliaria inmobiliaria = new Inmobiliaria();

    // Acciones
    private Button save = new Button("Guardar");
    DeleteButton delete = new DeleteButton("Eliminar",
	    VaadinIcons.WARNING, "¿Esta seguro que desea eliminar?", "20%", new Button.ClickListener() {
		@Override
		public void buttonClick(Button.ClickEvent clickEvent) {
		    delete();
		}
	    });

    // TabSheet
    TabSheet inmuebleFromTabSheet;
    FormLayout principal;
    FormLayout caracteristicas1;

    // TabPrincipal
    private final ComboBox<Persona> comboPropietario = new ComboBox<>();
    private final ComboBox<Inmobiliaria> comboInmobiliaria = new ComboBox<>();
    private Persona persona = new Persona();
    private Button btnNuevoPropietario = new Button(VaadinIcons.PLUS);
    private Button btnNuevaInmobiliaria = new Button(VaadinIcons.PLUS);
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

    private InmuebleABMView abmView;
    private Binder<Inmueble> binderInmueble = new Binder<>(Inmueble.class);
    private ProvinciaService provinciaService = new ProvinciaService();

    private Image imagen;

    TabSheet tabSheet;

    // TODO Boton test solo para testing (completado de comboboxes) comentar antes
    // de cada reunion formal
    Button test = new Button("llenar combo");

    public InmuebleForm(InmuebleABMView abmView) {
	this.abmView = abmView;
	configureComponents();
	binding();
	buildLayout();
	updateComboPersonas();
	updateComboInmobiliaria();
    }

    private void configureComponents() {
	delete.setStyleName(ValoTheme.BUTTON_DANGER);
	save.addClickListener(e -> this.save());

	btnNuevoPropietario.addClickListener(e -> this.setNewPropietario());
	btnNuevaInmobiliaria.addClickListener(e -> this.setNewInmobiliaria());
	save.setStyleName(ValoTheme.BUTTON_PRIMARY);
	save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
	setVisible(false);
	localidades.setItems(provinciaService.getLocalidades());
	provincias.setItems(provinciaService.getProvincias());

	// provincias.setTextInputAllowed(false);
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
		    String CP = valueChangeEvent.getValue().getCodigoPostal();
		    if (!CP.equals("0"))
			codPostal.setValue(CP);

		    else
			codPostal.setValue("");
		}

	    }

	});

	// TODO funcion del boton test
	test.addClickListener(event -> {
	    Persona p;
	    List<Persona> people = personaService.readAll();
	    p = people.get(0);
	    comboPropietario.setSelectedItem(p);

	    Provincia provincia;
	    List<Provincia> provincess = provinciaService.getProvincias();
	    provincia = provincess.get(0);
	    provincias.setSelectedItem(provincia);
	    // TODO comment

	});

	comboPropietario.setTextInputAllowed(true);
	comboInmobiliaria.setTextInputAllowed(true);
	clasesInmueble.setTextInputAllowed(true);
	localidades.setTextInputAllowed(true);
	provincias.setTextInputAllowed(true);

    }

    private void setNewPropietario() {
	this.persona = new Persona();
	persona.addRol(new Propietario());
	Propietario propietario = (Propietario) persona.getRol(Rol.Propietario);
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

    private void setNewInmobiliaria() {
	this.inmobiliaria = new Inmobiliaria();
	this.inmobiliaria.addInmueble(this.inmueble);
	inmobiliaria.setDireccion(new Direccion.Builder().build());

	new InmobiliariaWindow(this.inmobiliaria) {

	    @Override
	    public void onSave() {
		inmobiliariaService.saveOrUpdate(inmobiliaria);
		updateComboInmobiliaria();
		comboInmobiliaria.setSelectedItem(inmobiliaria);
		System.out.println("Cantidad de personas despues de guardar " + inmobiliariaService.readAll().size());
	    }
	};
    }

    private void updateComboInmobiliaria() {
	List<Inmobiliaria> inms = this.inmobiliariaService.readAll();
	comboInmobiliaria.setItems(inms);
	System.out.println(inms.size());
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

	binderInmueble.forField(this.localidades).withValidator(localidad -> localidades.isEnabled(),
		"Debe seleccionar una provincia primero")
		.asRequired("Seleccione una localidad").bind(inmueble -> {
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

	binderInmueble.forField(this.comboInmobiliaria)
		.bind(inmueble -> inmueble.getInmobiliaria(), setInmobiliaria());

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

    private Setter<Inmueble, Inmobiliaria> setInmobiliaria() {
	return (inmueble, inmobiliaria) -> {
	    if (inmobiliaria != null) {
		if (!inmobiliaria.getInmuebles().contains(inmueble)) {
		    inmobiliaria.addInmueble(inmueble);
		}
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
	comboInmobiliaria.addStyleName(ValoTheme.COMBOBOX_BORDERLESS);
	btnNuevaInmobiliaria.addStyleName(ValoTheme.BUTTON_BORDERLESS);
	btnNuevaInmobiliaria.addStyleName(ValoTheme.BUTTON_FRIENDLY);

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

	HorizontalLayout inmobiliariaCombo = new HorizontalLayout();
	inmobiliariaCombo.addComponents(comboInmobiliaria, btnNuevaInmobiliaria);
	inmobiliariaCombo.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
	inmobiliariaCombo.setCaption("inmobiliaria");
	inmobiliariaCombo.setExpandRatio(comboInmobiliaria, 1f);

	imagen = new Image(null, null);
	imagen.setWidth(100.0f, Unit.PIXELS);
	imagen.setCaption(null);
	VerticalLayout portada = new VerticalLayout(imagen, new UploadButton(new UploadReceiver() {
	    @Override
	    public void onSuccessfullUpload(String filename) {
		if (filename != null && filename != "") {
		    System.out.println("fILENAME:" + filename);
		    inmueble.setNombreArchivoPortada(filename);
		    imagen.setIcon(null);
		    if (inmueble.getId() != null)
			imagen.setSource(inmbService.getPortada(inmueble)); // TODO DEJAR LINDO TODO ESTO
		    else {
			imagen.setSource(new StreamResource(new StreamResource.StreamSource() {
			    @Override
			    public InputStream getStream() {
				try {

				    return new FileInputStream("Files" + File.separator + filename);
				} catch (FileNotFoundException e) {

				}
				return null;
			    }
			}, "Files" + File.separator + inmueble.getNombreArchivoPortada()));
		    }
		    imagen.setWidth(100.0f, Unit.PIXELS);
		    imagen.setCaption(null);
		}

	    }
	}));
	portada.setCaption("Portada");
	buscarUbicacion.setCaption("Ubicación");
	portada.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
	buscarUbicacion.setCaption("Ubicación");

	principal = new FormLayout(propietarioCombo, inmobiliariaCombo, clasesInmueble, tiposInmueble,
		new BlueLabel("Direccion"), calle, nro, provincias, localidades, codPostal,new BlueLabel("Adicional"), portada,buscarUbicacion);

	caracteristicas1 = new FormLayout(ambientes, cocheras, dormitorios, supTotal,
		supCubierta, new BlueLabel("Adicionales"), aEstrenar, aireAcond, cJardin, cParrilla, cPpileta);

	this.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);

	principal.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);

	caracteristicas1.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);

	inmuebleFromTabSheet = new TabSheet();
	inmuebleFromTabSheet.addTab(principal, "Datos Principales");
	inmuebleFromTabSheet.addTab(caracteristicas1, "Características");

	// pic.setSpacing(true);

	HorizontalLayout actions = new HorizontalLayout(save, delete, test);
	addComponents(inmuebleFromTabSheet, actions);
	actions.setSpacing(true);

	inmuebleFromTabSheet.setSelectedTab(principal);
	principal.addComponents();

    }

    public void setInmueble(Inmueble inmueble) {

	if (inmueble != null) {
	    this.inmueble = inmueble;
	    binderInmueble.readBean(this.inmueble);
	    Resource res = inmbService.getPortada(this.inmueble);
	    if (res == null) {
		imagen.setIcon(new ThemeResource("sinPortada.png"));
		imagen.setSource(null);
	    } else {
	    	imagen.setIcon(null);
		imagen.setSource(res);
	    }

	    Notification.show(this.inmueble.getNombreArchivoPortada());
	    delete.setVisible(true);
	} else {
	    imagen.setSource(null);
	    imagen.setIcon(new ThemeResource("sinPortada.png"));
	    this.inmueble = InmuebleService.getInstancia();
	    localidades.setEnabled(false);
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
	    Notification.show(inmueble.nombreArchivoPortada);
	    if (inmueble.getPropietario().getPersona() != null)
		success = inmbService.merge(inmueble);
	    if (success)
		getABMView().showSuccessNotification("Inmuble Guardado");
	    else
		getABMView().showSuccessNotification("No se han realizado modificaciones");

	} catch (ValidationException e) {
	    Utils.mostarErroresValidator(e);
	    checkFieldsPerTab(e.getFieldValidationErrors());

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
	this.comboInmobiliaria.clear();
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

    private void checkFieldsPerTab(List<BindingValidationStatus<?>> invalidComponents) {
	boolean tabPrincipalInvalidFields = false;
	boolean tabCaracteristicasInvalidFields = false;
	// TabElements for tab principal
	List<Component> tabPrincipalComponents = new ArrayList<Component>();
	tabPrincipalComponents.add(comboPropietario);
	tabPrincipalComponents.add(comboInmobiliaria);
	tabPrincipalComponents.add(clasesInmueble);
	tabPrincipalComponents.add(tiposInmueble);
	tabPrincipalComponents.add(new BlueLabel("Direccion"));
	tabPrincipalComponents.add(calle);
	tabPrincipalComponents.add(nro);
	tabPrincipalComponents.add(provincias);
	tabPrincipalComponents.add(localidades);
	tabPrincipalComponents.add(codPostal);
	tabPrincipalComponents.add(buscarUbicacion);
	for (BindingValidationStatus invalidField : invalidComponents) {
	    tabPrincipalInvalidFields = tabPrincipalComponents.contains(invalidField.getField());
	    if (tabPrincipalInvalidFields)
		break;
	}
	System.out.println(tabPrincipalInvalidFields);
	// Tab elements for tab caracteristicas
	List<Component> tapCaracteristicasComponents = new ArrayList<Component>();
	tapCaracteristicasComponents.add(ambientes);
	tapCaracteristicasComponents.add(cocheras);
	tapCaracteristicasComponents.add(dormitorios);
	tapCaracteristicasComponents.add(new BlueLabel("Adiconales"));
	tapCaracteristicasComponents.add(supTotal);
	tapCaracteristicasComponents.add(supCubierta);
	tapCaracteristicasComponents.add(aEstrenar);
	tapCaracteristicasComponents.add(aireAcond);
	tapCaracteristicasComponents.add(cJardin);
	tapCaracteristicasComponents.add(cParrilla);
	tapCaracteristicasComponents.add(cPpileta);
	for (BindingValidationStatus invalidField : invalidComponents) {
	    tabCaracteristicasInvalidFields = tapCaracteristicasComponents.contains(invalidField.getField());
	    if (tabCaracteristicasInvalidFields)
		break;
	}
	System.out.println(tabCaracteristicasInvalidFields);
	if (tabPrincipalInvalidFields && !tabCaracteristicasInvalidFields) {
	    Notification.show("Error al guardar, porfavor revise los campos principales",
		    Notification.Type.WARNING_MESSAGE);
	    inmuebleFromTabSheet.setSelectedTab(principal);
	}

	else if (!tabPrincipalInvalidFields && tabCaracteristicasInvalidFields) {
	    Notification.show("Error al guardar, porfavor revise las caracterisitcas del inmueble e intente de nuevo",
		    Notification.Type.WARNING_MESSAGE);
	    inmuebleFromTabSheet.setSelectedTab(caracteristicas1);
	} else {
	    Notification.show("Error al guardar, porfavor revise los campos e intente de nuevo",
		    Notification.Type.WARNING_MESSAGE);
	}
    }

}
