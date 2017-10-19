package com.TpFinal.view.cobros;

import com.TpFinal.dto.Localidad;
import com.TpFinal.dto.Provincia;
import com.TpFinal.dto.cobro.Cobro;
import com.TpFinal.dto.inmueble.ClaseInmueble;
import com.TpFinal.dto.inmueble.Direccion;
import com.TpFinal.dto.inmueble.Inmueble;
import com.TpFinal.dto.inmueble.TipoInmueble;
import com.TpFinal.dto.persona.Persona;
import com.TpFinal.dto.persona.Propietario;
import com.TpFinal.dto.publicacion.Rol;
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
    FormLayout principal;
    FormLayout caracteristicas1;
    
    //tab principal
    private TextField tfNumeroDeCota = new TextField("Número de cuota");
    private TextField tfMontoOriginal = new TextField("Monto sin interés");
    private TextField tfMontoTotal = new TextField("Monto total");
    private TextField tfMontoInteres = new TextField("Interés");
    private TextField tfComision = new TextField("Comisión");
    private TextField tfMontoPropietario = new TextField("Ganancia propietario");
    private TextField tfFechaVencimiento = new TextField("fechaDeVencimiento");
    private TextField tfFechaDePago = new TextField("Fecha del pago");
    
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
    	binderCobro.forField(this.tfNumeroDeCota)
    	.withConverter(new StringToIntegerConverter("Debe ingresar un número"))
    	.withValidator(n -> n >= 0, "Debe ingresar una altura no negativa!")
    	.bind(cobro -> cobro.getNumeroCuota(), (cobro,numero) ->{cobro.setNumeroCuota(numero);});
    	
    }

    private void buildLayout() {
    	principal = new FormLayout(tfNumeroDeCota, tfMontoOriginal, tfMontoInteres, tfMontoTotal, tfMontoPropietario, tfComision, tfFechaVencimiento, tfFechaDePago);
    	caracteristicas1=new FormLayout();
    	inmuebleFromTabSheet = new TabSheet();
    	inmuebleFromTabSheet.addTab(principal, "Datos Principales");
    	inmuebleFromTabSheet.addTab(caracteristicas1, "Características");
    	addComponents(inmuebleFromTabSheet);
    	inmuebleFromTabSheet.setSelectedTab(principal);
    	principal.addComponents();
    	this.setEditables();
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
        tfNumeroDeCota.setEnabled(false);
        tfMontoOriginal.setEnabled(false);
        tfMontoTotal.setEnabled(false); 
        tfMontoInteres.setEnabled(false);
        tfComision.setEnabled(false); 
        tfMontoPropietario.setEnabled(false); 
        tfFechaVencimiento.setEnabled(false);
        tfFechaDePago.setEnabled(false);
    }
}
