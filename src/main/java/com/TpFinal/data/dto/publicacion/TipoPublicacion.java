package com.TpFinal.data.dto.publicacion;

import com.TpFinal.data.dto.inmueble.TipoMoneda;

import java.util.ArrayList;
import java.util.List;

public enum TipoPublicacion {Alquiler,Venta;

    public static List<TipoPublicacion> toList() {
        TipoPublicacion[] tipos = TipoPublicacion.values();
        List<TipoPublicacion> ret = new ArrayList<>();
        for (TipoPublicacion t : tipos) {
            ret.add(t);
        }
        return ret;
    }
}
