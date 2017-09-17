package com.TpFinal.view;

import com.TpFinal.view.adressbook.AddressbookView;
import com.TpFinal.view.dashboard.DashboardView;
import com.TpFinal.view.map.MapView;
import com.TpFinal.view.pdf.PDFView;
import com.TpFinal.view.reports.ReportsView;
import com.TpFinal.view.transactions.TransactionsView;

import com.TpFinal.view.schedule.ScheduleView;
import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;

public enum DashboardViewType {
    INICIO("dashboard", DashboardView.class, FontAwesome.HOME, true),CONTACTOS(
            "contactos",AddressbookView.class, FontAwesome.USER, false), MAPA("mapa", MapView.class, FontAwesome.MAP, false),PDF(
            "PDF", PDFView.class, FontAwesome.FILE_PDF_O, false), TRANSACTIONS(
            "transactions", TransactionsView.class, FontAwesome.TABLE, false), REPORTS(
            "reports", ReportsView.class, FontAwesome.FILE_TEXT_O, true), SCHEDULE(
            "schedule", ScheduleView.class, FontAwesome.CALENDAR_O, false);

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
