package com.TpFinal.Integracion.dao;

import com.TpFinal.data.conexion.ConexionHibernate;
import com.TpFinal.data.conexion.TipoConexion;
import com.TpFinal.data.dao.DAOContratoAlquilerImpl;
import com.TpFinal.data.dao.DAOInmuebleImpl;
import com.TpFinal.data.dao.DAOPersonaImpl;
import com.TpFinal.data.dao.DAOPublicacionImpl;
import com.TpFinal.data.dao.interfaces.DAOContratoAlquiler;
import com.TpFinal.UnitTests.dto.contrato.ContratoAlquiler;
import com.TpFinal.UnitTests.dto.inmueble.ClaseInmueble;
import com.TpFinal.UnitTests.dto.inmueble.Coordenada;
import com.TpFinal.UnitTests.dto.inmueble.Direccion;
import com.TpFinal.UnitTests.dto.inmueble.EstadoInmueble;
import com.TpFinal.UnitTests.dto.inmueble.Inmueble;
import com.TpFinal.UnitTests.dto.inmueble.TipoInmueble;
import com.TpFinal.UnitTests.dto.persona.Inquilino;
import com.TpFinal.UnitTests.dto.persona.Persona;
import org.apache.commons.io.FileUtils;
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

/**
 * Created by Max on 9/30/2017.
 */
public class DAOContratoAlquilerIT {

