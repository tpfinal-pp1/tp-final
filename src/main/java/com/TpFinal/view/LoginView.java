package com.TpFinal.view;

import com.TpFinal.services.CredencialService;
import com.TpFinal.services.DashboardEventBus;
import com.TpFinal.services.DashboardEvent.UserLoginRequestedEvent;
import com.vaadin.data.HasValue;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Responsive;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class LoginView extends VerticalLayout {

    public LoginView() {
        setSizeFull();
        setMargin(false);
        setSpacing(false);

        Component loginForm = buildLoginForm();
        addComponent(loginForm);
        setComponentAlignment(loginForm, Alignment.MIDDLE_CENTER);


    }

    public LoginView(boolean b) {
        setSizeFull();
        setMargin(false);
        setSpacing(false);


        final VerticalLayout loginPanel = new VerticalLayout();
        loginPanel.setSizeUndefined();
        loginPanel.setMargin(false);
        Responsive.makeResponsive(loginPanel);
        loginPanel.addStyleName("login-panel");

        loginPanel.addComponent(buildLabels());
        loginPanel.addComponent(buildSerialFields());

        addComponent(loginPanel);
        setComponentAlignment(loginPanel, Alignment.MIDDLE_CENTER);

    }

    private Component buildLoginForm() {
        final VerticalLayout loginPanel = new VerticalLayout();
        loginPanel.setSizeUndefined();
        loginPanel.setMargin(false);
        Responsive.makeResponsive(loginPanel);
        loginPanel.addStyleName("login-panel");

        loginPanel.addComponent(buildLabels());
        loginPanel.addComponent(buildFields());
        return loginPanel;
    }

    private Component buildSerialFields() {
        HorizontalLayout fields = new HorizontalLayout();
        fields.addStyleName("fields");

        final TextField username = new TextField("Serial");
        username.setIcon(VaadinIcons.USER);
        username.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);


        final Button validate = new Button("Validar");
        validate.addStyleName(ValoTheme.BUTTON_PRIMARY);
        validate.setClickShortcut(KeyCode.ENTER);
        username.focus();
        String boton="Demo(1 intento)";
        boolean enabled=true;
        if(CredencialService.demoIsOver()) {
            boton = "Demo(0 intentos)";
            enabled=false;
        }
        username.addValueChangeListener(new HasValue.ValueChangeListener<String>() {
            @Override
            public void valueChange(HasValue.ValueChangeEvent<String> valueChangeEvent) {
                if(valueChangeEvent.getValue()!=null) {
                    String value=valueChangeEvent.getValue();
                    CredencialService cs=new CredencialService();
                   if(cs.validateSerial(value)){
                       validate.setEnabled(true);
                       System.out.println("Validado!!");
                   }
                   else{
                       validate.setEnabled(false);
                       System.out.println("Error!!");
                   }
                }

            }
        });
        final Button demo = new Button(boton);
        demo.setIcon(VaadinIcons.CLOCK);
        demo.setEnabled(enabled);
        VerticalLayout vl=new VerticalLayout(validate,demo);
        fields.addComponents(username,vl);
        fields.setComponentAlignment(vl, Alignment.BOTTOM_LEFT);
        demo.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
                DashboardEventBus.post(new UserLoginRequestedEvent("demo", "demo"));
            }
        });


        validate.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {

                DashboardEventBus.post(new UserLoginRequestedEvent(username
                        .getValue(), ""));
            }
        });
        validate.setEnabled(false);
        return fields;
    }


    private Component buildFields() {
        HorizontalLayout fields = new HorizontalLayout();
        fields.addStyleName("fields");

        final TextField username = new TextField("Usuario");
        username.setIcon(VaadinIcons.USER);
        username.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

        final PasswordField password = new PasswordField("Contraseña");
        password.setIcon(VaadinIcons.LOCK);
        password.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

        final Button signin = new Button("Iniciar Sesión");
        signin.addStyleName(ValoTheme.BUTTON_PRIMARY);
        signin.setClickShortcut(KeyCode.ENTER);
       username.focus();

        fields.addComponents(username, password, signin);
        fields.setComponentAlignment(signin, Alignment.BOTTOM_LEFT);

        signin.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
                DashboardEventBus.post(new UserLoginRequestedEvent(username
                        .getValue(), password.getValue()));
            }
        });
        return fields;
    }

    private Component buildLabels() {
        CssLayout labels = new CssLayout();
        labels.addStyleName("labels");

        Label welcome = new Label("Bienvenido");
        welcome.setSizeUndefined();
        welcome.addStyleName(ValoTheme.LABEL_H4);
        welcome.addStyleName(ValoTheme.LABEL_COLORED);
        labels.addComponent(welcome);

        Label title = new Label("Inmobi");
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_H3);
        title.addStyleName(ValoTheme.LABEL_LIGHT);
        labels.addComponent(title);
        return labels;
    }

}
