package com.TpFinal.Integracion.services;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.Year;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import com.TpFinal.services.Notificable;
import com.TpFinal.services.NotificadorConcreto;
import com.TpFinal.services.Planificador;


public class PlanificadorTest {
	Planificador sc;

	@Before
	public void setUp() throws Exception {
		sc = new Planificador();
		sc.encender();
	}

	@After
	public void tearDown() throws Exception {
		sc.apagar();
		
		
	}

	@Test
	public void test() {
		sc.setNotificacion(new NotificadorConcreto());
		for(int i=0; i<3; i++) {
			LocalDate fInicio = LocalDate.now();
			LocalDate fFin = fInicio.plusDays(1);
			
			sc.agregarAccion("Mensaje numero "+i, fInicio, fFin, "12", String.valueOf(33+i), "1", Long.valueOf(i));
		}
		
		try {
			TimeUnit.SECONDS.sleep(300);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
