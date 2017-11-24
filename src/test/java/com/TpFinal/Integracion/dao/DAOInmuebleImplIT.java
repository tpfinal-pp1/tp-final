package com.TpFinal.Integracion.dao;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.*;

import com.TpFinal.data.conexion.ConexionHibernate;
import com.TpFinal.data.conexion.TipoConexion;
import com.TpFinal.data.dao.DAOInmuebleImpl;
import com.TpFinal.data.dao.DAOPersonaImpl;
import com.TpFinal.dto.EstadoRegistro;
import com.TpFinal.dto.inmueble.ClaseInmueble;
import com.TpFinal.dto.inmueble.Coordenada;
import com.TpFinal.dto.inmueble.CriterioBusqInmueble;
import com.TpFinal.dto.inmueble.Direccion;
import com.TpFinal.dto.inmueble.EstadoInmueble;
import com.TpFinal.dto.inmueble.Inmueble;
import com.TpFinal.dto.inmueble.TipoInmueble;
import com.TpFinal.dto.inmueble.TipoMoneda;
import com.TpFinal.dto.persona.Persona;
import com.TpFinal.dto.persona.Propietario;
import com.TpFinal.dto.publicacion.Publicacion;
import com.TpFinal.dto.publicacion.PublicacionAlquiler;
import com.TpFinal.dto.publicacion.PublicacionVenta;
import com.TpFinal.dto.publicacion.TipoPublicacion;
import com.TpFinal.services.ProvinciaService;
import com.TpFinal.utils.GeneradorDeDatos;

