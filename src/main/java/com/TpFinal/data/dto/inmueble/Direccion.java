package com.TpFinal.data.dto.inmueble;

import javax.persistence.*;

import com.TpFinal.data.dto.Identificable;
import com.TpFinal.data.dto.Provincia;

@Entity
@Table(name = "direcciones")
public class Direccion implements Identificable {
	public static final String pLocalidad = "localidad";

	@Id
	@GeneratedValue
	@Column(name = "idDireccion")
	private Long idDireccion;

	@OneToOne(cascade = CascadeType.ALL)
	private Coordenada coordenada;

	@Column(name = "calle")
	private String calle;

	@Column(name = "localidad")
	private String localidad;

	@Column(name = "nro")
	private Integer nro;

	@Column(name = "cod_postal")
	private String codPostal;


	@Column(name = "provincia")
	private String provincia;

	@Column(name = "pais")
	private String pais;
	
	public Direccion() {
		super();
	}

	public Direccion(Builder builder) {
		this.idDireccion = null;
		this.coordenada = builder.coordenada;
		this.calle = builder.calle;
		this.localidad = builder.localidad;
		this.nro = builder.nro;
		this.codPostal = builder.codPostal;
		this.provincia = builder.provincia;
		this.pais = builder.pais;		
	}

	@Override
	public Long getId() {
		return idDireccion;
	}

	private void setIdDireccion(Long idDireccion) {
		this.idDireccion = idDireccion;
	}

	public Coordenada getCoordenada() {
		return coordenada;
	}

	public void setCoordenada(Coordenada coordenada) {
		this.coordenada = coordenada;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public Integer getNro() {
		return nro;
	}

	public void setNro(Integer nro) {
		this.nro = nro;
	}

	public String getCodPostal() {
		return codPostal;
	}

	public void setCodPostal(String codPostal) {
		this.codPostal = codPostal;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((calle == null) ? 0 : calle.hashCode());
		result = prime * result + ((codPostal == null) ? 0 : codPostal.hashCode());
		result = prime * result + ((coordenada == null) ? 0 : coordenada.hashCode());
		result = prime * result + ((localidad == null) ? 0 : localidad.hashCode());
		result = prime * result + ((nro == null) ? 0 : nro.hashCode());
		result = prime * result + ((pais == null) ? 0 : pais.hashCode());
		result = prime * result + ((provincia == null) ? 0 : provincia.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Direccion))
			return false;
		Direccion other = (Direccion) obj;
		if (calle == null) {
			if (other.calle != null)
				return false;
		} else if (!calle.equals(other.calle))
			return false;
		if (codPostal == null) {
			if (other.codPostal != null)
				return false;
		} else if (!codPostal.equals(other.codPostal))
			return false;
		if (coordenada == null) {
			if (other.coordenada != null)
				return false;
		} else if (!coordenada.equals(other.coordenada))
			return false;
		if (localidad == null) {
			if (other.localidad != null)
				return false;
		} else if (!localidad.equals(other.localidad))
			return false;
		if (nro == null) {
			if (other.nro != null)
				return false;
		} else if (!nro.equals(other.nro))
			return false;
		if (pais == null) {
			if (other.pais != null)
				return false;
		} else if (!pais.equals(other.pais))
			return false;
		if (provincia == null) {
			if (other.provincia != null)
				return false;
		} else if (!provincia.equals(other.provincia))
			return false;
		return true;
	}
	
	

	@Override
	public String toString() {
	    return calle + " " + nro + ", "+localidad+", "+provincia+", "+pais;
	}



	public static class Builder {
		private Coordenada coordenada;
		private String calle;
		private String localidad;
		private Integer nro;
		private String codPostal;
		private String provincia;
		private String pais;

		public Builder setCoordenada(Coordenada coordenada) {
			this.coordenada = coordenada;
			return this;
		}

		public Builder setCalle(String calle) {
			this.calle = calle;
			return this;
		}

		public Builder setLocalidad(String localidad) {
			this.localidad = localidad;
			return this;
		}

		public Builder setNro(Integer nro) {
			this.nro = nro;
			return this;
		}

		public Builder setCodPostal(String codPostal) {
			this.codPostal = codPostal;
			return this;
		}

		public Builder setProvincia(String provincia) {
			this.provincia = provincia;
			return this;
		}

		public Builder setPais(String pais) {
			this.pais = pais;
			return this;
		}
		
		public Direccion build() {
			return new Direccion(this);
		}

	}

}
