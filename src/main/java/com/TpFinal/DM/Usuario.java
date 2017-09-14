package com.TpFinal.DM;

import java.util.HashMap;

/**
 * Created by Hugo on 19/05/2017.
 */

public class Usuario {
    private String mail;
    private String contrasena;
    private Rol rol;
    private HashMap<Long,Campana> campanas;

    public Usuario() {
        new Usuario("", "", new Rol());

    }

    public Usuario(String mail, String contrasena, Rol rol) {
        this.mail = mail;
        this.contrasena = contrasena;
        this.rol = rol;
        this.campanas = new HashMap<Long,Campana>();
    }


    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    @Override
    public boolean equals(Object user){
        if(this == user) return true;
        if(user == null) return false;
        if(this.mail == null || this.contrasena == null) return false;
        if(user instanceof  Usuario) {
            Usuario otherUser = (Usuario) user;
            if(otherUser.mail == null || otherUser.contrasena == null)
                return  false;
            return this.mail.equals(otherUser.mail);
        }
        return false;
    }

    @Override
    public String toString(){
        return "Usuario:"     +
                "\nMail: "    +this.mail+
                "\nRol: "     +this.rol.toString();
    }

    public HashMap<Long,Campana> getCampanas() {
        return campanas;
    }

    public void setCampanas(HashMap<Long,Campana> campanas) {
        this.campanas = campanas;
    }
}
