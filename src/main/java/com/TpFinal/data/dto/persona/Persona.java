package com.TpFinal.data.dto.persona;

import com.TpFinal.data.dto.Identificable;

import javax.persistence.*;
import java.util.*;

@Entity  
@Table(name = "personas")
@Inheritance(strategy=InheritanceType.JOINED)  
public class Persona implements Identificable {

	public static final String idPersona = "id";
	public static final String nombrePersona = "nombre";
	public static final String apellidoPersona = "apellido";
	public static final String mailPersona = "mail";
	public static final String DNIPersona = "DNI";
	public static final String telefonoPersona = "telefono";
	public static final String telefono2Persona = "telefono2";
	public static final String infoPersona = "infoAdicional";

	@Id  
	@GeneratedValue(strategy=GenerationType.AUTO)  
	@Column(name = idPersona)
	private Long id;
	@Column(name=nombrePersona)
	private String nombre="";
	@Column(name=apellidoPersona)
	private String apellido="";
	@Column(name=mailPersona)
	private String mail="";
	@Column(name=DNIPersona)
	private String DNI="";
	@Column(name=telefonoPersona)
	private String telefono="";
	@Column(name=telefono2Persona)
	private String telefono2="";
	@Column(name=infoPersona)
	private String infoAdicional="";
	@OneToMany(mappedBy = "persona", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	protected Set<RolPersona> roles = new HashSet<>();



	public Persona() {


	}


	@Override
	public String toString() {
		String rols="";
		for (RolPersona rol: this.roles
				) {
			rols=rols+rol.RolPersona.toString()+" ," ;

		}
		return "{" +
				"nombre='" + nombre + '\'' +
				", apellido='" + apellido + '\'' +
				", DNI='" + DNI + '\'' +
				", roles=" + rols +
				'}';



	}



	private Persona(Builder b) {
		this.id=b.id;
		this.nombre=b.nombre;
		this.apellido=b.apellido;
		this.DNI=b.DNI;
		this.mail=b.mail;
		this.telefono=b.telefono;
		this.telefono2=b.telefono2;
		this.infoAdicional=b.infoAdicional;
		this.roles=b.roles;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Persona persona = (Persona) o;

		if (nombre != null ? !nombre.equals(persona.nombre) : persona.nombre != null) return false;
		if (apellido != null ? !apellido.equals(persona.apellido) : persona.apellido != null) return false;
		if (mail != null ? !mail.equals(persona.mail) : persona.mail != null) return false;
		if (DNI != null ? !DNI.equals(persona.DNI) : persona.DNI != null) return false;
		if (telefono != null ? !telefono.equals(persona.telefono) : persona.telefono != null) return false;
		if (telefono2 != null ? !telefono2.equals(persona.telefono2) : persona.telefono2 != null) return false;
		return infoAdicional != null ? infoAdicional.equals(persona.infoAdicional) : persona.infoAdicional == null;
	}

	@Override
	public int hashCode() {
		int result = nombre != null ? nombre.hashCode() : 0;
		result = 31 * result + (apellido != null ? apellido.hashCode() : 0);
		result = 31 * result + (mail != null ? mail.hashCode() : 0);
		result = 31 * result + (DNI != null ? DNI.hashCode() : 0);
		result = 31 * result + (telefono != null ? telefono.hashCode() : 0);
		result = 31 * result + (telefono2 != null ? telefono2.hashCode() : 0);
		result = 31 * result + (infoAdicional != null ? infoAdicional.hashCode() : 0);
		return result;
	}

	public Persona(Long id, String nombre, String apellido, String mail, String telefono, String telefono2, String DNI, String infoAdicional) {
		this.id=id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.mail = mail;
		this.telefono = telefono;
		this.DNI= DNI;
		this.telefono2=telefono2;
		this.infoAdicional=infoAdicional;

	}

	public String getNombre() {
		return nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public String getMail() {
		return mail;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	@Override
	public Long getId() {
		return this.id;
	}

	public String getDNI() {
		return DNI;
	}

	public void setDNI(String DNI) {
		this.DNI = DNI;
	}


	public String getTelefono2() {

		return telefono2;
	}

	public void setTelefono2(String telefono2) {
		this.telefono2 = telefono2;
	}

	public String getInfoAdicional() {
		return infoAdicional;
	}

	public void setInfoAdicional(String infoAdicional) {
		this.infoAdicional = infoAdicional;
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
		protected Set<RolPersona> roles = new HashSet<>();
		
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

		public Builder setRoles(Set<RolPersona> dato) {
			this.roles=dato;
			return this;
		}

		public Persona buid() {
			return new Persona(this);
		}
		
	}


}
