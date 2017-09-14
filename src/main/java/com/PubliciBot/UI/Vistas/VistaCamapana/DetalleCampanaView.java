package com.PubliciBot.UI.Vistas.VistaCamapana;

import com.PubliciBot.DM.Campana;
import com.PubliciBot.UI.Vistas.Controladores.EstadisticasCampanaController;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * Created by Max on 6/11/2017.
 */
public class DetalleCampanaView extends Window {

    private EstadisticasCampanaController estadisticasCampanaController;
    private Button closingButton;

    public DetalleCampanaView(Campana campana){
        super("Detalle de su campaña"); // Set window caption
        center();

        // Disable the close button
        setClosable(false);
        setHeight(400, Unit.PIXELS);
        setWidth(400, Unit.PIXELS);

        VerticalLayout vl = new VerticalLayout();
        estadisticasCampanaController = new EstadisticasCampanaController(campana);
        closingButton = new Button("Close");

        closingButton.addClickListener(event -> close());


        vl.addComponent(estadisticasCampanaController);
        vl.addComponent(closingButton);
        setContent(vl);
    }


}
