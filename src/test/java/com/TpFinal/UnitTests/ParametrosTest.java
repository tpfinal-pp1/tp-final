package com.TpFinal.UnitTests;

import static org.junit.Assert.*;

import com.TpFinal.services.Cipher;
import org.apache.commons.io.FileExistsException;
import org.junit.Test;

import com.TpFinal.properties.Parametros;

public class ParametrosTest {

	@Test(expected=IllegalArgumentException.class)
	public void illegar() throws IllegalArgumentException, FileExistsException {
		Parametros.getProperty("sarasa");
	}

	@Test
	public void cifrar(){
		String original="Hola todo bien?";
		String encripted=Cipher.encrypt(original);
		String decripted=Cipher.decrypt(encripted);
		assertEquals(original,decripted);
	}
	@Test
	public void traeDefaultsBien() throws IllegalArgumentException, FileExistsException {
		assertNotEquals(null, Parametros.getProperty("emailKey"));
	}
	@Test
	public void guardaBien(){
		Parametros.setProperty("framework","vaadin");
		try {
			assertEquals("vaadin",Parametros.getProperty("framework"));
		} catch (FileExistsException e) {
			e.printStackTrace();
		}


	}

}
