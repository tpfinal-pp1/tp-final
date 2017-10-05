package com.TpFinal.data.dao;

import com.TpFinal.data.conexion.ConexionHibernate;
import com.TpFinal.data.conexion.TipoConexion;
import com.TpFinal.data.dao.interfaces.DAOContratoVenta;
import com.TpFinal.data.dto.contrato.ContratoVenta;
import com.TpFinal.data.dto.inmueble.TipoMoneda;
import com.TpFinal.data.dto.publicacion.PublicacionVenta;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.hibernate.Hibernate;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DAOContratoVentaTest {
	DAOContratoVenta dao;
	List<ContratoVenta>contratos=new ArrayList<>();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ConexionHibernate.setTipoConexion(TipoConexion.H2Test);
	}
	
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
		dao.save(instancia("1.00"));
		dao.save(instancia("2.00"));
		dao.save(instancia("3.00"));
		
		assertEquals(3, dao.readAll().size());
		assertEquals(instanciaOV().getFechaPublicacion(), dao.readAll().get(0).getPublicacionVenta().getFechaPublicacion());
	}
	
	@Test
	public void delete() {
		dao.save(instancia("1.00"));
		dao.save(instancia("2.00"));
		dao.save(instancia("3.00"));
		
		dao.delete(dao.readAll().get(0));
		assertEquals(dao.readAll().size(), 2);
	}
	
	@Test
	public void update() {
		dao.save(instancia("1.00"));
		dao.save(instancia("2.00"));
		dao.save(instancia("3.00"));
		
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
		String pathOriginal="Files"+File.separator+"demo1.pdf";
		File archivoOriginal=new File(pathOriginal);
		archivoOriginal.createNewFile();  //lo creo
		dao.saveOrUpdateContrato(instancia("1"), archivoOriginal);
		assertEquals(1, dao.readAll().size());
		ContratoVenta contratoPersistido = dao.readAll().get(0); //Lo traigo de DB
		String pathPersistido="Files"+File.separator+"demo2.pdf";
		guardar(pathPersistido, blobToBytes(contratoPersistido.getDocumento()));  //Lo escribo en disco de nuevo
		File archivoPersistido=new File(pathPersistido);
		assertTrue(archivoPersistido.exists());
		assertTrue(FileUtils.contentEquals(archivoOriginal, archivoPersistido));
	}
	

	
	private byte[] blobToBytes(Blob c) throws SQLException, IOException {
		Blob blob = c;
		return blob.getBytes(1, (int) blob.length());

	}
	
	private void guardar(String filePath, byte[] fileBytes) throws IOException {
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
	
	private ContratoVenta instancia(String numero) {
		return new ContratoVenta.Builder()
				.setFechaCelebracion(LocalDate.of(2017, 05, 12))
				.setPrecioVenta(new BigDecimal(numero))
				.setPublicacionVenta(instanciaOV())
				.build();
	}
	
	
	
	private PublicacionVenta instanciaOV() {
		return new PublicacionVenta.Builder().setFechaPublicacion(LocalDate.of(2017, 10, 1))
		.setMoneda(TipoMoneda.Pesos).setPrecio(BigDecimal.valueOf(12e3)).setInmueble(null).build();
	}

}
