package com.TpFinal.view.reportes;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import com.TpFinal.utils.Utils;
import com.TpFinal.view.component.PDFComponent;
import com.vaadin.data.HasValue;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

import com.TpFinal.data.dao.DAOContratoAlquilerImpl;
import com.TpFinal.data.dao.interfaces.DAOContratoAlquiler;
import com.TpFinal.dto.cobro.EstadoCobro;
import com.TpFinal.dto.persona.Rol;
import com.TpFinal.services.ContratoService;
import com.TpFinal.services.PersonaService;
import com.TpFinal.view.component.DefaultLayout;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

/* User Interface written in Java.
 *
 * Define the user interface shown on the Vaadin generated web page by extending the UI class.
 * By default, a new UI instance is automatically created when the page is loaded. To reuse
 * the same instance, add @PreserveOnRefresh.
 */

@Title("Reportes")
@Theme("valo")
@Widgetset("com.vaadin.v7.Vaadin7WidgetSet")
public class ReportesView extends DefaultLayout implements View {

    private static DAOContratoAlquiler daoContratoAlquiler;
    // ContratoService service;

    private JasperReport reporte;
    private JasperPrint reporteLleno;
    Map<String, Object> parametersMap = new HashMap<String, Object>();
    private Button clearFilterTextBtn = new Button(VaadinIcons.CLOSE);
    PDFComponent pdfComponent = new PDFComponent();
    ComboBox<TipoReporte> tipoReporteCB = new ComboBox<TipoReporte>(
	    null, TipoReporte.toList());
    CheckBox checkbox = new CheckBox("Incluir Cobros Pendientes");
    String reportName = "";
    Button newReport = new Button("Generar");
    Notification error;

    DateField fDesde = null;
    DateField fHasta = null;

    List<Object> objects = null;
    boolean conCobrosPendientes = false;

    public enum TipoReporte {
	Propietario("ReportePropietarios.jasper"), AlquileresPorCobrar("ReporteAlquileresPorCobrar.jasper"), 
	AlquileresPorMes("ReporteAlquileresPorMess.jasper");

	private final String archivoReporte;

	TipoReporte(String archivoReporte) {
	    this.archivoReporte = archivoReporte;
	}

	@Override
	public String toString() {
	    switch (this) {
	    case Propietario:
		return "Propietario";
	    case AlquileresPorCobrar:
		return "Alquileres a Cobrar";
	    case AlquileresPorMes:
	    return "Alquileres por Mes";
	    default:
		return super.toString();

	    }
	}

	public static List<TipoReporte> toList() {
	    TipoReporte[] clases = TipoReporte.values();
	    List<TipoReporte> ret = new ArrayList<>();
	    for (TipoReporte c : clases) {
		ret.add(c);
	    }
	    return ret;
	}

	public String getArchivoReporte() {
	    return this.archivoReporte;
	}

    }

    public List<Object> getObjetos(TipoReporte tipo) {
	ArrayList<Object> objects = new ArrayList<>();
	ContratoService service = new ContratoService();
	List<ItemRepAlquileresACobrar> items = new ArrayList<ItemRepAlquileresACobrar>();

	System.out.println("Tipo" + tipo);
	switch (tipo) {
	case Propietario:
	    PersonaService servicePersona = new PersonaService();
	    objects.addAll(servicePersona.findForRole(
		    Rol.Propietario.toString()));
	    break;

	case AlquileresPorCobrar:
	    objects.addAll(filtrarPorRangos());
	    break;
	    
	case AlquileresPorMes:
		//objects.addAll(filtrarPorMes());
		break;

	}

	return objects;

    }
    
   /* public ItemRepAlquileresPorMes item() {
    	ContratoService service = new ContratoService();
    	return service.itemParaAlquileresPorMesPagosCobrados(fDesde.getValue());
    }
    */
  
    public ArrayList<Object> filtrarPorRangos() {
	ContratoService service = new ContratoService();
	ArrayList<Object> ret = new ArrayList<>();
	System.out.println(fDesde.toString().length() + "" + fHasta.toString().length());
	if (fHasta == null || fDesde == null) {
	    showErrorNotification("No hay Pagos en el rango de fecha seleccionado");
	    return new ArrayList<>(service.getCobrosOrdenadosPorAño());

	}

	if (fDesde.getValue() == null && fHasta.getValue() == null) {

	    return new ArrayList<>(service.getCobrosOrdenadosPorAño());
	}

	for (ItemRepAlquileresACobrar item : service.getListadoAlquileresACobrar(fDesde.getValue(), fHasta
		.getValue())) {

	    ret.add(item);

	}

	if (ret.size() == 0) {
	    showErrorNotification("No hay Pagos en el rango de fecha seleccionado");
	    return new ArrayList<>();
	}

	return ret;

    }
    
