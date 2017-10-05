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
import com.TpFinal.data.dto.EstadoRegistro;
import com.TpFinal.data.dto.contrato.Contrato;
import com.TpFinal.data.dto.contrato.ContratoAlquiler;
import com.TpFinal.data.dto.contrato.ContratoVenta;

public class ContratoServiceTest {
	
	private ContratoService service;
	List<Contrato>contratos=new ArrayList<>();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ConexionHibernate.setTipoConexion(TipoConexion.H2Test);
	}

	@Before
	public void setUp() throws Exception {
		service= new ContratoService();
		service.readAllVenta().forEach(c -> service.deleteSerious(c));
		service.readAllAlquiler().forEach(c -> service.deleteSerious(c));
		contratos.clear();
	}

	@After
	public void tearDown() throws Exception {
		service.readAllVenta().forEach(c -> service.deleteSerious(c));
		service.readAllAlquiler().forEach(c -> service.deleteSerious(c));
	}

	@Test
	public void save() {
		service.save(instanciaAlquiler("1"));
		service.save(instanciaAlquiler("2"));
		service.save(instanciaAlquiler("3"));
		service.save(instanciaVenta("1"));
		service.save(instanciaVenta("2"));
		service.save(instanciaVenta("3"));
		
		assertEquals(6, service.readAllContratos().size());
		assertEquals(3, service.readAllVenta().size());
		assertEquals(3, service.readAllAlquiler().size());
	}
	
	@Test
	public void logicalDelete() {
		service.save(instanciaAlquiler("1"));
		service.save(instanciaAlquiler("2"));
		service.save(instanciaAlquiler("3"));
		service.save(instanciaVenta("1"));
		service.save(instanciaVenta("2"));
		service.save(instanciaVenta("3"));
		
		service.delete(service.readAllAlquiler().get(0));
		service.delete(service.readAllVenta().get(0));
		
		assertEquals(4, service.readAllContratos().size());
		assertEquals(2, service.readAllVenta().size());
		assertEquals(2, service.readAllAlquiler().size());
	}
	
	@Test 
	public void update() {
		service.save(instanciaAlquiler("1"));
		service.save(instanciaAlquiler("2"));
		service.save(instanciaAlquiler("3"));
		service.save(instanciaVenta("1"));
		service.save(instanciaVenta("2"));
		service.save(instanciaVenta("3"));
		
		service.readAllAlquiler().forEach(a -> {
			a.setValorInicial(new BigDecimal("100.00"));
			service.update(a);
		});
		
		service.readAllVenta().forEach(v -> {
			v.setPrecioVenta(new BigDecimal("100.00"));
			service.update(v);
		});
		
		service.readAllAlquiler().forEach(a -> {
			assertEquals(new BigDecimal("100.00"), a.getValorInicial());
		});
		
		service.readAllVenta().forEach(v -> {
			assertEquals(new BigDecimal("100.00"), v.getPrecioVenta());
		});
		
		assertEquals(6, service.readAllContratos().size());
		assertEquals(3, service.readAllVenta().size());
		assertEquals(3, service.readAllAlquiler().size());
	
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
