package com.TpFinal.dto.publicacion;

import com.TpFinal.dto.EstadoRegistro;
import com.TpFinal.dto.contrato.ContratoDuracion;
import com.TpFinal.dto.contrato.TipoInteres;
import com.TpFinal.dto.inmueble.Inmueble;
import com.TpFinal.dto.inmueble.TipoMoneda;

import javax.persistence.*;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "publicaciones_alquiler")
public class PublicacionAlquiler extends Publicacion {

    public static final String pPrecioAlquiler = "valorCuota";
    @Column(name = pPrecioAlquiler)
    private BigDecimal valorCuota;

    // XXX refinamiento de requerimientos
    @Enumerated(EnumType.STRING)
    @Column(name = "tipoIncrementoCuota")
    private TipoInteres tipoIncrementoCuota;
    @Column(name = "incrementoCuota")
    private Double porcentajeIncrementoCuota;
    @Column(name = "intervaloActualizacionCuota")
    private Integer intervaloActualizacion;
    @ManyToOne(fetch = FetchType.EAGER)
    @Cascade({ CascadeType.SAVE_UPDATE, CascadeType.MERGE })
    @JoinColumn(name = "duracionContrato_Id")
    private ContratoDuracion duracionContrato;
    @Column(name = "cantidad_certificados_garantes")
    private Integer cantCertificadosGarantes = 2;

    // XXX

    @Enumerated(EnumType.STRING)
    @Column(name = "moneda")
    private TipoMoneda moneda;

    
    
    public Integer getCantCertificadosGarantes() {
        return cantCertificadosGarantes;
    }

    public void setCantCertificadosGarantes(Integer cantCertificadosGarantes) {
        this.cantCertificadosGarantes = cantCertificadosGarantes;
    }

    public TipoInteres getTipoIncrementoCuota() {
	return tipoIncrementoCuota;
    }

    public void setTipoIncrementoCuota(TipoInteres tipoIncrementoCuota) {
	this.tipoIncrementoCuota = tipoIncrementoCuota;
    }

    public Double getPorcentajeIncrementoCuota() {
	return porcentajeIncrementoCuota;
    }

    public void setPorcentajeIncrementoCuota(Double porcentajeIncrementoCuota) {
	this.porcentajeIncrementoCuota = porcentajeIncrementoCuota;
    }

    public Integer getIntervaloActualizacion() {
	return intervaloActualizacion;
    }

    public void setIntervaloActualizacion(Integer intervaloActualizacion) {
	this.intervaloActualizacion = intervaloActualizacion;
    }

    public ContratoDuracion getDuracionContrato() {
	return duracionContrato;
    }

    public void setDuracionContrato(ContratoDuracion duracionContrato) {
	this.duracionContrato = duracionContrato;
    }

    public BigDecimal getValorCuota() {
	return valorCuota;
    }

    public void setValorCuota(BigDecimal valorCuota) {
	this.valorCuota = valorCuota;
	this.valorCuota = this.valorCuota.setScale(2, RoundingMode.CEILING);
    }

    public PublicacionAlquiler() {
	super();
	tipoPublicacion = TipoPublicacion.Alquiler;
	this.estadoRegistro = EstadoRegistro.ACTIVO;
    }

    private PublicacionAlquiler(Builder b) {
	this.fechaPublicacion = b.fechaPublicacion;
	this.inmueble = b.inmueble;
	this.moneda = b.moneda;
	this.duracionContrato = b.duracionContrato;
	this.cantCertificadosGarantes = b.cantidadCertificadosGarantes;
	this.intervaloActualizacion = b.intervaloActualizacion;
	this.porcentajeIncrementoCuota = b.porcentajeIncrementoCuota;
	this.tipoIncrementoCuota = b.tipoIncrementoCuota;
	this.valorCuota = b.valorCuota.setScale(2, RoundingMode.CEILING);
	;
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
	private TipoInteres tipoIncrementoCuota;
	private Double porcentajeIncrementoCuota;
	private Integer intervaloActualizacion;
	private Integer cantidadCertificadosGarantes;
	private ContratoDuracion duracionContrato;
	private Inmueble inmueble;
	private LocalDate fechaPublicacion;
	private BigDecimal valorCuota;
	private TipoMoneda moneda;

	public Builder setContratoDuracion(ContratoDuracion duracion) {
	    this.duracionContrato = duracion;
	    return this;
	}

	public Builder setCantidadDeCertificados(Integer val) {
	    this.cantidadCertificadosGarantes = val;
	    return this;
	}

	public Builder setIntervaloActualizacion(Integer val) {
	    this.intervaloActualizacion = val;
	    return this;

	}

	public Builder setPorcentajeIncrementoCuota(Double p) {
	    this.porcentajeIncrementoCuota = p;
	    return this;
	}

	public Builder setTipoIncrementoCuota(TipoInteres tipo) {
	    this.tipoIncrementoCuota = tipo;
	    return this;
	}

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

	public PublicacionAlquiler build() {
	    return new PublicacionAlquiler(this);
	}

    }

    @Override
    public String toString() {
	return "PublicacionAlquiler [valorCuota=" + valorCuota + ", moneda=" + moneda + ", idPublicacion="
		+ idPublicacion + ", fechaPublicacion=" + fechaPublicacion + ", tipoPublicacion=" + tipoPublicacion
		+ ", estadoPublicacion=" + estadoPublicacion + ", estadoRegistro=" + estadoRegistro + "]";
    }

    @Override
    public int hashCode() {
	return Objects.hash(this.getId());
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (!(obj instanceof PublicacionAlquiler))
	    return false;
	PublicacionAlquiler p = (PublicacionAlquiler) obj;
	return Objects.equals(p.getId(), this.getId());
    }

}
