package com.TpFinal.Integracion.services;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.TpFinal.data.conexion.ConexionHibernate;
import com.TpFinal.data.conexion.TipoConexion;
import com.TpFinal.data.dao.DAOCobroImpl;
import com.TpFinal.data.dao.DAOContratoAlquilerImpl;
import com.TpFinal.data.dao.DAOContratoDuracionImpl;
import com.TpFinal.data.dao.interfaces.DAOCobro;
import com.TpFinal.data.dao.interfaces.DAOContratoAlquiler;
import com.TpFinal.data.dao.interfaces.DAOContratoDuracion;
import com.TpFinal.dto.EstadoRegistro;
import com.TpFinal.dto.cobro.Cobro;
import com.TpFinal.dto.cobro.EstadoCobro;
import com.TpFinal.dto.contrato.ContratoAlquiler;
import com.TpFinal.dto.contrato.ContratoDuracion;
import com.TpFinal.dto.contrato.DuracionContrato;
import com.TpFinal.dto.contrato.EstadoContrato;
import com.TpFinal.dto.contrato.TipoInteres;
import com.TpFinal.exceptions.services.ContratoServiceException;
import com.TpFinal.services.CobroService;
import com.TpFinal.services.ContratoService;

public class CobroServiceIT {
	DAOCobro daoCobro;
	DAOContratoAlquiler daoContrato;
	DAOContratoDuracion daoContratoDuracion;
	CobroService service;
	List<ContratoAlquiler>contratos= new ArrayList<>();
	List<Cobro>cobros= new ArrayList<>();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ConexionHibernate.setTipoConexion(TipoConexion.H2Test);
	}

	@Before
	public void setUp() throws Exception {
		daoCobro=new DAOCobroImpl();
		daoContrato= new DAOContratoAlquilerImpl();
		daoContratoDuracion = new DAOContratoDuracionImpl();
		service= new CobroService();
		contratos.clear();
		cobros.clear();
	}

	@After
	public void tearDown() throws Exception {
		removerAsociaciones();
		daoCobro.readAll().forEach(c -> daoCobro.delete(c));
		//daoContrato.readAll().forEach(c -> c.getDuracionContrato().removeContratosAlquiler(c));
		
		//daoContratoDuracion.readAll().forEach(c -> daoContratoDuracion.delete(c));
	}
	
	private void removerAsociaciones() {
		cobros=daoCobro.readAll();
		if(cobros!=null) {
			cobros.forEach(c-> {
					c.setContrato(null);
					daoCobro.saveOrUpdate(c);
						});
		}
			cobros.clear();
				
	}
	
	@Test
	public void alta() {
		for(int i =0; i< 4; i++) {service.save(instanciaCobro(i));}
		cobros=daoCobro.readAll();
		assertEquals(4, cobros.size());
		assertEquals(new Integer(0), cobros.get(0).getNumeroCuota());
		assertEquals(new Integer(1), cobros.get(1).getNumeroCuota());
		assertEquals(new Integer(2), cobros.get(2).getNumeroCuota());
		assertEquals(new Integer(3), cobros.get(3).getNumeroCuota());
	}
	
	@Test
	public void update() {
		for(int i =0; i< 4; i++) {service.save(instanciaCobro(i));}
		cobros=daoCobro.readAll();
		assertEquals(4, cobros.size());
		for(int i =0; i< 4; i++) {
			if(i==0) 
				cobros.get(i).setEstadoCobro(EstadoCobro.COBRADO);
			cobros.get(i).setNumeroCuota(i+4); service.save(cobros.get(i));
		}
		cobros=daoCobro.readAll();
		assertEquals(new Integer(4), cobros.get(0).getNumeroCuota());
		assertEquals(EstadoCobro.COBRADO, cobros.get(0).getEstadoCobro());
		assertEquals(new Integer(5), cobros.get(1).getNumeroCuota());
		assertEquals(EstadoCobro.NOCOBRADO, cobros.get(1).getEstadoCobro());
		assertEquals(new Integer(6), cobros.get(2).getNumeroCuota());
		assertEquals(EstadoCobro.NOCOBRADO, cobros.get(2).getEstadoCobro());
		assertEquals(new Integer(7), cobros.get(3).getNumeroCuota());
		assertEquals(EstadoCobro.NOCOBRADO, cobros.get(3).getEstadoCobro());
	}
	
	@Test
	public void delete() {
		for(int i =0; i< 4; i++) {service.save(instanciaCobro(i));}
		cobros=daoCobro.readAll();
		assertEquals(4, cobros.size());
		service.delete(cobros.get(0));
		service.delete(cobros.get(1));
		assertEquals(2, daoCobro.readAllActives().size());
	}
	
	@Test
	public void calculadorDeInteres() {
		ContratoService contratoService= new ContratoService();
		ContratoAlquiler ca = instanciaAlquilerConInteresSimple();
		contratoService.addCobros(ca);
		//aca deberia guardar el contrato con sus cobros
		try {
		contratoService.saveOrUpdate(ca, null);
		}catch(ContratoServiceException e) {
		    e.printStackTrace();
		}
		ca=(ContratoAlquiler) contratoService.readAll().get(0);
		assertEquals(24, ca.getCobros().size());
		List<Cobro>cos=service.readAll();
		cos.sort((c1, c2) -> {
			int ret=0;
			if(c1.getNumeroCuota()<c2.getNumeroCuota())
				ret=-1;
			else if(c1.getNumeroCuota()>c2.getNumeroCuota())
				ret=1;
			else
				ret=0;
			return ret;
		});
		BigDecimal expected = new BigDecimal("100.00");
		BigDecimal interes= new BigDecimal(ca.getInteresPunitorio().toString());
		interes=interes.divide(new BigDecimal(100));
		Long diasAtraso= ChronoUnit.DAYS.between(cos.get(0).getFechaDeVencimiento(), LocalDate.now());
		interes=interes.multiply(new BigDecimal(diasAtraso.toString()));
		interes=ca.getValorInicial().multiply(interes);
		expected=interes.add(ca.getValorInicial());
		expected=expected.setScale(2, RoundingMode.CEILING);
		assertEquals(expected,cos.get(0).getMontoRecibido());
	}
	
	@Test
	public void calculadorDeInteresAcumulativo() {
		ContratoService contratoService= new ContratoService();
		ContratoAlquiler ca = instanciaAlquilerConInteresAcumulativo();
		contratoService.addCobros(ca);
		try {
		    contratoService.saveOrUpdate(ca, null);
		} catch (ContratoServiceException e) {
		   
		    e.printStackTrace();
		}
		ca=(ContratoAlquiler) contratoService.readAll().get(0);
		assertEquals(24, service.readAll().size());
		List<Cobro>cos=service.readAll();
		cos.sort((c1, c2) -> {
			int ret=0;
			if(c1.getNumeroCuota()<c2.getNumeroCuota())
				ret=-1;
			else if(c1.getNumeroCuota()>c2.getNumeroCuota())
				ret=1;
			else
				ret=0;
			return ret;
		});
		BigDecimal interes= new BigDecimal(ca.getInteresPunitorio().toString());
		interes=interes.divide(new BigDecimal(100));
		BigDecimal valorAnterior= ca.getValorInicial();
		Long diasAtraso= ChronoUnit.DAYS.between(cos.get(0).getFechaDeVencimiento(), LocalDate.now());
		for(int i =0; i< diasAtraso; i++) {
			valorAnterior=valorAnterior.add(valorAnterior.multiply(interes));
		}
	
		valorAnterior=valorAnterior.setScale(2, RoundingMode.CEILING);
		assertEquals(valorAnterior,cos.get(0).getMontoRecibido());
		assertEquals(cos.get(0).getContrato(), cos.get(1).getContrato());
	}
	
	@Test
	public void calculadorDeInteresAcumulativoNoVigente() {
		ContratoService contratoService= new ContratoService();
		ContratoAlquiler ca = instanciaAlquilerConInteresAcumulativoNoVigente();
		contratoService.addCobros(ca);
		try {
		    contratoService.saveOrUpdate(ca, null);
		} catch (ContratoServiceException e) {
		  
		    e.printStackTrace();
		}
		ca=(ContratoAlquiler) contratoService.readAll().get(0);
		assertEquals(0, ca.getCobros().size());
		assertEquals(0, service.readAll().size());
		
		
	}
	
	@Test
	public void verificandoReadCobrosByEstado() {
		
		Cobro cobro = instanciaCobro(2);
		cobro.setEstadoCobro(EstadoCobro.COBRADO);
		
		Cobro cobro2 = instanciaCobro(2);
		cobro2.setEstadoCobro(EstadoCobro.NOCOBRADO);
		
		service.save(cobro);
		service.save(cobro2);
		
		List <Cobro> cobrosNoCobrados = service.readCobrosByEstado(EstadoCobro.NOCOBRADO);
		
		assertEquals(1, cobrosNoCobrados.size());
		
		List <Cobro> cobros2 = service.readCobrosByEstado(EstadoCobro.COBRADO);
		
		assertEquals(1, cobros2.size());
		
		Cobro cobro3 = instanciaCobro(2);
		cobro3.setEstadoCobro(EstadoCobro.NOCOBRADO);
		
		Cobro cobro4 = instanciaCobro(2);
		cobro4.setEstadoCobro(EstadoCobro.NOCOBRADO);
		
		service.save(cobro3);
		service.save(cobro4);
		
		List <Cobro> cobros3 = service.readCobrosByEstado(EstadoCobro.COBRADO);
		List <Cobro> cobros4 = service.readCobrosByEstado(EstadoCobro.NOCOBRADO);
		
		assertEquals(1, cobros3.size());
		assertEquals(3, cobros4.size());
		assertNotEquals(2, cobros4.size());
		
		
	}
	
    private ContratoAlquiler instanciaAlquilerConInteresSimple() {
    	LocalDate fecha=LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());
    	fecha=fecha.minusDays(2);
    	fecha=fecha.minusMonths(1);
        ContratoAlquiler ret = new ContratoAlquiler.Builder()
                .setFechaCelebracion(fecha)
                .setValorIncial(new BigDecimal("100.00"))
                .setDiaDePago(new Integer(13))
                .setInteresPunitorio(new Double(50))
                .setIntervaloActualizacion(new Integer(2))
                .setTipoIncrementoCuota(TipoInteres.Simple)
                .setTipoInteresPunitorio(TipoInteres.Simple)
                .setPorcentajeIncremento(new Double(0))
                .setInquilinoContrato(null)
                .setDuracionContrato(instanciaContratoDuracion24())
                .setEstadoRegistro(EstadoRegistro.ACTIVO)
                 .build();
        ret.setEstadoContrato(EstadoContrato.Vigente);
        return ret;
    }
    
    private ContratoAlquiler instanciaAlquilerConInteresSimpleNoVencido() {
    	LocalDate fecha=LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());
    	fecha=fecha.minusDays(2);
    	fecha=fecha.minusMonths(1);
        ContratoAlquiler ret = new ContratoAlquiler.Builder()
                .setFechaCelebracion(fecha)
                .setValorIncial(new BigDecimal("100.00"))
                .setDiaDePago(new Integer(13))
                .setInteresPunitorio(new Double(50))
                .setIntervaloActualizacion(new Integer(2))
                .setTipoIncrementoCuota(TipoInteres.Simple)
                .setTipoInteresPunitorio(TipoInteres.Simple)
                .setPorcentajeIncremento(new Double(0))
                .setInquilinoContrato(null)
                .setDuracionContrato(instanciaContratoDuracion24())
                .setEstadoRegistro(EstadoRegistro.ACTIVO)
                 .build();
        ret.setEstadoContrato(EstadoContrato.Vigente);
        return ret;
    }
    
    private ContratoAlquiler instanciaAlquilerConInteresAcumulativo() {
    	LocalDate fecha=LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());
    	fecha=fecha.minusDays(2);
    	fecha=fecha.minusMonths(1);
        ContratoAlquiler ret = new ContratoAlquiler.Builder()
                .setFechaCelebracion(fecha)
                .setValorIncial(new BigDecimal("100.00"))
                .setDiaDePago(new Integer(13))
                .setInteresPunitorio(new Double(50))
                .setIntervaloActualizacion(new Integer(2))
                .setTipoIncrementoCuota(TipoInteres.Simple)
                .setTipoInteresPunitorio(TipoInteres.Acumulativo)
                .setPorcentajeIncremento(new Double(0))
                .setInquilinoContrato(null)
                .setDuracionContrato(instanciaContratoDuracion24())
                .setEstadoRegistro(EstadoRegistro.ACTIVO)
                 .build();
        ret.setEstadoContrato(EstadoContrato.Vigente);
        return ret;
    }
    
    private ContratoAlquiler instanciaAlquilerConInteresAcumulativoNoVigente() {
    	LocalDate fecha=LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());
    	fecha=fecha.minusDays(2);
    	fecha=fecha.minusMonths(1);
        ContratoAlquiler ret = new ContratoAlquiler.Builder()
                .setFechaCelebracion(fecha)
                .setValorIncial(new BigDecimal("100.00"))
                .setDiaDePago(new Integer(13))
                .setInteresPunitorio(new Double(50))
                .setIntervaloActualizacion(new Integer(2))
                .setTipoIncrementoCuota(TipoInteres.Simple)
                .setTipoInteresPunitorio(TipoInteres.Acumulativo)
                .setPorcentajeIncremento(new Double(0))
                .setInquilinoContrato(null)
                .setDuracionContrato(instanciaContratoDuracion24())
                .setEstadoRegistro(EstadoRegistro.ACTIVO)
                 .build();
        return ret;
    }
	
    private Cobro instanciaCobro(Integer n) {
    	return new Cobro.Builder()
    			.setNumeroCuota(n)
    			.setFechaDeVencimiento(LocalDate.of(2017, 11, 2))
    			.setMontoOriginal(new BigDecimal("100"))
    			.build();
    }
    
    private ContratoDuracion instanciaContratoDuracion24() {
    	return new ContratoDuracion.Builder().setDescripcion("24 Horas").setDuracion(24).build();
    	  }
    
    
}
