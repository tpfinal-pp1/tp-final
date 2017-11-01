package com.TpFinal.view.reportes;

import java.math.BigDecimal;

public class ItemRepAlquileresPorMes {

	BigDecimal gananciaInmobiliariaPagosCobrados;
	BigDecimal gananciaInmobiliariaTodosLosCobros;
	BigDecimal ingresosTotalesPagosCobrados;
	BigDecimal ingresosTotalesPagosPendientes;
	int cantidadPagosCobrados;
	int cantidadPagosPendientes;
	
	public ItemRepAlquileresPorMes(BigDecimal gananciaInmobiliariaPagosCobrados,
	BigDecimal gananciaInmobiliariaTodosLosCobros,
	BigDecimal ingresosTotalesPagosCobrados,
	BigDecimal ingresosTotalesPagosPendientes,
	int cantidadPagosCobrados,
	int cantidadPagosPendientes) {
		
		this.gananciaInmobiliariaPagosCobrados = gananciaInmobiliariaPagosCobrados;
		this.gananciaInmobiliariaTodosLosCobros = gananciaInmobiliariaTodosLosCobros;
		this.ingresosTotalesPagosCobrados = ingresosTotalesPagosCobrados;
		this.ingresosTotalesPagosPendientes = ingresosTotalesPagosPendientes;
		this.cantidadPagosCobrados = cantidadPagosCobrados;
		this.cantidadPagosPendientes = cantidadPagosPendientes;
	}
	
	public ItemRepAlquileresPorMes() {
		this.gananciaInmobiliariaPagosCobrados = BigDecimal.ZERO;
		this.gananciaInmobiliariaTodosLosCobros = BigDecimal.ZERO;
		this.ingresosTotalesPagosCobrados = BigDecimal.ZERO;
		this.ingresosTotalesPagosPendientes  = BigDecimal.ZERO;
		this.cantidadPagosCobrados = 0;
		this.cantidadPagosPendientes = 0;
	}

	public BigDecimal getGananciaInmobiliariaPagosCobrados() {
		return gananciaInmobiliariaPagosCobrados;
	}

	public void setGananciaInmobiliariaPagosCobrados(BigDecimal gananciaInmobiliariaPagosCobrados) {
		this.gananciaInmobiliariaPagosCobrados = gananciaInmobiliariaPagosCobrados;
	}

	public BigDecimal getGananciaInmobiliariaTodosLosCobros() {
		return gananciaInmobiliariaTodosLosCobros;
	}

	public void setGananciaInmobiliariaTodosLosCobros(BigDecimal gananciaInmobiliariaTodosLosCobros) {
		this.gananciaInmobiliariaTodosLosCobros = gananciaInmobiliariaTodosLosCobros;
	}

	public BigDecimal getIngresosTotalesPagosCobrados() {
		return ingresosTotalesPagosCobrados;
	}

	public void setIngresosTotalesPagosCobrados(BigDecimal ingresosTotalesPagosCobrados) {
		this.ingresosTotalesPagosCobrados = ingresosTotalesPagosCobrados;
	}

	public BigDecimal getIngresosTotalesPagosPendientes() {
		return ingresosTotalesPagosPendientes;
	}

	public void setIngresosTotalesPagosPendientes(BigDecimal ingresosTotalesPagosPendientes) {
		this.ingresosTotalesPagosPendientes = ingresosTotalesPagosPendientes;
	}

	public int getCantidadPagosCobrados() {
		return cantidadPagosCobrados;
	}

	public void setCantidadPagosCobrados(int cantidadPagosCobrados) {
		this.cantidadPagosCobrados = cantidadPagosCobrados;
	}

	public int getCantidadPagosPendientes() {
		return cantidadPagosPendientes;
	}

	public void setCantidadPagosPendientes(int cantidadPagosPendientes) {
		this.cantidadPagosPendientes = cantidadPagosPendientes;
	}
	
	
	
	
	
}
