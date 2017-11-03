package com.TpFinal.view.calendario;

import com.TpFinal.dto.cita.Cita;
import com.vaadin.icons.VaadinIcons;
import org.vaadin.addon.calendar.item.BasicItem;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Meeting Pojo
 */

public class MeetingItem extends BasicItem {

	private final Cita meeting;

	/**
	 * constructor
	 *
	 * @param meeting A meeting
	 */

	public MeetingItem(Cita meeting) {
        super(meeting.getName(), null, meeting.getFechaInicio().atZone(ZoneId.systemDefault())
				, meeting.getFechaInicio().atZone(ZoneId.systemDefault()));
        this.meeting = meeting;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof MeetingItem)) {
			return false;
		}
		MeetingItem that = (MeetingItem) o;
		return getMeeting().equals(that.getMeeting());
	}

	public Cita getMeeting() {
		return meeting;
	}

	@Override
	public String getStyleName() {
		return "state-" + meeting.getState().name().toLowerCase();
	}

	@Override
	public int hashCode() {
		return getMeeting().hashCode();
	}

	@Override
	public boolean isAllDay() {
		return meeting.isLongTimeEvent();
	}

    @Override
    public boolean isMoveable() {
        return meeting.isEditable();
    }

    @Override
    public boolean isResizeable() {
        return meeting.isEditable();
    }

//    @Override
//    public boolean isClickable() {
//        return meeting.isEditable();
//    }

    @Override
	public void setEnd(ZonedDateTime end) {
		meeting.setFechaFin(end.toLocalDateTime());
		super.setEnd(end);
	}

	@Override
	public void setStart(ZonedDateTime start) {
		meeting.setFechaInicio(start.toLocalDateTime());
		super.setStart(start);
	}

    @Override
    public String getDateCaptionFormat() {
        //return CalendarItem.RANGE_TIME;
        return VaadinIcons.CLOCK.getHtml()+" %s<br>" +
               VaadinIcons.ARROW_CIRCLE_RIGHT_O.getHtml()+" %s";
	}

}