package com.TpFinal.services;

import static org.junit.Assert.*;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Blob;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.TpFinal.data.dto.contrato.ContratoVentaDTO;


public class ContratoVentaServiceTest {
	ContratoVentaService service;
	List<ContratoVentaDTO>contratos=new ArrayList<>();
	
	@Before
	public void setUp() throws Exception {
		File f = new File("src\\main\\resources\\prueba.pdf");
		if(f.exists())
			f.delete();
		else
			System.out.println("no existe");
		service = new ContratoVentaService();
		contratos.clear();
	}

	@After
	public void tearDown() throws Exception {
		contratos=service.readAll();
		contratos.forEach(c -> service.delete(c));
		File f = new File("src\\main\\resources\\prueba.pdf");
		if(f.exists())
			f.delete();
		else
			System.out.println("no existe");
	}

	@Test
	public void test() {
		File pdf = new File("src\\main\\resources\\demo.pdf");
		assertTrue(pdf.exists());
		service.save(instancia("1"), pdf);
		ContratoVentaDTO c = service.readAll().get(0);
		ContratoUtil cu = new ContratoUtil();
		cu.downloadFile(c, "src\\main\\resources\\prueba.pdf");
		pdf=new File("src\\main\\resources\\prueba.pdf");
		assertTrue(pdf.exists());
		pdf.delete();
	}
	
	public ContratoVentaDTO instancia(String numero) {
		return new ContratoVentaDTO.Builder()
				.setFechaCelebracion(LocalDate.of(2017, 05, 12))
				.setInmuebleVenta(null)
				.setPrecioVenta(new BigDecimal(numero))
				.build();
	}

}
