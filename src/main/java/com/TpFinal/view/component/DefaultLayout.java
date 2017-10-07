package com.TpFinal.view.component;

import com.TpFinal.services.DashboardEventBus;
import com.vaadin.server.Responsive;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public abstract class DefaultLayout extends VerticalLayout {

    HorizontalLayout toolbar;


    public DefaultLayout(){
        super();
        setSizeFull();
        addStyleName("transactions");
        addStyleName("v-scrollable");
        setMargin(false);
        setSpacing(false);
        DashboardEventBus.register(this);
        Responsive.makeResponsive(this);
    }




    public HorizontalLayout buildToolbar(String Title, Component... Components ){

        HorizontalLayout header = new HorizontalLayout();
        header.addStyleName("viewheader");
        Responsive.makeResponsive(header);

        Label title = new Label(Title);
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_H1);
        title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        header.addComponent(title);
        HorizontalLayout tools =new HorizontalLayout();

        for(Component comp: Components){
            tools.addComponent(comp);

        }
        tools.setSpacing(true);
        Responsive.makeResponsive(tools);

       tools.addStyleName("toolbar");
        header.addComponent(tools);


        return header;
    }



}



