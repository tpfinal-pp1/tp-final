package com.TpFinal.data.dto.inmueble;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "operaciones_venta")
public class OperacionVenta extends Operacion {

	@Column(name = "precio_operacion_venta")
	private BigDecimal precio;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "moneda")
	private TipoMoneda moneda;
	
	//TODO Linkear con contrato
	
	public OperacionVenta() {
		super();
	}
	
	private OperacionVenta(Builder b) {
		this.fechaPublicacion =b.fechaPublicacion;
		this.inmueble = b.inmueble;
		this.moneda = b.moneda;
		this.precio = b.precio;
	}
	
	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public TipoMoneda getMoneda() {
		return moneda;
	}

	public void setMoneda(TipoMoneda moneda) {
		this.moneda = moneda;
	}
	
	public static class Builder{
		private Inmueble inmueble;
		private LocalDate fechaPublicacion;
		private BigDecimal precio;
		private TipoMoneda moneda;
		
		
		public Builder setInmueble(Inmueble inmueble) {
			this.inmueble = inmueble;return this;
		}
		public Builder setFechaPublicacion(LocalDate fechaPublicacion) {
			this.fechaPublicacion = fechaPublicacion;return this;
		}
		public Builder setPrecio(BigDecimal precio) {
			this.precio = precio;return this;
		}
		public Builder setMoneda(TipoMoneda moneda) {
			this.moneda = moneda;return this;
		}
		public OperacionVenta build() {
			return new OperacionVenta(this);
		}		
	}
	

}
