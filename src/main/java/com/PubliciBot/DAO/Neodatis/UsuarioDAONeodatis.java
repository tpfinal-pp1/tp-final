package com.PubliciBot.DAO.Neodatis;

import com.PubliciBot.DAO.Interfaces.UsuarioDAO;
import com.PubliciBot.DM.*;
import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Max on 6/4/2017.
 */
public class UsuarioDAONeodatis extends DAONeodatis<Usuario> implements UsuarioDAO {



    @Override
    public void guardar(Usuario user){
        Usuario recovered = recuperarUsuario(user.getMail());
        if(recovered == null) {
            super.guardar(user);
        }
        else {
            Usuario nuevo = new Usuario(user.getMail(), user.getContrasena(), user.getRol());
            nuevo.getCampanas().putAll(user.getCampanas());
            ODB odb = null;
            try {

                odb = ODBFactory.open(fileNameNeodatisDB);
                IQuery usuarioCorrecto = new CriteriaQuery(Usuario.class, Where.equal("mail", user.getMail()));
                Objects<Usuario> usuarioRecuperado = odb.getObjects(usuarioCorrecto);
                recovered = usuarioRecuperado.getFirst();

                Collection campanas = recovered.getCampanas().values();
                if(campanas.size()>0) {
                    for (Object obj : campanas) {
                        Campana c = (Campana) obj;
                        ArrayList<AccionPublicitaria> acs = c.getAcciones();
                        if (acs.size() > 0) {
                            for (AccionPublicitaria ac : acs) {
                                /*
                                Medio medio = ac.getMedio();
                                TipoMedio tp = medio.getTipoMedio();
                                odb.delete(tp);
                                odb.delete(medio);
                                */
                                odb.delete(ac);
                            }
                        }
                        ArrayList<Post> posts = c.getPosts();
                        if(posts.size()>0) {
                            for (Post p : c.getPosts()) {
                                odb.delete(p);
                            }
                        }


                        odb.delete(c);
                    }
                }

                odb.delete(recovered);
                odb.store(nuevo);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                odb.close();
            }
        }
    }

    @Override
    public Usuario recuperarUsuario(String mail, String contraseña) {
        Usuario ret = null;
        ODB odb = null;
        try {
            odb = ODBFactory.open(fileNameNeodatisDB);
            IQuery usuarioCorrecto = new CriteriaQuery(Usuario.class, Where.and()
                    .add(Where.equal("mail", mail))
                    .add(Where.equal("contrasena", contraseña))
            );
            Objects<Usuario> usuarioRecuperado = odb.getObjects(usuarioCorrecto);
            if(usuarioRecuperado.size() > 0)
                ret = usuarioRecuperado.getFirst();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            odb.close();
        }
        return ret;
    }

    @Override
    public Usuario recuperarUsuario(String mail) {
        Usuario ret = null;
        ODB odb = null;
        try {
            odb = ODBFactory.open(fileNameNeodatisDB);
            IQuery usuarioCorrecto = new CriteriaQuery(Usuario.class, Where.equal("mail", mail));
            Objects<Usuario> usuarioRecuperado = odb.getObjects(usuarioCorrecto);
            if(usuarioRecuperado.size() > 0)
                ret = usuarioRecuperado.getFirst();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            odb.close();
        }
        return ret;
    }
}
