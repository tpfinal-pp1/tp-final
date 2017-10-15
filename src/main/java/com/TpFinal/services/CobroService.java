package com.TpFinal.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.TpFinal.data.dto.cobro.Cobro;

public class CobroService {
	
	List<Cobro>cobros;
	
	public CobroService() {
		cobros=new ArrayList<>();
		llenarConDatosHardCodeados();
	}
	
	public void save(Cobro c) {
		cobros.add(c);
	}
	
	public void delete(Cobro c) {
		cobros.remove(c);
	}
	
	public void update(Cobro c) {
		//TODO al dope hacerlo ahora, tira un print como que funca
	}
	
	public List<Cobro> readAll() {
		return this.cobros;
	}
	
	private void llenarConDatosHardCodeados() {
		//TODO
		
		cobros.add(new Cobro.Builder().setComision(new BigDecimal("100")).build());
	}

}
