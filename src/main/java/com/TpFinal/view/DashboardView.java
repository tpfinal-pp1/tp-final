package com.TpFinal.view;

import com.TpFinal.DashboardUI;
import com.TpFinal.dto.cita.Cita;
import com.TpFinal.dto.notificacion.Notificacion;
import com.TpFinal.dto.persona.Empleado;
import com.TpFinal.services.CitaService;
import com.TpFinal.services.DashboardEvent;
import com.TpFinal.services.DashboardEventBus;
import com.TpFinal.services.NotificacionService;
import com.TpFinal.view.calendario.CitaFormWindow;
import com.TpFinal.view.calendario.MeetingCalendar;
import com.TpFinal.view.calendario.MeetingItem;
import com.TpFinal.view.parametros.ParametrosDelSistemaWindow;
import com.google.common.eventbus.Subscribe;
import com.vaadin.annotations.Title;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.MarginInfo;
import org.ocpsoft.prettytime.PrettyTime;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.util.CurrentInstance;
import org.vaadin.addon.calendar.ui.CalendarComponentEvents;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.*;
import java.util.*;

@SuppressWarnings("serial")
@Title("Calendar Add-on Demo")
public final class DashboardView extends Panel implements View {

    public static final String TITLE_ID = "dashboard-title";

    private Label titleLabel;
    private NotificationsButton notificationsButton;
    private VerticalLayout notificationsLayout;
    private CssLayout dashboardPanels;
    private final VerticalLayout root;
    private Window notificationsWindow;
    NotificacionService nS;
    CitaFormWindow citaWindow;
    MeetingCalendar meetings;

    public DashboardView() {
	addStyleName(ValoTheme.PANEL_BORDERLESS);
	setSizeFull();
	DashboardEventBus.register(this);

	root = new VerticalLayout();
	root.setSizeFull();
	root.setSpacing(false);
	root.addStyleName("dashboard-view");
	setContent(root);
	Responsive.makeResponsive(root);

	root.addComponent(buildHeader());

	Component content = buildContent();
	root.addComponent(content);
	root.setExpandRatio(content, 1);
	nS = new NotificacionService();
	// All the open sub-windows should be closed whenever the root layout
	// gets clicked.
	root.addLayoutClickListener(new LayoutClickListener() {
	    @Override
	    public void layoutClick(final LayoutClickEvent event) {
		DashboardEventBus.post(new DashboardEvent.CloseOpenWindowsEvent());
	    }
	});

    }

    @Subscribe
    public void updateNotificationsCount(
	    final DashboardEvent.NotificationsCountUpdatedEvent event) {
	notificationsButton.setUnreadCount(nS
		.getUnreadNotificationsCount());
    }

    private Component buildHeader() {
	HorizontalLayout header = new HorizontalLayout();
	header.addStyleName("viewheader");

	titleLabel = new Label("Inicio");
	titleLabel.setId(TITLE_ID);
	titleLabel.setSizeUndefined();
	titleLabel.addStyleName(ValoTheme.LABEL_H1);
	titleLabel.addStyleName(ValoTheme.LABEL_NO_MARGIN);
	header.addComponent(titleLabel);

	notificationsButton = buildNotificationsButton();
	Button nuevo = new Button(VaadinIcons.PLUS);
	nuevo.addClickListener(new ClickListener() {
	    @Override
	    public void buttonClick(ClickEvent clickEvent) {
		Cita cita = new Cita();
		cita.setFechaInicio(LocalDate.now().atTime(LocalTime.now()));
		cita.setFechaFin(LocalDate.now().atTime(LocalTime.now().plusHours(1)));
		citaWindow = new CitaFormWindow(cita) {
		    @Override
		    public void onSave() {
			meetings.refreshCitas();

		    };
		};
	    }
	});
	// nuevo.setStyleName(ValoTheme.BUTTON_PRIMARY);
	
	Button preferenciasRecordatorios = new Button(VaadinIcons.COG);
	preferenciasRecordatorios.setDescription("Modificar preferencias de notificaciones");
	preferenciasRecordatorios.addClickListener(e ->{
	    Empleado emp = DashboardUI.getEmpleadoLogueado();
	    new ParametrosDelSistemaWindow(emp) {
	        
	        @Override
	        public void onSave() {
	    	CitaService.actualizarHsAntesCitas(emp);
	    	meetings.refreshCitas();	    	
	        }
	    };
	});
	
	HorizontalLayout tools = new HorizontalLayout( nuevo,notificationsButton, preferenciasRecordatorios);
	tools.addStyleName("toolbar");
	header.addComponent(tools);

	return header;
    }

