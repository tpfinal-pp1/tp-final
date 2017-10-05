package com.TpFinal.view.component;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public abstract class VentanaSelectora<T>extends Window {

    public static final String ID = "profilepreferenceswindow";


    /*
     * Fields for ediing the User object are defined here as class members.
     * They are later bound to a FieldGroup by calling
     * fieldGroup.bindMemberFields(this). The Fields' values don't need to be
     * explicitly set, calling fieldGroup.setItemDataSource(user) synchronizes
     * the fields with the user object.
     */
    public Grid<T> grid;
    private T objeto;
    public abstract void updateList();
    public abstract void setGrid();





    public VentanaSelectora(T seleccion) {
        objeto=seleccion;
        addStyleName("profile-window");
        setId(ID);

        Responsive.makeResponsive(this);

        setModal(true);
        setCloseShortcut(KeyCode.ESCAPE, null);
        setResizable(false);
        setClosable(false);
      /*  setHeight(90.0f, Unit.PERCENTAGE);

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

      //  detailsWrapper.addComponent(grid);
      //  detailsWrapper.addComponent(buildPreferencesTab())
        content.addComponent(grid);
        content.addComponent(buildFooter());*/
        setGrid();
        setContent(new VerticalLayout(grid,buildFooter()));

        grid.setSizeFull();
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
               objeto=event.getValue();
            }
        });


        Responsive.makeResponsive(this);
        setVisible(true);
        UI.getCurrent().addWindow(this);
        this.focus();

        updateList();
    }




/*
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
*//*
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

        titleField = new ComboBox<>("Title",
                Arrays.asList("Mr.", "Mrs.", "Ms."));
        titleField.setPlaceholder("Please specify");
        details.addComponent(titleField);

        sexField = new RadioButtonGroup<>("Sexo", Arrays.asList(true, false));
        sexField.setItemCaptionGenerator(item -> item ? "Male" : "Female");
        sexField.addStyleName("horizontal");
        details.addComponent(sexField);

        Label section = new Label("persona Info");
        section.addStyleName(ValoTheme.LABEL_H4);
        section.addStyleName(ValoTheme.LABEL_COLORED);
        details.addComponent(section);

        emailField = new TextField("Email");
        emailField.setWidth("100%");
        emailField.setRequiredIndicatorVisible(true);
        // TODO add validation that not empty, use binder
        details.addComponent(emailField);

        locationField = new TextField("Location");
        locationField.setWidth("100%");
        locationField
                .setComponentError(new UserError("This address doesn't exist"));
        details.addComponent(locationField);

        phoneField = new TextField("Phone");
        phoneField.setWidth("100%");
        details.addComponent(phoneField);


        section = new Label("Additional Info");
        section.addStyleName(ValoTheme.LABEL_H4);
        section.addStyleName(ValoTheme.LABEL_COLORED);
        details.addComponent(section);

        websiteField = new TextField("Website");
        websiteField.setPlaceholder("http://");
        websiteField.setWidth("100%");
        details.addComponent(websiteField);

        bioField = new TextArea("Bio");
        bioField.setWidth("100%");
        bioField.setRows(4);
        details.addComponent(bioField);

        return root;
    }
*/
    private Component buildFooter() {
        HorizontalLayout footer = new HorizontalLayout();
        footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
        footer.setWidth(100.0f, Unit.PERCENTAGE);
        footer.setSpacing(false);

        Button ok = new Button("Seleccionar");
        ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
        ok.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {


                    // Updated user should also be persisted to database. But
                    // not in this demo.
                Notification success ;
                    if (objeto==null){
                       success = new Notification(
                                "No Seleccionado");
                    }
                    else{
                        success = new Notification(
                                "Seleccionado");
                    }
                    success.setDelayMsec(2000);
                    success.setStyleName("bar success small");
                    success.setPosition(Position.BOTTOM_CENTER);
                    success.show(Page.getCurrent());
                    close();


            }
        });
        ok.focus();
        footer.addComponent(ok);
        footer.setComponentAlignment(ok, Alignment.TOP_RIGHT);
        return footer;
    }



  /*  public static void open() {
        DashboardEventBus.post(new DashboardEvent.CloseOpenWindowsEvent());
        Window w = new MiniVentana();
        UI.getCurrent().addWindow(w);
        w.focus();
    }*/
}
