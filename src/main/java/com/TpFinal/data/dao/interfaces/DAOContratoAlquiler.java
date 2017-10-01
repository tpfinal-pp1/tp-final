package com.TpFinal.data.dao.interfaces;

import com.TpFinal.data.dto.contrato.ContratoAlquiler;

import java.io.File;

/**
 * Created by Max on 9/30/2017.
 */
public interface DAOContratoAlquiler extends DAO<ContratoAlquiler>{
    boolean saveContrato(ContratoAlquiler entidad, File doc);
}
