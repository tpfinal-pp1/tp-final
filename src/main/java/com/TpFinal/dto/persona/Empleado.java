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

import org.apache.log4j.Logger;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.TpFinal.dto.EstadoRegistro;
import com.TpFinal.dto.cita.Cita;
import com.TpFinal.dto.inmueble.CriterioBusqInmueble;

@Entity
@Table(name = "empleados")
@PrimaryKeyJoinColumn(name = "id")
public class Empleado extends RolPersona {
    private static final Logger logger = Logger.getLogger(Empleado.class);

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
    @Column(name = "hs_antes_recordatorio1")
    protected Integer horasAntesRecoradatorio1 = 1;
    @Column(name = "hs_antes_recordatorio2")
    protected Integer horasAntesRecoradatorio2 = 24;

    @OneToOne(fetch = FetchType.EAGER)
    @Cascade({ CascadeType.ALL })
    protected Credencial credencial;

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
	this.credencial = b.credencial;
    }

    public Integer getHorasAntesRecoradatorio1() {
	return horasAntesRecoradatorio1;
    }

    public void setHorasAntesRecoradatorio1(Integer horasAntesRecoradatorio1) {
	this.horasAntesRecoradatorio1 = horasAntesRecoradatorio1;
    }

    public Integer getHorasAntesRecoradatorio2() {
	return horasAntesRecoradatorio2;
    }

    public void setHorasAntesRecoradatorio2(Integer horasAntesRecoradatorio2) {
	this.horasAntesRecoradatorio2 = horasAntesRecoradatorio2;
    }

    public EstadoEmpleado getEstadoEmpleado() {
	return estadoEmpleado;
    }

    public CategoriaEmpleado getCategoriaEmpleado() {
	return categoriaEmpleado;
    }

    public void setCategoriaEmpleado(CategoriaEmpleado categoriaEmpleado) {
	this.categoriaEmpleado = categoriaEmpleado;
	if (this.getCredencial() != null) {
	    logger.debug("Empleado: " + this.getPersona());
	    logger.debug("Categoria: " + this.getCategoriaEmpleado());
	    if (getCategoriaEmpleado() != null)
		this.getCredencial().setViewAccess(ViewAccess.valueOf(this.getCategoriaEmpleado()));
	}
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
	protected Set<Cita> calendarioPersonal = new HashSet<>();
	protected Persona persona;
	protected CategoriaEmpleado categoriaEmpleado;
	protected EstadoEmpleado estadoEmpleado = EstadoEmpleado.ACTIVO;
	protected LocalDate fechaDeAlta;
	protected LocalDate fechaDeBaja;
	protected Credencial credencial;

	public Builder setCalendarioPersonal(Set<Cita> calendario) {
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
