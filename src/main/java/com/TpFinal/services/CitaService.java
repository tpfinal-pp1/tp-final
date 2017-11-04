package com.TpFinal.services;

import com.TpFinal.data.dao.DAOCitaImpl;
import com.TpFinal.dto.cita.Cita;
import com.TpFinal.dto.contrato.ContratoDuracion;
import com.TpFinal.dto.persona.Empleado;
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
       Cita citaConId=null;
        if(b){
            List<Cita> citas=readAll();
            for (Cita citaGuardada:citas)
                if(citaGuardada.equals(cita))
                    citaConId=cita;
        }

       if(b&&(citaConId!=null)){
           b=b&&!Planificador.get().removeCita(citaConId);
            Planificador.get().addCita(citaConId);
        }
       else{
           System.err.println("Error al Agregar la Cita"+cita);
       }

       if(!b)
           System.err.println("Error de Planificador");
       return b;
    }

    public Cita getCitaFromTriggerKey(String triggerKey){
        int corte=triggerKey.indexOf('-');

        for (Cita cita:readAll()){
            if(cita.getId().toString().equals(triggerKey.substring(0,corte))){
                return cita;
            }
        }
        return null;
    }
    
    public Cita getUltimaAgregada() {
    	List<Cita>citas=dao.readAllActives();
    	citas.sort((c1,c2)-> c1.getId().compareTo(c2.getId()));
    	return citas.get(0);
    }

    public boolean editCita(Cita cita){
        boolean b= Planificador.get().removeCita(cita);
        if(!b)
            System.err.println("Error al Borrar los recodatorios de la cita..");
        b=b&&saveOrUpdate(cita);
        if(b)
            Planificador.get().addCita(cita);
        else{
            System.err.println("Error al editar la cita"+cita);
        }

        return b;

    }

    public boolean delete(Cita p) {
        boolean ret1=true;
        boolean ret2=true;

        ret1=dao.logicalDelete(p);
        if(!ret1){
            System.err.println("Error al Borrar la cita..");
        }
        ret2=Planificador.get().removeCita(p);
        if(!ret2){
            System.err.println("Error al Borrar los recodatorios de la cita... \nes probable que ya se hayan detonado los triggers");
        }


        return ret1&&ret2;
    }

    public List<Cita> readAllFromUser(Empleado user){
        List<Cita> ret=new ArrayList<>();
        for (Cita cita:readAll()){
            if(cita.getEmpleado().equals(user)){
               ret.add(cita);
            }}

        return ret;
    }

    private List<Cita> readAll(){
        return dao.readAllActives();
    }






}
