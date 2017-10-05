package com.TpFinal.view.component;

import com.vaadin.ui.Upload;
import com.vaadin.ui.themes.ValoTheme;

public class UploadButton extends Upload {


    public UploadButton (String buttonCaption, String uploadCaption,UploadReceiver uploadReceiver){
        super(uploadCaption,uploadReceiver);
        this.setButtonCaption( buttonCaption);
    }
    public UploadButton (String buttonCaption,UploadReceiver uploadReceiver){
        super("",uploadReceiver);
        this.setButtonCaption( buttonCaption);
        addStyleName(ValoTheme.BUTTON_TINY);
    }


}

