package com.TpFinal.view;

import com.TpFinal.DashboardUI;
import com.TpFinal.dto.EstadoRegistro;
import com.TpFinal.dto.notificacion.Notificacion;
import com.TpFinal.services.DashboardEvent;
import com.TpFinal.services.DashboardEventBus;
import com.TpFinal.services.DataProviderImpl;
import com.TpFinal.utils.DummyDataGenerator;
import com.TpFinal.view.dummy.meetings.MeetingCalendar;
import com.google.common.eventbus.Subscribe;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import org.ocpsoft.prettytime.PrettyTime;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.util.CurrentInstance;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;

@SuppressWarnings("serial")
public final class DashboardView extends Panel implements View{

    public static final String TITLE_ID = "dashboard-title";

    private Label titleLabel;
    private NotificationsButton notificationsButton;
    private CssLayout dashboardPanels;
    private final VerticalLayout root;
    private Window notificationsWindow;

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

        // All the open sub-windows should be closed whenever the root layout
        // gets clicked.
        root.addLayoutClickListener(new LayoutClickListener() {
            @Override
            public void layoutClick(final LayoutClickEvent event) {
                DashboardEventBus.post(new DashboardEvent.CloseOpenWindowsEvent());
            }
        });
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
        Button test=new Button("Generar Notis");
        test.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent clickEvent) {
                DataProviderImpl dt=new DataProviderImpl();
                for (Notificacion noti: DummyDataGenerator.randomNotifications(5)
                        ) {
                    dt.addNotificacion(noti);
                }

            }});
        HorizontalLayout tools = new HorizontalLayout(test,notificationsButton);
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

        CalStyle(String caption,CalAction action) {
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

    private Component buildCalendar(){
        // Initialize our new UI component
        MeetingCalendar meetings = new MeetingCalendar();
        meetings.setSizeFull();

      /*  ComboBox<Locale> localeBox = new ComboBox<>();
        localeBox.setItems(Locale.getAvailableLocales());
        localeBox.setEmptySelectionAllowed(false);
        localeBox.setValue(UI.getCurrent().getLocale());
        localeBox.addValueChangeListener(e -> meetings.getCalendar().setLocale(e.getValue()));
*/

        meetings.getCalendar().setLocale(UI.getCurrent().getLocale());
        meetings.getCalendar().setZoneId(ZoneId.of(meetings.getCalendar().getZoneId().getId()));
       /*ComboBox<String> zoneBox = new ComboBox<>();
        zoneBox.setItems(ZoneId.getAvailableZoneIds());
        zoneBox.setEmptySelectionAllowed(false);
        zoneBox.setValue(meetings.getCalendar().getZoneId().getId());
        zoneBox.addValueChangeListener(e -> meetings.getCalendar().setZoneId(ZoneId.of(e.getValue())));
*/
       /* ComboBox<CalStyle> calActionComboBox = new ComboBox<>();
        calActionComboBox.setItems(
                new CalStyle("Col 1 - 7", () -> meetings.getCalendar().withVisibleDays(1, 7)),
                new CalStyle("Col 1 - 5", () -> meetings.getCalendar().withVisibleDays(1, 5)),
                new CalStyle("Col 2 - 5", () -> meetings.getCalendar().withVisibleDays(2, 5)),
                new CalStyle("Col 6 - 7", () -> meetings.getCalendar().withVisibleDays(6, 7))
        );
        calActionComboBox.addValueChangeListener(e -> e.getValue().act());
        calActionComboBox.setEmptySelectionAllowed(false);*/

    /*    Button fixedSize = new Button("fixed Size", (Button.ClickEvent clickEvent) -> meetings.panel.setHeightUndefined());
        fixedSize.setIcon(VaadinIcons.LINK);

        Button fullSize = new Button("full Size", (Button.ClickEvent clickEvent) -> meetings.panel.setHeight(100, Unit.PERCENTAGE));
        fullSize.setIcon(VaadinIcons.UNLINK);
*/
       /* ComboBox<Month> months = new ComboBox<>();
        months.setItems(Month.values());
        months.setItemCaptionGenerator(month -> month.getDisplayName(TextStyle.FULL, meetings.getCalendar().getLocale()));
        months.setEmptySelectionAllowed(false);
        months.addValueChangeListener(me -> meetings.switchToMonth(me.getValue()));

        Button today = new Button("today", (Button.ClickEvent clickEvent) -> meetings.getCalendar().withDay(ZonedDateTime.now()));
        Button week = new Button("week", (Button.ClickEvent clickEvent) -> meetings.getCalendar().withWeek(ZonedDateTime.now()));

        HorizontalLayout nav = new HorizontalLayout( months, today, week);
        //nav.setWidth("100%");
*/
        // Show it in the middle of the screen
        final VerticalLayout layout = new VerticalLayout();
        meetings.panel.setHeightUndefined();
        layout.setStyleName("demoContentLayout");
        layout.setSizeFull();
        layout.setMargin(true);
        layout.setSpacing(true);
        meetings.panel.setHeight(100, Unit.PERCENTAGE);
       // layout.addComponent(nav);
        layout.addComponentsAndExpand(meetings);
        try{
            meetings.switchToMonth(LocalDate.now().getMonth());}
        catch (Exception e){
           System.err.println("Error al cambiar de mes");
            e.printStackTrace();
        }

       return layout;


    }



    private Component buildNotes() {
        TextArea notes = new TextArea("Notas");
        //notes.setValue("Notas:\n-Una nota ");
        String text="";
        try {
            for (String line : Files.readAllLines(Paths.get(CurrentInstance.get(VaadinRequest.class).getService().getBaseDirectory() + File.separator + "Readme.md"))) {
                text=text+"\n"+line;
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
        VerticalLayout notificationsLayout = new VerticalLayout();

        Label title = new Label("Notifications");
        title.addStyleName(ValoTheme.LABEL_H3);
        title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        notificationsLayout.addComponent(title);

        Collection<Notificacion> notifications = DashboardUI
                .getDataProvider().getNotifications();


        DashboardEventBus.post(new DashboardEvent.NotificationsCountUpdatedEvent());

        for (Notificacion notification : notifications) {
            VerticalLayout notificationLayout = new VerticalLayout();
            notificationLayout.setMargin(false);
            notificationLayout.setSpacing(false);
            notificationLayout.addStyleName("notification-item");

            Label titleLabel = new Label(notification.getTitulo());
            titleLabel.addStyleName("notification-title");
            PrettyTime p = new PrettyTime(Locale.getDefault());
            Date in = new Date();
            LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());
            Date out = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
            p.setLocale(new Locale("es", "AR"));
            Label timeLabel = new Label(p.format(out));
            timeLabel.addStyleName("notification-time");

            Label contentLabel = new Label(notification.getMensaje());
            contentLabel.addStyleName("notification-content");

            notificationLayout.addComponents(titleLabel, timeLabel,
                    contentLabel);
            notificationsLayout.addComponent(notificationLayout);
        }

        HorizontalLayout footer = new HorizontalLayout();
        footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
        footer.setWidth("100%");
        footer.setSpacing(false);
        Button showAll = new Button("Mostrar todas las notificaciones",
                new ClickListener() {
                    @Override
                    public void buttonClick(final ClickEvent event) {
                        Notification.show("Aun no implementado");
                    }
                });
        showAll.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        showAll.addStyleName(ValoTheme.BUTTON_SMALL);
        footer.addComponent(showAll);
        footer.setComponentAlignment(showAll, Alignment.TOP_CENTER);
        notificationsLayout.addComponent(footer);

        if (notificationsWindow == null) {
            notificationsWindow = new Window();
            notificationsWindow.setWidth(300.0f, Unit.PIXELS);
            notificationsWindow.addStyleName("notifications");
            notificationsWindow.setClosable(false);
            notificationsWindow.setResizable(false);
            notificationsWindow.setDraggable(false);
            notificationsWindow.setCloseShortcut(KeyCode.ESCAPE, null);
            notificationsWindow.setContent(notificationsLayout);
        }

        if (!notificationsWindow.isAttached()) {
            notificationsWindow.setPositionY(event.getClientY()
                    - event.getRelativeY() + 40);
            getUI().addWindow(notificationsWindow);
            notificationsWindow.focus();
        } else {
            notificationsWindow.close();
        }
    }

    @Override
    public void enter(final ViewChangeEvent event) {
        notificationsButton.updateNotificationsCount(null);
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
            setIcon(FontAwesome.BELL);
            setId(ID);
            addStyleName("notifications");
            addStyleName(ValoTheme.BUTTON_ICON_ONLY);
            DashboardEventBus.register(this);
        }

        @Subscribe
        public void updateNotificationsCount(
                final DashboardEvent.NotificationsCountUpdatedEvent event) {
            setUnreadCount(DashboardUI.getDataProvider()
                    .getUnreadNotificationsCount());
        }

        public void setUnreadCount(final int count) {
            setCaption(String.valueOf(count));

            String description = "Notifications";
            if (count > 0) {
                addStyleName(STYLE_UNREAD);
                description += " (" + count + " unread)";
            } else {
                removeStyleName(STYLE_UNREAD);
            }
            setDescription(description);
        }
    }
}
