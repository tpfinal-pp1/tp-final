package com.TpFinal.services;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.TpFinal.data.dto.contrato.ContratoVenta;


public class ContratoVentaServiceTest {
	ContratoVentaService service;
	List<ContratoVenta>contratos=new ArrayList<>();

	@Before
	public void setUp() throws Exception {
		File dir=new File("Files");
		deleteDirectory(dir);
		dir.mkdir();
		service = new ContratoVentaService();
		contratos.clear();
	}

	@After
	public void tearDown() throws Exception {
		contratos=service.readAll();
		contratos.forEach(c -> service.delete(c));
		File f = new File("Files");
		deleteDirectory(f);
	}

	static public boolean deleteDirectory(File path) {
		if (path.exists()) {
			File[] files = path.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					deleteDirectory(files[i]);
				} else {
					files[i].delete();
				}
			}
		}
		return (path.delete());
	}
	
	@Test
	public void testSaveEnService() throws IOException {
		File pdf = new File("Files"+File.separator+"demo1.pdf");
		pdf.createNewFile();
		assertTrue(pdf.exists());
		service.save(instancia("1"), pdf);
		ContratoVenta c = service.readAll().get(0);
		ContratoUtil cu = new ContratoUtil();
		cu.downloadFile(c, "Files"+File.separator+"demo1.pdf");
		pdf=new File("Files"+File.separator+"demo1.pdf");
		assertTrue(pdf.exists());
		pdf.delete();
	}
	
	@Test
	public void testSaveContratoEnDao() throws IOException {
		File pdf = new File("Files"+File.separator+"demo1.pdf");
		pdf.createNewFile();
		assertTrue(pdf.exists());
		service.saveContrato(instancia("1"), pdf);
		ContratoVenta c = service.readAll().get(0);
		ContratoUtil cu = new ContratoUtil();
		cu.downloadFile(c, "Files"+File.separator+"demo1.pdf");
		pdf=new File("Files"+File.separator+"demo1.pdf");
		assertTrue(pdf.exists());
		pdf.delete();
	}
	
	public ContratoVenta instancia(String numero) {
		return new ContratoVenta.Builder()
				.setFechaCelebracion(LocalDate.of(2017, 05, 12))
				.setPrecioVenta(new BigDecimal(numero))
				.build();
	}

}
