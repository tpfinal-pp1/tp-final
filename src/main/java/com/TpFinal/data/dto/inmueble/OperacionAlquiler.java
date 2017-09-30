package com.TpFinal.data.dto.inmueble;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "operaciones_alquiler")
public class OperacionAlquiler extends Operacion {

	@Column(name = "valor_cuota")
	private BigDecimal valorCuota;

	@Enumerated(EnumType.STRING)
	@Column(name = "moneda")
	private TipoMoneda moneda;

	// TODO Linkear con contrato

	public OperacionAlquiler() {
		super();
	}

	private OperacionAlquiler(Builder b) {
		this.fechaPublicacion = b.fechaPublicacion;
		this.inmueble = b.inmueble;
		this.moneda = b.moneda;
		this.valorCuota = b.valorCuota;
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

		public OperacionAlquiler build() {
			return new OperacionAlquiler(this);
		}
	}

}
