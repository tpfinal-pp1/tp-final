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
    private UploadButton importar = null;
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
			dbFile=Parametros.getProperty(Parametros.DB_NAME);
		} catch (FileExistsException e) {
			e.printStackTrace();
		}

	UploadDbReceiver uR=new UploadDbReceiver();
	importar=new UploadButton(uR);
	importar.addSucceededListener(success -> {
			Parametros.setProperty(Parametros.DB_NAME,uR.getFileName());
		}
	);
	exportar.focus();
	try {
	    exportar.setArchivoFromPath(Parametros.getProperty(Parametros.DB_PATH) + File.separator,dbFile);
	} catch (IllegalArgumentException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (FileExistsException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	//TODO 
	exportar.addClickListener(click ->{});
//=======
//	exportar.setArchivoFromPath(System.getProperty("user.home"),dbFile);
//>>>>>>> branch 'refactorImagenes' of https://github.com/tpfinal-pp1/tp-final.git




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
