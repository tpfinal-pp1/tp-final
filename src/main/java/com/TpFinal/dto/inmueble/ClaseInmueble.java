package com.TpFinal.dto.inmueble;

import java.util.ArrayList;
import java.util.List;

public enum ClaseInmueble {
    Departamento,
	Campo,
	FondoDeComercio,
	Quinta,
	Casa,
	Local,
	TerrenoOLote,
	Oficina,
	Consultorio,
	Ph,
	DepositoGalpon,
	OtroInmueble;

    public static List<ClaseInmueble> toList() {
	ClaseInmueble[] clases = ClaseInmueble.values();
	List<ClaseInmueble> ret = new ArrayList<>();
	for (ClaseInmueble c : clases) {
	    ret.add(c);
	}
	return ret;
    }
    
    @Override
    public String toString() {
	String ret;
	switch (this) {
	case FondoDeComercio:
	    ret = "Fondo de Comercio";	    
	    break;
	case TerrenoOLote:
	    ret = "Terreno o lote";	    
	    break;	
	case DepositoGalpon:
	    ret = "Depósito o Galpon";
	    break;
	case OtroInmueble:
	    ret = "Otro Imueble";
	    break;    
	default:
	    ret = super.toString();
	    break;
	}
	return ret;
    }

}
