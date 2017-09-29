package com.TpFinal.services;

import java.util.List;

import com.TpFinal.data.dao.InquilinoDAO;
import com.TpFinal.data.dao.InquilinoDAOImpl;
import com.TpFinal.data.dto.InquilinoDTO;

public class InquilinoService {

	private InquilinoDAO dao;
	
	public InquilinoService() {
		dao=new InquilinoDAOImpl();
	}
	
	public List<InquilinoDTO> readAll() {
		return dao.readAll();
	}

	public boolean update(InquilinoDTO entidad) {
		return dao.update(entidad);
	}

	public boolean delete(InquilinoDTO entidad) {
		return dao.delete(entidad);
	}

	public boolean save(InquilinoDTO entidad) {
		return dao.save(entidad);
	}

	public InquilinoDTO findById(Long id) {
		return dao.findById(id);
	}

}
