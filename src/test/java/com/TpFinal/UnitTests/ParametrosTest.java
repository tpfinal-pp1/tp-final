package com.TpFinal.UnitTests;

import static org.junit.Assert.*;

import com.TpFinal.services.CredencialService;
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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Scanner;

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
	public void serial(){
		CredencialService cs=new CredencialService();
		String key="VEZFXkJDRUZaQEdXQQ==";

		assertFalse(cs.validateSerial(key));

	}

	@Test
	public void comprimiryDescomprimir() throws Exception {
		String text = "Texto De Prueba";
		Files.write(Paths.get("Files"+File.separator+"test.txt"), text.getBytes());
		String comprimido=XZCompressor.comprimir("test.txt","Files");
		String descomprimido=XZCompressor.descomprimir(comprimido,"Files");
		String content = new Scanner(new File("Files"+File.separator+descomprimido))
				.useDelimiter("\\Z").next();
		assertEquals(text,content);

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
