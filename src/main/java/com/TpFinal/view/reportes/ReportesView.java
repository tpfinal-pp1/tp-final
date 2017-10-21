package com.TpFinal.view.reportes;


import java.io.File;

import java.util.*;

import com.TpFinal.utils.Utils;
import com.TpFinal.view.component.PDFComponent;
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
import com.TpFinal.dto.publicacion.Rol;
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

	private JasperReport reporte;
	private JasperPrint reporteLleno;
	Map<String, Object> parametersMap = new HashMap<String, Object>();
	PDFComponent pdfComponent=new PDFComponent();
	ComboBox<TipoReporte> tipoReporteCB= new ComboBox<TipoReporte>(
			null,TipoReporte.toList());
	HorizontalLayout mainLayout;
	String reportName="";
	Button newReport = new Button("Generar");
	Notification error ;
	public enum TipoReporte {
		Propietario("ReportePropietarios.jasper"),AlquileresPorCobrar("ReporteAlquileresPorCobrar.jasper");

		private final String archivoReporte;

		 TipoReporte(String archivoReporte) {
			this.archivoReporte=archivoReporte;
		}

		@Override
		public String toString(){
		 	switch (this){
				case Propietario:return "Propietario";
				case AlquileresPorCobrar:return  "Alquileres a Cobrar  ";
				default:return super.toString();

			}
		}


		public List<Object> getObjetos(){
		 	List<Object> objects=null;
		 	switch (this){
				case Propietario:
					PersonaService servicePersona = new PersonaService();
					objects=new ArrayList<Object>(servicePersona.findForRole(
							Rol.Propietario.toString()));break;
					
				case AlquileresPorCobrar:
					List<Object> objects2 = new ArrayList<Object>();
					objects=new ArrayList<Object>();
					daoContratoAlquiler.readAllActives().forEach(e -> {
						e.getCobros().forEach(z -> {
							if (z.getEstadoCobro() == EstadoCobro.NOCOBRADO) {
							objects2.add(new ItemRepAlquileresACobrar(e.getInquilinoContrato(), z, e.getMoneda()));
							}
							
							
						});
						
						
						
					});
					
					objects = objects2;break;
					

			}
		 	return  objects;
		}

		public static List<TipoReporte> toList() {
			TipoReporte[] clases = TipoReporte.values();
			List<TipoReporte> ret = new ArrayList<>();
			for (TipoReporte c : clases) {
				ret.add(c);
			}
			return ret;
		}
		public String getArchivoReporte(){
			return this.archivoReporte;
		}


	}





    public ReportesView() {
    	super();
    	  buildLayout();
          configureComponents();
          daoContratoAlquiler = new DAOContratoAlquilerImpl();
          newReport.click();

    }    
    
    public void buildLayout() {
    	CssLayout filtering = new CssLayout();

        filtering.addComponents(tipoReporteCB,newReport);
        tipoReporteCB.setStyleName(ValoTheme.COMBOBOX_BORDERLESS);
       // tipoReporteCB.setWidth("100%");
        filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);


       buildToolbar("Reportes",filtering);
       pdfComponent.setSizeFull();
		addComponent(pdfComponent);
       this.setExpandRatio(pdfComponent,1);
        this.setSpacing(false);
        this.setMargin(false);
        this.setSizeFull();

    }
    




    private void configureComponents() {
    	tipoReporteCB.setEmptySelectionAllowed(false);
    	tipoReporteCB.setSelectedItem(TipoReporte.Propietario);
		setComponentsVisible(true);
		newReport.setStyleName(ValoTheme.BUTTON_PRIMARY);

    	newReport.addClickListener(e -> {

			try {
				boolean success = generarReporte();
				pdfComponent.setPDF(reportName);
			}
			catch (Exception f){
				showErrorNotification("Error al generar el reporte");}



		});


    }




	public  boolean generarReporte(){
    	TipoReporte tipoReporte=tipoReporteCB.getValue();
    	String ubicacionReporte=new Utils().resourcesPath()+ tipoReporte.getArchivoReporte();
    	List<Object> objetos=tipoReporte.getObjetos();

		//Te trae el nombre del archivo en base a seleccion del combo
		File root=new File(File.separator+tipoReporte.getArchivoReporte());
		File root2=new File(tipoReporte.getArchivoReporte());
		File webapp=new File(new Utils().resourcesPath()+tipoReporte.getArchivoReporte());

		try {
			this.reporte = (JasperReport)JRLoader.
					loadObject(webapp);

		} catch (JRException e) {
			try {
				this.reporte = (JasperReport)JRLoader.
						loadObject(root2);
			} catch (Exception e1) {

			}
		}



		try {
			this.reporteLleno = JasperFillManager.fillReport(this.reporte, parametersMap,
					new JRBeanCollectionDataSource(objetos));
			return crearArchivo();
		} catch (Exception e) {

			return false;
		}
	}



	private boolean crearArchivo(){
		if(reportName.equals(""))
			reportName = Long.toString(new Date().getTime()/1000)+".pdf"; //Tiempo en segundos desde Epoch hasta ahora (no se repite)

		File dir=new File("Files");
		if(!dir.exists())
			dir.mkdir();
		JRPdfExporter exporter = new JRPdfExporter();
		exporter.setExporterInput(new SimpleExporterInput(reporteLleno));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput("Files"+
				File.separator+ reportName));
		try {
			exporter.exportReport();
			return true;
		} catch (JRException e) {

			return false;

		}

	}
    public void setComponentsVisible(boolean b){
    	newReport.setVisible(true);
    }
	public void showErrorNotification(String notification) {
		error= new Notification(
				notification);
		error.setDelayMsec(4000);
		error.setStyleName("bar error small");
		error.setPosition(Position.BOTTOM_CENTER);
		error.show(Page.getCurrent());
	}



}
