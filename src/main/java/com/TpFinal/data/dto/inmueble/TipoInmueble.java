package com.TpFinal.data.dto.inmueble;

import java.util.ArrayList;
import java.util.List;

public enum TipoInmueble {Vivienda,Comercial;

public static List<TipoInmueble> toList() {
    TipoInmueble[] tipos = TipoInmueble.values();
    List<TipoInmueble> ret = new ArrayList<>();
    for (TipoInmueble t : tipos) {
	ret.add(t);
    }
    return ret;
}

}
