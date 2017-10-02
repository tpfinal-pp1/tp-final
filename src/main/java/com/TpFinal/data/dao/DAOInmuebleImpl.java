package com.TpFinal.data.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

import com.TpFinal.data.dao.DAOImpl;
import com.TpFinal.data.dao.interfaces.DAOInmueble;
import com.TpFinal.data.dto.inmueble.ClaseInmueble;
import com.TpFinal.data.dto.inmueble.CriterioBusquedaInmuebleDTO;
import com.TpFinal.data.dto.inmueble.Direccion;
import com.TpFinal.data.dto.inmueble.EstadoInmueble;
import com.TpFinal.data.dto.inmueble.Inmueble;
import com.TpFinal.data.dto.operacion.Operacion;
import com.TpFinal.data.dto.operacion.OperacionAlquiler;
import com.TpFinal.data.dto.operacion.OperacionVenta;
import com.TpFinal.data.dto.operacion.TipoOperacion;

public class DAOInmuebleImpl extends DAOImpl<Inmueble> implements DAOInmueble {

	public DAOInmuebleImpl() {
		super(Inmueble.class);
	}

	@Override
	public List<Inmueble> findInmueblesbyEstado(EstadoInmueble estado) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Inmueble.class)
				.add(Restrictions.eq(Inmueble.pEstadoInmueble, estado));

		return findByCriteria(criteria);
	}

	@Override
	public List<Inmueble> findInmueblesbyCaracteristicas(CriterioBusquedaInmuebleDTO criterio) {

		DetachedCriteria query = null;
		List<Inmueble> resultadoQuery = new ArrayList<>();

		if (criterio.getTipoOperacion() != null || criterio.getTipoMoneda()!= null ) {
			TipoOperacion to = criterio.getTipoOperacion();
			query = DetachedCriteria.forClass(Operacion.class);		
			
			if (to != null){
				query.add(Restrictions.eq(Operacion.pTipoOperacion, to));
				if (to.equals(TipoOperacion.Alquiler)) {
					addRestriccionesDeAlquiler(query, criterio);

				} else {
					addRestriccionesDeVenta(query, criterio);
				}
			}
			
			if (criterio.getTipoMoneda()!= null) {
				query.add(Restrictions.eq("moneda", criterio.getTipoMoneda()));
			}
			
						
			DAOImpl<Operacion> dao = new DAOImpl<>(Operacion.class);
			query.createAlias("inmueble", "i");
			addRestriccionesDeInmueble(query, criterio,"i.");
			List<Operacion> operaciones = dao.findByCriteria(query);
			System.out.println("Ejecutando Query: "+query);
			for (Operacion o : operaciones) {
				resultadoQuery.add(o.getInmueble());
			}

		} else {
			query = DetachedCriteria.forClass(Inmueble.class);
			addRestriccionesDeInmueble(query, criterio,"");
			resultadoQuery = findByCriteria(query);
		}
		return resultadoQuery;
	}

	private void addRestriccionesDeInmueble(DetachedCriteria query, CriterioBusquedaInmuebleDTO criterio,
			String alias) {

		if (criterio.getaEstrenar() != null) {
			query.add(Restrictions.eq(alias + Inmueble.pAEstrenar, criterio.getaEstrenar()));
		}

		if (criterio.getConAireAcondicionado() != null) {
			query.add(Restrictions.eq(alias + Inmueble.pConAireAcond, criterio.getConAireAcondicionado()));
		}

		if (criterio.getConJardin() != null) {
			query.add(Restrictions.eq(alias + Inmueble.pConJardin, criterio.getConJardin()));
		}

		if (criterio.getConParrilla() != null) {
			query.add(Restrictions.eq(alias + Inmueble.pConParrilla, criterio.getConParrilla()));
		}

		if (criterio.getConPileta() != null) {
			query.add(Restrictions.eq(alias + Inmueble.pConPileta, criterio.getConPileta()));
		}

		if (criterio.getCiudad() != null) {
			query.createCriteria(alias + Inmueble.pDireccion)
					.add(Restrictions.eq(Direccion.pLocalidad, criterio.getCiudad()));
		}

		if (criterio.getClasesDeInmueble() != null) {
			Disjunction disjuncion = Restrictions.disjunction();
			criterio.getClasesDeInmueble()
					.forEach(clase -> disjuncion.add(Restrictions.eq(alias + Inmueble.pClaseInmb, clase)));
			query.add(disjuncion);
		}

		if (criterio.getEstadoInmueble() != null) {
			query.add(Restrictions.eq(alias + Inmueble.pEstadoInmueble, criterio.getEstadoInmueble()));
		}

		if (criterio.getMaxCantAmbientes() != null) {
			query.add(Restrictions.le(alias + Inmueble.pCantAmbientes, criterio.getMaxCantAmbientes()));
		}

		if (criterio.getMaxCantCocheras() != null) {
			query.add(Restrictions.le(alias + Inmueble.pCantCocheras, criterio.getMaxCantCocheras()));
		}

		if (criterio.getMaxCantDormitorios() != null) {
			query.add(Restrictions.le(alias + Inmueble.pCantDormitorios, criterio.getMaxCantDormitorios()));
		}

		if (criterio.getMaxSupCubierta() != null) {
			query.add(Restrictions.le(alias + Inmueble.pSupCubierta, criterio.getMaxSupCubierta()));
		}

		if (criterio.getMaxSupTotal() != null) {
			query.add(Restrictions.le(alias + Inmueble.pSupTotal, criterio.getMaxSupTotal()));
		}

		if (criterio.getMinCantAmbientes() != null) {
			query.add(Restrictions.ge(alias + Inmueble.pCantAmbientes, criterio.getMinCantAmbientes()));
		}

		if (criterio.getMinCantCocheras() != null) {
			query.add(Restrictions.ge(alias + Inmueble.pCantCocheras, criterio.getMinCantCocheras()));
		}

		if (criterio.getMinCantDormitorios() != null) {
			query.add(Restrictions.ge(alias + Inmueble.pCantDormitorios, criterio.getMinCantDormitorios()));
		}

		if (criterio.getMinSupCubierta() != null) {
			query.add(Restrictions.ge(alias + Inmueble.pSupCubierta, criterio.getMinSupCubierta()));
		}

		if (criterio.getMinSupTotal() != null) {
			query.add(Restrictions.ge(alias + Inmueble.pSupTotal, criterio.getMinSupTotal()));
		}

		if (criterio.getTipoInmueble() != null) {
			query.add(Restrictions.eq(Inmueble.pTipoInmb, criterio.getTipoInmueble()));
		}
	}

	private void addRestriccionesDeAlquiler(DetachedCriteria query, CriterioBusquedaInmuebleDTO criterio) {
		if (criterio.getMinPrecio() != null) {
			query.add(Restrictions.ge(OperacionAlquiler.pPrecioAlquiler, criterio.getMinPrecio()));
		}

		if (criterio.getMaxPrecio() != null) {
			query.add(Restrictions.le(OperacionAlquiler.pPrecioAlquiler, criterio.getMaxPrecio()));
		}
	}

	private void addRestriccionesDeVenta(DetachedCriteria query, CriterioBusquedaInmuebleDTO criterio) {
		if (criterio.getMinPrecio() != null) {
			query.add(Restrictions.ge(OperacionVenta.pPrecioVenta, criterio.getMinPrecio()));
		}

		if (criterio.getMaxPrecio() != null) {
			query.add(Restrictions.le(OperacionVenta.pPrecioVenta, criterio.getMaxPrecio()));
		}
	}

}
