package com.TpFinal.services;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ PersonaServiceNuevoIT.class, ContratoServiceTest.class, ProvinciaServiceTest.class,
	 InmuebleServiceIT.class,
	PublicacionServiceTest.class })
public class AllTestsService {
}
