package com.TpFinal.services;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
import com.TpFinal.data.dto.persona.RolPersona;
import com.TpFinal.data.dto.publicacion.Rol;

public class PersonaServiceNuevoTest {
	
	PersonaService service;
	List<Persona>persona= new ArrayList<>();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
		ConexionHibernate.setTipoConexion(TipoConexion.H2Test);
	 }
	
	@Before
	public void set() {
		service= new PersonaService();
		service.readAll().forEach(p -> service.seriusDelete(p));
		persona.clear();
	}
	
	@After
	public void tearDown() {
		service.readAll().forEach(p -> service.seriusDelete(p));
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
    	assertEquals(3, service.giveMeYourRoles(service.readAll().get(0)).size());
    	assertEquals(Rol.Inquilino, service.giveMeYourRoles(service.readAll().get(0)).get(0));
	}
	
	@Test
	public void editar() {
		Persona p = instancia("1");
    	p.agregarRol(instanciaInquilino(Calificacion.A));
    	p.agregarRol(instanciaInquilino(Calificacion.B));
    	p.agregarRol(instanciaInquilino(Calificacion.C));
    	
    	
    	service.save(p);
    	
    	p=service.readAllActives().get(0);
    	p.getRoles().forEach(rol -> {
    		if(rol.getClass().equals(Inquilino.class)){
    			Inquilino i =(Inquilino) rol;
    			if(i.getCalificacion().equals(Calificacion.C))
    				i.setCalificacion(Calificacion.D);
    		}
    	});
    	service.update(p);
    	
    	Set<RolPersona>roles=service.readAllActives().get(0).getRoles();
    	boolean estaD=false;
    	for(RolPersona r:roles) {
    		Inquilino i = (Inquilino)r;
    		estaD=estaD||i.getCalificacion().equals(Calificacion.D);
    	}
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
