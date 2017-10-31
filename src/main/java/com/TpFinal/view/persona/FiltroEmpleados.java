package com.TpFinal.view.persona;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import com.TpFinal.dto.persona.Persona;

public class FiltroEmpleados {

    private List<Predicate<Persona>> filtros = new ArrayList<>();
    private Predicate<Persona> filtroCompuesto = p -> true;
    private Predicate<Persona> filtroCustom = p -> true;

    public FiltroEmpleados() {
	filtroCompuesto = p -> true;
	filtroCustom = p -> true;
    }

    private void actualizarComposicion() {
	filtros.clear();
	filtros.addAll(Arrays.asList(
		this.filtroCustom));
	filtroCompuesto = filtros.stream().reduce(contrato -> true, Predicate::and);
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

    public List<Predicate<Persona>> getTodosLosFiltros() {
	return filtros;
    }

}
