package com.TpFinal.data.dao;

import com.TpFinal.data.dao.interfaces.DAOContrato;
import com.TpFinal.data.dto.contrato.Contrato;

public class DAOContratoImpl extends DAOImpl<Contrato>implements DAOContrato{
	public DAOContratoImpl() {
		super(Contrato.class);
	}

}
