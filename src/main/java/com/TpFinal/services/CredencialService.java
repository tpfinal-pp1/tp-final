package com.TpFinal.services;

import java.util.List;

import com.TpFinal.data.dao.DAOCredencialImpl;
import com.TpFinal.data.dao.interfaces.DAOCredencial;
import com.TpFinal.dto.persona.Credencial;

public class CredencialService {
	DAOCredencial dao;
	
	public CredencialService() {
		dao=new DAOCredencialImpl();
	}
	
	public boolean save(Credencial c) {
		return dao.saveOrUpdate(c);
	}
	
	public boolean delete(Credencial c) {
		return dao.logicalDelete(c);
	}
	
	public List<Credencial> readAll() {
		return dao.readAllActives();
	}
	
	public boolean existe(Credencial credencial) {
		return dao.existe(credencial);
	}

}
