package com.TpFinal.view.reportes;

import java.io.File;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

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

import com.TpFinal.dto.persona.Rol;
import com.TpFinal.services.ContratoService;
import com.TpFinal.services.PersonaService;
import com.TpFinal.view.component.DefaultLayout;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;

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
public class ReportesView extends DefaultLayout implements View {

    private static final Logger logger = Logger.getLogger(ReportesView.class);

    private JasperReport reporte;
    private JasperPrint reporteLleno;
    Map<String, Object> parametersMap = new HashMap<String, Object>();
    private Button clearFilterTextBtn = new Button(VaadinIcons.CLOSE);
    PDFComponent pdfComponent = new PDFComponent();
    ComboBox<TipoReporte> tipoReporteCB = new ComboBox<TipoReporte>(
	    null, TipoReporte.toList());
    CheckBox checkboxIncluirPendientes;
    String reportName = "";
    Button newReport = new Button("Generar");
    Notification error;

    DateField fDesdeDatePicker = null;
    DateField fHastaDatePicker = null;

    DateField fDesde2 = null;

    List<Object> objects = null;
    boolean incluirCobrosPendientes;
    private ContratoService contratoService = new ContratoService();

    public List<Object> getObjetos(TipoReporte tipo) {
	List<Object> objects = new ArrayList<>();

	if (logger.isDebugEnabled()) {
	    logger.debug("===========================================");
	    logger.debug("Cargando datos para reporte de tipo: " + tipo);
	    logger.debug("===========================================");
	}
	switch (tipo) {
	case Propietario: {
	    PersonaService servicePersona = new PersonaService();
	    objects.addAll(servicePersona.findForRole(
		    Rol.Propietario.toString()));
	    break;
	}

	case AlquileresPorCobrar: {
	    LocalDate fechaDesde = fDesdeDatePicker.getValue();
	    LocalDate fechaHasta = fHastaDatePicker.getValue();

	    if (logger.isDebugEnabled()) {
		logger.debug("==========================");
		logger.debug("fechaDesde: " + fechaDesde);
		logger.debug("fechaHasta: " + fechaHasta);
		logger.debug("==========================");
	    }

	    // Por defecto, si los campos estan vacios el reporte es hasta el mes actual.
	    if (fechaDesde == null && fechaHasta == null) {
		// Para obtener el ultimo dia del mes.
		fechaHasta = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
	    }
	    objects = contratoService.getListadoAlquileresACobrar(fechaDesde, fechaHasta);
	    break;
	}

	case AlquileresPorMes: {
	    LocalDate fechaDesde = fDesde2.getValue();
	    LocalDate fechaHasta;

	    // Por defecto traer cobros del mes actual
	    if (fechaDesde == null) {
		fechaDesde = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
		fechaHasta = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
	    } else {
		fechaDesde = fechaDesde.with(TemporalAdjusters.firstDayOfMonth());
		fechaHasta = fechaDesde.with(TemporalAdjusters.lastDayOfMonth());
	    }

	    if (logger.isDebugEnabled()) {
		logger.debug("==========================");
		logger.debug("fechaDesde: " + fechaDesde);
		logger.debug("fechaHasta: " + fechaHasta);
		logger.debug("Incluir Cobros Pendientes: "+ incluirCobrosPendientes);
		logger.debug("==========================");
	    }

	    objects = contratoService.getListadoAlquileresDelMes(fechaDesde, fechaHasta,incluirCobrosPendientes);
	    break;
	}

	}

	return objects;

    }

    public ReportesView() {
	super();
	buildLayout();
	configureComponents();
	newReport.click();

    }

