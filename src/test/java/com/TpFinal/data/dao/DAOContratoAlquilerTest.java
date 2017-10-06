package com.TpFinal.data.dao;

import com.TpFinal.data.conexion.ConexionHibernate;
import com.TpFinal.data.conexion.TipoConexion;
import com.TpFinal.data.dao.interfaces.DAOContratoAlquiler;
import com.TpFinal.data.dto.contrato.ContratoAlquiler;
import com.TpFinal.data.dto.inmueble.TipoMoneda;
import com.TpFinal.data.dto.persona.Inquilino;
import com.TpFinal.data.dto.persona.Persona;
import com.TpFinal.data.dto.publicacion.PublicacionAlquiler;
import com.TpFinal.data.dto.publicacion.PublicacionVenta;

import org.apache.commons.io.FileUtils;
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

/**
 * Created by Max on 9/30/2017.
 */
public class DAOContratoAlquilerTest {

    DAOContratoAlquiler dao;
    List<ContratoAlquiler> contratos=new ArrayList<>();

    @BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ConexionHibernate.setTipoConexion(TipoConexion.H2Test);
	}
    
    @Before
    public void setUp() throws Exception {

        File dir=new File("Files");
        deleteDirectory(dir);
        dir.mkdir();
        dao=new DAOContratoAlquilerImpl();
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
        dao.save(instancia("1"));
        dao.save(instancia("2"));
        dao.save(instancia("3"));

        assertEquals(3, dao.readAll().size());
        
        assertEquals(dao.readAll().get(0).getPublicacionAlquiler().getFechaPublicacion(), instanciaOA().getFechaPublicacion());
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
            contrato.setIntervaloDuracion(3);
            dao.update(contrato);
        });

        dao.readAll().forEach(contrato -> {
            assertEquals(new Integer (3), contrato.getIntervaloActualizacionn());
        });
    }

    @Test
    public void altaConDoc() throws SQLException, IOException {
        String pathOriginal = "Files"+File.separator+"demo1.pdf";
        File archivoOriginal = new File(pathOriginal);
        archivoOriginal.createNewFile();  //lo creo
       	dao.saveOrUpdateContrato(instancia("1"), archivoOriginal); //Lo guardo en db
        assertEquals(1, dao.readAll().size());
        ContratoAlquiler contratoPersistido = dao.readAll().get(0); //Lo traigo de DB
        String pathPersistido="Files"+File.separator+"demo2.pdf";
		guardar(pathPersistido, blobToBytes(contratoPersistido.getDocumento()));  //Lo escribo en disco de nuevo
		File archivoPersistido=new File(pathPersistido);
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

    public byte[] blobToBytes(Blob c) throws SQLException, IOException {
        Blob blob =c;
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
                 .setPublicacionAlquiler(instanciaOA())
                .build();

    }
    
    private Inquilino instanciaInq() {
    	Inquilino i = new Inquilino();
    	return i;
    }
    
    
    private PublicacionAlquiler instanciaOA() {
    	return new PublicacionAlquiler.Builder().setFechaPublicacion(LocalDate.of(2017, 10, 1))
    			.setMoneda(TipoMoneda.Pesos).setInmueble(null).build();
    }

}
