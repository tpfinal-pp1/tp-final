package com.TpFinal.data.dto.cobro;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.TpFinal.data.dto.EstadoRegistro;

@Entity
@Table(name="cobros")
public class Cobro {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
	private Long id;
	
	private Integer numeroCuota;
	private BigDecimal montoOriginal;
	private BigDecimal montoRecibido;
	private BigDecimal interes;
	private BigDecimal comision;
	private BigDecimal montoPropietario;
	private LocalDate fechaDePago;
	private LocalDate fechaDeVencimiento;
	private EstadoCobro estadoCobro;
	private EstadoRegistro estadoRegistro;
	
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

	public void setNumeroCuota(Integer numeroCuota) {
		this.numeroCuota = numeroCuota;
	}

	public void setMontoOriginal(BigDecimal montoOriginal) {
		this.montoOriginal = montoOriginal;
	}

	public void setMontoRecibido(BigDecimal montoRecibido) {
		this.montoRecibido = montoRecibido;
	}

	public void setInteres(BigDecimal interes) {
		this.interes = interes;
	}

	public void setComision(BigDecimal comision) {
		this.comision = comision;
	}

	public void setMontoPropietario(BigDecimal montoPropietario) {
		this.montoPropietario = montoPropietario;
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

	@Override
	public int hashCode() {
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

	@Override
	public boolean equals(Object obj) {
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

	public static class Builder{
		Integer numeroCuota;
		BigDecimal montoOriginal;
		BigDecimal montoRecibido;
		BigDecimal interes;
		BigDecimal comision;
		BigDecimal montoPropietario;
		LocalDate fechaDePago;
		LocalDate fechaDeVencimiento;
		
		public Builder setNumeroCuota(Integer dato) {
			this.numeroCuota=dato;
			return this;
		}
		public Builder setMontoOriginal(BigDecimal dato) {
			this.montoOriginal=dato;
			return this;
		}
		public Builder setMontoRecibido(BigDecimal dato) {
			this.montoRecibido=dato;
			return this;
		}
		public Builder setInteres(BigDecimal dato) {
			this.interes=dato;
			return this;
		}
		public Builder setComision(BigDecimal dato) {
			this.comision=dato;
			return this;
		}
		public Builder setMontoPropietario(BigDecimal dato) {
			this.montoPropietario=dato;
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
		public Cobro build() {
			return new Cobro(this);
		}
		
	}

}
