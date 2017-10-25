package com.TpFinal.services;

import com.TpFinal.data.dao.DAOInmuebleImpl;
import com.TpFinal.data.dao.interfaces.DAOInmueble;
import com.TpFinal.dto.EstadoRegistro;
import com.TpFinal.dto.contrato.Contrato;
import com.TpFinal.dto.contrato.EstadoContrato;
import com.TpFinal.dto.inmueble.ClaseInmueble;
import com.TpFinal.dto.inmueble.Coordenada;
import com.TpFinal.dto.inmueble.CriterioBusqInmueble;
import com.TpFinal.dto.inmueble.Direccion;
import com.TpFinal.dto.inmueble.EstadoInmueble;
import com.TpFinal.dto.inmueble.Inmueble;
import com.TpFinal.dto.inmueble.TipoInmueble;
import com.TpFinal.dto.persona.Propietario;
import com.TpFinal.dto.publicacion.EstadoPublicacion;
import com.TpFinal.dto.publicacion.Publicacion;
import com.TpFinal.dto.publicacion.TipoPublicacion;
import com.TpFinal.view.inmuebles.FiltroInmueble;

import com.google.gwt.user.server.rpc.UnexpectedException;
import com.vaadin.server.FileResource;
import com.vaadin.server.Resource;
import com.vaadin.server.StreamResource;
import com.vaadin.server.ThemeResource;

