
package com.TpFinal.data.dto.inmueble;

import java.math.BigDecimal;
import java.util.List;

import com.TpFinal.data.dto.operacion.TipoOperacion;

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
	private BigDecimal minPrecio;
	private BigDecimal maxPrecio;
	private TipoMoneda tipoMoneda;
	private TipoOperacion tipoOperacion;

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
		this.minPrecio = builder.minPrecio;
		this.maxPrecio = builder.maxPrecio;
		this.tipoMoneda = builder.tipoMoneda;
		this.tipoOperacion = builder.tipoOperacion;
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

	public BigDecimal getMinPrecio() {
		return minPrecio;
	}

	public void setMinPrecio(BigDecimal minPrecio) {
		this.minPrecio = minPrecio;
	}

	public BigDecimal getMaxPrecio() {
		return maxPrecio;
	}

	public void setMaxPrecio(BigDecimal maxPrecio) {
		this.maxPrecio = maxPrecio;
	}

	public TipoMoneda getTipoMoneda() {
		return tipoMoneda;
	}

	public void setTipoMoneda(TipoMoneda tipoMoneda) {
		this.tipoMoneda = tipoMoneda;
	}

	public TipoOperacion getTipoOperacion() {
		return tipoOperacion;
	}

	public void setTipoOperacion(TipoOperacion tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aEstrenar == null) ? 0 : aEstrenar.hashCode());
		result = prime * result + ((ciudad == null) ? 0 : ciudad.hashCode());
		result = prime * result + ((clasesDeInmueble == null) ? 0 : clasesDeInmueble.hashCode());
		result = prime * result + ((conAireAcondicionado == null) ? 0 : conAireAcondicionado.hashCode());
		result = prime * result + ((conJardin == null) ? 0 : conJardin.hashCode());
		result = prime * result + ((conParrilla == null) ? 0 : conParrilla.hashCode());
		result = prime * result + ((conPileta == null) ? 0 : conPileta.hashCode());
		result = prime * result + ((estadoInmueble == null) ? 0 : estadoInmueble.hashCode());
		result = prime * result + ((maxCantAmbientes == null) ? 0 : maxCantAmbientes.hashCode());
		result = prime * result + ((maxCantCocheras == null) ? 0 : maxCantCocheras.hashCode());
		result = prime * result + ((maxCantDormitorios == null) ? 0 : maxCantDormitorios.hashCode());
		result = prime * result + ((maxSupCubierta == null) ? 0 : maxSupCubierta.hashCode());
		result = prime * result + ((maxSupTotal == null) ? 0 : maxSupTotal.hashCode());
		result = prime * result + ((minCantAmbientes == null) ? 0 : minCantAmbientes.hashCode());
		result = prime * result + ((minCantCocheras == null) ? 0 : minCantCocheras.hashCode());
		result = prime * result + ((minCantDormitorios == null) ? 0 : minCantDormitorios.hashCode());
		result = prime * result + ((minSupCubierta == null) ? 0 : minSupCubierta.hashCode());
		result = prime * result + ((minSupTotal == null) ? 0 : minSupTotal.hashCode());
		result = prime * result + ((tipoInmueble == null) ? 0 : tipoInmueble.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof CriterioBusquedaInmuebleDTO))
			return false;
		CriterioBusquedaInmuebleDTO other = (CriterioBusquedaInmuebleDTO) obj;
		if (aEstrenar == null) {
			if (other.aEstrenar != null)
				return false;
		} else if (!aEstrenar.equals(other.aEstrenar))
			return false;
		if (ciudad == null) {
			if (other.ciudad != null)
				return false;
		} else if (!ciudad.equals(other.ciudad))
			return false;
		if (clasesDeInmueble == null) {
			if (other.clasesDeInmueble != null)
				return false;
		} else if (!clasesDeInmueble.equals(other.clasesDeInmueble))
			return false;
		if (conAireAcondicionado == null) {
			if (other.conAireAcondicionado != null)
				return false;
		} else if (!conAireAcondicionado.equals(other.conAireAcondicionado))
			return false;
		if (conJardin == null) {
			if (other.conJardin != null)
				return false;
		} else if (!conJardin.equals(other.conJardin))
			return false;
		if (conParrilla == null) {
			if (other.conParrilla != null)
				return false;
		} else if (!conParrilla.equals(other.conParrilla))
			return false;
		if (conPileta == null) {
			if (other.conPileta != null)
				return false;
		} else if (!conPileta.equals(other.conPileta))
			return false;
		if (estadoInmueble != other.estadoInmueble)
			return false;
		if (maxCantAmbientes == null) {
			if (other.maxCantAmbientes != null)
				return false;
		} else if (!maxCantAmbientes.equals(other.maxCantAmbientes))
			return false;
		if (maxCantCocheras == null) {
			if (other.maxCantCocheras != null)
				return false;
		} else if (!maxCantCocheras.equals(other.maxCantCocheras))
			return false;
		if (maxCantDormitorios == null) {
			if (other.maxCantDormitorios != null)
				return false;
		} else if (!maxCantDormitorios.equals(other.maxCantDormitorios))
			return false;
		if (maxSupCubierta == null) {
			if (other.maxSupCubierta != null)
				return false;
		} else if (!maxSupCubierta.equals(other.maxSupCubierta))
			return false;
		if (maxSupTotal == null) {
			if (other.maxSupTotal != null)
				return false;
		} else if (!maxSupTotal.equals(other.maxSupTotal))
			return false;
		if (minCantAmbientes == null) {
			if (other.minCantAmbientes != null)
				return false;
		} else if (!minCantAmbientes.equals(other.minCantAmbientes))
			return false;
		if (minCantCocheras == null) {
			if (other.minCantCocheras != null)
				return false;
		} else if (!minCantCocheras.equals(other.minCantCocheras))
			return false;
		if (minCantDormitorios == null) {
			if (other.minCantDormitorios != null)
				return false;
		} else if (!minCantDormitorios.equals(other.minCantDormitorios))
			return false;
		if (minSupCubierta == null) {
			if (other.minSupCubierta != null)
				return false;
		} else if (!minSupCubierta.equals(other.minSupCubierta))
			return false;
		if (minSupTotal == null) {
			if (other.minSupTotal != null)
				return false;
		} else if (!minSupTotal.equals(other.minSupTotal))
			return false;
		if (tipoInmueble != other.tipoInmueble)
			return false;
		return true;
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
		private BigDecimal minPrecio;
		private BigDecimal maxPrecio;
		private TipoMoneda tipoMoneda;
		private TipoOperacion tipoOperacion;

		public Builder setaEstrenar(Boolean aEstrenar) {
			this.aEstrenar = aEstrenar;
			return this;
		}

		public Builder setMinCantAmbientes(Integer minCantAmbientes) {
			this.minCantAmbientes = minCantAmbientes;
			return this;
		}

		public Builder setMaxCantAmbientes(Integer maxCantAmbientes) {
			this.maxCantAmbientes = maxCantAmbientes;
			return this;
		}

		public Builder setMinCantCocheras(Integer minCantCocheras) {
			this.minCantCocheras = minCantCocheras;
			return this;
		}

		public Builder setMaxCantCocheras(Integer maxCantCocheras) {
			this.maxCantCocheras = maxCantCocheras;
			return this;
		}

		public Builder setMinCantDormitorios(Integer minCantDormitorios) {
			this.minCantDormitorios = minCantDormitorios;
			return this;
		}

		public Builder setMaxCantDormitorios(Integer maxCantDormitorios) {
			this.maxCantDormitorios = maxCantDormitorios;
			return this;
		}

		public Builder setClasesDeInmueble(List<ClaseInmueble> clasesDeInmueble) {
			this.clasesDeInmueble = clasesDeInmueble;
			return this;
		}

		public Builder setConAireAcondicionado(Boolean conAireAcondicionado) {
			this.conAireAcondicionado = conAireAcondicionado;
			return this;
		}

		public Builder setConParrilla(Boolean conParrilla) {
			this.conParrilla = conParrilla;
			return this;
		}

		public Builder setConPileta(Boolean conPileta) {
			this.conPileta = conPileta;
			return this;
		}

		public Builder setConJardin(Boolean conJardin) {
			this.conJardin = conJardin;
			return this;
		}

		public Builder setCiudad(String ciudad) {
			this.ciudad = ciudad;
			return this;
		}

		public Builder setEstadoInmueble(EstadoInmueble estadoInmueble) {
			this.estadoInmueble = estadoInmueble;
			return this;
		}

		public Builder setMinSupCubierta(Integer minSupCubierta) {
			this.minSupCubierta = minSupCubierta;
			return this;
		}

		public Builder setMaxSupCubierta(Integer maxSupCubierta) {
			this.maxSupCubierta = maxSupCubierta;
			return this;
		}

		public Builder setMinSupTotal(Integer minSupTotal) {
			this.minSupTotal = minSupTotal;
			return this;
		}

		public Builder setMaxSupTotal(Integer maxSupTotal) {
			this.maxSupTotal = maxSupTotal;
			return this;
		}

		public Builder setTipoInmueble(TipoInmueble tipoInmueble) {
			this.tipoInmueble = tipoInmueble;
			return this;
		}

		public Builder setMinPrecio(BigDecimal minPrecio) {
			this.minPrecio = minPrecio;
			return this;
		}

		public Builder setMaxPrecio(BigDecimal maxPrecio) {
			this.maxPrecio = maxPrecio;
			return this;
		}

		public Builder setTipoMoneda(TipoMoneda tipoMoneda) {
			this.tipoMoneda = tipoMoneda;
			return this;
		}

		public Builder setTipoOperacion(TipoOperacion tipoOperacion) {
			this.tipoOperacion = tipoOperacion;
			return this;
		}

		public CriterioBusquedaInmuebleDTO build() {
			return new CriterioBusquedaInmuebleDTO(this);
		}

	}

}
