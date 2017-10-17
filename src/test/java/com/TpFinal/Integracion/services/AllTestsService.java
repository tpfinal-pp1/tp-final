package com.TpFinal.Integracion.services;

import com.TpFinal.UnitTests.ProvinciaServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ PersonaServiceNuevoIT.class, ContratoServiceIT.class, ProvinciaServiceTest.class,
	 InmuebleServiceIT.class,
	PublicacionServiceIT.class, CobroServiceIT.class })
public class AllTestsService {
}
