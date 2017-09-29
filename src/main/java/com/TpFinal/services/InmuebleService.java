package com.TpFinal.services;

import java.util.List;

import com.TpFinal.data.dao.DAOInmuebleImpl;
import com.TpFinal.data.dao.interfaces.DAOInmueble;
import com.TpFinal.data.dto.inmueble.CriterioBusquedaInmuebleDTO;
import com.TpFinal.data.dto.inmueble.Inmueble;

public class InmuebleService {
	private DAOInmueble dao;
	
	public InmuebleService() {
		dao = new DAOInmuebleImpl();
	}
	
	public List<Inmueble> readAll() {
		return dao.readAll();
	}

	public boolean update(Inmueble entidad) {
		return dao.update(entidad);
	}

	public boolean delete(Inmueble entidad) {
		return dao.delete(entidad);
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
