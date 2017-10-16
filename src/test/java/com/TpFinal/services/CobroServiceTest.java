package com.TpFinal.services;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.TpFinal.data.conexion.ConexionHibernate;
import com.TpFinal.data.conexion.TipoConexion;
import com.TpFinal.data.dao.DAOCobroImpl;
import com.TpFinal.data.dao.DAOContratoAlquilerImpl;
import com.TpFinal.data.dao.interfaces.DAOCobro;
import com.TpFinal.data.dao.interfaces.DAOContratoAlquiler;
import com.TpFinal.data.dto.EstadoRegistro;
import com.TpFinal.data.dto.cobro.Cobro;
import com.TpFinal.data.dto.cobro.EstadoCobro;
import com.TpFinal.data.dto.contrato.ContratoAlquiler;
import com.TpFinal.data.dto.contrato.DuracionContrato;
import com.TpFinal.data.dto.contrato.TipoInteres;

public class CobroServiceTest {

	DAOCobro daoCobro;
	DAOContratoAlquiler daoContrato;
	CobroService service;
	List<ContratoAlquiler>contratos= new ArrayList<>();
	List<Cobro>cobros= new ArrayList<>();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ConexionHibernate.setTipoConexion(TipoConexion.H2Test);
	}

	@Before
	public void setUp() throws Exception {
		daoCobro=new DAOCobroImpl();
		daoContrato= new DAOContratoAlquilerImpl();
		service= new CobroService();
		contratos.clear();
		cobros.clear();
	}

	@After
	public void tearDown() throws Exception {
		daoCobro.readAll().forEach(c -> daoCobro.delete(c));
		daoContrato.readAll().forEach(c -> daoContrato.delete(c));
	}
	
	@Test
	public void alta() {
		for(int i =0; i< 4; i++) {service.save(instanciaCobro(i));}
		cobros=service.readAll();
		assertEquals(4, cobros.size());
		assertEquals(new Integer(0), cobros.get(0).getNumeroCuota());
		assertEquals(new Integer(1), cobros.get(1).getNumeroCuota());
		assertEquals(new Integer(2), cobros.get(2).getNumeroCuota());
		assertEquals(new Integer(3), cobros.get(3).getNumeroCuota());
	}
	
	@Test
	public void update() {
		for(int i =0; i< 4; i++) {service.save(instanciaCobro(i));}
		cobros=service.readAll();
		assertEquals(4, cobros.size());
		for(int i =0; i< 4; i++) {
			if(i==0) 
				cobros.get(i).setEstadoCobro(EstadoCobro.COBRADO);
			cobros.get(i).setNumeroCuota(i+4); service.save(cobros.get(i));
		}
		cobros=service.readAll();
		assertEquals(new Integer(4), cobros.get(0).getNumeroCuota());
		assertEquals(EstadoCobro.COBRADO, cobros.get(0).getEstadoCobro());
		assertEquals(new Integer(5), cobros.get(1).getNumeroCuota());
		assertEquals(EstadoCobro.NOCOBRADO, cobros.get(1).getEstadoCobro());
		assertEquals(new Integer(6), cobros.get(2).getNumeroCuota());
		assertEquals(EstadoCobro.NOCOBRADO, cobros.get(2).getEstadoCobro());
		assertEquals(new Integer(7), cobros.get(3).getNumeroCuota());
		assertEquals(EstadoCobro.NOCOBRADO, cobros.get(3).getEstadoCobro());
	}
	
	@Test
	public void delete() {
		for(int i =0; i< 4; i++) {service.save(instanciaCobro(i));}
		cobros=service.readAll();
		assertEquals(4, cobros.size());
		service.delete(cobros.get(0));
		service.delete(cobros.get(1));
		assertEquals(2, service.readAll().size());
	}
	
	
    private ContratoAlquiler instanciaAlquilerSimple() {
        return new ContratoAlquiler.Builder()
                .setFechaCelebracion(LocalDate.of(2017, 05, 12))
                .setValorIncial(new BigDecimal("100.00"))
                .setDiaDePago(new Integer(13))
                .setInteresPunitorio(new Double(0.5))
                .setIntervaloActualizacion(new Integer(2))
                .setTipoIncrementoCuota(TipoInteres.Simple)
                .setTipoInteresPunitorio(TipoInteres.Simple)
                .setPorcentajeIncremento(new Double(0.5))
                .setInquilinoContrato(null)
                .setDuracionContrato(DuracionContrato.VeinticuatroMeses)
                .setEstadoRegistro(EstadoRegistro.ACTIVO)
                 .build();
    }
    
    private ContratoAlquiler instanciaAlquilerAcumulativo() {
        return new ContratoAlquiler.Builder()
                .setFechaCelebracion(LocalDate.of(2017, 05, 12))
                .setValorIncial(new BigDecimal("100.00"))
                .setDiaDePago(new Integer(11))
                .setInteresPunitorio(new Double(0.5))
                .setIntervaloActualizacion(new Integer(2))
                .setTipoIncrementoCuota(TipoInteres.Acumulativo)
                .setTipoInteresPunitorio(TipoInteres.Simple)
                .setPorcentajeIncremento(new Double(0.5))
                .setInquilinoContrato(null)
                .setDuracionContrato(DuracionContrato.VeinticuatroMeses)
                .setEstadoRegistro(EstadoRegistro.ACTIVO)
                 .build();
    }
    
    private Cobro instanciaCobro(Integer n) {
    	return new Cobro.Builder()
    			.setNumeroCuota(n)
    			.setFechaDeVencimiento(LocalDate.of(2017, 11, 2))
    			.setMontoOriginal(new BigDecimal("100"))
    			.build();
    }


}
