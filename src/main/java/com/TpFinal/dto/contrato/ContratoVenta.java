package com.TpFinal.dto.contrato;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Blob;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.TpFinal.dto.EstadoRegistro;
import com.TpFinal.dto.inmueble.Inmueble;
import com.TpFinal.dto.movimiento.Movimiento;
import com.TpFinal.dto.persona.Persona;

@Entity
@Table(name = "contratoVenta")
@PrimaryKeyJoinColumn(name = "id")
public class ContratoVenta extends Contrato implements Cloneable {

    @Column(name = "precioVenta")
    private BigDecimal precioVenta;

    @ManyToOne
    @Cascade({ CascadeType.SAVE_UPDATE, CascadeType.MERGE })
    @JoinColumn(name = "id_comprador")
    private Persona comprador;

    @ManyToOne
    @Cascade({ CascadeType.SAVE_UPDATE, CascadeType.MERGE })
    @JoinColumn(name = "id_vendedor")
    private Persona vendedor;

    @Column(name = "comisionAComprador")
    private Integer comisionAComprador;
    @Column(name = "comisionAVendedor")
    private Integer comsionAVendedor;

    public ContratoVenta() {
	super();
    }

    private ContratoVenta(Builder b) {
	super(b.id, b.fechaIngreso, b.documento, b.estadoRegistro, b.inmueble);
	this.fechaCelebracion = b.fechaCelebracion;
	this.precioVenta = b.precioVenta;
	this.comprador = b.comprador;
	this.comisionAComprador = b.comisionAComprador;
	this.comsionAVendedor = b.comisionAVendedor;
	if (b.inmueble != null) {
	    this.vendedor = b.inmueble.getPropietario() != null ? b.inmueble.getPropietario().getPersona() : null;
	    this.inmueble = b.inmueble;
	}
    }

    public Integer getComisionAComprador() {
	return comisionAComprador;
    }

    public void setComisionAComprador(Integer comisionAComprador) {
	this.comisionAComprador = comisionAComprador;
    }

    public Integer getComsionAVendedor() {
	return comsionAVendedor;
    }

    public void setComsionAVendedor(Integer comsionAVendedor) {
	this.comsionAVendedor = comsionAVendedor;
    }

    public Persona getVendedor() {
	return vendedor;
    }

    public void setVendedor(Persona vendedor) {
	this.vendedor = vendedor;
    }

    public BigDecimal getPrecioVenta() {
	return precioVenta;
    }

    public void setPrecioVenta(BigDecimal precioVenta) {
	this.precioVenta = precioVenta;
	this.precioVenta = this.precioVenta.setScale(2, RoundingMode.CEILING);
    }

    public Persona getComprador() {
	return comprador;
    }

    public void setComprador(Persona comprador) {
	this.comprador = comprador;
    }

    @Override
    public boolean equals(Object o) {
	if (this == o)
	    return true;
	if (!(o instanceof ContratoVenta))
	    return false;
	ContratoVenta contrato = (ContratoVenta) o;
	return getId() != null && Objects.equals(getId(), contrato.getId());
    }

    @Override
    public int hashCode() {
	return 5;
    }

    @Override
    public String toString() {
	return inmueble.toString() + ", "
		+ this.vendedor + ", " + this.comprador;
    }

    @Override
    public ContratoVenta clone() {
	ContratoVenta clon = new ContratoVenta();
	clon.setComprador(comprador);
	clon.setVendedor(vendedor);
	clon.setDocumento(null);
	clon.setInmueble(inmueble);
	clon.setEstadoContrato(EstadoContrato.EnProcesoDeCarga);
	clon.setMoneda(getMoneda());
	clon.setPrecioVenta(precioVenta);
	return clon;
    }

    public static class Builder {

	private Integer comisionAVendedor;
	private Integer comisionAComprador;
	private Persona comprador;
	private Inmueble inmueble;
	private Long id;
	private LocalDate fechaIngreso;
	private LocalDate fechaCelebracion;
	private Blob documento;
	private BigDecimal precioVenta;
	private EstadoRegistro estadoRegistro = EstadoRegistro.ACTIVO;

	public Builder setComisionAVendedor(Integer comision) {
	    this.comisionAVendedor = comision;
	    return this;
	}

	public Builder setComisionAComprador(Integer comision) {
	    this.comisionAComprador = comision;
	    return this;
	}

	public Builder setId(Long dato) {
	    this.id = dato;
	    return this;
	}

	public Builder setFechaIngreso(LocalDate dato) {
	    this.fechaIngreso = dato;
	    return this;
	}

	public Builder setFechaCelebracion(LocalDate dato) {
	    this.fechaCelebracion = dato;
	    return this;
	}

	public Builder setDocumento(Blob dato) {
	    this.documento = dato;
	    return this;
	}

	public Builder setPrecioVenta(BigDecimal dato) {
	    this.precioVenta = dato;
	    return this;
	}

	public Builder setEstadoRegistro(EstadoRegistro estadoRegistro) {
	    this.estadoRegistro = estadoRegistro;
	    return this;
	}

	public Builder setInmueble(Inmueble inmueble) {
	    this.inmueble = inmueble;
	    return this;
	}

	public Builder setComprador(Persona comprador) {
	    this.comprador = comprador;
	    return this;
	}

	public ContratoVenta build() {
	    return new ContratoVenta(this);
	}

    }

}
