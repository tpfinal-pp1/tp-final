package com.TpFinal.Integracion.services;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.time.LocalDate;
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
import com.TpFinal.data.dao.DAOPublicacionImpl;
import com.TpFinal.data.dao.interfaces.DAOInmueble;
import com.TpFinal.data.dao.interfaces.DAOPersona;
import com.TpFinal.data.dao.interfaces.DAOPublicacion;
import com.TpFinal.UnitTests.dto.inmueble.ClaseInmueble;
import com.TpFinal.UnitTests.dto.inmueble.Coordenada;
import com.TpFinal.UnitTests.dto.inmueble.Direccion;
import com.TpFinal.UnitTests.dto.inmueble.EstadoInmueble;
import com.TpFinal.UnitTests.dto.inmueble.Inmueble;
import com.TpFinal.UnitTests.dto.inmueble.TipoInmueble;
import com.TpFinal.UnitTests.dto.inmueble.TipoMoneda;
import com.TpFinal.UnitTests.dto.persona.Persona;
import com.TpFinal.UnitTests.dto.persona.Propietario;
import com.TpFinal.UnitTests.dto.publicacion.PublicacionAlquiler;
import com.TpFinal.UnitTests.dto.publicacion.PublicacionVenta;
import com.TpFinal.exceptions.services.PublicacionServiceException;

public class PublicacionServiceIT {

    InmuebleService serviceInm;
    List<Inmueble> inmuebles = new ArrayList<>();
    List<Persona> personas = new ArrayList<>();
    DAOPersona daoPer;
    PublicacionService servicePub;
    DAOInmueble daoInm;
    DAOPublicacion daoPub;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
	ConexionHibernate.setTipoConexion(TipoConexion.H2Test);
    }

    @Before
    public void setUp() throws Exception {
	serviceInm = new InmuebleService();
	servicePub = new PublicacionService();
	daoPub = new DAOPublicacionImpl();
	daoPub.readAll().forEach(p -> daoPub.delete(p));
	daoInm = new DAOInmuebleImpl();
	daoInm.readAll().forEach(i -> daoInm.delete(i));
	daoPer = new DAOPersonaImpl();
	daoPer.readAll().forEach(p -> daoPer.delete(p));
	inmuebles.clear();
    }

    @After
    public void tearDown() throws Exception {
	daoPub.readAll().forEach(p -> {
	    Inmueble i = p.getInmueble();
	    if (i != null) {
		i.removePublicacion(p);
		daoInm.saveOrUpdate(i);
		p.setInmueble(null);
		daoPub.saveOrUpdate(p);
	    }
	    daoPub.delete(p);
	});
    }

    @Test
    public void test() {
	Propietario pro = new Propietario();
	Persona per = instancia("1");
	per.addRol(pro);

	// guardo la persona y su rol
	daoPer.save(per);

	Inmueble in = unInmuebleNoPublicado();

	// in.setPropietario(pro);
	pro = daoPer.readAll().get(0).getPropietario();
	pro.addInmueble(in);
	assertEquals(1, pro.getInmuebles().size());
	// guardo el inmueble
	assertTrue(serviceInm.merge(in));
	in = daoInm.readAll().get(0);
	PublicacionVenta pv = instanciaVenta(in);
	PublicacionAlquiler pa = instanciaAlquiler(in);
	pv.setInmueble(in);
	pa.setInmueble(in);

	in.addPublicacion(pv);
	in.addPublicacion(pa);

	try {
	    servicePub.save(pa);
	} catch (PublicacionServiceException e) {

	}

	try {
	    servicePub.save(pv);
	} catch (PublicacionServiceException e) {

	}

	pa = (PublicacionAlquiler) servicePub.readAll().get(0);
	pv = (PublicacionVenta) servicePub.readAll().get(1);

	pa.setInmueble(null);
	pv.setInmueble(null);

	try {
	    servicePub.save(pa);
	} catch (PublicacionServiceException e) {
	    assertTrue(e instanceof PublicacionServiceException);
	}

	try {
	    servicePub.save(pv);
	} catch (PublicacionServiceException e) {
	    assertTrue(e instanceof PublicacionServiceException);
	}

    }

    private PublicacionVenta instanciaVenta(Inmueble i) {
	return new PublicacionVenta.Builder()
		.setFechaPublicacion(LocalDate.of(2017, 05, 12))
		.setMoneda(TipoMoneda.Pesos)
		.setPrecio(new BigDecimal("100.00"))
		.setInmueble(i)
		.build();
    }

    private PublicacionAlquiler instanciaAlquiler(Inmueble i) {
	return new PublicacionAlquiler.Builder()
		.setFechaPublicacion(LocalDate.of(2017, 05, 12))
		.setMoneda(TipoMoneda.Dolares)
		.setValorCuota(new BigDecimal("100.00"))
		.setInmueble(i)
		.build();
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