    DAOContratoAlquiler dao;
    DAOPublicacionImpl daoPublicaciones;
    DAOInmuebleImpl daoInmuebles;
    DAOPersonaImpl daoPersonas;
    List<ContratoAlquiler> contratos = new ArrayList<>();

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
	ConexionHibernate.setTipoConexion(TipoConexion.H2Test);
    }

    @Before
    public void setUp() throws Exception {

	File dir = new File("Files");
	deleteDirectory(dir);
	dir.mkdir();
	dao = new DAOContratoAlquilerImpl();
	daoPublicaciones = new DAOPublicacionImpl();
	daoInmuebles = new DAOInmuebleImpl();
	daoPersonas = new DAOPersonaImpl();
	contratos.clear();
    }

    @After
    public void tearDown() throws Exception {
	contratos = dao.readAll();
	desvincularInmuebleYContrato();
	desvincularPersonasYContrato();

	contratos.forEach(dao::delete);

	deleteDirectory(new File("Files"));
    }

    private void desvincularInmuebleYContrato() {
	daoInmuebles.readAll().forEach(inmueble -> {
	    inmueble.setContratos(null);
	    daoInmuebles.saveOrUpdate(inmueble);
	});
	dao.readAll().forEach(contrato -> {
	    contrato.setInmueble(null);
	    dao.saveOrUpdate(contrato);
	});

    }

    private void desvincularPersonasYContrato() {
	daoPersonas.readAll().forEach(persona -> {
	    persona.setRoles(null);
	    daoPersonas.saveOrUpdate(persona);
	});
	dao.readAll().forEach(contrato -> {
	    contrato.setInquilinoContrato(null);
	    dao.saveOrUpdate(contrato);
	});

    }

    @Test
    public void testModificar() {
	List<ContratoAlquiler> contratosEnMem = new ArrayList<>();
	contratosEnMem.add(instancia("1"));
	contratosEnMem.add(instancia("2"));
	contratosEnMem.add(instancia("3"));
	contratosEnMem.forEach(dao::saveOrUpdate);
	
	Persona p = new Persona.Builder()
		.setNombre("Pepe")
		.setApellido("Perez")
		.setDNI("123456")
		.setMail("q@q.com")
		.setTelefono("123")		
		.build();	
	
	 ContratoAlquiler c = contratosEnMem.get(0);
	 c.setInquilinoContrato(new Inquilino.Builder().setPersona(p).build());
	
	dao.saveOrUpdate(c);
	
    }
    
    @Test
    public void agregarSinDocs() {
	dao.save(instancia("1"));
	dao.save(instancia("2"));
	dao.save(instancia("3"));

	assertEquals(3, dao.readAll().size());

    }

    @Test
    public void delete() {
	dao.save(instancia("1"));
	dao.save(instancia("2"));
	dao.save(instancia("3"));

	dao.delete(dao.readAll().get(0));
	assertEquals(dao.readAll().size(), 2);
    }

    @Test
    public void update() {
	dao.save(instancia("1"));
	dao.save(instancia("2"));
	dao.save(instancia("3"));

	dao.readAll().forEach(contrato -> {
	    contrato.setValorInicial(new BigDecimal("10.00"));
	    dao.update(contrato);
	});

	dao.readAll().forEach(contrato -> {
	    assertEquals(new BigDecimal("10.00"), contrato.getValorInicial());
	});

	dao.readAll().forEach(contrato -> {
	    contrato.setDiaDePago(3);
	    dao.update(contrato);
	});

	dao.readAll().forEach(contrato -> {
	    assertEquals(new Integer(3), contrato.getDiaDePago());
	});

	dao.readAll().forEach(contrato -> {
	    contrato.setIntervaloActualizacion(3);
	    dao.update(contrato);
	});

	dao.readAll().forEach(contrato -> {
	    assertEquals(new Integer(3), contrato.getIntervaloActualizacion());
	});
    }

    @Test
    public void altaConDoc() throws SQLException, IOException {
	String pathOriginal = "Files" + File.separator + "demo1.pdf";
	File archivoOriginal = new File(pathOriginal);
	archivoOriginal.createNewFile(); // lo creo
	dao.saveOrUpdateContrato(instancia("1"), archivoOriginal); // Lo guardo en db
	assertEquals(1, dao.readAll().size());
	ContratoAlquiler contratoPersistido = dao.readAll().get(0); // Lo traigo de DB
	String pathPersistido = "Files" + File.separator + "demo2.pdf";
	guardar(pathPersistido, blobToBytes(contratoPersistido.getDocumento())); // Lo escribo en disco de nuevo
	File archivoPersistido = new File(pathPersistido);
	assertTrue(archivoPersistido.exists());
	assertTrue(FileUtils.contentEquals(archivoOriginal, archivoPersistido));
    }

    @Test
    public void contratoConInquilino() {
	ContratoAlquiler c = instancia("1");
	Inquilino i = instanciaInq();
	c.setInquilinoContrato(i);

	i.getContratos().add(c);
	dao.save(c);
	assertEquals(i, dao.readAll().get(0).getInquilinoContrato());

    }

    @Test
    public void testRelacionInmueble() {

	DAOInmuebleImpl daoI = new DAOInmuebleImpl();
	Inmueble i = unInmuebleNoPublicado();
	ContratoAlquiler c = instancia("1");

	daoI.create(i);
	c.setInmueble(i);
	i.addContrato(c);
	dao.create(c);
	daoI.saveOrUpdate(i);

	assertEquals(1, daoI.readAll().get(0).getContratos().size());
	assertEquals(i, dao.readAll().get(0).getInmueble());

    }

    public byte[] blobToBytes(Blob c) throws SQLException, IOException {
	Blob blob = c;
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

    private ContratoAlquiler instancia(String numero) {
	return new ContratoAlquiler.Builder()
		.setFechaCelebracion(LocalDate.of(2017, 05, 12))
		.setValorIncial(new BigDecimal(numero))
		.setDiaDePago(new Integer(numero))
		.setInteresPunitorio(new Double(numero))
		.setIntervaloActualizacion(new Integer(numero))
		.setInquilinoContrato(null)

		.build();

    }

    private Inquilino instanciaInq() {
	Inquilino i = new Inquilino();
	return i;
    }

    private Inmueble unInmuebleNoPublicado() {
	return new Inmueble.Builder()
		.setaEstrenar(true)
		.setCantidadAmbientes(2)
		.setCantidadCocheras(3)
		.setCantidadDormitorios(1)
		.setClaseInmueble(ClaseInmueble.Casa)
		.setConAireAcondicionado(true)
		.setConJardin(true).setConParilla(true).setConPileta(true)
		.setDireccion(
			new Direccion.Builder()
				.setCalle("Una calle")
				.setCodPostal("asd123")
				.setCoordenada(new Coordenada())
				.setLocalidad("una Localidad")
				.setNro(123)
				.setPais("Argentina")
				.setProvincia("Buenos Aires")
				.build())
		.setEstadoInmueble(EstadoInmueble.NoPublicado)
		.setSuperficieCubierta(200)
		.setSuperficieTotal(400)
		.setTipoInmueble(TipoInmueble.Vivienda)
		.build();
    }

}
