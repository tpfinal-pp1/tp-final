package com.TpFinal.data.dao;

import com.TpFinal.data.conexion.ConexionHibernate;
import com.TpFinal.data.conexion.TipoConexion;
import com.TpFinal.data.dao.interfaces.DAOContratoAlquiler;
import com.TpFinal.data.dto.contrato.ContratoAlquiler;
import com.TpFinal.data.dto.inmueble.TipoMoneda;
import com.TpFinal.data.dto.operacion.OperacionAlquiler;
import com.TpFinal.data.dto.operacion.OperacionVenta;

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
		ConexionHibernate.setTipoConexion(TipoConexion.MariaDBTest);
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
        dao.save(instancia("1", null));
        dao.save(instancia("2", null));
        dao.save(instancia("3", null));

        assertEquals(3, dao.readAll().size());
        
        assertEquals(dao.readAll().get(0).getOperacionAlquiler().getFechaPublicacion(), instanciaOA().getFechaPublicacion());
    }

    @Test
    public void delete() {
        dao.save(instancia("1", null));
        dao.save(instancia("2", null));
        dao.save(instancia("3", null));

        dao.delete(dao.readAll().get(0));
        assertEquals(dao.readAll().size(), 2);
    }

    @Test
    public void update() {
        dao.save(instancia("1", null));
        dao.save(instancia("2", null));
        dao.save(instancia("3", null));

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
            assertEquals(new Integer (3), contrato.getIntervaloDuracion());
        });
    }

    @Test
    public void altaConDoc() throws SQLException, IOException {
        String path = "Files"+File.separator+"demo1.pdf";
        File f = new File(path);
        f.createNewFile();  //lo creo
        persistirenDB(path); //Lo guardo en db
        f.delete();          //Lo borro del disco
        assertFalse(f.exists());
        assertEquals(1, dao.readAll().size());
        ContratoAlquiler contrato = dao.readAll().get(0); //Lo traigo de DB
        guardar(path+".test", toBytes(contrato));  //Lo escribo en disco de nuevo
        path = "Files"+File.separator+"demo1.pdf"+".test";
        f = new File(path);
        assertTrue(f.exists());

    }

    public void persistirenDB(String path) throws FileNotFoundException {
        File pdf = new File(path);
        FileInputStream pdf2 = new FileInputStream(pdf);
        assertTrue(pdf.exists());
        Blob archivo= Hibernate.getLobCreator(ConexionHibernate.openSession()).createBlob(pdf2, pdf.length());
        dao.save(instancia("1", archivo));
        try {
            pdf2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public byte[] toBytes(ContratoAlquiler c) throws SQLException, IOException {
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

    private ContratoAlquiler instancia(String numero, Blob doc) {
        return new ContratoAlquiler.Builder()
                .setFechaCelebracion(LocalDate.of(2017, 05, 12))
                .setDocumento(doc)
                .setValorIncial(new BigDecimal(numero))
                .setDiaDePago(new Integer(numero))
                .setInteresPunitorio(new Double(numero))
                .setIntervaloDuracion(new Integer(numero))
                .setInquilinoContrato(null)
                .setFechaDePago(LocalDate.of(2017, 05, 12))
                .setOperacionAlquiler(instanciaOA())
                .build();

    }
    
    private OperacionAlquiler instanciaOA() {
    	return new OperacionAlquiler.Builder().setFechaPublicacion(LocalDate.of(2017, 10, 1))
    			.setMoneda(TipoMoneda.Pesos).setInmueble(null).build();
    }

}
