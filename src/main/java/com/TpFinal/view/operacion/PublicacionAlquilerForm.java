package com.TpFinal.view.operacion;

import com.TpFinal.data.dto.inmueble.Inmueble;
import com.TpFinal.data.dto.inmueble.TipoMoneda;
import com.TpFinal.data.dto.publicacion.EstadoPublicacion;
import com.TpFinal.data.dto.publicacion.Publicacion;
import com.TpFinal.data.dto.publicacion.PublicacionAlquiler;
import com.TpFinal.services.InmuebleService;
import com.TpFinal.services.PublicacionService;
import com.TpFinal.view.component.BlueLabel;
import com.TpFinal.view.component.VentanaSelectora;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.converter.StringToBigDecimalConverter;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.data.validator.DateRangeValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.time.LocalDate;
import java.util.List;

/* Create custom UI Components.
 *
 * Create your own Vaadin components by inheritance and composition.
 * This is a form component inherited from VerticalLayout. Use
 * Use BeanFieldGroup to binding data fields from DTO to UI fields.
 * Similarly named field by naming convention or customized
 * with @PropertyId annotation.
 */
public class PublicacionAlquilerForm extends FormLayout {
    private PublicacionAlquiler PublicacionAlquiler;

    Button save = new Button("Guardar");
  //  Button test = new Button("Test");
    Button delete = new Button("Eliminar");
    DateField fechaPublicacion = new DateField("Fecha publicacion");
    RadioButtonGroup<EstadoPublicacion> estadoPublicacion = new RadioButtonGroup<>("Estado de la publicacion",EstadoPublicacion.toList());
    Button inmuebleSelector = new Button("Seleccionar inmueble");
    Label propietario = new Label ("Propietario: ");
    Label nombrePropietario = new Label();
    Inmueble inmuebleSeleccionado;
    TextField valorCuota = new TextField("Valor de cuota");
    ComboBox <TipoMoneda> moneda = new ComboBox<>("", TipoMoneda.toList());

   // TODO una vez que este contrato ContratoAlquiler contratoAlquiler;

    // private NativeSelect<PublicacionAlquiler.Sexo> sexo = new NativeSelect<>("Sexo");

    PublicacionService service = new PublicacionService();
    private PublicacionABMView addressbookView;
    private Binder<PublicacionAlquiler> binderPublicacionAlquiler = new Binder<>(PublicacionAlquiler.class);
    ComboBox <String> visualizadorInmueble= new ComboBox <String>("");
    TabSheet tabSheet;


    // Easily binding forms to beans and manage validation and buffering


    public PublicacionAlquilerForm(PublicacionABMView addressbook) {
        // setSizeUndefined();
        addressbookView=addressbook;
        configureComponents();
        binding();
        buildLayout();
        addStyleName("v-scrollable");
    }

    private void configureComponents() {
        /*
         * Highlight primary actions.
         *
         * With Vaadin built-in styles you can highlight the primary save button
         *
         * and give it a keyoard shortcut for a better UX.
         */

        //   sexo.setEmptySelectionAllowed(false);
        //  sexo.setItems(PublicacionAlquiler.Sexo.values());
        inmuebleSelector.addClickListener(e -> displayInmuebleSelector());
        delete.setStyleName(ValoTheme.BUTTON_DANGER);
        save.addClickListener(e -> this.save());
        delete.addClickListener(e -> this.delete());
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);



