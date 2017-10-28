package com.TpFinal.data.dao;

import com.TpFinal.data.dao.interfaces.DAOCredencial;
import com.TpFinal.dto.persona.Credencial;

public class DAOCredencialImpl extends DAOImpl<Credencial> implements DAOCredencial{
	
	public DAOCredencialImpl() {
		super(Credencial.class);
	}

}
