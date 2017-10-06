package com.TpFinal.data.dto;

public class Localidad {
    String nombre="";
    String codPosta="";
    Provincia provincia;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Localidad localidad = (Localidad) o;

        if (nombre != null ? !nombre.equals(localidad.nombre) : localidad.nombre != null) return false;
        if (codPosta != null ? !codPosta.equals(localidad.codPosta) : localidad.codPosta != null) return false;
        return provincia != null ? provincia.equals(localidad.provincia) : localidad.provincia == null;
    }

    @Override
    public int hashCode() {
        int result = nombre != null ? nombre.hashCode() : 0;
        result = 31 * result + (codPosta != null ? codPosta.hashCode() : 0);
        result = 31 * result + (provincia != null ? provincia.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return nombre;
    }

    public Localidad(String nombre, String codPosta){
        this.nombre=nombre;
        this.codPosta=codPosta;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodPosta() {
        return codPosta;
    }

    public void setCodPosta(String codPosta) {
        this.codPosta = codPosta;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }
}

