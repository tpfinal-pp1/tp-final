package com.TpFinal.data.conexion;

import java.util.Properties;

import org.hibernate.cfg.Environment;

public enum TipoConexion {	H2Test("org.hibernate.dialect.H2Dialect"
									,"org.h2.Driver"
									,"jdbc:h2:~//Test;AUTO_SERVER=TRUE"
									,"root"
									,"root"
									,"false"),
							H2Server("org.hibernate.dialect.H2Dialect"
									,"org.h2.Driver"
									,"jdbc:h2:~//Inmobi;AUTO_SERVER=TRUE"
									,"root"
									,"root"
									,"false");
	
	private final String dialect;
	private final String driver;
	private final String url;
	private final String user;
	private final String pass;
	private final String useNewIdGeneratorMappings;
	
	private TipoConexion(String dialect, String driver, String url, String user, String pass, String useNewIdGeneratorMappings) {
		this.dialect = dialect;
		this.driver = driver;
		this.url = url;
		this.user = user;
		this.pass = pass;
		this.useNewIdGeneratorMappings = useNewIdGeneratorMappings;
	}
	
	public Properties properties() {
		Properties p = new Properties();
		p.setProperty(Environment.DIALECT, this.dialect);
		p.setProperty(Environment.DRIVER, this.driver);
		p.setProperty(Environment.URL, this.url);
		p.setProperty(Environment.USER, this.user);
		p.setProperty(Environment.PASS, this.pass);
		p.setProperty(Environment.USE_NEW_ID_GENERATOR_MAPPINGS, this.useNewIdGeneratorMappings);
		return p;
	}
	

}
