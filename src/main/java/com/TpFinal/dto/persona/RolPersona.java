package com.TpFinal.dto.persona;

import java.util.Objects;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.TpFinal.dto.BorradoLogico;
import com.TpFinal.dto.EstadoRegistro;
import com.TpFinal.dto.Identificable;

@Entity
@Table(name = "roles")
@Inheritance(strategy = InheritanceType.JOINED)

public abstract class RolPersona implements Identificable, BorradoLogico {

    public static final String pRolPersona = "rol_persona";
    public static final String pIdRol = "id_rol";
    private static final String pEstadoRegistro = "estado_registro";

    @ManyToOne(fetch = FetchType.EAGER)
    @Cascade({ CascadeType.SAVE_UPDATE })
    @JoinColumn(name = "id_persona")
    protected Persona persona;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = pIdRol)
    protected Long idRol;

    @Enumerated(EnumType.STRING)
    @Column(name = pEstadoRegistro)
    @NotNull
    private EstadoRegistro estadoRegistro = EstadoRegistro.ACTIVO;

    public RolPersona() {
    }

    public RolPersona(Persona persona) {
	super();
	setPersona(persona);
	this.estadoRegistro = EstadoRegistro.ACTIVO;
    }

    public Persona getPersona() {
	return persona;
    }

    public Long getIdRol() {
	return idRol;
    }

    public void setPersona(Persona persona) {
	if (this.persona != null && !this.persona.equals(persona)) {
	    this.persona.removeRol(this);
	}
	this.persona = persona;
	if (persona != null && !persona.getRoles().contains(this))
	    persona.addRol(this);
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
	this.estadoRegistro = estado;

    }

    @Override
    public EstadoRegistro getEstadoRegistro() {
	return this.estadoRegistro;
    }

    @Override
    public int hashCode() {
	return 37;
    }

    @Override
    public boolean equals(Object o) {
	if (this == o)
	    return true;
	if (!(o instanceof RolPersona))
	    return false;
	RolPersona other = (RolPersona) o;
	return getId() != null && Objects.equals(getId(), other.getId());
    }

    public Rol getRol() {
	Rol ret = null;
	if (this instanceof Inquilino)
	    ret = Rol.Inquilino;
	else if (this instanceof Propietario)
	    ret = Rol.Propietario;
	else
	    ret = Rol.Empleado;
	return ret;
    }

}
