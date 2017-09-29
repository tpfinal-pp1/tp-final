package com.TpFinal.data.dto.inmueble;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.TpFinal.data.dto.Identificable;

@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipoOfrecimiento_inmueble")
@Entity
@Table(name = "Inmuebles")
public class Inmueble implements Identificable {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long idInmueble;

	@Column(name = "cantidad_ambientes")
	private Integer cantidadAmbientes;

	@Column(name = "cantidad_dormitorios")
	private Integer cantidadDormitorios;

	@Column(name = "superficie_total")
	private Integer superficieTotal;

	@Column(name = "superficie_cubierta")
	private Integer superficieCubierta;

	@Column(name = "a_estrenar")
	private Boolean aEstrenar;

	@Column(name = "cantidad_cocheras")
	private Integer cantidadCocheras;

	@Column(name = "con_aire_acondicionado")
	private Boolean conAireAcondicionado;

	@Column(name = "con_jardin")
	private Boolean conJardin;

	@Column(name = "con_parrilla")
	private Boolean conParilla;

	@Column(name = "con_pileta")
	private Boolean conPileta;

	@Enumerated(EnumType.STRING)
	@Column(name = "estado_inmueble")
	private EstadoInmueble estadoInmueble;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_inmueble")
	private TipoInmueble tipoInmueble;

	@Enumerated(EnumType.STRING)
	@Column(name = "clase_inmueble")
	private ClaseInmueble claseInmueble;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idDireccion")
	private Direccion direccion;

	public Inmueble() {
		super();
	}
	
	
	private Inmueble(Builder inmuebleBuilder) {
		this.idInmueble = null;
		this.aEstrenar = inmuebleBuilder.aEstrenar;
		this.cantidadAmbientes = inmuebleBuilder.cantidadAmbientes;
		this.cantidadCocheras = inmuebleBuilder.cantidadCocheras;
		this.cantidadDormitorios = inmuebleBuilder.cantidadDormitorios;
		this.claseInmueble = inmuebleBuilder.claseInmueble;
		this.conAireAcondicionado = inmuebleBuilder.conAireAcondicionado;
		this.conJardin = inmuebleBuilder.conJardin;
		this.conParilla = inmuebleBuilder.conParilla;
		this.conPileta = inmuebleBuilder.conPileta;
		this.direccion = inmuebleBuilder.direccion;
		this.estadoInmueble = inmuebleBuilder.estadoInmueble;
		this.superficieCubierta = inmuebleBuilder.superficieCubierta;
		this.superficieTotal =  inmuebleBuilder.superficieTotal;
		this.tipoInmueble = inmuebleBuilder.tipoInmueble;
	}

	@Override
	public Long getId() {
		return idInmueble;
	}

