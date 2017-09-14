package com.PubliciBot.UI.Vistas.VistaCamapana;

import com.PubliciBot.DM.AccionPublicitaria;
import com.PubliciBot.DM.Campana;
import com.PubliciBot.DM.Medio;
import com.PubliciBot.UI.Vistas.Controladores.ABMAccionController;
import com.PubliciBot.UI.Vistas.Controladores.ABMCampanasController;
import com.PubliciBot.UI.Vistas.Controladores.EstadisticasCampanaController;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import eu.livotov.tpt.gui.widgets.TPTSizer;

import java.util.ArrayList;

/**
 * Created by Max on 6/24/2017.
 */
public class AccionView extends Panel {


    public enum EstadoABMAccion{ NUEVAACCION, EDICIONACCION}

    Grid accionList = new Grid();
    ABMAccionController abmAccionController ;

    EstadisticasCampanaController estadisticasCampanaController ;
    Button nuevaAccion = new Button ("+ Accion");
    AccionPublicitaria seleccionada;
    Button eliminarAccion = new Button("Eliminar Accion");
    EstadoABMAccion estadoABMAccion;
    HorizontalLayout actions;


    public AccionView(ABMCampanasController abmCampanasController){
        super();

        abmAccionController  = new ABMAccionController(this, abmCampanasController);
        abmAccionController.setVisible(false);

        buildLayout();
        configureComponents();
        //Scrolleable
        this.addStyleName("v-scrollable");
        this.setHeight("100%");



    }

    private void configureComponents() {


        nuevaAccion.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                abmAccionController.setVisible(true);
                abmAccionController.crearAccion(new AccionPublicitaria());
                accionList.deselect(accionList.getSelectedRow());
                setNuevaCcionVisibility();
            }
        });

        //campanasList.setColumnOrder("nombre","descripcion");
        accionList.addSelectionListener(new SelectionEvent.SelectionListener() {
            @Override
            public void select(SelectionEvent selectionEvent) {
                if(seleccionada == null) {
                    seleccionada = (AccionPublicitaria) accionList.getSelectedRow();
                    actions.addComponent(eliminarAccion);
                }
                else {
                    AccionPublicitaria seleccionadaGrid = (AccionPublicitaria) accionList.getSelectedRow();
                    if(seleccionadaGrid != null) {
                        if(estadisticasCampanaController != null) {
                            actions.removeComponent(eliminarAccion);
                            seleccionada = seleccionadaGrid;
                            actions.addComponent(eliminarAccion);
                        }
                        else{
                            seleccionada = seleccionadaGrid;
                            actions.addComponent(eliminarAccion);
                        }
                    }
                    else{
                        actions.removeComponent(eliminarAccion);
                    }
                }
            }
        });

        eliminarAccion.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                abmAccionController.eliminarAccion((AccionPublicitaria)accionList.getSelectedRow());
                accionList.deselect(accionList.getSelectedRow());
            }
        });
        accionList.setContainerDataSource(new BeanItemContainer<>(AccionPublicitaria.class));
        accionList.removeColumn("valorPeriodicidad");

        accionList.setColumnOrder("nombreAccion","medio","destino","periodicidadSegundos");
    }

    private void buildLayout() {
        nuevaAccion.setStyleName(ValoTheme.BUTTON_PRIMARY);
         actions = new HorizontalLayout();
         actions.addComponent(nuevaAccion);
         actions.setSpacing(true);

        VerticalLayout left = new VerticalLayout(new TPTSizer( null, "15px") ,accionList,actions);

        left.setSizeFull();
        accionList.setSizeFull();
        left.setExpandRatio(accionList, 1);

        HorizontalLayout mainLayout = new HorizontalLayout(left, abmAccionController);
        mainLayout.setSizeFull();
        mainLayout.setExpandRatio(left, 1);
        mainLayout.setSpacing(true);
        mainLayout.setMargin(true);
        setContent(mainLayout);
        // Split and allow resizing
    }


    public void refreshAcciones(Campana camp) {
        ArrayList<AccionPublicitaria> acciones = camp.getAcciones();
        accionList.setContainerDataSource(new BeanItemContainer<>(
               AccionPublicitaria.class, acciones));




    }

    public void setNuevaCcionVisibility(){
        if(nuevaAccion.isVisible())
            nuevaAccion.setVisible(false);
        else
            nuevaAccion.setVisible(true);
    }
}
