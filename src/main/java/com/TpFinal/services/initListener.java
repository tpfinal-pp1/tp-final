package com.TpFinal.services;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.TpFinal.data.conexion.ConexionHibernate;
import com.TpFinal.utils.GeneradorDeDatos;

/**
 * Application Lifecycle Listener implementation class initListener
 *
 */
//@WebListener
public class initListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public initListener() {
        // TODO Auto-generated constructor stub
	
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
	System.out.println("Cerrando conexiones");
        ConexionHibernate.close();
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  { 
         System.out.println("Iniciando Server..");
         ConexionHibernate.createSessionFactory();
         
         
         
    }
	
}
