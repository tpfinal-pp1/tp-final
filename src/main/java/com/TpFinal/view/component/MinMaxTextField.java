package com.TpFinal.view.component;


import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;

public class MinMaxTextField extends HorizontalLayout {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    TextField min = new TextField();
    TextField max = new TextField();

    public MinMaxTextField(String caption) {
	this.setCaption(caption);
	min.setPlaceholder("mínimo");
	max.setPlaceholder("máximo");
	this.addComponents(min,max);
	this.setSpacing(false);
	
    }

    public TextField getMinTextField() {
        return min;
    }

    public TextField getMaxTextField() {
        return max;
    }  
    
    

}
