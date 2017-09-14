package com.PubliciBot.DAO.Neodatis;

import com.PubliciBot.DAO.Interfaces.DAO;
import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;

import java.util.List;


public class DAONeodatis<T> implements DAO<T> {

	protected String fileNameNeodatisDB = "PubliciBOT.DB";


	public void guardar(T t){
		ODB odb  = null;
		try {
			odb = ODBFactory.open(fileNameNeodatisDB);
			odb.store(t);
		}
		catch (Exception e){
			e.printStackTrace();
		}
		finally {
			odb.close();
		}
	}

	public Objects<T> obtenerTodos(Class<T> c) {
		ODB odb  = null;
		Objects<T> ret=null;
			try {
				odb = ODBFactory.open(fileNameNeodatisDB);

				ret = odb.getObjects(c);
			}
		catch (Exception e){
			e.printStackTrace();
		}
		finally {
			odb.close();
		}
		return ret;
	}


	public void eliminar(T t){
		ODB odb = null;
		try {
			odb = ODBFactory.open(fileNameNeodatisDB);
			odb.delete(t);
		}
		catch (Exception e){
			e.printStackTrace();
		}
		finally{
			odb.close();
		}
	}

	public boolean exists(T t){
		ODB odb = null;
		Objects<T> objs =null;
		try {
			odb = ODBFactory.open(fileNameNeodatisDB);

			objs = odb.getObjects(t.getClass());
		}
		catch (Exception e){
			e.printStackTrace();
		}
		finally{
			odb.close();
		}
		return objs.contains(t);
	}



	public List<T> buscar(T t, String campo, Object valor){
		ODB odb = null;
		Objects<T> objs =null;
		try {
			odb = ODBFactory.open(fileNameNeodatisDB);

			IQuery query = new CriteriaQuery(t.getClass(), Where.equal(campo, valor));
			objs = odb.getObjects(query);
		}
		catch (Exception e){
			e.printStackTrace();
		}
        finally{
			odb.close();
		}
		return (List<T>) objs;
	}
}
