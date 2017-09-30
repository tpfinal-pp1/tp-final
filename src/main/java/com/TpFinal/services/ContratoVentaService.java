package com.TpFinal.services;

import com.TpFinal.data.conexion.ConexionHibernate;
import com.TpFinal.data.dao.DAOContratoVentaImpl;
import com.TpFinal.data.dao.interfaces.DAOContratoVenta;
import com.TpFinal.data.dto.contrato.ContratoVentaDTO;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
    	ContratoUtil cu = new ContratoUtil();
    	Blob docBlob=cu.fileToBlob(doc);
    	//lo seteo en la entidad 
    	entidad.setDocumento(docBlob);
        return dao.save(entidad);
    }

    public ContratoVentaDTO findById(Long id) { return dao.findById(id); }
    
  
	
}
