package com.TpFinal.data.dto.inmueble;

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
	Cochera,
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

}
