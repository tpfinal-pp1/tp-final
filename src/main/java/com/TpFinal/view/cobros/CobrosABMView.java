package com.TpFinal.view.cobros;

import com.TpFinal.data.dto.contrato.Contrato;
import com.TpFinal.data.dto.contrato.ContratoAlquiler;
import com.TpFinal.data.dto.contrato.ContratoVenta;
import com.TpFinal.services.ContratoService;
import com.TpFinal.services.DashboardEvent;
import com.TpFinal.view.component.DefaultLayout;
import com.TpFinal.view.component.DialogConfirmacion;
import com.google.common.eventbus.Subscribe;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.List;

/**
 * Created by Max on 10/14/2017.
 */
@Title("Cobros")
@Theme("valo")
// @Widgetset("com.vaadin.v7.Vaadin7WidgetSet")
public class CobrosABMView extends DefaultLayout implements View {

    private TextField filter = new TextField();
    private Grid<Contrato> grid = new Grid<>();
    private Button newItem = new Button("Nuevo");
    private Button clearFilterTextBtn = new Button(VaadinIcons.CLOSE);
    private HorizontalLayout mainLayout;
    private CobrosForm cobrosForm = new CobrosForm(this);
    private boolean isonMobile = false;
    private Controller controller = new Controller();

    public CobrosABMView() {
        super();
        buildLayout();
        controller.configureComponents();

    }

    public Controller getController() {
        return controller;
    }

    private void buildLayout() {
        CssLayout filtering = new CssLayout();
        filtering.addComponents(filter, clearFilterTextBtn, newItem);
        filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        buildToolbar("Inmuebles", filtering);
        grid.setSizeFull();
        mainLayout = new HorizontalLayout(grid, cobrosForm);
        mainLayout.setSizeFull();
        addComponent(mainLayout);
        this.setExpandRatio(mainLayout, 1);
    }

    /**
     * Oculta o muestra los componentes de la grilla y su toolbar.
     *
     * @param b
     *            true para mostrar, false para ocultar
     */
    public void setComponentsVisible(boolean b) {
        newItem.setVisible(b);
        filter.setVisible(b);
        // clearFilterTextBtn.setVisible(b);
        if (isonMobile)
            grid.setVisible(b);

    }

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

    public boolean isIsonMobile() {
        return isonMobile;
    }

    public void ClearFilterBtnAction() {
        if (this.cobrosForm.isVisible()) {
            newItem.focus();
            cobrosForm.cancel();
        }
        filter.clear();
    }

    /*
     *
     * Deployed as a Servlet or Portlet.
     *
     * You can specify additional servlet parameters like the URI and UI class name
     * and turn on production mode when you have finished developing the
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
            System.out.println("Mobile!");
            isonMobile = true;
        } else {
            isonMobile = false;

        }

    }

    @Override
    public void enter(final ViewChangeListener.ViewChangeEvent event) {
    }

    public class Controller {

        private ContratoService contratoService = new ContratoService();

        public void configureComponents() {
            configureFilter();
            configureNewItem();
            configureGrid();
            updateList();
        }

        private void configureNewItem() {
            newItem.addClickListener(e -> {
                grid.asSingleSelect().clear();
                cobrosForm.clearFields();
                cobrosForm.setInmueble(null);
            });
            newItem.setStyleName(ValoTheme.BUTTON_PRIMARY);
        }

        private void configureFilter() {
            filter.addValueChangeListener(e -> updateList());
            filter.setValueChangeMode(ValueChangeMode.LAZY);
            filter.setPlaceholder("Filtrar");
            filter.setIcon(VaadinIcons.SEARCH);
            filter.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
            clearFilterTextBtn.setDescription("Limpiar filtro");
            clearFilterTextBtn.addClickListener(e -> ClearFilterBtnAction());
        }

        private void configureGrid() {
            grid.asSingleSelect().addValueChangeListener(event -> {
                if (event.getValue() == null) {
                    if (cobrosForm.isVisible())
                        setComponentsVisible(true);
                    cobrosForm.setVisible(false);
                    cobrosForm.clearFields();
                }
            });



            grid.addColumn(Contrato::getInmueble).setCaption("Inmueble");

            Grid.Column<Contrato, String> tipoCol = grid.addColumn(contrato -> {
                String ret = "";
                if (contrato instanceof ContratoVenta) {
                    ret = "Venta";
                } else {
                    ret = "Alquiler";
                }
                return ret;
            }).setCaption("Tipo");;

            Grid.Column<Contrato, String> fechaVencimientoCol = grid.addColumn(contrato -> {
                String ret = "";
                if(contrato instanceof ContratoAlquiler)
                    ret = contrato.getFechaCelebracion().plusMonths(((ContratoAlquiler) contrato).getDuracionContrato().getDuracion()).toString();
                return ret;
            }).setCaption("Fecha De Vencimiento");

            Grid.Column<Contrato, String> fechaDePago = grid.addColumn(contrato -> {
                String ret = "";
                if (contrato instanceof ContratoAlquiler) {
                    ret = ((ContratoAlquiler) contrato).getDiaDePago().toString();
                }
                return ret;
            }).setCaption("Fecha de Pago");

            Grid.Column<Contrato, String> inquilino = grid.addColumn(contrato -> {
                String ret = "";
                if (contrato instanceof ContratoAlquiler) {
                    ret = ((ContratoAlquiler) contrato).getInquilinoContrato().toString();
                }
                else if (contrato instanceof ContratoVenta){
                    ret = ((ContratoVenta) contrato).getComprador().toString();
                }
                return ret;
            }).setCaption("Inquilino");


            Grid.Column<Contrato, String> monto = grid.addColumn(contrato -> {
                String ret = "";
                if (contrato instanceof ContratoAlquiler) {
                    ret = ((ContratoAlquiler) contrato).getValorInicial().toString();
                }
                else if (contrato instanceof ContratoVenta){
                    ret = ((ContratoVenta) contrato).getPrecioVenta().toString();
                }
                return ret;
            }).setCaption("Monto");



            grid.addComponentColumn(inmueble -> {
                Button edit = new Button(VaadinIcons.EDIT);
                edit.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_SMALL, ValoTheme.BUTTON_PRIMARY);
                edit.addClickListener(e -> {
                   // cobrosForm.setInmueble(inmueble);

                });

                Button del = new Button(VaadinIcons.TRASH);
                del.addClickListener(click -> {
                    DialogConfirmacion dialog = new DialogConfirmacion("Eliminar",
                            VaadinIcons.WARNING,
                            "¿Esta seguro que desea Eliminar?",
                            "100px",
                            confirmacion -> {
                               // inmuebleService.delete(inmueble);
                               // showSuccessNotification("Borrado: " + inmueble.getDireccion().toString() + " de " +
                               //         inmueble.getPropietario().toString());
                                updateList();
                            });

                });
                del.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_SMALL, ValoTheme.BUTTON_DANGER);

                Button verFotos = new Button(VaadinIcons.PICTURE);
                verFotos.addClickListener(click -> {
                    Notification.show("A Implementar: Abrir Pantalla para ver fotos",
                            Notification.Type.WARNING_MESSAGE);
                });
                verFotos.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_SMALL);
                CssLayout hl = new CssLayout(edit, del, verFotos);
                hl.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
                return hl;
            }).setCaption("Acciones");


            grid.getColumns().forEach(c -> c.setResizable(false));
        }

        public void updateList() {
            List<Contrato> contratos = contratoService.readAll();
            System.out.println("cantidad de contratos "+contratos.size());
            grid.setItems(contratos);
        }



    }

}
