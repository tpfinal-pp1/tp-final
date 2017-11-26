package com.TpFinal.view.cobros;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.apache.commons.io.FileExistsException;
import org.apache.log4j.Logger;

import com.TpFinal.dto.cobro.Cobro;
import com.TpFinal.dto.cobro.EstadoCobro;
import com.TpFinal.dto.contrato.ContratoAlquiler;
import com.TpFinal.dto.contrato.ContratoVenta;
import com.TpFinal.dto.movimiento.Movimiento;
import com.TpFinal.services.CobroService;
import com.TpFinal.services.MovimientoService;
import com.TpFinal.services.PersonaService;
import com.TpFinal.services.Planificador;
import com.TpFinal.services.ProvinciaService;
import com.TpFinal.view.component.DialogConfirmacion;
import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Created by Max on 10/14/2017.
 */
public class CobrosForm extends FormLayout {
    private static final Logger logger = Logger.getLogger(CobrosForm.class);

    private PersonaService personaService = new PersonaService();
    private CobroService inmbService = new CobroService();
    private MovimientoService movimientoService = new MovimientoService();
    private Cobro cobro;

    // TabSheet
    TabSheet inmuebleFromTabSheet;
    FormLayout flPrincipal;

    // tab principal
    TextField tfInmueble = new TextField("Inmueble");
    TextField tfDiasAtraso = new TextField("Dias de atraso");
    TextField tfNumeroDeCota = new TextField("Número de cuota");
    TextField tfMontoOriginal = new TextField("Monto sin interés $");
    TextField tfMontoTotal = new TextField("Monto total $");
    TextField tfMontoInteres = new TextField("Interés $");
    TextField tfComision = new TextField("Comisión $");
    TextField tfMontoPropietario = new TextField("Ganancia propietario $");
    DateField tfFechaVencimiento = new DateField("fechaDeVencimiento");
    DateField tfFechaDePago = new DateField("Fecha del pago");
    Button pagar = new Button("Pagar", VaadinIcons.MONEY);

    private CobrosABMView abmView;
    private Binder<Cobro> binderCobro = new Binder<>(Cobro.class);
    private ProvinciaService provinciaService = new ProvinciaService();
    private CobroService cobroService = new CobroService();

    // TabSheet tabSheet;

    public CobrosForm(CobrosABMView abmView) {
	this.abmView = abmView;
	configureComponents();
	binding();
	buildLayout();
    }

    private void configureComponents() {

	pagar.addClickListener(click -> {
	    if (cobro.getEstadoCobro().equals(EstadoCobro.COBRADO)) {
		Notification.show("Este alquiler ya esta cobrado",
			Notification.Type.WARNING_MESSAGE);
	    }else  if (cobro.getEstadoCobro().equals(EstadoCobro.ANULADO)) {
			Notification.show("Este alquiler fue rescindido y el cobro anulado",
					Notification.Type.WARNING_MESSAGE);
		}
	    else {
		DialogConfirmacion dialog = new DialogConfirmacion("Cobrar",
			VaadinIcons.WARNING,
			"¿Esta seguro que desea cobrar?",
			"100px",
			confirmacion -> {
			    if (cobro.getEstadoCobro().equals(EstadoCobro.NOCOBRADO)) {
				cobro.setEstadoCobro(EstadoCobro.COBRADO);
				cobro.setFechaDePago(LocalDate.now());
				boolean guardo = cobroService.save(cobro);
				if (guardo)
				    binderCobro.readBean(cobro);
				System.out.println("Fecha de cobro " + cobro.getFechaDePago());
				if (guardo) {
				    if (cobro.getContrato() instanceof ContratoAlquiler) {
					// Creando el movimiento al cobrar
					Movimiento movPagoAlquiler = MovimientoService.getInstanciaPagoAlquiler(cobro);
					movimientoService.saveOrUpdate(movPagoAlquiler);

					Movimiento movGananciaInmobiliaria = MovimientoService
						.getInstanciaGananciaInmobiliariaComprador(cobro);
					movimientoService.saveOrUpdate(movGananciaInmobiliaria);

					Movimiento movPagoAPropietario = MovimientoService.getInstanciaPagoAPropietario(
						cobro);
					movimientoService.saveOrUpdate(movPagoAPropietario);

					if (cobroService.esAtrasado(cobro)) {
					    try {
						cobroService.enviarMailPorPagoAtrasado(cobro);
						System.out.println("[DEBUG] Mail enviado");
					    } catch (IllegalArgumentException | FileExistsException e) {
						System.err.println("Error al enviar el mail o en las properties");
						e.printStackTrace();
					    }
					}

					Planificador.get().removeJobCobroVencido(cobro);
					Planificador.get().removeJobCobroPorVencer(cobro);

				    } else if (cobro.getContrato() instanceof ContratoVenta) {
					Movimiento movPagoAlquiler = MovimientoService.getInstanciaPagoVenta(cobro);
					movimientoService.saveOrUpdate(movPagoAlquiler);

					Movimiento movPagoAPropietario = MovimientoService.getInstanciaPagoAPropietario(
						cobro);
					movimientoService.saveOrUpdate(movPagoAPropietario);
					
					Movimiento movGananciaInmobiliariaVendedor = MovimientoService.getInstanciaGananciaInmobiliariaVendedor(cobro);
					movimientoService.saveOrUpdate(movGananciaInmobiliariaVendedor);

					Movimiento movGananciaInmobiliariaComprador = MovimientoService.getInstanciaGananciaInmobiliariaComprador(cobro);
					movimientoService.saveOrUpdate(movGananciaInmobiliariaComprador);
					
					Movimiento selladoIngreso = MovimientoService.getInstanciaSelladoVentaIngreso(cobro);
					movimientoService.saveOrUpdate(selladoIngreso);
					Movimiento selladoEgreso = MovimientoService.getInstanciaSelladoVentaEgreso(cobro);
					movimientoService.saveOrUpdate(selladoEgreso);
				    }
				}
			    }
			    abmView.getController().updateList();
			    this.binderCobro.readBean(cobro);
			});
	    }

	});
    }

