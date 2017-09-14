package com.TpFinal.DM;

import java.util.Date;

/**
 * Created by Hugo on 14/05/2017.
 */
public class Tarjeta {

    private TipoTarjeta tipo;
    private String codigo;
    private String titular;
    private Date fechaVencimiento;
    private int codigoSeguridad;
    private EntidadFinanciera entidadFinanciera;

    public Tarjeta() {
        this.tipo = null;
        this.codigo = "";
        this.titular = "";
        this.fechaVencimiento = new Date();
        this.codigoSeguridad = 0;
        this.entidadFinanciera = new EntidadFinanciera();
    }

    public Tarjeta(TipoTarjeta tipo, String codigo, String titular, Date fechaVencimiento, int codigoSeguridad, EntidadFinanciera entidadFinanciera) {
        this.tipo = tipo;
        this.codigo = codigo;
        this.titular = titular;
        this.fechaVencimiento = fechaVencimiento;
        this.codigoSeguridad = codigoSeguridad;
        this.entidadFinanciera = entidadFinanciera;
    }


    public TipoTarjeta getTipo() {
        return tipo;
    }

    public void setTipo(TipoTarjeta tipo) {
        this.tipo = tipo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public int getCodigoSeguridad() {
        return codigoSeguridad;
    }

    public void setCodigoSeguridad(int codigoSeguridad) {
        this.codigoSeguridad = codigoSeguridad;
    }


}
