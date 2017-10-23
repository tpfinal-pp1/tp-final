package com.TpFinal.view.inmuebles;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

import com.TpFinal.dto.inmueble.Inmueble;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class InmuebleABMViewWindow extends Window {
    private InmuebleABMView abmView;

    public InmuebleABMViewWindow(String caption, Supplier<List<Inmueble>> supplier, Predicate<Inmueble> filtroCustom) {
	super(caption);
	abmView = new InmuebleABMView(supplier, filtroCustom);
	setModal(true);
	setResizable(false);
	setClosable(true);
	setDraggable(false);
	setHeight(100.0f, Unit.PERCENTAGE);
	setWidth(100.0f, Unit.PERCENTAGE);
	center();
	VerticalLayout content = new VerticalLayout();
	content.setSizeFull();
	content.setMargin(new MarginInfo(true, true, true, true));
	content.addComponent(abmView);
	setContent(content);
	
	UI.getCurrent().addWindow(this);
	this.focus();
    }

}
