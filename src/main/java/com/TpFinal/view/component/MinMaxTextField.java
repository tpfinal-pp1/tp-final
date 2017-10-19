package com.TpFinal.view.component;


import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

public class MinMaxTextField extends HorizontalLayout {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    TextField min = new TextField();
    TextField max = new TextField();
    Label separador = new Label("-");

    public MinMaxTextField(String caption) {
	this.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
	this.setCaption(caption);
	min.setPlaceholder("mínimo");
	max.setPlaceholder("máximo");
	min.setWidth("100%");
	max.setWidth("100%");
	
	this.addComponents(min,separador,max);
	this.setExpandRatio(min, 0.45f);
	this.setExpandRatio(max, 0.45f);
	this.setSpacing(true);
	this.forEach(component -> {
	    component.addStyleNames(ValoTheme.TEXTFIELD_ALIGN_CENTER,ValoTheme.TEXTFIELD_INLINE_ICON,ValoTheme.TEXTFIELD_BORDERLESS);
	});
		
    }

    public TextField getMinTextField() {
        return min;
    }

    public TextField getMaxTextField() {
        return max;
    }  
    
    

}
