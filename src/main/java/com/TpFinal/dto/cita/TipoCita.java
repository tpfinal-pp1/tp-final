package com.TpFinal.dto.cita;

public enum TipoCita { Tasacion,ExhInmueble,CelebContrato,Otros;
    
    @Override
    public String toString(){
	String ret;
	switch (this) {
	case Tasacion:
	    ret = "Tasación";	    
	    break;
	case ExhInmueble:
	    ret = "Exhibición de Inmueble";	    
	    break;	
	case CelebContrato:
	    ret = "Celebración de Contrato";
	    break;
	case Otros:
	    ret = "Cita";
	    break;    
	default:
	    ret = super.toString();
	    break;
	}
	return ret;
    }

}
