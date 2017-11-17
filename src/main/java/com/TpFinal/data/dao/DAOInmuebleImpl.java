package com.TpFinal.data.dao;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.jdbc.BlobProxy;

import com.TpFinal.data.conexion.ConexionHibernate;
import com.TpFinal.data.dao.interfaces.DAOInmueble;
import com.TpFinal.dto.EstadoRegistro;
import com.TpFinal.dto.contrato.Contrato;
import com.TpFinal.dto.inmueble.ClaseInmueble;
import com.TpFinal.dto.inmueble.CriterioBusqInmueble;
import com.TpFinal.dto.inmueble.Direccion;
import com.TpFinal.dto.inmueble.EstadoInmueble;
import com.TpFinal.dto.inmueble.Imagen;
import com.TpFinal.dto.inmueble.Inmueble;
import com.TpFinal.dto.publicacion.Publicacion;
import com.TpFinal.dto.publicacion.PublicacionAlquiler;
import com.TpFinal.dto.publicacion.PublicacionVenta;
import com.TpFinal.dto.publicacion.TipoPublicacion;

public class DAOInmuebleImpl extends DAOImpl<Inmueble> implements DAOInmueble {
    private static Logger logger = Logger.getLogger(DAOInmuebleImpl.class);

    public DAOInmuebleImpl() {
	super(Inmueble.class);
    }

    @Override
    public boolean addImagen(Imagen img, Inmueble inmueble) {
	boolean ret = false;
	FileInputStream docInputStream = null;
	Session session = ConexionHibernate.openSession();
	Transaction tx = null;
	try {
	    tx = session.beginTransaction();
	    inmueble.removeImagen(img);
	    session.merge(inmueble);
	    tx.commit();
	} catch (HibernateException e) {
	    System.err.println("Error al realizar Merge: " + inmueble);
	    e.printStackTrace();
	    if (tx != null)
		tx.rollback();
	} finally {
	    session.close();
	}
	session = ConexionHibernate.openSession();
	try {
	    tx = session.beginTransaction();
	    Blob archivo = null;
	    byte[] imagen = getImage(img.getPath());
	    if (imagen != null) {
		archivo = BlobProxy.generateProxy(imagen);
		img.setImagen(archivo);
		inmueble.addImagen(img);
		session.merge(inmueble);
		tx.commit();
		ret = true;
	    } else {
		ret = false;
		tx.rollback();
	    }
	} catch (HibernateException e) {
	    System.err.println("Error al realizar Merge: " + inmueble);
	    e.printStackTrace();
	    if (tx != null)
		tx.rollback();
	} finally {
	    session.close();
	    if (docInputStream != null)
		try {
		    docInputStream.close();
		} catch (IOException e) {
		    System.err.println("Error cerrar el archivo: " + docInputStream);
		    e.printStackTrace();
		}
	}
	return ret;

    }

