package com.TpFinal.view.movimientos;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.stream.Collectors;

import com.TpFinal.dto.inmueble.TipoMoneda;
import com.TpFinal.dto.movimiento.Movimiento;
import com.TpFinal.dto.movimiento.TipoMovimiento;
import com.TpFinal.services.MovimientoService;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class ResumenWindow extends CustomComponent {
    /**
     *
     */

    private static final long serialVersionUID = 1L;

    private final Window window = new Window();
    private TextField ingresos = new TextField("Ingresos");
    private TextField egresos = new TextField("Egresos");
    private TextField ganancias = new TextField("Ganancias");

    public ResumenWindow() {

	final VerticalLayout popupVLayout = new VerticalLayout();
	popupVLayout.setSpacing(true);
	popupVLayout.setMargin(true);

	window.setHeightUndefined();
	window.setWidth("600px");
	window.setModal(true);
	window.center();
	window.setResizable(false);
	window.setContent(popupVLayout);
	window.setCaption("Resumen Movimientos");
	window.setIcon(VaadinIcons.CHART_LINE);
	window.setDraggable(false);

	UI.getCurrent().addWindow(window);
	window.center();

	FormLayout HL = new FormLayout(ingresos, egresos, ganancias);
	HL.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
	popupVLayout.setMargin(new MarginInfo(false, true, true, true));
	popupVLayout.setSpacing(false);
	HL.setCaption("Totales");
	popupVLayout.addComponent(HL);

	MovimientoService ms = new MovimientoService();
	Map<TipoMovimiento, BigDecimal> totalesPesos = ms.readAll()
		.stream()
		.filter(m -> m.getTipoMoneda().equals(TipoMoneda.Pesos))
		.collect(Collectors.groupingBy(
			Movimiento::getTipoMovimiento,
			Collectors.reducing(BigDecimal.ZERO,
				Movimiento::getMonto,
				BigDecimal::add)));

	Map<TipoMovimiento, BigDecimal> totalesDolares = ms.readAll()
		.stream()
		.filter(m -> m.getTipoMoneda().equals(TipoMoneda.Dolares))
		.collect(Collectors.groupingBy(
			Movimiento::getTipoMovimiento,
			Collectors.reducing(BigDecimal.ZERO,
				Movimiento::getMonto,
				BigDecimal::add)));

	BigDecimal inPesos = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
	BigDecimal outPesos = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
	BigDecimal gananciaPesos = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
	BigDecimal inDolares = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
	BigDecimal outDolares = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
	BigDecimal gananciaDolares = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);

	if (totalesPesos.containsKey(TipoMovimiento.Egreso))
	    outPesos = totalesPesos.get(TipoMovimiento.Egreso).setScale(2, RoundingMode.HALF_UP);

	if (totalesPesos.containsKey(TipoMovimiento.Ingreso))
	    inPesos = totalesPesos.get(TipoMovimiento.Ingreso).setScale(2, RoundingMode.HALF_UP);

	if (totalesDolares.containsKey(TipoMovimiento.Egreso))
	    outDolares = totalesDolares.get(TipoMovimiento.Egreso).setScale(2, RoundingMode.HALF_UP);

	if (totalesDolares.containsKey(TipoMovimiento.Ingreso))
	    inDolares = totalesDolares.get(TipoMovimiento.Ingreso).setScale(2, RoundingMode.HALF_UP);

	gananciaPesos = inPesos.subtract(outPesos).setScale(2, RoundingMode.HALF_UP);
	gananciaDolares = inDolares.subtract(outDolares).setScale(2, RoundingMode.HALF_UP);

	ingresos.setValue(TipoMoneda.getSimbolo(TipoMoneda.Pesos) + inPesos.toPlainString() + " | "
		+ TipoMoneda.getSimbolo(TipoMoneda.Dolares) + inDolares.toPlainString());
	egresos.setValue(TipoMoneda.getSimbolo(TipoMoneda.Pesos) + outPesos.toPlainString() + " | "
		+ TipoMoneda.getSimbolo(TipoMoneda.Dolares) + outDolares.toPlainString());
	ganancias.setValue(TipoMoneda.getSimbolo(TipoMoneda.Pesos) + gananciaPesos.toPlainString() + " | "
		+ TipoMoneda.getSimbolo(TipoMoneda.Dolares) + gananciaDolares.toPlainString());

    }

    public void setWindowWidth(String width) {
	window.setWidth(width);
    }

    public void close() {
	window.close();
    }
}
