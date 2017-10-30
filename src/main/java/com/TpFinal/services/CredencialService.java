package com.TpFinal.services;

import java.util.List;

import com.TpFinal.data.dao.DAOCredencialImpl;
import com.TpFinal.data.dao.DAOPersonaImpl;
import com.TpFinal.data.dao.interfaces.DAOCredencial;
import com.TpFinal.dto.persona.Credencial;

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
	return dao.readAllActives().stream().anyMatch(c -> c.getUsuario().equals(usuario));
    }

    public void deepDelete(Credencial credencial) {
	if (credencial.getId() != null)
	{
	    DAOPersonaImpl daop = new DAOPersonaImpl();
	    credencial.getEmpleado().setCredencial(null);
	    daop.saveOrUpdate(credencial.getEmpleado().getPersona());
	    dao.delete(credencial);
	}
    }

}
