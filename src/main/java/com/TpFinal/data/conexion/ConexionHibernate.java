package com.TpFinal.data.conexion;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;


public class ConexionHibernate {
	private static Configuration configuration = new Configuration();
	private static SessionFactory sf=null;
	
	//TipoConexion Por Defecto
	private static TipoConexion	tipoConexion =TipoConexion.H2Server;
	
	
	public static void setTipoConexion(TipoConexion tipo) {
		tipoConexion = tipo;
	}
	
	private static Configuration getConfiguration() {
		//La ultima property es para que no crashee el add lo saque de aca:
		// https://stackoverflow.com/questions/32968527/hibernate-sequence-doesnt-exist
		configuration
		 		.configure().setProperties(tipoConexion.properties());
		return configuration;
	}
	
	private static ServiceRegistry getServiceRegistry(Configuration configuration) {
		ServiceRegistry serviceRegistry=null;
		try {
			
			 serviceRegistry = new StandardServiceRegistryBuilder()
					 				.applySettings(configuration.getProperties())
					 				.configure()
					 				.build();
			
		} catch (Exception e) {
			System.err.println("Error al conectar: ");
			e.printStackTrace();
		}
		return serviceRegistry;
	}
	
	private static SessionFactory getSession() {
		if(sf==null) {
			try {
				sf=getConfiguration().buildSessionFactory(getServiceRegistry(getConfiguration()));
				System.out.println("Conexion exitosa a url: "+tipoConexion.properties().getProperty(Environment.URL));
				System.out.println("----------------");
			} catch (Exception e) {
				System.err.println("Error al establecer la conexión.");
				e.printStackTrace();
			}
		}
		return sf;
	}

	public static Session openSession() {
		return getSession().openSession();
	}
	public static void createSessionFactory() {
	    getSession();
	}
	
	public static void close() {
		if(sf!= null) {
			sf.close();
		}
	}
	
}
