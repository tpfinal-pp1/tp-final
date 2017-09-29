package com.TpFinal.data.dao.interfaces;


import com.TpFinal.domain.DashboardNotification;
import com.TpFinal.domain.dummy.User;
import com.TpFinal.domain.Identificable;

import java.util.Collection;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;


public interface DAO<T extends Identificable> {
	public boolean create(T entidad);
	public List<T> readAll();
	public boolean update(T entidad);
	public boolean delete(T entidad);
	public boolean save(T entidad);
	public T findById(Long id);
	default public List<T> findByCriteria(DetachedCriteria criteria) {
        throw new RuntimeException("Metodo FindById no Implementado!");
    }  
	User authenticate(String userName, String password);
	int getUnreadNotificationsCount();
	Collection<DashboardNotification> getNotifications();
	
	 


}
