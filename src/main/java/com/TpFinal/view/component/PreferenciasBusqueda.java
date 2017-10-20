package com.TpFinal.view.component;

import java.math.BigDecimal;

import com.TpFinal.dto.Localidad;
import com.TpFinal.dto.Provincia;
import com.TpFinal.dto.inmueble.ClaseInmueble;
import com.TpFinal.dto.inmueble.CriterioBusqInmueble;
import com.TpFinal.dto.inmueble.Direccion;
import com.TpFinal.dto.inmueble.EstadoInmueble;
import com.TpFinal.dto.inmueble.TipoInmueble;
import com.TpFinal.dto.inmueble.TipoMoneda;
import com.TpFinal.dto.persona.Persona;
import com.TpFinal.services.InmuebleService;
import com.TpFinal.services.PersonaService;
import com.TpFinal.services.ProvinciaService;
import com.TpFinal.utils.Utils;
import com.TpFinal.view.inmuebles.InmuebleABMView;
import com.TpFinal.view.inmuebles.InmuebleABMViewWindow;
import com.vaadin.data.Binder;
import com.vaadin.data.HasValue;
import com.vaadin.data.ValidationException;
import com.vaadin.data.ValueProvider;
import com.vaadin.data.converter.StringToBigDecimalConverter;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.Setter;
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

    private CriterioBusqInmueble criterio;
    private Binder<CriterioBusqInmueble> binderBusqueda = new Binder<>(CriterioBusqInmueble.class);
    PersonaService personaService = new PersonaService();
    InmuebleService inmuebleService = new InmuebleService();
    ProvinciaService provinciaService = new ProvinciaService();
    // Componentes
    CheckBoxGroup<ClaseInmueble> clasesDeInmueble = new CheckBoxGroup<>("", ClaseInmueble.toList());
    ComboBox<Provincia> cbProvincia = new ComboBox<>("Provincia", provinciaService.getProvincias());
    ComboBox<Localidad> cbLocalidad = new ComboBox<>("Localidad", provinciaService.getLocalidades());
    RadioButtonGroup<TipoMoneda> rbgTipoMoneda = new RadioButtonGroup<>("Tipo Moneda", TipoMoneda.toList());
    RadioButtonGroup<TipoInmueble> rbgTipoInmueble = new RadioButtonGroup<>("Tipo", TipoInmueble.toList());
    RadioButtonGroup<EstadoInmueble> rbgEstadoInmueble = new RadioButtonGroup<>("Estado", EstadoInmueble
	    .toListPublicado());

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

    public PreferenciasBusqueda(CriterioBusqInmueble criterio) {
	super("Preferencias de Búsqueda");
	this.criterio = criterio;
	buildLayout();
	configureComponents();
	binding();
	setCriterio();
	UI.getCurrent().addWindow(this);
	this.focus();
    }

    private void buildLayout() {
	this.setWidthUndefined();
	setId(ID);
	Responsive.makeResponsive(this);

	setModal(true);
	setCloseShortcut(KeyCode.ESCAPE, null);
	setResizable(false);
	setClosable(true);
	setDraggable(false);
	setHeight(90.0f, Unit.PERCENTAGE);
	setWidth(45.0f, Unit.PERCENTAGE);
	center();

	VerticalLayout content = new VerticalLayout();
	content.setSizeFull();
	content.setMargin(new MarginInfo(true, true, false, false));
	setContent(content);

	TabSheet categoriasBusqueda = new TabSheet();
	categoriasBusqueda.setSizeFull();
	content.addComponent(categoriasBusqueda);
	content.setExpandRatio(categoriasBusqueda, 1f);
	categoriasBusqueda.addStyleName("test");
	categoriasBusqueda.addTab(caracteristicasPrincipales());
	categoriasBusqueda.addTab(caracteristicasAdicionales());
	categoriasBusqueda.addTab(clasesDeInmueble());
	content.addComponent(acciones());

    }

    private void cleanPreferences() {
	binderBusqueda.getFields().forEach(field -> field.clear());
	onClean();
    }

    private void search() {
	try {
	    binderBusqueda.writeBean(criterio);	   
	    InmuebleService inmuebleService = new InmuebleService();
	    new InmuebleABMViewWindow("Resultado Búsqueda", () -> inmuebleService.findByCaracteristicas(criterio));

	} catch (ValidationException e) {
	    Notification.show("Errores de Validación, por favor revise los campos e intente de nuevo",
		    Notification.Type.WARNING_MESSAGE);
	    Utils.mostarErroresValidator(e);
	    return;
	}

    }

    public abstract boolean onSave();
    public abstract boolean onClean();
    public abstract boolean searchVisible();

    private void configureComponents() {
	configureCombosProvinciaYLocalidad();
	this.buscar.setVisible(searchVisible());
    }

    private void configureCombosProvinciaYLocalidad() {
	cbLocalidad.setItems(provinciaService.getLocalidades());
	cbProvincia.setItems(provinciaService.getProvincias());
	cbProvincia.addValueChangeListener(new HasValue.ValueChangeListener<Provincia>() {
	    @Override
	    public void valueChange(HasValue.ValueChangeEvent<Provincia> valueChangeEvent) {
		Provincia provincia = valueChangeEvent.getValue();

		if (provincia != null) {
		    cbLocalidad.setEnabled(true);
		    cbLocalidad.setItems(provincia.getLocalidades());
		    cbLocalidad.setSelectedItem(provincia.getLocalidades().get(0));
		} else {
		    cbLocalidad.setEnabled(false);
		    cbLocalidad.setSelectedItem(null);
		}

	    }

	});
    }

    private void binding() {
	binderBusqueda.forField(this.aEstrenar).withNullRepresentation(false)
		.bind(CriterioBusqInmueble::getaEstrenar, CriterioBusqInmueble::setaEstrenar);
	binderBusqueda.forField(this.cbLocalidad)
		.bind(criterio -> {
		    String localidad = criterio.getLocalidad();
		    String provincia = criterio.getProvincia();
		    return (localidad != null && provincia != null) ? provinciaService
			    .getLocalidadFromNombreAndProvincia(localidad, provincia) : null;
		},
			(criterio, localidad) -> {
			    criterio.setLocalidad(localidad != null ? localidad.getNombre() : null);
			});

	binderBusqueda.forField(this.cbProvincia)
		.bind(criterio -> {
		    String provincia = criterio.getProvincia();
		    return provincia != null ? provinciaService.getProvinciaFromString(provincia) : null;
		},
			(criterio, provincia) -> {
			    criterio.setProvincia(provincia != null ? provincia.getNombre() : null);
			});
	binderBusqueda.forField(this.clasesDeInmueble)
		.bind(CriterioBusqInmueble::getClasesDeInmueble, CriterioBusqInmueble::setClasesDeInmueble);
	binderBusqueda.forField(this.conAireAcond).withNullRepresentation(false)
		.bind(CriterioBusqInmueble::getConAireAcondicionado, CriterioBusqInmueble::setConAireAcondicionado);
	binderBusqueda.forField(this.conJardin).withNullRepresentation(false)
		.bind(CriterioBusqInmueble::getConJardin, CriterioBusqInmueble::setConJardin);
	binderBusqueda.forField(this.conParrila).withNullRepresentation(false)
		.bind(CriterioBusqInmueble::getConParrilla, CriterioBusqInmueble::setConParrilla);
	binderBusqueda.forField(this.conPileta).withNullRepresentation(false)
		.bind(CriterioBusqInmueble::getConPileta, CriterioBusqInmueble::setConPileta);
	bindearMinMaxParaInteger(this.minMaxAmbientes,
		CriterioBusqInmueble::getMinCantAmbientes, CriterioBusqInmueble::setMinCantAmbientes,
		CriterioBusqInmueble::getMaxCantAmbientes, CriterioBusqInmueble::setMaxCantAmbientes);
	bindearMinMaxParaInteger(this.minMaxCocheras,
		CriterioBusqInmueble::getMinCantCocheras, CriterioBusqInmueble::setMinCantCocheras,
		CriterioBusqInmueble::getMaxCantCocheras, CriterioBusqInmueble::setMaxCantCocheras);
	bindearMinMaxParaInteger(this.minMaxDormitorios,
		CriterioBusqInmueble::getMinCantDormitorios, CriterioBusqInmueble::setMinCantDormitorios,
		CriterioBusqInmueble::getMaxCantDormitorios, CriterioBusqInmueble::setMaxCantDormitorios);
	bindearMinMaxParaInteger(this.minMaxSupCubierta,
		CriterioBusqInmueble::getMinSupCubierta, CriterioBusqInmueble::setMinSupCubierta,
		CriterioBusqInmueble::getMaxSupCubierta, CriterioBusqInmueble::setMaxSupCubierta);
	bindearMinMaxParaInteger(this.minMaxSupTotal,
		CriterioBusqInmueble::getMinSupTotal, CriterioBusqInmueble::setMinSupTotal,
		CriterioBusqInmueble::getMaxSupTotal, CriterioBusqInmueble::setMaxSupTotal);
	bindearMinMaxParaPrecio();
	binderBusqueda.forField(this.rbgEstadoInmueble)
		.bind(CriterioBusqInmueble::getEstadoInmueble, CriterioBusqInmueble::setEstadoInmueble);
	binderBusqueda.forField(this.rbgTipoInmueble)
		.bind(CriterioBusqInmueble::getTipoInmueble, CriterioBusqInmueble::setTipoInmueble);
	binderBusqueda.forField(this.rbgTipoMoneda)
		.bind(CriterioBusqInmueble::getTipoMoneda, CriterioBusqInmueble::setTipoMoneda);
    }

    private void bindearMinMaxParaInteger(MinMaxTextField minMaxtf,
	    ValueProvider<CriterioBusqInmueble, Integer> getterMin,
	    Setter<CriterioBusqInmueble, Integer> setterMin,
	    ValueProvider<CriterioBusqInmueble, Integer> getterMax,
	    Setter<CriterioBusqInmueble, Integer> setterMax) {
	binderBusqueda.forField(minMaxtf.getMinTextField())
		.withNullRepresentation("")
		.withConverter(new StringToIntegerConverter("Ingrese un número"))
		.withValidator(n -> {
		    boolean ret = false;
		    if (n == null) {
			ret = true;
		    } else {
			String val = minMaxtf.getMaxTextField().getValue();
			if (val != null) {
			    try {
				Integer v = Integer.parseInt(val);
				if (n <= v) {
				    ret = true;
				}
			    } catch (Exception e) {
				ret = true;
			    }
			} else {
			    ret = true;
			}
		    }
		    return ret;

		}, "Ingrese un número menor o igual al máximo!")
		.withValidator(n -> n == null || n >= 0, "Ingrese un número no negativo!")
		.bind(getterMin, setterMin);

	binderBusqueda.forField(minMaxtf.getMaxTextField())
		.withNullRepresentation("")
		.withConverter(new StringToIntegerConverter("Ingrese un número"))
		.withValidator(n -> {
		    boolean ret = false;
		    if (n == null) {
			ret = true;
		    } else {
			String val = minMaxtf.getMinTextField().getValue();
			if (val != null) {
			    try {
				Integer v = Integer.parseInt(val);
				if (n >= v) {
				    ret = true;
				}
			    } catch (Exception e) {
				ret = true;
			    }
			} else {
			    ret = true;
			}
		    }
		    return ret;

		}, "Ingrese un número mayor o igual al mínimo!")
		.withValidator(n -> n == null || n >= 0, "Ingrese un número no negativo!")
		.bind(getterMax, setterMin);
    }

    private void bindearMinMaxParaPrecio() {
	binderBusqueda.forField(minMaxPrecio.getMinTextField())
		.withNullRepresentation("")
		.withConverter(new StringToBigDecimalConverter("Ingrese un número"))
		.withValidator(n -> {
		    boolean ret = false;
		    if (n == null) {
			ret = true;
		    } else {
			String val = minMaxPrecio.getMaxTextField().getValue();
			if (val != null) {
			    try {
				BigDecimal v = BigDecimal.valueOf(Double.parseDouble(val));
				if (n.compareTo(v) <= 0) {
				    ret = true;
				}
			    } catch (Exception e) {
				ret = true;
			    }
			} else {
			    ret = true;
			}
		    }
		    return ret;

		}, "Ingrese un número menor o igual al máximo!")
		.withValidator(n -> {
		    return n == null || n.compareTo(BigDecimal.ZERO) >= 0;
		}, "Ingrese un número no negativo!")
		.bind(CriterioBusqInmueble::getMinPrecio, CriterioBusqInmueble::setMinPrecio);

	binderBusqueda.forField(minMaxPrecio.getMaxTextField())
		.withNullRepresentation("")
		.withConverter(new StringToBigDecimalConverter("Ingrese un número"))
		.withValidator(n -> {
		    boolean ret = false;
		    if (n == null) {
			ret = true;
		    } else {
			String val = minMaxPrecio.getMinTextField().getValue();
			if (val != null) {
			    try {
				BigDecimal v = BigDecimal.valueOf(Double.parseDouble(val));
				if (n.compareTo(v) >= 0) {
				    ret = true;
				}
			    } catch (Exception e) {
				ret = true;
			    }
			} else {
			    ret = true;
			}
		    }
		    return ret;

		}, "Ingrese un número mayor o igual al mínimo!")
		.withValidator(n -> {
		    return n == null || n.compareTo(BigDecimal.ZERO) >= 0;
		}, "Ingrese un número no negativo!")
		.bind(CriterioBusqInmueble::getMaxPrecio, CriterioBusqInmueble::setMaxPrecio);
    }

    private void setCriterio() {
	if (criterio != null) {
	    binderBusqueda.readBean(criterio);
	} else {
	    criterio = new CriterioBusqInmueble.Builder().build();
	}
	setVisible(true);
    }

    private void save() {
	boolean success = false;
	try {
	    binderBusqueda.writeBean(criterio);
	    success = onSave();
	} catch (ValidationException e) {
	    Notification.show("Errores de Validación, por favor revise los campos e intente de nuevo",
		    Notification.Type.WARNING_MESSAGE);
	    Utils.mostarErroresValidator(e);
	    return;
	}

	if (success) {
	    Notification exito = new Notification("Preferencias Guardadas");
	    exito.setDelayMsec(2000);
	    exito.setStyleName("bar success small");
	    exito.setPosition(Position.BOTTOM_CENTER);
	    exito.show(Page.getCurrent());
	    close();
	}

    }

    private Component acciones() {

	HorizontalLayout actions = new HorizontalLayout();
	actions.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
	actions.setWidth(100.0f, Unit.PERCENTAGE);
	actions.setSpacing(false);
	actions.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
	actions.addComponents(guardar, buscar, borrar);
	actions.forEach(c -> c.setWidth("90%"));

	return actions;
    }

    private HorizontalLayout clasesDeInmueble() {
	HorizontalLayout root = new HorizontalLayout();
	root.removeStyleName("v-scrollable");
	root.setCaption("Clases de Inmueble");
	root.setWidth(100.0f, Unit.PERCENTAGE);
	root.setMargin(true);

	FormLayout details = new FormLayout();
	details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
	root.addComponent(details);
	root.setExpandRatio(details, 1);

	details.addComponents(this.clasesDeInmueble);
	details.forEach(component -> component.setWidth("100%"));
	return root;
    }

    private Component caracteristicasPrincipales() {

	HorizontalLayout root = new HorizontalLayout();
	root.setCaption("Características Principales");
	root.setWidth(100.0f, Unit.PERCENTAGE);
	root.setMargin(true);

	FormLayout details = new FormLayout();
	details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
	root.addComponent(details);
	root.setExpandRatio(details, 1);
	rbgTipoInmueble.addStyleName(ValoTheme.OPTIONGROUP_HORIZONTAL);
	rbgEstadoInmueble.addStyleName(ValoTheme.OPTIONGROUP_HORIZONTAL);
	rbgTipoMoneda.addStyleName(ValoTheme.OPTIONGROUP_HORIZONTAL);
	details.addComponents(
		this.rbgTipoInmueble,
		this.rbgEstadoInmueble,
		this.cbProvincia,
		this.cbLocalidad,
		this.minMaxPrecio,
		this.rbgTipoMoneda);

	details.forEach(component -> component.setWidth("100%"));
	return root;
    }

    private Component caracteristicasAdicionales() {

	HorizontalLayout root = new HorizontalLayout();
	root.setCaption("Características Adicionales");
	root.setWidth(100.0f, Unit.PERCENTAGE);
	root.setMargin(true);

	FormLayout details = new FormLayout();
	details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
	root.addComponent(details);
	root.setExpandRatio(details, 1);

	details.addComponents(
		this.minMaxAmbientes,
		this.minMaxCocheras,
		this.minMaxDormitorios,
		this.minMaxSupTotal,
		this.minMaxSupCubierta,
		this.aEstrenar,
		this.conAireAcond,
		this.conJardin,
		this.conParrila,
		this.conPileta);
	details.forEach(component -> component.setWidth("100%"));
	return root;
    }

    public CriterioBusqInmueble getCriterio() {
	return criterio;
    }

}
