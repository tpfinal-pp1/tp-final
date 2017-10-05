package com.TpFinal.data.dto.contrato;

import com.TpFinal.data.dto.EstadoRegistro;
import com.TpFinal.data.dto.persona.Inquilino;
import com.TpFinal.data.dto.publicacion.PublicacionAlquiler;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name="contratoAlquiler")
@PrimaryKeyJoinColumn(name="id")
public class ContratoAlquiler extends Contrato {

    @Column(name="interesPunitorio")
    private Double interesPunitorio;
    @Column(name="valorInicial")
    private BigDecimal valorInicial;
    @Column(name="intervaloDuracion")
    private Integer intervaloDuracion;
    @Column(name="diaDePago")
    private Integer diaDePago;
    @Column(name="fechaDePago")
    private LocalDate fechaDePago;
    @OneToOne(cascade=CascadeType.ALL)
    private PublicacionAlquiler operacionAlquiler;
    @ManyToOne(fetch=FetchType.EAGER, cascade= CascadeType.ALL)
    @JoinColumn(name = "idRol")
    private Inquilino inquilinoContrato;
    
    public ContratoAlquiler() {super();}

    private ContratoAlquiler(Builder b) {
    	super(b.id, b.fechaDePago, b.documento, b.estadoRegistro);
        this.fechaCelebracion = b.fechaCelebracion;
        this.interesPunitorio = b.interesPunitorio;
        this.valorInicial = b.valorInicial;
        this.intervaloDuracion = b.intervaloDuracion;
        this.diaDePago = b.diaDePago;
        this.operacionAlquiler=b.operacionAlquiler;
        this.inquilinoContrato = b.inquilinoContrato;
    }


    public static class Builder{

        private Long id;
        private LocalDate fechaCelebracion;
        private Blob documento;
        private Inquilino inquilinoContrato;
        private BigDecimal valorInicial;
        private Double interesPunitorio;
        private Integer intervaloDuracion;
        private Integer diaDePago;
        private LocalDate fechaDePago;
        private PublicacionAlquiler operacionAlquiler;
        private EstadoRegistro estadoRegistro;

        public Builder setId(Long dato) {
            this.id=dato;
            return this;
        }

        public Builder setFechaCelebracion(LocalDate dato) {
            this.fechaCelebracion=dato;
            return this;
        }

        public Builder setDocumento(Blob dato) {
            this.documento=dato;
            return this;
        }

        public Builder setInquilinoContrato(Inquilino dato) {
            this.inquilinoContrato=dato;
            return this;
        }

        public Builder setValorIncial(BigDecimal dato) {
            this.valorInicial=dato;
            return this;
        }

        public Builder setIntervaloDuracion(Integer intervaloDuracion) {
            this.intervaloDuracion = intervaloDuracion;
            return this;
        }

        public Builder setDiaDePago(Integer diaDePago) {
            this.diaDePago = diaDePago;
            return this;
        }

        public Builder setFechaDePago(LocalDate fechaDePago) {
            this.fechaDePago = fechaDePago;
            return this;
        }

        public Builder setInteresPunitorio(Double interesPunitorio) {
            this.interesPunitorio = interesPunitorio;
            return this;
        }
        
        public Builder setPublicacionAlquiler(PublicacionAlquiler op) {
        	this.operacionAlquiler=op;
        	return this;
        }
        
        public Builder setEstadoRegistro(EstadoRegistro dato) {
        	this.estadoRegistro=dato;
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

    public Integer getIntervaloDuracion() {
        return intervaloDuracion;
    }

    public void setIntervaloDuracion(Integer intervaloDuracion) {
        this.intervaloDuracion = intervaloDuracion;
    }

    public Integer getDiaDePago() {
        return diaDePago;
    }

    public void setDiaDePago(Integer diaDePago) {
        this.diaDePago = diaDePago;
    }

    public LocalDate getFechaDePago() {
        return fechaDePago;
    }

    public void setFechaDePago(LocalDate fechaDePago) {
        this.fechaDePago = fechaDePago;
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
    
    

}
