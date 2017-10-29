package com.TpFinal.dto.cita;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.TpFinal.dto.BorradoLogico;
import com.TpFinal.dto.EstadoRegistro;
import com.TpFinal.dto.Identificable;

@Entity
@Table(name = "recordatorios")
public class Recordatorio implements Identificable, BorradoLogico {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_recordatorio")
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_registro")
    @NotNull
    private EstadoRegistro estadoRegistro = EstadoRegistro.ACTIVO;
    @Column(name = "fecha_inicio")
    private LocalDateTime fechaInicio;
    @Column(name = "fecha_fin")
    private LocalDateTime fechaFin;
    @Column(name = "hora")
    private String hora;
    @Column(name = "minuto")
    private String minuto;
    @Column(name = "periodicidad")
    private String periodicidad;
    @ManyToOne(fetch = FetchType.EAGER)
    @Cascade({ CascadeType.ALL })
    @JoinColumn(name = "id_cita")
    private Cita cita;

    public Recordatorio() {
    }

    public Recordatorio(Builder b) {
	this.fechaInicio = b.fechaInicio;
	this.fechaFin = b.fechaFin;
	this.minuto = b.minuto;
	this.hora = b.hora;
	this.cita = b.cita;
    }

    @Override
    public Long getId() {
	return id;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (!(obj instanceof Recordatorio))
	    return false;
	Recordatorio other = (Recordatorio) obj;
	return getId() != null && Objects.equals(getId(), other.getId());
    }

    public Cita getCita() {
	return cita;
    }

    public void setCita(Cita cita) {
	
	if (cita != null) {
	    this.cita = cita;
	    this.cita.addRecordatorio(this);
	} else {
	    if (this.cita != null) {
		this.cita.removeRecordatorio(this);
		this.cita = null;
	    }
	}
    }

    public LocalDateTime getFechaInicio() {
	return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
	this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
	return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
	this.fechaFin = fechaFin;
    }

    public String getHora() {
	return hora;
    }

    public void setHora(String hora) {
	this.hora = hora;
    }

    public String getMinuto() {
	return minuto;
    }

    public void setMinuto(String minuto) {
	this.minuto = minuto;
    }

    public String getPeriodicidad() {
	return periodicidad;
    }

    public void setPeriodicidad(String periodicidad) {
	this.periodicidad = periodicidad;
    }

    public void setId(Long id) {
	this.id = id;
    }

    @Override
    public void setEstadoRegistro(EstadoRegistro estado) {
	this.estadoRegistro = estado;
    }

    @Override
    public EstadoRegistro getEstadoRegistro() {
	return this.estadoRegistro;
    }

    public class Builder {

	private Cita cita;
	private LocalDateTime fechaFin;
	private String hora;
	private String minuto;
	private LocalDateTime fechaInicio;

	public Builder setDireccionLugar(String direccionLugar) {
	    this.hora = direccionLugar;
	    return this;
	}
	
	public Builder setCita(Cita cita) {
	    this.cita = cita;
	    return this;
	}

	public Builder setCitado(String citado) {
	    this.minuto = citado;
	    return this;
	}

	public Builder setFechaInicio(LocalDateTime fechaInicio) {
	    this.fechaInicio = fechaInicio;
	    return this;
	}

	public Builder setFechaFin(LocalDateTime fechaFin) {
	    this.fechaFin = fechaFin;
	    return this;
	}

	public Recordatorio build() {
	    return new Recordatorio(this);
	}

    }

}
