package com.TpFinal.data.dao.interfaces;

import java.util.List;

import com.TpFinal.dto.inmueble.CriterioBusqInmueble;
import com.TpFinal.dto.inmueble.EstadoInmueble;
import com.TpFinal.dto.inmueble.Imagen;
import com.TpFinal.dto.inmueble.Inmueble;

public interface DAOInmueble extends DAO<Inmueble>{
	public List<Inmueble> findInmueblesbyEstado(EstadoInmueble estado); 
	public List<Inmueble> findInmueblesbyCaracteristicas(CriterioBusqInmueble criterio);
	public boolean addImagen(Imagen img, Inmueble inmueble);

}
