package com.TpFinal.services;

import com.TpFinal.data.dao.DAOContratoVentaImpl;
import com.TpFinal.data.dao.interfaces.DAOContratoVenta;
import com.TpFinal.data.dto.contrato.ContratoVentaDTO;

import java.util.List;

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

    public boolean save(ContratoVentaDTO entidad) {
        return dao.save(entidad);
    }

    public ContratoVentaDTO findById(Long id) { return dao.findById(id); }
}
