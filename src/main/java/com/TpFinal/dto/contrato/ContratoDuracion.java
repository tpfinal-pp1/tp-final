package com.TpFinal.dto.contrato;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

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
    
    
    @OneToMany(mappedBy = "duracionContrato",fetch = FetchType.EAGER)
    @Cascade({ CascadeType.ALL})
    private Set<ContratoAlquiler> contratosAlquiler;
    
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
	//this.contratosAlquiler = b.contratosAlquiler;
	contratosAlquiler  = new HashSet<>();
	
    }

    public static class Builder {

	private String descripcion;
	private Integer duracion;
	//private Set<ContratoAlquiler> contratosAlquiler  = new HashSet<>();
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

    @Override
    public String toString() {
	return this.getDescripcion();
    }

    @Override
    public void setEstadoRegistro(EstadoRegistro estado) {
	this.estadoRegistro = estado;

    }

    @Override
    public EstadoRegistro getEstadoRegistro() {
	return this.estadoRegistro;
    }
    
    public Set<ContratoAlquiler> getContratosAlquiler() {
		return contratosAlquiler;
		}
    
    public void setContratos(Set<ContratoAlquiler> contratos) {
	this.contratosAlquiler = contratos;
    }
    
    public void addContratosAlquiler(ContratoAlquiler contrato) {
        if (!this.contratosAlquiler.contains(contrato)) {
	    this.contratosAlquiler.add(contrato);
	    contrato.setDuracionContrato(this);
        }
    }
    
    	public void removeContratosAlquiler(ContratoAlquiler contrato) {
    	if (this.contratosAlquiler.contains(contrato)) {
    	    this.contratosAlquiler.remove(contrato);
    	    contrato.setDuracionContrato(null);
    		}
    	}
}