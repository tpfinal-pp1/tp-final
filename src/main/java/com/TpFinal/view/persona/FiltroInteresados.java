package com.TpFinal.view.persona;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import com.TpFinal.dto.inmueble.CriterioBusqInmueble;
import com.TpFinal.dto.inmueble.Inmueble;
import com.TpFinal.dto.persona.Persona;
import com.TpFinal.dto.publicacion.Publicacion;
import com.TpFinal.dto.publicacion.PublicacionAlquiler;
import com.TpFinal.dto.publicacion.PublicacionVenta;
import com.TpFinal.services.InmuebleService;

public class FiltroInteresados {

    private List<Predicate<Persona>> filtros = new ArrayList<>();
    private Predicate<Persona> filtroCompuesto;
    private Predicate<Persona> publicaciones;
    private Predicate<Persona> tipoInmueble;
    private Predicate<Persona> provincia;
    private Predicate<Persona> maxSupTotal;
    private Predicate<Persona> minSupTotal;
    private Predicate<Persona> maxSupCubierta;
    private Predicate<Persona> minSupCubierta;
    private Predicate<Persona> maxDormitorios;
    private Predicate<Persona> minDormitorios;
    private Predicate<Persona> maxCocheras;
    private Predicate<Persona> minCocheras;
    private Predicate<Persona> maxAmbientes;
    private Predicate<Persona> minAmbientes;
    private Predicate<Persona> localidad;
    private Predicate<Persona> estadoInmueble;
    private Predicate<Persona> conPileta;
    private Predicate<Persona> conJardin;
    private Predicate<Persona> conAireAcondicionado;
    private Predicate<Persona> clasesDeInmueble;
    private Predicate<Persona> aEstrenar;

    public FiltroInteresados() {
	filtroCompuesto = p -> true;
    }

    public FiltroInteresados(Inmueble i) {
	aEstrenar = filtroAEstrenar(i);
	clasesDeInmueble = filtroClaseInmueble(i);
	conAireAcondicionado = filtroAireAcondicionado(i);
	conJardin = filtroConJardin(i);
	conPileta = filtroConPileta(i);
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

	filtros.addAll(Arrays.asList(this.aEstrenar
		,this.clasesDeInmueble
//		,this.conAireAcondicionado
//		,this.conJardin
//		,this.conPileta
//		,this.estadoInmueble
//		,this.localidad
//		,this.maxAmbientes
//		,this.minAmbientes
//		,this.minCocheras
//		,this.maxCocheras
//		,this.minDormitorios
//		,this.maxDormitorios
//		,this.minSupCubierta
//		,this.maxSupCubierta
//		,this.minSupTotal
//		,this.maxSupTotal
//		,this.publicaciones
//		,this.provincia
//		,this.tipoInmueble
		));

	filtroCompuesto = filtros.stream().reduce(persona -> true, Predicate::and);
    }

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

    private Predicate<Persona> filtroClaseInmueble(Inmueble i) {
	return p -> {
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
    }

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

    private Predicate<Persona> filtroEstadoInmueble(Inmueble i) {
	return p -> {
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
    }

    private Predicate<Persona> filtroLocalidad(Inmueble i) {
	return p -> {
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
    }

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

    private Predicate<Persona> filtroPublicaciones(Inmueble i) {
	return p -> {
	    if (p.getPrefBusqueda() != null) {
		if (p.getPrefBusqueda().getTipoMoneda() != null) {
		    return InmuebleService.getPublicacionesActivas(i).stream()
			    .anyMatch(matchPublicacion(p));
		} else {
		    return true;
		}
	    } else {
		return false;
	    }
	};
    }

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

    private Predicate<? super Publicacion> matchPublicacion(Persona p) {
	return pub -> {
	    CriterioBusqInmueble prefs = p.getPrefBusqueda();
	    if (pub instanceof PublicacionAlquiler) {
		PublicacionAlquiler pubA = (PublicacionAlquiler) pub;
		return (prefs.getTipoMoneda() != null ? pubA.getMoneda().equals(prefs.getTipoMoneda())
			: true)
			&& (prefs.getTipoPublicacion() != null ? pubA.getTipoPublicacion().equals(prefs
				.getTipoPublicacion())
				: true)
			&& (prefs.getMinPrecio() != null ? pubA.getPrecio().compareTo(prefs
				.getMinPrecio()) >= 0
				: true)
			&& (prefs.getMaxPrecio() != null ? pubA.getPrecio()
				.compareTo(prefs.getMaxPrecio()) <= 0
				: true);
	    } else {
		PublicacionVenta pubV = (PublicacionVenta) pub;
		return (prefs.getTipoMoneda() != null ? pubV.getMoneda().equals(prefs.getTipoMoneda())
			: true)
			&& (prefs.getTipoPublicacion() != null ? pubV.getTipoPublicacion().equals(prefs
				.getTipoPublicacion())
				: true)
			&& (prefs.getMinPrecio() != null ? pubV.getPrecio().compareTo(prefs
				.getMinPrecio()) >= 0
				: true)
			&& (prefs.getMaxPrecio() != null ? pubV.getPrecio()
				.compareTo(prefs.getMaxPrecio()) <= 0 : true);
	    }
	};
    }

}