    private NotificationsButton buildNotificationsButton() {
	NotificationsButton result = new NotificationsButton();
	result.addClickListener(new ClickListener() {
	    @Override
	    public void buttonClick(final ClickEvent event) {

		openNotificationsPopup(event);
	    }
	});
	return result;
    }

    private Component buildContent() {
	dashboardPanels = new CssLayout();
	dashboardPanels.addStyleName("dashboard-panels");
	Responsive.makeResponsive(dashboardPanels);
	dashboardPanels.addComponent(buildCalendar());
	return dashboardPanels;
    }

    private static class CalStyle {

	@FunctionalInterface
	interface CalAction {
	    void update();
	}

	private String caption;

	private CalAction action;

	CalStyle(String caption, CalAction action) {
	    this.caption = caption;
	    this.action = action;
	}

	private void act() {
	    action.update();
	}

	@Override
	public String toString() {
	    return caption;
	}
    }

    private Component buildCalendar() {
	// Initialize our new UI component
	meetings = new MeetingCalendar() {

	    @Override
	    public void onCalendarRangeSelect(CalendarComponentEvents.RangeSelectEvent event) {
		if (citaWindow != null && citaWindow.isAttached())
		    return;

		Cita cita = new Cita();
		cita.setFechaInicio(event.getStart().toLocalDateTime().toLocalDate().atTime(LocalTime.now()));

		cita.setFechaFin(event.getStart().toLocalDateTime().toLocalDate().atTime(LocalTime.now().plusHours(1)));
		citaWindow = new CitaFormWindow(cita) {
		    @Override
		    public void onSave() {
			refreshCitas();

		    }
		};

	    }

	    @Override
	    public void onCalendarClick(CalendarComponentEvents.ItemClickEvent event) {

		MeetingItem item = (MeetingItem) event.getCalendarItem();

		final Cita cita = item.getMeeting();

		citaWindow = new CitaFormWindow(cita) {
		    @Override
		    public void onSave() {

			refreshCitas();
		    }
		};

	    }
	};

	meetings.setSizeFull();

	meetings.getCalendar().setLocale(UI.getCurrent().getLocale());
	meetings.getCalendar().setZoneId(ZoneId.of(meetings.getCalendar().getZoneId().getId()));

	final VerticalLayout layout = new VerticalLayout(meetings);
	layout.setSizeFull();

	// layout.setSizeFull();
	layout.setStyleName("v-panel");
	layout.setSpacing(false);
	layout.setResponsive(true);

	layout.setMargin(new MarginInfo(true, true, false, false));
	layout.setExpandRatio(meetings, 1);

	try {
	    meetings.switchToMonth(LocalDate.now().getMonth());
	} catch (Exception e) {
	    System.err.println("Error al cambiar de mes");
	    e.printStackTrace();
	}

	return layout;

    }

    private Component buildNotes() {
	TextArea notes = new TextArea("Notas");
	// notes.setValue("Notas:\n-Una nota ");
	String text = "";
	try {
	    for (String line : Files.readAllLines(Paths.get(CurrentInstance.get(VaadinRequest.class).getService()
		    .getBaseDirectory() + File.separator + "Readme.md"))) {
		text = text + "\n" + line;
	    }
	    notes.setValue(text);
	} catch (IOException e) {
	    e.printStackTrace();
	}

	notes.setSizeFull();
	notes.addStyleName(ValoTheme.TEXTAREA_BORDERLESS);
	Component panel = createContentWrapper(notes);
	panel.addStyleName("notes");
	return panel;
    }

