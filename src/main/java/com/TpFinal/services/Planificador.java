package com.TpFinal.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.TpFinal.dto.notificacion.NotificadorJob;
import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;

import com.TpFinal.dto.cita.Cita;
import com.TpFinal.dto.cita.TipoCita;
import com.TpFinal.dto.cobro.Cobro;
import com.TpFinal.dto.contrato.ContratoAlquiler;
import com.TpFinal.dto.interfaces.Messageable;

public class Planificador {

	Scheduler sc;
	Job notificacion;
	// citas
	Integer horasAntesRecoradatorio1;
	Integer horasAntesRecoradatorio2;
	// cobros vencidos
	Integer horasAntesCobrosVencidos;
	LocalTime horaInicioCobrosVencidos;

	private static Planificador instancia;
	public static boolean demoIniciado = false;

	public static Planificador get() {
		if (instancia == null) {
			try {
				instancia = new Planificador();
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
		}

		return instancia;
	}

	private Planificador() throws SchedulerException {
		if (sc == null)
			this.sc = StdSchedulerFactory.getDefaultScheduler();
		// Luego se remplaza por la info de la bd
		horasAntesRecoradatorio1 = 1;
		horasAntesRecoradatorio2 = 24;
		horasAntesCobrosVencidos = 240;
		horaInicioCobrosVencidos = LocalTime.of(19, 00, 00);
	}

	public void setNotificacion(Job notificacion) {
		this.notificacion = notificacion;
	}

	public void encender() {
		try {
			if (!sc.isStarted())
				try {
					sc.start();
				} catch (SchedulerException e) {
					e.printStackTrace();
				}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	public void apagar() {
		try {
			if (sc.isStarted())
				try {
					sc.shutdown();
				} catch (SchedulerException e) {
					e.printStackTrace();
				}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	public void agregarNotificaciones(ContratoAlquiler c) {
		c.getCobros().forEach(c1 -> this.addJobCobroVencido(c1));
	}

	public void addJobCita(Cita cita) {
		if (cita.getId() != null) {
			agregarJobNotificacionCita(cita, horasAntesRecoradatorio1, 1);
			agregarJobNotificacionCita(cita, horasAntesRecoradatorio2, 2);
		} else
			throw new IllegalArgumentException("La cita debe estar persistida");
	}

	public void addJobCita(Cita cita, Integer hsAntesRecordatorio1, Integer hsAntesRecordatorio2) {
		if (cita.getId() != null) {
			agregarJobNotificacionCita(cita, hsAntesRecordatorio1, 1);
			agregarJobNotificacionCita(cita, hsAntesRecordatorio2, 2);
		} else
			throw new IllegalArgumentException("La cita debe estar persistida");
	}

	public boolean removeJobCita(Cita cita) {
		boolean ret = false;
		try {
			if (cita.getId() != null) {

				return sc.unscheduleJob(TriggerKey.triggerKey(String.valueOf(cita.getId()) + "-1")) &&
						sc.unscheduleJob(TriggerKey.triggerKey(String.valueOf(cita.getId()) + "-2"));
			} else
				throw new IllegalArgumentException("La cita debe estar persistida");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public void addJobCobroVencido(Cobro cobro) {
		if (cobro.getId() != null) {
			agregarJobNotificacionCobro(cobro, horasAntesCobrosVencidos, 1);
		} else
			throw new IllegalArgumentException("El Cobro debe estar persistida");
	}

	public boolean removeJobCobroVencido(Cobro cobro) {
		boolean ret = false;
		try {
			if (cobro.getId() != null) {

				return sc.unscheduleJob(TriggerKey.triggerKey(String.valueOf(cobro.getId()) + "-1"));
			} else
				throw new IllegalArgumentException("El Cobro debe estar persistida");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public void agregarJobs(List<Messageable> citas) {
		if (citas != null && citas.size() > 0) {
			citas.forEach(c -> {
				if (c instanceof Cita) {
					Cita c1 = (Cita) c;
					agregarJobNotificacionCita(c1, horasAntesRecoradatorio1, 1);
					agregarJobNotificacionCita(c1, horasAntesRecoradatorio2, 2);
				} else if (c instanceof Cobro) {
					Cobro c1 = (Cobro) c;
					agregarJobNotificacionCobro(c1, horasAntesCobrosVencidos, 1);
				}

			});
		}
	}

	public void agregarJobNotificacionSistema(String titulo, String mensaje, String username, LocalDateTime fechaInicio,
			LocalDateTime fechaFin, String perioricidad, String id) {
		try {

			String horan = "0 " + fechaInicio.getMinute() + " " + fechaInicio.getHour();
			horan = horan + " "+perioricidad;
			horan = horan + " * ? *";
			JobDetail j1 = JobBuilder.newJob(notificacion.getClass())
					.usingJobData("mensaje", mensaje)
					.usingJobData("titulo", titulo)
					.usingJobData("idCita", id)
					.usingJobData("usuario", username)
					.build();
			j1.getKey();

			Date startDate = Date.from(fechaInicio.minusMinutes(1).atZone(ZoneId.systemDefault()).toInstant());
			Date endDate = Date.from(fechaFin.plusMinutes(1).atZone(ZoneId.systemDefault()).toInstant());

			Trigger t = TriggerBuilder.newTrigger().withIdentity(id)
					.startAt(startDate)
					.withSchedule(CronScheduleBuilder.cronSchedule(horan))
					.endAt(endDate)
					.build();
			sc.scheduleJob(j1, t);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	private void agregarJobNotificacionCita(Cita c, Integer horas, Integer key) {
		LocalDateTime fechaInicio = c.getFechaInicio();
		fechaInicio = fechaInicio.minusHours(horas);
		LocalDateTime fechaFin = c.getFechaInicio();
		Integer perioricidad = horas + 1;
		String triggerKey = c.getId().toString() + "-" + key.toString();
		String username = c.getEmpleado();
		agregarJobNotificacionSistema(c.getTitulo(), c.getMessage(), username, fechaInicio, fechaFin, "1/1", triggerKey);
	}

	private void agregarJobNotificacionCobro(Cobro c, Integer horas, Integer key) {
		LocalDateTime fechaInicio = LocalDateTime.of(c.getFechaDeVencimiento(), LocalTime.now().plusMinutes(1));
		LocalDateTime fechaFin = LocalDateTime.of(c.getFechaDeVencimiento(), LocalTime.now().plusMinutes(10));
		fechaInicio = fechaInicio.minusHours(horas);
		fechaFin = fechaFin.minusHours(horas);
		Integer perioricidad = horas + 1;
		String triggerKey = c.getId().toString() + "-" + key.toString();
		String username = "broadcast";
		agregarJobNotificacionSistema(c.getTitulo(), c.getMessage(), username, fechaInicio, fechaFin, "1/1", triggerKey);
	}

	public static void initDemo() {
		if (!demoIniciado) {
			try {
				demoIniciado = true;
				Planificador planificador = Planificador.get();
				planificador.encender();
				planificador.setNotificacion(new NotificadorJob());
				List<Messageable> citas = new ArrayList<>();

				for (int i = 0; i < 10; i++) {
					LocalDateTime fInicio = LocalDateTime.now();
					fInicio = fInicio.plusMinutes(i + 2);
					fInicio = fInicio.plusHours(1);

					Cita c = new Cita.Builder()
							.setCitado("Señor " + String.valueOf(i))
							.setDireccionLugar("sarasa: " + String.valueOf(i))
							.setFechahora(fInicio)
							.setObservaciones("obs" + String.valueOf(i))
							.setTipoDeCita(TipoCita.Otros)
							.build();
					c.setId(Long.valueOf(i));

					citas.add(c);
				}
				planificador.agregarJobs(citas);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// TODO Lo dejo porque quizas sirva para otro tipo de notificacion
	@Deprecated
	public void agregarAccion(String mensaje, LocalDate fechaInicio, LocalDate fechaFin, String hora, String minuto,
			String perioricidad, Long id) {
		try {
			String horan = "0 " + minuto + " " + hora;
			horan = horan + " 1/" + perioricidad;
			horan = horan + " * ? *";
			JobDetail j1 = JobBuilder.newJob(notificacion.getClass())
					.usingJobData("mensaje", mensaje)
					.build();

			String fi = fechaInicio.toString() + " 00:00:00.0";
			String ff = fechaFin.toString() + " 00:00:00.0";

			Date startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(fi);
			Date endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(ff);

			Trigger t = TriggerBuilder.newTrigger().withIdentity(id.toString())
					.startAt(startDate)
					.withSchedule(CronScheduleBuilder.cronSchedule(horan))
					.endAt(endDate)
					.build();
			sc.scheduleJob(j1, t);
		} catch (SchedulerException | ParseException e) {
			e.printStackTrace();
		}
	}

	public LocalTime getHoraInicioCobrosVencidos() {
		return horaInicioCobrosVencidos;
	}

	public void setHoraInicioCobrosVencidos(LocalTime horaInicioCobrosVencidos) {
		this.horaInicioCobrosVencidos = horaInicioCobrosVencidos;
	}

}