package com.TpFinal.Integracion.dao;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.TpFinal.data.conexion.ConexionHibernate;
import com.TpFinal.data.conexion.TipoConexion;
import com.TpFinal.data.dao.DAOContratoAlquilerImpl;
import com.TpFinal.data.dao.DAOContratoVentaImpl;
import com.TpFinal.data.dao.DAOMovimientoImpl;
import com.TpFinal.data.dao.interfaces.DAOContratoAlquiler;
import com.TpFinal.data.dao.interfaces.DAOContratoVenta;
import com.TpFinal.data.dao.interfaces.DAOMovimiento;
import com.TpFinal.dto.EstadoRegistro;
import com.TpFinal.dto.movimiento.ClaseMovimiento;
import com.TpFinal.dto.movimiento.Movimiento;
import com.TpFinal.dto.movimiento.TipoMovimiento;

public class DAOMovimientoImplIT {


	DAOMovimiento dao;
	DAOContratoAlquiler daoContratoAlquiler;
	DAOContratoVenta daoContratoVenta;
	
	List<Movimiento> movimientos  = new ArrayList<>();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ConexionHibernate.setTipoConexion(TipoConexion.H2Test);
	}

	@Before
	public void setUp() throws Exception {

		dao = new DAOMovimientoImpl();
		daoContratoAlquiler = new DAOContratoAlquilerImpl();
		daoContratoVenta =  new DAOContratoVentaImpl();
		
		movimientos.clear();
	}

	@After
	public void tearDown() throws Exception {
		movimientos = dao.readAll();
		desvincularMovimientoYContratoAlquiler();
		desvincularMovimientoYContratoVenta();

		movimientos.forEach(dao::delete);
		
	
	}

	private void desvincularMovimientoYContratoAlquiler() {
		daoContratoAlquiler.readAll().forEach(contrato -> {
			daoContratoAlquiler.saveOrUpdate(contrato);
		});
		dao.readAll().forEach(movimiento -> {
			dao.saveOrUpdate(movimiento);
		});

	}
	

	private void desvincularMovimientoYContratoVenta() {
		daoContratoVenta.readAll().forEach(contrato -> {
			daoContratoVenta.saveOrUpdate(contrato);
		});
		dao.readAll().forEach(movimiento -> {
			dao.saveOrUpdate(movimiento);
		});

	}
	
	@Test
	public void delete() {
		dao.save(getInstanciaOtro(1));
		dao.save(getInstanciaOtro(2));
		dao.save(getInstanciaOtro(3));

		dao.delete(dao.readAll().get(0));
		assertEquals(dao.readAll().size(), 2);
	}

	@Test
	public void update() {
		dao.save(getInstanciaOtro(1));
		dao.save(getInstanciaOtro(2));
		dao.save(getInstanciaOtro(3));
		
		dao.readAll().forEach(movimiento -> {
			movimiento.setMonto(new BigDecimal("10.00"));
			dao.update(movimiento);
		});

		dao.readAll().forEach(movimiento -> {
			assertEquals(new BigDecimal("10.00"), movimiento.getMonto());
		});

		dao.readAll().forEach(movimiento -> {
			movimiento.setDescripcionMovimiento("3");
			dao.update(movimiento);
		});

		dao.readAll().forEach(movimiento -> {
			assertEquals(new Integer(3), Integer.valueOf(movimiento.getDescripcionMovimiento()));
		});
	}

	
	 public static Movimiento getInstanciaOtro(int numero) {
	    	return new Movimiento.Builder()
	    		.setdescripcionMovimiento(String.valueOf(numero))
	    		.setMonto(BigDecimal.ZERO)
	    		.setFecha(LocalDate.now())
	    		.setClaseMovimiento(ClaseMovimiento.Otro)
	    		.setEstadoRegistro(EstadoRegistro.ACTIVO)
	    		.setTipoMovimiento(TipoMovimiento.Ingreso)
	    		.build();
	        }

}
