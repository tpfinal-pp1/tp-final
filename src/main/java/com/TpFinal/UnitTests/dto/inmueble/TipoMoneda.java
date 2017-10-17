package com.TpFinal.UnitTests.dto.inmueble;

import java.util.ArrayList;
import java.util.List;

public enum TipoMoneda {Pesos,Dolares;

    public static List<TipoMoneda> toList() {
        TipoMoneda[] tipos = TipoMoneda.values();
        List<TipoMoneda> ret = new ArrayList<>();
        for (TipoMoneda t : tipos) {
            ret.add(t);
        }
        return ret;
    }
}
