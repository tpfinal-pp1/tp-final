package com.TpFinal.services;

import com.TpFinal.data.dao.DAOPropietarioImpl;
import com.TpFinal.data.dao.interfaces.DAOPropietario;
import com.TpFinal.data.dto.PropietarioDTO;

import java.util.List;

/**
 * Created by Max on 9/29/2017.
 */
public class PropietarioService {

    private DAOPropietario dao;

    public PropietarioService() {
        dao=new DAOPropietarioImpl();
    }

    public List<PropietarioDTO> readAll() { return dao.readAll(); }

    public boolean update(PropietarioDTO entidad) {
        return dao.update(entidad);
    }

    public boolean delete(PropietarioDTO entidad) {
        return dao.delete(entidad);
    }

    public boolean save(PropietarioDTO entidad) {
        return dao.save(entidad);
    }

    public PropietarioDTO findById(Long id) { return dao.findById(id); }
}
