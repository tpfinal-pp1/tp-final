package com.TpFinal.view.component;

import com.TpFinal.properties.Parametros;
import com.TpFinal.utils.Utils;
import com.vaadin.ui.Upload.Receiver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.Instant;

import org.apache.log4j.Logger;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;

public class UploadDbReceiver implements Receiver {
    private static final long serialVersionUID = 2215337036540966711L;
    private static Logger logger = Logger.getLogger(UploadDbReceiver.class);

    private OutputStream outputFile = null;
    private static final String directorioUpload = "Files";
    private String fullPath;
    private String filePath = directorioUpload + File.separator;
    private String fileName;

    public UploadDbReceiver() {
	File dir = new File(directorioUpload);
	dir.mkdir();
	fullPath = filePath;
    }

    @Override
    public OutputStream receiveUpload(String strFilename, String strMIMEType) {
	File file = null;

	if (logger.isDebugEnabled()) {
	    logger.debug("Cargando archivo: " + strFilename);
	}
	this.setFileName(strFilename);

	file = new File(this.getFullPath());
	if (!file.exists()) {
	    try {
		file.createNewFile();
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
	try {
	    outputFile = new FileOutputStream(file);
	} catch (FileNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return outputFile;
    }

    public String getFileName() {
	return fileName;
    }

    public void setFilePath(String filePath) {
	this.filePath = filePath;
    }

    public void setFileName(String fileName) {
	this.fileName = fileName;
    }

    public String getFilePath() {
	return filePath;
    }

    public OutputStream getOutputFile() {
	return outputFile;
    }

    public void setOutputFile(OutputStream outputFile) {
	this.outputFile = outputFile;
    }

    protected void finalize() {
	try {
	    super.finalize();
	    if (outputFile != null) {
		outputFile.close();
		outputFile = null;
	    }
	} catch (Throwable exception) {
	    exception.printStackTrace();
	}
    }

    /**
     * Devuelve path completo de un archivo. Path + filename + . + extension.
     * 
     * @return String con el path completo e.g. "../File/file.doc"
     */
    public String getFullPath() {
	return fullPath;
    }

    /**
     * Setea path completo de un archivo. "Path + filename + . + extension"
     */

    public void setFullPath(String fileName) {
	this.fullPath = fileName;
    }
}
