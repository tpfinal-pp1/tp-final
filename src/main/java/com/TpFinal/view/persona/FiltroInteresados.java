package com.TpFinal.view.persona;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.TpFinal.dto.inmueble.CriterioBusqInmueble;
import com.TpFinal.dto.inmueble.EstadoInmueble;
import com.TpFinal.dto.inmueble.Inmueble;
import com.TpFinal.dto.persona.Persona;
import com.TpFinal.dto.publicacion.Publicacion;
import com.TpFinal.dto.publicacion.PublicacionAlquiler;
import com.TpFinal.dto.publicacion.PublicacionVenta;
import com.TpFinal.services.InmuebleService;

public class FiltroInteresados {

    private List<Predicate<Persona>> filtros = new ArrayList<>();
    private Predicate<Persona> filtroCompuesto = p -> true;
    private Predicate<Persona> publicaciones = p -> true;
    private Predicate<Persona> tipoInmueble = p -> true;
    private Predicate<Persona> provincia = p -> true;
    private Predicate<Persona> maxSupTotal = p -> true;
    private Predicate<Persona> minSupTotal = p -> true;
    private Predicate<Persona> maxSupCubierta = p -> true;
    private Predicate<Persona> minSupCubierta = p -> true;
    private Predicate<Persona> maxDormitorios = p -> true;
    private Predicate<Persona> minDormitorios = p -> true;
    private Predicate<Persona> maxCocheras = p -> true;
    private Predicate<Persona> minCocheras = p -> true;
    private Predicate<Persona> maxAmbientes = p -> true;
    private Predicate<Persona> minAmbientes = p -> true;
    private Predicate<Persona> localidad = p -> true;
    private Predicate<Persona> estadoInmueble = p -> true;
    private Predicate<Persona> conPileta = p -> true;
    private Predicate<Persona> conJardin = p -> true;
    private Predicate<Persona> conParrilla = p -> true;
    private Predicate<Persona> conAireAcondicionado = p -> true;
    private Predicate<Persona> clasesDeInmueble = p -> true;
    private Predicate<Persona> aEstrenar = p -> true;
    private Predicate<Persona> filtroCustom = p -> true;
    private Predicate<Persona> filtroPersona = p -> true;

    public FiltroInteresados() {
	filtroCompuesto = p -> true;
	filtroCustom = p -> true;
    }

    public FiltroInteresados(Inmueble i) {
	aEstrenar = filtroAEstrenar(i);
	clasesDeInmueble = filtroClaseInmueble(i);
	conAireAcondicionado = filtroAireAcondicionado(i);
	conJardin = filtroConJardin(i);
	conPileta = filtroConPileta(i);
	conParrilla = filtroConParrilla(i);
	estadoInmueble = filtroEstadoInmueble(i);
	localidad = filtroLocalidad(i);
	minAmbientes = filtroMinAmbientes(i);
	maxAmbientes = filtroMaxAmbientes(i);
	minCocheras = filtroMinCocheras(i);
	maxCocheras = filtroMaxcocheras(i);
	minDormitorios = filtroMinDormitorios(i);
	maxDormitorios = filtroMaxDormitorios(i);
	minSupCubierta = filtroMinSupcubierta(i);
	maxSupCubierta = filtroMaxSupCubierta(i);
	minSupTotal = filtroMinSupTotal(i);
	maxSupTotal = filtroMaxSupTotal(i);
	provincia = filtroProvincia(i);
	tipoInmueble = filtroTipoInm(i);
	publicaciones = filtroPublicaciones(i);
	filtroCustom = p -> true;
	filtroPersona = p -> true;
	filtros.addAll(Arrays.asList(
		this.aEstrenar,
		this.clasesDeInmueble, // XXX ok
		this.conAireAcondicionado,
		this.conJardin,
		this.conPileta,
		this.conParrilla,
		this.estadoInmueble, // XXX ok
		this.localidad, // XXX ok
		this.provincia,
		this.tipoInmueble,
		this.maxAmbientes,
		this.minAmbientes, // XXX ok
		this.minCocheras,
		this.maxCocheras,
		this.minDormitorios,
		this.maxDormitorios,
		this.minSupCubierta,
		this.maxSupCubierta,
		this.minSupTotal,
		this.maxSupTotal,
		this.publicaciones,
		filtroCustom,
		filtroPersona));

	filtroCompuesto = filtros.stream().reduce(persona -> true, Predicate::and);
    }

