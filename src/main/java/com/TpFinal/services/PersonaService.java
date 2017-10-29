package com.TpFinal.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.TpFinal.data.dao.DAOPersonaImpl;
import com.TpFinal.data.dao.interfaces.DAOPersona;
import com.TpFinal.dto.persona.Empleado;
import com.TpFinal.dto.persona.Inquilino;
import com.TpFinal.dto.persona.Persona;
import com.TpFinal.view.persona.FiltroInteresados;

public class PersonaService {
	DAOPersona dao;
	
	public PersonaService() {
		dao = new DAOPersonaImpl();
	}
	
	public boolean saveOrUpdate(Persona p) {
		return dao.saveOrUpdate(p);
	}
	
	public boolean delete(Persona p) {
		return dao.logicalDelete(p);
	}
	
	public List<Persona>readAll(){
		return dao.readAllActives();
	}
	
	public List<Persona>getInmobiliarias(){
		return dao.readAllActives().stream()
				.filter(p -> p.getEsInmobiliaria())
				.collect(Collectors.toList());
	}
	
	public List<Persona>getPersonas(){
		return dao.readAllActives().stream()
				.filter(p -> !p.getEsInmobiliaria())
				.collect(Collectors.toList());
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
	    Persona p = new Persona.Builder().setNombre("nombre").setApellido("apellido").build();
	    Inquilino i = new Inquilino.Builder().setPersona(p).build();
	    p.getRoles().add(i);
	    return i;
	}
	
	public static Empleado getEmpleadoInstancia() {
		Empleado empleado = new Empleado.Builder().setNombre("").setApellido("").setDNI("").build();
		return empleado;
	}

	public List<Persona> findAllClientes(FiltroInteresados filtro) {
	    List<Persona> personas = dao.readAllActives().stream()
		    .filter(p -> !(p instanceof Empleado))
		    .filter(filtro.getFiltroCompuesto()).collect(Collectors.toList());
	    personas.sort(Comparator.comparing(Persona::getId));
	    return personas;
	}
	
	public List<Persona> findAllEmpleados(FiltroInteresados filtro) {
	    List<Persona> personas = dao.readAllActives().stream()
		    .filter(p -> p instanceof Empleado)
		    .filter(filtro.getFiltroCompuesto()).collect(Collectors.toList());
	    personas.sort(Comparator.comparing(Persona::getId));
	    return personas;
	}
	
	

}