public class DAOInmuebleImplIT {
    DAOInmuebleImpl dao;
    List<Inmueble> inmuebles = new ArrayList<>();
    private CriterioBusqInmueble criterio;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
	ConexionHibernate.setTipoConexion(TipoConexion.H2Test);

    }

    @Before
    public void setUp() throws Exception {
	dao = new DAOInmuebleImpl();
	dao.readAll().forEach(dao::delete);
	inmuebles.clear();
	criterio = null;

    }

    @After
    public void tearDown() throws Exception {
	inmuebles = dao.readAll();
	desvincular();
	inmuebles.forEach(dao::delete);
    }

    private void desvincular() {
	dao.readAll().forEach(i -> {
	    i.setPropietario(null);
	    dao.saveOrUpdate(i);
	});
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

	criterio = new CriterioBusqInmueble.Builder().setaEstrenar(true).build();
	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);

	assertEquals(1, inmuebles.size());
	assertTrue(inmuebles.get(0).getaEstrenar() == true);

	criterio = new CriterioBusqInmueble.Builder().setaEstrenar(false).build();
	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);

	assertEquals(1, inmuebles.size());
	assertTrue(inmuebles.get(0).getaEstrenar() == false);

    }

    @Test
    public void findInmueblesByCriteria_Localidad() {
	int cantidadDeInmuebles = 3;
	Stream.iterate(0, x -> x++).limit(cantidadDeInmuebles).forEach(x -> dao.create(unInmuebleNoPublicado()));

	criterio = new CriterioBusqInmueble.Builder().setLocalidad("una Localidad").build();

	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);
	assertEquals(cantidadDeInmuebles, inmuebles.size());

	criterio = new CriterioBusqInmueble.Builder().setLocalidad("otra localidad").build();

	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);
	assertEquals(0, inmuebles.size());

    }
    
    @Test
    public void findInmueblesByCriteria_Provincia() {
	int cantidadDeInmuebles = 3;
	Stream.iterate(0, x -> x++).limit(cantidadDeInmuebles).forEach(x -> dao.create(unInmuebleNoPublicado()));

	criterio = new CriterioBusqInmueble.Builder().setProvincia("Buenos Aires").build();

	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);
	assertEquals(cantidadDeInmuebles, inmuebles.size());

	criterio = new CriterioBusqInmueble.Builder().setProvincia("Jujuy").build();

	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);
	assertEquals(0, inmuebles.size());

    }
    
    
    @Test
    public void findInmueblesByCriteria_ProvinciaAndLocalidad() {
	int cantidadDeInmuebles = 3;
	Stream.iterate(0, x -> x++).limit(cantidadDeInmuebles).forEach(x ->{
	    Inmueble i= unInmuebleNoPublicado();
	    i.getDireccion().setLocalidad("ASD");
	    dao.create(i);
	    });

	criterio = new CriterioBusqInmueble.Builder().setProvincia("Buenos Aires").setLocalidad("ASD").build();

	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);
	assertEquals(cantidadDeInmuebles, inmuebles.size());

	dao.readAll().forEach(i ->{
	    i.getDireccion().setProvincia("Jujuy");
	    dao.merge(i);	    
	});
	
	criterio = new CriterioBusqInmueble.Builder().setProvincia("Jujuy").setLocalidad("ASD").build();

	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);
	assertEquals(3, inmuebles.size());

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

	criterio = new CriterioBusqInmueble.Builder().setConAireAcondicionado(true).build();
	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);

	assertEquals(1, inmuebles.size());
	assertTrue(inmuebles.get(0).getConAireAcondicionado() == true);

	criterio = new CriterioBusqInmueble.Builder().setConAireAcondicionado(false).build();
	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);

	assertEquals(1, inmuebles.size());
	assertTrue(inmuebles.get(0).getConAireAcondicionado() == false);
    }

    
    @Test
    public void findInmueblesByCriteria_Estado() {
	int cantidadDeInmuebles = 3;
	Stream.iterate(0, x -> x++).limit(cantidadDeInmuebles).forEach(x -> dao.create(unInmuebleNoPublicado()));

	criterio = new CriterioBusqInmueble.Builder().setEstadoInmueble(EstadoInmueble.NoPublicado).build();

	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);
	assertEquals(cantidadDeInmuebles, inmuebles.size());

	criterio = new CriterioBusqInmueble.Builder().setEstadoInmueble(EstadoInmueble.Alquilado).build();

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

	criterio = new CriterioBusqInmueble.Builder().setLocalidad("una Localidad").setConAireAcondicionado(true)
		.build();

	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);
	assertEquals(1, inmuebles.size());

	criterio = new CriterioBusqInmueble.Builder().setLocalidad("otra localidad").setConAireAcondicionado(false)
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

	criterio = new CriterioBusqInmueble.Builder().setConJardin(true).build();
	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);

	assertEquals(1, inmuebles.size());
	assertTrue(inmuebles.get(0).getConJardin() == true);

	criterio = new CriterioBusqInmueble.Builder().setConJardin(false).build();
	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);

	assertEquals(1, inmuebles.size());
	assertTrue(inmuebles.get(0).getConJardin() == false);
    }

    @Test
    public void findInmueblesByCriteria_Parrilla() {
	Inmueble inmuebleCon = unInmuebleNoPublicado();
	inmuebleCon.setConParrilla(true);
	Inmueble inmuebleSin = unInmuebleNoPublicado();
	inmuebleSin.setConParrilla(false);

	dao.create(inmuebleCon);
	dao.create(inmuebleSin);

	inmuebles = dao.readAll();

	assertTrue(inmuebles.get(0).getConParilla() == true);
	assertTrue(inmuebles.get(1).getConParilla() == false);

	criterio = new CriterioBusqInmueble.Builder().setConParrilla(true).build();
	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);

	assertEquals(1, inmuebles.size());
	assertTrue(inmuebles.get(0).getConParilla() == true);

	criterio = new CriterioBusqInmueble.Builder().setConParrilla(false).build();
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

	criterio = new CriterioBusqInmueble.Builder().setConPileta(true).build();
	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);

	assertEquals(1, inmuebles.size());
	assertTrue(inmuebles.get(0).getConPileta() == true);

	criterio = new CriterioBusqInmueble.Builder().setConPileta(false).build();
	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);

	assertEquals(1, inmuebles.size());
	assertTrue(inmuebles.get(0).getConPileta() == false);
    }

    @Ignore
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

	Inmueble inmuebleEnAlquileryVenta = unInmuebleNoPublicado();
	inmuebleEnAlquileryVenta.setEstadoInmueble(EstadoInmueble.EnAlquilerYVenta);

	dao.create(inmuebleAlquilado);
	dao.create(inmuebleEnAlquiler);
	dao.create(inmuebleEnAlquileryVenta);
	dao.create(inmuebleEnVenta);
	dao.create(inmuebleNoPublicado);
	dao.create(inmuebleVendido);

	inmuebles = dao.readAll();
	EstadoInmueble[] estados = EstadoInmueble.values();
	assertEquals(inmuebles.size(), estados.length);

	for (int i = 0; i < inmuebles.size(); i++) {
	    assertEquals(estados[i], inmuebles.get(i).getEstadoInmueble());
	}

	for (int i = 0; i < estados.length; i++) {
	    criterio = new CriterioBusqInmueble.Builder().setEstadoInmueble(estados[i]).build();
	    inmuebles = dao.findInmueblesbyCaracteristicas(criterio);
	    if (estados[i] != EstadoInmueble.EnAlquilerYVenta) {
	    assertEquals(1, inmuebles.size());
	    assertEquals(inmuebles.get(0).getEstadoInmueble(), estados[i]);
	    }
	    else {
		 assertEquals(3, inmuebles.size());
		 assertTrue(inmuebles.contains(inmuebleEnAlquiler));
		 assertTrue(inmuebles.contains(inmuebleEnVenta));
		 assertTrue(inmuebles.contains(inmuebleEnAlquileryVenta));
	    }
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

	criterio = new CriterioBusqInmueble.Builder().setMinSupTotal(200).build();
	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);
	assertEquals(2, inmuebles.size());

	criterio = new CriterioBusqInmueble.Builder().setMaxSupTotal(250).build();
	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);
	assertEquals(2, inmuebles.size());

	criterio = new CriterioBusqInmueble.Builder().setMaxSupTotal(300).build();
	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);
	assertEquals(3, inmuebles.size());

	criterio = new CriterioBusqInmueble.Builder().setMinSupTotal(100).setMaxSupTotal(300).build();
	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);
	assertEquals(3, inmuebles.size());

	criterio = new CriterioBusqInmueble.Builder().setMinSupTotal(400).build();
	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);
	assertEquals(0, inmuebles.size());

	criterio = new CriterioBusqInmueble.Builder().setMaxSupTotal(99).build();
	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);
	assertEquals(0, inmuebles.size());

    }

	@Ignore
    public void findInmueblesByCriteria_InmueblesAlquilados() {
	unoNoPublicado_unoEnAlquiler_unoEnVenta().forEach(dao::create);
	criterio = new CriterioBusqInmueble.Builder().setTipoPublicacion(TipoPublicacion.Alquiler).build();
	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);
	assertEquals(1, inmuebles.size());

    }

	@Ignore
    public void findInmueblesByCriteria_InmueblesEnVenta() {
	unoNoPublicado_unoEnAlquiler_unoEnVenta().forEach(dao::create);
	criterio = new CriterioBusqInmueble.Builder().setTipoPublicacion(TipoPublicacion.Venta).build();
	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);
	assertEquals(1, inmuebles.size());

    }

	@Ignore
    public void findInmueblesByCriteria_TodosLosInmuebles() {
	unoNoPublicado_unoEnAlquiler_unoEnVenta().forEach(dao::create);
	criterio = new CriterioBusqInmueble.Builder().build();
	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);
	assertEquals(3, inmuebles.size());

    }

	@Ignore
    public void findInmueblesByCriteria_EnVentaOAlquiler() {
	unoNoPublicado_unoEnAlquiler_unoEnVenta().forEach(dao::create);
	criterio = new CriterioBusqInmueble.Builder().setEstadoInmueble(EstadoInmueble.EnAlquilerYVenta)
		.build();
	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);
	assertEquals(2, inmuebles.size());

    }

	@Ignore
    public void findInmueblesByCriteria_InmueblesEnAlquilerAndValorCuotaMayorIgualA200() {
	crearInmueblesEnAlquilerEnDolaresConValorCuota100xN(3);

	criterio = new CriterioBusqInmueble.Builder().setTipoPublicacion(TipoPublicacion.Alquiler)
		.setMinPrecio(BigDecimal.valueOf(200)).build();
	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);
	assertEquals(2, inmuebles.size());
    }

	@Ignore
    public void findInmueblesByCriteria_InmueblesEnAlquilerAndValorCuotaMenorIgualA100() {

	crearInmueblesEnAlquilerEnDolaresConValorCuota100xN(3);

	criterio = new CriterioBusqInmueble.Builder().setTipoPublicacion(TipoPublicacion.Alquiler)
		.setMaxPrecio(BigDecimal.valueOf(100)).build();
	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);
	assertEquals(1, inmuebles.size());
    }

	@Ignore
    public void findInmueblesByCriteria_InmueblesEnVentaAndValorCuotaMayorIgualA200() {
	crearInmueblesEnVentaEnPesosConValorCuota100xN(3);

	criterio = new CriterioBusqInmueble.Builder().setTipoPublicacion(TipoPublicacion.Venta)
		.setMinPrecio(BigDecimal.valueOf(200)).build();
	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);
	assertEquals(2, inmuebles.size());
    }

	@Ignore
    public void findInmueblesByCriteria_InmueblesEnVentaAndValorCuotaMenorIgualA100() {

	crearInmueblesEnVentaEnPesosConValorCuota100xN(3);

	criterio = new CriterioBusqInmueble.Builder().setTipoPublicacion(TipoPublicacion.Venta)
		.setMaxPrecio(BigDecimal.valueOf(100)).build();
	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);
	assertEquals(1, inmuebles.size());
    }

	
	@Ignore
    public void findInmueblesByCriteria_InmueblesEnDolares() {
	crearInmueblesEnAlquilerEnDolaresConValorCuota100xN(2);
	criterio = new CriterioBusqInmueble.Builder().setTipoMoneda(TipoMoneda.Dolares).build();

	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);
	assertEquals(2, inmuebles.size());

	crearInmueblesEnVentaEnPesosConValorCuota100xN(1);
	crearInmueblesEnAlquilerEnDolaresConValorCuota100xN(4);

	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);
	assertEquals(6, inmuebles.size());

    }

	@Ignore
    public void findInmueblesByCriteria_minCantAmbientes() {
	Inmueble inmueble = unInmuebleCon5Ambientes();
	Inmueble otroInmueble = unInmuebleCon8Ambientes();
	Inmueble otroInmueble2 = unInmuebleEnVentaEnPesos();

	dao.create(inmueble);
	dao.create(otroInmueble);
	dao.create(otroInmueble2);

	criterio = new CriterioBusqInmueble.Builder().setMinCantAmbientes(5).build();

	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);
	assertEquals(2, inmuebles.size());
    }

    @Ignore
    public void findInmueblesByCriteria_maxCantAmbientes() {
	Inmueble inmueble = unInmuebleCon5Ambientes();
	Inmueble otroInmueble = unInmuebleCon8Ambientes();
	Inmueble otroInmueble2 = unInmuebleEnVentaEnPesos();

	dao.create(inmueble);
	dao.create(otroInmueble);
	dao.create(otroInmueble2);

	criterio = new CriterioBusqInmueble.Builder().setMaxCantAmbientes(4).build();

	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);
	assertEquals(1, inmuebles.size());
    }

	@Ignore
    public void findInmueblesByCriteria_minCantCocheras() {
	Inmueble inmueble1 = unInmuebleEnVentaEnPesos();
	Inmueble inmueble2 = unInmuebleEnVentaEnPesos();
	Inmueble inmueble3 = unInmuebleEnVentaEnPesos();
	Inmueble inmueble4 = unInmuebleEnVentaEnPesos();
	Inmueble inmueble5 = unInmuebleEnVentaEnPesos();

	inmueble1.setCantidadCocheras(4);
	inmueble2.setCantidadCocheras(4);
	inmueble3.setCantidadCocheras(5);

	dao.create(inmueble1);
	dao.create(inmueble2);
	dao.create(inmueble3);
	dao.create(inmueble4);
	dao.create(inmueble5);

	criterio = new CriterioBusqInmueble.Builder().setMinCantCocheras(4).build();

	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);

	assertEquals(3, inmuebles.size());

	inmueble1.setCantidadCocheras(1);
	inmueble2.setCantidadCocheras(3);
	inmueble3.setCantidadCocheras(4);
	inmueble4.setCantidadCocheras(5);
	inmueble5.setCantidadCocheras(10);

	dao.saveOrUpdate(inmueble1);
	dao.saveOrUpdate(inmueble2);
	dao.saveOrUpdate(inmueble3);
	dao.saveOrUpdate(inmueble4);
	dao.saveOrUpdate(inmueble5);

	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);

	assertEquals(3, inmuebles.size());

    }

	@Ignore
    public void findInmueblesByCriteria_maxCantCocheras() {
	Inmueble inmueble1 = unInmuebleEnVentaEnPesos();
	Inmueble inmueble2 = unInmuebleEnVentaEnPesos();
	Inmueble inmueble3 = unInmuebleEnVentaEnPesos();
	Inmueble inmueble4 = unInmuebleEnVentaEnPesos();
	Inmueble inmueble5 = unInmuebleEnVentaEnPesos();

	inmueble1.setCantidadCocheras(3);
	inmueble2.setCantidadCocheras(4);
	inmueble3.setCantidadCocheras(5);

	dao.create(inmueble1);
	dao.create(inmueble2);
	dao.create(inmueble3);
	dao.create(inmueble4);
	dao.create(inmueble5);

	criterio = new CriterioBusqInmueble.Builder().setMaxCantCocheras(4).build();

	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);

	assertEquals(4, inmuebles.size());

	inmueble1.setCantidadCocheras(1);
	inmueble2.setCantidadCocheras(3);
	inmueble3.setCantidadCocheras(4);
	inmueble4.setCantidadCocheras(5);
	inmueble5.setCantidadCocheras(10);

	dao.saveOrUpdate(inmueble1);
	dao.saveOrUpdate(inmueble2);
	dao.saveOrUpdate(inmueble3);
	dao.saveOrUpdate(inmueble4);
	dao.saveOrUpdate(inmueble5);

	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);

	assertEquals(3, inmuebles.size());

    }

	@Ignore
    public void findInmueblesByCriteria_minCantDormitorios() {
	Inmueble inmueble1 = unInmuebleEnVentaEnPesos();
	Inmueble inmueble2 = unInmuebleEnVentaEnPesos();
	Inmueble inmueble3 = unInmuebleEnVentaEnPesos();
	Inmueble inmueble4 = unInmuebleEnVentaEnPesos();
	Inmueble inmueble5 = unInmuebleEnVentaEnPesos();

	inmueble1.setCantidadDormitorios(2);
	inmueble2.setCantidadDormitorios(3);
	inmueble3.setCantidadDormitorios(4);

	dao.create(inmueble1);
	dao.create(inmueble2);
	dao.create(inmueble3);
	dao.create(inmueble4);
	dao.create(inmueble5);

	criterio = new CriterioBusqInmueble.Builder().setMinCantDormitorios(3).build();

	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);

	assertEquals(2, inmuebles.size());

	inmueble1.setCantidadDormitorios(1);
	inmueble2.setCantidadDormitorios(3);
	inmueble3.setCantidadDormitorios(4);
	inmueble4.setCantidadDormitorios(5);
	inmueble5.setCantidadDormitorios(10);

	dao.saveOrUpdate(inmueble1);
	dao.saveOrUpdate(inmueble2);
	dao.saveOrUpdate(inmueble3);
	dao.saveOrUpdate(inmueble4);
	dao.saveOrUpdate(inmueble5);

	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);

	assertEquals(4, inmuebles.size());

    }

	@Ignore
    public void findInmueblesByCriteria_maxCantDormitorios() {
	Inmueble inmueble1 = unInmuebleEnVentaEnPesos();
	Inmueble inmueble2 = unInmuebleEnVentaEnPesos();
	Inmueble inmueble3 = unInmuebleEnVentaEnPesos();
	Inmueble inmueble4 = unInmuebleEnVentaEnPesos();
	Inmueble inmueble5 = unInmuebleEnVentaEnPesos();

	inmueble1.setCantidadDormitorios(2);
	inmueble2.setCantidadDormitorios(3);
	inmueble3.setCantidadDormitorios(4);

	dao.create(inmueble1);
	dao.create(inmueble2);
	dao.create(inmueble3);
	dao.create(inmueble4);
	dao.create(inmueble5);

	criterio = new CriterioBusqInmueble.Builder().setMaxCantDormitorios(3).build();

	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);

	assertEquals(4, inmuebles.size());

	inmueble1.setCantidadDormitorios(1);
	inmueble2.setCantidadDormitorios(3);
	inmueble3.setCantidadDormitorios(4);
	inmueble4.setCantidadDormitorios(5);
	inmueble5.setCantidadDormitorios(10);

	dao.saveOrUpdate(inmueble1);
	dao.saveOrUpdate(inmueble2);
	dao.saveOrUpdate(inmueble3);
	dao.saveOrUpdate(inmueble4);
	dao.saveOrUpdate(inmueble5);

	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);

	assertEquals(2, inmuebles.size());

    }

    @Test
    public void sonIguales() {
	Inmueble inmueble = unInmuebleEnVentaEnPesos();
	Inmueble inmueble2 = unInmuebleEnVentaEnPesos();
	Inmueble inmueble3 = unInmuebleEnVentaEnPesos();

	assertTrue(inmueble.isSame(inmueble2));
	assertTrue(inmueble2.isSame(inmueble3));

	inmueble2.setCantidadAmbientes(10);
	inmueble3.setCantidadCocheras(11);

	assertFalse(inmueble.isSame(inmueble2));
	assertFalse(inmueble.isSame(inmueble3));
    }

	@Ignore
    public void findInmueblesByCriteria_minCSupCubierta() {
	Inmueble inmueble1 = unInmuebleEnVentaEnPesos();
	Inmueble inmueble2 = unInmuebleEnVentaEnPesos();
	Inmueble inmueble3 = unInmuebleEnVentaEnPesos();
	Inmueble inmueble4 = unInmuebleEnVentaEnPesos();
	Inmueble inmueble5 = unInmuebleEnVentaEnPesos();

	inmueble1.setSuperficieCubierta(299);
	inmueble2.setSuperficieCubierta(300);
	inmueble3.setSuperficieCubierta(301);

	dao.create(inmueble1);
	dao.create(inmueble2);
	dao.create(inmueble3);
	dao.create(inmueble4);
	dao.create(inmueble5);

	criterio = new CriterioBusqInmueble.Builder().setMinSupCubierta(300).build();

	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);

	assertEquals(2, inmuebles.size());

	inmueble1.setSuperficieCubierta(400);
	inmueble2.setSuperficieCubierta(355);
	inmueble3.setSuperficieCubierta(300);
	inmueble4.setSuperficieCubierta(299);
	inmueble5.setSuperficieCubierta(100);

	dao.saveOrUpdate(inmueble1);
	dao.saveOrUpdate(inmueble2);
	dao.saveOrUpdate(inmueble3);
	dao.saveOrUpdate(inmueble4);
	dao.saveOrUpdate(inmueble5);

	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);

	assertEquals(3, inmuebles.size());

    }

	@Ignore
    public void findInmueblesByCriteria_maxCSupCubierta() {
	Inmueble inmueble1 = unInmuebleEnVentaEnPesos();
	Inmueble inmueble2 = unInmuebleEnVentaEnPesos();
	Inmueble inmueble3 = unInmuebleEnVentaEnPesos();
	Inmueble inmueble4 = unInmuebleEnVentaEnPesos();
	Inmueble inmueble5 = unInmuebleEnVentaEnPesos();

	inmueble1.setSuperficieCubierta(301);
	inmueble2.setSuperficieCubierta(300);
	inmueble3.setSuperficieCubierta(299);

	dao.create(inmueble1);
	dao.create(inmueble2);
	dao.create(inmueble3);
	dao.create(inmueble4);
	dao.create(inmueble5);

	criterio = new CriterioBusqInmueble.Builder().setMaxSupCubierta(300).build();

	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);

	assertEquals(4, inmuebles.size());

	inmueble1.setSuperficieCubierta(400);
	inmueble2.setSuperficieCubierta(355);
	inmueble3.setSuperficieCubierta(300);
	inmueble4.setSuperficieCubierta(299);
	inmueble5.setSuperficieCubierta(400);

	dao.saveOrUpdate(inmueble1);
	dao.saveOrUpdate(inmueble2);
	dao.saveOrUpdate(inmueble3);
	dao.saveOrUpdate(inmueble4);
	dao.saveOrUpdate(inmueble5);

	inmuebles = dao.findInmueblesbyCaracteristicas(criterio);

	assertEquals(2, inmuebles.size());

    }

    @Test
    public void testPublicaciones() {
	Inmueble i = unInmuebleNoPublicado();
	i.setEstadoInmueble(EstadoInmueble.EnVenta);
	i.addPublicacion(new PublicacionVenta.Builder()
		.setFechaPublicacion(LocalDate.now())
		.setInmueble(i)
		.setPrecio(BigDecimal.valueOf(200))
		.build());
	dao.create(i);
	inmuebles = dao.readAll();
	assertEquals(1, inmuebles.get(0).getPublicaciones().size());
    }

    @Test
    public void testRelacionPropietarios() {
	Inmueble i = unInmuebleNoPublicado();
	dao.saveOrUpdate(i);
	Persona p = new Persona.Builder().setApellido("ape")
		.setDNI("123")
		.setinfoAdicional("Info adicional")
		.setMail("a@b.com")
		.setNombre("nom")
		.setTelefono("123456")
		.setTelefono2("321")
		.build();
	Propietario propietario = new Propietario.Builder()
		// .addInmueble(i)
		.setEstadoRegistro(EstadoRegistro.ACTIVO)
		.setPersona(p)
		.build();
	p.addRol(propietario);
	DAOPersonaImpl daop = new DAOPersonaImpl();
	// Debe existir primero una persona en la bd a la que asignarle el rol de
	// propietario.
	daop.saveOrUpdate(p);
	// i.setPropietario(propietario);
	propietario.addInmueble(i);
	dao.saveOrUpdate(i);
	inmuebles = dao.readAll();
	assertEquals(propietario, i.getPropietario());

	propietario.setPersona(null);
	// Hay que hacer esto para desvincular al propietario de la persona y asi poder
	// borrarlo cuando se borra el inmueble.
	daop.saveOrUpdate(p);

    }

    // @Test
    public void testGeneradorDeDatos() {
	GeneradorDeDatos.generarDatos(10, ProvinciaService.modoLecturaJson.local);
	inmuebles = dao.readAll();
	assertEquals(10, inmuebles.size());
	DAOPersonaImpl daop = new DAOPersonaImpl();
	for (Inmueble i : inmuebles) {
	    Persona p = i.getPropietario().getPersona();
	    i.getPropietario().setPersona(null);
	    daop.saveOrUpdate(p);
	}

    }

    private void crearInmueblesEnAlquilerEnDolaresConValorCuota100xN(int cantidadDeInmuebles) {
	for (int x = 1; x <= cantidadDeInmuebles; x++) {
	    Inmueble i = unInmuebleEnAlquilerEnDolares();
	    for (Publicacion o : i.getPublicaciones()) {
		if (o instanceof PublicacionAlquiler) {
		    PublicacionAlquiler oa = ((PublicacionAlquiler) o);
		    oa.setPrecio(BigDecimal.valueOf(100 * x));
		}
	    }
	    dao.create(i);
	}
    }

    private void crearInmueblesEnVentaEnPesosConValorCuota100xN(int cantidadDeInmuebles) {
	for (int x = 1; x <= cantidadDeInmuebles; x++) {
	    Inmueble i = unInmuebleEnVentaEnPesos();
	    for (Publicacion o : i.getPublicaciones()) {
		if (o instanceof PublicacionVenta) {
		    PublicacionVenta ov = ((PublicacionVenta) o);
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
	inmueble.addPublicacion(new PublicacionVenta.Builder()
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
	inmueble.addPublicacion(new PublicacionAlquiler.Builder()
		.setFechaPublicacion(LocalDate.of(2017, 9, 1))
		.setMoneda(TipoMoneda.Dolares)
		.setValorCuota(BigDecimal.valueOf(1e3))
		.setInmueble(inmueble)
		.build());
	return inmueble;
    }

    private Inmueble unInmuebleCon5Ambientes() {
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
    

}
