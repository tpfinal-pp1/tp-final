package com.TpFinal.view.component;


import com.vaadin.server.FileDownloader;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Button;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;


public class DownloadButton extends Button {
    StreamResource sr;
    FileDownloader fileDownloader;
    public DownloadButton(String buttonName,StreamResource streamResource){
        super(buttonName);
        sr =streamResource;
        fileDownloader = new FileDownloader(sr);
        fileDownloader.extend(this);
    }
    public DownloadButton(String buttonName,String filename){
        super(buttonName);
        fileDownloader = new FileDownloader(fromPathtoSR(filename));
        fileDownloader.extend(this);
    }

    private StreamResource fromPathtoSR(String filename) {

        return new StreamResource(new StreamResource.StreamSource() {
            public InputStream getStream() {
                InputStream is = null;
                try {
                    is = new FileInputStream("Uploads"+ File.separator+filename);
                } catch (FileNotFoundException e) {
                    System.err.println("No se ha encontrado el archivo a descargar");
                }
                return is;
            }
        }, filename);

    }
}
