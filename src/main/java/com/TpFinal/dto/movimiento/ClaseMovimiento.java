package com.TpFinal.dto.movimiento;

import java.util.Arrays;
import java.util.List;

public enum ClaseMovimiento {
    Deposito,
	CertificadoGarante,
	Sellado,
	Venta,
	Alquiler,
	Comision,
	Otro,
	PagoAPropietario,
	Impuesto, MesComision;

    @Override
    public String toString() {
	String ret = "";
	switch (this) {
	case Venta:
	    ret = "Venta";
	    break;
	case Sellado:
	    ret = "Sellado";
	    break;
	case CertificadoGarante:
	    ret = "Certificado Garante";
	    break;
	case Deposito:
	    ret = "Depósito";
	    break;

	case Alquiler:
	    ret = "Cuota Alquiler";
	    break;

	case Comision:
	    ret = "Comisión";
	    break;
	case MesComision:
	    ret = "Mes comisión";
	    break;

	case Otro:
	    ret = "Otro";
	    break;

	case PagoAPropietario:
	    ret = "Pago a propietario";
	    break;

	case Impuesto:
	    ret = "Impuesto";
	    break;
	}

	return ret;
    }

    public static List<ClaseMovimiento> toList() {
	return Arrays.asList(ClaseMovimiento.Venta, ClaseMovimiento.Alquiler, ClaseMovimiento.Comision,
		ClaseMovimiento.Otro, ClaseMovimiento.PagoAPropietario, ClaseMovimiento.Impuesto);
    }

    public static List<ClaseMovimiento> getValoresDisponiblesManual() {
	return Arrays.asList(ClaseMovimiento.Otro, ClaseMovimiento.Impuesto);
    }

}
