package com.PubliciBot.UI.Vistas.Controladores;

import com.PubliciBot.DM.*;
import com.PubliciBot.Services.*;
import com.PubliciBot.UI.MyUI;
import com.PubliciBot.UI.Vistas.VistaCamapana.ABMAccionView;
import com.PubliciBot.UI.Vistas.VistaCamapana.ABMCampanasView;
import com.PubliciBot.UI.Vistas.VistaCamapana.AccionView;
import com.PubliciBot.UI.Vistas.VistaCamapana.SelectorTags;
import com.PubliciBot.UI.authentication.StrictAccessControl;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Created by Hugo on 25/05/2017.
 */
public class ABMCampanasController extends HorizontalLayout {

    Label lblTitulo;

    TextField nombre;
    TextField descripcion;
    DateField fechaInicio;
    TextField duracion;
    ComboBox unidadMedida;
    TextArea txtMensaje;
    Image imgImgenMensaje;
    Label txtoduracion;

    Button btnGuardarCampana;

    Button seleccionarTags;
    CampanaService campanaService;

    AccionPublicitariaService publicitariaService;
    ABMAccionView accionView;

    HorizontalLayout hl;
    Button detalleCampanaSeleccionada;
    Campana nuevaCampana;

    ArrayList<Campana> creadasEnSesion;

    Button btnAgregarAccion;
    VerticalLayout verticalLayout;

    Button btnEjecutarAcciones;

    Upload subirArchivo;
    UploadReceiver uploadReceiver;

    UsuarioService usuarioService = new UsuarioService();

    ABMCampanasView addressbookUIView;

    AccionView accionView2;

    BeanFieldGroup<Campana> formFieldBindings;

    Button cancelar;



//comment

