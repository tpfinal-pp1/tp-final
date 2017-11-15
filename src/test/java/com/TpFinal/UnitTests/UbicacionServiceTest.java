package com.TpFinal.UnitTests;

import com.TpFinal.dto.inmueble.Coordenada;
import com.TpFinal.services.UbicacionService;
import org.junit.Test;

public class UbicacionServiceTest {

    @Test
    public void downloadMapImageTest(){
        UbicacionService uS=new UbicacionService();
        uS.getMapImage(new Coordenada(-34.541461,+-58.715379));

    }
}
