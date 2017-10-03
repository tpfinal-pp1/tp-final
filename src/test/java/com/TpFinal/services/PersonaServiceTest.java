package com.TpFinal.services;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.TpFinal.data.conexion.ConexionHibernate;
import com.TpFinal.data.conexion.TipoConexion;
import com.TpFinal.data.dto.EstadoRegistro;
import com.TpFinal.data.dto.persona.Calificacion;
import com.TpFinal.data.dto.persona.Inquilino;
import com.TpFinal.data.dto.persona.Persona;

public class PersonaServiceTest {
	
	PersonaService service;
	List<Persona>persona= new ArrayList<>();
	
	   @BeforeClass
	    public static void setUpBeforeClass() throws Exception{
	        ConexionHibernate.setTipoConexion(TipoConexion.H2Test);
	    }
	
	@Before
	public void set() {
		persona.clear();
		service= new PersonaService();
		service.readAll().forEach(p -> service.delete(p));
	}
	
	@After
	public void tearDown() {
		service.readAll().forEach(p -> service.delete(p));
	}
	
	@Test
	public void agregar() {
    	Persona p = instancia("1");
    	p.agregarRol(instanciaInquilino(Calificacion.A));
    	p.agregarRol(instanciaInquilino(Calificacion.B));
    	p.agregarRol(instanciaInquilino(Calificacion.C));
    	
    	service.save(p);
    	
    	assertEquals(1,service.readAllActives().size());
    	assertEquals(3, service.readAllActives().get(0).getRoles().size());
	}
	
	
	public static Persona instancia(String numero) {
        return new Persona.Builder()
                .setNombre("nombre "+numero)
                .setApellido("apellido "+numero)
                .setMail("mail "+numero)
                .setTelefono("telefono "+numero)
                .setTelefono("telefono "+numero)
                .setTelefono2("telefono2 "+numero)
                .setDNI("Dni"+numero)
                .setinfoAdicional("Info Adicional"+ numero)
                .setEstadoRegistro(EstadoRegistro.ACTIVO)
                .buid();
    }
    
    private Inquilino instanciaInquilino(String numero) {
    	return new Inquilino.Builder()
    			.setCalificacion(Calificacion.A)
    			.setEstadoRegistro(EstadoRegistro.ACTIVO)
    			.build();
    }
    
    private Inquilino instanciaInquilino(Calificacion c) {
    	return new Inquilino.Builder()
    			.setCalificacion(c)
    			.setEstadoRegistro(EstadoRegistro.ACTIVO)
    			.build();
    }


}
