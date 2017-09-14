package com.PubliciBot.UI.Vistas.VistaCamapana;

import com.vaadin.server.ClassResource;
import com.vaadin.server.FileResource;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.*;

import java.io.File;

/**
 * Created by Hugo on 27/06/2017.
 */
public class VisualizadorImagenView extends Window {

    String imagePath;

    public VisualizadorImagenView(String pathImagen) {

        super("Imagen de la campaña"); // Set window caption
        this.imagePath = pathImagen;

        Image picture = new Image();
        picture.setSource(cargarImagen());

        configurarPopup();

        Button btnCerrar = new Button("Cerrar");

        btnCerrar.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                close();
            }
        });

        VerticalLayout verticalLayout = new VerticalLayout(picture);
        verticalLayout.setComponentAlignment(picture, Alignment.MIDDLE_CENTER);
        verticalLayout.setMargin(true);
        verticalLayout.setSizeFull();

        HorizontalLayout botonesLayout = new HorizontalLayout(btnCerrar);
        botonesLayout.setComponentAlignment(btnCerrar, Alignment.MIDDLE_CENTER);
        botonesLayout.setMargin(true);
        botonesLayout.setSizeFull();

        VerticalLayout componentes = new VerticalLayout(verticalLayout, botonesLayout);
        componentes.setMargin(true);

        setContent(componentes);
    }

    private void configurarPopup()
    {
        center();
        setClosable(true);
        setHeight(350, Unit.PIXELS);
        setWidth(500, Unit.PIXELS);

        setModal(true);
    }

    private FileResource cargarImagen()
    {
        FileResource resource = new FileResource(new File(this.imagePath));

        return resource;
    }


}
