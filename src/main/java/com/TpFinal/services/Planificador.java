package com.TpFinal.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
import com.TpFinal.dto.notificacion.Notificacion;
import com.itextpdf.text.log.SysoCounter;

public class Planificador {
	
	Scheduler sc;
	Job notificacion;
	Integer horasAntesRecoradatorio1;
	Integer horasAntesRecoradatorio2;
	
	public Planificador() throws SchedulerException {
		this.sc=StdSchedulerFactory.getDefaultScheduler();
		//Luego se remplaza por la info de la bd
		horasAntesRecoradatorio1=1;
		horasAntesRecoradatorio2=24;
	}
	
	public void setNotificacion(Job notificacion) {
		this.notificacion=notificacion;
	}
	
	public void encender(){
		try {
			sc.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	public void apagar(){
		try {
			sc.shutdown();
		} catch (SchedulerException e) {
			e.printStackTrace();
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
	
	public void agregarCita(String titulo, String mensaje, LocalDateTime fechaInicio, LocalDateTime fechaFin, String perioricidad, String id) {
		try {
			String horan="0 "+fechaInicio.getMinute()+" "+fechaInicio.getHour();
			horan=horan+" 1/1";//+perioricidad;
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
	
	public void agregarNotificaciones(List<Cita>citas) {
		if(citas!=null && citas.size()>0) {
			citas.forEach(c -> {
				agregarPrimeraNotificacion(c, this.horasAntesRecoradatorio1);
				agregarPrimeraNotificacion(c, this.horasAntesRecoradatorio2);
			});
		}
	}
	
	private void agregarPrimeraNotificacion(Cita c, Integer horas) {
		LocalDateTime fechaInicio= c.getFechaHora();
		fechaInicio=fechaInicio.minusHours(horas);
		LocalDateTime fechaFin=c.getFechaHora();
		Integer perioricidad=horas+1;
		this.agregarCita(c.getTitulo(), c.getMessage(), fechaInicio, fechaFin, String.valueOf(perioricidad), UUID.randomUUID().toString());
	}
	
//	private void agregarSegundaNotificacion(Cita c) {
//		LocalDateTime fechaInicio= c.getFechaHora();
//		fechaInicio=fechaInicio.minusHours(horasAntesRecoradatorio2);
//		LocalDateTime fechaFin=c.getFechaHora();
//		Integer perioricidad=this.horasAntesRecoradatorio2+1;
//		this.agregarCita(c.getTitulo(), c.getMessage(), fechaInicio, fechaFin, String.valueOf(perioricidad), UUID.randomUUID().toString());
//	}

}
