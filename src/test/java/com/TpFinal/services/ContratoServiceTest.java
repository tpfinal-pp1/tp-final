package com.TpFinal.services;

import static org.junit.Assert.*;

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
import com.TpFinal.data.dao.DAOContratoImpl;
import com.TpFinal.data.dao.DAOContratoVentaImpl;
import com.TpFinal.data.dao.interfaces.DAOContrato;
import com.TpFinal.data.dao.interfaces.DAOContratoAlquiler;
import com.TpFinal.data.dao.interfaces.DAOContratoVenta;
import com.TpFinal.data.dto.EstadoRegistro;
import com.TpFinal.data.dto.contrato.Contrato;
import com.TpFinal.data.dto.contrato.ContratoAlquiler;
import com.TpFinal.data.dto.contrato.ContratoVenta;

public class ContratoServiceTest {
	
	private ContratoService service;
	private DAOContratoVenta daoVenta;
	private DAOContratoAlquiler daoAlquiler;
	List<Contrato>contratos=new ArrayList<>();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ConexionHibernate.setTipoConexion(TipoConexion.H2Test);
	}

	@Before
	public void setUp() throws Exception {
		service= new ContratoService();
		daoVenta=new DAOContratoVentaImpl();
		daoAlquiler=new DAOContratoAlquilerImpl();
		daoVenta.readAll().forEach(c -> daoVenta.delete(c));
		daoAlquiler.readAll().forEach(c -> daoAlquiler.delete(c));
		contratos.clear();
	}

	@After
	public void tearDown() throws Exception {
		daoVenta.readAll().forEach(c -> daoVenta.delete(c));
		daoAlquiler.readAll().forEach(c -> daoAlquiler.delete(c));
	}

	@Test
	public void save() {
		service.saveOrUpdate(instanciaAlquiler("1"),null);
		service.saveOrUpdate(instanciaAlquiler("2"),null);
		service.saveOrUpdate(instanciaAlquiler("3"),null);
		service.saveOrUpdate(instanciaVenta("1"),null);
		service.saveOrUpdate(instanciaVenta("2"),null);
		service.saveOrUpdate(instanciaVenta("3"),null);
		
		assertEquals(6, service.readAll().size());
	}
	
	@Test
	public void logicalDelete() {
		service.saveOrUpdate(instanciaAlquiler("1"),null);
		service.saveOrUpdate(instanciaAlquiler("2"),null);
		service.saveOrUpdate(instanciaAlquiler("3"),null);
		service.saveOrUpdate(instanciaVenta("1"),null);
		service.saveOrUpdate(instanciaVenta("2"),null);
		service.saveOrUpdate(instanciaVenta("3"),null);
		
		service.delete(service.readAll().get(0));
		service.delete(service.readAll().get(0));
		
		assertEquals(4, service.readAll().size());
	}
	
	@Test 
	public void update() {
		service.saveOrUpdate(instanciaAlquiler("1"),null);
		service.saveOrUpdate(instanciaAlquiler("2"),null);
		service.saveOrUpdate(instanciaAlquiler("3"),null);
		service.saveOrUpdate(instanciaVenta("1"),null);
		service.saveOrUpdate(instanciaVenta("2"),null);
		service.saveOrUpdate(instanciaVenta("3"),null);
		
		service.readAll().forEach(a -> {
			if(a.getClass().equals(ContratoAlquiler.class)) {
				ContratoAlquiler ca = (ContratoAlquiler)a;
				ca.setValorInicial(new BigDecimal("100.00"));
				service.saveOrUpdate(ca, null);
			}else {
				ContratoVenta cv = (ContratoVenta)a;
				cv.setPrecioVenta(new BigDecimal("100.00"));
				service.saveOrUpdate(cv, null);
			}
				
		});
		
		service.readAll().forEach(a -> {
			if(a.getClass().equals(ContratoAlquiler.class)) {
				ContratoAlquiler ca = (ContratoAlquiler)a;
				assertEquals(new BigDecimal("100.00"), ca.getValorInicial());
			}else {
				ContratoVenta ca = (ContratoVenta)a;
				assertEquals(new BigDecimal("100.00"), ca.getPrecioVenta());
			}
			
			
		});
		
		
		assertEquals(6, service.readAll().size());
	
	}
	
	private ContratoVenta instanciaVenta(String numero) {
		return new ContratoVenta.Builder()
				.setFechaCelebracion(LocalDate.of(2017, 05, 12))
				.setPrecioVenta(new BigDecimal(numero))
				.build();
	}
	
    private ContratoAlquiler instanciaAlquiler(String numero) {
        return new ContratoAlquiler.Builder()
                .setFechaCelebracion(LocalDate.of(2017, 05, 12))
                .setValorIncial(new BigDecimal(numero))
                .setDiaDePago(new Integer(numero))
                .setInteresPunitorio(new Double(numero))
                .setIntervaloDuracion(new Integer(numero))
                .setInquilinoContrato(null)
                .setFechaDePago(LocalDate.of(2017, 05, 12))
                .build();

    }

}
