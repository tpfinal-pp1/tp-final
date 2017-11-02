package com.TpFinal.services;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.TpFinal.dto.cita.Cita;
import com.TpFinal.dto.cita.TipoCita;
import com.TpFinal.dto.cobro.Cobro;
import com.TpFinal.dto.interfaces.Messageable;

public class Planificador {
	
	 Scheduler sc;
	 Job notificacion;
	 Integer horasAntesRecoradatorio1;
	Integer horasAntesRecoradatorio2;
	Integer horasAntesCobrosVencidos;
	private static Planificador instancia;
	public static boolean demoIniciado=false;
	
	public static Planificador get(){
		if(instancia==null)
			try {
				instancia=new Planificador();
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
		return instancia;
	}
	
	private Planificador() throws SchedulerException {
		if(sc==null)
			this.sc=StdSchedulerFactory.getDefaultScheduler();
		//Luego se remplaza por la info de la bd
		horasAntesRecoradatorio1=1;
		horasAntesRecoradatorio2=24;
		horasAntesCobrosVencidos=24;
	}

	public void setNotificacion(Job notificacion) {
		this.notificacion=notificacion;
	}
	
	public void encender(){
		try {
			if(!sc.isStarted())
            try {
                sc.start();
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	public void apagar(){
		try {
			if(sc.isStarted())
				try {
					sc.shutdown();
				} catch (SchedulerException e) {
					e.printStackTrace();
				}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	public void agregarNotificaciones(List<Messageable>citas) {
		if(citas!=null && citas.size()>0) {
			citas.forEach(c -> {
				if(c instanceof Cita) {
					Cita c1= (Cita)c;
					agregarNotificacionCita(c1, horasAntesRecoradatorio1);
					agregarNotificacionCita(c1, horasAntesRecoradatorio2);
				}else if(c instanceof Cobro) {
					Cobro c1= (Cobro)c;
					agregarNotificacionCobro(c1, horasAntesCobrosVencidos);
				}
				
			});
		}
	}
	
	public void agregarCita(String titulo, String mensaje, LocalDateTime fechaInicio, LocalDateTime fechaFin, String perioricidad, String id) {
		try {
			String horan="0 "+fechaInicio.getMinute()+" "+fechaInicio.getHour();
			horan=horan+" 1/1";
			horan=horan+" * ? *";
			JobDetail j1=JobBuilder.newJob(notificacion.getClass())
					.usingJobData("mensaje", mensaje)
					.usingJobData("titulo", titulo)
					.build();
			
			
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
	
	private void agregarNotificacionCita(Cita c, Integer horas) {
		LocalDateTime fechaInicio= c.getFechaHora();
		fechaInicio=fechaInicio.minusHours(horas);
		LocalDateTime fechaFin=c.getFechaHora();
		Integer perioricidad=horas+1;
		agregarCita(c.getTitulo(), c.getMessage(), fechaInicio, fechaFin, String.valueOf(perioricidad), UUID.randomUUID().toString());
	}
	
	private void agregarNotificacionCobro(Cobro c, Integer horas) {
		//TODO
	}
	
	public static void initDemo(){
		if(!demoIniciado) {
			try {
				demoIniciado = true;
				Planificador planificador = Planificador.get();
				planificador.encender();
				planificador.setNotificacion(new NotificadorBus());
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
				planificador.agregarNotificaciones(citas);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	//TODO Lo dejo porque quizas sirva para otro tipo de notificacion
	@Deprecated
	public void agregarAccion(String mensaje, LocalDate fechaInicio, LocalDate fechaFin,String hora, String minuto, String perioricidad, Long id) {
		try {
			String horan="0 "+minuto+" "+hora;
			horan=horan+" 1/"+perioricidad;
			horan=horan+" * ? *";
			JobDetail j1=JobBuilder.newJob(notificacion.getClass())
					.usingJobData("mensaje", mensaje)
					.build();
			
			String fi = fechaInicio.toString()+" 00:00:00.0";
			String ff = fechaFin.toString()+" 00:00:00.0";
			
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
	

}
