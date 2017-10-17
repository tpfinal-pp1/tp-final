package com.TpFinal.data.dao.interfaces;

import java.io.File;

import com.TpFinal.UnitTests.dto.contrato.ContratoVenta;

public interface DAOContratoVenta extends DAO<ContratoVenta>{

	boolean saveOrUpdateContrato(ContratoVenta entidad, File doc);

	boolean mergeContrato(ContratoVenta c, File doc);

}
