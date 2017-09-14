package com.TpFinal.ejemploHibernate.dao;


import com.TpFinal.ejemploHibernate.conexion.ConexionHibernate;
import com.TpFinal.ejemploHibernate.dto.DomicilioDTO;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;


public class DomicilioDAO {

	
	public List<DomicilioDTO> readAll(){
		List<DomicilioDTO> ret = null;
		Session session= ConexionHibernate.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			ret = (List<DomicilioDTO>)session.createQuery("FROM DomicilioDTO").list();
			tx.commit();
		} catch (HibernateException  e) {
			System.err.println("Error al leer");
			e.printStackTrace();
		}
		finally {
			session.close();
		}
		return ret;
	}
	
	public boolean addDomicilio(DomicilioDTO d) {
		boolean ret = false;
		Session session= ConexionHibernate.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(d);
			tx.commit();
			ret=true;
		} catch (HibernateException  e) {
			System.err.println("Error al guardar: ");
			e.printStackTrace();
		}
		finally {
			session.close();
		}
		return ret;
	}
	
	public boolean deleteDomicilio(DomicilioDTO d) {
		boolean ret = false;
		Session session= ConexionHibernate.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			DomicilioDTO toDelete=session.get(DomicilioDTO.class, d.getIdDomicilio());
			session.delete(toDelete);
			tx.commit();
			ret=true;
		} catch (HibernateException  e) {
			System.err.println("Error al guardar: ");
			e.printStackTrace();
		}
		finally {
			session.close();
		}
		return ret;
	}
	
	public boolean update(DomicilioDTO original, DomicilioDTO modificacion) {
		boolean ret = false;
		Session session= ConexionHibernate.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			DomicilioDTO toUpdate=session.get(DomicilioDTO.class, original.getIdDomicilio());
			toUpdate.setCalle(modificacion.getCalle());
			toUpdate.setAltura(modificacion.getAltura());
			toUpdate.setPiso(modificacion.getPiso());
			toUpdate.setDepartamento(modificacion.getDepartamento());
			session.update(toUpdate);
			tx.commit();
			ret=true;
		} catch (HibernateException  e) {
			System.err.println("Error al guardar: ");
			e.printStackTrace();
		}
		finally {
			session.close();
		}
		return ret;
	}
	
	public static void main(String[] args) {
		DomicilioDAO dao = new DomicilioDAO();
		dao.update(new DomicilioDTO(3,null,2,2,null), new DomicilioDTO("Upda", 12, 12, "update"));
		List<DomicilioDTO>domicilios=dao.readAll();
		domicilios.forEach(d -> System.out.println(d.getIdDomicilio()+" "+d.getCalle()));
		ConexionHibernate.close();
		
	}
}
