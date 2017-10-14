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
//@Table(name = "publicaciones_venta")
public class PublicacionVenta extends Publicacion {
	
	public static final String pPrecioVenta = "precio";

	public static String getpPrecioVenta() {
		return pPrecioVenta;
	}

//	public ContratoVenta getContratoVenta() {
//		return contratoVenta;
//	}
//
//	public void setContratoVenta(ContratoVenta contratoVenta) {
//		this.contratoVenta = contratoVenta;
//	}

	@Column(name = pPrecioVenta)

	private BigDecimal precio;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "moneda")
	private TipoMoneda moneda;
	
//	@OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
//	ContratoVenta contratoVenta;
	
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
		//this.contratoVenta = b.contratoVenta;
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
	
	
	@Override
	public int hashCode() {
	    final int prime = 31;
	    int result = super.hashCode();
	    result = prime * result + ((moneda == null) ? 0 : moneda.hashCode());
	    result = prime * result + ((precio == null) ? 0 : precio.hashCode());
	    return result;
	}

	@Override
	public boolean equals(Object obj) {
	    if (this == obj)
		return true;
	    if (!super.equals(obj))
		return false;
	    if (!(obj instanceof PublicacionVenta))
		return false;
	    PublicacionVenta other = (PublicacionVenta) obj;
	    if (moneda != other.moneda)
		return false;
	    if (precio == null) {
		if (other.precio != null)
		    return false;
	    } else if (!precio.equals(other.precio))
		return false;
	    return true;
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
