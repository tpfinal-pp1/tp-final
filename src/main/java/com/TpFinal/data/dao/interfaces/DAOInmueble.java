package com.TpFinal.data.dao.interfaces;

import java.util.List;

import com.TpFinal.data.dto.inmueble.CriterioBusquedaInmuebleDTO;
import com.TpFinal.data.dto.inmueble.EstadoInmueble;
import com.TpFinal.data.dto.inmueble.Inmueble;

public interface DAOInmueble extends DAO<Inmueble>{
	public List<Inmueble> findInmueblesbyEstado(EstadoInmueble estado); 
	public List<Inmueble> findInmueblesbyCaracteristicas(CriterioBusquedaInmuebleDTO criterio);

}
