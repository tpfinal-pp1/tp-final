package com.TpFinal.data.dao.interfaces;


import com.TpFinal.data.dto.DashboardNotification;
import com.TpFinal.data.dto.dummy.User;
import com.TpFinal.data.dto.Identificable;

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
        throw new RuntimeException("Metodo FindByCriteria no Implementado!");
    } 

	


}
