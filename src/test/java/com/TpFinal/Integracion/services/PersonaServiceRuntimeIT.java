package com.TpFinal.Integracion.services;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
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
import com.TpFinal.dto.persona.Rol;
import com.TpFinal.services.CobroService;
import com.TpFinal.services.ContratoService;
import com.TpFinal.services.PersonaService;

public class PersonaServiceRuntimeIT {

	PersonaService service;
	List<Persona>persona= new ArrayList<>();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
	}

	@Before
	public void set() {
		service=new PersonaService();
	}

	@After
	public void tearDown() {
	}

	
	@Test
	public void pagoAtrasado() {
		ContratoAlquiler c= instanciaContrato(8);
		List<Cobro>cobros=setearContratoParaTest(c);
	
		assertEquals(true,service.cobroAtrasado(cobros.get(0)));
		assertEquals(true,service.cobroAtrasado(cobros.get(5)));
	}
	
	@Test
	public void cantidadConsecutivos() {
		ContratoAlquiler c= instanciaContrato(8);
		List<Cobro>cobros=setearContratoParaTest(c);
		
		assertEquals(new Integer(6), service.cantidadConsecutivos(cobros));
		
		LocalDate pago= cobros.get(4).getFechaDeVencimiento();
		pago=pago.minusDays(2);
		
		cobros.get(4).setFechaDePago(pago);
		assertEquals(new Integer(4), service.cantidadConsecutivos(cobros));
	}
	
	@Test
	public void cantidadAtrasados() {
		ContratoAlquiler c= instanciaContrato(8);
		List<Cobro>cobros=setearContratoParaTest(c);
		
		
		assertEquals(new Integer(6), service.cantidadPagosAtrasados(cobros));
		
		LocalDate pago= cobros.get(4).getFechaDeVencimiento();
		pago=pago.minusDays(2);
		
		cobros.get(4).setFechaDePago(pago);
		assertEquals(new Integer(5), service.cantidadPagosAtrasados(cobros));
	}
	
	@Test
	public void calificarInquilinoA() {
		ContratoAlquiler c= instanciaContrato(8);
		c.setEstadoContrato(EstadoContrato.Vigente);
		new ContratoService().addCobrosAlquiler(c);
		Persona p = instancia("1");
		p.addRol(instanciaInquilino(Calificacion.B));
		System.out.println(p.getInquilino());
		p.getInquilino().addContrato(c);;
		System.out.println(p.getInquilino().getContratos().size());
		try {
			service.calificarInquilino(p);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		assertEquals(Calificacion.A, p.getInquilino().getCalificacion());
	}
	
	@Test
	public void calificarInquilinoB() {
		ContratoAlquiler c= instanciaContrato(6);
		c.setEstadoContrato(EstadoContrato.Vigente);
		new ContratoService().addCobrosAlquiler(c);
		
		List<Cobro>cobros=c.getCobros().stream().collect(Collectors.toList());
		cobros.sort((c1,c2)-> c1.getFechaDeVencimiento().compareTo(c2.getFechaDeVencimiento()));
		
		LocalDate pago= cobros.get(3).getFechaDeVencimiento();
		pago=pago.minusDays(2);
		
		cobros.get(3).setFechaDePago(pago);
		
		c.setCobros(cobros.stream().collect(Collectors.toSet()));
		Persona p = instancia("1");
		p.addRol(instanciaInquilino(Calificacion.C));
		
		Inquilino i= p.getInquilino();
		i.addContrato(c);
		
		service.calificarInquilino(p);
		
		assertEquals(Calificacion.B, p.getInquilino().getCalificacion());
	}
	
	@Test
	public void calificarInquilinoC() {
		ContratoAlquiler c= instanciaContrato(3);
		c.setEstadoContrato(EstadoContrato.Vigente);
		new ContratoService().addCobrosAlquiler(c);
		
		List<Cobro>cobros=c.getCobros().stream().collect(Collectors.toList());
		cobros.sort((c1,c2)-> c1.getFechaDeVencimiento().compareTo(c2.getFechaDeVencimiento()));
		
		LocalDate pago= cobros.get(1).getFechaDeVencimiento();
		pago=pago.minusDays(2);
		
		cobros.get(1).setFechaDePago(pago);
		
		c.setCobros(cobros.stream().collect(Collectors.toSet()));
		Persona p = instancia("1");
		p.addRol(instanciaInquilino(Calificacion.A));
		
		Inquilino i= p.getInquilino();
		i.addContrato(c);
		
		service.calificarInquilino(p);
		
		assertEquals(Calificacion.C, p.getInquilino().getCalificacion());
	}
	
	@Test
	public void calificarInquilinoD() {
		ContratoAlquiler c= instanciaContrato(6);
		c.setEstadoContrato(EstadoContrato.Vigente);
		new ContratoService().addCobrosAlquiler(c);
		
		List<Cobro>cobros=c.getCobros().stream().collect(Collectors.toList());
		cobros.sort((c1,c2)-> c1.getFechaDeVencimiento().compareTo(c2.getFechaDeVencimiento()));
		
		cobros.forEach(cobro -> {
			LocalDate pago= cobro.getFechaDeVencimiento();
			pago=pago.minusDays(1);
			cobro.setFechaDePago(pago);
		});
		
		c.setCobros(cobros.stream().collect(Collectors.toSet()));
		c.getCobros().forEach(cob -> System.out.println(cob.getFechaDePago()));
		Persona p = instancia("1");
		p.addRol(instanciaInquilino(Calificacion.A));
		
		Inquilino i= p.getInquilino();
		i.addContrato(c);
		
		service.calificarInquilino(p);
		
		assertEquals(Calificacion.D, p.getInquilino().getCalificacion());
	}
	
	
	
	private List<Cobro> setearContratoParaTest(ContratoAlquiler c) {
		c.setEstadoContrato(EstadoContrato.Vigente);
		new ContratoService().addCobrosAlquiler(c);
		List<Cobro>cobros=c.getCobros().stream().collect(Collectors.toList());
		new CobroService().calcularDatosFaltantes(cobros);
		cobros.sort((c1,c2)-> c1.getFechaDeVencimiento().compareTo(c2.getFechaDeVencimiento()));
		return service.filtrarUltimosSeisMeses(cobros);
	}

	private ContratoAlquiler instanciaContrato(int meses) {
		return new ContratoAlquiler.Builder()
				.setDiaDePago(2)
				.setDuracionContrato(new ContratoDuracion.Builder()
						.setDescripcion("12")
						.setDuracion(12)
						.build())
				.setFechaIngreso(LocalDate.now().minusMonths(meses))
				.setInteresPunitorio(new Double(12))
				.setIntervaloActualizacion(2)
				.setPorcentajeIncremento(new Double(10))
				.setTipoIncrementoCuota(TipoInteres.Simple)
				.setTipoInteresPunitorio(TipoInteres.Simple)
				.setValorIncial(new BigDecimal("1000"))
				.build();
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

	private Inquilino instanciaInquilino(Calificacion c) {
		return new Inquilino.Builder()
				.setCalificacion(c)
				.build();
	}
	



}
