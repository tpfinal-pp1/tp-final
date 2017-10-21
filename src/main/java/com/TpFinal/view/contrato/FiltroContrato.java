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
    private Predicate<Contrato> fechaCelebracion = contrato -> true;
    private Predicate<Contrato> estado = contrato -> true;
    private Predicate<Contrato> direccion = contrato -> true;
    private Predicate<Contrato> intervinientes = contrato -> true;
    private List<Predicate<Contrato>> filtros = new ArrayList<>();
    private Predicate<Contrato> filtroCompuesto;

    public FiltroContrato() {
	filtros.addAll(Arrays.asList(tipo, fechaCelebracion, estado, direccion, intervinientes));
	filtroCompuesto = contrato -> true;
    }

    public Predicate<Contrato> getFiltroCompuesto() {
	return filtroCompuesto;
    }

    private void actualizarComposicion() {
	filtros.clear();
	filtros.addAll(Arrays.asList(tipo, fechaCelebracion, estado, direccion, intervinientes));
	filtroCompuesto = filtros.stream().reduce(contrato -> true, Predicate::and);
    }

    public Predicate<Contrato> getTipo() {
	return tipo;
    }

    public void setTipo(Predicate<Contrato> tipo) {
	this.tipo = tipo;
	actualizarComposicion();
    }

    public Predicate<Contrato> getFechaCelebracion() {
	return fechaCelebracion;
    }

    public void setFechaCelebracion(Predicate<Contrato> fechaCelebracion) {
	this.fechaCelebracion = fechaCelebracion;
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

    public List<Predicate<Contrato>> getTodosLosFiltros() {
	return filtros;
    }

    public void setTodosLosFiltros(List<Predicate<Contrato>> todosLosFiltros) {
	this.filtros = todosLosFiltros;
	actualizarComposicion();
    }

}
