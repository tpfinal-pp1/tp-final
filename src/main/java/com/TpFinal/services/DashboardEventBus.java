package com.TpFinal.services;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;
import com.TpFinal.DashboardUI;

/**
 * A simple wrapper for Guava event bus. Defines static convenience methods for
 * relevant actions.
 */
public class DashboardEventBus implements SubscriberExceptionHandler {

    static DashboardEventBus instancia;
    private final EventBus eventBus = new EventBus(this);
private DashboardEventBus(){

}

    public static void post(final Object event) {

        get().eventBus.post(event);
    }

    public static void register(final Object object) {
        get().eventBus.register(object);
    }

    public static void unregister(final Object object) {
        get().eventBus.unregister(object);
    }

    public static DashboardEventBus get(){
        if (instancia==null)
            instancia=new DashboardEventBus();
        return instancia;
    }

    @Override
    public final void handleException(final Throwable exception,
            final SubscriberExceptionContext context) {
        exception.printStackTrace();
    }
}
