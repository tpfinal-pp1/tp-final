package com.TpFinal.view.reportes;

import com.TpFinal.data.dto.persona.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;



import com.TpFinal.data.dto.contrato.ContratoDuracion;
import com.TpFinal.data.dto.publicacion.Rol;
import com.TpFinal.services.ContratoDuracionService;
import com.TpFinal.services.DashboardEvent;
import com.TpFinal.services.PersonaService;
import com.TpFinal.view.component.DefaultLayout;
import com.google.common.eventbus.Subscribe;
import com.itextpdf.text.pdf.PdfWriter;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Widgetset;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.List;

/* User Interface written in Java.
 *
 * Define the user interface shown on the Vaadin generated web page by extending the UI class.
 * By default, a new UI instance is automatically created when the page is loaded. To reuse
 * the same instance, add @PreserveOnRefresh.
 */

@Title("Reportes")
@Theme("valo")
@Widgetset("com.vaadin.v7.Vaadin7WidgetSet")
public class ReportesViewMain extends DefaultLayout implements View {
Button newReport = new Button("Reporte");
    
    private JasperReport reporte;
    private JasperPrint reporteLleno;
    Map<String, Object> parametersMap = new HashMap<String, Object>();
    PersonaService servicePersona = new PersonaService();
    List<Persona> propietarios = new ArrayList<Persona>();

    HorizontalLayout mainLayout;
    private boolean isonMobile=false;
    
    
    public ReportesViewMain() {
    	super();
    	  buildLayout();
          configureComponents();
    	
    		
    }    
    
    public void buildLayout() {
    	CssLayout filtering = new CssLayout();
        HorizontalLayout hl= new HorizontalLayout();
        filtering.addComponents(newReport);
        filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        hl.addComponent(filtering);
        

       buildToolbar("Reportes",hl);
       // mainLayout = new HorizontalLayout(DuracionContratosForm);
       // mainLayout.setSizeFull();
       // addComponent(mainLayout);
       // this.setExpandRatio(mainLayout, 1);
    }
    
    
    private void configureComponents() {
    	
    	newReport.addClickListener(e -> {
    		
    		boolean success=false;
    		
        	try {
        		
        		propietarios = servicePersona.findForRole(Rol.Propietario.toString());
    			String reportName = "myreport";
    		
    			this.reporte = (JasperReport) 	JRLoader.loadObjectFromFile("ReportesJasper\\reportePropietarios.jasper");
    			this.reporteLleno = JasperFillManager.fillReport(this.reporte, parametersMap, 
    					new JRBeanCollectionDataSource(propietarios));
    			
    			JRPdfExporter exporter = new JRPdfExporter();
    			exporter.setExporterInput(new SimpleExporterInput(reporteLleno));
    			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(reportName+".pdf"));
    			//SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
    			//configuration.setPermissions(PdfWriter.AllowCopy | PdfWriter.AllowPrinting);
    			//exporter.setConfiguration(configuration);
    			exporter.exportReport();
    			success=true;
    			
    			
        	}

    			catch (JRException ex){
    				ex.printStackTrace();
    			}
        	
        	if(success) {
               Notification.show("Reporte Generado con Exito");
        			}
        	 });
    	
    	
    	 setComponentsVisible(true);         
         newReport.setStyleName(ValoTheme.BUTTON_PRIMARY);
    }
    
    public void setComponentsVisible(boolean b){
    	newReport.setVisible(true);
    }
    
    
}
