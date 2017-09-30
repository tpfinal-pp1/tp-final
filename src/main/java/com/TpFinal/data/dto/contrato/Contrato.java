package com.TpFinal.data.dto.contrato;

import java.sql.Blob;
import java.time.LocalDate;

import javax.persistence.*;

import com.TpFinal.data.dto.Identificable;

@Entity
@Table(name="contrato")
@Inheritance(strategy=InheritanceType.JOINED)
public class Contrato implements Identificable {
	@Id  
	@GeneratedValue(strategy=GenerationType.AUTO)  
	@Column(name = "id")  
	Long id;
	@Column(name="fechaCelebracion")
	LocalDate fechaCelebracion;
	@Column(name="documento")
	Blob documento;
	
	public Contrato() {}
	
	public Contrato(Long id, LocalDate fechaCelebracion, Blob documento) {
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
