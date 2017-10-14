package com.TpFinal.data.dto;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LocalidadTest {

	@Before
	public void setUp() throws Exception {
		JsonUtil gson = new JsonUtil();
		//traigo las localidades "feas"
		List<LocalidadRAW>localidades=gson.leerRaw("src"+File.separator+"main"+File.separator+"webapp"+File.separator+"Localidades.json");
		//lo paso al modelo de provincias(cada provincia con una lista de localidades
		List<Provincia>provincias=gson.rawToProvincias(localidades);
	}

	@After
	public void tearDown() throws Exception {
	}

//	@Test
//	public void traerProvincias() {
//		LocalidadJson gson = new LocalidadJson();
//		List<LocalidadRAW>localidades=gson.leer("src\\main\\webapp\\Localidades.json");
//		System.out.println(localidades.size());
//		localidades.forEach( l -> System.out.println(l.getNombre()+" "+l.getProvincia()+" "+l.getCodPosta()));
//	}
	

	public void transformar() {


		//imprimo resultados(consume mucho)
	/*	provincias.forEach(p ->{
			p.getLocalidades().forEach(l -> System.out.println(p.getNombre()+": "+l.getNombre()));
		});*/
		
		
	}


	
	public void transformar2() {
		
	}

}
