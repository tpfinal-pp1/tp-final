package com.TpFinal.dto.cobro;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.TpFinal.dto.BorradoLogico;
import com.TpFinal.dto.EstadoRegistro;
import com.TpFinal.dto.Identificable;
import com.TpFinal.dto.contrato.Contrato;
import com.TpFinal.dto.contrato.ContratoAlquiler;
import com.TpFinal.dto.interfaces.Messageable;

@Entity
@Table(name="cobros")
public class Cobro implements Identificable, BorradoLogico, Messageable {

	public static final String pEstadoCobro = "estadoCobro";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	@Column(name="numeroCuota")
	private Integer numeroCuota;
	@Column(name="montoOriginal")
	private BigDecimal montoOriginal;
	@Column(name="montoRecibido")
	private BigDecimal montoRecibido;
	@Column(name="interes")
	private BigDecimal interes;
	@Column(name="comision")
	private BigDecimal comision;
	@Column(name="montoPropietario")
	private BigDecimal montoPropietario;
	@Column(name="fechaDePago")
	private LocalDate fechaDePago;
	@Column(name="fechaDeVencimiento")
	private LocalDate fechaDeVencimiento;
	@Column(name= Cobro.pEstadoCobro)
	@Enumerated(EnumType.STRING)
	private EstadoCobro estadoCobro;
	@Column(name="estadoRegistro")
	@Enumerated(EnumType.STRING)
	private EstadoRegistro estadoRegistro;
	@Column(name="tipoCobro")
	@Enumerated(EnumType.STRING)
	private TipoCobro tipoCobro;
	@ManyToOne
	@Cascade({CascadeType.SAVE_UPDATE})
	@JoinColumn(name="idContrato")
	private Contrato contrato;
	@Column(name="randomKey")
	UUID randomKey;

	public Cobro() {}

	private Cobro(Builder b) {
		this.numeroCuota=b.numeroCuota;
		this.montoOriginal=b.montoOriginal;
		this.montoRecibido=b.montoRecibido;
		this.interes=b.interes;
		this.comision=b.comision;
		this.montoPropietario=b.montoPropietario;
		this.fechaDePago=b.fechaDePago;
		this.fechaDeVencimiento=b.fechaDeVencimiento;
		this.estadoCobro=EstadoCobro.NOCOBRADO;
		this.estadoRegistro=EstadoRegistro.ACTIVO;
		this.tipoCobro=b.tipoCobro;
		this.randomKey=UUID.randomUUID();
	}

	@Override
	public Long getId() {
		return this.id;
	}
	
	@Override
	public String getTriggerKey() {
		return this.id.toString()+"-"+this.randomKey.toString();
	}

	public void SetId(Long id) {
		this.id=id;
	}

	public Integer getNumeroCuota() {
		return numeroCuota;
	}

	public BigDecimal getMontoOriginal() {
		return montoOriginal;
	}

	public BigDecimal getMontoRecibido() {
		return montoRecibido;
	}

	public BigDecimal getInteres() {
		return interes;
	}

	public BigDecimal getComision() {
		return comision;
	}

	public BigDecimal getMontoPropietario() {
		return montoPropietario;
	}

	public LocalDate getFechaDePago() {
		return fechaDePago;
	}

	public LocalDate getFechaDeVencimiento() {
		return fechaDeVencimiento;
	}
	
	public TipoCobro getTipoCobro() {
		return tipoCobro;
	}

