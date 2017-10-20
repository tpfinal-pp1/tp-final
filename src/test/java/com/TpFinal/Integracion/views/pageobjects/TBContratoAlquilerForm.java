package com.TpFinal.Integracion.views.pageobjects;

import com.vaadin.testbench.ElementQuery;
import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.*;
import org.openqa.selenium.WebDriver;

/**
 * Created by Max on 10/19/2017.
 */
public class TBContratoAlquilerForm extends TestBenchTestCase {

    private ElementQuery<TabSheetElement> tabSheet1 ;
    private ElementQuery<ButtonElement> cancel ;
    private ElementQuery<ComboBoxElement> inmuebleComboBox ;
    private ElementQuery<TextFieldElement> propietarioTextField ;
    private ElementQuery<ComboBoxElement> inquilinoComboBox ;
    private ElementQuery<DateFieldElement> fechadeCelebracionDateField ;
    private ElementQuery<TextFieldElement> estadodocumentoTextField ;
    private ElementQuery<UploadElement> uploadButton ;
    private ElementQuery<ButtonElement> downlooadButton ;
    private ElementQuery<ComboBoxElement> duracinComboBox ;
    private ElementQuery<TextFieldElement> dadePagoTextField ;
    private ElementQuery<TextFieldElement> recargoPunitorioTextField ;
    private ElementQuery<ComboBoxElement> tipoInteresComboboxMonto ;
    private ElementQuery<TextFieldElement> frecuenciadeIncrementomesesTextField ;
    private ElementQuery<TextFieldElement> aumentoporActualizacinTextField ;
    private ElementQuery<ComboBoxElement> tipoInteresComboBox ;
    private ElementQuery<TextFieldElement> valorInicialTextField ;
    private ElementQuery<RadioButtonGroupElement> tipoMonedaRadioButtonGroup ;
    private ElementQuery<ButtonElement> guardarButton ;
    private ElementQuery<ButtonElement> eliminarButton ;
    private ElementQuery<ButtonElement> finalizarCargaButton ;

    public TBContratoAlquilerForm(WebDriver driver){
         setDriver(driver);

         tabSheet1 = $(TabSheetElement.class);
         cancel = $(VerticalLayoutElement.class).$(ButtonElement.class);
         inmuebleComboBox = $(ComboBoxElement.class).caption("Inmueble");
         propietarioTextField = $(TextFieldElement.class).caption("Propietario");
         inquilinoComboBox = $(ComboBoxElement.class).caption("Inquilino");
         fechadeCelebracionDateField = $(DateFieldElement.class).caption("Fecha de Celebracion");
         estadodocumentoTextField = $(TextFieldElement.class).caption("Estado documento");
         uploadButton = $(UploadElement.class);
         downlooadButton = $(FormLayoutElement.class).$(ButtonElement.class);
         duracinComboBox = $(ComboBoxElement.class).caption("Duración");
         dadePagoTextField = $(TextFieldElement.class).caption("Día de Pago");
         recargoPunitorioTextField = $(TextFieldElement.class).caption("Recargo Punitorio(%)");
         tipoInteresComboBox = $(ComboBoxElement.class).caption("Tipo Interes");
         frecuenciadeIncrementomesesTextField = $(TextFieldElement.class).caption("Frecuencia de Incremento(meses)");
         aumentoporActualizacinTextField = $(TextFieldElement.class).caption("Aumento por Actualización");
         tipoInteresComboboxMonto = $(ComboBoxElement.class).caption("Tipo Interes");
         valorInicialTextField = $(TextFieldElement.class).caption("Valor Inicial $");
         tipoMonedaRadioButtonGroup = $(RadioButtonGroupElement.class).caption("Tipo Moneda");
         guardarButton = $(ButtonElement.class).caption("Guardar");
         eliminarButton = $(ButtonElement.class).caption("Eliminar");
         finalizarCargaButton = $(ButtonElement.class).caption("Finalizar Carga");
    }


    public ComboBoxElement getTipoInteres(){return this.tipoInteresComboBox.first();}

    public ComboBoxElement getTipoInteresMonto(){ return tipoInteresComboboxMonto.get(1);}

    public ElementQuery<TabSheetElement> getTabSheet1() { return tabSheet1; }

    public ElementQuery<ButtonElement> getCancel() { return cancel; }

    public ElementQuery<ComboBoxElement> getInmuebleComboBox() { return inmuebleComboBox; }

    public ElementQuery<TextFieldElement> getPropietarioTextField() { return propietarioTextField; }

    public ElementQuery<ComboBoxElement> getInquilinoComboBox() { return inquilinoComboBox; }

    public ElementQuery<DateFieldElement> getFechadeCelebracionDateField() { return fechadeCelebracionDateField; }

    public ElementQuery<TextFieldElement> getEstadodocumentoTextField() { return estadodocumentoTextField; }

    public ElementQuery<UploadElement> getUploadButton() { return uploadButton; }

    public ElementQuery<ButtonElement> getDownlooadButton() { return downlooadButton; }

    public ElementQuery<ComboBoxElement> getDuracinComboBox() { return duracinComboBox; }

    public ElementQuery<TextFieldElement> getDadePagoTextField() { return dadePagoTextField; }

    public ElementQuery<TextFieldElement> getRecargoPunitorioTextField() { return recargoPunitorioTextField; }

    public ElementQuery<TextFieldElement> getFrecuenciadeIncrementomesesTextField() { return frecuenciadeIncrementomesesTextField; }

    public ElementQuery<TextFieldElement> getAumentoporActualizacinTextField() { return aumentoporActualizacinTextField; }

    public ElementQuery<TextFieldElement> getValorInicialTextField() { return valorInicialTextField; }

    public ElementQuery<RadioButtonGroupElement> getTipoMonedaRadioButtonGroup() { return tipoMonedaRadioButtonGroup; }

    public ElementQuery<ButtonElement> getGuardarButton() { return guardarButton; }

    public ElementQuery<ButtonElement> getEliminarButton() { return eliminarButton; }

    public ElementQuery<ButtonElement> getFinalizarCargaButton() { return finalizarCargaButton; }

    public boolean isDisplayed(){ return this.guardarButton.exists();}
}
