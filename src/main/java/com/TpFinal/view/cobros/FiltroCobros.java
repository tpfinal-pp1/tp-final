package com.TpFinal.view.cobros;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import com.TpFinal.dto.cobro.Cobro;
import com.TpFinal.dto.inmueble.TipoMoneda;

public class FiltroCobros {

    private List<Predicate<Cobro>> filtros = new ArrayList<>();
    private Predicate<Cobro> filtroCompuesto = p -> true;
    private Predicate<Cobro> filtroInmueble = p -> true;
    private Predicate<Cobro> filtroFechaVencimientoDesde = p -> true;
    private Predicate<Cobro> filtroFechaVencimientoHasta = p -> true;
    private Predicate<Cobro> filtroFechaDePagoDesde = p -> true;
    private Predicate<Cobro> filtroFechaDePagoHasta = p -> true;
    private Predicate<Cobro> anioV = p -> true;
    private Predicate<Cobro> mesV = p -> true;
    private Predicate<Cobro> anioP = p -> true;
    private Predicate<Cobro> mesP = p -> true;
    private Predicate<Cobro> filtroInquilino = p -> true;
    private Predicate<Cobro> filtroMonto = p -> true;
    private Predicate<Cobro> filtroCustom = p -> true;
    private Predicate<Cobro> filtroTipoMoneda = c -> c.getContrato().getMoneda().equals(TipoMoneda.Pesos);
    private Predicate<Cobro> filtroTipo = p -> true;
    private Predicate<Cobro> filtroCuota = p -> true;

    public FiltroCobros() {
	filtroCompuesto = p -> true;
	filtroCustom = p -> true;
    }

    private void actualizarComposicion() {
	filtros.clear();
	filtros.addAll(Arrays.asList(filtroCuota, filtroTipo, filtroInmueble, filtroMonto, filtroCustom,
		filtroFechaVencimientoDesde,
		filtroFechaVencimientoHasta,
		filtroFechaDePagoDesde,
		filtroFechaDePagoHasta,
		anioV,
		mesV,
		anioP,
		mesP,
		filtroInquilino,
		filtroMonto,
		filtroTipoMoneda));
	filtroCompuesto = filtros.stream().reduce(filtro -> true, Predicate::and);
    }

    public List<Predicate<Cobro>> getFiltros() {
	return filtros;
    }

    public Predicate<Cobro> getFiltroCustom() {
	return filtroCustom;
    }

    public void setFiltroCustom(Predicate<Cobro> filtroCustom) {
	this.filtroCustom = filtroCustom;
	actualizarComposicion();
    }

    public Predicate<Cobro> getFiltroCompuesto() {
	return filtroCompuesto;
    }

    public void setFiltroCompuesto(Predicate<Cobro> filtroCompuesto) {
	this.filtroCompuesto = filtroCompuesto;
	actualizarComposicion();
    }

    public List<Predicate<Cobro>> getTodosLosFiltros() {
	return filtros;
    }

    public Predicate<Cobro> getFiltroInmueble() {
	return filtroInmueble;
    }

    public void setFiltroInmueble(Predicate<Cobro> filtroInmueble) {
	this.filtroInmueble = filtroInmueble;
	actualizarComposicion();
    }

    public Predicate<Cobro> getFiltroInquilino() {
	return filtroInquilino;
    }

    public void setFiltroInquilino(Predicate<Cobro> filtroInquilino) {
	this.filtroInquilino = filtroInquilino;
	actualizarComposicion();
    }

    public Predicate<Cobro> getFiltroMonto() {
	return filtroMonto;
    }

    public void setFiltroMonto(Predicate<Cobro> filtroMonto) {
	this.filtroMonto = filtroMonto;
	actualizarComposicion();
    }

    public Predicate<Cobro> getFiltroFechaVencimientoHasta() {
	return filtroFechaVencimientoHasta;
    }

    public void setFiltroFechaVencimientoHasta(Predicate<Cobro> filtroFechaVencimientoHasta) {
	this.filtroFechaVencimientoHasta = filtroFechaVencimientoHasta;
	actualizarComposicion();
    }

    public Predicate<Cobro> getFiltroFechaVencimientoDesde() {
	return filtroFechaVencimientoDesde;
    }

    public void setFiltroFechaVencimientoDesde(Predicate<Cobro> filtroFechaVencimientoDesde) {
	this.filtroFechaVencimientoDesde = filtroFechaVencimientoDesde;
	actualizarComposicion();
    }

    public Predicate<Cobro> getFiltroFechaDePagoDesde() {
	return filtroFechaDePagoDesde;
    }

    public void setFiltroFechaDePagoDesde(Predicate<Cobro> filtroFechaDePagoDesde) {
	this.filtroFechaDePagoDesde = filtroFechaDePagoDesde;
	actualizarComposicion();
    }

    public Predicate<Cobro> getFiltroFechaDePagoHasta() {
	return filtroFechaDePagoHasta;
    }

    public void setFiltroFechaDePagoHasta(Predicate<Cobro> filtroFechaDePagoHasta) {
	this.filtroFechaDePagoHasta = filtroFechaDePagoHasta;
	actualizarComposicion();
    }

    public Predicate<Cobro> getAnioV() {
	return anioV;
    }

    public void setAnioV(Predicate<Cobro> anio) {
	this.anioV = anio;
	actualizarComposicion();
    }

    public Predicate<Cobro> getMesV() {
	return mesV;
    }

    public void setMesV(Predicate<Cobro> mes) {
	this.mesV = mes;
	actualizarComposicion();
    }

    public Predicate<Cobro> getAnioP() {
	return anioP;
    }

    public void setAnioP(Predicate<Cobro> anioP) {
	this.anioP = anioP;
	actualizarComposicion();
    }

    public Predicate<Cobro> getMesP() {
	return mesP;
    }

    public void setMesP(Predicate<Cobro> mesP) {
	this.mesP = mesP;
	actualizarComposicion();
    }

    public void setTipoMoneda(TipoMoneda tipoMoneda) {
	filtroTipoMoneda = c -> c.getContrato().getMoneda().equals(tipoMoneda);
	actualizarComposicion();
    }

    public void setfiltroTipo(Predicate<Cobro> p) {
	this.filtroTipo = p;
	actualizarComposicion();
    }

    public Predicate<Cobro> getFiltroTipo() {
	return filtroTipo;
    }

    public Predicate<Cobro> getFiltroCuota() {
	return filtroCuota;
    }

    public void setFiltroCuota(Predicate<Cobro> p) {
	this.filtroCuota = p;
	actualizarComposicion();

    }

}
