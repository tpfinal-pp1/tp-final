package com.PubliciBot.DM;

/**
 * Created by Max on 6/10/2017.
 */
public enum  UnidadMedida {
    SEMANA,
    MES,
    BIMESTRE,
    SEMESTRE;


    public int unidadASegundos(){



        switch (this) {
            case SEMANA:
                return 604800;
            case MES:
                return 2419200;
            case BIMESTRE:
                return 4838400;
            case SEMESTRE:
                return 4838400 * 3;

            default: return 0;

        }
    }
}



