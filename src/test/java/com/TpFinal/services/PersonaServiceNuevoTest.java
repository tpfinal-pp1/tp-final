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
import com.TpFinal.data.dao.DAOPersonaImpl;
import com.TpFinal.data.dao.interfaces.DAOPersona;
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
		service=new PersonaService();
		DAOPersona dao = new DAOPersonaImpl();
		service.readAll().forEach(p -> dao.delete(p));
		persona.clear();
	}
	
	@After
	public void tearDown() {
		DAOPersona dao = new DAOPersonaImpl();
		service.readAll().forEach(p -> dao.delete(p));
	}
	
	@Test
	public void agregar() {
    	Persona p = instancia("1");
    	p.addRol(instanciaInquilino(Calificacion.A));
    	p.addRol(instanciaInquilino(Calificacion.B));
    	p.addRol(instanciaInquilino(Calificacion.C));
    	
    	
    	service.saveOrUpdate(p);
    	assertEquals(1,service.readAll().size());
    	assertEquals(3, service.readAll().get(0).getRoles().size());
	}
	
	@Test
	public void editar() {
		Persona p = instancia("1");
    	p.addRol(instanciaInquilino(Calificacion.A));
    	p.addRol(instanciaInquilino(Calificacion.B));
    	p.addRol(instanciaInquilino(Calificacion.C));
    	
    	
    	service.saveOrUpdate(p);
    	
    	p=service.readAll().get(0);
    	p.getRoles().forEach(rol -> {
    		if(rol.getClass().equals(Inquilino.class)){
    			Inquilino i =(Inquilino) rol;
    			if(i.getCalificacion().equals(Calificacion.C))
    				i.setCalificacion(Calificacion.D);
    		}
    	});
    	service.saveOrUpdate(p);
    	
    	Set<RolPersona>roles=service.readAll().get(0).getRoles();
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
                .buid();
    }
    
    private Inquilino instanciaInquilino(String numero) {
    	return new Inquilino.Builder()
    			.setCalificacion(Calificacion.A)
    			.build();
    }
    
    private Inquilino instanciaInquilino(Calificacion c) {
    	return new Inquilino.Builder()
    			.setCalificacion(c)
    			.build();
    }


}
