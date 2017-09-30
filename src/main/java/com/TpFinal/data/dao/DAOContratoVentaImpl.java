package com.TpFinal.data.dao;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Blob;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.TpFinal.data.conexion.ConexionHibernate;
import com.TpFinal.data.dao.interfaces.DAOContratoVenta;
import com.TpFinal.data.dto.contrato.ContratoVenta;
import com.TpFinal.services.ContratoUtil;

public class DAOContratoVentaImpl extends DAOImpl<ContratoVenta> implements DAOContratoVenta {

	public DAOContratoVentaImpl() {
		super(ContratoVenta.class);
	}

	@Override
	public boolean saveContrato(ContratoVenta entidad, File doc) {

		// transformo el doc al tipo que necesita hibernate

		// lo seteo en la entidad


		boolean ret = false;
		Session session = ConexionHibernate.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			Blob archivo = null;
			try {
				FileInputStream docInputStream = new FileInputStream(doc);
				archivo = Hibernate.getLobCreator(session).createBlob(docInputStream, doc.length());
			} catch (Exception e) {
				e.printStackTrace();
			}

			entidad.setDocumento(archivo);

			session.saveOrUpdate(entidad);
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

}
