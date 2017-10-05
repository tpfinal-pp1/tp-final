package com.TpFinal.view.ContratoView;

/**
 * Created by Max on 10/5/2017.
 */

import com.TpFinal.data.dto.contrato.Contrato;
import com.TpFinal.services.ContratoService;
import com.TpFinal.services.DashboardEvent;
import com.TpFinal.view.component.DefaultLayout;
import com.google.common.eventbus.Subscribe;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Widgetset;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.List;



@Title("Addressbook")
@Theme("valo")
@Widgetset("com.vaadin.v7.Vaadin7WidgetSet")
public class ContratoABMView extends DefaultLayout implements View {

    /*
     * Hundreds of widgets. Vaadin's user interface components are just Java
     * objects that encapsulate and handle cross-browser support and
     * client-server communication. The default Vaadin components are in the
     * com.vaadin.ui package and there are over 500 more in
     * vaadin.com/directory.
     */
    TextField filter = new TextField();
    private Grid<Contrato> grid = new Grid<>(Contrato.class);
    Button nuevoContrato = new Button("Nuevo Alquiler");



    Button clearFilterTextBtn = new Button(VaadinIcons.CLOSE);


    HorizontalLayout mainLayout;
    // PublicacionVentaForm is an example of a custom component class
    ContratoForm ContratoForm = new ContratoForm(this);


    private boolean isonMobile = false;

    // PublicacionService is a in-memory mock DAO that mimics
    // a real-world datasource. Typically implemented for
    // example as EJB or Spring Data based service.
    ContratoService service = new ContratoService();


    public ContratoABMView() {
        super();
        buildLayout();
        configureComponents();


    }


    private void configureComponents() {
        /*
         * Synchronous event handling.
         *
         * Receive user interaction events on the server-side. This allows you
         * to synchronously handle those events. Vaadin automatically sends only
         * the needed changes to the web page without loading a new page.
         */
        //    nuevaVenta.addClickListener(e -> PublicacionVentaForm.setVenta(new Publicacion()));

        filter.addValueChangeListener(e -> updateList());
        filter.setValueChangeMode(ValueChangeMode.LAZY);

        filter.setPlaceholder("Filtrar");
        filter.addValueChangeListener(e -> updateList());
        clearFilterTextBtn.setDescription("Limpiar filtro");
        clearFilterTextBtn.addClickListener(e -> ClearFilterBtnAction());

        nuevoContrato.addClickListener(e -> {
            grid.asSingleSelect().clear();
            ContratoForm.setContrato(new Contrato());
        });

        grid.setColumns("fechaCelebracion");
        grid.setSizeFull();



        Responsive.makeResponsive(this);
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() == null) {
               ContratoForm.setVisible(false);
            }
            else {

                if(event.getValue() instanceof Contrato)
                    ContratoForm.setContrato((Contrato) event.getValue());
                }
        });

        // grid.setSelectionMode(Grid.SelectionMode.SINGLE);

        nuevoContrato.setStyleName(ValoTheme.BUTTON_PRIMARY);
        filter.setIcon(VaadinIcons.SEARCH);
        filter.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        updateList();
    }

    /*
     * Robust layouts.
     *
     * Layouts are components that contain other components. HorizontalLayout
     * contains TextField and Button. It is wrapped with a Grid into
     * VerticalLayout for the left side of the screen. Allow user to resize the
     * components with a SplitPanel.
     *
     * In addition to programmatically building layout in Java, you may also
     * choose to setup layout declaratively with Vaadin Designer, CSS and HTML.
     */

    public void setComponentsVisible(boolean b) {
        nuevoContrato.setVisible(b);
        filter.setVisible(b);
        //clearFilterTextBtn.setVisible(b);
        if (isonMobile)
            grid.setVisible(b);

    }

    private void buildLayout() {

        CssLayout filtering = new CssLayout();

        HorizontalLayout layout = new HorizontalLayout(nuevoContrato);
        filtering.addComponents(filter, clearFilterTextBtn, layout);
        filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        if (checkIfOnMobile()) {
            filter.setWidth("58%");
            nuevoContrato.setCaption("+ Alquiler");
            layout.setSpacing(false);
            layout.setMargin(false);
            layout.setResponsive(true);
            layout.setSizeUndefined();

        }

        addComponent(buildToolbar("Publicaciones", filtering, layout));
        grid.setSizeFull();
        mainLayout = new HorizontalLayout(grid, ContratoForm);
        //mainLayout.setSizeFull();
        addComponent(mainLayout);
        this.setExpandRatio(mainLayout, 1);

    }

    /*
     * Choose the design patterns you like.
     *
     * It is good practice to have separate data access methods that handle the
     * back-end access and/or the user interface updates. You can further split
     * your code into classes to easier maintenance. With Vaadin you can follow
     * MVC, MVP or any other design pattern you choose.
     */

    public void showErrorNotification(String notification) {
        Notification success = new Notification(
                notification);
        success.setDelayMsec(4000);
        success.setStyleName("bar error small");
        success.setPosition(Position.BOTTOM_CENTER);
        success.show(Page.getCurrent());
    }

    public void showSuccessNotification(String notification) {
        Notification success = new Notification(
                notification);
        success.setDelayMsec(2000);
        success.setStyleName("bar success small");
        success.setPosition(Position.BOTTOM_CENTER);
        success.show(Page.getCurrent());
    }

    public void updateList() {
        List<Contrato> contracts = service.findAll(filter.getValue());
        grid.setItems(contracts);

    }

    public boolean isIsonMobile() {
        return isonMobile;
    }

    public void ClearFilterBtnAction() {

        if (this.ContratoForm.isVisible()) {
            nuevoContrato.focus();
            ContratoForm.cancel();

        }
        filter.clear();
    }


    public boolean checkIfOnMobile() {
        if (Page.getCurrent().getBrowserWindowWidth() < 800) {
            isonMobile = true;
            return true;
        } else {
            isonMobile = false;
            return false;

        }
    }

    /*

     * Deployed as a Servlet or Portlet.
     *
     * You can specify additional servlet parameters like the URI and UI class
     * name and turn on production mode when you have finished developing the
     * application.
     */
    @Override
    public void detach() {
        super.detach();
        // A new instance of TransactionsView is created every time it's
        // navigated to so we'll need to clean up references to it on detach.
        com.TpFinal.services.DashboardEventBus.unregister(this);
    }

    @Subscribe
    public void browserWindowResized(final DashboardEvent.BrowserResizeEvent event) {
        if (Page.getCurrent().getBrowserWindowWidth() < 800) {
            isonMobile = true;
        } else {
            isonMobile = false;

        }

    }

    @Override
    public void enter(final ViewChangeListener.ViewChangeEvent event) {

    }
}