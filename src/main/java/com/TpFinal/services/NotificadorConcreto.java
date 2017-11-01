package com.TpFinal.services;

import com.TpFinal.dto.EstadoRegistro;
import com.TpFinal.dto.notificacion.Notificacion;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class NotificadorConcreto implements Notificable{

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			JobDataMap dataMap = context.getJobDetail().getJobDataMap();
			if(dataMap.size() != 0){
				String mensaje = dataMap.getString("mensaje");
				System.out.println(mensaje);

			}else{
				System.out.println("algo salio mal");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void notificar() {
		
		
	}

}
