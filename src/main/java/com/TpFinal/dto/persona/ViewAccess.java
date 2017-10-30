package com.TpFinal.dto.persona;

import com.TpFinal.view.DashboardViewType;

import java.util.ArrayList;

public enum ViewAccess {

    Admin(null),BLOQUEADO(null),Agente(null);
    ArrayList<DashboardViewType> views;

   private ViewAccess(ArrayList<DashboardViewType> dato){
       this.views=dato;
    }


}
