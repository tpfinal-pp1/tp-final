package com.TpFinal.view.operacion;

import com.TpFinal.data.dto.inmueble.Inmueble;
import com.TpFinal.data.dto.inmueble.TipoMoneda;
import com.TpFinal.data.dto.publicacion.EstadoPublicacion;
import com.TpFinal.data.dto.publicacion.Publicacion;
import com.TpFinal.data.dto.publicacion.PublicacionVenta;
import com.TpFinal.services.InmuebleService;
import com.TpFinal.services.PublicacionService;
import com.TpFinal.view.component.BlueLabel;
import com.TpFinal.view.component.TinyButton;
import com.TpFinal.view.component.VentanaSelectora;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.converter.StringToBigDecimalConverter;
import com.vaadin.data.validator.DateRangeValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Responsive;
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
public class PublicacionVentaForm extends FormLayout {
    private PublicacionVenta PublicacionVenta;

    Button save = new Button("Guardar");
  //  Button test = new Button("Test");
    Button delete = new Button("Eliminar");
    DateField fechaPublicacion = new DateField("Fecha publicacion");
    RadioButtonGroup<EstadoPublicacion> estadoPublicacion = new RadioButtonGroup<>("Estado de la publicacion",EstadoPublicacion.toList());
   Button inmuebleSelector = new Button("");
    Label propietario = new Label ("Propietario: ");
    Label nombrePropietario = new Label();
    Inmueble inmuebleSeleccionado;
    TextField precio = new TextField("");
    ComboBox <TipoMoneda> moneda = new ComboBox<>("", TipoMoneda.toList());
    ComboBox <String> visualizadorInmueble= new ComboBox <String>("");


    //TODO una vez que este contrato venta ContratoVenta contratoVenta;
    // private NativeSelect<PublicacionVenta.Sexo> sexo = new NativeSelect<>("Sexo");

    PublicacionService service = new PublicacionService();
    private PublicacionABMView addressbookView;
    private Binder<PublicacionVenta> binderPublicacionVenta = new Binder<>(PublicacionVenta.class);




    TabSheet tabSheet;






    // Easily binding forms to beans and manage validation and buffering


    public PublicacionVentaForm(PublicacionABMView addressbook) {
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
        //  sexo.setItems(PublicacionVenta.Sexo.values());
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
        binderPublicacionVenta.forField(estadoPublicacion).bind(Publicacion::getEstadoPublicacion,Publicacion::setEstadoPublicacion);
        binderPublicacionVenta.forField(fechaPublicacion).withValidator(new DateRangeValidator(
                "Debe celebrarse desde mañana en adelante", LocalDate.now(),LocalDate.now().plusDays(365))
        ).bind(Publicacion::getFechaPublicacion,Publicacion::setFechaPublicacion);
        binderPublicacionVenta.forField(moneda).bind("moneda");
        binderPublicacionVenta.forField(precio).withConverter(new StringToBigDecimalConverter("Ingrese un numero")).bind("precio");
       //binderPublicacionVenta.forField(precio).bind("precio");
        visualizadorInmueble.setEnabled(false);


    }

    private void buildLayout() {
        setMargin(false);
        setSpacing(false);

        inmuebleSelector.setIcon(VaadinIcons.SEARCH);
        visualizadorInmueble.addStyleName(ValoTheme.COMBOBOX_BORDERLESS);
        inmuebleSelector.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        inmuebleSelector.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        HorizontalLayout hl = new HorizontalLayout();
        hl.addComponents(visualizadorInmueble, inmuebleSelector);
        hl.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        hl.setCaption("Inmueble");
        hl.setExpandRatio(visualizadorInmueble, 1f);
        visualizadorInmueble.setCaption(null);


        moneda.addStyleName(ValoTheme.COMBOBOX_BORDERLESS);
        precio.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);

        precio.setCaption("Precio");
        moneda.setCaption("Moneda");
        moneda.setEmptySelectionAllowed(false);

        tabSheet=new TabSheet();
        fechaPublicacion.setWidth("40%");
        moneda.setWidth("30%");
        FormLayout principal=new FormLayout(fechaPublicacion,estadoPublicacion,
                new BlueLabel("Inmueble"),hl,precio,moneda);
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

    public void setPublicacionVenta(PublicacionVenta PublicacionVenta) {

        this.PublicacionVenta = PublicacionVenta;
        binderPublicacionVenta.readBean(PublicacionVenta);
        inmuebleSeleccionado = PublicacionVenta.getInmueble();
        if(inmuebleSeleccionado == null)
            inmuebleSeleccionado = new Inmueble();
        else {
            visualizadorInmueble.setValue(inmuebleSeleccionado.toString());
        }

        // Show delete button for only Persons already in the database
        delete.setVisible(PublicacionVenta.getId()!=null);

        setVisible(true);
        getAddressbookView().setComponentsVisible(false);

        if(getAddressbookView().isIsonMobile())
            tabSheet.focus();

    }



    private void delete() {
        service.delete(PublicacionVenta);
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
           VentanaSelectora<Inmueble> inmueblesSelector= new VentanaSelectora<Inmueble>(inmuebleSeleccionado) {
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
                   visualizadorInmueble.setValue(inmuebleSeleccionado.toString());


               }

           };

   }

    private void save() {

        boolean success=false;
        try {


            this.PublicacionVenta.setInmueble(inmuebleSeleccionado);
            this.PublicacionVenta.getInmueble().addPublicacion(this.PublicacionVenta);
            this.PublicacionVenta.setPropietarioPublicacion(inmuebleSeleccionado.getPropietario());
            binderPublicacionVenta.writeBean(this.PublicacionVenta);
            service.save(this.PublicacionVenta);
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
       /* String msg = String.format("Guardado '%s %s'.", PublicacionVenta.getNombre(),
                PublicacionVenta.getApellido());*
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