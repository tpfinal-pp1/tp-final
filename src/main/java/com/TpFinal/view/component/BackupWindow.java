package com.TpFinal.view.component;

import com.TpFinal.data.conexion.ConexionHibernate;
import com.TpFinal.dto.notificacion.NotificadorJob;
import com.TpFinal.properties.Parametros;
import com.TpFinal.services.InmuebleService;
import com.TpFinal.services.Planificador;
import com.TpFinal.utils.Utils;
import com.TpFinal.utils.XZCompressor;
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

    private final ExportDBButton exportar = new ExportDBButton();
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
	getUI().getCurrent().setPollInterval(500);
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
	// UPLOAD
	DBUploadReceiver uR = new DBUploadReceiver();
	importar = new UploadButton(uR);
	importar.addSucceededListener(success -> {
	    String descomprimido = "";
	    try {
		System.out.println("FILENAME  " + uR.getFileName());
		descomprimido = XZCompressor.descomprimir(uR.getFileName(), "Files");
		System.out.println("descomprimido " + descomprimido);
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	    for (int i = 0; i < 2; i++) {
		System.out.println("ORIGINAL" + descomprimido);
		descomprimido = Utils.removeFileExtension(descomprimido);
		System.out.println("RECORTANDO EXTENSIONES DB " + descomprimido);
		descomprimido = Utils.removeFileExtension(descomprimido);
		System.out.println("RECORTANDO EXTENSIONES DB " + descomprimido);
	    }
	    Parametros.setProperty(Parametros.DB_NAME, descomprimido);
	    importar.setEnabled(false);
	    exportar.setEnabled(false);
	    reiniciarServiciosYSesion(true);

	});

	// DOWNLOAD
	exportar.focus();

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
				infoLabel.setValue("Espere " + pollInterval / 1000
					+ " segundos... Porfavor no cierre el navegador. De cerrarlo el servidor puede quedar inhabilitado por 1 hora");

				window.setClosable(false);
				shutdown.setEnabled(false);

				new Thread(() -> {
				    try {
					Thread.sleep(pollInterval);
				    } catch (InterruptedException e) {
					e.printStackTrace();
				    }
				    ComprimirDBYPrepararDownload();
				    infoLabel.setValue(
					    "Compactando la Base de datos...Por favor no cierre el navegador. De cerrarlo el servidor puede quedar inhabilitado por 1 hora\"");
				    importar.setEnabled(true);
				    exportar.setEnabled(true);
				    shutdown.setVisible(false);
				    reiniciar.setVisible(true);
				    infoLabel.setValue("Ahora puede Importar o Exportar la Base de datos,\n" +
					    " porfavor antes de cerrar el navegador seleccione Reiniciar!!");

				}).start();

			    }
			});
	    }
	});

	reiniciar.addClickListener(new Button.ClickListener() {
	    @Override
	    public void buttonClick(Button.ClickEvent clickEvent) {

		ConexionHibernate.leaveBackupMode();
		reiniciarServiciosYSesion(false);

	    }
	});
	reiniciar.setVisible(false);
	reiniciar.setIcon(VaadinIcons.START_COG);

	reiniciar.addClickListener(new Button.ClickListener() {
	    @Override
	    public void buttonClick(Button.ClickEvent clickEvent) {

		ConexionHibernate.leaveBackupMode();
		reiniciarServiciosYSesion(false);

	    }
	});
	reiniciar.setVisible(false);
	reiniciar.setIcon(VaadinIcons.START_COG);

	buttonsHLayout.addComponent(importar);
	buttonsHLayout.addComponent(shutdown);
	buttonsHLayout.addComponent(reiniciar);
	buttonsHLayout.addComponent(exportar);

    }

    private void reiniciarServiciosYSesion(boolean seImportoDB) {
	logger.debug("Actualizando Conexión");
	ConexionHibernate.refreshConnection();
	logger.debug("Apagando Planificador");
	Planificador.get().apagar();
	logger.debug("Abriendo Conexiones");
	ConexionHibernate.createSessionFactory();
	if (seImportoDB) {
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
	Planificador.get().setNotificacion(new NotificadorJob());
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

    public void ComprimirDBYPrepararDownload() {
	String dbFile = "";
	String dbPath = "";
	String dbCompressedFile = "";

	try {
	    dbFile = Parametros.getProperty(Parametros.DB_NAME);
	    dbPath = Parametros.getProperty(Parametros.DB_PATH);
	    if (!dbFile.contains(".mv.db"))
		dbFile = dbFile + ".mv.db";

	    dbCompressedFile = XZCompressor.comprimir(dbFile, dbPath);
	    exportar.setArchivoFromPath(dbPath + File.separator, dbCompressedFile);

	} catch (Exception e) {
	    e.printStackTrace();
	}
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
