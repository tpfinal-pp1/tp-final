package com.TpFinal.data.dto;

public class Persona {
	
	private String nombre;
	private String apellido;
	private String mail;
	private String telefono;
	
	public Persona() {}
	
	public Persona(String nombre, String apellido, String mail, String telefono) {
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
	
	

}
