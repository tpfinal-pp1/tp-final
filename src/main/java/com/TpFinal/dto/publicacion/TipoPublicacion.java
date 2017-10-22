package com.TpFinal.dto.publicacion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum TipoPublicacion {Alquiler,Venta;

    public static List<TipoPublicacion> toList() {
       return Arrays.asList(TipoPublicacion.Alquiler, TipoPublicacion.Venta);
    }
}
