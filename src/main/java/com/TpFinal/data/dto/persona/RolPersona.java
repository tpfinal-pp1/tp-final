package com.TpFinal.data.dto.persona;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;

@Entity
@Table(name = "roles")
@Inheritance(strategy = InheritanceType.JOINED)

public abstract class RolPersona {

    @ManyToOne(fetch = FetchType.EAGER, cascade= {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idRol == null) ? 0 : idRol.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RolPersona other = (RolPersona) obj;
		if (idRol == null) {
			if (other.idRol != null)
				return false;
		} else if (!idRol.equals(other.idRol))
			return false;
		return true;
	}
	
	
    
    



}
