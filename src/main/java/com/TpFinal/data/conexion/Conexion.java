package com.TpFinal.data.conexion;

import java.io.File;
import java.util.Properties;

import org.apache.commons.io.FileExistsException;
import org.hibernate.cfg.Environment;
import com.TpFinal.properties.Parametros;

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
	    String useNewIdGeneratorMappings, String hbm2ddlauto, String dbPath, String dbName) {
	this.dialect = dialect;
	this.driver = driver;
	this.url = url;
	this.user = user;
	this.pass = pass;
	this.useNewIdGeneratorMappings = useNewIdGeneratorMappings;
	this.hbm2ddlauto = hbm2ddlauto;
	this.dbPath = dbPath;
	this.dbName = dbName;
	this.generarProperties();
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
	if (tc.equals(TipoConexion.H2Server)) {
	    try {
		c.setDbName(Parametros.getProperty(Parametros.DB_NAME));
		c.setDbRelativePath(Parametros.getProperty(Parametros.DB_PATH));
	    } catch (IllegalArgumentException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    } catch (FileExistsException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }

	} else {
	    c.setDbName("test_db");
	}
	return c;
    }

    private void generarProperties() {
	properties.setProperty(Environment.DIALECT, this.dialect);
	properties.setProperty(Environment.DRIVER, this.driver);
	properties.setProperty(Environment.DIALECT, this.dialect);
	properties.setProperty(Environment.HBM2DDL_AUTO, this.hbm2ddlauto);
	properties.setProperty(Environment.PASS, this.pass);
	properties.setProperty(Environment.USE_NEW_ID_GENERATOR_MAPPINGS, this.useNewIdGeneratorMappings);
	properties.setProperty(Environment.USER, this.user);
	properties.setProperty(Environment.URL, this.url);
    }

    public String getDbPath() {
	return dbPath;
    }

    /**
     * <p>
     * Setea la url al path relativo pasado como parametro.
     * <p>
     * URL = "jdbc:h2:file:." + File.separator + dbPath + File.separator + dbName
     * 
     * @param dbPath
     */
    public void setDbRelativePath(String dbPath) {
	this.dbPath = dbPath;
	this.url = "jdbc:h2:file:." + File.separator + dbPath + File.separator + dbName + ";AUTO_SERVER=TRUE";
	this.generarProperties();
    }

    /**
     * <p>
     * Setea la url al path absoluto pasado como parametro.
     * <p>
     * URL = "jdbc:h2:file:" + dbPath + File.separator + dbName
     * 
     * @param dbPath
     */
    public void setDbAbsolutePath(String dbPath) {
	this.dbPath = dbPath;
	this.url = "jdbc:h2:file:" + dbPath + File.separator + dbName + ";AUTO_SERVER=TRUE";
	this.generarProperties();
    }

    public String getDbName() {
	return dbName;
    }

    public void setDbName(String dbName) {
	this.dbName = dbName;
	this.generarProperties();
    }

    public String getDialect() {
	return dialect;
    }

    public void setDialect(String dialect) {
	this.dialect = dialect;
	this.generarProperties();
    }

    public String getDriver() {
	return driver;
    }

    public void setDriver(String driver) {
	this.driver = driver;
	this.generarProperties();
    }

    public String getUrl() {
	return url;

    }

    public void setUrl(String url) {
	this.url = url;
	this.generarProperties();
    }

    public String getUser() {
	return user;
    }

    public void setUser(String user) {
	this.user = user;
	this.generarProperties();
    }

    public String getPass() {
	return pass;
    }

    public void setPass(String pass) {
	this.pass = pass;
	this.generarProperties();
    }

    public String getUseNewIdGeneratorMappings() {
	return useNewIdGeneratorMappings;
    }

    public void setUseNewIdGeneratorMappings(String useNewIdGeneratorMappings) {
	this.useNewIdGeneratorMappings = useNewIdGeneratorMappings;
	this.generarProperties();
    }

    public String getHbm2ddlauto() {
	return hbm2ddlauto;
    }

    public void setHbm2ddlauto(String hbm2ddlauto) {
	this.hbm2ddlauto = hbm2ddlauto;
	this.generarProperties();

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