    private void actualizarComposicion() {
	filtros.clear();
	filtros.addAll(Arrays.asList(
		this.aEstrenar,
		this.clasesDeInmueble,
		this.conAireAcondicionado,
		this.conJardin,
		this.conPileta,
		this.conParrilla,
		this.estadoInmueble,
		this.localidad,
		this.provincia,
		this.tipoInmueble,
		this.maxAmbientes,
		this.minAmbientes,
		this.minCocheras,
		this.maxCocheras,
		this.minDormitorios,
		this.maxDormitorios,
		this.minSupCubierta,
		this.maxSupCubierta,
		this.minSupTotal,
		this.maxSupTotal,
		this.publicaciones,
		this.filtroCustom,
		this.filtroPersona));
	filtroCompuesto = filtros.stream().reduce(contrato -> true, Predicate::and);
    }

    public List<Predicate<Persona>> getFiltros() {
	return filtros;
    }

    public Predicate<Persona> getPublicaciones() {
	return publicaciones;
    }

    public Predicate<Persona> getTipoInmueble() {
	return tipoInmueble;
    }

    public Predicate<Persona> getProvincia() {
	return provincia;
    }

    public Predicate<Persona> getMaxSupTotal() {
	return maxSupTotal;
    }

    public Predicate<Persona> getMinSupTotal() {
	return minSupTotal;
    }

    public Predicate<Persona> getMaxSupCubierta() {
	return maxSupCubierta;
    }

    public Predicate<Persona> getMinSupCubierta() {
	return minSupCubierta;
    }

    public Predicate<Persona> getMaxDormitorios() {
	return maxDormitorios;
    }

    public Predicate<Persona> getMinDormitorios() {
	return minDormitorios;
    }

    public Predicate<Persona> getMaxCocheras() {
	return maxCocheras;
    }

    public Predicate<Persona> getMinCocheras() {
	return minCocheras;
    }

    public Predicate<Persona> getMaxAmbientes() {
	return maxAmbientes;
    }

    public Predicate<Persona> getMinAmbientes() {
	return minAmbientes;
    }

    public Predicate<Persona> getLocalidad() {
	return localidad;
    }

    public Predicate<Persona> getEstadoInmueble() {
	return estadoInmueble;
    }

    public Predicate<Persona> getConPileta() {
	return conPileta;
    }

    public Predicate<Persona> getConJardin() {
	return conJardin;
    }

    public Predicate<Persona> getConParrilla() {
	return conParrilla;
    }

    public Predicate<Persona> getConAireAcondicionado() {
	return conAireAcondicionado;
    }

    public Predicate<Persona> getClasesDeInmueble() {
	return clasesDeInmueble;
    }

    public Predicate<Persona> getaEstrenar() {
	return aEstrenar;
    }

    public Predicate<Persona> getFiltroCustom() {
	return filtroCustom;
    }

    public Predicate<Persona> getFiltroPersona() {
	return filtroPersona;
    }

    public void setFiltroPersona(Predicate<Persona> filtroPersona) {
	this.filtroPersona = filtroPersona;
	actualizarComposicion();
    }

    public void setFiltroCustom(Predicate<Persona> filtroCustom) {
	this.filtroCustom = filtroCustom;
	actualizarComposicion();
    }

    // XXX
    private Predicate<Persona> filtroAEstrenar(Inmueble i) {
	return p -> {
	    if (p.getPrefBusqueda() != null) {
		if (p.getPrefBusqueda().getaEstrenar() != null)
		    return p.getPrefBusqueda().getaEstrenar().equals(i.getaEstrenar());
		else
		    return true;
	    } else {
		return false;
	    }
	};
    }

