package com.TpFinal.Integracion.services;

import static org.junit.Assert.*;

import org.apache.commons.io.FileExistsException;
import org.junit.Ignore;
import org.junit.Test;

import com.TpFinal.properties.Parametros;

public class ParametrosTest {

	/*@Test(expected=IllegalArgumentException.class)*/
	@Ignore
	public void illegar() throws IllegalArgumentException, FileExistsException {
		Parametros.getProperty("sarasa");
	}

	@Ignore
	public void traeBien() throws IllegalArgumentException, FileExistsException {
		assertNotEquals(null, Parametros.getProperty("sendgrid.api.key"));
	}

}
