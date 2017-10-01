package com.TpFinal.services;

import com.TpFinal.data.dao.DAOContratoAlquilerImpl;
import com.TpFinal.data.dao.interfaces.DAOContratoAlquiler;
import com.TpFinal.data.dto.contrato.ContratoAlquiler;

import java.io.File;
import java.sql.Blob;
import java.util.List;

/**
 * Created by Max on 9/30/2017.
 */
public class ContratoAlquilerService {

    private DAOContratoAlquiler dao;

    public ContratoAlquilerService() {
        dao=new DAOContratoAlquilerImpl();
    }

    public List<ContratoAlquiler> readAll() { return dao.readAll(); }

    public boolean update(ContratoAlquiler entidad) {
        return dao.update(entidad);
    }

    public boolean delete(ContratoAlquiler entidad) {
        return dao.delete(entidad);
    }

    public boolean saveContrato(ContratoAlquiler entidad, File doc) {
        return dao.saveContrato(entidad,doc);
    }

    public boolean save(ContratoAlquiler entidad, File doc) {
        //transformo el doc al tipo que necesita hibernate
        ContratoUtil cu = new ContratoUtil();
        Blob docBlob = cu.fileToBlob(doc);
        //lo seteo en la entidad
        entidad.setDocumento(docBlob);
        return dao.save(entidad);
    }

    public ContratoAlquiler findById(Long id) { return dao.findById(id); }
}
