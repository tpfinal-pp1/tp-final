package com.PubliciBot.DM;

import java.util.HashSet;

/**
 * Created by Hugo on 14/05/2017.
 */
public class Rol {
    private String descripcion;
    private HashSet<Privilegio> listaPrivilegios;

    public Rol() {
        this.descripcion = "";
        this.listaPrivilegios = new HashSet<>() ;
    }

    public Rol(String descripcion) {
        this.descripcion = descripcion;
        this.listaPrivilegios = new HashSet<>() ;
    }

    public Rol(String descripcion, HashSet<Privilegio> listaPrivilegios) {
        this.descripcion = descripcion;
        this.listaPrivilegios = listaPrivilegios;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public HashSet<Privilegio> getListaPrivilegios() {
        return listaPrivilegios;
    }

    public void add(Privilegio privilegio) {
        this.listaPrivilegios.add(privilegio);
    }

    public void setListaPrivilegios(HashSet<Privilegio> listaPrivilegios) {
        this.listaPrivilegios = listaPrivilegios;
    }



    @Override
    public boolean equals(Object role){
        if(role == null) return false;
        if(this == role) return true;

        if(role instanceof Rol) {
            Rol other = (Rol) role;
            if (this.descripcion == null || other.descripcion == null)
                return false;
            if (this.listaPrivilegios == null || other.listaPrivilegios == null)
                return false;
            return  this.descripcion.equals(other.descripcion) &&
                    this.listaPrivilegios.equals(other.listaPrivilegios);
        }
        return false;
    }

    @Override
    public String toString(){
        return this.descripcion+
                "\nPermisos: "+ listaPrivilegios.toString();
    }
}
