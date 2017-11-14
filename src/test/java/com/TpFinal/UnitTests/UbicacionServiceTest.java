package com.TpFinal.UnitTests;

import com.TpFinal.services.UbicacionService;
import org.junit.Test;

public class UbicacionServiceTest {

    @Test
    public void downloadMapImageTest(){
        UbicacionService uS=new UbicacionService();
        uS.dowloadGStaticMapsWithMarker("-34.541461,+-58.715379");

    }
}
