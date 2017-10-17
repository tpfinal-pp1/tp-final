package com.TpFinal.data.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Blob;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.TpFinal.data.conexion.ConexionHibernate;
import com.TpFinal.data.dao.interfaces.DAOContratoVenta;
import com.TpFinal.dto.contrato.ContratoVenta;

public class DAOContratoVentaImpl extends DAOImpl<ContratoVenta> implements DAOContratoVenta {

	public DAOContratoVentaImpl() {
		super(ContratoVenta.class);
	}

	@Override
	public boolean saveOrUpdateContrato(ContratoVenta entidad, File doc) {
		boolean ret = false;
		FileInputStream docInputStream = null;
		Session session = ConexionHibernate.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			Blob archivo = null;

			docInputStream = new FileInputStream(doc);
			archivo = Hibernate.getLobCreator(session).createBlob(docInputStream, doc.length());

			entidad.setDocumento(archivo);

			session.saveOrUpdate(entidad);
			tx.commit();
			ret = true;
		} catch (HibernateException | FileNotFoundException e) {
			System.err.println("Error al leer");
			e.printStackTrace();
			tx.rollback();
		} finally {
			session.close();
			if (docInputStream != null)
				try {
					docInputStream.close();
				} catch (IOException e) {

				}
		}
		return ret;
	}
	
	@Override
	public boolean mergeContrato(ContratoVenta entidad, File doc) {
		boolean ret = false;
		FileInputStream docInputStream = null;
		Session session = ConexionHibernate.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			Blob archivo = null;

			docInputStream = new FileInputStream(doc);
			archivo = Hibernate.getLobCreator(session).createBlob(docInputStream, doc.length());

			entidad.setDocumento(archivo);

			session.merge(entidad);
			tx.commit();
			ret = true;
		} catch (HibernateException | FileNotFoundException e) {
			System.err.println("Error al leer");
			e.printStackTrace();
			tx.rollback();
		} finally {
			session.close();
			if (docInputStream != null)
				try {
					docInputStream.close();
				} catch (IOException e) {

				}
		}
		return ret;
	}

}
