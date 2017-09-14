package com.PubliciBot.Services;

import com.PubliciBot.DAO.Neodatis.CampanaDAONeodatis;
import com.PubliciBot.DAO.Neodatis.DAONeodatis;
import com.PubliciBot.DM.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Hugo on 22/05/2017.
 */
public class CampanaService {

    private HashMap<Long,Campana> campanasGuardadas;
    private CampanaDAONeodatis campanaDao;

    public CampanaService(){
        this.campanasGuardadas = new HashMap<Long,Campana>();
        this.campanaDao = new CampanaDAONeodatis();
    }

    public void establecerEstado(Campana campana, EstadoCampana estadoCampana)
    {
        campana.setEstadoCampana(estadoCampana);
    }



    public void agregarTagACampana(Campana campana, Tag tag)
    {
        campana.addTags(tag);
    }

    public void agregarAccionPublicitariaACampana(Campana campana, AccionPublicitaria accion)
    {
        campana.addAccion(accion);
    }

    public void asignarDuracion(Campana campana, int dias,UnidadMedida unidad)
    {
        campana.setDuracion(dias);
        campana.setUnidadMedida(unidad);
    }




    public void recuperarCampanas(Usuario usuario){
        this.campanasGuardadas = (HashMap<Long,Campana>) campanaDao.recuperarCampanas(usuario);
    }

    public HashMap<Long,Campana> getCampanasGuardadas(){
        return this.campanasGuardadas;
    }


    public List<Campana> findAll() {
        ArrayList arrayList = new ArrayList();
        for (Campana campana : campanasGuardadas.values()) {
            arrayList.add(campana);
        }
        return arrayList;
    }

}
