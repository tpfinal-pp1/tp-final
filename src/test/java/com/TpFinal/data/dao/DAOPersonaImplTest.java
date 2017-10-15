package com.TpFinal.data.dao;


import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import com.TpFinal.data.conexion.ConexionHibernate;
import com.TpFinal.data.conexion.TipoConexion;
import com.TpFinal.data.dto.EstadoRegistro;
import com.TpFinal.data.dto.persona.Calificacion;
import com.TpFinal.data.dto.persona.Inquilino;
import com.TpFinal.data.dto.persona.Persona;
import com.TpFinal.data.dto.persona.RolPersona;
import com.TpFinal.data.dto.publicacion.Rol;


public class DAOPersonaImplTest {

    DAOPersonaImpl dao;
    List<Persona>Personas= new ArrayList<>();


    @BeforeClass
    public static void setUpBeforeClass() throws Exception{
        ConexionHibernate.setTipoConexion(TipoConexion.H2Test);
    }


    @Before
    public void setUp() throws Exception {
        dao= new DAOPersonaImpl();
        dao.readAll().forEach(dao::delete);
        Personas.clear();
    }

    @After
    public void tearDown() throws Exception {
        Personas=dao.readAll();
        Personas.forEach(dao::delete);
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
    public void delete() {
    	dao.save(instancia("1"));
        dao.save(instancia("2"));
        dao.save(instancia("3"));
        dao.save(instancia("4"));
        dao.save(instancia("5"));
        dao.save(instancia("6"));

        dao.readAll().forEach(inq -> {
            if(inq.getNombre().equals("nombre 1"))
                dao.delete(inq);
        });

        assertEquals(dao.readAll().size(), 5);
    }
    
    @Test 
    public void logicalDelete() {
    	dao.save(instancia("1"));
        dao.save(instancia("2"));
        dao.save(instancia("3"));
        dao.save(instancia("4"));
        dao.save(instancia("5"));
        dao.save(instancia("6"));
        
        dao.logicalDelete(dao.readAll().get(0));
        //verifico si alguno esta en estado borrado
        boolean b=false;
        for(Persona p : dao.readAll()) {b=b||p.getEstadoRegistro().equals(EstadoRegistro.BORRADO);}
        assertTrue(b);
        
        //verifico que no cambie a todos
        assertEquals(6, dao.readAll().size());
        boolean b1=true;
        for(Persona p : dao.readAll()){b1=b1&&p.getEstadoRegistro().equals(EstadoRegistro.BORRADO);}
        assertFalse(b1);
        
        //verifico los activos
        assertEquals(5, dao.readAllActives().size());
    }
    
    @Test
    public void personaConRoles() {
    	dao.readAll().forEach(p -> dao.delete(p));
    	Persona p = instancia("1");
    	
    	p.addRol(instanciaInquilino(Calificacion.A));
    	p.addRol(instanciaInquilino(Calificacion.B));
    	p.addRol(instanciaInquilino(Calificacion.C));
    	
    	p.getRoles().forEach(r -> r.setPersona(p));
    	
    	
    	dao.save(p);
    	
    	
    	assertEquals(1, dao.readAllActives().size());
    	
    	
    	assertEquals(1, dao.readAll().size());

    	
    	assertEquals(3, dao.readAllActives().get(0).getRoles().size());
    	
    	Rol r = Rol.Propietario;
    	for(RolPersona rp : dao.readAllActives().get(0).getRoles()) {r=rp.giveMeYourRole();}
    	assertEquals(Rol.Inquilino, r);
        
    }
    
    

    @Test
    public void update() {
        Personas=dao.readAll();
        Personas.forEach(dao::delete);
        dao.save(instancia("1"));
        dao.save(instancia("2"));
        dao.save(instancia("3"));
        dao.save(instancia("4"));

        dao.readAll().forEach(inq -> {
            if(inq.getNombre().equals("nombre 1"))
            {
                inq.setNombre("sarasa");
                inq.setInfoAdicional("info");
                dao.update(inq);
            }
        });
        System.out.println(dao.readAllActives().size());
        boolean estaSarasa=false;
        boolean estaInfo=false;
        for(Persona p : dao.readAllActives()) {
        	estaSarasa=estaSarasa||p.getNombre().equals("sarasa");
        	estaInfo=estaInfo||p.getInfoAdicional().equals("info");
        }
        assertTrue(estaSarasa&&estaInfo);
        
//        assertEquals("sarasa", dao.readAllActives().get(2).getNombre());
//        assertEquals("info", dao.readAllActives().get(2).getInfoAdicional());
    }


    public static Persona instancia(String numero) {
        return new Persona.Builder()
                .setNombre("nombre "+numero)
                .setApellido("apellido "+numero)
                .setMail("mail "+numero)
                .setTelefono("telefono "+numero)
                .setTelefono("telefono "+numero)
                .setTelefono2("telefono2 "+numero)
                .setDNI("Dni"+numero)
                .setinfoAdicional("Info Adicional"+ numero)
                .build();
    }
    
    
    @SuppressWarnings("unused")
    private Inquilino instanciaInquilino(String numero) {
    	return new Inquilino.Builder()
    			.setCalificacion(Calificacion.A)
    			.build();
    }
    
    private Inquilino instanciaInquilino(Calificacion c) {
    	return new Inquilino.Builder()
    			.setCalificacion(c)
    			.build();
    }

}
