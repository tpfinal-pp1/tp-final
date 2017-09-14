package com.TpFinal;

import com.TpFinal.DM.AccionPublicitaria;
import com.TpFinal.DM.Medio;
import com.TpFinal.DM.Mensaje;
import com.TpFinal.DM.TipoMedio;
import com.TpFinal.Services.AccionPublicitariaService;
import org.junit.Test;

/**
 * Created by Hugo on 13/06/2017.
 */
public class MedioServiceTest {

@Test
    public void enviarMailTest()
    {
        AccionPublicitaria accionPublicitaria=new AccionPublicitaria();
        Medio medio = new Medio();

        Mensaje mensaje = new Mensaje("Mail enviado desde enviarMailTest!!",  "src/main/resources/20170629005818698.png");

        medio.setTipoMedio(TipoMedio.EMAIL);

        AccionPublicitariaService aps=new AccionPublicitariaService();
        AccionPublicitaria AP=new AccionPublicitaria();

        AP.setDestino("megafonomailer@gmail.com");

        aps.publicar(AP, mensaje);
    }

}