    // XXX
    private Predicate<Persona> filtroAireAcondicionado(Inmueble i) {
	return p -> {
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
    }

    // XXX
    private Predicate<Persona> filtroClaseInmueble(Inmueble i) {
	return p -> {
	    if (p.getPrefBusqueda() != null) {
		if (p.getPrefBusqueda().getClasesDeInmueble() != null && !p.getPrefBusqueda().getClasesDeInmueble()
			.isEmpty()) {
		    return p.getPrefBusqueda().getClasesDeInmueble().contains(i.getClaseInmueble());
		} else {
		    return true;
		}
	    } else {
		return false;
	    }
	};
    }

    // XXX
    private Predicate<Persona> filtroConJardin(Inmueble i) {
	return p -> {
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
    }

    // XXX
    private Predicate<Persona> filtroConPileta(Inmueble i) {
	return p -> {
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
    }

    // XXX
    private Predicate<Persona> filtroConParrilla(Inmueble i) {
	return p -> {
	    if (p.getPrefBusqueda() != null) {
		if (p.getPrefBusqueda().getConParrilla() != null) {
		    return p.getPrefBusqueda().getConParrilla().equals(i.getConParilla());
		} else {
		    return true;
		}
	    } else {
		return false;
	    }
	};
    }

    // XXX
    private Predicate<Persona> filtroEstadoInmueble(Inmueble i) {
	return p -> {
	    if (p.getPrefBusqueda() != null) {
		if (p.getPrefBusqueda().getEstadoInmueble() != null) {
		    if (p.getPrefBusqueda().getEstadoInmueble().equals(EstadoInmueble.EnAlquilerYVenta))
			return i.getEstadoInmueble() == EstadoInmueble.EnAlquilerYVenta ||
				i.getEstadoInmueble() == EstadoInmueble.EnAlquiler ||
				i.getEstadoInmueble() == EstadoInmueble.EnVenta;
		    else
			return p.getPrefBusqueda().getEstadoInmueble().equals(i.getEstadoInmueble())
				|| i.getEstadoInmueble() == EstadoInmueble.EnAlquilerYVenta;
		} else {
		    return true;
		}
	    } else {
		return false;
	    }
	};
    }

    // XXX
    private Predicate<Persona> filtroLocalidad(Inmueble i) {
	return p -> {
	    if (p.getPrefBusqueda() != null) {
		if (p.getPrefBusqueda().getLocalidad() != null) {
		    return p.getPrefBusqueda().getLocalidad().toLowerCase().equals(i.getDireccion().getLocalidad()
			    .toLowerCase());
		} else {
		    return true;
		}
	    } else {
		return false;
	    }
	};
    }

    // XXX
    private Predicate<Persona> filtroMaxAmbientes(Inmueble i) {
	return p -> {
	    if (p.getPrefBusqueda() != null) {
		if (p.getPrefBusqueda().getMaxCantAmbientes() != null) {
		    return p.getPrefBusqueda().getMaxCantAmbientes().compareTo(i.getCantidadAmbientes()) >= 0;
		} else {
		    return true;
		}
	    } else {
		return false;
	    }
	};
    }

    // XXX
    private Predicate<Persona> filtroMaxcocheras(Inmueble i) {
	return p -> {
	    if (p.getPrefBusqueda() != null) {
		if (p.getPrefBusqueda().getMaxCantCocheras() != null) {
		    return p.getPrefBusqueda().getMaxCantCocheras().compareTo(i.getCantidadCocheras()) >= 0;
		} else {
		    return true;
		}
	    } else {
		return false;
	    }
	};
    }

    // XXX
    private Predicate<Persona> filtroMaxDormitorios(Inmueble i) {
	return p -> {
	    if (p.getPrefBusqueda() != null) {
		if (p.getPrefBusqueda().getMaxCantDormitorios() != null) {
		    return p.getPrefBusqueda().getMaxCantDormitorios().compareTo(i.getCantidadDormitorios()) >= 0;
		} else {
		    return true;
		}
	    } else {
		return false;
	    }
	};
    }

    // XXX
    private Predicate<Persona> filtroMaxSupCubierta(Inmueble i) {
	return p -> {
	    if (p.getPrefBusqueda() != null) {
		if (p.getPrefBusqueda().getMaxSupCubierta() != null) {
		    return p.getPrefBusqueda().getMaxSupCubierta().compareTo(i.getSuperficieCubierta()) >= 0;
		} else {
		    return true;
		}
	    } else {
		return false;
	    }
	};
    }

    // XXX
    private Predicate<Persona> filtroMaxSupTotal(Inmueble i) {
	return p -> {
	    if (p.getPrefBusqueda() != null) {
		if (p.getPrefBusqueda().getMaxSupTotal() != null) {
		    return p.getPrefBusqueda().getMaxSupTotal().compareTo(i.getSuperficieTotal()) >= 0;
		} else {
		    return true;
		}
	    } else {
		return false;
	    }
	};
    }

    // XXX
    private Predicate<Persona> filtroMinAmbientes(Inmueble i) {
	return p -> {
	    if (p.getPrefBusqueda() != null) {
		if (p.getPrefBusqueda().getMinCantAmbientes() != null) {
		    return p.getPrefBusqueda().getMinCantAmbientes().compareTo(i.getCantidadAmbientes()) <= 0;
		} else {
		    return true;
		}
	    } else {
		return false;
	    }
	};
    }

    // XXX
    private Predicate<Persona> filtroMinCocheras(Inmueble i) {
	return p -> {
	    if (p.getPrefBusqueda() != null) {
		if (p.getPrefBusqueda().getMinCantCocheras() != null) {
		    return p.getPrefBusqueda().getMinCantCocheras().compareTo(i.getCantidadCocheras()) <= 0;
		} else {
		    return true;
		}
	    } else {
		return false;
	    }
	};
    }

    // XXX
    private Predicate<Persona> filtroMinDormitorios(Inmueble i) {
	return p -> {
	    if (p.getPrefBusqueda() != null) {
		if (p.getPrefBusqueda().getMinCantDormitorios() != null) {
		    return p.getPrefBusqueda().getMinCantDormitorios().compareTo(i.getCantidadDormitorios()) <= 0;
		} else {
		    return true;
		}
	    } else {
		return false;
	    }
	};
    }

    // XXX
    private Predicate<Persona> filtroMinSupcubierta(Inmueble i) {
	return p -> {
	    if (p.getPrefBusqueda() != null) {
		if (p.getPrefBusqueda().getMinSupCubierta() != null) {
		    return p.getPrefBusqueda().getMinSupCubierta().compareTo(i.getSuperficieCubierta()) <= 0;
		} else {
		    return true;
		}
	    } else {
		return false;
	    }
	};
    }

    // XXX
    private Predicate<Persona> filtroMinSupTotal(Inmueble i) {
	return p -> {
	    if (p.getPrefBusqueda() != null) {
		if (p.getPrefBusqueda().getMinSupTotal() != null) {
		    return p.getPrefBusqueda().getMinSupTotal().compareTo(i.getSuperficieTotal()) <= 0;
		} else {
		    return true;
		}
	    } else {
		return false;
	    }
	};
    }

    //XXX
    private Predicate<Persona> filtroProvincia(Inmueble i) {
	return p -> {
	    if (p.getPrefBusqueda() != null) {
		if (p.getPrefBusqueda().getProvincia() != null) {
		    return p.getPrefBusqueda().getProvincia().toLowerCase().equals(i.getDireccion().getProvincia()
			    .toLowerCase());
		} else {
		    return true;
		}
	    } else {
		return false;
	    }
	};
    }

    //XXX
    private Predicate<Persona> filtroPublicaciones(Inmueble i) {
	return p -> {
	    if (p.getPrefBusqueda() != null) {
		CriterioBusqInmueble prefs = p.getPrefBusqueda();
		List<Publicacion> publicaciones = InmuebleService.getPublicacionesActivas(i);
		List<PublicacionAlquiler> publicacionesAlquiler = matchAlquiler(prefs, publicaciones);
		List<PublicacionVenta> publicacionesVenta = matchVentas(prefs, publicaciones);

		if (prefs.getEstadoInmueble() != null) {
		    if (prefs.getEstadoInmueble() == EstadoInmueble.EnAlquilerYVenta) {
			return publicacionesAlquiler.size() > 0 || publicacionesVenta.size() > 0;
		    } else if (prefs.getEstadoInmueble() == EstadoInmueble.EnAlquiler) {
			return publicacionesAlquiler.size() > 0;
		    } else {
			return publicacionesVenta.size() > 0;
		    }
		} else
		    return true;
	    } else {
		return false;
	    }
	};
    }

    private List<PublicacionVenta> matchVentas(CriterioBusqInmueble prefs, List<Publicacion> publicaciones) {
	return publicaciones.stream()
		.filter(pub -> pub instanceof PublicacionVenta)
		.map(pub -> (PublicacionVenta) pub)
		.filter(pubA -> {
		    if (prefs.getTipoMoneda() != null)
			return pubA.getMoneda().equals(prefs.getTipoMoneda());
		    else
			return true;
		})
		.filter(pubA -> {
		    if (prefs.getMinPrecio() != null)
			return pubA.getPrecio().compareTo(prefs.getMinPrecio()) >= 0;
		    else
			return true;
		})
		.filter(pubA -> {
		    if (prefs.getMaxPrecio() != null)
			return pubA.getPrecio().compareTo(prefs.getMaxPrecio()) <= 0;
		    else
			return true;
		})
		.collect(Collectors.toList());
    }

    private List<PublicacionAlquiler> matchAlquiler(CriterioBusqInmueble prefs, List<Publicacion> publicaciones) {
	return publicaciones.stream()
		.filter(pub -> pub instanceof PublicacionAlquiler)
		.map(pub -> (PublicacionAlquiler) pub)
		.filter(pubA -> {
		    if (prefs.getTipoMoneda() != null)
			return pubA.getMoneda().equals(prefs.getTipoMoneda());
		    else
			return true;
		})
		.filter(pubA -> {
		    if (prefs.getMinPrecio() != null)
			return pubA.getPrecio().compareTo(prefs.getMinPrecio()) >= 0;
		    else
			return true;
		})
		.filter(pubA -> {
		    if (prefs.getMaxPrecio() != null)
			return pubA.getPrecio().compareTo(prefs.getMaxPrecio()) <= 0;
		    else
			return true;
		})
		.collect(Collectors.toList());
    }

    //XXX
    private Predicate<Persona> filtroTipoInm(Inmueble i) {
	return p -> {
	    if (p.getPrefBusqueda() != null) {
		if (p.getPrefBusqueda().getTipoInmueble() != null) {
		    return p.getPrefBusqueda().getTipoInmueble().equals(i.getTipoInmueble());
		} else {
		    return true;
		}
	    } else {
		return false;
	    }
	};
    }

    public Predicate<Persona> getFiltroCompuesto() {
	return filtroCompuesto;
    }

    public List<Predicate<Persona>> getTodosLosFiltros() {
	return filtros;
    }

}