    //TODO
    /*
    public ArrayList<Object> filtrarPorMes(){
    	ContratoService service = new ContratoService();
    	ArrayList<Object> ret = new ArrayList<>();
    	ItemRepAlquileresPorMes item;
    	System.out.println(fDesde.toString().length() + "" + fHasta.toString().length());
    
    	if (fDesde.getValue() == null) {
    		showErrorNotification("Debes seleccionar una fecha");
   	   
    	}
    	
    	if (fDesde.getValue() != null && conCobrosPendientes==false) {
    		
    		for (ItemRepAlquileresACobrar item2 : service.getListadoAlquileresCobradosPorMes(fDesde.getValue())) {

    			    ret.add(item2);
    	
    		}
    		
    		item = service.itemParaAlquileresPorMesPagosCobrados(fDesde.getValue()); 
    		
    		ret.add(item);
    		return ret;
    	}
    	
    	else {
    		
    		for (ItemRepAlquileresACobrar item2 : service.getListadoTodosLosAlquileresDeUnMes(fDesde.getValue())) {
    			ret.add(item2);
    		}
    		
    		item = service.itemParaAlquileresPorMesPagosCobrados(fDesde.getValue()); 
    		
    		ret.add(item);
    		return ret;
    		
    	}


    } */

    public ReportesView() {
	super();
	buildLayout();
	configureComponents();
	daoContratoAlquiler = new DAOContratoAlquilerImpl();
	newReport.click();

    }

    public void buildLayout() {
	CssLayout filtering = new CssLayout();
	CssLayout filtering2 = new CssLayout();

	fDesde = new DateField();
	fDesde.setPlaceholder("Desde");
	fDesde.setParseErrorMessage("Formato de fecha no reconocido");

	fHasta = new DateField();
	fHasta.setPlaceholder("Hasta");
	fHasta.setParseErrorMessage("Formato de fecha no reconocido");

	tipoReporteCB.setSelectedItem(TipoReporte.Propietario);
	clearFilterTextBtn.setVisible(false);
	clearFilterTextBtn.setStyleName(ValoTheme.BUTTON_BORDERLESS);
	fDesde.setVisible(false);
	fHasta.setVisible(false);
	checkbox.setVisible(false);
	fDesde.setStyleName(ValoTheme.DATEFIELD_BORDERLESS);
	fHasta.setStyleName(ValoTheme.DATEFIELD_BORDERLESS);
	clearFilterTextBtn.addClickListener(new Button.ClickListener() {
	    @Override
	    public void buttonClick(Button.ClickEvent clickEvent) {
		fDesde.setValue(null);
		fHasta.setValue(null);
	    }
	});

	generarReporte();
	filtering.addComponents(fDesde, fHasta, clearFilterTextBtn, tipoReporteCB, newReport);
	filtering2.addComponents(checkbox);
	tipoReporteCB.setStyleName(ValoTheme.COMBOBOX_BORDERLESS);
	tipoReporteCB.addValueChangeListener(new HasValue.ValueChangeListener<TipoReporte>() {
	    @Override
	    public void valueChange(HasValue.ValueChangeEvent<TipoReporte> valueChangeEvent) {
		if (valueChangeEvent.getValue() == TipoReporte.AlquileresPorCobrar) {
		    clearFilterTextBtn.setVisible(true);
		    fDesde.setVisible(true);
		    fDesde.setPlaceholder("Desde");
		    fHasta.setVisible(true);
		    checkbox.setVisible(false);
		}
		
		if (valueChangeEvent.getValue() == TipoReporte.AlquileresPorMes) {
			checkbox.setVisible(true);
			clearFilterTextBtn.setVisible(false);
			fDesde.setPlaceholder("Fecha Mes");
		    fDesde.setVisible(true);
		    fHasta.setVisible(false);
		}
		
		
		else {
		    clearFilterTextBtn.setVisible(false);
		    fDesde.setPlaceholder("Desde");
		    fDesde.setVisible(false);
		    fHasta.setVisible(false);
		    checkbox.setVisible(false);
		}
	    }
	});
	
	checkbox.addValueChangeListener(event -> {
	    if (event.isUserOriginated()) {
	    	conCobrosPendientes=false;
		}
	    
	    else {
	    	 conCobrosPendientes=true;
	    }
	});
	
	// tipoReporteCB.setWidth("100%");
	filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
	filtering2.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

	buildToolbar("Reportes", filtering);
	buildToolbar("", filtering2);
	
	pdfComponent.setSizeFull();
	addComponent(pdfComponent);
	this.setExpandRatio(pdfComponent, 1);
	this.setSpacing(false);
	this.setMargin(false);
	this.setSizeFull();

    }

