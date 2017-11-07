package com.TpFinal.view.duracionContratos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import com.TpFinal.dto.contrato.ContratoDuracion;
import com.TpFinal.dto.persona.Empleado;
import com.TpFinal.dto.persona.Persona;

public class FiltroDuracion {

    private List<Predicate<ContratoDuracion>> filtros = new ArrayList<>();
    private Predicate<ContratoDuracion> filtroCompuesto = p -> true;
    private Predicate<ContratoDuracion> filtroDescripcion = p -> true;
    private Predicate<ContratoDuracion> filtroDuracion = p -> true;
    private Predicate<ContratoDuracion> filtroCustom = p -> true;

    public FiltroDuracion() {
	filtroCompuesto = p -> true;
	filtroCustom = p -> true;
    }

    private void actualizarComposicion() {
	filtros.clear();
	filtros.addAll(Arrays.asList(filtroDescripcion, filtroDuracion, filtroCustom));
	filtroCompuesto = filtros.stream().reduce(filtro -> true, Predicate::and);
    }

    public List<Predicate<ContratoDuracion>> getFiltros() {
	return filtros;
    }

    public Predicate<ContratoDuracion> getFiltroCustom() {
	return filtroCustom;
    }

    public void setFiltroCustom(Predicate<ContratoDuracion> filtroCustom) {
	this.filtroCustom = filtroCustom;
	actualizarComposicion();
    }

    public Predicate<ContratoDuracion> getFiltroCompuesto() {
	return filtroCompuesto;
    }

    public void setFiltroCompuesto(Predicate<ContratoDuracion> filtroCompuesto) {
	this.filtroCompuesto = filtroCompuesto;
	actualizarComposicion();
    }

    public List<Predicate<ContratoDuracion>> getTodosLosFiltros() {
	return filtros;
    }

    public Predicate<ContratoDuracion> getFiltroDescripcion() {
	return filtroDescripcion;
    }

    public void setFiltroDescripcion(Predicate<ContratoDuracion> filtroDescripcion) {
	this.filtroDescripcion = filtroDescripcion;
	actualizarComposicion();
    }

    public Predicate<ContratoDuracion> getFiltroDuracion() {
	return filtroDuracion;
    }

    public void setFiltroDuracion(Predicate<ContratoDuracion> filtroDuracion) {
	this.filtroDuracion = filtroDuracion;
	actualizarComposicion();
    }

}
