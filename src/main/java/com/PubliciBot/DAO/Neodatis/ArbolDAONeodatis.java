package com.PubliciBot.DAO.Neodatis;

import com.PubliciBot.DAO.Interfaces.ArbolDAO;
import com.PubliciBot.DM.ArbolTags;
import com.PubliciBot.DM.Tag;
import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.Objects;

import java.util.ArrayList;

/**
 * Created by Hugo on 20/05/2017.
 */
public class ArbolDAONeodatis extends DAONeodatis<ArbolTags> implements ArbolDAO {



    @Override
    public void guardar(ArbolTags arbol) {
        ODB odb = null;
        try {
           odb = ODBFactory.open(fileNameNeodatisDB);
           ArrayList<Object> objs =
                   new ArrayList<Object>(odb.getObjects(ArbolTags.class));
           for (Object o : objs) {
               ArbolTags viejosarboles=(ArbolTags)o;
               for(Tag tag : viejosarboles.getTags()) {
                   odb.delete(tag);
               }
               odb.delete(o);
           }
            odb.store(arbol);
       }
       catch (Exception e){
           e.printStackTrace();
       }
       finally{
            odb.close();
        }

    }

    @Override
    public ArbolTags recuperar() {
        Objects<ArbolTags> todos = super.obtenerTodos(ArbolTags.class);
        if(todos!=null)
            if(todos.size()>0)
                return todos.getFirst();

        return new ArbolTags();
    }

}
