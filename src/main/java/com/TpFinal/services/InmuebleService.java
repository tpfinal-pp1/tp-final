package com.TpFinal.services;

import com.TpFinal.data.dao.DAOInmuebleImpl;
import com.TpFinal.data.dao.interfaces.DAOInmueble;
import com.TpFinal.data.dto.inmueble.ClaseInmueble;
import com.TpFinal.data.dto.inmueble.Coordenada;
import com.TpFinal.data.dto.inmueble.CriterioBusquedaInmuebleDTO;
import com.TpFinal.data.dto.inmueble.Direccion;
import com.TpFinal.data.dto.inmueble.EstadoInmueble;
import com.TpFinal.data.dto.inmueble.Inmueble;
import com.TpFinal.data.dto.inmueble.TipoInmueble;
import com.TpFinal.data.dto.persona.Propietario;

import java.util.List;

public class InmuebleService {
	private DAOInmueble dao;
	
	public InmuebleService() {
		dao = new DAOInmuebleImpl();
	}
	
	public List<Inmueble> readAll() {
		return dao.readAllActives();
	}
	/**
	 * Usar solo para guardar inmuebles
	 * 
	 * @param entidad
	 * @return
	 */
	public boolean saveOrUpdate(Inmueble entidad) {
		//if(entidad.getPropietario()!=null)
			//entidad.getPropietario().addInmueble(entidad);
	    return dao.saveOrUpdate(entidad);
	}
	
	/**
	 * Usar para update de inmuebles ya que incluye bidireccional
	 * @param inmueble
	 * @return
	 */
	@Deprecated
	public boolean updateBidireccional(Inmueble inmueble) {
		boolean ret=true;
		Inmueble inmBd=dao.findById(inmueble.getId());
		if(inmueble.getPropietario()==null || !inmueble.getPropietario().equals(inmBd.getPropietario())) {
			inmBd.getPropietario().getInmuebles().remove(inmBd);
		}
		dao.saveOrUpdate(inmBd);
		dao.saveOrUpdate(inmueble);
		return ret;
	}

	public boolean delete(Inmueble i) {
		return dao.logicalDelete(i);
	}
	
	public Inmueble findById(Long id) {
		return dao.findById(id);
	}
	
	public List<Inmueble> findByCaracteristicas(CriterioBusquedaInmuebleDTO criterio){
		return dao.findInmueblesbyCaracteristicas(criterio);
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
	

}
