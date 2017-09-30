package com.TpFinal.data.dto.persona;

import com.TpFinal.data.dto.Identificable;
import org.apache.commons.beanutils.BeanUtils;

import javax.persistence.*;

@Entity  
@Table(name = "persona")  
@Inheritance(strategy=InheritanceType.JOINED)  
public class Persona implements Identificable {
	
	@Id  
	@GeneratedValue(strategy=GenerationType.AUTO)  
	@Column(name = "id")
	private Long id;
	@Column(name="nombre")
	private String nombre="";
	@Column(name="apellido")
	private String apellido="";
	@Column(name="mail")
	private String mail="";
	@Column(name="DNI")
	private String DNI="";
	@Column(name="telefono")
	private String telefono="";
	@Column(name="telefono2")
	private String telefono2="";
	@Column(name="infoAdicional")
	private String infoAdicional="";




	public Persona() {}


	public Persona(Long id, String nombre, String apellido, String mail, String telefono,String telefono2, String DNI,String infoAdicional) {
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


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Persona persona = (Persona) o;

		if (id != null ? !id.equals(persona.id) : persona.id != null) return false;
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
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
		result = 31 * result + (apellido != null ? apellido.hashCode() : 0);
		result = 31 * result + (mail != null ? mail.hashCode() : 0);
		result = 31 * result + (DNI != null ? DNI.hashCode() : 0);
		result = 31 * result + (telefono != null ? telefono.hashCode() : 0);
		result = 31 * result + (telefono2 != null ? telefono2.hashCode() : 0);
		result = 31 * result + (infoAdicional != null ? infoAdicional.hashCode() : 0);
		return result;
	}
}
