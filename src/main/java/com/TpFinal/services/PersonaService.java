package com.TpFinal.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.TpFinal.data.dao.DAOImpl;
import com.TpFinal.data.dao.DAOPersonaImpl;
import com.TpFinal.data.dao.interfaces.DAO;
import com.TpFinal.data.dao.interfaces.DAOPersona;
import com.TpFinal.data.dto.EstadoRegistro;
import com.TpFinal.data.dto.persona.Inquilino;
import com.TpFinal.data.dto.persona.Persona;
import com.TpFinal.data.dto.publicacion.Rol;

public class PersonaService {
	DAOPersona dao;
	
	public PersonaService() {
		dao = new DAOPersonaImpl();
	}
	
	public boolean saveOrUpdate(Persona p) {
		if(p.getRoles()!=null || p.getRoles().size()!=0) 
			p.getRoles().forEach(rol -> rol.setPersona(p));
		return dao.saveOrUpdate(p);
	}
	
	public boolean delete(Persona p) {
		return dao.logicalDelete(p);
	}
	
	public List<Persona>readAll(){
		return dao.readAllActives();
	}
	
	 public synchronized List<Persona> findAll(String stringFilter) {
	        ArrayList arrayList = new ArrayList();
	        List<Persona> personas=dao.readAllActives();
	        if(stringFilter!=""){

	            for (Persona persona : personas) {

	                    boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
	                                            || persona.toString().toLowerCase()
	                                            .contains(stringFilter.toLowerCase());
	                    if (passesFilter) {

	                        arrayList.add(persona);
	                    }

	            }
	        }
	        else{
	            arrayList.addAll(personas);
	        }

	        Collections.sort(arrayList, new Comparator<Persona>() {

	            @Override
	            public int compare(Persona o1, Persona o2) {
	                return (int) (o2.getId() - o1.getId());
	            }
	        });
	        return arrayList;
	    }
	 
	 public synchronized List<Persona> findForRole(String stringFilter) {
	        ArrayList arrayList = new ArrayList();
	        List<Persona> personas=dao.readAllActives();
	        if(stringFilter!=""){

	            for (Persona persona : personas) {

	                    boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
	                                            || persona.roles().toLowerCase()
	                                            .contains(stringFilter.toLowerCase());
	                    if (passesFilter) {

	                        arrayList.add(persona);
	                    }

	            }
	        }
	        else{
	            arrayList.addAll(personas);
	        }

	        Collections.sort(arrayList, new Comparator<Persona>() {

	            @Override
	            public int compare(Persona o1, Persona o2) {
	                return (int) (o2.getId() - o1.getId());
	            }
	        });
	        return arrayList;
	    }

	public static Inquilino getPersonaConInquilino() {
	    Persona p = new Persona();
	    Inquilino i = new Inquilino.Builder().setPersona(p).build();
	    p.getRoles().add(i);
	    return i;
	}
	
	

}
