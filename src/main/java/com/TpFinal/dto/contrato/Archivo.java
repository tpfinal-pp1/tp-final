package com.TpFinal.dto.contrato;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Archivo")
public class Archivo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	protected Long id;
	private String nombre;
	private String extension;
	@Column(name = "documento")
	private Blob documento;
	
	public Archivo() {}
	
	private Archivo(Builder b) {
		this.nombre=b.nombre;
		this.extension=b.extension;
		this.documento=b.documento;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public Blob getDocumento() {
		return documento;
	}

	public void setDocumento(Blob documento) {
		this.documento = documento;
	}

	public static class Builder{
		private String nombre;
		private String extension;
		private Blob documento;
		
		public Builder setNombre(String dato) {
			this.nombre=dato;
			return this;
		}
		public Builder setExtension(String dato) {
			this.extension=dato;
			return this;
		}
		public Builder setDocumento(Blob dato) {
			this.documento=dato;
			return this;
		}
		public Archivo build() {
			return new Archivo(this);
		}
		
	}
	

}
