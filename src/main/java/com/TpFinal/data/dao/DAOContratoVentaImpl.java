package com.TpFinal.data.dao;

import com.TpFinal.data.dao.interfaces.DAOContratoVenta;
import com.TpFinal.data.dto.contrato.ContratoVentaDTO;

public class DAOContratoVentaImpl extends DAOImpl<ContratoVentaDTO> implements DAOContratoVenta{

	public DAOContratoVentaImpl() {
		super(ContratoVentaDTO.class);
	}

}
