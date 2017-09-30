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

import javax.validation.constraints.AssertFalse;

public class DAOContratoVentaTest {
	DAOContratoVenta dao;
	List<ContratoVenta>contratos=new ArrayList<>();

	@Before
	public void setUp() throws Exception {

		File dir=new File("Files");
		deleteDirectory(dir);
		dir.mkdir();
		dao=new DAOContratoVentaImpl();
		contratos.clear();
	}


	@After
	public void tearDown() throws Exception {
		contratos=dao.readAll();
		contratos.forEach(dao::delete);
		deleteDirectory(new File("Files"));
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
		String path="Files"+File.separator+"demo1.pdf";
		File f=new File(path);
		f.createNewFile();  //lo creo
		persistirenDB(path); //Lo guardo en db
		f.delete();          //Lo borro del disco
		assertEquals(1, dao.readAll().size());
		ContratoVenta contrato = dao.readAll().get(0); //Lo traigo de DB
		guardar(path+".test", toBytes(contrato));  //Lo escribo en disco de nuevo
		path="Files"+File.separator+"demo1.pdf"+".test";
		f=new File(path);
		assertTrue(f.exists());

	}
	
	public void persistirenDB(String path) throws FileNotFoundException {
		File pdf = new File(path);
		FileInputStream pdf2= new FileInputStream(pdf);
		assertTrue(pdf.exists());
		Blob archivo= Hibernate.getLobCreator(ConexionHibernate.openSession()).createBlob(pdf2, pdf.length());
		dao.save(instancia("1", archivo));
		try {
			pdf2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	
	public byte[] toBytes(ContratoVenta c) throws SQLException, IOException {
		Blob blob = c.getDocumento();
		return blob.getBytes(1, (int) blob.length());

	}
	
	public void guardar(String filePath, byte[] fileBytes) throws IOException {
		 FileOutputStream outputStream = new FileOutputStream(filePath);
	        outputStream.write(fileBytes);
	        outputStream.close();
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
	
	public ContratoVenta instancia(String numero, Blob doc) {
		return new ContratoVenta.Builder()
				.setFechaCelebracion(LocalDate.of(2017, 05, 12))
				.setDocumento(doc)
				.setInmuebleVenta(null)
				.setPrecioVenta(new BigDecimal(numero))
				.build();
	}

}
