package com.TpFinal.services;


import com.TpFinal.data.dao.DAONotificacionImpl;
import com.TpFinal.data.dao.interfaces.DAONotificacion;
import com.TpFinal.dto.notificacion.Notificacion;

import com.google.common.base.Predicate;
import com.google.common.collect.*;

import java.util.*;


public class NotificacionService {

     DAONotificacion dao;
     int  cantNotificacionesNoVistas=-1;



   public NotificacionService(){
       this.dao=new DAONotificacionImpl();
   }

    public boolean addNotificacion(Notificacion p){

        boolean ret= saveOrUpdate(p);
       DashboardEventBus.post(new DashboardEvent.NotificationsCountUpdatedEvent());
       cantNotificacionesNoVistas++;
       return ret;
    }




    public int getUnreadNotificationsCount() {

      //  if(cantNotificacionesNoVistas==-1) {
            Predicate<Notificacion> unreadPredicate = new Predicate<Notificacion>() {
                @Override
                public boolean apply(Notificacion input) {
                    return !input.isVisto()&& itsForCurrentUser(input);
                }
            };
            cantNotificacionesNoVistas = Collections2.filter(dao.readAllActives(), unreadPredicate).size();
    //    }

            return cantNotificacionesNoVistas;

    }



    public boolean itsForCurrentUser(Notificacion input){
        if(CredencialService.getCurrentUser().getCredencial().getUsuario().equals(input.getUsuario()))
            return true;
        if(input.getUsuario().equals("broadcast"))
            return true;

        return false;

    }

    public  Collection<Notificacion> getNotifications() {
        ArrayList<Notificacion> userNotis=new ArrayList<>();
        ArrayList<Notificacion> notificaciones=new ArrayList<>(dao.readAllActives());
        for(Notificacion noti:notificaciones)
            if(itsForCurrentUser(noti))
                userNotis.add(noti);

        setRead(userNotis);
        Collections.sort(userNotis, new Comparator<Notificacion>() {
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
        return userNotis;
    }

    private void setRead(ArrayList<Notificacion> notificaciones){

        cantNotificacionesNoVistas=0;
        for (Notificacion noti:notificaciones
                ) {
            if(itsForCurrentUser(noti)) {
                noti.setVisto(true);
            }
            saveOrUpdate(noti);
        }

    }
    private boolean saveOrUpdate(Notificacion p) {

        return dao.saveOrUpdate(p);
    }



}