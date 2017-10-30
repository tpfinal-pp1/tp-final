package com.TpFinal.utils;

import com.TpFinal.dto.*;
import com.TpFinal.dto.persona.Empleado;
import com.TpFinal.services.CredencialService;
import com.google.common.base.Predicate;
import com.google.common.collect.*;
import java.util.*;


public class DataProviderImpl implements DataProvider {



    private final Collection<DashboardNotification> notifications = DummyDataGenerator
            .randomNotifications();


    @Override
    public Empleado authenticate(String userName, String password) {
        CredencialService credServ=new CredencialService();
        return credServ.logIn(userName,password);
    }

    @Override
    public int getUnreadNotificationsCount() {
        Predicate<DashboardNotification> unreadPredicate = new Predicate<DashboardNotification>() {
            @Override
            public boolean apply(DashboardNotification input) {
                return !input.isRead();
            }
        };
        return Collections2.filter(notifications, unreadPredicate).size();
    }

    @Override
    public Collection<DashboardNotification> getNotifications() {
        for (DashboardNotification notification : notifications) {
            notification.setRead(true);
        }
        return Collections.unmodifiableCollection(notifications);
    }




}