package com.TpFinal.view.component;

import com.TpFinal.data.conexion.ConexionHibernate;
import com.TpFinal.properties.Parametros;
import com.TpFinal.services.InmuebleService;
import com.TpFinal.services.Planificador;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;

import com.vaadin.ui.themes.ValoTheme;
import org.apache.commons.io.FileExistsException;
import org.apache.log4j.Logger;

import java.io.File;

public class BackupWindow extends CustomComponent {
    /**
     *
     */
    private static final Logger logger = Logger.getLogger(BackupWindow.class);
    private static final long serialVersionUID = 1L;
    private final Label infoLabel = new Label("", ContentMode.HTML);
    private UploadButton importar = null;
    private boolean seImportoBD = false;
    private final DownloadButton exportar = new DownloadButton();
    private final Window window = new Window();
    private Button shutdown = new Button("Detener", VaadinIcons.STOP_COG);

    Button reiniciar = new Button("Reiniciar", VaadinIcons.START_COG);
    private int pollInterval = 0;
    private static VaadinSession vaadinSession;

    public static VaadinSession getVaadinSession() {
	return vaadinSession;
    }

    public BackupWindow() {
	vaadinSession = VaadinSession.getCurrent();
	pollInterval = getUI().getCurrent().getPollInterval();
	infoLabel.setSizeFull();
	infoLabel.setValue(
		"Antes de realizar cualquier operacion debe Detener todas las conexiones con el servidor, este proceso tomara "
			+ pollInterval / 1000 + " segundos");
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
	    logger.debug("Seteando importacion de db a true");
	    seImportoBD = true;
	    Parametros.setProperty(Parametros.DB_NAME, uR.getFileName());
	    importar.setEnabled(false);
	    exportar.setEnabled(false);

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
	    }
	});
	importar.setEnabled(false);
	exportar.setEnabled(false);
	shutdown.addClickListener(new Button.ClickListener() {
	    @Override
	    public void buttonClick(Button.ClickEvent clickEvent) {
		new DialogConfirmacion("Modo Mantenimiento", VaadinIcons.DATABASE,
			"Esta seguro que quiere entrar en este modo?\n" +
				" Todos los usuarios perderan su conexión\n" +
				".Asegurese de que todos los usuarios hayan guardado el contenido", "",
			new Button.ClickListener() {
			    @Override
			    public void buttonClick(Button.ClickEvent clickEvent) {

				ConexionHibernate.enterBackupMode();
				apagarServicios();
				for (int i = 0; i < 10; i++) {
				    showWaitNotification(); // Para que no la pueda cerrar

				}

				window.setClosable(false);
				shutdown.setEnabled(false);

				new Thread(() -> {
				    try {
					Thread.sleep(pollInterval);
				    } catch (InterruptedException e) {
					e.printStackTrace();
				    }
				    infoLabel.setValue("Ahora puede Importar o Exportar la Base de datos,\n" +
					    " porfavor antes de cerrar el navegador seleccione Reiniciar!!");
				    importar.setEnabled(true);
				    exportar.setEnabled(true);
				    shutdown.setVisible(false);
				    reiniciar.setVisible(true);

				}).start();

			    }
			});
	    }
	});

	reiniciar.addClickListener(new Button.ClickListener() {
	    @Override
	    public void buttonClick(Button.ClickEvent clickEvent) {

		ConexionHibernate.leaveBackupMode();
		reiniciarServiciosYSesion();

	    }
	});
	reiniciar.setVisible(false);
	reiniciar.setIcon(VaadinIcons.START_COG);

	reiniciar.addClickListener(new Button.ClickListener() {
	    @Override
	    public void buttonClick(Button.ClickEvent clickEvent) {

		ConexionHibernate.leaveBackupMode();
		reiniciarServiciosYSesion();

	    }
	});
	reiniciar.setVisible(false);
	reiniciar.setIcon(VaadinIcons.START_COG);

	buttonsHLayout.addComponent(importar);
	buttonsHLayout.addComponent(shutdown);
	buttonsHLayout.addComponent(reiniciar);
	buttonsHLayout.addComponent(exportar);

    }

    public void showWaitNotification() {
	Notification success = new Notification(
		"Espere " + pollInterval / 1000 + " segundos... Porfavor no cierre el navegador");
	success.setDelayMsec(pollInterval);
	success.setStyleName("bar success small");
	success.setPosition(Position.MIDDLE_CENTER);
	success.show(Page.getCurrent());
    }

    private void reiniciarServiciosYSesion() {
	logger.debug("Actualizando Conexión");
	ConexionHibernate.refreshConnection();
	logger.debug("Apagando Planificador");
	Planificador.get().apagar();
	logger.debug("Abriendo Conexiones");
	ConexionHibernate.createSessionFactory();
	if (seImportoBD) {
	    seImportoBD = false;
	    ConexionHibernate.leaveBackupMode();
	    logger.debug("Cargando Imagenes en File System");
	    InmuebleService.cargarImagenesAFileSystem();
	    ConexionHibernate.enterBackupMode();
	}
	logger.debug("Encendiendo Planificador");
	Planificador.get().encender();
	logger.debug("Creando nueva SessionFactory");
	ConexionHibernate.createSessionFactory();
	logger.debug("Encendiendo Planificador");
	Planificador.get().encender();
	logger.debug("Saliendo de modo BackUp..");
	ConexionHibernate.leaveBackupMode();
	logger.debug("Cerrando Session");
	VaadinSession.getCurrent().close();
	Page.getCurrent().reload();
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
