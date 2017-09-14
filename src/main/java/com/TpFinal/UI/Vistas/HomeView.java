package com.TpFinal.UI.Vistas;

import com.TpFinal.UI.MyUI;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;

public class HomeView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "";

    public HomeView() {
        CustomLayout aboutContent = new CustomLayout("homeview");
        aboutContent.setStyleName("about-content");

        // you can add Vaadin components in predefined slots in the custom
        // layout

       /* Image image = new Image(null, new ThemeResource("img/Logoazul2x2.png"));
      //  image.setStyleName("logo");
        aboutContent.addComponent(image);*/
            String nombre= MyUI.get().getAccessControl().getPrincipalName();


        aboutContent.addComponent(
                new Label( " <img src=\"http://i.imgur.com/6vp87Ak.png\" />  Bienvenido  "
                        +  nombre.substring(0, 1).toUpperCase() + nombre.substring(1), ContentMode.HTML), "info");


        setSizeFull();
        setMargin(false);
        setStyleName("about-view");
        addComponent(aboutContent);
        setComponentAlignment(aboutContent, Alignment.MIDDLE_CENTER);
    }

    @Override
    public void enter(ViewChangeEvent event) {
    }

}
