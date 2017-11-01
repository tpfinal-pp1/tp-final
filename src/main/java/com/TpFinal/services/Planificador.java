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
	
	static Scheduler sc;
	static Job notificacion;
	static Integer horasAntesRecoradatorio1;
	static Integer horasAntesRecoradatorio2;
	private static Planificador instancia;
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
		this.sc=StdSchedulerFactory.getDefaultScheduler();
		//Luego se remplaza por la info de la bd
		horasAntesRecoradatorio1=1;
		horasAntesRecoradatorio2=24;
	}
	
	public static void setNotificacion(Job notificacion) {
		Planificador.notificacion=notificacion;
	}
	
	public static void encender(){
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
	
	public static void apagar(){
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
	
	public static void agregarNotificaciones(List<Cita>citas) {
		if(citas!=null && citas.size()>0) {
			citas.forEach(c -> {
				agregarNotificacion(c, horasAntesRecoradatorio1);
				agregarNotificacion(c, horasAntesRecoradatorio2);
			});
		}
	}
	
	public static void agregarCita(String titulo, String mensaje, LocalDateTime fechaInicio, LocalDateTime fechaFin, String perioricidad, String id) {
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
	
	private static void agregarNotificacion(Cita c, Integer horas) {
		LocalDateTime fechaInicio= c.getFechaHora();
		fechaInicio=fechaInicio.minusHours(horas);
		LocalDateTime fechaFin=c.getFechaHora();
		Integer perioricidad=horas+1;
		agregarCita(c.getTitulo(), c.getMessage(), fechaInicio, fechaFin, String.valueOf(perioricidad), UUID.randomUUID().toString());
	}
	
	//TODO Lo dejo porque quizas sirva para otro tipo de notificacion
	@Deprecated
	public static void agregarAccion(String mensaje, LocalDate fechaInicio, LocalDate fechaFin,String hora, String minuto, String perioricidad, Long id) {
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
