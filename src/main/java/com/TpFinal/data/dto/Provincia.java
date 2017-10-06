package com.TpFinal.data.dto;

import java.util.ArrayList;
import java.util.List;

public class Provincia {
    String nombre ="";
    List<Localidad> localidades=new ArrayList<>();

   public Provincia() {}
   
   public Provincia(String nombre){
       this.nombre=nombre;
   }
    
    public List<Localidad> getLocalidades() {
        return localidades;
    }

    public void addLocalidad(Localidad localidad) {
        this.localidades.add(localidad);
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Provincia provincia = (Provincia) o;

        return nombre != null ? nombre.equals(provincia.nombre) : provincia.nombre == null;
    }

    @Override
    public String toString() {
        return nombre;
    }

    @Override
    public int hashCode() {
        return nombre != null ? nombre.hashCode() : 0;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
