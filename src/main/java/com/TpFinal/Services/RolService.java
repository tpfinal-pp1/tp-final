package com.TpFinal.Services;

import com.TpFinal.DM.Privilegio;
import com.TpFinal.DM.Rol;

import java.util.HashSet;

/**
 * Created by Hugo on 22/05/2017.
 */
public class RolService {
    public Rol crearRol(String nombreRol, HashSet<Privilegio> privilegios)
    {
        return new Rol(nombreRol, privilegios);
    }

    public boolean tienePrivilegio(Class<?> privilegio, Rol rol){

        for(Privilegio<?> privilegio1 : rol.getListaPrivilegios()){
            if(privilegio1.toString().equals(privilegio.toString()))
                return true;
        }
        return false;
    }


}
