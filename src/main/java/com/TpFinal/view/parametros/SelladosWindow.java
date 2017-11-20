package com.TpFinal.view.parametros;

import com.TpFinal.data.conexion.ConexionHibernate;
import com.TpFinal.dto.Provincia;
import com.TpFinal.properties.Parametros;
import com.TpFinal.services.InmuebleService;
import com.TpFinal.services.Planificador;
import com.TpFinal.services.ProvinciaService;
import com.TpFinal.view.component.DialogConfirmacion;
import com.TpFinal.view.component.DownloadButton;
import com.TpFinal.view.component.UploadButton;
import com.TpFinal.view.component.UploadReceiver;
import com.vaadin.data.HasValue;
import com.vaadin.event.selection.SingleSelectionEvent;
import com.vaadin.event.selection.SingleSelectionListener;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ErrorMessage;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.apache.commons.io.FileExistsException;
import org.apache.log4j.Logger;
import org.vaadin.maddon.layouts.MMarginInfo;

import java.io.File;
import java.math.BigDecimal;

public class SelladosWindow extends CustomComponent {
    /**
     *
     */

    private static final long serialVersionUID = 1L;

    private final Window window = new Window();
	private TextField montoSellado = new TextField();
	private ComboBox<Provincia> provincias= new ComboBox<>();
	private ProvinciaService provinciaService = new ProvinciaService();



    public SelladosWindow() {
		provincias.setItems(provinciaService.getProvincias());
	final VerticalLayout popupVLayout = new VerticalLayout();
	popupVLayout.setSpacing(true);
	popupVLayout.setMargin(true);
	HorizontalLayout buttonsHLayout = new HorizontalLayout();
	buttonsHLayout.setSpacing(true);

	window.setHeightUndefined();
	window.setModal(true);
	window.center();
	window.setResizable(false);
	window.setContent(popupVLayout);
	window.setCaption("Sellados");
	window.setIcon(VaadinIcons.DATABASE);
	window.setDraggable(false);

	UI.getCurrent().addWindow(window);
	window.center();
	provincias.addSelectionListener(new SingleSelectionListener<Provincia>() {
		@Override
		public void selectionChange(SingleSelectionEvent<Provincia> singleSelectionEvent) {
			if(singleSelectionEvent.getValue()!=null)
				montoSellado.setValue(provinciaService.getSelladoFromProvincia(singleSelectionEvent.getValue()).toString());
			else{
				montoSellado.setValue("N/A");
			}
		}
	});
	montoSellado.addValueChangeListener(new HasValue.ValueChangeListener<String>() {
		@Override
		public void valueChange(HasValue.ValueChangeEvent<String> valueChangeEvent) {
			if(valueChangeEvent.getValue()!=null){
				if(!valueChangeEvent.getValue().equals("N/A")){
					if(provincias.getValue()!=null){
						BigDecimal bigDecimal=null;
						try{
							bigDecimal=new BigDecimal(valueChangeEvent.getValue());
						}
						catch (Exception e){
							montoSellado.setComponentError(new ErrorMessage() {
								@Override
						public ErrorLevel getErrorLevel() {
							return ErrorLevel.ERROR;
						}

						@Override
						public String getFormattedHtmlMessage() {
							return "Porfavor, ingrese un número como monto sellado";
						}
							});


						}
						if(bigDecimal!=null){

							provinciaService.
									setSelladoToProvincia(provincias.getValue(),bigDecimal);
							montoSellado.setComponentError(null);}
					}
				}
			}
		}
	});
		HorizontalLayout HL=new HorizontalLayout(provincias,montoSellado);
		HL.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		popupVLayout.setMargin(new MarginInfo(false,true,true,true));
		popupVLayout.setSpacing(false);
		HL.setCaption("Sellado por Provincia");
		popupVLayout.addComponent(HL);
		montoSellado.setValueChangeMode(ValueChangeMode.LAZY);

	window.addCloseListener(new Window.CloseListener() {
	    @Override
	    public void windowClose(Window.CloseEvent closeEvent) {
	    }
	});

	provincias.setSelectedItem(provinciaService.getProvinciaFromString("Buenos Aires"));


    }

    public void setWindowWidth(String width) {
	window.setWidth(width);
    }

    public void close() {
	window.close();
    }
}
