package com.TpFinal.data.dto.persona;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.TpFinal.data.dto.BorradoLogico;
import com.TpFinal.data.dto.EstadoRegistro;
import com.TpFinal.data.dto.Identificable;
import com.TpFinal.data.dto.publicacion.Rol;

@Entity
@Table(name = "roles")
@Inheritance(strategy = InheritanceType.JOINED)

public abstract class RolPersona implements Identificable, BorradoLogico{

	public static final String rp = "rolPersona";
	public static final String idr = "idRol";
	private static final String estadoRegistroS="estadoRegistro";

    @ManyToOne(fetch = FetchType.EAGER, cascade= {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "idp")
    private Persona persona;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = idr)
    protected Long idRol;

	@Enumerated(EnumType.STRING)
	@Column(name = estadoRegistroS)
	@NotNull
	private EstadoRegistro estadoRegistro;

	public RolPersona() {}

	public RolPersona(Persona persona, EstadoRegistro estadoRegistro) {
		super();
		this.persona = persona;
		this.estadoRegistro = EstadoRegistro.ACTIVO;
	}

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
	public Long getId() {
		return this.idRol;
	}

	@Override
	public void setEstadoRegistro(EstadoRegistro estado) {
		this.estadoRegistro=estado;
		
	}

	@Override
	public EstadoRegistro getEstadoRegistro() {
		return this.estadoRegistro;
	}

	

	@Override
	public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((idRol == null) ? 0 : idRol.hashCode());
	    result = prime * result + ((persona == null) ? 0 : persona.hashCode());
	    return result;
	}

	@Override
	public boolean equals(Object obj) {
	    if (this == obj)
		return true;
	    if (obj == null)
		return false;
	    if (!(obj instanceof RolPersona))
		return false;
	    RolPersona other = (RolPersona) obj;
	    if (idRol == null) {
		if (other.idRol != null)
		    return false;
	    } else if (!idRol.equals(other.idRol))
		return false;
	    if (persona == null) {
		if (other.persona != null)
		    return false;
	    } else if (!persona.equals(other.persona))
		return false;
	    return true;
	}

	public Rol giveMeYourRole() {
		Rol ret=Rol.Propietario;
		if(this instanceof Inquilino)
			ret= Rol.Inquilino;
		return ret;
	}
	
	

}
