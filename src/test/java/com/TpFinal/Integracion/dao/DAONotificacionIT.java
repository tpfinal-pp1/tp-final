package com.TpFinal.Integracion.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.TpFinal.data.conexion.ConexionHibernate;
import com.TpFinal.data.conexion.TipoConexion;
import com.TpFinal.data.dao.DAONotificacionImpl;
import com.TpFinal.dto.cita.Cita;
import com.TpFinal.dto.cita.TipoCita;
import com.TpFinal.dto.notificacion.Notificacion;

public class DAONotificacionIT {

	DAONotificacionImpl dao;
    List<Notificacion>Notificacions= new ArrayList<>();

    @BeforeClass
    public static void setUpBeforeClass() throws Exception{
        ConexionHibernate.setTipoConexion(TipoConexion.H2Test);
    }

    @Before
    public void setUp() throws Exception {
        dao= new DAONotificacionImpl();
        dao.readAll().forEach(dao::delete);
        Notificacions.clear();
    }

    @After
    public void tearDown() throws Exception {
        Notificacions=dao.readAll();
        Notificacions.forEach(dao::delete);
    }
    
    @Test
    public void guardar() {
    	for(int i=0; i <3; i++) {
    		dao.saveOrUpdate(new Notificacion(instancia(String.valueOf(i))));
    	}
    	
    	assertEquals(3,dao.readAll().size());
    	dao.readAll().forEach(n -> {
    		System.out.println(n.getTitulo());
    		System.out.println(n.getMensaje());
    	});
    }
    
    @Test
    public void logicalDelete() {
    	for(int i=0; i <3; i++) {
    		dao.saveOrUpdate(new Notificacion(instancia(String.valueOf(i))));
    	}
    	dao.logicalDelete(dao.readAllActives().get(0));
    	dao.logicalDelete(dao.readAllActives().get(0));
    	
    	assertEquals(1, dao.readAllActives().size());
    }
    
    @Test
    public void update() {
    	for(int i=0; i <3; i++) {
    		dao.saveOrUpdate(new Notificacion(instancia(String.valueOf(i))));
    	}
    	Boolean ningunaVista=true;
    	dao.readAll().forEach(n -> {
    		assertFalse(n.getVisto());
    	});
    	
    	Notificacion n =dao.readAll().get(0);
    	n.setVisto(true);
    	dao.saveOrUpdate(n);
    	
    	Boolean unaVista=false;
    	Boolean todasVistas=true;
    	
    	dao.readAll().forEach(not -> {
    		unaVista.valueOf(unaVista||not.getVisto());
    		todasVistas.valueOf(todasVistas&&not.getVisto());
    	});
    }

    public Cita instancia(String numero) {
    	return new Cita.Builder()
    		.setCitado("Citado " + numero)
    		.setDireccionLugar("DireccionLugar " + numero)
    		.setFechahora(LocalDateTime.MIN)
    		.setObservaciones("Observaciones " + numero)
    		.setTipoDeCita(TipoCita.Otros)
    		.build();
        }
}
