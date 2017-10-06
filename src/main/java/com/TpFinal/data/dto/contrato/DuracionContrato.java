package com.TpFinal.data.dto.contrato;

public enum DuracionContrato { TreintaySeisMeses("36 meses", 36), VeinticuatroMeses("24 meses", 24);   
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
    
}
