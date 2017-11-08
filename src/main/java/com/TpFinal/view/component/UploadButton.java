package com.TpFinal.view.component;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Upload;
import com.vaadin.ui.themes.ValoTheme;

public class UploadButton extends Upload {



    public UploadButton (String buttonCaption, String uploadCaption,UploadReceiver uploadReceiver){
        super(uploadCaption,uploadReceiver);
        this.setButtonCaption( buttonCaption);
    }
    public UploadButton (String buttonCaption,UploadReceiver uploadReceiver){
        super(null,uploadReceiver);
        this.setButtonCaption( buttonCaption);
      //  this.setIcon(VaadinIcons.UPLOAD);
        this.setStyleName(ValoTheme.BUTTON_BORDERLESS);
    }
    public UploadButton (UploadReceiver uploadReceiver){
        super(null,uploadReceiver);
       // this.setIcon(VaadinIcons.UPLOAD);
        this.setStyleName(ValoTheme.BUTTON_TINY);
        this.setCaption(null);
    }
}

