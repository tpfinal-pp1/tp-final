package com.TpFinal.Integracion.services;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.TpFinal.data.conexion.ConexionHibernate;
import com.TpFinal.data.conexion.TipoConexion;
import com.TpFinal.data.dao.DAOInmobiliariaImpl;
import com.TpFinal.data.dao.DAOInmuebleImpl;
import com.TpFinal.data.dao.interfaces.DAOInmobiliaria;
import com.TpFinal.data.dao.interfaces.DAOInmueble;
import com.TpFinal.dto.inmobiliaria.Inmobiliaria;
import com.TpFinal.dto.inmueble.ClaseInmueble;
import com.TpFinal.dto.inmueble.Coordenada;
import com.TpFinal.dto.inmueble.Direccion;
import com.TpFinal.dto.inmueble.EstadoInmueble;
import com.TpFinal.dto.inmueble.Inmueble;
import com.TpFinal.dto.inmueble.TipoInmueble;
import com.TpFinal.dto.inmueble.TipoMoneda;
import com.TpFinal.dto.publicacion.PublicacionAlquiler;
import com.TpFinal.services.InmobiliariaService;

public class InmobiliariaServiceIT {
	InmobiliariaService inm = new InmobiliariaService();
	DAOInmobiliaria daoInmobiliaria;
	DAOInmueble daoInmueble;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ConexionHibernate.setTipoConexion(TipoConexion.H2Test);
	}

	@Before
	public void setUp() throws Exception {
		daoInmobiliaria= new DAOInmobiliariaImpl();
		daoInmueble=new DAOInmuebleImpl();
		borrarAsociaciones();
		daoInmobiliaria.readAll().forEach(i -> daoInmobiliaria.delete(i));
		daoInmueble.readAll().forEach(i -> daoInmueble.delete(i));
	}
	
	private void borrarAsociaciones() {
		daoInmueble.readAll().forEach(i -> {
			i.setInmobiliaria(null);
		});
	}

	@After
	public void tearDown() throws Exception {
		borrarAsociaciones();
		daoInmobiliaria.readAll().forEach(i -> daoInmobiliaria.delete(i));
		daoInmueble.readAll().forEach(i -> daoInmueble.delete(i));
	}

	@Test
	public void test() {
		for(int i=0; i<10; i++) {
			inm.saveOrUpdate(unaInmobiliaria(Integer.toString(i)));
		}
		
		assertEquals(10, inm.readAll().size());
		inm.readAll().forEach(i -> System.out.println(i.toString()));
	}
	

    private Inmueble unInmuebleCon8Ambientes() {
	Inmueble inmueble = new Inmueble.Builder()
		.setaEstrenar(true)
		.setCantidadAmbientes(5)
		.setCantidadCocheras(3)
		.setCantidadDormitorios(1)
		.setClaseInmueble(ClaseInmueble.Consultorio)
		.setConAireAcondicionado(true)
		.setConJardin(true)
		.setConParilla(true)
		.setConPileta(true)
		.setDireccion(new Direccion.Builder()
			.setCalle("Una calle")
			.setCodPostal("asd123")
			.setCoordenada(new Coordenada())
			.setLocalidad("una Localidad")
			.setNro(123)
			.setPais("Argentina")
			.setProvincia("Buenos Aires")
			.build())
		.setEstadoInmueble(EstadoInmueble.EnAlquiler)
		.setSuperficieCubierta(200)
		.setSuperficieTotal(400)
		.setTipoInmueble(TipoInmueble.Comercial)
		.build();
	inmueble.addPublicacion(new PublicacionAlquiler.Builder()
		.setFechaPublicacion(LocalDate.of(2017, 9, 1))
		.setMoneda(TipoMoneda.Dolares)
		.setValorCuota(BigDecimal.valueOf(1e3))
		.setInmueble(inmueble)
		.build());
	return inmueble;
    }
    
    private Inmobiliaria unaInmobiliaria(String n) {
    	return new Inmobiliaria.Builder()
    			.setNombre("Inmobiliaria Eastwood "+n)
    			.setCuit("132d1as32d1 "+n)
    			.build();
    }

}
