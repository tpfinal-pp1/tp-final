package com.TpFinal.data.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.TpFinal.data.dto.inmueble.ClaseInmueble;
import com.TpFinal.data.dto.inmueble.Coordenada;
import com.TpFinal.data.dto.inmueble.Direccion;
import com.TpFinal.data.dto.inmueble.EstadoInmueble;
import com.TpFinal.data.dto.inmueble.Inmueble;
import com.TpFinal.data.dto.inmueble.TipoInmueble;

public class DAOInmuebleImplTest {
	DAOInmuebleImpl dao;
	List<Inmueble> inmuebles = new ArrayList<>();

	@Before
	public void setUp() throws Exception {
		dao = new DAOInmuebleImpl(Inmueble.class);
		inmuebles.clear();
	}

	@After
	public void tearDown() throws Exception {
		inmuebles = dao.readAll();
		inmuebles.forEach(dao::delete);
	}

	@Test
	public void testCreate() {
		Inmueble unInmueble = getInstanciaPrueba();
		dao.create(unInmueble);
		Inmueble mismoInmueble = dao.readAll().get(0);
		assertEquals(unInmueble, mismoInmueble);
	}

	@Test
	public void testReadAll() {
		int cantidadDeInmuebles = 3;
		Stream.iterate(0, x -> x++).limit(cantidadDeInmuebles).forEach(x -> dao.create(getInstanciaPrueba()));
		inmuebles = dao.readAll();
		assertEquals(cantidadDeInmuebles, inmuebles.size());
	}

	@Test
	public void testUpdate() {
		Inmueble original = getInstanciaPrueba();
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
		Stream.iterate(0, x -> x++).limit(cantidadDeInmuebles).forEach(x -> dao.create(getInstanciaPrueba()));
		inmuebles = dao.findInmueblesbyEstado(EstadoInmueble.NoPublicado);
		assertEquals(cantidadDeInmuebles, inmuebles.size());
	}

	private Inmueble getInstanciaPrueba() {
		return new Inmueble.Builder().setaEstrenar(true).setCantidadAmbientes(2).setCantidadCocheras(3)
				.setCantidadDormitorios(1).setClaseInmueble(ClaseInmueble.Casa).setConAireAcondicionado(true)
				.setConJardin(true).setConParilla(true).setConPileta(true)
				.setDireccion(new Direccion.Builder().setCalle("Una calle").setCodPostal("asd123")
						.setCoordenada(new Coordenada()).setLocalidad("una Localidad").setNro(123).setPais("Argentina")
						.setProvincia("Buenos Aires").build())
				.setEstadoInmueble(EstadoInmueble.NoPublicado).setSuperficieCubierta(200).setSuperficieTotal(400)
				.setTipoInmueble(TipoInmueble.Vivienda).build();
	}

}
