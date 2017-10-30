package com.TpFinal.dto.persona;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.swing.text.View;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.TpFinal.dto.BorradoLogico;
import com.TpFinal.dto.EstadoRegistro;
import com.TpFinal.dto.Identificable;
import com.TpFinal.dto.persona.Empleado;

@Entity
@Table(name="credenciales")
public class Credencial implements Identificable, BorradoLogico {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idCredencial")
	private Long id;
	@Column(name="usuario", unique=true)
	private String usuario;
	@Column(name="contrasenia")
	private String contrasenia;
	@Enumerated(EnumType.STRING)
	@Column(name="estadoRegistro")
	private EstadoRegistro estadoRegistro;
	@Enumerated(EnumType.STRING)
	@Column(name="viewAccess")
	private ViewAccess viewAccess;
	@OneToOne(fetch=FetchType.EAGER)
	@Cascade({CascadeType.SAVE_UPDATE})
	private Empleado empleado;
	
	public Credencial() {this.estadoRegistro=EstadoRegistro.ACTIVO;}
	
	private Credencial(Builder b) {
		this.estadoRegistro=EstadoRegistro.ACTIVO;
		this.usuario=b.usuario;
		this.contrasenia=b.contrasenia;
		this.empleado=b.empleado;
	}
	
	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contraseña) {
		this.contrasenia = contraseña;
	}

	@Override
	public EstadoRegistro getEstadoRegistro() {
		return estadoRegistro;
	}

	public void setEstadoRegistro(EstadoRegistro estadoRegistro) {
		this.estadoRegistro = estadoRegistro;
	}
	

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public ViewAccess getViewAccess() {
		return viewAccess;
	}

	public void setViewAccess(ViewAccess viewAccess) {
		this.viewAccess = viewAccess;
	}



	public static class Builder{
		private String usuario;
		private String contrasenia;
		private Empleado empleado;
		private ViewAccess viewacc;
		
		public Builder setUsuario(String dato) {
			this.usuario=dato;
			return this;
		}
		public Builder setContrasenia(String dato) {
			this.contrasenia=dato;
			return this;
		}
		public Builder setEmpleado(Empleado dato) {
			this.empleado=dato;
			return this;
		}


		public Credencial build() {
			return new Credencial(this);
		}
		
	}

}
