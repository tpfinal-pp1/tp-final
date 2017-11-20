package com.TpFinal.view.component;

import com.TpFinal.data.conexion.ConexionHibernate;
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

public class DownloadButton extends Button {
    private static Logger logger = Logger.getLogger(DownloadButton.class);
    StreamResource sr;
    FileDownloader fileDownloader;

    /**
     * El boton se crea por defecto deshabilitado hasta tanto no se le setee un
     * archivo para descargar.
     */
    public DownloadButton() {
	super();
	this.setIcon(VaadinIcons.DOWNLOAD);
	this.setStyleName(ValoTheme.BUTTON_BORDERLESS);
	if (logger.isDebugEnabled())
	    logger.debug("Desactivando downloadButton(Constructor)");
	this.setEnabled(false);
    }

    public boolean setArchivo(Archivo archivo) {
	boolean success = false;
	if (archivo != null && archivo.getDocumento() != null) {
	    if (fileDownloader == null) {
		if (logger.isDebugEnabled())
		    logger.debug("Creando fileDowloader para archivo: " + archivo.getNombre() + archivo.getExtension());
		fileDownloader = new FileDownloader(Utils.archivoToStreamResource(archivo));
		fileDownloader.extend(this);
		this.setEnabled(true);
	    } else {
		if (logger.isDebugEnabled())
		    logger.debug("Seteando Archivo: " + archivo.getNombre() + archivo.getExtension());
		fileDownloader.setFileDownloadResource(Utils.archivoToStreamResource(archivo));
		this.setEnabled(true);
	    }
	    this.setEnabled(true);
	    success = true;
	} else {
	    if (this.fileDownloader != null) {
		this.setEnabled(true);
		fileDownloader.getFileDownloadResource();
		success = true;
	    } else {
		if (logger.isDebugEnabled())
		    logger.debug("Desactivando downloadButton");
		this.setEnabled(false);
	    }
	}
	return success;
    }

    public void setArchivoFromPath(String path, String filename) {
	if (fileDownloader == null) {
	    if (logger.isDebugEnabled())
		logger.debug("Creando fileDowloader para archivo: " + path);
	    fileDownloader = new FileDownloader(fromPathtoSR(path, filename));
	    fileDownloader.extend(this);
	    if (logger.isDebugEnabled())
		    logger.debug("Activando downloadButton");
	    this.setEnabled(true);
	} else {
	    if (logger.isDebugEnabled())
		logger.debug("Seteando Archivo: " + path);
	    fileDownloader.setFileDownloadResource(fromPathtoSR(path, filename));
	    if (logger.isDebugEnabled())
		    logger.debug("Activando downloadButton");
	    this.setEnabled(true);
	}
    }

    public void descargar(Archivo archivo) {
	if (setArchivo(archivo)) {
	    if (logger.isDebugEnabled())
		logger.debug("Descargando Archivo: " + archivo.getNombre() + archivo.getExtension());
	    fileDownloader.getFileDownloadResource();
	} else {
	    Notification.show("Archivo no Encontrado", Notification.Type.WARNING_MESSAGE);
	}
    }



    private StreamResource fromPathtoSR(String path, String filename) {
	if (logger.isDebugEnabled())
	    logger.debug("Seteando path de fileSystem: " + path);

	return new StreamResource(new StreamResource.StreamSource() {
	    public InputStream getStream() {
		InputStream is = null;
		try {
		    
		    is = new FileInputStream(path);
		} catch (FileNotFoundException e) {
		    System.err.println("No se ha encontrado el archivo a descargar: " + path+File.separator+filename);
		    e.printStackTrace();
		}
		return is;
	    }
	}, filename);

    }
}
