package com.TpFinal.dto.persona;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.TpFinal.dto.EstadoRegistro;
import com.TpFinal.dto.cita.Cita;
import com.TpFinal.dto.inmueble.CriterioBusqInmueble;

@Entity
@Table(name = "empleados")
@PrimaryKeyJoinColumn(name = "id")
public class Empleado extends RolPersona {

    @Enumerated(EnumType.STRING)
    @Column(name = "estadoEmpleado")
    protected EstadoEmpleado estadoEmpleado;
    @Enumerated(EnumType.STRING)
    @Column(name = "categoria")
    protected CategoriaEmpleado categoriaEmpleado = CategoriaEmpleado.sinCategoria;
    @Column(name = "fechaAlta")
    protected LocalDate fechaDeAlta;
    @Column(name = "fechaBaja")
    protected LocalDate fechaDeBaja;
    @OneToOne(fetch = FetchType.EAGER)
    @Cascade({ CascadeType.ALL })
    protected Credencial credencial;
    @OneToMany(mappedBy = "empleado", fetch = FetchType.EAGER)
    @Cascade({ CascadeType.ALL })
    private Set<Cita> calendarioPersonal = new HashSet<>();

    public Empleado() {
	super();
	this.estadoEmpleado = EstadoEmpleado.ACTIVO;
	this.setEstadoRegistro(EstadoRegistro.ACTIVO);
    }

    protected Empleado(Builder b) {
	super(b.persona);
	this.estadoEmpleado = b.estadoEmpleado;
	this.fechaDeAlta = b.fechaDeAlta;
	this.fechaDeBaja = b.fechaDeBaja;
	this.setEstadoRegistro(EstadoRegistro.ACTIVO);
	this.categoriaEmpleado = b.categoriaEmpleado;
	this.calendarioPersonal = b.calendarioPersonal;
    }
    
    public void addCita(Cita cita) {
   	if (!this.calendarioPersonal.contains(cita)) {
   	    this.calendarioPersonal.add(cita);
   	    cita.setEmpleado(this);
   	}
       }

       public void removeCita(Cita cita) {
   	if (this.calendarioPersonal.contains(cita)) {
   	    this.calendarioPersonal.remove(cita);
   	    cita.setEmpleado(null);
   	}
       }

    public EstadoEmpleado getEstadoEmpleado() {
	return estadoEmpleado;
    }

    public CategoriaEmpleado getCategoriaEmpleado() {
	return categoriaEmpleado;
    }

    public void setCategoriaEmpleado(CategoriaEmpleado categoriaEmpleado) {
	this.categoriaEmpleado = categoriaEmpleado;
    }

    public void setEstadoEmpleado(EstadoEmpleado estadoEmpleado) {
	this.estadoEmpleado = estadoEmpleado;
    }

    public LocalDate getFechaDeAlta() {
	return fechaDeAlta;
    }

    public void setFechaDeAlta(LocalDate fechaDeAlta) {
	this.fechaDeAlta = fechaDeAlta;
    }

    public LocalDate getFechaDeBaja() {
	return fechaDeBaja;
    }

    public void setFechaDeBaja(LocalDate fechaDeBaja) {
	this.fechaDeBaja = fechaDeBaja;
    }

    public Credencial getCredencial() {
	return credencial;
    }

    public void setCredencial(Credencial credencial) {
	this.credencial = credencial;
    }

    @Override
    public int hashCode() {
	return 31;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (!(obj instanceof Empleado))
	    return false;
	Empleado other = (Empleado) obj;
	return getId() != null && Objects.equals(getId(), other.getId());
    }

    public static class Builder {
	protected Set<Cita> calendarioPersonal= new HashSet<>();
	protected Persona persona;
	protected CategoriaEmpleado categoriaEmpleado;
	protected EstadoEmpleado estadoEmpleado;
	protected LocalDate fechaDeAlta;
	protected LocalDate fechaDeBaja;
	protected Credencial credencial;

	public Builder setCalendarioPersonal(Set<Cita> calendario)
	{
	    this.calendarioPersonal = calendario;
	    return this;
	}
	public Builder setPersona(Persona persona) {
	    this.persona = persona;
	    return this;
	}

	public Builder setCategoriaEmpleado(CategoriaEmpleado dato) {
	    this.categoriaEmpleado = dato;
	    return this;
	}

	public Builder setEstadoEmpeado(EstadoEmpleado dato) {
	    this.estadoEmpleado = dato;
	    return this;
	}

	public Builder setFechaDeAlta(LocalDate dato) {
	    this.fechaDeAlta = dato;
	    return this;
	}

	public Builder setFechaDeBaja(LocalDate dato) {
	    this.fechaDeBaja = dato;
	    return this;
	}

	public Builder setCredencial(Credencial dato) {
	    this.credencial = dato;
	    return this;
	}

	public Empleado build() {
	    return new Empleado(this);
	}

    }

}
