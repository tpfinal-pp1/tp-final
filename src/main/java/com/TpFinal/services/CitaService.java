package com.TpFinal.services;

import com.TpFinal.data.dao.DAOCitaImpl;
import com.TpFinal.dto.cita.Cita;
import com.TpFinal.dto.contrato.ContratoDuracion;

import java.util.List;

public class CitaService {
    DAOCitaImpl dao;

    public CitaService(){
        dao =new DAOCitaImpl();

    }

    public boolean saveOrUpdate(Cita cita) {
        return dao.saveOrUpdate(cita);
    }
    public boolean delete(Cita p) {
        return dao.logicalDelete(p);
    }

    public List<Cita> readAll(){
        return dao.readAllActives();
    }






}
