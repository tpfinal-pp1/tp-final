package com.TpFinal.data.dao;

import com.TpFinal.data.dao.DAOImpl;
import com.TpFinal.data.dao.interfaces.DAOInmueble;
import com.TpFinal.data.dto.inmueble.Inmueble;

public class DAOInmuebleImpl extends DAOImpl<Inmueble> implements DAOInmueble{

	public DAOInmuebleImpl(Class<Inmueble> clase) {
		super(Inmueble.class);		
	}

}
