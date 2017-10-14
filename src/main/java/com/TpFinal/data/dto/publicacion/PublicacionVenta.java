package com.TpFinal.data.dto.publicacion;

import com.TpFinal.data.dto.EstadoRegistro;
import com.TpFinal.data.dto.contrato.ContratoVenta;
import com.TpFinal.data.dto.inmueble.Inmueble;
import com.TpFinal.data.dto.inmueble.TipoMoneda;
import com.TpFinal.data.dto.persona.Propietario;

import javax.persistence.*;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.math.BigDecimal;
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
	
	@OneToOne
	@Cascade({CascadeType.SAVE_UPDATE})
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
		tipoPublicacion = TipoPublicacion.Venta;
		this.estadoRegistro=EstadoRegistro.ACTIVO;
	}
	
	public static String getpPrecioVenta() {
		return pPrecioVenta;
	}

	public ContratoVenta getContratoVenta() {
		return contratoVenta;
	}

	public void setContratoVenta(ContratoVenta contratoVenta) {
		this.contratoVenta = contratoVenta;
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
	public String toString() {
		return super.toString();
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
	

}
