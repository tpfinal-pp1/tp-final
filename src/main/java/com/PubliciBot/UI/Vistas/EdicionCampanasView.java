package com.PubliciBot.UI.Vistas;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.VerticalLayout;

/**
 * Created by Hugo on 25/05/2017.
 */
@Theme("newtheme")
public class EdicionCampanasView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "Campañas";

    public EdicionCampanasView()
    {
        //ABMCampanasController abmCampanasController = new ABMCampanasController();
        //this.addComponent(abmCampanasController);
    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
      //  Notification.show("Bienvenido "+((NavigatorUI) UI.getCurrent()).getLoggedInUser());

    }

}
