package com.TpFinal.data.dao.interfaces;

import java.io.File;

import com.TpFinal.dto.contrato.Contrato;

public interface DAOContrato extends DAO<Contrato>{

    boolean mergeContrato(Contrato contrato, File doc);

}
