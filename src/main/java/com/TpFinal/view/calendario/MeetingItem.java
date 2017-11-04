package com.TpFinal.view.calendario;

import com.TpFinal.dto.cita.Cita;
import com.TpFinal.dto.persona.Credencial;
import com.TpFinal.dto.persona.Empleado;
import com.TpFinal.services.CredencialService;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinSession;
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
				, meeting.getFechaFin().atZone(ZoneId.systemDefault()));
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

		if(meeting.getEmpleado().equals(CredencialService.getCurrentUser().
				getCredencial().getUsuario()))
			return	"state-"+Cita.State.planned.name().toLowerCase();

		return "state-"+Cita.State.confirmed.name().toLowerCase();
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
        return false;
    }

    @Override
    public boolean isResizeable() {
        return false;
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