package com.TpFinal.dto.inmueble;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum TipoInmueble {Vivienda,Comercial;

public static List<TipoInmueble> toList() {
    return Arrays.asList(TipoInmueble.Comercial, TipoInmueble.Vivienda);
}

}
