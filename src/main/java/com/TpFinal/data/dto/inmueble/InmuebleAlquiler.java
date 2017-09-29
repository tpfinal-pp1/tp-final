package com.TpFinal.data.dto.inmueble;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.javamoney.moneta.Money;


@Entity
@Table(name = "Inmuebles_en_Alquiler")
public class InmuebleAlquiler extends Inmueble {
	
	@Column (name ="valor_cuota")
	private Money valorCuota;

}
