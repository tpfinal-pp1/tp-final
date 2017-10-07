package com.TpFinal.data.dto.publicacion;

import com.TpFinal.data.dto.EstadoRegistro;
import com.TpFinal.data.dto.contrato.ContratoVenta;
import com.TpFinal.data.dto.inmueble.Inmueble;
import com.TpFinal.data.dto.inmueble.TipoMoneda;
import com.TpFinal.data.dto.persona.Propietario;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

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
		this.estadoRegistro=EstadoRegistro.ACTIVO;
	}
	
	private PublicacionVenta(Builder b) {
		this.fechaPublicacion =b.fechaPublicacion;
		this.inmueble = b.inmueble;
		this.moneda = b.moneda;
		this.precio = b.precio;
		this.contratoVenta = b.contratoVenta;
		this.propietarioPublicacion = b.propietario;
		tipoPublicacion = TipoPublicacion.Venta;
		this.estadoRegistro=EstadoRegistro.ACTIVO;
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
		private Propietario propietario;
		
		
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
		public Builder setPropietario(Propietario propietario){
			this.propietario = propietario;
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
