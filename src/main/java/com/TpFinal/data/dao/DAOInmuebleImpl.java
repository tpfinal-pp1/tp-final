package com.TpFinal.data.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.TpFinal.data.dao.interfaces.DAOInmueble;
import com.TpFinal.domain.inmueble.EstadoInmueble;
import com.TpFinal.domain.inmueble.Inmueble;

public class DAOInmuebleImpl extends DAOImpl<Inmueble> implements DAOInmueble{

	public DAOInmuebleImpl(Class<Inmueble> clase) {
		super(Inmueble.class);		
	}

	@Override
	public List<Inmueble> findInmueblesbyEstado(EstadoInmueble estado) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Inmueble.class).add(Restrictions.eq("estadoInmueble", estado));
		
		return findByCriteria(criteria);
	}

}
