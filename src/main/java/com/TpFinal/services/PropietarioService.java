package com.TpFinal.services;

import java.util.List;

import com.TpFinal.data.dao.DAOPropietarioImpl;
import com.TpFinal.data.dao.interfaces.DAOPropietario;
import com.TpFinal.domain.persona.Propietario;

public class PropietarioService {

	private DAOPropietario dao;
	
	public PropietarioService() {
		dao=new DAOPropietarioImpl();
	}
	
	public List<Propietario> readAll() {
		return dao.readAll();
	}

	public boolean update(Propietario entidad) {
		return dao.update(entidad);
	}

	public boolean delete(Propietario entidad) {
		return dao.delete(entidad);
	}

	public boolean save(Propietario entidad) {
		return dao.save(entidad);
	}

	public Propietario findById(Long id) {
		return dao.findById(id);
	}
}
