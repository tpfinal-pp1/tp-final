package com.TpFinal.data.dao;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ DAOContratoAlquilerTest.class, DAOContratoVentaTest.class, DAOInmuebleImplTest.class,
		DAOPersonaImplTest.class, DAOContratoImplTest.class })
public class AllTestsDAO {
	
}
