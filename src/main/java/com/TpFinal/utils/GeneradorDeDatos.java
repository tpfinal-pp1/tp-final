package com.TpFinal.utils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import com.TpFinal.data.dao.DAOInmuebleImpl;
import com.TpFinal.data.dao.DAOPublicacionImpl;
import com.TpFinal.data.dao.DAOPersonaImpl;
import com.TpFinal.data.dto.EstadoRegistro;
import com.TpFinal.data.dto.contrato.ContratoVenta;
import com.TpFinal.data.dto.inmueble.*;
import com.TpFinal.data.dto.persona.Persona;
import com.TpFinal.data.dto.persona.Propietario;
import com.TpFinal.data.dto.publicacion.Publicacion;
import com.TpFinal.data.dto.publicacion.PublicacionVenta;

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
    private static String[] localidades = { "17 de Agosto", "25 de Mayo", "9 de Julio / La Ni�a", "Acassuso",
	    "Aguas Verdes",
	    "Alberti", "Arenas Verdes", "Arrecifes", "Avellaneda", "Ayacucho", "Azul", "Bah�a Blanca", "Bah�a San Blas",
	    "Balcarce", "Balneario Marisol", "Balneario Orense", "Balneario Reta", "Balneario San Cayetano", "Baradero",
	    "Bella Vista", "Benito Ju�rez", "Berazategui", "Berisso", "Boulogne", "Bragado", "Brandsen", "Campana",
	    "Capilla del Se�or", "Capital Federal", "Capit�n Sarmiento", "Carapachay", "Carhue", "Carhu�",
	    "Carlos Keen", "Carmen de Areco", "Carmen de Patagones", "Caseros", "Castelar", "Castelli", "Chacabuco",
	    "Chascom�s", "Chivilcoy", "City Bell", "Ciudadela", "Claromec�", "Col�n", "Coronel Dorrego",
	    "Coronel Pringles", "Coronel Su�rez", "Darregueira", "Dunamar", "Escobar", "Ezeiza", "Florencio Varela",
	    "Florida", "Fort�n Mercedes", "Garin", "General Arenales", "General Belgrano", "General Madariaga",
	    "General Villegas", "Gral. Daniel Cerri", "Gran Buenos Aires", "Guamin�", "Haedo", "Huanguelen",
	    "Hurlingham", "Isla Mart�n Garc�a", "Ituzaingo", "Jun�n", "La Plata", "La Tablada", "Laferrere", "Lanus",
	    "Laprida", "Las Flores", "Las Gaviotas", "Las Toninas", "Lima", "Lisandro Olmos", "Llavallol", "Lobos",
	    "Lomas de Zamora", "Los Toldos - Gral. Viamonte", "Los Polvorines", "Lucila del Mar", "Luis Guill�n",
	    "Luj�n", "Magdalena", "Maip�", "Mar Azul", "Mar Chiquita", "Mar de Aj�", "Mar de Cobo", "Mar del Plata",
	    "Mar del Sud", "Mar del Tuy�", "Martinez", "M�danos / Laguna Chasic�", "Mercedes", "Merlo", "Miramar",
	    "Monte Hermoso", "Moreno", "Mor�n", "Munro", "Navarro", "Necochea", "Nueva Atlantis", "Olavarr�a", "Olivos",
	    "Open Door", "Ostende", "Pedro Luro", "Pehuaj�", "Pehuen C�", "Pergamino", "Pig��", "Pilar", "Pinamar",
	    "Provincia de Buenos Aires", "Puan", "Punta Alta", "Punta Indio", "Punta Lara", "Quequ�n", "Quilmes",
	    "Ramallo", "Ramos Mej�a", "Ranchos", "Rauch", "Rivadavia", "Rojas", "Roque P�rez", "Saenz Pe�a",
	    "Saladillo", "Salto", "San Antonio de Areco", "San Bernardo", "San Cayetano", "San Clemente del Tuy�",
	    "San Fernando", "San Isidro", "San Justo", "San Martin", "San Miguel del Monte", "San Nicol�s", "San Pedro",
	    "San Vicente", "Santa Clara del Mar", "Santa Teresita", "Sarand�", "Sierra de la Ventana",
	    "Sierra de los Padres", "Sierras de los Padres", "Tandil", "Tapalqu�", "Temperley", "Tigre",
	    "Tornquist / Ruta Prov. 76", "Trenque Lauquen", "Tres Arroyos", "Turdera", "Valentin Alsina",
	    "Vicente Lopez", "Victoria", "Villa Ballester", "Villa Gesell", "Villa Lynch", "Villa Serrana La Gruta",
	    "Villa Ventana", "Villalonga", "Wilde", "Z�rate" };
    private static Random random = new Random();
    private static DAOInmuebleImpl daoInm = new DAOInmuebleImpl();
    private static DAOPersonaImpl daoPer = new DAOPersonaImpl();
	private static DAOPublicacionImpl daoope = new DAOPublicacionImpl();

    private static String getTelefeno() {
	return Integer.toString(
		random.nextInt(10) * 1000 + random.nextInt(10) + 100 + random.nextInt(10) * 10 + random.nextInt(10))
		+ " " + Integer.toString(random.nextInt(10) * 1000 + random.nextInt(10) + 100 + random.nextInt(10) * 10
			+ random.nextInt(10));

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
			    .setLocalidad(localidades[random.nextInt(localidades.length)])
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
		    .setEstadoRegistro(EstadoRegistro.ACTIVO)
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
			.setEstadoRegistro(EstadoRegistro.ACTIVO).
				setFechaCelebracion(LocalDate.now()).setPublicacionVenta(OPV).setPrecioVenta(OPV.getPrecio()).build());



		daoPer.saveOrUpdate(p);
	    daoInm.create(inmueble);
	    inmueble.setPropietario(prop);
	    daoInm.saveOrUpdate(inmueble);
	    daoope.saveOrUpdate(OPV);






	}
	System.out.println("Agregados " + cantidad + " inmuebles a la base de datos.");
    }

}