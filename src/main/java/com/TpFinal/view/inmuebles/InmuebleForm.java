package com.TpFinal.view.inmuebles;

import java.util.Collections;

import com.TpFinal.data.dto.Localidad;
import com.TpFinal.data.dto.Provincia;
import com.TpFinal.data.dto.inmueble.ClaseInmueble;
import com.TpFinal.data.dto.inmueble.Inmueble;
import com.TpFinal.data.dto.inmueble.TipoInmueble;
import com.TpFinal.data.dto.persona.Propietario;
import com.TpFinal.services.InmuebleService;
import com.TpFinal.services.PersonaService;
import com.vaadin.data.BeanValidationBinder;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.converter.StringToBigIntegerConverter;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

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
    private Inmueble inmueble;
    
    //Acciones
    private Button save = new Button("Guardar");
    private Button test = new Button("Test");
    private Button delete = new Button("Eliminar");
    
    //TabPrincipal
    private ComboBox<Propietario> propietario = new ComboBox<>();
    private Button nuevoPropietario  = new Button();
    private ComboBox <ClaseInmueble> clasesInmueble = new ComboBox<>("Clase", ClaseInmueble.toList());
    private RadioButtonGroup <TipoInmueble> tiposInmueble = new RadioButtonGroup<>("Tipo", TipoInmueble.toList());
    
    //TabDireccion
    private TextField calle = new TextField("Calle");
    private TextField nro = new TextField("Número");
    private TextField codPostal = new TextField ("Código Postal");
    
    private ComboBox<Localidad> localidades = new ComboBox<>("Localidad");
    private ComboBox <Provincia> provincias = new ComboBox<>("Provincia");
        

    
    //TabCaracteristicas 1
    private TextField ambientes = new TextField("Ambientes");
    private TextField cocheras = new TextField("Cocheras");
    private TextField dormitorios = new TextField("Dormitorios");
    private TextField supTotal = new TextField("Sup. Total");
    private TextField supCubierta = new TextField("Sup. Cubierta");
    
    //TabCaracteristicas 2
    private CheckBox aEstrenar = new CheckBox("A estrenar");
    private CheckBox aireAcond = new CheckBox("Aire Acondicionado");
    private CheckBox cJardin= new CheckBox("Jardín");
    private CheckBox cParrilla = new CheckBox("Parrilla");
    private CheckBox cPpileta = new CheckBox("Pileta");
    
    PersonaService service = new PersonaService();
    private InmuebleABMView inmuebleABMView;
    private Binder<Inmueble> binderInmueble = new Binder<>(Inmueble.class);
    
    TabSheet tabSheet;

    // Easily binding forms to beans and manage validation and buffering

    public InmuebleForm(InmuebleABMView abmView) {
	// setSizeUndefined();
	inmuebleABMView = abmView;
	configureComponents();
	binding();
	buildLayout();

    }

    private void configureComponents() {
	delete.setStyleName(ValoTheme.BUTTON_DANGER);
	save.addClickListener(e -> this.save());
	test.addClickListener(e -> this.test());
	delete.addClickListener(e -> this.delete());
	save.setStyleName(ValoTheme.BUTTON_PRIMARY);
	save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

	setVisible(false);
    }

    private void binding() {
	binderInmueble.forField(calle).bind(inmueble -> inmueble.getDireccion().getCalle()
		,(inmueble, street) -> inmueble.getDireccion().setCalle(street));
	
	binderInmueble.forField(this.aEstrenar).bind(Inmueble::getaEstrenar,Inmueble::setaEstrenar);
	binderInmueble.forField(this.aireAcond).bind(Inmueble::getConAireAcondicionado,Inmueble::setConAireAcondicionado);
	
	binderInmueble.forField(this.ambientes)
	.withConverter(new StringToIntegerConverter("Debe ingresar un número"))
	.withValidator(n -> n>=0, "Debe ingresar un número no negativo")
	.bind(Inmueble::getCantidadAmbientes,Inmueble::setCantidadAmbientes);
	
	binderInmueble.forField(this.cJardin).bind(Inmueble::getConJardin, Inmueble::setConJardin);
	binderInmueble.forField(this.clasesInmueble).bind(Inmueble::getClaseInmueble,Inmueble::setClaseInmueble);
	binderInmueble.forField(this.cocheras)
	.withConverter(new StringToIntegerConverter("Debe ingresar un número"))
	.withValidator(n -> n>=0, "Debe ingresar un número no negativo")
	.bind(Inmueble::getCantidadCocheras,Inmueble::setCantidadCocheras);
	
	binderInmueble.forField(this.cParrilla).bind(Inmueble::getConParilla,Inmueble::setConParilla);
	binderInmueble.forField(this.cPpileta).bind(Inmueble::getConPileta,Inmueble::setConPileta);
	
	binderInmueble.forField(this.dormitorios)
	.withConverter(new StringToIntegerConverter("Debe ingresar un número"))
	.withValidator(n -> n>=0, "Debe ingresar un número no negativo")
	.bind(Inmueble::getCantidadDormitorios,Inmueble::setCantidadDormitorios);
	
	//TODO
//	binderInmueble.forField(this.localidades);
//	binderInmueble.forField(this.provincias);
	
	binderInmueble.forField(this.propietario).bind(Inmueble::getPropietario,Inmueble::setPropietario);
	
	binderInmueble.forField(this.supCubierta)
	.withConverter(new StringToIntegerConverter("Debe ingresar un número"))
	.withValidator(n -> n>=0, "Debe ingresar un número no negativo")
	.bind(Inmueble::getSuperficieCubierta,Inmueble::setSuperficieCubierta);
	
	binderInmueble.forField(this.supTotal)
	.withConverter(new StringToIntegerConverter("Debe ingresar un número"))
	.withValidator(n -> n>=0, "Debe ingresar un número no negativo")
	.bind(Inmueble::getSuperficieTotal,Inmueble::setSuperficieTotal);
	
	binderInmueble.forField(this.tiposInmueble).bind(Inmueble::getTipoInmueble,Inmueble::setTipoInmueble);
	
    }

    private void buildLayout() {
	addStyleName("v-scrollable");
	
	nuevoPropietario.setIcon(VaadinIcons.PLUS);
	
	
	propietario.addStyleName(ValoTheme.COMBOBOX_BORDERLESS);
	nuevoPropietario.addStyleName(ValoTheme.BUTTON_BORDERLESS);
	nuevoPropietario.addStyleName(ValoTheme.BUTTON_FRIENDLY);
	
	HorizontalLayout propietarioCombo = new HorizontalLayout();
	propietarioCombo.addComponents(propietario, nuevoPropietario);
	propietarioCombo.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
	propietarioCombo.setCaption("Propietario");
	propietarioCombo.setExpandRatio(propietario, 1f);
	tiposInmueble.addStyleName(ValoTheme.OPTIONGROUP_HORIZONTAL);
	
	
	FormLayout principal = new FormLayout(calle, localidades,provincias,propietarioCombo,clasesInmueble,tiposInmueble);
	FormLayout caracteristicas1 = new FormLayout(ambientes,cocheras,dormitorios,supTotal,supCubierta);
	FormLayout caracteristicas2 = new FormLayout(aEstrenar,aireAcond,cJardin,cParrilla,cPpileta);
	
	principal.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
	caracteristicas1.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
	caracteristicas2.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
	
	TabSheet tabsheet = new TabSheet();
	tabsheet.addTab(principal, "Datos Principales");
	tabsheet.addTab(caracteristicas1, "Características 1");
	tabsheet.addTab(caracteristicas2, "Características 2");
	
	HorizontalLayout actions = new HorizontalLayout(save, test, delete);
	addComponents(tabsheet,actions);
	actions.setSpacing(true);
		
    }

    public void setInmueble(Inmueble inmueble) {

	this.inmueble = inmueble;
	binderInmueble.readBean(inmueble);

	// Show delete button for only Persons already in the database
	delete.setVisible(inmueble.getId() != null);

	setVisible(true);
	getABMView().setComponentsVisible(false);
	if (getABMView().isIsonMobile())
	    this.focus();

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
//	nombre.setValue(DummyDataGenerator.randomFirstName());
//	apellido.setValue(DummyDataGenerator.randomLastName());
//	mail.setValue(nombre.getValue() + "@" + apellido.getValue() + ".com");
//	DNI.setValue(DummyDataGenerator.randomNumber(8));
//	telefono.setValue(DummyDataGenerator.randomPhoneNumber());
//	telefono2.setValue(DummyDataGenerator.randomPhoneNumber());
//	String info = DummyDataGenerator.randomText(80);
//	if (info.length() > 255) {
//	    info = info.substring(0, 255);
//
//	}
//	infoAdicional.setValue(info);
//
//	save();
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
	    getABMView().showSuccessNotification("Guardado: "  + inmueble.getDireccion().toString() + " de " +
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
