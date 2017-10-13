package com.TpFinal.data.dto.contrato;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.TpFinal.data.dto.BorradoLogico;
import com.TpFinal.data.dto.EstadoRegistro;
import com.TpFinal.data.dto.Identificable;

@Entity
@Table(name = "contratoDuracion")
@PrimaryKeyJoinColumn(name = "id")
public class ContratoDuracion implements Identificable, BorradoLogico  {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	protected Long id;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "duracion")
	private Integer duracion;

	public ContratoDuracion(Builder b) {

		this.descripcion = b.descripcion;
		this.duracion = b.duracion;

	}

	public static class Builder {

		private String descripcion;
		private Integer duracion;
		private Long id;


		public Builder setDescripcion (String descripcion) {
			this.descripcion = descripcion;
			return this;
		}

		public Builder setDuracion (Integer duracion) {
			this.duracion = duracion;
			return this;
		}

		public Builder setId(Long id) {
			this.id=id;
			return this;
		}

		public ContratoDuracion build() {
			return new ContratoDuracion(this);
		}
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Integer getDuracion() {
		return duracion;
	}
	public void setDuracion(Integer duracion) {
		this.duracion = duracion;
	}

	public String getDuracionString() {
		return duracion.toString();
	}

	public void setDuracionString(String valor) {
		this.duracion = Integer.valueOf(valor);
	}
	
	@Override
	public String toString() {
		return this.getDuracion() + " " + "Meses";
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
}

