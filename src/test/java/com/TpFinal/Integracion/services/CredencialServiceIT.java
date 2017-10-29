package com.TpFinal.Integracion.services;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.TpFinal.data.conexion.ConexionHibernate;
import com.TpFinal.data.conexion.TipoConexion;
import com.TpFinal.data.dao.DAOCredencialImpl;
import com.TpFinal.dto.persona.Credencial;
import com.TpFinal.dto.persona.Empleado;
import com.TpFinal.services.CredencialService;

public class CredencialServiceIT {

	DAOCredencialImpl dao;
	List<Credencial>Credencials= new ArrayList<>();
	CredencialService service = new CredencialService();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
		ConexionHibernate.setTipoConexion(TipoConexion.H2Test);
	}

	@Before
	public void setUp() throws Exception {
		dao= new DAOCredencialImpl();
		dao.readAll().forEach(dao::delete);
		Credencials.clear();
	}

	@After
	public void tearDown() throws Exception {
		Credencials=dao.readAll();
		Credencials.forEach(dao::delete);
	}

	@Test
    public void agregar() {
    	for(int i=0; i<3; i++) {service.saveOrUpdate(instanciaCredencial(String.valueOf(i)));}
    	
    	List<Credencial>credenciales=service.readAll();
    	assertEquals(3, credenciales.size());
    }
    
    @Test
    public void existe() {
    	for(int i=0; i<3; i++) {service.saveOrUpdate(instanciaCredencial(String.valueOf(i)));}
    	
    	assertTrue(service.existe(new Credencial.Builder().setUsuario("dsa0").setContrasenia("asd0").build()));
    	assertTrue(service.existe(new Credencial.Builder().setUsuario("dsa1").setContrasenia("asd1").build()));
    	assertTrue(service.existe(new Credencial.Builder().setUsuario("dsa2").setContrasenia("asd2").build()));
    	
    	assertFalse(service.existe(new Credencial.Builder().setUsuario("dsa1").setContrasenia("asd0").build()));
    	
    }
	
    @Test
    public void logicalDelete() {
    	for(int i=0; i<3; i++) {service.saveOrUpdate(instanciaCredencial(String.valueOf(i)));}
    	
    	assertTrue(service.delete(service.readAll().get(0)));
    	assertTrue(service.delete(service.readAll().get(0)));
    	assertEquals(1, service.readAll().size());
    	assertEquals(3, dao.readAll().size());
    	
    }
    
    
    public static Empleado instanciaEmpleado(String numero) {
        return new Empleado.Builder()
                .setNombre("nombre "+numero)
                .setApellido("apellido "+numero)
                .setMail("mail "+numero)
                .setTelefono("telefono "+numero)
                .setTelefono("telefono "+numero)
                .setTelefono2("telefono2 "+numero)
                .setDNI("Dni"+numero)
                .setinfoAdicional("Info Adicional"+ numero)
                .setFechaDeAlta(LocalDate.now())
                .build();
    }
    
    public static Credencial instanciaCredencial(String numero) {
    	return new Credencial.Builder()
    			.setContrasenia("asd"+numero)
    			.setUsuario("dsa"+numero)
    			.build();
    }


}
