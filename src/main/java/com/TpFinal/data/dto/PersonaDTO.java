package com.TpFinal.data.dto;

import javax.persistence.*;

@Entity  
@Table(name = "persona")  
@Inheritance(strategy=InheritanceType.JOINED)  
public class PersonaDTO implements Identificable {
	
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
	@Column(name="telefono")
	private String telefono;
	
	public PersonaDTO() {}
	
	public PersonaDTO(String nombre, String apellido, String mail, String telefono) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.mail = mail;
		this.telefono = telefono;
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

}
