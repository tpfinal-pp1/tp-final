package com.TpFinal.data.dto.inmueble;

public enum EstadoInmueble {Alquilado,EnAlquiler,EnVenta,NoPublicado,Vendido;
     @Override
     public String toString(){
	 String ret ="";
	 switch (this) {
	case Alquilado:
	    ret = "Alquilado";
	    break;
	case EnAlquiler:
	    ret = "En Alquiler";
	    break;
	case EnVenta:
	    ret = "En Venta";
	    break;
	case Vendido:
	    ret = "Vendido";
	    break;	
	case NoPublicado:
	    ret = "No Publicado";
	    break;	
	default:
	    ret = "-";
	    break;
	}
	 return ret;
     }
}
