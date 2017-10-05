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
	
	public List<Inmueble> readAllActives() {
		return dao.readAllActives();
	}
	
	public List<Inmueble> readAll() {
		return dao.readAll();
	}

	public boolean update(Inmueble entidad) {
		return dao.update(entidad);
	}
	
	public boolean saveOrUpdate(Inmueble entidad) {
	    return dao.saveOrUpdate(entidad);
	}

	public boolean delete(Inmueble i) {
		return dao.logicalDelete(i);
	}
	
	public boolean seriousDelete(Inmueble i) {
		return dao.delete(i);
	}

	public boolean save(Inmueble entidad) {
		return dao.save(entidad);
	}

	public Inmueble findById(Long id) {
		return dao.findById(id);
	}
	
	public List<Inmueble> findByCaracteristicas(CriterioBusquedaInmuebleDTO criterio){
		return dao.findInmueblesbyCaracteristicas(criterio);
	}
	

}
