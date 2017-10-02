package com.TpFinal.data.dto.persona;

import com.TpFinal.data.dto.inmueble.Inmueble;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "roles")
@Inheritance(strategy = InheritanceType.JOINED)

public abstract class RolPersona {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id")
    @NotNull
    private Persona persona;

    @Id
    @GeneratedValue
    @Column(name = "idRol")
    protected Long idRol;

	public Persona getPersona() {
		return persona;
	}

	public Long getIdRol() {
		return idRol;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public void setIdRol(Long idRol) {
		this.idRol = idRol;
	}
    
    



}
