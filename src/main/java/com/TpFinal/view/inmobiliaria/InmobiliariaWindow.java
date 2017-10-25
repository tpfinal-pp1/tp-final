package com.TpFinal.view.inmobiliaria;

import com.TpFinal.dto.Localidad;
import com.TpFinal.dto.Provincia;
import com.TpFinal.dto.inmueble.Direccion;
import com.TpFinal.dto.persona.Persona;
import com.TpFinal.services.PersonaService;
import com.TpFinal.services.ProvinciaService;
import com.TpFinal.view.persona.PersonaFormWindow;
import com.vaadin.data.Binder;
import com.vaadin.data.HasValue;
import com.vaadin.data.ValidationException;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public abstract class InmobiliariaWindow extends PersonaFormWindow {

	public InmobiliariaWindow(Persona p) {
		super(p);
		this.root.setCaption("Inmobiliarias");
		this.root.setVisible(true);
		configure();
	}
	
	private void configure() {
		this.DNI.setVisible(false);
		this.apellido.setVisible(false);
		this.nombre.setCaption("Nombre Inmobiliaria");
	}



}