    private Component createContentWrapper(final Component content) {
	final CssLayout slot = new CssLayout();
	slot.setWidth("100%");
	slot.addStyleName("dashboard-panel-slot");

	CssLayout card = new CssLayout();
	card.setWidth("100%");
	card.addStyleName(ValoTheme.LAYOUT_CARD);

	HorizontalLayout toolbar = new HorizontalLayout();
	toolbar.addStyleName("dashboard-panel-toolbar");
	toolbar.setWidth("100%");
	toolbar.setSpacing(false);

	Label caption = new Label(content.getCaption());
	caption.addStyleName(ValoTheme.LABEL_H4);
	caption.addStyleName(ValoTheme.LABEL_COLORED);
	caption.addStyleName(ValoTheme.LABEL_NO_MARGIN);
	content.setCaption(null);

	MenuBar tools = new MenuBar();
	tools.addStyleName(ValoTheme.MENUBAR_BORDERLESS);
	MenuItem max = tools.addItem("", VaadinIcons.EXPAND, new Command() {

	    @Override
	    public void menuSelected(final MenuItem selectedItem) {
		if (!slot.getStyleName().contains("max")) {
		    selectedItem.setIcon(VaadinIcons.COMPRESS);
		    toggleMaximized(slot, true);
		} else {
		    slot.removeStyleName("max");
		    selectedItem.setIcon(VaadinIcons.EXPAND);
		    toggleMaximized(slot, false);
		}
	    }
	});
	max.setStyleName("icon-only");
	MenuItem root = tools.addItem("", VaadinIcons.COG, null);
	root.addItem("Configurar", new Command() {
	    @Override
	    public void menuSelected(final MenuItem selectedItem) {
		Notification.show("Aun no implementado");
	    }
	});
	root.addSeparator();
	root.addItem("Cerrar", new Command() {
	    @Override
	    public void menuSelected(final MenuItem selectedItem) {
		Notification.show("Aun no implementado");
	    }
	});

	toolbar.addComponents(caption, tools);
	toolbar.setExpandRatio(caption, 1);
	toolbar.setComponentAlignment(caption, Alignment.MIDDLE_LEFT);

	card.addComponents(toolbar, content);
	slot.addComponent(card);
	return slot;
    }

