package com.TpFinal.data.dao;

import java.util.Collection;
import java.util.List;

import com.TpFinal.data.dao.interfaces.DAO;
import com.TpFinal.data.dao.interfaces.DAOPersona;
import com.TpFinal.data.dto.DashboardNotification;
import com.TpFinal.data.dto.persona.Persona;
import com.TpFinal.data.dto.dummy.User;


public class DAOPersonaImpl implements DAOPersona  {

    private DAO<Persona>dao;

    public DAOPersonaImpl() {
        dao = new DAOImpl<>(Persona.class);
    }

    @Override
    public boolean create(Persona entidad) {
        return dao.create(entidad);
    }

    @Override
    public List<Persona> readAll() {
        return dao.readAll();
    }

    @Override
    public boolean update(Persona entidad) {
        return dao.update(entidad);
    }

    @Override
    public boolean delete(Persona entidad) {
        return dao.delete(entidad);
    }

    @Override
    public boolean save(Persona entidad) {
        return dao.save(entidad);
    }

    @Override
    public Persona findById(Long id) {
        return dao.findById(id);
    }

	@Override
	public List<Persona> readAllActives() {
		return dao.readAllActives();
	}

	@Override
	public boolean logicalDelete(Persona entidad) {
		return dao.logicalDelete(entidad);
	}




}
