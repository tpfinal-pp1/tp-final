package com.TpFinal.UnitTests;


import com.TpFinal.dto.persona.Persona;
import com.TpFinal.utils.Utils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class UtilsTest {

    @Test
    public void SearchTest() {

	
	List<Persona> personas = new ArrayList<>();
	Persona p = new Persona.Builder().setNombre("Pepe")
		.setApellido("Peposo").build();
	personas.add(p);
	ArrayList<Object> objetos = new ArrayList<Object>();
	for (Persona persona : personas) {
	    Object objeto = (Object) persona;
	    objetos.add(objeto);

	}

	List<Object> resultados = Utils.Search(objetos, "Pephe Pepozientoohh", 70);
	assertTrue(resultados.size() == 1);

    }
}
