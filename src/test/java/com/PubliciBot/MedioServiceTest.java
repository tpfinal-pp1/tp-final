package com.PubliciBot;

import com.PubliciBot.DM.AccionPublicitaria;
import com.PubliciBot.DM.Medio;
import com.PubliciBot.DM.Mensaje;
import com.PubliciBot.DM.TipoMedio;
import com.PubliciBot.Services.AccionPublicitariaService;
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
