package com.TpFinal.Integracion.views.pageobjects.TBContratosView;

import com.vaadin.testbench.ElementQuery;
import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.*;
import org.openqa.selenium.WebDriver;

/**
 * Created by Max on 10/19/2017.
 */
public class TBContratoVentaView extends TestBenchTestCase{

    private ElementQuery<ComboBoxElement> inmuebleComboBox ;
    private ElementQuery<ComboBoxElement> compradorComboBox ;
    private ElementQuery<DateFieldElement> fechadeCelebracionDateField ;
    private ElementQuery<TextFieldElement> valordeventaTextField ;
    private ElementQuery<RadioButtonGroupElement> tipoMonedaRadioButtonGroup;
    private ElementQuery<TextFieldElement> estadoDocumentoTextField ;
    private ElementQuery<UploadElement> upload2 ;
    private ElementQuery<ButtonElement> downloadButton ;
    private ElementQuery<ButtonElement> guardarButton ;
    private ElementQuery<ButtonElement> eliminarButton ;
    private ElementQuery<ButtonElement> finalizarCargaButton ;
    private ElementQuery<ButtonElement> cancelButton ;

    public TBContratoVentaView(WebDriver driver){
        setDriver(driver);

        inmuebleComboBox = $(ComboBoxElement.class).caption("Inmueble");
        compradorComboBox = $(ComboBoxElement.class).caption("Comprador");
        fechadeCelebracionDateField = $(DateFieldElement.class).caption("Fecha de Celebracion");
        valordeventaTextField = $(TextFieldElement.class).caption("Valor de venta $");
        tipoMonedaRadioButtonGroup = $(RadioButtonGroupElement.class).caption("Tipo Moneda");
        estadoDocumentoTextField = $(TextFieldElement.class).caption("Estado Documento");
        upload2 = $(UploadElement.class);
        downloadButton = $(FormLayoutElement.class).$(ButtonElement.class);
        guardarButton = $(ButtonElement.class).caption("Guardar");
        eliminarButton = $(ButtonElement.class).caption("Eliminar");
        finalizarCargaButton = $(ButtonElement.class).caption("Finalizar Carga");
        cancelButton = $(VerticalLayoutElement.class).$(ButtonElement.class);
    }

    public ElementQuery<ComboBoxElement> getInmuebleComboBox() { return inmuebleComboBox; }

    public ElementQuery<ComboBoxElement> getCompradorComboBox() { return compradorComboBox; }

    public ElementQuery<DateFieldElement> getFechadeCelebracionDateField() { return fechadeCelebracionDateField; }

    public ElementQuery<TextFieldElement> getValordeventaTextField() { return valordeventaTextField; }

    public ElementQuery<RadioButtonGroupElement> getTipoMonedaRadioButtonGroup() { return tipoMonedaRadioButtonGroup; }

    public ElementQuery<TextFieldElement> getEstadoDocumentoTextField() { return estadoDocumentoTextField; }

    public ElementQuery<UploadElement> getUpload2() { return upload2; }

    public ElementQuery<ButtonElement> getDownloadButton() { return downloadButton; }

    public ElementQuery<ButtonElement> getGuardarButton() { return guardarButton; }

    public ElementQuery<ButtonElement> getEliminarButton() { return eliminarButton; }

    public ElementQuery<ButtonElement> getFinalizarCargaButton() { return finalizarCargaButton; }

    public ElementQuery<ButtonElement> getCancelButton() { return cancelButton; }

    public boolean isDisplayed(){return this.guardarButton.exists();}
}
