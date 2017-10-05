package com.TpFinal.view;

import com.TpFinal.view.ContratoView.ContratoABMView;
import com.TpFinal.view.dummy.dashboard.DashboardView;
import com.TpFinal.view.dummy.reports.ReportsView;
import com.TpFinal.view.dummy.schedule.ScheduleView;
import com.TpFinal.view.inmuebles.InmuebleABMView;
import com.TpFinal.view.operacion.PublicacionABMView;
import com.TpFinal.view.persona.PersonaABMView;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.Resource;

public enum DashboardViewType {
    INICIO("dashboard", DashboardView.class, VaadinIcons.HOME, true),
    PERSONAS("personas",PersonaABMView.class, VaadinIcons.USER, false),
    OPERACIONES("publicaciones", PublicacionABMView.class, VaadinIcons.BOOKMARK_O, false),
    INMUEBLES("inmuebles",InmuebleABMView.class, VaadinIcons.BUILDING,false),
    REPORTES("reportes", ReportsView.class, VaadinIcons.FILE, false),
    SCHEDULE("calendario", ScheduleView.class, VaadinIcons.CALENDAR_O, false),
    CONTRATOS("contratos", ContratoABMView.class, VaadinIcons.BOOKMARK_O, false);

    private final String viewName;
    private final Class<? extends View> viewClass;
    private final Resource icon;
    private final boolean stateful;

    private DashboardViewType(final String viewName,
            final Class<? extends View> viewClass, final Resource icon,
            final boolean stateful) {
        this.viewName = viewName;
        this.viewClass = viewClass;
        this.icon = icon;
        this.stateful = stateful;
    }

    public boolean isStateful() {
        return stateful;
    }

    public String getViewName() {
        return viewName;
    }

    public Class<? extends View> getViewClass() {
        return viewClass;
    }

    public Resource getIcon() {
        return icon;
    }

    public static DashboardViewType getByViewName(final String viewName) {
        DashboardViewType result = null;
        for (DashboardViewType viewType : values()) {
            if (viewType.getViewName().equals(viewName)) {
                result = viewType;
                break;
            }
        }
        return result;
    }

}
