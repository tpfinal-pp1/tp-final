package com.TpFinal.data.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.TpFinal.domain.persona.Propietario;

public class DAOPropietarioImplTest {
	
	DAOPropietarioImpl dao;
	List<Propietario>propietarios=new ArrayList<>();

	@Before
	public void setUp() throws Exception {
		dao= new DAOPropietarioImpl();
		propietarios.clear();
	}
	
	@After
	public void tearDown() throws Exception {
		propietarios=dao.readAll();
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
	public void otroDelete() {
		dao.save(instancia("1"));
		
		dao.readAll().forEach(inq -> {
			if(inq.getNombre().equals("nombre 1"))
				dao.delete(inq);
		});
		
		assertEquals(dao.readAll().size(), 0);
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
		assertEquals("pepito", dao.readAll().get(0).getApellido());
		assertEquals("123456781", dao.readAll().get(0).getTelefono());
		assertEquals("876543211", dao.readAll().get(0).getTelefono2());
	}
	
	@Test
	public void otroUpdate() {
		dao.save(instancia("6"));
		dao.save(instancia("7"));
		dao.save(instancia("8"));
		
		dao.readAll().forEach(inq -> {
			System.out.print(inq);
			if(inq.getNombre().equals("nombre 7"))
			{
				inq.setNombre("cereza");
				dao.update(inq);
			}
		});
		
		assertEquals("cereza", dao.readAll().get(1).getNombre());
		assertEquals("pepito", dao.readAll().get(1).getApellido());
		assertEquals("123456787", dao.readAll().get(1).getTelefono());
		assertEquals("876543217", dao.readAll().get(1).getTelefono2());
	}
	
	public Propietario instancia(String numero) {
		return new Propietario.Builder()
				.setNombre("nombre "+numero)
				.setApellido("pepito")
				.setMail("mail "+numero)
				.setTelefono("12345678"+numero)
				.setTelefono2("87654321"+numero)
				.buid();
	}

}
