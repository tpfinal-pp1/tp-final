package com.TpFinal.services;

import java.util.Collection;

import com.TpFinal.dto.notificacion.Notificacion;
import com.TpFinal.dto.persona.Empleado;


/**
 * QuickTickets Dashboard backend API.
 */
public interface DataProvider {
    /**
     * @param count
     *            Number of transactions to fetch.
     * @return A Collection of most recent transactions.
     */


    /**

     */
    Empleado authenticate(String userName, String password);

    /**
     * @return The number of unread notifications for the current user.
     */
    int getUnreadNotificationsCount();

    /**
     * @return Notifications for the current user.
     */
    Collection<Notificacion> getNotifications();
}




