package com.TpFinal.view.component;

import com.TpFinal.data.conexion.ConexionHibernate;
import com.TpFinal.properties.Parametros;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import org.apache.commons.io.FileExistsException;

import java.io.File;
import java.time.Instant;

public class BackupWindow extends CustomComponent {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private final Label infoLabel = new Label("", ContentMode.HTML);
    private  UploadButton importar=null;
    private final DownloadButton exportar = new DownloadButton();
    private final Window window = new Window();

    public BackupWindow() {
		ConexionHibernate.enterBackupMode();
    	getUI().getCurrent().setPollInterval(999999999);
	infoLabel.setSizeFull();
	infoLabel.setValue("Al abrir esta ventana Todas las Conexiones estan siendo congeladas \n " +
			"hasta no terminar las operaciones");
	//importar.addStyleName(ValoTheme.BUTTON_DANGER);
	//exportar.addStyleName(ValoTheme.BUTTON_PRIMARY);
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
	window.setCaption("Backup/Restore");
	window.setIcon(VaadinIcons.DATABASE);
	String dbFile="";
		try {
			dbFile=Parametros.getProperty("db_name");
		} catch (FileExistsException e) {
			e.printStackTrace();
		}

	UploadReceiver uR=new UploadReceiver();
	uR.setFilePath(System.getProperty("user.home"));
	importar=new UploadButton(uR);
	importar.addSucceededListener(new Upload.SucceededListener() {
		@Override
		public void uploadSucceeded(Upload.SucceededEvent succeededEvent) {
			Parametros.setProperty("db_name",uR.getFileName());
		}
	});
	exportar.focus();
	exportar.setArchivoFromPath(System.getProperty("user.home"),dbFile);
	exportar.addClickListener(new Button.ClickListener() {
		@Override
		public void buttonClick(Button.ClickEvent clickEvent) {

			window.close();
		}
	});



	// ui
	popupVLayout.addComponent(infoLabel);
	popupVLayout.addComponent(buttonsHLayout);
	popupVLayout.setComponentAlignment(buttonsHLayout, Alignment.TOP_CENTER);
	UI.getCurrent().addWindow(window);
	window.center();


	window.addCloseListener(new Window.CloseListener() {
		@Override
		public void windowClose(Window.CloseEvent closeEvent) {
			getUI().getCurrent().setPollInterval(10000);
			ConexionHibernate.leaveBackupMode();
		}
	});
		buttonsHLayout.addComponent(importar);
		buttonsHLayout.addComponent(exportar);
    }

    public void setInfo(String info) {
	infoLabel.setValue(info);
    }

    public void setWindowWidth(String width) {
	window.setWidth(width);
    }



    public void close() {
	window.close();
    }
}
