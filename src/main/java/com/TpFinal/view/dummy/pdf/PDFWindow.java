package com.TpFinal.view.dummy.pdf;


import com.TpFinal.services.DashboardEventBus;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Widgetset;

import com.vaadin.server.Responsive;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.io.InputStream;

@Title("PDF")
@Theme("valo")
@Widgetset("com.vaadin.v7.Vaadin7WidgetSet")
public class PDFWindow extends Window {
    String filename="";

    public PDFWindow(String filename) {
        super();
        this.filename=filename;
        configureComponents();
        DashboardEventBus.register(this);
        setSizeFull();
        addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
        Responsive.makeResponsive(this);
        setVisible(true);
        UI.getCurrent().addWindow(this);
        this.focus();

    }


    private void configureComponents() {

        Embedded pdf = new Embedded("", new StreamResource(new StreamResource.StreamSource() {
            public InputStream getStream() {
                InputStream is = PDFWindow.class.getClassLoader().getResourceAsStream(filename);
                return is;
            }
        }, filename));

        pdf.setType(Embedded.TYPE_BROWSER);
        pdf.setMimeType("application/pdf");
        pdf.setSizeFull();
        this.setContent(pdf);
    }
}





