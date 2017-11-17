package com.TpFinal.view.component;

import com.TpFinal.DashboardServlet;
import com.TpFinal.data.conexion.ConexionHibernate;
import com.TpFinal.properties.Parametros;
import com.TpFinal.services.Planificador;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import org.apache.commons.io.FileExistsException;
import org.apache.log4j.Logger;

import java.io.File;
import java.time.Instant;

import javax.servlet.ServletException;

public class BackupWindow extends CustomComponent {
    /**
     *
     */
    private static final Logger logger = Logger.getLogger(BackupWindow.class);
    private static final long serialVersionUID = 1L;
    private final Label infoLabel = new Label("", ContentMode.HTML);
    private UploadButton importar = null;

    private final DownloadButton exportar = new DownloadButton();
    private final Window window = new Window();
    private Button shutdown = new Button("Apagar", VaadinIcons.POWER_OFF);

    public BackupWindow() {
	ConexionHibernate.enterBackupMode();
	apagarServicios();
	getUI().getCurrent().setPollInterval(999999999);
	infoLabel.setSizeFull();
	infoLabel.setValue("Al abrir esta ventana Todas las Conexiones estan siendo congeladas \n " +
		"hasta no terminar las operaciones");
	// importar.addStyleName(ValoTheme.BUTTON_DANGER);
	// exportar.addStyleName(ValoTheme.BUTTON_PRIMARY);
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
	window.setDraggable(false);
	String dbFile = "";
	try {
	    dbFile = Parametros.getProperty(Parametros.DB_NAME) + ".mv.db";
	} catch (FileExistsException e) {
	    e.printStackTrace();
	}

	UploadReceiver uR = new UploadReceiver();
	importar = new UploadButton(uR);
	importar.addSucceededListener(success -> {
	    Parametros.setProperty(Parametros.DB_NAME, uR.getFileName());
	    logger.debug("Actualizando Conexión");
	    ConexionHibernate.refreshConnection();
	    logger.debug("Apagando Planificador");
	    Planificador.get().apagar();
	    logger.debug("Creando nueva SessionFactory");
	    ConexionHibernate.createSessionFactory();
	    logger.debug("Encendiendo Planificador");
	    Planificador.get().encender();
	    logger.debug("Saliendo de modo BackUp..");
	    ConexionHibernate.leaveBackupMode();
	    logger.debug("Cerrando Session");
	    getUI().getCurrent().getSession().close();
	});
	exportar.focus();
	try {
	    exportar.setArchivoFromPath(Parametros.getProperty(Parametros.DB_PATH) + File.separator, dbFile);
	} catch (IllegalArgumentException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (FileExistsException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	// TODO
	exportar.addClickListener(click -> {
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
		reiniciarServiciosYSesion();
	    }
	});

	buttonsHLayout.addComponent(importar);
	buttonsHLayout.addComponent(exportar);

    }

    private void reiniciarServiciosYSesion() {
	logger.debug("Abriendo Conexiones");
	ConexionHibernate.createSessionFactory();
	logger.debug("Encendiendo Planificador");
	Planificador.get().encender();
	logger.debug("Creando nueva SessionFactory");
	ConexionHibernate.createSessionFactory();
	logger.debug("Encendiendo Planificador");
	Planificador.get().encender();
	logger.debug("Saliendo de modo BackUp..");
	ConexionHibernate.leaveBackupMode();
	logger.debug("Cerrando Session");
	getUI().getCurrent().getSession().close();
    }

    private void apagarServicios() {
	logger.debug("Cerrando conexiones");
	ConexionHibernate.refreshConnection();
	ConexionHibernate.close();
	logger.debug("Apagando Planificador..");
	Planificador.get().apagar();

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
