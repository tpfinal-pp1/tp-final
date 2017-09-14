package com.TpFinal.UI.Vistas.Controladores;

import com.TpFinal.DM.AccionPublicitaria;
import com.TpFinal.DM.Campana;
import com.TpFinal.DM.Tag;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;

import java.util.ArrayList;

/**
 * Created by Max on 6/4/2017.
 */
public class EstadisticasCampanaController extends Panel {

    private ArrayList<Label> tagsCampana;
    private ArrayList<Label> accionesCampana;
    private Image imagenCampana;
    private Label tituloCampana;
    private Label descripcionCampana;
    private Label textoCampana;
    private Label duracionCampana;
    private Label fechaInicioCampana;
    private Label accionCampana;


    public EstadisticasCampanaController(Campana campana){

            initComponents(campana);

            addComponents();
    }

    private void initComponents(Campana campana){
        this.tagsCampana = new ArrayList<>();
        this.accionesCampana = new ArrayList<>();
        this.agregarLabels(campana);
        this.tituloCampana      = new Label("Nombre: "  + campana.getNombre());
        this.descripcionCampana = new Label("Descripcion: "    + campana.getDescripcion());
        this.textoCampana       = new Label("Mensaje: " + campana.getMensaje().getTextoMensaje());
        this.duracionCampana    = new Label("Duracion: " + campana.getDuracion()+ " "+ campana.getUnidadMedida());
        this.fechaInicioCampana = new Label("Inicio: "   + campana.getFechaInicio());

    }

    private void agregarLabels(Campana campana){
        Label lbl = null;
        this.accionCampana      = new Label ();
        for(Tag tag : campana.getTags()){
             lbl = new Label(
                    String.format("<font size = \"5\" color=\"#4286f4\">"+"#"+tag.getNombre() )
                    , ContentMode.HTML);


            tagsCampana.add(lbl);
        }
        String acciones="Acciones: ";
        for(AccionPublicitaria ac : campana.getAcciones()) {
           acciones=acciones+ ac.getMedio().getTipoMedio() + ": "+ac.getDestino()+ ", ";

        }

        accionCampana.setValue(acciones);

    }

    private void addComponents(){
        VerticalLayout elvertical=new VerticalLayout();
        HorizontalLayout labels=new HorizontalLayout();
        labels.setSpacing(true);
        elvertical.addStyleName("v-scrollable");
        elvertical.setHeight("100%");

        for(Label lbl : tagsCampana)
            labels.addComponent(lbl);

        elvertical.addComponents(labels);
        elvertical.addComponent(tituloCampana);
        elvertical.addComponent(descripcionCampana);
        elvertical.addComponent(textoCampana);
        elvertical.addComponent(duracionCampana);
        elvertical.addComponent(fechaInicioCampana);
        elvertical.addComponent(accionCampana);

        elvertical.setMargin(true);
        elvertical.setSpacing(true);
        this.setContent(elvertical);

    }






}
