package com.TpFinal.data.dto.contrato;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.sql.Blob;
import java.time.LocalDate;

@Entity
@Table(name="contratoVenta")
@PrimaryKeyJoinColumn(name="id")
public class ContratoVenta extends Contrato {
	
		//TODO cuando este lista la clase mapeo
		//@ManyToOne(cascade=CascadeType., fetch=FetchType.EAGER)
		//@JoinColumn(name="TODO")
		//private InmuebleVenta inmuebleVenta;
		@Column(name="precioVenta")
		private BigDecimal precioVenta;
	
	
		public ContratoVenta() {super();}
		
		private ContratoVenta(Builder b) {
			this.id=b.id;
			this.fechaCelebracion=b.fechaCelebracion;
			this.documento=b.documento;
			//this.inmuebleVenta=b.inmuebleVenta;
			this.precioVenta=b.precioVenta;
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
			
			public ContratoVenta build() {
				return new ContratoVenta(this);
			}
		}
	
	
}
