package com.TpFinal.view.calendario;

import com.TpFinal.dto.cita.Cita;
import com.TpFinal.dto.cita.TipoCita;
import com.TpFinal.services.CitaService;
import com.TpFinal.utils.DummyDataGenerator;
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


public class MeetingCalendar extends CustomComponent {

    private final Random R = new Random(0);

    private MeetingDataProvider eventProvider;

    private Calendar<MeetingItem> calendar;

    public Panel panel;

    CitaService service;
    public MeetingCalendar() {

        setId("meeting-meetings");
      //  setSizeFull();

        initCalendar();

        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(false);
        layout.setSpacing(false);
      //  layout.setSizeFull();

        panel = new Panel(calendar);
     //   panel.setHeight(100, Unit.PERCENTAGE);
        layout.addComponent(panel);

        setCompositionRoot(layout);
        service=new CitaService();

        refreshCitas();

    }

    public void refreshCitas(){
        service.readAll().forEach(cita->eventProvider.addItem(new MeetingItem(cita)));
    }


    public void switchToMonth(Month month) {
        calendar.withMonth(month);
    }

    public Calendar<MeetingItem> getCalendar() {
        return calendar;
    }

    private void onCalendarRangeSelect(CalendarComponentEvents.RangeSelectEvent event) {

        Cita meeting = new Cita();
        meeting.setCitado(DummyDataGenerator.randomFirstName()+" "+DummyDataGenerator.randomLastName());
        meeting.setTipoDeCita(TipoCita.CelebContrato);
        meeting.setState(Cita.State.planned);
        meeting.setLongTimeEvent(!event.getStart().truncatedTo(DAYS).equals(event.getEnd().truncatedTo(DAYS)));
        meeting.setStart(event.getStart());
        meeting.setEnd(event.getEnd());
        eventProvider.addItem(new MeetingItem(meeting));
	}

    private void onCalendarClick(CalendarComponentEvents.ItemClickEvent event) {

        MeetingItem item = (MeetingItem) event.getCalendarItem();

        final Cita meeting = item.getMeeting();

        Notification.show(meeting.getName(), meeting.getDetails(), Type.HUMANIZED_MESSAGE);
    }

    private void initCalendar() {

        eventProvider = new MeetingDataProvider();

        calendar = new Calendar<>(eventProvider);

        calendar.addStyleName("meetings");
        calendar.setWidth(100.0f, Unit.PERCENTAGE);
        calendar.setHeight(100.0f, Unit.PERCENTAGE);
        calendar.setResponsive(true);

        calendar.setItemCaptionAsHtml(true);
        calendar.setContentMode(ContentMode.HTML);

//        calendar.setLocale(Locale.JAPAN);
//        calendar.setZoneId(ZoneId.of("America/Chicago"));
//        calendar.setWeeklyCaptionProvider(date ->  "<br>" + DateTimeFormatter.ofPattern("dd.MM.YYYY", getLocale()).format(date));
//        calendar.setWeeklyCaptionProvider(date -> DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(getLocale()).format(date));

        calendar.withVisibleDays(1, 7);
//        calendar.withMonth(ZonedDateTime.now().getMonth());

        calendar.setStartDate(ZonedDateTime.of(2017, 9, 10, 0,0,0, 0, calendar.getZoneId()));
        calendar.setEndDate(ZonedDateTime.of(2017, 9, 16, 0,0,0, 0, calendar.getZoneId()));

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
    }

    private final class MeetingDataProvider extends BasicItemProvider<MeetingItem> {

        void removeAllEvents() {
            this.itemList.clear();
            fireItemSetChanged();
        }
    }

}

