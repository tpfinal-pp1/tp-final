package com.TpFinal.view.component;

import com.vaadin.ui.Button;
import com.vaadin.ui.themes.ValoTheme;

public class TinyButton extends Button {

    public TinyButton(String caption){
        super(caption);
        addStyleName(ValoTheme.BUTTON_TINY);

    }

}
