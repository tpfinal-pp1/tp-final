package com.TpFinal.UnitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.TpFinal.dto.inmueble.ClaseInmueble;
import com.TpFinal.dto.inmueble.Coordenada;
import com.TpFinal.dto.inmueble.CriterioBusqInmueble;
import com.TpFinal.dto.inmueble.Direccion;
import com.TpFinal.dto.inmueble.EstadoInmueble;
import com.TpFinal.dto.inmueble.Inmueble;
import com.TpFinal.dto.inmueble.TipoInmueble;
import com.TpFinal.dto.persona.Persona;
import com.TpFinal.view.persona.FiltroInteresados;

public class FiltroInteresadosTest {

    @Test
	public void testAEstrenar() {
	    Persona p = instancia ("1");
	    Inmueble i = unInmuebleNoPublicado();
	    FiltroInteresados filtro = new FiltroInteresados();
	    
	    i.setaEstrenar(true);
	    p.setPrefBusqueda(new CriterioBusqInmueble.Builder().setaEstrenar(true).build());	    
	    filtro = new FiltroInteresados(i);	    
	    assertTrue(filtro.getaEstrenar().test(p));
	    i.setaEstrenar(false);
	    filtro = new FiltroInteresados(i);
	    assertFalse(filtro.getaEstrenar().test(p));	    	 
	}
    
    public void testAmbientes() {
	    Persona p = instancia ("1");
	    Inmueble i = unInmuebleNoPublicado();
	    FiltroInteresados filtro = new FiltroInteresados();
	    
	    i.setCantidadAmbientes(10);
	    p.setPrefBusqueda(new CriterioBusqInmueble.Builder().setMinCantAmbientes(5).setMaxCantAmbientes(10).build());	    
	    filtro = new FiltroInteresados(i);	    
	    assertTrue(filtro.getMinAmbientes().test(p));
	    assertTrue(filtro.getMaxAmbientes().test(p));
	    i.setCantidadAmbientes(4);
	    filtro = new FiltroInteresados(i);
	    assertFalse(filtro.getMinAmbientes().test(p));
	    assertTrue(filtro.getMaxAmbientes().test(p));   
	    i.setCantidadAmbientes(11);
	    filtro = new FiltroInteresados(i);
	    assertFalse(filtro.getMinAmbientes().test(p));
	    assertFalse(filtro.getMaxAmbientes().test(p));
	}
    
   
	
	  private Inmueble unInmuebleNoPublicado() {
			return new Inmueble.Builder()
				.setaEstrenar(true)
				.setCantidadAmbientes(2)
				.setCantidadCocheras(3)
				.setCantidadDormitorios(1)
				.setClaseInmueble(ClaseInmueble.Casa)
				.setConAireAcondicionado(true)
				.setConJardin(true).setConParilla(true).setConPileta(true)
				.setDireccion(
					new Direccion.Builder()
						.setCalle("Una calle")
						.setCodPostal("asd123")
						.setCoordenada(new Coordenada())
						.setLocalidad("una Localidad")
						.setNro(123)
						.setPais("Argentina")
						.setProvincia("Buenos Aires")
						.build())
				.setEstadoInmueble(EstadoInmueble.NoPublicado)
				.setSuperficieCubierta(200)
				.setSuperficieTotal(400)
				.setTipoInmueble(TipoInmueble.Vivienda)
				.build();
		    }
	   
	  public Persona instancia(String numero) {
	        return new Persona.Builder()
	                .setNombre("nombre "+numero)
	                .setApellido("apellido "+numero)
	                .setMail("mail "+numero)
	                .setTelefono("telefono "+numero)
	                .setTelefono("telefono "+numero)
	                .setTelefono2("telefono2 "+numero)
	                .setDNI("Dni"+numero)
	                .setinfoAdicional("Info Adicional"+ numero)
	                .build();
	    }

}
