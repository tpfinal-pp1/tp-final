
package com.TpFinal.utils;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import com.TpFinal.data.dao.DAOContratoImpl;
import com.TpFinal.data.dao.DAOInmuebleImpl;
import com.TpFinal.data.dao.DAOPublicacionImpl;
import com.TpFinal.dto.EstadoRegistro;
import com.TpFinal.dto.Localidad;
import com.TpFinal.dto.Provincia;
import com.TpFinal.dto.contrato.ContratoAlquiler;
import com.TpFinal.dto.contrato.ContratoDuracion;
import com.TpFinal.dto.contrato.ContratoVenta;
import com.TpFinal.dto.contrato.EstadoContrato;
import com.TpFinal.dto.contrato.TipoInteres;
import com.TpFinal.dto.inmueble.*;
import com.TpFinal.dto.persona.Inquilino;
import com.TpFinal.dto.persona.Persona;
import com.TpFinal.dto.persona.Propietario;
import com.TpFinal.dto.publicacion.PublicacionAlquiler;
import com.TpFinal.dto.publicacion.PublicacionVenta;
import com.TpFinal.services.CobroService;
import com.TpFinal.services.ContratoService;
import com.TpFinal.services.ProvinciaService;
import com.TpFinal.data.dao.DAOPersonaImpl;
import com.vaadin.server.VaadinRequest;
import com.vaadin.util.CurrentInstance;

public class GeneradorDeDatos {

    private static String[] nombres = { "Elliott", "Albertha", "Wilburn", "Marquita", "Merrilee", "Rosy", "Williemae",
	    "Loma", "Raymond", "Ardis", "Patrice", "Julie", "Maryjane", "Giselle", "Irena", "Hang", "Margarita",
	    "Raymundo", "Zachariah", "Stephenie", "Freddy", "Natividad", "Tequila", "Ron", "Sunni", "Verlie", "Dennis",
	    "Nathan", "Tania", "Lory", "Eladia", "Kitty", "Elyse", "Kandice", "Charlene", "Isobel", "Morris",
	    "Rosalina", "Narcisa", "Eun", "Alda", "Marilou", "Beatrice", "Marcy", "Margery",
	    "Bradley			",
	    "Raeann", "Katheryn", "Brandy", "Hulda" };

    private static Random random = new Random(100);
    private static DAOInmuebleImpl daoInm = new DAOInmuebleImpl();
    private static DAOPersonaImpl daoPer = new DAOPersonaImpl();
    private static DAOPublicacionImpl daoope = new DAOPublicacionImpl();
    private static ProvinciaService serviceProvincia;
    private static CobroService cobroService;

    private static DAOContratoImpl daoContratos = new DAOContratoImpl();

    private static ContratoService contratoService = new ContratoService();

    private static String getTelefeno() {
	return Integer.toString(
		random.nextInt(10) * 1000 + random.nextInt(10) + 100 + random.nextInt(10) * 10 + random.nextInt(10))
		+ " " + Integer.toString(random.nextInt(10) * 1000 + random.nextInt(10) + 100 + random.nextInt(10) * 10
			+ random.nextInt(10));

    }

    public static String getpathJsonServer() {
	return CurrentInstance.get(VaadinRequest.class).getService().getBaseDirectory() + File.separator
		+ "Localidades.json";
    }

    public static void generarDatos(int cantidad) {
	generarDatos(cantidad, ProvinciaService.modoLecturaJson.server);
    }

