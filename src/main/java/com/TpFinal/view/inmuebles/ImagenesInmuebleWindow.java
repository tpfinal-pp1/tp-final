package com.TpFinal.view.inmuebles;

import com.TpFinal.dto.inmueble.Inmueble;
import com.TpFinal.services.InmuebleService;
import com.TpFinal.utils.Utils;
import com.TpFinal.view.component.DeleteButton;
import com.TpFinal.view.component.UploadButton;
import com.TpFinal.view.component.AbstractUploadReceiver;
import com.TpFinal.view.component.UploadReceiver;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Resource;
import com.vaadin.server.Responsive;
import com.vaadin.server.Sizeable;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.Set;

public abstract class ImagenesInmuebleWindow extends Window {
    public static final String ID = "profilepreferenceswindow";

    /*
     * Fields for editing the User object are defined here as class members. They
     * are later bound to a FieldGroup by calling fieldGroup.bindMemberFields(this).
     * The Fields' values don't need to be explicitly set, calling
     * fieldGroup.setItemDataSource(user) synchronizes the fields with the user
     * object.
     */

    private InmuebleService inmbService = new InmuebleService();
    protected Inmueble inmueble;
    DeleteButton delete = new DeleteButton("Eliminar",
	    VaadinIcons.WARNING, "Eliminar", "20%", e -> delete());
    ListSelect<String> select = new ListSelect<>(null);
    Button makePortada = new Button("Portada", e -> setPortada());
    private Image preview = new Image(null);
    private Component profiletab;
    private Panel panel;
    private Component footer;
    private VerticalLayout root;
    UploadReceiver uploadReciever = new UploadReceiver();
    UploadButton upload = new UploadButton(uploadReciever);

    private void resize() {
	center();
	fireResize();
	addStyleName("v-scrollable");

    }

    private void refreshListSelect() {
	select.setItems(inmueble.getPathImagenes());
	boolean onlyOne = inmueble.getPathImagenes().size() == 1;
	if (inmueble.getPathImagenes().iterator().hasNext()) {
	    String primera = inmueble.getPathImagenes().iterator().next();
	    preview.setSource(InmuebleService.GenerarStreamResource(
		    primera));
	    if (onlyOne) {
		inmueble.setNombreArchivoPortada(primera);
		makePortada.setIcon(VaadinIcons.BOOKMARK);
	    }

	} else {
	    System.out.println("SIn imagenes");
	    preview.setSource(new ThemeResource("sinPortada.png"));
	}
	resize();

    }

    private void setPortada() {
	if (select.getSelectedItems().iterator().hasNext())
	    inmueble.setNombreArchivoPortada(
		    select.getSelectedItems().iterator().next());

	makePortada.setIcon(VaadinIcons.BOOKMARK);
	resize();

    }

    private void delete() {
	for (String path : select.getSelectedItems()) {
	    inmueble.removePathImagen(path);
	    if (path.equals(inmueble.getNombreArchivoPortada())) {

		if (inmueble.getPathImagenes().size() > 0)
		    inmueble.setNombreArchivoPortada(inmueble.getPathImagenes().iterator().next());
		else
		    inmueble.setNombreArchivoPortada(null);
	    }
	}

	refreshListSelect();
    }

    public ImagenesInmuebleWindow(Inmueble inmueble) {
	this.inmueble = inmueble;
	refreshListSelect();
	configureComponents();

	select.setItems(inmueble.getPathImagenes());
	select.setRows(4);
	select.setResponsive(true);
	select.setSizeFull();

	select.addValueChangeListener(event -> {
	    Set<String> selected = event.getValue();
	    if (selected.size() == 1) {
		if (selected.iterator().hasNext()) {
		    String first = selected.iterator().next();
		    Resource res = InmuebleService.GenerarStreamResource(first);
		    preview.setSource(res);
		    if (first.equals(inmueble.getNombreArchivoPortada())) {
			makePortada.setIcon(VaadinIcons.BOOKMARK);
		    } else {
			makePortada.setIcon(null);
		    }

		}
		resize();
	    }

	});
	if (inmueble.getPathImagenes().iterator().hasNext()) {
	    String first = inmueble.getPathImagenes().iterator().next();
	    Resource res = InmuebleService.GenerarStreamResource(first);
	    preview.setSource(res);

	}

    }