        setVisible(false);
    }


    private void binding(){
       //binder.bindInstanceFields(this); //Binding automatico
        binderPublicacionAlquiler.forField(estadoPublicacion).bind(Publicacion::getEstadoPublicacion,Publicacion::setEstadoPublicacion);
        binderPublicacionAlquiler.forField(fechaPublicacion).withValidator(new DateRangeValidator(
                "Debe celebrarse desde mañana en adelante", LocalDate.now(),LocalDate.now().plusDays(365))
        ).bind(Publicacion::getFechaPublicacion,Publicacion::setFechaPublicacion);

       binderPublicacionAlquiler.forField(moneda).bind("moneda"); //MAGIC
  binderPublicacionAlquiler.forField(valorCuota).withConverter(new StringToBigDecimalConverter("Ingrese un numero")).bind("valorCuota");







    }

    private void buildLayout() {
        inmuebleSelector.setIcon(VaadinIcons.SEARCH);
        visualizadorInmueble.addStyleName(ValoTheme.COMBOBOX_BORDERLESS);
        inmuebleSelector.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        inmuebleSelector.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        HorizontalLayout hl = new HorizontalLayout();
        hl.addComponents(visualizadorInmueble, inmuebleSelector);
        hl.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        hl.setCaption("Inmueble");
        hl.setExpandRatio(visualizadorInmueble, 1f);



        moneda.addStyleName(ValoTheme.COMBOBOX_BORDERLESS);
       valorCuota.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);

        valorCuota.setCaption(null);
        moneda.setCaption(null);
        visualizadorInmueble.setCaption(null);
        HorizontalLayout hl2 = new HorizontalLayout();
        hl2.addComponents(valorCuota,moneda);
        valorCuota.setSizeUndefined();
        moneda.setSizeUndefined();
        hl2.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        hl2.setCaption("Precio");
        hl2.setExpandRatio(moneda, 1f);
        hl2.setWidth("100%");

        moneda.setEmptySelectionAllowed(false);
        tabSheet=new TabSheet();
        fechaPublicacion.setWidth("20%");

        FormLayout principal=new FormLayout(fechaPublicacion,estadoPublicacion,new BlueLabel("Inmueble"),hl,hl2);
        moneda.setWidth("34%");
        principal.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);


        FormLayout adicional=new FormLayout(propietario,nombrePropietario);
        adicional.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        FormLayout mainLayout = new FormLayout(principal,adicional);



        tabSheet.addTab(principal,"Venta");
        tabSheet.addTab(adicional,"Contrato");


        addComponent(tabSheet);
        addComponent(mainLayout);
        HorizontalLayout actions = new HorizontalLayout(save,delete);
        addComponent(actions);
        actions.setSpacing(true);


    }

    public void setPublicacionAlquiler(PublicacionAlquiler PublicacionAlquiler) {

        this.PublicacionAlquiler = PublicacionAlquiler;
        binderPublicacionAlquiler.readBean(PublicacionAlquiler);
        inmuebleSeleccionado = PublicacionAlquiler.getInmueble();
        if(inmuebleSeleccionado == null)
            inmuebleSeleccionado = new Inmueble();

        // Show delete button for only Persons already in the database
        delete.setVisible(PublicacionAlquiler.getId()!=null);

        setVisible(true);

        getAddressbookView().setComponentsVisible(false);
        if(getAddressbookView().isIsonMobile())
            tabSheet.focus();

    }



    private void delete() {
        service.delete(PublicacionAlquiler);
        addressbookView.updateList();
        setVisible(false);
        getAddressbookView().setComponentsVisible(true);
        getAddressbookView().showSuccessNotification("Borrado");


    }

   /* private void test() {
        nombre.setValue(DummyDataGenerator.randomFirstName());
        apellido.setValue(DummyDataGenerator.randomLastName());
        mail.setValue(nombre.getValue()+"@"+apellido.getValue()+".com");
        DNI.setValue(DummyDataGenerator.randomNumber(8));
        telefono.setValue(DummyDataGenerator.randomPhoneNumber());
        telefono2.setValue(DummyDataGenerator.randomPhoneNumber());
        String info=DummyDataGenerator.randomText(80);
        if(info.length()>255){
            info=info.substring(0,255);

        }
        infoAdicional.setValue(info);


        save();

    }*/

    private void displayInmuebleSelector(){

        VentanaSelectora<Inmueble> inmuebles= new VentanaSelectora<Inmueble>(inmuebleSeleccionado) {
            @Override
            public void updateList() {
                InmuebleService InmuebleService=
                        new InmuebleService();
                List<Inmueble> inmuebles = InmuebleService.readAll();
                grid.setItems(inmuebles);

            }

            @Override
            public void setGrid() {
                grid=new Grid<Inmueble>(Inmueble.class);
            }

            @Override
            public void seleccionado(Inmueble seleccion) {
                inmuebleSeleccionado=seleccion;
            }


        };
    }


    private void save() {

        boolean success=false;
        try {
            binderPublicacionAlquiler.writeBean(PublicacionAlquiler);
            service.save(PublicacionAlquiler);
            success=true;


        } catch (ValidationException e) {
            e.printStackTrace();
            Notification.show("Error al guardar, porfavor revise los campos e intente de nuevo");
            // Notification.show("Error: "+e.getCause());
            return;
        }
        catch (Exception e){
            e.printStackTrace();
            Notification.show("Error: "+e.toString());
        }

        addressbookView.updateList();
       /* String msg = String.format("Guardado '%s %s'.", PublicacionAlquiler.getNombre(),
                PublicacionAlquiler.getApellido());*
        Notification.show(msg, Type.TRAY_NOTIFICATION);*/
        setVisible(false);
        getAddressbookView().setComponentsVisible(true);


        if(success)
            getAddressbookView().showSuccessNotification("Guardado");




    }

    public void cancel() {
        addressbookView.updateList();
        setVisible(false);
        getAddressbookView().setComponentsVisible(true);
    }



    public PublicacionABMView getAddressbookView() {
        return addressbookView;
    }



}