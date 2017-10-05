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
	
	public boolean saveOrUpdate(Inmueble entidad) {
	    return dao.saveOrUpdate(entidad);
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
