package com.TpFinal.view.reportes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.util.Locale;

import com.TpFinal.dto.cobro.Cobro;
import com.TpFinal.dto.contrato.ContratoAlquiler;
import com.TpFinal.dto.inmueble.TipoMoneda;
import com.TpFinal.dto.persona.Inquilino;


public class ItemRepAlquileresACobrar {
    // Inquilino - Fecha de Vencimiento - Monto - Fecha de Cobro
    private Integer anio;
    private String mes;
    private String fechaVencimiento;
    private String monto;
    private String nombre;
    private String apellido;

    public ItemRepAlquileresACobrar(Inquilino i, Cobro c, TipoMoneda tipoMoneda) {
	LocalDate fecha = c.getFechaDeVencimiento();
	anio = fecha.getYear();
	mes = fecha.getMonth().getDisplayName(TextStyle.FULL, Locale.forLanguageTag("es-AR"));
	fechaVencimiento = fecha.format(new DateTimeFormatterBuilder().appendPattern("dd/MM/YYYY").toFormatter());
	monto = TipoMoneda.getSimbolo(tipoMoneda) + " " + c.getMontoRecibido().toString();
	nombre = i.getPersona().getNombre();
	apellido = i.getPersona().getApellido();
    }

    public Integer getAnio() {
	return anio;
    }

    public void setAnio(Integer anio) {
	this.anio = anio;
    }

    public String getMes() {
	return mes;
    }
    
    public int getMesNumero() {
	return getFechaVencimientoEnDate().getMonthValue();
    }

    public void setMes(String mes) {
	this.mes = mes;
    }

    public String getFechaVencimiento() {
	return fechaVencimiento;
    }

    public LocalDate getFechaVencimientoEnDate() {
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
	formatter = formatter.withLocale(Locale.forLanguageTag("es-AR"));
	return LocalDate.parse(fechaVencimiento, formatter);
    }
    
    

    public void setFechaVencimiento(String fechaVencimiento) {
	this.fechaVencimiento = fechaVencimiento;
    }

    public String getMonto() {
	return monto;
    }

    public void setMonto(String monto) {
	this.monto = monto;
    }

    public String getNombre() {
	return nombre;
    }

    public void setNombre(String nombre) {
	this.nombre = nombre;
    }

    public String getApellido() {
	return apellido;
    }

    public void setApellido(String apellido) {
	this.apellido = apellido;
    }

    @Override
    public String toString() {
	return "" + anio + " - " + mes + " - fecha venc.: " + fechaVencimiento
		+ " - monto: " + monto + " - inquilino: " + nombre + " " + apellido;
    }
    
    

}
