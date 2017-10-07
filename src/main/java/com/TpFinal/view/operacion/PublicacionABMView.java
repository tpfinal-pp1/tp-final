package com.TpFinal.view.operacion;


import com.TpFinal.data.dto.contrato.ContratoAlquiler;
import com.TpFinal.data.dto.contrato.ContratoVenta;
import com.TpFinal.data.dto.inmueble.*;
import com.TpFinal.data.dto.persona.Propietario;
import com.TpFinal.data.dto.publicacion.Publicacion;
import com.TpFinal.data.dto.publicacion.PublicacionAlquiler;
import com.TpFinal.data.dto.publicacion.PublicacionVenta;
import com.TpFinal.services.DashboardEvent;
import com.TpFinal.services.PublicacionService;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/* User Interface written in Java.
 *
 * Define the user interface shown on the Vaadin generated web page by extending the UI class.
 * By default, a new UI instance is automatically created when the page is loaded. To reuse
 * the same instance, add @PreserveOnRefresh.
 */

@Title("Addressbook")
@Theme("valo")
@Widgetset("com.vaadin.v7.Vaadin7WidgetSet")
public class PublicacionABMView extends DefaultLayout implements View {

    /*
     * Hundreds of widgets. Vaadin's user interface components are just Java
     * objects that encapsulate and handle cross-browser support and
     * client-server communication. The default Vaadin components are in the
     * com.vaadin.ui package and there are over 500 more in
     * vaadin.com/directory.
     */
    TextField filter = new TextField();
    private Grid<Publicacion> grid = new Grid<>(Publicacion.class);
    Button nuevoAlquiler = new Button("Nuevo Alquiler");
    Button nuevaVenta = new Button("Nueva Venta");


    Button clearFilterTextBtn = new Button(VaadinIcons.CLOSE);



    HorizontalLayout mainLayout;
    // ContratoVentaForm is an example of a custom component class
    PublicacionVentaForm PublicacionVentaForm = new PublicacionVentaForm(this);
    PublicacionAlquilerForm PublicacionAlquilerForm = new PublicacionAlquilerForm(this);

   private boolean isonMobile=false;

    // PublicacionService is a in-memory mock DAO that mimics
    // a real-world datasource. Typically implemented for
    // example as EJB or Spring Data based service.
    PublicacionService service = new PublicacionService();


