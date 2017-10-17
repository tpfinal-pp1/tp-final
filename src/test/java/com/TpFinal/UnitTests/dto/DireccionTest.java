package com.TpFinal.UnitTests.dto;

import static org.junit.Assert.*;

import org.junit.Test;

import com.TpFinal.UnitTests.dto.inmueble.Direccion;

public class DireccionTest {

	@Test
	public void modificandoAtributos() {
		Direccion dir = instancia("Padre Stoppler", "Pablo Nogues", 7777, "Buenos Aires", "Argentina");
		
		assertEquals("Padre Stoppler", dir.getCalle());
		dir.setCalle("Padre Francisco");		
		assertEquals("Padre Francisco",dir.getCalle());
		assertNotEquals("Padre Stoppler", dir.getCalle());
					
	}
	
	@Test
	public void modificandoNro() {
		Direccion dir = instancia("Padre Stoppler", "Pablo Nogues", 7777, "Buenos Aires", "Argentina");
		assertEquals("7777",dir.getNro().toString());
		
		dir.setNro(9999);
		assertEquals("9999",dir.getNro().toString());
		assertNotEquals("7777", dir.getNro().toString());
		
	}
	
	@Test
	public void modificandoLocalidad() {
		Direccion dir = instancia("Padre Stoppler", "Pablo Nogues", 7777, "Buenos Aires", "Argentina");
		assertEquals("Pablo Nogues",dir.getLocalidad());
		
		dir.setLocalidad("Muniz");
		assertEquals("Muniz", dir.getLocalidad());
		assertNotEquals("Pablo Nogues", dir.getLocalidad());
		
	}
	
	@Test
	public void modificandoProvincia() {
		Direccion dir = instancia("Padre Stoppler", "Pablo Nogues", 7777, "Buenos Aires", "Argentina");
		assertEquals("Buenos Aires",dir.getProvincia());
		
		dir.setProvincia("San Juan");
		assertEquals("San Juan",dir.getProvincia());
		assertNotEquals("Buenos Aires", dir.getProvincia());
	}
	
	@Test
	public void test() {
		Direccion dir = instancia("Padre Stoppler", "Pablo Nogues", 7777, "Buenos Aires", "Argentina");
		assertEquals("1233",dir.getCodPostal());
		assertEquals("Argentina",dir.getPais());
	}
	
	public static Direccion instancia(String calle, String localidad, Integer nro, String provincia, String pais) {
		return new Direccion.Builder()
				.setCalle(calle)
				.setLocalidad(localidad)
				.setNro(nro)
				.setProvincia(provincia)
				.setPais(pais)
				.setCodPostal("1233")
				.build();
				
		
	}

}
