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
import com.TpFinal.data.dao.interfaces.DAOContrato;
import com.TpFinal.dto.contrato.Archivo;
import com.TpFinal.dto.contrato.Contrato;
import com.TpFinal.dto.contrato.ContratoVenta;

public class DAOContratoImpl extends DAOImpl<Contrato> implements DAOContrato {
    public DAOContratoImpl() {
	super(Contrato.class);
    }

    public boolean mergeContrato(Contrato entidad, File doc) {
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
	    System.err.println("Error al realizar Merge: " + entidad);
	    e.printStackTrace();
	    if (tx != null)
		tx.rollback();
	} finally {
	    session.close();
	    if (docInputStream != null)
		try {
		    docInputStream.close();
		} catch (IOException e) {
		    System.err.println("Error cerrar el archivo: " + docInputStream);
		    e.printStackTrace();
		}
	}
	return ret;
    }

}
