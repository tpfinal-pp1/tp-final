package com.TpFinal.UnitTests.dto.contrato;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public enum DuracionContrato {
    TreintaySeisMeses("36 meses", 36), VeinticuatroMeses("24 meses", 24);
    private String descripcion;
    private Integer duracion;

    private DuracionContrato(String descripcion, Integer duracion) {
	this.descripcion = descripcion;
	this.duracion = duracion;
    }

    public String getDescripcion() {
	return descripcion;
    }

    public Integer getDuracion() {
	return duracion;
    }

    public static Collection<DuracionContrato> toList() {
	DuracionContrato[] duraciones = DuracionContrato.values();
	List<DuracionContrato> ret = new ArrayList<>();

	for (DuracionContrato d : duraciones) {
	    ret.add(d);
	}

	return ret;
    }
    
    @Override 
    public String toString() {
	return this.getDescripcion();
    }

}
