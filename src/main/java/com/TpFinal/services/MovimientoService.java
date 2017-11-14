package com.TpFinal.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.TpFinal.data.dao.DAOMovimientoImpl;
import com.TpFinal.data.dao.interfaces.DAOMovimiento;
import com.TpFinal.dto.EstadoRegistro;
import com.TpFinal.dto.cobro.Cobro;
import com.TpFinal.dto.contrato.ContratoAlquiler;
import com.TpFinal.dto.contrato.ContratoVenta;
import com.TpFinal.dto.movimiento.ClaseMovimiento;
import com.TpFinal.dto.movimiento.Movimiento;
import com.TpFinal.dto.movimiento.TipoMovimiento;

public class MovimientoService {
	DAOMovimiento dao;
	
	public MovimientoService() {
		dao = new DAOMovimientoImpl();
	}
	
	public boolean saveOrUpdate(Movimiento m) {
		return dao.saveOrUpdate(m);
	    }

	    public boolean delete(Movimiento m) {
		return dao.logicalDelete(m);
	    }

	    public List<Movimiento> readAll() {
		return dao.readAllActives();
	    }
	    
	    public static Movimiento getInstanciaOtro() {
	    	return new Movimiento.Builder()
	    		.setdescripcionMovimiento("")
	    		.setMonto(BigDecimal.ZERO)
	    		.setFecha(LocalDate.now())
	    		.setClaseMovimiento(ClaseMovimiento.Otro)
	    		.setEstadoRegistro(EstadoRegistro.ACTIVO)
	    		.setTipoMovimiento(TipoMovimiento.Ingreso)
	    		.build();
	        }
	    
	    public static Movimiento getInstanciaPagoAlquiler(Cobro c) {
	    	ContratoAlquiler c1=(ContratoAlquiler) c.getContrato();
	    	Movimiento ret= new Movimiento.Builder()
	    		.setdescripcionMovimiento(c.getContrato().getInmueble().toString())
	    		.setMonto(c.getMontoRecibido())
	    		.setFecha(LocalDate.now())
	    		.setClaseMovimiento(ClaseMovimiento.Alquiler)
	    		.setEstadoRegistro(EstadoRegistro.ACTIVO)
	    		.setTipoMovimiento(TipoMovimiento.Ingreso)
	    		.setContratoAlquiler(c1)
	    		.build();
	    	return ret;
	        }
	    
	    public static Movimiento getInstanciaGananciaInmobiliaria(Cobro c) {
	    	ContratoAlquiler c1=(ContratoAlquiler) c.getContrato();
	    	Movimiento ret= new Movimiento.Builder()
	    			.setdescripcionMovimiento(c.getContrato().getInmueble().toString())
		    		.setMonto(c.getComision())
		    		.setFecha(LocalDate.now())
		    		.setClaseMovimiento(ClaseMovimiento.Comisión)
		    		.setEstadoRegistro(EstadoRegistro.ACTIVO)
		    		.setTipoMovimiento(TipoMovimiento.Ingreso)
		    		.setContratoAlquiler(c1)
	    		.build();
	    	return ret;
	        }
	    
	    public static Movimiento getInstanciaPagoAPropietario(Cobro c) {
	    	ContratoAlquiler c1=(ContratoAlquiler) c.getContrato();
	    	Movimiento ret= new Movimiento.Builder()
	    			.setdescripcionMovimiento(c.getContrato().getInmueble().toString())
		    		.setMonto(c.getMontoPropietario())
		    		.setFecha(LocalDate.now())
		    		.setClaseMovimiento(ClaseMovimiento.PagoAPropietario)
		    		.setEstadoRegistro(EstadoRegistro.ACTIVO)
		    		.setTipoMovimiento(TipoMovimiento.Egreso)
		    		.setContratoAlquiler(c1)
	    		.build();
	    	return ret;
	        }
	    
	    public static Movimiento getInstanciaPagoVenta(ContratoVenta contratoVenta) {
	    	return new Movimiento.Builder()
	    			.setdescripcionMovimiento(contratoVenta.getInmueble().toString())
		    		.setMonto(contratoVenta.getPrecioVenta())
		    		.setFecha(LocalDate.now())
		    		.setClaseMovimiento(ClaseMovimiento.Venta)
		    		.setEstadoRegistro(EstadoRegistro.ACTIVO)
		    		.setTipoMovimiento(TipoMovimiento.Ingreso)
		    		.setContratoVenta(contratoVenta)
	    		.build();
	        }


}
