package com.TpFinal.data.dto.persona;



public enum TipoRolPersona {
    Inquilino,Propietario,Interesado,Empleado;




    public Class fromEnum(TipoRolPersona tipoRolPersona){
        switch (tipoRolPersona){
            case Inquilino: return com.TpFinal.data.dto.persona.Inquilino.class;
            default: return null;

        }

    }







}
