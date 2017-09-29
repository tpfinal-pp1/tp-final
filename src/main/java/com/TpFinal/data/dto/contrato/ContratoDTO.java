package com.TpFinal.data.dto.contrato;

import java.sql.Blob;
import java.time.LocalDate;

import com.TpFinal.data.dto.Identificable;


public class ContratoDTO implements Identificable {
	
	Long id;
	LocalDate fechaCelebracion;
	Blob documento;
	
	public ContratoDTO() {}
	
	public ContratoDTO(Long id, LocalDate fechaCelebracion, Blob documento) {
		super();
		this.id = id;
		this.fechaCelebracion = fechaCelebracion;
		this.documento = documento;
	}

	@Override
	public Long getId() {
		return this.id;
	}

	public LocalDate getFechaCelebracion() {
		return fechaCelebracion;
	}

	public void setFechaCelebracion(LocalDate fechaCelebracion) {
		this.fechaCelebracion = fechaCelebracion;
	}

	public Blob getDocumento() {
		return documento;
	}

	public void setDocumento(Blob documento) {
		this.documento = documento;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
}
