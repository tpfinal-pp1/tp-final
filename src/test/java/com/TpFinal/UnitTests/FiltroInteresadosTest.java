package com.TpFinal.UnitTests;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Test;

import com.TpFinal.dto.inmueble.ClaseInmueble;
import com.TpFinal.dto.inmueble.Coordenada;
import com.TpFinal.dto.inmueble.CriterioBusqInmueble;
import com.TpFinal.dto.inmueble.Direccion;
import com.TpFinal.dto.inmueble.EstadoInmueble;
import com.TpFinal.dto.inmueble.Inmueble;
import com.TpFinal.dto.inmueble.TipoInmueble;
import com.TpFinal.dto.inmueble.TipoMoneda;
import com.TpFinal.dto.persona.Persona;
import com.TpFinal.dto.publicacion.PublicacionAlquiler;
import com.TpFinal.view.persona.FiltroInteresados;

public class FiltroInteresadosTest {

    @Test
    public void testAEstrenar() {
	Persona p = instancia("1");
	Inmueble i = unInmuebleEnAlquiler();
	FiltroInteresados filtro = new FiltroInteresados();

	i.setaEstrenar(true);
	p.setPrefBusqueda(new CriterioBusqInmueble.Builder()
		.build());
	filtro = new FiltroInteresados(i);
	assertTrue(filtro.getaEstrenar().test(p));
	p.getPrefBusqueda().setaEstrenar(true);
	assertTrue(filtro.getaEstrenar().test(p));
	i.setaEstrenar(false);
	filtro = new FiltroInteresados(i);
	assertFalse(filtro.getaEstrenar().test(p));
	p.getPrefBusqueda().setaEstrenar(false);
	assertTrue(filtro.getaEstrenar().test(p));
    }

    @Test
    public void testAireAcond() {
	Persona p = instancia("1");
	Inmueble i = unInmuebleEnAlquiler();
	FiltroInteresados filtro = new FiltroInteresados();

	i.setConAireAcondicionado(true);
	p.setPrefBusqueda(new CriterioBusqInmueble.Builder()
		.setConAireAcondicionado(true)
		.build());
	filtro = new FiltroInteresados(i);
	assertTrue(filtro.getConAireAcondicionado().test(p));
	i.setConAireAcondicionado(false);
	filtro = new FiltroInteresados(i);
	assertFalse(filtro.getConAireAcondicionado().test(p));
    }

    @Test
    public void testClaseInmueble() {
	Persona p = instancia("1");
	Inmueble i = unInmuebleEnAlquiler();
	FiltroInteresados filtro = new FiltroInteresados();

	i.setClaseInmueble(ClaseInmueble.Casa);
	p.setPrefBusqueda(new CriterioBusqInmueble.Builder()
		.build());
	filtro = new FiltroInteresados(i);
	assertEquals(true, filtro.getClasesDeInmueble().test(p));
	p.getPrefBusqueda().getClasesDeInmueble().add(ClaseInmueble.Casa);
	assertEquals(true, filtro.getClasesDeInmueble().test(p));
	i.setClaseInmueble(ClaseInmueble.Campo);
	filtro = new FiltroInteresados(i);
	assertEquals(false, filtro.getClasesDeInmueble().test(p));
	p.getPrefBusqueda().getClasesDeInmueble().add(ClaseInmueble.Campo);
	assertEquals(true, filtro.getClasesDeInmueble().test(p));
    }

    @Test
    public void testFiltroConJardin() {
	Persona p = instancia("1");
	Inmueble i = unInmuebleEnAlquiler();
	FiltroInteresados filtro = new FiltroInteresados();

	i.setConJardin(true);
	p.setPrefBusqueda(new CriterioBusqInmueble.Builder()
		.build());
	filtro = new FiltroInteresados(i);
	assertEquals(true, filtro.getConJardin().test(p));
	p.getPrefBusqueda().setConJardin(true);
	assertEquals(true, filtro.getConJardin().test(p));
	p.getPrefBusqueda().setConJardin(false);
	assertEquals(false, filtro.getConJardin().test(p));

    }

    @Test
    public void testFiltroConPileta() {
	Persona p = instancia("1");
	Inmueble i = unInmuebleEnAlquiler();
	FiltroInteresados filtro = new FiltroInteresados();

	i.setConPileta(true);
	p.setPrefBusqueda(new CriterioBusqInmueble.Builder()
		.build());
	filtro = new FiltroInteresados(i);
	assertEquals(true, filtro.getConPileta().test(p));
	p.getPrefBusqueda().setConPileta(true);
	assertEquals(true, filtro.getConPileta().test(p));
	p.getPrefBusqueda().setConPileta(false);
	assertEquals(false, filtro.getConPileta().test(p));

    }

