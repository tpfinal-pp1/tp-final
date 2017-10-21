package com.TpFinal.view.contrato;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import com.TpFinal.dto.contrato.Contrato;

public class FiltroContrato {
    public static enum tipo {
	Alquiler, Venta
    };

    private Predicate<Contrato> tipo = contrato -> true;
    private Predicate<Contrato> fechaDesde = contrato -> true;
    private Predicate<Contrato> fechaHasta = contrato -> true;
    private Predicate<Contrato> anio = contrato -> true;
    private Predicate<Contrato> mes = contrato -> true;
    private Predicate<Contrato> estado = contrato -> true;
    private Predicate<Contrato> direccion = contrato -> true;
    private Predicate<Contrato> intervinientes = contrato -> true;
    private List<Predicate<Contrato>> filtros = new ArrayList<>();
    private Predicate<Contrato> filtroCompuesto;

    public FiltroContrato() {
	filtros.addAll(Arrays.asList(tipo, fechaDesde, fechaHasta, anio, mes, estado, direccion, intervinientes));
	filtroCompuesto = contrato -> true;
    }

    public Predicate<Contrato> getFiltroCompuesto() {
	return filtroCompuesto;
    }

    private void actualizarComposicion() {
	filtros.clear();
	filtros.addAll(Arrays.asList(tipo, fechaDesde, fechaHasta, anio, mes, estado, direccion, intervinientes));
	filtroCompuesto = filtros.stream().reduce(contrato -> true, Predicate::and);
    }

    public void clearFiltro(Predicate<Contrato> filtro) {
	filtro = contrato -> true;
    }

    public Predicate<Contrato> getTipo() {
	return tipo;
    }

    public void setTipo(Predicate<Contrato> tipo) {
	this.tipo = tipo;
	actualizarComposicion();
    }    

    public Predicate<Contrato> getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Predicate<Contrato> fechaDesde) {
        this.fechaDesde = fechaDesde;
        actualizarComposicion();
    }

    public Predicate<Contrato> getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Predicate<Contrato> fechaHasta) {
        this.fechaHasta = fechaHasta;
        actualizarComposicion();
    }

    public Predicate<Contrato> getAnio() {
        return anio;
    }

    public void setAnio(Predicate<Contrato> anio) {
        this.anio = anio;
        actualizarComposicion();
    }

    public Predicate<Contrato> getMes() {
        return mes;
    }

    public void setMes(Predicate<Contrato> mes) {
        this.mes = mes;
        actualizarComposicion();
    }

    public Predicate<Contrato> getEstado() {
	return estado;
    }

    public void setEstado(Predicate<Contrato> estado) {
	this.estado = estado;
	actualizarComposicion();
    }

    public Predicate<Contrato> getDireccion() {
	return direccion;
    }

    public void setDireccion(Predicate<Contrato> direccion) {
	this.direccion = direccion;
	actualizarComposicion();
    }

    public Predicate<Contrato> getIntervinientes() {
        return intervinientes;
    }

    public void setIntervinientes(Predicate<Contrato> intervinientes) {
        this.intervinientes = intervinientes;
        actualizarComposicion();
    }

    public List<Predicate<Contrato>> getTodosLosFiltros() {
	return filtros;
    }

    public void setTodosLosFiltros(List<Predicate<Contrato>> todosLosFiltros) {
	this.filtros = todosLosFiltros;
	actualizarComposicion();
    }

}
