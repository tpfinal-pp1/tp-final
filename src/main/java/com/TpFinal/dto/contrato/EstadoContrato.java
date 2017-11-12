package com.TpFinal.dto.contrato;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public enum EstadoContrato {
    EnProcesoDeCarga, Vigente, ProximoAVencer, Renovado, Cancelado, Vencido, Celebrado;

    public static List<EstadoContrato> toList() {
	EstadoContrato[] estadosContrato = EstadoContrato.values();
	List<EstadoContrato> ret = new ArrayList<>();
	for (EstadoContrato t : estadosContrato) {
	    ret.add(t);
	}
	ret.sort(Comparator.comparing(EstadoContrato::toString));
	return ret;
    }

    @Override
    public String toString() {
	String ret;
	switch (this) {
	case EnProcesoDeCarga:
	    ret = "En Proceso de carga";
	    break;
	case ProximoAVencer:
	    ret = "Próximo a vencer";
	    break;
	default:
	    ret = super.toString();
	    break;
	}
	return ret;
    }
}