package com.TpFinal.data.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.TpFinal.data.dao.interfaces.DAOCobro;
import com.TpFinal.dto.cobro.Cobro;
import com.TpFinal.dto.cobro.EstadoCobro;


public class DAOCobroImpl extends DAOImpl<Cobro> implements DAOCobro{

	public DAOCobroImpl() {
		super(Cobro.class);
	}

	@Override
	public List<Cobro> findCobrobyEstado(EstadoCobro estado) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Cobro.class)
				.add(Restrictions.eq(Cobro.pEstadoCobro, estado)).setResultTransformer(
					Criteria.DISTINCT_ROOT_ENTITY);

			return findByCriteria(criteria);
	}

	
}
