package com.TpFinal.view.component;

import com.vaadin.ui.Upload.Receiver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public abstract class UploadReceiver implements Receiver {
    private static final long serialVersionUID = 2215337036540966711L;
    OutputStream outputFile = null;

    private String pathAndName;
    private String filePath;


    public UploadReceiver()

    {

        this.filePath="Files"+File.separator;
        File dir = new File("Files");
        dir.mkdir();

    }





    public abstract void onSuccessfullUpload(String filename);

    @Override
    public OutputStream receiveUpload(String strFilename, String strMIMEType) {
        File file=null;
        try {
            this.setPathAndName(filePath+strFilename);

            file = new File(this.getPathAndName());

            if(!file.exists()) {
                file.createNewFile();
            }
            outputFile =  new FileOutputStream(file);
            onSuccessfullUpload(strFilename);
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

    public String getPathAndName() {
        return pathAndName;
    }

    public void setPathAndName(String fileName) {
        this.pathAndName = fileName;
    }
}
