package com.TpFinal.view.component;

import com.vaadin.ui.Upload.Receiver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public abstract class UploadReceiverRefactorizado implements Receiver {
    private static final long serialVersionUID = 2215337036540966711L;
    
    private OutputStream outputFile = null;
    private static final String directorioUpload =  "Files";
    private String fullPath;
    private final String  filePath =directorioUpload+File.separator;
    private String fileName;
    private String fileType;
    
    public UploadReceiverRefactorizado()
    {
        File dir = new File(directorioUpload);
        dir.mkdir();
    }

    @Override
    public OutputStream receiveUpload(String strFilename, String strMIMEType) {
        File file=null;
        try {
            this.setFullPath(filePath+strFilename+"."+strMIMEType);
            file = new File(this.getFullPath());
            if(!file.exists()) {
                file.createNewFile();
            }
            outputFile =  new FileOutputStream(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputFile;
    }    
    
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
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
            if(outputFile!=null) {
                outputFile.close();
                outputFile = null;
            }
        } catch (Throwable exception) {
            exception.printStackTrace();
        }
    }
    

    /**
     * Devuelve path completo de un archivo. Path + filename + . + extension.
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
