package com.TpFinal.data.conexion;

import java.io.File;
import java.util.Properties;
import java.util.Random;

import org.hibernate.cfg.Environment;

public class Conexion {
    private String dialect;
    private String driver;
    private String url;
    private String user;
    private String pass;
    private String useNewIdGeneratorMappings;
    private String hbm2ddlauto;
    private String dbPath;
    private String dbName;
    private Properties properties;

    public Conexion(String dialect, String driver, String url, String user, String pass,
	    String useNewIdGeneratorMappings, String hbm2ddlauto) {
	this.dialect = dialect;
	this.driver = driver;
	this.url = url;
	this.user = user;
	this.pass = pass;
	this.useNewIdGeneratorMappings = useNewIdGeneratorMappings;
	this.hbm2ddlauto = hbm2ddlauto;
	// TODO
	// this.generarProperties();
    }

    public Conexion(Builder b) {
	this.dialect = b.dialect;
	this.driver = b.driver;
	this.url = b.url;
	this.user = b.user;
	this.pass = b.pass;
	this.useNewIdGeneratorMappings = b.useNewIdGeneratorMappings;
	this.hbm2ddlauto = b.hbm2ddlauto;
	this.properties = b.properties;
    }

    public static Conexion getTipoConexionFrom(TipoConexion tc) {
	Properties p = tc.properties();
	Conexion c = new Conexion.Builder()
		.setProperties(p)
		.setDialect(p.getProperty(Environment.DIALECT))
		.setDriver(p.getProperty(Environment.DRIVER))
		.setHbm2ddlauto(p.getProperty(Environment.HBM2DDL_AUTO))
		.setPass(p.getProperty(Environment.PASS))
		.setUrl(p.getProperty(Environment.URL))
		.setUseNewIdGeneratorMappings(p.getProperty(Environment.USE_NEW_ID_GENERATOR_MAPPINGS))
		.setUser(p.getProperty(Environment.USER))
		.build();
	if(tc.equals(TipoConexion.H2Server)) {
	    Random r = new Random();
	    c.setDbName("db_"+ r.nextInt(Integer.MAX_VALUE));
	    p.setProperty(Environment.URL, "jdbc:h2:~//"+ c.getDbName() +";AUTO_SERVER=TRUE");
	}else {
	    c.setDbName("Test");
	}
	return c;
    }

    public String getDbPath() {
	return dbPath;
    }

    /**
     * <p>Setea la url al path relativo pasado como parametro.
     * <p> URL = "jdbc:h2:file:." + File.separator + dbPath + File.separator + dbName
     * @param dbPath
     */
    public void setDbRelativePath(String dbPath) {
	this.dbPath = dbPath;
	this.url = "jdbc:h2:file:." + File.separator + dbPath + File.separator + dbName +";AUTO_SERVER=TRUE";
    }
    
    /**
     * <p>Setea la url al path absoluto pasado como parametro.
     * <p> URL = "jdbc:h2:file:" + dbPath + File.separator + dbName
     * @param dbPath
     */
    public void setDbAbsolutePath(String dbPath) {
   	this.dbPath = dbPath;
   	this.url = "jdbc:h2:file:" + dbPath + File.separator + dbName +";AUTO_SERVER=TRUE";
       }

    public String getDbName() {
	return dbName;
    }

    public void setDbName(String dbName) {
	this.dbName = dbName;
    }

    public String getDialect() {
	return dialect;
    }

    public void setDialect(String dialect) {
	this.dialect = dialect;
    }

    public String getDriver() {
	return driver;
    }

    public void setDriver(String driver) {
	this.driver = driver;
    }

    public String getUrl() {
	return url;
    }

    public void setUrl(String url) {
	this.url = url;
    }

    public String getUser() {
	return user;
    }

    public void setUser(String user) {
	this.user = user;
    }

    public String getPass() {
	return pass;
    }

    public void setPass(String pass) {
	this.pass = pass;
    }

    public String getUseNewIdGeneratorMappings() {
	return useNewIdGeneratorMappings;
    }

    public void setUseNewIdGeneratorMappings(String useNewIdGeneratorMappings) {
	this.useNewIdGeneratorMappings = useNewIdGeneratorMappings;
    }

    public String getHbm2ddlauto() {
	return hbm2ddlauto;
    }

    public void setHbm2ddlauto(String hbm2ddlauto) {
	this.hbm2ddlauto = hbm2ddlauto;
    }

    public Properties getProperties() {
	return properties;
    }

    public void setProperties(Properties properties) {
	this.properties = properties;
    }

    public static class Builder {

	private String hbm2ddlauto;
	private String useNewIdGeneratorMappings;
	private String pass;
	private String user;
	private String url;
	private String driver;
	private String dialect;
	private Properties properties;

	public Builder setHbm2ddlauto(String hbm2ddlauto) {
	    this.hbm2ddlauto = hbm2ddlauto;
	    return this;
	}

	public Builder setUseNewIdGeneratorMappings(String useNewIdGeneratorMappings) {
	    this.useNewIdGeneratorMappings = useNewIdGeneratorMappings;
	    return this;
	}

	public Builder setProperties(Properties properties) {
	    this.properties = properties;
	    return this;
	}

	public Builder setPass(String pass) {
	    this.pass = pass;
	    return this;
	}

	public Builder setUser(String user) {
	    this.user = user;
	    return this;
	}

	public Builder setUrl(String url) {
	    this.url = url;
	    return this;
	}

	public Builder setDriver(String driver) {
	    this.driver = driver;
	    return this;
	}

	public Builder setDialect(String dialect) {
	    this.dialect = dialect;
	    return this;
	}

	private Conexion build() {
	    return new Conexion(this);
	}

    }

}
