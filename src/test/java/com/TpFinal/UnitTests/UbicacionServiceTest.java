package com.TpFinal.UnitTests;

import com.TpFinal.dto.inmueble.Coordenada;
import com.TpFinal.dto.inmueble.Direccion;
import com.TpFinal.dto.inmueble.Inmueble;
import com.TpFinal.services.InmuebleService;
import com.TpFinal.services.UbicacionService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UbicacionServiceTest {
    UbicacionService uS;
    @Before
    public void setUp(){
         uS=new UbicacionService();
    }


    @Test
    public void downloadJsonGeocodeDataAndStaticMapTest(){
        Inmueble inmueble= InmuebleService.getInstancia();
        Direccion dir=new Direccion.Builder().setLocalidad("San Miguel")
                .setCalle("Libertad")
                .setNro(2136)
                .setProvincia("Buenos Aires")
                .setPais("Argentina").build();

        Coordenada coordenada=uS.geoCode(dir);
        dir.setCoordenada(coordenada);
        inmueble.setDireccion(dir);
        Assert.assertNotNull( coordenada);
        Assert.assertNotNull(uS.getMapImage(inmueble));
    }

}
