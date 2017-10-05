package com.TpFinal.data.dto.contrato;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.TpFinal.data.dto.publicacion.PublicacionVenta;

import java.math.BigDecimal;
import java.sql.Blob;
import java.time.LocalDate;

@Entity
@Table(name="contratoVenta")
@PrimaryKeyJoinColumn(name="id")
public class ContratoVenta extends Contrato {
	
		@Column(name="precioVenta")
		private BigDecimal precioVenta;
		
		@OneToOne(cascade=CascadeType.ALL)
		private PublicacionVenta operacionVenta;
	
		public ContratoVenta() {super();}
		
		private ContratoVenta(Builder b) {
			super(b.id, b.fechaCelebracion, b.documento);
			this.precioVenta=b.precioVenta;
			this.operacionVenta=b.operacionVentaç;
		}
		
		
	
		public PublicacionVenta getOperacionVenta() {
			return operacionVenta;
		}

		public void setOperacionVenta(PublicacionVenta operacionVenta) {
			this.operacionVenta = operacionVenta;
		}

		public BigDecimal getPrecioVenta() {
			return precioVenta;
		}

		public void setPrecioVenta(BigDecimal precioVenta) {
			this.precioVenta = precioVenta;
		}



		public static class Builder{
			
			private Long id;
			private LocalDate fechaCelebracion;
			private Blob documento;
			private BigDecimal precioVenta;
			private PublicacionVenta operacionVentaç;
			
			public Builder setId(Long dato) {
				this.id=dato;
				return this;
			}
			
			public Builder setFechaCelebracion(LocalDate dato) {
				this.fechaCelebracion=dato;
				return this;
			}
			
			public Builder setDocumento(Blob dato) {
				this.documento=dato;
				return this;
			}
			
		
			public Builder setPrecioVenta(BigDecimal dato) {
				this.precioVenta=dato;
				return this;
			}
			
			public Builder setOperacionVenta(PublicacionVenta dato) {
				this.operacionVentaç=dato;
				return this;
			}
			
			public ContratoVenta build() {
				return new ContratoVenta(this);
			}
		}
	
	
}
