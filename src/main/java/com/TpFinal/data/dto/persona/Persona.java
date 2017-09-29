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
	private String nombre;
	@Column(name="apellido")
	private String apellido;
	@Column(name="mail")
	private String mail;
	@Column(name="DNI")
	private String DNI;
	@Column(name="telefono")
	private String telefono;
	@Column(name="telefono2")
	private String telefono2;
	@Column(name="infoAdicional")
	private String infoAdicional;




	public Persona() {}

	@Override
	public Persona clone() throws CloneNotSupportedException {
		try {
			return (Persona) BeanUtils.cloneBean(this);
		} catch (Exception ex) {
			throw new CloneNotSupportedException();
		}
	}

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

}