    public static byte[] getImage(String path) {
	File file = new File("./" + path);
	if (file.exists()) {
	    try {
		BufferedImage bufferedImage = ImageIO.read(file);
		ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
		ImageIO.write(bufferedImage, "jpg", byteOutStream);
		return byteOutStream.toByteArray();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
	return null;
    }

    @Override
    public List<Inmueble> findInmueblesbyEstado(EstadoInmueble estado) {
	DetachedCriteria criteria = DetachedCriteria.forClass(Inmueble.class)
		.add(Restrictions.eq(Inmueble.pEstadoInmueble, estado)).setResultTransformer(
			Criteria.DISTINCT_ROOT_ENTITY);

	return findByCriteria(criteria);
    }

    @Override
    public List<Inmueble> findInmueblesbyCaracteristicas(CriterioBusqInmueble criterio) {

	DetachedCriteria query = null;
	List<Inmueble> resultadoQuery = new ArrayList<>();

	// if (criterio.getTipoPublicacion() != null || criterio.getTipoMoneda() !=
	// null) {
	// TipoPublicacion to = criterio.getTipoPublicacion();
	// query =
	// DetachedCriteria.forClass(Publicacion.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	//
	// if (to != null || (criterio.getEstadoInmueble() != null && criterio
	// .getEstadoInmueble() == EstadoInmueble.EnAlquilerYVenta)) {
	// query.add(Restrictions.eq(Publicacion.pTipoPublicacion, to));
	// query.add(Restrictions.eq("estadoRegistro", EstadoRegistro.ACTIVO));
	//
	// if (to != null) {
	// if (to.equals(TipoPublicacion.Alquiler)) {
	// addRestriccionesDeAlquiler(query, criterio);
	//
	// } else if (to.equals(TipoPublicacion.Venta)) {
	// addRestriccionesDeVenta(query, criterio);
	// }
	// } else {
	// addRestriccionesDeVentaOAlquiler(query, criterio);
	// }
	// }
	//
	// if (criterio.getTipoMoneda() != null) {
	// query.add(Restrictions.eq("moneda", criterio.getTipoMoneda()));
	// }
	//
	// DAOImpl<Publicacion> dao = new DAOImpl<>(Publicacion.class);
	// query.createAlias("inmueble", "i");
	// addRestriccionesDeInmueble(query, criterio, "i.");
	// List<Publicacion> publicaciones = dao.findByCriteria(query);
	// for (Publicacion o : publicaciones) {
	// resultadoQuery.add(o.getInmueble());
	// }
	//
	// } else {
	query = DetachedCriteria.forClass(Inmueble.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	addRestriccionesDeInmueble(query, criterio, "");
	resultadoQuery = findByCriteria(query);
	// }
	return resultadoQuery;
    }

    private void addRestriccionesDeInmueble(DetachedCriteria query, CriterioBusqInmueble criterio,
	    String alias) {
	query.add(Restrictions.eq(alias + "estadoRegistro", EstadoRegistro.ACTIVO));
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

	if (criterio.getLocalidad() != null || criterio.getProvincia() != null) {
	    String aliasDir = "d";
	    query.createAlias(Inmueble.pDireccion, aliasDir);
	    if (criterio.getLocalidad() != null) {
		query.add(Restrictions.eq(aliasDir + "." + Direccion.pLocalidad, criterio.getLocalidad()));
	    }

	    if (criterio.getProvincia() != null) {
		query.add(Restrictions.eq(aliasDir + "." + Direccion.pProvincia, criterio.getProvincia()));
	    }
	}

	if (criterio.getClasesDeInmueble() != null) {
	    Disjunction disjuncion = Restrictions.disjunction();
	    criterio.getClasesDeInmueble()
		    .forEach(clase -> disjuncion.add(Restrictions.eq(alias + Inmueble.pClaseInmb, clase)));
	    query.add(disjuncion);
	}

	if (criterio.getEstadoInmueble() != null) {
	    if (criterio.getEstadoInmueble() != EstadoInmueble.EnAlquilerYVenta) {
		query.add(Restrictions.eq(alias + Inmueble.pEstadoInmueble, criterio.getEstadoInmueble()));
	    } else {
		Disjunction disjuncion = Restrictions.disjunction();
		disjuncion.add(Restrictions.eq(alias + Inmueble.pEstadoInmueble, EstadoInmueble.EnAlquiler));
		disjuncion.add(Restrictions.eq(alias + Inmueble.pEstadoInmueble, EstadoInmueble.EnVenta));
		disjuncion.add(Restrictions.eq(alias + Inmueble.pEstadoInmueble, EstadoInmueble.EnAlquilerYVenta));
		query.add(disjuncion);
	    }
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

    private void addRestriccionesDeAlquiler(DetachedCriteria query, CriterioBusqInmueble criterio) {
	if (criterio.getMinPrecio() != null) {
	    query.add(Restrictions.ge(PublicacionAlquiler.pPrecioAlquiler, criterio.getMinPrecio()));
	}

	if (criterio.getMaxPrecio() != null) {
	    query.add(Restrictions.le(PublicacionAlquiler.pPrecioAlquiler, criterio.getMaxPrecio()));
	}
    }

    private void addRestriccionesDeVenta(DetachedCriteria query, CriterioBusqInmueble criterio) {
	if (criterio.getMinPrecio() != null) {
	    query.add(Restrictions.ge(PublicacionVenta.pPrecioVenta, criterio.getMinPrecio()));
	}

	if (criterio.getMaxPrecio() != null) {
	    query.add(Restrictions.le(PublicacionVenta.pPrecioVenta, criterio.getMaxPrecio()));
	}
    }

    private void addRestriccionesDeVentaOAlquiler(DetachedCriteria query, CriterioBusqInmueble criterio) {
	if (criterio.getMinPrecio() != null) {
	    Disjunction disjuncion = Restrictions.disjunction();
	    disjuncion.add(Restrictions.ge(PublicacionAlquiler.pPrecioAlquiler, criterio.getMinPrecio()));
	    disjuncion.add(Restrictions.ge(PublicacionVenta.pPrecioVenta, criterio.getMinPrecio()));
	    query.add(disjuncion);
	}

	if (criterio.getMaxPrecio() != null) {
	    Disjunction disjuncion = Restrictions.disjunction();
	    disjuncion.add(Restrictions.le(PublicacionAlquiler.pPrecioAlquiler, criterio.getMaxPrecio()));
	    disjuncion.add(Restrictions.le(PublicacionVenta.pPrecioVenta, criterio.getMaxPrecio()));
	    query.add(disjuncion);
	}

    }

}
