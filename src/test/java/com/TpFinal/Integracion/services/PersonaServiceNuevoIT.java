package com.TpFinal.Integracion.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.TpFinal.data.conexion.ConexionHibernate;
import com.TpFinal.data.conexion.TipoConexion;
import com.TpFinal.data.dao.DAOPersonaImpl;
import com.TpFinal.data.dao.interfaces.DAOPersona;
import com.TpFinal.dto.cobro.Cobro;
import com.TpFinal.dto.contrato.ContratoAlquiler;
import com.TpFinal.dto.contrato.ContratoDuracion;
import com.TpFinal.dto.contrato.EstadoContrato;
import com.TpFinal.dto.contrato.TipoInteres;
import com.TpFinal.dto.persona.Calificacion;
import com.TpFinal.dto.persona.CategoriaEmpleado;
import com.TpFinal.dto.persona.Credencial;
import com.TpFinal.dto.persona.Empleado;
import com.TpFinal.dto.persona.Inquilino;
import com.TpFinal.dto.persona.Persona;
import com.TpFinal.dto.persona.RolPersona;
import com.TpFinal.services.CobroService;
import com.TpFinal.services.ContratoService;
import com.TpFinal.services.PersonaService;
import com.TpFinal.view.empleados.FiltroEmpleados;

public class PersonaServiceNuevoIT {

