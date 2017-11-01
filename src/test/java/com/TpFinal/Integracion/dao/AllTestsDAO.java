package com.TpFinal.Integracion.dao;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.TpFinal.dto.notificacion.Notificacion;

@RunWith(Suite.class)
@SuiteClasses({ DAOCobroImplIT.class, DAOContratoAlquilerIT.class, DAOContratoImplIT.class, DAOContratoVentaIT.class,
	DAOInmuebleImplIT.class, DAOPersonaImplIT.class, DAOCitaImplIT.class,DAORecordatorioImplIT.class, DAOCredencialImplIT.class, DAONotificacionIT.class })
public class AllTestsDAO {

}
