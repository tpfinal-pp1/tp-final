package com.TpFinal.data.dao;

import java.util.Collection;
import java.util.List;

import com.TpFinal.data.dao.interfaces.DAO;
import com.TpFinal.data.dao.interfaces.InquilinoDAO;
import com.TpFinal.data.dto.DashboardNotification;
import com.TpFinal.data.dto.InquilinoDTO;
import com.TpFinal.data.dto.User;


public class InquilinoDAOImpl implements InquilinoDAO  {
	
	private DAO<InquilinoDTO>dao;
	
	public InquilinoDAOImpl() {
		dao = new DAOImpl<>(InquilinoDTO.class);
	}

	@Override
	public boolean create(InquilinoDTO entidad) {
		return dao.create(entidad);
	}

	@Override
	public List<InquilinoDTO> readAll() {
		return dao.readAll();
	}

	@Override
	public boolean update(InquilinoDTO entidad) {
		return dao.update(entidad);
	}

	@Override
	public boolean delete(InquilinoDTO entidad) {
		return dao.delete(entidad);
	}

	@Override
	public boolean save(InquilinoDTO entidad) {
		return dao.save(entidad);
	}

	@Override
	public InquilinoDTO findById(Long id) {
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
