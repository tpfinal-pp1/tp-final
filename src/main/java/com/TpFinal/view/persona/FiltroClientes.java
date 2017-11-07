package com.TpFinal.view.persona;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import com.TpFinal.dto.persona.Empleado;
import com.TpFinal.dto.persona.Persona;

public class FiltroClientes {

    private List<Predicate<Persona>> filtros = new ArrayList<>();
    private Predicate<Persona> filtroCompuesto = p -> true;
    private Predicate<Persona> filtroNombre = p -> true;
    private Predicate<Persona> filtroApellido = p -> true;
    private Predicate<Persona> filtroDNI = p -> true;
    private Predicate<Persona> filtroCalificacion = p -> true;
    private Predicate<Persona> filtroRol = p -> true;

    private Predicate<Persona> filtroCustom = p -> true;

    public FiltroClientes() {
	filtroCompuesto = p -> true;
	filtroCustom = p -> true;
    }

    private void actualizarComposicion() {
	filtros.clear();
	filtros.addAll(Arrays.asList(this.filtroApellido, this.filtroDNI,
		this.filtroCalificacion, this.filtroNombre,
		this.filtroCustom, this.filtroRol));
	filtroCompuesto = filtros.stream().reduce(persona -> true, Predicate::and);
    }

    public List<Predicate<Persona>> getFiltros() {
	return filtros;
    }

    public Predicate<Persona> getFiltroCustom() {
	return filtroCustom;
    }

    public void setFiltroCustom(Predicate<Persona> filtroCustom) {
	this.filtroCustom = filtroCustom;
	actualizarComposicion();
    }

    public Predicate<Persona> getFiltroCompuesto() {
	return filtroCompuesto;
    }

    public void setFiltroCompuesto(Predicate<Persona> filtroCompuesto) {
	this.filtroCompuesto = filtroCompuesto;
	actualizarComposicion();
    }

    public List<Predicate<Persona>> getTodosLosFiltros() {
	return filtros;
    }

    public Predicate<Persona> getFiltroNombre() {
	return filtroNombre;
    }

    public void setFiltroNombre(Predicate<Persona> filtroNombre) {
	this.filtroNombre = filtroNombre;
	actualizarComposicion();
    }

    public Predicate<Persona> getFiltroApellido() {
        return filtroApellido;
    }

    public void setFiltroApellido(Predicate<Persona> filtroApellido) {
        this.filtroApellido = filtroApellido;
        actualizarComposicion();
    }

    public Predicate<Persona> getFiltroDNI() {
        return filtroDNI;
    }

    public void setFiltroDNI(Predicate<Persona> filtroDNI) {
        this.filtroDNI = filtroDNI;
        actualizarComposicion();
    }

    public Predicate<Persona> getFiltroCalificacion() {
        return filtroCalificacion;
    }

    public void setFiltroCalificacion(Predicate<Persona> filtroCalificacion) {
        this.filtroCalificacion = filtroCalificacion;
        actualizarComposicion();
    }

    public Predicate<Persona> getFiltroRol() {
        return filtroRol;
    }

    public void setFiltroRol(Predicate<Persona> filtroRol) {
        this.filtroRol = filtroRol;
        actualizarComposicion();
    }   

}
