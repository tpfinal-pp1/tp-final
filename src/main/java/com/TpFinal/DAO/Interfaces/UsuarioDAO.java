package com.TpFinal.DAO.Interfaces;

import com.TpFinal.DM.Usuario;

/**
 * Created by Max on 6/4/2017.
 */
public interface UsuarioDAO extends DAO<Usuario> {

    Usuario recuperarUsuario(String mail, String contraseña);
    Usuario recuperarUsuario(String mail);
}
