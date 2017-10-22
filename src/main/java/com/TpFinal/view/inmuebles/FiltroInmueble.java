package com.TpFinal.view.inmuebles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import com.TpFinal.dto.inmueble.Inmueble;

public class FiltroInmueble {
	private Predicate<Inmueble> direccion = contrato -> true;
	private Predicate<Inmueble> propietario = contrato -> true;
	private Predicate<Inmueble> tipoInmueble = contrato -> true;
	private Predicate<Inmueble> estadoInmueble = contrato -> true;
	private List<Predicate<Inmueble>> filtros = new ArrayList<>();
	private Predicate<Inmueble> filtroCompuesto;

	public FiltroInmueble() {
		filtros.addAll(Arrays.asList(propietario, tipoInmueble, direccion, estadoInmueble));
		filtroCompuesto = contrato -> true;
	}

	public Predicate<Inmueble> getFiltroCompuesto() {
		return filtroCompuesto;
	}

	private void actualizarComposicion() {
		filtros.clear();
		filtros.addAll(Arrays.asList(propietario, tipoInmueble, direccion, estadoInmueble));
		filtroCompuesto = filtros.stream().reduce(contrato -> true, Predicate::and);
	}

	@Deprecated
	public void clearFiltro(Predicate<Inmueble> filtro) {
		filtro = contrato -> true;
	}

	public Predicate<Inmueble> getPropietario() {
		return propietario;
	}

	public void setPropietario(Predicate<Inmueble> propietario) {
		this.propietario = propietario;
		actualizarComposicion();
	}    

	public Predicate<Inmueble> getTipo() {
		return tipoInmueble;
	}

	public void setTipo(Predicate<Inmueble> tipo) {
		this.tipoInmueble = tipo;
		actualizarComposicion();
	}

	public Predicate<Inmueble> getDireccion() {
		return direccion;
	}

	public void setDireccion(Predicate<Inmueble> direccion) {
		this.direccion = direccion;
		actualizarComposicion();
	}

	public Predicate<Inmueble> getEstado() {
		return estadoInmueble;
	}

	public void setEstado(Predicate<Inmueble> estado) {
		this.estadoInmueble = estado;
		actualizarComposicion();
	}

	public List<Predicate<Inmueble>> getTodosLosFiltros() {
		return filtros;
	}

	public void setTodosLosFiltros(List<Predicate<Inmueble>> todosLosFiltros) {
		this.filtros = todosLosFiltros;
		actualizarComposicion();
	}

}
