package com.TpFinal.services;

import com.TpFinal.data.conexion.ConexionHibernate;
import com.TpFinal.data.dao.DAOContratoVentaImpl;
import com.TpFinal.data.dao.interfaces.DAOContratoVenta;
import com.TpFinal.data.dto.contrato.ContratoVentaDTO;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.hibernate.Hibernate;

/**
 * Created by Max on 9/29/2017.
 */
public class ContratoVentaService {

    private DAOContratoVenta dao;

    public ContratoVentaService() {
        dao=new DAOContratoVentaImpl();
    }

    public List<ContratoVentaDTO> readAll() { return dao.readAll(); }

    public boolean update(ContratoVentaDTO entidad) {
        return dao.update(entidad);
    }

    public boolean delete(ContratoVentaDTO entidad) {
        return dao.delete(entidad);
    }

    public boolean save(ContratoVentaDTO entidad, File doc) {
    	//transformo el doc al tipo que necesita hibernate
    	Blob docBlob=fileToBlob(doc);
    	//lo seteo en la entidad 
    	entidad.setDocumento(docBlob);
        return dao.save(entidad);
    }

    public ContratoVentaDTO findById(Long id) { return dao.findById(id); }
    
    public Blob fileToBlob(File doc)  {
    	Blob archivo=null;
    	try {
    		FileInputStream docInputStream= new FileInputStream(doc);
    		 archivo= Hibernate.getLobCreator(ConexionHibernate.openSession()).createBlob(docInputStream, doc.length());
    		
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return archivo;
	}
    
    @SuppressWarnings("resource")
	public boolean downloadFile(ContratoVentaDTO contrato, String downloadPath) {
    	boolean ret=true;
    	try {
			Blob docBlob=contrato.getDocumento();
			byte[] docBlobBytes = docBlob.getBytes(1, (int) docBlob.length());
			FileOutputStream docFOS=new FileOutputStream(downloadPath);
			docFOS.write(docBlobBytes);
		} catch (Exception e) {
			ret=false;
			e.printStackTrace();
		}
    	return ret;
    }
	
	//No se usan los dejo como referencia 
	public void leerArchivo(ContratoVentaDTO c, String path) throws SQLException, IOException {
		Blob blob = c.getDocumento();
		 byte[] blobBytes = blob.getBytes(1, (int) blob.length());
		 guardar(path, blobBytes);
	}
	
	public void guardar(String filePath, byte[] fileBytes) throws IOException {
		 FileOutputStream outputStream = new FileOutputStream(filePath);
	        outputStream.write(fileBytes);
	        outputStream.close();
	}
	
}
