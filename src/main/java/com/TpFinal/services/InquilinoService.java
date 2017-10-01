package com.TpFinal.services;

import java.util.List;

import com.TpFinal.data.dao.DAOInquilinoImpl;
import com.TpFinal.data.dao.interfaces.DAOInquilino;
import com.TpFinal.data.dto.persona.Inquilino;


public class InquilinoService {

	private DAOInquilino dao;
	
	public InquilinoService() {
		dao=new DAOInquilinoImpl();
	}
	
	public List<Inquilino> readAll() {
		return dao.readAll();
	}

	public boolean update(Inquilino entidad) {
		return dao.update(entidad);
	}

	public boolean delete(Inquilino entidad) {
		return dao.delete(entidad);
	}

	public boolean save(Inquilino entidad) {
		return dao.save(entidad);
	}

	public Inquilino findById(Long id) {
		return dao.findById(id);
	}

}
