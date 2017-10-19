package com.TpFinal.Integracion.views.pageobjects;

import com.vaadin.testbench.ElementQuery;
import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.*;
import org.openqa.selenium.WebDriver;

/**
 * Created by Max on 10/17/2017.
 */
public class TBContratoView extends TestBenchTestCase {

    private ElementQuery<GridElement> grid1 ;
    private ElementQuery<TextFieldElement> textFieldFilter ;
    private ElementQuery<ButtonElement> button1;
    private ElementQuery<ButtonElement> nuevaVentaButton ;
    private ElementQuery<ComboBoxElement> inmuebleComboBoxVenta ;
    private ElementQuery<ComboBoxElement> compradorComboBox ;
    private ElementQuery<DateFieldElement> fechadeCelebracionDateFieldVenta ;
    private ElementQuery<TextFieldElement> valordeventaTextField ;
    private ElementQuery<RadioButtonGroupElement> tipoMonedaRadioButtonGroupVenta;
    private ElementQuery<TextFieldElement> estadoDocumentoTextFieldVenta;
    private ElementQuery<ButtonElement> guardarButtonVenta ;
    private ElementQuery<ButtonElement> eliminarButtonVenta ;
    private ElementQuery<UploadElement> uploadDocVenta ;
    private ElementQuery <ButtonElement> downloadContratoVenta ;
    private ElementQuery<ButtonElement> finalizarCargaButtonVemta ;
    private ElementQuery<ButtonElement> cancelNuevaVenta ;
    private ElementQuery<ButtonElement> nuevoAlquilerButton ;
    private ElementQuery <TabSheetElement> tabSheet1 ;
    private ElementQuery<ComboBoxElement> inmuebleComboBoxAlquiler ;
    private ElementQuery<ComboBoxElement> inquilinoComboBoxAlquiler;
    private ElementQuery<DateFieldElement> fechadeCelebracionDateFieldAlquiler;
    private ElementQuery<TextFieldElement> estadodocumentoTextFieldAlquiler ;
    private ElementQuery<UploadElement> uploadDocAlquiler ;
    private ElementQuery<ButtonElement> downloadContratoAlquiler ;
    private ElementQuery<ButtonElement> guardarButtonAlquiler ;
    private ElementQuery<ButtonElement> eliminarButtonAlquiler;
    private ElementQuery<ButtonElement> finalizarCargaButtonAlquiler ;
    private ElementQuery<ButtonElement> cancelNuevoAlquiler ;
    private ElementQuery<ComboBoxElement> duracinComboBox;
    private ElementQuery<TextFieldElement> dadePagoTextField ;
    private ElementQuery<TextFieldElement> recargoPunitorioTextField ;
    private ElementQuery<ComboBoxElement> tipoInteresComboBoxMonto;
    private ElementQuery<TextFieldElement> aumentoporActualizacinTextField ;
    private ElementQuery<ComboBoxElement> tipoInteresComboBox ;
    private ElementQuery<TextFieldElement> valorInicialTextField ;
    private ElementQuery<RadioButtonGroupElement> tipoMonedaRadioButtonGroupAlquiler ;
    private ElementQuery<TextFieldElement> diadePagoTextField ;




