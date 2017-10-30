package com.TpFinal.view.cobros;

import com.TpFinal.dto.Localidad;
import com.TpFinal.dto.Provincia;
import com.TpFinal.dto.cobro.Cobro;
import com.TpFinal.dto.cobro.EstadoCobro;
import com.TpFinal.dto.inmueble.ClaseInmueble;
import com.TpFinal.dto.inmueble.Direccion;
import com.TpFinal.dto.inmueble.Inmueble;
import com.TpFinal.dto.inmueble.TipoInmueble;
import com.TpFinal.dto.persona.Persona;
import com.TpFinal.dto.persona.Propietario;
import com.TpFinal.dto.persona.Rol;
import com.TpFinal.services.CobroService;
import com.TpFinal.services.InmuebleService;
import com.TpFinal.services.PersonaService;
import com.TpFinal.services.ProvinciaService;
import com.TpFinal.utils.Utils;
import com.TpFinal.view.component.BlueLabel;
import com.TpFinal.view.component.DeleteButton;
import com.TpFinal.view.component.TinyButton;
import com.TpFinal.view.persona.PersonaFormWindow;
import com.vaadin.data.Binder;
import com.vaadin.data.BindingValidationStatus;
import com.vaadin.data.HasValue;
import com.vaadin.data.ValidationException;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Setter;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Max on 10/14/2017.
 */
public class CobrosForm extends FormLayout {
    private CobroService inmbService = new CobroService();
    private Cobro cobro;

    //TabSheet
    TabSheet inmuebleFromTabSheet;
    FormLayout flPrincipal;
    
    //tab principal
    TextField tfInmueble = new TextField("Inmueble");
    TextField tfDiasAtraso= new TextField("Dias de atraso");
    TextField tfNumeroDeCota = new TextField("Número de cuota");
    TextField tfMontoOriginal = new TextField("Monto sin interés $");
    TextField tfMontoTotal = new TextField("Monto total $");
    TextField tfMontoInteres = new TextField("Interés $");
    TextField tfComision = new TextField("Comisión $");
    TextField tfMontoPropietario = new TextField("Ganancia propietario $");
    DateField tfFechaVencimiento = new DateField("fechaDeVencimiento");
    DateField tfFechaDePago = new DateField("Fecha del pago");
    
    
    private CobrosABMView abmView;
    private Binder<Cobro> binderCobro = new Binder<>(Cobro.class);
    private ProvinciaService provinciaService = new ProvinciaService();
    
    //TabSheet tabSheet;

    public CobrosForm(CobrosABMView abmView) {
        this.abmView = abmView;
        configureComponents();
        binding();
        buildLayout();
    }

    private void configureComponents() {
    	//TODO
    }

    private void binding() {
    	binderCobro.forField(tfInmueble)
    	.bind(cobro -> cobro.getContrato().getInmueble().getDireccion().toString(), (cobro, sarasa) -> {});
    	
    	binderCobro.forField(tfDiasAtraso)
    	.bind(cobro -> {
    		Long l=new Long(0);
    		if(cobro.getEstadoCobro().equals(EstadoCobro.NOCOBRADO)) {
    			l= ChronoUnit.DAYS.between(cobro.getFechaDeVencimiento(), LocalDate.now());
        		if(l.compareTo(new Long(0))==-1)
        			l= new Long(0);
    		}else if(cobro.getEstadoCobro().equals(EstadoCobro.COBRADO)) {
    			l=ChronoUnit.DAYS.between(cobro.getFechaDeVencimiento(), cobro.getFechaDePago());
    		}
    		return l.toString();
    	}, (cobro, dias)->{});
    	
    	binderCobro.forField(this.tfNumeroDeCota)
    	.withConverter(new StringToIntegerConverter("Debe ingresar un número"))
    	.withValidator(n -> n >= 0, "Debe ingresar una altura no negativa!")
    	.bind(cobro -> cobro.getNumeroCuota(), (cobro,numero) ->{cobro.setNumeroCuota(numero);});
    	
    	binderCobro.forField(this.tfMontoOriginal)
    	.bind(cobro -> cobro.getMontoOriginal().toString(), (cobro, monto)-> cobro.setMontoOriginal(new BigDecimal(monto)));
    	
    	binderCobro.forField(this.tfMontoInteres)
    	.bind(cobro -> cobro.getInteres().toString(), (cobro, monto)-> cobro.setInteres(new BigDecimal(monto)));

    	binderCobro.forField(this.tfMontoTotal)
    	.bind(cobro -> cobro.getMontoRecibido().toString(), (cobro, monto)-> cobro.setMontoRecibido(new BigDecimal(monto)));
    	
    	binderCobro.forField(this.tfMontoPropietario)
    	.bind(cobro -> cobro.getMontoPropietario().toString(), (cobro, monto)-> cobro.setMontoPropietario(new BigDecimal(monto)));
    	
    	binderCobro.forField(this.tfComision)
    	.bind(cobro -> cobro.getComision().toString(), (cobro, monto)-> cobro.setComision(new BigDecimal(monto)));
    	
    	binderCobro.forField(this.tfFechaVencimiento)
    	.bind(cobro -> cobro.getFechaDeVencimiento(), (cobro, fecha) -> cobro.setFechaDeVencimiento(fecha));
    	
    	binderCobro.forField(this.tfFechaDePago)
    	.bind(cobro -> cobro.getFechaDePago(), (cobro, fecha) -> cobro.setFechaDePago(fecha));
    	
    	
    }

    private void buildLayout() {
    	flPrincipal = new FormLayout(tfInmueble,tfNumeroDeCota, tfDiasAtraso,tfMontoOriginal, tfMontoInteres, tfMontoTotal, tfMontoPropietario, tfComision, tfFechaVencimiento, tfFechaDePago);
    	inmuebleFromTabSheet = new TabSheet();
    	inmuebleFromTabSheet.addTab(flPrincipal, "Datos Principales");
    	addComponents(inmuebleFromTabSheet);
    	inmuebleFromTabSheet.setSelectedTab(flPrincipal);
    	flPrincipal.addComponents();
    	this.setEditables();
    	this.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
    	flPrincipal.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
    }

    public void setCobro(Cobro cobro) {

        if(cobro != null) {
            this.cobro = cobro;
            binderCobro.readBean(this.cobro);
        }else {
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
       //TODO
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
