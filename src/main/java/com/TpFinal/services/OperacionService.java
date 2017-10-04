package com.TpFinal.services;

import com.TpFinal.data.dao.DAOInmuebleImpl;
import com.TpFinal.data.dao.DAOOperacionImpl;
import com.TpFinal.data.dao.interfaces.DAOInmueble;
import com.TpFinal.data.dao.interfaces.DAOOperacion;
import com.TpFinal.data.dto.inmueble.CriterioBusquedaInmuebleDTO;
import com.TpFinal.data.dto.inmueble.Inmueble;
import com.TpFinal.data.dto.operacion.Operacion;

import java.util.List;

public class OperacionService {
    private DAOOperacion dao;

    public OperacionService() {
        dao = new DAOOperacionImpl();
    }

    public List<Operacion> findAll(String busqueda) {
        return dao.readAll();
    }

    public boolean update(Operacion entidad) {
        return dao.update(entidad);
    }

    public boolean delete(Operacion entidad) {
        return dao.delete(entidad);
    }

    public boolean save(Operacion entidad) {
        return dao.save(entidad);
    }

    public Operacion findById(Long id) {
        return dao.findById(id);
    }



}
