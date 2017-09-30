package com.TpFinal.view.component;


import com.vaadin.server.FileDownloader;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Button;


public class DownloadButton extends Button {
    StreamResource sr;
    FileDownloader fileDownloader;
    public DownloadButton(String name,StreamResource streamResource){
        super(name);
        sr =streamResource;
        fileDownloader = new FileDownloader(sr);
        fileDownloader.extend(this);
    }
}
