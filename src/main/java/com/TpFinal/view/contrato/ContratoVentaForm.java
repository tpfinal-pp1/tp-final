package com.TpFinal.view.contrato;

import com.TpFinal.data.dto.contrato.Contrato;
import com.TpFinal.data.dto.contrato.ContratoVenta;
import com.TpFinal.data.dto.inmueble.Inmueble;
import com.TpFinal.data.dto.inmueble.TipoMoneda;
import com.TpFinal.data.dto.persona.Persona;
import com.TpFinal.services.ContratoService;
import com.TpFinal.services.InmuebleService;
import com.TpFinal.services.PersonaService;
import com.TpFinal.view.component.*;
import com.vaadin.data.*;
import com.vaadin.data.converter.StringToBigDecimalConverter;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* Create custom UI Components.
 *
 * Create your own Vaadin components by inheritance and composition.
 * This is a form component inherited from VerticalLayout. Use
 * Use BeanFieldGroup to binding data fields from DTO to UI fields.
 * Similarly named field by naming convention or customized
 * with @PropertyId annotation.
 */
public class ContratoVentaForm extends FormLayout {
    private ContratoVenta ContratoVenta;

    // Actions
    Button save = new Button("Guardar");
    Button delete = new Button("Eliminar");

    // TabPrincipal
    ComboBox<Inmueble> cbInmuebles = new ComboBox<>("Inmueble");

    Label lblNombreVendedor = new Label("No seleccionado");
    ComboBox<Persona> cbComprador = new ComboBox<>("Comprador");
    DateField fechaCelebracion = new DateField("Fecha de Celebracion");

    // Documento
    public String nombreArchivo="";
    TextField tfDocumento = new TextField();
    File archivo;

    DownloadButton btDescargar = new DownloadButton();
    UploadButton btCargar = new UploadButton(new UploadReceiver() {
        @Override
        public void onSuccessfullUpload(String filename) {
            nombreArchivo=filename;
            tfDocumento.setValue("Documento cargado");
            btDescargar.setFile(filename);
            archivo=new File(this.getPathAndName());

        }
    });

    TextField tfPrecioDeVenta = new TextField("Valor de venta $");
    RadioButtonGroup<TipoMoneda> rbgTipoMoneda = new RadioButtonGroup<>("Tipo Moneda", TipoMoneda.toList());

    ContratoService service = new ContratoService();
    private ContratoABMView addressbookView;
    private Binder<ContratoVenta> binderContratoVenta = new Binder<>(ContratoVenta.class);




    TabSheet tabSheet;

    Persona person ; //TODO ver que hacer con persona




    // Easily binding forms to beans and manage validation and buffering


    public ContratoVentaForm(ContratoABMView addressbook) {
        // setSizeUndefined();
        addressbookView=addressbook;
        configureComponents();
        binding();
        buildLayout();
        updateComboInmuebles();
        updateComboCompradores();
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
        //  sexo.setItems(ContratoVenta.Sexo.values());
        delete.setStyleName(ValoTheme.BUTTON_DANGER);
        save.addClickListener(e -> this.save());
        delete.addClickListener(e -> this.delete());
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        cbInmuebles.addValueChangeListener(new HasValue.ValueChangeListener<Inmueble>() {
            @Override
            public void valueChange(HasValue.ValueChangeEvent<Inmueble> valueChangeEvent) {
                if(valueChangeEvent != null){
                    Inmueble inmueble = (Inmueble) valueChangeEvent.getValue();
                    if(inmueble == null) {
                        ContratoVenta.setInmueble(null);
                        lblNombreVendedor.setValue("No seleccionado");
                    }
                    else {
                        Persona vendedor = inmueble.getPropietario().getPersona();
                        lblNombreVendedor.setValue(vendedor.getNombre());
                        ContratoVenta.setInmueble(inmueble);
                        ContratoVenta.setVendedor(vendedor);
                    }
                }
            }
        });

        cbComprador.addValueChangeListener(new HasValue.ValueChangeListener<Persona>() {
            @Override
            public void valueChange(HasValue.ValueChangeEvent<Persona> valueChangeEvent) {
                if(valueChangeEvent != null){
                    Persona comprador = (Persona) valueChangeEvent.getValue();
                    if(comprador != null) {
                        ContratoVenta.setComprador(comprador);
                    }
                    else
                        ContratoVenta.setComprador(null);
                }
            }
        });

        btDescargar.addClickListener(event -> {
        	 btDescargar.descargar(ContratoVenta, "Contrato.doc");
        });
       

        

        setVisible(false);
    }


