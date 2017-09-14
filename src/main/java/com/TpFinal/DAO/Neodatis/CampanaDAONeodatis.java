package com.TpFinal.DAO.Neodatis;

import com.TpFinal.DAO.Interfaces.CampanaDAO;
import com.TpFinal.DM.Campana;
import com.TpFinal.DM.Usuario;
import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.nq.SimpleNativeQuery;

import java.util.HashMap;

/**
 * Created by Max on 6/4/2017.
 */
public class CampanaDAONeodatis extends DAONeodatis<Campana> implements CampanaDAO {

    @Override
    public HashMap<Long,Campana> recuperarCampanas(Usuario usuario) {
        ODB odb = null;
        try {
            odb = ODBFactory.open(fileNameNeodatisDB);
            IQuery usuarioDeCampana = new SimpleNativeQuery(){
                public boolean match(Usuario user) {
                    return user.equals(usuario);
                }
            };
            Objects<Object> usuarios = odb.getObjects(usuarioDeCampana);
            if(usuarios != null) {
                Usuario user = (Usuario) (usuarios.getFirst());
                HashMap<Long,Campana> ret = user.getCampanas();
                return ret;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            odb.close();
        }
        return new HashMap<Long,Campana>();
    }




}
