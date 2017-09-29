package com.TpFinal.data.dto;

import javax.persistence.*;

@Entity
@Table(name="inquilino")
@PrimaryKeyJoinColumn(name="id")
public class InquilinoDTO extends PersonaDTO{

	@Column(name="telefono2")
	public String telefono2;
	
	public InquilinoDTO() {super();}

	public InquilinoDTO(String nombre, String apellido, String mail, String telefono,String telefono2) {
		super(nombre,apellido, mail,telefono);
		this.telefono2 = telefono2;
	}

	public String getTelefono2() {
		return telefono2;
	}

	public void setTelefono2(String telefono2) {
		this.telefono2 = telefono2;
	}
	
	
	
	
}
