package com.TpFinal.services;

import com.TpFinal.data.dto.contrato.ContratoAlquiler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by Max on 9/30/2017.
 */
public class ContratoAlquilerServiceTest {

    ContratoAlquilerService service;
    List<ContratoAlquiler> contratos=new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        File dir=new File("Files");
        deleteDirectory(dir);
        dir.mkdir();
        service = new ContratoAlquilerService();
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
        ContratoAlquiler c = service.readAll().get(0);
        ContratoUtil cu = new ContratoUtil();
        cu.downloadFile(c, "Files"+File.separator+"demo1.pdf");
        pdf = new File("Files"+File.separator+"demo1.pdf");
        assertTrue(pdf.exists());
        pdf.delete();
    }

    @Test
    public void testSaveContratoEnDao() throws IOException {
        File pdf = new File("Files"+File.separator+"demo1.pdf");
        pdf.createNewFile();
        assertTrue(pdf.exists());
        service.saveContrato(instancia("1"), pdf);
        ContratoAlquiler c = service.readAll().get(0);
        ContratoUtil cu = new ContratoUtil();
        cu.downloadFile(c, "Files"+File.separator+"demo1.pdf");
        pdf=new File("Files"+File.separator+"demo1.pdf");
        assertTrue(pdf.exists());
        pdf.delete();
    }

    private ContratoAlquiler instancia(String numero) {
        return new ContratoAlquiler.Builder()
                .setFechaCelebracion(LocalDate.of(2017, 05, 12))
                .setValorIncial(new BigDecimal(numero))
                .setDiaDePago(new Integer(numero))
                .setInteresPunitorio(new Double(numero))
                .setIntervaloDuracion(new Integer(numero))
                .setInquilinoContrato(null)
                .setFechaDePago(LocalDate.of(2017, 05, 12))
                .build();

    }
}
