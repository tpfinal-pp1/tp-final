package com.TpFinal.view.cobros;

import com.TpFinal.dto.cobro.Cobro;
import com.TpFinal.dto.cobro.EstadoCobro;
import com.TpFinal.dto.persona.Persona;
import com.TpFinal.dto.publicacion.Rol;
import com.TpFinal.services.CobroService;
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
import com.vaadin.server.Responsive;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.LocalDateRenderer;
import com.vaadin.ui.themes.ValoTheme;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by Max on 10/14/2017.
 */
@Title("Cobros")
@Theme("valo")
// @Widgetset("com.vaadin.v7.Vaadin7WidgetSet")
public class CobrosABMView extends DefaultLayout implements View {

    private TextField filter = new TextField();
    private Grid<Cobro> grid = new Grid<>();
    private Button clearFilterTextBtn = new Button(VaadinIcons.CLOSE);
    private HorizontalLayout mainLayout;
    private CobrosForm cobrosForm = new CobrosForm(this);
    private boolean isonMobile = false;
    private Controller controller = new Controller();
    RadioButtonGroup<String> filtroRoles = new RadioButtonGroup<>();
    Button seleccionFiltro = new Button(VaadinIcons.SEARCH_MINUS);
    Window sw = new Window("Filtrar");

    private int acciones = 0;

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
        filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        
        filtering.addComponents(filter, clearFilterTextBtn);
        HorizontalLayout hlf = new HorizontalLayout(seleccionFiltro, filtering);
        buildToolbar("Cobros", hlf);
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

        private CobroService cobroService = new CobroService();

        public void configureComponents() {
            configureFilter();
            configureGrid();
            updateList();
            cobrosForm.cancel();
           
        }

        private void configureFilter() {
            filter.addValueChangeListener(e -> updateList());
            filter.setValueChangeMode(ValueChangeMode.LAZY);
            filter.setPlaceholder("Filtrar");
            filter.setIcon(VaadinIcons.SEARCH);
            filter.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
            clearFilterTextBtn.setDescription("Limpiar filtro");
            clearFilterTextBtn.addClickListener(e -> ClearFilterBtnAction());
            seleccionFiltro.addClickListener(e -> abriVentanaSelectoraFiltros());
        }
        
        private void abriVentanaSelectoraFiltros() {
        	HorizontalLayout hl = new HorizontalLayout(filtroRoles);
        	hl.setMargin(true);
        	hl.setSpacing(true);
        	sw.setContent(hl);
        	filtroRoles.setItems("Todos", "Cobrados", "No cobrados");
        	filtroRoles.addValueChangeListener(l -> {
        	    System.out.println(l.getValue());
        	    String valor = l.getValue();
        	    filter(valor);
        	});
        	Responsive.makeResponsive(sw);
        	sw.setModal(true);
        	sw.setResizable(false);
        	sw.setClosable(true);
        	sw.setVisible(true);
        	sw.center();
        	UI.getCurrent().addWindow(sw);
        	sw.focus();
        }
        
        public void filter(String valor) {
        	List<Cobro> customers = null;
        	if (valor.equals("Todos")) {
        		 customers = cobroService.findAll(filter.getValue());
        	}
        	else if (valor.equals("Cobrados")) {
        		customers = cobroService.findByEstado(EstadoCobro.COBRADO.toString());

        	}
        	else if (valor.equals("No cobrados")) {
        		customers = cobroService.findByEstado(EstadoCobro.NOCOBRADO.toString());

        	}
        	grid.setItems(customers);
       }


        @SuppressWarnings("unchecked")
		private void configureGrid() {
            grid.asSingleSelect().addValueChangeListener(event -> {
                if (event.getValue() == null) {
                    if (cobrosForm.isVisible())
                        setComponentsVisible(true);
                    cobrosForm.setVisible(false);
                    cobrosForm.clearFields();
                    cobrosForm.setVisible(false);
                }
            });

            Grid.Column<Cobro,String> inmuebleCol = grid.addColumn(cobro -> {
                String ret = "";
                ret = cobro.getContrato().getInmueble().toString();
                return ret ;
            }).setCaption("Inmueble");

            Grid.Column<Cobro, String> tipoCol = grid.addColumn(cobro -> {
                String ret = "";
                ret = "Alquiler";
                return ret;
            }).setCaption("Tipo");

            grid.addColumn(Cobro::getFechaDeVencimiento, new LocalDateRenderer("dd/MM/yyyy")).setCaption("Fecha De Vencimiento");

           Grid.Column<Cobro, String> fechaCobroCol= grid.addColumn(cobro -> {
            		String ret="";
            		if(cobro.getFechaDePago()!=null) {
            			DateTimeFormatter formatters = DateTimeFormatter.ofPattern("d/MM/uuuu");
            			ret=cobro.getFechaDePago().format(formatters);
            		}
            		else
            			ret="No ha sido pagado";
            		return ret;
        }).setCaption("Fecha de Pago");

            Grid.Column<Cobro, String> inquilino = grid.addColumn(cobro -> {
                String ret = "";
                ret = cobro.getContrato().getInquilinoContrato().toString();
                return ret;
            }).setCaption("Inquilino");

            Grid.Column<Cobro, String> monto = grid.addColumn(cobro -> {
                String ret = "";
                ret = cobro.getMontoRecibido().toString();
                return ret;
            }).setCaption("Monto");


            grid.addComponentColumn(cobro -> {
                Button ver = new Button(VaadinIcons.EYE);
                ver.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_SMALL, ValoTheme.BUTTON_PRIMARY);
                ver.addClickListener(e -> {
                	 //TODO
                	cobrosForm.setCobro(cobro);

                });


                Button pagar = new Button(VaadinIcons.MONEY);
                pagar.addClickListener(click -> {
                	if(cobro.getEstadoCobro().equals(EstadoCobro.COBRADO)) {
		    				Notification.show("Este alquiler ya esta cobrado",
		                             Notification.Type.WARNING_MESSAGE);
		    			}else {
		    				 DialogConfirmacion dialog = new DialogConfirmacion("Cobrar alquiler",
		              			    VaadinIcons.WARNING,
		              			    "¿Esta seguro que desea cobrar este alquiler?",
		              			    "100px",
		              			    confirmacion -> {
		              			    			 if(cobro.getEstadoCobro().equals(EstadoCobro.NOCOBRADO)) {
		              			    				cobro.setEstadoCobro(EstadoCobro.COBRADO);
		              			    				cobro.setFechaDePago(LocalDate.now());
		              			    				cobroService.save(cobro);
		              			    			}
		              			    			updateList();
		              			    	});
		    			}
                	
                });
                pagar.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_SMALL);
                CssLayout hl = new CssLayout(ver, pagar);
                hl.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
                hl.setCaption("Accion "+acciones);
                acciones++;
                return hl;
            }).setCaption("Acciones");

            grid.getColumns().forEach(c -> c.setResizable(false));
            
        }

        public void updateList() {
            List<Cobro> cobros = cobroService.readAll();
            cobros.sort((c1, c2)-> {return c1.getFechaDeVencimiento().compareTo(c2.getFechaDeVencimiento());});
            grid.setItems(cobros);
        }

    }

}

