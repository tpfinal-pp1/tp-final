package com.PubliciBot.DM;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Hugo on 14/05/2017.
 */
public class Tag {
    private String nombre;
    private Tag TagPadre;
    private Set<AccionPublicitaria> acciones;

    public Tag(String nombre) {
        if(nombre.equals("")){
            throw new RuntimeException("Tag Vacio");
        }
        this.nombre = nombre;
        this.acciones = new HashSet<>();
    }

    public Tag(String nombre, Tag TagPadre) {
        if(TagPadre==this){
            throw new IllegalArgumentException("Padre no puede ser el mismo");
        }
        else {
            this.nombre = nombre;
            this.TagPadre = TagPadre;
            this.acciones = new HashSet<>();
        }
    }

    public String getNombre() {
        return nombre;
    }

    public Set<AccionPublicitaria> getAcciones() {
        return acciones;
    }

    public void setAcciones(Set<AccionPublicitaria> acciones) {
        this.acciones = acciones;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Tag getPadre() {
        return TagPadre;
    }

    private void setTagPadre(Tag TagPadre) {
        this.TagPadre = TagPadre;
    }
    @Override
    public String toString() {
        return this.nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tag tag = (Tag) o;

        if (nombre != null  && ((Tag) o).getNombre() != null)
            return nombre.equals(((Tag) o).getNombre());

       return false;
    }






}