    private void binding(){
       //binder.bindInstanceFields(this); //Binding automatico
        binderContratoVenta.forField(fechaCelebracion).bind(Contrato::getFechaCelebracion,Contrato::setFechaCelebracion);
        binderContratoVenta.forField(rbgTipoMoneda).bind("moneda");
        binderContratoVenta.forField(cbInmuebles)
                .withNullRepresentation(new Inmueble())
                .bind(Contrato::getInmueble, Contrato::setInmueble);

        Validator<Persona> personaValidator = new Validator<Persona>() {
            @Override
            public ValidationResult apply(Persona persona, ValueContext valueContext) {
                ValidationResult result = new ValidationResult() {
                    @Override
                    public String getErrorMessage() {
                        if(ContratoVenta.getInmueble().getId() != null)
                        if(ContratoVenta.getInmueble().getPropietario().getPersona().getId().equals(persona.getId()))
                            return "Error: No se puede seleccionar al vendedor como comprador";
                        return "No se detectaron errores";
                    }

                    @Override
                    public boolean isError() {
                        if(ContratoVenta.getInmueble().getId() != null)
                            if(persona != null)
                            if(ContratoVenta.getInmueble().getPropietario().getPersona().getId().equals(persona.getId()))
                                return true;
                        return false;
                    }
                };
                return result;
            }
        };
        binderContratoVenta.forField(cbComprador).withValidator(personaValidator)
                .withNullRepresentation(new Persona())
                .bind(contratoVenta -> contratoVenta.getComprador(), (contratoVenta, persona) -> contratoVenta.setComprador(persona));

        /*binderContratoVenta.forField(lblVendedor)
                .withNullRepresentation("")
                .bind( (inmueble) -> inmueble.getVendedor().getNombre(), (inmueble,nombre) -> inmueble.getVendedor().setNombre(nombre));
        */

        binderContratoVenta.forField(tfPrecioDeVenta)
                .withNullRepresentation("")
                .withConverter(new StringToBigDecimalConverter("Ingrese un numero")).bind("precioVenta");




    }


    private void updateComboInmuebles() {
        InmuebleService is = new InmuebleService();
        cbInmuebles.setItems(is.readAll());
    }

        private void updateComboCompradores() {
        PersonaService ps = new PersonaService();
        cbComprador.setItems(ps.readAll());
    }

    private void buildLayout() {
        setSizeFull();

        tabSheet=new TabSheet();

        BlueLabel seccionDoc = new BlueLabel("Documento Word");
        //
        // TinyButton personas = new TinyButton("Ver Personas");
        //
        // personas.addClickListener(e -> getPersonaSelector());
        //
        // VerticalLayout Roles = new VerticalLayout(personas);
        //
        // fechaCelebracion.setWidth("100");


        if (this.addressbookView.checkIfOnMobile())
            rbgTipoMoneda.addStyleName(ValoTheme.OPTIONGROUP_SMALL);

        else
            rbgTipoMoneda.addStyleName(ValoTheme.OPTIONGROUP_HORIZONTAL);




        HorizontalLayout documentoButtonsRow = new HorizontalLayout();
        documentoButtonsRow.addComponents(btCargar, btDescargar);
        documentoButtonsRow.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        tfDocumento.setCaption("Nombre");
        btCargar.setStyleName(ValoTheme.BUTTON_BORDERLESS);
        btDescargar.setStyleName(ValoTheme.BUTTON_BORDERLESS);


        BlueLabel info = new  BlueLabel("Información Adicional");


        HorizontalLayout hl = new HorizontalLayout( lblNombreVendedor);
        hl.setCaption("Vendedor");
        FormLayout principal = new FormLayout(cbInmuebles, cbComprador, fechaCelebracion, hl, tfPrecioDeVenta, rbgTipoMoneda,seccionDoc,
                tfDocumento,
                documentoButtonsRow);

        principal.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);

        fechaCelebracion.setWidth("100");

        tabSheet.addTab(principal,"Venta");

        addComponent(tabSheet);
        HorizontalLayout actions = new HorizontalLayout(save,delete);

        addComponent(actions);
        this.setSpacing(false);
        actions.setSpacing(true);

        //  addStyleName("v-scrollable");

    }

    public void setContratoVenta(ContratoVenta ContratoVenta) {

        this.ContratoVenta = ContratoVenta;
        binderContratoVenta.readBean(ContratoVenta);

        // Show delete button for only Persons already in the database
        delete.setVisible(ContratoVenta.getId()!=null);

        setVisible(true);
        getAddressbookView().setComponentsVisible(false);

        if(getAddressbookView().isIsonMobile())
            tabSheet.focus();

    }



    private void delete() {
        service.delete(ContratoVenta);
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

    private void save() {

        boolean success=false;
        try {
            if(ContratoVenta.getInmueble() != null && ContratoVenta.getComprador() != null && ContratoVenta.getVendedor() != null) {
                if (ContratoVenta.getInmueble().getId() != null && ContratoVenta.getComprador().getId() != null && ContratoVenta.getVendedor().getId() != null) {
                    binderContratoVenta.writeBean(ContratoVenta);
                    if(!archivo.exists())
                        service.saveOrUpdate(ContratoVenta, null);
                    else{
                        service.saveOrUpdate(ContratoVenta, archivo);

                    }
                        success = true;
                }
            }

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
       /* String msg = String.format("Guardado '%s %s'.", ContratoVenta.getNombre(),
                ContratoVenta.getApellido());*
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



    public ContratoABMView getAddressbookView() {
        return addressbookView;
    }


    private void getPersonaSelector() {
        VentanaSelectora<Persona> personaSelector = new VentanaSelectora<Persona>(person) {
            @Override
            public void updateList() {
                PersonaService PersonaService=
                        new PersonaService();
                List<Persona> Personas = PersonaService.readAll();
                Collections.sort(Personas, new Comparator<Persona>() {

                    @Override
                    public int compare(Persona o1, Persona o2) {
                        return (int) (o2.getId() - o1.getId());
                    }
                });
                grid.setItems(Personas);
            }

            @Override
            public void setGrid() {
                grid=new Grid<>(Persona.class);
            }

            @Override
            public void seleccionado(Persona objeto) {
                   person=objeto;
            }


        };
        personaSelector.getSelectionButton().addClickListener(e -> person = personaSelector.getObjeto());
    }


}