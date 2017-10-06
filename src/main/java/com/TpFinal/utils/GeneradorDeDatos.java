package com.TpFinal.utils;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.Random;

import com.TpFinal.data.dao.DAOInmuebleImpl;
import com.TpFinal.data.dao.DAOPublicacionImpl;
import com.TpFinal.data.dao.DAOPersonaImpl;
import com.TpFinal.data.dto.EstadoRegistro;
import com.TpFinal.data.dto.LocalidadRAW;
import com.TpFinal.data.dto.Provincia;
import com.TpFinal.data.dto.contrato.ContratoVenta;
import com.TpFinal.data.dto.inmueble.*;
import com.TpFinal.data.dto.persona.Persona;
import com.TpFinal.data.dto.persona.Propietario;
import com.TpFinal.data.dto.publicacion.PublicacionVenta;
import com.google.gson.*;

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
    private static String[] calles = { "lima", "buenos aires", "gaspar campos", "ecuador", "colombia", "san mart�n",
	    "quito", "calle 1", "calle2", "calle 3" };




    private static Random random = new Random();
    private static DAOInmuebleImpl daoInm = new DAOInmuebleImpl();
    private static DAOPersonaImpl daoPer = new DAOPersonaImpl();
	private static DAOPublicacionImpl daoope = new DAOPublicacionImpl();
	private static ArrayList<LocalidadRAW> localidades;
	private static ArrayList<Provincia> provincias;

    private static String getTelefeno() {
	return Integer.toString(
		random.nextInt(10) * 1000 + random.nextInt(10) + 100 + random.nextInt(10) * 10 + random.nextInt(10))
		+ " " + Integer.toString(random.nextInt(10) * 1000 + random.nextInt(10) + 100 + random.nextInt(10) * 10
			+ random.nextInt(10));

    }

	public static ArrayList<LocalidadRAW> getLocalidades() {
		return localidades;
	}


	public static ArrayList<Provincia> getProvincias() {
		return provincias;
	}

	public static void setProvincias(ArrayList<Provincia> provincias) {
		GeneradorDeDatos.provincias = provincias;
	}




	public static ArrayList<LocalidadRAW> CargarLocalidadesyProvincias() {

		JsonArray json = null;
		File file;
		VaadinRequest vaadinRequest = CurrentInstance.get(VaadinRequest.class);
		File baseDirectory = vaadinRequest.getService().getBaseDirectory();
		file = new File(baseDirectory + "/"+"Localidades.json");
		try {
			json = readJsonArrayFromFile(file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		ArrayList<LocalidadRAW> locs = new ArrayList<LocalidadRAW>();
		HashMap<Integer,Provincia> provincias = new HashMap<Integer,Provincia>();
		ArrayList<Provincia> arrayProvs=new ArrayList<Provincia>();


		for (int i = 0; i < json.size(); i++) {
			JsonObject objeto = json.get(i).getAsJsonObject();
			JsonPrimitive provincia=objeto.getAsJsonPrimitive("prv_nombre");
			JsonPrimitive localidad=objeto.getAsJsonPrimitive("loc_nombre");
			JsonPrimitive codPostal=objeto.getAsJsonPrimitive("loc_cpostal");
    		LocalidadRAW loc=new LocalidadRAW(localidad.getAsString(),codPostal.getAsString());
			Provincia prov=new Provincia(provincia.getAsString());

			if(!provincias.containsKey(prov.hashCode())){

				provincias.put(prov.hashCode(),prov);
				arrayProvs.add(prov);


			}
			else{
				prov=provincias.get(prov.hashCode());
			}
			prov.addLocalidad(loc);
			//loc.setProvincia(prov); //FIXME
			loc.setProvincia(prov.getNombre());
			locs.add(loc);


		}
		System.out.println(locs.get(100));
		GeneradorDeDatos.provincias=arrayProvs;
		GeneradorDeDatos.localidades=locs;
		return locs;
	}



	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	private static JsonArray readJsonArrayFromFile(File path) throws IOException {
		BufferedReader rd = new BufferedReader(new FileReader(path));
		String jsonText = readAll(rd);
		JsonElement jelement = new JsonParser().parse(jsonText);
		return jelement.getAsJsonArray();

	}

    private static int getAltura() {
	return random.nextInt(3000);
    }

    private static String getMail() {
	return nombres[random.nextInt(nombres.length)] + "@" + "mail.com";
    }

    private static LocalDate getFechaNac() {
	return LocalDate.of(1970 + random.nextInt(48), 1 + random.nextInt(12), 1 + random.nextInt(28));
    }

    public static void generarDatos(int cantidad) {

	for (int i = 0; i < cantidad; i++) {
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
			    .setCodPostal(Integer.toString(random.nextInt(9999)))
			    .setCoordenada(new Coordenada(random.nextDouble(), random.nextDouble()))
			    .setLocalidad(localidades.get(random.nextInt(localidades.size())).getNombre())
			    .setNro(random.nextInt(10) * 1000 + random.nextInt(10) * 100)
			    .setPais("Argentina")
			    .setProvincia("Buenos Aires")
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



		PublicacionVenta OPV=new PublicacionVenta.Builder().
				setFechaPublicacion(LocalDate.now()).
				setInmueble(inmueble).
				setPrecio(new BigDecimal((random.nextInt(500000) + 200000) )).
				setMoneda(TipoMoneda.Dolares).build();

		OPV.setContratoVenta(new ContratoVenta.Builder()
				.setFechaCelebracion(LocalDate.now()).setPublicacionVenta(OPV).setPrecioVenta(OPV.getPrecio()).build());



		daoPer.saveOrUpdate(p);
	    daoInm.create(inmueble);
	    inmueble.setPropietario(prop);
	    daoInm.saveOrUpdate(inmueble);
		daoope.saveOrUpdate(OPV);






	}
	System.out.println("Agregados " + cantidad + " inmuebles a la base de datos.");
    }

}