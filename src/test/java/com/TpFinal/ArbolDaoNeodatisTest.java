package com.TpFinal;

import com.TpFinal.DM.ArbolTags;
import com.TpFinal.DM.Tag;
import com.TpFinal.Services.ArbolTagsService;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by agusa on 5/6/2017.
 */
public class ArbolDaoNeodatisTest {

    @Test
    public void DAOTest() {
        ArbolTagsService arbol = new ArbolTagsService();
        //PARA NO SOBREESCRIBIR LA DB
        ArbolTagsService arbolDeDB = new ArbolTagsService();
        arbolDeDB.recuperarArbol();
        arbol.setArbolTags(new ArbolTags());


        Tag tagPadre = new Tag ("Padre");
        Tag h1p1 = new Tag ("Hijo 1 P1",tagPadre);
        Tag h2p1 = new Tag ("Hijo 2 P1",tagPadre);
        Tag tagPadre2 = new Tag ("Padre 2");
        Tag h1p2 = new Tag ("Hijo 1 P2",tagPadre2);

        arbol.agregarTag(tagPadre);
        arbol.agregarTag(h1p1);
        arbol.agregarTag(h2p1);
        arbol.agregarTag(tagPadre2);
        arbol.agregarTag(h1p2);


        arbol.guardarArbol();
        arbol.recuperarArbol();
        Assert.assertEquals(5,arbol.getArbolTags().getTags().size());
        arbol.setArbolTags(new ArbolTags());
        arbol.guardarArbol();

        //DEVOLVER A LA DB A SU ESTADO PREVIO AL TEST
        arbolDeDB.guardarArbol();
    }
}





