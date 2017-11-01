package com.TpFinal.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.TpFinal.data.dao.DAOPersonaImpl;
import com.TpFinal.data.dao.interfaces.DAOPersona;
import com.TpFinal.dto.EstadoRegistro;
import com.TpFinal.dto.persona.Credencial;
import com.TpFinal.dto.persona.Empleado;
import com.TpFinal.dto.persona.EstadoEmpleado;
import com.TpFinal.dto.persona.Inquilino;
import com.TpFinal.dto.persona.Persona;
import com.TpFinal.dto.persona.Rol;
import com.TpFinal.view.persona.FiltroEmpleados;
import com.TpFinal.view.persona.FiltroInteresados;

public class PersonaService {
    DAOPersona dao;
    CredencialService credencialService = new CredencialService();

    public PersonaService() {
	dao = new DAOPersonaImpl();
    }

    public boolean saveOrUpdate(Persona p) {
	return dao.saveOrUpdate(p);
    }

    public boolean delete(Persona p) {
	if (p.giveMeYourRoles().contains(Rol.Empleado)) {
	    Credencial c = ((Empleado) p.getRol(Rol.Empleado)).getCredencial();
	    if (c != null) {
		credencialService.deepDelete(c);
	    }
	}
	return dao.logicalDelete(p);
    }

    public List<Persona> readAll() {
	return dao.readAllActives();
    }

    public List<Persona> getInmobiliarias() {
	return dao.readAllActives().stream()
		.filter(p -> p.getEsInmobiliaria())
		.collect(Collectors.toList());
    }

    public List<Persona> getPersonas() {
	return dao.readAllActives().stream()
		.filter(p -> !p.getEsInmobiliaria())
		.collect(Collectors.toList());
    }

    public synchronized List<Persona> findAll(String stringFilter) {
	ArrayList arrayList = new ArrayList();
	List<Persona> personas = dao.readAllActives();
	if (stringFilter != "") {

	    for (Persona persona : personas) {

		boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
			|| persona.toString().toLowerCase()
				.contains(stringFilter.toLowerCase());
		if (passesFilter) {

		    arrayList.add(persona);
		}

	    }
	} else {
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
	List<Persona> personas = dao.readAllActives();
	if (stringFilter != "") {

	    for (Persona persona : personas) {

		boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
			|| persona.roles().toLowerCase()
				.contains(stringFilter.toLowerCase());
		if (passesFilter) {

		    arrayList.add(persona);
		}

	    }
	} else {
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
	Persona p = new Persona.Builder().setNombre("").setApellido("").build();
	Inquilino i = new Inquilino.Builder().setPersona(p).build();
	p.getRoles().add(i);
	return i;
    }

    public List<Persona> findAllClientes(FiltroInteresados filtro) {
	List<Persona> personas = dao.readAllActives().stream()
		.filter(p -> !p.giveMeYourRoles().contains(Rol.Empleado))
		.filter(filtro.getFiltroCompuesto()).collect(Collectors.toList());
	personas.sort(Comparator.comparing(Persona::getId));
	return personas;
    }

    public List<Empleado> findAllEmpleados(FiltroEmpleados filtro) {
	List<Empleado> empleados = dao.readAll().stream()
		.filter(p -> p.giveMeYourRoles().contains(Rol.Empleado))		
		.map(p -> (Empleado) p.getRol(Rol.Empleado))
		.filter(filtro.getFiltroCompuesto())
		.collect(Collectors.toList());
	empleados.sort(Comparator.comparing(Empleado::getId));
	return empleados;
    }

    public static Empleado getEmpleadoInstancia() {
	Persona p = new Persona();
	Empleado e = new Empleado.Builder()
		.setFechaDeAlta(LocalDate.now())
		.setPersona(p)
		.build();
	Credencial c = new Credencial.Builder().setEmpleado(e).build();
	e.setCredencial(c);
	return e;
    }

    public boolean darDeBajaEmpleado(Empleado empleado) {
	boolean ret = true;
	empleado.setFechaDeBaja(LocalDate.now());
	empleado.setEstadoEmpleado(EstadoEmpleado.NOACTIVO);
	ret = ret && dao.saveOrUpdate(empleado.getPersona());
	ret = ret && this.delete(empleado.getPersona());
	if (!ret) {
	    // Rollback.
	    empleado.setFechaDeBaja(null);
	    empleado.setEstadoEmpleado(EstadoEmpleado.ACTIVO);
	    empleado.getPersona().setEstadoRegistro(EstadoRegistro.ACTIVO);
	    dao.saveOrUpdate(empleado.getPersona());
	}

	return ret;

    }

    public boolean reincorporarEmpleado(Empleado empleado) {
	boolean ret = false;
	if (empleado != null) {
	    if (empleado.getPersona() != null) {
		empleado.setEstadoEmpleado(EstadoEmpleado.ACTIVO);
		LocalDate fechaAlta = empleado.getFechaDeAlta();
		LocalDate fechaBaja = empleado.getFechaDeBaja();
		empleado.setFechaDeAlta(LocalDate.now());
		empleado.setFechaDeBaja(null);
		ret = dao.saveOrUpdate(empleado.getPersona());
		if (ret == false) {
		    empleado.setEstadoEmpleado(EstadoEmpleado.NOACTIVO);
		    empleado.setFechaDeAlta(fechaAlta);
		    empleado.setFechaDeBaja(fechaBaja);}
	    }
	}
	return ret;
    }

}
