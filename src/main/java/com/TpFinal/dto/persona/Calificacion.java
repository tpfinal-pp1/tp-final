package com.TpFinal.dto.persona;

public enum Calificacion {
    A, B, C, D;

    @Override
    public String toString() {
	String ret = super.toString();
	switch (this) {
	case A:
	    ret = "A - Muy Malo";
	    break;
	case B:
	    ret = "B - Malo";
	    break;
	case C:
	    ret = "C - Regular";
	    break;
	case D:
	    ret = "D - Bueno";
	    break;

	default:
	    break;
	}
	return ret;
    }
}