    @Test
    public void testFiltroConParrilla() {
	Persona p = instancia("1");
	Inmueble i = unInmuebleEnAlquiler();
	FiltroInteresados filtro = new FiltroInteresados();

	i.setConParrilla(true);
	p.setPrefBusqueda(new CriterioBusqInmueble.Builder()
		.build());
	filtro = new FiltroInteresados(i);
	assertEquals(true, filtro.getConParrilla().test(p));
	p.getPrefBusqueda().setConParrilla(true);
	assertEquals(true, filtro.getConParrilla().test(p));
	p.getPrefBusqueda().setConParrilla(false);
	assertEquals(false, filtro.getConParrilla().test(p));
    }

    @Test
    public void testFiltroEstadoInmueble() {
	Persona p = instancia("1");
	Inmueble i = unInmuebleEnAlquiler();
	FiltroInteresados filtro = new FiltroInteresados();

	i.setEstadoInmueble(EstadoInmueble.EnAlquilerYVenta);
	p.setPrefBusqueda(new CriterioBusqInmueble.Builder()
		.build());
	filtro = new FiltroInteresados(i);
	assertEquals(true, filtro.getEstadoInmueble().test(p));
	p.getPrefBusqueda().setEstadoInmueble(EstadoInmueble.EnAlquiler);
	assertEquals(true, filtro.getEstadoInmueble().test(p));
	p.getPrefBusqueda().setEstadoInmueble(EstadoInmueble.EnVenta);
	assertEquals(true, filtro.getEstadoInmueble().test(p));

	p.setPrefBusqueda(new CriterioBusqInmueble.Builder()
		.build());
	i.setEstadoInmueble(EstadoInmueble.EnAlquiler);
	filtro = new FiltroInteresados(i);
	assertEquals(true, filtro.getEstadoInmueble().test(p));
	p.getPrefBusqueda().setEstadoInmueble(EstadoInmueble.EnAlquiler);
	assertEquals(true, filtro.getEstadoInmueble().test(p));
	p.getPrefBusqueda().setEstadoInmueble(EstadoInmueble.EnVenta);
	assertEquals(false, filtro.getEstadoInmueble().test(p));

	p.setPrefBusqueda(new CriterioBusqInmueble.Builder()
		.build());
	i.setEstadoInmueble(EstadoInmueble.EnVenta);
	filtro = new FiltroInteresados(i);
	assertEquals(true, filtro.getEstadoInmueble().test(p));
	p.getPrefBusqueda().setEstadoInmueble(EstadoInmueble.EnAlquiler);
	assertEquals(false, filtro.getEstadoInmueble().test(p));
	p.getPrefBusqueda().setEstadoInmueble(EstadoInmueble.EnVenta);
	assertEquals(true, filtro.getEstadoInmueble().test(p));
    }

    @Test
    public void testFiltroLocalidad() {
	Persona p = instancia("1");
	Inmueble i = unInmuebleEnAlquiler();
	FiltroInteresados filtro = new FiltroInteresados();
	i.getDireccion().setLocalidad("Localidad");
	p.setPrefBusqueda(new CriterioBusqInmueble.Builder()
		.build());
	filtro = new FiltroInteresados(i);
	assertEquals(true, filtro.getLocalidad().test(p));
	p.getPrefBusqueda().setLocalidad("localidad");
	assertEquals(true, filtro.getLocalidad().test(p));
	p.getPrefBusqueda().setLocalidad("Otra localidad");
	assertEquals(false, filtro.getLocalidad().test(p));
    }
    
    @Test
    public void testFiltroProvincia() {
	Persona p = instancia("1");
	Inmueble i = unInmuebleEnAlquiler();
	FiltroInteresados filtro = new FiltroInteresados();
	i.getDireccion().setProvincia("Buenos Aires");
	p.setPrefBusqueda(new CriterioBusqInmueble.Builder()
		.build());
	filtro = new FiltroInteresados(i);
	assertEquals(true, filtro.getProvincia().test(p));
	p.getPrefBusqueda().setProvincia("Buenos Aires");
	assertEquals(true, filtro.getProvincia().test(p));
	p.getPrefBusqueda().setProvincia("Otra Provincia");
	assertEquals(false, filtro.getProvincia().test(p));
    }