    public ABMCampanasController(ABMCampanasView adbUI) {
        super();

        this.addressbookUIView = adbUI;
        setMargin(true);
        initComponents();
        dibujarControles();
        cargarComboDuracion();
        //SE ABRE VENTANA PARA ASIGNAR TAGS A CAMPAÑA
        seleccionarTags.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                if(nuevaCampana == null)
                    Notification.show("primero se debe crear la campaña");
                else {
                    SelectorTags tagger = new SelectorTags();
                    tagger.setSelected(nuevaCampana);
                    tagger.setModal(true);
                    tagger.getSeleccionar().addClickListener(new Button.ClickListener() {
                        @Override
                        public void buttonClick(Button.ClickEvent clickEvent) {
                            nuevaCampana.getTags().clear();
                            Collection tagsCampana = tagger.getItems();
                            boolean isSelected ;
                            for (Object obj : tagsCampana) {
                                isSelected = tagger.isSelected(obj);
                                if(isSelected) {
                                    Tag t = (Tag) obj;
                                    if(!nuevaCampana.getTags().contains(t)) {
                                        ArrayList<Tag> hijos = tagger.getArbolTagService().buscarTagPorPadre(t);
                                        for (Tag hijo : hijos)
                                            campanaService.agregarTagACampana(nuevaCampana, hijo);
                                        campanaService.agregarTagACampana(nuevaCampana, t);
                                    }
                                }
                            }
                            //tagger.vaciarSeleccionados();
                            tagger.close();
                            Notification.show("Campanas agregadas: " + nuevaCampana.getTags());
                        }
                    });
                    tagger.getCerrar().addClickListener(new Button.ClickListener() {
                        @Override
                        public void buttonClick(Button.ClickEvent clickEvent) {
                            tagger.close();
                            tagger.vaciarSeleccionados();
                            if (nuevaCampanaNoTieneTags()) {
                                Notification.show("No se seleccionaron tags");
                            }
                        }
                    });
                    UI.getCurrent().addWindow(tagger);
                }
            }

        });

        btnAgregarAccion.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                if (nuevaCampana != null) {
                    accionView2.refreshAcciones(nuevaCampana);
                    accionView.setModal(true);
                    UI.getCurrent().addWindow(accionView);
                } else {
                    Notification.show("Capo, primero agrega Campaña");
                }
            }
        });


        btnGuardarCampana.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                guardar();

                //MedioService medioService = new MedioService(nuevaCampana.getAcciones())
            }
        });

        cancelar.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                setVisible(false);
               addressbookUIView.setNuevaCampanaVisibility();
            }
        });
        this.addStyleName("v-scrollable");
        this.setHeight("100%");

        this.setSpacing(false);
    }

    private boolean nuevaCampanaNoTieneTags() {
        return nuevaCampana.getTags().size() == 0;
    }

    public UnidadMedida obtenerUnidadMedida() {

        return (UnidadMedida) unidadMedida.getValue();

    }

    private void initComponents() {

        campanaService = new CampanaService();

        lblTitulo = new Label("Administración de Campañas");
        accionView2 = new AccionView(this);
        accionView = new ABMAccionView(accionView2);
        publicitariaService = new AccionPublicitariaService();
        nombre = new TextField("Nombre");
        descripcion = new TextField("Descripción");
        fechaInicio = new DateField("Fecha de inicio");
        fechaInicio.setValue(Date.from(Instant.now()));
        duracion = new TextField();
        txtoduracion =new Label("Duración");
        unidadMedida = new ComboBox();
        unidadMedida.setWidth(115, Unit.PIXELS);
        unidadMedida.setNullSelectionAllowed(false);
        unidadMedida.setTextInputAllowed(false);

        txtMensaje = new TextArea("Mensaje adjunto");
        txtMensaje.setValue("Mensaje de Prueba");
        //imgImgenMensaje = new Image("Imagen adjunta");

        seleccionarTags = new Button("Tags");
        btnGuardarCampana = new Button(" Guardar ");

        //detalleCampanaSeleccionada = new Button("Detalles Campaña");
        cancelar = new Button("Cancelar");
        btnGuardarCampana.setStyleName(ValoTheme.BUTTON_PRIMARY);
        btnGuardarCampana.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        hl = new HorizontalLayout();


        btnAgregarAccion = new Button("Acciones");


        uploadReceiver = new UploadReceiver();

        subirArchivo = new Upload("Agregar Imagen...", uploadReceiver);
        subirArchivo.setButtonCaption("Subir");

        creadasEnSesion = new ArrayList<>();



        /*
        uploadFile = new Upload("Upload Image Here", receiver);

        uploadFile.setImmediate(true);
        uploadFile.setButtonCaption("Subir imagen");

        uploadFile.addSucceededListener(receiver);
*/
    }


    private void dibujarControles() {

        verticalLayout = new VerticalLayout();
        HorizontalLayout horizontalLayout = new HorizontalLayout();

        VerticalLayout layoutcampana = new VerticalLayout();

        layoutcampana.setMargin(true);
        layoutcampana.setSpacing(true);

        layoutcampana.addComponents( nombre, descripcion, fechaInicio, txtoduracion);

        horizontalLayout.addComponents(duracion, unidadMedida);
        horizontalLayout.setSpacing(true);
        layoutcampana.addComponents(horizontalLayout, txtMensaje, subirArchivo);
        subirArchivo.setWidth(50,Unit.PERCENTAGE);

        seleccionarTags.setWidth(100,Unit.PERCENTAGE);
        // cancelar.setWidth(100,Unit.PERCENTAGE);
        //  btnGuardarCampana.setWidth(100,Unit.PERCENTAGE);
        //  btnAgregarAccion.setWidth(100,Unit.PERCENTAGE);
        //  seleccionarTags.setWidth(100,Unit.PERCENTAGE);

        VerticalLayout layoutcampanaEspaciada = new VerticalLayout();
        GridLayout grid = new GridLayout(2,2);

        layoutcampanaEspaciada.setSpacing(true);
        //layoutcampana.setSpacing(true);
        grid.setSpacing(true);
        layoutcampanaEspaciada.addComponent(grid);

        grid.addComponents(seleccionarTags);
        grid.addComponent(btnAgregarAccion);

        grid.addComponent(btnGuardarCampana);
        grid.addComponent(cancelar);

        layoutcampanaEspaciada.addComponent(grid);
        layoutcampana.addComponent(layoutcampanaEspaciada);

        verticalLayout.addComponent(layoutcampana);
        verticalLayout.setSpacing(true);
        //verticalLayout.addStyleName();
        this.addComponent(verticalLayout);

        this.setMargin(true);

    }


    private void cargarComboDuracion() {
        unidadMedida.addItems(UnidadMedida.values());
        unidadMedida.setValue(UnidadMedida.SEMANA);
    }

    private Usuario getUsuarioSesion() {
        StrictAccessControl strictAccessControl = (StrictAccessControl) ((MyUI) getUI()).getAccessControl();
        return strictAccessControl.getRecoveredUser();
    }


    public AccionPublicitariaService getPublicitariaService() {
        return this.publicitariaService;
    }

    public Campana getNuevaCampana() {
        return this.nuevaCampana;
    }



    private void cleanValidators(){
        nombre.removeAllValidators();
        descripcion.removeAllValidators();
        fechaInicio.removeAllValidators();
        duracion.removeAllValidators();
        txtMensaje.removeAllValidators();
        validateFields();
    }

    private void validateFields(){
        nombre.addValidator(
                new StringLengthValidator(
                        "Must be between 2 and 10 characters in length", 2, 10, false));
        descripcion.addValidator(
                new StringLengthValidator(
                        "Debe estar entre 0 y 50 caracteres", 0,50,true));

        duracion.addValidator(
                new IntegerRangeValidator("Como minimo 1", 1, Integer.MAX_VALUE ));

        txtMensaje.addValidator(new StringLengthValidator(
                "Debe estar por debajo de los 20 caracteres", 0,100,false));
    }

    public void crearCampana(Campana campana){
        this.nuevaCampana = campana;
        String mensajeCampana = campana.getMensaje().getTextoMensaje();
        txtMensaje.setValue(mensajeCampana);

        if(campana != null ){
            formFieldBindings = BeanFieldGroup.bindFieldsBuffered(campana, this);
            nombre.focus();
        }
    }


    public void guardar() {
        try {
            cleanValidators();
            boolean areValidFields = formFieldBindings.isValid();

            if(areValidFields) {
                // Commit the fields from UI to DAO
                formFieldBindings.commit();

                //MENSAJE CAMPAÑA
                String mensajeTxt = txtMensaje.getValue();
                Mensaje mensaje = null;

                String fileNameFinal = uploadReceiver.getFileName();
                mensaje = new Mensaje(mensajeTxt, fileNameFinal, nuevaCampana.getDescripcion());
                uploadReceiver.setFileName(null);

                PostService PS = new PostService();
                nuevaCampana.setMensaje(mensaje);
                PS.generarPosts(nuevaCampana);
                nuevaCampana.actualizarEstado();

                //nuevaCampana.setEstadoCampana(EstadoCampana.PRELIMINAR);

                Usuario actual = getUsuarioSesion();
                usuarioService.agregarCampañaAUsuario(nuevaCampana, actual);
                usuarioService.guardarUsuario(actual);
                cleanValidators();
                setVisible(false);

                addressbookUIView.refreshCampanas();
               addressbookUIView.setNuevaCampanaVisibility();

            }
        } catch (FieldGroup.CommitException e) {
            // Validation exceptions could be shown here
        }
    }

    public void eliminar(Campana seleccionada) {

        Usuario actual = getUsuarioSesion();


        actual.getCampanas().remove(seleccionada.getId());
        usuarioService.guardarUsuario(actual);
        for(Post post : seleccionada.getPosts()){
            Tasker.removePost(post);
            System.out.println("Tasker: Eliminando posts:"+post);
        }


        addressbookUIView.refreshCampanas();
    }
}