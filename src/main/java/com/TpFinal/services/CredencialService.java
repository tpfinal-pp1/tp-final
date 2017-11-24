package com.TpFinal.services;


import java.io.File;
import java.io.IOException;
import java.time.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.TpFinal.utils.Cipher;
import com.google.gwt.i18n.client.constants.DateTimeConstantsImpl;
import org.apache.log4j.Logger;
import com.TpFinal.DashboardUI;
import com.TpFinal.data.dao.DAOCredencialImpl;
import com.TpFinal.data.dao.DAOPersonaImpl;
import com.TpFinal.data.dao.interfaces.DAOCredencial;
import com.TpFinal.dto.persona.*;
import com.TpFinal.view.DashboardViewType;


public class CredencialService {
    private final static Logger logger = Logger.getLogger(CredencialService.class);

    DAOCredencial dao;

    public CredencialService() {
	dao = new DAOCredencialImpl();
    }

    public boolean saveOrUpdate(Credencial c) {
	return dao.saveOrUpdate(c);
    }

    public boolean delete(Credencial c) {
	return dao.logicalDelete(c);
    }

    public List<Credencial> readAll() {
	return dao.readAllActives();
    }

    public boolean existe(Credencial credencial) {
	return dao.existe(credencial);
    }

    public boolean existeUsuario(String usuario) {
	return usuario != null && usuario != "" ? dao.readAllActives().stream().anyMatch(c -> c.getUsuario().equals(
		usuario)) : false;
    }

    public void deepDelete(Credencial credencial) {
	if (credencial.getId() != null) {
	    DAOPersonaImpl daop = new DAOPersonaImpl();
	    credencial.getEmpleado().setCredencial(null);
	    daop.saveOrUpdate(credencial.getEmpleado().getPersona());
	    dao.delete(credencial);
	}

    }

    public static Empleado getCurrentUser() {
	return DashboardUI.getEmpleadoLogueado();
    }

    public boolean hasViewAccess(Credencial credencial, Class view) {

	ArrayList<DashboardViewType> userViews = credencial.getViewAccess().views;
	for (DashboardViewType viewType : userViews)
	    if (viewType.getViewClass().equals(view))
		return true;
	return false;
    }


    public static boolean demoIsOver(){
		File f=new File("inmobi.db.mv");
		return f.exists();
	}

	public static boolean terminateDemo(){
		File f=new File("inmobi.db.mv");
		try {
			return f.createNewFile();
		} catch (IOException e) {
			return false;
		}
	}

    public boolean isfirstRun(){
    	return readAll().size()==0;
	}
  public boolean validateSerial(String serial){
    	try {
			long instant = Long.valueOf(Cipher.decrypt(serial));
			Instant instantOfKey = Instant.ofEpochMilli(instant);
			LocalDate dateOfKey= instantOfKey.atZone(ZoneId.systemDefault()).toLocalDate();;
			LocalDate now=LocalDate.now();
			System.out.println("Date of Key: "+dateOfKey);
			System.out.println("Now: "+now);
			return dateOfKey.plusDays(3).isAfter(now);


		}
    	catch (Exception e){

    		return false;
		}

	}