    public PublicacionABMView(){
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
    //    nuevaVenta.addClickListener(e -> ContratoVentaForm.setVenta(new Publicacion()));

        filter.addValueChangeListener(e -> updateList());
        filter.setValueChangeMode(ValueChangeMode.LAZY);

        filter.setPlaceholder("Filtrar");
        filter.addValueChangeListener(e -> updateList());
        clearFilterTextBtn.setDescription("Limpiar filtro");
        clearFilterTextBtn.addClickListener(e -> ClearFilterBtnAction());

        nuevaVenta.addClickListener(e -> {
            grid.asSingleSelect().clear();
            PublicacionVentaForm.setPublicacionVenta(new PublicacionVenta.Builder()
                                .setPrecio(new BigDecimal("0"))
                                .setFechaPublicacion(LocalDate.now())
                                .setMoneda(TipoMoneda.Pesos)
                                .setContratoVenta(new ContratoVenta())
                                .setInmueble(new Inmueble.Builder()
                                        .setaEstrenar(false)
                                        .setCantidadAmbientes(0)
                                        .setCantidadCocheras(0)
                                        .setCantidadDormitorios(0)
                                        .setClaseInmueble(ClaseInmueble.OtroInmueble)
                                        .setConAireAcondicionado(false)
                                        .setConJardin(false)
                                        .setConParilla(false)
                                        .setConPileta(false)
                                        .setDireccion(new Direccion.Builder()
                                                .setCalle("")
                                                .setCodPostal("")
                                                .setCoordenada(new Coordenada())
                                                .setLocalidad("")
                                                .setNro(0)
                                                .setPais("Argentina")
                                                .setProvincia("")
                                                .build())
                                        .setEstadoInmueble(EstadoInmueble.NoPublicado)
                                        .setPropietario(new Propietario())
                                        .setSuperficieCubierta(0)
                                        .setSuperficieTotal(0)
                                        .setTipoInmueble(TipoInmueble.Vivienda)
                                        .build())
                                .setPropietario(new Propietario())
                                .build()
            );
            grid.setEnabled(false);
        });

        nuevoAlquiler.addClickListener(e -> {
            grid.asSingleSelect().clear();
            PublicacionAlquilerForm.setPublicacionAlquiler(new PublicacionAlquiler.Builder()
                            .setValorCuota(new BigDecimal("0"))
                            .setFechaPublicacion(LocalDate.now())
                            .setMoneda(TipoMoneda.Pesos)
                            .setContratoAlquiler(new ContratoAlquiler())
                            .setInmueble(new Inmueble.Builder()
                                    .setaEstrenar(false)
                                    .setCantidadAmbientes(0)
                                    .setCantidadCocheras(0)
                                    .setCantidadDormitorios(0)
                                    .setClaseInmueble(ClaseInmueble.OtroInmueble)
                                    .setConAireAcondicionado(false)
                                    .setConJardin(false)
                                    .setConParilla(false)
                                    .setConPileta(false)
                                    .setDireccion(new Direccion.Builder()
                                            .setCalle("")
                                            .setCodPostal("")
                                            .setCoordenada(new Coordenada())
                                            .setLocalidad("")
                                            .setNro(0)
                                            .setPais("Argentina")
                                            .setProvincia("")
                                            .build())
                                    .setEstadoInmueble(EstadoInmueble.NoPublicado)
                                    .setPropietario(new Propietario())
                                    .setSuperficieCubierta(0)
                                    .setSuperficieTotal(0)
                                    .setTipoInmueble(TipoInmueble.Vivienda)
                                    .build())
                            .setPropietario(new Propietario())
                            .build()
            );
            grid.setEnabled(false);
        });

        grid.setColumns("inmueble","propietarioPublicacion","tipoPublicacion","fechaPublicacion","estadoPublicacion");
        grid.getColumn("tipoPublicacion").setCaption("Operación");
        grid.getColumn("fechaPublicacion").setCaption("Fecha Publicación");
        grid.getColumn("propietarioPublicacion").setCaption("Propietario");


        Responsive.makeResponsive(this);
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() == null) {
                PublicacionVentaForm.setVisible(false);
            } else {

                if(event.getValue() instanceof PublicacionAlquiler) {
                    PublicacionAlquilerForm.setPublicacionAlquiler((PublicacionAlquiler) event.getValue());
                    grid.setEnabled(false);
                }

                else if(event.getValue() instanceof PublicacionVenta){
                    PublicacionVentaForm.setPublicacionVenta((PublicacionVenta) event.getValue());
                    grid.setEnabled(false);
                }
            }
        });

       // grid.setSelectionMode(Grid.SelectionMode.SINGLE);

        nuevaVenta.setStyleName(ValoTheme.BUTTON_PRIMARY);
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

    public void setComponentsVisible(boolean b){
        nuevaVenta.setVisible(b);
        nuevoAlquiler.setVisible(b);
        filter.setVisible(b);
        //clearFilterTextBtn.setVisible(b);
        if(isonMobile)
            grid.setVisible(b);

    }

    private void buildLayout() {

        CssLayout filtering = new CssLayout();

        HorizontalLayout layout=new HorizontalLayout(nuevaVenta,nuevoAlquiler);
        filtering.addComponents(filter, clearFilterTextBtn,layout);
        filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        if(checkIfOnMobile()) {
            filter.setWidth("58%");
            nuevaVenta.setCaption("+ Venta");
            nuevoAlquiler.setCaption("+ Alquiler");
            layout.setSpacing(false);
            layout.setMargin(false);
            layout.setResponsive(true);
            layout.setSizeUndefined();

        }

        addComponent(buildToolbar("Publicaciones",filtering, layout));
        grid.setSizeFull();
        mainLayout = new HorizontalLayout(grid, PublicacionVentaForm,PublicacionAlquilerForm);
        mainLayout.setSizeFull();
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

    public void showErrorNotification(String notification){
        Notification success = new Notification(
                notification);
        success.setDelayMsec(4000);
        success.setStyleName("bar error small");
        success.setPosition(Position.BOTTOM_CENTER);
        success.show(Page.getCurrent());
    }

    public void showSuccessNotification(String notification){
        Notification success = new Notification(
                notification);
        success.setDelayMsec(2000);
        success.setStyleName("bar success small");
        success.setPosition(Position.BOTTOM_CENTER);
        success.show(Page.getCurrent());
    }

    public void updateList() {
            List<Publicacion> customers = service.readAll(filter.getValue());
            grid.setItems(customers);

    }

    public boolean isIsonMobile() {
        return isonMobile;
    }

    public void ClearFilterBtnAction(){
            if(this.PublicacionVentaForm.isVisible()){
                nuevaVenta.focus();
                PublicacionVentaForm.cancel();

            }
            else if(this.PublicacionAlquilerForm.isVisible()){
                nuevoAlquiler.focus();
                PublicacionAlquilerForm.cancel();
            }

            filter.clear();
    }



    public boolean checkIfOnMobile(){
        if (Page.getCurrent().getBrowserWindowWidth() < 800) {
            isonMobile=true;
            return true;
        }
        else{
            isonMobile=false;
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
            isonMobile=true;
    }
        else{
        isonMobile=false;

    }

    }

    @Override
    public void enter(final ViewChangeListener.ViewChangeEvent event) {

    }


    public void enableGrid() { grid.setEnabled(true);}
}