    public static void generarDatos(int cantidad, ProvinciaService.modoLecturaJson modoLectura) {

	serviceProvincia = new ProvinciaService(modoLectura);
	cobroService = new CobroService();
	

	List<Provincia> provincias = serviceProvincia.getProvincias();
	try {
	if(daoInm.readAll().size()==0) {
	    for (int i = 0; i < cantidad; i++) {

		    Provincia provincia = provinciaRandom(provincias);
		    Localidad localidad = localidadRandom(provincia);
		    Inmueble inmueble = inmuebleRandom(provincia, localidad);
		    Persona p = personaRandom();
		    daoPer.create(p);
		    Propietario prop = asignarRolPropietarioA(p);

		    PublicacionVenta pubVenta = publicacionVentaRandom(inmueble);
		    PublicacionAlquiler pubAlquiler = publicacionAlquilerRandom(inmueble);
		    Persona comprador = personaRandom();
		    Persona inquilino = personaRandom();
		    daoPer.saveOrUpdate(comprador);
		    
		    Inquilino inq = asignarRolInquilinoA(inquilino);
		    daoPer.saveOrUpdate(inquilino);

		    ContratoVenta contratoVenta =contratoVentaDe(inmueble, pubVenta, comprador);
		    contratoVenta.setEstadoContrato(EstadoContrato.values()[random.nextInt(EstadoContrato.values().length)]);
		    
		    
		    daoPer.saveOrUpdate(p);
		    daoInm.create(inmueble);
		    inmueble.setPropietario(prop);
		    daoInm.saveOrUpdate(inmueble);
		    daoope.saveOrUpdate(pubVenta);
		    daoope.saveOrUpdate(pubAlquiler);
		    inmueble.addContrato(contratoVenta);
		    ContratoAlquiler contratoAlquiler = contratoAlquilerDe(inmueble,inq);
		    contratoAlquiler.setEstadoContrato(EstadoContrato.values()[random.nextInt(EstadoContrato.values().length)]);
		    inmueble.addContrato(contratoAlquiler);
		    daoContratos.saveOrUpdate(contratoAlquiler);
		    
		    daoInm.saveOrUpdate(inmueble);
		    if (contratoAlquiler.getEstadoContrato().equals(EstadoContrato.Vigente)) {
			System.out.println("un contrato vigente");
			contratoService.addCobros(contratoAlquiler);		
			contratoService.saveOrUpdate(contratoAlquiler, null);
		    }
		    

		}
		System.out.println("Agregados\n"
		+ daoInm.readAll().size() +" inmuebles.\n"
		+ daoope.readAll().size() +" publicaciones.\n"
		+ daoContratos.readAll().size() +" contratos.\n"
		+ daoPer.readAll().size() +" personas.\n"
		+ "a la base de datos.");
	    
	}
	} catch(Exception e){
	    
	}
	
    }

    private static ContratoAlquiler contratoAlquilerDe(Inmueble inmueble, Inquilino inquilino) {
	ContratoDuracion duracion = duracionRandom();
	return new ContratoAlquiler.Builder()
		.setDiaDePago(1 + random.nextInt(28))
		.setEstadoRegistro(EstadoRegistro.ACTIVO)
		.setFechaCelebracion(fechaRandom())
		.setInmueble(inmueble)
		.setInquilinoContrato(inquilino)
		.setInteresPunitorio(new Double(1))
		.setValorIncial(cuotaRandom())
		.setDuracionContrato(duracion)
		.setIntervaloActualizacion(intervaloRandom(duracion))
		.setPorcentajeIncremento(new Double(1))
		.setTipoIncrementoCuota(TipoInteres.Simple)
		.setTipoInteresPunitorio(TipoInteres.Simple)
		.build();
    }

    private static TipoInteres tipoIncrementoRandom() {
	return random.nextBoolean()? TipoInteres.Acumulativo: TipoInteres.Simple;
    }

    private static Integer intervaloRandom(ContratoDuracion duracion) {
	Integer ret = 1+random.nextInt(duracion.getDuracion());
	while(duracion.getDuracion() % ret != 0) {
	    ret = 1+random.nextInt(duracion.getDuracion());
	}
	return ret;
    }

    private static ContratoDuracion duracionRandom() {
    	Integer valor = random.nextInt(100);
    	return new ContratoDuracion.Builder().setDescripcion(String.valueOf(valor)+ " Meses").setDuracion(valor).build();
    	  }

    private static BigDecimal cuotaRandom() {
	return BigDecimal.valueOf(random.nextInt(10)*1000+random.nextInt(10)*100);
    }

    private static ContratoVenta contratoVentaDe(Inmueble inmueble, PublicacionVenta pubVenta, Persona comprador) {
	return new ContratoVenta.Builder()
		.setComprador(comprador)
		.setFechaCelebracion(fechaRandom())	
		.setPrecioVenta(pubVenta.getPrecio())
		.setInmueble(inmueble)
		.build();
    }