    public Empleado logIn(String user, String pass) {
		Empleado ret = new Empleado();
		ArrayList<Credencial> credencials = new ArrayList<Credencial>();
		try {
			credencials.addAll(readAll());
		} catch (Exception e) {
			e.printStackTrace();
		}
		boolean hayAlMenosUnAdmin = false;
		boolean huboMatch = false;
		boolean noHayEmpleados = credencials.size() == 0;
		//SERIAL Y ACTIVACION
		if (noHayEmpleados) {
			if(validateSerial(user+pass)){
				return  temporalAdmin();
			}
			else{
				File f=new File("inmobi.db.mv");
				if(demoIsOver()){
					return new Empleado();
				}
				else if(terminateDemo()){
					return temporalDemo();
				}
				else{
					return new Empleado();
				}
				}
			}



		for (Credencial cred : credencials) {

			if (credencials.size() == 1) { // ANTILOCKOUT
			if (logger.isDebugEnabled()) {
				logger.debug("Credencial size = 1 -> ok");
			}
			Persona personaTemp = cred.getEmpleado().getPersona();
			if ((personaTemp.getNombre() + personaTemp.getApellido()).equals("AccesoTemporal")) {
				cred.setViewAccess(ViewAccess.Admin);
				return cred.getEmpleado();
			}
			}

			boolean match = cred.getUsuario().equals(user)
				&& cred.getContrasenia().equals(pass);
			if (match) {
			ret = cred.getEmpleado();
			huboMatch = true;
			}
			if (cred.getViewAccess().equals(ViewAccess.Admin)) {
			hayAlMenosUnAdmin = true;

			}
		}
		// ANTILOCKOUT
		if (!hayAlMenosUnAdmin && !huboMatch) {

			Persona p = new Persona.Builder().setNombre("Acceso").setApellido("Temporal").build();
			Empleado emp = new Empleado.Builder().setPersona(p).setPersona(p).build();
			Credencial cred = new Credencial();
			emp.setCategoriaEmpleado(CategoriaEmpleado.sinCategoria);

			if (noHayEmpleados) {
			cred.setUsuario("admin");
			cred.setContrasenia("admin");
			} else {
			cred.setUsuario(String.valueOf(Instant.now().toEpochMilli()));
			cred.setContrasenia(String.valueOf(Instant.now().toEpochMilli()));
			}
			cred.setEmpleado(emp);
			cred.setViewAccess(ViewAccess.Recovery);
			emp.setCredencial(cred);
			ret = emp;
		}
		// ANTILOCKOUT
		else if (!hayAlMenosUnAdmin && huboMatch) {
			Credencial credUpgrade = ret.getCredencial();
			credUpgrade.setViewAccess(ViewAccess.Admin);
			ret.setCredencial(credUpgrade);
		}

		return ret;
    }

    public static void crearAdminAdmin() {
	DAOPersonaImpl daoPersona = new DAOPersonaImpl();
	DAOCredencialImpl daoCredencial = new DAOCredencialImpl();
	List<Persona> personas = daoPersona.readAllActives();
	boolean existeAdminAdmin = daoCredencial.readAll()
		.stream().peek(c -> logger.debug("Filtrando: " + "user: " + c.getUsuario() + " pass: " + c
			.getContrasenia()))
		.anyMatch(c -> c.getUsuario().equals("admin") && c.getContrasenia().equals("admin"));
	if (!existeAdminAdmin) {
	    if (logger.isDebugEnabled()) {
		logger.debug("Creando admin admin");
		daoPersona.saveOrUpdate(empleadoRandom());
	    }

	}

    }


	private Empleado temporalDemo(){
		Persona p = new Persona.Builder().setNombre("Modo").setApellido("Demostración").build();
		Empleado emp = new Empleado.Builder().setPersona(p).setPersona(p).build();
		Credencial cred = new Credencial();
		emp.setCategoriaEmpleado(CategoriaEmpleado.sinCategoria);
		cred.setUsuario("demo");
		cred.setContrasenia("demo");
		cred.setEmpleado(emp);
		cred.setViewAccess(ViewAccess.Demo);
		emp.setCredencial(cred);
		return emp;
	}

	private Empleado temporalAdmin(){
		Persona p = new Persona.Builder().setNombre("Acceso").setApellido("Temporal").build();
		Empleado emp = new Empleado.Builder().setPersona(p).setPersona(p).build();
		Credencial cred = new Credencial();
		emp.setCategoriaEmpleado(CategoriaEmpleado.sinCategoria);
		cred.setUsuario("admin");
		cred.setContrasenia("admin");
		cred.setEmpleado(emp);
		cred.setViewAccess(ViewAccess.Recovery);
		emp.setCredencial(cred);
		return emp;
	}


    private static Persona empleadoRandom() {

	Persona p = new Persona.Builder()
		.setApellido("por defecto")
		.setDNI("123123123")
		.setinfoAdicional("Info adicional del admin")
		.setMail("asd@dsa.com")
		.setNombre("Admin")
		.setTelefono("123123123")
		.setTelefono2("3231321321")
		.build();
	Empleado e = new Empleado.Builder()
		.setCategoriaEmpleado(CategoriaEmpleado.admin)
		.setFechaDeAlta(LocalDate.now().minus(Period.ofMonths(1)))
		.setPersona(p)
		.build();
	String usuario = p.getNombre().toLowerCase();
	Credencial c = new Credencial.Builder()
		.setContrasenia("admin")
		.setEmpleado(e)
		.setUsuario("admin")
		.build();

	// Setea access a las views
	c.setViewAccess(ViewAccess.Admin);
	e.setCredencial(c);
	return p;
    }

}
