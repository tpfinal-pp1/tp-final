package com.TpFinal.services;

import java.util.List;

import com.TpFinal.data.dao.DAOInmuebleImpl;
import com.TpFinal.data.dao.interfaces.DAOInmueble;
import com.TpFinal.data.dto.inmueble.CriterioBusquedaInmuebleDTO;
import com.TpFinal.data.dto.inmueble.Inmueble;
import com.TpFinal.data.dto.persona.Persona;

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
	 * @return
	 */
	public boolean saveOrUpdate(Inmueble entidad) {
		if(entidad.getPropietario()!=null)
			entidad.getPropietario().addInmueble(entidad);
	    return dao.saveOrUpdate(entidad);
	}
	
	/**
	 * Usar para update de inmuebles ya que incluye bidireccional
	 * @param inmueble
	 * @return
	 */
	public boolean updateBidireccional(Inmueble inmueble) {
		boolean ret=true;
		Inmueble inmBd=dao.findById(inmueble.getId());
		if(inmueble.getPropietario()==null || !inmueble.getPropietario().equals(inmBd.getPropietario())) {
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
	
	public List<Inmueble> findByCaracteristicas(CriterioBusquedaInmuebleDTO criterio){
		return dao.findInmueblesbyCaracteristicas(criterio);
	}
	

}
