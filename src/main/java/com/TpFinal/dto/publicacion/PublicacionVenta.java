package com.TpFinal.dto.publicacion;

import com.TpFinal.dto.EstadoRegistro;
import com.TpFinal.dto.inmueble.Inmueble;
import com.TpFinal.dto.inmueble.TipoMoneda;

import javax.persistence.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "publicaciones_venta")
public class PublicacionVenta extends Publicacion {
	
	public static final String pPrecioVenta = "precio";
	@Column(name = pPrecioVenta)
	private BigDecimal precio;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "moneda")
	private TipoMoneda moneda;
	
	
	
	public PublicacionVenta() {
		super();
		tipoPublicacion = TipoPublicacion.Venta;
		this.estadoRegistro=EstadoRegistro.ACTIVO;
	}
	
	private PublicacionVenta(Builder b) {
		this.fechaPublicacion =b.fechaPublicacion;
		this.inmueble = b.inmueble;
		this.moneda = b.moneda;
		this.precio = b.precio.setScale(2,RoundingMode.HALF_UP);;
		tipoPublicacion = TipoPublicacion.Venta;
		this.estadoRegistro=EstadoRegistro.ACTIVO;
	}
	
	public static String getpPrecioVenta() {
		return pPrecioVenta;
	}
	
	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
		this.precio=this.precio.setScale(2,RoundingMode.HALF_UP);
	}

	public TipoMoneda getMoneda() {
		return moneda;
	}

	public void setMoneda(TipoMoneda moneda) {
		this.moneda = moneda;
	}
	
	
	
	@Override
	public String toString() {
	    return "PublicacionVenta [precio=" + precio + ", moneda=" + moneda + ", idPublicacion=" + idPublicacion
		    + ", fechaPublicacion=" + fechaPublicacion + ", tipoPublicacion=" + tipoPublicacion
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
		if(!(obj instanceof PublicacionVenta)) return false;
		PublicacionVenta p = (PublicacionVenta)obj;
		return Objects.equals(p.getId(), this.getId());
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
		public PublicacionVenta build() {
			return new PublicacionVenta(this);
		}		
	}
	

}
