package com.TpFinal.dto.persona;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "empleados")
@PrimaryKeyJoinColumn(name = "id")
public class Empleado extends Persona{
	@Enumerated(EnumType.STRING)
	@Column(name="estadoEmpleado")
	private EstadoEmpleado estadoEmpleado;
	@Column(name="fechaAlta")
	private LocalDate fechaDeAlta;
	@Column(name="fechaBaja")
	private LocalDate fechaDeBaja;
	@OneToOne(fetch=FetchType.EAGER)
	@Cascade({CascadeType.ALL})
	private Credencial credencial;
	
	
	public Empleado() {super();this.estadoEmpleado=EstadoEmpleado.ACTIVO;}
	
	private Empleado(Builder b) {
		this.estadoEmpleado=b.estadoEmpleado;
		this.fechaDeAlta=b.fechaDeAlta;
		this.fechaDeBaja=b.fechaDeBaja;
	}
	
	public EstadoEmpleado getEstadoEmpleado() {
		return estadoEmpleado;
	}

	public void setEstadoEmpleado(EstadoEmpleado estadoEmpleado) {
		this.estadoEmpleado = estadoEmpleado;
	}

	public LocalDate getFechaDeAlta() {
		return fechaDeAlta;
	}

	public void setFechaDeAlta(LocalDate fechaDeAlta) {
		this.fechaDeAlta = fechaDeAlta;
	}

	public LocalDate getFechaDeBaja() {
		return fechaDeBaja;
	}

	public void setFechaDeBaja(LocalDate fechaDeBaja) {
		this.fechaDeBaja = fechaDeBaja;
	}
	
	public Credencial getCredencial() {
		return credencial;
	}

	public void setCredencial(Credencial credencial) {
		this.credencial = credencial;
	}
	
	public static class Builder{
		private EstadoEmpleado estadoEmpleado;
		private LocalDate fechaDeAlta;
		private LocalDate fechaDeBaja;
		private Credencial credencial;
		
		public Builder setEstadoEmpeado(EstadoEmpleado dato) {
			this.estadoEmpleado=dato;
			return this;
		}
		public Builder setFechaDeAlta(LocalDate dato) {
			this.fechaDeAlta=dato;
			return this;
		}
		public Builder setFechaDeBaja(LocalDate dato) {
			this.fechaDeBaja=dato;
			return this;
		}
		public Builder setCredencial(Credencial dato) {
			this.credencial=dato;
			return this;
		}
		public Empleado build() {
			return new Empleado(this);
		}
		
	}
	
}