	private void setIdInmueble(Long idInmueble) {
		this.idInmueble = idInmueble;
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

	public Boolean getConParilla() {
		return conParilla;
	}

	public void setConParilla(Boolean conParilla) {
		this.conParilla = conParilla;
	}

	public Boolean getConPileta() {
		return conPileta;
	}

	public void setConPileta(Boolean conPileta) {
		this.conPileta = conPileta;
	}

	public EstadoInmueble getEstadoInmueble() {
		return estadoInmueble;
	}

	public void setEstadoInmueble(EstadoInmueble estadoInmueble) {
		this.estadoInmueble = estadoInmueble;
	}

	public TipoInmueble getTipoInmueble() {
		return tipoInmueble;
	}

	public void setTipoInmueble(TipoInmueble tipoInmueble) {
		this.tipoInmueble = tipoInmueble;
	}

	public ClaseInmueble getClaseInmueble() {
		return claseInmueble;
	}

	public void setClaseInmueble(ClaseInmueble claseInmueble) {
		this.claseInmueble = claseInmueble;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aEstrenar == null) ? 0 : aEstrenar.hashCode());
		result = prime * result + ((cantidadAmbientes == null) ? 0 : cantidadAmbientes.hashCode());
		result = prime * result + ((cantidadCocheras == null) ? 0 : cantidadCocheras.hashCode());
		result = prime * result + ((cantidadDormitorios == null) ? 0 : cantidadDormitorios.hashCode());
		result = prime * result + ((claseInmueble == null) ? 0 : claseInmueble.hashCode());
		result = prime * result + ((conAireAcondicionado == null) ? 0 : conAireAcondicionado.hashCode());
		result = prime * result + ((conJardin == null) ? 0 : conJardin.hashCode());
		result = prime * result + ((conParilla == null) ? 0 : conParilla.hashCode());
		result = prime * result + ((conPileta == null) ? 0 : conPileta.hashCode());
		result = prime * result + ((direccion == null) ? 0 : direccion.hashCode());
		result = prime * result + ((estadoInmueble == null) ? 0 : estadoInmueble.hashCode());
		result = prime * result + ((superficieCubierta == null) ? 0 : superficieCubierta.hashCode());
		result = prime * result + ((superficieTotal == null) ? 0 : superficieTotal.hashCode());
		result = prime * result + ((tipoInmueble == null) ? 0 : tipoInmueble.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Inmueble))
			return false;
		Inmueble other = (Inmueble) obj;
		if (aEstrenar == null) {
			if (other.aEstrenar != null)
				return false;
		} else if (!aEstrenar.equals(other.aEstrenar))
			return false;
		if (cantidadAmbientes == null) {
			if (other.cantidadAmbientes != null)
				return false;
		} else if (!cantidadAmbientes.equals(other.cantidadAmbientes))
			return false;
		if (cantidadCocheras == null) {
			if (other.cantidadCocheras != null)
				return false;
		} else if (!cantidadCocheras.equals(other.cantidadCocheras))
			return false;
		if (cantidadDormitorios == null) {
			if (other.cantidadDormitorios != null)
				return false;
		} else if (!cantidadDormitorios.equals(other.cantidadDormitorios))
			return false;
		if (claseInmueble != other.claseInmueble)
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
		if (conParilla == null) {
			if (other.conParilla != null)
				return false;
		} else if (!conParilla.equals(other.conParilla))
			return false;
		if (conPileta == null) {
			if (other.conPileta != null)
				return false;
		} else if (!conPileta.equals(other.conPileta))
			return false;
		if (direccion == null) {
			if (other.direccion != null)
				return false;
		} else if (!direccion.equals(other.direccion))
			return false;
		if (estadoInmueble != other.estadoInmueble)
			return false;
		if (superficieCubierta == null) {
			if (other.superficieCubierta != null)
				return false;
		} else if (!superficieCubierta.equals(other.superficieCubierta))
			return false;
		if (superficieTotal == null) {
			if (other.superficieTotal != null)
				return false;
		} else if (!superficieTotal.equals(other.superficieTotal))
			return false;
		if (tipoInmueble != other.tipoInmueble)
			return false;
		return true;
	}

	public static class Builder {
		private Integer cantidadAmbientes;
		private Integer cantidadDormitorios;
		private Integer superficieTotal;
		private Integer superficieCubierta;
		private Boolean aEstrenar;
		private Integer cantidadCocheras;
		private Boolean conAireAcondicionado;
		private Boolean conJardin;
		private Boolean conParilla;
		private Boolean conPileta;
		private EstadoInmueble estadoInmueble;
		private TipoInmueble tipoInmueble;
		private ClaseInmueble claseInmueble;
		private Direccion direccion;

		public Builder setCantidadAmbientes(Integer cantidadAmbientes) {
			this.cantidadAmbientes = cantidadAmbientes;
			return this;
		}

		public Builder setCantidadDormitorios(Integer cantidadDormitorios) {
			this.cantidadDormitorios = cantidadDormitorios;
			return this;
		}

		public Builder setSuperficieTotal(Integer superficieTotal) {
			this.superficieTotal = superficieTotal;
			return this;
		}

		public Builder setSuperficieCubierta(Integer superficieCubierta) {
			this.superficieCubierta = superficieCubierta;
			return this;
		}

		public Builder setaEstrenar(Boolean aEstrenar) {
			this.aEstrenar = aEstrenar;
			return this;
		}

		public Builder setCantidadCocheras(Integer cantidadCocheras) {
			this.cantidadCocheras = cantidadCocheras;
			return this;
		}

		public Builder setConAireAcondicionado(Boolean conAireAcondicionado) {
			this.conAireAcondicionado = conAireAcondicionado;
			return this;
		}

		public Builder setConJardin(Boolean conJardin) {
			this.conJardin = conJardin;
			return this;
		}

		public Builder setConParilla(Boolean conParilla) {
			this.conParilla = conParilla;
			return this;
		}

		public Builder setConPileta(Boolean conPileta) {
			this.conPileta = conPileta;
			return this;
		}

		public Builder setEstadoInmueble(EstadoInmueble estadoInmueble) {
			this.estadoInmueble = estadoInmueble;
			return this;
		}

		public Builder setTipoInmueble(TipoInmueble tipoInmueble) {
			this.tipoInmueble = tipoInmueble;
			return this;
		}

		public Builder setClaseInmueble(ClaseInmueble claseInmueble) {
			this.claseInmueble = claseInmueble;
			return this;
		}

		public Builder setDireccion(Direccion direccion) {
			this.direccion = direccion;
			return this;
		}
		
		public Inmueble build() {
			return new Inmueble(this);
		}

	}

}
