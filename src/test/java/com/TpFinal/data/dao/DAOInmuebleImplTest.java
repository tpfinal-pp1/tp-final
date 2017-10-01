package com.TpFinal.data.dao;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.TpFinal.data.conexion.ConexionHibernate;
import com.TpFinal.data.conexion.TipoConexion;
import com.TpFinal.data.dto.inmueble.ClaseInmueble;
import com.TpFinal.data.dto.inmueble.Coordenada;
import com.TpFinal.data.dto.inmueble.CriterioBusquedaInmuebleDTO;
import com.TpFinal.data.dto.inmueble.Direccion;
import com.TpFinal.data.dto.inmueble.EstadoInmueble;
import com.TpFinal.data.dto.inmueble.Inmueble;
import com.TpFinal.data.dto.inmueble.TipoInmueble;
import com.TpFinal.data.dto.inmueble.TipoMoneda;
import com.TpFinal.data.dto.operacion.OperacionVenta;

public class DAOInmuebleImplTest {
	DAOInmuebleImpl dao;
	List<Inmueble> inmuebles = new ArrayList<>();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ConexionHibernate.setTipoConexion(TipoConexion.H2Test);
	}

	@Before
	public void setUp() throws Exception {
		dao = new DAOInmuebleImpl();
		inmuebles.clear();
	}

	@After
	public void tearDown() throws Exception {
		inmuebles = dao.readAll();
		inmuebles.forEach(dao::delete);
	}

	@Test
	public void testCreate() {
		List<Inmueble> inmueblesAGuardar = unoNoPublicado_unoEnAlquiler_unoEnVenta();
		inmueblesAGuardar.forEach(dao::create);
		List<Inmueble> inmueblesEnBD = dao.readAll();
		inmueblesEnBD.forEach(i -> assertEquals(i,inmueblesAGuardar.get(inmueblesEnBD.indexOf(i))));
	}

	

	@Test
	public void testReadAll() {
		int cantidadDeInmuebles = 3;
		Stream.iterate(0, x -> x++).limit(cantidadDeInmuebles).forEach(x -> dao.create(unInmuebleNoPublicado()));
		inmuebles = dao.readAll();
		assertEquals(cantidadDeInmuebles, inmuebles.size());
	}

	@Test
	public void testUpdate() {
		Inmueble original = unInmuebleNoPublicado();
		dao.create(original);
		Inmueble modificado = dao.readAll().get(0);
		modificado.setCantidadAmbientes(10);
		modificado.getDireccion().setCoordenada(new Coordenada(12.0, 10.2));
		dao.update(modificado);
		assertTrue(modificado.getCantidadAmbientes().equals(dao.findById(modificado.getId()).getCantidadAmbientes()));
		assertTrue(modificado.getDireccion().getCoordenada()
				.equals(dao.findById(modificado.getId()).getDireccion().getCoordenada()));
	}

	@Test
	public void findInmueblesbyEstado() {
		int cantidadDeInmuebles = 3;
		Stream.iterate(0, x -> x++).limit(cantidadDeInmuebles).forEach(x -> dao.create(unInmuebleNoPublicado()));
		inmuebles = dao.findInmueblesbyEstado(EstadoInmueble.NoPublicado);
		assertEquals(cantidadDeInmuebles, inmuebles.size());
	}

	@Test
	public void findInmueblesByCriteria_Estado() {
		int cantidadDeInmuebles = 3;
		Stream.iterate(0, x -> x++).limit(cantidadDeInmuebles).forEach(x -> dao.create(unInmuebleNoPublicado()));

		CriterioBusquedaInmuebleDTO criterio = new CriterioBusquedaInmuebleDTO.Builder()
				.setEstadoInmueble(EstadoInmueble.NoPublicado).build();

		inmuebles = dao.findInmueblesbyCaracteristicas(criterio);
		assertEquals(cantidadDeInmuebles, inmuebles.size());

		criterio = new CriterioBusquedaInmuebleDTO.Builder().setEstadoInmueble(EstadoInmueble.Alquilado).build();

		inmuebles = dao.findInmueblesbyCaracteristicas(criterio);
		assertEquals(0, inmuebles.size());

	}

	@Test
	public void findInmueblesByCriteria_Ciudad() {
		int cantidadDeInmuebles = 3;
		Stream.iterate(0, x -> x++).limit(cantidadDeInmuebles).forEach(x -> dao.create(unInmuebleNoPublicado()));

		CriterioBusquedaInmuebleDTO criterio = new CriterioBusquedaInmuebleDTO.Builder().setCiudad("una Localidad")
				.build();

		inmuebles = dao.findInmueblesbyCaracteristicas(criterio);
		assertEquals(cantidadDeInmuebles, inmuebles.size());

		criterio = new CriterioBusquedaInmuebleDTO.Builder().setCiudad("otra localidad").build();

		inmuebles = dao.findInmueblesbyCaracteristicas(criterio);
		assertEquals(0, inmuebles.size());

	}

	@Test
	public void findInmueblesByCriteria_CiudadAndAireAcondicionado() {
		int cantidadDeInmuebles = 3;

		for (int x = 0; x < cantidadDeInmuebles; x++) {
			Inmueble i = unInmuebleNoPublicado();
			if (x % 2 == 0) {
				i.setConAireAcondicionado(false);
				i.setDireccion(new Direccion.Builder().setLocalidad("otra localidad").build());
			}
			dao.create(i);

		}

		CriterioBusquedaInmuebleDTO criterio = new CriterioBusquedaInmuebleDTO.Builder().setCiudad("una Localidad")
				.setConAireAcondicionado(true).build();

		inmuebles = dao.findInmueblesbyCaracteristicas(criterio);
		assertEquals(1, inmuebles.size());

		criterio = new CriterioBusquedaInmuebleDTO.Builder().setCiudad("otra localidad").setConAireAcondicionado(false)
				.build();

		inmuebles = dao.findInmueblesbyCaracteristicas(criterio);
		assertEquals(2, inmuebles.size());

	}

	@Test
	public void findInmueblesByCriteria_SupTotal() {
		int cantidadDeInmuebles = 3;

		for (int x = 1; x <= cantidadDeInmuebles; x++) {
			Inmueble i = unInmuebleNoPublicado();
			i.setSuperficieTotal(x * 100);
			dao.create(i);

		}

		CriterioBusquedaInmuebleDTO criterio = new CriterioBusquedaInmuebleDTO.Builder().setMinSupTotal(200).build();
		inmuebles = dao.findInmueblesbyCaracteristicas(criterio);
		assertEquals(2, inmuebles.size());

		criterio = new CriterioBusquedaInmuebleDTO.Builder().setMaxSupTotal(250).build();
		inmuebles = dao.findInmueblesbyCaracteristicas(criterio);
		assertEquals(2, inmuebles.size());

		criterio = new CriterioBusquedaInmuebleDTO.Builder().setMaxSupTotal(300).build();
		inmuebles = dao.findInmueblesbyCaracteristicas(criterio);
		assertEquals(3, inmuebles.size());

		criterio = new CriterioBusquedaInmuebleDTO.Builder().setMinSupTotal(100).setMaxSupTotal(300).build();
		inmuebles = dao.findInmueblesbyCaracteristicas(criterio);
		assertEquals(3, inmuebles.size());

		criterio = new CriterioBusquedaInmuebleDTO.Builder().setMinSupTotal(400).build();
		inmuebles = dao.findInmueblesbyCaracteristicas(criterio);
		assertEquals(0, inmuebles.size());

		criterio = new CriterioBusquedaInmuebleDTO.Builder().setMaxSupTotal(99).build();
		inmuebles = dao.findInmueblesbyCaracteristicas(criterio);
		assertEquals(0, inmuebles.size());
	}
	
	
	private List<Inmueble> unoNoPublicado_unoEnAlquiler_unoEnVenta() {
		List<Inmueble> inmuebles = new ArrayList<>();
		inmuebles.add(unInmuebleNoPublicado());
		inmuebles.add(unInmuebleEnVenta());
		inmuebles.add(unInmuebleEnAlquiler());
		return inmuebles;
	}
	

	private Inmueble unInmuebleNoPublicado() {
		return new Inmueble.Builder().setaEstrenar(true).setCantidadAmbientes(2).setCantidadCocheras(3)
				.setCantidadDormitorios(1).setClaseInmueble(ClaseInmueble.Casa).setConAireAcondicionado(true)
				.setConJardin(true).setConParilla(true).setConPileta(true)
				.setDireccion(new Direccion.Builder().setCalle("Una calle").setCodPostal("asd123")
						.setCoordenada(new Coordenada()).setLocalidad("una Localidad").setNro(123).setPais("Argentina")
						.setProvincia("Buenos Aires").build())
				.setEstadoInmueble(EstadoInmueble.NoPublicado).setSuperficieCubierta(200).setSuperficieTotal(400)
				.setTipoInmueble(TipoInmueble.Vivienda).build();
	}

	private Inmueble unInmuebleEnVenta() {
		return new Inmueble.Builder().setaEstrenar(true).setCantidadAmbientes(2).setCantidadCocheras(3)
				.setCantidadDormitorios(1).setClaseInmueble(ClaseInmueble.Ph).setConAireAcondicionado(true)
				.setConJardin(true).setConParilla(true).setConPileta(true)
				.setDireccion(new Direccion.Builder().setCalle("Una calle").setCodPostal("asd123")
						.setCoordenada(new Coordenada()).setLocalidad("una Localidad").setNro(123).setPais("Argentina")
						.setProvincia("Buenos Aires").build())
				.setEstadoInmueble(EstadoInmueble.EnVenta).setSuperficieCubierta(200).setSuperficieTotal(400)
				.setTipoInmueble(TipoInmueble.Vivienda)
				.addOperacion(new OperacionVenta.Builder().setFechaPublicacion(LocalDate.of(2017, 10, 1))
						.setMoneda(TipoMoneda.Pesos).setPrecio(BigDecimal.valueOf(12e3)).build())
				.build();
	}

	private Inmueble unInmuebleEnAlquiler() {
		return new Inmueble.Builder().setaEstrenar(true).setCantidadAmbientes(2).setCantidadCocheras(3)
				.setCantidadDormitorios(1).setClaseInmueble(ClaseInmueble.Consultorio).setConAireAcondicionado(true)
				.setConJardin(true).setConParilla(true).setConPileta(true)
				.setDireccion(new Direccion.Builder().setCalle("Una calle").setCodPostal("asd123")
						.setCoordenada(new Coordenada()).setLocalidad("una Localidad").setNro(123).setPais("Argentina")
						.setProvincia("Buenos Aires").build())
				.setEstadoInmueble(EstadoInmueble.EnAlquiler).setSuperficieCubierta(200).setSuperficieTotal(400)
				.setTipoInmueble(TipoInmueble.Comercial)
				.addOperacion(new OperacionVenta.Builder().setFechaPublicacion(LocalDate.of(2017, 9, 1))
						.setMoneda(TipoMoneda.Dolares).setPrecio(BigDecimal.valueOf(1e3)).build())
				.build();
	}
	

}
