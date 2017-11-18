package com.TpFinal.services;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.TpFinal.DashboardUI;
import com.TpFinal.data.dao.DAOCredencialImpl;
import com.TpFinal.data.dao.DAOPersonaImpl;
import com.TpFinal.data.dao.interfaces.DAOCredencial;
import com.TpFinal.dto.persona.*;

import com.TpFinal.view.DashboardViewType;
import com.TpFinal.view.empleados.EmpleadoABMView;
import com.vaadin.server.VaadinSession;


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
	public static Empleado getCurrentUser() {
		return DashboardUI.getEmpleadoLogueado();
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
	ArrayList<Credencial> credencials=new ArrayList<Credencial>();
	try {
	   credencials.addAll(readAll());
	} catch (Exception e) {
	    e.printStackTrace();
	}
	boolean hayAlMenosUnAdmin=false;
	boolean huboMatch=false;
	boolean noHayEmpleados=credencials.size()==0;
	for (Credencial cred : credencials) {

		if(credencials.size()==1){  //ANTILOCKOUT
			Persona personaTemp=cred.getEmpleado().getPersona();
			if((personaTemp.getNombre()+personaTemp.getApellido()).equals("AccesoTemporal")){
				cred.setViewAccess(ViewAccess.Admin);
				return cred.getEmpleado();
			}
		}

	    boolean match = cred.getUsuario().equals(user)
		    && cred.getContrasenia().equals(pass);
	    if (match) {
		ret= cred.getEmpleado();
		huboMatch=true;
	    }
	    if(cred.getViewAccess().equals(ViewAccess.Admin)){
	    	hayAlMenosUnAdmin=true;

		}
	}
		//ANTILOCKOUT
	if(!hayAlMenosUnAdmin&&!huboMatch){

	  Persona p=new Persona.Builder().setNombre("Acceso").setApellido("Temporal").build(); Empleado
	  emp= new Empleado.Builder().setPersona(p).setPersona(p).build(); Credencial cred=new Credencial();
	  emp.setCategoriaEmpleado(CategoriaEmpleado.sinCategoria);

		if(noHayEmpleados){
			 cred.setUsuario("admin"); cred.setContrasenia("admin");
		}
		else{
			cred.setUsuario(String.valueOf(Instant.now().toEpochMilli()));
			cred.setContrasenia(String.valueOf(Instant.now().toEpochMilli()));
		}
		cred.setEmpleado(emp);
	  cred.setViewAccess(ViewAccess.Recovery); emp.setCredencial(cred);
	  ret=emp;
	}
	//ANTILOCKOUT
	else if(!hayAlMenosUnAdmin&&huboMatch)
		{
			Credencial credUpgrade=ret.getCredencial();
			credUpgrade.setViewAccess(ViewAccess.Admin);
			ret.setCredencial(credUpgrade);
		}

	return ret;
    }

}
