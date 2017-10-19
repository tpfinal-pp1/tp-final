package com.TpFinal.Integracion.services;

import static org.junit.Assert.*;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import com.TpFinal.data.conexion.ConexionHibernate;
import com.TpFinal.data.conexion.TipoConexion;
import com.TpFinal.data.dao.DAOContratoAlquilerImpl;
import com.TpFinal.data.dao.DAOContratoDuracionImpl;
import com.TpFinal.data.dao.interfaces.DAOContratoAlquiler;
import com.TpFinal.data.dao.interfaces.DAOContratoDuracion;
import com.TpFinal.dto.contrato.ContratoDuracion;
import com.TpFinal.services.ContratoDuracionService;

public class ContratoDuracionServiceIT {
	
	DAOContratoAlquiler daoContrato;
	private ContratoDuracionService service;
	private DAOContratoDuracion daoContratoDuracion;
	
	List<ContratoDuracion> duraciones =new ArrayList<>();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ConexionHibernate.setTipoConexion(TipoConexion.H2Test);
	}

	@Before
	public void setUp() throws Exception {
		service = new ContratoDuracionService();
		daoContrato= new DAOContratoAlquilerImpl();
		daoContratoDuracion = new DAOContratoDuracionImpl();
		duraciones.clear();
		service.readAll().forEach(c -> service.delete(c));
	}
	
	@After
	public void tearDown() throws Exception {
		//daoContrato.readAll().forEach(c -> c.getDuracionContrato().removeContratosAlquiler(c));
		
		service.readAll().forEach(c -> service.delete(c));
	}
	
	private ContratoDuracion instanciaContratoDuracion(String numero) {
		return new ContratoDuracion.Builder().setDescripcion(numero + " Meses")
				.setDuracion(new Integer(numero))
				.build();
	}
	
	@Test
	public void save() {
		service.saveOrUpdate(instanciaContratoDuracion("3"));
		service.saveOrUpdate(instanciaContratoDuracion("4"));
		service.saveOrUpdate(instanciaContratoDuracion("5"));
		service.saveOrUpdate(instanciaContratoDuracion("6"));
		service.saveOrUpdate(instanciaContratoDuracion("8"));
		service.saveOrUpdate(instanciaContratoDuracion("33"));
		
		assertEquals(6, service.readAll().size());
		
		
		
	}
		
	
	
}