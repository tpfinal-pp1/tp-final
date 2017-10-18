package com.TpFinal.Integracion.services;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.TpFinal.data.conexion.ConexionHibernate;
import com.TpFinal.data.conexion.TipoConexion;
import com.TpFinal.data.dao.DAOContratoAlquilerImpl;
import com.TpFinal.data.dao.DAOContratoVentaImpl;
import com.TpFinal.data.dao.interfaces.DAOContratoAlquiler;
import com.TpFinal.data.dao.interfaces.DAOContratoVenta;
import com.TpFinal.dto.EstadoRegistro;
import com.TpFinal.dto.cobro.Cobro;
import com.TpFinal.dto.contrato.Contrato;
import com.TpFinal.dto.contrato.ContratoAlquiler;
import com.TpFinal.dto.contrato.ContratoVenta;
import com.TpFinal.dto.contrato.DuracionContrato;
import com.TpFinal.dto.contrato.EstadoContrato;
import com.TpFinal.dto.contrato.TipoInteres;
import com.TpFinal.services.ContratoService;

public class ContratoServiceIT {
	
	private ContratoService service;
	private DAOContratoVenta daoVenta;
	private DAOContratoAlquiler daoAlquiler;
	List<Contrato>contratos=new ArrayList<>();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ConexionHibernate.setTipoConexion(TipoConexion.H2Test);
	}

	@Before
	public void setUp() throws Exception {
		service= new ContratoService();
		daoVenta=new DAOContratoVentaImpl();
		daoAlquiler=new DAOContratoAlquilerImpl();
		daoVenta.readAll().forEach(c -> daoVenta.delete(c));
		daoAlquiler.readAll().forEach(c -> daoAlquiler.delete(c));
		contratos.clear();
	}

	@After
	public void tearDown() throws Exception {
		daoVenta.readAll().forEach(c -> daoVenta.delete(c));
		daoAlquiler.readAll().forEach(c -> daoAlquiler.delete(c));
	}

	@Test
	public void save() {
		service.saveOrUpdate(instanciaAlquiler("1"),null);
		service.saveOrUpdate(instanciaAlquiler("2"),null);
		service.saveOrUpdate(instanciaAlquiler("3"),null);
		service.saveOrUpdate(instanciaVenta("1"),null);
		service.saveOrUpdate(instanciaVenta("2"),null);
		service.saveOrUpdate(instanciaVenta("3"),null);
		
		assertEquals(6, service.readAll().size());
	}
	
	@Test
	public void logicalDelete() {
		service.saveOrUpdate(instanciaAlquiler("1"),null);
		service.saveOrUpdate(instanciaAlquiler("2"),null);
		service.saveOrUpdate(instanciaAlquiler("3"),null);
		service.saveOrUpdate(instanciaVenta("1"),null);
		service.saveOrUpdate(instanciaVenta("2"),null);
		service.saveOrUpdate(instanciaVenta("3"),null);
		
		service.delete(service.readAll().get(0));
		service.delete(service.readAll().get(0));
		
		assertEquals(4, service.readAll().size());
	}
	
	@Test 
	public void update() {
		service.saveOrUpdate(instanciaAlquiler("1"),null);
		service.saveOrUpdate(instanciaAlquiler("2"),null);
		service.saveOrUpdate(instanciaAlquiler("3"),null);
		service.saveOrUpdate(instanciaVenta("1"),null);
		service.saveOrUpdate(instanciaVenta("2"),null);
		service.saveOrUpdate(instanciaVenta("3"),null);
		
		service.readAll().forEach(a -> {
			if(a.getClass().equals(ContratoAlquiler.class)) {
				ContratoAlquiler ca = (ContratoAlquiler)a;
				ca.setValorInicial(new BigDecimal("100.00"));
				service.saveOrUpdate(ca, null);
			}else {
				ContratoVenta cv = (ContratoVenta)a;
				cv.setPrecioVenta(new BigDecimal("100.00"));
				service.saveOrUpdate(cv, null);
			}
				
		});
		
		service.readAll().forEach(a -> {
			if(a.getClass().equals(ContratoAlquiler.class)) {
				ContratoAlquiler ca = (ContratoAlquiler)a;
				assertEquals(new BigDecimal("100.00"), ca.getValorInicial());
			}else {
				ContratoVenta ca = (ContratoVenta)a;
				assertEquals(new BigDecimal("100.00"), ca.getPrecioVenta());
			}
			
			
		});
		
		
		assertEquals(6, service.readAll().size());
	
	}
	
	@Test
	public void testAddCobrosSimples() {
		ContratoAlquiler ca = instanciaAlquilerSimple();
		service.addCobros(ca);
		List<Cobro>cos=new ArrayList<>();
		ca.getCobros().forEach(c->cos.add(c));
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
		LocalDate fecha = LocalDate.of(2017, 05, 13);
		assertEquals(ca.getCobros().size(), 24);
		BigDecimal monto = new BigDecimal("100.00");
		BigDecimal expected = new BigDecimal("100.00");
		for(int i =0; i<cos.size(); i++) {
			expected=expected.setScale(2, RoundingMode.CEILING);
			assertEquals(expected.toBigInteger(), cos.get(i).getMontoOriginal().toBigInteger());
			if((i+1)%2==0) {
				Double interes= new Double(0.5);
				expected=expected.add(monto.multiply(new BigDecimal(interes.toString())));
			}
			//System.out.println(cos.get(i).getFechaDeVencimiento());
			assertEquals(fecha, cos.get(i).getFechaDeVencimiento());
			fecha=fecha.plusMonths(1);
		}
	}
	
	@Test
	public void testAddCobrosAcumulativos() {
		ContratoAlquiler ca = instanciaAlquilerAcumulativo();
		service.addCobros(ca);
		List<Cobro>cos=new ArrayList<>();
		ca.getCobros().forEach(c->cos.add(c));
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
		assertEquals(ca.getCobros().size(), 24);
		BigDecimal expected = new BigDecimal("100.00");
		
		LocalDate fecha = LocalDate.of(2017, 06, 11);
		for(int i =0; i<cos.size(); i++) {
			expected=expected.setScale(2, RoundingMode.CEILING);
			assertEquals(expected.toBigInteger(),cos.get(i).getMontoOriginal().toBigInteger());
			if((i+1)%2==0) {
				Double interes= new Double(0.5);
				expected=expected.add(expected.multiply(new BigDecimal(interes.toString())));
				expected=expected.setScale(2, RoundingMode.CEILING);
			}
			assertEquals(fecha, cos.get(i).getFechaDeVencimiento());
			fecha=fecha.plusMonths(1);
		}
	}
	
	
	private ContratoVenta instanciaVenta(String numero) {
		return new ContratoVenta.Builder()
				.setFechaCelebracion(LocalDate.of(2017, 05, 12))
				.setPrecioVenta(new BigDecimal(numero))
				.build();
	}
	
    private ContratoAlquiler instanciaAlquiler(String numero) {
        return new ContratoAlquiler.Builder()
                .setFechaCelebracion(LocalDate.of(2017, 05, 12))
                .setValorIncial(new BigDecimal(numero))
                .setDiaDePago(new Integer(numero))
                .setInteresPunitorio(new Double(numero))
                .setIntervaloActualizacion(new Integer(numero))
                .setInquilinoContrato(null)
                 .build();
    }
    
    private ContratoAlquiler instanciaAlquilerSimple() {
        ContratoAlquiler ret= new ContratoAlquiler.Builder()
                .setFechaCelebracion(LocalDate.of(2017, 05, 12))
                .setValorIncial(new BigDecimal("100.00"))
                .setDiaDePago(new Integer(13))
                .setInteresPunitorio(new Double(50))
                .setIntervaloActualizacion(new Integer(2))
                .setTipoIncrementoCuota(TipoInteres.Simple)
                .setTipoInteresPunitorio(TipoInteres.Simple)
                .setPorcentajeIncremento(new Double(50))
                .setInquilinoContrato(null)
                .setDuracionContrato(DuracionContrato.VeinticuatroMeses)
                .setEstadoRegistro(EstadoRegistro.ACTIVO)
                
                 .build();
        ret.setEstadoContrato(EstadoContrato.Vigente);
        return ret;
    }
    
    private ContratoAlquiler instanciaAlquilerAcumulativo() {
        ContratoAlquiler ca= new ContratoAlquiler.Builder()
                .setFechaCelebracion(LocalDate.of(2017, 05, 12))
                .setValorIncial(new BigDecimal("100.00"))
                .setDiaDePago(new Integer(11))
                .setInteresPunitorio(new Double(50))
                .setIntervaloActualizacion(new Integer(2))
                .setTipoIncrementoCuota(TipoInteres.Acumulativo)
                .setTipoInteresPunitorio(TipoInteres.Simple)
                .setPorcentajeIncremento(new Double(50))
                .setInquilinoContrato(null)
                .setDuracionContrato(DuracionContrato.VeinticuatroMeses)
                .setEstadoRegistro(EstadoRegistro.ACTIVO)
                 .build();
        ca.setEstadoContrato(EstadoContrato.Vigente);
        return ca;
    }
    
    

}
