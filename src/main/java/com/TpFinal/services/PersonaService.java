package com.TpFinal.services;

import java.util.ArrayList;
import java.util.List;

import com.TpFinal.data.dao.DAOPersonaImpl;
import com.TpFinal.data.dao.interfaces.DAOPersona;
import com.TpFinal.data.dto.operacion.Rol;
import com.TpFinal.data.dto.persona.Persona;

public class PersonaService {
	DAOPersona dao;
	
	public PersonaService() {
		dao = new DAOPersonaImpl();
	}
	
	public boolean save(Persona p) {
		if(p.getRoles()!=null || p.getRoles().size()!=0) 
			p.getRoles().forEach(rol -> rol.setPersona(p));
		return dao.save(p);
	}
	
	public boolean update(Persona p) {
		if(p.getRoles()!=null || p.getRoles().size()!=0) 
			p.getRoles().forEach(rol -> rol.setPersona(p));
		return dao.update(p);
	}
	
	public boolean logicalDelete(Persona p) {
		return dao.logicalDelete(p);
	}
	
	public boolean delete(Persona p) {
		return dao.delete(p);
	}
	
	public List<Persona>readAllActives(){
		return dao.readAllActives();
	}
	
	public List<Persona>readAll(){
		return dao.readAll();
	}
	
	public List<Rol> giveMeYourRoles(Persona p){
		List<Rol>ret= new ArrayList<>();
		p.getRoles().forEach(r -> ret.add(r.giveMeYourRole()));
		return ret;
	}
	
	

}
