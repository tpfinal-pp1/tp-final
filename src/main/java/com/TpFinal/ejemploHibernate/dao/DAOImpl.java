package com.TpFinal.ejemploHibernate.dao;

import com.TpFinal.ejemploHibernate.conexion.ConexionHibernate;
import com.TpFinal.ejemploHibernate.dto.DomicilioDTO;
import com.TpFinal.ejemploHibernate.dto.Identificable;
import com.TpFinal.ejemploHibernate.dto.PersonaDTO;
import com.TpFinal.ejemploHibernate.dto.TipoMascota;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class DAOImpl<T extends Identificable> implements DAO<T> {
	private Class<T> clase;

	public DAOImpl() {
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
			System.err.println("Error al leer");
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
		} finally {
			session.close();
		}
		return ret;
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
		} finally {
			session.close();
		}
		return ret;
	}

	public Class<T> getClaseEntidad() {
		return clase;
	}

	@Override
	public T findById(Integer id) {
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
		} finally {
			session.close();
		}
		return entidad;
	}

	public static void main(String[] args) {
		DAOImpl<PersonaDTO> dao = new DAOImpl<>(PersonaDTO.class);
		System.out.println(dao.getClaseEntidad().getSimpleName());
		PersonaDTO p = new PersonaDTO("Persona a borrar", "1233 4567", "unmail@mail.com", LocalDate.now(),
				TipoMascota.Perro, new DomicilioDTO("Una calle", 123, 1, "algo"));
		PersonaDTO p2 = new PersonaDTO("Persona a modificar", "0000 0000", "unmail@mail.com", LocalDate.now(),
				TipoMascota.Gato, new DomicilioDTO("Una calle", 123, 1, "algo"));
		dao.create(p);
		dao.create(p2);
		dao.readAll().forEach(System.out::println);
		dao.delete(p);

		p2.setNombre("Nombre Modificado");
		dao.update(p2);
		dao.readAll().forEach(System.out::println);
		ConexionHibernate.close();
	}

}
