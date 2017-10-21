package com.TpFinal.data.dao;

import com.TpFinal.data.dao.interfaces.DAOInmobiliaria;
import com.TpFinal.dto.inmobiliaria.Inmobiliaria;

public class DAOInmobiliariaImpl extends DAOImpl<Inmobiliaria> implements DAOInmobiliaria {
	
	public DAOInmobiliariaImpl() {
		super(Inmobiliaria.class);
	}
}
