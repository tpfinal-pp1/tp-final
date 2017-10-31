package com.TpFinal.services;


import com.TpFinal.dto.notificacion.Notificacion;
import com.TpFinal.dto.persona.Empleado;
import com.TpFinal.utils.DummyDataGenerator;
import com.google.common.base.Predicate;
import com.google.common.collect.*;
import java.util.*;


public class DataProviderImpl implements DataProvider {



    private final Collection<Notificacion> notifications = DummyDataGenerator
            .randomNotifications();


    @Override
    public Empleado authenticate(String userName, String password) {
        CredencialService credServ=new CredencialService();
        return credServ.logIn(userName,password);
    }

    @Override
    public int getUnreadNotificationsCount() {

        Predicate<Notificacion> unreadPredicate = new Predicate<Notificacion>() {
            @Override
            public boolean apply(Notificacion input) {
                return !input.isVisto();
            }
        };
        return Collections2.filter(notifications, unreadPredicate).size();
    }

    @Override
    public Collection<Notificacion> getNotifications() {
        for (Notificacion notification : notifications) {
            notification.setVisto(true);
        }

        return Collections.unmodifiableCollection(notifications);
    }




}