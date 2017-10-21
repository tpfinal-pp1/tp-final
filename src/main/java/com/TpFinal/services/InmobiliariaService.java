package com.TpFinal.services;

import java.util.List;

import com.TpFinal.data.dao.DAOInmobiliariaImpl;
import com.TpFinal.data.dao.interfaces.DAOInmobiliaria;
import com.TpFinal.dto.inmobiliaria.Inmobiliaria;

public class InmobiliariaService {
	DAOInmobiliaria dao;
	
	public InmobiliariaService() {
		dao= new DAOInmobiliariaImpl();
	}
	
	public boolean saveOrUpdate(Inmobiliaria i) {
		return dao.saveOrUpdate(i);
	}
	
	public boolean delete(Inmobiliaria i) {
		return dao.logicalDelete(i);
	}
	
	public List<Inmobiliaria>readAll(){
		return dao.readAllActives();
	}
	
	

}
