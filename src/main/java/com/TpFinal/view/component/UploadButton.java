package com.TpFinal.view.component;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Upload;
import com.vaadin.ui.themes.ValoTheme;

public class UploadButton extends Upload {    
    
    public UploadButton (UploadReceiver uploadReceiver){
        super(null,uploadReceiver);     
        this.setStyleName(ValoTheme.BUTTON_TINY);
        this.setCaption(null);
    }
    public UploadButton (UploadDbReceiver uploadReceiver){
        super(null,uploadReceiver);     
        this.setStyleName(ValoTheme.BUTTON_TINY);
        this.setCaption(null);
    }
}