    private void configureComponents() {
	objects = new ArrayList<Object>();
	tipoReporteCB.setEmptySelectionAllowed(false);
	// tipoReporteCB.setSelectedItem(TipoReporte.Propietario);
	setComponentsVisible(true);
	newReport.setStyleName(ValoTheme.BUTTON_PRIMARY);

	newReport.addClickListener(e -> {

	    try {
		boolean success = generarReporte();
		pdfComponent.setPDF(reportName);
		// limpiamos la lista
		objects.clear();
	    } catch (Exception f) {
		f.printStackTrace();
		System.out.println(f);
		showErrorNotification("Error al generar el reporte");
	    }

	});

    }
    /*
     * private DateField filtroFDesde() { DateField fDesde = new DateField();
     * fDesde.setPlaceholder("Desde");
     * fDesde.setParseErrorMessage("Formato de fecha no reconocido");
     * 
     * return fDesde; }
     * 
     * private DateField filtroFHasta() { DateField fHasta = new DateField();
     * fHasta.setPlaceholder("Hasta");
     * fHasta.setParseErrorMessage("Formato de fecha no reconocido");
     * 
     * return fHasta; }
     */

    public boolean generarReporte() {
	TipoReporte tipoReporte = tipoReporteCB.getValue();
	System.out.println(tipoReporte);

	List<Object> objetos = getObjetos(tipoReporte);

	// Te trae el nombre del archivo en base a seleccion del combo
	File root = new File(File.separator + tipoReporte.getArchivoReporte());
	File root2 = new File(tipoReporte.getArchivoReporte());
	File webapp = new File(new Utils().resourcesPath() + tipoReporte.getArchivoReporte());

	try {
	    this.reporte = (JasperReport) JRLoader.loadObject(webapp);

	} catch (JRException e) {
	    try {
		this.reporte = (JasperReport) JRLoader.loadObject(root2);
	    } catch (Exception e1) {
		System.err.println("Error al cargar reporte");
		e1.printStackTrace();

	    }
	}

	try {
	    this.reporteLleno = JasperFillManager.fillReport(this.reporte, parametersMap,
		    new JRBeanCollectionDataSource(objetos, false));

	    return crearArchivo();
	} catch (Exception e) {
	    System.err.println("Error al cargar Reporte");
	    e.printStackTrace();
	    return false;
	}

    }

    private boolean crearArchivo() {
	reportName = Long.toString(new Date().getTime() / 1000) + ".pdf"; // Tiempo en segundos desde Epoch hasta ahora
									  // (no se repite)

	File dir = new File("Files");
	if (!dir.exists())
	    dir.mkdir();
	JRPdfExporter exporter = new JRPdfExporter();
	exporter.setExporterInput(new SimpleExporterInput(reporteLleno));
	exporter.setExporterOutput(new SimpleOutputStreamExporterOutput("Files" +
		File.separator + reportName));
	try {
	    exporter.exportReport();
	    return true;
	} catch (JRException e) {
	    System.err.println("Error al exportar a reporte");
	    e.printStackTrace();
	    return false;

	}

    }

    public void setComponentsVisible(boolean b) {
	newReport.setVisible(true);
    }

    public void showErrorNotification(String notification) {
	error = new Notification(
		notification);
	error.setDelayMsec(4000);
	error.setStyleName("bar error small");
	error.setPosition(Position.BOTTOM_CENTER);
	error.show(Page.getCurrent());
    }

    public boolean elRangoDeFechasElegidoEsValido() {
	if (fDesde.isEmpty() || fHasta.isEmpty() ||
		fDesde.getParseErrorMessage().equals("Formato de fecha no reconocido") ||
		fHasta.getParseErrorMessage().equals("Formato de fecha no reconocido") || (fHasta.getValue().isAfter(
			fDesde.getValue())) ||
		(fDesde.getValue().isBefore(fHasta.getValue())))
	    return false;
	return true;
    }

}
