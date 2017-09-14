package com.TpFinal.Services;

/**
 * Created by Max on 5/23/2017.
 */
public class TreeService {


    public TreeService() {

    }


    //Metodo recursivo para obtener items (ya solucionado )
  /*  public ArrayList<Tag> recorrerRecursivamente(Tree arbol,ArrayList<Tag> tags){
        ArrayList<Tag> ret = new ArrayList<Tag>();

        for(Tag tag : tags){
            ArrayList<Tag> hijosdelTag = convertiraTags(arbol.getChildren(tag));
            if(hijosdelTag.size() == 0)
                ret=new ArrayList<Tag>();
            else {
                hijosdelTag.addAll(recorrerRecursivamente(arbol, hijosdelTag));
                ret = hijosdelTag;
            }
        }
        return ret;
    }*/
    //Fin metodo recursivo para obtener items (ya solucionado )
}
