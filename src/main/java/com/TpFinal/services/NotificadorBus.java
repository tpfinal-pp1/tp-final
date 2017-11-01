package com.TpFinal.services;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.TpFinal.dto.notificacion.Notificacion;

public class NotificadorBus implements Job{
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			JobDataMap dataMap = context.getJobDetail().getJobDataMap();
			if(dataMap.size() != 0){
				String mensaje = dataMap.getString("mensaje");
				String titulo = dataMap.getString("titulo");
				
				Notificacion noti = new Notificacion();
				noti.setMensaje(mensaje);
				noti.setTitulo(titulo);
				
				DataProviderImpl dtp=new DataProviderImpl();
				dtp.addNotificacion(noti);
				
			}else{
				System.out.println("algo salio mal");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
