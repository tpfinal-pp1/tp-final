package com.TpFinal.services;

import com.TpFinal.data.dao.DAOCitaImpl;
import com.TpFinal.dto.cita.Cita;
import com.TpFinal.dto.contrato.ContratoDuracion;
import com.TpFinal.dto.persona.CategoriaEmpleado;
import com.TpFinal.dto.persona.Empleado;

import org.apache.log4j.Logger;
import org.h2.table.Plan;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CitaService {
	DAOCitaImpl dao;
	private static final Logger logger = Logger.getLogger(CitaService.class);

	public CitaService() {
		dao = new DAOCitaImpl();

	}

	public boolean saveOrUpdate(Cita cita) {
		if(cita.getRandomKey()==null)
			cita.generateUUID();
		return dao.saveOrUpdate(cita);
	}

	public boolean addCita(Cita cita) {
		boolean b = saveOrUpdate(cita);
		Long id = null;
		if (b) {
			List<Cita> citas = readAll();
			for (Cita citaGuardada : citas) {
				id = citaGuardada.getId();
				citaGuardada.setId(null);
				if (citaGuardada.equals(cita)) {
					cita.setId(id);
					break;
				}
			}
		}

		agregarTriggers(cita);

		return b;
	}

	public Cita getCitaFromTriggerKey(String triggerKey) {
		if(!triggerKey.equals("")){
			int corte = triggerKey.indexOf('-');

			for (Cita cita : readAll()) {
				if (cita.getId().toString().equals(triggerKey.substring(0, corte))) {
					return cita;
				}
			}
		}
		return null;

	}

	public void agregarTriggers(Cita cita) {
		Planificador.get().removeJobCita(cita);
		Planificador.get().addJobCita(cita);
	}

	public Cita getUltimaAgregada() {
		List<Cita> citas = dao.readAllActives();
		citas.sort((c1, c2) -> c2.getId().compareTo(c1.getId()));
		return citas.get(0);
	}

	public boolean editCita(Cita cita) {
		System.out.println("EDITADA " + cita);
		agregarTriggers(cita);
		return saveOrUpdate(cita);

	}

	public static boolean actualizarHsAntesCitas(Empleado emp) {
		DAOCitaImpl dao = new DAOCitaImpl();
		boolean ret = false;
		List<Cita> citasDelEmpleado = dao.readAll()
				.stream()
				.filter(c -> {
					return c.getEmpleado().equals(emp.getCredencial().getUsuario());
				})
				.collect(Collectors.toList());
		citasDelEmpleado.forEach(cita -> {
			Planificador.get().removeJobCita(cita);
			System.out.println("ACAAAAAAAAAAAAAAAAAA"+emp.getHorasAntesRecoradatorio1()+" "+emp.getHorasAntesRecoradatorio2());
			Planificador.get().addJobCita(cita, emp.getHorasAntesRecoradatorio1(), emp.getHorasAntesRecoradatorio2());
		});

		return ret;
	}

	public boolean delete(Cita p) {

		return dao.logicalDelete(p);

	}

	public boolean deleteCita(Cita p) {
		System.out.println("BORRADA" + p);
		boolean ret1 = true;
		boolean ret2 = true;

		ret1 = dao.logicalDelete(p);
		if (!ret1) {
			System.err.println("Error al Borrar la cita..");
		}
		ret2 = Planificador.get().removeJobCita(p);
		if (!ret2) {
			System.err.println("Error al Borrar los recodatorios de la cita... " +
					"\nes probable que ya se hayan detonado los triggers");
		}

		return ret1;
	}

	public boolean colisionaConCitasUser(Empleado user, Cita cita) {
		ArrayList<Cita> citas = (ArrayList<Cita>) readAllFromUser(user);
		for (Cita candidata : citas) {
			if (logger.isDebugEnabled()) {
				logger.debug("Candidata: " + candidata.getFechaInicio() + " - " + candidata.getFechaFin() + " Cita: "
						+ cita.getFechaInicio() + " - " + cita.getFechaFin() + " Resultado: " + (colisionDeCitas(cita,
								candidata) ? "colisiona" : "no colisiona"));
				;
				String mensaje = cita.getEmpleado()!=null? cita.getEmpleado(): "nulo";
				logger.debug("cita.empleado: " + mensaje);
				mensaje = user.getCredencial()!=null? user.getCredencial().toString(): "nulo";
				logger.debug("user.getCredencial: " + mensaje);
				mensaje= user.getCredencial()!=null? (user.getCredencial().getUsuario()!=null? user.getCredencial().getUsuario(): "nulo"):"nulo";
				logger.debug("user.getCredencial.getUsuario: " + mensaje);
			}

			if (colisionDeCitas(cita, candidata)) {
				return true;
			}
		}
		return false;
	}

	private boolean colisionDeCitas(Cita A, Cita B) {
		logger.debug("Id cita 1: " + A.getId() + " - Id cita 2: " + B.getId());
		if (A != null && A.equals(B)) {
			return false;
		}
		if (B.getFechaFin().isBefore(A.getFechaInicio())) {
			return false;
		} else if (A.getFechaFin().isBefore(B.getFechaInicio())) {
			return false;
		} else {
			return true;
		}

	}


	public List<Cita> readAllFromUser(Empleado user) {
		List<Cita> ret = new ArrayList<>();
		for (Cita cita : readAll()) {
			if (cita.getEmpleado().equals(user.getCredencial().getUsuario())) {
				ret.add(cita);
			}
		}

		return ret;
	}

	public List<Cita> readAll() {
		return dao.readAllActives();
	}

}
