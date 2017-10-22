package com.TpFinal.services;

import com.TpFinal.data.dao.DAOPublicacionImpl;
import com.TpFinal.data.dao.interfaces.DAOPublicacion;
import com.TpFinal.dto.inmueble.*;
import com.TpFinal.dto.persona.Persona;
import com.TpFinal.dto.publicacion.*;
import com.TpFinal.exceptions.services.PublicacionServiceException;
import com.TpFinal.view.publicacion.FiltroPublicacion;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PublicacionService {
    private DAOPublicacion daoPublicacion;
    private InmuebleService inmuebleService;
    public final static PublicacionVenta INSTANCIA_VENTA = InstanciaPublicacionVenta();
    public final static PublicacionAlquiler INSTANCIA_ALQUILER = InstanciaPublicacionAlquiler();

    public PublicacionService() {
	daoPublicacion = new DAOPublicacionImpl();
	inmuebleService = new InmuebleService();
    }

    public List<Publicacion> readAll() {
	return daoPublicacion.readAllActives();
    }

    public boolean delete(Publicacion publicacion) {
	boolean ret = daoPublicacion.logicalDelete(publicacion);
	inmuebleService.actualizarEstadoInmuebleSegunPublicacion(publicacion.getInmueble());
	return ret;
    }

    public boolean save(Publicacion publicacion) throws PublicacionServiceException {
	boolean ret = true;
	if (publicacion.getInmueble() != null) {
	ret = daoPublicacion.saveOrUpdate(publicacion);
	inmuebleService.actualizarEstadoInmuebleSegunPublicacion(publicacion.getInmueble());
	}else {
	    throw new PublicacionServiceException("La publicación debe tener un inmueble asociado!");
	}
	return ret;
    }

    public Publicacion findById(Long id) {
	return daoPublicacion.findById(id);
    }

    public List<Publicacion> readAll(String stringFilter) {
	ArrayList<Publicacion> arrayList = new ArrayList();
	List<Publicacion> publicaciones = daoPublicacion.readAllActives();
	if (stringFilter != "") {

	    for (Publicacion publicacion : publicaciones) {

		boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
			|| publicacion.toString().toLowerCase()
				.contains(stringFilter.toLowerCase());
		if (passesFilter) {

		    arrayList.add(publicacion);
		}

	    }
	} else {
	    arrayList.addAll(publicaciones);
	}

	Collections.sort(arrayList, new Comparator<Publicacion>() {

	    @Override
	    public int compare(Publicacion o1, Publicacion o2) {
		return (int) (o2.getId() - o1.getId());
	    }
	});
	for (Publicacion p : arrayList) {
	    if (p.getInmueble() != null) {
		// p.setPropietarioPublicacion(p.getInmueble().getPropietario());
	    }
	    if (p.getEstadoPublicacion() == null)
		p.setEstadoPublicacion(EstadoPublicacion.Activa);
	}
	return arrayList;

    }
    
    public List<Publicacion> findAll(FiltroPublicacion filtro) {
    	List<Publicacion> publicaciones = daoPublicacion.readAllActives()
				.stream()
				.filter(filtro.getFiltroCompuesto())
				.collect(Collectors.toList());
		publicaciones.sort(Comparator.comparing(Publicacion::getId));
		return publicaciones;
    }

    static PublicacionAlquiler InstanciaPublicacionAlquiler() {
	Persona p = new Persona();
	p.addRol(Rol.Propietario);

	return new PublicacionAlquiler.Builder()
		.setValorCuota(new BigDecimal(0))
		.setFechaPublicacion(LocalDate.now())
		.setMoneda(TipoMoneda.Pesos)
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
			.setEstadoInmueble(EstadoInmueble.NoPublicado)
			.setPropietario(p.getPropietario())
			.setSuperficieCubierta(0)
			.setSuperficieTotal(0)
			.setTipoInmueble(TipoInmueble.Vivienda)
			.build())
		.build();
    }

    static PublicacionVenta InstanciaPublicacionVenta() {
	Persona p = new Persona();
	p.addRol(Rol.Propietario);
	PublicacionVenta PV = new PublicacionVenta.Builder()
		.setPrecio(new BigDecimal(0))
		.setFechaPublicacion(LocalDate.now())
		.setMoneda(TipoMoneda.Pesos)
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
			.setEstadoInmueble(EstadoInmueble.NoPublicado)
			.setPropietario(p.getPropietario())
			.setSuperficieCubierta(0)
			.setSuperficieTotal(0)
			.setTipoInmueble(TipoInmueble.Vivienda)
			.build())
		.build();
	return PV;

    }

}
