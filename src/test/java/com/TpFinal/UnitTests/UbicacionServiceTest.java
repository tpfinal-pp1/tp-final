package com.TpFinal.UnitTests;

import com.TpFinal.dto.inmueble.Coordenada;
import com.TpFinal.dto.inmueble.Direccion;
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
    public void downloadMapImageTest(){

        Assert.assertNotNull(uS.getMapImage(new Coordenada(-34.541461,+-58.715379)));


    }

    @Test
    public void downloadJsonGeocodeDataTest(){
        uS.dowloadGeoCodingData(new Direccion.Builder().setLocalidad("San Miguel")
        .setCalle("Domingo Faustino Sarmiento")
                .setNro(1765)
                .setProvincia("Buenos Aires")
                .setCodPostal("1663")
                .setPais("Argentina").build());

    }
}