    public void testAmbientes() {
	Persona p = instancia("1");
	Inmueble i = unInmuebleEnAlquiler();
	FiltroInteresados filtro = new FiltroInteresados();

	i.setCantidadAmbientes(10);
	p.setPrefBusqueda(new CriterioBusqInmueble.Builder()
		.setMinCantAmbientes(5)
		.setMaxCantAmbientes(10)
		.build());
	filtro = new FiltroInteresados(i);
	assertTrue(filtro.getMinAmbientes().test(p));
	assertTrue(filtro.getMaxAmbientes().test(p));
	i.setCantidadAmbientes(4);
	filtro = new FiltroInteresados(i);
	assertFalse(filtro.getMinAmbientes().test(p));
	assertTrue(filtro.getMaxAmbientes().test(p));
	i.setCantidadAmbientes(11);
	filtro = new FiltroInteresados(i);
	assertFalse(filtro.getMinAmbientes().test(p));
	assertFalse(filtro.getMaxAmbientes().test(p));
    }
    
    public void testCocheras() {
	Persona p = instancia("1");
	Inmueble i = unInmuebleEnAlquiler();
	FiltroInteresados filtro = new FiltroInteresados();

	i.setCantidadCocheras(10);
	p.setPrefBusqueda(new CriterioBusqInmueble.Builder()
		.setMinCantCocheras(5)
		.setMaxCantCocheras(10)
		.build());
	filtro = new FiltroInteresados(i);
	assertTrue(filtro.getMinCocheras().test(p));
	assertTrue(filtro.getMaxCocheras().test(p));
	i.setCantidadCocheras(4);
	filtro = new FiltroInteresados(i);
	assertFalse(filtro.getMinCocheras().test(p));
	assertTrue(filtro.getMaxCocheras().test(p));
	i.setCantidadCocheras(11);
	filtro = new FiltroInteresados(i);
	assertFalse(filtro.getMinCocheras().test(p));
	assertFalse(filtro.getMaxCocheras().test(p));
    }
    
    public void testDormitorios() {
	Persona p = instancia("1");
	Inmueble i = unInmuebleEnAlquiler();
	FiltroInteresados filtro = new FiltroInteresados();

	i.setCantidadDormitorios(10);
	p.setPrefBusqueda(new CriterioBusqInmueble.Builder()
		.setMinCantDormitorios(5)
		.setMaxCantDormitorios(10)
		.build());
	filtro = new FiltroInteresados(i);
	assertTrue(filtro.getMinDormitorios().test(p));
	assertTrue(filtro.getMaxDormitorios().test(p));
	i.setCantidadDormitorios(4);
	filtro = new FiltroInteresados(i);
	assertFalse(filtro.getMinDormitorios().test(p));
	assertTrue(filtro.getMaxDormitorios().test(p));
	i.setCantidadDormitorios(11);
	filtro = new FiltroInteresados(i);
	assertFalse(filtro.getMinDormitorios().test(p));
	assertFalse(filtro.getMaxDormitorios().test(p));
    }
    
    public void testSupCubierta() {
	Persona p = instancia("1");
	Inmueble i = unInmuebleEnAlquiler();
	FiltroInteresados filtro = new FiltroInteresados();

	i.setSuperficieCubierta(10);
	p.setPrefBusqueda(new CriterioBusqInmueble.Builder()
		.setMinSupCubierta(5)
		.setMaxSupCubierta(10)
		.build());
	filtro = new FiltroInteresados(i);
	assertTrue(filtro.getMinSupCubierta().test(p));
	assertTrue(filtro.getMaxSupCubierta().test(p));
	i.setSuperficieCubierta(4);
	filtro = new FiltroInteresados(i);
	assertFalse(filtro.getMinSupCubierta().test(p));
	assertTrue(filtro.getMaxSupCubierta().test(p));
	i.setSuperficieCubierta(11);
	filtro = new FiltroInteresados(i);
	assertFalse(filtro.getMinSupCubierta().test(p));
	assertFalse(filtro.getMaxSupCubierta().test(p));
    }
    
