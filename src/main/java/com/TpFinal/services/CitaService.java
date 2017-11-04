package com.TpFinal.services;

import com.TpFinal.data.dao.DAOCitaImpl;
import com.TpFinal.dto.cita.Cita;
import com.TpFinal.dto.contrato.ContratoDuracion;
import com.TpFinal.dto.persona.CategoriaEmpleado;
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
        System.out.println("AGREGADA "+cita);
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

    public boolean editCita(Cita cita){
        System.out.println("EDITADA "+cita);
        Cita citaOriginal=null;
            List<Cita> citas=readAll();
            for (Cita citaGuardada:citas)
                if(citaGuardada.getId().equals(cita.getId()))
                    citaOriginal=cita;


        delete(citaOriginal);
        cita.setId(null);
        return addCita(cita);

    }

    public boolean delete(Cita p) {
        System.out.println("BORRADA"+p);
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


        return ret1;
    }

    public List<Cita> readAllFromUser(Empleado user){
        if(user.getCategoriaEmpleado().equals(CategoriaEmpleado.admin)){
            return readAll();
        }
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
