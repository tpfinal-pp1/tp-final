package com.TpFinal.UnitTests.dto.publicacion;

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
