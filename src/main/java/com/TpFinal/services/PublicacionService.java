package com.TpFinal.services;

import com.TpFinal.data.dao.DAOInmuebleImpl;
import com.TpFinal.data.dao.DAOPublicacionImpl;
import com.TpFinal.data.dao.interfaces.DAOInmueble;
import com.TpFinal.data.dao.interfaces.DAOPublicacion;
import com.TpFinal.data.dto.inmueble.CriterioBusquedaInmuebleDTO;
import com.TpFinal.data.dto.inmueble.Inmueble;
import com.TpFinal.data.dto.publicacion.Publicacion;

import java.util.List;

public class PublicacionService {
    private DAOPublicacion dao;

    public PublicacionService() {
        dao = new DAOPublicacionImpl();
    }

    public List<Publicacion> findAll(String busqueda) {
        return dao.readAll();
    }

    public boolean update(Publicacion entidad) {
        return dao.update(entidad);
    }

    public boolean delete(Publicacion entidad) {
        return dao.delete(entidad);
    }

    public boolean save(Publicacion entidad) {
        return dao.save(entidad);
    }

    public Publicacion findById(Long id) {
        return dao.findById(id);
    }



}
