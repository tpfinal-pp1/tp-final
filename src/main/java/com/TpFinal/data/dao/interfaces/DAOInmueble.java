package com.TpFinal.data.dao.interfaces;

import java.util.List;

import com.TpFinal.dto.inmueble.CriterioBusquedaInmuebleDTO;
import com.TpFinal.dto.inmueble.EstadoInmueble;
import com.TpFinal.dto.inmueble.Inmueble;

public interface DAOInmueble extends DAO<Inmueble>{
	public List<Inmueble> findInmueblesbyEstado(EstadoInmueble estado); 
	public List<Inmueble> findInmueblesbyCaracteristicas(CriterioBusquedaInmuebleDTO criterio);

}
