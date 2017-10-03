package com.TpFinal.services;

import com.TpFinal.data.dao.DAOContratoAlquilerImpl;
import com.TpFinal.data.dao.DAOContratoVentaImpl;
import com.TpFinal.data.dao.interfaces.DAOContratoAlquiler;
import com.TpFinal.data.dao.interfaces.DAOContratoVenta;
import com.TpFinal.data.dto.contrato.ContratoAlquiler;
import com.TpFinal.data.dto.contrato.ContratoVenta;

import java.io.File;
import java.sql.Blob;
import java.util.List;

/**
 * Created by Max on 9/30/2017.
 */
public class ContratoAlquilerService {

    private DAOContratoAlquiler daoAlquiler;
    private DAOContratoVenta daoVenta;

    public ContratoAlquilerService() {
        daoAlquiler=new DAOContratoAlquilerImpl();
        daoVenta= new DAOContratoVentaImpl();
    }

    public List<ContratoAlquiler> readAll() { return daoAlquiler.readAll(); }

    public boolean update(ContratoAlquiler entidad) {
        return daoAlquiler.update(entidad);
    }

    public boolean delete(ContratoAlquiler entidad) {
        return daoAlquiler.delete(entidad);
    }

    public boolean saveContrato(ContratoAlquiler entidad, File doc) {
    	if(entidad.getInquilinoContrato()!=null)
    		entidad.getInquilinoContrato().getContratos().add(entidad);
        return daoAlquiler.saveContrato(entidad,doc);
    }

    public ContratoAlquiler findById(Long id) { return daoAlquiler.findById(id); }
    
    @SuppressWarnings("unused")
	private boolean saveContratoVenta(ContratoVenta cv, File doc) {
    	return daoVenta.saveContrato(cv, doc);
    }
    
}
