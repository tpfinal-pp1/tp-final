package com.TpFinal.DAO.Interfaces;

import com.TpFinal.DM.Campana;
import com.TpFinal.DM.Usuario;

import java.util.Map;

/**
 * Created by Max on 6/4/2017.
 */
public interface CampanaDAO extends DAO<Campana> {

    Map<Long,Campana> recuperarCampanas(Usuario usuario);


}
