package com.TpFinal.services;


import com.TpFinal.data.dao.DAONotificacionImpl;
import com.TpFinal.data.dao.interfaces.DAONotificacion;
import com.TpFinal.dto.notificacion.Notificacion;
import com.TpFinal.dto.persona.Empleado;

import com.google.common.base.Predicate;
import com.google.common.collect.*;
import org.mockito.internal.matchers.Not;

import java.util.*;


public class NotificacionService {

    static DAONotificacion dao;
    static NotificacionService instancia;
    static int  cantNotificacionesNoVistas=-1;



   private NotificacionService(){
       this.dao=new DAONotificacionImpl();
   }

    public static boolean addNotificacion(Notificacion p){
        get();
        boolean ret= saveOrUpdate(p);
       DashboardEventBus.post(new DashboardEvent.NotificationsCountUpdatedEvent());
       cantNotificacionesNoVistas++;
       return ret;
    }


    public static NotificacionService get(){
        if(instancia==null)
            instancia=new NotificacionService();
        return instancia;
    }

    public static int getUnreadNotificationsCount() {
        get();
        if(cantNotificacionesNoVistas==-1) {
            Predicate<Notificacion> unreadPredicate = new Predicate<Notificacion>() {
                @Override
                public boolean apply(Notificacion input) {
                    return !input.isVisto();
                }
            };
            cantNotificacionesNoVistas = Collections2.filter(dao.readAllActives(), unreadPredicate).size();
        }

            return cantNotificacionesNoVistas;

    }


    public static  Collection<Notificacion> getNotifications() {
        get();
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

    private static void setRead(ArrayList<Notificacion> notificaciones){
        get();
        cantNotificacionesNoVistas=0;
        for (Notificacion noti:notificaciones
                ) {
            noti.setVisto(true);
            saveOrUpdate(noti);
        }

    }
    private static boolean saveOrUpdate(Notificacion p) {
        get();
        return dao.saveOrUpdate(p);
    }



}