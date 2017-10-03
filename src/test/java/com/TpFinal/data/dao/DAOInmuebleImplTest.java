package com.TpFinal.data.dao;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
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
import com.TpFinal.data.dto.operacion.Operacion;
import com.TpFinal.data.dto.operacion.OperacionAlquiler;
import com.TpFinal.data.dto.operacion.OperacionVenta;
import com.TpFinal.data.dto.operacion.TipoOperacion;

public class DAOInmuebleImplTest {
    DAOInmuebleImpl dao;
    List<Inmueble> inmuebles = new ArrayList<>();
    private CriterioBusquedaInmuebleDTO criterio;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
	ConexionHibernate.setTipoConexion(TipoConexion.MariaDBTest);
    }

    @Before
    public void setUp() throws Exception {
	dao = new DAOInmuebleImpl();
	inmuebles.clear();
	criterio = null;

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
	inmueblesEnBD.forEach(i -> assertEquals(i, inmueblesAGuardar.get(inmueblesEnBD.indexOf(i))));
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
    public void findInmueblesByCriteria_AEstrenar() {
	Inmueble inmuebleAEstrenar = unInmuebleNoPublicado();
	inmuebleAEstrenar.setaEstrenar(true);
	Inmueble inmuebleEstrenado = unInmuebleNoPublicado();
	inmuebleEstrenado.setaEstrenar(false);

	dao.create(inmuebleAEstrenar);
	dao.create(inmuebleEstrenado);

	inmuebles = dao.readAll();

	assertTrue(inmuebles.get(0).getaEstrenar() == true);
	assertTrue(inmuebles.get(1).getaEstrenar() == false);

	criterio = new CriterioBusquedaInmuebleDTO.Builder().setaEstrenar(true).build();
	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);

	assertEquals(1, inmuebles.size());
	assertTrue(inmuebles.get(0).getaEstrenar() == true);

	criterio = new CriterioBusquedaInmuebleDTO.Builder().setaEstrenar(false).build();
	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);

	assertEquals(1, inmuebles.size());
	assertTrue(inmuebles.get(0).getaEstrenar() == false);

    }

    @Test
    public void findInmueblesByCriteria_Ciudad() {
	int cantidadDeInmuebles = 3;
	Stream.iterate(0, x -> x++).limit(cantidadDeInmuebles).forEach(x -> dao.create(unInmuebleNoPublicado()));

	criterio = new CriterioBusquedaInmuebleDTO.Builder().setCiudad("una Localidad").build();

	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);
	assertEquals(cantidadDeInmuebles, inmuebles.size());

	criterio = new CriterioBusquedaInmuebleDTO.Builder().setCiudad("otra localidad").build();

	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);
	assertEquals(0, inmuebles.size());

    }

    @Test
    public void findInmueblesByCriteria_AireAcondicionado() {
	Inmueble inmuebleConAA = unInmuebleNoPublicado();
	inmuebleConAA.setConAireAcondicionado(true);
	Inmueble inmuebleSinAA = unInmuebleNoPublicado();
	inmuebleSinAA.setConAireAcondicionado(false);

	dao.create(inmuebleConAA);
	dao.create(inmuebleSinAA);

	inmuebles = dao.readAll();

	assertTrue(inmuebles.get(0).getConAireAcondicionado() == true);
	assertTrue(inmuebles.get(1).getConAireAcondicionado() == false);

	criterio = new CriterioBusquedaInmuebleDTO.Builder().setConAireAcondicionado(true).build();
	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);

	assertEquals(1, inmuebles.size());
	assertTrue(inmuebles.get(0).getConAireAcondicionado() == true);

	criterio = new CriterioBusquedaInmuebleDTO.Builder().setConAireAcondicionado(false).build();
	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);

	assertEquals(1, inmuebles.size());
	assertTrue(inmuebles.get(0).getConAireAcondicionado() == false);
    }

    @Test
    public void findInmueblesByCriteria_Estado() {
	int cantidadDeInmuebles = 3;
	Stream.iterate(0, x -> x++).limit(cantidadDeInmuebles).forEach(x -> dao.create(unInmuebleNoPublicado()));

	criterio = new CriterioBusquedaInmuebleDTO.Builder().setEstadoInmueble(EstadoInmueble.NoPublicado).build();

	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);
	assertEquals(cantidadDeInmuebles, inmuebles.size());

	criterio = new CriterioBusquedaInmuebleDTO.Builder().setEstadoInmueble(EstadoInmueble.Alquilado).build();

	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);
	assertEquals(0, inmuebles.size());
    }

    @Test
    public void findInmueblesByCriteria_ClaseInmuebleCochera() {
	crearInmueblesEnVentaEnPesosConValorCuota100xN(3);
	Inmueble i = dao.readAll().get(0);
	i.setClaseInmueble(ClaseInmueble.Cochera);
	dao.save(i);

	criterio = new CriterioBusquedaInmuebleDTO.Builder().setClasesDeInmueble(Arrays.asList(ClaseInmueble.Cochera))
		.build();
	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);
	assertEquals(1, inmuebles.size());

	criterio = new CriterioBusquedaInmuebleDTO.Builder().setClasesDeInmueble(Arrays.asList(ClaseInmueble.Ph))
		.build();
	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);
	assertEquals(2, inmuebles.size());
    }

    @Test
    public void findInmueblesByCriteria_ClaseInmuebleCocheraOrPh() {
	crearInmueblesEnVentaEnPesosConValorCuota100xN(3);
	Inmueble i = dao.readAll().get(0);
	i.setClaseInmueble(ClaseInmueble.Cochera);
	dao.save(i);

	criterio = new CriterioBusquedaInmuebleDTO.Builder()
		.setClasesDeInmueble(Arrays.asList(ClaseInmueble.Cochera, ClaseInmueble.Ph)).build();
	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);
	assertEquals(3, inmuebles.size());

	criterio = new CriterioBusquedaInmuebleDTO.Builder().setClasesDeInmueble(Arrays.asList(ClaseInmueble.Ph))
		.build();
	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);
	assertEquals(2, inmuebles.size());
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

	criterio = new CriterioBusquedaInmuebleDTO.Builder().setCiudad("una Localidad").setConAireAcondicionado(true)
		.build();

	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);
	assertEquals(1, inmuebles.size());

	criterio = new CriterioBusquedaInmuebleDTO.Builder().setCiudad("otra localidad").setConAireAcondicionado(false)
		.build();

	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);
	assertEquals(2, inmuebles.size());

    }

    @Test
    public void findInmueblesByCriteria_Jardin() {
	Inmueble inmuebleConJardin = unInmuebleNoPublicado();
	inmuebleConJardin.setConJardin(true);
	Inmueble inmuebleSinJardin = unInmuebleNoPublicado();
	inmuebleSinJardin.setConJardin(false);

	dao.create(inmuebleConJardin);
	dao.create(inmuebleSinJardin);

	inmuebles = dao.readAll();

	assertTrue(inmuebles.get(0).getConJardin() == true);
	assertTrue(inmuebles.get(1).getConJardin() == false);

	criterio = new CriterioBusquedaInmuebleDTO.Builder().setConJardin(true).build();
	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);

	assertEquals(1, inmuebles.size());
	assertTrue(inmuebles.get(0).getConJardin() == true);

	criterio = new CriterioBusquedaInmuebleDTO.Builder().setConJardin(false).build();
	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);

	assertEquals(1, inmuebles.size());
	assertTrue(inmuebles.get(0).getConJardin() == false);
    }

    @Test
    public void findInmueblesByCriteria_Parrilla() {
	Inmueble inmuebleCon = unInmuebleNoPublicado();
	inmuebleCon.setConParilla(true);
	Inmueble inmuebleSin = unInmuebleNoPublicado();
	inmuebleSin.setConParilla(false);

	dao.create(inmuebleCon);
	dao.create(inmuebleSin);

	inmuebles = dao.readAll();

	assertTrue(inmuebles.get(0).getConParilla() == true);
	assertTrue(inmuebles.get(1).getConParilla() == false);

	criterio = new CriterioBusquedaInmuebleDTO.Builder().setConParrilla(true).build();
	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);

	assertEquals(1, inmuebles.size());
	assertTrue(inmuebles.get(0).getConParilla() == true);

	criterio = new CriterioBusquedaInmuebleDTO.Builder().setConParrilla(false).build();
	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);

	assertEquals(1, inmuebles.size());
	assertTrue(inmuebles.get(0).getConParilla() == false);
    }

    @Test
    public void findInmueblesByCriteria_Pileta() {
	Inmueble inmuebleCon = unInmuebleNoPublicado();
	inmuebleCon.setConPileta(true);
	Inmueble inmuebleSin = unInmuebleNoPublicado();
	inmuebleSin.setConPileta(false);
	dao.create(inmuebleCon);
	dao.create(inmuebleSin);

	inmuebles = dao.readAll();

	assertTrue(inmuebles.get(0).getConPileta() == true);
	assertTrue(inmuebles.get(1).getConPileta() == false);

	criterio = new CriterioBusquedaInmuebleDTO.Builder().setConPileta(true).build();
	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);

	assertEquals(1, inmuebles.size());
	assertTrue(inmuebles.get(0).getConPileta() == true);

	criterio = new CriterioBusquedaInmuebleDTO.Builder().setConPileta(false).build();
	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);

	assertEquals(1, inmuebles.size());
	assertTrue(inmuebles.get(0).getConPileta() == false);
    }

    @Test
    public void findInmueblesByCriteria_EstadoInmueble() {

	Inmueble inmuebleAlquilado = unInmuebleNoPublicado();
	inmuebleAlquilado.setEstadoInmueble(EstadoInmueble.Alquilado);

	Inmueble inmuebleEnAlquiler = unInmuebleNoPublicado();
	inmuebleEnAlquiler.setEstadoInmueble(EstadoInmueble.EnAlquiler);

	Inmueble inmuebleEnVenta = unInmuebleNoPublicado();
	inmuebleEnVenta.setEstadoInmueble(EstadoInmueble.EnVenta);

	Inmueble inmuebleNoPublicado = unInmuebleNoPublicado();
	inmuebleNoPublicado.setEstadoInmueble(EstadoInmueble.NoPublicado);

	Inmueble inmuebleVendido = unInmuebleNoPublicado();
	inmuebleVendido.setEstadoInmueble(EstadoInmueble.Vendido);

	dao.create(inmuebleAlquilado);
	dao.create(inmuebleEnAlquiler);
	dao.create(inmuebleEnVenta);
	dao.create(inmuebleNoPublicado);
	dao.create(inmuebleVendido);

	inmuebles = dao.readAll();
	EstadoInmueble[] estados = EstadoInmueble.values();

	assertEquals(inmuebles.size(), estados.length);

	for (int i = 0; i < inmuebles.size(); i++) {
	    assertEquals(inmuebles.get(i).getEstadoInmueble(), estados[i]);
	}

	for (int i = 0; i < estados.length; i++) {
	    criterio = new CriterioBusquedaInmuebleDTO.Builder().setEstadoInmueble(estados[i]).build();
	    inmuebles = dao.findInmueblesbyCaracteristicas(criterio);
	    assertEquals(1, inmuebles.size());
	    assertEquals(inmuebles.get(0).getEstadoInmueble(), estados[i]);
	}

    }

    @Test
    public void findInmueblesByCriteria_SupTotal() {
	int cantidadDeInmuebles = 3;

	for (int x = 1; x <= cantidadDeInmuebles; x++) {
	    Inmueble i = unInmuebleNoPublicado();
	    i.setSuperficieTotal(x * 100);
	    dao.create(i);

	}

	criterio = new CriterioBusquedaInmuebleDTO.Builder().setMinSupTotal(200).build();
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

    @Test
    public void findInmueblesByCriteria_InmueblesAlquilados() {
	unoNoPublicado_unoEnAlquiler_unoEnVenta().forEach(dao::create);
	criterio = new CriterioBusquedaInmuebleDTO.Builder().setTipoOperacion(TipoOperacion.Alquiler).build();
	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);
	assertEquals(1, inmuebles.size());

    }

    @Test
    public void findInmueblesByCriteria_InmueblesEnVenta() {
	unoNoPublicado_unoEnAlquiler_unoEnVenta().forEach(dao::create);
	criterio = new CriterioBusquedaInmuebleDTO.Builder().setTipoOperacion(TipoOperacion.Venta).build();
	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);
	assertEquals(1, inmuebles.size());

    }

    @Test
    public void findInmueblesByCriteria_TodosLosInmuebles() {
	unoNoPublicado_unoEnAlquiler_unoEnVenta().forEach(dao::create);
	criterio = new CriterioBusquedaInmuebleDTO.Builder().build();
	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);
	assertEquals(3, inmuebles.size());

    }

    @Test
    public void findInmueblesByCriteria_InmueblesEnAlquilerAndValorCuotaMayorIgualA200() {
	crearInmueblesEnAlquilerEnDolaresConValorCuota100xN(3);

	criterio = new CriterioBusquedaInmuebleDTO.Builder().setTipoOperacion(TipoOperacion.Alquiler)
		.setMinPrecio(BigDecimal.valueOf(200)).build();
	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);
	assertEquals(2, inmuebles.size());
    }

    @Test
    public void findInmueblesByCriteria_InmueblesEnAlquilerAndValorCuotaMenorIgualA100() {

	crearInmueblesEnAlquilerEnDolaresConValorCuota100xN(3);

	criterio = new CriterioBusquedaInmuebleDTO.Builder().setTipoOperacion(TipoOperacion.Alquiler)
		.setMaxPrecio(BigDecimal.valueOf(100)).build();
	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);
	assertEquals(1, inmuebles.size());
    }

    @Test
    public void findInmueblesByCriteria_InmueblesEnVentaAndValorCuotaMayorIgualA200() {
	crearInmueblesEnVentaEnPesosConValorCuota100xN(3);

	criterio = new CriterioBusquedaInmuebleDTO.Builder().setTipoOperacion(TipoOperacion.Venta)
		.setMinPrecio(BigDecimal.valueOf(200)).build();
	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);
	assertEquals(2, inmuebles.size());
    }

    @Test
    public void findInmueblesByCriteria_InmueblesEnVentaAndValorCuotaMenorIgualA100() {

	crearInmueblesEnVentaEnPesosConValorCuota100xN(3);

	criterio = new CriterioBusquedaInmuebleDTO.Builder().setTipoOperacion(TipoOperacion.Venta)
		.setMaxPrecio(BigDecimal.valueOf(100)).build();
	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);
	assertEquals(1, inmuebles.size());
    }

    @Test
    public void findInmueblesByCriteria_InmueblesEnVentaAndValorCuotaMayorIgualA200AndClaseCochera() {
	crearInmueblesEnVentaEnPesosConValorCuota100xN(3);
	Inmueble i = dao.readAll().get(0);
	i.setClaseInmueble(ClaseInmueble.Cochera);
	dao.save(i);

	criterio = new CriterioBusquedaInmuebleDTO.Builder().setTipoOperacion(TipoOperacion.Venta)
		.setClasesDeInmueble(Arrays.asList(ClaseInmueble.Cochera)).build();
	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);
	assertEquals(1, inmuebles.size());
    }

    @Test
    public void findInmueblesByCriteria_InmueblesEnVentaAndValorCuotaMayorIgualA200AndEsCocheraAndSinPileta() {
	crearInmueblesEnVentaEnPesosConValorCuota100xN(3);
	Inmueble i = dao.readAll().get(0);
	i.setClaseInmueble(ClaseInmueble.Cochera);
	i.setConPileta(false);
	dao.save(i);

	criterio = new CriterioBusquedaInmuebleDTO.Builder().setTipoOperacion(TipoOperacion.Venta)
		.setClasesDeInmueble(Arrays.asList(ClaseInmueble.Cochera)).setConPileta(false).build();
	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);
	assertEquals(1, inmuebles.size());
    }

    @Test
    public void findInmueblesByCriteria_InmueblesEnDolares() {
	crearInmueblesEnAlquilerEnDolaresConValorCuota100xN(2);
	criterio = new CriterioBusquedaInmuebleDTO.Builder().setTipoMoneda(TipoMoneda.Dolares).build();

	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);
	assertEquals(2, inmuebles.size());

	crearInmueblesEnVentaEnPesosConValorCuota100xN(1);
	crearInmueblesEnAlquilerEnDolaresConValorCuota100xN(4);

	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);
	assertEquals(6, inmuebles.size());

    }

    @Test
    public void testOperaciones() {
	Inmueble i = unInmuebleNoPublicado();
	i.setEstadoInmueble(EstadoInmueble.EnVenta);
	i.addOperacion(new OperacionVenta.Builder()
		.setFechaPublicacion(LocalDate.now())
		.setInmueble(i)
		.setPrecio(BigDecimal.valueOf(200))
		.build());
	dao.create(i);
	inmuebles = dao.readAll();
	assertEquals(1, inmuebles.get(0).getOperaciones().size());
    }

    private void crearInmueblesEnAlquilerEnDolaresConValorCuota100xN(int cantidadDeInmuebles) {
	for (int x = 1; x <= cantidadDeInmuebles; x++) {
	    Inmueble i = unInmuebleEnAlquilerEnDolares();
	    for (Operacion o : i.getOperaciones()) {
		if (o instanceof OperacionAlquiler) {
		    OperacionAlquiler oa = ((OperacionAlquiler) o);
		    oa.setPrecio(BigDecimal.valueOf(100 * x));
		}
	    }
	    dao.create(i);
	}
    }

    private void crearInmueblesEnVentaEnPesosConValorCuota100xN(int cantidadDeInmuebles) {
	for (int x = 1; x <= cantidadDeInmuebles; x++) {
	    Inmueble i = unInmuebleEnVentaEnPesos();
	    for (Operacion o : i.getOperaciones()) {
		if (o instanceof OperacionVenta) {
		    OperacionVenta ov = ((OperacionVenta) o);
		    ov.setPrecio(BigDecimal.valueOf(100 * x));
		}
	    }
	    dao.create(i);
	}
    }

    private List<Inmueble> unoNoPublicado_unoEnAlquiler_unoEnVenta() {
	List<Inmueble> inmuebles = new ArrayList<>();
	inmuebles.add(unInmuebleNoPublicado());
	inmuebles.add(unInmuebleEnVentaEnPesos());
	inmuebles.add(unInmuebleEnAlquilerEnDolares());
	return inmuebles;
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

    private Inmueble unInmuebleEnVentaEnPesos() {
	Inmueble inmueble = new Inmueble.Builder()
		.setaEstrenar(true)
		.setCantidadAmbientes(2)
		.setCantidadCocheras(3)
		.setCantidadDormitorios(1)
		.setClaseInmueble(ClaseInmueble.Ph)
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
		.setEstadoInmueble(EstadoInmueble.EnVenta)
		.setSuperficieCubierta(200)
		.setSuperficieTotal(400)
		.setTipoInmueble(TipoInmueble.Vivienda)
		.build();
	inmueble.addOperacion(new OperacionVenta.Builder()
		.setFechaPublicacion(LocalDate.of(2017, 10, 1))
		.setMoneda(TipoMoneda.Pesos)
		.setPrecio(BigDecimal.valueOf(12e3))
		.setInmueble(inmueble)
		.build());
	return inmueble;
    }

    private Inmueble unInmuebleEnAlquilerEnDolares() {
	Inmueble inmueble = new Inmueble.Builder()
		.setaEstrenar(true)
		.setCantidadAmbientes(2)
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
	inmueble.addOperacion(new OperacionAlquiler.Builder()
		.setFechaPublicacion(LocalDate.of(2017, 9, 1))
		.setMoneda(TipoMoneda.Dolares)
		.setValorCuota(BigDecimal.valueOf(1e3))
		.setInmueble(inmueble)
		.build());
	return inmueble;
    }

}
