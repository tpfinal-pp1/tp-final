package com.TpFinal.data.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

import com.TpFinal.data.dao.DAOImpl;
import com.TpFinal.data.dao.interfaces.DAOInmueble;
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

		if (criterio.getTipoOperacion() != null) {
			TipoOperacion to = criterio.getTipoOperacion();

			query = DetachedCriteria.forClass(Operacion.class).add(Restrictions.eq(Operacion.pTipoOperacion, to));
			if (to.equals(TipoOperacion.Alquiler)) {
				addRestriccionesDeAlquiler(query, criterio);
				DAOImpl<OperacionAlquiler> dao = new DAOImpl<>(OperacionAlquiler.class);
				List<OperacionAlquiler> operaciones = dao.findByCriteria(query);
				for (OperacionAlquiler o : operaciones) {
					resultadoQuery.add(o.getInmueble());
				}
			} else {
				addRestriccionesDeVenta(query, criterio);
				DAOImpl<OperacionVenta> dao = new DAOImpl<>(OperacionVenta.class);
				List<OperacionVenta> operaciones = dao.findByCriteria(query);
				for (OperacionVenta o : operaciones) {
					resultadoQuery.add(o.getInmueble());
				}
			}
			query.createCriteria("inmueble");
			addRestriccionesDeInmueble(query, criterio);
		} else {
			query = DetachedCriteria.forClass(Inmueble.class);
			addRestriccionesDeInmueble(query, criterio);
			resultadoQuery = findByCriteria(query);
		}
		return resultadoQuery;
	}

	private void addRestriccionesDeVenta(DetachedCriteria query, CriterioBusquedaInmuebleDTO criterio) {
		// TODO Auto-generated method stub

	}

	private void addRestriccionesDeInmueble(DetachedCriteria query, CriterioBusquedaInmuebleDTO criterio) {
		if (criterio.getaEstrenar() != null) {
			query.add(Restrictions.eq(Inmueble.pAEstrenar, criterio.getaEstrenar()));
		}

		if (criterio.getConAireAcondicionado() != null) {
			query.add(Restrictions.eq(Inmueble.pConAireAcond, criterio.getConAireAcondicionado()));
		}

		if (criterio.getConJardin() != null) {
			query.add(Restrictions.eq(Inmueble.pConJardin, criterio.getConJardin()));
		}

		if (criterio.getConParrilla() != null) {
			query.add(Restrictions.eq(Inmueble.pConParrilla, criterio.getConParrilla()));
		}

		if (criterio.getConPileta() != null) {
			query.add(Restrictions.eq(Inmueble.pConPileta, criterio.getConPileta()));
		}

		if (criterio.getCiudad() != null) {
			query.createCriteria(Inmueble.pDireccion).add(Restrictions.eq(Direccion.pLocalidad, criterio.getCiudad()));
		}

		if (criterio.getClasesDeInmueble() != null) {
			Disjunction disjuncion = Restrictions.disjunction();
			criterio.getClasesDeInmueble()
					.forEach(clase -> disjuncion.add(Restrictions.eq(Inmueble.pClaseInmb, clase)));
			query.add(disjuncion);
		}

		if (criterio.getEstadoInmueble() != null) {
			query.add(Restrictions.eq(Inmueble.pEstadoInmueble, criterio.getEstadoInmueble()));
		}

		if (criterio.getMaxCantAmbientes() != null) {
			query.add(Restrictions.le(Inmueble.pCantAmbientes, criterio.getMaxCantAmbientes()));
		}

		if (criterio.getMaxCantCocheras() != null) {
			query.add(Restrictions.le(Inmueble.pCantCocheras, criterio.getMaxCantCocheras()));
		}

		if (criterio.getMaxCantDormitorios() != null) {
			query.add(Restrictions.le(Inmueble.pCantDormitorios, criterio.getMaxCantDormitorios()));
		}

		if (criterio.getMaxSupCubierta() != null) {
			query.add(Restrictions.le(Inmueble.pSupCubierta, criterio.getMaxSupCubierta()));
		}

		if (criterio.getMaxSupTotal() != null) {
			query.add(Restrictions.le(Inmueble.pSupTotal, criterio.getMaxSupTotal()));
		}

		if (criterio.getMinCantAmbientes() != null) {
			query.add(Restrictions.ge(Inmueble.pCantAmbientes, criterio.getMinCantAmbientes()));
		}

		if (criterio.getMinCantCocheras() != null) {
			query.add(Restrictions.ge(Inmueble.pCantCocheras, criterio.getMinCantCocheras()));
		}

		if (criterio.getMinCantDormitorios() != null) {
			query.add(Restrictions.ge(Inmueble.pCantDormitorios, criterio.getMinCantDormitorios()));
		}

		if (criterio.getMinSupCubierta() != null) {
			query.add(Restrictions.ge(Inmueble.pSupCubierta, criterio.getMinSupCubierta()));
		}

		if (criterio.getMinSupTotal() != null) {
			query.add(Restrictions.ge(Inmueble.pSupTotal, criterio.getMinSupTotal()));
		}

		if (criterio.getTipoInmueble() != null) {
			query.add(Restrictions.eq(Inmueble.pTipoInmb, criterio.getTipoInmueble()));
		}
	}

	private void addRestriccionesDeAlquiler(DetachedCriteria criteria, CriterioBusquedaInmuebleDTO criterio) {
		// if (criterio.getMinPrecio() != null) {
		// criteria.add(Restrictions.ge(OperacionAlquiler.pPrecioAlquiler,
		// criterio.getMinPrecio()));
		// }

	}

}
