package com.TpFinal.services;

import com.TpFinal.data.dao.DAOCitaImpl;
import com.TpFinal.dto.cita.Cita;
import com.TpFinal.dto.contrato.ContratoDuracion;
import org.h2.table.Plan;

import java.util.ArrayList;
import java.util.List;

public class CitaService {
    DAOCitaImpl dao;

    public CitaService(){
        dao =new DAOCitaImpl();

    }

    private boolean saveOrUpdate(Cita cita) {
        return dao.saveOrUpdate(cita);
    }

    public boolean addCita(Cita cita){
       boolean b= saveOrUpdate(cita);
       ArrayList citas=new ArrayList<>();
       citas.add(cita);
       if(b)
           Planificador.get().agregarNotificaciones(citas);
       return b;
    }

    /*public boolean editCita(Cita cita){


    }
*/
    public boolean delete(Cita p) {
        return dao.logicalDelete(p);
    }

    public List<Cita> readAll(){
        return dao.readAllActives();
    }






}