	PersonaService service;
	List<Persona>persona= new ArrayList<>();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
		ConexionHibernate.setTipoConexion(TipoConexion.H2Test);
	}

	@Before
	public void set() {
		service=new PersonaService();
		DAOPersona dao = new DAOPersonaImpl();
		service.readAll().forEach(p -> dao.delete(p));
		persona.clear();
	}

	@After
	public void tearDown() {
		DAOPersona dao = new DAOPersonaImpl();
		service.readAll().forEach(p -> dao.delete(p));
	}

	@Test
	public void agregar() {
		Persona p = instancia("1");
		p.addRol(instanciaInquilino(Calificacion.A));
		p.addRol(instanciaInquilino(Calificacion.B));
		p.addRol(instanciaInquilino(Calificacion.C));


		service.saveOrUpdate(p);
		assertEquals(1,service.readAll().size());
		assertEquals(3, service.readAll().get(0).getRoles().size());
	}

	@Test
	public void editar() {
		Persona p = instancia("1");
		p.addRol(instanciaInquilino(Calificacion.A));
		p.addRol(instanciaInquilino(Calificacion.B));
		p.addRol(instanciaInquilino(Calificacion.C));


		service.saveOrUpdate(p);

		p=service.readAll().get(0);
		p.getRoles().forEach(rol -> {
			if(rol.getClass().equals(Inquilino.class)){
				Inquilino i =(Inquilino) rol;
				if(i.getCalificacion().equals(Calificacion.C))
					i.setCalificacion(Calificacion.D);
			}
		});
		service.saveOrUpdate(p);

		Set<RolPersona>roles=service.readAll().get(0).getRoles();
		boolean estaD=false;
		for(RolPersona r:roles) {
			Inquilino i = (Inquilino)r;
			estaD=estaD||i.getCalificacion().equals(Calificacion.D);
		}
	}

	@Test
	public void inmoPersona() {
		service.saveOrUpdate(instancia("1"));
		service.saveOrUpdate(instancia("2"));
		service.saveOrUpdate(instancia("3"));
		service.saveOrUpdate(instanciaInmo("4"));
		service.saveOrUpdate(instanciaInmo("5"));
		service.saveOrUpdate(instanciaInmo("6"));
		service.saveOrUpdate(instanciaInmo("7"));

		assertEquals(7, service.readAll().size());
		assertEquals(3, service.getPersonas().size());
		assertEquals(4, service.getInmobiliarias().size());
	}

	@Test
	public void altaDeEmpleado() {
		service.saveOrUpdate(instanciaEmpleadoAgente("1"));
		service.saveOrUpdate(instanciaEmpleadoAgente("3"));
		service.saveOrUpdate(instanciaEmpleadoAgente("4"));
		service.saveOrUpdate(instanciaEmpleadoAgente("5"));
		service.saveOrUpdate(instanciaEmpleadoAgente("15"));

		assertEquals(5, service.findAllEmpleados(new FiltroEmpleados()).size());

		service.saveOrUpdate(instancia("3"));
		service.saveOrUpdate(instanciaInmo("4"));
		service.saveOrUpdate(instanciaInmo("5"));

		assertEquals(8, service.readAll().size());
		assertEquals(6, service.getPersonas().size());

		service.saveOrUpdate(instanciaEmpleadoAdministrador("342"));
		service.saveOrUpdate(instanciaEmpleadoAdministrador("343"));
		service.saveOrUpdate(instanciaEmpleadoAdministrador("344"));
		service.saveOrUpdate(instanciaEmpleadoAdministrador("346"));
		service.saveOrUpdate(instanciaEmpleadoAdministrador("463"));
		service.saveOrUpdate(instanciaEmpleadoAdministrador("300"));

		assertEquals(12, service.getPersonas().size());
		assertEquals(11, service.findAllEmpleados(new FiltroEmpleados()).size());
		

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
				.setEsInmobiliaria(false)
				.build();
	}

	public static Persona instanciaInmo(String numero) {
		return new Persona.Builder()
				.setNombre("nombre "+numero)
				.setApellido("apellido "+numero)
				.setMail("mail "+numero)
				.setTelefono("telefono "+numero)
				.setTelefono("telefono "+numero)
				.setTelefono2("telefono2 "+numero)
				.setDNI("Dni"+numero)
				.setinfoAdicional("Info Adicional"+ numero)
				.setEsInmobiliaria(true)
				.build();
	}

	private static Persona instanciaEmpleadoAdministrador(String numero) {

		Persona p = new Persona.Builder()
				.setApellido(numero+"Aasdfafa")
				.setDNI(numero+32523)
				.setinfoAdicional("Un administrador del sistema")
				.setMail(numero+"@hotmail.com")
				.setNombre(numero+"Abdsa")
				.setTelefono(numero+"45345")
				.setTelefono2(numero+"34534")
				.build();
		Empleado e = new Empleado.Builder()
				.setCategoriaEmpleado(CategoriaEmpleado.admin)
				.setFechaDeAlta(LocalDate.now().minus(Period.ofMonths(1)))
				.setPersona(p)
				.build();
		String usuario = p.getNombre().toLowerCase();
		Credencial c = new Credencial.Builder()
				.setContrasenia(usuario)
				.setEmpleado(e)
				.setUsuario(usuario)
				.build();

		return p;
	}

	private static Persona instanciaEmpleadoAgente(String numero) {

		Persona p = new Persona.Builder()
				.setApellido(numero)
				.setDNI(numero)
				.setinfoAdicional("Bla bla bla")
				.setMail(numero+"@gmail.com")
				.setNombre(numero)
				.setTelefono(numero)
				.setTelefono2(numero)
				.build();
		Empleado e = new Empleado.Builder()
				.setCategoriaEmpleado(CategoriaEmpleado.agenteInmobilario)
				.setFechaDeAlta(LocalDate.now().minus(Period.ofMonths(1)))
				.setPersona(p)
				.build();
		String usuario = p.getNombre().toLowerCase();
		Credencial c = new Credencial.Builder()
				.setContrasenia(usuario)
				.setEmpleado(e)
				.setUsuario(usuario)
				.build();

		return p;
	}

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
	
	private ContratoAlquiler instanciaContrato(String n) {
		return new ContratoAlquiler.Builder()
				.setDiaDePago(2)
				.setDuracionContrato(new ContratoDuracion.Builder()
						.setDescripcion("12")
						.setDuracion(12)
						.build())
				.setFechaCelebracion(LocalDate.now().minusMonths(6))
				.setInteresPunitorio(new Double(12))
				.setIntervaloActualizacion(2)
				.setPorcentajeIncremento(new Double(10))
				.setTipoIncrementoCuota(TipoInteres.Simple)
				.setTipoInteresPunitorio(TipoInteres.Simple)
				.setValorIncial(new BigDecimal("1000"))
				.build();
	}


}
