package com.TpFinal.dto;

import java.util.Set;

import com.TpFinal.dto.inmueble.Inmueble;

public interface Apropiable {

	public String getNombreCompleto();
	public Set<Inmueble> getInmuebles();
	public void addInmueble(Inmueble i);
	public void removeInmueble(Inmueble i);
}
