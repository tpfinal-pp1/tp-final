package com.TpFinal.Integracion.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.TpFinal.data.conexion.ConexionHibernate;
import com.TpFinal.data.conexion.TipoConexion;
import com.TpFinal.data.dao.DAOCitaImpl;
import com.TpFinal.data.dao.DAOPersonaImpl;
import com.TpFinal.data.dao.DAORecordatorioImpl;
import com.TpFinal.dto.EstadoRegistro;
import com.TpFinal.dto.cita.Cita;
import com.TpFinal.dto.cita.Recordatorio;
import com.TpFinal.dto.cita.TipoCita;
import com.TpFinal.dto.inmueble.CriterioBusqInmueble;
import com.TpFinal.dto.persona.Calificacion;
import com.TpFinal.dto.persona.Empleado;
import com.TpFinal.dto.persona.Inquilino;
import com.TpFinal.dto.persona.Persona;
import com.TpFinal.dto.persona.Rol;
import com.TpFinal.dto.persona.RolPersona;

public class DAORecordatorioImplIT {

    DAORecordatorioImpl dao;
    DAOCitaImpl daoCitas = new DAOCitaImpl();
    
    List<Recordatorio> recordatorios = new ArrayList<>();

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
	ConexionHibernate.setTipoConexion(TipoConexion.H2Test);
    }

    @Before
    public void setUp() throws Exception {
	dao = new DAORecordatorioImpl();
	daoCitas.readAll().forEach(daoCitas::delete);
	dao.readAll().forEach(dao::delete);
	recordatorios.clear();
    }

    @After
    public void tearDown() throws Exception {
	daoCitas.readAll().forEach(daoCitas::delete);
	recordatorios = dao.readAll();
	recordatorios.forEach(dao::delete);
    }

    @Test
    public void agregar() {
	dao.save(instancia(1));
	dao.save(instancia(2));
	dao.save(instancia(3));
	dao.save(instancia(4));

	assertEquals(dao.readAll().size(), 4);
    }

    @Test
    public void delete() {
	dao.save(instancia(1));
	dao.save(instancia(2));
	dao.save(instancia(3));
	dao.save(instancia(4));
	dao.save(instancia(5));
	dao.save(instancia(6));


	dao.readAll().forEach(recordatorio -> {
	    if (recordatorio.getMensaje().equals("Mensaje 1"))
		dao.delete(recordatorio);
	});

	assertEquals(dao.readAll().size(), 5);
    }

    @Test
    public void logicalDelete() {
	dao.save(instancia(1));
	dao.save(instancia(2));
	dao.save(instancia(3));
	dao.save(instancia(4));
	dao.save(instancia(5));
	dao.save(instancia(6));

	dao.logicalDelete(dao.readAll().get(0));
	// verifico si alguno esta en estado borrado
	boolean b = false;
	for (Recordatorio recordatorio : dao.readAll()) {
	    b = b || recordatorio.getEstadoRegistro().equals(EstadoRegistro.BORRADO);
	}
	assertTrue(b);

	// verifico que no cambie a todos
	assertEquals(6, dao.readAll().size());
	boolean b1 = true;
	for (Recordatorio p : dao.readAll()) {
	    b1 = b1 && p.getEstadoRegistro().equals(EstadoRegistro.BORRADO);
	}
	assertFalse(b1);

	// verifico los activos
	assertEquals(5, dao.readAllActives().size());
    }

    @Test
    public void update() {
	Recordatorio recordatorio1 = instancia(1);
	Recordatorio recordatario2 = instancia(2);
	dao.save(recordatorio1);
	dao.save(recordatario2);

	recordatorio1.setMensaje("pepe");
	dao.update(recordatorio1);

	boolean estaPepe = dao.readAll().stream().anyMatch(cita -> cita.getMensaje().equals("pepe"));
	boolean noMensaje1 = dao.readAll().stream().allMatch(cita -> !cita.getMensaje().equals("Mensaje 1"));
	boolean estaMensaje2 = dao.readAll().stream().anyMatch(cita -> cita.getMensaje().equals("Mensaje 2"));
	assertTrue(estaPepe);
	assertTrue(noMensaje1);
	assertTrue(estaMensaje2);
    }

    @Test
    public void RecordatorioConCita() {
	Recordatorio recordatorio = instancia(1);
	Cita cita1 = new Cita.Builder()
		.setCitado("Pepe")
		.setDireccionLugar("ASD 123")
		.setFechahora(recordatorio.getFechaInicio())
		.setObservaciones("bla")
		.setTipoDeCita(TipoCita.Otros)
		.build();
	
	Cita cita2 = new Cita.Builder()
		.setCitado("Pepe")
		.setDireccionLugar("ASD 123")
		.setFechahora(recordatorio.getFechaInicio())
		.setObservaciones("bla")
		.setTipoDeCita(TipoCita.Otros)
		.build();

	recordatorio.setCita(cita1);
	dao.save(recordatorio);
	Recordatorio recordatorioEnBd = dao.findById(recordatorio.getId());
	assertTrue(recordatorio.equals(recordatorioEnBd));
	assertTrue(recordatorioEnBd.getCita().equals(cita1));
	assertTrue(!recordatorioEnBd.getCita().equals(cita2));
    }

    public Recordatorio instancia(int numero) {
	return new Recordatorio.Builder()
		.setMensaje("Mensaje " + numero)
		.setFechaFin(LocalDateTime.of(LocalDate.now(), LocalTime.MIN).plusDays(1))
		.setFechaInicio(LocalDateTime.of(LocalDate.now(), LocalTime.MIN))
		.setHora(Integer.toString(LocalTime.MIN.getHour()))
		.setMinuto(Integer.toString(LocalTime.MIN.getMinute()))
		.build();
    }
}
