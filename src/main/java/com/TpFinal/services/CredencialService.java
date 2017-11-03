package com.TpFinal.services;

import java.util.ArrayList;
import java.util.List;

import com.TpFinal.data.dao.DAOCredencialImpl;
import com.TpFinal.data.dao.DAOPersonaImpl;
import com.TpFinal.data.dao.interfaces.DAOCredencial;
import com.TpFinal.dto.persona.Credencial;
import com.TpFinal.dto.persona.Empleado;

import com.TpFinal.view.DashboardViewType;


public class CredencialService {
    DAOCredencial dao;

    public CredencialService() {
	dao = new DAOCredencialImpl();
    }

    public boolean saveOrUpdate(Credencial c) {
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

    public boolean existeUsuario(String usuario) {
	return usuario!=null && usuario!=""? dao.readAllActives().stream().anyMatch(c -> c.getUsuario().equals(usuario)) : false;
    }

    public void deepDelete(Credencial credencial) {
	if (credencial.getId() != null) {
	    DAOPersonaImpl daop = new DAOPersonaImpl();
	    credencial.getEmpleado().setCredencial(null);
	    daop.saveOrUpdate(credencial.getEmpleado().getPersona());
	    dao.delete(credencial);
	}

    }

    public boolean hasViewAccess(Credencial credencial, Class view) {
	ArrayList<DashboardViewType> userViews = credencial.getViewAccess().views;
	for (DashboardViewType viewType : userViews)
	    if (viewType.getViewClass().equals(view))
		return true;
	return false;
    }

    public Empleado logIn(String user, String pass) {
	Empleado ret = new Empleado();

	try {
	    readAll();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	for (Credencial cred : readAll()) {
	    boolean match = cred.getUsuario().equals(user)
		    && cred.getContrasenia().equals(pass);
	    if (match) {
		// TODO Hacer que se fije si las credenciales caducaron
		return cred.getEmpleado();
	    }
	}

	/*
	 * //todo PARA QUE PASEN LOS TESTS BORRAR EN PRODUCCION if(user.equals("")){
	 * 
	 * Persona p=new
	 * Persona.Builder().setNombre("Test").setApellido("User").build(); Empleado
	 * emp= new Empleado.Builder().setPersona(p).setPersona(p).build(); Credencial
	 * cred=new Credencial(); cred.setUsuario(user); cred.setContrasenia(pass);
	 * cred.setViewAccess(ViewAccess.Admin); emp.setCredencial(cred); return emp;
	 * 
	 * }
	 */

	return ret;
    }

}
