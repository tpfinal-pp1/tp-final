package com.TpFinal.view.dummy.pdf;


import com.TpFinal.services.DashboardEventBus;

import com.TpFinal.utils.Utils;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Widgetset;

import com.vaadin.server.Responsive;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

@Title("PDF")
@Theme("valo")
@Widgetset("com.vaadin.v7.Vaadin7WidgetSet")
public class PDFComponent extends VerticalLayout {

    Embedded pdf = new Embedded();

    public PDFComponent() {
        super();


    }


    public void setPDF(String filename) {
        pdf.setSource(Utils.fromPathtoSR(filename));
        pdf.setType(Embedded.TYPE_BROWSER);
        pdf.setMimeType("application/pdf");
        pdf.setSizeFull();
        this.addComponent(pdf);
        this.setExpandRatio(pdf,1);
        this.setSizeFull();

        this.setMargin(false);
        this.setSpacing(false);
    }

}





