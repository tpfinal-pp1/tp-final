package com.TpFinal.utils;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import com.TpFinal.data.dao.DAOInmuebleImpl;
import com.TpFinal.data.dao.DAOPublicacionImpl;
import com.TpFinal.data.dao.DAOPersonaImpl;
import com.TpFinal.data.dto.EstadoRegistro;
import com.TpFinal.data.dto.Localidad;
import com.TpFinal.data.dto.Provincia;
import com.TpFinal.data.dto.contrato.ContratoVenta;
import com.TpFinal.data.dto.inmueble.*;
import com.TpFinal.data.dto.persona.Persona;
import com.TpFinal.data.dto.persona.Propietario;
import com.TpFinal.data.dto.publicacion.PublicacionVenta;
import com.TpFinal.services.ProvinciaService;
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
   
    private static Random random = new Random();
    private static DAOInmuebleImpl daoInm = new DAOInmuebleImpl();
    private static DAOPersonaImpl daoPer = new DAOPersonaImpl();
    private static DAOPublicacionImpl daoope = new DAOPublicacionImpl();
    private static ProvinciaService serviceProvincia= new ProvinciaService(CurrentInstance.get(VaadinRequest.class).getService().getBaseDirectory()+File.separator+"Localidades.json");
 

    private static String getTelefeno() {
	return Integer.toString(
		random.nextInt(10) * 1000 + random.nextInt(10) + 100 + random.nextInt(10) * 10 + random.nextInt(10))
		+ " " + Integer.toString(random.nextInt(10) * 1000 + random.nextInt(10) + 100 + random.nextInt(10) * 10
			+ random.nextInt(10));

    }
 
    public static void generarDatos(int cantidad) {
	List<Provincia>provincias = serviceProvincia.getProvincias();

	for (int i = 0; i < cantidad; i++) {
	    
	  
	  Provincia provincia = provincias.get(random.nextInt(provincias.size()));
	  Localidad localidad = provincia.getLocalidades().get(random.nextInt(provincia.getLocalidades().size()));
	    Inmueble inmueble = new Inmueble.Builder()
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
			    .setCalle("calle " + i)
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
	    Persona p = new Persona.Builder()
		    .setApellido(nombres[random.nextInt(nombres.length)])
		    .setDNI(Integer.toString(i))
		    .setinfoAdicional("Bla bla bla")
		    .setMail(nombres[random.nextInt(nombres.length)] + "@" + nombres[random.nextInt(nombres.length)]
			    + ".mail.com")
		    .setNombre(nombres[random.nextInt(nombres.length)])
		    .setTelefono(getTelefeno())
		    .setTelefono2(getTelefeno())
		    .buid();
	    daoPer.create(p);

	    Propietario prop = new Propietario();
	    prop.setEstadoRegistro(EstadoRegistro.ACTIVO);
	    // prop.setPersona(p);
	    p.addRol(prop);

	    PublicacionVenta OPV = new PublicacionVenta.Builder().setFechaPublicacion(LocalDate.now()).setInmueble(
		    inmueble).setPrecio(new BigDecimal((random.nextInt(500000) + 200000))).setMoneda(TipoMoneda.Dolares)
		    .build();

	    OPV.setContratoVenta(new ContratoVenta.Builder()
		    .setFechaCelebracion(LocalDate.now()).setPublicacionVenta(OPV).setPrecioVenta(OPV.getPrecio())
		    .build());

	    daoPer.saveOrUpdate(p);
	    daoInm.create(inmueble);
	    inmueble.setPropietario(prop);
	    daoInm.saveOrUpdate(inmueble);
	    daoope.saveOrUpdate(OPV);

	}
	System.out.println("Agregados " + cantidad + " inmuebles a la base de datos.");
    }

}