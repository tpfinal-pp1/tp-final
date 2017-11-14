package com.TpFinal.Integracion.services;

import static org.junit.Assert.*;

import org.apache.commons.io.FileExistsException;
import org.junit.Test;

import com.TpFinal.properties.Parametros;

public class ParametrosIT {

	@Test(expected=IllegalArgumentException.class)
	public void illegar() throws IllegalArgumentException, FileExistsException {
		Parametros.getProperty("sarasa");
	}
	
	@Test 
	public void traeBien() throws IllegalArgumentException, FileExistsException {
		assertNotEquals(null, Parametros.getProperty("sendgrid.api.key"));
	}

}