    public void buildLayout() {
	CssLayout filtering = new CssLayout();

	incluirCobrosPendientes = false;
	checkboxIncluirPendientes = new CheckBox("Incluir Cobros Pendientes", false);

	fDesdeDatePicker = new DateField();
	fDesdeDatePicker.setPlaceholder("Desde");
	fDesdeDatePicker.setParseErrorMessage("Formato de fecha no reconocido");

	fHastaDatePicker = new DateField();
	fHastaDatePicker.setPlaceholder("Hasta");
	fHastaDatePicker.setParseErrorMessage("Formato de fecha no reconocido");

	fDesde2 = new DateField();
	fDesde2.setPlaceholder("Fecha Mes");
	fDesde2.setParseErrorMessage("Formato de fecha no reconocido");

	tipoReporteCB.setSelectedItem(TipoReporte.Propietario);
	clearFilterTextBtn.setVisible(false);
	clearFilterTextBtn.setStyleName(ValoTheme.BUTTON_BORDERLESS);
	fDesdeDatePicker.setVisible(false);
	fHastaDatePicker.setVisible(false);
	fDesde2.setVisible(false);
	checkboxIncluirPendientes.setVisible(false);
	fDesdeDatePicker.setStyleName(ValoTheme.DATEFIELD_BORDERLESS);
	fHastaDatePicker.setStyleName(ValoTheme.DATEFIELD_BORDERLESS);
	fDesde2.setStyleName(ValoTheme.DATEFIELD_BORDERLESS);
	clearFilterTextBtn.addClickListener(new Button.ClickListener() {
	    @Override
	    public void buttonClick(Button.ClickEvent clickEvent) {
		fDesdeDatePicker.setValue(null);
		fHastaDatePicker.setValue(null);
	    }
	});

	generarReporte();
	filtering.addComponents(fDesdeDatePicker, fHastaDatePicker, clearFilterTextBtn, fDesde2, checkboxIncluirPendientes,
		tipoReporteCB,
		newReport);
	tipoReporteCB.setStyleName(ValoTheme.COMBOBOX_BORDERLESS);
	tipoReporteCB.addValueChangeListener(new HasValue.ValueChangeListener<TipoReporte>() {
	    @Override
	    public void valueChange(HasValue.ValueChangeEvent<TipoReporte> valueChangeEvent) {

		if (valueChangeEvent.getValue() == TipoReporte.Propietario) {
		    clearFilterTextBtn.setVisible(false);
		    fDesdeDatePicker.setVisible(false);
		    fHastaDatePicker.setVisible(false);
		    fDesde2.setVisible(false);
		    checkboxIncluirPendientes.setVisible(false);

		}

		if (valueChangeEvent.getValue() == TipoReporte.AlquileresPorMes) {
		    checkboxIncluirPendientes.setVisible(true);
		    fDesde2.setVisible(true);
		    clearFilterTextBtn.setVisible(false);
		    fDesdeDatePicker.setVisible(false);
		    fHastaDatePicker.setVisible(false);
		}

		if (valueChangeEvent.getValue() == TipoReporte.AlquileresPorCobrar) {
		    clearFilterTextBtn.setVisible(true);
		    fDesdeDatePicker.setVisible(true);
		    fHastaDatePicker.setVisible(true);
		    fDesde2.setVisible(false);
		    checkboxIncluirPendientes.setVisible(false);
		}

	    }
	});

	checkboxIncluirPendientes.addValueChangeListener(event -> incluirCobrosPendientes =event.getValue());

	// tipoReporteCB.setWidth("100%");
	filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

	buildToolbar("Reportes", filtering);

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
		if (success) {
		    pdfComponent.setPDF(reportName);
		    showSuccessNotification("Reporte Generado");
		}
		// limpiamos la lista
		objects.clear();
	    } catch (Exception f) {
		f.printStackTrace();
		System.out.println(f);
		showErrorNotification("Error al generar el reporte");
	    }

	});

    }

    public boolean generarReporte() {
	TipoReporte tipoReporte = tipoReporteCB.getValue();

	if (logger.isDebugEnabled()) {
	    logger.debug("===========================================");
	    logger.debug("Generando Reporte de tipo: " + tipoReporte);
	    logger.debug("===========================================");
	}

	List<Object> objetos = getObjetos(tipoReporte);

	// Te trae el nombre del archivo que contiene al generador de reportes
	// en base a seleccion del combo
	File root2 = new File(tipoReporte.getGeneradorDeReporte());
	File pathAGeneradorEnWebapp = new File(new Utils().resourcesPath() + tipoReporte.getGeneradorDeReporte());

	try {

	    if (logger.isDebugEnabled()) {
		logger.debug("===========================================");
		logger.debug("Cargando generador de reportes: " + pathAGeneradorEnWebapp);
		logger.debug("===========================================");
	    }

	    this.reporte = (JasperReport) JRLoader.loadObject(pathAGeneradorEnWebapp);

	} catch (JRException e) {
	    try {
		this.reporte = (JasperReport) JRLoader.loadObject(root2);
	    } catch (Exception e1) {
		logger.error("Error al cargar el generador de reportes");
		e1.printStackTrace();
	    }
	}

	if (objetos.size() != 0) {
	    try {
		this.reporteLleno = JasperFillManager.fillReport(this.reporte, parametersMap,
			new JRBeanCollectionDataSource(objetos, false));
		return crearArchivo(tipoReporte);
	    } catch (Exception e) {
		logger.error("==========ERROR============");
		logger.error("Error al generar el Reporte");
		logger.error("===========================");
		e.printStackTrace();
		return false;
	    }
	} else {
	    showErrorNotification("No se encontraron datos para generar el reporte");
	    return false;
	}

    }

    private boolean crearArchivo(TipoReporte tipoReporte) {
	// Tiempo en segundos desde Epoch hasta ahora (no se repite)
	reportName = tipoReporte.getPrefijoArchivo() + Long.toString(new Date().getTime() / 1000) + ".pdf";

	File dir = new File("Files");
	if (!dir.exists())
	    dir.mkdir();
	JRPdfExporter exporter = new JRPdfExporter();
	exporter.setExporterInput(new SimpleExporterInput(reporteLleno));
	exporter.setExporterOutput(new SimpleOutputStreamExporterOutput("Files" +
		File.separator + reportName));
	try {
	    if (logger.isDebugEnabled()) {
		logger.debug("===========================================");
		logger.debug("Creando Archivo PDF: " + reportName);
		logger.debug("===========================================");
	    }
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

    public void showSuccessNotification(String notification) {
	Notification success = new Notification(
		notification);
	success.setDelayMsec(2000);
	success.setStyleName("bar success small");
	success.setPosition(Position.BOTTOM_CENTER);
	success.show(Page.getCurrent());
    }

    public boolean elRangoDeFechasElegidoEsValido() {
	if (fDesdeDatePicker.isEmpty() || fHastaDatePicker.isEmpty() ||
		fDesdeDatePicker.getParseErrorMessage().equals("Formato de fecha no reconocido") ||
		fHastaDatePicker.getParseErrorMessage().equals("Formato de fecha no reconocido") || (fHastaDatePicker
			.getValue()
			.isAfter(
				fDesdeDatePicker.getValue())) ||
		(fDesdeDatePicker.getValue().isBefore(fHastaDatePicker.getValue())))
	    return false;
	return true;
    }

}
