package com.TpFinal.data.dao;

import java.util.Collection;
import java.util.List;

import com.TpFinal.data.dao.interfaces.DAO;
import com.TpFinal.data.dao.interfaces.DAOPropietario;
import com.TpFinal.domain.DashboardNotification;
import com.TpFinal.domain.dummy.User;
import com.TpFinal.domain.persona.Propietario;

public class DAOPropietarioImpl implements DAOPropietario{
	
	private DAO<Propietario> dao;
	
	public DAOPropietarioImpl() {
		dao = new DAOImpl<>(Propietario.class);
	}

	@Override
	public boolean create(Propietario entidad) {
		return dao.create(entidad);
	}

	@Override
	public List<Propietario> readAll() {
		return dao.readAll();
	}

	@Override
	public boolean update(Propietario entidad) {
		return dao.update(entidad);
	}

	@Override
	public boolean delete(Propietario entidad) {
		return dao.delete(entidad);
	}

	@Override
	public boolean save(Propietario entidad) {
		return dao.save(entidad);
	}

	@Override
	public Propietario findById(Long id) {
		return dao.findById(id);
	}

	@Override
	public User authenticate(String userName, String password) {
		return dao.authenticate(userName, password);
	}

	@Override
	public int getUnreadNotificationsCount() {
		return dao.getUnreadNotificationsCount();
	}

	@Override
	public Collection<DashboardNotification> getNotifications() {
		return dao.getNotifications();
	}
	
	
}
