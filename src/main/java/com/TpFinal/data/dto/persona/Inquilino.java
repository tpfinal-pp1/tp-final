package com.TpFinal.data.dto.persona;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.apache.tapestry.pageload.EstablishDefaultParameterValuesVisitor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.TpFinal.data.dto.EstadoRegistro;
import com.TpFinal.data.dto.contrato.ContratoAlquiler;
import com.TpFinal.data.dto.publicacion.Rol;

@Entity
@Table(name = "inquilino")
@PrimaryKeyJoinColumn(name = "id")
public class Inquilino extends RolPersona {

    @Enumerated(EnumType.STRING)
    @Column(name = "calificacion")
    private Calificacion calificacion;
    @OneToMany(mappedBy = "inquilinoContrato", fetch = FetchType.EAGER)
    @Cascade({ CascadeType.SAVE_UPDATE })
    private Set<ContratoAlquiler> contratos = new HashSet<>();

    public Inquilino() {
	super();
    }

    private Inquilino(Builder b) {
	super(b.persona, EstadoRegistro.ACTIVO);
	this.contratos = b.contratos;
	this.calificacion = b.calificacion;
    }

    public Calificacion getCalificacion() {
	return calificacion;
    }

    public Set<ContratoAlquiler> getContratos() {
	return contratos;
    }

    public void setCalificacion(Calificacion calificacion) {
	this.calificacion = calificacion;
    }

    public void setContratos(Set<ContratoAlquiler> contratos) {
	this.contratos = contratos;
    }

    public void removeContrato(ContratoAlquiler contratoAlquiler) {
	if (this.contratos.contains(contratoAlquiler)) {
	    this.contratos.remove(contratoAlquiler);
	    contratoAlquiler.setInquilinoContrato(null);
	}

    }

    public void addContrato(ContratoAlquiler contratoAlquiler) {
	if (!this.contratos.contains(contratoAlquiler)) {
	    this.contratos.add(contratoAlquiler);
	    contratoAlquiler.setInquilinoContrato(this);
	}

    }

    @Override
    public boolean equals(Object o) {
	if (this == o)
	    return true;
	if (!(o instanceof Inquilino))
	    return false;
	Inquilino inquilino = (Inquilino) o;
	return getId() != null && Objects.equals(getId(), inquilino.getId());
    }

    @Override
    public int hashCode() {
	return 11;
    }

    @Override
    public String toString() {

	String ret = "";
	if (super.getPersona() != null) {
	    ret = super.getPersona().getNombre() + " " + super.getPersona().getApellido();
	}
	return ret;
    }

    public static class Builder {
	private Set<ContratoAlquiler> contratos = new HashSet<>();
	private Calificacion calificacion;
	private Rol rolPersona = Rol.Inquilino;
	private EstadoRegistro estadoRegistro;
	private Persona persona;

	public Builder setContratos(Set<ContratoAlquiler> dato) {
	    this.contratos = dato;
	    return this;
	}

	public Builder setCalificacion(Calificacion dato) {
	    this.calificacion = dato;
	    return this;
	}

	public Builder setPersona(Persona dato) {
	    this.persona = dato;
	    return this;
	}

	public Inquilino build() {
	    return new Inquilino(this);
	}
    }

}
