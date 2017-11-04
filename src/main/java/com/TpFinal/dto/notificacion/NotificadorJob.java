package com.TpFinal.dto.notificacion;

import com.TpFinal.services.NotificacionService;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.TpFinal.dto.notificacion.Notificacion;

public class NotificadorJob implements Job{
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			JobDataMap dataMap = context.getJobDetail().getJobDataMap();
			if(dataMap.size() != 0){

				String mensaje = dataMap.getString("mensaje");
				String titulo = dataMap.getString("titulo");
				String usaurio=dataMap.getString("usuario");
				String idCita=dataMap.getString("idCita");
				Notificacion noti = new Notificacion();
				noti.setUsuario(usaurio);
				noti.setIdCita(idCita);
				noti.setMensaje(mensaje);
				noti.setTitulo(titulo);
				NotificacionService nS=new NotificacionService();
				nS.addNotificacion(noti);
				
			}else{
				System.out.println("algo salio mal");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
