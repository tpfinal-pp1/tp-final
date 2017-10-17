package com.TpFinal.Integracion.services;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.TpFinal.data.conexion.ConexionHibernate;
import com.TpFinal.data.conexion.TipoConexion;
import com.TpFinal.data.dao.DAOInmuebleImpl;
import com.TpFinal.data.dao.DAOPersonaImpl;
import com.TpFinal.data.dao.interfaces.DAOInmueble;
import com.TpFinal.data.dao.interfaces.DAOPersona;
import com.TpFinal.dto.inmueble.ClaseInmueble;
import com.TpFinal.dto.inmueble.Coordenada;
import com.TpFinal.dto.inmueble.Direccion;
import com.TpFinal.dto.inmueble.EstadoInmueble;
import com.TpFinal.dto.inmueble.Inmueble;
import com.TpFinal.dto.inmueble.TipoInmueble;
import com.TpFinal.dto.persona.Persona;
import com.TpFinal.dto.persona.Propietario;
import com.TpFinal.dto.publicacion.Rol;
import com.TpFinal.services.InmuebleService;

public class InmuebleServiceIT {
	
	InmuebleService service;
	List<Inmueble>inmuebles=new ArrayList<>();
	List<Persona>personas= new ArrayList<>();
	DAOPersona daoPer;
	DAOInmueble daoInm;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
		ConexionHibernate.setTipoConexion(TipoConexion.H2Test);
	 }

	@Before
	public void setUp() throws Exception {
		service= new InmuebleService();
		daoInm=new DAOInmuebleImpl();
		daoInm.readAll().forEach(i -> daoInm.delete(i));
		daoPer=new DAOPersonaImpl();
		daoPer.readAll().forEach(p -> daoPer.delete(p));
		inmuebles.clear();
	}

	@After
	public void tearDown() throws Exception {
		daoInm.readAll().forEach(i -> daoInm.delete(i));
		daoPer.readAll().forEach(p -> daoPer.delete(p));
	}

	@Test
	public void test() {
		Propietario pro= new Propietario();
		Persona per = instancia("1");
		per.addRol(pro);
		//guardo la persona y su rol
		daoPer.save(per);
		
		Inmueble in = unInmuebleNoPublicado();
		
		//in.setPropietario(pro);
		pro=daoPer.readAllActives().get(0).getPropietario();
		pro.addInmueble(in);
		assertEquals(1, pro.getInmuebles().size());
		//guardo el inmueble
		assertTrue(service.merge(in));
		
		Inmueble inm = service.readAll().get(0);
		inm.setPropietario(null);
		assertTrue(service.merge(inm));
		

		assertEquals(null, service.readAll().get(0).getPropietario());
		Propietario p= (Propietario)daoPer.readAllActives().get(0).getRol(Rol.Propietario);
		
		assertEquals(0, p.getInmuebles().size());
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
	   
	  public Persona instancia(String numero) {
	        return new Persona.Builder()
	                .setNombre("nombre "+numero)
	                .setApellido("apellido "+numero)
	                .setMail("mail "+numero)
	                .setTelefono("telefono "+numero)
	                .setTelefono("telefono "+numero)
	                .setTelefono2("telefono2 "+numero)
	                .setDNI("Dni"+numero)
	                .setinfoAdicional("Info Adicional"+ numero)
	                .build();
	    }

}