import java.io.*;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class InmuebleService {
    private DAOInmueble dao;
    private Supplier<List<Inmueble>> supplier;

    public InmuebleService() {
	dao = new DAOInmuebleImpl();
	supplier = () -> this.readAll();
    }
    
    public InmuebleService(Supplier<List<Inmueble>> supplier) {
	dao = new DAOInmuebleImpl();
	this.supplier = supplier;
    }
    
    public void setSupplier(Supplier<List<Inmueble>> supplier) {
	this.supplier = supplier;
    }

    public List<Inmueble> readAll() {
	List<Inmueble> ret = dao.readAllActives();
	ret.sort(Comparator.comparing(Inmueble::getId));
	return ret;
    }

    public boolean merge(Inmueble entidad) {
		System.out.println(entidad.nombreArchivoPortada);
	return dao.merge(entidad);
    }

    public boolean delete(Inmueble i) {
	return dao.logicalDelete(i);
    }

    public Inmueble findById(Long id) {
	return dao.findById(id);
    }

    public List<Inmueble> findByCaracteristicas(CriterioBusqInmueble criterio) {
	List<Inmueble> ret = dao.findInmueblesbyCaracteristicas(criterio);
	ret.sort(Comparator.comparing(Inmueble::getId).reversed());
	return ret;
    }

    public static Inmueble getInstancia() {
	return new Inmueble.Builder()
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
		.setPropietario(new Propietario())
		.setSuperficieCubierta(0)
		.setSuperficieTotal(0)
		.setTipoInmueble(TipoInmueble.Vivienda)
		.build();
    }

    public Resource getPortada(Inmueble inmueble){
    	if(inmueble!=null&&inmueble.getId()!=null) {
			if (new File("Files" + File.separator + inmueble.getNombreArchivoPortada()).exists()) {
				StreamResource str = new StreamResource(new StreamResource.StreamSource() {
					@Override
					public InputStream getStream() {
						try {

							return new FileInputStream("Files" + File.separator + inmueble.getNombreArchivoPortada());
						} catch (FileNotFoundException e) {

						}
						return null;
					}
				}, "Files" + File.separator + inmueble.getNombreArchivoPortada());

				return str;
			}


		}
		return null;

	}





    public List<Inmueble> filtrarPorCalle(String filtro) {
	List<Inmueble> inmuebles = dao.readAllActives().stream()
		.filter(i -> {
		    return filtro == null || filtro.isEmpty()
			    || i.getDireccion().getCalle().toLowerCase().contains(filtro.toLowerCase());
		})
		.collect(Collectors.toList());
	Collections.sort(inmuebles, new Comparator<Inmueble>() {

	    @Override
	    public int compare(Inmueble o1, Inmueble o2) {
		return o1.getDireccion().getCalle().compareTo(o2.getDireccion().getCalle());
	    }

	});
	return inmuebles;
    }

    /**
     * Revisa la lista de publicaciones. Si existe al menos una publicación activa,
     * cambia el estadoPublicación a activo sino, cambia el estadoPublicacion a
     * noPublicado
     * 
     * @param inmueble
     */
    public boolean actualizarEstadoInmuebleSegunPublicacion(Inmueble inmueble) {
	System.out.println("Actualizando Estado Inmueble");
	boolean ret = true;
	List<Publicacion> publicaciones = getListadoDePublicaciones(inmueble);
	List<Publicacion> pubsActivas = publicaciones.stream().filter(this::estaActivoYNoFueBorrado)
		.limit(2).collect(Collectors.toList());

	if (!pubsActivas.isEmpty()) {
	    setEstadoInmuebleSegunPublicaciones(inmueble, pubsActivas);
	    ret = ret && dao.merge(inmueble);
	}
	return ret;

    }

    private boolean estaActivoYNoFueBorrado(Publicacion p) {
	return p.getEstadoRegistro() == EstadoRegistro.ACTIVO && p
		.getEstadoPublicacion() == EstadoPublicacion.Activa;
    }

    private void setEstadoInmuebleSegunPublicaciones(Inmueble inmueble, List<Publicacion> publicaciones) {
	if (!(inmueble.getEstadoInmueble() == EstadoInmueble.Alquilado
		|| (inmueble.getEstadoInmueble() == EstadoInmueble.Vendido))) {
	    if (publicaciones.size() == 2) {
		inmueble.setEstadoInmueble(EstadoInmueble.EnAlquilerYVenta);
	    } else {
		Publicacion publicacion = publicaciones.get(0);
		if (publicacion.getTipoPublicacion() == TipoPublicacion.Alquiler) {
		    inmueble.setEstadoInmueble(EstadoInmueble.EnAlquiler);
		} else {
		    inmueble.setEstadoInmueble(EstadoInmueble.EnVenta);
		}
	    }
	}

    }

    public List<Publicacion> getListadoDePublicaciones(Inmueble inmueble) {
	List<Publicacion> ret = null;
	if (inmueble != null && inmueble.getPublicaciones() != null) {
	    ret = inmueble.getPublicaciones().stream().collect(Collectors.toList());
	}
	return ret;
    }

    public void desvincularContrato(Contrato contratoAntiguo) {
	Inmueble i = contratoAntiguo.getInmueble();
	i.removeContrato(contratoAntiguo);
	dao.saveOrUpdate(i);
    }

    public boolean inmueblePoseeContratoVigente(Inmueble inmueble) {
	boolean ret = false;
	if (inmueble != null) {
	    Set<Contrato> contratos = dao.findById(inmueble.getId()).getContratos();
	    if (contratos != null)
		ret = contratos.stream().anyMatch(contrato -> contrato.getEstadoContrato() == EstadoContrato.Vigente);
	}
	return ret;
    }

    public static List<Publicacion> getPublicacionesActivas(Inmueble i) {
	
	List<Publicacion> publicaciones = i.getPublicaciones().stream().collect(Collectors.toList());
	List<Publicacion> publicacionesActivas =publicaciones.stream()
		.filter(p -> p.getEstadoRegistro().equals(EstadoRegistro.ACTIVO))
		.filter(p -> p.getEstadoPublicacion().equals(EstadoPublicacion.Activa))
		.collect(Collectors.toList());
	
	return publicacionesActivas;
    }
    
    public List<Inmueble> findAll(FiltroInmueble filtro) {
    	List<Inmueble> inmuebles = supplier.get()
				.stream()
				.filter(filtro.getFiltroCompuesto())
				.collect(Collectors.toList());
		inmuebles.sort(Comparator.comparing(Inmueble::getId));
		return inmuebles;
    }


}
