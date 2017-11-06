package com.TpFinal.view.reportes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.util.Locale;

import com.TpFinal.dto.cobro.Cobro;
import com.TpFinal.dto.inmueble.TipoMoneda;
import com.TpFinal.dto.persona.Inquilino;

public class ItemRepAlquileresACobrar {
    // Inquilino - Fecha de Vencimiento - Monto - Fecha de Cobro
    private Integer anio;
    private String mes;
    private String fechaVencimiento;
    private BigDecimal monto;
    private String nombre;
    private String apellido;
    private Integer numeroMes;
    private String nombreYApellido;
    private String estadoCobro;
    private BigDecimal montoConIntereses;
    private BigDecimal gananciaImboliaria;
    private String tipoMonedaString;
    
    private BigDecimal gananciaDolares;

    public ItemRepAlquileresACobrar(Inquilino i, Cobro c, TipoMoneda tipoMoneda) {
	LocalDate fecha = c.getFechaDeVencimiento();
	anio = fecha.getYear();
	mes = formatearMes(fecha.getMonth().getDisplayName(TextStyle.FULL, Locale.forLanguageTag("es-AR")));
	numeroMes = fecha.getMonthValue();
	fechaVencimiento = fecha.format(new DateTimeFormatterBuilder().appendPattern("dd/MM/YYYY").toFormatter());
	monto = c.getMontoOriginal();
	nombre = i.getPersona().getNombre();
	apellido = i.getPersona().getApellido();
	nombreYApellido = nombre + " " + apellido;
	gananciaImboliaria = c.getComision();
	montoConIntereses = c.getMontoOriginal().add(c.getInteres());
	estadoCobro = c.getEstadoCobroString();
	tipoMonedaString = TipoMoneda.getSimbolo(tipoMoneda);
	
	gananciaDolares = c.getMontoOriginal().add(c.getInteres());
    }

    public Integer getAnio() {
	return anio;
    }

    public Integer getNumeroMes() {
        return numeroMes;
    }

    public void setNumeroMes(Integer numeroMes) {
        this.numeroMes = numeroMes;
    }

    public void setAnio(Integer anio) {
	this.anio = anio;
    }

    public String getMes() {
	return mes;
    }

    public void setMes(String mes) {
	this.mes = mes;
    }

    public String getFechaVencimiento() {
	return fechaVencimiento;
    }

    public LocalDate getFechaVencimientoEnDate() {


	return LocalDate.parse(fechaVencimiento, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }


    public void setFechaVencimiento(String fechaVencimiento) {
	this.fechaVencimiento = fechaVencimiento;
    }

    public BigDecimal getMonto() {
	return monto;
    }

    public void setMonto(BigDecimal monto) {
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
    
    public String getNombreYApellido() {
		return nombreYApellido;
	}

	public void setNombreYApellido(String nombreYApellido) {
		this.nombreYApellido = nombreYApellido;
	}

	public String getEstadoCobro() {
		return estadoCobro;
	}

	public void setEstadoCobro(String estadoCobro) {
		this.estadoCobro = estadoCobro;
	}

	public BigDecimal getMontoConIntereses() {
		return montoConIntereses;
	}

	public void setMontoConIntereses(BigDecimal montoConIntereses) {
		this.montoConIntereses = montoConIntereses;
	}

	public BigDecimal getGananciaImboliaria() {
		return gananciaImboliaria;
	}

	public void setGananciaImboliaria(BigDecimal gananciaImboliaria) {
		this.gananciaImboliaria = gananciaImboliaria;
	}
	
	public String getTipoMonedaString() {
		return tipoMonedaString;
	}

	public void setTipoMonedaString(String tipoMonedaString) {
		this.tipoMonedaString = tipoMonedaString;
	}


	public BigDecimal getGananciaDolares() {
		return gananciaDolares;
	}

	public void setGananciaDolares(BigDecimal gananciaDolares) {
		this.gananciaDolares = gananciaDolares;
	}

	public String formatearMes (String mes) {
		
		String mesFormateado = ""; 
		
		mesFormateado = mes.toUpperCase().substring(0, 1) + mes.toLowerCase().substring(1);
		
		return mesFormateado;
	}

	@Override
    public String toString() {
	return "" + anio + " - " + mes + " - fecha venc.: " + fechaVencimiento
		+ " - monto: " + monto + " - inquilino: " + nombre + " " + apellido;
    }

}
