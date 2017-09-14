package com.PubliciBot.DAO.Interfaces;

public interface DAO<T> {
	
	public void guardar(T t);
	public void eliminar(T t);

}
