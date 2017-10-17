package com.TpFinal.data.dao;

import com.TpFinal.data.dao.interfaces.DAOContratoDuracion;
import com.TpFinal.UnitTests.dto.contrato.ContratoDuracion;

public class DAOContratoDuracionImpl extends DAOImpl<ContratoDuracion> implements DAOContratoDuracion{
	public DAOContratoDuracionImpl() {
		super(ContratoDuracion.class);
	}
}