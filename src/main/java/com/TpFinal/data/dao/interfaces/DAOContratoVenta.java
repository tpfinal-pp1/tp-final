package com.TpFinal.data.dao.interfaces;

import java.io.File;

import com.TpFinal.data.dto.contrato.ContratoVenta;

public interface DAOContratoVenta extends DAO<ContratoVenta>{

	boolean saveContrato(ContratoVenta entidad, File doc);

}
