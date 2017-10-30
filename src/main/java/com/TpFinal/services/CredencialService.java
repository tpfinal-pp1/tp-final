package com.TpFinal.services;

import java.util.ArrayList;
import java.util.List;

import com.TpFinal.data.dao.DAOCredencialImpl;
import com.TpFinal.data.dao.interfaces.DAOCredencial;
import com.TpFinal.dto.EstadoRegistro;
import com.TpFinal.dto.persona.Credencial;
import com.TpFinal.dto.persona.Empleado;
import com.TpFinal.dto.persona.Persona;
import com.TpFinal.dto.persona.ViewAccess;
import com.TpFinal.view.DashboardViewType;

import javax.swing.text.View;

public class CredencialService {
	DAOCredencial dao;
	
	public CredencialService() {
		dao=new DAOCredencialImpl();
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


	public boolean hasViewAccess(Credencial credencial,Class view){
		ArrayList<DashboardViewType> userViews=credencial.getViewAccess().views;
		for (DashboardViewType viewType: userViews)
			if(viewType.getViewClass().equals(view))
				return true;
		return false;
	}

	public Empleado logIn(String user,String pass){
		Empleado ret=new Empleado();

		/*if(user.equals("admin")){
			Persona p=new Persona.Builder().setNombre("Admin").setApellido("User").build();
			Empleado emp= new Empleado.Builder().setPersona(p).setPersona(p).build();
			Credencial cred=new Credencial();
			cred.setUsuario(user);
			cred.setContrasenia(pass);
			cred.setViewAccess(ViewAccess.Admin);
			emp.setCredencial(cred);
			return emp;

		}*/
		try{readAll();}
		catch (Exception e){
			e.printStackTrace();
		}
		for(Credencial cred:readAll()){
			boolean match=cred.getUsuario().equals(user)
					&&cred.getContrasenia().equals(pass);
			if(match){
				//TODO Hacer que se fije si las credenciales caducaron
				return cred.getEmpleado();
			}
		}




		return ret;
	}
	
	public boolean existe(Credencial credencial) {
		return dao.existe(credencial);
	}

}
