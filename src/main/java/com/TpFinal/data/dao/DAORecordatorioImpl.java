package com.TpFinal.data.dao;

import com.TpFinal.data.dao.interfaces.DAOCita;
import com.TpFinal.data.dao.interfaces.DAOContratoDuracion;
import com.TpFinal.data.dao.interfaces.DAORecordatorio;
import com.TpFinal.dto.cita.Cita;
import com.TpFinal.dto.cita.Recordatorio;


public class DAORecordatorioImpl extends DAOImpl<Recordatorio> implements DAORecordatorio{
	public DAORecordatorioImpl() {
		super(Recordatorio.class);
	}
}