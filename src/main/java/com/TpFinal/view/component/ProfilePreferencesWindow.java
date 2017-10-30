package com.TpFinal.view.component;

import java.util.Arrays;

import com.TpFinal.dto.persona.Empleado;
import com.TpFinal.services.DashboardEvent;
import com.TpFinal.services.DashboardEventBus;
import com.vaadin.annotations.PropertyId;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.UserError;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.RadioButtonGroup;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.v7.data.fieldgroup.BeanFieldGroup;
import com.vaadin.v7.data.fieldgroup.FieldGroup.CommitException;

@SuppressWarnings("serial")
public class ProfilePreferencesWindow extends Window {

    public static final String ID = "profilepreferenceswindow";

    private final BeanFieldGroup<Empleado> fieldGroup;
    /*
     * Fields for editing the User object are defined here as class members.
     * They are later bound to a FieldGroup by calling
     * fieldGroup.bindMemberFields(this). The Fields' values don't need to be
     * explicitly set, calling fieldGroup.setItemDataSource(user) synchronizes
     * the fields with the user object.
     */
    @PropertyId("nombre")
    private TextField firstNameField;
    @PropertyId("apellido")
    private TextField lastNameField;
    @PropertyId("mail")
    private TextField emailField;
    @PropertyId("telefono")
    private TextField phoneField;
    @PropertyId("infoAdicional")
    private TextArea bioField;

    private ProfilePreferencesWindow(final Empleado user,
            final boolean preferencesTabOpen) {
        addStyleName("profile-window");
        setId(ID);
        Responsive.makeResponsive(this);

        setModal(true);
        setCloseShortcut(KeyCode.ESCAPE, null);
        setResizable(false);
        setClosable(false);
        setHeight(90.0f, Unit.PERCENTAGE);

        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        content.setMargin(new MarginInfo(true, false, false, false));
        content.setSpacing(false);
        setContent(content);

        TabSheet detailsWrapper = new TabSheet();
        detailsWrapper.setSizeFull();
        detailsWrapper.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
        detailsWrapper.addStyleName(ValoTheme.TABSHEET_ICONS_ON_TOP);
        detailsWrapper.addStyleName(ValoTheme.TABSHEET_CENTERED_TABS);
        content.addComponent(detailsWrapper);
        content.setExpandRatio(detailsWrapper, 1f);

        detailsWrapper.addComponent(buildProfileTab());
        detailsWrapper.addComponent(buildPreferencesTab());

        if (preferencesTabOpen) {
            detailsWrapper.setSelectedTab(1);
        }

        content.addComponent(buildFooter());

        fieldGroup = new BeanFieldGroup<Empleado>(Empleado.class);
        fieldGroup.bindMemberFields(this);
        fieldGroup.setItemDataSource(user);
    }

    private Component buildPreferencesTab() {
        VerticalLayout root = new VerticalLayout();
        root.setCaption("Preferences");
        root.setIcon(VaadinIcons.COGS);
        root.setSpacing(true);
        root.setMargin(true);
        root.setSizeFull();

        Label message = new Label("Not implemented in this demo");
        message.setSizeUndefined();
        message.addStyleName(ValoTheme.LABEL_LIGHT);
        root.addComponent(message);
        root.setComponentAlignment(message, Alignment.MIDDLE_CENTER);

        return root;
    }

    private Component buildProfileTab() {
        HorizontalLayout root = new HorizontalLayout();
        root.setCaption("Profile");
        root.setIcon(VaadinIcons.USER);
        root.setWidth(100.0f, Unit.PERCENTAGE);
        root.setMargin(true);
        root.addStyleName("profile-form");

        VerticalLayout pic = new VerticalLayout();
        pic.setSizeUndefined();
        pic.setSpacing(true);
        Image profilePic = new Image(null,
                new ThemeResource("img/profile-pic-300px.jpg"));
        profilePic.setWidth(100.0f, Unit.PIXELS);
        pic.addComponent(profilePic);

        Button upload = new Button("Change…", new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                Notification.show("Not implemented in this demo");
            }
        });
        upload.addStyleName(ValoTheme.BUTTON_TINY);
        pic.addComponent(upload);

        root.addComponent(pic);

        FormLayout details = new FormLayout();
        details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        root.addComponent(details);
        root.setExpandRatio(details, 1);

        firstNameField = new TextField("First Name");
        details.addComponent(firstNameField);
        lastNameField = new TextField("Last Name");
        details.addComponent(lastNameField);


        Label section = new Label("persona Info");
        section.addStyleName(ValoTheme.LABEL_H4);
        section.addStyleName(ValoTheme.LABEL_COLORED);
        details.addComponent(section);

        emailField = new TextField("Email");
        emailField.setWidth("100%");
        emailField.setRequiredIndicatorVisible(true);
        // TODO add validation that not empty, use binder
        details.addComponent(emailField);

        phoneField = new TextField("Telefono");
        phoneField.setWidth("100%");
        details.addComponent(phoneField);


        section = new Label("Additional Info");
        section.addStyleName(ValoTheme.LABEL_H4);
        section.addStyleName(ValoTheme.LABEL_COLORED);
        details.addComponent(section);

        bioField = new TextArea("Info");
        bioField.setWidth("100%");
        bioField.setRows(4);
        details.addComponent(bioField);

        return root;
    }

    private Component buildFooter() {
        HorizontalLayout footer = new HorizontalLayout();
        footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
        footer.setWidth(100.0f, Unit.PERCENTAGE);
        footer.setSpacing(false);

        Button ok = new Button("OK");
        ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
        ok.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                close();
            }
        });
        ok.focus();
        footer.addComponent(ok);
        footer.setComponentAlignment(ok, Alignment.TOP_RIGHT);
        return footer;
    }

    public static void open(final Empleado user,
            final boolean preferencesTabActive) {
        DashboardEventBus.post(new DashboardEvent.CloseOpenWindowsEvent());
        Window w = new ProfilePreferencesWindow(user, preferencesTabActive);
        UI.getCurrent().addWindow(w);
        w.focus();
    }
}
