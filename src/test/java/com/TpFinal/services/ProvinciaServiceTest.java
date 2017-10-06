package com.TpFinal.services;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.TpFinal.data.dto.Provincia;

public class ProvinciaServiceTest {
	ProvinciaService service=new ProvinciaService("src\\\\main\\\\webapp\\\\Localidades.json");
	List<Provincia>provincias= new ArrayList<>();
	
	@Before
	public void setUp() throws Exception {
		provincias.clear();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		provincias=service.getProvincias();
		
		assertEquals(24,provincias.size());
		provincias.forEach(p -> {
			assertTrue(p.getLocalidades().size()>1);
		});
	}

}
