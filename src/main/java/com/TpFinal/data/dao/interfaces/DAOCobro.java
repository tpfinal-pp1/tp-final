package com.TpFinal.data.dao.interfaces;

import java.util.List;

import com.TpFinal.dto.cobro.Cobro;
import com.TpFinal.dto.cobro.EstadoCobro;

public interface DAOCobro extends DAO<Cobro>{
	public List<Cobro> findCobrobyEstado(EstadoCobro estado); 
}
