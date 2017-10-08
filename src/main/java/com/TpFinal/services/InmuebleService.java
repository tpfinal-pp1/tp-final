package com.TpFinal.services;

import com.TpFinal.data.dao.DAOInmuebleImpl;
import com.TpFinal.data.dao.interfaces.DAOInmueble;
import com.TpFinal.data.dto.inmueble.ClaseInmueble;
import com.TpFinal.data.dto.inmueble.Coordenada;
import com.TpFinal.data.dto.inmueble.CriterioBusquedaInmuebleDTO;
import com.TpFinal.data.dto.inmueble.Direccion;
import com.TpFinal.data.dto.inmueble.EstadoInmueble;
import com.TpFinal.data.dto.inmueble.Inmueble;
import com.TpFinal.data.dto.inmueble.TipoInmueble;
import com.TpFinal.data.dto.persona.Persona;
import com.TpFinal.data.dto.persona.Propietario;
import com.TpFinal.data.dto.publicacion.Rol;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class InmuebleService {
    private DAOInmueble dao;

    public InmuebleService() {
	dao = new DAOInmuebleImpl();
    }

    public List<Inmueble> readAll() {
	return dao.readAllActives();
    }

    /**
     * Usar solo para guardar inmuebles
     * 
     * @param entidad
     * @return <code> true </code> si se guardo, <code> false </code> si ocurrio un
     *         error.
     */
    public boolean saveOrUpdate(Inmueble entidad) {
	// if(entidad.getPropietario()!=null)
	// entidad.getPropietario().addInmueble(entidad);
	return dao.saveOrUpdate(entidad);
    }

    /**
     * Usar para update de inmuebles ya que incluye bidireccional
     * 
     * @param inmueble
     * @return
     */
    @Deprecated
    public boolean updateBidireccional(Inmueble inmueble) {
	boolean ret = true;
	Inmueble inmBd = dao.findById(inmueble.getId());
	if (inmueble.getPropietario() == null || !inmueble.getPropietario().equals(inmBd.getPropietario())) {
	    inmBd.getPropietario().getInmuebles().remove(inmBd);
	}
	dao.saveOrUpdate(inmBd);
	dao.saveOrUpdate(inmueble);
	return ret;
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

    /**
     * guarda un inmueble junto con todas las entidades a las que esta asociado.
     * 
     * @param inmueble
     * @return
     */
    public boolean save(Inmueble inmueble) {
	boolean ret = false;
	PersonaService pService = new PersonaService();
	if (inmueble != null) {
	    // si es != null entonces esta en la bd
	    if (inmueble.getId() != null) {
		if (inmueble.getPropietario() != null) {
		    if (inmueble.getPropietario().getPersona() != null) {
			Inmueble inmuebleSinModificar = findById(inmueble.getId());

			if (inmuebleSinModificar.getPropietario().getPersona() != null) {
			    Persona propietarioAntiguo = inmuebleSinModificar.getPropietario().getPersona();
			    ((Propietario) propietarioAntiguo.getRol(Rol.Propietario)).getInmuebles().remove(
				    inmuebleSinModificar);
			    pService.saveOrUpdate(propietarioAntiguo);

			}

			Persona propietarioNuevo = inmueble.getPropietario().getPersona();
			((Propietario) propietarioNuevo.getRol(Rol.Propietario)).addInmueble(inmueble);
			pService.saveOrUpdate(propietarioNuevo);
		    }
		}
	    } else {
		if (inmueble.getPropietario() != null) {
		    if (inmueble.getPropietario().getPersona() != null) {
			Persona propietarioNuevo = inmueble.getPropietario().getPersona();
			((Propietario) propietarioNuevo.getRol(Rol.Propietario)).addInmueble(inmueble);
			pService.saveOrUpdate(propietarioNuevo);
		    }
		}

	    }
	    ret = dao.saveOrUpdate(inmueble);
	}
	return ret;
    }

    public List<Inmueble> filtrarPorCalle(String filtro) {
	List<Inmueble> inmuebles = dao.readAllActives().stream()
		.filter( i -> {return filtro == null || filtro.isEmpty()
                || i.getDireccion().getCalle().toLowerCase().contains(filtro.toLowerCase());})
		.collect(Collectors.toList());
	Collections.sort(inmuebles, new Comparator<Inmueble>(){

	    @Override
	    public int compare(Inmueble o1, Inmueble o2) {
		return o1.getDireccion().getCalle().compareTo(o2.getDireccion().getCalle());
	    }
	    
	});
	return inmuebles;
    }

}
