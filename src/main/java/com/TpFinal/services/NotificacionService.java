package com.TpFinal.services;


import com.TpFinal.data.dao.DAONotificacionImpl;
import com.TpFinal.data.dao.interfaces.DAONotificacion;
import com.TpFinal.dto.notificacion.Notificacion;
import com.TpFinal.dto.persona.Empleado;

import com.google.common.base.Predicate;
import com.google.common.collect.*;
import java.util.*;


public class NotificacionService {

    DAONotificacion dao;



   public NotificacionService(){
       this.dao=new DAONotificacionImpl();
   }

    public boolean addNotificacion(Notificacion p){
        boolean ret= saveOrUpdate(p);
       DashboardEventBus.post(new DashboardEvent.NotificationsCountUpdatedEvent());
       return ret;
    }



    public int getUnreadNotificationsCount() {

        Predicate<Notificacion> unreadPredicate = new Predicate<Notificacion>() {
            @Override
            public boolean apply(Notificacion input) {
                return !input.isVisto();
            }
        };
        return Collections2.filter(dao.readAllActives(), unreadPredicate).size();
    }


    public Collection<Notificacion> getNotifications() {
        ArrayList<Notificacion> notificaciones=new ArrayList<>(dao.readAllActives());
        setRead(notificaciones);
        Collections.sort(notificaciones, new Comparator<Notificacion>() {
            @Override
            public int compare(Notificacion o1, Notificacion o2) {
               if(o1.getId()>o2.getId()){
                   return -1;
               }
               else{
                   return 1;
               }

            }
        });
        return notificaciones;
    }

    private void setRead(ArrayList<Notificacion> notificaciones){
        for (Notificacion noti:notificaciones
                ) {
            noti.setVisto(true);
            saveOrUpdate(noti);
        }

    }
    private boolean saveOrUpdate(Notificacion p) {
        return dao.saveOrUpdate(p);
    }



}