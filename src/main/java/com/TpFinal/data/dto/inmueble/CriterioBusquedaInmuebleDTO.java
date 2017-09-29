package com.TpFinal.data.dto.inmueble;

import java.util.List;

public class CriterioBusquedaInmuebleDTO {
	private Boolean aEstrenar;
	private Integer minCantAmbientes;
	private Integer maxCantAmbientes;
	private Integer minCantCocheras;
	private Integer maxCantCocheras;
	private Integer minCantDormitorios;
	private Integer maxCantDormitorios;
	private List<ClaseInmueble> clasesDeInmueble;
	private Boolean conAireAcondicionado;
	private Boolean conParrilla;
	private Boolean conPileta;
	private Boolean conJardin;
	private String ciudad;
	private EstadoInmueble estadoInmueble;
	private Integer minSupCubierta;
	private Integer maxSupCubierta;
	private Integer minSupTotal;
	private Integer maxSupTotal;
	private TipoInmueble tipoInmueble;

	public CriterioBusquedaInmuebleDTO() {
		super();
	}

	public CriterioBusquedaInmuebleDTO(CriterioBusquedaInmuebleDTO.Builder builder) {
		super();
		this.aEstrenar = builder.aEstrenar;
		this.minCantAmbientes = builder.minCantAmbientes;
		this.maxCantAmbientes = builder.maxCantAmbientes;
		this.minCantCocheras = builder.minCantCocheras;
		this.maxCantCocheras = builder.maxCantCocheras;
		this.minCantDormitorios = builder.minCantDormitorios;
		this.maxCantDormitorios = builder.maxCantDormitorios;
		this.clasesDeInmueble = builder.clasesDeInmueble;
		this.conAireAcondicionado = builder.conAireAcondicionado;
		this.conParrilla = builder.conParrilla;
		this.conPileta = builder.conPileta;
		this.conJardin = builder.conJardin;
		this.ciudad = builder.ciudad;
		this.estadoInmueble = builder.estadoInmueble;
		this.minSupCubierta = builder.minSupCubierta;
		this.maxSupCubierta = builder.maxSupCubierta;
		this.minSupTotal = builder.minSupTotal;
		this.maxSupTotal = builder.maxSupTotal;
		this.tipoInmueble = builder.tipoInmueble;
	}

	public Boolean getaEstrenar() {
		return aEstrenar;
	}

	public void setaEstrenar(Boolean aEstrenar) {
		this.aEstrenar = aEstrenar;
	}

	public Integer getMinCantAmbientes() {
		return minCantAmbientes;
	}

	public void setMinCantAmbientes(Integer minCantAmbientes) {
		this.minCantAmbientes = minCantAmbientes;
	}

	public Integer getMaxCantAmbientes() {
		return maxCantAmbientes;
	}

	public void setMaxCantAmbientes(Integer maxCantAmbientes) {
		this.maxCantAmbientes = maxCantAmbientes;
	}

	public Integer getMinCantCocheras() {
		return minCantCocheras;
	}

	public void setMinCantCocheras(Integer minCantCocheras) {
		this.minCantCocheras = minCantCocheras;
	}

	public Integer getMaxCantCocheras() {
		return maxCantCocheras;
	}

	public void setMaxCantCocheras(Integer maxCantCocheras) {
		this.maxCantCocheras = maxCantCocheras;
	}

	public Integer getMinCantDormitorios() {
		return minCantDormitorios;
	}

	public void setMinCantDormitorios(Integer minCantDormitorios) {
		this.minCantDormitorios = minCantDormitorios;
	}

	public Integer getMaxCantDormitorios() {
		return maxCantDormitorios;
	}

	public void setMaxCantDormitorios(Integer maxCantDormitorios) {
		this.maxCantDormitorios = maxCantDormitorios;
	}

	public List<ClaseInmueble> getClasesDeInmueble() {
		return clasesDeInmueble;
	}

	public void setClasesDeInmueble(List<ClaseInmueble> clasesDeInmueble) {
		this.clasesDeInmueble = clasesDeInmueble;
	}

	public Boolean getConAireAcondicionado() {
		return conAireAcondicionado;
	}

