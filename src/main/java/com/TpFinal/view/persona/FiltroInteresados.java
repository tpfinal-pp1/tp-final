package com.TpFinal.view.persona;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import com.TpFinal.dto.inmueble.CriterioBusqInmueble;
import com.TpFinal.dto.inmueble.Inmueble;
import com.TpFinal.dto.persona.Persona;

public class FiltroInteresados {

    private Predicate<Persona> tipo = persona -> true;
    private Predicate<Persona> fechaDesde = persona -> true;
    private Predicate<Persona> fechaHasta = persona -> true;
    private Predicate<Persona> anio = persona -> true;
    private Predicate<Persona> mes = persona -> true;
    private Predicate<Persona> estado = persona -> true;
    private Predicate<Persona> direccion = persona -> true;
    private Predicate<Persona> intervinientes = persona -> true;
    private List<Predicate<Persona>> filtros = new ArrayList<>();
    private Predicate<Persona> filtroCompuesto;

    public FiltroInteresados(Inmueble i) {

	// XXX hacer todo esto, sino poner todos los filtros a true.

	Predicate<Persona> aEstrenar = p -> {
	    if (p.getPrefBusqueda() != null) {
		if (p.getPrefBusqueda().getaEstrenar() != null)
		    return p.getPrefBusqueda().getaEstrenar().equals(i.getaEstrenar());
		else
		    return true;
	    } else {
		return false;
	    }
	};

	Predicate<Persona> clasesDeInmueble = p -> {
	    if (p.getPrefBusqueda() != null) {
		if (p.getPrefBusqueda().getClasesDeInmueble() != null) {
		    return p.getPrefBusqueda().getClasesDeInmueble().contains(i.getClaseInmueble());
		} else {
		    return true;
		}
	    } else {
		return false;
	    }
	};

	Predicate<Persona> conAireAcondicionado = p -> {
	    if (p.getPrefBusqueda() != null) {
		if (p.getPrefBusqueda().getConAireAcondicionado() != null) {
		    return p.getPrefBusqueda().getConAireAcondicionado().equals(i.getConAireAcondicionado());
		} else {
		    return true;
		}
	    } else {
		return false;
	    }
	};
	
	Predicate<Persona> conJardin = p -> {
	    if (p.getPrefBusqueda() != null) {
		if (p.getPrefBusqueda().getConJardin() != null) {
		    return p.getPrefBusqueda().getConJardin().equals(i.getConJardin());
		} else {
		    return true;
		}
	    } else {
		return false;
	    }
	};
	
	Predicate<Persona> conPileta = p -> {
	    if (p.getPrefBusqueda() != null) {
		if (p.getPrefBusqueda().getConPileta() != null) {
		    return p.getPrefBusqueda().getConPileta().equals(i.getConPileta());
		} else {
		    return true;
		}
	    } else {
		return false;
	    }
	};
	
	Predicate<Persona> estadoInmueble = p -> {
	    if (p.getPrefBusqueda() != null) {
		if (p.getPrefBusqueda().getEstadoInmueble() != null) {
		    return p.getPrefBusqueda().getEstadoInmueble().equals(i.getEstadoInmueble());
		} else {
		    return true;
		}
	    } else {
		return false;
	    }
	};
	
	Predicate<Persona> localidad = p -> {
	    if (p.getPrefBusqueda() != null) {
		if (p.getPrefBusqueda().getLocalidad() != null) {
		    return p.getPrefBusqueda().getLocalidad().equals(i.getDireccion().getLocalidad());
		} else {
		    return true;
		}
	    } else {
		return false;
	    }
	};
	
	

	/// XXX agregar el else para pref nula.

	filtros.addAll(Arrays.asList(tipo, fechaDesde, fechaHasta, anio, mes, estado, direccion, intervinientes));
	filtroCompuesto = persona -> true;
    }

    public Predicate<Persona> getFiltroCompuesto() {
	return filtroCompuesto;
    }

    private void actualizarComposicion() {
	filtros.clear();
	filtros.addAll(Arrays.asList(tipo, fechaDesde, fechaHasta, anio, mes, estado, direccion, intervinientes));
	filtroCompuesto = filtros.stream().reduce(persona -> true, Predicate::and);
    }

    public void clearFiltro(Predicate<Persona> filtro) {
	filtro = persona -> true;
    }

    public Predicate<Persona> getTipo() {
	return tipo;
    }

    public void setTipo(Predicate<Persona> tipo) {
	this.tipo = tipo;
	actualizarComposicion();
    }

    public Predicate<Persona> getFechaDesde() {
	return fechaDesde;
    }

    public void setFechaDesde(Predicate<Persona> fechaDesde) {
	this.fechaDesde = fechaDesde;
	actualizarComposicion();
    }

    public Predicate<Persona> getFechaHasta() {
	return fechaHasta;
    }

    public void setFechaHasta(Predicate<Persona> fechaHasta) {
	this.fechaHasta = fechaHasta;
	actualizarComposicion();
    }

    public Predicate<Persona> getAnio() {
	return anio;
    }

    public void setAnio(Predicate<Persona> anio) {
	this.anio = anio;
	actualizarComposicion();
    }

    public Predicate<Persona> getMes() {
	return mes;
    }

    public void setMes(Predicate<Persona> mes) {
	this.mes = mes;
	actualizarComposicion();
    }

    public Predicate<Persona> getEstado() {
	return estado;
    }

    public void setEstado(Predicate<Persona> estado) {
	this.estado = estado;
	actualizarComposicion();
    }

    public Predicate<Persona> getDireccion() {
	return direccion;
    }

    public void setDireccion(Predicate<Persona> direccion) {
	this.direccion = direccion;
	actualizarComposicion();
    }

    public Predicate<Persona> getIntervinientes() {
	return intervinientes;
    }

    public void setIntervinientes(Predicate<Persona> intervinientes) {
	this.intervinientes = intervinientes;
	actualizarComposicion();
    }

    public List<Predicate<Persona>> getTodosLosFiltros() {
	return filtros;
    }

    public void setTodosLosFiltros(List<Predicate<Persona>> todosLosFiltros) {
	this.filtros = todosLosFiltros;
	actualizarComposicion();
    }

}
