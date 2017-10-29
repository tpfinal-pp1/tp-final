package com.TpFinal.services;

import org.quartz.Job;

public interface Notificable extends Job{
	public void notificar();

}
