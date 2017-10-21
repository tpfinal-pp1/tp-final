package com.TpFinal.dto.inmobiliaria;

import java.util.Set;

import com.TpFinal.dto.Apropiable;
import com.TpFinal.dto.BorradoLogico;
import com.TpFinal.dto.EstadoRegistro;
import com.TpFinal.dto.Identificable;
import com.TpFinal.dto.inmueble.Inmueble;

public class Inmobiliaria implements Identificable, BorradoLogico, Apropiable {
	
	private String nombre;
	private Set<Inmueble>inmuebles;
	
	
	public Inmobiliaria() {}

	@Override
	public String getNombreCompleto() {
		return nombre;
	}

	@Override
	public Set<Inmueble> getInmuebles() {
		return this.inmuebles;
	}

	@Override
	public void addInmueble(Inmueble i) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeInmueble(Inmueble i) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setEstadoRegistro(EstadoRegistro estado) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public EstadoRegistro getEstadoRegistro() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}

}
