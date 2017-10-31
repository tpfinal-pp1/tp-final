package com.TpFinal.dto.persona;

public enum EstadoEmpleado {
	ACTIVO,NOACTIVO;
	@Override
	public String toString() {
	    String ret ="";
	    switch (this) {
	    case ACTIVO:
		ret = "Activo";
		break;
	    case NOACTIVO:
		ret = "Dado de baja";
		break;

	    default:
		break;
	    }
	    return ret;
	}
}