    public void testSupTotal() {
	Persona p = instancia("1");
	Inmueble i = unInmuebleEnAlquiler();
	FiltroInteresados filtro = new FiltroInteresados();

	i.setSuperficieTotal(10);
	filtro = new FiltroInteresados(i);
	assertTrue(filtro.getMinSupTotal().test(p));
	assertTrue(filtro.getMaxSupTotal().test(p));
	p.setPrefBusqueda(new CriterioBusqInmueble.Builder()
		.setMinSupTotal(5)
		.setMaxSupTotal(10)
		.build());
	filtro = new FiltroInteresados(i);
	assertTrue(filtro.getMinSupTotal().test(p));
	assertTrue(filtro.getMaxSupTotal().test(p));
	i.setSuperficieTotal(4);
	filtro = new FiltroInteresados(i);
	assertFalse(filtro.getMinSupTotal().test(p));
	assertTrue(filtro.getMaxSupTotal().test(p));
	i.setSuperficieTotal(11);
	filtro = new FiltroInteresados(i);
	assertFalse(filtro.getMinSupTotal().test(p));
	assertFalse(filtro.getMaxSupTotal().test(p));
    }
    
    public void testPublicaciones() {
	Persona p = instancia("1");
	Inmueble i = unInmuebleEnAlquiler();
	FiltroInteresados filtro = new FiltroInteresados();

	p.setPrefBusqueda(new CriterioBusqInmueble.Builder()
		.setEstadoInmueble(EstadoInmueble.EnAlquilerYVenta)
		.build());
	filtro = new FiltroInteresados(i);
	assertTrue(filtro.getPublicaciones().test(p));
	p.getPrefBusqueda().setMinPrecio(BigDecimal.valueOf(98));
	p.getPrefBusqueda().setMaxPrecio(BigDecimal.valueOf(99));
	assertFalse(filtro.getPublicaciones().test(p));
	p.getPrefBusqueda().setMinPrecio(BigDecimal.valueOf(99));
	p.getPrefBusqueda().setMaxPrecio(BigDecimal.valueOf(100));
	assertTrue(filtro.getPublicaciones().test(p));
	p.getPrefBusqueda().setMinPrecio(BigDecimal.valueOf(99));
	p.getPrefBusqueda().setMaxPrecio(BigDecimal.valueOf(101));
	assertTrue(filtro.getPublicaciones().test(p));
	p.getPrefBusqueda().setMinPrecio(BigDecimal.valueOf(100));
	p.getPrefBusqueda().setMaxPrecio(BigDecimal.valueOf(101));
	assertTrue(filtro.getPublicaciones().test(p));
	p.getPrefBusqueda().setMinPrecio(BigDecimal.valueOf(101));
	p.getPrefBusqueda().setMaxPrecio(BigDecimal.valueOf(102));
	assertFalse(filtro.getPublicaciones().test(p));	
    }
    
    @Test
    public void testTipoInmueble() {
	Persona p = instancia("1");
	Inmueble i = unInmuebleEnAlquiler();
	FiltroInteresados filtro = new FiltroInteresados();
	i.setTipoInmueble(TipoInmueble.Comercial);
	p.setPrefBusqueda(new CriterioBusqInmueble.Builder()
		.build());
	filtro = new FiltroInteresados(i);
	assertEquals(true, filtro.getTipoInmueble().test(p));
	p.getPrefBusqueda().setTipoInmueble(TipoInmueble.Comercial);
	assertEquals(true, filtro.getTipoInmueble().test(p));
	p.getPrefBusqueda().setTipoInmueble(TipoInmueble.Vivienda);
	assertEquals(false, filtro.getTipoInmueble().test(p));
    }

    private Inmueble unInmuebleEnAlquiler() {
	Inmueble inm = new Inmueble.Builder()
		.setaEstrenar(true)
		.setCantidadAmbientes(10)
		.setCantidadCocheras(10)
		.setCantidadDormitorios(10)
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
		.setEstadoInmueble(EstadoInmueble.EnAlquiler)
		.setSuperficieCubierta(200)
		.setSuperficieTotal(400)
		.setTipoInmueble(TipoInmueble.Vivienda)
		.build();
	PublicacionAlquiler pubA = new PublicacionAlquiler.Builder()
		.setFechaPublicacion(LocalDate.now())
		.setInmueble(inm)
		.setMoneda(TipoMoneda.Pesos)
		.setValorCuota(BigDecimal.valueOf(100))
		.build();
	inm.addPublicacion(pubA);

	return inm;
    }

    public Persona instancia(String numero) {
	return new Persona.Builder()
		.setNombre("nombre " + numero)
		.setApellido("apellido " + numero)
		.setMail("mail " + numero)
		.setTelefono("telefono " + numero)
		.setTelefono("telefono " + numero)
		.setTelefono2("telefono2 " + numero)
		.setDNI("Dni" + numero)
		.setinfoAdicional("Info Adicional" + numero)
		.build();
    }

}
