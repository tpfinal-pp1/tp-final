package com.TpFinal.dto.persona;

public enum CategoriaEmpleado {
    sinCategoria, admin, agenteInmobilario;
    @Override
    public String toString() {
	String ret;
	switch (this) {
	case sinCategoria:
	    ret = "Sin Categoría";
	    break;
	case admin:
	    ret = "Administrador";
	    break;
	case agenteInmobilario:
	    ret = "Agente Inmobiliario";
	    break;
	default:
	    ret = super.toString();
	    break;
	}
	return ret;
    }

}
