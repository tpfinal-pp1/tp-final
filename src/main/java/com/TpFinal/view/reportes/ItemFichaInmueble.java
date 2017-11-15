package com.TpFinal.view.reportes;

import java.awt.Image;

import com.TpFinal.dto.inmueble.Inmueble;
import com.TpFinal.services.UbicacionService;

public class ItemFichaInmueble {
   
    private Integer cantidadAmbientes;
    private Integer cantidadDormitorios;
    private Integer superficieTotal;
    private Integer superficieCubierta;
    private Boolean aEstrenar;
    private Integer cantidadCocheras;
    private Boolean conAireAcondicionado;
    private Boolean conJardin;
    private Boolean conParrilla;
    private Boolean conPileta;
    private String estadoInmueble;
    private String tipoInmueble;
    private String claseInmueble;
	private String calleDireccion;
	private String localidadDireccion;
	private Integer nroDireccion;
	private String codPostalDireccion;
	private String provinciaDireccion;
	private String paisDireccion;
    private String nombrePropietario;
    private String apellidoPropietario;
    private String propietario;
    private Image imagen;
    private UbicacionService service = new UbicacionService();
    
    public ItemFichaInmueble(Inmueble inmueble) {
    	 this.cantidadAmbientes = inmueble.getCantidadAmbientes();
    	 this.cantidadDormitorios = inmueble.getCantidadDormitorios();
    	 this.superficieTotal = inmueble.getSuperficieTotal();
    	 this.superficieCubierta = inmueble.getSuperficieCubierta();
    	 this.aEstrenar = inmueble.getaEstrenar();
    	 this.cantidadCocheras = inmueble.getCantidadCocheras();
    	 this.conAireAcondicionado = inmueble.getConAireAcondicionado();
    	 this.conJardin = inmueble.getConJardin();
    	 this.conParrilla = inmueble.getConParilla();
    	 this.conPileta = inmueble.getConPileta();
    	 this.estadoInmueble = inmueble.getEstadoInmueble().toString();
    	 this.tipoInmueble = inmueble.getTipoInmueble().toString();
    	 this.claseInmueble = inmueble.getClaseInmueble().toString();
    	 this.calleDireccion = inmueble.getDireccion().getCalle();
    	 this.localidadDireccion = inmueble.getDireccion().getLocalidad();
    	 this.nroDireccion = inmueble.getDireccion().getNro();
    	 this.codPostalDireccion = inmueble.getDireccion().getCodPostal();
    	 this.provinciaDireccion = inmueble.getDireccion().getProvincia();
    	 this.paisDireccion = inmueble.getDireccion().getPais();
    	 this.nombrePropietario = inmueble.getPropietario().getPersona().getNombre();
    	 this.apellidoPropietario = inmueble.getPropietario().getPersona().getApellido();
    	 this.propietario = inmueble.getPropietario().getPersona().toString();
    	 this.imagen = service.getMapImage(inmueble);
    }

	public Integer getCantidadAmbientes() {
		return cantidadAmbientes;
	}

	public void setCantidadAmbientes(Integer cantidadAmbientes) {
		this.cantidadAmbientes = cantidadAmbientes;
	}

	public Integer getCantidadDormitorios() {
		return cantidadDormitorios;
	}

	public void setCantidadDormitorios(Integer cantidadDormitorios) {
		this.cantidadDormitorios = cantidadDormitorios;
	}

	public Integer getSuperficieTotal() {
		return superficieTotal;
	}

	public void setSuperficieTotal(Integer superficieTotal) {
		this.superficieTotal = superficieTotal;
	}

	public Integer getSuperficieCubierta() {
		return superficieCubierta;
	}

	public void setSuperficieCubierta(Integer superficieCubierta) {
		this.superficieCubierta = superficieCubierta;
	}

	public Boolean getaEstrenar() {
		return aEstrenar;
	}

	public void setaEstrenar(Boolean aEstrenar) {
		this.aEstrenar = aEstrenar;
	}

	public Integer getCantidadCocheras() {
		return cantidadCocheras;
	}

	public void setCantidadCocheras(Integer cantidadCocheras) {
		this.cantidadCocheras = cantidadCocheras;
	}

	public Boolean getConAireAcondicionado() {
		return conAireAcondicionado;
	}

	public void setConAireAcondicionado(Boolean conAireAcondicionado) {
		this.conAireAcondicionado = conAireAcondicionado;
	}

	public Boolean getConJardin() {
		return conJardin;
	}

	public void setConJardin(Boolean conJardin) {
		this.conJardin = conJardin;
	}

	public Boolean getConParrilla() {
		return conParrilla;
	}

	public void setConParrilla(Boolean conParrilla) {
		this.conParrilla = conParrilla;
	}

	public Boolean getConPileta() {
		return conPileta;
	}

	public void setConPileta(Boolean conPileta) {
		this.conPileta = conPileta;
	}

	public String getEstadoInmueble() {
		return estadoInmueble;
	}

	public void setEstadoInmueble(String estadoInmueble) {
		this.estadoInmueble = estadoInmueble;
	}

	public String getTipoInmueble() {
		return tipoInmueble;
	}

	public void setTipoInmueble(String tipoInmueble) {
		this.tipoInmueble = tipoInmueble;
	}

	public String getClaseInmueble() {
		return claseInmueble;
	}

	public void setClaseInmueble(String claseInmueble) {
		this.claseInmueble = claseInmueble;
	}

	public String getCalleDireccion() {
		return calleDireccion;
	}

	public void setCalleDireccion(String calleDireccion) {
		this.calleDireccion = calleDireccion;
	}

	public String getLocalidadDireccion() {
		return localidadDireccion;
	}

	public void setLocalidadDireccion(String localidadDireccion) {
		this.localidadDireccion = localidadDireccion;
	}

	public Integer getNroDireccion() {
		return nroDireccion;
	}

	public void setNroDireccion(Integer nroDireccion) {
		this.nroDireccion = nroDireccion;
	}

	public String getCodPostalDireccion() {
		return codPostalDireccion;
	}

	public void setCodPostalDireccion(String codPostalDireccion) {
		this.codPostalDireccion = codPostalDireccion;
	}

	public String getProvinciaDireccion() {
		return provinciaDireccion;
	}

	public void setProvinciaDireccion(String provinciaDireccion) {
		this.provinciaDireccion = provinciaDireccion;
	}

	public String getPaisDireccion() {
		return paisDireccion;
	}

	public void setPaisDireccion(String paisDireccion) {
		this.paisDireccion = paisDireccion;
	}

	public String getNombrePropietario() {
		return nombrePropietario;
	}

	public void setNombrePropietario(String nombrePropietario) {
		this.nombrePropietario = nombrePropietario;
	}

	public String getApellidoPropietario() {
		return apellidoPropietario;
	}

	public void setApellidoPropietario(String apellidoPropietario) {
		this.apellidoPropietario = apellidoPropietario;
	}

	public String getPropietario() {
		return propietario;
	}

	public void setPropietario(String propietario) {
		this.propietario = propietario;
	}

	public Image getImagen() {
		return imagen;
	}

	public void setImagen(Image imagen) {
		this.imagen = imagen;
	}	
    
}
