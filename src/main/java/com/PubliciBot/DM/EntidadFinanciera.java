package com.PubliciBot.DM;

/**
 * Created by Hugo on 14/05/2017.
 * Clase creada para soportar los distintas empresas: VISA, MASTERCARD, AMEX, (por el momento)
 */
public class EntidadFinanciera {
    private String nombreEntidad;
    private String descripcion;

    public EntidadFinanciera() {
        this.nombreEntidad = "";
        this.descripcion = "";
    }

    public EntidadFinanciera(String nombreEntidad, String descripcion) {
        this.nombreEntidad = nombreEntidad;
        this.descripcion = descripcion;
    }

    public String getNombreEntidad() {
        return nombreEntidad;
    }

    public void setNombreEntidad(String nombreEntidad) {
        this.nombreEntidad = nombreEntidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
