package com.TpFinal.data.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.TpFinal.domain.persona.Inquilino;

public class DAOInquilinoImplTest {
	
	DAOInquilinoImpl dao;
	List<Inquilino>inquilinos= new ArrayList<>();
	

	@Before
	public void setUp() throws Exception {
		dao= new DAOInquilinoImpl();
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
		dao.save(instancia("1"));
		dao.save(instancia("2"));
		dao.save(instancia("3"));
		dao.save(instancia("4"));
		
		dao.readAll().forEach(inq -> {
			System.out.print(inq);
			if(inq.getNombre().equals("nombre 1"))
			{
				inq.setNombre("sarasa");
				dao.update(inq);
			}
		});
		
		assertEquals("sarasa", dao.readAll().get(0).getNombre());
	}
	
	
	public Inquilino instancia(String numero) {
		return new Inquilino.Builder()
			.setNombre("nombre "+numero)
			.setApellido("apellido "+numero)
			.setMail("mail "+numero)
			.setTelefono("telefono "+numero)
			.setTelefono2("telefono "+numero)
			.buid();
	}

}
