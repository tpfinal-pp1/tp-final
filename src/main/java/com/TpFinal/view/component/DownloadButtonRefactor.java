package com.TpFinal.view.component;

import com.TpFinal.dto.contrato.Archivo;
import com.TpFinal.dto.contrato.Contrato;
import com.TpFinal.utils.Utils;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.Page;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.shared.Position;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.themes.ValoTheme;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Blob;

import org.apache.log4j.Logger;

public class DownloadButtonRefactor extends Button {
    private static Logger logger = Logger.getLogger(DownloadButtonRefactor.class);
    StreamResource sr;
    FileDownloader fileDownloader;

    /**
     * El boton se crea por defecto deshabilitado hasta tanto no se le setee un
     * archivo para descargar.
     */
    public DownloadButtonRefactor() {
	super();
	this.setIcon(VaadinIcons.DOWNLOAD);
	this.setStyleName(ValoTheme.BUTTON_BORDERLESS);
	this.setEnabled(false);
    }

    public boolean setArchivo(Archivo archivo) {
	boolean success = false;
	if (archivo != null) {
	    if (fileDownloader == null) {
		if (logger.isDebugEnabled())
		    logger.debug("Creando fileDowloader para archivo: " + archivo.getNombre());
		fileDownloader = new FileDownloader(Utils.archivoToStreamResource(archivo));
		fileDownloader.extend(this);
	    } else {
		if (logger.isDebugEnabled())
		    logger.debug("Seteando Archivo: " + archivo.getNombre());
		fileDownloader.setFileDownloadResource(Utils.archivoToStreamResource(archivo));
	    }
	    this.setEnabled(true);
	    success = true;
	} else {
	    this.setEnabled(false);
	}
	return success;
    }

    public void setArchivo(String filename) {
	if (fileDownloader == null) {
	    if (logger.isDebugEnabled())
		    logger.debug("Creando fileDowloader para archivo: " + filename);
	    fileDownloader = new FileDownloader(fromPathtoSR(filename));
	    fileDownloader.extend(this);
	} else {
	    if (logger.isDebugEnabled())
		    logger.debug("Seteando Archivo: " + filename);
	    fileDownloader.setFileDownloadResource(fromPathtoSR(filename));
	}
    }

    public static StreamResource fromPathtoSR(String filename) {

	return new StreamResource(new StreamResource.StreamSource() {
	    public InputStream getStream() {
		InputStream is = null;
		try {
		    is = new FileInputStream("Files" + File.separator + filename);
		} catch (FileNotFoundException e) {
		    System.err.println("No se ha encontrado el archivo a descargar");
		    e.printStackTrace();
		}
		return is;
	    }
	}, filename);

    }

    public void descargar(Archivo archivo) {
	if (setArchivo(archivo)) {
	    if (logger.isDebugEnabled())
		logger.debug("Descargando Archivo: " + archivo.getNombre());
	    fileDownloader.getFileDownloadResource();
	} else {
	    Notification.show("Archivo no Encontrado", Notification.Type.WARNING_MESSAGE);
	}
    }
}
