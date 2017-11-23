package com.TpFinal.UnitTests;

import static org.junit.Assert.*;

import com.TpFinal.utils.Cipher;
import com.TpFinal.utils.XZCompressor;
import org.apache.commons.io.FileExistsException;
import org.junit.Ignore;
import org.junit.Test;

import com.TpFinal.properties.Parametros;
import org.tukaani.xz.LZMA2Options;
import org.tukaani.xz.XZInputStream;
import org.tukaani.xz.XZOutputStream;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

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
	public void comprimiryDescomprimir() throws Exception {
		XZCompressor.comprimir("inmobi_db.mv.db","Files"+ File.separator);
		//DESCOMPRIMIR
		XZCompressor.descomprimir("inmobi_db.mv.db","Files"+ File.separator);

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
