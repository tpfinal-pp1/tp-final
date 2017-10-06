package com.TpFinal.view.operacion;

import com.TpFinal.data.dto.inmueble.Inmueble;
import com.TpFinal.data.dto.publicacion.EstadoPublicacion;
import com.TpFinal.data.dto.publicacion.Publicacion;
import com.TpFinal.data.dto.publicacion.PublicacionVenta;
import com.TpFinal.services.InmuebleService;
import com.TpFinal.services.PublicacionService;
import com.TpFinal.view.component.VentanaSelectora;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.validator.DateRangeValidator;
import com.vaadin.event.ShortcutAction;
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
    Button inmuebleSelector = new Button("Seleccionar inmueble");
    Label propietario = new Label ("Propietario: ");
    Label nombrePropietario = new Label();
    Inmueble inmuebleSeleccionado;

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

    }

    private void buildLayout() {
        setSizeFull();
        setMargin(true);

        tabSheet=new TabSheet();

        VerticalLayout principal=new VerticalLayout(fechaPublicacion,estadoPublicacion,inmuebleSelector);
        HorizontalLayout adicional=new HorizontalLayout(propietario,nombrePropietario);
        principal.setSpacing(true);
        adicional.setSpacing(true);
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

       };


   }

    private void save() {

        boolean success=false;
        try {

            binderPublicacionVenta.writeBean(this.PublicacionVenta);
            service.save(this.PublicacionVenta);
            this.PublicacionVenta.setInmueble(inmuebleSeleccionado);
            this.PublicacionVenta.getInmueble().addPublicacion(this.PublicacionVenta);
            this.PublicacionVenta.setPropietarioPublicacion(inmuebleSeleccionado.getPropietario());
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