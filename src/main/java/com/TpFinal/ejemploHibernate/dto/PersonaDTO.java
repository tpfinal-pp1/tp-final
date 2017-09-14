package com.TpFinal.ejemploHibernate.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "personas")
public class PersonaDTO implements Identificable
{
	@Id @GeneratedValue
	@Column(name = "idPersona")
	private Integer idPersona;
	
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "telefono")
	private String telefono;
	
	@Column(name = "mail")
	private String mail;
	
	@Column(name = "cumpleanios")
	private LocalDate fechaCumpleaños;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "mascotaPreferida")
	private TipoMascota	mascotaPreferida;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idDomicilio")
	private DomicilioDTO domicilio;
	
	public PersonaDTO() {}	
	

	public PersonaDTO(String nombre, String telefono, String mail, LocalDate fechaCumpleaños,
			TipoMascota mascotaPreferida, DomicilioDTO domicilio) {
		super();
		this.nombre = nombre;
		this.telefono = telefono;
		this.mail = mail;
		this.fechaCumpleaños = fechaCumpleaños;
		this.mascotaPreferida = mascotaPreferida;
		this.domicilio = domicilio;
	}


	public PersonaDTO(Integer idPersona, String nombre, String telefono)
	{
		this.idPersona = idPersona;
		this.nombre = nombre;
		this.telefono = telefono;
	}

	public PersonaDTO(int idPersona) {
		super();
		this.idPersona = idPersona;
	}

	public int getIdPersona() 
	{
		return this.idPersona;
	}

	//Recomendado en documentación de hibernate.
	private void setIdPersona(Integer idPersona) 
	{
		this.idPersona = idPersona;
	}

	public String getNombre() 
	{
		return this.nombre;
	}

	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}

	public String getTelefono() 
	{
		return this.telefono;
	}

	public void setTelefono(String telefono) 
	{
		this.telefono = telefono;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public LocalDate getFechaCumpleaños() {
		return fechaCumpleaños;
	}

	public void setFechaCumpleaños(LocalDate fechaCumpleaños) {
		this.fechaCumpleaños = fechaCumpleaños;
	}

	public DomicilioDTO getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(DomicilioDTO domicilio) {
		this.domicilio = domicilio;
	}
	
	public String getFechaOrdenada() {
		DateTimeFormatter formatter =new DateTimeFormatterBuilder().appendPattern("dd-MM-yyyy").toFormatter(); 
		return this.fechaCumpleaños.format(formatter);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PersonaDTO other = (PersonaDTO) obj;
		if (domicilio == null) {
			if (other.domicilio != null)
				return false;
		} else if (!domicilio.equals(other.domicilio))
			return false;
		if (fechaCumpleaños == null) {
			if (other.fechaCumpleaños != null)
				return false;
		} else if (!fechaCumpleaños.equals(other.fechaCumpleaños))
			return false;
		if (idPersona != other.idPersona)
			return false;
		if (mail == null) {
			if (other.mail != null)
				return false;
		} else if (!mail.equals(other.mail))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (telefono == null) {
			if (other.telefono != null)
				return false;
		} else if (!telefono.equals(other.telefono))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PersonaDTO [idPersona=" + idPersona + ", nombre=" + nombre + ", telefono=" + telefono + ", mail=" + mail
				+ ", fechaCumpleaños=" + getFechaOrdenada() + ", mascota preferida="+ mascotaPreferida + ", domicilio=" + domicilio + "]";
	}

	@Override
	public Integer getId() {
		return idPersona;
	}

	
		
}
