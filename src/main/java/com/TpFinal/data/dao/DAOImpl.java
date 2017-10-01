package com.TpFinal.data.dao;



import com.TpFinal.data.dto.DashboardNotification;

import com.TpFinal.data.dto.dummy.User;
import com.TpFinal.data.conexion.ConexionHibernate;
import com.TpFinal.data.dao.interfaces.DAO;
import com.TpFinal.data.dto.Identificable;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class DAOImpl<T extends Identificable> implements DAO<T> {
	private Class<T> clase;

	private DAOImpl() {

	};

	public DAOImpl(Class<T> clase) {
		this.clase = clase;
	}

	@Override
	public boolean create(T entidad) {
		boolean resultado = false;
		Session session = ConexionHibernate.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(entidad);
			tx.commit();
			resultado = true;
		} catch (HibernateException e) {
			System.err.println("---------------------------------------------");
			System.err.println("---------------------------------------------");
			System.err.println("Error al intentar crear: ");
			System.err.println(entidad);
			System.err.println("---------------------------------------------");
			System.err.println("---------------------------------------------");
			
			e.printStackTrace();
		} finally {
			session.close();
		}
		return resultado;
	}



	@SuppressWarnings("unchecked")
	@Override
	public List<T> readAll() {
		List<T> entidades = new ArrayList<T>();
		Session session = ConexionHibernate.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			entidades = session.createQuery("from " + getClaseEntidad().getSimpleName()).list();
			tx.commit();
		} catch (HibernateException e) {
			System.err.println("Error al leer");
			e.printStackTrace();
		} finally {
			session.close();
		}
		return entidades;
	}

	@Override
	public boolean update(T entidad) {
		boolean ret = false;
		Session session = ConexionHibernate.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.update(entidad);
			tx.commit();
			ret = true;
		} catch (HibernateException e) {
			System.err.println("Error al leer");
			e.printStackTrace();
			tx.rollback();
		} finally {
			session.close();
		}
		return ret;
	}



	@Override
	public boolean save(T entidad) {
		if(exists(entidad)){
			return update(entidad);
		}
		return create(entidad);

	}


	public boolean exists(T entidad){
		if(entidad.getId()==null){
			return false;
		}
		T ret= findById(entidad.getId());
		if(ret!=null){
			return true;
		}
		return false;
	}

	@Override
	public boolean delete(T entidad) {
		boolean ret = false;
		Session session = ConexionHibernate.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			@SuppressWarnings("unchecked")
			T toDelete = (T) session.get(entidad.getClass(), entidad.getId());
			session.delete(toDelete);
			tx.commit();
			ret = true;
		} catch (HibernateException e) {
			System.err.println("Error al leer");
			e.printStackTrace();
			tx.rollback();
		} finally {
			session.close();
		}
		return ret;
	}

	public Class<T> getClaseEntidad() {
		return clase;
	}

	@Override
	public T findById(Long id) {
		T entidad = null;
		Session session = ConexionHibernate.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			entidad = session.get(clase, id);
			tx.commit();
		} catch (HibernateException e) {
			System.err.println("Error al leer");
			e.printStackTrace();
			tx.rollback();
		} finally {
			session.close();
		}
		return entidad;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByCriteria(DetachedCriteria criteria) {
		List<T> entidades = new ArrayList<T>();
		Session session = ConexionHibernate.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			entidades = criteria.getExecutableCriteria(session).list();
			tx.commit();
		} catch (HibernateException e) {
			System.err.println("---------------------------------");
			System.err.println("---------------------------------");
			System.err.println("Error al ejecutar criteria Query");
			System.err.println(criteria.toString());
			System.err.println("---------------------------------");
			System.err.println("---------------------------------");
			e.printStackTrace();
			tx.rollback();
		} finally {
			session.close();
		}
		return entidades;
	}


	public User authenticate(String userName, String password) {
		return null;
	}


	public int getUnreadNotificationsCount() {
		return 0;
	}


	public Collection<DashboardNotification> getNotifications() {
		return null;
	}



}
