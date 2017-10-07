package com.TpFinal.data.dto.contrato;

import com.TpFinal.data.dto.EstadoRegistro;
import com.TpFinal.data.dto.inmueble.Inmueble;
import com.TpFinal.data.dto.persona.Inquilino;
import com.TpFinal.data.dto.publicacion.PublicacionAlquiler;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import java.math.BigDecimal;
import java.sql.Blob;
import java.time.LocalDate;

/**
 * Created by Max on 9/30/2017.
 */
@Entity
@Table(name = "contratoAlquiler")
@PrimaryKeyJoinColumn(name = "id")
public class ContratoAlquiler extends Contrato {

    @Enumerated(EnumType.STRING)
    @Column(name = "tipointeresPunitorio")
    private TipoInteres tipoInteresPunitorio;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipoIncrementoCuota")
    private TipoInteres tipoIncrementoCuota;
    @Column(name = "incrementoCuota")
    private Double porcentajeIncrementoCuota;
    @Column(name = "interesPunitorio")
    private Double interesPunitorio;
    @Column(name = "valorInicial")
    private BigDecimal valorInicial;
    @Column(name = "intervaloActualizacionCuota")
    private Integer intervaloActualizacion;
    @Column(name = "diaDePago")
    private Integer diaDePago;
    @Enumerated(EnumType.STRING)
    @Column(name = "duracionContrato")
    private DuracionContrato duracionContrato;

    @OneToOne(cascade = CascadeType.ALL)
    private PublicacionAlquiler operacionAlquiler;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "idRol")
    private Inquilino inquilinoContrato;

    public ContratoAlquiler() {
	super();
    }

    private ContratoAlquiler(Builder b) {
	super(b.id, b.fechaCelebracion, b.documento, b.estadoRegistro, b.inmueble);
	this.interesPunitorio = b.interesPunitorio;
	this.valorInicial = b.valorInicial;
	this.intervaloActualizacion = b.intervaloActualizacion;
	this.diaDePago = b.diaDePago;
	this.operacionAlquiler = b.operacionAlquiler;
	this.inquilinoContrato = b.inquilinoContrato;
	this.tipoIncrementoCuota = b.tipoIncrementoCuota;
	this.tipoInteresPunitorio = b.tipoInteresPunitorio;
	this.duracionContrato = b.duracionContrato;
    }

    public static class Builder {

	private DuracionContrato duracionContrato;
	private TipoInteres tipoInteresPunitorio;
	private TipoInteres tipoIncrementoCuota;
	private Inmueble inmueble;
	private Long id;
	private LocalDate fechaCelebracion;
	private Blob documento;
	private Inquilino inquilinoContrato;
	private BigDecimal valorInicial;
	private Double interesPunitorio;
	private Integer intervaloActualizacion;
	private Integer diaDePago;
	private PublicacionAlquiler operacionAlquiler;
	private EstadoRegistro estadoRegistro = EstadoRegistro.ACTIVO;

	public Builder setId(Long dato) {
	    this.id = dato;
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

	public Builder setInquilinoContrato(Inquilino dato) {
	    this.inquilinoContrato = dato;
	    return this;
	}

	public Builder setValorIncial(BigDecimal dato) {
	    this.valorInicial = dato;
	    return this;
	}

	public Builder setIntervaloActualizacion(Integer intervaloDuracion) {
	    this.intervaloActualizacion = intervaloDuracion;
	    return this;
	}

	public Builder setDiaDePago(Integer diaDePago) {
	    this.diaDePago = diaDePago;
	    return this;
	}

	public Builder setInteresPunitorio(Double interesPunitorio) {
	    this.interesPunitorio = interesPunitorio;
	    return this;
	}

	public Builder setPublicacionAlquiler(PublicacionAlquiler op) {
	    this.operacionAlquiler = op;
	    return this;
	}

	public Builder setInmueble(Inmueble inmueble) {
	    this.inmueble = inmueble;
	    return this;
	}

	public Builder setEstadoRegistro(EstadoRegistro estadoRegistro) {
	    this.estadoRegistro = estadoRegistro;
	    return this;
	}

	public Builder setDuracionContrato(DuracionContrato duracionContrato) {
	    this.duracionContrato = duracionContrato;
	    return this;
	}

	public Builder setTipoInteresPunitorio(TipoInteres tipoInteresPunitorio) {
	    this.tipoInteresPunitorio = tipoInteresPunitorio;
	    return this;
	}

	public Builder setTipoIncrementoCuota(TipoInteres tipoIncrementoCuota) {
	    this.tipoIncrementoCuota = tipoIncrementoCuota;
	    return this;
	}

	public ContratoAlquiler build() {
	    return new ContratoAlquiler(this);
	}
    }

    public Double getInteresPunitorio() {
	return interesPunitorio;
    }

    public void setInteresPunitorio(Double interesPunitorio) {
	this.interesPunitorio = interesPunitorio;
    }

    public BigDecimal getValorInicial() {
	return valorInicial;
    }

    public void setValorInicial(BigDecimal valorInicial) {
	this.valorInicial = valorInicial;
    }

    public Integer getIntervaloActualizacionn() {
	return intervaloActualizacion;
    }

    public void setIntervaloActualizacion(Integer intervaloActualizacion) {
	this.intervaloActualizacion = intervaloActualizacion;
    }

    public Integer getDiaDePago() {
	return diaDePago;
    }

    public void setDiaDePago(Integer diaDePago) {
	this.diaDePago = diaDePago;
    }

    public PublicacionAlquiler getPublicacionAlquiler() {
	return operacionAlquiler;
    }

    public void setPublicacionAlquiler(PublicacionAlquiler operacionAlquiler) {
	this.operacionAlquiler = operacionAlquiler;
    }

    public Inquilino getInquilinoContrato() {
	return inquilinoContrato;
    }

    public void setInquilinoContrato(Inquilino inquilinoContrato) {
	this.inquilinoContrato = inquilinoContrato;
    }

    public TipoInteres getTipoInteresPunitorio() {
        return tipoInteresPunitorio;
    }

    public void setTipoInteresPunitorio(TipoInteres tipoInteresPunitorio) {
        this.tipoInteresPunitorio = tipoInteresPunitorio;
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

    public DuracionContrato getDuracionContrato() {
        return duracionContrato;
    }

    public void setDuracionContrato(DuracionContrato duracionContrato) {
        this.duracionContrato = duracionContrato;
    }

    public PublicacionAlquiler getOperacionAlquiler() {
        return operacionAlquiler;
    }

    public void setOperacionAlquiler(PublicacionAlquiler operacionAlquiler) {
        this.operacionAlquiler = operacionAlquiler;
    }
    

}
