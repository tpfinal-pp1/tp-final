package com.TpFinal.data.dao.interfaces;

import java.util.List;

import com.TpFinal.domain.inmueble.EstadoInmueble;
import com.TpFinal.domain.inmueble.Inmueble;

public interface DAOInmueble {
	public List<Inmueble> findInmueblesbyEstado(EstadoInmueble estado); 

}
