package com.TpFinal.data.dao;

import com.TpFinal.data.dao.interfaces.DAOContratoVenta;
import com.TpFinal.data.dto.contrato.ContratoVenta;

public class DAOContratoVentaImpl extends DAOImpl<ContratoVenta> implements DAOContratoVenta{

	public DAOContratoVentaImpl() {
		super(ContratoVenta.class);
	}

}