    public TBContratoView(WebDriver driver){
        setDriver(driver);

        grid1                               = $(GridElement.class);
        textFieldFilter                     = $(TextFieldElement.class);
        button1                             = $(VerticalLayoutElement.class).$(ButtonElement.class);
        nuevaVentaButton                    = $(ButtonElement.class).caption("Nueva Venta");
        inmuebleComboBoxVenta               = $(ComboBoxElement.class).caption("Inmueble");
        compradorComboBox                   = $(ComboBoxElement.class).caption("Comprador");
        fechadeCelebracionDateFieldVenta    = $(DateFieldElement.class).caption("Fecha de Celebracion");
        valordeventaTextField               = $(TextFieldElement.class).caption("Valor de venta $");
        tipoMonedaRadioButtonGroupVenta     = $(RadioButtonGroupElement.class).caption("Tipo Moneda");
        estadoDocumentoTextFieldVenta       = $(TextFieldElement.class).caption("Estado Documento");
        guardarButtonVenta                  = $(ButtonElement.class).caption("Guardar");
        eliminarButtonVenta                 = $(ButtonElement.class).caption("Eliminar");
        uploadDocVenta                      = $(UploadElement.class);
        downloadContratoVenta               = $(FormLayoutElement.class).$(ButtonElement.class);
        finalizarCargaButtonVemta           = $(ButtonElement.class).caption("Finalizar Carga");
        cancelNuevaVenta                    = $(VerticalLayoutElement.class).$(ButtonElement.class);
        nuevoAlquilerButton                 = $(ButtonElement.class).caption("Nuevo Alquiler");
        tabSheet1                           = $(TabSheetElement.class);
        inmuebleComboBoxAlquiler            = $(ComboBoxElement.class).caption("Inmueble");
        inquilinoComboBoxAlquiler           = $(ComboBoxElement.class).caption("Inquilino");
        fechadeCelebracionDateFieldAlquiler = $(DateFieldElement.class).caption("Fecha de Celebracion");
        estadodocumentoTextFieldAlquiler    = $(TextFieldElement.class).caption("Estado documento");
        uploadDocAlquiler                   = $(UploadElement.class);
        downloadContratoAlquiler            = $(FormLayoutElement.class).$(ButtonElement.class);
        guardarButtonAlquiler               = $(ButtonElement.class).caption("Guardar");
        eliminarButtonAlquiler              = $(ButtonElement.class).caption("Eliminar");
        finalizarCargaButtonAlquiler        = $(ButtonElement.class).caption("Finalizar Carga");
        cancelNuevoAlquiler                 = $(VerticalLayoutElement.class).$(ButtonElement.class);
        duracinComboBox                     = $(ComboBoxElement.class).caption("Duración");
        dadePagoTextField                   = $(TextFieldElement.class).caption("Día de Pago");
        recargoPunitorioTextField           = $(TextFieldElement.class).caption("Recargo Punitorio(%)");
        tipoInteresComboBox                 = $(ComboBoxElement.class).caption("Tipo Interes");
       //org.vaadin.risto.stepper.IntStepperElement frecuenciadeIncrementomesesorgvaadinristostepperIntStepper = $(org.vaadin.risto.stepper.IntStepperElement.class).caption("Frecuencia de Incremento(meses)").first();
        aumentoporActualizacinTextField     = $(TextFieldElement.class).caption("Aumento por Actualización");
        tipoInteresComboBoxMonto            = $(ComboBoxElement.class).caption("Tipo Interes");
        valorInicialTextField               = $(TextFieldElement.class).caption("Valor Inicial $");
        tipoMonedaRadioButtonGroupAlquiler  = $(RadioButtonGroupElement.class).caption("Tipo Moneda");
        diadePagoTextField                  = $(TextFieldElement.class).caption("Día de Pago");

    }


    public ElementQuery<GridElement> getGrid1() { return grid1; }

    public ElementQuery<TextFieldElement> getTextFieldFilter() { return textFieldFilter; }

    public ElementQuery<ButtonElement> getButton1() { return button1; }

    public ElementQuery<ButtonElement> getNuevaVentaButton() { return nuevaVentaButton; }

    public ElementQuery<ComboBoxElement> getInmuebleComboBoxVenta() { return inmuebleComboBoxVenta; }

    public ElementQuery<ComboBoxElement> getCompradorComboBox() { return compradorComboBox; }

    public ElementQuery<DateFieldElement> getFechadeCelebracionDateFieldVenta() { return fechadeCelebracionDateFieldVenta; }

