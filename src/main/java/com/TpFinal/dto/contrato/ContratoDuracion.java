package com.TpFinal.dto.contrato;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.TpFinal.dto.BorradoLogico;
import com.TpFinal.dto.EstadoRegistro;
import com.TpFinal.dto.Identificable;

@Entity
@Table(name = "contratoDuracion")

public class ContratoDuracion implements Identificable, BorradoLogico {

    private static final String estadoRegistroS = "estadoRegistro";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    protected Long id;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "duracion")
    private Integer duracion;

    @Column(name = ContratoDuracion.estadoRegistroS)
    @NotNull
    private EstadoRegistro estadoRegistro;

    public ContratoDuracion() {
    }

    public ContratoDuracion(Builder b) {

	this.id = b.id;
	this.descripcion = b.descripcion;
	this.duracion = b.duracion;
	this.estadoRegistro = EstadoRegistro.ACTIVO;

    }

    public static class Builder {

	private String descripcion;
	private Integer duracion;
	private Long id;
	private EstadoRegistro estadoRegistro;

	public Builder setDescripcion(String descripcion) {
	    this.descripcion = descripcion;
	    return this;
	}

	public Builder setDuracion(Integer duracion) {
	    this.duracion = duracion;
	    return this;
	}

	public Builder setId(Long id) {
	    this.id = id;
	    return this;
	}

	public ContratoDuracion build() {
	    return new ContratoDuracion(this);
	}
    }

    @Override
    public Long getId() {
	return this.id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getDescripcion() {
	return descripcion;
    }

    public void setDescripcion(String descripcion) {
	this.descripcion = descripcion;
    }

    public Integer getDuracion() {
	return duracion;
    }

    public void setDuracion(Integer duracion) {
	this.duracion = duracion;
    }

    public String getDuracionString() {
	return duracion.toString();
    }

    public void setDuracionString(String valor) {
	this.duracion = Integer.valueOf(valor);
    }

    @Override
    public String toString() {
	return this.getDuracion() + " " + "Meses";
    }

    @Override
    public void setEstadoRegistro(EstadoRegistro estado) {
	this.estadoRegistro = estado;

    }

    @Override
    public EstadoRegistro getEstadoRegistro() {
	return this.estadoRegistro;
    }
}
