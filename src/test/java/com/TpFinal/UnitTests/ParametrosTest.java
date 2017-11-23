package com.TpFinal.UnitTests;

import static org.junit.Assert.*;

import com.TpFinal.services.Cipher;
import org.apache.commons.io.FileExistsException;
import org.junit.Ignore;
import org.junit.Test;

import com.TpFinal.properties.Parametros;
import org.tukaani.xz.LZMA2Options;
import org.tukaani.xz.UnsupportedOptionsException;
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
	@Ignore
	public void comprimiryDescomprimir() throws Exception {
		//COMPRIMIR
		FileInputStream inFile = new FileInputStream("Files"+ File.separator+"inmobi_db.mv.db");
		FileOutputStream outfile = new FileOutputStream("Files"+ File.separator+"inmobi_db.mv.db.xz");

		LZMA2Options options = new LZMA2Options();

		options.setPreset(7); // play with this number: 6 is default but 7 works better for mid sized archives ( > 8mb)

		XZOutputStream out = new XZOutputStream(outfile, options);

		byte[] buf = new byte[8192];
		int size;
		while ((size = inFile.read(buf)) != -1)
			out.write(buf, 0, size);

		out.finish();

		//DESCOMPRIMIR
		FileInputStream fin = new FileInputStream("Files"+ File.separator+"inmobi_db.mv.db.xz");
		BufferedInputStream in = new BufferedInputStream(fin);
		FileOutputStream out2 = new FileOutputStream("Files"+ File.separator+"inmobi_db(descomprimida).mv.db");
		XZInputStream xzIn = new XZInputStream(in);
		final byte[] buffer = new byte[8192];
		int n = 0;
		while (-1 != (n = xzIn.read(buffer))) {
			out2.write(buffer, 0, n);
		}
		out2.close();
		xzIn.close();


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
