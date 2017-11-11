package com.TpFinal.view.calendario;

import com.TpFinal.dto.cita.Cita;
import com.TpFinal.dto.persona.CategoriaEmpleado;
import com.TpFinal.dto.persona.Empleado;
import com.TpFinal.services.CitaService;
import com.TpFinal.services.CredencialService;
import com.TpFinal.view.calendario.MeetingItem;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.Notification.Type;
import org.vaadin.addon.calendar.Calendar;
import org.vaadin.addon.calendar.handler.BasicDateClickHandler;
import org.vaadin.addon.calendar.item.BasicItemProvider;
import org.vaadin.addon.calendar.ui.CalendarComponentEvents;

import java.time.Month;
import java.time.ZonedDateTime;
import java.util.GregorianCalendar;
import java.util.Random;

import static java.time.temporal.ChronoUnit.DAYS;


public abstract class MeetingCalendar extends CustomComponent {

    private final Random R = new Random(0);

    private MeetingDataProvider eventProvider;

    private Calendar<MeetingItem> calendar;

    CitaService service= new CitaService();


    public Panel panel;

    public MeetingCalendar() {

        setId("meeting-meetings");
        initCalendar();
        setCompositionRoot(calendar);


        refreshCitas();

    }

    public void switchToMonth(Month month) {
        calendar.withMonth(month);
    }

    public Calendar<MeetingItem> getCalendar() {
        return calendar;
    }

    public void refreshCitas(){
        eventProvider.removeAllEvents();

        if (CredencialService.getCurrentUser().getCategoriaEmpleado().equals(CategoriaEmpleado.admin))
            service.readAll().
                    forEach(cita->eventProvider.addItem(new MeetingItem(cita)));
        else
            service.readAllFromUser(CredencialService.getCurrentUser()).
                    forEach(cita->eventProvider.addItem(new MeetingItem(cita)));


    }

    public abstract void onCalendarRangeSelect(CalendarComponentEvents.RangeSelectEvent event);



    public abstract void onCalendarClick(CalendarComponentEvents.ItemClickEvent event);
    private void initCalendar() {

        eventProvider = new MeetingDataProvider();

        calendar = new Calendar<>(eventProvider);
       //calendar.setSizeFull();

        calendar.setStyleName("meetings");
        calendar.setWidth(100.0f, Unit.PERCENTAGE);
        calendar.setHeight(90.0f, Unit.PERCENTAGE);
        calendar.setResponsive(true);


        calendar.setItemCaptionAsHtml(true);
        calendar.setContentMode(ContentMode.HTML);
        calendar.setResponsive(true);
//        calendar.setLocale(Locale.JAPAN);
//        calendar.setZoneId(ZoneId.of("America/Chicago"));
//        calendar.setWeeklyCaptionProvider(date ->  "<br>" + DateTimeFormatter.ofPattern("dd.MM.YYYY", getLocale()).format(date));
//        calendar.setWeeklyCaptionProvider(date -> DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(getLocale()).format(date));

        calendar.withVisibleDays(1, 7);
//        calendar.withMonth(ZonedDateTime.now().getMonth());

     //   calendar.setStartDate(ZonedDateTime.of(2017, 9, 10, 0,0,0, 0, calendar.getZoneId()));
    //    calendar.setEndDate(ZonedDateTime.of(2017, 9, 16, 0,0,0, 0, calendar.getZoneId()));

        addCalendarEventListeners();

        setupBlockedTimeSlots();
    }

    private void setupBlockedTimeSlots() {

        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(java.util.Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
        cal.clear(java.util.Calendar.MINUTE);
        cal.clear(java.util.Calendar.SECOND);
        cal.clear(java.util.Calendar.MILLISECOND);

        GregorianCalendar bcal = new GregorianCalendar(UI.getCurrent().getLocale());
        bcal.clear();

        long start = bcal.getTimeInMillis();

        bcal.add(java.util.Calendar.HOUR, 7);
        bcal.add(java.util.Calendar.MINUTE, 30);
        long end = bcal.getTimeInMillis();

        calendar.addTimeBlock(start, end, "my-blocky-style");

        cal.add(java.util.Calendar.DAY_OF_WEEK, 1);

        bcal.clear();
        bcal.add(java.util.Calendar.HOUR, 14);
        bcal.add(java.util.Calendar.MINUTE, 30);
        start = bcal.getTimeInMillis();

        bcal.add(java.util.Calendar.MINUTE, 60);
        end = bcal.getTimeInMillis();

        calendar.addTimeBlock(start, end);

    }



    private void addCalendarEventListeners() {
        calendar.setHandler(new BasicDateClickHandler(true));
        calendar.setHandler(this::onCalendarClick);
        calendar.setHandler(this::onCalendarRangeSelect);
        calendar.setHandler(this::onItemMoveHandler);
    }

    private void onItemMoveHandler(CalendarComponentEvents.ItemMoveEvent itemMoveEvent) {
        MeetingItem item = (MeetingItem) itemMoveEvent.getCalendarItem();

        final Cita cita = item.getMeeting();
        service.editCita(cita);
        refreshCitas();
    }

    private final class MeetingDataProvider extends BasicItemProvider<MeetingItem> {

        void removeAllEvents() {
            this.itemList.clear();
            fireItemSetChanged();

        }
    }

}
