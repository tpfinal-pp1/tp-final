package com.TpFinal.data.dto.inmueble;

public enum EstadoInmueble {
    Alquilado, EnAlquiler, EnVenta, NoPublicado, Vendido;
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
	default:
	    ret = super.toString();
	    break;
	}
	return ret;
    }
}
