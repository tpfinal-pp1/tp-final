package com.TpFinal.services;

import com.TpFinal.data.dao.DAOPersonaImpl;
import com.TpFinal.data.dto.persona.Persona;
import com.TpFinal.utils.DummyDataGenerator;
import com.TpFinal.utils.GeneradorDeDatos;
import com.TpFinal.utils.Utils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class UtilsTest {


    @Test
    public void SearchTest() {

        DAOPersonaImpl daoPersona =
                new DAOPersonaImpl();
        List<Persona> personas =  new ArrayList<>();
        Persona p = new Persona.Builder().setNombre("Agustin")
                .setApellido("Alexander").buid();
        personas.add(p);
        ArrayList<Object> objetos=new ArrayList<Object>();
        for (Persona persona:personas
             ) {
            Object objeto=(Object) persona;
            objetos.add(objeto);

        }

        List<Object> resultados=Utils.Search(objetos, "Agkkl;ustkjlkisadn Alexthughjandasder",62);
        assertTrue(resultados.size()==1);

    }
}
