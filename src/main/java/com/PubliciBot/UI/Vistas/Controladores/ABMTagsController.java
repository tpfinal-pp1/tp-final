package com.PubliciBot.UI.Vistas.Controladores;

import com.PubliciBot.DM.Tag;
import com.PubliciBot.Services.ArbolTagsService;
import com.PubliciBot.UI.Vistas.ABMTagsView;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.Collection;

public class ABMTagsController extends VerticalLayout {
    Tree treeVaadin;

    public ABMTagsController(ABMTagsView abmtag) {
        super();
        treeVaadin = new Tree();

        ArbolTagsService arbolTagService = new ArbolTagsService();

        TextField txtNuevoTag = new TextField("");
        txtNuevoTag.setMaxLength(30);
        Label Title = new Label("Administracion de Tags");
        Button btnAgregarTag = new Button("+ Tag");
        Button btneliminarTag = new Button("Eliminar");

        arbolTagService.recuperarArbol();
        treeVaadin = arbolTagService.convertirArbolaTree(treeVaadin);
        btneliminarTag.setStyleName(ValoTheme.BUTTON_DANGER);
        btnAgregarTag.setStyleName(ValoTheme.BUTTON_PRIMARY);
        btnAgregarTag.setClickShortcut(ShortcutAction.KeyCode.ENTER);


        btnAgregarTag.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                Tag nuevo = null;
                Tag temp = null;
                String nombre = txtNuevoTag.getValue().trim();
                if (!nombre.isEmpty()) {
                    txtNuevoTag.setValue("");

                    temp = (Tag) treeVaadin.getValue();
                    if (temp != null ) {
                        nuevo = new Tag(nombre,temp);
                        arbolTagService.agregarTag(nuevo);
                        agregarTag(treeVaadin, nuevo);
                        setearPadre(treeVaadin, nuevo, temp);
                    }
                    else {
                        nuevo = new Tag(nombre);
                        arbolTagService.agregarTag(nuevo);
                        agregarTag(treeVaadin, nuevo);
                    }
                }
                else
                    Notification.show("Imposible crear un Tag vacío.");
              //  Notification.show(arbolTagService.getArbolTags().getTags().toString());
                arbolTagService.guardarArbol();
            }
        });

        btneliminarTag.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                Tag temp = (Tag) treeVaadin.getValue();
                if (temp != null) {
                    quitarTagTree(treeVaadin, temp);
                    arbolTagService.quitarTagArbolTags(temp);
                }
                if (temp == null)
                    Notification.show("No se ha seleccionado ningun tag");
                Notification.show(arbolTagService.getArbolTags().getTags().toString());
                arbolTagService.guardarArbol();
            }
        });
        HorizontalLayout HL = new HorizontalLayout();
        HL.addComponent(txtNuevoTag);
        HL.addComponent(btnAgregarTag);
        HL.setComponentAlignment(txtNuevoTag, Alignment.MIDDLE_CENTER);
        HL.setComponentAlignment(btnAgregarTag, Alignment.BOTTOM_CENTER);
        this.addComponent(Title);
        this.setComponentAlignment(Title, Alignment.BOTTOM_CENTER);
        this.addComponent(HL);
        this.addComponent(treeVaadin);
        this.setMargin(true);
        this.setSpacing(true);
        HorizontalLayout HL2 = new HorizontalLayout();
        HL2.addComponent(btneliminarTag);
        HL2.setComponentAlignment(btneliminarTag, Alignment.BOTTOM_RIGHT);
        HL2.setSpacing(true);
        this.addComponent(HL2);


    }


    private void agregarTag(Tree arbol, Tag tag) {
        if (!exists(arbol, tag)) {
            arbol.addItem(tag);
        }
     //   Notification.show("No se puede agregar un tag duplicado");
        Notification.show(tag.getNombre());
    }

    private boolean exists(Tree arbol, Tag tag) {
        Collection<Object> treeTags = (Collection<Object>) arbol.getItemIds();
        for (Object t : treeTags) {
            Tag auxTag = (Tag) t;
            if (auxTag.equals(tag))
                return true;
        }
        return false;
    }

    private boolean setearPadre(Tree arbol, Tag tagHijo, Tag tagPadre) {
        boolean ret;
        try {
            ret = arbol.setParent(tagHijo, tagPadre);
        }
        catch (Exception e) {
            throw new IllegalArgumentException("No se encuentra el tag padre o hijo");
        }
        return ret;
    }

    private boolean quitarTagTree(Tree arbol, Tag tag) {
        boolean hasChildren = arbol.getChildren(tag) != null;
        if (hasChildren) {
            Object[] children = arbol.getChildren(tag).toArray();
            for (Object o : children) {
                Tag child = (Tag) o;
                quitarTagTree(arbol, child);
            }
        }
        return arbol.removeItem(tag);
    }
}
