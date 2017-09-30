package com.TpFinal.view.component;

import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.Receiver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class UploadReceiver implements Receiver {
    private static final long serialVersionUID = 2215337036540966711L;
    OutputStream outputFile = null;

    private String fileName;
    private String filePath;


    public UploadReceiver()

    {
        this.filePath="Uploads"+File.separator;
        File dir = new File("Uploads");
        dir.mkdir();
    }
    public UploadReceiver(String filePath)

    {
        this.filePath=filePath;

    }





    @Override
    public OutputStream receiveUpload(String strFilename, String strMIMEType) {
        File file=null;
        try {
            this.setFileName(filePath+strFilename);

            file = new File(this.getFileName());

            if(!file.exists()) {
                file.createNewFile();
            }
            outputFile =  new FileOutputStream(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputFile;
    }

    protected void finalize() {
        try {
            super.finalize();
            if(outputFile!=null) {
                outputFile.close();
            }
        } catch (Throwable exception) {
            exception.printStackTrace();
        }
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
