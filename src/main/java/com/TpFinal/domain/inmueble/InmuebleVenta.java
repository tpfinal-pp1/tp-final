package com.TpFinal.domain.inmueble;

import javax.persistence.Column;

import org.javamoney.moneta.Money;


//@Entity
//@Table(name = "Inmuebles_en_Venta")
public class InmuebleVenta extends Inmueble{
	
	@Column (name ="precio")
	private Money precio;

}
