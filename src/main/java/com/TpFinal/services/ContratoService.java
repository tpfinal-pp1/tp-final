package com.TpFinal.services;

import com.TpFinal.data.dao.DAOContratoAlquilerImpl;
import com.TpFinal.data.dao.DAOContratoImpl;
import com.TpFinal.data.dao.DAOContratoVentaImpl;
import com.TpFinal.data.dao.interfaces.DAOContrato;
import com.TpFinal.data.dao.interfaces.DAOContratoAlquiler;
import com.TpFinal.data.dao.interfaces.DAOContratoVenta;
import com.TpFinal.data.dto.EstadoRegistro;
import com.TpFinal.data.dto.contrato.Contrato;
import com.TpFinal.data.dto.contrato.ContratoAlquiler;
import com.TpFinal.data.dto.contrato.ContratoVenta;
import com.TpFinal.data.dto.contrato.DuracionContrato;
import com.TpFinal.data.dto.contrato.TipoInteres;
import com.TpFinal.data.dto.inmueble.ClaseInmueble;
import com.TpFinal.data.dto.inmueble.Coordenada;
import com.TpFinal.data.dto.inmueble.Direccion;
import com.TpFinal.data.dto.inmueble.Inmueble;
import com.TpFinal.data.dto.persona.Persona;
import com.TpFinal.data.dto.persona.Propietario;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.openqa.selenium.internal.FindsById;

public class ContratoService {
    public static enum instancia {
	venta, alquiler
    };

    private DAOContratoAlquiler daoAlquiler;
    private DAOContratoVenta daoVenta;
    private DAOContrato daoContrato;
    private InmuebleService inmuebleService;

    public ContratoService() {
	daoAlquiler = new DAOContratoAlquilerImpl();
	daoVenta = new DAOContratoVentaImpl();
	daoContrato = new DAOContratoImpl();
	inmuebleService = new InmuebleService();
    }

    public boolean saveOrUpdate(Contrato contrato, File doc) {
	boolean ret = false;
	if (contrato.getId() != null) {
	    Contrato contratoAntiguo = daoContrato.findById(contrato.getId());
	    if (contrato.getInmueble()!= null && !contratoAntiguo.getInmueble().equals(contrato.getInmueble())) {
		inmuebleService.desvincularContrato(contratoAntiguo);
	    }
	}
	if (contrato instanceof ContratoVenta) {
	    ContratoVenta c = (ContratoVenta) contrato;
	    if (doc != null) {
		ret = daoVenta.mergeContrato(c, doc);
	    } else {
		ret = daoVenta.merge(c);
	    }
	} else {
	    ContratoAlquiler c = (ContratoAlquiler) contrato;
	    if (doc != null)
		ret = daoAlquiler.mergeContrato(c, doc);
	    else
		ret = daoAlquiler.merge(c);
	}

	return ret;
    }

    public boolean delete(Contrato contrato) {
	boolean ret = false;
	if (contrato instanceof ContratoVenta) {
	    ContratoVenta c = (ContratoVenta) contrato;
	    ret = daoVenta.logicalDelete(c);
	} else {
	    ContratoAlquiler c = (ContratoAlquiler) contrato;
	    ret = daoAlquiler.logicalDelete(c);
	}
	return ret;
    }

    public List<Contrato> readAll() {
	return daoContrato.readAllActives();
    }

    public synchronized List<Contrato> findAll(String stringFilter) {
	ArrayList<Contrato> arrayList = new ArrayList<>();
	List<Contrato> contratos = daoContrato.readAllActives();

	if (stringFilter != "") {

	    for (Contrato contrato : contratos) {

		boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
			|| contrato.toString().toLowerCase()
				.contains(stringFilter.toLowerCase());
		if (passesFilter) {

		    arrayList.add(contrato);
		}

	    }
	} else {
	    arrayList.addAll(contratos);
	}

	Collections.sort(arrayList, new Comparator<Contrato>() {

	    @Override
	    public int compare(Contrato o1, Contrato o2) {
		return (int) (o2.getId() - o1.getId());
	    }
	});
	return arrayList;
    }

    public static LocalDate getFechaVencimiento(ContratoAlquiler c) {
	LocalDate ret;
	ret = c.getFechaCelebracion().plus(c.getDuracionContrato().getDuracion(), ChronoUnit.MONTHS);
	return ret;
    }

    public static ContratoAlquiler getInstanciaAlquiler() {
	return new ContratoAlquiler.Builder()
		.setDiaDePago(1)
		.setDuracionContrato(DuracionContrato.VeinticuatroMeses)
		.setInquilinoContrato(PersonaService.getPersonaConInquilino())
		.setInteresPunitorio(0.0)
		.setIntervaloActualizacion(24)
		.setTipoIncrementoCuota(TipoInteres.Acumulativo)
		.setTipoInteresPunitorio(TipoInteres.Simple)
		.setValorIncial(BigDecimal.ZERO)
		.setDocumento(null)
		.setEstadoRegistro(EstadoRegistro.ACTIVO)
		.setFechaCelebracion(LocalDate.now())
		.setInmueble(InmuebleService.getInstancia())
		.build();
    }

    public static ContratoVenta getInstanciaVenta() {
	return new ContratoVenta.Builder()
		.setPrecioVenta(new BigDecimal("0"))
		.setFechaCelebracion(LocalDate.now())
		.setDocumento(null)
		.setInmueble(new Inmueble.Builder()
			.setaEstrenar(false)
			.setCantidadAmbientes(0)
			.setCantidadCocheras(0)
			.setCantidadDormitorios(0)
			.setClaseInmueble(ClaseInmueble.OtroInmueble)
			.setConAireAcondicionado(false)
			.setConJardin(false)
			.setConParilla(false)
			.setConPileta(false)
			.setDireccion(new Direccion.Builder()
				.setCalle("")
				.setCodPostal("")
				.setCoordenada(new Coordenada())
				.setLocalidad("")
				.setNro(0)
				.setPais("Argentina")
				.setProvincia("")
				.build())
			.setPropietario(new Propietario.Builder()
				.setPersona(new Persona())
				.build())
			.build())
		.setEstadoRegistro(EstadoRegistro.ACTIVO)
		.build();
    }

}
