package com.TpFinal.data.dao.interfaces;

import com.TpFinal.dto.persona.Credencial;

public interface DAOCredencial extends DAO<Credencial>{

	public boolean existe(Credencial c);
}
