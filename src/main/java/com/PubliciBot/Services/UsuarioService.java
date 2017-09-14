package com.PubliciBot.Services;

import com.PubliciBot.DAO.Interfaces.UsuarioDAO;
import com.PubliciBot.DAO.Neodatis.UsuarioDAONeodatis;
import com.PubliciBot.DM.Campana;
import com.PubliciBot.DM.Rol;
import com.PubliciBot.DM.Usuario;

/**
 * Created by Hugo on 22/05/2017.
 */
public class UsuarioService {

    private UsuarioDAO usuarioDao;

    public UsuarioService(){
        usuarioDao = new UsuarioDAONeodatis();
    }

    public boolean tienePrivilegio(Usuario user,Class<?> privilegio){
        RolService rls = new RolService();
        return rls.tienePrivilegio(privilegio,user.getRol());
    }

    public Usuario iniciarSesion(String email, String password)
    {
        return null;
    }

    public Usuario crearUsuario(String email, String password, Rol rol)
    {
        return new Usuario(email,password,rol);
    }

    public void asignarRol(Usuario usuario, Rol rol)
    {
        usuario.setRol(rol);
    }

    public Usuario buscarUsuario(String mail,String contraseña) {
        return this.usuarioDao.recuperarUsuario(mail,contraseña);
    }

    public void agregarCampañaAUsuario(Campana campana, Usuario user){
        if(campana!=null){
        Long lon = new Long(user.getCampanas().size());
        if(campana.getId() == null){
            campana.setId(lon++);
        }
        user.getCampanas().put(campana.getId(),campana);}
    }

    public void eliminarCampaña(Campana campana, Usuario actual){
        actual.getCampanas().remove(campana);
    }

    public void guardarUsuario(Usuario user){
        this.usuarioDao.guardar(user);
    }

}