	public void setConAireAcondicionado(Boolean conAireAcondicionado) {
		this.conAireAcondicionado = conAireAcondicionado;
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

	public Boolean getConJardin() {
		return conJardin;
	}

	public void setConJardin(Boolean conJardin) {
		this.conJardin = conJardin;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public EstadoInmueble getEstadoInmueble() {
		return estadoInmueble;
	}

	public void setEstadoInmueble(EstadoInmueble estadoInmueble) {
		this.estadoInmueble = estadoInmueble;
	}

	public Integer getMinSupCubierta() {
		return minSupCubierta;
	}

	public void setMinSupCubierta(Integer minSupCubierta) {
		this.minSupCubierta = minSupCubierta;
	}

	public Integer getMaxSupCubierta() {
		return maxSupCubierta;
	}

	public void setMaxSupCubierta(Integer maxSupCubierta) {
		this.maxSupCubierta = maxSupCubierta;
	}

	public Integer getMinSupTotal() {
		return minSupTotal;
	}

	public void setMinSupTotal(Integer minSupTotal) {
		this.minSupTotal = minSupTotal;
	}

	public Integer getMaxSupTotal() {
		return maxSupTotal;
	}

	public void setMaxSupTotal(Integer maxSupTotal) {
		this.maxSupTotal = maxSupTotal;
	}

	public TipoInmueble getTipoInmueble() {
		return tipoInmueble;
	}

	public void setTipoInmueble(TipoInmueble tipoInmueble) {
		this.tipoInmueble = tipoInmueble;
	}

	public static class Builder {
		private Boolean aEstrenar;
		private Integer minCantAmbientes;
		private Integer maxCantAmbientes;
		private Integer minCantCocheras;
		private Integer maxCantCocheras;
		private Integer minCantDormitorios;
		private Integer maxCantDormitorios;
		private List<ClaseInmueble> clasesDeInmueble;
		private Boolean conAireAcondicionado;
		private Boolean conParrilla;
		private Boolean conPileta;
		private Boolean conJardin;
		private String ciudad;
		private EstadoInmueble estadoInmueble;
		private Integer minSupCubierta;
		private Integer maxSupCubierta;
		private Integer minSupTotal;
		private Integer maxSupTotal;
		private TipoInmueble tipoInmueble;

		public Builder setaEstrenar(Boolean aEstrenar) {
			this.aEstrenar = aEstrenar;
			return this;
		}

		public Builder setMinCantAmbientes(Integer minCantAmbientes) {
			this.minCantAmbientes = minCantAmbientes;return this;
		}

		public Builder setMaxCantAmbientes(Integer maxCantAmbientes) {
			this.maxCantAmbientes = maxCantAmbientes;return this;
		}

		public Builder setMinCantCocheras(Integer minCantCocheras) {
			this.minCantCocheras = minCantCocheras;return this;
		}

		public Builder setMaxCantCocheras(Integer maxCantCocheras) {
			this.maxCantCocheras = maxCantCocheras;return this;
		}

		public Builder setMinCantDormitorios(Integer minCantDormitorios) {
			this.minCantDormitorios = minCantDormitorios;return this;
		}

		public Builder setMaxCantDormitorios(Integer maxCantDormitorios) {
			this.maxCantDormitorios = maxCantDormitorios;return this;
		}

		public Builder setClasesDeInmueble(List<ClaseInmueble> clasesDeInmueble) {
			this.clasesDeInmueble = clasesDeInmueble;return this;
		}

		public Builder setConAireAcondicionado(Boolean conAireAcondicionado) {
			this.conAireAcondicionado = conAireAcondicionado;return this;
		}

		public Builder setConParrilla(Boolean conParrilla) {
			this.conParrilla = conParrilla;return this;
		}

		public Builder setConPileta(Boolean conPileta) {
			this.conPileta = conPileta;return this;
		}

		public Builder setConJardin(Boolean conJardin) {
			this.conJardin = conJardin;return this;
		}

		public Builder setCiudad(String ciudad) {
			this.ciudad = ciudad;return this;
		}

		public Builder setEstadoInmueble(EstadoInmueble estadoInmueble) {
			this.estadoInmueble = estadoInmueble;return this;
		}

		public Builder setMinSupCubierta(Integer minSupCubierta) {
			this.minSupCubierta = minSupCubierta;return this;
		}

		public Builder setMaxSupCubierta(Integer maxSupCubierta) {
			this.maxSupCubierta = maxSupCubierta;return this;
		}

		public Builder setMinSupTotal(Integer minSupTotal) {
			this.minSupTotal = minSupTotal;return this;
		}

		public Builder setMaxSupTotal(Integer maxSupTotal) {
			this.maxSupTotal = maxSupTotal;return this;
		}

		public Builder setTipoInmueble(TipoInmueble tipoInmueble) {
			this.tipoInmueble = tipoInmueble;return this;
		}
		
		public CriterioBusquedaInmuebleDTO build() {
			return new CriterioBusquedaInmuebleDTO(this);
		}

	}

}
