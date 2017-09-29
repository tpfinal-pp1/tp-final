package com.TpFinal.data.dao;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.sql.Blob;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.TpFinal.data.dao.interfaces.DAOContratoVenta;
import com.TpFinal.data.dto.contrato.ContratoVentaDTO;

public class DAOContratoVentaTest {
	DAOContratoVenta dao;
	List<ContratoVentaDTO>contratos=new ArrayList<>();

	@Before
	public void setUp() throws Exception {
		dao=new DAOContratoVentaImpl();
		contratos.clear();
	}

	@After
	public void tearDown() throws Exception {
		contratos=dao.readAll();
		contratos.forEach(dao::delete);
	}

	@Test
	public void agregarSinDocs() {
		dao.save(instancia("1.00", null));
		dao.save(instancia("2.00", null));
		dao.save(instancia("3.00", null));
		
		assertEquals(3, dao.readAll().size());
	}
	
	@Test
	public void delete() {
		dao.save(instancia("1.00", null));
		dao.save(instancia("2.00", null));
		dao.save(instancia("3.00", null));
		
		dao.delete(dao.readAll().get(0));
		assertEquals(dao.readAll().size(), 2);
	}
	
	@Test
	public void update() {
		dao.save(instancia("1.00", null));
		dao.save(instancia("2.00", null));
		dao.save(instancia("3.00", null));
		
		dao.readAll().forEach(contrato -> {
			contrato.setPrecioVenta(new BigDecimal("10.00"));
			dao.update(contrato);
		});
		
		dao.readAll().forEach(contrato -> {
			assertEquals(new BigDecimal("10.00"), contrato.getPrecioVenta());
		});
		
	}
	
	
	
	
	public ContratoVentaDTO instancia(String numero, Blob doc) {
		return new ContratoVentaDTO.Builder()
				.setFechaCelebracion(LocalDate.of(2017, 05, 12))
				.setDocumento(doc)
				.setInmuebleVenta(null)
				.setPrecioVenta(new BigDecimal(numero))
				.build();
	}

}
