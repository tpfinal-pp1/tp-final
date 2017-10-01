package com.TpFinal.data.dao;


import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

import com.TpFinal.data.conexion.ConexionHibernate;
import com.TpFinal.data.conexion.TipoConexion;
import com.TpFinal.data.dto.persona.Calificacion;
import com.TpFinal.data.dto.persona.Inquilino;


public class DAOInquilinoImplTest {
	
	DAOInquilinoImpl dao;
	List<Inquilino>inquilinos= new ArrayList<>();

	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
		ConexionHibernate.setTipoConexion(TipoConexion.H2Test);
	}

	
	@Before
	public void setUp() throws Exception {
		dao= new DAOInquilinoImpl();
		dao.readAll().forEach(dao::delete);
		inquilinos.clear();
	}
	
	@After
	public void tearDown() throws Exception {
		inquilinos=dao.readAll();
		inquilinos.forEach(dao::delete);
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
	public void update() {
		inquilinos=dao.readAll();
		inquilinos.forEach(dao::delete);
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
		
		assertEquals("sarasa", dao.readAll().get(0).getNombre());
		assertEquals("info", dao.readAll().get(0).getInfoAdicional());
	}
	
	
	public Inquilino instancia(String numero) {
		return new Inquilino.Builder()
			.setNombre("nombre "+numero)
			.setApellido("apellido "+numero)
			.setMail("mail "+numero)
			.setTelefono("telefono "+numero)
			.setCalificacion(Calificacion.A)
			.setTelefono("telefono "+numero)
				.setTelefono2("telefono2 "+numero)
				.setDNI("Dni"+numero)
				.setinfoAdicional("Info Adicional"+ numero)
			.buid();

	}

}
