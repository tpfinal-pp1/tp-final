package com.TpFinal.data.dao;

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
		DetachedCriteria criteria = DetachedCriteria.forClass(Inmueble.class);

		if (criterio.getaEstrenar() != null) {
			criteria.add(Restrictions.eq(Inmueble.pAEstrenar, criterio.getaEstrenar()));
		}

		if (criterio.getConAireAcondicionado() != null) {
			criteria.add(Restrictions.eq(Inmueble.pConAireAcond, criterio.getConAireAcondicionado()));
		}

		if (criterio.getConJardin() != null) {
			criteria.add(Restrictions.eq(Inmueble.pConJardin, criterio.getConJardin()));
		}

		if (criterio.getConParrilla() != null) {
			criteria.add(Restrictions.eq(Inmueble.pConParrilla, criterio.getConParrilla()));
		}

		if (criterio.getConPileta() != null) {
			criteria.add(Restrictions.eq(Inmueble.pConPileta, criterio.getConPileta()));
		}

		if (criterio.getCiudad() != null) {
			criteria.createCriteria(Inmueble.pDireccion)
					.add(Restrictions.eq(Direccion.pLocalidad, criterio.getCiudad()));
		}

		if (criterio.getClasesDeInmueble() != null) {
			Disjunction disjuncion = Restrictions.disjunction();
			criterio.getClasesDeInmueble()
					.forEach(clase -> disjuncion.add(Restrictions.eq(Inmueble.pClaseInmb, clase)));
			criteria.add(disjuncion);
		}

		if (criterio.getEstadoInmueble() != null) {
			criteria.add(Restrictions.eq(Inmueble.pEstadoInmueble, criterio.getEstadoInmueble()));
		}

		if (criterio.getMaxCantAmbientes() != null) {
			criteria.add(Restrictions.le(Inmueble.pCantAmbientes, criterio.getMaxCantAmbientes()));
		}

		if (criterio.getMaxCantCocheras() != null) {
			criteria.add(Restrictions.le(Inmueble.pCantCocheras, criterio.getMaxCantCocheras()));
		}

		if (criterio.getMaxCantDormitorios() != null) {
			criteria.add(Restrictions.le(Inmueble.pCantDormitorios, criterio.getMaxCantDormitorios()));
		}

		if (criterio.getMaxSupCubierta() != null) {
			criteria.add(Restrictions.le(Inmueble.pSupCubierta, criterio.getMaxSupCubierta()));
		}

		if (criterio.getMaxSupTotal() != null) {
			criteria.add(Restrictions.le(Inmueble.pSupTotal, criterio.getMaxSupTotal()));
		}

		if (criterio.getMinCantAmbientes() != null) {
			criteria.add(Restrictions.ge(Inmueble.pCantAmbientes, criterio.getMinCantAmbientes()));
		}

		if (criterio.getMinCantCocheras() != null) {
			criteria.add(Restrictions.ge(Inmueble.pCantCocheras, criterio.getMinCantCocheras()));
		}

		if (criterio.getMinCantDormitorios() != null) {
			criteria.add(Restrictions.ge(Inmueble.pCantDormitorios, criterio.getMinCantDormitorios()));
		}

		if (criterio.getMinSupCubierta() != null) {
			criteria.add(Restrictions.ge(Inmueble.pSupCubierta, criterio.getMinSupCubierta()));
		}

		if (criterio.getMinSupTotal() != null) {
			criteria.add(Restrictions.ge(Inmueble.pSupTotal, criterio.getMinSupTotal()));
		}

		if (criterio.getTipoInmueble() != null) {
			criteria.add(Restrictions.eq(Inmueble.pTipoInmb, criterio.getTipoInmueble()));
		}

//		if (criterio.getTipoOperacion() != null) {
//			TipoOperacion to = criterio.getTipoOperacion();
//			if (to == TipoOperacion.Alquiler) {
//				criteria.add(criterion)
//			}
//		}

		return findByCriteria(criteria);
	}

}
