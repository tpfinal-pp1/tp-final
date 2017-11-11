package com.TpFinal.data.dao;

import com.TpFinal.data.dao.interfaces.DAOMovimiento;
import com.TpFinal.dto.movimiento.Movimiento;

public class DAOMovimientoImpl extends DAOImpl<Movimiento> implements DAOMovimiento{
	public DAOMovimientoImpl() {
		super(Movimiento.class);
	}

}