    private void binding() {
	binderCobro.forField(tfInmueble)
		.bind(cobro -> cobro.getContrato().getInmueble().getDireccion().toString(), (cobro, sarasa) -> {
		});

	binderCobro.forField(tfDiasAtraso)
		.bind(cobro -> {
		    Long l = new Long(0);
		    if (cobro.getEstadoCobro().equals(EstadoCobro.NOCOBRADO)) {
			l = ChronoUnit.DAYS.between(cobro.getFechaDeVencimiento(), LocalDate.now());
			if (l.compareTo(new Long(0)) == -1)
			    l = new Long(0);
		    } else if (cobro.getEstadoCobro().equals(EstadoCobro.COBRADO)) {
			l = ChronoUnit.DAYS.between(cobro.getFechaDeVencimiento(), cobro.getFechaDePago());
			if (l.compareTo(new Long(0)) == -1)
			    l = new Long(0);
		    }
		    return l.toString();
		}, (cobro, dias) -> {
		});

	binderCobro.forField(this.tfNumeroDeCota)
		.withConverter(new StringToIntegerConverter("Debe ingresar un número"))
		.withValidator(n -> n >= 0, "Debe ingresar una altura no negativa!")
		.bind(cobro -> cobro.getNumeroCuota(), (cobro, numero) -> {
		    cobro.setNumeroCuota(numero);
		});

	binderCobro.forField(this.tfMontoOriginal)
		.bind(cobro -> cobro.getMontoOriginal().toString(), (cobro, monto) -> cobro.setMontoOriginal(
			new BigDecimal(monto)));

	binderCobro.forField(this.tfMontoInteres)
		.bind(cobro -> cobro.getInteres().toString(), (cobro, monto) -> cobro.setInteres(new BigDecimal(
			monto)));

	binderCobro.forField(this.tfMontoTotal)
		.bind(cobro -> cobro.getMontoRecibido().toString(), (cobro, monto) -> cobro.setMontoRecibido(
			new BigDecimal(monto)));

	binderCobro.forField(this.tfMontoPropietario)
		.bind(cobro -> cobro.getMontoPropietario().toString(), (cobro, monto) -> cobro.setMontoPropietario(
			new BigDecimal(monto)));

	binderCobro.forField(this.tfComision)
		.bind(cobro -> cobro.getComision().toString(), (cobro, monto) -> cobro.setComision(new BigDecimal(
			monto)));

	binderCobro.forField(this.tfFechaVencimiento)
		.bind(cobro -> cobro.getFechaDeVencimiento(), (cobro, fecha) -> cobro.setFechaDeVencimiento(fecha));

	binderCobro.forField(this.tfFechaDePago)
		.bind(cobro -> cobro.getFechaDePago(), (cobro, fecha) -> cobro.setFechaDePago(fecha));

    }

    private void buildLayout() {
	VerticalLayout vl = new VerticalLayout();
	flPrincipal = new FormLayout(tfInmueble, tfNumeroDeCota, tfDiasAtraso, tfMontoOriginal, tfMontoInteres,
		tfMontoTotal, tfMontoPropietario, tfComision, tfFechaVencimiento, tfFechaDePago);
	vl.addComponent(flPrincipal);
	vl.addComponent(pagar);
	vl.setComponentAlignment(pagar, Alignment.MIDDLE_CENTER);

	inmuebleFromTabSheet = new TabSheet();
	inmuebleFromTabSheet.addTab(vl, "Datos Principales");
	addComponents(inmuebleFromTabSheet);
	inmuebleFromTabSheet.setSelectedTab(vl);
	flPrincipal.addComponents();
	this.setEditables();
	this.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
	flPrincipal.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
    }

    public void setCobro(Cobro cobro) {
    	if(cobro.getEstadoCobro()==EstadoCobro.ANULADO)
    		Notification.show("ANULADO");
	if (cobro != null) {
	    this.cobro = cobro;
	    binderCobro.readBean(this.cobro);
	} else {
	}
	setVisible(true);
	getABMView().setComponentsVisible(false);
	if (getABMView().isIsonMobile())
	    this.focus();
    }

    public void cancel() {
	abmView.getController().updateList();
	setVisible(false);
	getABMView().setComponentsVisible(true);
    }

    public CobrosABMView getABMView() {
	return abmView;
    }

    public void clearFields() {
	// TODO
    }

    private void setEditables() {
	tfInmueble.setEnabled(false);
	tfDiasAtraso.setEnabled(false);
	tfNumeroDeCota.setEnabled(false);
	tfMontoOriginal.setEnabled(false);
	tfMontoTotal.setEnabled(false);
	tfMontoInteres.setEnabled(false);
	tfComision.setEnabled(false);
	tfMontoPropietario.setEnabled(false);
	tfFechaVencimiento.setEnabled(false);
	tfFechaDePago.setEnabled(false);
	tfInmueble.setWidth("100%");
	tfDiasAtraso.setWidth("100%");
	tfNumeroDeCota.setWidth("100%");
	tfMontoOriginal.setWidth("100%");
	tfMontoTotal.setWidth("100%");
	tfMontoInteres.setWidth("100%");
	tfComision.setWidth("100%");
	tfMontoPropietario.setWidth("100%");
	tfFechaVencimiento.setWidth("100%");
	tfFechaDePago.setWidth("100%");
    }
}
