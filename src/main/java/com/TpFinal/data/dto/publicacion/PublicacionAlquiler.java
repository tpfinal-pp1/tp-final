package com.TpFinal.data.dto.publicacion;

import com.TpFinal.data.dto.EstadoRegistro;
import com.TpFinal.data.dto.contrato.ContratoAlquiler;
import com.TpFinal.data.dto.inmueble.Inmueble;
import com.TpFinal.data.dto.inmueble.TipoMoneda;
import com.TpFinal.data.dto.persona.Propietario;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
//@Table(name = "publicaciones_alquiler")
public class PublicacionAlquiler extends Publicacion {

    public static final String pPrecioAlquiler = "valorCuota";

    public BigDecimal getValorCuota() {
	return valorCuota;
    }

    public void setValorCuota(BigDecimal valorCuota) {
	this.valorCuota = valorCuota;
    }

    @Column(name = pPrecioAlquiler)
    private BigDecimal valorCuota;

    @Enumerated(EnumType.STRING)
    @Column(name = "moneda")
    private TipoMoneda moneda;

//    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
//    ContratoAlquiler contratoAlquiler;

    public PublicacionAlquiler() {
	super();
	tipoPublicacion = TipoPublicacion.Alquiler;
	this.estadoRegistro = EstadoRegistro.ACTIVO;
    }

    private PublicacionAlquiler(Builder b) {
	this.fechaPublicacion = b.fechaPublicacion;
	this.inmueble = b.inmueble;
	this.moneda = b.moneda;
	this.valorCuota = b.valorCuota;
	//this.contratoAlquiler = b.contratoAlquiler;
	tipoPublicacion = TipoPublicacion.Alquiler;
	this.estadoRegistro = EstadoRegistro.ACTIVO;
    }

    public BigDecimal getPrecio() {
	return valorCuota;
    }

    public void setPrecio(BigDecimal precio) {
	this.valorCuota = precio;
    }

    public TipoMoneda getMoneda() {
	return moneda;
    }

    public void setMoneda(TipoMoneda moneda) {
	this.moneda = moneda;
    }

    public static class Builder {
	private ContratoAlquiler contratoAlquiler;
	private Inmueble inmueble;
	private LocalDate fechaPublicacion;
	private BigDecimal valorCuota;
	private TipoMoneda moneda;

	public Builder setInmueble(Inmueble inmueble) {
	    this.inmueble = inmueble;
	    return this;
	}

	public Builder setFechaPublicacion(LocalDate fechaPublicacion) {
	    this.fechaPublicacion = fechaPublicacion;
	    return this;
	}

	public Builder setValorCuota(BigDecimal valorCuota) {
	    this.valorCuota = valorCuota;
	    return this;
	}

	public Builder setMoneda(TipoMoneda moneda) {
	    this.moneda = moneda;
	    return this;
	}

	public Builder setContratoAlquiler(ContratoAlquiler contratoAlquiler) {
	    this.contratoAlquiler = contratoAlquiler;
	    return this;
	}

	public PublicacionAlquiler build() {
	    return new PublicacionAlquiler(this);
	}

	@Override
	public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((fechaPublicacion == null) ? 0 : fechaPublicacion.hashCode());
	    result = prime * result + ((inmueble == null) ? 0 : inmueble.hashCode());
	    result = prime * result + ((moneda == null) ? 0 : moneda.hashCode());
	    result = prime * result + ((valorCuota == null) ? 0 : valorCuota.hashCode());
	    return result;
	}

	@Override
	public boolean equals(Object obj) {
	    if (this == obj)
		return true;
	    if (obj == null)
		return false;
	    if (!(obj instanceof Builder))
		return false;
	    Builder other = (Builder) obj;
	    if (fechaPublicacion == null) {
		if (other.fechaPublicacion != null)
		    return false;
	    } else if (!fechaPublicacion.equals(other.fechaPublicacion))
		return false;
	    if (inmueble == null) {
		if (other.inmueble != null)
		    return false;
	    } else if (!inmueble.equals(other.inmueble))
		return false;
	    if (moneda != other.moneda)
		return false;
	    if (valorCuota == null) {
		if (other.valorCuota != null)
		    return false;
	    } else if (!valorCuota.equals(other.valorCuota))
		return false;
	    return true;
	}
	
	

    }

    @Override
    public String toString() {
	return "PublicacionAlquiler \n[\nvalorCuota=" + valorCuota + "\nmoneda=" + moneda + "\nidPublicacion="
		+ idPublicacion
		+ "\nfechaPublicacion=" + fechaPublicacion + "\ntipoPublicacion=" + tipoPublicacion + "\n]";
    }

}
