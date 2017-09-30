package com.TpFinal.services;

import com.TpFinal.data.dao.DAOPropietarioImpl;
import com.TpFinal.data.dao.interfaces.DAOPropietario;
import com.TpFinal.data.dto.Propietario;

import java.util.List;

/**
 * Created by Max on 9/29/2017.
 */
public class PropietarioService {

    private DAOPropietario dao;

    public PropietarioService() {
        dao=new DAOPropietarioImpl();
    }

    public List<Propietario> readAll() { return dao.readAll(); }

    public boolean update(Propietario entidad) {
        return dao.update(entidad);
    }

    public boolean delete(Propietario entidad) {
        return dao.delete(entidad);
    }

    public boolean save(Propietario entidad) {
        return dao.save(entidad);
    }

    public Propietario findById(Long id) { return dao.findById(id); }
}
