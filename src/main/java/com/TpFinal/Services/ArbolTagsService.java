package com.TpFinal.Services;

import com.TpFinal.DAO.Neodatis.ArbolDAONeodatis;
import com.TpFinal.DM.ArbolTags;
import com.TpFinal.DM.Tag;
import com.vaadin.ui.Tree;

import java.util.ArrayList;

/**
 * Created by alumnos on 18/05/2017.
 */
public class ArbolTagsService {

    private ArbolDAONeodatis treeDAO;
    private ArbolTags arbolTags;

    public ArbolTagsService() {
        treeDAO = new ArbolDAONeodatis();
        arbolTags = new ArbolTags();
        this.recuperarArbol();
    }

    public void agregarTag(Tag tag) {
        if (!exists(tag)) {
            arbolTags.AgregarTag(tag);
        }
    }


    public boolean exists(Tag tag) {
        for (Tag t : arbolTags.getTags()) {
            if (t.getNombre().equals(tag.getNombre()))
               return true;
        }
       return false;
    }



    public void quitarTagArbolTags(Tag tag){
        ArrayList<Tag> hijos = buscarTagPorPadre(tag);
        for(Tag tagtemp : hijos){
            quitarTagArbolTags(tagtemp);
        }
        for(Tag tagtemp : hijos){
            arbolTags.getTags().remove(tagtemp);
        }

        arbolTags.getTags().remove(tag);
    }

    public ArrayList<Tag> buscarTagPorPadre(Tag tagPadre) {
        ArrayList<Tag> ret = new ArrayList<Tag>();
        for (Tag tagTemp : this.arbolTags.getTags()) {

            if(tagTemp.getPadre() != null) {
                if (tagTemp.getPadre().equals(tagPadre))
                    ret.add(tagTemp);
            }

        }
        return ret;
    }

    public Tree convertirArbolaTree (Tree treeVaadin) {
        //Limpio el arbol para no repetir los items
        treeVaadin.removeAllItems();

        for (Tag tag : arbolTags.getTags()) {
            treeVaadin.addItem(tag);
        }

        for (Tag tag : arbolTags.getTags()) {
            if (tag.getPadre() != null)
                treeVaadin.setParent(tag, tag.getPadre());
        }
        return treeVaadin;
    }

    public void recuperarArbol() { this.arbolTags = treeDAO.recuperar(); }

    public void guardarArbol() {
        treeDAO.guardar(arbolTags);
    }

    public ArbolTags getArbolTags() {
        return arbolTags;
    }

    public void setArbolTags(ArbolTags arbolTags) {
        this.arbolTags = arbolTags;
    }
}