	public void setTipoCobro(TipoCobro tipoCobro) {
		this.tipoCobro = tipoCobro;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setNumeroCuota(Integer numeroCuota) {
		this.numeroCuota = numeroCuota;
	}

	public void setMontoOriginal(BigDecimal montoOriginal) {
		this.montoOriginal = montoOriginal;
		this.montoOriginal=this.montoOriginal.setScale(2, RoundingMode.CEILING);
	}

	public void setMontoRecibido(BigDecimal montoRecibido) {
		this.montoRecibido = montoRecibido;
		this.montoRecibido = this.montoRecibido.setScale(2, RoundingMode.CEILING);
	}

	public void setInteres(BigDecimal interes) {
		this.interes = interes;
		this.interes= this.interes.setScale(2, RoundingMode.CEILING);
	}

	public void setComision(BigDecimal comision) {
		this.comision = comision;
		this.comision=this.comision.setScale(2, RoundingMode.CEILING);
	}

	public void setMontoPropietario(BigDecimal montoPropietario) {
		this.montoPropietario = montoPropietario;
		this.montoPropietario=this.montoPropietario.setScale(2, RoundingMode.CEILING);
	}

	public void setFechaDePago(LocalDate fechaDePago) {
		this.fechaDePago = fechaDePago;
	}

	public void setFechaDeVencimiento(LocalDate fechaDeVencimiento) {
		this.fechaDeVencimiento = fechaDeVencimiento;
	}

	public EstadoCobro getEstadoCobro() {
		return estadoCobro;
	}

	public EstadoRegistro getEstadoRegistro() {
		return estadoRegistro;
	}

	public void setEstadoCobro(EstadoCobro estadoCobro) {
		this.estadoCobro = estadoCobro;
	}

	public void setEstadoRegistro(EstadoRegistro estadoRegistro) {
		this.estadoRegistro = estadoRegistro;
	}

	public Contrato getContrato() {
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		if(this.contrato!=null && !this.contrato.equals(contrato))
			this.contrato.removeCobro(this);
		this.contrato = contrato;
		if(contrato!=null && !this.contrato.getCobros().contains(this))
			this.contrato.addCobro(this);
	}
	
	public UUID getRandomKey() {
		return randomKey;
	}

	public void setRandomKey(UUID randomKey) {
		this.randomKey = randomKey;
	}

	public String getEstadoCobroString() {
		if (this.estadoCobro.equals(EstadoCobro.COBRADO)){
			return "Cobrado";
		}
		else return "No Cobrado";
	}

	public int oldHashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comision == null) ? 0 : comision.hashCode());
		result = prime * result + ((fechaDePago == null) ? 0 : fechaDePago.hashCode());
		result = prime * result + ((fechaDeVencimiento == null) ? 0 : fechaDeVencimiento.hashCode());
		result = prime * result + ((interes == null) ? 0 : interes.hashCode());
		result = prime * result + ((montoOriginal == null) ? 0 : montoOriginal.hashCode());
		result = prime * result + ((montoPropietario == null) ? 0 : montoPropietario.hashCode());
		result = prime * result + ((montoRecibido == null) ? 0 : montoRecibido.hashCode());
		result = prime * result + ((numeroCuota == null) ? 0 : numeroCuota.hashCode());
		return result;
	}

	public boolean isSame(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cobro other = (Cobro) obj;
		if (comision == null) {
			if (other.comision != null)
				return false;
		} else if (!comision.equals(other.comision))
			return false;
		if (fechaDePago == null) {
			if (other.fechaDePago != null)
				return false;
		} else if (!fechaDePago.equals(other.fechaDePago))
			return false;
		if (fechaDeVencimiento == null) {
			if (other.fechaDeVencimiento != null)
				return false;
		} else if (!fechaDeVencimiento.equals(other.fechaDeVencimiento))
			return false;
		if (interes == null) {
			if (other.interes != null)
				return false;
		} else if (!interes.equals(other.interes))
			return false;
		if (montoOriginal == null) {
			if (other.montoOriginal != null)
				return false;
		} else if (!montoOriginal.equals(other.montoOriginal))
			return false;
		if (montoPropietario == null) {
			if (other.montoPropietario != null)
				return false;
		} else if (!montoPropietario.equals(other.montoPropietario))
			return false;
		if (montoRecibido == null) {
			if (other.montoRecibido != null)
				return false;
		} else if (!montoRecibido.equals(other.montoRecibido))
			return false;
		if (numeroCuota == null) {
			if (other.numeroCuota != null)
				return false;
		} else if (!numeroCuota.equals(other.numeroCuota))
			return false;
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Cobro))
			return false;
		Cobro cobro = (Cobro) o;
		return getId() != null && Objects.equals(getId(), cobro.getId());
	}

	@Override
	public int hashCode() {
		return 37;
	}

	@Override
	public String toString() {
		return this.contrato.getInmueble().getDireccion().toString();
	}

	public static class Builder{
		Integer numeroCuota;
		BigDecimal montoOriginal;
		BigDecimal montoRecibido;
		BigDecimal interes;
		BigDecimal comision;
		BigDecimal montoPropietario;
		LocalDate fechaDePago;
		LocalDate fechaDeVencimiento;
		TipoCobro tipoCobro;

		public Builder setNumeroCuota(Integer dato) {
			this.numeroCuota=dato;
			return this;
		}
		public Builder setMontoOriginal(BigDecimal dato) {
			this.montoOriginal=dato;
			this.montoOriginal=this.montoOriginal.setScale(2, RoundingMode.CEILING);
			return this;
		}
		public Builder setMontoRecibido(BigDecimal dato) {
			this.montoRecibido=dato;
			this.montoRecibido=this.montoRecibido.setScale(2, RoundingMode.CEILING);
			return this;
		}
		public Builder setInteres(BigDecimal dato) {
			this.interes=dato;
			this.interes=this.interes.setScale(2, RoundingMode.CEILING);
			return this;
		}
		public Builder setComision(BigDecimal dato) {
			this.comision=dato;
			this.comision=this.comision.setScale(2, RoundingMode.CEILING);
			return this;
		}
		public Builder setMontoPropietario(BigDecimal dato) {
			this.montoPropietario=dato;
			this.montoPropietario=this.montoPropietario.setScale(2, RoundingMode.CEILING);
			return this;
		}
		public Builder setFechaDePago(LocalDate dato) {
			this.fechaDePago=dato;
			return this;
		}
		public Builder setFechaDeVencimiento(LocalDate dato) {
			this.fechaDeVencimiento=dato;
			return this;
		}
		public Builder setTipoCobro(TipoCobro dato) {
			this.tipoCobro=dato;
			return this;
		}
		public Cobro build() {
			return new Cobro(this);
		}

	}

	@Override
	public String getTitulo() {
		ContratoAlquiler contrato=(ContratoAlquiler)this.contrato;
		return "Vencimiento, "+contrato.getInquilinoContrato().getPersona().getApellido()+" "+contrato.getInquilinoContrato().getPersona().getNombre();
	}

	@Override
	public String getMessage() {
		return "Venc: "+this.fechaDeVencimiento.format(new DateTimeFormatterBuilder().appendPattern("dd/MM/YYYY").toFormatter())+"\n"+", Monto: $"+this.montoRecibido;
	}


}
