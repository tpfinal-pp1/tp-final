package com.TpFinal.data.dto;

import static org.junit.Assert.*;

import org.apache.tools.ant.util.PackageNameMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.TpFinal.data.dto.persona.Inquilino;
import com.TpFinal.data.dto.persona.Persona;
import com.TpFinal.data.dto.persona.Propietario;
import com.TpFinal.data.dto.publicacion.Rol;

public class PersonaTest {



	@Test
	public void addRol() {
		Persona p = instancia("1");
		assertTrue(p.addRol(Rol.Inquilino));
		assertFalse(p.addRol(Rol.Inquilino));
		assertEquals(1, p.getRoles().size());
		assertTrue(p.addRol(Rol.Propietario));
		assertFalse(p.addRol(Rol.Propietario));
	}
	
	@Test
	public void getRol() {
		Persona p = instancia("1");
		p.addRol(Rol.Inquilino);
		p.addRol(Rol.Propietario);
		
		assertEquals(Inquilino.class, p.getRol(Rol.Inquilino).getClass());
		assertEquals(Propietario.class, p.getRol(Rol.Propietario).getClass());
	}
	
	@Test
	public void giveMeYourRoles() {
		Persona p = instancia("1");
		p.addRol(Rol.Inquilino);
		p.addRol(Rol.Propietario);
		assertEquals(2, p.giveMeYourRoles().size());
		
		p=instancia("1");
		p.addRol(Rol.Inquilino);
		assertEquals(1, p.giveMeYourRoles().size());
		
		p=instancia("1");
		assertEquals(0, p.giveMeYourRoles().size());
		
		
		
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

}
