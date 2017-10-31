package com.TpFinal.data.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.TpFinal.data.conexion.ConexionHibernate;
import com.TpFinal.data.dao.interfaces.DAOCredencial;
import com.TpFinal.dto.EstadoRegistro;
import com.TpFinal.dto.persona.Credencial;

public class DAOCredencialImpl extends DAOImpl<Credencial> implements DAOCredencial{

	public DAOCredencialImpl() {
		super(Credencial.class);
	}

	@Override
	public boolean existe(Credencial c) {
		DetachedCriteria criteria = DetachedCriteria.forClass(c.getClass())
				.add(Restrictions.eq("usuario", c.getUsuario()))
				.add(Restrictions.eq("contrasenia", c.getContrasenia()))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			List<Credencial>credenciales= borrarRepetidos(findByCriteria(criteria));
		return true && credenciales.size()>0;
	}

}