    private void openNotificationsPopup(final ClickEvent event) {
	if (notificationsWindow != null) {
	    if (notificationsWindow.isAttached()) {
		notificationsWindow.close();
		return;
	    }
	}

	notificationsLayout = new VerticalLayout();
	notificationsLayout.addStyleName("v-scrollable");
	Label title = new Label("Notificaciones");
	title.addStyleName(ValoTheme.LABEL_H3);
	title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
	notificationsLayout.addComponent(title);

	ArrayList<Notificacion> notifications = (ArrayList<Notificacion>) nS.getNotifications();
	DashboardEventBus.post(new DashboardEvent.NotificationsCountUpdatedEvent());
	int size = notifications.size();

	boolean first = true;
	for (int i=0 ;i<size ;i++) {
		Notificacion notification=notifications.get(i);

	    VerticalLayout notificationLayout = new VerticalLayout();
	    notificationLayout.setMargin(false);
	    notificationLayout.setSpacing(false);
	    notificationLayout.addStyleName("notification-item");

	    Label titleLabel = new Label(notification.getTitulo());
	    titleLabel.addStyleName("notification-title");
	    PrettyTime p = new PrettyTime(Locale.getDefault());

	    Date out = Date.from(notification.getFechaCreacion()
		    .atZone(ZoneId.systemDefault()).toInstant());

	    p.setLocale(new Locale("es", "AR"));
	    Label timeLabel = new Label(p.format(out));
	    timeLabel.addStyleName("notification-time");

	    String content = notification.getMensaje();
	    String ret = "";

	    if (content.length() > 39 &&  notification.getMensaje().length()>=42) {
		content = notification.getMensaje().substring(0, 42) + "...";
	    }

	    Label contentLabel = new Label(content);
	    contentLabel.addStyleName("notification-content");

	    notificationLayout.addComponents(titleLabel, timeLabel,
		    contentLabel);

	    notificationLayout.setDescription(notification.getIdCita());

	    notificationLayout.addLayoutClickListener(new LayoutClickListener() {
		@Override
		public void layoutClick(LayoutClickEvent layoutClickEvent) {
		    CitaService citaService = new CitaService();
		    Cita ret = citaService.getCitaFromTriggerKey(notificationLayout.getDescription());
		    if (ret != null) {
		    	notificationsWindow.close();
			citaWindow = new CitaFormWindow(ret) {
			    @Override
			    public void onSave() {
				meetings.refreshCitas();
			    };
			};
		    }
		};
	    });

	    boolean stale = notification.getFechaCreacion().isAfter(notification.getFechaCreacion().plusDays(1))
		    && notification.isVisto();

	    if ((!stale)) {
			if (!first) {
				Label divider = notificationDivider();
				divider.setSizeFull();
				notificationsLayout.addComponent(divider);
			}
			notificationsLayout.addComponent(notificationLayout);
			first = false;
	    }

	}

	HorizontalLayout footer = new HorizontalLayout();
	footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
	footer.setWidth("100%");
	footer.setSpacing(false);

	Button showAll = new Button("",
		new ClickListener() {
		    @Override
		    public void buttonClick(final ClickEvent event) {

		    }
		});
	showAll.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
	showAll.addStyleName(ValoTheme.BUTTON_SMALL);
	footer.addComponent(showAll);
	footer.setComponentAlignment(showAll, Alignment.TOP_CENTER);
	notificationsLayout.addComponent(footer);
	notificationsWindow = new Window();
	notificationsWindow.setWidth(300.0f, Unit.PIXELS);
	notificationsWindow.addStyleName("notifications");
	notificationsWindow.setClosable(false);
	notificationsWindow.setResizable(false);
	notificationsWindow.setDraggable(false);
	notificationsWindow.setCloseShortcut(KeyCode.ESCAPE, null);
	notificationsWindow.setContent(notificationsLayout);

	if (!notificationsWindow.isAttached()) {
	    notificationsWindow.setPositionY(event.getClientY()
		    - event.getRelativeY() + 40);
	    getUI().addWindow(notificationsWindow);
	    notificationsWindow.focus();
	} else {
	    notificationsWindow.close();
	}

    }

    private Label notificationDivider() {
	Label label = new Label("<hr color=#DEDEDE size=1>", ContentMode.HTML);
	label.setWidth(300.0f, Unit.PIXELS);
	return label;
    }

    @Override
    public void enter(final ViewChangeEvent event) {
	updateNotificationsCount(null);
    }

    private void toggleMaximized(final Component panel, final boolean maximized) {
	for (Iterator<Component> it = root.iterator(); it.hasNext();) {
	    it.next().setVisible(!maximized);
	}
	dashboardPanels.setVisible(true);

	for (Iterator<Component> it = dashboardPanels.iterator(); it.hasNext();) {
	    Component c = it.next();
	    c.setVisible(!maximized);
	}

	if (maximized) {
	    panel.setVisible(true);
	    panel.addStyleName("max");
	} else {
	    panel.removeStyleName("max");
	}
    }

    public static final class NotificationsButton extends Button {
	private static final String STYLE_UNREAD = "unread";
	public static final String ID = "dashboard-notifications";

	public NotificationsButton() {
	    setIcon(VaadinIcons.BELL);
	    setId(ID);
	    addStyleName("notifications");
	    addStyleName(ValoTheme.BUTTON_ICON_ONLY);
	    DashboardEventBus.register(this);
	}

	public void setUnreadCount(final int count) {
	    setCaption(String.valueOf(count));

	    String description = "Notificaciones";
	    if (count > 0) {
		addStyleName(STYLE_UNREAD);
		description += " (" + count + " sin leer)";
	    } else {
		removeStyleName(STYLE_UNREAD);
	    }
	    setDescription(description);
	}
    }
}
