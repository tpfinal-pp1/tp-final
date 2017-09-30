package com.TpFinal.data.dao;

import com.TpFinal.data.dao.interfaces.DAO;
import com.TpFinal.data.dao.interfaces.DAOPropietario;

import com.TpFinal.data.dto.Propietario;

import java.util.List;

/**
 * Created by Max on 9/29/2017.
 */
public class DAOPropietarioImpl implements DAOPropietario{

    private DAO<Propietario> dao;

    public DAOPropietarioImpl() { dao = new DAOImpl<>(Propietario.class); }
    @Override
    public boolean create(Propietario entidad) { return dao.create(entidad);}

    @Override
    public List<Propietario> readAll() { return dao.readAll();}

    @Override
    public boolean update(Propietario entidad) { return dao.update(entidad); }

    @Override
    public boolean delete(Propietario entidad) { return dao.delete(entidad); }

    @Override
    public boolean save(Propietario entidad) { return dao.save(entidad); }

    @Override
    public Propietario findById(Long id) { return dao.findById(id); }


}
