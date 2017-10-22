package com.TpFinal.dto.inmueble;

import java.util.Arrays;
import java.util.List;

public enum EstadoInmueble {
    Alquilado, EnAlquiler, EnAlquilerYVenta, EnVenta, NoPublicado, Vendido;
    @Override
    public String toString() {
	String ret = "";
	switch (this) {
	case EnAlquiler:
	    ret = "En Alquiler";
	    break;
	case EnVenta:
	    ret = "En Venta";
	    break;
	case NoPublicado:
	    ret = "No Publicado";
	    break;
	case EnAlquilerYVenta:
	    ret = "En Alquiler y Venta";
	    break;
	default:
	    ret = super.toString();
	    break;
	}
	return ret;
    }
    
    public static List<EstadoInmueble> toList() {
    	return Arrays.asList(EstadoInmueble.EnAlquiler,EstadoInmueble.EnVenta,EstadoInmueble.EnAlquilerYVenta, EstadoInmueble.Vendido, EstadoInmueble.Alquilado);
        }

    public static List<EstadoInmueble> toListPublicado() {
	return Arrays.asList(EstadoInmueble.EnAlquiler,EstadoInmueble.EnVenta,EstadoInmueble.EnAlquilerYVenta);
    }
}
