package com.TpFinal.data.dto;

public class Localidad {
   private String loc_nombre="";
    private Integer loc_cpostal;
    private String prv_nombre;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Localidad localidad = (Localidad) o;

        if (loc_nombre != null ? !loc_nombre.equals(localidad.loc_nombre) : localidad.loc_nombre != null) return false;
        if (loc_cpostal != null ? !loc_cpostal.equals(localidad.loc_cpostal) : localidad.loc_cpostal != null) return false;
        return prv_nombre != null ? prv_nombre.equals(localidad.prv_nombre) : localidad.prv_nombre == null;
    }

    @Override
    public int hashCode() {
        int result = loc_nombre != null ? loc_nombre.hashCode() : 0;
        result = 31 * result + (loc_cpostal != null ? loc_cpostal.hashCode() : 0);
        result = 31 * result + (prv_nombre != null ? prv_nombre.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return loc_nombre;
    }

    public Localidad(String nombre, Integer codPosta){
        this.loc_nombre=nombre;
        this.loc_cpostal=codPosta;
    }
    public String getNombre() {
        return loc_nombre;
    }

    public void setNombre(String nombre) {
        this.loc_nombre = nombre;
    }

    public Integer getCodPosta() {
        return loc_cpostal;
    }

    public void setCodPosta(Integer codPosta) {
        this.loc_cpostal = codPosta;
    }

    public String getProvincia() {
        return prv_nombre;
    }

    public void setProvincia(String provincia) {
        this.prv_nombre = provincia;
    }
}

