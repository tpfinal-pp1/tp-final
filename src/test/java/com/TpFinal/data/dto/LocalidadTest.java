package com.TpFinal.data.dto;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LocalidadTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		LocalidadJson gson = new LocalidadJson();
		List<Localidad>localidades=gson.leer("src\\main\\webapp\\Localidades.json");
		System.out.println(localidades.size());
		localidades.forEach( l -> System.out.println(l.getNombre()+" "+l.getProvincia()));
	}

}
