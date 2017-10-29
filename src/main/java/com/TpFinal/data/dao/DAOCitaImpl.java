package com.TpFinal.data.dao;

import com.TpFinal.data.dao.interfaces.DAOCita;
import com.TpFinal.data.dao.interfaces.DAOContratoDuracion;
import com.TpFinal.dto.cita.Cita;


public class DAOCitaImpl extends DAOImpl<Cita> implements DAOCita{
	public DAOCitaImpl() {
		super(Cita.class);
	}
}