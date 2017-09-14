package com.TpFinal.ejemploHibernate.dao;

import com.TpFinal.ejemploHibernate.conexion.ConexionHibernate;
import com.TpFinal.ejemploHibernate.dto.DomicilioDTO;
import com.TpFinal.ejemploHibernate.dto.PersonaDTO;
import com.TpFinal.ejemploHibernate.dto.TipoMascota;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.List;


public class PersonaDAO {

	public List<PersonaDTO>readAll(){
		List<PersonaDTO>ret=null;
		Session session= ConexionHibernate.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			//Teoricamente con el mapeo ya no hace falta hacer el join, automaticamente trae todo
			ret=session.createQuery("FROM PersonaDTO").list();
			tx.commit();
		} catch (HibernateException e) {
			System.err.println("Error al leer");
			e.printStackTrace();
		}finally {
			session.close();
		}
		return ret;
	}
	
	public boolean addPersona(PersonaDTO p) {
		boolean ret=false;
		Session session=ConexionHibernate.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(p);
			tx.commit();
			ret=true;
		} catch (HibernateException e) {
			System.err.println("Error al leer");
			e.printStackTrace();
		}
		finally {
			session.close();
		}
		return ret;
	}
	
	public boolean deletePersona(PersonaDTO p) {
		//borra en cascada el domicilio
		boolean ret=false;
		Session session=ConexionHibernate.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			PersonaDTO toDelete=session.get(PersonaDTO.class, p.getIdPersona());
			session.delete(toDelete);
			tx.commit();
			ret=true;
		} catch (HibernateException e) {
			System.err.println("Error al leer");
			e.printStackTrace();
		}
		finally {
			session.close();
		}
		return ret;
	}
	
	public boolean update(PersonaDTO original, PersonaDTO modi) {
		boolean ret=false;
		Session session=ConexionHibernate.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			PersonaDTO toUpdate=session.get(PersonaDTO.class, original.getIdPersona());
			toUpdate.setNombre(modi.getNombre());
			toUpdate.setTelefono(modi.getTelefono());
			toUpdate.setMail(modi.getMail());
			toUpdate.setFechaCumpleaños(modi.getFechaCumpleaños());
			toUpdate.setDomicilio(modi.getDomicilio());
			session.update(toUpdate);
			tx.commit();
			ret=true;
		} catch (HibernateException e) {
			System.err.println("Error al leer");
			e.printStackTrace();
		}
		finally {
			session.close();
		}
		return ret;
	}
	
	public static void main(String[] args) {
		PersonaDAO dao = new PersonaDAO();
		DomicilioDTO d = new DomicilioDTO("nuevaaaaaaa", 12, 12, "n1");
		PersonaDTO p1 = new PersonaDTO("updated", "dasdas", "updas", LocalDate.of(200, 12, 1), TipoMascota.Gato, d);
		dao.update(new PersonaDTO(1), p1);
		List<PersonaDTO>personas=dao.readAll();
		personas.forEach(p -> System.out.println(p.getNombre()+" "+p.getDomicilio().getCalle()));
		//XXX recordar esto al cerrar el programa
		ConexionHibernate.close();
	}
}
