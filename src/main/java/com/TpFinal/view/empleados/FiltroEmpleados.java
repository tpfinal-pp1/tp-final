package com.TpFinal.view.empleados;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import com.TpFinal.dto.persona.Empleado;

public class FiltroEmpleados {

    private List<Predicate<Empleado>> filtros = new ArrayList<>();
    private Predicate<Empleado> filtroCompuesto = p -> true;
    private Predicate<Empleado> filtroNombre = p -> true;
    private Predicate<Empleado> filtroApellido = p -> true;
    private Predicate<Empleado> filtroEmail = p -> true;
    private Predicate<Empleado> filtroCategoria = p -> true;
    private Predicate<Empleado> filtroAcceso = p -> true;
    private Predicate<Empleado> filtroTelefono = p -> true;
    private Predicate<Empleado> filtroEstadoEmpleado = p -> true;

    private Predicate<Empleado> filtroCustom = p -> true;

    public FiltroEmpleados() {
	filtroCompuesto = p -> true;
	filtroCustom = p -> true;
    }

    private void actualizarComposicion() {
	filtros.clear();
	filtros.addAll(Arrays.asList(this.filtroApellido, this.filtroCategoria, this.filtroEmail,
		this.filtroEstadoEmpleado, this.filtroNombre,
		this.filtroCustom, this.filtroAcceso, this.filtroTelefono));
	filtroCompuesto = filtros.stream().reduce(contrato -> true, Predicate::and);
    }

    public List<Predicate<Empleado>> getFiltros() {
	return filtros;
    }

    public Predicate<Empleado> getFiltroCustom() {
	return filtroCustom;
    }

    public void setFiltroCustom(Predicate<Empleado> filtroCustom) {
	this.filtroCustom = filtroCustom;
	actualizarComposicion();
    }

    public Predicate<Empleado> getFiltroCompuesto() {
	return filtroCompuesto;
    }

    public List<Predicate<Empleado>> getTodosLosFiltros() {
	return filtros;
    }

    public Predicate<Empleado> getFiltroNombre() {
	return filtroNombre;
    }

    public void setFiltroNombre(Predicate<Empleado> filtroNombre) {
	this.filtroNombre = filtroNombre;
	actualizarComposicion();
    }

    public Predicate<Empleado> getFiltroTelefono() {
	return filtroTelefono;
    }

    public void setFiltroTelefono(Predicate<Empleado> filtroTelefono) {
	this.filtroTelefono = filtroTelefono;
	actualizarComposicion();
    }

    public Predicate<Empleado> getFiltroAcceso() {
	return filtroAcceso;
    }

    public void setFiltroAcceso(Predicate<Empleado> filtroAceso) {
	this.filtroAcceso = filtroAceso;
	actualizarComposicion();
    }

    public Predicate<Empleado> getFiltroApellido() {
	return filtroApellido;
    }

    public void setFiltroApellido(Predicate<Empleado> filtroApellido) {
	this.filtroApellido = filtroApellido;
	actualizarComposicion();
    }

    public Predicate<Empleado> getFiltroEmail() {
	return filtroEmail;
    }

    public void setFiltroEmail(Predicate<Empleado> filtroEmail) {
	this.filtroEmail = filtroEmail;
	actualizarComposicion();
    }

    public Predicate<Empleado> getFiltroCategoria() {
	return filtroCategoria;
    }

    public void setFiltroCategoria(Predicate<Empleado> filtroCategoria) {
	this.filtroCategoria = filtroCategoria;
	actualizarComposicion();
    }

    public Predicate<Empleado> getFiltroEstadoEmpleado() {
	return filtroEstadoEmpleado;
    }

    public void setFiltroEstadoEmpleado(Predicate<Empleado> filtroEstadoEmpleado) {
	this.filtroEstadoEmpleado = filtroEstadoEmpleado;
	actualizarComposicion();
    }

    public void setFiltroCompuesto(Predicate<Empleado> filtroCompuesto) {
	this.filtroCompuesto = filtroCompuesto;
	actualizarComposicion();
    }

}
