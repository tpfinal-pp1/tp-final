package com.TpFinal.data.dto.contrato;

import com.TpFinal.data.dto.EstadoRegistro;
import com.TpFinal.data.dto.cobro.Cobro;
import com.TpFinal.data.dto.inmueble.Inmueble;
import com.TpFinal.data.dto.persona.Inquilino;
import com.TpFinal.data.dto.persona.Persona;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.math.BigDecimal;
import java.sql.Blob;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @Cascade({ CascadeType.SAVE_UPDATE ,CascadeType.MERGE})
    @JoinColumn(name = "idRol")
    private Inquilino inquilinoContrato;

    @ManyToOne
    @Cascade({ CascadeType.SAVE_UPDATE,CascadeType.MERGE })
    @JoinColumn(name = "id_propietario")
    private Persona propietario;
    @OneToMany(mappedBy="contrato", fetch=FetchType.EAGER)
    @Cascade({CascadeType.ALL})
    private Set<Cobro>cobros;

    public ContratoAlquiler() {
	super();
    }

    private ContratoAlquiler(Builder b) {
	super(b.id, b.fechaCelebracion, b.documento, b.estadoRegistro, b.inmueble);
	this.interesPunitorio = b.interesPunitorio;
	this.valorInicial = b.valorInicial;
	this.intervaloActualizacion = b.intervaloActualizacion;
	this.diaDePago = b.diaDePago;
	this.inquilinoContrato = b.inquilinoContrato;
	this.tipoIncrementoCuota = b.tipoIncrementoCuota;
	this.tipoInteresPunitorio = b.tipoInteresPunitorio;
	this.duracionContrato = b.duracionContrato;
	this.porcentajeIncrementoCuota=b.porcentajeIncrementoCuota;
	cobros=new HashSet<>();

	if (b.inmueble != null) {
	    this.propietario = b.inmueble.getPropietario() != null ? b.inmueble.getPropietario().getPersona() : null;
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

    public Integer getDiaDePago() {
	return diaDePago;
    }

    public void setDiaDePago(Integer diaDePago) {
	this.diaDePago = diaDePago;
    }

    public Inquilino getInquilinoContrato() {
	return inquilinoContrato;
    }

    public void setInquilinoContrato(Inquilino inquilinoContrato) {
	if (this.inquilinoContrato != null && !this.inquilinoContrato.equals(inquilinoContrato)) {
	    this.inquilinoContrato.removeContrato(this);
	}
	this.inquilinoContrato = inquilinoContrato;
	if(inquilinoContrato !=  null && !inquilinoContrato.getContratos().contains(this))
	    inquilinoContrato.addContrato(this);
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

    public Persona getPropietario() {
	return propietario;
    }

    public void setPropietario(Persona propietario) {	
	this.propietario = propietario;
    }

    public Integer getIntervaloActualizacion() {
	return intervaloActualizacion;
    }

    public void setIntervaloActualizacion(Integer intervaloActualizacion) {
	this.intervaloActualizacion = intervaloActualizacion;
    }
    
    public Set<Cobro> getCobros() {
		return cobros;
	}

	public void setCobros(Set<Cobro> cobros) {
		this.cobros = cobros;
	}
	
	public void addCobro(Cobro c) {
		if(!this.cobros.contains(c)) {
			this.cobros.add(c);
		}
	}
	
	public void removeCobro(Cobro c) {
		if(this.cobros.contains(c)) {
			this.cobros.remove(c);
		}
	}
	@Override
	public String toString() {
		return  inmueble.toString()+", "
				+this.propietario.toString()+", "+this.inquilinoContrato ;
	}

	@Override
    public boolean equals(Object o) {
	if (this == o)
	    return true;
	if (!(o instanceof ContratoAlquiler))
	    return false;
	ContratoAlquiler contrato = (ContratoAlquiler) o;
	return getId() != null && Objects.equals(getId(), contrato.getId());
    }

    @Override
    public int hashCode() {
	return 3;
    }
    
    private void agregarCobros() {
    	if(this.duracionContrato!=null) {
    		BigDecimal valorAnterior = this.valorInicial;
    		for(int i=0; i<this.duracionContrato.getDuracion(); i++) {
    			//si el dia de celebracion es mayor o igual al dia de pago entonces las coutas empiezan el proximo mes
    			LocalDate fechaCobro=LocalDate.of(fechaCelebracion.getDayOfMonth(), fechaCelebracion.getMonthValue(), this.diaDePago);
    			if(fechaCelebracion.getDayOfMonth()>=(int)this.diaDePago) {
        			fechaCobro=fechaCobro.plusMonths(i+1);
    			}else {
    				fechaCobro=fechaCobro.plusMonths(i);
    			}
    			
    			Cobro c =new Cobro.Builder()
    					.setNumeroCuota(i)
    					.setFechaDePago(fechaCobro)
    					.setMontoOriginal(valorAnterior)
    					.build();
    			if(i+1%this.intervaloActualizacion==0) {
    				if(this.tipoIncrementoCuota.equals(TipoInteres.Acumulativo)) {
    					BigDecimal incremento= new BigDecimal(this.porcentajeIncrementoCuota.toString());
    					BigDecimal aux = valorAnterior.multiply(incremento);
    					valorAnterior=valorAnterior.add(aux);
    				}else if(this.tipoIncrementoCuota.equals(TipoInteres.Simple)) {
    					BigDecimal incremento= new BigDecimal(this.porcentajeIncrementoCuota.toString());
    					BigDecimal aux = this.valorInicial.multiply(incremento);
    					valorAnterior=valorAnterior.add(aux);
    				}
    			}
    			this.cobros.add(c);
    		}
    	}
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
	private Double porcentajeIncrementoCuota;
	private Integer intervaloActualizacion;
	private Integer diaDePago;
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
	
	public Builder setPorcentajeIncremento(Double porcentaje) {
		this.porcentajeIncrementoCuota=porcentaje;
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

}
