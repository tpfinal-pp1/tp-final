package com.TpFinal.dto.movimiento;

import java.util.Arrays;
import java.util.List;

public enum ClaseMovimiento {
	Venta, Alquiler, Comisión, Otro, PagoAPropietario, Impuesto;
	
	
	 @Override
	    public String toString() {
		String ret = "";
		switch (this) {
		case Venta:
		    ret = "Venta";
		    break;
		    
		case Alquiler:
			ret="Alquiler";
			break;
			
		case Comisión:
			ret="Comisión";
			break;
		
		case Otro:
			ret="Otro";
			break;
		
		case PagoAPropietario:
			ret="PagoAPropietario";
			break;
			
		case Impuesto:
			ret="Impuesto";
			break;
		}
		
		return ret;
	 }
	 
	 public static List <ClaseMovimiento> toList(){
		 return Arrays.asList(ClaseMovimiento.Venta, ClaseMovimiento.Alquiler, ClaseMovimiento.Comisión, ClaseMovimiento.Otro, ClaseMovimiento.PagoAPropietario, ClaseMovimiento.Impuesto);
	 }
	 
	 public static List<ClaseMovimiento> getValoresDisponiblesManual(){
		 return Arrays.asList(ClaseMovimiento.Otro, ClaseMovimiento.Impuesto);
	 }
	 
}
