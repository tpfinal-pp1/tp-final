package com.TpFinal.services;

import com.TpFinal.data.dao.DAOContratoVentaImpl;
import com.TpFinal.data.dao.interfaces.DAOContratoVenta;
import com.TpFinal.data.dto.contrato.ContratoVenta;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.sql.Blob;
import java.util.List;

/**
 * Created by Max on 9/29/2017.
 */
public class ContratoVentaService {

    private DAOContratoVenta dao;

    public ContratoVentaService() {
        dao=new DAOContratoVentaImpl();
    }

    public List<ContratoVenta> readAll() { return dao.readAll(); }

    public boolean update(ContratoVenta entidad) {
        return dao.update(entidad);
    }

    public boolean delete(ContratoVenta entidad) {
        return dao.delete(entidad);
    }

    public boolean save(ContratoVenta entidad, File doc) {
    	//transformo el doc al tipo que necesita hibernate
    	ContratoUtil cu = new ContratoUtil();
    	Blob docBlob=cu.fileToBlob(doc);
    	//lo seteo en la entidad 
    	entidad.setDocumento(docBlob);
        return dao.save(entidad);
    }

    public ContratoVenta findById(Long id) { return dao.findById(id); }
    
  
	
}
