package com.TpFinal.view.component;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public abstract class VentanaSelectora<T>extends Window {

    public static final String ID = "profilepreferenceswindow";


    public Grid<T> grid;
    private T objeto;
    public abstract void updateList();
    public abstract void setGrid();
    public abstract void seleccionado(T objeto);

    private Button ok = new Button("Seleccionar");





    public VentanaSelectora(T seleccion) {
            objeto = seleccion;
            addStyleName("profile-window");
            setId(ID);

            Responsive.makeResponsive(this);

            setModal(true);
            setCloseShortcut(KeyCode.ESCAPE, null);
            setResizable(false);
        setClosable(true);
        setGrid();
        setContent(new VerticalLayout(grid,buildFooter()));

        grid.setSizeFull();
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
               objeto =event.getValue();
            }
        });


        Responsive.makeResponsive(this);
        setVisible(true);
        UI.getCurrent().addWindow(this);
        this.focus();

        updateList();
    }







    private Component buildFooter() {
        HorizontalLayout footer = new HorizontalLayout();
        footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
        footer.setWidth(100.0f, Unit.PERCENTAGE);
        footer.setSpacing(false);


        ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
        ok.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {


                    // Updated user should also be persisted to database. But
                    // not in this demo.
                Notification success ;
                    if (objeto==null){
                       success = new Notification(
                                "No Seleccionado");
                    }
                    else{
                        success = new Notification(
                                "Seleccionado");
                        seleccionado(objeto);


                    }
                    success.setDelayMsec(2000);
                    success.setStyleName("bar success small");
                    success.setPosition(Position.BOTTOM_CENTER);
                    success.show(Page.getCurrent());
                    close();


            }
        });
        ok.focus();
        footer.addComponent(ok);
        footer.setComponentAlignment(ok, Alignment.TOP_RIGHT);
        return footer;
    }



    public T getObjeto(){return this.objeto;}

    public Button getSelectionButton(){return this.ok;}
}


