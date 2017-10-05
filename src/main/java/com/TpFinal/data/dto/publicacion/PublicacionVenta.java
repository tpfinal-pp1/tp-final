package com.TpFinal.data.dto.publicacion;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.TpFinal.data.dto.contrato.ContratoVenta;
import com.TpFinal.data.dto.inmueble.Inmueble;
import com.TpFinal.data.dto.inmueble.TipoMoneda;

@Entity
@Table(name = "publicaciones_venta")
public class PublicacionVenta extends Publicacion {
	
	public static final String pPrecioVenta = "precio";

	public static String getpPrecioVenta() {
		return pPrecioVenta;
	}

	public ContratoVenta getContratoVenta() {
		return contratoVenta;
	}

	public void setContratoVenta(ContratoVenta contratoVenta) {
		this.contratoVenta = contratoVenta;
	}

	@Column(name = pPrecioVenta)

	private BigDecimal precio;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "moneda")
	private TipoMoneda moneda;
	
	@OneToOne(cascade = CascadeType.ALL)
	ContratoVenta contratoVenta;
	
	public PublicacionVenta() {
		super();
		tipoPublicacion = TipoPublicacion.Venta;
	}
	
	private PublicacionVenta(Builder b) {
		this.fechaPublicacion =b.fechaPublicacion;
		this.inmueble = b.inmueble;
		this.moneda = b.moneda;
		this.precio = b.precio;
		this.contratoVenta = b.contratoVenta;
		tipoPublicacion = TipoPublicacion.Venta;
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
		private ContratoVenta contratoVenta;
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
		public Builder setContratoVenta(ContratoVenta contratoVenta) {
			this.contratoVenta = contratoVenta;
			return this;
		}
		public PublicacionVenta build() {
			return new PublicacionVenta(this);
		}		
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
	

}