    private static Inquilino asignarRolInquilinoA(Persona inquilino) {
	Inquilino inq = new Inquilino();
	inq.setEstadoRegistro(EstadoRegistro.ACTIVO);
	inquilino.addRol(inq);
	return inq;
    }

    private static Propietario asignarRolPropietarioA(Persona p) {
	Propietario prop = new Propietario();
	prop.setEstadoRegistro(EstadoRegistro.ACTIVO);
	p.addRol(prop);
	return prop;
    }

    private static PublicacionVenta publicacionVentaRandom(Inmueble inmueble) {
	return new PublicacionVenta.Builder().setFechaPublicacion(LocalDate.now()).setInmueble(
		inmueble).setPrecio(new BigDecimal((random.nextInt(500000) + 200000))).setMoneda(tipoMonedaRandom())
		.build();
    }

    private static TipoMoneda tipoMonedaRandom() {
	return random.nextBoolean()? TipoMoneda.Dolares : TipoMoneda.Pesos;
    }

    private static PublicacionAlquiler publicacionAlquilerRandom(Inmueble inmueble) {
	return new PublicacionAlquiler.Builder().setFechaPublicacion(LocalDate.now()).setInmueble(
		inmueble).setValorCuota(new BigDecimal((random.nextInt(500000) + 200000))).setMoneda(tipoMonedaRandom())
		.build();
    }

    private static Localidad localidadRandom(Provincia provincia) {
	return provincia.getLocalidades().get(random.nextInt(provincia.getLocalidades().size()));
    }

    private static Provincia provinciaRandom(List<Provincia> provincias) {
	return provincias.get(random.nextInt(provincias.size()));
    }

    private static Inmueble inmuebleRandom(Provincia provincia, Localidad localidad) {
	return new Inmueble.Builder()
		.setaEstrenar(random.nextBoolean())
		.setCantidadAmbientes(random.nextInt(10))
		.setCantidadCocheras(random.nextInt(10))
		.setCantidadDormitorios(random.nextInt(10))
		.setClaseInmueble(ClaseInmueble.values()[random.nextInt(ClaseInmueble.values().length)])
		.setConAireAcondicionado(random.nextBoolean())
		.setConJardin(random.nextBoolean())
		.setConParilla(random.nextBoolean())
		.setConPileta(random.nextBoolean())
		.setDireccion(new Direccion.Builder()
			.setCalle("calle " + 1 + random.nextInt(100))
			.setCodPostal(localidad.getCodigoPostal())
			.setCoordenada(new Coordenada(random.nextDouble(), random.nextDouble()))
			.setLocalidad(localidad.getNombre())
			.setNro(random.nextInt(10) * 1000 + random.nextInt(10) * 100)
			.setPais("Argentina")
			.setProvincia(provincia.getNombre())
			.build())
		.setEstadoInmueble(EstadoInmueble.values()[random.nextInt(EstadoInmueble.values().length)])
		.setSuperficieCubierta((random.nextInt(10) + 1) * 10)
		.setSuperficieTotal((random.nextInt(10) + 1) * 10)
		.setTipoInmueble(TipoInmueble.values()[random.nextInt(TipoInmueble.values().length)])
		.build();
    }

    private static Persona personaRandom() {
	return new Persona.Builder()
		.setApellido(nombres[random.nextInt(nombres.length)])
		.setDNI(dniRandom())
		.setinfoAdicional("Bla bla bla")
		.setMail(nombres[random.nextInt(nombres.length)] + "@" + nombres[random.nextInt(nombres.length)]
			+ ".mail.com")
		.setNombre(nombres[random.nextInt(nombres.length)])
		.setTelefono(getTelefeno())
		.setTelefono2(getTelefeno())
		.build();
    }

    private static String dniRandom() {
	return Integer.toString(new Double((1 + random.nextInt(10) * 10e6) + (1 + random.nextInt(10) * 10e3) + (1
		+ random.nextInt(
			10) * 10e4) + (1 + random.nextInt(10) * 10e3) + (1 + random.nextInt(10) * 10e2) + (1 + random
				.nextInt(
					10) * 10e1) + 1 + random.nextInt(10)).intValue());
    }

    private static LocalDate fechaRandom() {
	return LocalDate.of(1990 + random.nextInt(28), 1 + random.nextInt(12), 1 + random.nextInt(28));
    }
}