    public abstract void onClose();

    private void configureComponents() {

	upload.addSucceededListener(success -> {
	    if (uploadReciever.getFileName() != null && uploadReciever.getFileName() != "") {
		inmueble.addPathImagen(uploadReciever.getFileName());
		refreshListSelect();
		try {
		    uploadReciever.getOutputFile().flush();
		    uploadReciever.getOutputFile().close();
		} catch (Exception e) {

		}
	    }
	});

	preview = new Image(null, null);
	preview.setSizeUndefined();
	// preview.setWidth(100.0f, Unit.PIXELS);
	preview.setCaption(null);

	// delete.setWidth(28.0f,Unit.PERCENTAGE);
	// makePortada.setWidth(28.0f,Unit.PERCENTAGE);
	// upload.setWidth(28.0f,Unit.PERCENTAGE);

	// select.setWidth(40.0f,Unit.PERCENTAGE);
	// addStyleName("profile-window");
	setId(ID);
	Responsive.makeResponsive(this);

	setModal(true);
	setCloseShortcut(ShortcutAction.KeyCode.ESCAPE, null);
	setResizable(false);
	setClosable(true);
	setSizeUndefined();

	root = new VerticalLayout();

	// content.setSizeFull();

	TabSheet detailsWrapper = new TabSheet();
	// detailsWrapper.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
	detailsWrapper.addStyleName(ValoTheme.TABSHEET_ICONS_ON_TOP);
	detailsWrapper.addStyleName(ValoTheme.TABSHEET_CENTERED_TABS);
	profiletab = buildProfileTab();
	detailsWrapper.addComponent(profiletab);

	// content.setExpandRatio(detailsWrapper, 1f);

	// preview.setSizeUndefined();
	HorizontalLayout HL = new HorizontalLayout(select);
	HL.setWidth(100, Unit.PERCENTAGE);
	panel = new Panel(preview);
	panel.setWidth(100.0f, Unit.PERCENTAGE);

	panel.setSizeFull();
	// preview.setHeight(57.0f, Sizeable.Unit.PERCENTAGE);
	preview.setWidth(100.0f, Unit.PERCENTAGE);
	// panel.setHeight(600.0f, Sizeable.Unit.PIXELS);

	footer = buildFooter();
	root.addComponents(detailsWrapper, panel, select, footer);
	root.setComponentAlignment(footer, Alignment.BOTTOM_CENTER);
	setContent(root);
	this.setResizeLazy(false);

	setWidth(400.0f, Sizeable.Unit.PIXELS);
	center();
	getUI().getCurrent().addWindow(this);
	focus();
	this.addCloseListener(new Window.CloseListener() {
	    @Override
	    public void windowClose(Window.CloseEvent closeEvent) {
		onClose();
	    }
	});

    }

    private Component buildFooter() {

	HorizontalLayout footer = new HorizontalLayout();
	footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
	footer.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);
	// footer.setSpacing(true);

	makePortada.setStyleName(ValoTheme.BUTTON_PRIMARY);
	footer.addComponents(delete, upload, makePortada);

	footer.setComponentAlignment(makePortada, Alignment.TOP_RIGHT);
	footer.setComponentAlignment(upload, Alignment.TOP_CENTER);
	footer.setComponentAlignment(delete, Alignment.TOP_LEFT);
	return footer;
    }

    private Component buildProfileTab() {
	HorizontalLayout profile = new HorizontalLayout();
	profile.setCaption("Imagenes");
	profile.setIcon(VaadinIcons.CAMERA);
	// root.setHeight(500.0f, Unit.PERCENTAGE);
	// root.setMargin(true);
	return profile;
    }

}
