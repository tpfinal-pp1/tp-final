package com.TpFinal.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import org.apache.commons.io.FileExistsException;

public class Parametros {
	private static Properties p = new Properties();
	//por si ser rompe, lo unico que hice es agregar la ruta C:\\TP\\
	public static final String PARAM_FILE = "C:\\TP\\TP.properties";
	
	//legacy code, ahora asigno las properties directamente
	public static final String sendGridApiKey = "sendgrid.api.key";
	

	public static String getProperty(String key)throws IllegalArgumentException, FileExistsException{
		try {
			File f = new File(PARAM_FILE);
			if (!f.exists()) 
				throw new FileExistsException("El archivo no existe, copialo y pegalo en la ruta");
			
			FileInputStream fis = new FileInputStream(PARAM_FILE);
			p.load(fis);
		} catch (Exception e) {
			System.err.println(PARAM_FILE + ":archivo no encontrado.");
		}

		if (p.getProperty(key) == null){
			throw new IllegalArgumentException("El parametro no existe");
		}
		return p.getProperty(key);
	}

}
