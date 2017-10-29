package com.TpFinal.dto.persona;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.TpFinal.dto.EstadoRegistro;
import com.TpFinal.dto.inmueble.CriterioBusqInmueble;
import com.TpFinal.dto.persona.Persona.Builder;

@Entity
@Table(name = "empleados")
@PrimaryKeyJoinColumn(name = "idPersona")
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
	
	
	public Empleado() {super();this.estadoEmpleado=EstadoEmpleado.ACTIVO;this.setEstadoRegistro(EstadoRegistro.ACTIVO);}
	
	private Empleado(Builder b) {
		super(b.nombre, b.apellido, b.mail, b.telefono, b.telefono2, b.DNI, b.infoAdicional);
		this.estadoEmpleado=b.estadoEmpleado;
		this.fechaDeAlta=b.fechaDeAlta;
		this.fechaDeBaja=b.fechaDeBaja;
		this.setEstadoRegistro(EstadoRegistro.ACTIVO);
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
		private String nombre = "";
		private String apellido = "";
		private String mail = "";
		private String DNI = "";
		private String telefono = "";
		private String telefono2 = "";
		private String infoAdicional = "";
		private Set<RolPersona> roles = new HashSet<>();
		private Boolean esInmobiliaria;
		private CriterioBusqInmueble prefBusqueda;
		
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
		public Builder setNombre(String dato) {
			this.nombre = dato;
			return this;
		}

		public Builder setApellido(String dato) {
			this.apellido = dato;
			return this;
		}

		public Builder setMail(String dato) {
			this.mail = dato;
			return this;
		}

		public Builder setTelefono(String dato) {
			this.telefono = dato;
			return this;
		}

		public Builder setTelefono2(String dato) {
			this.telefono2 = dato;
			return this;
		}

		public Builder setDNI(String dato) {
			this.DNI = dato;
			return this;
		}

		public Builder setinfoAdicional(String dato) {
			this.infoAdicional = dato;
			return this;
		}

		public Builder setRoles(Set<RolPersona> dato) {
			this.roles = dato;
			return this;
		}

		public Builder setPrefBusqueda(CriterioBusqInmueble dato) {
			this.prefBusqueda = dato;
			return this;
		}
		
		public Builder setEsInmobiliaria(Boolean dato) {
			this.esInmobiliaria = dato;
			return this;
		}
		public Empleado build() {
			return new Empleado(this);
		}
		
	}
	
}
