package com.TpFinal.data.dao;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.TpFinal.data.conexion.ConexionHibernate;
import com.TpFinal.data.conexion.TipoConexion;
import com.TpFinal.data.dao.interfaces.DAOContrato;
import com.TpFinal.data.dao.interfaces.DAOContratoAlquiler;
import com.TpFinal.data.dao.interfaces.DAOContratoVenta;
import com.TpFinal.data.dto.contrato.Contrato;
import com.TpFinal.data.dto.contrato.ContratoAlquiler;
import com.TpFinal.data.dto.contrato.ContratoVenta;

public class DAOContratoImplTest {
	DAOContrato daoContrato;
	DAOContratoVenta daoVenta;
	DAOContratoAlquiler daoAlquiler;
	List<Contrato>contratos=new ArrayList<>();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ConexionHibernate.setTipoConexion(TipoConexion.H2Test);
	}

	@Before
	public void setUp() throws Exception {
		daoContrato=new DAOContratoImpl();
		daoVenta= new DAOContratoVentaImpl();
		daoAlquiler=new DAOContratoAlquilerImpl();
		daoVenta.readAll().forEach(v -> daoVenta.delete(v));
		daoAlquiler.readAll().forEach(v -> daoAlquiler.delete(v));
		daoContrato.readAll().forEach(v -> daoContrato.delete(v));
		contratos.clear();
		
	}

	@After
	public void tearDown() throws Exception {
		daoVenta.readAll().forEach(v -> daoVenta.delete(v));
		daoAlquiler.readAll().forEach(v -> daoAlquiler.delete(v));
		daoContrato.readAll().forEach(v -> daoContrato.delete(v));
	}

	@Test
	public void readAll() {
		daoVenta.saveOrUpdate(instanciaVenta("1"));
		daoVenta.saveOrUpdate(instanciaVenta("2"));
		daoVenta.saveOrUpdate(instanciaVenta("3"));
		daoAlquiler.saveOrUpdate(instanciaAlquiler("1"));
		daoAlquiler.saveOrUpdate(instanciaAlquiler("2"));
		daoAlquiler.saveOrUpdate(instanciaAlquiler("3"));
		
		assertEquals(6, daoContrato.readAll().size());
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
	
	

}
