package com.TpFinal.services;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import com.TpFinal.data.dto.Localidad;
import com.TpFinal.data.dto.inmueble.Direccion;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.TpFinal.data.dto.Provincia;

public class ProvinciaServiceTest {
	ProvinciaService service=new ProvinciaService(ProvinciaService.modoLecturaJson.local);
	List<Provincia>provincias= new ArrayList<>();
	
	@Before
	public void setUp() throws Exception {
		provincias.clear();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void traerProvincias() {
		provincias=service.getProvincias();
		
		assertEquals(24,provincias.size());
		provincias.forEach(p -> {
			assertTrue(p.getLocalidades().size()>=1);
		});
		Provincia capitalFederal=service.getProvinciaFromString("Capital Federal");
		assertNotNull(capitalFederal);
		assertTrue(capitalFederal.getLocalidades().size()==1);
		assertTrue(capitalFederal.getLocalidades().get(0).getCodigoPostal().equals("0"));

	}

	@Test
	public void traerLocalidad(){
		Localidad SanMiguel=service.getLocalidadFromNombreAndProvincia("San Miguel","Buenos Aires");
		assertNotNull(SanMiguel);
		assertEquals("1663",SanMiguel.getCodigoPostal());

	}

}
