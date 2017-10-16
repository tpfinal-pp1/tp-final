package com.TpFinal.services;

import com.TpFinal.data.dao.DAOInmuebleImpl;
import com.TpFinal.data.dao.interfaces.DAOInmueble;
import com.TpFinal.data.dto.EstadoRegistro;
import com.TpFinal.data.dto.contrato.Contrato;
import com.TpFinal.data.dto.contrato.EstadoContrato;
import com.TpFinal.data.dto.inmueble.ClaseInmueble;
import com.TpFinal.data.dto.inmueble.Coordenada;
import com.TpFinal.data.dto.inmueble.CriterioBusquedaInmuebleDTO;
import com.TpFinal.data.dto.inmueble.Direccion;
import com.TpFinal.data.dto.inmueble.EstadoInmueble;
import com.TpFinal.data.dto.inmueble.Inmueble;
import com.TpFinal.data.dto.inmueble.TipoInmueble;
import com.TpFinal.data.dto.persona.Persona;
import com.TpFinal.data.dto.persona.Propietario;
import com.TpFinal.data.dto.publicacion.EstadoPublicacion;
import com.TpFinal.data.dto.publicacion.Publicacion;
import com.TpFinal.data.dto.publicacion.Rol;
import com.TpFinal.data.dto.publicacion.TipoPublicacion;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class InmuebleService {
    private DAOInmueble dao;

    public InmuebleService() {
	dao = new DAOInmuebleImpl();
    }

    public List<Inmueble> readAll() {
	return dao.readAllActives();
    }

    public boolean merge(Inmueble entidad) {
	return dao.merge(entidad);
    }

    public boolean delete(Inmueble i) {
	return dao.logicalDelete(i);
    }

    public Inmueble findById(Long id) {
	return dao.findById(id);
    }

    public List<Inmueble> findByCaracteristicas(CriterioBusquedaInmuebleDTO criterio) {
	return dao.findInmueblesbyCaracteristicas(criterio);
    }

    public static Inmueble getInstancia() {
	return new Inmueble.Builder()
		.setaEstrenar(false)
		.setCantidadAmbientes(0)
		.setCantidadCocheras(0)
		.setCantidadDormitorios(0)
		.setClaseInmueble(ClaseInmueble.OtroInmueble)
		.setConAireAcondicionado(false)
		.setConJardin(false)
		.setConParilla(false)
		.setConPileta(false)
		.setDireccion(new Direccion.Builder()
			.setCalle("")
			.setCodPostal("")
			.setCoordenada(new Coordenada())
			.setLocalidad("")
			.setNro(0)
			.setPais("Argentina")
			.setProvincia("")
			.build())
		.setEstadoInmueble(EstadoInmueble.NoPublicado)
		.setPropietario(new Propietario())
		.setSuperficieCubierta(0)
		.setSuperficieTotal(0)
		.setTipoInmueble(TipoInmueble.Vivienda)
		.build();
    }

    public List<Inmueble> filtrarPorCalle(String filtro) {
	List<Inmueble> inmuebles = dao.readAllActives().stream()
		.filter(i -> {
		    return filtro == null || filtro.isEmpty()
			    || i.getDireccion().getCalle().toLowerCase().contains(filtro.toLowerCase());
		})
		.collect(Collectors.toList());
	Collections.sort(inmuebles, new Comparator<Inmueble>() {

	    @Override
	    public int compare(Inmueble o1, Inmueble o2) {
		return o1.getDireccion().getCalle().compareTo(o2.getDireccion().getCalle());
	    }

	});
	return inmuebles;
    }

    /**
     * Revisa la lista de publicaciones. Si existe al menos una publicación activa,
     * cambia el estadoPublicación a activo sino, cambia el estadoPublicacion a
     * noPublicado
     * 
     * @param inmueble
     */
    public boolean actualizarEstadoInmuebleSegunPublicacion(Inmueble inmueble) {
	System.out.println("Actualizando Estado Inmueble");
	boolean ret = true;
	List<Publicacion> publicaciones = getListadoDePublicaciones(inmueble);
	List<Publicacion> pubsActivas = publicaciones.stream().filter(this::estaActivoYNoFueBorrado)
		.limit(2).collect(Collectors.toList());

	if (!pubsActivas.isEmpty()) {
	    setEstadoInmuebleSegunPublicaciones(inmueble, pubsActivas);
	    ret = ret && dao.merge(inmueble);
	}
	return ret;

    }

    private boolean estaActivoYNoFueBorrado(Publicacion p) {
	return p.getEstadoRegistro() == EstadoRegistro.ACTIVO && p
		.getEstadoPublicacion() == EstadoPublicacion.Activa;
    }

    private void setEstadoInmuebleSegunPublicaciones(Inmueble inmueble, List<Publicacion> publicaciones) {
	if (!(inmueble.getEstadoInmueble() == EstadoInmueble.Alquilado
		|| (inmueble.getEstadoInmueble() == EstadoInmueble.Vendido))) {
	    if (publicaciones.size() == 2) {
		inmueble.setEstadoInmueble(EstadoInmueble.EnAlquilerYVenta);
	    } else {
		Publicacion publicacion = publicaciones.get(0);
		if (publicacion.getTipoPublicacion() == TipoPublicacion.Alquiler) {
		    inmueble.setEstadoInmueble(EstadoInmueble.EnAlquiler);
		} else {
		    inmueble.setEstadoInmueble(EstadoInmueble.EnVenta);
		}
	    }
	}

    }

    public List<Publicacion> getListadoDePublicaciones(Inmueble inmueble) {
	List<Publicacion> ret = null;
	if (inmueble != null && inmueble.getPublicaciones() != null) {
	    ret = inmueble.getPublicaciones().stream().collect(Collectors.toList());
	}
	return ret;
    }

    public void desvincularContrato(Contrato contratoAntiguo) {
	Inmueble i = contratoAntiguo.getInmueble();
	i.removeContrato(contratoAntiguo);
	dao.saveOrUpdate(i);
    }

    public boolean inmueblePoseeContratoVigente(Inmueble inmueble) {
	boolean ret = false;
	if (inmueble != null) {
	    Set<Contrato> contratos = dao.findById(inmueble.getId()).getContratos();
	    if (contratos != null)
		ret = contratos.stream().anyMatch(contrato -> contrato.getEstadoContrato() == EstadoContrato.Vigente);
	}
	return ret;
    }

}
