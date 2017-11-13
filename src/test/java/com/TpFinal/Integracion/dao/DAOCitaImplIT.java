package com.TpFinal.Integracion.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.TpFinal.data.conexion.ConexionHibernate;
import com.TpFinal.data.conexion.TipoConexion;
import com.TpFinal.data.dao.DAOCitaImpl;
import com.TpFinal.dto.EstadoRegistro;
import com.TpFinal.dto.cita.Cita;
import com.TpFinal.dto.cita.Recordatorio;
import com.TpFinal.dto.cita.TipoCita;

public class DAOCitaImplIT {

    DAOCitaImpl dao;
    List<Cita> citas = new ArrayList<>();

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
	ConexionHibernate.setTipoConexion(TipoConexion.H2Test);
    }

    @Before
    public void setUp() throws Exception {
	dao = new DAOCitaImpl();
	dao.readAll().forEach(dao::delete);
	citas.clear();
    }

    @After
    public void tearDown() throws Exception {
	citas = dao.readAll();
	citas.forEach(dao::delete);
    }

    @Test
    public void agregar() {
	dao.save(instancia("1"));
	dao.save(instancia("2"));
	dao.save(instancia("3"));
	dao.save(instancia("4"));

	System.out.println("Trigger key "+dao.readAll().get(0).getTriggerKey());
	
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

	dao.readAll().forEach(cita -> {
	    if (cita.getCitado().equals("Citado 1"))
		dao.delete(cita);
	});

	assertEquals(dao.readAll().size(), 5);
    }

    @Test
    public void logicalDelete() {
	dao.save(instancia("1"));
	dao.save(instancia("2"));
	dao.save(instancia("3"));
	dao.save(instancia("4"));
	dao.save(instancia("5"));
	dao.save(instancia("6"));

	dao.logicalDelete(dao.readAll().get(0));
	// verifico si alguno esta en estado borrado
	boolean b = false;
	for (Cita cita : dao.readAll()) {
	    b = b || cita.getEstadoRegistro().equals(EstadoRegistro.BORRADO);
	}
	assertTrue(b);

	// verifico que no cambie a todos
	assertEquals(6, dao.readAll().size());
	boolean b1 = true;
	for (Cita p : dao.readAll()) {
	    b1 = b1 && p.getEstadoRegistro().equals(EstadoRegistro.BORRADO);
	}
	assertFalse(b1);

	// verifico los activos
	assertEquals(5, dao.readAllActives().size());
    }

    @Test
    public void update() {
	Cita cita1 = instancia("1");
	Cita cita2 = instancia("2");
	dao.save(cita1);
	dao.save(cita2);

	cita1.setCitado("pepe");
	dao.update(cita1);

	boolean estaPepe = dao.readAll().stream().anyMatch(cita -> cita.getCitado().equals("pepe"));
	boolean noEstaCitado1 = dao.readAll().stream().allMatch(cita -> !cita.getCitado().equals("Citado 1"));
	boolean estaCitado2 = dao.readAll().stream().anyMatch(cita -> cita.getCitado().equals("Citado 2"));
	assertTrue(estaPepe);
	assertTrue(noEstaCitado1);
	assertTrue(estaCitado2);
    }

    @Test
    public void CitaConRecordatorios() {
	Cita cita1 = instancia("1");
	Recordatorio recordatorio1 = new Recordatorio.Builder()
		.setCita(cita1)
		.setFechaInicio(cita1.getFechaInicio())
		.setFechaFin(LocalDateTime.now())
		.setHora(Integer.toString(cita1.getFechaInicio().getHour()))
		.setMinuto(Integer.toString(cita1.getFechaInicio().getMinute()))
		.build();
	Recordatorio recordatorio2 = new Recordatorio.Builder()
		.setCita(cita1)
		.setFechaInicio(cita1.getFechaInicio())
		.setFechaFin(LocalDateTime.now())
		.setHora(Integer.toString(cita1.getFechaInicio().getHour()))
		.setMinuto(Integer.toString(cita1.getFechaInicio().getMinute()))
		.build();
	
	cita1.addRecordatorio(recordatorio1);
	dao.save(cita1);
	Cita citaEnBd = dao.findById(cita1.getId());
	assertTrue(cita1.equals(citaEnBd));
	assertTrue(citaEnBd.getRecordatorios().contains(recordatorio1));
	assertTrue(!citaEnBd.getRecordatorios().contains(recordatorio2));
    }

    public Cita instancia(String numero) {
	return new Cita.Builder()
		.setCitado("Citado " + numero)
		.setDireccionLugar("DireccionLugar " + numero)
		.setFechahora(LocalDateTime.MIN)
		.setObservaciones("Observaciones " + numero)
		.setTipoDeCita(TipoCita.Otros)
		.build();
    }
}
