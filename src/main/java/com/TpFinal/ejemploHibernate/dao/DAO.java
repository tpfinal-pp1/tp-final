package com.TpFinal.ejemploHibernate.dao;

import com.TpFinal.ejemploHibernate.dto.Identificable;

import java.util.List;


public interface DAO<T extends Identificable> {
	public boolean create(T entidad);
	public List<T> readAll();
	public boolean update(T entidad);
	public boolean delete(T entidad);
	public T findById(Integer id);
}
