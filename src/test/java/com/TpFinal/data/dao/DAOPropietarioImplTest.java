package com.TpFinal.data.dao;

import com.TpFinal.data.dto.Propietario;
import org.junit.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Max on 9/29/2017.
 */
public class DAOPropietarioImplTest {

    DAOPropietarioImpl dao;
    List<Propietario> propietarios = new ArrayList<>();


    @Before
    public void setUp() throws Exception {
        dao= new DAOPropietarioImpl();
        propietarios.clear();
    }

    @After
    public void tearDown() throws Exception {
        propietarios =dao.readAll();
        propietarios.forEach(dao::delete);
    }

    @Test
    public void agregar() {
        dao.save(instancia("1"));
        dao.save(instancia("2"));
        dao.save(instancia("3"));
        dao.save(instancia("4"));

        assertEquals(dao.readAll().size(), 4);
    }

    @Test
    public void update() {
        dao.save(instancia("1"));
        dao.save(instancia("2"));
        dao.save(instancia("3"));
        dao.save(instancia("4"));

        dao.readAll().forEach(prop -> {
            if(prop.getNombre().equals("nombre 1"))
            {
                prop.setNombre("sarasa");
                dao.update(prop);
            }
        });

        assertEquals("sarasa", dao.readAll().get(0).getNombre());
    }

    @Test
    public void delete() {
        dao.save(instancia("1"));
        dao.save(instancia("2"));
        dao.save(instancia("3"));
        dao.save(instancia("4"));
        dao.save(instancia("5"));
        dao.save(instancia("6"));

        dao.readAll().forEach(prop -> {
            if(prop.getNombre().equals("nombre 1"))
                dao.delete(prop);
        });

        assertEquals(dao.readAll().size(), 5);
    }

    public Propietario instancia(String numero) {
        return new Propietario.Builder()
                .setNombre("nombre "+numero)
                .setApellido("apellido "+numero)
                .setMail("mail "+numero)
                .setTelefono("telefono "+numero)
                .setTelefono("telefono "+numero).setDNI("DNI"+numero)
                .build();
    }
}