    public ElementQuery<TextFieldElement> getValordeventaTextField() { return valordeventaTextField; }

    public ElementQuery<RadioButtonGroupElement> getTipoMonedaRadioButtonGroupVenta() { return tipoMonedaRadioButtonGroupVenta; }

    public ElementQuery<TextFieldElement> getEstadoDocumentoTextFieldVenta() { return estadoDocumentoTextFieldVenta; }

    public ElementQuery<ButtonElement> getGuardarButtonVenta() { return guardarButtonVenta; }

    public ElementQuery<ButtonElement> getEliminarButtonVenta() { return eliminarButtonVenta; }

    public ElementQuery<UploadElement> getUploadDocVenta() { return uploadDocVenta; }

    public ElementQuery<ButtonElement> getDownloadContratoVenta() { return downloadContratoVenta; }

    public ElementQuery<ButtonElement> getFinalizarCargaButtonVemta() { return finalizarCargaButtonVemta; }

    public ElementQuery<ButtonElement> getCancelNuevaVenta() { return cancelNuevaVenta; }

    public ElementQuery<ButtonElement> getNuevoAlquilerButton() { return nuevoAlquilerButton; }

    public ElementQuery<TabSheetElement> getTabSheet1() { return tabSheet1; }

    public ElementQuery<ComboBoxElement> getInmuebleComboBoxAlquiler() { return inmuebleComboBoxAlquiler; }

    public ElementQuery<ComboBoxElement> getInquilinoComboBoxAlquiler() { return inquilinoComboBoxAlquiler; }

    public ElementQuery<DateFieldElement> getFechadeCelebracionDateFieldAlquiler() { return fechadeCelebracionDateFieldAlquiler; }

    public ElementQuery<TextFieldElement> getEstadodocumentoTextFieldAlquiler() { return estadodocumentoTextFieldAlquiler; }

    public ElementQuery<UploadElement> getUploadDocAlquiler() { return uploadDocAlquiler; }

    public ElementQuery<ButtonElement> getDownloadContratoAlquiler() { return downloadContratoAlquiler; }

    public ElementQuery<ButtonElement> getGuardarButtonAlquiler() { return guardarButtonAlquiler; }

    public ElementQuery<ButtonElement> getEliminarButtonAlquiler() { return eliminarButtonAlquiler; }

    public ElementQuery<ButtonElement> getFinalizarCargaButtonAlquiler() { return finalizarCargaButtonAlquiler; }

    public ElementQuery<ButtonElement> getCancelNuevoAlquiler() { return cancelNuevoAlquiler; }

    public ElementQuery<ComboBoxElement> getDuracinComboBox() { return duracinComboBox; }

    public ElementQuery<TextFieldElement> getDadePagoTextField() { return dadePagoTextField; }

    public ElementQuery<TextFieldElement> getRecargoPunitorioTextField() { return recargoPunitorioTextField; }

    public ElementQuery<ComboBoxElement> getTipoInteresComboBoxMonto() { return tipoInteresComboBoxMonto; }

    public ElementQuery<TextFieldElement> getAumentoporActualizacinTextField() { return aumentoporActualizacinTextField; }

    public ElementQuery<ComboBoxElement> getTipoInteresComboBox() { return tipoInteresComboBox; }

    public ElementQuery<TextFieldElement> getValorInicialTextField() { return valorInicialTextField; }

    public ElementQuery<RadioButtonGroupElement> getTipoMonedaRadioButtonGroupAlquiler() { return tipoMonedaRadioButtonGroupAlquiler; }

    public boolean isDisplayed() { return this.grid1.exists(); }

    public ElementQuery<TextFieldElement> getDiadePagoTextField() { return diadePagoTextField; }

    public boolean isAlquilerFormDisplayed(){ return this.guardarButtonAlquiler.exists(); }

    public boolean isVentaFormDisplayed(){ return this.guardarButtonVenta.exists(); }
}
