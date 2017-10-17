package com.TpFinal.data.dao;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ DAOContratoAlquilerIT.class, DAOContratoVentaIT.class, DAOInmuebleImplTest.class,
		DAOPersonaImplTest.class, DAOContratoImplTest.class })
public class AllTestsDAO {
	
}
