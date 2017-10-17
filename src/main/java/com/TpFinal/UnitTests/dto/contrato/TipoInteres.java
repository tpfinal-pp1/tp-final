package com.TpFinal.UnitTests.dto.contrato;

import java.util.ArrayList;

import java.util.List;

public enum TipoInteres {Simple,Acumulativo;

public static List<TipoInteres> toList() {    
    TipoInteres[] tiposInteres = TipoInteres.values();
	List<TipoInteres> ret = new ArrayList<>();

	for (TipoInteres t : tiposInteres) {
	    ret.add(t);
	}

	return ret;
    }
}


