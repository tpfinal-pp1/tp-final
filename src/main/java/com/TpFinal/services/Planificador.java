package com.TpFinal.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.itextpdf.text.log.SysoCounter;

public class Planificador {
	
	Scheduler sc;
	Notificable notificacion;
	
	public Planificador() throws SchedulerException {
		this.sc=StdSchedulerFactory.getDefaultScheduler();
	}
	
	public void setNotificacion(Notificable notificacion) {
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
