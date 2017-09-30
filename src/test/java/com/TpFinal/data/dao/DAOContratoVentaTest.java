package com.TpFinal.data.dao;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.TpFinal.data.conexion.ConexionHibernate;
import com.TpFinal.data.dao.interfaces.DAOContratoVenta;
import com.TpFinal.data.dto.contrato.ContratoVenta;

public class DAOContratoVentaTest {
	DAOContratoVenta dao;
	List<ContratoVenta>contratos=new ArrayList<>();

	@Before
	public void setUp() throws Exception {
		File f = new File("src\\main\\resources\\demo1.pdf");
		if(f.exists())f.delete();
		dao=new DAOContratoVentaImpl();
		contratos.clear();
	}

	@After
	public void tearDown() throws Exception {
		contratos=dao.readAll();
		contratos.forEach(dao::delete);
		File f = new File("src\\main\\resources\\demo1.pdf");
		if(f.exists())f.delete();
	}

	@Test
	public void agregarSinDocs() {
		dao.save(instancia("1.00", null));
		dao.save(instancia("2.00", null));
		dao.save(instancia("3.00", null));
		
		assertEquals(3, dao.readAll().size());
	}
	
	@Test
	public void delete() {
		dao.save(instancia("1.00", null));
		dao.save(instancia("2.00", null));
		dao.save(instancia("3.00", null));
		
		dao.delete(dao.readAll().get(0));
		assertEquals(dao.readAll().size(), 2);
	}
	
	@Test
	public void update() {
		dao.save(instancia("1.00", null));
		dao.save(instancia("2.00", null));
		dao.save(instancia("3.00", null));
		
		dao.readAll().forEach(contrato -> {
			contrato.setPrecioVenta(new BigDecimal("10.00"));
			dao.update(contrato);
		});
		
		dao.readAll().forEach(contrato -> {
			assertEquals(new BigDecimal("10.00"), contrato.getPrecioVenta());
		});
		
	}
	
	@Test 
	public void altaConDoc() throws SQLException, IOException {
		String path="src\\main\\resources\\demo.pdf";
		guardarArchivo(path);
		assertEquals(1, dao.readAll().size());
		//ahora lo traigo
		ContratoVenta c = dao.readAll().get(0);
		leerArchivo(c, "src\\main\\resources\\demo1.pdf");
		File pdf = new File("src\\main\\resources\\demo1.pdf");
		
		assertTrue(pdf.exists());
	}
	
	public void guardarArchivo(String path) throws FileNotFoundException {
		File pdf = new File(path);
		FileInputStream pdf2= new FileInputStream(pdf);
		assertTrue(pdf.exists());
		Blob archivo= Hibernate.getLobCreator(ConexionHibernate.openSession()).createBlob(pdf2, pdf.length());
		dao.save(instancia("1", archivo));
	}
	
	
	public void leerArchivo(ContratoVenta c, String path) throws SQLException, IOException {
		Blob blob = c.getDocumento();
		 byte[] blobBytes = blob.getBytes(1, (int) blob.length());
		 guardar(path, blobBytes);
	}
	
	public void guardar(String filePath, byte[] fileBytes) throws IOException {
		 FileOutputStream outputStream = new FileOutputStream(filePath);
	        outputStream.write(fileBytes);
	        outputStream.close();
	}
	
	
	
	public ContratoVenta instancia(String numero, Blob doc) {
		return new ContratoVenta.Builder()
				.setFechaCelebracion(LocalDate.of(2017, 05, 12))
				.setDocumento(doc)
				.setInmuebleVenta(null)
				.setPrecioVenta(new BigDecimal(numero))
				.build();
	}

}
