package com.TpFinal.data.dao;

import com.TpFinal.data.dao.interfaces.DAONotificacion;
import com.TpFinal.dto.notificacion.Notificacion;

public class DAONotificacionImpl extends DAOImpl<Notificacion> implements DAONotificacion {
	
	public DAONotificacionImpl() {
		super(Notificacion.class);
	}

}
