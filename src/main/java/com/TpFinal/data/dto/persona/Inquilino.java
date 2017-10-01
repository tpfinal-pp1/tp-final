package com.TpFinal.data.dto.persona;

import javax.persistence.*;

@Entity
@Table(name="inquilino")
@PrimaryKeyJoinColumn(name="id")
public class Inquilino extends Persona {
	
	@Enumerated(EnumType.STRING)
	@Column(name="calificacion")
	private Calificacion calificacion;
	
	public Inquilino() {super();}
	
	private Inquilino(Builder b) {
		super(b.id,b.nombre,b.apellido,b.mail,b.telefono,b.telefono2,b.DNI,b.infoAdicional);
		this.calificacion=b.calificacion;
	}


	public Inquilino(Long id, String nombre, String apellido, String mail, String telefono,String telefono2, String DNI,String infoAdicional) {
		super(id, nombre,apellido, mail,telefono,telefono2,DNI,infoAdicional);
	}

		public static class Builder{
		private Long id;
		private String nombre;
		private String apellido;
		private String mail;
		private String telefono;
		private String telefono2;
		private String DNI;
		private String infoAdicional;
		private Calificacion calificacion;
		
		public Builder setId(Long dato) {
			this.id=dato;
			return this;
		}
		
		public Builder setNombre(String dato) {
			this.nombre=dato;
			return this;
		}
		
		public Builder setApellido(String dato) {
			this.apellido=dato;
			return this;
		}
		
		public Builder setMail(String dato) {
			this.mail=dato;
			return this;
		}
		
		public Builder setTelefono(String dato) {
			this.telefono=dato;
			return this;
		}
		
		public Builder setTelefono2(String dato) {
				this.telefono2=dato;
				return this;
		}
		
		public Builder setDNI(String dato) {
				this.DNI=dato;
				return this;
		}
		
		public Builder setinfoAdicional(String dato) {
				this.infoAdicional=dato;
				return this;
			}

		public Builder setCalificacion(Calificacion dato) {
			this.calificacion=dato;
			return this;
		}



			public Inquilino buid() {
			return new Inquilino(this);
		}
		
	}
	
	
}
